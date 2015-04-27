/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.view;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.AbstractFormPart;
import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IFormPart;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.ui.internal.viewer.PatternFilter;
import com.thalesgroup.orchestra.framework.ui.viewer.VariablesViewer;

/**
 * Variables details page.
 * @author t0076261
 */
public class VariablesDetailsPage extends AbstractFormPart implements IDetailsPage {
  /**
   * Variables viewer.
   */
  protected VariablesViewer _viewer;
  /**
   * Initial input (from master selection).
   */
  protected Object _initialInput;
  /**
   * Scope Context.
   */
  protected Context _context;
  /**
   * Pattern filter.
   */
  protected PatternFilter _filter;
  /**
   * Shared actions handler.
   */
  protected ISharedActionsHandler _sharedActionsHandler;

  /**
   * Constructor.
   * @param initialInput_p
   * @param context_p The scope {@link Context}, used to resolve variables and categories.
   * @param filter_p Optional pattern filter used to highlight values selected by the contexts viewer regular expression.
   * @param handler_p Optional shared actions handler.
   */
  public VariablesDetailsPage(Object initialInput_p, Context context_p, PatternFilter filter_p, ISharedActionsHandler handler_p) {
    _initialInput = initialInput_p;
    _context = context_p;
    _filter = filter_p;
    _sharedActionsHandler = handler_p;
  }

  /**
   * @see org.eclipse.ui.forms.IDetailsPage#createContents(org.eclipse.swt.widgets.Composite)
   */
  public void createContents(Composite parent_p) {
    _viewer = new VariablesViewer();
    _viewer.createViewer(getToolkit(), parent_p, _initialInput, _context, _filter, _sharedActionsHandler);
  }

  /**
   * @see org.eclipse.ui.forms.AbstractFormPart#dispose()
   */
  @Override
  public void dispose() {
    try {
      super.dispose();
    } finally {
      // Get rid of the viewer.
      if (null != _viewer) {
        _viewer.dispose();
        _viewer = null;
      }
    }
  }

  /**
   * Get toolkit.
   * @return
   */
  protected FormToolkit getToolkit() {
    return getManagedForm().getToolkit();
  }

  /**
   * Is page disposed ?
   * @return
   */
  public boolean isDisposed() {
    // Precondition.
    if (null == _viewer) {
      return true;
    }
    // Check that neither the viewer nor the control is disposed.
    TreeViewer treeViewer = _viewer.getViewer();
    if ((null == treeViewer) || (null == treeViewer.getControl())) {
      return true;
    }
    // Return control state.
    return treeViewer.getControl().isDisposed();
  }

  /**
   * Pre-dispose page.
   */
  public void preDispose() {
    _viewer.preDispose();
  }

  /**
   * Refresh specified element.
   * @param element_p
   */
  public void refreshElement(Object element_p) {
    _viewer.refreshElement(element_p);
  }

  /**
   * Refresh whole page.
   */
  public void refreshPage() {
    if (null != _viewer) {
      TreeViewer viewer = _viewer.getViewer();
      if (null != viewer) {
        viewer.refresh();
      }
    }
  }

  /**
   * @see org.eclipse.ui.forms.IPartSelectionListener#selectionChanged(org.eclipse.ui.forms.IFormPart, org.eclipse.jface.viewers.ISelection)
   */
  public void selectionChanged(IFormPart part_p, ISelection selection_p) {
    // Not much to do here yet.
  }

  /**
   * Set selection for current page to specified one.
   * @param selection_p
   */
  public void setSelection(ISelection selection_p) {
    _viewer.setSelection(selection_p);
  }
}