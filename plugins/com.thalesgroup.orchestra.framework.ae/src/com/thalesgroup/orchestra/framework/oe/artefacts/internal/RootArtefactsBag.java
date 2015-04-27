package com.thalesgroup.orchestra.framework.oe.artefacts.internal;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.EList;

import com.thalesgroup.orchestra.framework.exchange.GefHandler;
import com.thalesgroup.orchestra.framework.exchange.StatusHandler;
import com.thalesgroup.orchestra.framework.exchange.status.SeverityType;
import com.thalesgroup.orchestra.framework.exchange.status.Status;
import com.thalesgroup.orchestra.framework.exchange.status.StatusDefinition;
import com.thalesgroup.orchestra.framework.gef.Element;
import com.thalesgroup.orchestra.framework.gef.GEF;
import com.thalesgroup.orchestra.framework.gef.GenericExportFormat;
import com.thalesgroup.orchestra.framework.lib.constants.PapeeteHTTPKeyRequest;
import com.thalesgroup.orchestra.framework.lib.utils.uri.BadOrchestraURIException;
import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;
import com.thalesgroup.orchestra.framework.oe.OrchestraExplorerActivator;
import com.thalesgroup.orchestra.framework.oe.artefacts.IArtefact;
import com.thalesgroup.orchestra.framework.oe.artefacts.IArtefactProperties;
import com.thalesgroup.orchestra.framework.oe.artefacts.description.ArtefactsDescriptionLoader;
import com.thalesgroup.orchestra.framework.oe.variableloader.EnvironmentVariablesLoader;
import com.thalesgroup.orchestra.framework.puci.PUCI;
import com.thalesgroup.orchestra.framework.root.ui.DisplayHelper;

/**
 * Singleton managing root artefacts only. Sub artefacts (artefacts retrieved by expand command) are managed by their parent artefact.
 * @author S0024585
 */
public class RootArtefactsBag {

  /**
   * Singleton
   */
  private static RootArtefactsBag _instance = new RootArtefactsBag();

  /**
   * True if the sub-artefacts must be removed when doing a getRootArtefacts
   */
  private boolean _clearSubArtefact = false;

  /**
   * Listeners list.
   */
  protected List<IArtefactBagListener> _listeners = new ArrayList<IArtefactBagListener>(0);

  /**
   * Artefacts considered as new (also contained in _rootArtefacts)
   */
  protected List<Artefact> _newRootArtefacts;

  /**
   * Artefacts considered as old (also contained in _rootArtefacts)
   */
  protected List<Artefact> _oldRootArtefacts;

  /**
   * Tool sorted hash map
   */
  protected Map<String, List<Artefact>> _typeSortedRootArtefacts;

  /**
   * URI sorted hash map
   */
  protected Map<String, Artefact> _URIAssociatedRootArtefacts;

  /**
   * Job used to update root artifacts asynchronously.
   */
  protected final Job _updateRootArtefactsJob;

  /**
   * Allow user interactions
   */
  protected boolean _allowUserInteractions;

  /**
   * Constructor
   */
  private RootArtefactsBag() {
    // Nothing
    _oldRootArtefacts = new ArrayList<Artefact>(0);
    _newRootArtefacts = new ArrayList<Artefact>(0);
    _typeSortedRootArtefacts = new HashMap<String, List<Artefact>>(0);
    _URIAssociatedRootArtefacts = new HashMap<String, Artefact>(0);
    _updateRootArtefactsJob = new Job(Messages.RootArtefactsBag_UpdateRootArtefacts) {
      @Override
      protected IStatus run(IProgressMonitor monitor_p) {
        // Initialize.
        EnvironmentVariablesLoader.getInstance().init();
        // Update information on connectors.
        ArtefactsDescriptionLoader.getInstance().init();
        OrchestraExplorerActivator.getDefault().getConnectorsInformationManager().update();
        // Then load root artifacts.
        IStatus loadStatus = loadRootArtefacts();
        if (_allowUserInteractions && loadStatus.getSeverity() != IStatus.OK) {
          DisplayHelper.displayErrorDialog(Messages.RootArtefactsBag_OrchestraExplorer, null, loadStatus);
        }
        return org.eclipse.core.runtime.Status.OK_STATUS;
      }
    };
  }

  /**
   * Add a listener
   * @param listener_p
   */
  public void addArtefactBagListener(IArtefactBagListener listener_p) {
    _listeners.add(listener_p);
  }

  /**
   * Add a new root artefact in the bag.
   * @param artefact_p
   */
  public void addRootArtefact(Artefact artefact_p) {
    addRootArtefact(artefact_p, true);
  }

  /**
   * Add a root artefact in the bag and notify the listeners if asked.
   * @param artefact_p
   * @param notifyListeners_p <code>true</code> if listeners must be notified.
   */
  protected void addRootArtefact(Artefact artefact_p, boolean notifyListeners_p) {
    _newRootArtefacts.add(artefact_p);
    // Add it in its type list
    String type = artefact_p.getType();
    List<Artefact> toolList = _typeSortedRootArtefacts.get(type);
    if (toolList == null) {
      toolList = new ArrayList<Artefact>(1);
      toolList.add(artefact_p);
      _typeSortedRootArtefacts.put(type, toolList);
    } else {
      if (!toolList.contains(artefact_p)) {
        toolList.add(artefact_p);
      }
    }
    // Add it for URI access
    _URIAssociatedRootArtefacts.put(artefact_p.getUri().getUri(), artefact_p);
    if (notifyListeners_p) {
      // Notify after adding root artefact
      for (IArtefactBagListener listener : _listeners) {
        listener.artefactAdded(artefact_p);
      }
    }
  }

  /**
   * Build {@link IStatus} from status model.
   * @param status_p
   * @return An IStatus from a Gef status.
   */
  public IStatus buildIStatus(com.thalesgroup.orchestra.framework.exchange.status.Status status_p) {
    IStatus result = null;
    String pluginId = OrchestraExplorerActivator.getDefault().getPluginId();
    String emptyString = "";//$NON-NLS-1$
    if (status_p.getStatus().size() > 0) {
      List<IStatus> children = new ArrayList<IStatus>();
      for (com.thalesgroup.orchestra.framework.exchange.status.Status status : status_p.getStatus()) {
        children.add(buildIStatus(status));
      }
      String message = status_p.getMessage();
      if ((null == message) || (status_p.eContainer() instanceof StatusDefinition)) {
        message = emptyString;
      }
      result = new MultiStatus(pluginId, 0, children.toArray(new IStatus[children.size()]), message, null);
    } else {
      int severity = IStatus.OK;
      switch (status_p.getSeverity()) {
        case OK:
          severity = IStatus.OK;
        break;
        case WARNING:
          severity = IStatus.WARNING;
        break;
        case ERROR:
          severity = IStatus.ERROR;
        break;
        case INFO:
          severity = IStatus.INFO;
        break;
        default:
        // Not possible
        break;
      }
      String message = status_p.getMessage();
      // To avoid NPE later
      if (null == message) {
        message = emptyString;
      }
      String[] mes = message.split("\n"); //$NON-NLS-1$
      if (mes.length == 1) {
        result = new org.eclipse.core.runtime.Status(severity, pluginId, mes[0]);
      } else {
        List<IStatus> children = new ArrayList<IStatus>();
        for (String string : mes) {
          children.add(new org.eclipse.core.runtime.Status(severity, pluginId, string));
        }
        result = new MultiStatus(pluginId, 0, children.toArray(new IStatus[children.size()]), emptyString, null);
      }
    }
    return result;
  }

  /**
   * Expands all artefacts in the list.
   * @param artefacts_p
   * @param artefactUris_p
   * @return The status of the expand.
   */
  public IStatus expandsArtefacts(Map<String, Artefact> artefacts_p, List<OrchestraURI> artefactUris_p) {
    String pluginId = OrchestraExplorerActivator.getDefault().getPluginId();
    MultiStatus result = new MultiStatus(pluginId, 0, Messages.RootArtefactsBag_ProblemsInExpand, null);
    Map<String, String> expand;
    try {
      expand = PUCI.expand(artefactUris_p);
    } catch (Exception exception_p) {
      OrchestraExplorerActivator.getDefault().logMessage(Messages.RootArtefactsBag_ErrorDuringExpand, IStatus.ERROR, exception_p);
      return new org.eclipse.core.runtime.Status(IStatus.ERROR, pluginId, Messages.RootArtefactsBag_UnexpectedErrorInCommunicationWithFramework, exception_p);
    }
    // Handle the Status
    String statusFilePath = expand.get(PapeeteHTTPKeyRequest.__RESPONSE_FILE_PATH);
    // No file to read, but an error is likely to have occurred.
    if (null == statusFilePath) {
      result = new MultiStatus(pluginId, 0, Messages.RootArtefactsBag_ErrorDuringExpand, null);
      result.add(new org.eclipse.core.runtime.Status(IStatus.ERROR, pluginId, expand.get(PapeeteHTTPKeyRequest.__MESSAGE)));
      return result;
    }
    // Load resulting status file.
    StatusHandler statusHandler = new StatusHandler();
    StatusDefinition statusModel = statusHandler.loadModel(statusFilePath);
    String exportFilePath = null;
    Status frameworkStatus = statusModel.getStatus();
    if (!frameworkStatus.getSeverity().equals(SeverityType.INFO)) {
      // The Framework returns an error itself
      return buildIStatus(frameworkStatus);
    }
    // Framework status is INFO, now check expand statuses
    EList<Status> list = frameworkStatus.getStatus();
    for (Status expandStatus : list) {
      if (expandStatus.getSeverity().equals(SeverityType.ERROR)) {
        // Error on expand...
        result.add(buildIStatus(expandStatus));
      } else {
        if (expandStatus.getSeverity().equals(SeverityType.WARNING)) {
          // Add warnings
          result.add(buildIStatus(expandStatus));
        }
        // Get the GEF file to process
        exportFilePath = expandStatus.getExportFilePath();
        IStatus resultStatus = loadSubArtefacts(artefacts_p, exportFilePath);
        if (resultStatus.getSeverity() != IStatus.OK) {
          result.add(resultStatus);
        }
      }
    }
    return result;
  }

  /**
   * @return all artefacts managed by the artefact bag
   */
  public Collection<Artefact> getAllRootArtefacts() {
    return _URIAssociatedRootArtefacts.values();
  }

  /**
   * Find the RootArtefact of the given {@link OrchestraURI} of an {@link Artefact}
   * @param uri_p The {@link OrchestraURI}
   * @return The RootArtefact, may be null if none found
   */
  public IArtefact getArtefactRootArtefact(OrchestraURI uri_p) {
    for (Artefact rootArtefact : _URIAssociatedRootArtefacts.values()) {
      // Same artefact type and the rootArtefact has children
      if ((rootArtefact.getUri().getRootType().equals(uri_p.getRootType())) && (rootArtefact.hasChildren())) {
        // Search in the children (recursive search of the children's children if neccessary
        if (searchChildrenOfRootArtefact(rootArtefact.getChildren(), uri_p)) {
          return rootArtefact;
        }
      }
    }
    return null;
  }

  /**
   * @return the number of {@link Artefact} in the bag.
   */
  public int getBagSize() {
    return _URIAssociatedRootArtefacts.size();
  }

  /**
   * Retrieve the list of environments to which the root artefacts are linked
   * @return The list of environments
   */
  public List<String> getEnvironmentsList() {
    ArrayList<String> result = new ArrayList<String>();
    // Parse all artefact to get their environments
    for (Artefact artefact : _URIAssociatedRootArtefacts.values()) {
      String environment = artefact.getPropertyValue(IArtefactProperties.ENVIRONMENT);
      // Only store the environment ones
      if (!result.contains(environment)) {
        result.add(environment);
      }
    }
    return result;
  }

  /**
   * Return any artefact (root artefact, child artefact) from its URI
   * @param uri_p The searched URI
   * @return Found artefact, null otherwise.
   */
  public IArtefact getArtefactForURI(OrchestraURI uri_p) {
    if (null != uri_p.getObjectType()) {
      // If not root URI, find matching root URI
      OrchestraURI rootUri = new OrchestraURI(uri_p.getRootType(), uri_p.getRootName());
      Artefact artefact = _URIAssociatedRootArtefacts.get(rootUri.getUri());
      if (null != artefact) {
        artefact = getSubArtefactFromURI(artefact, uri_p);
      }
      return artefact;
    }
    return _URIAssociatedRootArtefacts.get(uri_p.getUri());
  }

  /**
   * Return the {@link Artefact} associated with this {@link OrchestraURI}
   * @param uri_p
   * @return
   */
  public IArtefact getRootArtefactForURI(OrchestraURI uri_p) {
    return getRootArtefactForURI(uri_p.getUri());
  }

  /**
   * Return the {@link Artefact} associated with this URI
   * @param uri_p
   * @return
   */
  public IArtefact getRootArtefactForURI(String uri_p) {
    return _URIAssociatedRootArtefacts.get(uri_p);
  }

  /**
   * Return the list of {@link Artefact} linked to a given environment
   * @param environment_p The environment to search
   * @return The list of {@link Artefact}
   */
  public List<Artefact> getRootArtefactsByEnvironment(String environment_p) {
    // Precondition
    if (null == environment_p) {
      return Collections.emptyList();
    }
    ArrayList<Artefact> result = new ArrayList<Artefact>();
    // Parse all artefacts
    for (Artefact artefact : _URIAssociatedRootArtefacts.values()) {
      String env = artefact.getPropertyValue(IArtefactProperties.ENVIRONMENT);
      // If the artefact is linked to the given environment, return it
      if (environment_p.equals(env)) {
        result.add(artefact);
      }
    }
    return result;
  }

  /**
   * Get artefacts whose absolute path starts with <code>path</code>
   * @param path_p
   * @return List of {@link Artefact}
   */
  public List<Artefact> getRootArtefactsForFilePath(IPath rootPath_p, IPath path_p) {
    ArrayList<Artefact> result = new ArrayList<Artefact>();
    IPath artefactPath = null;
    for (Artefact artefact : _URIAssociatedRootArtefacts.values()) {
      IPath namePath = new Path(artefact.getUri().getRootName());
      String propertyValue = artefact.getPropertyValue(IArtefactProperties.ABSOLUTE_PATH);
      // Return a result only if not null, i.e. the environment artefacts are therefore not returned as their absolute path is null
      if (null != propertyValue) {
        artefactPath = new Path(propertyValue);
        if (rootPath_p.isPrefixOf(artefactPath) && ((path_p == null) || (path_p.isPrefixOf(namePath)))) {
          result.add(artefact);
        }
      }
    }
    return result;
  }

  /**
   * @param type_p
   * @param subPath_p
   * @return All artefacts of the specified type and which the root name starts with subPath
   */
  public Collection<Artefact> getRootArtefactsForPath(String type, IPath path_p) {
    Collection<Artefact> artefactList = null;
    if (type != null) {
      artefactList = _typeSortedRootArtefacts.get(type);
    } else {
      artefactList = _URIAssociatedRootArtefacts.values();
    }
    if (path_p.segmentCount() == 0) {
      // Speeding the always true case
      return artefactList;
    }
    List<Artefact> result = new ArrayList<Artefact>(0);
    for (Artefact artefact : artefactList) {
      String name = artefact.getUri().getRootName();
      IPath rootPath = new Path(name);
      if (path_p.isPrefixOf(rootPath) && (!path_p.equals(rootPath))) {
        result.add(artefact);
      }
    }
    return result;
  }

  /**
   * Return all {@link Artefact} of the specified type. <code>null</code> if no artefacts are found.
   */
  public List<Artefact> getRootArtefactsForType(String type_p) {
    return _typeSortedRootArtefacts.get(type_p);
  }

  /**
   * Retrieve artefacts in the given environment and at the given path, recursively if needed.
   * @param environment_p The environment where to seek artefacts
   * @param relativePath_p The path where to seek artefacts (can be an empty string to seek artefacts directlry under given environment but can't be
   *          <code>null</code>)
   * @return a collection of {@link Artefact}
   */
  public Collection<Artefact> getRootArtefactsFromEnvironmentAndRelativePath(String environment_p, String relativePath_p, boolean recursive_p) {
    List<Artefact> rootArtefacts = getRootArtefactsByEnvironment(environment_p);
    List<Artefact> resultList = new ArrayList<Artefact>();
    IPath relativePath = new Path(relativePath_p);
    for (Artefact rootArtefact : rootArtefacts) {
      IPath rootArtefactPath = new Path(rootArtefact.getUri().getRootName());
      IPath rootArtefactContainerPath = rootArtefactPath.removeLastSegments(1);
      if (!recursive_p && relativePath.equals(rootArtefactContainerPath) || recursive_p && relativePath.isPrefixOf(rootArtefactContainerPath)) {
        resultList.add(rootArtefact);
      }
    }
    return resultList;
  }

  /**
   * Return all types of currents artefacts
   * @return
   */
  public Set<String> getRootArtefactsTypes() {
    return _typeSortedRootArtefacts.keySet();
  }

  /**
   * @return The set of root paths of all artefacts.
   */
  public Set<String> getRootPaths() {
    HashSet<String> result = new HashSet<String>(0);
    for (Artefact element : _URIAssociatedRootArtefacts.values()) {
      result.add(element.getRootPath());
    }
    return result;
  }

  /**
   * @param artefact
   * @return <code>true</code> if the artefact is considered as new for the bag. <code>false</code> otherwise.
   */
  public boolean isNew(IArtefact artefact) {
    return (_newRootArtefacts.contains(artefact));
  }

  /**
   * @param artefact
   * @return <code>true</code> if the artefact is considered as old for the bag. <code>false</code> otherwise.
   */
  public boolean isOld(IArtefact artefact) {
    return (_oldRootArtefacts.contains(artefact));
  }

  /**
   * Build a list of Artifact Element from a gef file
   * @param requestContext_p
   * @return
   */
  public IStatus loadNewBag(String requestContext_p) {
    String pluginId = OrchestraExplorerActivator.getDefault().getPluginId();
    MultiStatus result = new MultiStatus(pluginId, 0, "", null); //$NON-NLS-1$
    List<Artefact> lstArtifactElement = new ArrayList<Artefact>(0);
    if (null == requestContext_p) {
      newArtefactBag(lstArtifactElement);
      return result;
    }
    // ***START - Load the URI from GEF***
    GefHandler handler = new GefHandler();
    GEF gef = handler.loadModel(requestContext_p);
    if (null == gef) {
      return new org.eclipse.core.runtime.Status(IStatus.ERROR, pluginId, MessageFormat.format(Messages.RootArtefactsBag_GefFileCanNotBeRead, requestContext_p));
    }
    result.add(readArtefacts(gef, lstArtifactElement));
    handler.unloadModel(gef);
    // ***END***
    if (lstArtifactElement.isEmpty()) {
      OrchestraExplorerActivator.getDefault().logMessage(Messages.RootArtefactsBag_NoArtefactsFound, IStatus.WARNING, null);
    }
    newArtefactBag(lstArtifactElement);
    return result;
  }

  /**
   * Loads root artefacts from the Framework.
   * @return The {@link IStatus} of the load.
   */
  protected IStatus loadRootArtefacts() {
    String pluginId = OrchestraExplorerActivator.getDefault().getPluginId();
    StatusHandler statusHandler = null;
    MultiStatus result = null;
    Map<String, String> rootArtifacts = null;
    try {
      rootArtifacts = PUCI.getRootArtifacts();
    } catch (Exception exception_p) {
      OrchestraExplorerActivator.getDefault().logMessage(Messages.RootArtefactsBag_ErrorDuringArtefactRetrieval, IStatus.ERROR, exception_p);
      result = new MultiStatus(pluginId, 0, Messages.RootArtefactsBag_UnexpectedErrorInCommunicationWithFramework, exception_p);
      loadNewBag(null);
      return result;
    }
    // Handle the Status
    String statusFilePath = rootArtifacts.get(PapeeteHTTPKeyRequest.__RESPONSE_FILE_PATH);
    // No file to read, but an error is likely to have occurred.
    if (null == statusFilePath) {
      result = new MultiStatus(pluginId, 0, Messages.RootArtefactsBag_ErrorDuringArtefactRetrieval, null);
      result.add(new org.eclipse.core.runtime.Status(IStatus.ERROR, pluginId, rootArtifacts.get(PapeeteHTTPKeyRequest.__MESSAGE)));
      loadNewBag(null);
      return result;
    }
    // Try and load the resulting status.
    statusHandler = new StatusHandler();
    StatusDefinition statusModel = statusHandler.loadModel(statusFilePath);
    Status frameworkStatus = (statusModel == null) ? null : statusModel.getStatus();
    SeverityType severity = (frameworkStatus == null) ? SeverityType.ERROR : frameworkStatus.getSeverity();
    // Check the framework status severity
    if (!severity.equals(SeverityType.INFO)) {
      if (statusModel == null) {
        result = new MultiStatus(pluginId, 0, Messages.RootArtefactsBag_ErrorDuringArtefactRetrieval, null);
        result.add(new org.eclipse.core.runtime.Status(IStatus.ERROR, pluginId, MessageFormat.format(Messages.RootArtefactsBag_RequestStatusFileUnreadable,
            statusFilePath)));
      } else {
        result = new MultiStatus(pluginId, 0, Messages.RootArtefactsBag_ErrorDuringArtefactRetrieval, null);
        result.add(new org.eclipse.core.runtime.Status(IStatus.ERROR, pluginId, frameworkStatus.getMessage()));
      }
      loadNewBag(null);
      return result;
    }
    // Framework is OK. So the framework status contains one sub-status: the operation status. check it.
    final Status operationStatus = frameworkStatus.getStatus().get(0);
    final IStatus status = buildIStatus(operationStatus);
    String filePath = operationStatus.getExportFilePath();
    IStatus loadStatus = loadNewBag(filePath);
    result = new MultiStatus(pluginId, 0, Messages.RootArtefactsBag_RetreiveRootArtefactsError, null);
    if (status.getSeverity() != IStatus.OK) {
      result.add(status);
    }
    if (loadStatus.getSeverity() != IStatus.OK) {
      result.add(loadStatus);
    }
    return result;
  }

  /**
   * Load artefacts contained in the Gef file
   * @param parentArtefacts_p
   * @param gefFilePath_p
   * @param urisMap_p
   */
  public IStatus loadSubArtefacts(Map<String, Artefact> parentArtefacts_p, String gefFilePath_p) {
    String pluginId = OrchestraExplorerActivator.getDefault().getPluginId();
    List<Artefact> gefArtefacts = new ArrayList<Artefact>(0);
    GefHandler handler = new GefHandler();
    GEF gef = handler.loadModel(gefFilePath_p);
    if (null == gef) {
      return new org.eclipse.core.runtime.Status(IStatus.ERROR, pluginId, MessageFormat.format(Messages.RootArtefactsBag_GefFileCanNotBeRead, gefFilePath_p));
    }
    readArtefacts(gef, gefArtefacts);
    List<Artefact> updatedArtefacts = new ArrayList<Artefact>(0);
    for (Artefact artefact : gefArtefacts) {
      String loadedURI = artefact.getUri().getUri();
      Artefact parentArtefact = parentArtefacts_p.get(loadedURI);
      if (null != parentArtefact) {
        // Get the rootArtefact of this artefact to reset its environment after the update.
        // The retrival of the rootArtefact must be done before the update as it may be a rootArtefact which will be updated and its environment belongings will
        // be lost
        // Use the parent URI as the artefact may not have been ever loaded before in the OE
        IArtefact oldRootArtefact = getRootArtefactForURI(parentArtefact.getUri().getUri());
        // If loading artefacts which are not direct children from the rootArtefact, the above returned value will be null
        if (null == oldRootArtefact) {
          // Search the rootArtefact for the parentArtefact
          oldRootArtefact = getArtefactRootArtefact(parentArtefact.getUri());
        }
        String oldEnv = oldRootArtefact.getProperties().get(IArtefactProperties.ENVIRONMENT);
        String oldEnvId = oldRootArtefact.getProperties().get(IArtefactProperties.ENVIRONMENT_ID);
        String oldAbsPath = oldRootArtefact.getProperties().get(IArtefactProperties.ABSOLUTE_PATH);
        String oldRelPath = oldRootArtefact.getProperties().get(IArtefactProperties.RELATIVE_PATH);
        parentArtefact.update(artefact);
        parentArtefact.setChildren(artefact.getChildren());
        // Reset the environment if none after the update
        if (null == parentArtefact.getProperties().get(IArtefactProperties.ENVIRONMENT)) {
          parentArtefact.getProperties().put(IArtefactProperties.ENVIRONMENT, oldEnv);
        }
        // Reset the environment Id if none after the update
        if (null == parentArtefact.getProperties().get(IArtefactProperties.ENVIRONMENT_ID)) {
          parentArtefact.getProperties().put(IArtefactProperties.ENVIRONMENT_ID, oldEnvId);
        }
        if (null == parentArtefact.getProperties().get(IArtefactProperties.ABSOLUTE_PATH)) {
          parentArtefact.getProperties().put(IArtefactProperties.ABSOLUTE_PATH, oldAbsPath);
        }
        if (null == parentArtefact.getProperties().get(IArtefactProperties.RELATIVE_PATH)) {
          parentArtefact.getProperties().put(IArtefactProperties.RELATIVE_PATH, oldRelPath);
        }

        if (!updatedArtefacts.contains(parentArtefact)) {
          updatedArtefacts.add(parentArtefact);
        }
      }
    }
    RootArtefactsBag.getInstance().modifiedArtefacts(updatedArtefacts);
    return new org.eclipse.core.runtime.Status(IStatus.OK, pluginId, null);
  }

  /**
   * Notify the listeners of modified artefacts.
   * @param updatedArtefacts_p
   */
  protected void modifiedArtefacts(List<Artefact> updatedArtefacts_p) {
    for (IArtefactBagListener listener : _listeners) {
      for (Artefact artefact : updatedArtefacts_p) {
        listener.artefactModified(artefact);
      }
    }
  }

  /**
   * Set the content of the artefact bag with all artefacts from the list.
   * @param gefArtefacts_p
   */
  protected void newArtefactBag(List<Artefact> gefArtefacts_p) {
    // Remove previously mark as old artefacts
    for (IArtefact artefact : _oldRootArtefacts) {
      // Remove from the type association map
      String artefactType = artefact.getType();
      List<Artefact> artefactTypeList = _typeSortedRootArtefacts.get(artefactType);
      artefactTypeList.remove(artefact);
      if (artefactTypeList.size() == 0) {
        _typeSortedRootArtefacts.remove(artefactType);
      }
      // Remove from the URI association map
      _URIAssociatedRootArtefacts.remove(artefact.getUri().getUri());
    }
    // Remove the children of the remaining artefacts if the flag is at True
    // The flag will be at true after a change of context or a save, therefore there is no sub-artefacts clearing for a getRootArtefacts
    if (_clearSubArtefact) {
      for (Artefact oldArtefact : _URIAssociatedRootArtefacts.values()) {
        oldArtefact.getChildren().clear();
      }
      // Reset the flag to false after the clearing
      setClearSubArtefact(false);
    }
    // Clear old artifacts structure.
    _oldRootArtefacts.clear();
    // Mark all current artifact as old
    _oldRootArtefacts.addAll(_URIAssociatedRootArtefacts.values());
    // Clean artifacts considered as new
    _newRootArtefacts.clear();
    for (Artefact gefArtefact : gefArtefacts_p) {
      String gefArtefactUri = gefArtefact.getUri().getUri();
      Artefact alreadyKnownArtefact = _URIAssociatedRootArtefacts.get(gefArtefactUri);
      if (alreadyKnownArtefact != null) {
        // The artifact is already known
        // Remove it from the old artifacts list
        _oldRootArtefacts.remove(alreadyKnownArtefact);
        // Then update the known one
        alreadyKnownArtefact.update(gefArtefact);

      } else {
        // Unknown so add it!
        addRootArtefact(gefArtefact, false);
      }
    }
    // Clean sub-structure of removed artifacts.
    for (Artefact oldArtefact : _oldRootArtefacts) {
      oldArtefact.getChildren().clear();
    }
    // Notify listeners.
    for (IArtefactBagListener listener : _listeners) {
      listener.fullBagModified();
    }
  }

  /**
   * Read all artefacts contained if the GEF element.
   * @param sourceGef_p
   * @param artefactList_p
   * @return
   */
  private IStatus readArtefacts(GEF sourceGef_p, List<Artefact> artefactList_p) {
    String pluginId = OrchestraExplorerActivator.getDefault().getPluginId();
    MultiStatus result = new MultiStatus(pluginId, 0, "", null); //$NON-NLS-1$
    EList<GenericExportFormat> generic = sourceGef_p.getGENERICEXPORTFORMAT();
    for (GenericExportFormat genericExportFormat : generic) {
      EList<Element> elements = genericExportFormat.getELEMENT();
      for (Element element : elements) {
        Artefact artefact;
        try {
          artefact = new Artefact(element, true);
          artefactList_p.add(artefact);
          recurseElements(element, artefact);
        } catch (BadOrchestraURIException exception_p) {
          String message = MessageFormat.format(Messages.RootArtefactsBag_GEFUriNotValid, element.getUri());
          OrchestraExplorerActivator.getDefault().logMessage(message, IStatus.ERROR, exception_p);
          result.add(new org.eclipse.core.runtime.Status(IStatus.WARNING, pluginId, message));
        }

      }
    }
    return result;
  }

  /**
   * Will parse all elements of an ArtifactElement.
   * @param element_p
   * @param parent_p
   */
  private IStatus recurseElements(Element element_p, Artefact parent_p) {
    String pluginId = OrchestraExplorerActivator.getDefault().getPluginId();
    MultiStatus result = new MultiStatus(pluginId, 0, MessageFormat.format("Error ''{0}''", element_p.getUri()), null); //$NON-NLS-1$
    EList<Element> elements = element_p.getELEMENT();
    for (Element element : elements) {
      Artefact artefact;
      try {
        artefact = new Artefact(element, false);
        parent_p.addChild(artefact);
        IStatus recurseResult = recurseElements(element, artefact);
        if (recurseResult.getSeverity() != IStatus.OK) {
          result.add(recurseResult);
        }
      } catch (BadOrchestraURIException exception_p) {
        String message = MessageFormat.format(Messages.RootArtefactsBag_GEFUriNotValid, element.getUri());
        OrchestraExplorerActivator.getDefault().logMessage(message, IStatus.ERROR, exception_p);
        result.add(new org.eclipse.core.runtime.Status(IStatus.WARNING, pluginId, message));
      }
    }
    return result;
  }

  /**
   * Remove a listener
   * @param listener_p
   */
  public void removeArtefactBagListener(IArtefactBagListener listener_p) {
    _listeners.remove(listener_p);
  }

  /**
   * Search if the given {@link OrchestraURI} is a one of the children given in the list or one the children's children
   * @param childrenList_p The list of {@link Artefact} children
   * @param uri_p The {@link OrchestraURI} to search
   * @return True if the {@link OrchestraURI} is a match to any children or any of the children's children, else false
   */
  protected boolean searchChildrenOfRootArtefact(List<Artefact> childrenList_p, OrchestraURI uri_p) {
    return null != getChildOfRootArtefact(childrenList_p, uri_p);
  }

  /**
   * Get artefact matching the given {@link OrchestraURI} within children artefacts
   * @param childrenList_p The list of {@link Artefact} children
   * @param uri_p The {@link OrchestraURI} to search
   * @return Found artefact, null otherwise.
   */
  protected Artefact getChildOfRootArtefact(List<Artefact> childrenList_p, OrchestraURI uri_p) {
    // Parse all children
    for (Artefact artefact : childrenList_p) {
      // If type and id identical to the searched artefact, return true, else parse the children if any
      if (artefact.getUri().getObjectType().equals(uri_p.getObjectType()) && artefact.getUri().getObjectId().equals(uri_p.getObjectId())) {
        return artefact;
      } else if (artefact.hasChildren()) {
        // Search the children's children
        Artefact child = getChildOfRootArtefact(artefact.getChildren(), uri_p);
        // Children is the searched one, return it
        if (null != child) {
          return child;
        }
      }
    }
    return null;
  }

  /**
   * @param artefact_p root artefact
   * @param uri_p subartefact URI
   * @return Artefact
   */
  protected Artefact getSubArtefactFromURI(Artefact artefact_p, OrchestraURI uri_p) {
    if (artefact_p.hasChildren()) {
      return getChildOfRootArtefact(artefact_p.getChildren(), uri_p);
    }
    return null;
  }

  /**
   * @param clearSubArtefact_p the clearSubArtefact to set
   */
  public void setClearSubArtefact(boolean clearSubArtefact_p) {
    _clearSubArtefact = clearSubArtefact_p;
  }

  /**
   * Reload, in a job, the root artefacts (if the Job isn't already scheduled).
   */
  public void updateRootArtefacts(boolean allowUserInteractions_p) {
    if (Job.NONE == _updateRootArtefactsJob.getState()) {
      _allowUserInteractions = allowUserInteractions_p;
      // Job is not already RUNNING, WAITING or SLEEPING -> schedule it.
      _updateRootArtefactsJob.schedule();
    }
  }

  /**
   * Reload root artefacts with user interaction allowed
   */
  public void updateRootArtefacts() {
    updateRootArtefacts(true);
  }

  /**
   * Get the singleton
   * @return
   */
  public static RootArtefactsBag getInstance() {
    return _instance;
  }

  /**
   * Add subscriber to update job events.
   * @param listener_p
   */
  public void addUpdateJobListener(IJobChangeListener listener_p) {
    _updateRootArtefactsJob.addJobChangeListener(listener_p);
  }

  /**
   * Remove subscriber from update job events
   * @param listener_p
   */
  public void removeUpdateJobListener(IJobChangeListener listener_p) {
    _updateRootArtefactsJob.removeJobChangeListener(listener_p);
  }
}
