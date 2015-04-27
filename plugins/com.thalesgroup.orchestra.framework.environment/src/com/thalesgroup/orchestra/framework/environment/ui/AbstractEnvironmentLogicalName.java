/**
 * Copyright (c) THALES, 2013. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment.ui;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.environment.IEnvironmentHandler;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper.LayoutType;

/**
 * An abstract implementation of the logical name page that provides a simple UI for setting a logical name.
 * @author S0035580
 */
public abstract class AbstractEnvironmentLogicalName extends AbstractEnvironmentPage {
  /**
   * Default value of the logical name
   */
  protected String _defaultLogicalName;

  /**
   * Text field to enter the logical name of the connection string
   */
  protected Text _rawLogicalNameText;

  /**
   * @param pageId_p
   * @param handler_p The environment handler responsible for currently edited environment.
   */
  public AbstractEnvironmentLogicalName(String pageId_p, IEnvironmentHandler handler_p) {
    super(pageId_p, handler_p);
  }

  /**
   * Create the elements of the page: logical name field.
   * @see com.thalesgroup.orchestra.framework.common.ui.wizards.AbstractFormsWizardPage#doCreateControl(org.eclipse.swt.widgets.Composite,
   *      org.eclipse.ui.forms.widgets.FormToolkit)
   */
  @Override
  protected Composite doCreateControl(Composite parent_p, FormToolkit toolkit_p) {
    IVariablesHandler variablesHandler = _handler.getVariablesHandler();
    int reference;

    if (variablesHandler.isVariableReadOnly()) {
      reference = SWT.BORDER | SWT.READ_ONLY;
    } else {
      reference = SWT.BORDER;
    }

    // Main component.
    Composite composite = FormHelper.createCompositeWithLayoutType(toolkit_p, parent_p, LayoutType.GRID_LAYOUT, 3, false);
    //
    // Logical name field.
    //
    Label emptyLineLabel = new Label(composite, SWT.NONE);
    emptyLineLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
    String label = getLabelConnectionString();
    if (null == label) {
      label = ICommonConstants.EMPTY_STRING;
    }
    _rawLogicalNameText = (Text) FormHelper.createLabelAndText(toolkit_p, composite, label, ICommonConstants.EMPTY_STRING, reference).get(Text.class);
    _rawLogicalNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

    // Set page read only if provided by a baseline.
    if (variablesHandler.isVariableReadOnly()) {
      _rawLogicalNameText.setBackground(toolkit_p.getColors().getBackground());
    }

    // Set listeners.
    ModifyListener rawTextModifyListener = new ModifyListener() {
      /**
       * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
       */
      public void modifyText(ModifyEvent event_p) {
        forceUpdate();
      }
    };
    _rawLogicalNameText.addModifyListener(rawTextModifyListener);

    // / Set initial value.
    if (null != _defaultLogicalName)
      _rawLogicalNameText.setText(_defaultLogicalName);

    // Activate the Progress bar
    Wizard wizard = (Wizard) getWizard();
    wizard.setNeedsProgressMonitor(true);

    return composite;
  }

  /**
   * Get the label to the field.
   * @return the label of the TextBox
   */
  protected abstract String getLabelConnectionString();
}
