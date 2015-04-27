/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environments;

import org.eclipse.osgi.util.NLS;

/**
 * @author t0076261
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.environments.messages"; //$NON-NLS-1$
  public static String ArtifactEnvironment_GetContextEnvironmentsMessage;
  public static String AbstractEnvironmentsRegistry_MakeBaseline_BaselineResultIsMissing;
  public static String AbstractEnvironmentsRegistry_ProgressText_PropagatingBaseline;
  public static String ArtifactEnvironmentRegistry_Command_Error_InvalidArguments;
  public static String ArtifactEnvironmentRegistry_Command_Error_NoEnvironment;
  public static String ArtifactEnvironmentRegistry_RootArtefacts_Error_ArtefactIsNotUnique;
  public static String ArtifactEnvironmentRegistry_Transcription_Warning_NoTranscriptionAvailable;
  public static String BaselineLogFailed;
  public static String EnvironmentsHub_BaselineCreationOk;
  public static String EnvironmentsHub_BaselineStartingPoint_Error_CantPopulateBaseline;
  public static String EnvironmentsHub_ButtonLabel_Continue;
  public static String EnvironmentsHub_Create_DirectoryAlreadyExists_Error;
  public static String EnvironmentsHub_ErrorCreatingDirectory;
  public static String EnvironmentsHub_LogBaselineProcessConditionsOk;
  public static String EnvironmentsHub_LogContextValidationOk;
  public static String EnvironmentsHub_LogMessageLocation;
  public static String EnvironmentsHub_MakeBaseline_Successful_Message;
  public static String EnvironmentsHub_SwitchContextMessage;
  public static String EnvironmentsHub_SwitchContext_ErrorMessage;
  public static String EnvironmentsHub_UseBaseline_AsReference_AlreadyExistsInWorkspace;
  public static String EnvironmentsHub_UseBaselineProcess_Error_FailedToPopulateBaselineReferenceContext;
  public static String EnvironmentsRegistry_BaselineReference_InvalidBaselineLocation;
  public static String EnvironmentsRegistry_BaselineSelection_TaskText;
  public static String EnvironmentsRegistry_BaselineUsage_WrapUp_Message;
  public static String EnvironmentsRegistry_MakeBaseline_PreCondition_Error_CannotBeAppliedToDefaultContext;
  public static String EnvironmentsRegistry_MBLProcess_Error_FailedToPopulateBaselineProject;
  public static String EnvironmentsRegistry_MBLProcess_Error_FailedToReachBaselineProject;
  public static String EnvironmentsRegistry_MBLProcess_Error_FailedToReloadBaselineProject;
  public static String EnvironmentsRegistry_MBLProcess_Error_FailedToSaveModifiedBaselineProject;
  public static String EnvironmentsRegistry_MBLProcess_Error_FailedToUpdateBaselineProjectNature;
  public static String EnvironmentsRegistry_MBLProcess_Error_BaselineCatalogIsNotWritable;
  public static String EnvironmentsRegistry_PreConditionsError_LockAlreadyHoldByAnotherSession;
  public static String EnvironmentsRegistry_PreConditionsError_LockFailed;
  public static String EnvironmentsRegistry_PreConditionsError_LockFreeWhenItShouldBeOwned;
  public static String EnvironmentsRegistry_PreConditionsError_MakeBaselineAlreadyRunning;
  public static String EnvironmentsRegistry_PreConditionsError_NoBaselineRepositorySet;
  public static String EnvironmentsRegistry_ProgressText_AskingForBaselineName;
  public static String EnvironmentsRegistry_ProgressText_CheckingConditions;
  public static String EnvironmentsRegistry_ProgressText_CreatingProject;
  public static String EnvironmentsRegistry_ProgressText_ValidatingContext;
  public static String EnvironmentsRegistry_ValidationError_NameAlreadyExists;
  public static String EnvironmentsRegistry_ValidationError_NameEmpty;
  public static String EnvironmentsRegistry_ValidationWarning_DialogMessage;
  public static String EnvironmentsRegistry_ValidationDialog_Title;
  public static String EnvironmentsRegistry_ValidationWarning_EnvironmentVariablesUsageWrapUp;
  public static String EnvironmentsRegistry_ValidationWarning_EnvironmentVariableUsage;
  public static String EnvironmentsRegistry_ValidationWarning_NotCompatibleEnvironments;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
  }
}
