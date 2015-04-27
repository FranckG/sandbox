/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.integration.tests.connector.mockecf.launcher;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * @author T0052089
 */
public class Activator implements BundleActivator {

  private static BundleContext __context;

  /*
   * (non-Javadoc)
   * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
   */
  public void start(BundleContext bundleContext) throws Exception {
    __context = bundleContext;
  }

  /*
   * (non-Javadoc)
   * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
   */
  public void stop(BundleContext bundleContext) throws Exception {
    __context = null;
  }

  static BundleContext getContext() {
    return __context;
  }
}
