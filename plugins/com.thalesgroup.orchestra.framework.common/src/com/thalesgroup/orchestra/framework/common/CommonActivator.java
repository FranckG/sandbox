/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.common;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

import com.thalesgroup.orchestra.framework.common.activator.AbstractActivator;

/**
 * @author t0076261
 */
public class CommonActivator extends AbstractActivator {
  /**
   * Shared instance.
   */
  private static CommonActivator __instance;

  /**
   * Get shared instance.
   * @return
   */
  public static CommonActivator getInstance() {
    return __instance;
  }

  /**
   * @see org.eclipse.core.runtime.Plugin#start(org.osgi.framework.BundleContext)
   */
  @Override
  public void start(BundleContext context_p) throws Exception {
    __instance = this;
    super.start(context_p);
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
   * Log a message to the eclipse log file.
   * @param message_p The message to log. Can not be <code>null</code>.
   * @param status_p The message severity, from {@link IStatus#OK} to {@link IStatus#ERROR}.
   * @param throwable_p An error description, if needed. Can be <code>null</code>.
   */
  public void logMessage(String message_p, int status_p, Throwable throwable_p) {
    // Precondition.
    if (null == message_p) {
      return;
    }
    // Create expected status.
    IStatus resultingMessage = new Status(status_p, getPluginId(), message_p, throwable_p);
    // Log it.
    getLog().log(resultingMessage);
  }
}