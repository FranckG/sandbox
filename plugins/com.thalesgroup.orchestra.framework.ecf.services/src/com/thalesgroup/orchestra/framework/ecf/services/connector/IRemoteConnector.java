/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ecf.services.connector;

import org.eclipse.core.runtime.IStatus;

import com.thalesgroup.orchestra.framework.connector.CommandContext;
import com.thalesgroup.orchestra.framework.connector.CommandStatus;
import com.thalesgroup.orchestra.framework.connector.IConnector;

/**
 * Connector interface dedicated to remote services.<br>
 * Must be instantiated by clients so as to provide such remote connection services.<br>
 * Use provided publishing class to subscribe/unsubscribe a remote implementation.
 * @author t0076261
 */
public interface IRemoteConnector extends IConnector {
  /**
   * Close hosting application.<br>
   * The method is provided here, as this is an in-process implementation, that can not be invoked by the launcher (outside the application VM).<br>
   * <b>Implementation details :</b><br>
   * Make sure the implementation does return from this method before stopping the application.<br>
   * This is required so as to be able to get a result, and not to return an error at server side.
   * @return A {@link CommandStatus} with a severity set to {@link IStatus#OK} if command is take into account, another severity if not.
   * @throws Exception Do not use as an applicative result. Instead return a {@link CommandStatus} to fully describe the result. Exceptions are limited to
   *           technical issues.
   */
  public CommandStatus closeApplication() throws Exception;

  /**
   * Get handled type by this connector.<br>
   * This refers to the type defined in association and connector configuration file.
   * @return The handled type, as a not <code>null</code> {@link String}.
   * @throws Exception Do not use as an applicative result. Instead return a {@link CommandStatus} to fully describe the result. Exceptions are limited to
   *           technical issues.
   */
  public String getType() throws Exception;

  /**
   * Import specified artifacts (in specified context) within the application environment.
   * @param context_p
   * @return A {@link CommandStatus} with a severity set to {@link IStatus#OK} if all artifacts are handled, another severity if at least one artifact is not
   *         handled (sub-statuses can be provided).
   * @throws Exception Do not use as an applicative result. Instead return a {@link CommandStatus} to fully describe the result. Exceptions are limited to
   *           technical issues.
   */
  public CommandStatus importArtifacts(CommandContext context_p) throws Exception;

  /**
   * Are specified artifacts (in specified context) currently handled by connected application ?
   * @param context_p
   * @return A {@link CommandStatus} with a severity set to {@link IStatus#OK} if all artifacts are handled, another severity if at least one artifact is not
   *         handled (sub-statuses can be provided).<br>
   *         If sub-statuses are provided per artifact, only those with a severity set to {@link IStatus#ERROR} or {@link IStatus#WARNING} will be imported.
   * @throws Exception Do not use as an applicative result. Instead return a {@link CommandStatus} to fully describe the result. Exceptions are limited to
   *           technical issues.
   */
  public CommandStatus isHandlingArtifacts(CommandContext context_p) throws Exception;
}