package com.thalesgroup.orchestra.framework.ae.creation;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

import com.thalesgroup.orchestra.framework.root.ui.activator.AbstractUIActivator;

public class ArtefactCreationActivator extends AbstractUIActivator {

  private static ArtefactCreationActivator __plugin;

  private static BundleContext context;

  private IOrchestraExplorerServices _services;

  static BundleContext getContext() {
    return context;
  }

  /*
   * (non-Javadoc)
   * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
   */
  @Override
  public void start(BundleContext bundleContext) throws Exception {
    ArtefactCreationActivator.context = bundleContext;
    __plugin = this;
  }

  /*
   * (non-Javadoc)
   * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
   */
  @Override
  public void stop(BundleContext bundleContext) throws Exception {
    ArtefactCreationActivator.context = null;
  }

  public static ArtefactCreationActivator getDefault() {
    return __plugin;
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

  public void setOrchestraExplorerServices(IOrchestraExplorerServices services_p) {
    _services = services_p;
  }

  public IOrchestraExplorerServices getOrchestraExplorerServices() {
    return _services;
  }
}
