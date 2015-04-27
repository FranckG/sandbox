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
package com.thalesgroup.orchestra.framework.application;

import java.io.File;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import com.thalesgroup.orchestra.framework.FrameworkActivator;
import com.thalesgroup.orchestra.framework.application.FrameworkConsoleView.LogLevel;
import com.thalesgroup.orchestra.framework.common.CommonActivator;
import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.common.helper.MarkerHelper;
import com.thalesgroup.orchestra.framework.common.helper.ProjectHelper;
import com.thalesgroup.orchestra.framework.config.ConfDirHelper;
import com.thalesgroup.orchestra.framework.lib.helper.ConnectionHelper;
import com.thalesgroup.orchestra.framework.model.ModelActivator;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.data.DataHandler;
import com.thalesgroup.orchestra.framework.model.handler.data.DataHandler.InvalidContextException;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.model.handler.data.ICurrentContextChangeListener;
import com.thalesgroup.orchestra.framework.model.handler.data.SharedDataUtil;
import com.thalesgroup.orchestra.framework.model.validation.LiveValidationContentAdapter;
import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;
import com.thalesgroup.orchestra.framework.server.ServerManager;
import com.thalesgroup.orchestra.framework.startup.command.StartupCommandHandler;
import com.thalesgroup.orchestra.framework.transcription.TranscriptionHelper;

/**
 * This class controls all aspects of the application's execution
 */
/**
 * <p>
 * Title : PapeeteApplication
 * </p>
 * <p>
 * Description : Papeete Application
 * </p>
 * @author Papeete Tool Suite developer
 * @version 3.7.0
 */
public class FrameworkApplication implements IApplication, ICurrentContextChangeListener {
  /**
   * Initial context setting errors.
   */
  private IStatus _initialContextErrors;
  /**
   * Is application currently starting ?
   */
  private boolean _isStarting;
  /**
   * Server manager instance.
   */
  private ServerManager _manager;

  /**
   * @see com.thalesgroup.orchestra.framework.model.handler.data.ICurrentContextChangeListener#contextChanged(com.thalesgroup.orchestra.framework.model.contexts.Context,
   *      org.eclipse.core.runtime.IStatus, org.eclipse.core.runtime.IProgressMonitor, boolean)
   */
  public IStatus contextChanged(Context currentContext_p, IStatus errorStatus_p, IProgressMonitor progressMonitor_p, boolean allowUserInteractions_p) {
    // Merge configuration directories.
    List<String> sources = ConfDirHelper.getConfigurationDirectoriesAbsolutePaths(currentContext_p);
    AbstractVariable variable = DataUtil.getVariable(DataUtil.__CONFIGURATIONDIRECTORY_VARIABLE_NAME, currentContext_p);
    VariableValue destination = DataUtil.getValues(variable, currentContext_p).get(0);
    // Progress reporting.
    if (null != progressMonitor_p) {
      progressMonitor_p.setTaskName(Messages.FrameworkApplication_SwitchContextMessage);
    }
    ConfDirHelper.mergeConfDir(sources, destination);
    // Then initialize/reinitialize transcription helper (for associations).
    TranscriptionHelper.getInstance().init(destination.getValue());
    // Re-initialize manager.
    _manager.contextSwitched();
    // Retain existing error.
    if (_isStarting) {
      _initialContextErrors = errorStatus_p;
    } else if (null != _initialContextErrors) {
      _initialContextErrors = null;
    }

    return Status.OK_STATUS;
  }

  /**
   * Get contexts from pool (contexts with same path in workspace or same name are excluded). <br>
   * If no contexts pool location is specified <code>null</code> is returned.
   */
  protected List<RootContextsProject> getContextsPoolList(String contextPoolLocation_p, List<String> excludedDirectories_p, int maxDepth_p) {
    // Preconditions.
    if (null == contextPoolLocation_p || ICommonConstants.EMPTY_STRING.equals(contextPoolLocation_p)) {
      // No contexts pool location given -> return null.
      return null;
    }
    List<RootContextsProject> adminContextsInPool =
        ProjectActivator.getInstance().getProjectHandler().getValidContextsFromLocation(contextPoolLocation_p, excludedDirectories_p, maxDepth_p);
    if (adminContextsInPool.isEmpty()) {
      // Given contexts pool location is empty -> return an empty list.
      return Collections.emptyList();
    }
    // Generate contexts pool list.
    List<RootContextsProject> contextsInPool = new ArrayList<RootContextsProject>(adminContextsInPool);
    // Remove conflicting contexts.
    Set<RootContextsProject> conflictingContexts = ProjectActivator.getInstance().getProjectHandler().getConflictingContexts(adminContextsInPool);
    contextsInPool.removeAll(conflictingContexts);
    // Show excluded contexts in log.
    if (!conflictingContexts.isEmpty()) {
      MultiStatus logStatus =
          new MultiStatus(FrameworkActivator.getDefault().getPluginId(), 0,
              "The following contexts, found in the contexts pool, have been ignored (common structures or names):", null); //$NON-NLS-1$
      for (RootContextsProject conflictingContext : conflictingContexts) {
        String conflictingContextTrace =
            "Path=" + conflictingContext._description.getLocationURI().getPath() + " Name=" + conflictingContext.getAdministratedContext().getName(); //$NON-NLS-1$ //$NON-NLS-2$
        logStatus.add(new Status(IStatus.WARNING, FrameworkActivator.getDefault().getPluginId(), conflictingContextTrace));
      }
      FrameworkActivator.getDefault().getLog().log(logStatus);
    }
    return contextsInPool;
  }

  /**
   * Initialize the Orchestra Framework.<br />
   * It ensure the current environment is up and properly configured.
   */
  public void init() {
    _manager = new ServerManager();
    try {
      _manager.initialize();
    } catch (Throwable ex) {
      FrameworkActivator.getDefault().log(
          new Status(IStatus.ERROR, FrameworkActivator.getDefault().getPluginId(), Messages.OrchestraFrameworkApplication_Message_FailedToStart, ex),
          LogLevel.ERROR);
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.handler.data.ICurrentContextChangeListener#preContextChange(com.thalesgroup.orchestra.framework.model.contexts.Context,
   *      org.eclipse.core.runtime.IProgressMonitor, boolean)
   */
  public void preContextChange(Context futurCurrentContext_p, IProgressMonitor progressMonitor_p, boolean allowUserInteractions_p) {
    // Try and create configuration directory
    try {
      AbstractVariable variable = DataUtil.getVariable(DataUtil.__CONFIGURATIONDIRECTORY_VARIABLE_NAME, futurCurrentContext_p);
      VariableValue destination = DataUtil.getValues(variable, futurCurrentContext_p).get(0);
      File directory = new File(destination.getValue());
      if (!directory.exists()) {
        directory.mkdirs();
      }
    } catch (Exception exception_p) {
      // Could not create it, let the validation phase detect it.
    }
  }

  protected void initializeOrchestraDoctor() {
    // Copy Orchestra Doctor path to APPDATA.
    {
      // Get application data path.
      String appData = System.getenv("APPDATA"); //$NON-NLS-1$
      // Precondition.
      if ((null != appData) && !appData.trim().isEmpty()) {
        // Doctor path file relative path.
        String doctorPathRelativePath = "lib/OrchestraDoctor.path"; //$NON-NLS-1$
        // Try and get platform installation path.
        URL platformInstallationURL = Platform.getInstallLocation().getURL();
        try {
          // Remove 'eclipse' last segment before adding OD path relative path.
          IPath sourceFilePath = new Path(new File(platformInstallationURL.getPath()).getAbsolutePath()).removeLastSegments(1).append(doctorPathRelativePath);
          IPath destinationPath = new Path(appData).append("Orchestra/OrchestraFramework").append(doctorPathRelativePath).removeLastSegments(1); //$NON-NLS-1$
          FileUtils.copyFileToDirectory(sourceFilePath.toFile(), destinationPath.toFile());
        } catch (Exception exception_p) {
          // Too bad.
        }
      }
    }
    // Delete installation configuration file, if any.
    {
      File file = new File(ModelUtil.getInstallationConfigurationFilePath());
      if (file.exists()) {
        boolean deleted = file.delete();
        if (!deleted) {
          // If such projects are found, log them.
          FrameworkActivator.getDefault().log(
              new Status(IStatus.WARNING, FrameworkActivator.getDefault().getPluginId(), MessageFormat.format(
                  Messages.FrameworkApplication_Warning_InstallationFetchedFile_CouldNotBeDeleted, file)), null);
        }
      }
    }
  }

  /**
   * Pre-Start hook.
   */
  protected void preStart() {
    // Deal with Orchestra Doctor issues.
    initializeOrchestraDoctor();
    // Delete no more accessible projects (projects loaded in the workspace but which have their directory deleted or moved).
    IStatus deletionStatus = ProjectHelper.handleUnaccessibleProjectsFromWorkspace();
    if (!deletionStatus.isOK()) {
      // If such projects are found, log them.
      FrameworkActivator.getDefault().log(deletionStatus, null);
    }
    // Set shared DataUtil implementation to use.
    ModelActivator.getInstance().setDataUtil(new SharedDataUtil());
    // Initialize environment registry.
    FrameworkActivator.getDefault().initializeEnvironmentRegistry();
    // Register live validation.
    ModelHandlerActivator.getDefault().getEditingDomain().getNotificationsListener().addAdapter(LiveValidationContentAdapter.getInstance());
    // Clean all validation markers.
    MarkerHelper.deleteValidationMarkers();
    // Force validation instantiation.
    ModelHandlerActivator.getDefault().getValidationHandler();
  }

  /**
   * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.IApplicationContext)
   */
  public Object start(IApplicationContext context) throws Exception {
    // Create display for main thread.
    Display display = PlatformUI.createDisplay();
    // Command line arguments management.
    Object argsObject = context.getArguments().get(IApplicationContext.APPLICATION_ARGS);
    FrameworkCommandLineArgsHandler commandArgsHandler = new FrameworkCommandLineArgsHandler();
    if (argsObject instanceof String[]) {
      String[] commandLineArgs = (String[]) argsObject;
      IStatus initStatus = commandArgsHandler.initializeHandler(commandLineArgs);
      if (!initStatus.isOK()) {
        CommandLineErrorDialog commandLineErrorDialog =
            new CommandLineErrorDialog(display.getActiveShell(), initStatus, commandArgsHandler.getCommandLineUsageNote());
        commandLineErrorDialog.open();
        return IApplication.EXIT_OK;
      }
    }
    // Make the handler accessible for other bundles.
    ProjectActivator.getInstance().setCommandLineArgumentsHandler(commandArgsHandler);
    // Set start flag to true.
    _isStarting = true;
    // Register shutdown hook, in case of VM termination.
    Runtime.getRuntime().addShutdownHook(new Thread() {
      /**
       * @see java.lang.Thread#run()
       */
      @Override
      public void run() {
        saveWorkspace();
      }
    });

    // Precondition.
    if (ConnectionHelper.ping()) {
      MessageDialog.openError(display.getActiveShell(), Messages.PapeeteApplication_ApplicationEarlyClose_Title,
          Messages.PapeeteApplication_ApplicationEarlyClose_Message);
      return IApplication.EXIT_OK;
    }
    // Pre-start hook.
    preStart();

    // Check that Orchestra Doctor has been installed.
    // Exit the application if not.
    IStatus doctorStatus = ModelUtil.getOrchestraDoctorExecutablePath();
    if (!doctorStatus.isOK()) {
      MessageDialog.openError(display.getActiveShell(), Messages.PapeeteApplication_ApplicationEarlyClose_Title, doctorStatus.getMessage());
      return IApplication.EXIT_OK;
    }

    DataHandler dataHandler = ModelHandlerActivator.getDefault().getDataHandler();
    // Register framework implementations.
    dataHandler.addCurrentContextChangeListener(FrameworkActivator.getDefault().getEnvironmentsRegistry());
    // Register as context listener.
    dataHandler.addCurrentContextChangeListener(this);
    // Initialize contexts pool list.
    List<RootContextsProject> contextsInPool =
        getContextsPoolList(commandArgsHandler.getAdminContextsPoolLocation(), commandArgsHandler.getExcludeDirectories(), commandArgsHandler.getMaxDepth());
    // Auto join.
    Context autoJoinUserContext = null;
    String adminContextToAutoJoin = commandArgsHandler.getAdminContextToAutoJoin();
    if (adminContextToAutoJoin != null && !ICommonConstants.EMPTY_STRING.equals(adminContextToAutoJoin)) {
      // Find admin context to auto join.
      RootContextsProject foundContext = null;
      for (RootContextsProject contextInPool : contextsInPool) {
        if (adminContextToAutoJoin.equals(contextInPool.getAdministratedContext().getName())) {
          foundContext = contextInPool;
          break;
        }
      }
      if (null == foundContext) {
        // Specified context not found.
        MessageDialog.openError(display.getActiveShell(), Messages.FrameworkApplication_Dialog_Title_AutoJoinError,
            MessageFormat.format(Messages.FrameworkApplication_Dialog_Message_AutoJoinError, adminContextToAutoJoin));
      } else {
        IStatus participateStatus =
            ModelHandlerActivator.getDefault().getDataHandler().participateInAProject(foundContext.getAdministratedContext().getName(), foundContext);
        if (participateStatus.isOK()) {
          // User context has been successfully created/imported -> keep it to set it as current.
          autoJoinUserContext = ModelHandlerActivator.getDefault().getDataHandler().getContext(foundContext.getContext());
        } else {
          // Auto join failed !
          ErrorDialog.openError(display.getActiveShell(), Messages.FrameworkApplication_Dialog_Title_AutoJoinError, null, participateStatus);
        }
      }
    }
    // Admin context to set as current context
    Context adminContextToSet = null;
    String setCurrentContext = commandArgsHandler.getSetCurrentContext();
    if (setCurrentContext != null && !ICommonConstants.EMPTY_STRING.equals(setCurrentContext)) {
      List<RootContextsProject> adminContexts = ProjectActivator.getInstance().getProjectHandler().getValidContextsFromLocation(setCurrentContext);
      if (0 == adminContexts.size()) {
        MessageDialog.openError(display.getActiveShell(), Messages.FrameworkApplication_Dialog_Title_SetCurrentContextError,
            MessageFormat.format(Messages.FrameworkApplication_ErrorMessage_SetCurrentContext_ContextNotFound, setCurrentContext));
      } else if (1 == adminContexts.size()) {
        IStatus importStatus = ModelHandlerActivator.getDefault().getDataHandler().importContexts(adminContexts, false, false);
        if (importStatus.isOK()) {
          // Get admin context and do not check that user id belongs to administrators
          adminContextToSet = ModelHandlerActivator.getDefault().getDataHandler().getContext(adminContexts.get(0).getContext(false));
        } else {
          ErrorDialog.openError(display.getActiveShell(), Messages.FrameworkApplication_Dialog_Title_SetCurrentContextError, null, importStatus);
        }
      } else {
        MessageDialog.openError(display.getActiveShell(), Messages.FrameworkApplication_Dialog_Title_SetCurrentContextError,
            MessageFormat.format(Messages.FrameworkApplication_ErrorMessage_SetCurrentContext_TooManyContexts, setCurrentContext));
      }
    }

    // Initialize context.
    Context currentContext = null;
    if (null != autoJoinUserContext) {
      // If auto join operation has created/imported an user context, set it as current.
      currentContext = autoJoinUserContext;
    }
    if (null != adminContextToSet) {
      currentContext = adminContextToSet;
    } else {
      try {
        currentContext = dataHandler.getCurrentContext();
      } catch (InvalidContextException ice_p) {
        MessageDialog.openWarning(display.getActiveShell(), Messages.PapeeteApplication_InvalidContext_Title,
            ice_p.getMessage() + Messages.PapeeteApplication_InvalidContext_Message);
        currentContext = dataHandler.getDefaultContext();
        dataHandler.saveCurrentContextPreference(currentContext);
      }
    }
    // Initialize server.
    init();
    // Do set context, so as to benefit from scripts execution.
    // Do not enable pre-unload script for there is no previous context at this point.
    dataHandler.doSetCurrentContext(currentContext, true, DataHandler.SCRIPTS_PRELOAD | DataHandler.SCRIPTS_POSTLOAD, true, null, null);
    // Revert start flag to false.
    _isStarting = false;
    // Launch Startup Commands in background
    StartupCommandHandler.getInstance().launchCommands();
    // Start workbench.
    try {
      FrameworkApplicationWorkbenchAdvisor advisor = new FrameworkApplicationWorkbenchAdvisor(_manager, _initialContextErrors, contextsInPool);
      // Release error status.
      _initialContextErrors = null;
      int returnCode = PlatformUI.createAndRunWorkbench(display, advisor);
      if (returnCode == PlatformUI.RETURN_RESTART) {
        return IApplication.EXIT_RESTART;
      }
      return IApplication.EXIT_OK;
    } finally {
      // Unregister listener.
      dataHandler.removeCurrentContextChangeListener(this);
      // Dispose manager.
      _manager.dispose();
      // Dispose display.
      display.dispose();
      // Save workspace.
      saveWorkspace();
    }
  }

  /**
   * @see org.eclipse.equinox.app.IApplication#stop()
   */
  public void stop() {
    final IWorkbench workbench = PlatformUI.getWorkbench();
    if (workbench == null)
      return;
    final Display display = workbench.getDisplay();
    display.syncExec(new Runnable() {
      public void run() {
        if (!display.isDisposed())
          workbench.close();
      }
    });
  }

  /**
   * Save workspace.
   */
  public static void saveWorkspace() {
    // Save workspace.
    CommonActivator commonActivator = CommonActivator.getInstance();
    try {
      // Log.
      if (null != commonActivator) {
        commonActivator.logMessage("FrameworkApplication.saveWorkspace(..) _ Trying to save workspace...", IStatus.OK, null); //$NON-NLS-1$
      }
      // Try and save.
      IWorkspace workspace = ResourcesPlugin.getWorkspace();
      if (null != workspace) {
        workspace.save(true, null);
      }
      // Log.
      if (null != commonActivator) {
        commonActivator.logMessage("FrameworkApplication.saveWorkspace(..) _ Workspace saved.", IStatus.OK, null); //$NON-NLS-1$
      }
    } catch (IllegalStateException illegalStateException_p) {
      // Log.
      if (null != commonActivator) {
        commonActivator.logMessage(
            "FrameworkApplication.saveWorkspace(..) _ Could not access workspace. Nothing to save.", IStatus.WARNING, illegalStateException_p); //$NON-NLS-1$
      }
    } catch (Exception exception_p) {
      // Log.
      if (null != commonActivator) {
        commonActivator.logMessage("FrameworkApplication.saveWorkspace(..) _ Failed to save workspace.", IStatus.ERROR, exception_p); //$NON-NLS-1$
      }
    }
  }

  /**
   * ErrorDialog to display if an error occurs when processing command line arguments.
   * @author T0052089
   */
  public static class CommandLineErrorDialog extends MessageDialog {
    /**
     * Command line usage note, displayed in the error dialog.
     */
    private String _commandLineUsageNote;

    /**
     * @param parentShell_p
     * @param commandLineProcessingStatus_p
     * @param commandLineUsageNote_p
     */
    public CommandLineErrorDialog(Shell parentShell_p, IStatus commandLineProcessingStatus_p, String commandLineUsageNote_p) {
      super(parentShell_p, Messages.PapeeteApplication_ApplicationEarlyClose_Title, null, Messages.FrameworkApplication_CommandLineErrorDialog_Message
                                                                                          + commandLineProcessingStatus_p.getMessage(), MessageDialog.ERROR,
            new String[] { IDialogConstants.OK_LABEL }, 0);
      _commandLineUsageNote = commandLineUsageNote_p;
    }

    /**
     * @see org.eclipse.jface.dialogs.MessageDialog#createCustomArea(org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected Control createCustomArea(Composite parent_p) {
      // Display command line usage.
      StyledText styledText = FormHelper.createStyledText(null, parent_p, _commandLineUsageNote, SWT.READ_ONLY);
      GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
      layoutData.verticalIndent = 15;
      styledText.setLayoutData(layoutData);
      return styledText;
    }
  }
}