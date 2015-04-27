/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.application;

import org.eclipse.osgi.util.NLS;

/**
 * @author t0076261
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.application.messages"; //$NON-NLS-1$
  public static String FrameworkApplication_CommandLineErrorDialog_Message;
  public static String FrameworkApplication_Dialog_Message_AutoJoinError;
  public static String FrameworkApplication_Dialog_Title_AutoJoinError;
  public static String FrameworkApplication_Dialog_Title_SetCurrentContextError;
  public static String FrameworkApplication_ErrorMessage_SetCurrentContext_TooManyContexts;
  public static String FrameworkApplication_ErrorMessage_SetCurrentContext_ContextNotFound;
  public static String FrameworkApplication_SwitchContextMessage;
  public static String FrameworkApplication_Warning_InstallationFetchedFile_CouldNotBeDeleted;
  public static String FrameworkApplicationWorkbenchAdvisor_ExitConfirmationDialog_Message;
  public static String FrameworkApplicationWorkbenchAdvisor_ExitConfirmationDialog_Title;
  public static String FrameworkCommandArgsHandler_CommandLineUsageNote;
  public static String FrameworkCommandArgsHandler_ErrorMessage_AdminContextsLocationAvailableOnlyWithUserOnly;
  public static String FrameworkCommandArgsHandler_ErrorMessage_InvalidLocation;
  public static String FrameworkCommandLineArgsHandler_ErrorMessage_OptionAvailableOnlyWithContextsPool;
  public static String FrameworkCommandLineArgsHandler_ErrorMessage_AutoJoinContextNameMissing;
  public static String FrameworkCommandLineArgsHandler_ErrorMessage_ExcludeDirectories_DirectoriesMissing;
  public static String FrameworkCommandLineArgsHandler_ErrorMessage_MaxDepth_DepthValueMissing;
  public static String FrameworkCommandLineArgsHandler_ErrorMessage_MaxDepth_WrongValue;
  public static String FrameworkCommandLineArgsHandler_ErrorMessage_SetCurrentContext_MissingValue;
  public static String FrameworkCommandLineArgsHandler_ErrorMessage_SetCurrentContextIncompatibleWithUserOnly;
  public static String FrameworkWorkbenchWindowAdvisor_ErrorDialogTitle_ParticipateFailed;
  public static String FrameworkWorkbenchWindowAdvisor_JoineContextCantBeSetAsCurrent;
  public static String FrameworkWorkbenchWindowAdvisor_Menu_Text_JoinContext;
  public static String FrameworkWorkbenchWindowAdvisor_SwitchContextMessage;
  public static String OrchestraFrameworkApplication_Message_FailedToStart;
  public static String PapeeteApplication_ApplicationEarlyClose_Message;
  public static String PapeeteApplication_ApplicationEarlyClose_Title;
  public static String PapeeteApplication_InvalidContext_Message;
  public static String PapeeteApplication_InvalidContext_Title;
  public static String PapeeteApplicationActionBarAdvisor_Menu_Text_Edit;
  public static String PapeeteApplicationActionBarAdvisor_Menu_Text_File;
  public static String PapeeteApplicationActionBarAdvisor_Menu_Text_Help;
  public static String PapeeteApplicationActionBarAdvisor_Menu_Text_Mode;
  public static String PapeeteApplicationActionBarAdvisor_Menu_Text_Window;
  public static String PapeeteWorkbenchWindowAdvisor_Menu_Text_Minimize;
  public static String PapeeteWorkbenchWindowAdvisor_Menu_Text_Restore;
  public static String PapeeteWorkbenchWindowAdvisor_Menu_Text_Shutdown;
  public static String PapeeteWorkbenchWindowAdvisor_PopUp_Message;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
    // Static initialization
  }
}
