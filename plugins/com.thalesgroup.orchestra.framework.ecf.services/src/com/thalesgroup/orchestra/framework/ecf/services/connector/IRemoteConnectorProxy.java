/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ecf.services.connector;

import java.io.Serializable;

import com.thalesgroup.orchestra.framework.connector.CommandContext;
import com.thalesgroup.orchestra.framework.connector.CommandStatus;
import com.thalesgroup.orchestra.framework.ecf.services.handler.ClientConnectorHandler;
import com.thalesgroup.orchestra.framework.lib.constants.ICommandConstants;

/**
 * This is a pure connection proxy.<br>
 * This is not intended to be implemented by the client side.<br>
 * Instead implement {@link IRemoteConnector} and register it using {@link ClientConnectorHandler#register(IRemoteConnector)}.
 * @author t0076261
 */
public interface IRemoteConnectorProxy extends Serializable {
  /**
   * Close hosting application.<br>
   * The method is provided here, as this is an in-process implementation, that can not be invoked by the launcher (outside the application VM).
   * @return A serialized form of a {@link CommandStatus}.
   * @throws Exception Do not use as an applicative result. Instead return a {@link CommandStatus} to fully describe the result. Exceptions are limited to
   *           technical issues.
   */
  public String closeApplication() throws Exception;

  /**
   * Create new root artifacts, as defined by specified context.
   * @param context_p A serialized form of a {@link CommandContext}.
   * @return A serialized form of a {@link CommandStatus}.
   * @throws Exception Do not use as an applicative result. Instead return a {@link CommandStatus} to fully describe the result. Exceptions are limited to
   *           technical issues.
   */
  public String create(String context_p) throws Exception;

  /**
   * Export documentary content for specified context.
   * @param context_p A serialized form of a {@link CommandContext}.
   * @return A serialized form of a {@link CommandStatus}.
   * @throws Exception Do not use as an applicative result. Instead return a {@link CommandStatus} to fully describe the result. Exceptions are limited to
   *           technical issues.
   */
  public String documentaryExport(String context_p) throws Exception;

  /**
   * Execute a specific command for specified context.<br>
   * A specific command is a command that is not having a direct implementation in the connector.<br>
   * This can be specified by a unique command type (in the {@link CommandContext}).<br>
   * This is up to the application, and the connector implementation to define the expected behavior and result.
   * @param context_p A serialized form of a {@link CommandContext}.
   * @return A serialized form of a {@link CommandStatus}.
   * @throws Exception Do not use as an applicative result. Instead return a {@link CommandStatus} to fully describe the result. Exceptions are limited to
   *           technical issues.
   */
  public String executeSpecificCommand(String context_p) throws Exception;

  /**
   * Export sub-artifacts list for specified context.
   * @param context_p A serialized form of a {@link CommandContext}.
   * @return A serialized form of a {@link CommandStatus}.
   * @throws Exception Do not use as an applicative result. Instead return a {@link CommandStatus} to fully describe the result. Exceptions are limited to
   *           technical issues.
   */
  public String expand(String context_p) throws Exception;

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
   * @param context_p A serialized form of a {@link CommandContext}.
   * @return A serialized form of a {@link CommandStatus}.
   * @throws Exception Do not use as an applicative result. Instead return a {@link CommandStatus} to fully describe the result. Exceptions are limited to
   *           technical issues.
   */
  public String importArtifacts(String context_p) throws Exception;

  /**
   * Are specified artifacts (in specified context) currently handled by connected application ?
   * @param context_p A serialized form of a {@link CommandContext}.
   * @return A serialized form of a {@link CommandStatus}.
   * @throws Exception Do not use as an applicative result. Instead return a {@link CommandStatus} to fully describe the result. Exceptions are limited to
   *           technical issues.
   */
  public String isHandlingArtifacts(String context_p) throws Exception;

  /**
   * Export LM content for specified context.
   * @param context_p A serialized form of a {@link CommandContext}.
   * @return A serialized form of a {@link CommandStatus}.
   * @throws Exception Do not use as an applicative result. Instead return a {@link CommandStatus} to fully describe the result. Exceptions are limited to
   *           technical issues.
   */
  public String lmExport(String context_p) throws Exception;

  /**
   * Navigate to artifacts, as defined by specified context.
   * @param context_p A serialized form of a {@link CommandContext}.
   * @return A serialized form of a {@link CommandStatus}.
   * @throws Exception Do not use as an applicative result. Instead return a {@link CommandStatus} to fully describe the result. Exceptions are limited to
   *           technical issues.
   */
  public String navigate(String context_p) throws Exception;

  /**
   * Search for artifacts, as defined by specified context.
   * @param context_p A {@link CommandContext} that refers both to a command and its artifacts.<br>
   *          From PUCI description, the search URIs format is as follow :<br>
   *          <ul>
   *          <li>An absolute URI that stands for the seed of the search. Only its sub-tree will be considered.</li>
   *          <li>A <code>relativePath</code> parameter that contains the relative path for the search, separated by
   *          {@link ICommandConstants#DEFAULT_PARAMETER_SEPARATOR} string.</li>
   *          <li>A <code>keepOpen</code> parameter that specifies the application behavior. Either it should remain opened <code>true</code> or be closed just
   *          after the search service <code>false</code>.</li>
   *          </ul>
   * @return A not <code>null</code> {@link CommandStatus} describing results for each artifact (see {@link CommandContext} for artifacts list) as children
   *         statuses.<br>
   *         More precisely :<br>
   *         <ul>
   *         <li>A first status with overall result (Ok, Warning, Error).
   *         <ul>
   *         <li>One status per provided URI with result (Ok, Error).
   *         <ul>
   *         <li>N sub-statuses with resulting URIs, where <code>0 == N</code> when parent status result is Error, <code>1 == N</code> when the search returned
   *         only one possible result, and <code>1 < N</code> when more than one result is returned.
   *         </ul>
   *         </li>
   *         </ul>
   *         </li>
   *         </ul>
   * @throws Exception Do not use as an applicative result. Instead return a {@link CommandStatus} to fully describe the result. Exceptions are limited to
   *           technical issues.
   */
  public String search(String context_p) throws Exception;
}