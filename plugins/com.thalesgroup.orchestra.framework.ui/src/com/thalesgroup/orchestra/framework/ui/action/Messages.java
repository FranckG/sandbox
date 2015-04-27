/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.action;

import org.eclipse.osgi.util.NLS;

/**
 * @author s0011584
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.ui.action.messages"; //$NON-NLS-1$
  public static String AdministratorModeAction_ToolTip_AdministratorMode;
  public static String AdministratorModeAction_ToolTip_UserMode;
  public static String AdministratorModeStatusLine_Text_Administrator;
  public static String AdministratorModeStatusLine_Text_User;
  public static String AdministratorModeStatusLine_ToolTip_Text;
  public static String EditElementAction_Action_Text;
  public static String ExportContextsAction_Action_Text;
  public static String FindVariableUsagesAction_Action_Text;
  public static String ImportContextsAction_Action_Text;
  public static String InitializeCurrentVersionsAction_Text;
  public static String RefreshConnectorConfigurationAction_Action_Title;
  public static String RenameVariableAction_Action_Text;
  public static String RestoreVersionAction_Text;
  public static String SelectVersionAction_Label;
  public static String SetAsCurrentContextAction_setAsCurrentContext;
  public static String SetAsCurrentContextAction_SwitchContext_ConfirmationMessage;
  public static String SetAsCurrentContextAction_SwitchContext_Title;
  public static String SetModeAdministratorAction_Action_Text;
  public static String SetModeUserAction_Action_Text0;
  public static String SynchronizeAction_Action_Text;
  public static String SynchronizeAllAction_Action_Text;

  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
    // Static initialization
  }
}
