/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.connector;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

import com.thalesgroup.orchestra.framework.connector.helper.SerializationHelper;

/**
 * Connector activator.
 * @author t0076261
 */
public class ConnectorActivator extends Plugin {
  /**
   * Shared instance.
   */
  private static ConnectorActivator __instance;
  /**
   * Serialization helper.
   */
  private SerializationHelper _serializationHelper;

  /**
   * Get plug-in ID.
   * @return
   */
  public String getPluginId() {
    return getBundle().getSymbolicName();
  }

  /**
   * Get serialization helper.
   * @return A not <code>null</code> {@link SerializationHelper}.
   */
  public SerializationHelper getSerializationHelper() {
    if (null == _serializationHelper) {
      _serializationHelper = new SerializationHelper();
    }
    return _serializationHelper;
  }

  /**
   * @see org.eclipse.core.runtime.Plugin#start(org.osgi.framework.BundleContext)
   */
  @Override
  public void start(BundleContext context_p) throws Exception {
    super.start(context_p);
    __instance = this;
  }

  /**
   * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
   */
  @Override
  public void stop(BundleContext context_p) throws Exception {
    __instance = null;
    super.stop(context_p);
  }

  /**
   * Get shared instance.
   * @return
   */
  public static ConnectorActivator getInstance() {
    return __instance;
  }
}