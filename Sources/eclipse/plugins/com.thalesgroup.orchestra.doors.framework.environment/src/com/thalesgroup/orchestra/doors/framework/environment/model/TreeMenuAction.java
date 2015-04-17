/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.doors.framework.environment.model;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import com.thalesgroup.orchestra.doors.framework.environment.ui.DoorsEnvironmentTreePage;
import com.thalesgroup.orchestra.doors.framework.environment.ui.EditDoorsElementWizard;
import com.thalesgroup.orchestra.doors.framework.environment.ui.ProjectTreePanel;
import com.thalesgroup.orchestra.framework.environment.ui.IVariablesHandler;

/**
 * @author S0032874 Default action used in the Doors Tree to add a new node
 */
public abstract class TreeMenuAction extends BaseSelectionListenerAction {
  public static final String DEFAULT_NAME_FORMAT = "{0}_{1}"; //$NON-NLS-1$

  protected final String _defaultNamePrefix;

  /**
   * A reference to the ProjectTreePanel
   */
  protected final ProjectTreePanel _projectTreePanel;

  protected String _resultDoorsElementName;

  /**
   * The model carried by the selected node when the action is triggered
   */
  protected AbstractDoorsElement _selectedDoorsElement;

  /**
   * Constructor
   * @param title_p The title of the action
   * @param panel_p The panel
   */
  protected TreeMenuAction(String title_p, String defaultNamePrefix_p, ProjectTreePanel panel_p) {
    super(title_p);
    _projectTreePanel = panel_p;
    _defaultNamePrefix = defaultNamePrefix_p;
  }

  /**
   * Check if the action can be enabled depending on the selected node Default implementation returns true
   * @param model_p The model to check
   * @return True if the connection can be enabled, else false
   */
  protected boolean canBeEnabled(AbstractDoorsElement model_p) {
    return true;
  }

  /**
   * Get a unique index
   * @return New index
   */
  protected String generateDefaultName() {
    List<? extends AbstractDoorsElement> newElementSiblings = getNewElementSiblings(_selectedDoorsElement);

    int suffix = -1;
    String defaultName;
    boolean nameFound;

    do {
      nameFound = false;
      ++suffix;
      defaultName = MessageFormat.format(DEFAULT_NAME_FORMAT, _defaultNamePrefix, String.valueOf(suffix));

      Iterator<? extends AbstractDoorsElement> newElementSiblingsIterator = newElementSiblings.iterator();
      while (newElementSiblingsIterator.hasNext() && !nameFound) {
        nameFound = newElementSiblingsIterator.next().getDoorsName().equals(defaultName);
      }
      // Some elements left -> tried name is already used.
    } while (nameFound);

    return defaultName;
  }

  protected abstract List<? extends AbstractDoorsElement> getNewElementSiblings(AbstractDoorsElement parentElement_p);

  /**
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    // /// TEST POUR RECUPERER LES VARIABLESHANDLER
    DoorsEnvironmentTreePage page = _projectTreePanel.getDoorsEnvironmentTreePage();
    IVariablesHandler variablesHandler = page.getVariablesHandler();
    EditDoorsElementWizard editDoorsElementWizard = new EditDoorsElementWizard(getText(), generateDefaultName(), variablesHandler);
    WizardDialog editDoorsElementWizardDialog = new WizardDialog(_projectTreePanel.getFullTreeViewer().getControl().getShell(), editDoorsElementWizard);
    if (Window.OK == editDoorsElementWizardDialog.open()) {
      _resultDoorsElementName = editDoorsElementWizard.getRawDoorsElementName();
    }
  }

  /**
   * @see org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org.eclipse.jface.viewers.IStructuredSelection)
   */
  @Override
  protected boolean updateSelection(IStructuredSelection selection_p) {
    _selectedDoorsElement = (AbstractDoorsElement) selection_p.getFirstElement();
    if (null != _selectedDoorsElement) {
      return canBeEnabled(_selectedDoorsElement);
    }
    return false;
  }

  /**
   * Refresh the tree to display the new node
   */
  public void updateTree() {
    _projectTreePanel.getFullTreeViewer().refresh();
    _projectTreePanel.getDoorsEnvironmentTreePage().forceUpdate();
  }

}
