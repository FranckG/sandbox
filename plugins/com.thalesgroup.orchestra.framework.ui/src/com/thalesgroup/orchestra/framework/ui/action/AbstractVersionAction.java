/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.action;

import org.eclipse.emf.common.command.Command;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import com.thalesgroup.orchestra.framework.common.command.ModelModifyingCommand;
import com.thalesgroup.orchestra.framework.model.IEditionContextProvider;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.contexts.InstallationCategory;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;

/**
 * Abstract product version handling action.
 * @author t0076261
 */
public abstract class AbstractVersionAction extends BaseSelectionListenerAction {
  /**
   * Edition context provider to use.
   */
  protected IEditionContextProvider _editionContextProvider;
  /**
   * Currently selected installation category.
   */
  protected InstallationCategory _installationCategory;

  /**
   * Constructor.
   * @param actionText_p
   * @param editionContextProvider_p
   */
  public AbstractVersionAction(String actionText_p, IEditionContextProvider editionContextProvider_p) {
    super(actionText_p);
    _editionContextProvider = editionContextProvider_p;
  }

  /**
   * Create command to execute.
   * @return
   */
  protected abstract ModelModifyingCommand createCommand();

  /**
   * Do update selection for specified installation category.
   * @param installationCategory_p
   * @return
   */
  protected abstract boolean doUpdateSelection(InstallationCategory installationCategory_p);

  /**
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    Command command = createCommand();
    if (null != command) {
      ModelHandlerActivator.getDefault().getEditingDomain().getCommandStack().execute(command);
    }
  }

  /**
   * @see org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org.eclipse.jface.viewers.IStructuredSelection)
   */
  @Override
  protected boolean updateSelection(IStructuredSelection selection_p) {
    // Select version action available if one unique installation category is selected and is selectable.
    boolean uniqueSelection = (1 == selection_p.size());
    if (!uniqueSelection) {
      return false;
    }
    Object element = selection_p.getFirstElement();
    // Element must be an installation category.
    if (!ContextsPackage.Literals.INSTALLATION_CATEGORY.isInstance(element)) {
      return false;
    }
    // Check that this category is indeed selectable.
    InstallationCategory installationCategory = (InstallationCategory) element;
    // Do check selected installation category.
    boolean isSelectable = doUpdateSelection(installationCategory);
    if (isSelectable) {
      _installationCategory = installationCategory;
    } else {
      _installationCategory = null;
    }
    return isSelectable;
  }
}