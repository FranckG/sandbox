/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.exchange;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * Exchange models activator.
 * @author t0076261
 */
public class ExchangeActivator extends Plugin {
  /**
   * Shared instance.
   */
  private static ExchangeActivator __instance;

  /**
   * Get plug-in ID.
   * @return
   */
  public String getPluginId() {
    return getBundle().getSymbolicName();
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
    super.stop(context_p);
    __instance = null;
  }

  /**
   * Get shared instance.
   * @return
   */
  public static ExchangeActivator getInstance() {
    return __instance;
  }
}