/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.project.mode;

import org.eclipse.ui.PlatformUI;

import com.thalesgroup.orchestra.framework.project.ProjectActivator;

/**
 * Administrator mode handler.
 * @author t0076261
 */
public class AdministratorModeHandler {
  /**
   * Flip administrator mode to its current opposite.<br>
   * That is, if the mode is currently <code>Administrator</code>, flip to <code>User</code>.<br>
   * And if the mode is currently <code>User</code>, flip to <code>Administrator</code>.<br>
   * If the workbench is running, the user is asked to save modifications before proceeding.
   * @return
   */
  public static boolean changeAdministratorMode() {
    boolean proceed = true;
    if (PlatformUI.isWorkbenchRunning()) {
      proceed = PlatformUI.getWorkbench().saveAllEditors(true);
    }
    if (proceed) {
      return ProjectActivator.getInstance().changeAdministratorMode();
    }
    return false;
  }
}