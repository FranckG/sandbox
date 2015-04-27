/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.action;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.IWorkbenchGraphicConstants;

import com.thalesgroup.orchestra.framework.ui.internal.wizard.ImportContextsWizard;

/**
 * @author t0076261
 */
public class ImportContextsAction extends AbstractWizardBasedAction {
  /**
   * Constructor.
   * @param shell_p
   */
  public ImportContextsAction(Shell shell_p) {
    super(Messages.ImportContextsAction_Action_Text);
    setShell(shell_p);
    setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(IWorkbenchGraphicConstants.IMG_ETOOL_IMPORT_WIZ));
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.action.AbstractWizardBasedAction#createWizard()
   */
  @Override
  protected Wizard createWizard() {
    return new ImportContextsWizard();
  }
}