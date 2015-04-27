/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.wizard.page;

import org.eclipse.emf.common.util.EList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsFactory;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingVariable;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingVariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.root.ui.custom.ICustomMonoValueVariableEditButton;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper.LayoutType;

/**
 * {@link OverridingVariable} Mono values edition handler.
 * @author t0076261
 */
public class OVMonoValueEditionHandler extends MonoValueEditionHandler {
  /**
   * Overriding variable.
   */
  protected OverridingVariable _overridingVariable;
  /**
   * Restore button.
   */
  protected Button _restoreButton;
  /**
   * Text listener.
   */
  protected FocusListener _textListener;
  /**
   * Flag for variable value access type.<br>
   * <code>true</code> in read/write mode. <code>false</code> (default) in read only mode.
   */
  protected volatile boolean _valueAccessFlag;

  /**
   * Constructor.
   * @param overridingVariable_p
   */
  public OVMonoValueEditionHandler(OverridingVariable overridingVariable_p) {
    _overridingVariable = overridingVariable_p;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.MonoValueEditionHandler#activateBinding()
   */
  @Override
  protected void activateBinding() {
    // Set initial value.
    String variableValue = getValue();
    if (null == variableValue) {
      variableValue = ICommonConstants.EMPTY_STRING;
    }
    _text.setText(variableValue);
    // Stop here if value is not modifiable.
    if (DataUtil.isUnmodifiable(_variable, _editionContext)) {
      return;
    }
    // Add text listener.
    _textListener = new FocusListener() {
      public void focusGained(FocusEvent e_p) {
        // Do nothing.
      }

      public void focusLost(FocusEvent e_p) {
        // Get new value.
        String newValue = _text.getText();
        setOverridingVariableValue(newValue);
      }
    };
    _text.addFocusListener(_textListener);
  }

  @Override
  protected void createCustomButton(FormToolkit toolkit_p, Composite buttonsComposite_p, final ICustomMonoValueVariableEditButton customButton_p) {
    Button button = FormHelper.createButton(toolkit_p, buttonsComposite_p, customButton_p.getLabel(), SWT.PUSH);
    GridData data = (GridData) FormHelper.updateControlLayoutDataWithLayoutTypeData(button, LayoutType.GRID_LAYOUT);
    data.grabExcessVerticalSpace = false;

    SelectionAdapter adapter = new SelectionAdapter() {
      /**
       * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
       */
      @Override
      public void widgetSelected(SelectionEvent e_p) {
        String newValue = customButton_p.getValue();
        setOverridingVariableValue(newValue);
        _text.setText(newValue);
      }
    };
    button.addSelectionListener(adapter);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.MonoValueEditionHandler#createButtonsPart(org.eclipse.ui.forms.widgets.FormToolkit,
   *      org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Composite createButtonsPart(FormToolkit toolkit_p, Composite parent_p) {
    Composite result = super.createButtonsPart(toolkit_p, parent_p);
    // Stop here if value is not modifiable.
    if (DataUtil.isUnmodifiable(_variable, _editionContext)) {
      return result;
    }
    // Add restore button.
    _restoreButton = toolkit_p.createButton(result, Messages.MultipleValuesEditionHandler_Button_Restore_Text, SWT.PUSH);
    _restoreButton.setLayoutData(new GridData(SWT.FILL, SWT.NULL, false, false));
    _restoreButton.addSelectionListener(new SelectionAdapter() {
      /**
       * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
       */
      @Override
      public void widgetSelected(SelectionEvent e_p) {
        // Get overriding value.
        OverridingVariableValue value = getOverridingValue(false);
        // Remove it from container.
        ((AbstractVariable) value.eContainer()).getValues().remove(value);
        // Restore value.
        _text.setText(getDefaultValueValue());
        _restoreButton.setEnabled(false);
      }
    });
    return result;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.MonoValueEditionHandler#doSetEnabled(boolean)
   */
  @Override
  protected void doSetEnabled(boolean enabled_p) {
    super.doSetEnabled(enabled_p);
    if (null != _restoreButton) {
      _restoreButton.setEnabled(enabled_p && (null != getOverridingValue(false)));
    }
  }

  /**
   * Get variable default value.
   * @return
   */
  protected VariableValue getDefaultValue() {
    return DataUtil.getRawValues(_variable, _editionContext).get(0);
  }

  /**
   * Get variable default value value (its content) or an empty string if it is null.
   * @return
   */
  protected String getDefaultValueValue() {
    String value = getDefaultValue().getValue();
    if (null == value) {
      value = ICommonConstants.EMPTY_STRING;
    }
    return value;
  }

  /**
   * Get currently overriding value.
   * @param create_p <code>true</code> to force creation if none exists.
   * @return
   */
  protected OverridingVariableValue getOverridingValue(boolean create_p) {
    OverridingVariableValue result = null;
    // Precondition.
    // Do nothing in case the variable is not modifiable.
    if (DataUtil.isUnmodifiable(_variable, _editionContext)) {
      return result;
    }
    // Get overriding value.
    EList<VariableValue> values = _overridingVariable.getValues();
    if (1 == values.size()) {
      VariableValue value = values.get(0);
      if (ContextsPackage.Literals.OVERRIDING_VARIABLE_VALUE.isInstance(value)) {
        result = (OverridingVariableValue) value;
      }
    }
    // None exists, create it.
    if (create_p && (null == result)) {
      // Create overriding value.
      result = ContextsFactory.eINSTANCE.createOverridingVariableValue();
      // Set overridden value.
      result.setOverriddenValue(_variable.getValues().get(0));
      // Add value to overriding variable.
      _overridingVariable.getValues().add(result);
    }
    return result;
  }

  /**
   * Add new overriding value
   * @param newValue New value
   */
  protected void setOverridingVariableValue(String newValue) {
    // Set it.
    OverridingVariableValue ovvalue = getOverridingValue((null != newValue) && !newValue.equals(getDefaultValueValue()));
    if (null != ovvalue) {
      ovvalue.setValue(newValue);
    }
    // Refresh restore button status.
    if (!_restoreButton.isEnabled()) {
      _restoreButton.setEnabled(null != ovvalue);
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.MonoValueEditionHandler#getValue()
   */
  @Override
  public String getValue() {
    _valueAccessFlag = false;
    return super.getValue();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.MonoValueEditionHandler#getVariableValue()
   */
  @Override
  public VariableValue getVariableValue() {
    VariableValue value = getOverridingValue(_valueAccessFlag);
    if (null == value) {
      value = getDefaultValue();
    }
    return value;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractValueEditionHandler#isHandling(com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable)
   */
  @Override
  public boolean isHandling(AbstractVariable variable_p) {
    return super.isHandling(variable_p) || (_overridingVariable == variable_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.MonoValueEditionHandler#setNewValue(java.lang.String)
   */
  @Override
  public void setNewValue(String newValue_p) {
    _valueAccessFlag = true;
    try {
      super.setNewValue(newValue_p);
    } finally {
      _valueAccessFlag = false;
    }
  }
}