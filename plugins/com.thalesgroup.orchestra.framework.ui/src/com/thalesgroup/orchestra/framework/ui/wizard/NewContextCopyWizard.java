/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.wizard;

import java.text.MessageFormat;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.wizard.WizardPage;

import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;

/**
 * This wizard is a customized version of the NewContextWizard. It is used to get needed data (context name and location) to create a copy of an existing
 * context.
 */
public class NewContextCopyWizard extends NewContextWizard {
  /**
   * The context to copy.
   */
  protected Context _sourceContext;

  /**
   * Constructor.
   * @param sourceContext_p
   */
  public NewContextCopyWizard(Context sourceContext_p) {
    _sourceContext = sourceContext_p;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.wizard.NewContextWizard#addPages()
   */
  @Override
  public void addPages() {
    String pagesTitle = MessageFormat.format(Messages.NewContextCopyWizard_PagesTitle, _sourceContext.getName());
    String pagesDescription = Messages.NewContextCopyWizard_PagesDescription;
    // Page used to get new context copy name (parent selection is disabled).
    _newContextParametersPage = new NewContextWizardParentContextPage(false);
    _newContextParametersPage.setTitle(pagesTitle);
    _newContextParametersPage.setDescription(pagesDescription);
    addPage(_newContextParametersPage);
    // Page used to get new context copy location.
    _newContextWizardLocationPage = new NewContextWizardLocationPage();
    _newContextWizardLocationPage.setTitle(pagesTitle);
    _newContextWizardLocationPage.setDescription(pagesDescription);
    addPage(_newContextWizardLocationPage);
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#getWindowTitle()
   */
  @Override
  public String getWindowTitle() {
    return Messages.NewContextCopyWizard_WizardTitle;
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#performFinish()
   */
  @Override
  public boolean performFinish() {
    // Should the project be created in an external location or directly in the workspace.
    String projectLocation = _useProjectLocation ? _newProjectLocation : null;
    IStatus status =
        ModelHandlerActivator.getDefault().getDataHandler()
            .createNewContextCopy(_newContextParametersPage.getNewContextName(), projectLocation, _sourceContext);
    if (!status.isOK()) {
      // Display an error message to the user.
      if (getContainer().getCurrentPage() instanceof WizardPage) {
        WizardPage currentPage = (WizardPage) getContainer().getCurrentPage();
        currentPage.setMessage(status.getMessage(), IMessageProvider.ERROR);
      }
    }
    return status.isOK();
  }
}
