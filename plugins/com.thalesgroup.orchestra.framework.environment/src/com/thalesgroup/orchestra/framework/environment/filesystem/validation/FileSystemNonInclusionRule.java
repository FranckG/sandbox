/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment.filesystem.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.thalesgroup.orchestra.framework.environment.EnvironmentAttributes;
import com.thalesgroup.orchestra.framework.environment.filesystem.FileSystemEnvironment;
import com.thalesgroup.orchestra.framework.environment.registry.EnvironmentRegistry.EnvironmentDescriptor;
import com.thalesgroup.orchestra.framework.environment.validation.AbstractEnvironmentRule;
import com.thalesgroup.orchestra.framework.environment.validation.ConfigurationDirectoriesRuleContext;
import com.thalesgroup.orchestra.framework.environment.validation.EnvironmentRuleContext;

/**
 * Rule to verify that the paths provided by all the environments whom {@link EnvironmentDescriptor} is of type 'File System' are not hierarchically linked
 * between them
 * @author S0032874
 */
public class FileSystemNonInclusionRule extends AbstractEnvironmentRule {

  /**
   * {@link FileSystemEnvironment} category name
   */
  private final String _fileSystemCategory = "File System"; //$NON-NLS-1$

  /**
   * @see com.thalesgroup.orchestra.framework.environment.validation.AbstractEnvironmentRule#doValidate()
   */
  @Override
  protected void doValidate() {
    // Get all FileSystem Context, i.e. all environment which contribute to "File System" category in the descriptor
    Collection<EnvironmentRuleContext> contexts = getContextsByCategory(_fileSystemCategory);
    // Create a ValidationHelper.
    ValidationHelper helper = new ValidationHelper();
    List<EnvironmentAttributes> environmentList = new ArrayList<EnvironmentAttributes>();
    for (EnvironmentRuleContext environmentRuleContext : contexts) {
      // Ignore the ConfigurationDirectories FileSystemEnvironment
      if (environmentRuleContext instanceof ConfigurationDirectoriesRuleContext) {
        continue;
      }
      // Retrieve all the FileSystemEnvironment
      environmentList.addAll(environmentRuleContext._attributes);
    }
    // Get all the conflicts
    Map<EnvironmentAttributes, List<String>> messageMap = helper.validateFileSystemEnvironmentPaths(environmentList, null);
    // Display for each environment the error messages
    for (Map.Entry<EnvironmentAttributes, List<String>> entry : messageMap.entrySet()) {
      for (String message : entry.getValue()) {
        addFailure(entry.getKey(), message);
      }
    }
  }
}
