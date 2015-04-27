/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.framework.connector.CommandContext;
import com.thalesgroup.orchestra.framework.connector.CommandStatus;
import com.thalesgroup.orchestra.framework.environment.registry.EnvironmentRegistry;
import com.thalesgroup.orchestra.framework.lib.constants.ICommandConstants;
import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;

/**
 * @author t0076261
 */
public abstract class AbstractEnvironment implements IEnvironment {
  /**
   * Attributes map.
   */
  private Map<String, String> _attributes;
  /**
   * Declaration id. See {@link EnvironmentRegistry} for details about environment ids.
   */
  private String _declarationId;
  /**
   * Associated {@link IEnvironmentHandler} implementation.
   */
  private IEnvironmentHandler _environmentHandler;
  /**
   * Runtime id. See {@link EnvironmentRegistry} for details about environment ids.
   */
  private String _runtimeId;
  /**
   * Environment string representation.
   */
  protected String _stringRepresentation;

  /**
   * Constructor.
   */
  public AbstractEnvironment() {
    _attributes = new HashMap<String, String>(0);
  }

  /**
   * Compute {@link String} representation, as returned by {@link #toString()} method, at attributes set time.
   * @return <code>null</code> if no representation can be computed at call time.<br>
   *         Default implementation does invoke (corresponding) {@link IEnvironmentHandler#toString(Map)}.
   */
  protected String computeStringRepresentation() {
    return EnvironmentActivator.getInstance().getEnvironmentRegistry().getEnvironmentDescriptor(_declarationId).getValue().getHandler().toString(_attributes);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironment#getAttributes()
   */
  public Map<String, String> getAttributes() {
    return new HashMap<String, String>(_attributes);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironment#getDeclarationId()
   */
  public String getDeclarationId() {
    return _declarationId;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironment#getEnvironmentHandler()
   */
  public IEnvironmentHandler getEnvironmentHandler() {
    return _environmentHandler;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironment#getRuntimeId()
   */
  public String getRuntimeId() {
    return _runtimeId;
  }

  /**
   * Handle specified attributes at set time.<br>
   * This is an opportunity to handle attributes as reloaded from the context model before using them in this environment instance.
   * @param context_p Envionment context.
   * @return A not <code>null</code> map of attributes.<br>
   *         Default implementation does return specified attributes.
   */
  @Deprecated
  protected Map<String, String> handleInitialAttributes(EnvironmentContext context_p, IProgressMonitor progressMonitor_p) {
    return context_p._attributes;
  }

  /**
   * Handle specified attributes at set time.<br>
   * This is an opportunity to handle attributes as reloaded from the context model before using them in this environment instance.
   * @param context_p Envionment context.
   * @return Couple value of
   *         <ul>
   *         <li>A not <code>null</code> map of attributes.<br>
   *         Default implementation does return specified attributes.
   *         <li>A return status
   *         </ul>
   */
  protected Couple<Map<String, String>, IStatus> handleInitialAttributesWithStatus(EnvironmentContext context_p, IProgressMonitor progressMonitor_p) {
    return new Couple<Map<String, String>, IStatus>(handleInitialAttributes(context_p, progressMonitor_p), Status.OK_STATUS);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironment#isBaselineCompliant()
   */
  public boolean isBaselineCompliant() {
    // By default, an environment should be able to handle baselines.
    return true;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironment#setAttributes(com.thalesgroup.orchestra.framework.environment.EnvironmentContext,
   *      org.eclipse.core.runtime.IProgressMonitor)
   */
  public IStatus setAttributes(EnvironmentContext context_p, IProgressMonitor progressMonitor_p) {
    // Precondition.
    if (null == context_p) {
      return null;
    }
    // Replace parameters with specified ones.
    _attributes.clear();
    Couple<Map<String, String>, IStatus> attributes = handleInitialAttributesWithStatus(context_p, progressMonitor_p);
    _attributes.putAll(attributes.getKey());
    // Compute string representation.
    _stringRepresentation = computeStringRepresentation();

    return attributes.getValue();
  }

  /**
   * Set declaration id.
   * @param declarationId_p A not <code>null</code> declaration id.
   */
  public void setDeclarationId(String declarationId_p) {
    _declarationId = declarationId_p;
  }

  /**
   * Set environment handler.
   * @param handler_p
   */
  public void setEnvironmentHandler(IEnvironmentHandler handler_p) {
    Assert.isNotNull(handler_p);
    _environmentHandler = handler_p;
  }

  /**
   * Set environment runtime id.
   * @param
   */
  public void setRuntimeId(String runtimeId_p) {
    _runtimeId = runtimeId_p;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    if (null == _stringRepresentation) {
      return ICommonConstants.EMPTY_STRING;
    }
    return _stringRepresentation;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironment#transcript(com.thalesgroup.orchestra.framework.connector.CommandContext,
   *      org.eclipse.core.runtime.IProgressMonitor)
   */
  public CommandStatus transcript(CommandContext context_p, IProgressMonitor progressMonitor_p) {
    return new CommandStatus(IStatus.WARNING, ICommonConstants.EMPTY_STRING, null, 0);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironment#useTranscription()
   */
  public boolean useTranscription() {
    return false;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironment#executeCommand(java.lang.String,
   *      com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI)
   */
  public final CommandStatus executeCommand(String command_p, OrchestraURI uri_p) {
    if (ICommandConstants.SET_CREDENTIALS.equals(command_p)) {
      return doSetCredentials();
    }

    return doExecuteCommand(command_p, uri_p);
  }

  /**
   * Execute an environment command
   * @param command_p Command
   * @param uri_p URI
   * @return
   */
  public CommandStatus doExecuteCommand(String command_p, OrchestraURI uri_p) {
    return new CommandStatus(IStatus.OK, ICommonConstants.EMPTY_STRING, null, 0);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironment#areCredentialsSupported()
   */
  public boolean areCredentialsSupported() {
    return false;
  }

  /**
   * Set credentials
   * @return
   */
  public CommandStatus doSetCredentials() {
    return new CommandStatus(IStatus.OK, ICommonConstants.EMPTY_STRING, null, 0);
  }
}