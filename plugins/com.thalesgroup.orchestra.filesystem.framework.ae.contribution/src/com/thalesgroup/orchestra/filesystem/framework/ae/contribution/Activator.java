package com.thalesgroup.orchestra.filesystem.framework.ae.contribution;

import org.osgi.framework.BundleContext;

import com.thalesgroup.orchestra.framework.root.ui.activator.AbstractUIActivator;

public class Activator extends AbstractUIActivator {
  /**
   * Shared instance.
   */
  private static Activator __instance;

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
  public static Activator getInstance() {
    return __instance;
  }
}
