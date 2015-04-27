/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.application;

import java.io.File;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.thalesgroup.orchestra.framework.FrameworkActivator;
import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.project.ICommandLineArgsHandler;

/**
 * This class processes the command line arguments.
 * @author T0052089
 */
public class FrameworkCommandLineArgsHandler implements ICommandLineArgsHandler {
  /**
   * AdminContextsPoolLocation argument, must be followed by an existing directory path (can be an UNC path), only available in user only mode.
   */
  public static final String ADMIN_CONTEXTS_POOL_LOCATION = "-adminContextsPoolLocation"; //$NON-NLS-1$
  /**
   * AutoJoin argument, automatically join an admin context at startup.
   */
  public static final String AUTO_JOIN = "-autoJoin"; //$NON-NLS-1$
  /**
   * Baselines repository location on File System. Expected value is a folder absolute path where to store/access baselines (and associated catalog).<br>
   * Path can be an UNC one if required.<br>
   * If not provided, baseline creation capability will not be accessible.
   */
  public static final String BASELINE_CONTEXTS_REPOSITORY_LOCATION = "-baselinesRepositoryLocation"; //$NON-NLS-1$
  /**
   * Minimize argument, starts ODM in iconified state.
   */
  public static final String MINIMIZE = "-minimize"; //$NON-NLS-1$
  /**
   * UserOnly argument, the admin mode is hidden, only the user mode is available.
   */
  public static final String USER_MODE_ONLY = "-userOnly"; //$NON-NLS-1$ 
  /**
   * ExcludeDirectories argument, exclude directories from context discovery
   */
  public static final String EXCLUDE_DIRECTORIES = "-excludeDirectories"; //$NON-NLS-1$
  /**
   * MaxDepth argument, max depth level of directory search in context discovery
   */
  public static final String MAX_DEPTH = "-maxDepth";
  /**
   * SetCurrentContext argument, path to admin context to set as current context
   */
  public static final String SET_CURRENT_CONTEXT = "-setCurrentContext";
  /**
   * AdminContextsPoolLocation path.
   */
  private String _adminContextsPoolLocation;
  /**
   * Admin context name to auto participate to (admin context is taken from the contexts pool).
   */
  private String _adminContextToAutoJoin;
  /**
   * Baselines repository absolute path.
   */
  private String _baselinesRepositoryPath;
  /**
   * Is -minimize specified ?
   */
  private boolean _minimize;
  /**
   * Is -userOnly specified ?
   */
  private boolean _userModeOnly;
  /**
   * List of directories to exclude from context discovery
   */
  private List<String> _excludeDirectories;
  /**
   * Max level depth of context discovery
   */
  private int _maxDepth;
  /**
   * Admin context to import and to set as current context
   */
  private String _setCurrentContext;

  /**
   * Constructor. Define default behavior.
   */
  public FrameworkCommandLineArgsHandler() {
    _minimize = false;
    _userModeOnly = false;
    _adminContextsPoolLocation = ICommonConstants.EMPTY_STRING;
    _adminContextToAutoJoin = ICommonConstants.EMPTY_STRING;
    _baselinesRepositoryPath = ICommonConstants.EMPTY_STRING;
    _excludeDirectories = null;
    _maxDepth = Integer.MAX_VALUE;
    _setCurrentContext = ICommonConstants.EMPTY_STRING;
  }

  /**
   * @return the adminContextsPoolLocation
   */
  public String getAdminContextsPoolLocation() {
    return _adminContextsPoolLocation;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.project.ICommandLineArgsHandler#getAdminContextToAutoJoin()
   */
  public String getAdminContextToAutoJoin() {
    return _adminContextToAutoJoin;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.project.ICommandLineArgsHandler#getBaselinesRepositoryPath()
   */
  public String getBaselinesRepositoryPath() {
    return _baselinesRepositoryPath;
  }

  /**
   * @return the excludeDirectories option value
   */
  public List<String> getExcludeDirectories() {
    return _excludeDirectories;
  }

  /**
   * @return the max depth option value
   */
  public int getMaxDepth() {
    return _maxDepth;
  }

  /**
   * @return Path to current context to set
   */
  public String getSetCurrentContext() {
    return _setCurrentContext;
  }

  /**
   * Get command line arguments usage note.
   * @return
   */
  public String getCommandLineUsageNote() {
    return Messages.FrameworkCommandArgsHandler_CommandLineUsageNote;
  }

  /**
   * Parses given arguments to initialize the attributes. Object's attributes are kept untouched if an error occurred.
   * @param commandLineArgs_p
   * @return An OK status if the given arguments are valid, an ERROR status else.
   */
  public IStatus initializeHandler(String[] commandLineArgs_p) {
    // No command line arguments -> keep default behavior.
    if (0 == commandLineArgs_p.length) {
      return Status.OK_STATUS;
    }
    List<String> commandLineArgsList = Arrays.asList(commandLineArgs_p);
    // -minimize argument.
    _minimize = commandLineArgsList.contains(MINIMIZE);
    // -userOnly argument.
    _userModeOnly = commandLineArgsList.contains(USER_MODE_ONLY);
    // -adminContextsPoolLocation argument.
    int adminContextsPoolLocationArgIndex = commandLineArgsList.indexOf(ADMIN_CONTEXTS_POOL_LOCATION);
    if (-1 != adminContextsPoolLocationArgIndex) {
      // -adminContextsPoolLocation can be used ONLY if -userOnly is specified.
      if (!_userModeOnly) {
        return new Status(IStatus.ERROR, FrameworkActivator.getDefault().getPluginId(), MessageFormat.format(
            Messages.FrameworkCommandArgsHandler_ErrorMessage_AdminContextsLocationAvailableOnlyWithUserOnly, ADMIN_CONTEXTS_POOL_LOCATION, USER_MODE_ONLY));
      }
      int adminContextPoolLocationArgValueIndex = adminContextsPoolLocationArgIndex + 1;
      // Check a path is specified after the argument.
      if (adminContextPoolLocationArgValueIndex >= commandLineArgsList.size()) {
        return new Status(IStatus.ERROR, FrameworkActivator.getDefault().getPluginId(), MessageFormat.format(
            Messages.FrameworkCommandArgsHandler_ErrorMessage_InvalidLocation, ADMIN_CONTEXTS_POOL_LOCATION));
      }
      _adminContextsPoolLocation = commandLineArgsList.get(adminContextPoolLocationArgValueIndex);
      // Check a valid directory path is specified after the argument.
      if (!new File(_adminContextsPoolLocation).isDirectory()) {
        _adminContextsPoolLocation = ICommonConstants.EMPTY_STRING;
        return new Status(IStatus.ERROR, FrameworkActivator.getDefault().getPluginId(), MessageFormat.format(
            Messages.FrameworkCommandArgsHandler_ErrorMessage_InvalidLocation, ADMIN_CONTEXTS_POOL_LOCATION));
      }
    }
    // -autoJoin argument.
    int autoJoinArgIndex = commandLineArgsList.indexOf(AUTO_JOIN);
    if (-1 != autoJoinArgIndex) {
      // -autoJoin can be used ONLY if a contexts pool is given.
      if (ICommonConstants.EMPTY_STRING.equals(_adminContextsPoolLocation)) {
        return new Status(IStatus.ERROR, FrameworkActivator.getDefault().getPluginId(), MessageFormat.format(
            Messages.FrameworkCommandLineArgsHandler_ErrorMessage_OptionAvailableOnlyWithContextsPool, AUTO_JOIN));
      }
      int autoJoinArgValueIndex = autoJoinArgIndex + 1;
      // Check a context name is specified after the argument.
      if (autoJoinArgValueIndex >= commandLineArgsList.size()) {
        return new Status(IStatus.ERROR, FrameworkActivator.getDefault().getPluginId(), MessageFormat.format(
            Messages.FrameworkCommandLineArgsHandler_ErrorMessage_AutoJoinContextNameMissing, AUTO_JOIN));
      }
      // Validate given context name ?
      _adminContextToAutoJoin = commandLineArgsList.get(autoJoinArgValueIndex);
    }
    // Baselines repository location argument.
    int baselineArgumentIndex = commandLineArgsList.indexOf(BASELINE_CONTEXTS_REPOSITORY_LOCATION);
    if (-1 != baselineArgumentIndex) {
      int baselinesRepositoryValueIndex = baselineArgumentIndex + 1;
      // Make sure there is a value.
      if (baselinesRepositoryValueIndex >= commandLineArgsList.size()) {
        return new Status(IStatus.ERROR, FrameworkActivator.getDefault().getPluginId(), MessageFormat.format(
            Messages.FrameworkCommandArgsHandler_ErrorMessage_InvalidLocation, BASELINE_CONTEXTS_REPOSITORY_LOCATION));
      }
      // Get and check value.
      _baselinesRepositoryPath = commandLineArgsList.get(baselinesRepositoryValueIndex);
      if (!new File(_baselinesRepositoryPath).isDirectory()) {
        _baselinesRepositoryPath = ICommonConstants.EMPTY_STRING;
        return new Status(IStatus.ERROR, FrameworkActivator.getDefault().getPluginId(), MessageFormat.format(
            Messages.FrameworkCommandArgsHandler_ErrorMessage_InvalidLocation, BASELINE_CONTEXTS_REPOSITORY_LOCATION));
      }
    }
    // Exclude directory argument
    int excludeDirectoriesArgIndex = commandLineArgsList.indexOf(EXCLUDE_DIRECTORIES);
    if (-1 != excludeDirectoriesArgIndex) {
      // -autoJoin can be used ONLY if a contexts pool is given.
      if (ICommonConstants.EMPTY_STRING.equals(_adminContextsPoolLocation)) {
        return new Status(IStatus.ERROR, FrameworkActivator.getDefault().getPluginId(), MessageFormat.format(
            Messages.FrameworkCommandLineArgsHandler_ErrorMessage_OptionAvailableOnlyWithContextsPool, EXCLUDE_DIRECTORIES));
      }
      int excludeDirectoriesArgValueIndex = excludeDirectoriesArgIndex + 1;
      if (excludeDirectoriesArgValueIndex >= commandLineArgsList.size()) {
        return new Status(IStatus.ERROR, FrameworkActivator.getDefault().getPluginId(), MessageFormat.format(
            Messages.FrameworkCommandLineArgsHandler_ErrorMessage_ExcludeDirectories_DirectoriesMissing, EXCLUDE_DIRECTORIES));
      }
      // Excluded directories are separated by commas
      String excludeDirectories = commandLineArgsList.get(excludeDirectoriesArgValueIndex);
      String[] directories = excludeDirectories.split(",");
      _excludeDirectories = Arrays.asList(directories);
    }

    // Max depth argument
    int maxDepthArgIndex = commandLineArgsList.indexOf(MAX_DEPTH);
    if (-1 != maxDepthArgIndex) {
      // -maxDepth can be used ONLY if a contexts pool is given.
      if (ICommonConstants.EMPTY_STRING.equals(_adminContextsPoolLocation)) {
        return new Status(IStatus.ERROR, FrameworkActivator.getDefault().getPluginId(), MessageFormat.format(
            Messages.FrameworkCommandLineArgsHandler_ErrorMessage_OptionAvailableOnlyWithContextsPool, MAX_DEPTH));
      }
      int maxDepthArgValueIndex = maxDepthArgIndex + 1;
      if (maxDepthArgValueIndex >= commandLineArgsList.size()) {
        return new Status(IStatus.ERROR, FrameworkActivator.getDefault().getPluginId(), MessageFormat.format(
            Messages.FrameworkCommandLineArgsHandler_ErrorMessage_MaxDepth_DepthValueMissing, MAX_DEPTH));
      }
      String maxDepth = commandLineArgsList.get(maxDepthArgValueIndex);
      try {
        _maxDepth = Integer.parseInt(maxDepth);
        if (_maxDepth < 0) {
          return new Status(IStatus.ERROR, FrameworkActivator.getDefault().getPluginId(), MessageFormat.format(
              Messages.FrameworkCommandLineArgsHandler_ErrorMessage_MaxDepth_WrongValue, MAX_DEPTH));
        }
      } catch (NumberFormatException exception) {
        return new Status(IStatus.ERROR, FrameworkActivator.getDefault().getPluginId(), MessageFormat.format(
            Messages.FrameworkCommandLineArgsHandler_ErrorMessage_MaxDepth_WrongValue, MAX_DEPTH));
      }
    }
    int setCurrentContextArgIndex = commandLineArgsList.indexOf(SET_CURRENT_CONTEXT);
    if (-1 != setCurrentContextArgIndex) {
      // -setCurrentContext is not compatible with -userOnly
      if (_userModeOnly) {
        return new Status(IStatus.ERROR, FrameworkActivator.getDefault().getPluginId(), MessageFormat.format(
            Messages.FrameworkCommandLineArgsHandler_ErrorMessage_SetCurrentContextIncompatibleWithUserOnly, SET_CURRENT_CONTEXT, USER_MODE_ONLY));
      }
      int setCurrentContextArgValueIndex = setCurrentContextArgIndex + 1;
      if (setCurrentContextArgValueIndex >= commandLineArgsList.size()) {
        // TODO message d'erreur
        return new Status(IStatus.ERROR, FrameworkActivator.getDefault().getPluginId(), MessageFormat.format(
            Messages.FrameworkCommandLineArgsHandler_ErrorMessage_SetCurrentContext_MissingValue, MAX_DEPTH));
      }
      _setCurrentContext = commandLineArgsList.get(setCurrentContextArgValueIndex);
    }
    return Status.OK_STATUS;
  }

  /**
   * @return the minimize
   */
  public boolean minimizeAsked() {
    return _minimize;
  }

  /**
   * @return the userOnlyMode
   */
  public boolean userModeOnly() {
    return _userModeOnly;
  }
}
