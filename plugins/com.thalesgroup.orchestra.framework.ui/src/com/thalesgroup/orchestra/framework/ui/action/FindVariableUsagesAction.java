/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.action;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.BaseSelectionListenerAction;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.model.IEditionContextProvider;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import com.thalesgroup.orchestra.framework.root.ui.AbstractRunnableWithDisplay;
import com.thalesgroup.orchestra.framework.root.ui.DisplayHelper;
import com.thalesgroup.orchestra.framework.ui.dialog.FindVariableUsagesDialog;

/**
 * @author s0040806
 */
public class FindVariableUsagesAction extends BaseSelectionListenerAction {

  /**
   * Edition context provider.
   */
  private IEditionContextProvider _contextProvider;

  public FindVariableUsagesAction(Shell shell_p, IEditionContextProvider contextProvider_p) {
    super(Messages.FindVariableUsagesAction_Action_Text);
    _contextProvider = contextProvider_p;
  }

  /**
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    AbstractRunnableWithDisplay displayRunnable = new AbstractRunnableWithDisplay() {
      public void run() {
        final Display display = PlatformUI.getWorkbench().getDisplay();
        @SuppressWarnings("synthetic-access")
        Shell shell = display.getActiveShell();
        FormToolkit toolkit = new FormToolkit(PlatformUI.getWorkbench().getDisplay());
        toolkit.setBackground(shell.getBackground());
        Object selection = getStructuredSelection().getFirstElement();
        Variable variable;
        if (selection instanceof VariableValue) {
          variable = (Variable) ((VariableValue) selection).eContainer();
        } else {
          variable = (Variable) selection;
        }
        FindVariableUsagesDialog dialog = new FindVariableUsagesDialog(shell, toolkit, variable, _contextProvider.getEditionContext());
        ;
        dialog.open();
      }
    };
    DisplayHelper.displayRunnable(displayRunnable, false);
  }

  /**
   * @see org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org.eclipse.jface.viewers.IStructuredSelection)
   */
  @Override
  protected boolean updateSelection(IStructuredSelection selection_p) {
    boolean uniqueSelection = (1 == selection_p.size());
    return uniqueSelection
           && _contextProvider.getEditionContext() != null
           && (ContextsPackage.Literals.VARIABLE.isInstance(selection_p.getFirstElement()) || ContextsPackage.Literals.VARIABLE_VALUE.isInstance(selection_p
               .getFirstElement()));
  }
}
