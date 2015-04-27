/**
 * Copyright (c) THALES, 2010. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.connector.migration;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URLDecoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.thalesgroup.orchestra.framework.FrameworkActivator;
import com.thalesgroup.orchestra.framework.UriMigration.Migration;
import com.thalesgroup.orchestra.framework.UriMigration.MigrationDefinition;
import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.common.helper.ProjectHelper;
import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.framework.connector.AbstractConnector;
import com.thalesgroup.orchestra.framework.connector.Artifact;
import com.thalesgroup.orchestra.framework.connector.CommandContext;
import com.thalesgroup.orchestra.framework.connector.CommandStatus;
import com.thalesgroup.orchestra.framework.connector.IConnectorRegistry.IConnectorDescriptor;
import com.thalesgroup.orchestra.framework.contextsproject.ContextReference;
import com.thalesgroup.orchestra.framework.environments.ArtifactEnvironmentRegistry;
import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.command.DeleteContextServiceCommand;
import com.thalesgroup.orchestra.framework.model.handler.data.DataHandler;
import com.thalesgroup.orchestra.framework.model.handler.data.RootElement;
import com.thalesgroup.orchestra.framework.model.handler.helper.NotificationHelper;
import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.project.ProjectHandler;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;
import com.thalesgroup.orchestra.framework.variablemanager.server.model.VariableManager;

/**
 * Orchestra Framework migration connector.
 * @author t0076261
 */
public class MigrationConnector extends AbstractConnector {
  /**
   * URI V4 regular expression pattern.
   */
  protected static Matcher __uriV4Matcher = Pattern
      .compile("^papeete:///([^/]*)/([^/]*)/([^/]*)/([^/]*)/([^/]*)/([^/]*)(/([^/]*))?").matcher(ICommonConstants.EMPTY_STRING); //$NON-NLS-1$
  /**
   * Command ID for importing a context to workspace for context migration process.
   */
  private static final String COMMAND_ID_IMPORT_CONTEXT = "ImportContext"; //$NON-NLS-1$
  /**
   * Command ID for parents candidates for context migration process.
   */
  private static final String COMMAND_ID_PARENTS_CANDIDATES = "ParentsCandidates"; //$NON-NLS-1$
  /**
   * Command ID for URI Migration command.
   */
  public static final String COMMAND_ID_URI_MIGRATION = "UriMigration"; //$NON-NLS-1$
  /**
   * Command ID for context name validation for context migration process.
   */
  private static final String COMMAND_ID_VALIDATE_CONTEXTNAME = "ValidateContextName"; //$NON-NLS-1$
  /**
   * Command ID for workspace location for context migration process.
   */
  private static final String COMMAND_ID_WORKSPACE_LOCATION = "WorkspaceLocation"; //$NON-NLS-1$
  /**
   * URI V4 Prefix.
   */
  protected static final String PREFIX_V4_URI = "papeete:"; //$NON-NLS-1$
  /**
   * URL V4 Prefix.
   */
  protected static final String PREFIX_V4_URL = "orchestra:?"; //$NON-NLS-1$
  /**
   * URI parameter name : URI to migrate.
   */
  public static final String URI_PARAMETER_NAME_URI_TO_MIGRATE = "UriToMigrate"; //$NON-NLS-1$
  /**
   * URI migration structure.<br>
   * Key is the URI V4 tool name.<br>
   * Value is a couple of (URI V5 root type (to use instead), Boolean : invoke connector for migration (true to do so, false otherwise)).
   */
  private Map<String, Couple<String, Boolean>> _uriMigrationStructure;

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#create(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  public CommandStatus create(CommandContext context_p) throws Exception {
    // Unsupported command.
    return createStatusForUnsupportedCommand(context_p);
  }

  /**
   * Create URI migration internal structure.
   * @return
   */
  @SuppressWarnings("boxing")
  protected CommandStatus createUriMigrationStructure() {
    // Read URI migration models.
    try {
      // Get directory.
      IPath path = new Path(VariableManager.getValue("\\Orchestra\\ConfigurationDirectory")); //$NON-NLS-1$
      path = path.removeTrailingSeparator().append("/Framework/Misc/Migration"); //$NON-NLS-1$
      final File directory = path.toFile();
      // No such directory.
      if (!directory.exists()) {
        throw new Exception(MessageFormat.format(Messages.MigrationConnector_UriMigration_StructureFailed_NoFolderFound, path.toString()));
      }
      // Get files.
      File[] files = directory.listFiles(new FilenameFilter() {
        @Override
        public boolean accept(File dir_p, String name_p) {
          // Keep files with the correct extension.
          return directory.equals(dir_p) && "urimigration".equals(new Path(name_p).getFileExtension()); //$NON-NLS-1$
        }
      });
      // No migration file.
      if ((null == files) || (0 == files.length)) {
        throw new Exception(MessageFormat.format(Messages.MigrationConnector_UriMigration_StructureFailed_NoFileFound, path.toString()));
      }
      // Load files and populate resulting structure.
      ResourceSet resourceSet = new ResourceSetImpl();
      for (File migrationFile : files) {
        try {
          Resource resource = resourceSet.getResource(URI.createFileURI(migrationFile.getAbsolutePath()), true);
          MigrationDefinition definition = (MigrationDefinition) resource.getContents().get(0);
          for (Migration migration : definition.getMigrations()) {
            // Wait for the laziest time to initialize the resulting structure.
            // This will allow for error detection.
            if (null == _uriMigrationStructure) {
              _uriMigrationStructure = new HashMap<String, Couple<String, Boolean>>(0);
            }
            _uriMigrationStructure.put(migration.getToolName(), new Couple<String, Boolean>(migration.getRootType(), migration.isInvokeConnector()));
          }
        } catch (Exception exception_p) {
          FrameworkActivator.getDefault().log(
              new Status(IStatus.WARNING, getClass().getPackage().getName(), MessageFormat.format(
                  Messages.MigrationConnector_UriMigration_StructureFailed_ErrorWhileReadingFile, migrationFile.getAbsolutePath()), exception_p), null);
          continue;
        }
      }
      if (null == _uriMigrationStructure) {
        throw new Exception(MessageFormat.format(Messages.MigrationConnector_UriMigration_StructureFailed_NoValidFileFound, path.toString()));
      }
      return new CommandStatus(IStatus.OK, ICommonConstants.EMPTY_STRING, null, 0);
    } catch (Exception exception_p) {
      return new CommandStatus(IStatus.ERROR, MessageFormat.format(Messages.MigrationConnector_UriMigration_StructureFailed_ErrorWhileReadingFileResult,
          exception_p.getLocalizedMessage()), null, 0);
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#documentaryExport(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  public CommandStatus documentaryExport(CommandContext context_p) throws Exception {
    // Unsupported command.
    return createStatusForUnsupportedCommand(context_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.AbstractConnector#executeSpecificCommand(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  @Override
  public CommandStatus executeSpecificCommand(CommandContext context_p) throws Exception {
    String commandType = context_p.getCommandType();
    // Do only handle URI migration command so far.
    if (COMMAND_ID_URI_MIGRATION.equals(commandType)) {
      return handleUriMigration(context_p);
    } else if (COMMAND_ID_PARENTS_CANDIDATES.equals(commandType)) {
      return handleParentsCandidates(context_p);
    } else if (COMMAND_ID_WORKSPACE_LOCATION.equals(commandType)) {
      return handleWorkspaceLocation(context_p);
    } else if (COMMAND_ID_IMPORT_CONTEXT.equals(commandType)) {
      return handleImportContextToWorkspace(context_p);
    } else if (COMMAND_ID_VALIDATE_CONTEXTNAME.equals(commandType)) {
      return handleValidateContextName(context_p);
    }
    return super.executeSpecificCommand(context_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#expand(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  public CommandStatus expand(CommandContext context_p) throws Exception {
    // Unsupported command.
    return createStatusForUnsupportedCommand(context_p);
  }

  /**
   * Handle import context to workspace/reload command for context migration process.
   * @param context_p
   * @return
   */
  protected CommandStatus handleImportContextToWorkspace(CommandContext context_p) {
    // There should be only one artifact.
    OrchestraURI uri = context_p.getArtifacts()[0].getUri();
    // This parameter name is defined by the AbstractMigration class, as a part of its process.
    String contextLocation = uri.getParameters().get("ContextLocation"); //$NON-NLS-1$
    if (null == contextLocation) {
      return new CommandStatus(IStatus.ERROR, Messages.MigrationConnector_ContextMigration_ImportFailed_UnableToImportNull, uri, 0);
    }
    ProjectHandler projectHandler = ProjectActivator.getInstance().getProjectHandler();
    RootContextsProject project = projectHandler.getContextFromLocation(contextLocation, null);
    if (null == project) {
      return new CommandStatus(IStatus.ERROR, MessageFormat.format(Messages.MigrationConnector_ContextMigration_ImportFailed_NoContextAtSpecifiedLocation,
          contextLocation), uri, 0);
    }
    // Do the dance.
    DataHandler dataHandler = ModelHandlerActivator.getDefault().getDataHandler();
    Context context = null;
    boolean refreshContext = false;
    // Project already exists in workspace.
    if (project._project.isAccessible()) {
      // Refresh project.
      ProjectHelper.refreshProject(project._project, null);
      // Reload it.
      context = dataHandler.getContext(project.getContext());
      if (null != context) {
        DeleteContextServiceCommand command = new DeleteContextServiceCommand(context, false);
        if (command.canExecute()) {
          command.execute();
          refreshContext = true;
        } else {
          return new CommandStatus(IStatus.ERROR, MessageFormat.format(Messages.MigrationConnector_MigrationConnector_ImportFailed_CanNotUnloadExistingContext,
              context.getName()), uri, 0);
        }
      }
    }
    // Do import project.
    IStatus result = dataHandler.importContexts(Collections.singletonList(project), false);
    if (!result.isOK()) {
      return new CommandStatus(IStatus.ERROR, MessageFormat.format(Messages.MigrationConnector_ImportFailed_UnableToImportContextFromSpecifiedLocation,
          contextLocation), uri, 0);
    }
    // Refresh context.
    if (refreshContext) {
      context = dataHandler.getContext(project.getContext());
      // Make as if the context was just added for most notifications are not taken into account while it's loading.
      NotificationHelper.fireNonDirtyingNotification(Notification.ADD, context.eResource(), null, null, context);
    }
    return new CommandStatus(IStatus.OK, ICommonConstants.EMPTY_STRING, uri, 0);
  }

  /**
   * Handle parents candidates command for context migration process.
   * @param context_p
   * @return
   */
  protected CommandStatus handleParentsCandidates(CommandContext context_p) {
    // There should be only one artifact.
    OrchestraURI uri = context_p.getArtifacts()[0].getUri();
    // Precondition.
    if (!ProjectActivator.getInstance().isAdministrator()) {
      return new CommandStatus(IStatus.ERROR, Messages.MigrationConnector_ParentsCandidate_Error_CanNotBeInUserMode, uri, 0);
    }
    RootElement rootElement = ModelHandlerActivator.getDefault().getDataHandler().getAllContextsInWorkspace();
    Collection<CommandStatus> results = new ArrayList<CommandStatus>(0);
    for (RootContextsProject project : rootElement.getProjects()) {
      // Skip project hosted by the workspace directly.
      if (project.isInWorkspace()) {
        continue;
      }
      // First status is made of the context name.
      ContextReference context = project.getContext();
      // Precondition.
      if (null != context) {
        CommandStatus result = new CommandStatus(IStatus.OK, context.getName(), null, 0);
        // Then a child gives the absolute location.
        result.addChild(new CommandStatus(IStatus.OK, project.getLocation(), null, 0));
        results.add(result);
      }
    }
    CommandStatus result = new CommandStatus(IStatus.OK, Messages.MigrationConnector_ParentsCandidate_ResultMessage, uri, 0);
    result.addChildren(results);
    return result;
  }

  /**
   * Handle URI migration for specified parameters.<br>
   * This methods hard-code a projection from v4/v5 URIs to new v5 ones.<br>
   * Still some details can not be resolved here. In these cases, the method invokes all registered connectors, except COM ones, with the following command type
   * {@link #COMMAND_ID_URI_MIGRATION} and with a single {@link Artifact} that contains the newly (potentially incomplete) computed URI.<br>
   * The result is expected to be either an error, or a valid result, with the resulting status 'message' attribute containing the new URI to use.
   * @param uris_p The URIs to migrate to v5 standards as couples of ({@link OldUri} old URI to migrate, {@link OrchestraURI} source URI (at service call
   *          time)).
   * @return A not <code>null</code> collection of {@link CommandStatus}, with URI attribute set to source URI and message attribute set to new migrated URI.
   */
  protected Collection<CommandStatus> handleUriMigration(Collection<Couple<OldUri, OrchestraURI>> uris_p) {
    Collection<CommandStatus> result = new ArrayList<CommandStatus>(0);
    Couple<String, Boolean> projection = null;
    String type = null;
    // New URI to source one.
    Map<OrchestraURI, OrchestraURI> migratedUris = new HashMap<OrchestraURI, OrchestraURI>(uris_p.size());
    // Cycle through URIs to migrate.
    for (Couple<OldUri, OrchestraURI> couple : uris_p) {
      // Collect data.
      OldUri oldUri = couple.getKey();
      String rootType = oldUri._rootType;
      // Get projection, if any.
      if ((null == projection) && (null != _uriMigrationStructure)) {
        projection = _uriMigrationStructure.get(rootType);
      }
      // Project tool name into new root type.
      if (null != projection) {
        rootType = (null != projection.getKey()) ? projection.getKey() : rootType;
      }
      // Retain type.
      if (null == type) {
        type = rootType;
      }
      String rootLogicalName = oldUri._rootName;
      String objectType = oldUri._objectType;
      String objectId = oldUri._objectId;
      String objectNameParameter = null;
      // Try and use object name instead.
      if (null == objectId) {
        objectId = oldUri._objectName;
      } else if (null != oldUri._objectName) {
        // Special case.
        // Retain object name as a parameter as both object name and object id were valued !
        objectNameParameter = oldUri._objectName;
      }
      // Create new URI.
      OrchestraURI newUri = null;
      try {
        newUri = new OrchestraURI(rootType, rootLogicalName, objectType, objectId);
      } catch (Exception exception_p) {
        result.add(new CommandStatus(IStatus.ERROR, Messages.MigrationConnector_MigrationFailed_CanNotCreateNewUri, couple.getValue(), 0));
        // Stop here for current URI.
        continue;
      }
      // Add parameters.
      for (Entry<String, String> parameter : oldUri._parametersAsMap.entrySet()) {
        newUri.addParameter(parameter.getKey(), parameter.getValue());
      }
      if (null != objectNameParameter) {
        newUri.addParameter("UriMigrationOldObjectName", objectNameParameter); //$NON-NLS-1$
      }
      // Retain URI.
      migratedUris.put(newUri, couple.getValue());
    }
    // The new URIs are available.
    // Still, there might be issues with their contents.
    // Ask related connector to deal with them, if required.
    boolean askConnector = (null != projection) ? projection.getValue().booleanValue() : false;
    // Connector should not be invoked, stop here.
    if (!askConnector) {
      for (Entry<OrchestraURI, OrchestraURI> entry : migratedUris.entrySet()) {
        result.add(new CommandStatus(IStatus.OK, entry.getKey().getUri(), entry.getValue(), 0));
      }
      return result;
    }
    // Create context.
    // No export file path, result is expected to be pushed as a String in the 'message' attribute of the status.
    CommandContext context = new CommandContext(COMMAND_ID_URI_MIGRATION, null);
    for (Entry<OrchestraURI, OrchestraURI> entry : migratedUris.entrySet()) {
      OrchestraURI uri = entry.getKey();
      ArtifactEnvironmentRegistry artifactEnvironmentRegistry = FrameworkActivator.getDefault().getEnvironmentsRegistry().getArtifactEnvironmentRegistry();
      String handlingEnvironmentId = artifactEnvironmentRegistry.getHandlingEnvironmentId(uri);
      Artifact artifact = new Artifact(uri, handlingEnvironmentId);
      if (null != handlingEnvironmentId) {
        artifact.setEnvironmentProperties(artifactEnvironmentRegistry.getEnvironmentProperties(handlingEnvironmentId));
      }
      context.addArtifact(artifact);
    }
    // Get connector from root type.
    IConnectorDescriptor descriptor = FrameworkActivator.getDefault().getConnectorRegistry().getConnectorDescriptor(type);
    if (null != descriptor) {
      CommandStatus commandResult = descriptor.executeSpecificCommand(context);
      if (null != commandResult) {
        // Convert from new URI to source one.
        for (CommandStatus status : commandResult.getChildren()) {
          result.add(new CommandStatus(status.getSeverity(), status.getMessage(), migratedUris.get(status.getUri()), status.getCode()));
        }
      }
    } else {
      // Failed to convert URIs.
      for (Entry<OrchestraURI, OrchestraURI> entry : migratedUris.entrySet()) {
        result.add(new CommandStatus(IStatus.ERROR, MessageFormat.format(Messages.MigrationConnector_NoConnectorToMigrateUri, entry.getKey().getUri()), entry
            .getValue(), 0));
      }
    }
    return result;
  }

  /**
   * Handle URI migration.
   * @param context_p
   * @return
   */
  protected CommandStatus handleUriMigration(CommandContext context_p) {
    // Create URI migration structure.
    if (null == _uriMigrationStructure) {
      CommandStatus status = createUriMigrationStructure();
      // Precondition.
      // Could not create migration structure, stop here.
      if (!status.isOK()) {
        return status;
      }
    }
    // Resulting statuses.
    Collection<CommandStatus> errors = new ArrayList<CommandStatus>(0);
    Collection<CommandStatus> results = new ArrayList<CommandStatus>(0);
    // Cycle through artifacts, so as to sort artifact on a tool name basis.
    Map<String, Collection<Couple<OldUri, OrchestraURI>>> toolNameToOldUri = new HashMap<String, Collection<Couple<OldUri, OrchestraURI>>>(0);
    for (Artifact artifact : context_p.getArtifacts()) {
      OrchestraURI uri = artifact.getUri();
      if (null != uri) {
        Map<String, String> parameters = uri.getParameters();
        String previousUri = parameters.get(URI_PARAMETER_NAME_URI_TO_MIGRATE);
        if (null == previousUri) {
          errors.add(new CommandStatus(IStatus.ERROR, MessageFormat.format(Messages.MigrationConnector_UriMigrationIsMissingUriParameter,
              URI_PARAMETER_NAME_URI_TO_MIGRATE), uri, 0));
          continue;
        }
        try {
          // Get old URI.
          OldUri oldUri = new OldUri(previousUri);
          // Get calling collection.
          Collection<Couple<OldUri, OrchestraURI>> oldUris = toolNameToOldUri.get(oldUri._rootType);
          // Create it lazily, if needed.
          if (null == oldUris) {
            oldUris = new HashSet<Couple<OldUri, OrchestraURI>>(1);
            toolNameToOldUri.put(oldUri._rootType, oldUris);
          }
          // Add URI to deal with.
          oldUris.add(new Couple<OldUri, OrchestraURI>(oldUri, uri));
        } catch (Exception exception_p) {
          errors.add(new CommandStatus(IStatus.ERROR, exception_p.getLocalizedMessage(), uri, 0));
          continue;
        }
      }
    }
    // Handle each pile of URIs based on the target tool name.
    for (Collection<Couple<OldUri, OrchestraURI>> uris : toolNameToOldUri.values()) {
      Collection<CommandStatus> statuses = handleUriMigration(uris);
      for (CommandStatus status : statuses) {
        if (status.isOK()) {
          results.add(status);
        } else {
          errors.add(status);
        }
      }
    }
    // Create resulting status.
    int resultingSeverity = IStatus.OK;
    if (!errors.isEmpty()) {
      if (errors.size() == context_p.getArtifacts().length) {
        resultingSeverity = IStatus.ERROR;
      } else {
        resultingSeverity = IStatus.WARNING;
      }
    }
    CommandStatus result = new CommandStatus(resultingSeverity, ICommonConstants.EMPTY_STRING, null, 0);
    result.addChildren(results);
    result.addChildren(errors);
    return result;
  }

  /**
   * Handle validate context name for context migration process.
   * @param context_p
   * @return
   */
  protected CommandStatus handleValidateContextName(CommandContext context_p) {
    // There should be only one artifact.
    OrchestraURI uri = context_p.getArtifacts()[0].getUri();
    // This parameter name is defined by the AbstractMigration class, as a part of its process.
    String contextName = uri.getParameters().get("ContextName"); //$NON-NLS-1$
    // Check name for itself.
    if (null == contextName) {
      return new CommandStatus(IStatus.ERROR, Messages.MigrationConnector_ContextMigration_ValidateContextName_NullIsNotAValidName, uri, 0);
    }
    // Check that there is no project with this name.
    String projectName = ProjectHelper.generateContextProjectName(contextName);
    IProject handle = ProjectHelper.getProject(projectName);
    if (handle.exists()) {
      return new CommandStatus(IStatus.ERROR, MessageFormat.format(
          Messages.MigrationConnector_ContextMigration_ValidateContextName_NameIsAlreadyAssignedInTheWorkspace, contextName), uri, 0);
    }
    // Check that there is no folder with this name.
    IPath folderPath = ResourcesPlugin.getWorkspace().getRoot().getLocation().append("/" + projectName); //$NON-NLS-1$
    if (folderPath.toFile().exists()) {
      return new CommandStatus(IStatus.ERROR, MessageFormat.format(
          Messages.MigrationConnector_ContextMigration_ValidateContextName_FutureFolderIsAlreadyExistingInTheWorkspace, contextName), uri, 0);
    }
    // Check that there is no context with this name in the workspace.
    RootElement contextsInWorkspace = ModelHandlerActivator.getDefault().getDataHandler().getAllContextsInWorkspace();
    for (RootContextsProject project : contextsInWorkspace.getProjects()) {
      if (contextName.equals(project.getAdministratedContext().getName())) {
        return new CommandStatus(IStatus.ERROR, MessageFormat.format(
            Messages.MigrationConnector_ContextMigration_ValidateContextName_NameIsAlreadyAssignedInTheWorkspace, contextName), uri, 0);
      }
    }
    return new CommandStatus(IStatus.OK, ICommonConstants.EMPTY_STRING, uri, 0);
  }

  /**
   * Handle workspace location command for context migration process.
   * @param context_p
   * @return
   */
  protected CommandStatus handleWorkspaceLocation(CommandContext context_p) {
    // There should be only one artifact.
    OrchestraURI uri = context_p.getArtifacts()[0].getUri();
    return new CommandStatus(IStatus.OK, ResourcesPlugin.getWorkspace().getRoot().getLocation().toString(), uri, 0);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#navigate(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  public CommandStatus navigate(CommandContext context_p) throws Exception {
    // Unsupported command.
    return createStatusForUnsupportedCommand(context_p);
  }

  /**
   * Old URI decomposition.
   * @author t0076261
   */
  protected class OldUri {
    protected String _objectId;
    protected String _objectName;
    protected String _objectType;
    protected Map<String, String> _parametersAsMap;
    protected String _parametersAsString;
    protected String _rootName;
    protected String _rootType;

    /**
     * Constructor.
     * @param oldUri_p The URI V4/V5 as a String.
     * @throws Exception if the URI can not be decoded by previous URI patterns.
     */
    protected OldUri(String oldUri_p) throws Exception {
      String isoEncoding = "ISO-8859-1"; //$NON-NLS-1$
      if (oldUri_p.startsWith(PREFIX_V4_URI)) {
        // Deal with a v4 URI.
        boolean match = __uriV4Matcher.reset(oldUri_p).matches();
        if (!match) {
          throw new RuntimeException(MessageFormat.format(Messages.MigrationConnector_InvalidV4UriFormat_CantBeMigrated, oldUri_p));
        }
        _rootType = URLDecoder.decode(__uriV4Matcher.group(1), isoEncoding);
        _rootName = URLDecoder.decode(__uriV4Matcher.group(3), isoEncoding);
        _objectType = URLDecoder.decode(__uriV4Matcher.group(4), isoEncoding);
        if ((null != _objectType) && _objectType.isEmpty()) {
          _objectType = null;
        }
        _objectName = URLDecoder.decode(__uriV4Matcher.group(5), isoEncoding);
        if ((null != _objectName) && _objectName.isEmpty()) {
          _objectName = null;
        }
        _objectId = URLDecoder.decode(__uriV4Matcher.group(6), isoEncoding);
        if ((null != _objectId) && _objectId.isEmpty()) {
          _objectId = null;
        }
        _parametersAsString = __uriV4Matcher.group(8);
        if ((null != _parametersAsString) && _parametersAsString.isEmpty()) {
          _parametersAsString = null;
        }
        _parametersAsMap = new HashMap<String, String>(0);
        if (null != _parametersAsString) {
          String[] parameters = _parametersAsString.split(","); //$NON-NLS-1$
          for (String parameter : parameters) {
            String[] pair = parameter.split("="); //$NON-NLS-1$
            if (2 == pair.length) {
              _parametersAsMap.put(URLDecoder.decode(pair[0], isoEncoding), URLDecoder.decode(pair[1], isoEncoding));
            }
          }
        }
      } else if (oldUri_p.startsWith(PREFIX_V4_URL)) {
        // Deal with a v4 URL.
        String urlParameters = oldUri_p.replace(PREFIX_V4_URL, ICommonConstants.EMPTY_STRING);
        if (urlParameters.trim().isEmpty()) {
          throw new RuntimeException(MessageFormat.format(Messages.MigrationConnector_InvalidV4UrlFormat_CantBeMigrated, oldUri_p));
        }
        // Decompose parameters.
        Map<String, String> decomposedParameters = new HashMap<String, String>(0);
        String[] parameters = urlParameters.split("&"); //$NON-NLS-1$
        // Cycle through parameters to decompose.
        for (String parameter : parameters) {
          String[] couple = parameter.split("="); //$NON-NLS-1$
          if ((0 == couple.length) || (2 < couple.length)) {
            // Skip this.
            continue;
          }
          // Get key and value.
          String key = couple[0];
          String value = null;
          if (2 == couple.length) {
            value = couple[1];
          }
          decomposedParameters.put(key, value);
        }
        // Extract data.
        String toolName = decomposedParameters.get("targettoolname"); //$NON-NLS-1$
        if ((null != toolName) && !toolName.isEmpty()) {
          _rootType = URLDecoder.decode(toolName, isoEncoding);
        }
        String projectName = decomposedParameters.get("projectname"); //$NON-NLS-1$
        if ((null != toolName) && !toolName.isEmpty()) {
          _rootName = URLDecoder.decode(projectName, isoEncoding);
        }
        String objectType = decomposedParameters.get("objecttype"); //$NON-NLS-1$
        if ((null != objectType) && !objectType.isEmpty()) {
          _objectType = URLDecoder.decode(objectType, isoEncoding);
        }
        String objectName = decomposedParameters.get("objectname"); //$NON-NLS-1$
        if ((null != objectName) && !objectName.isEmpty()) {
          _objectName = URLDecoder.decode(objectName, isoEncoding);
        }
        String objectId = decomposedParameters.get("objectid"); //$NON-NLS-1$
        if ((null != objectId) && !objectId.isEmpty()) {
          _objectId = URLDecoder.decode(objectId, isoEncoding);
        }
        _parametersAsString = null;
        _parametersAsMap = new HashMap<String, String>(0);
      } else {
        // Deal with this URI as if it was an Orchestra V5 one.
        // If this fails, the constructor will throw an exception.
        OrchestraURI uri = new OrchestraURI(oldUri_p);
        _objectId = uri.getObjectId();
        _objectName = null;
        _objectType = uri.getObjectType();
        _parametersAsMap = uri.getParameters();
        _parametersAsString = uri.getEncodedParameters();
        _rootName = uri.getRootName();
        _rootType = uri.getRootType();
      }
    }
  }
}