/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.handler.data;

import java.util.Map;

import org.eclipse.core.runtime.IStatus;

/**
 * A context change listeners handler for remote applications.<br>
 * In process application should register as {@link ICurrentContextChangeListener} against {@link DataHandler}.
 * @author t0076261
 */
public interface IRemoteContextChangeListenersHandler {
  /**
   * Context is about to be switched to specified one.<br>
   * Collect remote listeners statuses so as to present the user with current usages.
   * @param newContextName_p
   * @return A not <code>null</code> {@link Map} of (Remote Listener Name, Change Status).
   */
  public Map<String, IStatus> preContextChangeCollectRemoteStatus(String newContextName_p);

  /**
   * Context has just been switched to specified one.<br>
   * Collection remote listeners statuses after taking into account the new context.
   * @param newContextName_p
   * @return A not <code>null</code> {@link Map} of (Remote Listener Name, Change Status).
   */
  public Map<String, IStatus> postContextChangeCollectRemoteStatus(String newContextName_p, boolean allowUserInteractions_p);
}