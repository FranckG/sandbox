package com.thalesgroup.orchestra.framework.integration.tests.connector.mockecf;

import org.osgi.framework.BundleContext;

import com.thalesgroup.orchestra.framework.common.activator.AbstractActivator;

public class MockEcfConnectorActivator extends AbstractActivator {
  /**
   * Shared instance.
   */
  private static MockEcfConnectorActivator __instance;

  /**
   * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
   */
  @Override
  public void start(BundleContext bundleContext) throws Exception {
    super.start(bundleContext);
    __instance = this;
  }

  /**
   * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
   */
  @Override
  public void stop(BundleContext bundleContext) throws Exception {
    super.stop(bundleContext);
    __instance = null;
  }

  /**
   * Get shared instance.
   * @return
   */
  public static MockEcfConnectorActivator getInstance() {
    return __instance;
  }
}