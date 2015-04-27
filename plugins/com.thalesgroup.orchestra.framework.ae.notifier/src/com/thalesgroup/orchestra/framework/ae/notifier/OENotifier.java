/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ae.notifier;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.PlatformUI;

import com.thalesgroup.orchestra.framework.lib.constants.ISegmentConstants;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.data.ICurrentContextChangeListener;
import com.thalesgroup.orchestra.framework.oe.OrchestraExplorerActivator;
import com.thalesgroup.orchestra.framework.oe.notifier.INotifier;
import com.thalesgroup.orchestra.framework.oe.ui.views.OrchestraExplorerView;
import com.thalesgroup.orchestra.framework.remote.services.ConnectionHandler;

import framework.orchestra.thalesgroup.com.VariableManagerAPI.VariableManagerAdapter;

/**
 * @author s0018747 Notify the Orchestra Explorer that a new context has been applied => Need to refresh the artifacts list.
 */
public class OENotifier implements ICurrentContextChangeListener, INotifier {

  private ConnectionHandler _connectionHandler;

  private OEContextChangeListener _oEContextChangeListener;

  /**
   * @see com.thalesgroup.orchestra.framework.model.handler.data.ICurrentContextChangeListener#contextChanged(com.thalesgroup.orchestra.framework.model.contexts.Context,
   *      org.eclipse.core.runtime.IStatus, org.eclipse.core.runtime.IProgressMonitor, boolean)
   */
  public IStatus contextChanged(Context currentContext_p, IStatus errorStatus_p, IProgressMonitor progressMonitor_p, boolean allowUserInteractions_p) {
    OrchestraExplorerView currentView = OrchestraExplorerActivator.getDefault().getCurrentView();
    if (!((null == currentView) || currentView.isDisposed() || PlatformUI.getWorkbench().isClosing())) {
      // Progress reporting.
      if (null != progressMonitor_p) {
        progressMonitor_p.setTaskName(Messages.OENotifier_SwitchContextMessage);
      }
      // Update environment list
      OrchestraExplorerActivator.getDefault().updateEnvironments();

      try {
        if (ISegmentConstants.GOLD_SEGMENT.equals(VariableManagerAdapter.getInstance().getSegmentName())) {
          OrchestraExplorerActivator.getDefault().reloadRootArtefacts(allowUserInteractions_p);
        }
      } catch (Exception e) {
        // TODO Auto-generated catch block
      }
    }
    return Status.OK_STATUS;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.handler.data.ICurrentContextChangeListener#preContextChange(com.thalesgroup.orchestra.framework.model.contexts.Context,
   *      org.eclipse.core.runtime.IProgressMonitor, boolean)
   */
  public void preContextChange(Context futureContext_p, IProgressMonitor progressMonitor_p, boolean allowUserInteractions_p) {
    // Nothing to do.
  }

  /**
   * @see com.thalesgroup.orchestra.papeete.artifact_explorer.ui.utils.INotifier#notifyChange()
   */
  @Override
  public void start() {
    _oEContextChangeListener = new OEContextChangeListener();
    _connectionHandler = new ConnectionHandler();
    _connectionHandler.register(_oEContextChangeListener);
    // Register as context listener.
    ModelHandlerActivator.getDefault().getDataHandler().addCurrentContextChangeListener(this);
  }

  /**
   * @see com.thalesgroup.orchestra.papeete.artifact_explorer.ui.utils.INotifier#stop()
   */
  @Override
  public void stop() {
    // Remove listener
    _connectionHandler.unregister(_oEContextChangeListener);
    ModelHandlerActivator.getDefault().getDataHandler().removeCurrentContextChangeListener(this);
  }
}