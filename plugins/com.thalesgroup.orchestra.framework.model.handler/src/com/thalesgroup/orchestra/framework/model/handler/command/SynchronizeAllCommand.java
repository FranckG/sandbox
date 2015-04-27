/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.handler.command;


/**
 * Synchronize all contexts at once command.<br>
 * This command can not be undone.<br>
 * @author s0040806
 */
public class SynchronizeAllCommand extends SynchronizeCommand {

  public SynchronizeAllCommand() {
    this(true);
  }

  public SynchronizeAllCommand(boolean allowUserInteraction_p) {
    super(getWorkspaceContextList(), allowUserInteraction_p);
  }
}
