/**
 * Copyright (c) THALES, 2010. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.common.ui.dialog;

import java.util.Set;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.root.ui.AbstractRunnableWithDisplay;
import com.thalesgroup.orchestra.framework.root.ui.DisplayHelper;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper.LayoutType;

/**
 * Create a dialog with customizable title and confirmation message, which shows a collection of {@link String}. It must be used with the {@link DisplayHelper}
 * class.
 * @author s0024585
 */
public class ListConfirmationDialog extends AbstractRunnableWithDisplay {
  /**
   * Confirmation message
   */
  protected String _confirmationMessage;
  /**
   * The return code depending on the user choice.
   */
  protected int _returnCode;
  /**
   * Tool set
   */
  protected Set<String> _stringSet;
  /**
   * The dialog title.
   */
  protected String _title;

  /**
   * Create a dialog with a title, a confirmation message and a list of tools.
   * @param stringSet_p
   * @param title_p
   * @param confirmationMessage_p
   */
  public ListConfirmationDialog(Set<String> stringSet_p, String title_p, String confirmationMessage_p) {
    _stringSet = stringSet_p;
    _title = title_p;
    _confirmationMessage = confirmationMessage_p;
  }

  /**
   * @return the {@link Window} code, depending of user choice.
   */
  public int getReturnCode() {
    return _returnCode;
  }

  /**
   * @see java.lang.Runnable#run()
   */
  @Override
  public void run() {
    final Display display = PlatformUI.getWorkbench().getDisplay();
    MessageDialog dialog =
        new MessageDialog(display.getActiveShell(), _title, null, _confirmationMessage, MessageDialog.QUESTION, new String[] { IDialogConstants.YES_LABEL,
                                                                                                                              IDialogConstants.NO_LABEL }, 1) {
          /**
           * @see org.eclipse.jface.dialogs.MessageDialog#configureShell(org.eclipse.swt.widgets.Shell)
           */
          @Override
          protected void configureShell(Shell shell_p) {
            super.configureShell(shell_p);
            int defaultWidth = 450;
            int defaultHeight = 350;
            Shell activeShell = display.getActiveShell();
            if (null != activeShell) {
              // Center dialog on active parent shell.
              Rectangle bounds = activeShell.getBounds();
              shell_p.setBounds(bounds.x + (bounds.width - defaultWidth) / 2, bounds.y + (bounds.height - defaultHeight) / 2, defaultWidth, defaultHeight);
            } else {
              shell_p.setSize(defaultWidth, defaultHeight);
            }
          }

          /**
           * @see org.eclipse.jface.dialogs.MessageDialog#createCustomArea(org.eclipse.swt.widgets.Composite)
           */
          @Override
          protected Control createCustomArea(Composite parent_p) {
            FormToolkit toolkit = new FormToolkit(parent_p.getDisplay());
            toolkit.setBackground(parent_p.getBackground());
            Composite composite = FormHelper.createCompositeWithLayoutType(toolkit, parent_p, LayoutType.GRID_LAYOUT, 1, false);
            List list = new List(composite, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL | SWT.H_SCROLL);
            list.setEnabled(false);
            FormHelper.updateControlLayoutDataWithLayoutTypeData(list, LayoutType.GRID_LAYOUT);
            for (String registeredConsumer : _stringSet) {
              list.add(registeredConsumer);
            }
            toolkit.dispose();
            return composite;
          }

          /**
           * @see org.eclipse.jface.window.Window#setShellStyle(int)
           */
          @Override
          protected void setShellStyle(int newShellStyle_p) {
            super.setShellStyle(newShellStyle_p | SWT.RESIZE);
          }
        };
    _returnCode = dialog.open();
  }
}
