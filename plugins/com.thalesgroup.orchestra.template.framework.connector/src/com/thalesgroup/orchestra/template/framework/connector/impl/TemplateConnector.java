/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.template.framework.connector.impl;

import com.thalesgroup.orchestra.framework.connector.AbstractConnector;
import com.thalesgroup.orchestra.framework.connector.CommandContext;
import com.thalesgroup.orchestra.framework.connector.CommandStatus;

/**
 * A connector that runs directly within the Framework virtual machine.<br>
 * Please refer to the IDD for common pitfalls.
 * @author t0076261
 */
public class TemplateConnector extends AbstractConnector {
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
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#navigate(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  public CommandStatus navigate(CommandContext context_p) throws Exception {
    return super.createStatusForUnsupportedCommand(context_p);
  }
}