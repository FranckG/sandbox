/** 
 * <p>THALES Services - Engineering & Process Management</p>
 * <p>THALES Part Number 16 262 601</p>
 * <p>Copyright (c) 2002-2008 : THALES Services - Engineering & Process Management</p>
 */
package com.thalesgroup.orchestra.framework.oe.ui.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;

import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;
import com.thalesgroup.orchestra.framework.oe.OrchestraExplorerActivator;
import com.thalesgroup.orchestra.framework.oe.artefacts.description.IArtefactTypeDescription;
import com.thalesgroup.orchestra.framework.oe.artefacts.internal.Artefact;
import com.thalesgroup.orchestra.framework.oe.artefacts.internal.RootArtefactsBag;
import com.thalesgroup.orchestra.framework.oe.ui.constants.IImageConstants;
import com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.ArtefactNode;
import com.thalesgroup.orchestra.framework.root.ui.DisplayHelper;

/**
 * This class handles the loading / refreshing action of the Orchestra Explorer Sub-artifacts
 * @author s0018747
 */
public class ExpandArtefactAction extends ArtefactAction {

  public ExpandArtefactAction() {
    super(Messages.ExpandArtefactAction_GetSubArtefactListLabel);
    setToolTipText(Messages.ExpandArtefactAction_GetSubArtefactListTooltip);
    setImageDescriptor(OrchestraExplorerActivator.getDefault().getImageDescriptor(IImageConstants.DESC_GET_SUB_ART));
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
    return Messages.ExpandArtefactAction_TaskName + super.getTaskName();
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
        return artefactTypeDescription.isExpandable();
      }
    }
    return false;
  }

  /**
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    final List<ArtefactNode> currentTreeSelection = getCurrentTreeSelection(ArtefactNode.class);
    // Something should be selected
    executeAsynchronously(null, new Runnable() {
      @Override
      public void run() {
        if (!currentTreeSelection.isEmpty()) {
          // Sort the tree
          List<OrchestraURI> uris = new ArrayList<OrchestraURI>(0);
          Map<String, Artefact> artefacts = new HashMap<String, Artefact>(0);
          for (ArtefactNode artefactNode : currentTreeSelection) {
            Artefact artefact = artefactNode.getValue();
            if (artefact.getGenericArtifact().isExpandable()) {
              uris.add(artefact.getUri());
              artefacts.put(artefact.getUri().getUri(), artefact);
            }
          }
          IStatus status = RootArtefactsBag.getInstance().expandsArtefacts(artefacts, uris);
          if (status.getSeverity() != IStatus.OK) {
            DisplayHelper.displayErrorDialog(Messages.ExpandArtefactAction_OrchestraExplorer, null, status);
          }
        }
      }
    });
  }
}