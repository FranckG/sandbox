/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.view;

import org.eclipse.jface.action.ContributionItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.thalesgroup.orchestra.framework.ui.internal.viewer.ModelElementFilter;
import com.thalesgroup.orchestra.framework.ui.internal.viewer.ModelElementFilter.FilterState;

/**
 * This contribution creates a menu to manage a ModelElementFilter.
 * @author T0052089
 */
public class ModelElementFilterContribution extends ContributionItem {
  /**
   * Show all elements menu item -> when selected, unselects all filter criteria.
   */
  protected MenuItem _showAllElementsMenuItem;
  /**
   * Show contributed elements menu item.
   */
  protected MenuItem _showContributedElementsMenuItem;
  /**
   * Show empty mandatory variables menu item.
   */
  protected MenuItem _showEmptyMandatoryVariablesMenuItem;
  /**
   * Show overridden variables menu item.
   */
  protected MenuItem _showOverriddenVariablesMenuItem;

  /**
   * @see org.eclipse.jface.action.ContributionItem#fill(org.eclipse.swt.widgets.Menu, int)
   */
  @Override
  public void fill(Menu menu_p, int index_p) {
    // Get ModelElementFilter to manage.
    final ModelElementFilter modelElementFilter = getModelElementFilter();
    if (null == modelElementFilter) {
      return;
    }
    // Fill menu.
    _showContributedElementsMenuItem = new MenuItem(menu_p, SWT.CHECK);
    _showContributedElementsMenuItem.setText(Messages.ModelElementFilterContribution_MenuItem_ShowContributedElements);
    _showEmptyMandatoryVariablesMenuItem = new MenuItem(menu_p, SWT.CHECK);
    _showEmptyMandatoryVariablesMenuItem.setText(Messages.ModelElementFilterContribution_MenuItem_ShowEmptyMandatoryVariables);
    _showOverriddenVariablesMenuItem = new MenuItem(menu_p, SWT.CHECK);
    _showOverriddenVariablesMenuItem.setText(Messages.ModelElementFilterContribution_MenuItem_ShowOverriddenVariables);
    new MenuItem(menu_p, SWT.SEPARATOR);
    _showAllElementsMenuItem = new MenuItem(menu_p, SWT.CHECK);
    _showAllElementsMenuItem.setText(Messages.ModelElementFilterContribution_MenuItem_ShowAllElements);
    // Set initial state.
    FilterState currentFilterState = modelElementFilter.getCurrentFilterState();
    updateButtonsState(currentFilterState);
    // Create selection listener and add it to every menu item.
    SelectionListener changeFilterSelectionListener = new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e_p) {
        FilterState newFilterState = null;
        if (_showAllElementsMenuItem == e_p.getSource()) {
          // "Show all" has been clicked -> disable all filter criteria.
          // Note : "Show all" can't be unselected.
          newFilterState = new FilterState(false, false, false);
        } else {
          // A filter criterion has been changed -> create a new filter state.
          newFilterState =
              new FilterState(_showContributedElementsMenuItem.getSelection(), _showEmptyMandatoryVariablesMenuItem.getSelection(),
                  _showOverriddenVariablesMenuItem.getSelection());
        }
        // Set new filter state.
        modelElementFilter.setCurrentFilterState(newFilterState);
        // Update buttons states according to filter state.
        updateButtonsState(newFilterState);
      }
    };
    _showContributedElementsMenuItem.addSelectionListener(changeFilterSelectionListener);
    _showEmptyMandatoryVariablesMenuItem.addSelectionListener(changeFilterSelectionListener);
    _showOverriddenVariablesMenuItem.addSelectionListener(changeFilterSelectionListener);
    _showAllElementsMenuItem.addSelectionListener(changeFilterSelectionListener);
  }

  /**
   * Get the ModelElementFilter or the VariablesView view part.
   * @return
   */
  protected ModelElementFilter getModelElementFilter() {
    IWorkbenchWindow active = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
    if (active == null) {
      return null;
    }
    IWorkbenchPage page = active.getActivePage();
    if (page == null) {
      return null;
    }
    IWorkbenchPart part = page.getActivePart();
    if (!(part instanceof VariablesView)) {
      return null;
    }
    return ((VariablesView) part).getModelElementFilter();
  }

  /**
   * Update buttons states according to given FilterState.
   * @param newFilterState
   */
  protected void updateButtonsState(FilterState newFilterState) {
    _showContributedElementsMenuItem.setSelection(newFilterState._selectContributedElements);
    _showEmptyMandatoryVariablesMenuItem.setSelection(newFilterState._selectEmptyMandatoryVariables);
    _showOverriddenVariablesMenuItem.setSelection(newFilterState._selectOverriddenVariables);
    // No criterion is selected -> filter is not filtering -> "Show all" is checked.
    _showAllElementsMenuItem.setSelection(!newFilterState.isFiltering());
  }
}
