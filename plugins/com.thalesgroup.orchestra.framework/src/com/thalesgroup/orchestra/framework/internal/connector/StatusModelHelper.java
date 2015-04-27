/**
 * Copyright (c) THALES, 2010. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.internal.connector;

import java.io.File;
import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.eclipse.core.runtime.IStatus;

import com.thalesgroup.orchestra.framework.FrameworkActivator;
import com.thalesgroup.orchestra.framework.connector.CommandStatus;
import com.thalesgroup.orchestra.framework.environment.EnvironmentVariables;
import com.thalesgroup.orchestra.framework.exchange.StatusHandler;
import com.thalesgroup.orchestra.framework.exchange.status.SeverityType;
import com.thalesgroup.orchestra.framework.exchange.status.Status;
import com.thalesgroup.orchestra.framework.exchange.status.StatusDefinition;
import com.thalesgroup.orchestra.framework.exchange.status.StatusFactory;
import com.thalesgroup.orchestra.framework.variablemanager.server.model.VariableManager;

/**
 * @author s0024585
 */
public class StatusModelHelper {
  /**
   * Format for data file names (files under shared dir).
   */
  private static final String DATA_FILE_NAME_FORMAT = "{0}_{1}.{2}"; //$NON-NLS-1$

  /**
   * Counter used to create unique directories under custom shared dir.
   */
  private static final AtomicLong __uniqueDirectoryCounter = new AtomicLong();

  /**
   * Build the final model and serialize it in the file _responseFilePath
   * @param allStatus_p The list of {@link CommandStatus} to add in the model
   */
  public static void buildStatusModel(StatusHandler statusHandler_p, StatusDefinition statusDefinitionRoot_p, List<Status> allStatus_p) {
    statusDefinitionRoot_p.getStatus().getStatus().addAll(allStatus_p);
    statusHandler_p.saveModel(statusDefinitionRoot_p, true);
  }

  /**
   * Build a {@link Status} model object from a {@link CommandStatus}.
   * @param commandStatus_p
   * @return The non <code>null</code> {@link Status} model object.
   */
  public static Status buildStatusModelObject(CommandStatus commandStatus_p) {
    Status modelStatus = StatusFactory.eINSTANCE.createStatus();
    modelStatus.setCode(commandStatus_p.getCode());
    modelStatus.setMessage(commandStatus_p.getMessage());

    SeverityType severity = SeverityType.INFO;
    switch (commandStatus_p.getSeverity()) {
      case IStatus.OK:
        severity = SeverityType.OK;
      break;
      case IStatus.WARNING:
        severity = SeverityType.WARNING;
      break;
      case IStatus.ERROR:
        severity = SeverityType.ERROR;
      break;
      default:
        severity = SeverityType.INFO;
    }
    modelStatus.setSeverity(severity);
    if (commandStatus_p.getUri() != null) {
      modelStatus.setUri(commandStatus_p.getUri().getUri());
    }
    for (CommandStatus childCommandStatus : commandStatus_p.getChildren()) {
      Status childStatus = buildStatusModelObject(childCommandStatus);
      modelStatus.getStatus().add(childStatus);
    }
    // Oops, something went really wrong.
    // Add status to log for debugging purposes.
    if (null != commandStatus_p.getException()) {
      FrameworkActivator.getDefault().getLog().log(commandStatus_p);
    }
    return modelStatus;
  }

  /**
   * <ol>
   * <li>Create an unique directory under the custom shareddir (see
   * {@link com.thalesgroup.orchestra.framework.lib.base.UtilFunction#getCustomSharedDirSegment()}). The name of the created directory is composed of an integer
   * in base 36 starting from 0.</li>
   * <li>Generate a file name from passed arguments.</li>
   * </ol>
   * Generate a file name using arguments. Return the absolute p
   * @param prefix_p The filename prefix
   * @param rootType_p
   * @param extension_p The filename extension
   * @return the generated file name in the created directory (file is not created)
   */
  public static String createDataFileName(String prefix_p, String rootType_p, String extension_p) {
    // Get custom shared dir (shareddir/hostname_username).
    File customSharedDirectory = new File(VariableManager.getSharedDirectory(), EnvironmentVariables.GetCustomSharedDir());
    // Create unique directory.
    boolean uniqueDirectoryCreated = false;
    File uniqueDirectoryInCustomSharedDir = null;
    // Ensure unique directory is created (if several Frameworks share the same shareddir, a directory can already exist !).
    while (!uniqueDirectoryCreated) {
      // Generate unique directory segment (a long in base 36).
      String uniqueDirectorySegment = Long.toString(__uniqueDirectoryCounter.getAndIncrement(), Character.MAX_RADIX);
      uniqueDirectoryInCustomSharedDir = new File(customSharedDirectory, uniqueDirectorySegment);
      // Try to create the directory.
      uniqueDirectoryCreated = uniqueDirectoryInCustomSharedDir.mkdirs();
    }
    // Generate file path in the created directory.
    String dataFileName = MessageFormat.format(DATA_FILE_NAME_FORMAT, prefix_p, rootType_p, extension_p);
    File dataFile = new File(uniqueDirectoryInCustomSharedDir, dataFileName);
    return dataFile.getAbsolutePath();
  }

  /**
   * Reset the unique folder generator to restart at 0. Useful when shared dir has been cleaned.
   */
  public static void resetUniqueDirectoryCounter() {
    __uniqueDirectoryCounter.set(0);
  }

  public static Status getNewContainerStatus() {
    Status containerStatus = StatusFactory.eINSTANCE.createStatus();
    containerStatus.setSeverity(SeverityType.INFO);
    containerStatus.setMessage("Orchestra Framework response container status"); //$NON-NLS-1$
    return containerStatus;
  }
}
