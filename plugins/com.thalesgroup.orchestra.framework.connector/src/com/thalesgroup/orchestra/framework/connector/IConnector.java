/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.connector;

import java.util.Map;

import com.thalesgroup.orchestra.framework.lib.constants.ICommandConstants;

/**
 * Connector root interface.<br>
 * Defines Orchestra services along with the possibility to execute specific unknown commands.<br>
 * In this case, this is up to the application, and the connector implementation to define the expected behavior and result.
 * @author t0076261
 */
public interface IConnector {
  /**
   * Create new root artifacts, as defined by specified context.
   * @param context_p A {@link CommandContext} that refers both to a command and its artifacts.
   * @return A not <code>null</code> {@link CommandStatus} describing results for each artifact (see {@link CommandContext} for artifacts list) as children
   *         statuses.<br>
   *         If the {@link CommandContext} specified export path is used, its content <b>must</b> be a Generic Export Format result.
   * @throws Exception Do not use as an applicative result. Instead return a {@link CommandStatus} to fully describe the result. Exceptions are limited to
   *           technical issues.
   */
  public CommandStatus create(CommandContext context_p) throws Exception;

  /**
   * Export documentary content for specified context.
   * @param context_p A {@link CommandContext} that refers both to a command and its artifacts.
   * @return A not <code>null</code> {@link CommandStatus} describing results for each artifact (see {@link CommandContext} for artifacts list) as children
   *         statuses.<br>
   *         This is also expected that the {@link CommandContext} specified export path be filled with a Generic Export Format result.
   * @throws Exception Do not use as an applicative result. Instead return a {@link CommandStatus} to fully describe the result. Exceptions are limited to
   *           technical issues.
   */
  public CommandStatus documentaryExport(CommandContext context_p) throws Exception;

  /**
   * Execute a specific command for specified context.<br>
   * A specific command is a command that is not having a direct implementation in the connector.<br>
   * This can be specified by a unique command type (in the {@link CommandContext}).<br>
   * This is up to the application, and the connector implementation to define the expected behavior and result.
   * @param context_p A {@link CommandContext} that refers both to a command and its artifacts.
   * @return A not <code>null</code> {@link CommandStatus} describing results for each artifact (see {@link CommandContext} for artifacts list) as children
   *         statuses.<br>
   *         If the {@link CommandContext} specified export path is used, its content <b>must</b> be a Generic Export Format result.
   * @throws Exception Do not use as an applicative result. Instead return a {@link CommandStatus} to fully describe the result. Exceptions are limited to
   *           technical issues.
   */
  public CommandStatus executeSpecificCommand(CommandContext context_p) throws Exception;

  /**
   * Export sub-artifacts list for specified context.
   * @param context_p A {@link CommandContext} that refers both to a command and its artifacts.
   * @return A not <code>null</code> {@link CommandStatus} describing results for each artifact (see {@link CommandContext} for artifacts list) as children
   *         statuses.<br>
   *         This is also expected that the {@link CommandContext} specified export path be filled with a Generic Export Format result.
   * @throws Exception Do not use as an applicative result. Instead return a {@link CommandStatus} to fully describe the result. Exceptions are limited to
   *           technical issues.
   */
  public CommandStatus expand(CommandContext context_p) throws Exception;

  /**
   * Export LM content for specified context.
   * @param context_p A {@link CommandContext} that refers both to a command and its artifacts.
   * @return A not <code>null</code> {@link CommandStatus} describing results for each artifact (see {@link CommandContext} for artifacts list) as children
   *         statuses.<br>
   *         This is also expected that the {@link CommandContext} specified export path be filled with a Generic Export Format result.
   * @throws Exception Do not use as an applicative result. Instead return a {@link CommandStatus} to fully describe the result. Exceptions are limited to
   *           technical issues.
   */
  public CommandStatus lmExport(CommandContext context_p) throws Exception;

  /**
   * Navigate to artifacts, as defined by specified context.
   * @param context_p A {@link CommandContext} that refers both to a command and its artifacts.
   * @return A not <code>null</code> {@link CommandStatus} describing results for each artifact (see {@link CommandContext} for artifacts list) as children
   *         statuses.
   * @throws Exception Do not use as an applicative result. Instead return a {@link CommandStatus} to fully describe the result. Exceptions are limited to
   *           technical issues.
   */
  public CommandStatus navigate(CommandContext context_p) throws Exception;

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
  public CommandStatus search(CommandContext context_p) throws Exception;

  /**
   * Set connector specific options (at creation time).
   * @param options_p A {@link Map} of options specific to this connector.
   */
  public void setOptions(Map<String, ?> options_p);
}