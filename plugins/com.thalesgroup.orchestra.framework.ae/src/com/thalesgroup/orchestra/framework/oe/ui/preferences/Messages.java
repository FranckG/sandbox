/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.oe.ui.preferences;

import org.eclipse.osgi.util.NLS;

/**
 * @author S0024585
 */
public class Messages extends NLS {
  public static String ArtefactExplorerPreferencePage_DefaultSortCriterion;
  public static String ArtefactExplorerPreferencePage_DefaultGroupByCriterion;
  public static String ArtefactExplorerPreferencePage_DisplayPopup;
  public static String ArtefactExplorerPreferencePage_ErrorMessages;
  public static String ArtefactExplorerPreferencePage_InformationMessages;
  public static String ArtefactExplorerPreferencePage_OrchestraExplorerPreferences;
  public static String ArtefactExplorerPreferencePage_WaringMessages;
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.oe.ui.preferences.messages"; //$NON-NLS-1$
  public static String PreferenceManager_AllSortValues;
  public static String PreferenceManager_FileSystemSortValue;
  public static String PreferenceManager_NameSortValue;
  public static String PreferenceManager_TypeSortValue;
  public static String PreferenceManager_AllGroupByValues;
  public static String PreferenceManager_FileSystemGroupByValue;
  public static String PreferenceManager_TypeGroupByValue;
  public static String PreferenceManager_NoneGroupByValue;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
    // Nothing to do
  }
}
