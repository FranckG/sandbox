/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.action;

import org.eclipse.jface.action.ControlContribution;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.project.mode.IAdministratorModeListener;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;

/**
 * Add a composite to status line to display the current mode.
 * @author s0011584
 */
public class AdministratorModeStatusLine extends ControlContribution implements IAdministratorModeListener {
  private Label _control;
  private Composite _composite;

  public AdministratorModeStatusLine() {
    super(AdministratorModeStatusLine.class.getName());
  }

  @Override
  protected Control createControl(Composite parent_p) {
    _composite = new Composite(parent_p, SWT.NONE);
    _composite.setLayout(new GridLayout());
    _control = new Label(_composite, SWT.HORIZONTAL);
    _control.setLayoutData(new GridData());
    _control.setToolTipText(Messages.AdministratorModeStatusLine_ToolTip_Text);
    // Initialize text.
    modeChanged(ProjectActivator.getInstance().isAdministrator());
    // Force control size.
    FormHelper.forceControlSize(_control, 25, 1);
    return _composite;
  }

  public void modeChanged(boolean newAdministratorState_p) {
    if (newAdministratorState_p) {
      _control.setText(Messages.AdministratorModeStatusLine_Text_Administrator);
    } else {
      _control.setText(Messages.AdministratorModeStatusLine_Text_User);
    }
    _control.update();
  }
}