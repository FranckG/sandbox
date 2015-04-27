/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.action;

import com.thalesgroup.orchestra.framework.project.ProjectActivator;

/**
 * Set mode to User.
 * @author s0011584
 */
public class SetModeUserAction extends SetModeAction {
  public SetModeUserAction() {
    super(Messages.SetModeUserAction_Action_Text0);
  }

  public void modeChanged(boolean newAdministratorState_p) {
    setChecked(!newAdministratorState_p);
  }
  
  @Override
  public void run() {
    // Not consistent state : UI has unchecked action.
    if (!ProjectActivator.getInstance().isAdministrator() && !isChecked()) {
      setChecked(true);
    } else {
      super.run();
    }
  }
}
