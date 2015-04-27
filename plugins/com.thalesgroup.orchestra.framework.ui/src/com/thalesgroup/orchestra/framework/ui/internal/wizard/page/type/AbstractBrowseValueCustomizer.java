/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.wizard.page.type;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper.LayoutType;
import com.thalesgroup.orchestra.framework.ui.internal.wizard.page.IValueHandler;

/**
 * A customizer that handles a browse button.
 * @author t0076261
 */
public abstract class AbstractBrowseValueCustomizer extends DefaultValueCustomizer implements SelectionListener {
  /**
   * Browse button.
   */
  protected Button _browseButton;

  /**
   * Constructor.
   * @param editionContext_p
   * @param handler_p
   */
  public AbstractBrowseValueCustomizer(Context editionContext_p, IValueHandler handler_p) {
    super(editionContext_p, handler_p);
  }

  /**
   * Optionally creates a button to help variable value edition.
   * @return the additional button or <code>null</code> if there are no additional button to create.
   */
  protected Button createBrowseButton(FormToolkit toolkit_p, Composite parent_p) {
    _browseButton = FormHelper.createButton(toolkit_p, parent_p, getButtonLabel(), SWT.PUSH);
    _browseButton.setToolTipText(getToolTipText());
    GridData data = (GridData) FormHelper.updateControlLayoutDataWithLayoutTypeData(_browseButton, LayoutType.GRID_LAYOUT);
    data.grabExcessVerticalSpace = false;
    // Handle button selection.
    _browseButton.addSelectionListener(this);
    return _browseButton;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.type.DefaultValueCustomizer#createButtonsPart(org.eclipse.ui.forms.widgets.FormToolkit,
   *      org.eclipse.swt.widgets.Composite, org.eclipse.swt.widgets.Composite)
   */
  @Override
  public Composite createButtonsPart(FormToolkit toolkit_p, Composite parent_p, Composite composite_p) {
    Composite result = super.createButtonsPart(toolkit_p, parent_p, composite_p);
    createBrowseButton(toolkit_p, composite_p);
    return result;
  }

  /**
   * @return the button label for the type.
   */
  public abstract String getButtonLabel();

  /**
   * @return the button tool tip text for the type.
   */
  public abstract String getToolTipText();

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.type.DefaultValueCustomizer#setEnabled(boolean)
   */
  @Override
  public void setEnabled(boolean enabled_p) {
    super.setEnabled(enabled_p);
    // Browse button must exist.
    if (null != _browseButton) {
      // And a value must be accessible.
      if (null != _valueHandler.getValue()) {
        _browseButton.setEnabled(enabled_p && !isVariableReadOnly());
      } else {
        _browseButton.setEnabled(false);
      }
    }
  }

  /**
   * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
   */
  public void widgetDefaultSelected(SelectionEvent e_p) {
    // Do nothing here.
  }
}