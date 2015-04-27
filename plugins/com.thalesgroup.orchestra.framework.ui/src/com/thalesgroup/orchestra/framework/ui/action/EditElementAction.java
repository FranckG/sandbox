/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.action;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Shell;

import com.thalesgroup.orchestra.framework.model.IEditionContextProvider;
import com.thalesgroup.orchestra.framework.ui.activator.OrchestraFrameworkUiActivator;
import com.thalesgroup.orchestra.framework.ui.wizard.ChangeWizardFactory;

/**
 * Edit element action.
 * @author t0076261
 */
public class EditElementAction extends AbstractWizardBasedAction {
  /**
   * Edition context provider.
   */
  private IEditionContextProvider _contextProvider;

  /**
   * Constructor.
   * @param contextProvider_p
   */
  public EditElementAction(Shell shell_p, IEditionContextProvider contextProvider_p) {
    super(Messages.EditElementAction_Action_Text);
    setShell(shell_p);
    setImageDescriptor(OrchestraFrameworkUiActivator.getDefault().getImageDescriptor("edit.gif")); //$NON-NLS-1$
    _contextProvider = contextProvider_p;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.action.AbstractWizardBasedAction#createWizard()
   */
  @Override
  protected Wizard createWizard() {
    // Recompute wizard at runtime for the selection might not have changed, but the underlying object might.
    return ChangeWizardFactory.createChangeWizardFor(getStructuredSelection().getFirstElement(), _contextProvider.getEditionContext());
  }

  /**
   * @see org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org.eclipse.jface.viewers.IStructuredSelection)
   */
  @Override
  protected boolean updateSelection(IStructuredSelection selection_p) {
    boolean uniqueSelection = (1 == selection_p.size());
    return uniqueSelection && _contextProvider.getEditionContext() != null
           && (null != ChangeWizardFactory.getWizardTypeFor(selection_p.getFirstElement(), _contextProvider.getEditionContext()));
  }
}