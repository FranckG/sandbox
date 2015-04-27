/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.action;

import org.eclipse.jface.action.Action;

import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.command.SynchronizeAllCommand;
import com.thalesgroup.orchestra.framework.ui.activator.OrchestraFrameworkUiActivator;

/**
 * Synchronise all contexts of the workspace
 * @author s0040806
 */
public class SynchronizeAllAction extends Action {
  /**
   * Constructor.
   */
  public SynchronizeAllAction() {
    super(Messages.SynchronizeAllAction_Action_Text);
    setImageDescriptor(OrchestraFrameworkUiActivator.getDefault().getImageDescriptor("refresh.gif")); //$NON-NLS-1$
  }

  /**
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    SynchronizeAllCommand command = new SynchronizeAllCommand();
    ModelHandlerActivator.getDefault().getEditingDomain().getCommandStack().execute(command);
  }
}
