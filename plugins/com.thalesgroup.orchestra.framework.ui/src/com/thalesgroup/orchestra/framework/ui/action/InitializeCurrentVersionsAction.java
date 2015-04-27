/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.action;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.command.InitializeCurrentVersionsCommand;
import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.ui.activator.OrchestraFrameworkUiActivator;

/**
 * Initialize current versions for selected context.
 * @author t0076261
 */
public class InitializeCurrentVersionsAction extends BaseSelectionListenerAction {
  /**
   * Selected contexts.
   */
  private List<Context> _selectedContexts;

  /**
   * Constructor.
   */
  public InitializeCurrentVersionsAction() {
    super(Messages.InitializeCurrentVersionsAction_Text);
    setImageDescriptor(OrchestraFrameworkUiActivator.getDefault().getImageDescriptor("initializeVersions.png")); //$NON-NLS-1$
  }

  /**
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    // Create command.
    Command command = new InitializeCurrentVersionsCommand(_selectedContexts);
    // And execute it.
    ModelHandlerActivator.getDefault().getEditingDomain().getCommandStack().execute(command);
  }

  /**
   * @see org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org.eclipse.jface.viewers.IStructuredSelection)
   */
  @Override
  protected boolean updateSelection(IStructuredSelection selection_p) {
    // Initialize attributes.
    _selectedContexts = new ArrayList<Context>(0);
    // Make sure ODM is currently running in administrator mode.
    if (!ProjectActivator.getInstance().isAdministrator()) {
      return false;
    }
    // Make sure all selected objects are contexts.
    for (Object selectedElement : selection_p.toList()) {
      if (!ContextsPackage.Literals.CONTEXT.isInstance(selectedElement)) {
        // Free memory.
        _selectedContexts.clear();
        return false;
      }
      _selectedContexts.add((Context) selectedElement);
    }
    return !_selectedContexts.isEmpty();
  }
}