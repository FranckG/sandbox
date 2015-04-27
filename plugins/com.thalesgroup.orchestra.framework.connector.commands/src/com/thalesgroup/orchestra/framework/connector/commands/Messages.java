/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.connector.commands;

import org.eclipse.osgi.util.NLS;

/**
 * @author t0076261
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.connector.commands.messages"; //$NON-NLS-1$
  public static String FrameworkCommandsConnector_GetEnvironmentAttributes_Message_WrapUp;
  public static String FrameworkCommandsConnector_GetEnvironmentAttributes_Error_NoEnvironmentsInContext;
  public static String FrameworkCommandsConnector_GetEnvironmentAttributes_Error_EnvironmentNotInContext;
  public static String FrameworkCommandsConnector_GetEnvironmentAttributes_Error_InvalidContext;
  public static String FrameworkCommandsConnector_GetEnvironmentAttributes_Error_InvalidUri;
  public static String FrameworkCommandsConnector_GetEnvironments_Error_NoEnvironmentsInContext;
  public static String FrameworkCommandsConnector_GetCredentials_Error_UriRequired;
  public static String FrameworkCommandsConnector_GetCredentials_Error_ParameterRequired;
  public static String FrameworkCommandsConnector_GetCredentials_Error_IsPersistentWrongValue;
  public static String FrameworkCommandsConnector_GetCredentials_Successful;
  public static String FrameworkCommandsConnector_GetCredentials_Failed;
  public static String FrameworkCommandsConnector_GetCredentials_Error_UnexpectedError;
  public static String FrameworkCommandsConnector_ResetCredentials_Error_UriRequired;
  public static String FrameworkCommandsConnector_ResetCredentials_Error_ParameterRequired;
  public static String FrameworkCommandsConnector_ResetCredentials_Failed;
  public static String FrameworkCommandsConnector_ResetCredentials_Error_UnexpectedError;
  public static String FrameworkCommandsConnector_ResetCredentials_Error_IsPersistentWrongValue;
  public static String FrameworkCommandsConnector_ResetCredentials_Successful;
  public static String FrameworkCommandsConnector_GetContextEnvironments_Error_InvalidContext;
  public static String FrameworkCommandsConnector_GetContextEnvironments_Error_InvalidUri;
  public static String FrameworkCommandsConnector_GetContextEnvironments_Message_WrapUp;
  public static String FrameworkCommandsConnector_GetLoadedContexts_Message_WrapUp;
  public static String FrameworkCommandsConnector_ChangeContext_NoSuchContext;
  public static String FrameworkCommandsConnector_CloseFramework_Error;
  public static String FrameworkCommandsConnector_CloseFramework_Successful;
  public static String FrameworkCommandsConnector_ContextChange_Error_ParameterRequired;
  public static String FrameworkCommandsConnector_ContextChange_Error_UnexpectedError;
  public static String FrameworkCommandsConnector_ContextChange_Error_UriRequired;
  public static String FrameworkCommandsConnector_ContextChange_Successful;
  public static String FrameworkCommandsConnector_ExpandArtifactSets_Error_CantGetRootArtifacts;
  public static String FrameworkCommandsConnector_ExpandArtifactSets_Error_InvalidSetUri;
  public static String FrameworkCommandsConnector_ExpandArtifactSets_Error_NoRootArtifact;
  public static String FrameworkCommandsConnector_ExpandArtifactSets_Message_WrapUp;
  public static String FrameworkCommandsConnector_ModeChange_Error_FailedToChangeMode;
  public static String FrameworkCommandsConnector_ModeChange_Error_ParameterRequired;
  public static String FrameworkCommandsConnector_ModeChange_Error_UnexpectedError;
  public static String FrameworkCommandsConnector_ModeChange_Error_UriRequired;
  public static String FrameworkCommandsConnector_ModeChange_Successful;
  public static String FrameworkCommandsConnector_SynchronizeAllContexts_Error_PermissionDenied;
  public static String FrameworkCommandsConnector_SetVariableValue_Error_MissingNameParameterInUri;
  public static String FrameworkCommandsConnector_SetVariableValue_Error_MissingValueParameterInUri;
  public static String FrameworkCommandsConnector_SetVariableValue_Error_CannotChangeDefaultContext;
  public static String FrameworkCommandsConnector_SetVariableValue_Error_FailedToSaveCurrentContext;
  public static String FrameworkCommandsConnector_EnvironmentCommand_Error_UnknownEnvironmentId;
  public static String FrameworkCommandsConnector_Explorer_Error_TitleIsMandatory;
  public static String FrameworkCommandsConnector_Explorer_Error_InvalidMultiSelectParameterValue;

  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
  }
}
