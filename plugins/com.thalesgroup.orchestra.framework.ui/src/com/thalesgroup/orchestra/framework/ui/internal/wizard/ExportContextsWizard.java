/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.wizard;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.MessageDialog;

import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;
import com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizard;
import com.thalesgroup.orchestra.framework.ui.internal.wizard.page.ExportContextsPage;

/**
 * Export contexts wizard.
 * @author t0076261
 */
public class ExportContextsWizard extends AbstractFormsWizard {
  /**
   * Contexts to select in export selection tree, can be null.
   */
  private List<Context> _contextsToSelect;

  /**
   * Export page.
   */
  private ExportContextsPage _exportPage;

  /**
   * Constructor.
   * @param contexts_p contexts to select in this wizard (can be null).
   */
  public ExportContextsWizard(List<Context> contexts_p) {
    setNeedsProgressMonitor(false);
    setWindowTitle(Messages.ExportContextsWizard_Wizard_Title);
    _contextsToSelect = contexts_p;
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#addPages()
   */
  @Override
  public void addPages() {
    _exportPage = new ExportContextsPage(_contextsToSelect);
    addPage(_exportPage);
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#performFinish()
   */
  @Override
  public boolean performFinish() {
    String rootLocation = _exportPage.getLocation();
    Collection<RootContextsProject> projects = _exportPage.getCheckedProjects();
    // Precondition.
    if ((null == rootLocation) || (null == projects) || projects.isEmpty()) {
      return false;
    }
    // Go for it !
    for (RootContextsProject project : projects) {
      IStatus exportStatus;
      exportStatus = ProjectActivator.getInstance().getProjectHandler().exportProjectTo(project._project, rootLocation, false);
      if (!exportStatus.isOK() && exportStatus.matches(IStatus.WARNING)) {
        if (MessageDialog.openConfirm(getShell(), Messages.ExportContextsWizard_Dialog_Confirm_Title, exportStatus.getMessage())) {
          exportStatus = ProjectActivator.getInstance().getProjectHandler().exportProjectTo(project._project, rootLocation, true);
        }
      }
      if (!exportStatus.isOK()) {
        _exportPage.setErrorMessage(exportStatus.getMessage());
        return false;
      }
    }
    return true;
  }
}