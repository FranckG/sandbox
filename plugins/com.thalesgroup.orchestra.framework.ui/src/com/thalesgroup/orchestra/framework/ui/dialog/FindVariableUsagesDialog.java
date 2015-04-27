/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.dialog;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;
import com.thalesgroup.orchestra.framework.ui.viewer.VariableUsagesViewer;

/**
 * @author s0040806
 */
public class FindVariableUsagesDialog extends MessageDialog {

  private FormToolkit _toolkit;
  private VariableUsagesViewer _viewer;

  protected Variable _variable;
  protected Context _context;

  /**
   * @param parentShell_p
   * @param toolkit_p
   * @param variable_p
   * @param context_p
   */
  public FindVariableUsagesDialog(Shell parentShell_p, FormToolkit toolkit_p, Variable variable_p, Context context_p) {
    super(parentShell_p, Messages.FindVariableUsagesDialog_WindowTitle, null, getDialogMessage(variable_p), MessageDialog.NONE,
          new String[] { IDialogConstants.OK_LABEL }, 0);
    _toolkit = toolkit_p;
    _variable = variable_p;
    _context = context_p;
  }

  private static String getDialogMessage(Variable variable_p) {
    // return MessageFormat.format(Messages.FindVariableUsagesDialog_PageTitle, ModelUtil.getElementPath(variable_p));
    return null;
  }

  @Override
  protected void configureShell(Shell shell_p) {
    super.configureShell(shell_p);
    int defaultDialogWidth = 550;
    int defaultDialogHeight = 550;
    Shell activeShell = PlatformUI.getWorkbench().getDisplay().getActiveShell();
    if (null != activeShell) {
      // Center dialog on active parent shell.
      Rectangle bounds = activeShell.getBounds();
      shell_p.setBounds(bounds.x + (bounds.width - defaultDialogWidth) / 2, bounds.y + (bounds.height - defaultDialogHeight) / 2, defaultDialogWidth,
          defaultDialogHeight);
    }
  }

  /**
   * @see org.eclipse.jface.dialogs.MessageDialog#createCustomArea(org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Control createCustomArea(Composite parent_p) {
    Group group = new Group(parent_p, SWT.NONE);
    group.setText(Messages.FindVariableUsagesDialog_PageTitle);
    GridLayout groupLayout = new GridLayout(1, true);
    groupLayout.marginWidth = 5;
    group.setLayout(groupLayout);
    GridData groupData = new GridData(SWT.FILL, SWT.FILL, true, false);
    group.setLayoutData(groupData);

    // Context
    Label contextLabel = new Label(group, SWT.NONE);
    contextLabel.setText(Messages.FindVariableUsagesDialog_Context_Label + " " + _context.getName()); //$NON-NLS-1$
    // Variable location
    Label variablePathLabel = new Label(group, SWT.NONE);
    variablePathLabel.setText(Messages.FindVariableUsagesDialog_VariableLocation_Label
                              + " " + ModelUtil.getElementPath(ModelUtil.getCategory(_variable)).substring(1)); //$NON-NLS-1$

    // Variable name
    Composite variableComposite = new Composite(group, SWT.NONE);
    GridLayout variableLayout = new GridLayout(2, false);
    variableLayout.marginWidth = 0;
    variableLayout.horizontalSpacing = 10;
    variableComposite.setLayout(variableLayout);
    GridData variableData = new GridData(SWT.FILL, SWT.FILL, true, false);
    variableComposite.setLayoutData(variableData);

    Label variableName = new Label(variableComposite, SWT.NONE);
    variableName.setText(Messages.FindVariableUsagesDialog_VariableName_Label);

    Label variableValue = new Label(variableComposite, SWT.NONE);
    variableValue.setText(_variable.getName());
    variableValue.setFont(new Font(parent_p.getDisplay(), variableValue.getFont().toString(), 9, SWT.BOLD));

    _viewer = new VariableUsagesViewer(_variable, _context);
    _viewer.createViewer(_toolkit, parent_p, null, null);
    return _viewer.getViewer().getControl();
  }

  /**
   * @see org.eclipse.jface.dialogs.Dialog#isResizable()
   */
  protected boolean isResizable() {
    return true;
  }

}
