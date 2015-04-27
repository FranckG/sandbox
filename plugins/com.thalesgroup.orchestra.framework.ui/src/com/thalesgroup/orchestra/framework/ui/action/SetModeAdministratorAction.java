/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.action;

import com.thalesgroup.orchestra.framework.project.ProjectActivator;


/**
 * Set mode to administrator.
 * @author s0011584
 */
public class SetModeAdministratorAction extends SetModeAction {
  public SetModeAdministratorAction() {
    super(Messages.SetModeAdministratorAction_Action_Text);
  }

  public void modeChanged(boolean newAdministratorState_p) {
    setChecked(newAdministratorState_p);
  }
  
  @Override
  public void run() {
    // Not consistent state : UI has unchecked action.
    if (ProjectActivator.getInstance().isAdministrator() && !isChecked()) {
      setChecked(true);
    } else {
      super.run();
    }
  }

}
