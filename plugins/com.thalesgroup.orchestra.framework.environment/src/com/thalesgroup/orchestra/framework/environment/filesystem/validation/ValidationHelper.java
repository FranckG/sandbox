/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment.filesystem.validation;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.thalesgroup.orchestra.framework.common.helper.FileHelper;
import com.thalesgroup.orchestra.framework.environment.EnvironmentAttributes;
import com.thalesgroup.orchestra.framework.environment.filesystem.FileSystemEnvironmentHandler;
import com.thalesgroup.orchestra.framework.environment.filesystem.Messages;

/**
 * This class provides methods to compare the paths of {@link EnvironmentAttributes} between each other <br>
 * Also provides the {@link AdditionalPath} class and a method to instantiate it
 * @author S0032874
 */
public class ValidationHelper {
  /**
   * Compare a path to an {@link AdditionalPath}, the later also carries the possible error messages
   * @param additionalPath_p The additional path to test
   * @param basePath_p The path to test
   * @return An error message based on the messages given by {@link AdditionalPath}, may be null if no error
   */
  private String compareToAdditionalPath(AdditionalPath additionalPath_p, String basePath_p) {
    // Precondition
    if (null == additionalPath_p) {
      return null;
    }
    String message = null;
    // get the path
    String path = additionalPath_p._path;
    // Compare the two paths
    ContainmentStatus status = compareValues(path, basePath_p);
    // Manage the result
    switch (status) {
      case CONTAINING:
        // Folder of first value contains folder of second value.
        message = MessageFormat.format(additionalPath_p._pathIsContainedMessage, basePath_p, path);
      break;
      case CONTAINED:
        // Folder of second value contains folder of first value.
        message = MessageFormat.format(additionalPath_p._pathContainsMessage, basePath_p, path);
      break;
      case EQUALS:
        // Folder of first value is identical to folder of second value.
        message = MessageFormat.format(additionalPath_p._pathIdenticalMessage, basePath_p);
      break;
      default:
      break;
    }
    return message;
  }

  /**
   * Compares two folder path to check if one is contained by the other; the result is: <br>
   * - The first value is contained by the second value: {@link ContainmentStatus#CONTAINED} <br>
   * - The second value is contained by the first value: {@link ContainmentStatus#CONTAINING} <br>
   * - The two values are identical: {@link ContainmentStatus#EQUALS} <br>
   * - There is no hierarchical link between the first value and the second value: {@link ContainmentStatus#NONE} <br>
   * - One or both values are invalid: {@link ContainmentStatus#ERROR} <br>
   * @param firstValue_p The first value
   * @param secondValue_p The second value
   * @return A {@link ContainmentStatus} describing the hierarchical link between the first value and the second value.
   */
  public ContainmentStatus compareValues(String firstValue_p, String secondValue_p) {
    // Precondition.
    if ((null == firstValue_p) || (null == secondValue_p)) {
      return ContainmentStatus.ERROR;
    }
    // Precondition.
    if (firstValue_p.equals(secondValue_p)) {
      return ContainmentStatus.EQUALS;
    }
    // Get associated paths.
    IPath firstValuePath = new Path(firstValue_p.toLowerCase());
    IPath secondValuePath = new Path(secondValue_p.toLowerCase());
    // Test prefixing.
    if (firstValuePath.isPrefixOf(secondValuePath)) {
      return ContainmentStatus.CONTAINING;
    } else if (secondValuePath.isPrefixOf(firstValuePath)) {
      return ContainmentStatus.CONTAINED;
    }
    return ContainmentStatus.NONE;
  }

  /**
   * Create an {@link AdditionalPath} class
   * @param path_p Not null
   * @param isContainedMessage_p Not null
   * @param containsMessage_p Not null
   * @param identicalMessage_p Not null
   * @return The created {@link AdditionalPath}, not null
   * @see AdditionalPath#AdditionalPath(String, String, String)
   */
  public AdditionalPath newAdditionalPath(String path_p, String isContainedMessage_p, String containsMessage_p, String identicalMessage_p) {
    return new AdditionalPath(path_p, isContainedMessage_p, containsMessage_p, identicalMessage_p);
  }

  /**
   * Verify that the path is a valid folder path
   * @param path_p The path to check
   * @return The error message, null if there is no error
   */
  private String testFolderIsValid(String path_p) {
    File file = FileHelper.isValidAbsolutePath(path_p);
    if ((null == file) || !file.isDirectory()) {
      // Invalid path, return an error message
      return MessageFormat.format(Messages.FileSystemEnvironmentRule_Path_Not_Valid_Folder_Error_Pattern, path_p);
    }
    // No error, return null
    return null;
  }

  /**
   * Perform validation operations on the paths of the given FileSytem: <br>
   * - Verify that each path is a valid folder path <br>
   * - Compare the paths of a list of {@link EnvironmentAttributes} between each other; an additional path can also be tested against all the environments paths
   * by providing a {@link AdditionalPath}. <br>
   * The result is a map containing all the error messages with the {@link EnvironmentAttributes} associated to them as key
   * @param environmentList The list of {@link EnvironmentAttributes} to test
   * @param additionalPath_p A {@link AdditionalPath} describing an additional path to test against those from the environments, can be null
   * @return A map containing all the error messages with the {@link EnvironmentAttributes} associated to them as key
   */
  public Map<EnvironmentAttributes, List<String>> validateFileSystemEnvironmentPaths(List<EnvironmentAttributes> environmentList,
      AdditionalPath additionalPath_p) {
    // Error message map to be returned
    Map<EnvironmentAttributes, List<String>> errorMessageMap = new HashMap<EnvironmentAttributes, List<String>>();
    int envNumber = environmentList.size();
    // For each environment check for possible link between the paths
    for (int k = 0; k < envNumber; k++) {
      // 'baseEnvironment' is the base for the search,
      EnvironmentAttributes baseEnvironment = environmentList.get(k);
      // Get the environment attributes i.e. the paths
      List<String> baseDirectoryList = baseEnvironment._expandedAttributes.get(FileSystemEnvironmentHandler.ATTRIBUTE_KEY_INPUT_DIRECTORIES);
      int size = baseDirectoryList.size();
      // Go through paths in the list.
      for (int i = 0; i < size; ++i) {
        // 'basePath' all paths will be tested against this one
        String basePath = baseDirectoryList.get(i);
        // List of error for the baseEnvironment
        List<String> baseMessageList = new ArrayList<String>();

        // Verify that the path is valid for a folder
        String message = testFolderIsValid(basePath);
        if (message != null) {
          // If there is an error, store it
          baseMessageList.add(message);
        }

        // First test the 'basePath' against the additional path, if any
        if (null != additionalPath_p) {
          // Compare the two paths
          String additionalPathMessage = compareToAdditionalPath(additionalPath_p, basePath);
          // Add the error message, if any
          if (null != additionalPathMessage) {
            baseMessageList.add(additionalPathMessage);
          }
        }

        // Secondly test the 'basePath' against all the other paths the same environment and store the errors
        // Compare the first path to other paths from the rest of the list.
        for (int j = i + 1; j < size; ++j) {
          String secondPath = baseDirectoryList.get(j);
          ContainmentStatus status = compareValues(basePath, secondPath);
          message = null;
          // Manage the result
          switch (status) {
            case CONTAINING:
              // Folder of first value contains folder of second value.
              message = MessageFormat.format(Messages.FileSystemEnvironmentRule_Same_Environment_Error_Pattern, basePath, secondPath);
            break;
            case CONTAINED:
              // Folder of second value contains folder of first value.
              message = MessageFormat.format(Messages.FileSystemEnvironmentRule_Same_Environment_Error_Pattern, secondPath, basePath);
            break;
            case EQUALS:
              // Folder of first value is identical to folder of second value
              message = MessageFormat.format(Messages.FileSystemEnvironmentRule_Path_Identical_Error_Pattern, basePath);
            break;
            default:
            break;
          }
          // Add the error message, if any
          if (null != message) {
            baseMessageList.add(message);
          }
        }

        // Thirdly test the 'basePath' against the paths from the other environments. Then store the errors so that later when displaying the errors of
        // another environment, the conflicts with the first environment can also be displayed without re-checking the previous environment
        // Go through the next environments
        for (int l = k + 1; l < envNumber; l++) {
          EnvironmentAttributes nextEnvironment = environmentList.get(l);
          // Get the environment attributes i.e. the paths
          List<String> nextDirectoryList = nextEnvironment._expandedAttributes.get(FileSystemEnvironmentHandler.ATTRIBUTE_KEY_INPUT_DIRECTORIES);
          // Go through all the paths from this environment
          for (String nextEnvPath : nextDirectoryList) {
            ContainmentStatus status = compareValues(basePath, nextEnvPath);
            // Compute the error message coherent for nextEnvironment, i.e. the opposite of the one from baseEnvironment
            String nextMessage = null;
            String baseMessage = null;
            // Manage the result
            switch (status) {
              case CONTAINING:
                // Folder of first value contains folder of second value.
                baseMessage =
                    MessageFormat.format(Messages.FileSystemEnvironmentRule_Path_Contains_Other_Environment_Error_Pattern, basePath, nextEnvPath,
                        nextDirectoryList.toString());
                nextMessage =
                    MessageFormat.format(Messages.FileSystemEnvironmentRule_Path_Contained_By_Other_Environment_Error_Pattern, nextEnvPath, basePath,
                        baseDirectoryList.toString());
              break;
              case CONTAINED:
                // Folder of second value contains folder of first value.
                baseMessage =
                    MessageFormat.format(Messages.FileSystemEnvironmentRule_Path_Contained_By_Other_Environment_Error_Pattern, basePath, nextEnvPath,
                        nextDirectoryList.toString());
                nextMessage =
                    MessageFormat.format(Messages.FileSystemEnvironmentRule_Path_Contains_Other_Environment_Error_Pattern, nextEnvPath, basePath,
                        baseDirectoryList.toString());
              break;
              case EQUALS:
                // Folder of first value is identical to folder of second value
                baseMessage =
                    MessageFormat.format(Messages.FileSystemEnvironmentRule_Path_Identical_In_Other_Environment_Error_Pattern, basePath,
                        nextDirectoryList.toString());
                nextMessage =
                    MessageFormat.format(Messages.FileSystemEnvironmentRule_Path_Identical_In_Other_Environment_Error_Pattern, basePath,
                        baseDirectoryList.toString());
              break;
              default:
              break;
            }
            // There was an error, store the messages
            if (null != nextMessage) {
              baseMessageList.add(baseMessage);

              // Get the nextEnvironment errorMessage list
              List<String> nextMessageList = errorMessageMap.get(nextEnvironment);
              if (null == nextMessageList) {
                // If null, first pass, create it
                nextMessageList = new ArrayList<String>();
                errorMessageMap.put(nextEnvironment, nextMessageList);
              }
              // Add the error message
              nextMessageList.add(nextMessage);
            }
          }
        }
        // Now get the baseEnvironment errorMessage list and append the new one
        List<String> messageList = errorMessageMap.get(baseEnvironment);
        if (null == messageList) {
          // If null, first pass, create it
          messageList = new ArrayList<String>();
          errorMessageMap.put(baseEnvironment, messageList);
        }
        messageList.addAll(baseMessageList);
      }
    }
    // Return the map containing all the environments and their associated error messages
    return errorMessageMap;
  }

  /**
   * Class describing the data required to test a path against the path contained by one or more environments. <br>
   * This include the path himself and two error messages
   * @author S0032874
   */
  public class AdditionalPath {

    /**
     * Path to test
     */
    public String _path;

    /**
     * Error message displayed when the path contains a path from one of the environment
     */
    public String _pathContainsMessage;

    /**
     * Error message displayed when the path is identical to the path from on the environment
     */
    public String _pathIdenticalMessage;

    /**
     * Error message displayed when the path is contained by a path from one of the environment
     */
    public String _pathIsContainedMessage;

    /**
     * Constructor
     * @param path_p The path
     * @param pathIsContainedMessage_p Error message when the path contains another one
     * @param pathContainsMessage_p Error message when the path is contained by another one
     * @param pathIdenticalMessage_p Error message when the path is identical to another one
     */
    public AdditionalPath(String path_p, String pathIsContainedMessage_p, String pathContainsMessage_p, String pathIdenticalMessage_p) {
      _path = path_p;
      _pathContainsMessage = pathContainsMessage_p;
      _pathIsContainedMessage = pathIsContainedMessage_p;
      _pathIdenticalMessage = pathIdenticalMessage_p;
    }
  }

  /**
   * Status describing the result of a comparison between paths
   * @author S0032874
   */
  public static enum ContainmentStatus {
    CONTAINED, CONTAINING, EQUALS, ERROR, NONE;
  }
}
