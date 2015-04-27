/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.handler.command;

import org.eclipse.osgi.util.NLS;

/**
 * @author t0076261
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.model.handler.command.messages"; //$NON-NLS-1$
  public static String CopyToClipboardCommand_Description;
  public static String CopyToClipboardCommand_Label;
  public static String CutToClipboardCommand_Description;
  public static String CutToClipboardCommand_Label;
  public static String DeleteCommand_ErrorMessage_ElementIsNotModifiable;
  public static String DeleteCommandWithConfirmation_ConfirmationDialog_Question;
  public static String DeleteCommandWithConfirmation_ConfirmationDialog_Text;
  public static String DeleteCommandWithConfirmation_ConfirmationDialog_Title;
  public static String DeleteContext_ConfirmationDialog_AdditionalWarning_Admin;
  public static String DeleteContext_ConfirmationDialog_AdditionalWarning_User;
  public static String DeleteContext_Dialog_Title_UserMode;
  public static String InitializeCurrentVersionsCommand_Label;
  public static String PasteFromClipboardCommand_Description;
  public static String PasteFromClipboardCommand_Error_Title;
  public static String PasteFromClipboardCommand_ErrorMessage_CantCutAndPasteInTheSameDestination_Details;
  public static String PasteFromClipboardCommand_ErrorMessage_CantCutAndPasteInTheSameDestination;
  public static String PasteFromClipboardCommand_ErrorMessage_CantPasteCategoryInItself;
  public static String PasteFromClipboardCommand_First_CopyOf;
  public static String PasteFromClipboardCommand_Label;
  public static String PasteFromClipboardCommand_Multiple_CopyOf;
  public static String RefreshConnectorConfigurationCommand_Action_DefaultValue;
  public static String RefreshConnectorConfigurationCommand_Action_Description;
  public static String RefreshConnectorConfigurationCommand_Action_Name;
  public static String RefreshConnectorConfigurationCommand_Path_Connector;
  public static String RestoreVersionCommand_Description;
  public static String RestoreVersionCommand_Label;
  public static String SelectVersionCommand_Description;
  public static String SelectVersionCommand_Label;
  public static String SynchronizeCommand_Dialog_Title;
  public static String SynchronizeCommand_ErrorDialog_Title;
  public static String SynchronizeCommand_ErrorMessage_CouldNotPopulateContextDescriptionFile;
  public static String SynchronizeCommand_Message_CantSynchronizeWithReadOnlyFiles;
  public static String SynchronizeCommand_Message_CopyFailed;
  public static String SynchronizeCommand_Message_NoBackupAt;
  public static String SynchronizeCommand_Message_NoProjectAt;
  public static String SynchronizeCommand_Message_NoSyncNeeded;
  public static String SynchronizeCommand_Message_ParentCorrupted;
  public static String SynchronizeCommand_Message_ParentMissing;
  public static String SynchronizeCommand_Message_RestorationFailed;
  public static String SynchronizeCommand_Message_Start;
  public static String SynchronizeCommand_Message_SyncFailed;
  public static String SynchronizeCommand_SaveError_OperationCancelled;
  public static String UnableToDeleteContextCommand_ErrorDialog_Text;
  public static String UnableToDeleteContextCommand_ErrorDialog_Title;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
    // Static initialization
  }
}
