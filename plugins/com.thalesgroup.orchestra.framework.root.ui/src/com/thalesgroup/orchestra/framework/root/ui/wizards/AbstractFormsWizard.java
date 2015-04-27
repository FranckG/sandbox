/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.root.ui.wizards;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * Abstract wizard that displays pages using Forms.
 * @author t0076261
 */
public abstract class AbstractFormsWizard extends Wizard {
  /**
   * Form toolkit.
   */
  private FormToolkit _toolkit;

  /**
   * @see org.eclipse.jface.wizard.Wizard#addPage(org.eclipse.jface.wizard.IWizardPage)
   */
  @Override
  public void addPage(IWizardPage page_p) {
    // Add toolkit to page.
    if (page_p instanceof AbstractFormsWizardPage) {
      ((AbstractFormsWizardPage) page_p).setToolkit(getToolkit());
    }
    // Go on with page addition.
    super.addPage(page_p);
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#createPageControls(org.eclipse.swt.widgets.Composite)
   */
  @Override
  public void createPageControls(Composite pageContainer_p) {
    // Adapt container.
    getToolkit().adapt(pageContainer_p);
    // Add toolkit to static pages.
    for (IWizardPage page : getPages()) {
      if (page instanceof AbstractFormsWizardPage) {
        ((AbstractFormsWizardPage) page).setToolkit(getToolkit());
      }
    }
    // Go on with pages creation.
    super.createPageControls(pageContainer_p);
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#dispose()
   */
  @Override
  public void dispose() {
    try {
      super.dispose();
    } finally {
      if (null != _toolkit) {
        _toolkit.dispose();
        _toolkit = null;
      }
    }
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#getShell()
   */
  @Override
  public Shell getShell() {
    Shell result = super.getShell();
    if (null == result) {
      result = PlatformUI.getWorkbench().getDisplay().getActiveShell();
    }
    return result;
  }

  /**
   * Get toolkit.
   * @return
   */
  protected FormToolkit getToolkit() {
    if (null == _toolkit) {
      _toolkit = new FormToolkit(PlatformUI.getWorkbench().getDisplay());
      _toolkit.setBackground(getShell().getBackground());
    }
    return _toolkit;
  }
}