/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.connector.commands;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import com.thalesgroup.orchestra.framework.FrameworkActivator;
import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.framework.connector.AbstractConnector;
import com.thalesgroup.orchestra.framework.connector.Artifact;
import com.thalesgroup.orchestra.framework.connector.CommandContext;
import com.thalesgroup.orchestra.framework.connector.CommandStatus;
import com.thalesgroup.orchestra.framework.environment.IEnvironment;
import com.thalesgroup.orchestra.framework.exchange.GefHandler;
import com.thalesgroup.orchestra.framework.gef.Element;
import com.thalesgroup.orchestra.framework.gef.GEF;
import com.thalesgroup.orchestra.framework.gef.GefFactory;
import com.thalesgroup.orchestra.framework.gef.GenericExportFormat;
import com.thalesgroup.orchestra.framework.gef.Properties;
import com.thalesgroup.orchestra.framework.gef.Property;
import com.thalesgroup.orchestra.framework.internal.connector.StatusModelHelper;
import com.thalesgroup.orchestra.framework.lib.constants.ICommandConstants;
import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsFactory;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingVariable;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingVariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;
import com.thalesgroup.orchestra.framework.model.contexts.util.ContextsResourceImpl;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.command.SynchronizeAllCommand;
import com.thalesgroup.orchestra.framework.model.handler.data.DataHandler;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.model.handler.data.RootElement;
import com.thalesgroup.orchestra.framework.oe.ui.dialogs.OrchestraExplorerDialog;
import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.project.mode.AdministratorModeHandler;
import com.thalesgroup.orchestra.framework.ui.action.SetAsCurrentContextAction;
import com.thalesgroup.orchestra.security.CredentialsManagement;
import com.thalesgroup.orchestra.security.InMemoryCredentialsManagement;
import com.thalesgroup.orchestra.security.credentials.ICredentialsResponse;

/**
 * Connector for Framework commands that can be addressed by external clients.
 * @author t0076261
 */
public class FrameworkCommandsConnector extends AbstractConnector {

  /**
   * {@link OrchestraURI} comparator instance.
   */
  private OrchestraURIComparator _orchestraURIComparator;

  // Credentials specifics
  private static final String CREDENTIALS_MASTER_PASSWORD =
      "8iCaQC-KV1C90FknmA}Fzd}UniU5]R7)IPbpMhU)Pq}8Ib}Dg8h4gK4yrGmxEA[-]4ol>XExr{txSD2KMahnVgIA)QMRBmwPpcq63NBOW76g[SR7MtItKgbk50]Lzy)IJLvr9)CMwkQaqj2Y(0hfEJ[KEZ<PCVgf8V0oqajE2KETEZk1Fwm>hl42P5oF-CMbCpWksLeG>[WkyZYWBqUSKEHAHVy3AO7jlL3WMe}qPZzBck}jAVk57J9kUx[yO(Es"; //$NON-NLS-1$
  private static final String CREDENTIALS_LOCATION_VARIABLE = "\\Orchestra\\Security\\CredentialsLocation"; //$NON-NLS-1$
  private static String __credentialsLocation;
  private static CredentialsManagement __credentialsManagement;

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#create(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  @Override
  public CommandStatus create(CommandContext context_p) throws Exception {
    return createStatusForUnsupportedCommand(context_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#documentaryExport(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  @Override
  public CommandStatus documentaryExport(CommandContext context_p) throws Exception {
    return createStatusForUnsupportedCommand(context_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.AbstractConnector#executeSpecificCommand(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  @Override
  public CommandStatus executeSpecificCommand(CommandContext context_p) throws Exception {
    String commandType = context_p.getCommandType();
    if ("ChangeContext".equals(commandType)) { //$NON-NLS-1$
      return handleContextChange(context_p);
    } else if ("ChangeMode".equals(commandType)) { //$NON-NLS-1$
      return handleModeChange(context_p);
    } else if ("CloseFramework".equals(commandType)) { //$NON-NLS-1$
      return handleCloseFramework(context_p);
    } else if ("ExpandArtifactSet".equals(commandType)) { //$NON-NLS-1$
      return handleExpandArtifactSet(context_p);
    } else if ("GetLoadedContexts".equals(commandType)) { //$NON-NLS-1$
      return handleGetLoadedContexts(context_p);
    } else if ("GetContextEnvironments".equals(commandType)) { //$NON-NLS-1$
      return handleGetEnvironments(context_p);
    } else if ("GetEnvironmentAttributes".equals(commandType)) { //$NON-NLS-1$
      return handleGetEnvironmentAttributes(context_p);
    } else if ("KillNow".equals(commandType)) { //$NON-NLS-1$
      System.exit(-1);
    } else if ("SynchronizeAllContexts".equals(commandType)) { //$NON-NLS-1$
      return handleSynchronizeAllContexts();
    } else if ("SetVariableValue".equals(commandType)) { //$NON-NLS-1$
      return handleSetVariableValue(context_p);
    } else if (ICommandConstants.GET_CREDENTIALS.equals(commandType)) {
      return handleGetCredentials(context_p);
    } else if (ICommandConstants.RESET_CREDENTIALS.equals(commandType)) {
      return handleResetCredentials(context_p);
    } else if ("Explorer".equals(commandType)) { //$NON-NLS-1$
      return handleLaunchExplorer(context_p);
    }

    // Check whether the command shall be sent to an environment and execute it if so<br>
    // Command syntax : orchestra:<command>://FrameworkCommands/FC/Env/<environment Id>
    OrchestraURI uri = context_p.getArtifacts()[0].getUri();
    if ("Env".equals(uri.getObjectType())) { //$NON-NLS-1$
      return handleEnvironmentCommand(commandType, uri);
    }

    return super.executeSpecificCommand(context_p);
  }

  /**
   * Execute an environment command
   * @param command_p Command
   * @param uri_p Orchestra URI
   * @return Status
   */
  public CommandStatus handleEnvironmentCommand(String command_p, OrchestraURI uri_p) {
    String objectId = uri_p.getObjectId();
    IEnvironment environment = FrameworkActivator.getDefault().getEnvironmentsRegistry().getArtifactEnvironmentRegistry().getEnvironment(objectId);
    if (null == environment) {
      return new CommandStatus(IStatus.ERROR,
          MessageFormat.format(Messages.FrameworkCommandsConnector_EnvironmentCommand_Error_UnknownEnvironmentId, objectId), uri_p, 0);
    }
    return environment.executeCommand(command_p, uri_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#expand(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  @Override
  public CommandStatus expand(CommandContext context_p) throws Exception {
    return createStatusForUnsupportedCommand(context_p);
  }

  /**
   * Get {@link OrchestraURIComparator} in-use implementation.
   * @return
   */
  protected OrchestraURIComparator getOrchestraURIComparator() {
    if (null == _orchestraURIComparator) {
      _orchestraURIComparator = new OrchestraURIComparator();
    }
    return _orchestraURIComparator;
  }

  /**
   * Get plug-in for status creation.
   * @return
   */
  protected String getPluginId() {
    return getClass().getPackage().getName();
  }

  /**
   * Get root types for specified artifacts set URI, if any.
   * @param artifactSetUri_p
   * @return
   */
  protected List<String> getRootTypes(OrchestraURI artifactSetUri_p) {
    // Get parameter.
    String rootTypes = artifactSetUri_p.getParameters().get("RootTypes"); //$NON-NLS-1$
    // Preconditions.
    if ((null == rootTypes) || rootTypes.trim().isEmpty()) {
      return Collections.emptyList();
    }
    String[] values = rootTypes.split("\\|\\$\\|"); //$NON-NLS-1$
    return Arrays.asList(values);
  }

  /**
   * Handle "CloseFramework" command.
   * @param context_p
   * @return
   */
  protected CommandStatus handleCloseFramework(CommandContext context_p) {
    final boolean[] isClosed = new boolean[1];
    PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
      /**
       * @see java.lang.Runnable#run()
       */
      @Override
      public void run() {
        isClosed[0] = PlatformUI.getWorkbench().close();
      }
    });

    if (isClosed[0]) {
      return new CommandStatus(IStatus.OK, Messages.FrameworkCommandsConnector_CloseFramework_Successful, null, 0);
    }
    return new CommandStatus(IStatus.ERROR, Messages.FrameworkCommandsConnector_CloseFramework_Error, null, 0);
  }

  /**
   * Handle context change command.
   * @param context_p
   * @return
   */
  protected CommandStatus handleContextChange(CommandContext context_p) {
    // Get first artifact only.
    // That does not make much sense to try and apply several contexts.
    Artifact artifact = context_p.getArtifacts()[0];
    final OrchestraURI uri = artifact.getUri();
    // Precondition.
    if (null == uri) {
      CommandStatus result = super.createStatusForUnsupportedCommand(context_p);
      result.addChild(new CommandStatus(IStatus.ERROR, Messages.FrameworkCommandsConnector_ContextChange_Error_UriRequired, null, 0));
      return result;
    }
    Map<String, String> parameters = uri.getParameters();
    final String contextName = parameters.get("ContextName"); //$NON-NLS-1$
    // Invalid command.
    // Stop here.
    if (null == contextName) {
      return new CommandStatus(IStatus.ERROR, Messages.FrameworkCommandsConnector_ContextChange_Error_ParameterRequired, uri, 0);
    }
    final boolean askUser = Boolean.parseBoolean(parameters.get("AskUser")); //$NON-NLS-1$
    // Cycle through contexts so as to find target one.
    final CommandStatus[] errorStatus = new CommandStatus[] { null };
    RootElement contexts = ModelHandlerActivator.getDefault().getDataHandler().getAllContexts();
    // Is there a context with specified name ?
    boolean noSuchContext = true;
    for (Context context : contexts.getContexts()) {
      if (contextName.equals(context.getName())) {
        // There is a context with this name.
        noSuchContext = false;
        // Do switch.
        final Context contextToUse = context;
        PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
          /**
           * @see java.lang.Runnable#run()
           */
          @Override
          public void run() {
            try {
              SetAsCurrentContextAction action = new SetAsCurrentContextAction();
              action.shouldAskUserOnError(askUser);
              action.selectionChanged(new StructuredSelection(contextToUse));
              action.run();
            } catch (Exception e_p) {
              // Error while setting new context.
              // Create status.
              IStatus status =
                  new Status(IStatus.ERROR, getPluginId(), MessageFormat.format(Messages.FrameworkCommandsConnector_ContextChange_Error_UnexpectedError,
                      contextName), e_p);
              // Create aggregating command status.
              errorStatus[0] = new CommandStatus(IStatus.ERROR, status.getMessage(), uri, 0);
              errorStatus[0].addChild(new CommandStatus(status));
              // Log error.
              FrameworkActivator.getDefault().getLog().log(status);
            }
          }
        });
        // Stop search here.
        break;
      }
    }
    // No such context error.
    if (noSuchContext) {
      return new CommandStatus(IStatus.ERROR, MessageFormat.format(Messages.FrameworkCommandsConnector_ChangeContext_NoSuchContext, contextName), uri, 0);
    }
    // Return switching error, if any.
    if (null != errorStatus[0]) {
      return errorStatus[0];
    }
    return new CommandStatus(IStatus.OK, MessageFormat.format(Messages.FrameworkCommandsConnector_ContextChange_Successful, contextName), uri, 0);
  }

  /**
   * @param context_p the command context containing the uri to resolve. URI format: orchestra:GetLoadedContexts://FrameworkCommands/FC
   * @return a gef file with the list of loaded context and their attributes id, name and path
   */
  protected CommandStatus handleGetLoadedContexts(CommandContext context_p) {
    // Resulting wrap-up.
    CommandStatus result = new CommandStatus(Messages.FrameworkCommandsConnector_GetLoadedContexts_Message_WrapUp, null, 0);

    // initialize the gef model
    String outputGefFile = context_p.getExportFilePath();
    GefHandler handler = new GefHandler();
    GEF gefModel = handler.createNewModel(outputGefFile);
    // create the generic export format element
    GenericExportFormat genericExportFormat = GefFactory.eINSTANCE.createGenericExportFormat();

    // get all the contexts currently loaded in the Framework
    RootElement contexts = ModelHandlerActivator.getDefault().getDataHandler().getAllContexts();
    for (Context context : contexts.getContexts()) {
      String name = context.getName();
      String id = context.getId();
      String path = null;

      // create an element for each context
      Element contextElement = GefFactory.eINSTANCE.createElement();
      // add it to the generic export format
      genericExportFormat.getELEMENT().add(contextElement);

      contextElement.setLabel(name);
      contextElement.setType("Context"); //$NON-NLS-1$

      // create the properties element
      Properties contextProperties = GefFactory.eINSTANCE.createProperties();
      contextElement.getPROPERTIES().add(contextProperties);

      // Create the name property
      Property propertyName = GefFactory.eINSTANCE.createProperty();
      propertyName.setName("name"); //$NON-NLS-1$
      GefHandler.addValue(propertyName, GefHandler.ValueType.TEXT, name);
      // add it to the context element
      contextProperties.getPROPERTY().add(propertyName);

      // Create the id property
      Property propertyInternalId = GefFactory.eINSTANCE.createProperty();
      propertyInternalId.setName("id"); //$NON-NLS-1$
      GefHandler.addValue(propertyInternalId, GefHandler.ValueType.TEXT, id);
      // add it to the context element
      contextProperties.getPROPERTY().add(propertyInternalId);

      ContextsResourceImpl contextResource = context.eResource();
      URI eUri = contextResource.getURI();
      if (eUri.isPlatformResource()) {
        String platformString = eUri.toPlatformString(true);
        IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(platformString);

        path = resource.getLocation().makeAbsolute().toOSString();

        // Create the path property
        Property propertyPath = GefFactory.eINSTANCE.createProperty();
        propertyPath.setName("path"); //$NON-NLS-1$
        GefHandler.addValue(propertyPath, GefHandler.ValueType.TEXT, path);
        // add it to the context element
        contextProperties.getPROPERTY().add(propertyPath);
      }
    }

    // save the gef model
    gefModel.getGENERICEXPORTFORMAT().add(genericExportFormat);
    handler.saveModel(gefModel, true);
    handler.clean();
    return result;
  }

  /**
   * @param context_p the command context containing the uri to resolve. URI fomrat:
   *          orchestra:GetEnvironmentAttributes://FrameworkCommands/FC?ContextName=<ContextName>&EnvironmentId=<EnvId>
   * @return a gef file containing the attributes of the environment
   */
  protected CommandStatus handleGetEnvironmentAttributes(CommandContext context_p) {
    // Resulting wrap-up.
    CommandStatus result = new CommandStatus(Messages.FrameworkCommandsConnector_GetEnvironmentAttributes_Message_WrapUp, null, 0);
    Artifact[] artifacts = context_p.getArtifacts();
    if ((null == artifacts) || (0 == artifacts.length)) {
      return result;
    }
    // Cycle trough artifact URIs.
    for (Artifact artifact : artifacts) {
      // Get URI.
      OrchestraURI orchestraURI = artifact.getUri();
      String contextName = orchestraURI.getParameters().get("ContextName"); //$NON-NLS-1$
      String envId = orchestraURI.getParameters().get("EnvironmentId"); //$NON-NLS-1$
      // Check URI validity.
      if ((null == contextName) || (null == envId)) {
        // Invalid artifact set.
        result.addChild(new CommandStatus(IStatus.ERROR, Messages.FrameworkCommandsConnector_GetEnvironmentAttributes_Error_InvalidUri, orchestraURI, 0));
        continue;
      }

      // get the Context object
      Context context = getContext(contextName);
      if (null == context) {
        // context not loaded in current workspace.
        result.addChild(new CommandStatus(IStatus.ERROR, MessageFormat.format(
            Messages.FrameworkCommandsConnector_GetEnvironmentAttributes_Error_InvalidContext, contextName), orchestraURI, 0));
        continue;
      }
      // get the environments in the context
      Couple<Map<String, IEnvironment>, IStatus> environments =
          FrameworkActivator.getDefault().getEnvironmentsRegistry().getArtifactEnvironmentRegistry().getEnvironments(context, null, false);
      Map<String, IEnvironment> environmentMap = environments.getKey();

      if (environmentMap.isEmpty()) {
        // context not loaded in current workspace.
        result.addChild(new CommandStatus(IStatus.ERROR, MessageFormat.format(
            Messages.FrameworkCommandsConnector_GetEnvironmentAttributes_Error_NoEnvironmentsInContext, contextName), orchestraURI, 0));
        continue;
      }

      IStatus status = environments.getValue();
      if (!status.isOK()) {
        result.addChild(new CommandStatus(status));
        continue;
      }

      if (!environmentMap.containsKey(envId)) {
        // return an error if environment does not exist in context
        result.addChild(new CommandStatus(IStatus.ERROR, MessageFormat.format(
            Messages.FrameworkCommandsConnector_GetEnvironmentAttributes_Error_EnvironmentNotInContext, envId, contextName), orchestraURI, 0));
        continue;
      }

      // get the proper environment
      IEnvironment environment = environmentMap.get(envId);
      // Status for current GetContextEnvironments.
      CommandStatus getContextEnvironmentsStatus = new CommandStatus(ICommonConstants.EMPTY_STRING, orchestraURI, 0);
      result.addChild(getContextEnvironmentsStatus);

      // initialize the gef model
      String outputGefFile = context_p.getExportFilePath();
      GefHandler handler = new GefHandler();
      GEF gefModel = handler.createNewModel(outputGefFile);
      // create the generic export format element
      GenericExportFormat genericExportFormat = GefFactory.eINSTANCE.createGenericExportFormat();

      // create an element for environment attributes
      Element envAttributesElement = GefFactory.eINSTANCE.createElement();
      envAttributesElement.setType("EnvironmentAttributes"); //$NON-NLS-1$
      // create the properties element
      Properties envAttributesProperties = GefFactory.eINSTANCE.createProperties();
      envAttributesElement.getPROPERTIES().add(envAttributesProperties);

      Map<String, String> envAttributes = environment.getAttributes();
      if (envAttributes != null) {
        for (String attrName : envAttributes.keySet()) {
          String attrValue = envAttributes.get(attrName);
          if (attrValue != null) {
            // Create the property
            Property propertyEnvAttr = GefFactory.eINSTANCE.createProperty();
            propertyEnvAttr.setName(attrName);
            GefHandler.addValue(propertyEnvAttr, GefHandler.ValueType.TEXT, attrValue);
            // add it to the environment element
            envAttributesProperties.getPROPERTY().add(propertyEnvAttr);
          }
        }
      }

      // add the env element to the generic export format
      genericExportFormat.getELEMENT().add(envAttributesElement);

      // save the gef model
      gefModel.getGENERICEXPORTFORMAT().add(genericExportFormat);
      handler.saveModel(gefModel, true);
      handler.clean();
    }

    return result;
  }

  /**
   * get informations about declared environments in context
   * @param context_p the command context containing the uri to resolve. URI fomrat:
   *          orchestra:GetContextEnvironments://FrameworkCommands/FC?ContextName=<ContextName>
   * @return a gef file containing informations about environments of the given context
   */
  protected CommandStatus handleGetEnvironments(CommandContext context_p) {
    // Resulting wrap-up.
    CommandStatus result = new CommandStatus(Messages.FrameworkCommandsConnector_GetContextEnvironments_Message_WrapUp, null, 0);
    Artifact[] artifacts = context_p.getArtifacts();
    if ((null == artifacts) || (0 == artifacts.length)) {
      return result;
    }
    // Cycle trough artifact URIs.
    for (Artifact artifact : artifacts) {
      // Get URI.
      OrchestraURI orchestraURI = artifact.getUri();
      String contextName = orchestraURI.getParameters().get("ContextName"); //$NON-NLS-1$
      // Check URI validity.
      Context context;
      if (null == contextName) {
        // If no context name is provided, use current context
        context = ModelHandlerActivator.getDefault().getDataHandler().getCurrentContext();
      } else {
        // get the Context object
        context = getContext(contextName);
      }
      if (null == context) {
        // context not loaded in current workspace.
        result.addChild(new CommandStatus(IStatus.ERROR, Messages.FrameworkCommandsConnector_GetContextEnvironments_Error_InvalidContext, orchestraURI, 0));
        continue;
      }

      // Status for current GetContextEnvironments.
      CommandStatus getContextEnvironmentsStatus = new CommandStatus(ICommonConstants.EMPTY_STRING, orchestraURI, 0);
      result.addChild(getContextEnvironmentsStatus);

      // initialize the gef model
      String outputGefFile = context_p.getExportFilePath();
      GefHandler handler = new GefHandler();
      GEF gefModel = handler.createNewModel(outputGefFile);
      // create the generic export format element
      GenericExportFormat genericExportFormat = GefFactory.eINSTANCE.createGenericExportFormat();

      // get the environments in the context
      Couple<Map<String, IEnvironment>, IStatus> environments =
          FrameworkActivator.getDefault().getEnvironmentsRegistry().getArtifactEnvironmentRegistry().getEnvironments(context, null, false);
      Map<String, IEnvironment> environmentMap = environments.getKey();
      if (environmentMap.isEmpty()) {
        // context not loaded in current workspace.
        result.addChild(new CommandStatus(IStatus.ERROR, MessageFormat.format(
            Messages.FrameworkCommandsConnector_GetEnvironmentAttributes_Error_NoEnvironmentsInContext, contextName), orchestraURI, 0));
        continue;
      }

      IStatus status = environments.getValue();
      if (!status.isOK()) {
        result.addChild(new CommandStatus(status));
        continue;
      }

      // for each environment create a gef element and add its properties
      for (IEnvironment environment : environmentMap.values()) {
        // get the string representation of the environment
        String envName = environment.toString();
        // get the type of the environment
        String envType = environment.getDeclarationId();
        // get the internal id of the environment
        String envId = environment.getRuntimeId();
        // get credential support status
        String envCred = Boolean.toString(environment.areCredentialsSupported());
        // create an element for each environment
        Element envElement = GefFactory.eINSTANCE.createElement();
        // add it to the generic export format
        genericExportFormat.getELEMENT().add(envElement);

        envElement.setLabel(envName);
        envElement.setType("Environment"); //$NON-NLS-1$

        // create the properties element
        Properties envProperties = GefFactory.eINSTANCE.createProperties();
        envElement.getPROPERTIES().add(envProperties);

        // Create the name property
        Property propertyName = GefFactory.eINSTANCE.createProperty();
        propertyName.setName("name"); //$NON-NLS-1$
        GefHandler.addValue(propertyName, GefHandler.ValueType.TEXT, envName);
        // add it to the environment element
        envProperties.getPROPERTY().add(propertyName);

        // Create the id property
        Property propertyEnvType = GefFactory.eINSTANCE.createProperty();
        propertyEnvType.setName("type"); //$NON-NLS-1$
        GefHandler.addValue(propertyEnvType, GefHandler.ValueType.TEXT, envType);
        // add it to the environment element
        envProperties.getPROPERTY().add(propertyEnvType);

        // Create the type property
        Property propertyEnvId = GefFactory.eINSTANCE.createProperty();
        propertyEnvId.setName("id"); //$NON-NLS-1$
        GefHandler.addValue(propertyEnvId, GefHandler.ValueType.TEXT, envId);
        // add it to the environment element
        envProperties.getPROPERTY().add(propertyEnvId);

        // Create the type property
        Property propertyCredentials = GefFactory.eINSTANCE.createProperty();
        propertyCredentials.setName("credentialsSupported"); //$NON-NLS-1$
        GefHandler.addValue(propertyCredentials, GefHandler.ValueType.TEXT, envCred);
        // add it to the environment element
        envProperties.getPROPERTY().add(propertyCredentials);

        // create an element for environment attributes
        Element envAttributesElement = GefFactory.eINSTANCE.createElement();
        envAttributesElement.setType("EnvironmentAttributes"); //$NON-NLS-1$
        envElement.getELEMENT().add(envAttributesElement);
        // create the properties element
        Properties envAttributesProperties = GefFactory.eINSTANCE.createProperties();
        envAttributesElement.getPROPERTIES().add(envAttributesProperties);

        Map<String, String> envAttributes = environment.getAttributes();
        if (envAttributes != null) {
          for (String attrName : envAttributes.keySet()) {
            String attrValue = envAttributes.get(attrName);
            if (attrValue != null) {
              // Create the property
              Property propertyEnvAttr = GefFactory.eINSTANCE.createProperty();
              propertyEnvAttr.setName(attrName);
              GefHandler.addValue(propertyEnvAttr, GefHandler.ValueType.TEXT, attrValue);
              // add it to the environment element
              envAttributesProperties.getPROPERTY().add(propertyEnvAttr);
            }
          }
        }

        genericExportFormat.getELEMENT().add(envElement);
      }

      // save the gef model
      gefModel.getGENERICEXPORTFORMAT().add(genericExportFormat);
      handler.saveModel(gefModel, true);
      handler.clean();
    }

    return result;

  }

  private Context getContext(String contextName) {
    RootElement contexts = ModelHandlerActivator.getDefault().getDataHandler().getAllContexts();
    // Is there a context with specified name ?
    for (Context context : contexts.getContexts()) {
      if (contextName.equals(context.getName())) {
        // There is a context with this name.
        return context;
      }
    }
    return null;
  }

  /**
   * @return Path to credentials database file
   */
  private String getCredentialsLocation() {
    Context context = ModelHandlerActivator.getDefault().getDataHandler().getCurrentContext();
    // Specified path must point to an existing variable.
    AbstractVariable variable = DataUtil.getVariable(CREDENTIALS_LOCATION_VARIABLE, context);
    // Get overriding variable.
    OverridingVariable overridingVariable = DataUtil.getOverridingVariable(variable, context);
    if (null != overridingVariable) {
      variable = overridingVariable;
    }
    String path = DataUtil.getSubstitutedValue(variable.getValues().get(0), context).getValue();
    return path;
  }

  /**
   * Get credentials data by id.
   * @param context_p the command context containing the URI to resolve. URI format:
   *          orchestra:GetCredentials://FrameworkCommands/FC?CredentialsId=<CredentialsIdStringValue
   *          >&CredentialsUIPasswordConfirmation=<CredentialsUIPasswordConfirmationValue>&CredentialsUIOnTopMessage=<CredentialsUIOnTopMessageStringValue>
   * @return a HTTP body response containing data of credentialsId request
   */
  protected CommandStatus handleGetCredentials(final CommandContext context_p) {

    // Get first artifact only.
    Artifact artifact = context_p.getArtifacts()[0];
    final OrchestraURI uri = artifact.getUri();

    // Precondition.
    if (null == uri) {
      CommandStatus result = super.createStatusForUnsupportedCommand(context_p);
      result.addChild(new CommandStatus(IStatus.ERROR, Messages.FrameworkCommandsConnector_GetCredentials_Error_UriRequired, null, 0));
      return result;
    }

    // get 'getCredentails' command parameters
    Map<String, String> parameters = uri.getParameters();

    final String credentialsId = parameters.get("CredentialsId"); //$NON-NLS-1$
    if (null == credentialsId) { // Invalid command, stop here.
      return new CommandStatus(IStatus.ERROR, Messages.FrameworkCommandsConnector_GetCredentials_Error_ParameterRequired, uri, 0);
    }
    final boolean credentialsUIPasswordConfirmation = Boolean.valueOf(parameters.get("CredentialsUIPasswordConfirmation")).booleanValue(); //$NON-NLS-1$
    final String credentialsUIOnTopMessage = parameters.get("CredentialsUIOnTopMessage"); //$NON-NLS-1$    

    final CommandStatus[] errorStatus = new CommandStatus[] { null };
    ICredentialsResponse credentialsReponse = null;

    boolean isPersistent;
    if (parameters.containsKey("IsPersistent")) { //$NON-NLS-1$
      try {
        isPersistent = Boolean.parseBoolean(parameters.get("IsPersistent")); //$NON-NLS-1$
      } catch (Exception exception_p) {
        return new CommandStatus(IStatus.ERROR, Messages.FrameworkCommandsConnector_GetCredentials_Error_IsPersistentWrongValue, uri, 0);
      }
    } else {
      isPersistent = true;
    }

    try {
      if (isPersistent) {
        String path = getCredentialsLocation();
        if ((null == __credentialsManagement) || !path.equals(__credentialsLocation)) {
          __credentialsLocation = path;
          __credentialsManagement = new CredentialsManagement(path, CREDENTIALS_MASTER_PASSWORD);
        }
        credentialsReponse = __credentialsManagement.getCredentialsWithDialogBox(credentialsId, credentialsUIPasswordConfirmation, credentialsUIOnTopMessage);
      } else {
        credentialsReponse =
            InMemoryCredentialsManagement.getCredentialsWithDialogBox(credentialsId, credentialsUIPasswordConfirmation, credentialsUIOnTopMessage);
      }
      if (null == credentialsReponse) {
        errorStatus[0] = new CommandStatus(IStatus.ERROR, Messages.FrameworkCommandsConnector_GetCredentials_Failed, uri, 0);
      }
    } catch (Exception e_p) {
      // Error while getting credentials.
      // Create status.
      IStatus status = new Status(IStatus.ERROR, getPluginId(), Messages.FrameworkCommandsConnector_GetCredentials_Error_UnexpectedError, e_p);
      // Create aggregating command status.
      errorStatus[0] = new CommandStatus(IStatus.ERROR, status.getMessage(), uri, 0);
      errorStatus[0].addChild(new CommandStatus(status));

      // Log error.
      FrameworkActivator.getDefault().getLog().log(status);

      // Return error, command result creation
      return new CommandStatus(IStatus.ERROR, /* Messages.FrameworkCommandsConnector_GetCredentials_Error_UnexpectedError + */
      "error=" + e_p.getMessage() + " (" + e_p.getClass().getSimpleName() + ')', uri, 0); //$NON-NLS-1$ //$NON-NLS-2$
    }

    // command result creation
    String command = ""; //$NON-NLS-1$
    if (null != credentialsReponse.getCredentialsData()) {
      command =
          "login=" + OrchestraURI.encode(credentialsReponse.getCredentialsData().getUsername()) + "&password=" + OrchestraURI.encode(credentialsReponse.getCredentialsData().getPassword()) + "&"; //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
    }
    command = command + "uistatus=" + credentialsReponse.getCredentialsUIStatus(); //$NON-NLS-1$

    // return credentials data in command status message
    return new CommandStatus(IStatus.OK, /* Messages.FrameworkCommandsConnector_GetCredentials_Successful + */command, uri, 0);
  }

  /**
   * reset credential by identifier
   * @param context_p the command context containing the URI to resolve. URI format:
   *          orchestra:ResetCredentials://FrameworkCommands/FC?CredentialsId_p=<CredentialsIdValue>
   * @return a HTTP body response
   */
  protected CommandStatus handleResetCredentials(CommandContext context_p) {
    // Get first artifact only.
    Artifact artifact = context_p.getArtifacts()[0];
    final OrchestraURI uri = artifact.getUri();
    // Precondition.
    if (null == uri) {
      CommandStatus result = super.createStatusForUnsupportedCommand(context_p);
      result.addChild(new CommandStatus(IStatus.ERROR, Messages.FrameworkCommandsConnector_ResetCredentials_Error_UriRequired, null, 0));
      return result;
    }
    Map<String, String> parameters = uri.getParameters();
    // get 'resetCredentails' command parameter
    final String credentialsId = parameters.get("CredentialsId"); //$NON-NLS-1$
    // Invalid command, stop here.
    if (null == credentialsId) {
      return new CommandStatus(IStatus.ERROR, Messages.FrameworkCommandsConnector_ResetCredentials_Error_ParameterRequired, uri, 0);
    }

    boolean isPersistent;
    if (parameters.containsKey("IsPersistent")) { //$NON-NLS-1$
      try {
        isPersistent = Boolean.parseBoolean(parameters.get("IsPersistent")); //$NON-NLS-1$
      } catch (Exception exception_p) {
        return new CommandStatus(IStatus.ERROR, Messages.FrameworkCommandsConnector_ResetCredentials_Error_IsPersistentWrongValue, uri, 0);
      }
    } else {
      isPersistent = true;
    }

    final CommandStatus[] errorStatus = new CommandStatus[] { null };

    try {
      boolean successfullycredentialsReset;
      if (isPersistent) {
        String path = getCredentialsLocation();
        if ((null == __credentialsManagement) || !path.equals(__credentialsLocation)) {
          __credentialsLocation = path;
          __credentialsManagement = new CredentialsManagement(path, CREDENTIALS_MASTER_PASSWORD);
        }
        successfullycredentialsReset = __credentialsManagement.delCredentials(credentialsId);
      } else {
        successfullycredentialsReset = InMemoryCredentialsManagement.delCredentials(credentialsId);
      }
      if (!successfullycredentialsReset) {
        errorStatus[0] = new CommandStatus(IStatus.ERROR, Messages.FrameworkCommandsConnector_ResetCredentials_Failed, uri, 0);
      }
    } catch (Exception e_p) {
      // Error while setting new context.
      // Create status.
      IStatus status = new Status(IStatus.ERROR, getPluginId(), Messages.FrameworkCommandsConnector_ResetCredentials_Error_UnexpectedError + "(" //$NON-NLS-1$
                                                                + e_p.getClass().getSimpleName() + ")", e_p); //$NON-NLS-1$
      // Create aggregating command status.
      errorStatus[0] = new CommandStatus(IStatus.ERROR, status.getMessage(), uri, 0);
      errorStatus[0].addChild(new CommandStatus(status));
      // Log error.
      FrameworkActivator.getDefault().getLog().log(status);
    }

    // Return error, if any.
    if (null != errorStatus[0]) {
      return errorStatus[0];
    }
    return new CommandStatus(IStatus.OK, Messages.FrameworkCommandsConnector_ResetCredentials_Successful, uri, 0);
  }

  /**
   * Handle "ExpandArtifactSet" command.
   * @param context_p
   * @return
   */
  protected CommandStatus handleExpandArtifactSet(CommandContext context_p) {
    // Resulting wrap-up.
    CommandStatus result = new CommandStatus(Messages.FrameworkCommandsConnector_ExpandArtifactSets_Message_WrapUp, null, 0);
    Artifact[] artifacts = context_p.getArtifacts();
    if ((null == artifacts) || (0 == artifacts.length)) {
      return result;
    }
    // Get root artifacts.
    GefHandler gefHandler = new GefHandler();
    // Create export file path.
    String subExportFile = StatusModelHelper.createDataFileName(ICommandConstants.GET_ROOT_ARTIFACTS, "Framework", gefHandler.getFileExtension()); //$NON-NLS-1$
    // Create calling context.
    CommandContext context = new CommandContext(context_p.getCommandType(), subExportFile);
    CommandStatus commandStatus = FrameworkActivator.getDefault().getEnvironmentsRegistry().getRootArtifacts(context, null);
    // Only stop if everything went mad.
    // In case data are partially ok, use them anyway (ie WARNING included).
    if (IStatus.ERROR == commandStatus.getSeverity()) {
      result.addChild(commandStatus);
      return result;
    }
    // Read all root artifacts.
    GEF gef = gefHandler.loadModel(subExportFile);
    // Precondition.
    if (null == gef) {
      result.addChild(new CommandStatus(IStatus.ERROR, Messages.FrameworkCommandsConnector_ExpandArtifactSets_Error_CantGetRootArtifacts, null, 0));
      return result;
    }
    // Existing root URIs.
    Collection<OrchestraURI> URIs = new ArrayList<OrchestraURI>(0);
    // Cycle through root artifacts.
    for (GenericExportFormat genericExportFormat : gef.getGENERICEXPORTFORMAT()) {
      for (Element element : genericExportFormat.getELEMENT()) {
        URIs.add(new OrchestraURI(element.getUri()));
      }
    }
    // Precondition.
    if (URIs.isEmpty()) {
      result.addChild(new CommandStatus(IStatus.ERROR, Messages.FrameworkCommandsConnector_ExpandArtifactSets_Error_NoRootArtifact, null, 0));
      return result;
    }
    // Cycle trough artifact set URIs.
    for (Artifact artifact : artifacts) {
      // Get URI.
      OrchestraURI orchestraURI = artifact.getUri();
      String logicalFolderPath = orchestraURI.getParameters().get("LogicalFolderPath"); //$NON-NLS-1$
      // Check URI validity.
      if (null == logicalFolderPath) {
        // Invalid artifact set.
        result.addChild(new CommandStatus(IStatus.ERROR, Messages.FrameworkCommandsConnector_ExpandArtifactSets_Error_InvalidSetUri, orchestraURI, 0));
        continue;
      }
      // Status for current set.
      CommandStatus setStatus = new CommandStatus(ICommonConstants.EMPTY_STRING, orchestraURI, 0);
      result.addChild(setStatus);
      // Retained URIs.
      List<OrchestraURI> retainedURIs = new ArrayList<OrchestraURI>(0);
      // Logical folder path, as a path :)
      IPath prefixPath = new Path(logicalFolderPath);
      // Get sub-artifacts root type.
      List<String> rootTypes = getRootTypes(orchestraURI);
      // Cycle through current root artifacts.
      for (OrchestraURI uri : URIs) {
        // There is a root types filter.
        if (!rootTypes.isEmpty() && !rootTypes.contains(uri.getRootType())) {
          // Entry does not match, skip it.
          continue;
        }
        // Compare paths.
        IPath logicalPath = new Path(uri.getRootName());
        if (prefixPath.isPrefixOf(logicalPath)) {
          retainedURIs.add(uri);
        }
      }
      // Sort URIs.
      if (!retainedURIs.isEmpty()) {
        // Sort them.
        Collections.sort(retainedURIs, getOrchestraURIComparator());
        // Then add them.
        for (OrchestraURI uri : retainedURIs) {
          setStatus.addChild(new CommandStatus(IStatus.OK, ICommonConstants.EMPTY_STRING, uri, 0));
        }
      }
    }
    return result;
  }

  /**
   * Handle mode change command.
   * @param context_p
   * @return
   */
  protected CommandStatus handleModeChange(CommandContext context_p) {
    // Get first artifact only.
    // That does not make much sense to try and apply several contexts.
    Artifact artifact = context_p.getArtifacts()[0];
    final OrchestraURI uri = artifact.getUri();
    // Precondition.
    if (null == uri) {
      CommandStatus result = super.createStatusForUnsupportedCommand(context_p);
      result.addChild(new CommandStatus(IStatus.ERROR, Messages.FrameworkCommandsConnector_ModeChange_Error_UriRequired, null, 0));
      return result;
    }
    Map<String, String> parameters = uri.getParameters();
    final String mode = parameters.get("Mode"); //$NON-NLS-1$
    // Invalid command.
    // Stop here.
    if (null == mode) {
      return new CommandStatus(IStatus.ERROR, Messages.FrameworkCommandsConnector_ModeChange_Error_ParameterRequired, uri, 0);
    }
    final CommandStatus[] errorStatus = new CommandStatus[] { null };
    PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
      /**
       * @see java.lang.Runnable#run()
       */
      @Override
      public void run() {
        // Get current mode.
        boolean isCurrentlyAdministrator = ProjectActivator.getInstance().isAdministrator();
        boolean successfullyChanged = true;
        try {
          if ("User".equals(mode)) { //$NON-NLS-1$
            if (isCurrentlyAdministrator) {
              successfullyChanged = AdministratorModeHandler.changeAdministratorMode();
            }
          } else if ("Administrator".equals(mode)) { //$NON-NLS-1$
            if (!isCurrentlyAdministrator) {
              successfullyChanged = AdministratorModeHandler.changeAdministratorMode();
            }
          }
          // Mode not changed.
          if (!successfullyChanged) {
            errorStatus[0] =
                new CommandStatus(IStatus.OK, MessageFormat.format(Messages.FrameworkCommandsConnector_ModeChange_Error_FailedToChangeMode, mode), uri, 0);
          }
        } catch (Exception e_p) {
          // Error while setting new context.
          // Create status.
          IStatus status =
              new Status(IStatus.ERROR, getPluginId(), MessageFormat.format(Messages.FrameworkCommandsConnector_ModeChange_Error_UnexpectedError, mode), e_p);
          // Create aggregating command status.
          errorStatus[0] = new CommandStatus(IStatus.ERROR, status.getMessage(), uri, 0);
          errorStatus[0].addChild(new CommandStatus(status));
          // Log error.
          FrameworkActivator.getDefault().getLog().log(status);
        }
      }
    });
    // Return error, if any.
    if (null != errorStatus[0]) {
      return errorStatus[0];
    }
    return new CommandStatus(IStatus.OK, MessageFormat.format(Messages.FrameworkCommandsConnector_ModeChange_Successful, mode), uri, 0);
  }

  /**
   * Handle "SynchronizeAllContexts" command
   * @return
   */
  protected CommandStatus handleSynchronizeAllContexts() {
    final CommandStatus[] errorStatus = new CommandStatus[] { null };
    PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
      /**
       * @see java.lang.Runnable#run()
       */
      @Override
      public void run() {
        IStatus status;

        // Synchronize all contexts non interactively
        SynchronizeAllCommand command = new SynchronizeAllCommand(false);
        if (command.canExecute()) {
          status = command.batchExecute();
        } else {
          status = new Status(IStatus.ERROR, Messages.FrameworkCommandsConnector_SynchronizeAllContexts_Error_PermissionDenied, getPluginId());
        }
        errorStatus[0] = new CommandStatus(status);
      }
    });

    return errorStatus[0];

  }

  /**
   * Get or create overriding variable for specified variable path in specified context.
   * @param variablePathp
   * @param contextp
   * @return <code>null</code> if the overriding variable could not be created or parameters are invalid.
   */
  protected OverridingVariable getOverridingMonoValuedVariable(String variablePathp, Context contextp) {
    // Precondition.
    if (null == variablePathp) {
      return null;
    }
    // Specified path must point to an existing variable.
    AbstractVariable variable = DataUtil.getVariable(variablePathp, contextp);
    if (null == variable) {
      return null;
    }
    // Get overriding variable.
    OverridingVariable overridingVariable = DataUtil.getOverridingVariable(variable, contextp);
    // Either there is no overriding variable, or it does not belong to
    // specified context.
    if ((null == overridingVariable) || (contextp != ModelUtil.getContext(overridingVariable))) {
      // Create a new 'local' overriding variable.
      OverridingVariable newOverridingVariable = ContextsFactory.eINSTANCE.createOverridingVariable();
      newOverridingVariable.setOverriddenVariable((Variable) variable);
      // Create overriding value.
      OverridingVariableValue ovValue = ContextsFactory.eINSTANCE.createOverridingVariableValue();
      ovValue.setOverriddenValue(variable.getValues().get(0));
      ovValue.setValue(ICommonConstants.EMPTY_STRING);
      newOverridingVariable.getValues().add(ovValue);
      // Add newly created overriding variable to specified context.
      contextp.getOverridingVariables().add(newOverridingVariable);
      return newOverridingVariable;
    }
    return overridingVariable;
  }

  /**
   * Set value of a variable in the current context
   * @param context_p
   * @return
   */
  private CommandStatus handleSetVariableValue(CommandContext context_p) {
    Artifact artifact = context_p.getArtifacts()[0];
    final OrchestraURI uri = artifact.getUri();
    TreeMap<String, String> parameters = uri.getParameters();
    final String name = parameters.get("name"); //$NON-NLS-1$
    if (null == name) {
      CommandStatus commandStatus = super.createStatusForUnsupportedCommand(context_p);
      commandStatus.addChild(new CommandStatus(IStatus.ERROR, Messages.FrameworkCommandsConnector_SetVariableValue_Error_MissingNameParameterInUri, null, 0));
      return commandStatus;
    }
    final String value = parameters.get("value"); //$NON-NLS-1$
    if (null == value) {
      CommandStatus commandStatus = super.createStatusForUnsupportedCommand(context_p);
      commandStatus.addChild(new CommandStatus(IStatus.ERROR, Messages.FrameworkCommandsConnector_SetVariableValue_Error_MissingValueParameterInUri, null, 0));
      return commandStatus;
    }

    DataHandler dataHandler = ModelHandlerActivator.getDefault().getDataHandler();
    final Context currentContext = dataHandler.getCurrentContext();
    // Precondition.
    if (currentContext.eResource().isDefault()) {
      IStatus status = new Status(IStatus.ERROR, getPluginId(), Messages.FrameworkCommandsConnector_SetVariableValue_Error_CannotChangeDefaultContext, null);
      // Create aggregating command status.
      CommandStatus commandStatus = new CommandStatus(IStatus.ERROR, status.getMessage(), uri, 0);
      commandStatus.addChild(new CommandStatus(status));
      return commandStatus;
    }

    final CommandStatus[] errorStatus = new CommandStatus[] { null };
    PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
      public void run() {
        ModelHandlerActivator.getDefault().getEditingDomain().getNotificationsListener().setActive(false);
        try {
          Context currentContextToSave = ModelHandlerActivator.getDefault().getEditingDomain().getLocalContextFrom(currentContext);
          AbstractVariable lmContexts = getOverridingMonoValuedVariable(name, currentContext);
          AbstractVariable lmContextsToSave = getOverridingMonoValuedVariable(name, currentContextToSave);
          lmContexts.getValues().get(0).setValue(value);
          lmContextsToSave.getValues().get(0).setValue(value);
          // save it
          try {
            currentContextToSave.eResource().save(null);
          } catch (IOException e) {
            // Error while saving context.
            // Create status.
            IStatus status = new Status(IStatus.ERROR, getPluginId(), Messages.FrameworkCommandsConnector_SetVariableValue_Error_FailedToSaveCurrentContext, e);
            // Create aggregating command status.
            errorStatus[0] = new CommandStatus(IStatus.ERROR, status.getMessage(), uri, 0);
            errorStatus[0].addChild(new CommandStatus(status));
            // Log error.
            FrameworkActivator.getDefault().getLog().log(status);
          }
        } finally {
          ModelHandlerActivator.getDefault().getEditingDomain().getNotificationsListener().setActive(true);
        }
      }
    });
    // Return error, if any.
    if (null != errorStatus[0]) {
      return errorStatus[0];
    }

    return new CommandStatus(IStatus.OK, "", uri, 0); //$NON-NLS-1$
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#navigate(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  @Override
  public CommandStatus navigate(CommandContext context_p) throws Exception {
    return createStatusForUnsupportedCommand(context_p);
  }

  /**
   * {@link OrchestraURI} comparator that compares logical names.
   * @author t0076261
   */
  protected class OrchestraURIComparator implements Comparator<OrchestraURI> {
    /**
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(OrchestraURI o1_p, OrchestraURI o2_p) {
      // Preconditions.
      if (null == o1_p) {
        if (null == o2_p) {
          return 0;
        }
        return 1;
      } else if (null == o2_p) {
        return -1;
      }
      // Compare root names.
      return o1_p.getRootName().compareToIgnoreCase(o2_p.getRootName());
    }
  }

  /**
   * Launch an Orchestra Explorer
   * @param context_p
   * @return
   */
  protected CommandStatus handleLaunchExplorer(CommandContext context_p) {
    // Get first artifact only.
    // That does not make much sense to try and apply several contexts.
    Artifact artifact = context_p.getArtifacts()[0];
    final OrchestraURI uri = artifact.getUri();

    Map<String, String> parameters = uri.getParameters();

    // Title is mandatory
    String title = parameters.get("title"); //$NON-NLS-1$
    if (null == title) {
      return new CommandStatus(IStatus.ERROR, Messages.FrameworkCommandsConnector_Explorer_Error_TitleIsMandatory, uri, 0);
    }

    String message = parameters.get("message"); //$NON-NLS-1$

    String filteredRootType = parameters.get("filterRootType"); //$NON-NLS-1$
    List<String> filteredTypesList = null == filteredRootType ? null : Arrays.asList(filteredRootType.split(";"));

    String selectedURIs = parameters.get("selectedURIs"); //$NON-NLS-1$
    List<String> selectedURIsList = null == selectedURIs ? null : Arrays.asList(selectedURIs.split(";"));

    String multiSelect = parameters.get("multiSelect"); //$NON-NLS-1$
    if (null != multiSelect && !multiSelect.equals("yes") && !multiSelect.equals("no")) { //$NON-NLS-1$ //$NON-NLS-2$
      return new CommandStatus(IStatus.ERROR, Messages.FrameworkCommandsConnector_Explorer_Error_InvalidMultiSelectParameterValue, uri, 0);
    }
    boolean isMultiSelectable = multiSelect == null ? true : multiSelect.equals("yes"); //$NON-NLS-1$

    String selectableType = parameters.get("selectableType"); //$NON-NLS-1$
    List<String> selectableTypeList = null == selectableType ? null : Arrays.asList(selectableType.split(";"));

    Display display = new Display();
    CommandStatus status = null;
    try {
      OrchestraExplorerDialog dialog = new OrchestraExplorerDialog(title, message, filteredTypesList, selectedURIsList, isMultiSelectable, selectableTypeList);
      dialog.setBlockOnOpen(true);
      int dialogStatus = dialog.open();
      switch (dialogStatus) {
        case Window.OK:
          // Create export file path.
          String outputGefFile = context_p.getExportFilePath();
          // Builf GEF file from selected artefacts
          dialog.buildGEF(outputGefFile);
          status = new CommandStatus(IStatus.OK, "", uri, 0);
        break;
        case Window.CANCEL:
          status = new CommandStatus(IStatus.WARNING, "", uri, 0);
        break;
      }
    } catch (Exception exception_p) {
      status = new CommandStatus(IStatus.ERROR, exception_p.getMessage(), uri, 0);
    } finally {
      display.dispose();
    }
    return status;
  }
}