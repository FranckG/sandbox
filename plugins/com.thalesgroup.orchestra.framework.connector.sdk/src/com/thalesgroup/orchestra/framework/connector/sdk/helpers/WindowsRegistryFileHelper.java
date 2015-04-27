/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.connector.sdk.helpers;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.thalesgroup.orchestra.framework.common.helper.FileHelper;
import com.thalesgroup.orchestra.framework.connector.sdk.SdkWizardsActivator;

/**
 * @author T0052089
 */
public class WindowsRegistryFileHelper {
  /**
   * Registry key deletion parameter.
   */
  public static final String COMMAND_REG_DELETE_PARAMETER = "delete"; //$NON-NLS-1$
  /**
   * Force option for registry key deletion.
   */
  public static final String COMMAND_REG_DELETE_FORCE_OPTION = "/f"; //$NON-NLS-1$
  /**
   * Registry file import parameter.
   */
  public static final String COMMAND_REG_IMPORT_PARAMETER = "import"; //$NON-NLS-1$
  /**
   * Windows registry access command.
   */
  public static final String COMMAND_REG = "reg"; //$NON-NLS-1$
  /**
   * Template registry file suffix.
   */
  public static final String TEMPLATE_FILE_SUFFIX = "Template"; //$NON-NLS-1$
  /**
   * HKEY_CURRENT_USER hive initials.
   */
  public static final String HKCU_HIVE_INITIALS = "HKCU"; //$NON-NLS-1$
  /**
   * HKEY_CURRENT_USER template file location in the SDK bundle.
   */
  public static final String HKCU_TEMPLATE_FILE_PATH = generateHkcuFilePath(SdkWizardsActivator.getDefault().getPluginId(), TEMPLATE_FILE_SUFFIX);
  /**
   * HKEY_LOCAL_MACHINE hive initials.
   */
  public static final String HKLM_HIVE_INITIALS = "HKLM"; //$NON-NLS-1$;
  /**
   * HKEY_LOCAL_MACHINE template file location in the SDK bundle.
   */
  public static final String HKLM_TEMPLATE_FILE_PATH = generateHklmFilePath(SdkWizardsActivator.getDefault().getPluginId(), TEMPLATE_FILE_SUFFIX);
  /**
   * Key pattern used to find a key reference in a .reg file.
   */
  public static final String KEY_PATTERN_IN_REG_FILES = "\\[.+\\]"; //$NON-NLS-1$
  /**
   * .reg files encoding (UTF-16 little endian).
   */
  public static final String REG_FILES_CHARSET_NAME = "UTF-16LE"; //$NON-NLS-1$
  /**
   * Registry files extension (*.reg).
   */
  public static final String REGISTRY_FILES_EXTENSION = "reg"; //$NON-NLS-1$
  /**
   * Registry files location in connector projects.
   */
  public static final String REGISTRY_FILES_FOLDER_RELATIVE_LOCATION = "deployment/registry"; //$NON-NLS-1$ 

  /**
   * Delete the given key from the registry.
   * @param regKeyToDelete_p
   * @return a status with severity OK if deletion worked, with severity ERROR else.
   */
  public static IStatus deleteKeyInWindowsRegistry(String regKeyToDelete_p) {
    final String pluginId = SdkWizardsActivator.getDefault().getPluginId();
    int deleteResult = -1;
    try {
      ProcessBuilder deleteCommandProcessBuilder =
          new ProcessBuilder(COMMAND_REG, COMMAND_REG_DELETE_PARAMETER, regKeyToDelete_p, COMMAND_REG_DELETE_FORCE_OPTION);
      Process deleteCommandProcess = deleteCommandProcessBuilder.start();
      deleteResult = deleteCommandProcess.waitFor();
    } catch (Exception exception_p) {
      return new Status(IStatus.ERROR, pluginId, MessageFormat.format(Messages.WindowsRegistryFileHelper_ErrorMessage_RegistryKeyDeletion_Error,
          regKeyToDelete_p), exception_p);
    }
    switch (deleteResult) {
      case 0: // Success
        return new Status(IStatus.OK, pluginId, MessageFormat.format(Messages.WindowsRegistryFileHelper_Message_RegistryKeyDeletion_Success, regKeyToDelete_p));
      case 1: // Failure
        return new Status(IStatus.ERROR, pluginId, MessageFormat.format(Messages.WindowsRegistryFileHelper_ErrorMessage_RegistryKeyDeletion_Failure,
            regKeyToDelete_p));
      default:
        return new Status(IStatus.ERROR, pluginId, MessageFormat.format(Messages.WindowsRegistryFileHelper_ErrorMessage_RegistryKeyDeletion_UnexpectedResult,
            regKeyToDelete_p, String.valueOf(deleteResult)));
    }
  }

  /**
   * Generate .reg file path for HKCU hive.
   * @param projectName_p
   * @param fileNameSuffix_p
   * @return
   */
  public static String generateHkcuFilePath(String projectName_p, String fileNameSuffix_p) {
    return projectName_p + "/" + WindowsRegistryFileHelper.REGISTRY_FILES_FOLDER_RELATIVE_LOCATION + "/" + HKCU_HIVE_INITIALS + "_" + fileNameSuffix_p + "." //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
           + REGISTRY_FILES_EXTENSION;
  }

  /**
   * Generate .reg file path for HKLM hive.
   * @param projectName_p
   * @param fileNameSuffix_p
   * @return
   */
  public static String generateHklmFilePath(String projectName_p, String fileNameSuffix_p) {
    return projectName_p + "/" + WindowsRegistryFileHelper.REGISTRY_FILES_FOLDER_RELATIVE_LOCATION + "/" + HKLM_HIVE_INITIALS + "_" + fileNameSuffix_p + "." //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
           + REGISTRY_FILES_EXTENSION;
  }

  /**
   * Get the first registry key present in a .reg file.
   * @param regFileResource_p
   * @return
   */
  public static String getFirstRegistryKeyFromRegFile(IResource regFileResource_p) {
    try {
      // Load reg file content.
      String regFileContent = new String(FileHelper.readRawFile(regFileResource_p.getFullPath().toString()), REG_FILES_CHARSET_NAME);
      // Find registry key.
      Pattern regKeyPattern = Pattern.compile(KEY_PATTERN_IN_REG_FILES);
      Matcher regKeyMatcher = regKeyPattern.matcher(regFileContent);
      if (!regKeyMatcher.find()) {
        return null;
      }
      String regKey = regKeyMatcher.group();
      return regKey.substring(1, regKey.length() - 1);
    } catch (UnsupportedEncodingException exception_p) {
      return null;
    }
  }

  /**
   * Find registry file resource (*.reg file) from given project and starting with the given hive initials.
   * @param containingProject_p
   * @param hiveInitials_p
   * @return
   */
  public static IResource getRegistryFileResource(IProject containingProject_p, String hiveInitials_p) {
    try {
      // Get files under "deployment/registry" directory.
      IResource[] resources = containingProject_p.getFolder(REGISTRY_FILES_FOLDER_RELATIVE_LOCATION).members();
      // Find the first file starting with the given prefix and with "reg" extension.
      for (IResource resource : resources) {
        if (resource.getName().startsWith(hiveInitials_p) && REGISTRY_FILES_EXTENSION.equals(resource.getFileExtension())) {
          return resource;
        }
      }
    } catch (CoreException exception_p) {
      // Nothing to to, null will be returned.
    }
    return null;
  }

  /**
   * Import given file in the Windows registry.
   * @param regFileAbsoluteLocation_p Not <code>null</code> absolute path to the .reg file to import.
   * @return An <code>IStatus</code> with severity <code>IStatus.OK</code> if the import was successful or <code>IStatus.ERROR</code> if it was not.
   */
  public static IStatus importRegFileInWindowsRegistry(IPath regFileAbsoluteLocation_p) {
    final String pluginId = SdkWizardsActivator.getDefault().getPluginId();

    int importResult = -1;

    try {
      ProcessBuilder importCommandProcessBuilder = new ProcessBuilder(COMMAND_REG, COMMAND_REG_IMPORT_PARAMETER, regFileAbsoluteLocation_p.toString());
      Process importCommandProcess = importCommandProcessBuilder.start();
      importResult = importCommandProcess.waitFor();
    } catch (Exception exception_p) {
      return new Status(IStatus.ERROR, pluginId, MessageFormat.format(Messages.WindowsRegistryFileHelper_ErrorMessage_RegistryImport_Error,
          regFileAbsoluteLocation_p), exception_p);
    }
    switch (importResult) {
      case 0: // Success
        return new Status(IStatus.OK, pluginId, MessageFormat.format(Messages.WindowsRegistryFileHelper_Message_RegistryImport_Success,
            regFileAbsoluteLocation_p));
      case 1: // Failure
        return new Status(IStatus.ERROR, pluginId, MessageFormat.format(Messages.WindowsRegistryFileHelper_ErrorMessage_RegistryImport_Failure,
            regFileAbsoluteLocation_p));
      default:
        return new Status(IStatus.ERROR, pluginId, MessageFormat.format(Messages.WindowsRegistryFileHelper_ErrorMessage_RegistryImport_UnexpectedResult,
            regFileAbsoluteLocation_p, String.valueOf(importResult)));
    }
  }
}
