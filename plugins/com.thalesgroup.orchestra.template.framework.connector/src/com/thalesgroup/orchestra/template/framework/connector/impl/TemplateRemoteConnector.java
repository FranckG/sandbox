/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.template.framework.connector.impl;

import com.thalesgroup.orchestra.framework.connector.AbstractConnector;
import com.thalesgroup.orchestra.framework.connector.CommandContext;
import com.thalesgroup.orchestra.framework.connector.CommandStatus;
import com.thalesgroup.orchestra.framework.ecf.services.connector.IRemoteConnector;
import com.thalesgroup.orchestra.template.framework.connector.application.TemplateApplication;

/**
 * A connector that runs outside the Framework virtual machine.<br>
 * See provided {@link TemplateApplication} on how/when to declare the connector to the Framework.
 * @author t0076261
 */
public class TemplateRemoteConnector extends AbstractConnector implements IRemoteConnector {
  /**
   * @see com.thalesgroup.orchestra.framework.ecf.services.connector.IRemoteConnector#closeApplication()
   */
  public CommandStatus closeApplication() throws Exception {
    CommandContext context = new CommandContext("closeApplication", null); //$NON-NLS-1$
    return super.createStatusForUnsupportedCommand(context);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#create(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  public CommandStatus create(CommandContext context_p) throws Exception {
    return super.createStatusForUnsupportedCommand(context_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#documentaryExport(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  public CommandStatus documentaryExport(CommandContext context_p) throws Exception {
    return super.createStatusForUnsupportedCommand(context_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#expand(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  public CommandStatus expand(CommandContext context_p) throws Exception {
    return super.createStatusForUnsupportedCommand(context_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ecf.services.connector.IRemoteConnector#getType()
   */
  public String getType() throws Exception {
    // TODO return supported type ID.
    return null;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ecf.services.connector.IRemoteConnector#importArtifacts(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  public CommandStatus importArtifacts(CommandContext context_p) throws Exception {
    return super.createStatusForUnsupportedCommand(context_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ecf.services.connector.IRemoteConnector#isHandlingArtifacts(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  public CommandStatus isHandlingArtifacts(CommandContext context_p) throws Exception {
    return super.createStatusForUnsupportedCommand(context_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#navigate(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  public CommandStatus navigate(CommandContext context_p) throws Exception {
    return super.createStatusForUnsupportedCommand(context_p);
  }
}