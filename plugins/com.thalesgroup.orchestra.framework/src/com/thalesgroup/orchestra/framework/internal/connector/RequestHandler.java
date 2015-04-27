/**
 * Copyright (c) THALES, 2010. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.internal.connector;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IStatus;

import com.thalesgroup.orchestra.framework.FrameworkActivator;
import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.connector.Artifact;
import com.thalesgroup.orchestra.framework.connector.CommandContext;
import com.thalesgroup.orchestra.framework.connector.CommandStatus;
import com.thalesgroup.orchestra.framework.connector.IConnector;
import com.thalesgroup.orchestra.framework.environments.ArtifactEnvironmentRegistry;
import com.thalesgroup.orchestra.framework.exchange.GefHandler;
import com.thalesgroup.orchestra.framework.exchange.StatusHandler;
import com.thalesgroup.orchestra.framework.exchange.status.Status;
import com.thalesgroup.orchestra.framework.exchange.status.StatusDefinition;
import com.thalesgroup.orchestra.framework.internal.connector.ConnectorRegistry.ConnectorDescriptor;
import com.thalesgroup.orchestra.framework.lib.base.constants.HTTPConstants;
import com.thalesgroup.orchestra.framework.lib.constants.ICommandConstants;
import com.thalesgroup.orchestra.framework.lib.constants.PapeeteHTTPKeyRequest;
import com.thalesgroup.orchestra.framework.lib.utils.uri.BadOrchestraURIException;
import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.models.connector.KeepOpen;
import com.thalesgroup.orchestra.framework.models.connector.LaunchArguments;

/**
 * Handles request received through HTTP handler.
 * @author s0024585
 */
public class RequestHandler {
  /**
   * HTTP parameters retrieved
   */
  private Map<String, String> _arguments;
  /**
   * The command type
   */
  private String _commandType;
  /**
   * GEF handler.
   */
  private GefHandler _gefHandler;
  /**
   * The response file path
   */
  private String _responseFilePath;
  /**
   * The Status definition.
   */
  private StatusDefinition _statusDefinitionRoot;
  /**
   * Status handler.
   */
  private StatusHandler _statusHandler;
  /**
   * The root element of the Status model
   */
  private Status _statusRoot;

  /**
   * Constructor
   * @param arguments_p
   */
  public RequestHandler(Map<String, String> arguments_p) {
    _statusHandler = new StatusHandler();
    _gefHandler = new GefHandler();
    _arguments = arguments_p;
  }

  /**
   * Execute the request described by the map.
   */
  public Map<String, String> execute() {

    // map response creation
    Map<String, String> httpBodyDataResponse = new HashMap<String, String>();

    try {
      _commandType = _arguments.get(PapeeteHTTPKeyRequest._COMMAND_NAME_KEY);

      _responseFilePath = StatusModelHelper.createDataFileName(_commandType, "status", _statusHandler.getFileExtension()); //$NON-NLS-1$
      httpBodyDataResponse.put(PapeeteHTTPKeyRequest.__RESPONSE_FILE_PATH, _responseFilePath);

      _statusDefinitionRoot = _statusHandler.createNewModel(_responseFilePath);
      _statusRoot = StatusModelHelper.getNewContainerStatus();
      _statusDefinitionRoot.setStatus(_statusRoot);

      // Resulting statuses.
      List<Status> allStatus = new ArrayList<Status>(0);
      // Artifacts environment registry.
      ArtifactEnvironmentRegistry artifactEnvironmentRegistry = FrameworkActivator.getDefault().getEnvironmentsRegistry().getArtifactEnvironmentRegistry();
      // Root type to command context.
      Map<String, Collection<Artifact>> rootTypeToArtifacts = new HashMap<String, Collection<Artifact>>(0);
      // Root type to errors.
      Map<String, Collection<CommandStatus>> rootTypeToErrors = new HashMap<String, Collection<CommandStatus>>(0);
      // Retrieve and group URIs
      String[] URIs = null;
      String urisAsStrings = _arguments.get(PapeeteHTTPKeyRequest._URIS);
      if ((null == urisAsStrings) || ICommonConstants.EMPTY_STRING.equals(urisAsStrings.trim())) {
        URIs = new String[0];
      } else {
        URIs = urisAsStrings.split("\\|"); //$NON-NLS-1$
      }
      // Regroup URIs by root types.
      for (String uri : URIs) {
        OrchestraURI orchestraURI = null;
        try {
          // Get URI.
          orchestraURI = new OrchestraURI(uri);
          String rootType = orchestraURI.getRootType();
          Collection<CommandStatus> errors = rootTypeToErrors.get(rootType);
          // Create list of errors, if needed.
          if (null == errors) {
            errors = new ArrayList<CommandStatus>(0);
            rootTypeToErrors.put(rootType, errors);
          }
          // Then environment ID (if any).
          String environmentId = artifactEnvironmentRegistry.getHandlingEnvironmentId(orchestraURI);
          // Get existing collection of artifacts.
          Collection<Artifact> artifacts = rootTypeToArtifacts.get(rootType);
          // Create list of Artifacts.
          if (null == artifacts) {
            artifacts = new LinkedHashSet<Artifact>(1);
            rootTypeToArtifacts.put(rootType, artifacts);
          }
          // Create artifact.
          Artifact artifact = new Artifact(orchestraURI, environmentId);
          boolean keepArtifact = addPhysicalPath(artifact, errors);
          // An error was recorded.
          if (!keepArtifact) {
            // Skip this artifact.
            continue;
          }
          // Set environment properties.
          artifact.setEnvironmentProperties(artifactEnvironmentRegistry.getEnvironmentProperties(environmentId));
          // Add new Artifact.
          artifacts.add(artifact);
        } catch (BadOrchestraURIException exception_p) {
          // Should not happen : URIs string are received through OrchestraURI, so, the URI should be well formed.
          orchestraURI = null;
          allStatus.add(StatusModelHelper.buildStatusModelObject(new CommandStatus(IStatus.ERROR, exception_p.getMessage(), orchestraURI,
              exception_p._errorCode)));
        }
      }

      // Environment commands first.
      if (ICommandConstants.GET_ROOT_ARTIFACTS.equals(_commandType) || ICommandConstants.GET_ARTIFACTS_METADATA.equals(_commandType)
          || ICommandConstants.TRANSCRIPTION.equals(_commandType)) {
        executeEnvironmentCommand(allStatus, rootTypeToArtifacts);
        StatusModelHelper.buildStatusModel(_statusHandler, _statusDefinitionRoot, allStatus);
        return httpBodyDataResponse;
      }

      // Then commands targeting connectors explicitly.
      // Cycle through root types then URIs.
      for (Entry<String, Collection<Artifact>> entry : rootTypeToArtifacts.entrySet()) {
        // Get root type.
        String rootType = entry.getKey();
        // Get associated connector descriptor.
        ConnectorDescriptor connectorDescriptor = (ConnectorDescriptor) FrameworkActivator.getDefault().getConnectorRegistry().getConnectorDescriptor(rootType);
        if (null != connectorDescriptor) {
          // Connector available, invoke command.
          // Build context.
          String subExportFile = StatusModelHelper.createDataFileName(_commandType, rootType, _gefHandler.getFileExtension());
          boolean keepOpen = isKeepOpen(connectorDescriptor);
          String launchArguments = getLaunchArguments(connectorDescriptor);
          CommandContext context = new CommandContext(_commandType, subExportFile, keepOpen, launchArguments);
          context.addArtifacts(entry.getValue());

          // Do call command.
          Status internalExecution = internalExecution(rootType, context, rootTypeToErrors.get(rootType));

          // Get credentials command specificities. (API with no status file returned, all data returned (credentials or error message) in HTML body response.)
          if (ICommandConstants.GET_CREDENTIALS.equals(_commandType)) {
            httpBodyDataResponse.remove(PapeeteHTTPKeyRequest.__RESPONSE_FILE_PATH);
            // if (internalExecution.isSetSeverity() && SeverityType.OK.equals(internalExecution.getSeverity())) {
            String credentialsCommandResult = internalExecution.getMessage();
            // get credentials key/value data and put them to HTTP response HTML body.
            for (String s1 : credentialsCommandResult.split("&")) { //$NON-NLS-1$
              String[] s2 = s1.split("=", -1); //$NON-NLS-1$
              httpBodyDataResponse.put(s2[0], OrchestraURI.decode(s2[1])); // URI encoded in returned message of "internalExecution". Must be URI decoded
                                                                           // cause will be re-encoded before sending HTTP response (and we don't want double
                                                                           // URI encoding).
            }
            // }
          }

          allStatus.add(internalExecution);
        } else {
          // No connector found.
          String message = MessageFormat.format(Messages.RequestHandler_NoConnectorDefined, rootType);
          CommandStatus errorStatus = new CommandStatus(IStatus.ERROR, message, null, 0);
          for (Artifact artifact : entry.getValue()) {
            errorStatus.addChild(new CommandStatus(IStatus.ERROR, message, artifact.getUri(), 0));
          }
          allStatus.add(StatusModelHelper.buildStatusModelObject(errorStatus));
        }
      }

      if (httpBodyDataResponse.containsKey(PapeeteHTTPKeyRequest.__RESPONSE_FILE_PATH)) {
        // Build and save the complete model
        StatusModelHelper.buildStatusModel(_statusHandler, _statusDefinitionRoot, allStatus);
      }

      return httpBodyDataResponse;

    } finally {
      _gefHandler.clean();
      _statusHandler.clean();
    }
  }

  /**
   * Execute a command dedicated to environments.<br>
   * As a result, both the GEF model file (when applicable) and the provided {@link Status} list should be filled with expected data.
   */
  protected void executeEnvironmentCommand(List<Status> allStatus_p, Map<String, Collection<Artifact>> rootTypeToArtifacts_p) {
    // Create export file path.
    String subExportFile = StatusModelHelper.createDataFileName(_commandType, "Framework", _gefHandler.getFileExtension()); //$NON-NLS-1$
    // Create calling context.
    CommandContext context = new CommandContext(_commandType, subExportFile);
    for (Collection<Artifact> artifacts : rootTypeToArtifacts_p.values()) {
      context.addArtifacts(artifacts);
    }
    CommandStatus status = null;
    // Switch depending on command type.
    if (ICommandConstants.GET_ROOT_ARTIFACTS.equals(_commandType)) {
      status = FrameworkActivator.getDefault().getEnvironmentsRegistry().getRootArtifacts(context, null);
    } else if (ICommandConstants.GET_ARTIFACTS_METADATA.equals(_commandType)) {
      // Resolve sub artifacts path, if needed, before going on.
      for (Entry<String, Collection<Artifact>> entry : rootTypeToArtifacts_p.entrySet()) {
        // First get associated connector.
        String rootType = entry.getKey();
        // Get connector descriptor.
        ConnectorDescriptor connectorDescriptor = (ConnectorDescriptor) FrameworkActivator.getDefault().getConnectorRegistry().getConnectorDescriptor(rootType);
        // No descriptor available.
        if (null == connectorDescriptor) {
          // Skip this entry.
          continue;
        }
        // Connector does not handle MetaData.
        if (!connectorDescriptor.getConnectorModel().isMetadataResolver()) {
          // Skip this entry.
          continue;
        }
        // Get all artifacts.
        Collection<Artifact> artifacts = entry.getValue();
        // Create retained artifacts structure.
        Collection<Artifact> retainedArtifacts = new ArrayList<Artifact>(0);
        // Cycle through artifacts.
        for (Artifact artifact : artifacts) {
          OrchestraURI uri = artifact.getUri();
          // Retain only sub-artifacts, as root ones are already dealt with (by previous processing).
          // What's more, the element should not already be resolved from outside.
          if ((null != uri.getObjectType()) && (null == uri.getParameters().get(ICommandConstants.URI_PARAMETER_PHYSICAL_PATH))) {
            retainedArtifacts.add(artifact);
          }
        }
        // No artifact to handle.
        if (retainedArtifacts.isEmpty()) {
          // Skip this entry.
          continue;
        }
        // Create calling context.
        boolean keepOpen = isKeepOpen(connectorDescriptor);
        String launchArguments = getLaunchArguments(connectorDescriptor);
        CommandContext pathResolverContext = new CommandContext(ICommandConstants.GET_ARTIFACTS_CONTAINER, null, keepOpen, launchArguments);
        pathResolverContext.addArtifacts(retainedArtifacts);
        try {
          // Execute specific command.
          CommandStatus result = connectorDescriptor.getConnector().executeSpecificCommand(pathResolverContext);
          // Cycle through retained artifacts.
          for (Artifact artifact : retainedArtifacts) {
            // Get status for artifact.
            CommandStatus statusForUri = result.getStatusForUri(artifact.getUri());
            if ((null != statusForUri) && statusForUri.isOK()) {
              // Get physical path for
              String physicalPath = statusForUri.getMessage();
              if (null != physicalPath) {
                // Replace root physical path with the one provided by the connector.
                artifact.setRootPhysicalPath(physicalPath);
              }
            }
          }
        } catch (Exception exception_p) {
          // Job may have been done partly, ignore it entirely though.
        }
      }
      // Invoke command.
      status = FrameworkActivator.getDefault().getEnvironmentsRegistry().getArtifactsMetadata(context, null);
    } else if (ICommandConstants.TRANSCRIPTION.equals(_commandType)) {
      status = FrameworkActivator.getDefault().getEnvironmentsRegistry().transcript(context, null);
    }
    // The command is not dealt with here.
    if (null == status) {
      return;
    }
    Status statusModel = StatusModelHelper.buildStatusModelObject(status);
    if (new File(subExportFile).exists()) {
      statusModel.setExportFilePath(subExportFile);
    }
    allStatus_p.add(statusModel);
  }

  /**
   * Add physical path, if any, to specified artifact.
   * @param artifact_p A not <code>null</code> artifact, containing an URI to deal with, and possibly the environment ID handling this URI.
   * @param errors_p A not <code>null</code> but possibly empty collection of already collected errors for current command handling.
   * @return <code>true</code> if artifact is valid and should be kept (ie add physical path was ok), <code>false</code> to force artifact skipping. In the
   *         latter case, the errors collection is appended the error cause.
   */
  protected boolean addPhysicalPath(Artifact artifact_p, Collection<CommandStatus> errors_p) {
    // Precondition.
    if ((null == artifact_p) || (null == errors_p)) {
      return false;
    }
    // URI.
    OrchestraURI uri = artifact_p.getUri();
    // Get root type.
    String rootType = uri.getRootType();
    // Try and get path from URI, if any.
    if (ICommandConstants.GET_ARTIFACTS_METADATA.equals(_commandType)) {
      String physicalPath = uri.getParameters().get(ICommandConstants.URI_PARAMETER_PHYSICAL_PATH);
      if (null != physicalPath) {
        artifact_p.setRootPhysicalPath(physicalPath);
        return true;
      }
    }
    // Get associated connector descriptor.
    ConnectorDescriptor connectorDescriptor = (ConnectorDescriptor) FrameworkActivator.getDefault().getConnectorRegistry().getConnectorDescriptor(rootType);
    // Connector available.
    if (null != connectorDescriptor) {
      // Environment ID.
      String environmentId = artifact_p.getEnvironmentId();
      // If the connector is explicitly working in the logical domain, or the data is not associated to any environment, skip transcription.
      boolean isLogicalOnly = connectorDescriptor.isLogicalOnly() || (null == environmentId);
      // There is no transcription for Create either.
      if (!isLogicalOnly && !ICommandConstants.CREATE.equals(_commandType)) {
        // Resolve root artifact physical path.
        CommandStatus status =
            FrameworkActivator.getDefault().getEnvironmentsRegistry().getArtifactEnvironmentRegistry().transcript(uri, environmentId).getStatusForUri(uri);
        if (status.isOK()) {
          artifact_p.setRootPhysicalPath(status.getMessage());
        } else if (IStatus.ERROR == status.getSeverity()) {
          // Add transcription error.
          errors_p.add(status);
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Execute the command for the specified context.
   * @param rootType_p The connector type.
   * @param context_p The context of the command.
   * @param errors_p A collection of {@link CommandStatus} reporting errors for specified context. To be completed within the execution and integrated to
   *          resulting status.
   * @return The status of the connector execution.
   */
  protected Status internalExecution(String rootType_p, CommandContext context_p, Collection<CommandStatus> errors_p) {
    IConnector myConnector = null;
    CommandStatus status = null;
    // At this point, connector descriptor is available.
    ConnectorDescriptor myConnectorDescriptor = (ConnectorDescriptor) FrameworkActivator.getDefault().getConnectorRegistry().getConnectorDescriptor(rootType_p);
    // Result top message.
    String topMessage = MessageFormat.format(Messages.RequestHandler_ConnectorServerExecution_WrapUp_Message, _commandType, rootType_p);

    // Remove non-search URI from the context if the command is actually a search one.
    List<CommandStatus> trivialResults = new ArrayList<CommandStatus>(0);
    // Do only consider the search case.
    if (ICommandConstants.SEARCH.equals(_commandType)) {
      for (Artifact artifact : context_p.getArtifacts()) {
        OrchestraURI uri = artifact.getUri();
        if (!isSearchUri(uri)) {
          // Do not deal with this artifact.
          context_p.removeArtifact(artifact);
          // Result is OK.
          CommandStatus commandStatus = new CommandStatus(IStatus.OK, ICommonConstants.EMPTY_STRING, uri, 0);
          // With a unique child being itself.
          commandStatus.addChild(new CommandStatus(IStatus.OK, ICommonConstants.EMPTY_STRING, uri, 0));
          trivialResults.add(commandStatus);
        }
      }
    }

    // Make sure there is still work to do.
    if (0 == context_p.getArtifacts().length) {
      // There is nothing to do at connector level.
      status = new CommandStatus(topMessage, null, 0);
      // Add trivial results.
      status.addChildren(trivialResults);
      return StatusModelHelper.buildStatusModelObject(status);
    }

    // Make sure command is supported then.
    if (!myConnectorDescriptor.isCommandSupported(_commandType)) {
      String message = MessageFormat.format(Messages.RequestHandler_CommandNotSupported, _commandType, rootType_p);
      status = new CommandStatus(topMessage, null, 0);
      for (Artifact artifact : context_p.getArtifacts()) {
        OrchestraURI uri = artifact.getUri();
        status.addChild(new CommandStatus(IStatus.ERROR, message, uri, 0));
      }
      // Add trivial results.
      status.addChildren(trivialResults);
      return StatusModelHelper.buildStatusModelObject(status);
    }

    // Make sure command should be proceeded for all artifacts.
    // Check that artifacts are indeed having an environment ID for Orchestra services.
    if (ICommandConstants.EXPAND.equalsIgnoreCase(_commandType) || ICommandConstants.EXPORT_DOC.equalsIgnoreCase(_commandType)
        || ICommandConstants.EXPORT_LM.equalsIgnoreCase(_commandType) || ICommandConstants.NAVIGATE.equalsIgnoreCase(_commandType)
        || ICommandConstants.SEARCH.equalsIgnoreCase(_commandType)) {
      for (Artifact artifact : context_p.getArtifacts()) {
        // No environment found !
        if (null == artifact.getEnvironmentId()) {
          // Add error.
          errors_p.add(new CommandStatus(IStatus.ERROR, Messages.RequestHandler_CouldNotFindArtefact, artifact.getUri(), 0));
          // And remove artifact from command context.
          context_p.removeArtifact(artifact);
        }
      }
    }

    // Make sure there is still work to do.
    if (0 >= context_p.getArtifacts().length) {
      // There is nothing to do at connector level, hence everything is OK so far.
      status = new CommandStatus(topMessage, null, 0);
      // Add errors.
      status.addChildren(errors_p);
      // Add trivial results.
      status.addChildren(trivialResults);
      return StatusModelHelper.buildStatusModelObject(status);
    }

    // Get connector instance.
    myConnector = myConnectorDescriptor.getConnector();
    // Do execute the command.
    try {
      if (ICommandConstants.CREATE.equalsIgnoreCase(_commandType)) {
        status = myConnector.create(context_p);
      } else if (ICommandConstants.EXPAND.equalsIgnoreCase(_commandType)) {
        status = myConnector.expand(context_p);
      } else if (ICommandConstants.EXPORT_DOC.equalsIgnoreCase(_commandType)) {
        status = myConnector.documentaryExport(context_p);
      } else if (ICommandConstants.EXPORT_LM.equalsIgnoreCase(_commandType)) {
        status = myConnector.lmExport(context_p);
      } else if (ICommandConstants.NAVIGATE.equalsIgnoreCase(_commandType)) {
        status = myConnector.navigate(context_p);
      } else if (ICommandConstants.SEARCH.equalsIgnoreCase(_commandType)) {
        status = myConnector.search(context_p);
      } else {
        status = myConnector.executeSpecificCommand(context_p);
      }
    } catch (Exception exception_p) {
      status = new CommandStatus(IStatus.ERROR, exception_p.getMessage(), null, HTTPConstants.HTTP_INTERNAL_ERROR);
      FrameworkActivator.getDefault().log(
          new org.eclipse.core.runtime.Status(IStatus.ERROR, FrameworkActivator.getDefault().getPluginId(), exception_p.getMessage(), exception_p.getCause()),
          null);
    }

    // Deal with resulting status.
    // There are others children to add, make a parent status to hold them all.
    if ((errors_p.size() > 0) || (trivialResults.size() > 0)) {
      CommandStatus modifiedStatus = new CommandStatus(topMessage, null, 0);
      modifiedStatus.addChild(status);
      status = modifiedStatus;
    }
    // Add errors anyway.
    status.addChildren(errors_p);
    // Add trivial results.
    status.addChildren(trivialResults);
    // Convert to model status.
    Status modelStatus = StatusModelHelper.buildStatusModelObject(status);
    // Add exported file path.
    File exportFilePath = new File(context_p.getExportFilePath());
    if (exportFilePath.exists()) {
      modelStatus.setExportFilePath(context_p.getExportFilePath());
    }
    return modelStatus;
  }

  /**
   * Is specified URI a search one ?
   * @param uri_p A not <code>null</code> {@link OrchestraURI}.
   * @return <code>true</code> if so, <code>false</code> otherwise.
   */
  protected boolean isSearchUri(OrchestraURI uri_p) {
    // Precondition.
    if (null == uri_p) {
      return false;
    }
    return (null != uri_p.getParameters().get(ICommandConstants.URI_PARAMETER_SEARCH_RELATIVE_PATH));
  }

  /**
   * Check whether application has to be kept open after executing connector command
   * @param connector_Descriptor_p Connector descriptor
   * @return <code>true</code> if the application has to be kept open or <code>false</code> otherwise
   */
  private boolean isKeepOpen(ConnectorDescriptor connector_Descriptor_p) {
    boolean keepOpen = false;
    // Get variable path from connector
    KeepOpen keepOpenReference = connector_Descriptor_p.getConnectorModel().getKeepOpen();
    if (keepOpenReference != null) {
      String variablePath = keepOpenReference.getName();

      // Retrieve variable value from ODM variable if available
      VariableValue variableValue = DataUtil.getValue(variablePath);
      if (variableValue != null) {
        String value = variableValue.getValue();
        // Keep application open if value is "true"
        if (value.equals("true")) { //$NON-NLS-1$
          keepOpen = true;
        }
      }
    }
    return keepOpen;
  }

  /**
   * Get arguments to pass to launchers
   * @param connector_Descriptor_p Connector descriptor
   * @return program arguments as string
   */
  private String getLaunchArguments(ConnectorDescriptor connector_Descriptor_p) {
    // Get variable path from connector
    LaunchArguments launchArgumentsReference = connector_Descriptor_p.getConnectorModel().getLaunchArguments();
    if (launchArgumentsReference != null) {
      String variablePath = launchArgumentsReference.getName();

      // Retrieve variable value from ODM variable if available
      VariableValue variableValue = DataUtil.getValue(variablePath);
      if (variableValue != null) {
        return variableValue.getValue();
      }
    }
    return null;
  }
}