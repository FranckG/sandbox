package com.thalesgroup.orchestra.framework.integration.tests.application;

import org.osgi.framework.BundleContext;

import com.thalesgroup.orchestra.framework.root.ui.activator.AbstractUIActivator;

public class FrameworkApplicationTestActivator extends AbstractUIActivator {
  /**
   * Shared instance.
   */
  private static FrameworkApplicationTestActivator __plugin;

  /**
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
   */
  @Override
  public void start(BundleContext bundleContext) throws Exception {
    super.start(bundleContext);
    __plugin = this;

  }

  /**
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
   */
  @Override
  public void stop(BundleContext bundleContext) throws Exception {
    __plugin = null;
    super.stop(bundleContext);
  }

  /**
   * Returns the shared instance
   * @return the shared instance
   */
  public static FrameworkApplicationTestActivator getDefault() {
    return __plugin;
  }
}
