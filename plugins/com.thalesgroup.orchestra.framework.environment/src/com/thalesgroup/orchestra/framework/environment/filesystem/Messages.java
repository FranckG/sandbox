/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment.filesystem;

import org.eclipse.osgi.util.NLS;

/**
 * @author t0076261
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.environment.filesystem.messages"; //$NON-NLS-1$
  public static String FileSystemEnvironment_Command_InvalidParameters;
  public static String FileSystemEnvironment_Error_Message_NoVersionForSpecifiedArtefact;
  public static String FileSystemEnvironment_Transcription_Warning_URIs_NotHandled;
  public static String FileSystemEnvironment_Error_Message_BaselineNotSupported;
  public static String FileSystemEnvironmentDefaultFiltersRegistry_Initialization_WrapUp;
  public static String FileSystemEnvironmentDefaultFiltersRegistry_OK_FilterLoaded;
  public static String FileSystemEnvironmentDefaultFiltersRegistry_OK_NoDefaultFilterFound;
  public static String FileSystemEnvironmentDefaultFiltersRegistry_Warning_FilterAlreadyLoaded;
  public static String FileSystemEnvironmentDefaultFiltersRegistry_Warning_FilterValueNotDefined;
  public static String FileSystemEnvironmentHandler_DirectoryValidation_Error_NoContent;
  public static String FileSystemEnvironmentHandler_DirectoryValidation_Error_NonAbsolutePath;
  public static String FileSystemEnvironmentHandler_FilterValidation_Error_MalformedFilter;
  public static String FileSystemEnvironmentHandler_FilterValidation_Error_NoContent;
  public static String FileSystemEnvironmentHandler_FilterValidation_WrapUpMessage;
  public static String FileSystemEnvironmentHandler_ToString_Default;
  public static String FileSystemEnvironmentHandler_ToString_Pattern;
  public static String FileSystemEnvironmentRule_ConfDirectory_Contained_By_Environment_Error_Pattern;
  public static String FileSystemEnvironmentRule_ConfDirectory_Contains_Environment_Error_Pattern;
  public static String FileSystemEnvironmentRule_Path_Contained_By_Other_Environment_Error_Pattern;
  public static String FileSystemEnvironmentRule_Path_Contains_Other_Environment_Error_Pattern;
  public static String FileSystemEnvironmentRule_Path_Identical_Error_Pattern;
  public static String FileSystemEnvironmentRule_Path_Identical_In_Other_Environment_Error_Pattern;
  public static String FileSystemEnvironmentRule_Path_Identical_To_ConfDirectory_Error_Pattern;
  public static String FileSystemEnvironmentRule_Path_Not_Valid_Folder_Error_Pattern;
  public static String FileSystemEnvironmentRule_Same_Environment_Error_Pattern;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
    // Nothing to do
  }
}
