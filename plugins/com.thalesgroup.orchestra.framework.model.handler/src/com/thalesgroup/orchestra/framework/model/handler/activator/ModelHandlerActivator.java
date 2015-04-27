/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.handler.activator;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.osgi.framework.BundleContext;

import com.thalesgroup.orchestra.framework.common.helper.ExtensionPointHelper;
import com.thalesgroup.orchestra.framework.model.contexts.provider.ContextsItemProviderAdapterFactory;
import com.thalesgroup.orchestra.framework.model.handler.IValidationHandler;
import com.thalesgroup.orchestra.framework.model.handler.data.ContextsEditingDomain;
import com.thalesgroup.orchestra.framework.model.handler.data.DataHandler;
import com.thalesgroup.orchestra.framework.model.handler.data.IRemoteContextChangeListenersHandler;
import com.thalesgroup.orchestra.framework.root.ui.activator.AbstractUIActivator;

/**
 * Model handler activator.<br>
 * Provides with the unique editing domain reference.
 * @author t0076261
 */
public class ModelHandlerActivator extends AbstractUIActivator {
  /**
   * Shared instance.
   */
  private static ModelHandlerActivator __default;
  /**
   * Data handler.
   */
  private DataHandler _dataHandler;
  /**
   * The editing domain reference.
   */
  private ContextsEditingDomain _editingDomain;
  /**
   * Registered consumers
   */
  private Set<String> _registeredConsumers;
  /**
   * Remote ContextChangeListener handler.
   */
  private IRemoteContextChangeListenersHandler _remoteContextChangeListenerHandler;
  /**
   * Validation handler.
   */
  private IValidationHandler _validationHandler;

  /**
   * Clean editing domain.<br>
   * This removes all resources from its resource set.<br>
   * Also resets the cross referencer to emptiness.<br>
   * <b>WARNING</b> This should only be used by the tests framework. This is not intended to be used within the application !
   */
  public void cleanEditingDomain() {
    // Precondition.
    if (null == _editingDomain) {
      return;
    }
    // Clean resource set.
    _editingDomain.getResourceSet().clean();
  }

  /**
   * Get data handler unique instance.
   * @return A not <code>null</code> instance of {@link DataHandler}.
   */
  public DataHandler getDataHandler() {
    if (null == _dataHandler) {
      _dataHandler = new DataHandler();
    }
    return _dataHandler;
  }

  /**
   * Get editing domain.
   * @return A not <code>null</code> instance of {@link AdapterFactoryEditingDomain}.
   */
  public ContextsEditingDomain getEditingDomain() {
    // Lazy creation of the editing domain.
    if (null == _editingDomain) {
      // Create adapter factory.
      ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory();
      adapterFactory.addAdapterFactory(new ContextsItemProviderAdapterFactory());
      // Feed the new editing domain with it.
      _editingDomain = new ContextsEditingDomain(adapterFactory);
    }
    return _editingDomain;
  }

  public Set<String> getRegisteredConsumers() {
    if (null == _registeredConsumers) {
      _registeredConsumers = new HashSet<String>();
    }
    return _registeredConsumers;
  }

  /**
   * Get unique {@link IRemoteContextChangeListenersHandler} instance.
   * @return
   */
  public IRemoteContextChangeListenersHandler getRemoteContextChangeListenerHandler() {
    return _remoteContextChangeListenerHandler;
  }

  /**
   * Get validation handler implementation.
   * @return <code>null</code> if no such contribution could be found.
   */
  public IValidationHandler getValidationHandler() {
    if (null != _validationHandler) {
      return _validationHandler;
    }
    _validationHandler = (IValidationHandler) ExtensionPointHelper.createExecutableExtension(getPluginId(), "validationHandler", null); //$NON-NLS-1$
    return _validationHandler;
  }

  /**
   * @param toolInstanceId_p
   */
  public void registerConsumer(String toolInstanceId_p) {
    getRegisteredConsumers().add(toolInstanceId_p);
  }

  /**
   * Set unique {@link IRemoteContextChangeListenersHandler} instance.
   * @param remoteContextChangeListenerHandler_p
   */
  public void setRemoteContextChangeListenerHandler(IRemoteContextChangeListenersHandler remoteContextChangeListenerHandler_p) {
    _remoteContextChangeListenerHandler = remoteContextChangeListenerHandler_p;
  }

  /**
   * @see org.eclipse.core.runtime.Plugin#start(org.osgi.framework.BundleContext)
   */
  @Override
  public void start(BundleContext context_p) throws Exception {
    super.start(context_p);
    __default = this;
    // Resolve editing domain right now.
    getEditingDomain();
  }

  /**
   * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
   */
  @Override
  public void stop(BundleContext context_p) throws Exception {
    __default = null;
    if (null != _editingDomain) {
      _editingDomain.getResourceSet().clean();
      _editingDomain = null;
    }
    super.stop(context_p);
  }

  /**
   * @param toolInstanceId_p
   */
  public void unregisterConsumer(String toolInstanceId_p) {
    getRegisteredConsumers().remove(toolInstanceId_p);
  }

  /**
   * Get shared instance.
   * @return
   */
  public static ModelHandlerActivator getDefault() {
    return __default;
  }
}