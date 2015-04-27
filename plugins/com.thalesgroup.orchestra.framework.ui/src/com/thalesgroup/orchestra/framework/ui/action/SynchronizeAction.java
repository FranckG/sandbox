/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.action;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.command.SynchronizeCommand;
import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.ui.activator.OrchestraFrameworkUiActivator;

/**
 * Synchronize action.
 * @author t0076261
 */
public class SynchronizeAction extends BaseSelectionListenerAction {
  /**
   * Currently selected contexts.
   */
  private Context _context;

  /**
   * Constructor.
   */
  public SynchronizeAction() {
    super(Messages.SynchronizeAction_Action_Text);
    setImageDescriptor(OrchestraFrameworkUiActivator.getDefault().getImageDescriptor("refresh.gif")); //$NON-NLS-1$
  }

  /**
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    List<Context> contextList = new ArrayList<Context>();
    contextList.add(_context);
    SynchronizeCommand command = new SynchronizeCommand(contextList);
    ModelHandlerActivator.getDefault().getEditingDomain().getCommandStack().execute(command);
  }

  /**
   * @see org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org.eclipse.jface.viewers.IStructuredSelection)
   */
  @Override
  protected boolean updateSelection(IStructuredSelection selection_p) {
    // Synchronize action available if one unique context is selected and current mode is administrator.
    boolean uniqueSelection = (1 == selection_p.size());
    if (!uniqueSelection) {
      return false;
    }

    Object element = selection_p.getFirstElement();
    boolean result = ProjectActivator.getInstance().isAdministrator() && ContextsPackage.Literals.CONTEXT.isInstance(element);
    if (result) {
      _context = (Context) element;
    } else {
      _context = null;
    }
    return result;
  }
}