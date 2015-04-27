/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.connector.contribution;

import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

/**
 * @author s0018747
 */
public class ContributionActivator extends Plugin {
  // The shared instance
  private static ContributionActivator __plugin;

  /**
   * The constructor
   */
  public ContributionActivator() {
    // nothing
  }

  public Map<String, String> getMapParameters(String tool) {
    Map<String, AbstractParametersHandler> mapOfParams = ExecutableExtensionHolder.getInstance().getHandlers();
    if (mapOfParams.get(tool)!=null)
      return mapOfParams.get(tool).getParametersWizardPages();
    return null;
  }

  /**
   * Get plug-in ID.
   * @return
   */
  public String getPluginId() {
    return getBundle().getSymbolicName();
  }

  private void loadContributions() {
    IConfigurationElement[] config = Platform.getExtensionRegistry().getConfigurationElementsFor(getPluginId(), "artifactParameters"); //$NON-NLS-1$
    for (final IConfigurationElement e : config) {
      final String toolName = e.getAttribute("toolName"); //$NON-NLS-1$
      this.getLog().log(new Status(IStatus.INFO, getPluginId(), "Evaluating extension")); //$NON-NLS-1$
      ISafeRunnable runnable = new ISafeRunnable() {

        public void handleException(Throwable exception) {
          ContributionActivator.this.getLog().log(new Status(IStatus.ERROR, getPluginId(), exception.getMessage()));
        }

        public void run() throws Exception {
          Object o = e.createExecutableExtension("class"); //$NON-NLS-1$
          if (o instanceof AbstractParametersHandler) {
            //IWizardPage[] pages = ((AbstractParametersHandler) o).createParametersWizardPages();
            ExecutableExtensionHolder.getInstance().getHandlers().put(toolName, ((AbstractParametersHandler) o));
            //ExecutableExtensionHolder.getInstance().getPages().put(toolName, pages);
          }
        }
      };

      SafeRunner.run(runnable);
    }
  }

  /**
   * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
   */
  @Override
  public void start(BundleContext context) throws Exception {
    super.start(context);
    __plugin = this;
    loadContributions();
  }

  /**
   * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
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
  public static ContributionActivator getDefault() {
    return __plugin;
  }
}