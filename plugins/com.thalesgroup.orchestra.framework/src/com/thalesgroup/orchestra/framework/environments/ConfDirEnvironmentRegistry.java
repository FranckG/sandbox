/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environments;

import org.eclipse.core.runtime.IProgressMonitor;

import com.thalesgroup.orchestra.framework.connector.CommandContext;
import com.thalesgroup.orchestra.framework.connector.CommandStatus;
import com.thalesgroup.orchestra.framework.environment.registry.EnvironmentRegistry;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariable;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;

/**
 * An environment registry dedicated to configuration directories handling.<br>
 * Does use the {@link EnvironmentRegistry} as a pool of descriptors.<br>
 * Maintain its own list of active environments, based on current context.
 * @author t0076261
 */
public class ConfDirEnvironmentRegistry extends AbstractEnvironmentsRegistry {
  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironment#getArtifactsMetadata(com.thalesgroup.orchestra.framework.connector.CommandContext,
   *      org.eclipse.core.runtime.IProgressMonitor)
   */
  public CommandStatus getArtifactsMetadata(CommandContext context_p, IProgressMonitor progressMonitor_p) {
    // This is not applicable to configuration directories.
    return null;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environments.AbstractEnvironmentsRegistry#getEnvironmentsSourceVariable(com.thalesgroup.orchestra.framework.model.contexts.Context)
   */
  @Override
  protected EnvironmentVariable getEnvironmentsSourceVariable(Context currentContext_p) {
    return (EnvironmentVariable) DataUtil.getVariable(DataUtil.__CONFIGURATIONDIRECTORIES_VARIABLE_NAME, currentContext_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironment#getRootArtifacts(com.thalesgroup.orchestra.framework.connector.CommandContext,
   *      org.eclipse.core.runtime.IProgressMonitor)
   */
  public CommandStatus getRootArtifacts(CommandContext context_p, IProgressMonitor progressMonitor_p) {
    // This is not applicable to configuration directories.
    return null;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironment#isHandlingArtifacts(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  public CommandStatus isHandlingArtifacts(CommandContext context_p) {
    // This is not applicable to configuration directories.
    return null;
  }
}