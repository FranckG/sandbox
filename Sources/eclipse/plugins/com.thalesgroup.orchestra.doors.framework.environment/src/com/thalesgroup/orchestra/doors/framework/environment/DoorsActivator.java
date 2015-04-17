/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.doors.framework.environment;

import org.osgi.framework.BundleContext;

import com.thalesgroup.orchestra.framework.root.ui.activator.AbstractUIActivator;

/**
 * @author S0032874 <br>
 *         Activator for Doors Environment, extends {@link AbstractUIActivator}
 */
public class DoorsActivator extends AbstractUIActivator {

  /**
   * Shared instance.
   */
  private static DoorsActivator __instance;

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
  public static DoorsActivator getInstance() {
    return __instance;
  }

}
