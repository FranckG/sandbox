/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.action;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.project.mode.AdministratorModeHandler;
import com.thalesgroup.orchestra.framework.project.mode.IAdministratorModeListener;
import com.thalesgroup.orchestra.framework.ui.activator.OrchestraFrameworkUiActivator;

/**
 * This action manages the switch mode between administration mode and user mode.
 * @author s0011584
 */
public class AdministratorModeAction extends Action implements IAdministratorModeListener {
  public AdministratorModeAction() {
    super(ICommonConstants.EMPTY_STRING, SWT.TOGGLE);
    setImageDescriptor(OrchestraFrameworkUiActivator.getDefault().getImageDescriptor("shield.gif")); //$NON-NLS-1$
    setId(this.getClass().getCanonicalName());
    modeChanged(ProjectActivator.getInstance().isAdministrator());
  }

  public void modeChanged(boolean newAdministratorState_p) {
    setChecked(newAdministratorState_p);
    setToolTipText(newAdministratorState_p ? Messages.AdministratorModeAction_ToolTip_UserMode : Messages.AdministratorModeAction_ToolTip_AdministratorMode);
  }

  @Override
  public void run() {
    boolean previousChecked = isChecked();
    boolean proceed = AdministratorModeHandler.changeAdministratorMode();
    if (!proceed) {
      setChecked(!previousChecked);
    }
  }
}