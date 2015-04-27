/**
 * Copyright (c) THALES, 2010. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.handler.internal.command;

import org.eclipse.osgi.util.NLS;

/**
 * @author s0024585
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.model.handler.internal.command.messages"; //$NON-NLS-1$
  public static String ConfirmSwitchContextCommand_SwitchContextConfirmationMessage;
  public static String ConfirmSwitchContextCommand_SwitchContextTitle;
  public static String SwitchContextWizard_Button_Text_Refresh;
  public static String SwitchContextWizard_Column_Text_Name;
  public static String SwitchContextWizard_Column_Text_Status;
  public static String SwitchContextWizard_Page_Title_RegisteredClientsWrapup;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
    // Static initialization
  }
}
