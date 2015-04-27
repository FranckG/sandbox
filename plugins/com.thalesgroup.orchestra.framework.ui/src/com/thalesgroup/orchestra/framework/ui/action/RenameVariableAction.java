/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.action;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Shell;

import com.thalesgroup.orchestra.framework.model.IEditionContextProvider;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import com.thalesgroup.orchestra.framework.ui.internal.wizard.RenameVariableWizard;

/**
 * @author s0040806
 */
public class RenameVariableAction extends AbstractWizardBasedAction {

  /**
   * Edition context provider.
   */
  private IEditionContextProvider _contextProvider;

  public RenameVariableAction(Shell shell_p, IEditionContextProvider contextProvider_p) {
    super(Messages.RenameVariableAction_Action_Text);
    setShell(shell_p);
    _contextProvider = contextProvider_p;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.action.AbstractWizardBasedAction#createWizard()
   */
  @Override
  protected Wizard createWizard() {
    return new RenameVariableWizard((Variable) getStructuredSelection().getFirstElement(), _contextProvider.getEditionContext());
  }

  /**
   * Check whether a variable is inherited
   * @param variable_p
   * @param editionContext_p
   * @return <code>true</true> if variable is inherited
   */
  protected boolean isInheritedVariable(AbstractVariable variable_p, Context editionContext_p) {
    Context variableContext = ModelUtil.getContext(variable_p);
    return variableContext != editionContext_p;
  }

  /**
   * Shall the action be enabled?
   * @param selection_p
   * @param editionContext_p
   * @return <code>true</true> if action shell be enabled
   */
  protected boolean isActionEnabled(Object selection_p, Context editionContext_p) {
    AbstractVariable variable;
    if (ContextsPackage.Literals.VARIABLE_VALUE.isInstance(selection_p)) {
      variable = (AbstractVariable) ((VariableValue) selection_p).eContainer();
    } else if (ContextsPackage.Literals.ABSTRACT_VARIABLE.isInstance(selection_p)) {
      variable = (AbstractVariable) selection_p;
    } else {
      return false;
    }

    if (ContextsPackage.Literals.OVERRIDING_VARIABLE.isInstance(variable)) {
      variable = ((OverridingVariable) variable).getOverriddenVariable();
    }

    return !isInheritedVariable(variable, editionContext_p);
  }

  /**
   * @see org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org.eclipse.jface.viewers.IStructuredSelection)
   */
  @Override
  protected boolean updateSelection(IStructuredSelection selection_p) {
    boolean uniqueSelection = (1 == selection_p.size());
    if (uniqueSelection) {
      Object selection = selection_p.getFirstElement();
      Context editionContext = _contextProvider.getEditionContext();
      return editionContext != null && isActionEnabled(selection, editionContext);
    }
    return false;
  }
}
