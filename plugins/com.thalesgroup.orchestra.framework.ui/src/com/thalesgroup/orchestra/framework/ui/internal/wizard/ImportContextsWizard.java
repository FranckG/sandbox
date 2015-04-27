/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.wizard;

import java.text.MessageFormat;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.jface.dialogs.IDialogConstants;

import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.root.ui.DisplayHelper;
import com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizard;
import com.thalesgroup.orchestra.framework.ui.activator.OrchestraFrameworkUiActivator;
import com.thalesgroup.orchestra.framework.ui.internal.wizard.page.ImportContextsPage;

/**
 * @author t0076261
 */
public class ImportContextsWizard extends AbstractFormsWizard {
  /**
   * Export page.
   */
  private ImportContextsPage _importPage;

  /**
   * Constructor.
   */
  public ImportContextsWizard() {
    setNeedsProgressMonitor(true);
    setWindowTitle(Messages.ImportContextsWizard_Wizard_Title);
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#addPages()
   */
  @Override
  public void addPages() {
    _importPage = new ImportContextsPage();
    addPage(_importPage);
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#performFinish()
   */
  @Override
  public boolean performFinish() {
    IStatus importContextsStatuses =
        ModelHandlerActivator.getDefault().getDataHandler().importContexts(_importPage.getCheckedProjects(), _importPage.shouldImportAsCopy());
    // If errors occurred, display them.
    if (!importContextsStatuses.isOK()) {
      MultiStatus displayStatus =
          new MultiStatus(OrchestraFrameworkUiActivator.getDefault().getPluginId(), 0, MessageFormat.format(Messages.ImportContextsWizard_ImportError_Reason,
              IDialogConstants.SHOW_DETAILS_LABEL), null);
      displayStatus.add(importContextsStatuses);
      DisplayHelper.displayErrorDialog(Messages.ImportContextsWizard_ImportError_Title, Messages.ImportContextsWizard_ImportError_Message, displayStatus);
    }
    // Close wizard anyway.
    return true;
  }
}