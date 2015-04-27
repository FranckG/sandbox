/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.oe.ui.wizard.creation;

import org.eclipse.osgi.util.NLS;

/**
 * @author s0018747
 */
public class Messages extends NLS {
  public static String ArtefactCreationWizard_InvalidCreationValues;
  public static String ArtefactCreationWizard_OrchestraExplorer;
  public static String ArtifactCreationWizard_AnErrorHasOccured;
  public static String ArtifactCreationWizard_WarningsHaveBeenReported;
  public static String ArtifactCreationWizard_Description;
  public static String ArtifactCreationWizard_Title;
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.oe.ui.wizard.creation.messages"; //$NON-NLS-1$
  public static String CreateArtefactWizardPage_AtefactUriAlreadyExists;
  public static String CreateArtefactWizardPage_BackslashReplacedWithSlash;
  public static String CreateArtefactWizardPage_NameCannotBeEmpty;
  public static String CreateArtefactWizardPage_NotAuthorizedCharacter;
  public static String CreateArtefactWizardPage_SelectEnvironment;
  public static String CreateArtefactWizardPage_SelectType;
  public static String CreateArtifactWizardPage_ArtefactPath_Not_Writable;
  public static String CreateArtifactWizardPage_Environment;
  public static String CreateArtifactWizardPage_Type;
  public static String CreateArtifactWizardPage_blank;
  public static String CreateArtifactWizardPage_LogicalPath;
  public static String CreateArtifactWizardPage_Name;
  public static String CreateArtifactWizardPage_No_RootPath;
  public static String CreateArtifactWizardPage_page_name;
  public static String CreateArtifactWizardPage_PhysicalPath;
  public static String CreateArtifactWizardPage_PhysicalRoot;
  public static String CreateArtifactWizardPage_star;

  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

}
