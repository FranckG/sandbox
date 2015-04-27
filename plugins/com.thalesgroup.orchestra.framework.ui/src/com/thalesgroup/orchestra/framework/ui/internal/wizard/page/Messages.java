/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.wizard.page;

import org.eclipse.osgi.util.NLS;

/**
 * @author t0076261
 */
public class Messages extends NLS {
  public static String AbstractContextsSelectionPage_Tooltip_ProjectLocation_Pattern;
  public static String AbstractContextsSelectionPage_Tooltip_ProjectLocation_Property_InWorkspace;
  public static String AbstractContextsSelectionPage_Tooltip_ProjectLocation_Property_OutsideWorkspace;
  public static String AbstractPageWithLiveValidation_ValidationFailed_HeaderMessage;
  public static String AbstractValueEditionHandler_Button_Text_Variables;
  public static String AbstractValueEditionHandler_Button_ToolTip_Variables;
  public static String AbstractValueEditionHandler_Dialog_Message;
  public static String AbstractValueEditionHandler_Dialog_Title;
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.ui.internal.wizard.page.messages"; //$NON-NLS-1$
  public static String EditCategoryPage_Page_Title;
  public static String EditContextPage_Administrators_Label_Text;
  public static String EditContextPage_AdministratorsAdd_Button_Text;
  public static String EditContextPage_AdministratorsId_Column_Text;
  public static String EditContextPage_AdministratorsName_Column_Text;
  public static String EditContextPage_AdministratorsRemove_Button_Text;
  public static String EditContextPage_Button_Text_Browse;
  public static String EditContextPage_ErrorMessage_EmptyPath;
  public static String EditContextPage_ErrorMessage_FoundContextNotParent;
  public static String EditContextPage_ErrorMessage_OnlyEnvVar;
  public static String EditContextPage_Page_Title;
  public static String EditContextPage_ParentContext_Label_Text;
  public static String EditContextPage_ParentPath_Label_Text;
  public static String EditContextPage_Text_NoPathToParent;
  public static String EditContextPage_WarningMessage_NoContextFound;
  public static String EditContextPage_WarningMessage_NonExistingDirectory;
  public static String ExportContextsPage_ContextsSelectionPart_Title;
  public static String ExportContextsPage_Dialog_Message;
  public static String ExportContextsPage_ErrorMessage_HierachyIsAlreadyAProject;
  public static String ExportContextsPage_ErrorMessage_InvalidDestinationDirectory;
  public static String ExportContextsPage_ErrorMessage_SameLocation;
  public static String ExportContextsPage_Label_Text_Location;
  public static String ExportContextsPage_Message_PromptForContextSelection;
  public static String ExportContextsPage_Message_PromptForLocationPath;
  public static String ExportContextsPage_Page_Title;
  public static String ImportContextsPage_Button_ImportAsCopy_Text;
  public static String ImportContextsPage_Button_ImportAsCopy_Tooltip;
  public static String ImportContextsPage_Button_Text_Refresh;
  public static String ImportContextsPage_Contexts_In_Workspace;
  public static String ImportContextsPage_ContextSelectionPart_Title;
  public static String ImportContextsPage_Dialog_Message;
  public static String ImportContextsPage_ErrorMessage_InvalidLocation;
  public static String ImportContextsPage_ImportContextsPage_Button_ImportAsCopy_Text;
  public static String ImportContextsPage_ImportContextsPage_Button_ImportAsCopy_Tooltip;
  public static String ImportContextsPage_Label_Text_Location;
  public static String ImportContextsPage_Message_PromptForContextSelection;
  public static String ImportContextsPage_Message_PromptForLocationPath;
  public static String ImportContextsPage_Not_Valid_Selection;
  public static String ImportContextsPage_Page_Title;
  public static String ImportContextsPage_ProgressMonitor_TaskName;
  public static String MonoValueEditionHandler_ToolTip_Text_Substituted;
  public static String MultipleValuesEditionHandler_Button_Add_Text;
  public static String MultipleValuesEditionHandler_Button_Remove_Text;
  public static String MultipleValuesEditionHandler_Button_Restore_Text;
  public static String RenameVariablePage_ErrorMessage_VariableDoesExist;
  public static String RenameVariablePage_ImpactedVariables_Label;
  public static String RenameVariablePage_Context_Label;
  public static String RenameVariablePage_VariableLocation_Label;
  public static String RenameVariablePage_VariableName_Label;
  public static String RenameVariablePage_Page_Description;
  public static String RenameVariablePage_Page_Title;
  public static String StartingPointEnvConfPage_ContextCreationFailed_DialogMessage;
  public static String StartingPointEnvConfPage_ContextCreationFailed_DialogTitle;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
    // Static initialization
  }
}
