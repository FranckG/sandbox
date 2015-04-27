/** 
 * <p>THALES Services - Engineering & Process Management</p>
 * <p>THALES Part Number 16 262 601</p>
 * <p>Copyright (c) 2002-2008 : THALES Services - Engineering & Process Management</p>
 */

package com.thalesgroup.orchestra.framework.oe.ui.preferences;

import org.eclipse.jface.preference.IPreferenceStore;

import com.thalesgroup.orchestra.framework.oe.OrchestraExplorerActivator;
import com.thalesgroup.orchestra.framework.oe.ui.OrchestraExplorerState.GroupType;
import com.thalesgroup.orchestra.framework.oe.ui.OrchestraExplorerState.SortType;

/**
 * <p>
 * Title : PreferenceManager
 * </p>
 * <p>
 * Description : The class is used to manage the preferences of Artifact Explorer
 * </p>
 * @author Orchestra Framework Tool Suite developer
 * @version 3.7.0
 */
public class PreferenceManager {
  private static PreferenceManager _instance = null;

  private PreferenceManager() {
    initializeDefaultPreferences();
  }

  /**
   * Returns true if the user wants to display error messages
   * @return true if display error messages
   */
  public boolean displayErrorMessage() {
    return getPreferenceStore().getBoolean(IPreferenceIDs.DISPLAY_POPUP_ERROR_MESSAGES_ID);
  }

  /**
   * Returns true if the user wants to display informations messages
   * @return true if display information messages
   */
  public boolean displayInformationMessage() {
    return getPreferenceStore().getBoolean(IPreferenceIDs.DISPLAY_POPUP_INFORMATION_MESSAGES_ID);
  }

  /**
   * Returns true if the user wants to display warning messages
   * @return true if display warning messages
   */
  public boolean displayWarningMessage() {
    return getPreferenceStore().getBoolean(IPreferenceIDs.DISPLAY_POPUP_WARNING_MESSAGES_ID);
  }

  /**
   * Returns the array of default sort criteria
   * @return array of criteria
   */
  public String[] getDefaultSortCriterionValues() {
    return GetArrayOfStringsFromString(Messages.PreferenceManager_AllSortValues);
  }

  /**
   * Returns the array of default sort criteria
   * @return array of criteria
   */
  public String[] getDefaultGroupCriterionValues() {
    return GetArrayOfStringsFromString(Messages.PreferenceManager_AllGroupByValues);
  }

  /**
   * Returns the default sort criteria for the plugin
   * @return sort criteria type
   */
  public SortType getPreferedSortCriterion() {
    String value = getPreferenceStore().getString(IPreferenceIDs.SORT_CRITERION_ID);
    if (value.equals(Messages.PreferenceManager_TypeSortValue)) {
      return SortType.TYPE;
    }
    if (value.equals(Messages.PreferenceManager_NameSortValue)) {
      return SortType.NAME;
    }
    if (value.equals(Messages.PreferenceManager_FileSystemSortValue)) {
      return SortType.ENVIRONMENT;
    }
    return SortType.NAME;
  }

  /**
   * Returns the default group criteria for the plugin
   * @return group criteria type
   */
  public GroupType getPreferedGroupCriterion() {
    String value = getPreferenceStore().getString(IPreferenceIDs.GROUP_CRITERION_ID);
    if (value.equals(Messages.PreferenceManager_TypeGroupByValue)) {
      return GroupType.TYPE;
    }
    if (value.equals(Messages.PreferenceManager_NoneGroupByValue)) {
      return GroupType.NONE;
    }
    if (value.equals(Messages.PreferenceManager_FileSystemGroupByValue)) {
      return GroupType.ENVIRONMENT;
    }
    return GroupType.TYPE;
  }

  /**
   * Initialization
   */
  public void initializeDefaultPreferences() {
    IPreferenceStore store = getPreferenceStore();
    store.setDefault(IPreferenceIDs.SORT_CRITERION_ID, Messages.PreferenceManager_FileSystemSortValue);
    store.setDefault(IPreferenceIDs.GROUP_CRITERION_ID, Messages.PreferenceManager_TypeGroupByValue);
    store.setDefault(IPreferenceIDs.DISPLAY_POPUP_INFORMATION_MESSAGES_ID, false);
    store.setDefault(IPreferenceIDs.DISPLAY_POPUP_WARNING_MESSAGES_ID, true);
    store.setDefault(IPreferenceIDs.DISPLAY_POPUP_ERROR_MESSAGES_ID, true);
  }

  /**
   * Converts the string into array
   * @param strings
   * @return array
   */
  public static String[] GetArrayOfStringsFromString(String strings) {
    String values[] = strings.split(",");//$NON-NLS-1$
    return values;
  }

  /**
   * Returns the singleton instance
   * @return
   */
  public static PreferenceManager getInstance() {
    if (_instance == null) {
      _instance = new PreferenceManager();
    }
    return _instance;
  }

  /**
   * Returns the preference store of the plugin.
   * @return
   */
  public static IPreferenceStore getPreferenceStore() {
    return OrchestraExplorerActivator.getDefault().getPreferenceStore();
  }
}
