/**
 * <p>
 * Copyright (c) 2002 : Thales Research & Technology
 * </p>
 * <p>
 * Société : Thales Research & Technology
 * </p>
 * <p>
 * Thales Part Number 16 262 601
 * </p>
 */
package com.thalesgroup.orchestra.framework.environment;

import com.thalesgroup.orchestra.framework.environment.core.EnvironmentVariableLoader;

/**
 * <p>
 * Titre : PapeetePath
 * </p>
 * <p>
 * Description : Papeete all used paths
 * @author Themis developer
 * @version 3.1
 */
public final class EnvironmentVariables {
  private static String _PAPEETE_HOME = null;
  private static String _THEMIS_SHAREDDIR = null;
  private static String _THEMIS_TEMP = null;
  private static String _CUSTOM_SHARED_DIR = null;
  private static EnvironmentVariableLoader variableMgr = null;

  /**
   * A wrapper for {@link EnvironmentVariableLoader#getConfDir()}.
   */
  public static String GetConfDir() {
    return variableMgr.getConfDir();
  }

  /**
   * Method GetConfigServerDtdPathName.
   * @return String
   */
  public static String GetConfigServerDtdPathName() {
    return variableMgr.GetConfigServerDtdPathName();
  }

  /**
   * Method GetConfigServerFilePathName.
   * @return String
   */
  public static String GetConfigServerFilePathName() {
    return variableMgr.GetConfigServerFilePathName();
  }

  /**
   * @return
   */
  public static String GetPapeete_Home() {
    return _PAPEETE_HOME;
  }

  public static String GetPapeeteLib() {
    return variableMgr.GetPapeeteLib();
  }

  /**
   * Method GetSharedDir.
   * @return String
   */
  public static String GetSharedDir() {
    return _THEMIS_SHAREDDIR;
  }

  /**
   * Method GetTempDir.
   * @return String
   */
  public static String GetTempDir() {
    return _THEMIS_TEMP;
  }

  /**
   * Method GetCustomSharedDir.
   * @return String
   */
  public static String GetCustomSharedDir() {
    return _CUSTOM_SHARED_DIR;
  }

  /**
   * Set custom shared directory
   * @param customSharedDir_p Custom shared directory
   */
  public static void SetCustomSharecDir(String customSharedDir_p) {
    _CUSTOM_SHARED_DIR = customSharedDir_p;
  }

  public static void init() {
    variableMgr = EnvironmentVariableLoader.getInstance();
    variableMgr.init();
    _PAPEETE_HOME = variableMgr.getPapeeteHome();
    _THEMIS_SHAREDDIR = variableMgr.getSharedDir();
    _THEMIS_TEMP = variableMgr.getTempDir();
  }
}