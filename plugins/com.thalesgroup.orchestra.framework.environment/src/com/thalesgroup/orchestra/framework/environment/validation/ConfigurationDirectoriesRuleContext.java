/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment.validation;

/**
 * A context dedicated to configuration directories validation.
 * @author t0076261
 */
public class ConfigurationDirectoriesRuleContext extends EnvironmentRuleContext {
  /**
   * The absolute path located in \Orchestra\ConfigurationDirectory mono-valued variable.
   */
  public String _configurationDirectoryAbsolutePath;
}