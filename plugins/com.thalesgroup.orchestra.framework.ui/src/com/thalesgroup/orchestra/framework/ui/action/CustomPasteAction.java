/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.action;

import java.util.Collection;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.command.CutToClipboardCommand;
import com.thalesgroup.orchestra.framework.model.handler.data.ContextsEditingDomain;
import com.thalesgroup.orchestra.framework.model.handler.data.ContextsEditingDomain.ClipboardElement;
import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.ui.wizard.NewContextCopyWizard;

/**
 * Customized Paste action.<br>
 * - When a context paste is asked, it is not executed by a command in the command stack but directly in this action to avoid interferences with the undo/redo
 * mechanisms (context paste can not be undone anyway), <br>
 * - Other paste actions are executed by commands in the command stack and so can be undone/redone.
 */
public class CustomPasteAction extends org.eclipse.emf.edit.ui.action.PasteAction {
  /**
   * Context to paste (if any).
   */
  protected Context _contextToPaste;

  /**
   * Constructor.
   * @param domain_p
   */
  public CustomPasteAction(EditingDomain domain_p) {
    super(domain_p);
  }

  /**
   * @see org.eclipse.emf.edit.ui.action.CommandActionHandler#run()
   */
  @Override
  public void run() {
    if (null != _contextToPaste) {
      // There is a context to paste.
      Shell activeShell = PlatformUI.getWorkbench().getDisplay().getActiveShell();
      WizardDialog newContextCopyWizardDialog = new WizardDialog(activeShell, new NewContextCopyWizard(_contextToPaste));
      newContextCopyWizardDialog.open();
    } else {
      // Not a context paste, execute a command in the command stack.
      super.run();
    }
  }

  /**
   * @see org.eclipse.emf.edit.ui.action.CommandActionHandler#updateSelection(org.eclipse.jface.viewers.IStructuredSelection)
   */
  @Override
  public boolean updateSelection(IStructuredSelection selection_p) {
    _contextToPaste = null;
    // Check Clipboard content.
    ContextsEditingDomain contextsEditingDomain = ModelHandlerActivator.getDefault().getEditingDomain();
    ClipboardElement clipboardElement = contextsEditingDomain.getClipboardContent();
    // Check clipboard isn't empty.
    if (null == clipboardElement || clipboardElement.isEmpty()) {
      return false;
    }
    // Are we pasting contexts ?
    Collection<Context> contextsToPaste = EcoreUtil.getObjectsByType(clipboardElement._elements, ContextsPackage.Literals.CONTEXT);
    if (clipboardElement._elements.size() == contextsToPaste.size()) {
      // Only copy is allowed, not cut !
      if (CutToClipboardCommand.class.equals(clipboardElement._commandClass)) {
        return false;
      }
      // Context copy is allowed only in admin mode.
      if (!ProjectActivator.getInstance().isAdministrator()) {
        return false;
      }
      // Only one context can be copied.
      if (1 != contextsToPaste.size()) {
        return false;
      }
      _contextToPaste = contextsToPaste.iterator().next();
      return true;
    }
    // Not pasting contexts, use the "command" mechanisms.
    return super.updateSelection(selection_p);
  }
}
