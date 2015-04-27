/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ecf.tests;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * @author t0076261
 *
 */
public class EcfTestActivator extends Plugin {
  /**
   * Shared instance.
   */
  private static EcfTestActivator __instance;

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
    __instance = null;
    super.stop(context_p);
  }

  /**
   * Get shared instance.
   * @return
   */
  public static EcfTestActivator getInstance() {
    return __instance;
  }
}
