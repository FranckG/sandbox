/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.oe.ui.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import com.thalesgroup.orchestra.framework.ae.creation.ui.environment.EnvironmentInformation;
import com.thalesgroup.orchestra.framework.exchange.StatusHandler;
import com.thalesgroup.orchestra.framework.exchange.status.SeverityType;
import com.thalesgroup.orchestra.framework.exchange.status.StatusDefinition;
import com.thalesgroup.orchestra.framework.exchange.status.StatusFactory;
import com.thalesgroup.orchestra.framework.lib.constants.ICommandConstants;
import com.thalesgroup.orchestra.framework.lib.constants.PapeeteHTTPKeyRequest;
import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;
import com.thalesgroup.orchestra.framework.oe.OrchestraExplorerActivator;
import com.thalesgroup.orchestra.framework.oe.artefacts.description.IArtefactTypeDescription;
import com.thalesgroup.orchestra.framework.oe.artefacts.internal.Artefact;
import com.thalesgroup.orchestra.framework.oe.artefacts.internal.RootArtefactsBag;
import com.thalesgroup.orchestra.framework.oe.ui.OrchestraExplorerState;
import com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.AbstractNode;
import com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.ArtefactNode;
import com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.StringNode;
import com.thalesgroup.orchestra.framework.puci.PUCI;
import com.thalesgroup.orchestra.framework.root.ui.DisplayHelper;

/**
 * @author S0032874
 */
public class SetCredentialAction extends BaseSelectionListenerAction {
  /**
   * Latest selection on selection provider.
   */
  protected IStructuredSelection _latestSelection;

  protected OrchestraExplorerState _orchestraExplorerState;

  public SetCredentialAction(OrchestraExplorerState orchestraExplorerState_p) {
    super(Messages.ReInitializeCredentialActionLabel);
    _orchestraExplorerState = orchestraExplorerState_p;
  }

  /**
   * @see org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org.eclipse.jface.viewers.IStructuredSelection)
   */
  @Override
  protected boolean updateSelection(IStructuredSelection selection_p) {
    _latestSelection = selection_p;
    return calculateEnabledState();
  }

  /**
   * Check whether an artefact supports credentials
   * @param artefactNode_p
   * @return
   */
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
        // check if the property is present and true in the artefact connector file
        return artefactTypeDescription.isSetCredential();
      }
    }
    return false;
  }

  /**
   * Get environment id of the first artefact within a string node
   * @param node_p The {@link StringNode} where to start the search
   * @return The list of {@link Artefact} linked to this {@link StringNode}
   */
  protected String getStringNodeEnvironmentId(StringNode node_p) {
    // Get root path of given node (environment description).
    AbstractNode<?> rootNode = node_p.getRootNode();
    if (!(rootNode instanceof StringNode)) {
      return null;
    }
    String environmentName = ((StringNode) rootNode).getValue();
    // Get relative path of given node (relative to the root node).
    IPath givenNodeRelativePath = node_p.getNodePath(false);
    // Get artefacts belonging to the selected node.
    Collection<Artefact> artefacts =
        RootArtefactsBag.getInstance().getRootArtefactsFromEnvironmentAndRelativePath(environmentName, givenNodeRelativePath.toString(), true);
    String environmentId = artefacts.iterator().next().getEnvironmentId();

    return environmentId;
  }

  /**
   * Apply enablement condition to all currently selected elemens.
   * @return <code>true</code> if enablement condition is met <code>false</code> if there is no selection or the element is not fulfilling enablement condition.
   */
  protected boolean calculateEnabledState() {
    boolean result = false;

    if (null != _latestSelection && 1 == _latestSelection.size()) {
      Object node = _latestSelection.toList().get(0);
      if (node instanceof ArtefactNode) {
        result = isEnablingCondition((ArtefactNode) node);
      } else if (_orchestraExplorerState.getGroupType() == OrchestraExplorerState.GroupType.ENVIRONMENT && node instanceof StringNode) {
        String envId = getStringNodeEnvironmentId((StringNode) node);
        EnvironmentInformation env = OrchestraExplorerActivator.getDefault().getEnvironmentById(envId);
        if (null != env && env.areCredentialsSupported()) {
          result = true;
        }
      }
    }

    return result;
  }

  /**
   * Get asynchronous task name, if any.
   * @return Default implementation returns an empty {@link String} or the name of the unique selected element (if any).
   */
  protected String getTaskName() {
    if (null != _latestSelection && 1 == _latestSelection.size()) {
      Object node = _latestSelection.toList().get(0);
      String name;
      if (node instanceof ArtefactNode) {
        name = ((ArtefactNode) node).getValue().getName();
      } else {
        name = ((StringNode) node).getLabel();
      }
      return " (" + name + ")"; //$NON-NLS-1$ //$NON-NLS-2$
    }
    return ""; //$NON-NLS-1$
  }

  /**
   * Execute specified task asynchronously within current environment.
   * @param taskName_p
   * @param task_p
   */
  protected void executeAsynchronously(String taskName_p, final Runnable task_p) {
    // Precondition.
    if (null == task_p) {
      return;
    }
    Job job = new Job((null == taskName_p) ? getTaskName() : taskName_p) {
      @Override
      protected IStatus run(IProgressMonitor monitor_p) {
        task_p.run();
        return Status.OK_STATUS;
      }
    };
    job.schedule();
  }

  /**
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    if (null != _latestSelection && 1 == _latestSelection.size()) {
      Object node = _latestSelection.toList().get(0);
      OrchestraURI uri;
      if (node instanceof ArtefactNode) {
        // Artefact command
        Artefact artefact = ((ArtefactNode) node).getValue();
        uri = artefact.getUri();
      } else {
        // Environment command
        String envId = getStringNodeEnvironmentId((StringNode) node);
        uri = new OrchestraURI("orchestra://FrameworkCommands/FC/Env/" + envId);
      }
      final List<OrchestraURI> uriList = new ArrayList<OrchestraURI>(1);
      uriList.add(uri);
      executeAsynchronously(null, new Runnable() {
        @Override
        public void run() {
          Map<String, String> papeeteResponse = null;
          try {
            papeeteResponse = PUCI.executeSpecificCommand(ICommandConstants.SET_CREDENTIALS, uriList);
          } catch (Exception exception_p) {
            OrchestraExplorerActivator.getDefault().logMessage(Messages.ReInitializeCredentialActionError, IStatus.ERROR, exception_p);
          }
          if (null != papeeteResponse) {
            String statusModelPath = papeeteResponse.get(PapeeteHTTPKeyRequest.__RESPONSE_FILE_PATH);
            // No file to read, but an error is likely to have occurred.
            if (null == statusModelPath) {
              MultiStatus result = new MultiStatus(OrchestraExplorerActivator.getDefault().getPluginId(), 0, Messages.ReInitializeCredentialActionError, null);
              result.add(new org.eclipse.core.runtime.Status(IStatus.ERROR, OrchestraExplorerActivator.getDefault().getPluginId(), papeeteResponse
                  .get(PapeeteHTTPKeyRequest.__MESSAGE)));
              DisplayHelper.displayErrorDialog(Messages.GetRootArtefactAction_OrchestraExplorer, null, result);
              // Stop here.
              return;
            }
            StatusHandler handler = new StatusHandler();
            try {
              StatusDefinition statusDefinition = handler.loadModel(statusModelPath);
              com.thalesgroup.orchestra.framework.exchange.status.Status frameworkStatus = statusDefinition.getStatus();
              com.thalesgroup.orchestra.framework.exchange.status.Status reportingStatus = frameworkStatus;
              // No error to report at Framework level directly.
              if (SeverityType.INFO.equals(frameworkStatus.getSeverity())) {
                // Create a fake head status, acting as the reporting one.
                reportingStatus = StatusFactory.eINSTANCE.createStatus();
                reportingStatus.setMessage(Messages.ReInitializeCredentialActionError);
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
