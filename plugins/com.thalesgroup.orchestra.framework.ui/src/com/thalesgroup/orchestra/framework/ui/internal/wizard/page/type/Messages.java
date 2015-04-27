/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.wizard.page.type;

import org.eclipse.osgi.util.NLS;

/**
 * @author s0011584
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.ui.internal.wizard.page.type.messages"; //$NON-NLS-1$
  public static String EnvironmentValueCustomizer_OpenButton_Text_Read;
  public static String EnvironmentValueCustomizer_OpenButton_Tooltip_Read;
  public static String EnvironmentValueCustomizer_OpenButton_Text_Setup;
  public static String EnvironmentValueCustomizer_OpenButton_Tooltip_Setup;
  public static String EnvironmentValueCustomizer_ErrorMessage_Setup;
  public static String FileVariableValueEditionHandler_Button_Text_Browse;
  public static String FileVariableValueEditionHandler_Button_ToolTip_Browse;
  public static String FolderVariableValueEditionHandler_Button_Text_Browse;
  public static String FolderVariableValueEditionHandler_Button_ToolTip_Browse;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
    // Static initialization
  }
}
