/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.pref;

import org.eclipse.osgi.util.NLS;

/**
 * @author t0076261
 *
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.pref.messages"; //$NON-NLS-1$
  public static String AbstractDefaultPreferencePage_Error_UnableToSavePreferences;
  public static String FrameworkPreferencePage_ValidationGroup_Description;
  public static String FrameworkPreferencePage_ValidationGroup_Levels_Error;
  public static String FrameworkPreferencePage_ValidationGroup_Levels_Info;
  public static String FrameworkPreferencePage_ValidationGroup_Levels_Label;
  public static String FrameworkPreferencePage_ValidationGroup_Levels_LevelSelectionLabel;
  public static String FrameworkPreferencePage_ValidationGroup_Levels_Warning;
  public static String FrameworkPreferencePage_ValidationGroup_Name;
  public static String FrameworkPreferencePage_ValidationGroup_Tooltip;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
  }
}
