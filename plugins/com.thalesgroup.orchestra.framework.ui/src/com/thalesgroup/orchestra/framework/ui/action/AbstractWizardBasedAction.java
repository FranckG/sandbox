/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.action;

import org.eclipse.jface.layout.PixelConverter;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

/**
 * @author t0076261
 */
public abstract class AbstractWizardBasedAction extends BaseSelectionListenerAction {
  /**
   * Shell reference.
   */
  private Shell _shell;

  /**
   * Constructor.
   * @param text_p The {@link String} used as the text for the action, or <code>null</code> if there is no text.
   */
  public AbstractWizardBasedAction(String text_p) {
    super(text_p);
  }

  /**
   * Create wizard that should be invoked.
   * @return
   */
  protected abstract Wizard createWizard();

  /**
   * Get shell.
   * @return
   */
  public Shell getShell() {
    if (null == _shell) {
      return PlatformUI.getWorkbench().getDisplay().getActiveShell();
    }
    return _shell;
  }

  /**
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    Wizard wizard = createWizard();
    if (null != wizard) {
      WizardDialog dialog = new WizardDialog(getShell(), wizard);
      // Limit the dialog horizontal size to avoid big dialog if big texts in it.
      PixelConverter converter = new PixelConverter(JFaceResources.getDialogFont());
      dialog.setPageSize(converter.convertWidthInCharsToPixels(70), SWT.DEFAULT);
      dialog.open();
    }
  }

  /**
   * Set shell to use. <code>null</code> to use default active shell.
   * @param shell_p
   */
  public void setShell(Shell shell_p) {
    _shell = shell_p;
  }
}