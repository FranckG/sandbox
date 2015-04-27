/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ecf.services;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

import com.thalesgroup.orchestra.framework.ecf.services.launcher.ILauncher;

/**
 * @author t0076261
 */
public class EcfServicesActivator extends Plugin {
  /**
   * Shared instance.
   */
  private static EcfServicesActivator __instance;
  /**
   * Type to launcher descriptor map.
   */
  private Map<String, LauncherDescriptor> _toolToLauncher;

  /**
   * Collect all contributed launchers.<br>
   * See extension point <code>com.thalesgroup.orchestra.framework.ecf.services.launcher</code> to declare a new launcher.
   * @return
   */
  public IStatus collectLaunchers() {
    MultiStatus result = new MultiStatus(getPluginId(), 0, Messages.EcfServicesActivator_StatusMessage_Launchers_CollectWrapUp, null);
    // Lazy load map.
    if (null == _toolToLauncher) {
      _toolToLauncher = new HashMap<String, LauncherDescriptor>(0);
      // Read extensions.
      IConfigurationElement[] configurationElements = ExtensionPointHelper.getConfigurationElements(getPluginId(), "launcher"); //$NON-NLS-1$
      // Precondition.
      if ((null == configurationElements) || (0 == configurationElements.length)) {
        result.add(new Status(IStatus.WARNING, getPluginId(), Messages.EcfServicesActivator_StatusMessage_Launchers_NoLauncherDefined));
        return result;
      }
      // Cycle through extensions.
      for (IConfigurationElement configurationElement : configurationElements) {
        String type = configurationElement.getAttribute("type"); //$NON-NLS-1$
        // Precondition.
        if (null == type) {
          continue;
        }
        // Create new launcher.
        LauncherDescriptor launcher = new LauncherDescriptor();
        launcher._type = type;
        launcher._launchTime = ExtensionPointHelper.getValue(configurationElement, "launchTime"); //$NON-NLS-1$
        IConfigurationElement launcherClass = ExtensionPointHelper.getChild(configurationElement, "launcherClass"); //$NON-NLS-1$
        if (null != launcherClass) {
          launcher._launcher = (ILauncher) ExtensionPointHelper.createInstance(launcherClass, ExtensionPointHelper.ATT_CLASS);
        }
        IConfigurationElement applicationPath = ExtensionPointHelper.getChild(configurationElement, "applicationPath"); //$NON-NLS-1$
        if (null != applicationPath) {
          // Path type.
          String pathType = applicationPath.getAttribute("type"); //$NON-NLS-1$
          launcher._applicationPathType = LauncherPathType.valueOf(pathType);
          // Path value.
          launcher._applicationPath = applicationPath.getAttribute("path"); //$NON-NLS-1$
        }
        if ((null == launcher._launcher) && (null == launcher._applicationPath)) {
          result.add(new Status(IStatus.ERROR, getPluginId(), MessageFormat.format(Messages.EcfServicesActivator_StatusMessage_Launchers_InvalidLauncher_Error,
              launcher._type)));
        } else {
          _toolToLauncher.put(type, launcher);
          result.add(new Status(IStatus.OK, getPluginId(), MessageFormat.format(
              Messages.EcfServicesActivator_StatusMessage_Launchers_LauncherSuccessfullyLoaded, launcher._type)));
        }
      }
    }
    return result;
  }

  /**
   * Get launcher for specified type.<br>
   * A type is defined by an association (see associations declaration files).
   * @param type_p
   * @return <code>null</code> if no such launcher could be found.
   */
  public LauncherDescriptor getLauncher(String type_p) {
    // Lazy load map.
    if (null == _toolToLauncher) {
      collectLaunchers();
    }
    return _toolToLauncher.get(type_p);
  }

  /**
   * Get plug-in ID.
   * @return
   */
  public String getPluginId() {
    return getBundle().getSymbolicName();
  }

  /**
   * Log a message to the eclipse log file.
   * @param message_p The message to log. Can not be <code>null</code>.
   * @param status_p The message severity, from {@link IStatus#OK} to {@link IStatus#ERROR}.
   * @param throwable_p An error description, if needed. Can be <code>null</code>.
   */
  public void logMessage(String message_p, int status_p, Throwable throwable_p) {
    // Precondition.
    if (null == message_p) {
      return;
    }
    // Create expected status.
    IStatus resultingMessage = new Status(status_p, getPluginId(), message_p, throwable_p);
    // Log it.
    getLog().log(resultingMessage);
  }

  /**
   * @see org.eclipse.core.runtime.Plugin#start(org.osgi.framework.BundleContext)
   */
  @Override
  public void start(BundleContext context_p) throws Exception {
    __instance = this;
    super.start(context_p);
  }

  /**
   * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
   */
  @Override
  public void stop(BundleContext context_p) throws Exception {
    super.stop(context_p);
    __instance = null;
  }

  /**
   * Get shared instance.
   * @return
   */
  public static EcfServicesActivator getInstance() {
    return __instance;
  }

  /**
   * Launcher descriptor as extracted from the contributors.
   * @author t0076261
   */
  public class LauncherDescriptor {
    public String _applicationPath;
    public LauncherPathType _applicationPathType;
    public ILauncher _launcher;
    public Integer _launchTime;
    public String _type;
  }

  /**
   * Launcher ODM path type.
   * @author t0076261
   */
  public enum LauncherPathType {
    CATEGORY, VARIABLE
  }
}