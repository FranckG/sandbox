package com.thalesgroup.orchestra.framework.oe.ui.wizard.creation;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.swt.widgets.Composite;

import com.thalesgroup.orchestra.framework.ae.creation.ui.connector.IConnectorArtefactCreationHandler;
import com.thalesgroup.orchestra.framework.ae.creation.ui.environment.IEnvironmentArtefactCreationHandler;
import com.thalesgroup.orchestra.framework.exchange.StatusHandler;
import com.thalesgroup.orchestra.framework.exchange.status.SeverityType;
import com.thalesgroup.orchestra.framework.exchange.status.Status;
import com.thalesgroup.orchestra.framework.exchange.status.StatusDefinition;
import com.thalesgroup.orchestra.framework.lib.constants.PapeeteHTTPKeyRequest;
import com.thalesgroup.orchestra.framework.lib.utils.uri.BadOrchestraURIException;
import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;
import com.thalesgroup.orchestra.framework.oe.OrchestraExplorerActivator;
import com.thalesgroup.orchestra.framework.oe.artefacts.IArtefactProperties;
import com.thalesgroup.orchestra.framework.oe.artefacts.internal.Artefact;
import com.thalesgroup.orchestra.framework.oe.artefacts.internal.RootArtefactsBag;
import com.thalesgroup.orchestra.framework.puci.PUCI;
import com.thalesgroup.orchestra.framework.root.ui.DisplayHelper;
import com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizard;

public class ArtefactCreationWizard extends AbstractFormsWizard {
  private static final String PHYSICAL_PATH_PARAMETER = "physicalPath";

  private boolean _needsPreviousAndNextButtons = true;
  protected CreateArtefactWizardPage _page;

  private String _currentContext;

  /**
   * @return the currentContext
   */
  public String getCurrentContext() {
    return _currentContext;
  }

  private IEnvironmentArtefactCreationHandler _environmentHandler;

  private IConnectorArtefactCreationHandler _connectorHandler;

  Map<String, String> _uriParametersMap;

  /**
   * Root type on which the user clicked to create a new artefact
   */
  private String _defaultRootType;

  /**
   * Environment on which the user clicked to create a new artefact
   */
  private String _defaultEnvironment;

  /**
   * Constructor
   * @param defaultType_p Type of artefact to create (can be <code>null</code>)
   * @param rootPath_p Root artefact path where to create the artefact (can be <code>null</code>)
   * @param namePrefix_p Sub path for artefact name (can be <code>null</code>)
   */
  public ArtefactCreationWizard(String defaultType_p, String defaultEnvironment_p, String currentContext_p) {
    super();
    super.setWindowTitle(Messages.ArtifactCreationWizard_Title);
    _currentContext = currentContext_p;
    _defaultRootType = defaultType_p;
    _defaultEnvironment = defaultEnvironment_p;
    _uriParametersMap = new HashMap<String, String>();
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#addPages()
   */
  @Override
  public void addPages() {
    _page = new CreateArtefactWizardPage(this);
    addPage(_page);
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#canFinish()
   */
  @Override
  public boolean canFinish() {
    return _page.isPageComplete() && (null == _environmentHandler || _environmentHandler.arePagesComplete())
           && (null == _connectorHandler || _connectorHandler.arePagesComplete());
  }

  /**
   * @see com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizard#createPageControls(org.eclipse.swt.widgets.Composite)
   */
  @Override
  public void createPageControls(Composite pageContainer_p) {
    super.createPageControls(pageContainer_p);
    getShell().setSize(600, 500);
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#needsPreviousAndNextButtons()
   */
  @Override
  public boolean needsPreviousAndNextButtons() {
    return _needsPreviousAndNextButtons;
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#performFinish()
   */
  @Override
  public boolean performFinish() {
    /**
     * Execute creation asynchronously.
     */
    final Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        String pluginId = OrchestraExplorerActivator.getDefault().getPluginId();
        // Run environment pre create action first
        IStatus preCreateStatus = environmentPreCreate();
        if (!preCreateStatus.isOK()) {
          MultiStatus errorStatus = new MultiStatus(pluginId, 0, Messages.ArtifactCreationWizard_AnErrorHasOccured, null);
          errorStatus.add(new org.eclipse.core.runtime.Status(IStatus.ERROR, pluginId, preCreateStatus.getMessage()));
          DisplayHelper.displayErrorDialog(Messages.ArtefactCreationWizard_OrchestraExplorer, null, errorStatus);
          return;
        }

        // First of all, make sure mandatory parameters are valid one.
        String artefactRootType = _page.getArtefactRootType();
        String environmentName = _page.getEnvironmentName();
        String environmentId = _page.getEnvironmentId();

        String artefactRootName = getArtefactRootName();

        // Then execute request creation service.
        final OrchestraURI orcURI = new OrchestraURI(artefactRootType, artefactRootName);
        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        Set<String> keys = _uriParametersMap.keySet();
        for (String string : keys) {
          if (null != string)
            treeMap.put(string, _uriParametersMap.get(string));
        }
        orcURI.setParameters(treeMap);
        Map<String, String> result = null;
        try {
          result = PUCI.create(Collections.singletonList(orcURI));
        } catch (Exception exception_p) {
          MultiStatus errorStatus = new MultiStatus(pluginId, 0, Messages.ArtifactCreationWizard_AnErrorHasOccured, null);
          errorStatus.add(new org.eclipse.core.runtime.Status(IStatus.ERROR, pluginId, null, exception_p));
          DisplayHelper.displayErrorDialog(Messages.ArtefactCreationWizard_OrchestraExplorer, null, errorStatus);
          return;
        }
        String status = result.get(PapeeteHTTPKeyRequest.__RESPONSE_FILE_PATH);
        StatusHandler statusHandler = new StatusHandler();
        StatusDefinition statusModel = statusHandler.loadModel(status);
        // Get a global OK for the artefact creation
        boolean ok = false;
        Status rootStatus = statusModel.getStatus();
        if (null != rootStatus) {
          List<Status> statuses = rootStatus.getStatus();
          if (!statuses.isEmpty()) {
            ok = SeverityType.OK.equals(statuses.get(0).getSeverity());
          }
        }
        // If NOK for creation, analyze further the returned status
        if (!ok) {
          // Get resulting status.
          Status statusForUri = statusHandler.getStatusForUri(statusModel, orcURI.getUri());
          if ((null == statusForUri) || !SeverityType.OK.equals(statusForUri.getSeverity())) {
            IStatus errorStatus;
            // Error message by default
            String message = Messages.ArtifactCreationWizard_AnErrorHasOccured;
            if (null != statusForUri) {
              // Build error status recursively
              errorStatus = RootArtefactsBag.getInstance().buildIStatus(statusForUri);
              // Specific message for warning
              if (IStatus.WARNING == errorStatus.getSeverity()) {
                message = Messages.ArtifactCreationWizard_WarningsHaveBeenReported;
              }
            } else {
              // Fallback status
              errorStatus = new org.eclipse.core.runtime.Status(IStatus.ERROR, pluginId, message, null);
            }

            MultiStatus displayStatus = new MultiStatus(pluginId, 0, message, null);
            displayStatus.add(errorStatus);
            DisplayHelper.displayErrorDialog(Messages.ArtefactCreationWizard_OrchestraExplorer, null, displayStatus);
            return;
          }
        }

        // Run environment post create action
        IStatus postCreateStatus = environmentPostCreate();
        if (!postCreateStatus.isOK()) {
          MultiStatus errorStatus = new MultiStatus(pluginId, 0, Messages.ArtifactCreationWizard_AnErrorHasOccured, null);
          errorStatus.add(new org.eclipse.core.runtime.Status(IStatus.ERROR, pluginId, postCreateStatus.getMessage()));
          DisplayHelper.displayErrorDialog(Messages.ArtefactCreationWizard_OrchestraExplorer, null, errorStatus);
          return;
        }

        try {
          // Create the new element to be inserted in the tree
          // We have to rebuild a new Orchestra URI in case its root name has been
          // modified by the post create action
          OrchestraURI newURI = new OrchestraURI(artefactRootType, getArtefactRootName());
          Artefact newChild;
          newChild = new Artefact(newURI, true);
          newChild.setEnvironmentId(environmentId);

          // Make sure it is associated with an environment.
          newChild.getProperties().put(IArtefactProperties.ENVIRONMENT, environmentName);
          // Add artifact.
          RootArtefactsBag.getInstance().addRootArtefact(newChild);
        } catch (BadOrchestraURIException exception_p) {
          MultiStatus displayStatus = new MultiStatus(pluginId, 0, Messages.ArtifactCreationWizard_AnErrorHasOccured, null);
          displayStatus.add(new org.eclipse.core.runtime.Status(IStatus.ERROR, pluginId, null, exception_p));
          DisplayHelper.displayErrorDialog(Messages.ArtefactCreationWizard_OrchestraExplorer, null, displayStatus);
          return;
        }
      }
    });
    thread.start();
    return true;
  }

  /**
   * @param needsPreviousAndNextButtons_p
   */
  public void setNeedsPreviousAndNextButtons(boolean needsPreviousAndNextButtons_p) {
    _needsPreviousAndNextButtons = needsPreviousAndNextButtons_p;
  }

  /**
   * @param environmentHandler_p
   */
  public void setEnvironmentHandler(IEnvironmentArtefactCreationHandler environmentHandler_p) {
    _environmentHandler = environmentHandler_p;

  }

  /**
   * @param connectorHandler_p
   */
  public void setConnectorHandler(IConnectorArtefactCreationHandler connectorHandler_p) {
    _connectorHandler = connectorHandler_p;
  }

  /**
   * @return
   */
  public String getArtefactFullPath() {
    return _uriParametersMap.get(PHYSICAL_PATH_PARAMETER);
  }

  /**
   * @return
   */
  public String getArtefactRootName() {
    return _environmentHandler.getRootName();
  }

  public Map<String, String> getParameters() {
    return _uriParametersMap;
  }

  public IStatus environmentPreCreate() {
    return _environmentHandler.preCreate();
  }

  public IStatus environmentPostCreate() {
    return _environmentHandler.postCreate();
  }

  public String getDefaultRootType() {
    return _defaultRootType;
  }

  public String getDefaultEnvironment() {
    return _defaultEnvironment;
  }
}