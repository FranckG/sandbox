/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environments.actions;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import com.thalesgroup.orchestra.framework.FrameworkActivator;
import com.thalesgroup.orchestra.framework.common.CommonActivator;
import com.thalesgroup.orchestra.framework.environment.BaselineResult;
import com.thalesgroup.orchestra.framework.root.ui.AbstractRunnableWithDisplay;
import com.thalesgroup.orchestra.framework.root.ui.DisplayHelper;

/**
 * Trigger a baseline creation action.
 * @author t0076261
 */
public class MakeBaselineAction extends BaseSelectionListenerAction {
  /**
   * Constructor.
   */
  public MakeBaselineAction() {
    super(Messages.MakeBaselineAction_Text);
  }

  /**
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    ProgressMonitorDialog progressMonitorDialog = new ProgressMonitorDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell());
    try {
      progressMonitorDialog.run(true, false, new IRunnableWithProgress() {
        /**
         * @see org.eclipse.jface.operation.IRunnableWithProgress#run(org.eclipse.core.runtime.IProgressMonitor)
         */
        public void run(IProgressMonitor monitor_p) throws InvocationTargetException, InterruptedException {
          // Trigger baseline creation.
          final BaselineResult makeBaselineResult = FrameworkActivator.getDefault().getEnvironmentsRegistry().makeBaseline(null, monitor_p);
          // Deal with baseline result.
          if (!makeBaselineResult.getStatus().isOK()) {
            DisplayHelper.displayErrorDialog(Messages.MakeBaselineAction_Text, Messages.BaselineActions_ExecutionErrorMessage, makeBaselineResult.getStatus());
          } else {
            DisplayHelper.displayRunnable(new AbstractRunnableWithDisplay() {
              /**
               * @see java.lang.Runnable#run()
               */
              public void run() {
                MessageDialog.openInformation(getDisplay().getActiveShell(), Messages.MakeBaselineAction_Text, makeBaselineResult.getStatus().getMessage());
              }
            }, false);
          }
        }
      });
    } catch (Exception exception_p) {
      StringBuilder loggerMessage = new StringBuilder("MakeBaselineAction.run(..) _ "); //$NON-NLS-1$
      CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.ERROR, exception_p);
    }
  }
}