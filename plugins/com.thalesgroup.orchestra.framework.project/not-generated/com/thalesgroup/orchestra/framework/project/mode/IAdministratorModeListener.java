/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.project.mode;

/**
 * Implement this interface in order to be alerted when the end-user changes the mode. The mode can be either:
 * <ul>
 * <li>administrator</li>
 * <li>not administrator (user)</li>
 * </ul>
 * The default value is <code>administrator</code>.
 * @author s0011584
 */
public interface IAdministratorModeListener {
  /**
   * Called after the mode has been changed by the end-user.
   * @param newAdministratorState the new administrator state. <br />
   *          If <code>true</code> then the user changed from user to administrator mode, otherwise, the user changed the mode from administrator to user mode.
   */
  void modeChanged(boolean newAdministratorState);
}
