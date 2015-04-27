/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.integration.tests.connector.mockecf.launcher;

import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.core.plugin.PluginRegistry;
import org.eclipse.pde.internal.core.DependencyManager;
import org.eclipse.pde.internal.launching.launcher.BundleLauncherHelper;
import org.eclipse.pde.internal.ui.IPDEUIConstants;
import org.eclipse.pde.ui.launcher.IPDELauncherConstants;

import com.thalesgroup.orchestra.framework.ecf.services.launcher.ILauncher;
import com.thalesgroup.orchestra.framework.integration.tests.helpers.IntegrationTestsHelper;

/**
 * @author T0052089
 */
public class Launcher implements ILauncher {

  private void appendPlugin(StringBuffer buffer, IPluginModelBase model) {
    if (buffer.length() > 0)
      buffer.append(',');
    buffer.append(model.getPluginBase().getId());
    buffer.append(BundleLauncherHelper.VERSION_SEPARATOR);
    buffer.append(model.getPluginBase().getVersion());
  }

  private void initializePluginsList(ILaunchConfigurationWorkingCopy wc, IPluginModelBase applicationPluginModel_p) {
    StringBuffer wsplugins = new StringBuffer();
    StringBuffer explugins = new StringBuffer();
    Set plugins = DependencyManager.getSelfAndDependencies(applicationPluginModel_p, null);
    Iterator iter = plugins.iterator();
    while (iter.hasNext()) {
      String id = iter.next().toString();
      IPluginModelBase model = PluginRegistry.findModel(id);
      if (model == null || !model.isEnabled())
        continue;
      if (model.getUnderlyingResource() == null) {
        appendPlugin(explugins, model);
      } else {
        appendPlugin(wsplugins, model);
      }
    }
    wc.setAttribute(IPDELauncherConstants.SELECTED_WORKSPACE_PLUGINS, wsplugins.toString());
    wc.setAttribute(IPDELauncherConstants.SELECTED_TARGET_PLUGINS, explugins.toString());
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ecf.services.launcher.ILauncher#launchApplication(com.thalesgroup.orchestra.framework.ecf.services.launcher.ILauncher.IApplicationContext)
   */
  @SuppressWarnings("nls")
  public IStatus launchApplication(IApplicationContext applicationContext_p) {
    try {
      ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
      ILaunchConfigurationType type = manager.getLaunchConfigurationType("org.eclipse.pde.ui.RuntimeWorkbench");
      ILaunchConfiguration[] configurations;
      configurations = manager.getLaunchConfigurations(type);
      for (int i = 0; i < configurations.length; i++) {
        ILaunchConfiguration configuration = configurations[i];
        if (configuration.getName().equals("Start ECF Mock Connector")) {
          configuration.delete();
          break;
        }
      }
      ILaunchConfigurationWorkingCopy workingCopy = type.newInstance(null, "Start ECF Mock Connector");
      workingCopy.setAttribute(IPDELauncherConstants.USE_PRODUCT, false);
      workingCopy.setAttribute(IPDELauncherConstants.APPLICATION, "com.thalesgroup.orchestra.framework.integration.tests.connector.mockecf.application");

      workingCopy.setAttribute(IPDELauncherConstants.USE_DEFAULT, false);
      workingCopy.setAttribute(IPDELauncherConstants.INCLUDE_OPTIONAL, true);
      workingCopy.setAttribute(IPDELauncherConstants.AUTOMATIC_ADD, true);
      workingCopy.setAttribute(IPDELauncherConstants.AUTOMATIC_VALIDATE, false);
      workingCopy.setAttribute(IPDELauncherConstants.SHOW_SELECTED_ONLY, false);

      workingCopy.setAttribute(IPDEUIConstants.GENERATED_CONFIG, true);

      initializePluginsList(workingCopy, PluginRegistry.findModel("com.thalesgroup.orchestra.framework.integration.tests.connector.mockecf"));

      ILaunch launchedAppli = workingCopy.launch(ILaunchManager.DEBUG_MODE, null);

      IntegrationTestsHelper.__lastLaunchedEcfConnector = launchedAppli;
    } catch (CoreException coreException_p) {
      return new Status(IStatus.ERROR, Activator.getContext().getBundle().getSymbolicName(), "Can't launch mock ECF connector !", coreException_p);
    }

    return Status.OK_STATUS;
  }
}
