/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;

import com.thalesgroup.orchestra.framework.FrameworkActivator;
import com.thalesgroup.orchestra.framework.common.CommonActivator;
import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.framework.environment.EnvironmentActivator;
import com.thalesgroup.orchestra.framework.environment.EnvironmentContext;
import com.thalesgroup.orchestra.framework.environment.IEnvironmentHandler;
import com.thalesgroup.orchestra.framework.environment.filesystem.FileSystemEnvironmentHandler;
import com.thalesgroup.orchestra.framework.environment.ui.AbstractVariablesHandler;
import com.thalesgroup.orchestra.framework.environment.ui.IVariablesHandler;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.util.FileUtils;

/**
 * @author s0011584
 */
public class ConfDirHelper {
  private static HashMap<String, List<Path>> __confDirImage = new HashMap<String, List<Path>>(0);
  private static List<Path> __directoriesList;

  /**
   * Add the file or directory pointed by the <code>subPath_p</code> relative to <code>confDirPath_p</code>
   * @param confDirPath_p
   * @param subPath_p
   * @return a status describing the state of the operation.
   */
  public static IStatus add(String confDirPath_p, String subPath_p) {
    IStatus result;

    String confDirPath = Paths.get(confDirPath_p).toAbsolutePath().toString();
    Path srcData = Paths.get(confDirPath, subPath_p);
    String subPath = srcData.toAbsolutePath().toString().replace(confDirPath, ICommonConstants.EMPTY_STRING);

    if (subPath.startsWith(File.separator)) {
      subPath = subPath.substring(1);
    }

    result = checkConfDirPath(confDirPath);
    if (result.getSeverity() != IStatus.OK) {
      return result;
    }

    // Check the subpath in the confdir image
    // Check the key
    if (!__confDirImage.containsKey(subPath)) {
      // Most simple case: add the file!
      List<Path> l = new ArrayList<Path>();
      l.add(Paths.get(confDirPath));
      __confDirImage.put(subPath, l);
    } else {
      List<Path> l = __confDirImage.get(subPath);
      if (l.contains(Paths.get(confDirPath))) {
        return new Status(IStatus.ERROR, ModelHandlerActivator.getDefault().getPluginId(), MessageFormat.format(
            "''{0}'' is already contributed by ''{1}''.", subPath, confDirPath)); //$NON-NLS-1$
      }
      int confDirIndex = __directoriesList.indexOf(Paths.get(confDirPath));
      int index = -1;
      for (int i = confDirIndex - 1; i > 0; i--) {
        if (l.contains(__directoriesList.get(i))) {
          index = l.indexOf(__directoriesList.get(i)) + 1;
          break;
        }
      }
      if (index == -1) {
        index = 0;
      }
      l.add(index, Paths.get(confDirPath));
    }

    // If it's a directory, add all subdirectories and files.
    if (Files.isDirectory(srcData)) {
      recurse(srcData, confDirPath);
    }

    result = rebuild(subPath);
    return result;
  }

  /**
   * Build the image containing for each file/directory the confdir from which it comes.
   * @param directoriesList_p
   */
  private static void buildImage(List<Path> directoriesList_p) {
    for (Path directory : directoriesList_p) {
      recurse(directory, directory.toString());
    }
  }

  /**
   * @param confDirPath_p
   * @return
   */
  private static IStatus checkConfDirPath(String confDirPath_p) {
    if (!__directoriesList.contains(Paths.get(confDirPath_p))) {
      return new Status(IStatus.ERROR, ModelHandlerActivator.getDefault().getPluginId(), MessageFormat.format(
          "The directory ''{0}'' is not included in ConfigurationDirectories variable.", confDirPath_p)); //$NON-NLS-1$
    }

    return new Status(IStatus.OK, ModelHandlerActivator.getDefault().getPluginId(), ICommonConstants.EMPTY_STRING);
  }

  /**
   * Delete <code>subPath_p</code> contributed by <code>confDirPath_p</code>.
   * @param confDirPath_p
   * @param subPath_p
   * @return
   */
  public static IStatus delete(String confDirPath_p, String subPath_p) {
    IStatus result;

    String confDirPath = Paths.get(confDirPath_p).toAbsolutePath().toString();
    Path srcData = Paths.get(confDirPath, subPath_p);
    String subPath = srcData.toAbsolutePath().toString().replace(confDirPath, ICommonConstants.EMPTY_STRING);

    if (subPath.startsWith(File.separator)) {
      subPath = subPath.substring(1);
    }

    result = checkConfDirPath(confDirPath);
    if (result.getSeverity() != IStatus.OK) {
      return result;
    }

    // Check the subpath in the confdir image
    // Check the key
    if (__confDirImage.containsKey(subPath)) {
      List<Path> confDirList = __confDirImage.get(subPath);
      // Check the confDir
      if (confDirList.contains(Paths.get(confDirPath))) {
        confDirList.remove(Paths.get(confDirPath));
      } else {
        return new Status(IStatus.ERROR, ModelHandlerActivator.getDefault().getPluginId(), MessageFormat.format(
            "The configuration directory ''{0}'' does not contribute to ''{1}''.", confDirPath, subPath)); //$NON-NLS-1$
      }
    } else {
      return new Status(IStatus.ERROR, ModelHandlerActivator.getDefault().getPluginId(), MessageFormat.format("The subpath ''{0}'' is unknown.", subPath)); //$NON-NLS-1$
    }

    String extractedConfDir = DataUtil.getValues(DataUtil.__CONFIGURATIONDIRECTORY_VARIABLE_NAME).get(0).getValue();

    Path destData = Paths.get(extractedConfDir, subPath);

    if (Files.isDirectory(destData)) {
      updateImageForDelete(extractedConfDir, confDirPath, destData);
    }

    result = rebuild(subPath);

    return result;
  }

  /**
   * Get configuration directories absolute paths, in the order defined by specified context.
   * @param context_p
   * @return
   */
  public static List<String> getConfigurationDirectoriesAbsolutePaths(final Context context_p) {
    List<String> result = new ArrayList<String>(0);
    AbstractVariable variable = DataUtil.getVariable(DataUtil.__CONFIGURATIONDIRECTORIES_VARIABLE_NAME, context_p);
    List<VariableValue> values = DataUtil.getValues(variable, context_p);
    // Set variables handler for environments to use.
    IVariablesHandler handler = new AbstractVariablesHandler() {
      /**
       * @see com.thalesgroup.orchestra.framework.environment.ui.IVariablesHandler#getSubstitutedValue(java.lang.String)
       */
      @Override
      public String getSubstitutedValue(String rawValue_p) {
        // Precondition.
        if (null == rawValue_p) {
          return null;
        }
        return DataUtil.getSubstitutedValue(rawValue_p, context_p);
      }
    };
    EnvironmentActivator.getInstance().setVariablesHandler(handler);
    try {
      // Cycle through values.
      for (VariableValue variableValue : values) {
        // Ignore wrong types.
        if (!(variableValue instanceof EnvironmentVariableValue)) {
          continue;
        }
        EnvironmentVariableValue environmentVariableValue = (EnvironmentVariableValue) variableValue;
        // Get handler for this environment.
        Couple<IStatus, IEnvironmentHandler> environmentHandlerCouple =
            EnvironmentActivator.getInstance().getEnvironmentRegistry().getEnvironmentHandler(environmentVariableValue.getEnvironmentId());
        if (!environmentHandlerCouple.getKey().isOK()) {
          // EnvironmentHandler can't be found.
          continue;
        }
        // Get environment values.
        Map<String, String> environmentValues = ModelUtil.convertEnvironmentVariableValues(environmentVariableValue);
        // Get expanded attributes (contains resolved and substituted artifact paths).
        EnvironmentContext environmentContext = new EnvironmentContext();
        environmentContext._attributes = environmentValues;
        environmentContext._allowUserInteractions = false;
        Map<String, List<String>> expandedAttributes = environmentHandlerCouple.getValue().getExpandedAttributes(environmentContext);
        // This is to be a file system environment.
        // Get list of directories.
        List<String> directories = expandedAttributes.get(FileSystemEnvironmentHandler.ATTRIBUTE_KEY_INPUT_DIRECTORIES);
        if (null != directories) {
          result.addAll(directories);
        }
      }
    } finally {
      EnvironmentActivator.getInstance().setVariablesHandler(null);
    }
    return result;
  }

  public static void mergeConfDir(List<String> sources_p, VariableValue destination_p) {
    // Preconditions.
    if ((null == sources_p) || (null == destination_p)) {
      return;
    }
    String destValue = destination_p.getValue();
    if (null == destValue) {
      return;
    }
    __directoriesList = new ArrayList<Path>(0);
    for (String source : sources_p) {
      __directoriesList.add(Paths.get(source));
    }
    // Fill __confDirImage.
    __confDirImage.clear();
    buildImage(__directoriesList);

    Path confDirExtractedDestination = Paths.get(destValue);
    if (Files.exists(confDirExtractedDestination)) {
      // Clean configuration directory (don't delete conf dir root for now).
      try {
        FileUtils.CleanDirectory(confDirExtractedDestination);
      } catch (IOException exception_p) {
        String message = MessageFormat.format("Fail to clean conf dir : ''{0}''.", destValue); //$NON-NLS-1$
        IStatus cleanErrorStatus = new Status(IStatus.ERROR, FrameworkActivator.getDefault().getPluginId(), message, exception_p);
        FrameworkActivator.getDefault().log(cleanErrorStatus, null);
        return;
      }
      // Delete root configuration directory.
      // Clean and delete are separated because if clean fails, it's a big problem, but if delete root directory fails, it's not (provided that the conf dir
      // root can be read and written in).
      try {
        FileUtils.DeleteDirectory(confDirExtractedDestination);
      } catch (IOException exception_p) {
        String message = MessageFormat.format("Fail to delete conf dir : ''{0}'' (the directory has been successfully cleaned, though).", destValue); //$NON-NLS-1$
        IStatus cleanErrorStatus = new Status(IStatus.WARNING, FrameworkActivator.getDefault().getPluginId(), message, exception_p);
        FrameworkActivator.getDefault().log(cleanErrorStatus, null);
      }
    }
    // Fill configuration directory.
    String copyMainErrorMessage = MessageFormat.format("Error(s) occurred when merging confdirs to ''{0}''", destValue); //$NON-NLS-1$
    MultiStatus copyMainErrorStatus = new MultiStatus(FrameworkActivator.getDefault().getPluginId(), 0, copyMainErrorMessage, null);
    for (Path sourceValue : __directoriesList) {
      try {
        FileUtils.CopyDirectory(sourceValue, confDirExtractedDestination);
      } catch (IOException exception_p) {
        String message = MessageFormat.format("Fail to deploy confdir ''{0}''", sourceValue); //$NON-NLS-1$
        copyMainErrorStatus.add(new Status(IStatus.ERROR, FrameworkActivator.getDefault().getPluginId(), message, exception_p));
      }
    }
    if (0 != copyMainErrorStatus.getChildren().length) {
      // If main status is not empty, add it to log.
      FrameworkActivator.getDefault().log(copyMainErrorStatus, null);
    }
  }

  /**
   * Copy the modified file (or directory) described
   * @param confDirPath_p
   * @param subPath_p
   * @return
   */
  public static IStatus modify(String confDirPath_p, String subPath_p) {
    IStatus result;
    // Ensure that the file separator is consistent with the one used in the cache.
    String confDirPath = Paths.get(confDirPath_p).toAbsolutePath().toString();
    Path srcData = Paths.get(confDirPath, subPath_p);
    String subPath = srcData.toAbsolutePath().toString().replace(confDirPath, ICommonConstants.EMPTY_STRING);

    if (subPath.startsWith(File.separator)) {
      subPath = subPath.substring(1);
    }

    if (Files.notExists(srcData)) {
      return new Status(IStatus.ERROR, ModelHandlerActivator.getDefault().getPluginId(), MessageFormat.format("Source ''{0}'' does not exist.", srcData)); //$NON-NLS-1$
    }

    if (Files.isDirectory(srcData)) {
      return new Status(IStatus.ERROR, ModelHandlerActivator.getDefault().getPluginId(), "Modify action can not be done on a directory."); //$NON-NLS-1$
    }

    result = checkConfDirPath(confDirPath);
    if (result.getSeverity() != IStatus.OK) {
      return result;
    }

    // Check the subpath in the confdir image
    // Check the key
    if (!__confDirImage.containsKey(subPath)) {
      // Unknown subpath: the file does not exist, so no modifications allowed.
      return new Status(IStatus.ERROR, ModelHandlerActivator.getDefault().getPluginId(), MessageFormat.format(
          "File ''{0}'' does not previously exist.", subPath)); //$NON-NLS-1$
    }

    List<Path> confDirList = __confDirImage.get(subPath);
    // Check the confDir
    if (!confDirPath.equals(confDirList.get(confDirList.size() - 1).toString())) {
      // The last confdir to have copied the file is not confDirPath_p
      // So don't modify the file.
      return new Status(IStatus.OK, ModelHandlerActivator.getDefault().getPluginId(), ICommonConstants.EMPTY_STRING);
    }

    result = rebuild(subPath);

    return result;
  }

  /**
   * Rebuild a directory or a file from its subPathKey in the image of the confDir.
   * @param subPathKey_p
   * @return
   */
  private static IStatus rebuild(String subPathKey_p) {
    IStatus result = new Status(IStatus.OK, ModelHandlerActivator.getDefault().getPluginId(), ICommonConstants.EMPTY_STRING);

    // Delete the existing file or directory.
    Path dest = Paths.get(DataUtil.getValues(DataUtil.__CONFIGURATIONDIRECTORY_VARIABLE_NAME).get(0).getValue(), subPathKey_p);
    if (Files.exists(dest)) {
      try {
        if (Files.isDirectory(dest)) {
          FileUtils.DeleteDirectory(dest);
        } else {
          Files.delete(dest);
        }
      } catch (IOException exception_p) {
        return new Status(IStatus.ERROR, ModelHandlerActivator.getDefault().getPluginId(), exception_p.getMessage());
      }
    }

    // Rebuild
    List<Path> confDirs = __confDirImage.get(subPathKey_p);

    for (Path confDir : confDirs) {
      Path src = Paths.get(confDir.toString(), subPathKey_p);
      try {
        if (Files.isDirectory(src)) {
          FileUtils.CopyDirectory(src, dest);
        } else {
          Files.copy(src, dest);
        }
      } catch (IOException exception_p) {
        return new Status(IStatus.ERROR, ModelHandlerActivator.getDefault().getPluginId(), exception_p.getMessage());
      }
    }

    return result;
  }

  /**
   * Recurse all subdirectories of <code>dirFile_p</code> to build the image.
   * @param dirFile_p
   * @param confDir_p
   */
  private static void recurse(Path dirFile_p, final String confDir_p) {
    // Precondition.
    if ((null == dirFile_p) || !Files.exists(dirFile_p) || !Files.isDirectory(dirFile_p)) {
      return;
    }

    try {
      Files.walkFileTree(dirFile_p, new SimpleFileVisitor<Path>() {

        /**
         * @see java.nio.file.SimpleFileVisitor#visitFile(java.lang.Object, java.nio.file.attribute.BasicFileAttributes)
         */
        @Override
        @SuppressWarnings("synthetic-access")
        public FileVisitResult visitFile(Path file_p, BasicFileAttributes attrs_p) throws IOException {
          int confDirLen = confDir_p.length();
          String key = file_p.toString();
          key = key.substring(confDirLen);
          if (key.startsWith(File.separator)) {
            key = key.substring(1);
          }

          if (__confDirImage.containsKey(key)) {
            List<Path> list = __confDirImage.get(key);
            list.add(Paths.get(confDir_p));
          } else {
            List<Path> list = new ArrayList<Path>();
            list.add(Paths.get(confDir_p));
            __confDirImage.put(key, list);
          }

          return FileVisitResult.CONTINUE;
        }

      });
    } catch (IOException exception_p) {
      // TODO Auto-generated catch block
      StringBuilder loggerMessage = new StringBuilder("ConfDirHelper.recurse(..) _ "); //$NON-NLS-1$
      CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.ERROR, exception_p);
    }
  }

  /**
   * Removes recursively the <code>rootConfDir</code> from all subdirectories and files of dest_p in the image
   * @param extractedConfDir_p
   * @param rootConfDir
   * @param dest_p
   */
  private static void updateImageForDelete(String extractedConfDir_p, final String rootConfDir, Path dest_p) {
    final int rootLen = extractedConfDir_p.length();

    try {
      Files.walkFileTree(dest_p, new SimpleFileVisitor<Path>() {

        /**
         * @see java.nio.file.SimpleFileVisitor#visitFile(java.lang.Object, java.nio.file.attribute.BasicFileAttributes)
         */
        @Override
        @SuppressWarnings("synthetic-access")
        public FileVisitResult visitFile(Path file_p, BasicFileAttributes attrs_p) throws IOException {
          String key = file_p.toString();
          key = key.substring(rootLen);
          if (key.startsWith(File.separator)) {
            key = key.substring(1);
          }

          if (__confDirImage.containsKey(key)) {
            List<Path> list = __confDirImage.get(key);
            if (list.remove(Paths.get(rootConfDir))) {
              if (list.size() == 0) {
                __confDirImage.remove(key);
              }
            }
          }
          return FileVisitResult.CONTINUE;
        }

      });
    } catch (IOException exception_p) {
      // TODO Auto-generated catch block
      StringBuilder loggerMessage = new StringBuilder("ConfDirHelper.updateImageForDelete(..) _ "); //$NON-NLS-1$
      CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.ERROR, exception_p);
    }

  }
}