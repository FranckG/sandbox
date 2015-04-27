/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.action;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.data.DataHandler;
import com.thalesgroup.orchestra.framework.ui.activator.OrchestraFrameworkUiActivator;
import com.thalesgroup.orchestra.framework.ui.view.VariablesView;

/**
 * @author s0011584
 */
public class SetAsCurrentContextAction extends BaseSelectionListenerAction implements SelectionListener {
  /**
   * Ask user on error ?
   */
  private boolean _askUser;

  /**
   * Constructor.
   */
  public SetAsCurrentContextAction() {
    super(Messages.SetAsCurrentContextAction_setAsCurrentContext);
    setImageDescriptor(OrchestraFrameworkUiActivator.getDefault().getImageDescriptor("setAsCurrent.gif")); //$NON-NLS-1$
    // By default, always ask user.
    _askUser = true;
  }

  /**
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    // Get context to set "as current" from selection
    IStructuredSelection structuredSelection = getStructuredSelection();
    Context contextToSetAsCurrent = (Context) structuredSelection.getFirstElement();
    DataHandler dataHandler = ModelHandlerActivator.getDefault().getDataHandler();
    VariablesView sharedInstance = VariablesView.getSharedInstance();
    // Get old context.
    Context oldContext = ModelHandlerActivator.getDefault().getEditingDomain().getLocalContextFrom(dataHandler.getCurrentContext());
    // Set current context.
    dataHandler.setCurrentContext(contextToSetAsCurrent, _askUser);
    // Refresh contexts viewer.
    sharedInstance.refreshElement(oldContext, oldContext);
    sharedInstance.refreshElement(contextToSetAsCurrent, contextToSetAsCurrent);
    // Refresh selection to update commands in CommandActionHandlers (useful for DeleteAction since it's not possible to delete a context once it is the current
    // one).
    sharedInstance.setSelection(contextToSetAsCurrent, contextToSetAsCurrent);
  }

  /**
   * Should context switching ask user on error ?
   * @param askUser_p <code>true</code> to allow user interaction (on error), <code>false</code> to force silent mode.
   */
  public void shouldAskUserOnError(boolean askUser_p) {
    _askUser = askUser_p;
  }

  /**
   * @see org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org.eclipse.jface.viewers.IStructuredSelection)
   */
  @Override
  public boolean updateSelection(IStructuredSelection selection_p) {
    // Action unavailable for multiple selection.
    if (1 != selection_p.size()) {
      return false;
    }
    // Selected object must be a loaded (eResource not null) and not modified context.
    // Unloaded contexts are given to this command during multiple contexts deletions (every contexts are deleted in the model, then ContextsViewer deletes them
    // in the tree one by one in asyncExec, trying to preserve selection and then selecting unloaded contexts).
    Object selectedObject = selection_p.getFirstElement();
    return (selectedObject instanceof Context) && (null != ((Context) selectedObject).eResource())
           && !ModelHandlerActivator.getDefault().getEditingDomain().isModified(((Context) selectedObject).eResource());
  }

  /**
   * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
   */
  public void widgetDefaultSelected(SelectionEvent e_p) {
    // Do nothing.
  }

  /**
   * This method is used to call this action from a MenuItem.
   * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
   */
  public void widgetSelected(SelectionEvent e_p) {
    MenuItem menuItem = (MenuItem) e_p.widget;
    if (menuItem.getSelection()) {
      String contextName = menuItem.getText();
      setAsCurrentContext(contextName);
    }
  }

  /**
   * This method will call {@link #updateSelection(IStructuredSelection)} by building the {@link StructuredSelection} in order to perform {@link #run()} as if
   * it have been called by {@link BaseSelectionListenerAction}.
   */
  public void setAsCurrentContext(String contextName_p) {
    // Precondition.
    if (null == contextName_p) {
      return;
    }
    // Go through contexts list to find the context with given name.
    for (Context currentContext : ModelHandlerActivator.getDefault().getDataHandler().getAllContexts().getContexts()) {
      if (contextName_p.equals(currentContext.getName())) {
        // Create a selection.
        selectionChanged(new StructuredSelection(currentContext));
        // And run.
        run();
        return;
      }
    }
  }
}