/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.server;

import java.io.File;
import java.io.FilenameFilter;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Comparator;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.PlatformUI;

import com.thalesgroup.orchestra.framework.FrameworkActivator;
import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.config.ConfigServer;
import com.thalesgroup.orchestra.framework.environment.EnvironmentActivator;
import com.thalesgroup.orchestra.framework.environment.EnvironmentVariables;
import com.thalesgroup.orchestra.framework.environment.core.EnvironmentVariableLoader;
import com.thalesgroup.orchestra.framework.internal.connector.StatusModelHelper;
import com.thalesgroup.orchestra.framework.lib.base.UtilFunction;
import com.thalesgroup.orchestra.framework.lib.base.conf.ServerConfParam;
import com.thalesgroup.orchestra.framework.lib.helper.ConnectionHelper;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.transcription.TranscriptionHelper;
import com.thalesgroup.orchestra.framework.ui.wizard.custom.CustomVariableEditButtonRegistry;

/**
 * Server initialization and states manager.
 * @author t0076261
 */
public class ServerManager {
  /**
   * Is HTTP server started ?
   */
  private boolean _isHTTPServerStarted;

  /**
   * Check configuration.
   * @param message
   */
  private IStatus checkConfigurationFiles() {
    MultiStatus result = new MultiStatus(FrameworkActivator.getDefault().getPluginId(), 0, Messages.ServerManager_CheckConfigurationFiles, null);
    String serverConfParam = EnvironmentVariables.GetConfigServerFilePathName();
    if (!new File(serverConfParam).exists()) {
      result.add(new Status(IStatus.ERROR, FrameworkActivator.getDefault().getPluginId(), MessageFormat.format(Messages.ServerManager_RequiredFileIsMissing,
          serverConfParam)));
    } else {
      result.add(new Status(IStatus.OK, FrameworkActivator.getDefault().getPluginId(), MessageFormat.format(Messages.ServerManager_RequiredFileIsFound,
          serverConfParam)));
    }
    return result;
  }

  /**
   * Initialize shared directory at startup
   */
  public void initSharedDirectory() {
    // Get custom shared directory prefix
    final String customSharedDirSegment = UtilFunction.getCustomSharedDirSegment();

    // Get list of available shared directories that start with the custom shared directory prefix
    File sharedDirFile = new File(EnvironmentVariables.GetSharedDir());
    final File[] availableFoldersList = sharedDirFile.listFiles(new FilenameFilter() {
      public boolean accept(File directory_p, String file_p) {
        if (file_p.startsWith(customSharedDirSegment) && new File(directory_p, file_p).isDirectory()) {
          return true;
        }
        return false;
      }
    });

    // When no shared directory is available, create a temporary one
    if (null == availableFoldersList || 0 == availableFoldersList.length) {
      File folderToCreate = new File(sharedDirFile, customSharedDirSegment + "-0");
      String customSharedDir = folderToCreate.getName();
      // Save custom shared directory name to environment variables
      EnvironmentVariables.SetCustomSharecDir(customSharedDir);

      // Generate custom host/user shared dir path.
      String customSharedDirPath =
          UtilFunction.GetString(new String[] { EnvironmentVariables.GetSharedDir(), File.separator, customSharedDir }, ICommonConstants.EMPTY_STRING);
      File customSharedDirFile = new File(customSharedDirPath);
      customSharedDirFile.mkdirs();
    } else {
      // Sort available folder list in the last modification reverse order
      Arrays.sort(availableFoldersList, new Comparator<File>() {
        public int compare(File o1_p, File o2_p) {
          long last1 = o1_p.lastModified();
          long last2 = o2_p.lastModified();
          if (last1 < last2) {
            return 1;
          } else if (last1 > last2) {
            return -1;
          }
          return 0;
        }
      });
      // Select last used available shared directories
      EnvironmentVariables.SetCustomSharecDir(availableFoldersList[0].getName());
    }
  }

  /**
   * Clear currently in-use shared directory.
   * @return
   */
  protected IStatus clearSharedDirectory() {
    // Get custom shared directory prefix
    final String customSharedDirSegment = UtilFunction.getCustomSharedDirSegment();

    // Get list of directories to delete that start with the custom shared directory prefix
    File sharedDirFile = new File(EnvironmentVariables.GetSharedDir());
    final File[] availableFoldersList = sharedDirFile.listFiles(new FilenameFilter() {
      public boolean accept(File directory_p, String file_p) {
        if (file_p.startsWith(customSharedDirSegment) && new File(directory_p, file_p).isDirectory()) {
          return true;
        }
        return false;
      }
    });

    // Compute unique directory name
    // It is made of custom shared directory prefix + "-" + number
    File folderToCreate = null;
    int suffix = 0;
    boolean folderExists = true;
    while (folderExists) {
      folderToCreate = new File(sharedDirFile, customSharedDirSegment + "-" + suffix);
      folderExists = folderToCreate.exists();
      ++suffix;
    }
    String customSharedDir = folderToCreate.getName();
    // Save custom shared directory name to environment variables
    EnvironmentVariables.SetCustomSharecDir(customSharedDir);

    // Generate custom host/user shared dir path.
    String customSharedDirPath =
        UtilFunction.GetString(new String[] { EnvironmentVariables.GetSharedDir(), File.separator, customSharedDir }, ICommonConstants.EMPTY_STRING);
    File customSharedDirFile = new File(customSharedDirPath);

    // Do deletion.
    // Optimization :
    // -> Create a new folder with previous name.
    // -> Delete previous one(s) asynchronously.
    // -> Make sure deletion is done before exiting the Framework.
    try {
      // Create a new empty one at expected location.
      customSharedDirFile.mkdirs();
      // Restart directory generator from 0.
      StatusModelHelper.resetUniqueDirectoryCounter();

      //
      // Delete previous ones asynchronously if any.
      //

      // Keep previous shared directory: prod000108638
      if (null == availableFoldersList || availableFoldersList.length < 2) {
        return Status.OK_STATUS;
      }

      // Sort available folder list in the last modification order
      Arrays.sort(availableFoldersList, new Comparator<File>() {
        public int compare(File o1_p, File o2_p) {
          long last1 = o1_p.lastModified();
          long last2 = o2_p.lastModified();
          if (last1 < last2) {
            return -1;
          } else if (last1 > last2) {
            return 1;
          }
          return 0;
        }
      });

      // Remove latest modified folder from folders to delete
      int nbFoldersToDelete = availableFoldersList.length - 1;
      final File[] foldersToDelete = new File[nbFoldersToDelete];
      System.arraycopy(availableFoldersList, 0, foldersToDelete, 0, nbFoldersToDelete);

      Job triggerJob = new Job("No name") { //$NON-NLS-1$
            /**
             * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
             */
            @Override
            protected IStatus run(IProgressMonitor monitor_p) {
              while (!PlatformUI.isWorkbenchRunning()) {
                try {
                  Thread.sleep(5000);
                } catch (InterruptedException exception_p) {
                  // Die silently please.
                }
              }

              // Create list of deleted folders as string for the job message
              StringBuilder fileListString = new StringBuilder();
              fileListString.append(foldersToDelete[0].getAbsolutePath());
              for (int i = 1; i < foldersToDelete.length; i++) {
                fileListString.append(", ");
                fileListString.append(foldersToDelete[i].getAbsolutePath());
              }

              Job deletionJob =
                  new FolderDeletionJob(foldersToDelete, MessageFormat.format(Messages.ServerManager_SharedDirectoryDeletion_Job_Message, fileListString));
              deletionJob.schedule();
              return Status.OK_STATUS;
            }
          };
      triggerJob.setSystem(true);
      triggerJob.schedule();
    } catch (Exception exception_p) {
      String errorMessage = MessageFormat.format(Messages.ServerManager_StatusMessage_CustomSharedDirDeletionError, customSharedDirPath);
      return new Status(IStatus.ERROR, FrameworkActivator.getDefault().getPluginId(), errorMessage);
    }
    return Status.OK_STATUS;
  }

  /**
   * Context has been switched.<br>
   * Re-initialize context dependent contents.
   */
  public void contextSwitched() {
    String pluginId = FrameworkActivator.getDefault().getPluginId();
    MultiStatus result = new MultiStatus(pluginId, 0, Messages.ServerManager_ContextSwitching, null);
    String contextName = ModelHandlerActivator.getDefault().getDataHandler().getCurrentContext().getName();
    if (_isHTTPServerStarted) {
      result.add(new Status(IStatus.INFO, pluginId, MessageFormat.format(Messages.ServerManager_ContextSwitchedTo, contextName)));
      EnvironmentVariables.init();
      result.add(writeVariables());
      IStatus clearShareddirResult = clearSharedDirectory();
      if (!clearShareddirResult.isOK()) {
        // If no problem keep it quiet.
        result.add(clearShareddirResult);
      }
      IStatus createTempDirResult = createTemporaryDirectory();
      if (!createTempDirResult.isOK()) {
        // If no problem keep it quiet.
        result.add(createTempDirResult);
      }
      result.add(FrameworkActivator.getDefault().getConnectorRegistry().checkConnectorsAssociationAvailability());
    } else {
      result.add(new Status(IStatus.ERROR, pluginId, MessageFormat.format(Messages.ServerManager_ContextFailedToSwitchTo, contextName)));
    }
    FrameworkActivator.getDefault().log(result, null);
  }

  /**
   * Create the TemporaryDirectory folder when switching context
   * @return OK if the creation was a success, else an error
   */
  protected IStatus createTemporaryDirectory() {
    String tempDirPath = EnvironmentVariables.GetTempDir();
    File tempDir = new File(tempDirPath);
    // If the file does not exist or is not a directory, create it
    if (!tempDir.exists() || !tempDir.isDirectory()) {
      // If the create failed, return an error
      if (!tempDir.mkdir()) {
        String errorMessage = MessageFormat.format(Messages.ServerManager_StatusMessage_TempDirNotCreated, tempDirPath);
        return new Status(IStatus.ERROR, FrameworkActivator.getDefault().getPluginId(), errorMessage);
      }
    }
    return Status.OK_STATUS;
  }

  /**
   * Dispose server manager.
   */
  public void dispose() {
    // TODO Guillaume
  }

  /**
   * Initialize.
   */
  public void initialize() {
    String pluginId = FrameworkActivator.getDefault().getPluginId();
    MultiStatus result = new MultiStatus(pluginId, 0, Messages.ServerManager_Initialization, null);
    EnvironmentVariables.init();
    initSharedDirectory();
    result.add(ConfigServer.getInstance().initialization());
    // Initialize connector registry.
    FrameworkActivator.getDefault().initializeConnectorRegistry();
    result.add(checkConfigurationFiles());
    // Start the Jetty HTTP server and the registry.
    try {
      Platform.getBundle("org.eclipse.equinox.http.jetty").start(); //$NON-NLS-1$
      Platform.getBundle("org.eclipse.equinox.http.registry").start(); //$NON-NLS-1$
      // Make sure server is really started.
      if (!ConnectionHelper.ping()) {
        throw new RuntimeException();
      }
      _isHTTPServerStarted = true;
      result.add(new Status(IStatus.INFO, pluginId, Messages.ServerManager_HttpServerStarted));
      String startMessage =
          MessageFormat
              .format(
                  "{0} Version: {1}, IP: {2}, {3} Port: {4}", Platform.getProduct().getName(), FrameworkActivator.getDefault().getVersion(), ConfigServer.getInstance().GetServerIP(), ConfigServer.getInstance().GetServerPortType(), //$NON-NLS-1$
                  String.valueOf(ConfigServer.getInstance().GetPort()));
      result.add(new Status(IStatus.INFO, pluginId, startMessage));
      // Add system properties for the about dialog (see about.mappings file).
      System.setProperty("FrameworkVersion", FrameworkActivator.getDefault().getVersion()); //$NON-NLS-1$
      System.setProperty("FrameworkPortMode", ConfigServer.getInstance().GetServerPortType()); //$NON-NLS-1$
      System.setProperty("FrameworkEcfLocation", ServerConfParam.getInstance().getEcfServerLocation()); //$NON-NLS-1$
    } catch (Throwable throwable_p) {
      _isHTTPServerStarted = false;
      result.add(new Status(IStatus.ERROR, pluginId, Messages.ServerManager_HttpServerFailedToStart));
    }
    // Set transcription provider.
    EnvironmentActivator.getInstance().setTranscriptionProvider(TranscriptionHelper.getInstance());
    // Log results.
    FrameworkActivator.getDefault().log(result, null);
    // Initialise custom variable edit button registry
    CustomVariableEditButtonRegistry.getInstance().initialize();
  }

  /**
   * Is HTTP server successfully started ?
   * @return
   */
  public boolean isHTTPServerStarted() {
    return _isHTTPServerStarted;
  }

  /**
   * Get variable message.
   */
  private String variableMessage(String variablePath, String variableValue) {
    return variablePath + ": " + variableValue; //$NON-NLS-1$
  }

  /**
   * Write main Framework variables to console.
   * @return
   */
  private IStatus writeVariables() {
    String pluginId = FrameworkActivator.getDefault().getPluginId();
    MultiStatus result = new MultiStatus(pluginId, 0, Messages.ServerManager_SharedVariablesHandling, null);
    result.add(new Status(IStatus.OK, pluginId, variableMessage(EnvironmentVariableLoader.ORCHESTRA_SERVER_HOME, EnvironmentVariables.GetPapeete_Home())));
    result.add(new Status(IStatus.OK, pluginId, variableMessage(EnvironmentVariableLoader.ORCHESTRA_ARTEFACTS, FrameworkActivator.getDefault()
        .getEnvironmentsRegistry().getArtifactEnvironmentRegistry().toString())));
    result.add(new Status(IStatus.OK, pluginId, variableMessage(EnvironmentVariableLoader.ORCHESTRA_CONFDIR, EnvironmentVariables.GetConfDir())));
    result.add(new Status(IStatus.OK, pluginId, variableMessage(EnvironmentVariableLoader.ORCHESTRA_SHAREDDIR, EnvironmentVariables.GetSharedDir())));
    result.add(new Status(IStatus.OK, pluginId, variableMessage(EnvironmentVariableLoader.ORCHESTRA_TEMP, EnvironmentVariables.GetTempDir())));
    return result;
  }
}