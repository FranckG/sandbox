/**
 * <p>Copyright (c) 2002-2007 : Thales Services - Engineering & Process Management</p>
 * <p>Société : Thales Services - Engineering & Process Management</p>
 * <p>Thales Part Number 16 262 601</p>
 */
package com.thalesgroup.orchestra.framework.ecf.services.handler;

import org.eclipse.core.runtime.IStatus;

import com.thalesgroup.orchestra.framework.ecf.services.connector.IRemoteConnector;
import com.thalesgroup.orchestra.framework.ecf.services.connector.IRemoteConnectorProxy;
import com.thalesgroup.orchestra.framework.ecf.services.internal.connector.RemoteConnectorProxy;
import com.thalesgroup.orchestra.framework.remote.services.ConnectionHandler;

/**
 * Client ECF connectors handler.<br>
 * Allows for subscription/unsubscription of client connectors implementations.
 * @author t0076261
 */
public class ClientConnectorHandler extends ConnectionHandler {
  /**
   * Register a new connector implementation to Orchestra Framework server.<br>
   * @param connector_p The {@link IRemoteConnector} implementation to subscribe.
   * @return {@link IStatus}
   */
  public IStatus register(IRemoteConnector connector_p) {
    String name = null;
    try {
      name = connector_p.getType();
    } catch (Exception exception_p) {
      name = ""; //$NON-NLS-1$
    }
    return register(connector_p, name, IRemoteConnectorProxy.class, new RemoteConnectorProxy(connector_p));
  }

  /**
   * Unregister specified {@link IRemoteConnector} implementation from Orchestra Framework server.
   * @param connector_p
   * @return {@link IStatus}
   */
  public IStatus unregister(IRemoteConnector connector_p) {
    String name = null;
    try {
      name = connector_p.getType();
    } catch (Exception exception_p) {
      name = ""; //$NON-NLS-1$
    }
    return unregister(connector_p, name);
  }
}