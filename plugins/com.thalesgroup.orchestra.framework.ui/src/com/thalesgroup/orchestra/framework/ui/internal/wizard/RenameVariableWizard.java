/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.wizard;

import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.command.RenameVariableCommand;
import com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizard;
import com.thalesgroup.orchestra.framework.ui.internal.wizard.page.RenameVariablePage;

/**
 * @author s0040806
 */
public class RenameVariableWizard extends AbstractFormsWizard {

  protected Variable _variable;
  protected Context _context;

  private RenameVariablePage _page;

  public RenameVariableWizard(Variable variable_p, Context context_p) {
    _variable = variable_p;
    _context = context_p;
    setWindowTitle(Messages.RenameVariableWizard_Wizard_Title);
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#addPages()
   */
  @Override
  public void addPages() {
    _page = new RenameVariablePage(_variable, _context);
    addPage(_page);
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#performFinish()
   */
  @Override
  public boolean performFinish() {
    ModelHandlerActivator.getDefault().getEditingDomain().getCommandStack().execute(new RenameVariableCommand(_variable, _context, _page.getVariableName()));
    return true;
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#needsPreviousAndNextButtons()
   */
  @Override
  public boolean needsPreviousAndNextButtons() {
    return false;
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#needsProgressMonitor()
   */
  @Override
  public boolean needsProgressMonitor() {
    return false;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.common.ui.wizards.AbstractChangeWizard#getCommandLabel()
   */
  protected String getCommandLabel() {
    // TODO Auto-generated method stub
    return null;
  }
}
