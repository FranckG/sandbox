/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.connector;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;

/**
 * A default implementation that :<br>
 * - returns an error status for specific command. - handles the map of options.
 * @author t0076261
 */
public abstract class AbstractConnector implements IConnector {
  /**
   * Options map.
   */
  private Map<String, Object> _options;

  /**
   * Create a new error status for specified unsupported command context.<br>
   * Adds an error child status per input artifact.
   * @param context_p
   * @return
   */
  protected CommandStatus createStatusForUnsupportedCommand(CommandContext context_p) {
    CommandStatus result = createStatusForUnsupportedCommand(context_p.getCommandType());
    Artifact[] artifacts = context_p.getArtifacts();
    if (null != artifacts) {
      for (Artifact artifact : artifacts) {
        result.addChild(new CommandStatus(IStatus.ERROR, result.getMessage(), artifact.getUri(), 0));
      }
    }
    return result;
  }

  /**
   * Create a new error status for specified unsupported command.
   * @param commandType_p
   * @return
   * @deprecated use {@link #createStatusForUnsupportedCommand(CommandContext)} instead.
   */
  protected CommandStatus createStatusForUnsupportedCommand(String commandType_p) {
    String connectorMessagePart = Messages.AbstractConnector_ConnectorId_Type;
    String connectorType = getConnectorType();
    if (null == connectorType) {
      connectorType = getClass().getName();
      connectorMessagePart = Messages.AbstractConnector_ConnectorId_Implementation;
    }
    // Error, command not supported.
    return new CommandStatus(IStatus.ERROR, MessageFormat.format(Messages.AbstractConnector_ErrorMessage_CommandNotSupported, commandType_p,
        connectorMessagePart, connectorType), null, 0);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#executeSpecificCommand(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  public CommandStatus executeSpecificCommand(CommandContext context_p) throws Exception {
    return createStatusForUnsupportedCommand(context_p);
  }

  /**
   * Get connector handled type.
   * @return
   */
  protected String getConnectorType() {
    return (String) getOptions().get(IConnectorOptionConstants.CONNECTOR_TYPE);
  }

  /**
   * Get options map.
   * @return
   */
  protected Map<String, ?> getOptions() {
    if (null == _options) {
      _options = new HashMap<String, Object>(0);
    }
    return _options;
  }

  /**
   * By default, lmExport do a documentary export.
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#lmExport(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  public CommandStatus lmExport(CommandContext context_p) throws Exception {
    return documentaryExport(context_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#search(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  public CommandStatus search(CommandContext context_p) throws Exception {
    return createStatusForUnsupportedCommand(context_p);
  }

  /**
   * Specified map is either used as the whole map of options (if none already exists) or is merged with existing options map.
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#setOptions(java.util.Map)
   */
  public void setOptions(Map<String, ?> options_p) {
    if (null == _options) {
      _options = new HashMap<String, Object>(options_p);
    } else {
      _options.putAll(options_p);
    }
  }
}