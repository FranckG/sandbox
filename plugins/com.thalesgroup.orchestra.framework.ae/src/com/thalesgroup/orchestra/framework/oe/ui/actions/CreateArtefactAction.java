/** 
 * <p>THALES Services - Engineering & Process Management</p>
 * <p>THALES Part Number 16 262 601</p>
 * <p>Copyright (c) 2002-2008 : THALES Services - Engineering & Process Management</p>
 */
package com.thalesgroup.orchestra.framework.oe.ui.actions;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.wizard.WizardDialog;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.oe.OrchestraExplorerActivator;
import com.thalesgroup.orchestra.framework.oe.ui.OrchestraExplorerState;
import com.thalesgroup.orchestra.framework.oe.ui.constants.IImageConstants;
import com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.AbstractNode;
import com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.ArtefactNode;
import com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.StringNode;
import com.thalesgroup.orchestra.framework.oe.ui.wizard.creation.ArtefactCreationWizard;
import com.thalesgroup.orchestra.framework.root.ui.AbstractRunnableWithDisplay;
import com.thalesgroup.orchestra.framework.root.ui.DisplayHelper;

import framework.orchestra.thalesgroup.com.VariableManagerAPI.VariableManagerAdapter;

/**
 * This action opens the creation wizard
 * @author s0018747
 */
public class CreateArtefactAction extends ArtefactAction {

  protected OrchestraExplorerState _orchestraExplorerState;

  public CreateArtefactAction(OrchestraExplorerState orchestraExplorerState_p) {
    super(Messages.CreateArtefactAction_CreateArtefactLabel);
    setToolTipText(Messages.CreateArtefactAction_CreateArtefactTooltip);
    setImageDescriptor(OrchestraExplorerActivator.getDefault().getImageDescriptor(IImageConstants.DESC_CREATE));
    _orchestraExplorerState = orchestraExplorerState_p;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.ui.actions.ArtefactAction#calculateEnabledState()
   */
  @Override
  protected boolean calculateEnabledState() {
    return OrchestraExplorerActivator.getDefault().getConnectorsInformationManager().isCreationSupported();
  }

  /**
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    String pluginId = OrchestraExplorerActivator.getDefault().getPluginId();
    // Get current context from web services
    final String currentContext;
    try {
      currentContext = VariableManagerAdapter.getInstance().getCurrentContextName();
    } catch (Exception exception_p) {
      MultiStatus result = new MultiStatus(OrchestraExplorerActivator.getDefault().getPluginId(), 0, "Failed to get current context.", null);
      result.add(new Status(IStatus.ERROR, pluginId, "", null));
      DisplayHelper.displayErrorDialog(Messages.CreateArtefactAction_OrchestraExplorer, "Artefact creation is not possible.", result);
      return;
    }

    // If current context is default context, display an error message
    if ("Default Context".equals(currentContext)) {
      MultiStatus result = new MultiStatus(OrchestraExplorerActivator.getDefault().getPluginId(), 0, "Current context is default context.", null);
      result.add(new Status(IStatus.WARNING, pluginId, "", null));
      DisplayHelper.displayErrorDialog(Messages.CreateArtefactAction_OrchestraExplorer, "Artefact creation is not possible.", result);
      return;
    }

    final List<TreeNode> currentTreeSelection = getCurrentTreeSelection(TreeNode.class);

    DisplayHelper.displayRunnable(new AbstractRunnableWithDisplay() {
      /**
       * @see java.lang.Runnable#run()
       */
      @Override
      public void run() {
        String type = ICommonConstants.EMPTY_STRING;
        String environment = ICommonConstants.EMPTY_STRING;
        if (!currentTreeSelection.isEmpty()) {
          AbstractNode<?> node = (AbstractNode<?>) currentTreeSelection.get(0);
          if (node instanceof ArtefactNode) {
            ArtefactNode artefactNode = (ArtefactNode) node;
            type = artefactNode.getValue().getUri().getRootType();
            environment = ((ArtefactNode) node).getFatherEnvironment();
          } else if (node instanceof StringNode) {
            switch (_orchestraExplorerState.getGroupType()) {
              case TYPE:
                type = node.getRootNode().getNodePath(true).segment(0);
              break;
              case ENVIRONMENT:
                environment = node.getRootNode().getNodeSegment();
              break;
              default:
            }

          }
        }
        ArtefactCreationWizard wizard = new ArtefactCreationWizard(type, environment, currentContext);
        final WizardDialog dialog = new WizardDialog(getDisplay().getActiveShell(), wizard);
        dialog.open();
      }
    }, true);
  }
}