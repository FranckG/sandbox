/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.wizard;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.layout.PixelConverter;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import com.thalesgroup.orchestra.framework.model.IEditionContextProvider;

/**
 * An {@link IDoubleClickListener} that opens a wizard on modification for selected element.
 * @author t0076261
 */
public class OpenChangeWizardListener implements IDoubleClickListener {
  /**
   * Delegate to action.
   */
  private IAction _delegateAction;
  /**
   * Edition context provider.
   */
  private IEditionContextProvider _provider;

  /**
   * Constructor.
   * @param action_p
   */
  public OpenChangeWizardListener(IAction action_p) {
    _delegateAction = action_p;
  }

  /**
   * Constructor.
   * @param provider_p
   */
  public OpenChangeWizardListener(IEditionContextProvider provider_p) {
    _provider = provider_p;
  }

  /**
   * @see org.eclipse.jface.viewers.IDoubleClickListener#doubleClick(org.eclipse.jface.viewers.DoubleClickEvent)
   */
  public void doubleClick(DoubleClickEvent event_p) {
    ISelection selection = event_p.getSelection();
    if (selection instanceof StructuredSelection) {
      StructuredSelection structuredSelection = (StructuredSelection) selection;
      // Default behavior.
      if (null == _delegateAction) {
        Wizard changeWizard = ChangeWizardFactory.createChangeWizardFor(structuredSelection.getFirstElement(), _provider.getEditionContext());
        if (null != changeWizard) {
          Shell shell = event_p.getViewer().getControl().getShell();
          WizardDialog dialog = new WizardDialog(shell, changeWizard);
          // Limit the dialog horizontal size to avoid big dialog if big texts in it.
          PixelConverter converter = new PixelConverter(JFaceResources.getDialogFont());
          dialog.setPageSize(converter.convertWidthInCharsToPixels(70), SWT.DEFAULT);
          dialog.open();
        }
      } else { // Delegate to another action.
        if (_delegateAction instanceof BaseSelectionListenerAction) {
          ((BaseSelectionListenerAction) _delegateAction).selectionChanged(structuredSelection);
        }
        if (_delegateAction.isEnabled()) {
          _delegateAction.run();
        }
      }
    }
  }
}