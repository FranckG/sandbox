/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.handler.data;

import org.eclipse.osgi.util.NLS;

/**
 * @author s0011584
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.model.handler.data.messages"; //$NON-NLS-1$
  public static String ContextsEditingDomain_Save_Problems;
  public static String ContextsEditingDomain_Save_Resource_Error;
  public static String ContextsEditingDomain_Save_Resource_NotModified_Warning;
  public static String ContextsEditingDomain_Save_Resource_Skipped_Warning;
  public static String DataHandler_ContextSettingDialog_ButtonLabel_Continue;
  public static String DataHandler_ContextSettingDialog_ButtonLabel_Quit;
  public static String DataHandler_ContextSettingDialog_ButtonLabel_Reload;
  public static String DataHandler_ContextSettingDialog_ButtonLabel_Retry;
  public static String DataHandler_ContextSettingError_Message;
  public static String DataHandler_ContextSettingError_Title;
  public static String DataHandler_Create_DirectoryAlreadyExists_Error;
  public static String DataHandler_Create_NoName_Error;
  public static String DataHandler_Create_NoResource_Error;
  public static String DataHandler_Import_ContextsNotLoaded_Error;
  public static String DataHandler_Import_Problems;
  public static String DataHandler_Participate_Import_Error;
  public static String DataHandler_Participate_NoName_Error;
  public static String DataHandler_Participate_NoParent_Error;
  public static String DataHandler_Participate_Save_Error;
  public static String DataHandler_PopulateContextDescription_NotExecutable_Error;
  public static String DataHandler_RemoteListeners_PostContextChange_WrapupMessage;
  public static String DataHandler_ScriptExecutionMessage_ExceptionWhileExecutingScript;
  public static String DataHandler_ScriptExecutionMessage_ExitOnError;
  public static String DataHandler_SetContext_ProgressTask_CheckRegisteredApplications;
  public static String DataHandler_SetContext_ProgressTask_ContextChangeListeners;
  public static String DataHandler_SetContext_ProgressTask_PreContextChangeListeners;
  public static String DataHandler_SetContext_ProgressTask_ScriptExecution;
  public static String DataHandler_SetContext_ProgressTask_ValidateContext;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
    // Nothing to do.
  }
}
