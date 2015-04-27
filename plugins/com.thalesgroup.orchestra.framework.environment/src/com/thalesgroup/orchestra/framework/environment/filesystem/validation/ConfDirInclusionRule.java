/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment.filesystem.validation;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.thalesgroup.orchestra.framework.environment.EnvironmentAttributes;
import com.thalesgroup.orchestra.framework.environment.filesystem.FileSystemEnvironment;
import com.thalesgroup.orchestra.framework.environment.filesystem.Messages;
import com.thalesgroup.orchestra.framework.environment.filesystem.validation.ValidationHelper.AdditionalPath;
import com.thalesgroup.orchestra.framework.environment.validation.AbstractEnvironmentRule;
import com.thalesgroup.orchestra.framework.environment.validation.ConfigurationDirectoriesRuleContext;
import com.thalesgroup.orchestra.framework.environment.validation.EnvironmentRuleContext;

/**
 * Rule to verify that the paths from the {@link FileSystemEnvironment} contributing to the configurationDirectories do no have hierarchical link between each
 * other and neither with the configurationDirectory folder
 * @author S0032874
 */
public class ConfDirInclusionRule extends AbstractEnvironmentRule {

  /**
   * @see com.thalesgroup.orchestra.framework.environment.validation.AbstractEnvironmentRule#doValidate()
   */
  @Override
  protected void doValidate() {
    // Get all FileSystemContext
    Collection<EnvironmentRuleContext> contexts = getContextsFor(FileSystemEnvironment.FILE_SYSTEM_ENVIRONMENT_ID);
    // Create a ValidationHelper.
    ValidationHelper helper = new ValidationHelper();
    for (EnvironmentRuleContext environmentRuleContext : contexts) {
      // Ignore all environment which are no ConfigurationDirectoriesRuleContext
      ConfigurationDirectoriesRuleContext envContext = null;
      try {
        envContext = (ConfigurationDirectoriesRuleContext) environmentRuleContext;
      } catch (ClassCastException exception) {
        // Ignore this FileSystemEnvironment
        continue;
      }
      // Get the configurationDirectory path
      String confDirectoryPath = envContext._configurationDirectoryAbsolutePath;
      // Create a AdditionalPath class for the confDirectory data
      AdditionalPath additionalPath =
          helper.newAdditionalPath(confDirectoryPath, Messages.FileSystemEnvironmentRule_ConfDirectory_Contained_By_Environment_Error_Pattern,
              Messages.FileSystemEnvironmentRule_ConfDirectory_Contains_Environment_Error_Pattern,
              Messages.FileSystemEnvironmentRule_Path_Identical_To_ConfDirectory_Error_Pattern);
      // Get all the conflicts
      Map<EnvironmentAttributes, List<String>> messageMap = helper.validateFileSystemEnvironmentPaths(envContext._attributes, additionalPath);
      // Display for each environment the error messages
      for (Map.Entry<EnvironmentAttributes, List<String>> entry : messageMap.entrySet()) {
        for (String message : entry.getValue()) {
          addFailure(entry.getKey(), message);
        }
      }
    }
  }
}