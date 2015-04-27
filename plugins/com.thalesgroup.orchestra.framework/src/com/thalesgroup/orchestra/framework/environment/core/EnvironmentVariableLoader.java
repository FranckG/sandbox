package com.thalesgroup.orchestra.framework.environment.core;

import java.io.File;

import com.thalesgroup.orchestra.framework.lib.base.conf.ServerConfParam;
import com.thalesgroup.orchestra.framework.variablemanager.server.model.VariableManager;

public final class EnvironmentVariableLoader {
  private static EnvironmentVariableLoader __instance;
  public static final String ORCHESTRA_ARTEFACTS = "\\Orchestra\\ArtefactPath"; //$NON-NLS-1$
  public static final String ORCHESTRA_CONFDIR = "\\Orchestra\\ConfigurationDirectory"; //$NON-NLS-1$
  public static final String ORCHESTRA_SERVER_HOME = "\\Orchestra installation\\Products\\OrchestraFramework\\InstallLocation"; //$NON-NLS-1$
  public static final String ORCHESTRA_SHAREDDIR = "\\Orchestra\\SharedDirectory"; //$NON-NLS-1$
  public static final String ORCHESTRA_TEMP = "\\Orchestra\\TemporaryDirectory"; //$NON-NLS-1$
  private String _configurationDirectoryFullPath;
  private String _frameworkInstallationDirectoryFullPath;
  private String _libraryDirectoryFullPath;
  private String _serverConfigurationDtdFileFullPath;
  private String _sharedDirectoryFullPath;
  private String _temporayDirectoryFullPath;

  private EnvironmentVariableLoader() {
    // Nothing to do.
    // Singleton lock.
  }

  public String getConfDir() {
    return _configurationDirectoryFullPath;
  }

  /**
   * Method GetConfigServerDtdPathName.
   * @return String
   */
  public String GetConfigServerDtdPathName() {
    return _serverConfigurationDtdFileFullPath;
  }

  /**
   * Method GetConfigServerFilePathName.
   * @return String
   */
  public String GetConfigServerFilePathName() {
    return ServerConfParam.FILE_SERVER_CONF_PARAM;
  }

  public String getPapeeteHome() {
    return _frameworkInstallationDirectoryFullPath;
  }

  public String GetPapeeteLib() {
    return _libraryDirectoryFullPath;
  }

  /**
   * Get a valid path from the value of the supplied variable name. A valid variable is defined by the {@link #validate(String, String)} method.
   * @param name the name of the path variable to get a valid value of.
   * @return a valid path.
   */
  public String getPath(String name) {
    VariableManager manager = VariableManager.getInstance();
    String value = manager.getVariable(name).getValues().get(0);
    return value;
  }

  public String getSharedDir() {
    return _sharedDirectoryFullPath;
  }

  public String getTempDir() {
    return _temporayDirectoryFullPath;
  }

  public void init() {
    _frameworkInstallationDirectoryFullPath = getPath(ORCHESTRA_SERVER_HOME);

    _configurationDirectoryFullPath = getPath(ORCHESTRA_CONFDIR);
    _sharedDirectoryFullPath = getPath(ORCHESTRA_SHAREDDIR);
    _temporayDirectoryFullPath = getPath(ORCHESTRA_TEMP);

    StringBuffer papeeteHome = new StringBuffer();
    papeeteHome.append(_frameworkInstallationDirectoryFullPath);
    if (!papeeteHome.toString().endsWith(File.separator)) {
      papeeteHome.append(File.separator);
    }
    StringBuffer papeeteLib = new StringBuffer(papeeteHome);
    papeeteLib.append("lib"); //$NON-NLS-1$
    _libraryDirectoryFullPath = papeeteLib.toString();

    StringBuffer papeeteServerConfigDtd = new StringBuffer(papeeteHome);
    papeeteServerConfigDtd.append("dtd\\serverConfParam.dtd"); //$NON-NLS-1$
    _serverConfigurationDtdFileFullPath = papeeteServerConfigDtd.toString();
  }

  public static EnvironmentVariableLoader getInstance() {
    if (__instance == null) {
      __instance = new EnvironmentVariableLoader();
    }
    return __instance;
  }
}