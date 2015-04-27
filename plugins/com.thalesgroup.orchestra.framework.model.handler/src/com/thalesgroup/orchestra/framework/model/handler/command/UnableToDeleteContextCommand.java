/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.handler.command;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.AbstractCommand.NonDirtying;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

/**
 * Unable to delete context command.<br>
 * Deleting current context is not allowed.
 * @author t0076261
 */
public class UnableToDeleteContextCommand extends AbstractCommand implements NonDirtying {
  /**
   * @see org.eclipse.emf.common.command.AbstractCommand#canUndo()
   */
  @Override
  public boolean canUndo() {
    return false;
  }

  /**
   * @see org.eclipse.emf.common.command.Command#execute()
   */
  public void execute() {
    final Display display = PlatformUI.getWorkbench().getDisplay();
    display.syncExec(new Runnable() {
      public void run() {
        MessageDialog dialog =
            new MessageDialog(display.getActiveShell(), Messages.UnableToDeleteContextCommand_ErrorDialog_Title, null,
                Messages.UnableToDeleteContextCommand_ErrorDialog_Text, MessageDialog.ERROR, new String[] { IDialogConstants.OK_LABEL }, 0);
        dialog.open();
      }
    });
  }

  /**
   * @see org.eclipse.emf.common.command.AbstractCommand#prepare()
   */
  @Override
  protected boolean prepare() {
    return true;
  }

  /**
   * @see org.eclipse.emf.common.command.Command#redo()
   */
  public void redo() {
    // Do nothing.
  }
}
