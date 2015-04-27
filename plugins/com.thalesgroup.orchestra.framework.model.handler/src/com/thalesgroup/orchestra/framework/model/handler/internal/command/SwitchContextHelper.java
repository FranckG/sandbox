/**
 * Copyright (c) THALES, 2010. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.handler.internal.command;

import java.util.Collections;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.wizard.WizardDialog;

import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.data.IRemoteContextChangeListenersHandler;
import com.thalesgroup.orchestra.framework.root.ui.AbstractRunnableWithDisplay;
import com.thalesgroup.orchestra.framework.root.ui.DisplayHelper;

/**
 * Handles interactions with user on context switch and reporting from registered remote applications.
 * @author t0076261
 */
public class SwitchContextHelper {
  /**
   * Confirm context switch ?
   * @param newContextName_p
   * @return <code>true</code> to proceed with context switching. <code>false</code> to end switching before it is taken into account.
   */
  public boolean confirmSwitch(String newContextName_p) {
    // Get PreContextChange statuses from clients.
    Map<String, IStatus> remoteStatus = getPreContextChangeStatuses(newContextName_p);
    if (remoteStatus.isEmpty()) {
      return true;
    }
    // Check all statuses first. If all are OK, then proceed with switching.
    boolean allOk = true;
    for (IStatus status : remoteStatus.values()) {
      allOk &= status.isOK();
    }
    // Proceed with switching.
    if (allOk) {
      return true;
    }
    // Not all statuses are OK, display returns to user.
    final SwitchContextWizard wizard = new SwitchContextWizard(remoteStatus, newContextName_p);
    // Display wizard synchronously.
    DisplayHelper.displayRunnable(new AbstractRunnableWithDisplay() {
      public void run() {
        // Open wizard dialog.
        WizardDialog dialog = new WizardDialog(getDisplay().getActiveShell(), wizard);
        dialog.open();
      }
    }, false);
    return wizard.proceedWithSwitching();
  }

  /**
   * Get PostContextChange clients statuses.
   * @param newContextName_p
   * @return A not <code>null</code> but possibly empty {@link Map} of (Client name, Status with regards to actions taken after context switching).
   */
  public Map<String, IStatus> getPostContextChangeStatuses(String newContextName_p, boolean allowUserInteractions_p) {
    IRemoteContextChangeListenersHandler remoteContextChangeListenerHandler = ModelHandlerActivator.getDefault().getRemoteContextChangeListenerHandler();
    // No handler set.
    if (null == remoteContextChangeListenerHandler) {
      return Collections.emptyMap();
    }
    // Get postContextChange statuses from remote listeners.
    Map<String, IStatus> postContextChangeCollectRemoteStatus =
        remoteContextChangeListenerHandler.postContextChangeCollectRemoteStatus(newContextName_p, allowUserInteractions_p);
    if (null == postContextChangeCollectRemoteStatus) {
      postContextChangeCollectRemoteStatus = Collections.emptyMap();
    }
    return postContextChangeCollectRemoteStatus;
  }

  /**
   * Get PreContextChange clients current statuses.
   * @param newContextName_p
   * @return A not <code>null</code> but possibly empty {@link Map} of (Client name, Current Status with regards to context switching).
   */
  public Map<String, IStatus> getPreContextChangeStatuses(String newContextName_p) {
    IRemoteContextChangeListenersHandler remoteContextChangeListenerHandler = ModelHandlerActivator.getDefault().getRemoteContextChangeListenerHandler();
    // No handler set, confirm switching to new context.
    if (null == remoteContextChangeListenerHandler) {
      return Collections.emptyMap();
    }
    // Get preContextChange statuses from remote listeners.
    Map<String, IStatus> preContextChangeCollectRemoteStatus = remoteContextChangeListenerHandler.preContextChangeCollectRemoteStatus(newContextName_p);
    if (null == preContextChangeCollectRemoteStatus) {
      preContextChangeCollectRemoteStatus = Collections.emptyMap();
    }
    return preContextChangeCollectRemoteStatus;
  }
}