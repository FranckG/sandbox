/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.handler.command;

/**
 * Command updater.<br>
 * Allows for updating of an external part, triggered by a command execution for instance.<br>
 * This is the typical use here :<br>
 * Playing with Cut/Copy/Paste commands does change the state of actions defined elsewhere (and unknown to this bundle).
 * @author t0076261
 */
public interface ICommandUpdater {
  /**
   * Do update something, somewhere.<br>
   * This is entirely up to the implementation.
   */
  public void forceUpdate();
}