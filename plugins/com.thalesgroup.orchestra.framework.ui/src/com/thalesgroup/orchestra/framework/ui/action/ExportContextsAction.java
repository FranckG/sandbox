/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.action;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.IWorkbenchGraphicConstants;

import com.thalesgroup.orchestra.framework.model.ISelectedContextsProvider;
import com.thalesgroup.orchestra.framework.ui.internal.wizard.ExportContextsWizard;

/**
 * @author t0076261
 */
public class ExportContextsAction extends AbstractWizardBasedAction {
  /**
   * Selected contexts provider.
   */
  private ISelectedContextsProvider _contextsProvider;

  /**
   * Constructor.
   * @param shell_p
   * @param contextsProvider_p
   */
  public ExportContextsAction(Shell shell_p, ISelectedContextsProvider contextsProvider_p) {
    super(Messages.ExportContextsAction_Action_Text);
    setShell(shell_p);
    setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(IWorkbenchGraphicConstants.IMG_ETOOL_EXPORT_WIZ));
    _contextsProvider = contextsProvider_p;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.action.AbstractWizardBasedAction#createWizard()
   */
  @Override
  protected Wizard createWizard() {
    return new ExportContextsWizard(_contextsProvider.getSelectedContexts());
  }
}