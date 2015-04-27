/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.wizard;

import org.eclipse.osgi.util.NLS;

/**
 * @author t0076261
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.ui.internal.wizard.messages"; //$NON-NLS-1$
  public static String AbstractEditVariablePage_Button_Title_Final;
  public static String AbstractEditVariablePage_Button_Title_Mandatory;
  public static String AbstractEditVariablePage_Button_Title_MultipleValues;
  public static String AbstractEditVariablePage_Button_Title_SingleValue;
  public static String AbstractEditVariablePage_Label_Title_Description;
  public static String AbstractEditVariablePage_Label_Title_Name;
  public static String AbstractEditVariablePage_Label_Title_Value;
  public static String AbstractEditVariablePage_Page_Title;
  public static String AbstractVariableWizard_Command_Label;
  public static String DisconnectedModeWizard_DisconnectedMode;
  public static String DisconnectedModeWizard_FileIsNotGefFile;
  public static String DisconnectedModeWizard_PathDoesNotExist;
  public static String DisconnectedModeWizard_PathIsNotFilePath;
  public static String DynamicAssociationWizard_FilePathDoesNotExist;
  public static String DynamicAssociationWizard_PathIsNotFilePath;
  public static String DynamicAssociationWizard_WindowsTitle;
  public static String EditCategoryWizard_Command_Label;
  public static String EditCategoryWizard_Wizard_Title;
  public static String EditContextWizard_Command_Label;
  public static String EditContextWizard_ContextDescriptionSaveError_Message;
  public static String EditContextWizard_ContextDescriptionSaveError_Title;
  public static String EditContextWizard_Wizard_Title;
  public static String EditContextWizard_LdapAccessError_Title;
  public static String EditContextWizard_LdapAccessError_WarningMessage;
  public static String EditContextWizard_LdapAccessError_Reason;
  public static String EditContextWizard_LdapAccessError_UnknownUser;
  public static String EditOverridingVariableWizard_Wizard_Title;
  public static String EditVariableWizard_Wizard_Title;
  public static String ExportContextsWizard_Dialog_Confirm_Title;
  public static String ExportContextsWizard_Wizard_Title;
  public static String ImportContextsWizard_ImportError_Message;
  public static String ImportContextsWizard_ImportError_Reason;
  public static String ImportContextsWizard_ImportError_Title;
  public static String ImportContextsWizard_Wizard_Title;
  public static String RenameVariableWizard_Wizard_Title;
  public static String SetupEnvironmentWizard_EnvironmentTypePage_Title;
  public static String SetupEnvironmentWizard_Wizard_Title;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
    // Static initialization
  }
}
