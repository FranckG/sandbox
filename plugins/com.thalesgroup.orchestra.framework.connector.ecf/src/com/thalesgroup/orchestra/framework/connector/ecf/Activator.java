package com.thalesgroup.orchestra.framework.connector.ecf;

import org.osgi.framework.BundleContext;

import com.thalesgroup.orchestra.framework.common.activator.AbstractActivator;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractActivator {
  // The shared instance
  private static Activator __plugin;

  /**
   * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
   */
  @Override
  public void start(BundleContext context) throws Exception {
    super.start(context);
    __plugin = this;
  }

  /**
   * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
   */
  @Override
  public void stop(BundleContext context) throws Exception {
    __plugin = null;
    super.stop(context);
  }

  /**
   * Returns the shared instance
   * @return the shared instance
   */
  public static Activator getDefault() {
    return __plugin;
  }
}
