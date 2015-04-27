/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.action;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;

import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.project.mode.AdministratorModeHandler;
import com.thalesgroup.orchestra.framework.project.mode.IAdministratorModeListener;

/**
 * Common action for set mode.
 * @author s0011584
 */
public abstract class SetModeAction extends Action implements IAdministratorModeListener {
  public SetModeAction(String text_p) {
    super(text_p, SWT.CHECK);
    modeChanged(ProjectActivator.getInstance().isAdministrator());
  }

  /**
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    AdministratorModeHandler.changeAdministratorMode();
  }
}