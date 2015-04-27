/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.filesystem.framework.ae.contribution;

import org.eclipse.osgi.util.NLS;

/**
 * @author s0018747
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.filesystem.framework.ae.contribution.messages"; //$NON-NLS-1$
  public static String FileSystemArtefactCreationPage_Page_Name;
  public static String FileSystemArtefactCreationPage_Page_Description;
  public static String FileSystemArtefactCreationPage_Page_Title;
  public static String FileSystemArtefactCreationPage_Directory_Label;
  public static String FileSystemArtefactCreationPage_LogicalName_Label;
  public static String FileSystemArtefactCreationPage_PhysicalPath_Label;

  public static String FileSystemArtefactCreationPage_ArtefactUriAlreadyExists;
  public static String FileSystemArtefactCreationPage_FileAlreadyExists;
  public static String FileSystemArtefactCreationPage_BackslashReplacedWithSlash;
  public static String FileSystemArtefactCreationPage_NameCannotBeEmpty;
  public static String FileSystemArtefactCreationPage_NotAuthorizedCharacter;
  public static String FileSystemArtefactCreationPage_ExcludedDirectory;
  public static String FileSystemArtefactCreationPage_BadLogicalName;
  
  public static String FileSystemArtefactCreationHandler_FailedToCreateDirectories;

  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

}
