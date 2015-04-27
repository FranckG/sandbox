/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environments.actions;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.PlatformUI;

import com.thalesgroup.orchestra.framework.FrameworkActivator;
import com.thalesgroup.orchestra.framework.common.CommonActivator;
import com.thalesgroup.orchestra.framework.connector.CommandStatus;
import com.thalesgroup.orchestra.framework.root.ui.DisplayHelper;

/**
 * Use a baseline action.
 * @author t0076261
 */
public class UseBaselineAction extends Action {
  /**
   * Constructor.
   */
  public UseBaselineAction() {
    super(Messages.UseBaselineAction_Text);
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
          // Trigger baseline usage.
          CommandStatus status = FrameworkActivator.getDefault().getEnvironmentsRegistry().useBaseline(monitor_p);
          // Deal with baseline result.
          if (!status.isOK()) {
            DisplayHelper.displayErrorDialog(Messages.UseBaselineAction_Text, Messages.BaselineActions_ExecutionErrorMessage, status);
          }
        }
      });
    } catch (Exception exception_p) {
      StringBuilder loggerMessage = new StringBuilder("MakeBaselineAction.run(..) _ "); //$NON-NLS-1$
      CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.ERROR, exception_p);
    }
  }
}