package com.thalesgroup.orchestra.framework.project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.osgi.framework.BundleContext;
import org.osgi.service.prefs.BackingStoreException;

import com.thalesgroup.orchestra.framework.common.CommonActivator;
import com.thalesgroup.orchestra.framework.project.mode.IAdministratorModeListener;
import com.thalesgroup.orchestra.framework.root.ui.activator.AbstractUIActivator;

public class ProjectActivator extends AbstractUIActivator {
  /**
   * Shared instance.
   */
  private static ProjectActivator __instance;
  private static final boolean ADMINISTRATOR_DEFAULT_PREFERENCE_KEY = true;
  /**
   * The key used to store the administrator mode in preferences.
   */
  private static final String ADMINISTRATOR_PREFERENCE_KEY = "administrator"; //$NON-NLS-1$
  /**
   * Command line arguments handler.
   */
  private ICommandLineArgsHandler _commandLineArgumentsHandler;

  private ProjectsEditingDomain _editingDomain;

  private List<IAdministratorModeListener> _listeners;

  private ProjectHandler _projectHandler;

  public ProjectActivator() {
    __instance = this;
  }

  public boolean addAdministratorModeChangeListener(IAdministratorModeListener listener_p) {
    return _listeners.add(listener_p);
  }

  /**
   * Change the administrator mode. Alert listeners that the state has been changed.
   * @param administrator_p the administrator to set
   */
  public boolean changeAdministratorMode() {
    boolean administrator = !isAdministrator();
    IEclipsePreferences node = new InstanceScope().getNode(getPluginId());
    node.putBoolean(ADMINISTRATOR_PREFERENCE_KEY, administrator);
    // Flush right now for further preference key readings.
    try {
      node.flush();
      notifyListeners(administrator);
      return true;
    } catch (BackingStoreException exception_p) {
      CommonActivator.getInstance().logMessage("Saving preferences failed while persisting the administrative mode.", IStatus.WARNING, exception_p); //$NON-NLS-1$
      return false;
    }
  }

  /**
   * Returns the command line arguments handler. If no handler was set, a default handler is returned.
   * @return a not <code>null</code> handler.
   */
  public ICommandLineArgsHandler getCommandLineArgsHandler() {
    if (null == _commandLineArgumentsHandler) {
      _commandLineArgumentsHandler = new DefautCommandLineArgsHandler();
    }
    return _commandLineArgumentsHandler;
  }

  /**
   * Get current user id.<br>
   * This has nothing to do with being administrator or not.
   * @return
   */
  public String getCurrentUserId() {
    return System.getProperty("user.name"); //$NON-NLS-1$
  }

  /**
   * Get contexts projects editing domain.
   * @return
   */
  public ProjectsEditingDomain getEditingDomain() {
    if (null == _editingDomain) {
      _editingDomain = new ProjectsEditingDomain(new AdapterFactoryImpl());
    }
    return _editingDomain;
  }

  /**
   * Get project handler unique instance.
   * @return A not <code>null</code> instance of {@link ProjectHandler}.
   */
  public ProjectHandler getProjectHandler() {
    if (null == _projectHandler) {
      _projectHandler = new ProjectHandler();
    }
    return _projectHandler;
  }

  /**
   * Consult the administrator mode.
   * @return the administrator mode.
   */
  public boolean isAdministrator() {
    // Forced to false if in user mode only.
    return !getCommandLineArgsHandler().userModeOnly()
           && Platform.getPreferencesService().getBoolean(getPluginId(), ADMINISTRATOR_PREFERENCE_KEY, ADMINISTRATOR_DEFAULT_PREFERENCE_KEY, null);
  }

  private void notifyListeners(boolean administrator_p) {
    List<IAdministratorModeListener> listeners = Collections.unmodifiableList(_listeners);
    for (IAdministratorModeListener iAdministratorModeListener : listeners) {
      iAdministratorModeListener.modeChanged(administrator_p);
    }
  }

  public boolean removeAdministratorModeListener(IAdministratorModeListener listener_p) {
    return _listeners.remove(listener_p);
  }

  /**
   * Set command line arguments handler.
   * @param commandLineArgumentsHandler_p
   */
  public void setCommandLineArgumentsHandler(ICommandLineArgsHandler commandLineArgumentsHandler_p) {
    _commandLineArgumentsHandler = commandLineArgumentsHandler_p;
  }

  /**
   * @see org.eclipse.core.runtime.Plugin#start(org.osgi.framework.BundleContext)
   */
  @Override
  public void start(BundleContext context_p) throws Exception {
    __instance = this;
    _listeners = new ArrayList<IAdministratorModeListener>();
    super.start(context_p);
  }

  /**
   * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
   */
  @Override
  public void stop(BundleContext context_p) throws Exception {
    super.stop(context_p);
    __instance = null;
    _listeners.clear();
  }

  /**
   * Get shared instance.
   * @return
   */
  public static ProjectActivator getInstance() {
    return __instance;
  }
}
