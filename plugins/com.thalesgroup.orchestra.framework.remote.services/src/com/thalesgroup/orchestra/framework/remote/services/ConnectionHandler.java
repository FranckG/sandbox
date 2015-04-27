/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.remote.services;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ecf.core.ContainerFactory;
import org.eclipse.ecf.core.IContainer;
import org.eclipse.ecf.core.identity.ID;
import org.eclipse.ecf.core.identity.IDFactory;
import org.eclipse.ecf.remoteservice.IRemoteServiceContainerAdapter;

import com.thalesgroup.orchestra.framework.lib.base.conf.ServerConfParam;
import com.thalesgroup.orchestra.framework.remote.services.connectionLoss.ConnectionLossHandler;
import com.thalesgroup.orchestra.framework.remote.services.connectionLoss.IConnectionLossListener;
import com.thalesgroup.orchestra.framework.remote.services.connectionLoss.IReconnectionListener;
import com.thalesgroup.orchestra.framework.remote.services.context.IRemoteContextChangeListener;
import com.thalesgroup.orchestra.framework.remote.services.context.IRemoteContextChangeListener2;
import com.thalesgroup.orchestra.framework.remote.services.context.IRemoteContextChangeListenerProxy;
import com.thalesgroup.orchestra.framework.remote.services.context.IRemoteContextChangeListenerProxy2;
import com.thalesgroup.orchestra.framework.remote.services.internal.context.RemoteContextChangeListenerProxy;
import com.thalesgroup.orchestra.framework.remote.services.internal.context.RemoteContextChangeListenerProxy2;

/**
 * Connection to server handler.<br>
 * Allows for subscription/unsubscription of remote implementations.
 * @author t0076261
 */
public class ConnectionHandler {
  /**
   * Registered implementation to ECF container.
   */
  private Map<Object, IContainer> _implementationToContainer = new HashMap<Object, IContainer>(0);

  private ConnectionLossHandler _connectionLossHandler;

  /**
   * Register a new remote change context listener to the server.
   * @param contextChangeListener_p A not <code>null</code> implementation of {@link IRemoteContextChangeListener}.
   * @return A not <code>null</code> status that describes the connection result.
   */
  public IStatus register(IRemoteContextChangeListener contextChangeListener_p) {
    return register(contextChangeListener_p, contextChangeListener_p.getName(), IRemoteContextChangeListenerProxy.class, new RemoteContextChangeListenerProxy(
        contextChangeListener_p));
  }

  /**
   * Register a new remote change context listener to the server.
   * @param contextChangeListener_p A not <code>null</code> implementation of {@link IRemoteContextChangeListener}.
   * @return A not <code>null</code> status that describes the connection result.
   */
  public IStatus register(IRemoteContextChangeListener2 contextChangeListener_p) {
    return register(contextChangeListener_p, contextChangeListener_p.getName(), IRemoteContextChangeListenerProxy2.class,
        new RemoteContextChangeListenerProxy2(contextChangeListener_p));
  }

  /**
   * Register specified implementation, through specified proxy, to the server.
   * @param implementation_p The implementation to register.
   * @param name_p A textual representation of the implementation.
   * @param proxyInterface_p The implementation proxy interface to register.
   * @param proxyImplementation_p The proxy implementation to register.
   * @return
   */
  protected IStatus register(final Object implementation_p, String name_p, Class<?> proxyInterface_p, Object proxyImplementation_p) {
    String pluginId = "com.thalesgroup.orchestra.framework.remote.services"; //$NON-NLS-1$
    try {
      // Read server configuration.
      IStatus result = ServerConfParam.getInstance().readFile();
      if (!result.isOK()) {
        return new MultiStatus(pluginId, 0, new IStatus[] { result }, Messages.ConnectionHandler_Error_UnableToGetServerConfiguration, null);
      }
      // Get client container.
      IContainer container = ContainerFactory.getDefault().createContainer("ecf.generic.client"); //$NON-NLS-1$
      if (null == container) {
        throw new RuntimeException(Messages.ConnectionHandler_Error_UnableToCreateContainer);
      }
      // Create a connection loss handler if it does not exist already
      if (null == _connectionLossHandler) {
        _connectionLossHandler = new ConnectionLossHandler();
      }
      container.addListener(_connectionLossHandler);
      // Get location from server configuration.
      String serverId = ServerConfParam.getInstance().getEcfServerLocation();
      if (null == serverId) {
        throw new RuntimeException(Messages.ConnectionHandler_Error_ServerNotResolved);
      }
      ID serverID = IDFactory.getDefault().createStringID(serverId);
      container.connect(serverID, null);
      // Register remote service to hub.
      IRemoteServiceContainerAdapter containerAdapter = (IRemoteServiceContainerAdapter) container.getAdapter(IRemoteServiceContainerAdapter.class);
      containerAdapter.registerRemoteService(new String[] { proxyInterface_p.getName() }, proxyImplementation_p, null);
      // Retain container.
      _implementationToContainer.put(implementation_p, container);
    } catch (Exception exception_p) {
      // Create error result.
      return new Status(IStatus.ERROR, pluginId, null, exception_p);
    }
    // Create OK result.
    return new Status(IStatus.OK, pluginId, MessageFormat.format(Messages.ConnectionHandler_Ok_ImplementationSuccessfullyRegistered, name_p,
        implementation_p.getClass()));
  }

  /**
   * Unregister specified implementation of remote context change listener.
   * @param contextChangeListener_p A not <code>null</code> implementation of {@link IRemoteContextChangeListener}.
   * @return A not <code>null</code> status that describes the connection result.
   */
  public IStatus unregister(IRemoteContextChangeListener contextChangeListener_p) {
    return unregister(contextChangeListener_p, contextChangeListener_p.getName());
  }

  /**
   * Unregister specified implementation of remote context change listener.
   * @param contextChangeListener_p A not <code>null</code> implementation of {@link IRemoteContextChangeListener}.
   * @return A not <code>null</code> status that describes the connection result.
   */
  public IStatus unregister(IRemoteContextChangeListener2 contextChangeListener_p) {
    return unregister(contextChangeListener_p, contextChangeListener_p.getName());
  }

  /**
   * Unregister specified implementation.
   * @param implementation_p The implementation to register.
   * @param name_p A textual representation of the implementation.
   * @return
   */
  protected IStatus unregister(Object implementation_p, String name_p) {
    String pluginId = "com.thalesgroup.orchestra.framework.remote.services"; //$NON-NLS-1$
    if (null == implementation_p) {
      return new Status(IStatus.ERROR, pluginId, Messages.ConnectionHandler_Error_NoImplementationProvided);
    }
    IContainer container = _implementationToContainer.remove(implementation_p);
    if (null == container) {
      return new Status(IStatus.ERROR, pluginId, MessageFormat.format(Messages.ConnectionHandler_Error_NoImplementationFound, name_p,
          implementation_p.getClass()));
    }
    container.removeListener(_connectionLossHandler);
    container.disconnect();
    container.dispose();
    return new Status(IStatus.OK, pluginId, MessageFormat.format(Messages.ConnectionHandler_Ok_ImplementationSuccessfullyUnregistered, name_p,
        implementation_p.getClass()));
  }

  /**
   * Add connection loss listener
   */
  public void setConnectionLossListener(IConnectionLossListener listener_p) {
    if (null != _connectionLossHandler) {
      _connectionLossHandler.setConnectionLossListener(listener_p);
    }
  }

  /**
   * Remove connection loss listener
   * @param object_p
   */
  public void unsetConnectionLossListener() {
    if (null != _connectionLossHandler) {
      _connectionLossHandler.unsetConnectionLossListener();
    }
  }

  /**
   * Add reconnection listener
   * @param object_p
   * @param listener_p
   */
  public void setReconnectionListener(IReconnectionListener listener_p) {
    if (null != _connectionLossHandler) {
      _connectionLossHandler.setReconnectionListener(listener_p);
    }
  }

  /**
   * Remove reconnection listener
   * @param object_p
   */
  public void unsetReconnectionListener() {
    if (null != _connectionLossHandler) {
      _connectionLossHandler.unsetReconnectionListener();
    }
  }
}