/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.common.helper;

import org.eclipse.osgi.util.NLS;

/**
 * @author s0011584
 *
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.common.helper.messages"; //$NON-NLS-1$
  public static String FileHelper_copyResourceTo_failed;
  public static String ProjectHelper_HandleWorkspace_Error_CantSaveWorkspace;
  public static String ProjectHelper_HandleWorkspace_Error_GettingCorruptedProjectData;
  public static String ProjectHelper_HandleWorkspace_Error_ProjectCantBeReImportedAfterCorruption;
  public static String ProjectHelper_HandleWorkspace_Warning_ProjectReImportedAfterCorruption;
public static String ProjectHelper_NewContextWizard_NoAlphaNum_Error_Message;
  public static String ProjectHelper_Precondition_ExistOpen_Failed;
  public static String ProjectHelper_Precondition_Files_Failed;
  public static String ProjectHelper_Precondition_NotInCurrentLocation_Failed;
  public static String ProjectHelper_Project_Copy_Failed;
  public static String ProjectHelper_Project_Import_Failed;
  public static String ProjectHelper_Project_Not_Found;
  public static String ProjectHelper_UnaccessibleProject_Delete_Failure;
  public static String ProjectHelper_UnaccessibleProject_Delete_Result;
  public static String ProjectHelper_UnaccessibleProject_Delete_Success;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
    // Default constructor.
  }
}
