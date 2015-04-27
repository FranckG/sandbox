/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.connector.sdk;

import org.osgi.framework.BundleContext;

import com.thalesgroup.orchestra.framework.root.ui.activator.AbstractUIActivator;

/**
 * @author t0076261
 */
public class SdkWizardsActivator extends AbstractUIActivator {
  /**
   * Shared instance.
   */
  private static SdkWizardsActivator __default;

  /**
   * @see org.eclipse.core.runtime.Plugin#start(org.osgi.framework.BundleContext)
   */
  @Override
  public void start(BundleContext context_p) throws Exception {
    super.start(context_p);
    __default = this;
  }

  /**
   * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
   */
  @Override
  public void stop(BundleContext context_p) throws Exception {
    __default = null;
    super.stop(context_p);
  }

  /**
   * Get shared instance.
   * @return
   */
  public static SdkWizardsActivator getDefault() {
    return __default;
  }
}
