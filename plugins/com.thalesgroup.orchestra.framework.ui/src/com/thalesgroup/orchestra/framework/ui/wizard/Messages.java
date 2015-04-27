package com.thalesgroup.orchestra.framework.ui.wizard;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.ui.wizard.messages"; //$NON-NLS-1$

  public static String DisconnectedModePage_ChooseTheFile;
  public static String DisconnectedModePage_SelectConnectorResponseFile;
  public static String DisconnectedModePage_SelectGefFile;
  public static String DisconnectedModePage_ToolInDisconnectedMode;
  public static String DynamicAssociationPage_ArtifactFileLabel;
  public static String DynamicAssociationPage_ChooseTheFile;
  public static String DynamicAssociationPage_ConfigurationDirectoryChoice;
  public static String DynamicAssociationPage_NoFileFound;
  public static String DynamicAssociationPage_SelectPhysicalArtifactPath;
  public static String DynamicAssociationPage_SelectTheFile;
  public static String MainNewContextWizardPage_0;
  public static String MainNewContextWizardPage_3;
  public static String MainNewContextWizardPage_4;
  public static String NewContextCopyWizard_PagesDescription;

  public static String NewContextCopyWizard_PagesTitle;

  public static String NewContextCopyWizard_WizardTitle;

  public static String NewContextWizard_1;
  public static String NewContextWizard_2;
  public static String NewContextWizard_ChooseDestinationDirectory_Dialog_Title;
  public static String NewContextWizard_contextExistsMessage;

  public static String NewContextWizard_Message_PromptForContextNameMessage;
  public static String NewContextWizard_NoAlphaNum_Error_Message;
  public static String NewContextWizard_projectExistsMessage;
  public static String NewContextWizard_ProjectLocation_BrowseButton_Text;
  public static String NewContextWizard_UseAnotherParentContext;
  public static String NewContextWizard_UseDefaultParentContext;

  public static String NewContextWizard_ErrorMessage_CantCreateContextInWorkspace;

  public static String NewContextWizard_ErrorMessage_ContextCantBeNested;

  public static String NewContextWizard_ErrorMessage_DestinationAlreadyContainsADirectoryWithContextName;

  public static String NewContextWizard_ErrorMessage_LocationCantBeNull;

  public static String NewContextWizard_ErrorMessage_LocationMustBeAnAbsoluteDirectoryPath;

  public static String NewContextWizardLocationPage_ContextLocation_Label;
  public static String NewContextWizardLocationPage_ContextLocationGroup_Title;
  public static String NewContextWizardLocationPage_CreateOutsideProject_Button_Text;
  public static String NewContextWizardLocationPage_CreateWorkspaceProject_Button_Text;
  public static String NewContextWizardLocationPage_ErrorMessage_InvalidLocationPath;
  public static String NewContextWizardLocationPage_Message_PromptForLocationPath;

  public static String NewUseBaselineContextWizard_StartingPointContextCreationPagesDescription;
  public static String NewUseBaselineContextWizard_ReferenceContextCreationPagesDescription;

  public static String NewUseBaselineContextWizard_StartingPointPagesTitle;
  public static String NewUseBaselineContextWizard_ReferencePagesTitle;

  public static String NewUseBaselineContextWizard_StartingPointPageDescription;

  public static String NewUseBaselineContextWizard_StartingPointWindowTitle;
  public static String NewUseBaselineContextWizard_ReferenceWindowTitle;

  public static String ParticipateContextWizard_Description;
  public static String ParticipateContextWizard_Dialog_Title_NewParticipation;
  public static String ParticipateContextWizard_Name_Label_Text;
  public static String ParticipateContextWizard_Title;

  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
    // Static initialization
  }
}