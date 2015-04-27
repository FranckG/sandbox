/**
 * <p>
 * Copyright (c) 2002-2008 : Thales Services - Engineering & Process Management
 * </p>
 * <p>
 * Société : Thales Services - Engineering & Process Management
 * </p>
 * <p>
 * Thales Part Number 16 262 601
 * </p>
 */
package com.thalesgroup.orchestra.framework.oe.variableloader;

import java.io.File;
import java.rmi.RemoteException;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;

import com.thalesgroup.orchestra.framework.lib.constants.IFrameworkConstants;
import com.thalesgroup.orchestra.framework.oe.OrchestraExplorerActivator;

import framework.orchestra.thalesgroup.com.VariableManagerAPI.VariableManagerAdapter;

import javax.xml.rpc.ServiceException;

/**
 * <p>
 * Title : EnvironmentVariablesLoader
 * </p>
 * <p>
 * Description : Class handling loading of environment variables required for Artifact Explorer
 * </p>
 * @author Papeete Tool Suite developer
 * @version 3.7.0
 */
public final class EnvironmentVariablesLoader {

  /**
   * Singleton
   */
  private static EnvironmentVariablesLoader _instance = new EnvironmentVariablesLoader();

  /**
   * The 'config' directory contained in the extracted confdir.
   */
  private String _connectorsConfigDirectory;

  /**
   * @return the 'config' directory path contained in the extracted confdir.
   */
  public String getConfig() {
    return _connectorsConfigDirectory;
  }

  /**
   * Read variables from the Framework.
   */
  public void init() {
    String pluginId = OrchestraExplorerActivator.getDefault().getPluginId();
    String confdir = ""; //$NON-NLS-1$
    MultiStatus rootStatus = new MultiStatus(pluginId, 0, Messages.EnvironmentVariablesLoader_ProblemWithOrchestraServices, null);
    try {
      VariableManagerAdapter variableManagerAdapter = VariableManagerAdapter.getInstance();
      confdir = variableManagerAdapter.getFirstValue(variableManagerAdapter.getVariableConfigurationDirectory());
    } catch (RemoteException remoteException) {
      MultiStatus status = new MultiStatus(pluginId, 0, Messages.EnvironmentVariablesLoader_ExceptionWhileAccessingFrameworkServices, null);
      status.add(new Status(IStatus.ERROR, pluginId, null, remoteException));
      rootStatus.add(status);
    } catch (ServiceException serviceException) {
      MultiStatus status = new MultiStatus(pluginId, 0, Messages.EnvironmentVariablesLoader_ExceptionWhileAccessingFrameworkServices, null);
      status.add(new Status(IStatus.ERROR, pluginId, null, serviceException));
      rootStatus.add(status);
    }
    if (rootStatus.getSeverity() != IStatus.OK) {
      OrchestraExplorerActivator.getDefault().getLog().log(rootStatus);
      return;
    }
    StringBuilder confdirBuffer = new StringBuilder(confdir);
    if (!confdirBuffer.toString().endsWith(File.separator)) {
      confdirBuffer.append(File.separator);
    }
    StringBuilder config = new StringBuilder(confdirBuffer.toString());
    config.append(IFrameworkConstants.FRAMEWORK_CONFDIR_NAME);
    config.append(File.separator);
    config.append("Config");//$NON-NLS-1$
    _connectorsConfigDirectory = config.toString();
  }

  /**
   * @return the singleton
   */
  public static EnvironmentVariablesLoader getInstance() {
    return _instance;
  }
}
