/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.handler.data;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;

import com.thalesgroup.orchestra.framework.model.contexts.Context;

/**
 * @author s0011584
 */
public interface ICurrentContextChangeListener {
  /**
   * Context has been changed to specified one.<br>
   * There might have been errors while changing the context.<br>
   * They are described in the specified error status.
   * @param currentContext_p
   * @param errorStatus_p
   * @param progressMonitor_p
   * @param allowUserInteractions_p <code>true</code> if implementation is allowed to interact with user, <code>false</code> to disable all dialogs/... with
   *          user.
   * @return Status
   */
  IStatus contextChanged(Context currentContext_p, IStatus errorStatus_p, IProgressMonitor progressMonitor_p, boolean allowUserInteractions_p);

  /**
   * Pre-hook before specified context is set as current one.
   * @param futureContext_p
   * @param progressMonitor_p
   * @param allowUserInteractions_p <code>true</code> if implementation is allowed to interact with user, <code>false</code> to disable all dialogs/... with
   *          user.
   */
  void preContextChange(Context futureContext_p, IProgressMonitor progressMonitor_p, boolean allowUserInteractions_p);
}