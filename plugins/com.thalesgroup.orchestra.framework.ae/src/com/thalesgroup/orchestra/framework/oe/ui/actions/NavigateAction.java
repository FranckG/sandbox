/** 
 * <p>THALES Services - Engineering & Process Management</p>
 * <p>THALES Part Number 16 262 601</p>
 * <p>Copyright (c) 2002-2008 : THALES Services - Engineering & Process Management</p>
 */
package com.thalesgroup.orchestra.framework.oe.ui.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;

import com.thalesgroup.orchestra.framework.exchange.StatusHandler;
import com.thalesgroup.orchestra.framework.exchange.status.SeverityType;
import com.thalesgroup.orchestra.framework.exchange.status.Status;
import com.thalesgroup.orchestra.framework.exchange.status.StatusDefinition;
import com.thalesgroup.orchestra.framework.exchange.status.StatusFactory;
import com.thalesgroup.orchestra.framework.lib.constants.PapeeteHTTPKeyRequest;
import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;
import com.thalesgroup.orchestra.framework.oe.OrchestraExplorerActivator;
import com.thalesgroup.orchestra.framework.oe.artefacts.description.IArtefactTypeDescription;
import com.thalesgroup.orchestra.framework.oe.artefacts.internal.Artefact;
import com.thalesgroup.orchestra.framework.oe.artefacts.internal.RootArtefactsBag;
import com.thalesgroup.orchestra.framework.oe.ui.constants.IImageConstants;
import com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.ArtefactNode;
import com.thalesgroup.orchestra.framework.puci.PUCI;
import com.thalesgroup.orchestra.framework.root.ui.DisplayHelper;

/**
 * Navigate to an Artifact If multiple objects are selected navigate to all of them. If nothing is selected do nothing.
 * @author S0018747
 */

public class NavigateAction extends ArtefactAction {

  public NavigateAction() {
    super(Messages.NavigateAction_NavigateLabel);
    setToolTipText(Messages.NavigateAction_NavigateTooltip);
    setImageDescriptor(OrchestraExplorerActivator.getDefault().getImageDescriptor(IImageConstants.DESC_NAVIGATE));
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.ui.actions.ArtefactAction#calculateEnabledState()
   */
  @Override
  protected boolean calculateEnabledState() {
    return applyEnablementConditionToSelection();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.ui.actions.ArtefactAction#getTaskName()
   */
  @Override
  protected String getTaskName() {
    return Messages.NavigateAction_TaskName + super.getTaskName();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.ui.actions.ArtefactAction#isEnablingCondition(com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.ArtefactNode)
   */
  @Override
  protected boolean isEnablingCondition(ArtefactNode artefactNode_p) {
    // Get underlying model element.
    Artefact artefact = artefactNode_p.getValue();
    if (null != artefact) {
      // Precondition.
      if (RootArtefactsBag.getInstance().isOld(artefact)) {
        return false;
      }
      // Get artifact description.
      IArtefactTypeDescription artefactTypeDescription = artefact.getGenericArtifact();
      if (null != artefactTypeDescription) {
        return artefactTypeDescription.isNavigable();
      }
    }
    return false;
  }

  /**
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    List<ArtefactNode> currentTreeSelection = getCurrentTreeSelection(ArtefactNode.class);
    if (!currentTreeSelection.isEmpty()) {
      List<OrchestraURI> urisToNavigate = new ArrayList<OrchestraURI>();
      for (ArtefactNode artefactNode : currentTreeSelection) {
        if (artefactNode.getValue().getGenericArtifact().isNavigable()) {
          urisToNavigate.add(artefactNode.getValue().getUri());
        }
      }
      final List<OrchestraURI> listToProcess = urisToNavigate;
      executeAsynchronously(null, new Runnable() {
        @Override
        public void run() {
          Map<String, String> papeeteResponse = null;
          try {
            papeeteResponse = PUCI.navigate(listToProcess);
          } catch (Exception exception_p) {
            OrchestraExplorerActivator.getDefault().logMessage(Messages.NavigateAction_NavigationError, IStatus.ERROR, exception_p);
          }
          if (null != papeeteResponse) {
            String statusModelPath = papeeteResponse.get(PapeeteHTTPKeyRequest.__RESPONSE_FILE_PATH);
            // No file to read, but an error is likely to have occurred.
            if (null == statusModelPath) {
              MultiStatus result = new MultiStatus(OrchestraExplorerActivator.getDefault().getPluginId(), 0, Messages.NavigateAction_NavigationError, null);
              result.add(new org.eclipse.core.runtime.Status(IStatus.ERROR, OrchestraExplorerActivator.getDefault().getPluginId(), papeeteResponse
                  .get(PapeeteHTTPKeyRequest.__MESSAGE)));
              DisplayHelper.displayErrorDialog(Messages.GetRootArtefactAction_OrchestraExplorer, null, result);
              // Stop here.
              return;
            }
            StatusHandler handler = new StatusHandler();
            try {
              StatusDefinition statusDefinition = handler.loadModel(statusModelPath);
              Status frameworkStatus = statusDefinition.getStatus();
              Status reportingStatus = frameworkStatus;
              // No error to report at Framework level directly.
              if (SeverityType.INFO.equals(frameworkStatus.getSeverity())) {
                // Create a fake head status, acting as the reporting one.
                reportingStatus = StatusFactory.eINSTANCE.createStatus();
                reportingStatus.setMessage(Messages.NavigateAction_NavigationError);
                reportingStatus.getStatus().addAll(frameworkStatus.getStatus());
              }
              IStatus status = RootArtefactsBag.getInstance().buildIStatus(reportingStatus);
              if (!status.isOK()) {
                DisplayHelper.displayErrorDialog(Messages.GetRootArtefactAction_OrchestraExplorer, null, status);
              }
            } finally {
              handler.clean();
            }
          }
        }
      });
    }
  }
}