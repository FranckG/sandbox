/** 
 * <p>THALES Services - Engineering & Process Management</p>
 * <p>THALES Part Number 16 262 601</p>
 * <p>Copyright (c) 2002-2008 : THALES Services - Engineering & Process Management</p>
 */
package com.thalesgroup.orchestra.framework.oe.ui.actions;

import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;

import com.thalesgroup.orchestra.framework.lib.base.conf.ServerConfParam;
import com.thalesgroup.orchestra.framework.oe.OrchestraExplorerActivator;
import com.thalesgroup.orchestra.framework.oe.artefacts.internal.RootArtefactsBag;
import com.thalesgroup.orchestra.framework.oe.ui.constants.IImageConstants;

/**
 * This class handles the loading / refreshing action of the Orchestra Explorer Root Artifacts
 * @author s0018747
 */
public class GetRootArtefactsAction extends ArtefactAction {

  public GetRootArtefactsAction() {
    super(Messages.GetRootArtefactsAction_UpdateRootArtefactsLabel);
    setToolTipText(Messages.GetRootArtefactsAction_UpdateRootArtefactsTooltip);
    setImageDescriptor(OrchestraExplorerActivator.getDefault().getImageDescriptor(IImageConstants.DESC_LOAD));
  }

  /**
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    executeAsynchronously(Messages.GetRootartefactAction_TaskName, new Runnable() {
      @Override
      public void run() {
        // Reconnect just in case
        IProduct product = Platform.getProduct();
        // Do not try to reconnect if running in the Framework.
        if ((null != product) && !"com.thalesgroup.orchestra.framework.application".equals(product.getApplication())) { //$NON-NLS-1$
          try {
            ServerConfParam.getInstance().readFile();
          } catch (Exception exception_p) {
            StringBuilder loggerMessage = new StringBuilder(".run(..) _ "); //$NON-NLS-1$
            loggerMessage.append(Messages.GetRootArtefactsAction_FrameworkReconnectionError);
            OrchestraExplorerActivator.getDefault().logMessage(loggerMessage.toString(), IStatus.ERROR, exception_p);
          }
        }
        RootArtefactsBag.getInstance().updateRootArtefacts();
      }
    });
  }
}