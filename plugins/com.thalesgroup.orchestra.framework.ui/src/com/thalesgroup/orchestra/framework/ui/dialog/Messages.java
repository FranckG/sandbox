/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.dialog;

import org.eclipse.osgi.util.NLS;

/**
 * @author t0076261
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.ui.dialog.messages"; //$NON-NLS-1$
  public static String ChooseBaselineDialog_Button_BaselineType_Reference;
  public static String ChooseBaselineDialog_Button_BaselineType_StartingPoint;
  public static String ChooseBaselineDialog_GroupText_BaselineDescription;
  public static String ChooseBaselineDialog_GroupText_SelectBaseline;
  public static String ChooseBaselineDialog_GroupText_SelectBaselineUsage;
  public static String ChooseBaselineDialog_PageTitle;
  public static String ChooseBaselineDialog_WizardTitle;
  public static String FindVariableUsagesDialog_WindowTitle;
  public static String FindVariableUsagesDialog_PageTitle;
  public static String FindVariableUsagesDialog_Context_Label;
  public static String FindVariableUsagesDialog_VariableLocation_Label;
  public static String FindVariableUsagesDialog_VariableName_Label;
  public static String MakeBaselineDialog_Page_Title;
  public static String MakeBaselineDialog_Window_Title;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
  }
}
