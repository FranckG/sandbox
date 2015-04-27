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
package com.thalesgroup.orchestra.framework;

import org.eclipse.core.runtime.IStatus;
import org.osgi.framework.BundleContext;

import com.thalesgroup.orchestra.framework.application.FrameworkConsoleView;
import com.thalesgroup.orchestra.framework.application.FrameworkConsoleView.LogLevel;
import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.connector.IConnectorRegistry;
import com.thalesgroup.orchestra.framework.environment.EnvironmentActivator;
import com.thalesgroup.orchestra.framework.environment.registry.EnvironmentRegistry;
import com.thalesgroup.orchestra.framework.environments.ArtifactEnvironmentRegistry;
import com.thalesgroup.orchestra.framework.environments.EnvironmentsHub;
import com.thalesgroup.orchestra.framework.internal.connector.ConnectorRegistry;
import com.thalesgroup.orchestra.framework.root.ui.activator.AbstractUIActivator;

/**
 * <p>
 * Title : PapeeteActivator
 * </p>
 * <p>
 * Description : The activator class controls the plug-in life cycle
 * </p>
 * @author Orchestra Framework developer
 * @version 3.7.0
 */
public class FrameworkActivator extends AbstractUIActivator {
  /**
   * Shared instance.
   */
  private static FrameworkActivator __plugin;
  /**
   * Connector registry.
   */
  private ConnectorRegistry _connectorRegistry;
  /**
   * Environment registry.
   */
  private EnvironmentRegistry _environmentRegistry;
  /**
   * Environments registry.
   */
  private EnvironmentsHub _environmentsRegistry;

  /**
   * Get {@link EnvironmentsHub} unique instance.
   * @return A not <code>null</code> {@link ArtifactEnvironmentRegistry} instance.
   */
  public EnvironmentsHub getEnvironmentsRegistry() {
    if (null == _environmentsRegistry) {
      _environmentsRegistry = new EnvironmentsHub();
    }
    return _environmentsRegistry;
  }

  /**
   * Get connector registry.
   * @return
   */
  public IConnectorRegistry getConnectorRegistry() {
    return _connectorRegistry;
  }

  /**
   * Get environment registry.
   * @return
   */
  public EnvironmentRegistry getEnvironmentRegistry() {
    return _environmentRegistry;
  }

  /**
   * @return The version of the application
   */
  public String getVersion() {
    return getBundle().getVersion().toString();
  }

  /**
   * Initialize connector registry.
   */
  public void initializeConnectorRegistry() {
    if (null != _connectorRegistry) {
      return;
    }
    // Initialize connector registry.
    _connectorRegistry = new ConnectorRegistry();
    IStatus result = _connectorRegistry.initialize();
    FrameworkActivator.getDefault().log(result, null);
  }

  /**
   * Initialize environment registry.
   */
  public void initializeEnvironmentRegistry() {
    if (null != _environmentRegistry) {
      return;
    }
    // Initialize environment registry.
    _environmentRegistry = EnvironmentActivator.getInstance().getEnvironmentRegistry();
    IStatus result = _environmentRegistry.initialize();
    FrameworkActivator.getDefault().log(result, null);
  }

  /**
   * Log specified status to all known loggers.
   * @param status_p
   * @param level_p Optional level for console view. Can be <code>null</code>.
   */
  public void log(IStatus status_p, LogLevel level_p) {
    // Precondition.
    if (null == status_p) {
      return;
    }
    // Log to Eclipse log first.
    getLog().log(status_p);
    // Finally, log to console.
    logToConsole(status_p, level_p, 0);
  }

  /**
   * Log to console.
   * @param status_p
   * @param level_p
   * @param tabCount_p
   */
  protected void logToConsole(IStatus status_p, LogLevel level_p, int tabCount_p) {
    // Log status.
    String tabs = ICommonConstants.EMPTY_STRING;
    for (int i = 0; i < tabCount_p; i++) {
      tabs += '\t';
    }
    String statusMessage = tabs + status_p.getMessage();
    if (null != level_p) {
      FrameworkConsoleView.writeMessageToConsole(statusMessage, level_p, false);
    } else {
      switch (status_p.getSeverity()) {
        case IStatus.CANCEL:
        case IStatus.WARNING:
          FrameworkConsoleView.writeWarningMessageToConsole(statusMessage);
        break;
        case IStatus.ERROR:
          FrameworkConsoleView.writeErrorMessageToConsole(statusMessage);
        break;
        case IStatus.OK:
          FrameworkConsoleView.writeServerMessageToConsole(statusMessage);
        break;
        case IStatus.INFO:
        default:
          FrameworkConsoleView.writeInfoMessageToConsole(statusMessage);
        break;
      }
    }
    // Log children statuses.
    for (IStatus childStatus : status_p.getChildren()) {
      logToConsole(childStatus, level_p, tabCount_p + 1);
    }
  }

  /**
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
   */
  @Override
  public void start(BundleContext context) throws Exception {
    super.start(context);
    __plugin = this;
  }

  /**
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
   */
  @Override
  public void stop(BundleContext context) throws Exception {
    __plugin = null;
    super.stop(context);
  }

  /**
   * Returns the shared instance
   * @return the shared instance
   */
  public static FrameworkActivator getDefault() {
    return __plugin;
  }
}
