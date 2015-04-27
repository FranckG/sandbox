/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.wizard.page;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariable;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingVariable;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingVariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper.LayoutType;
import com.thalesgroup.orchestra.framework.ui.internal.wizard.page.type.IMultipleValueCustomizer;

/**
 * {@link OverridingVariable} Multiple values edition handler.
 * @author t0076261
 */
public class OVMultipleValuesEditionHandler extends MultipleValuesEditionHandler {
  /**
   * Latest created overriding variable value.
   */
  protected OverridingVariableValue _latestOverridingValue;
  /**
   * Overriding variable.
   */
  protected OverridingVariable _overridingVariable;
  /**
   * Restore button.
   */
  protected Button _restoreButton;

  /**
   * Constructor.
   * @param overridingVariable_p
   */
  public OVMultipleValuesEditionHandler(OverridingVariable overridingVariable_p) {
    _overridingVariable = overridingVariable_p;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.MultipleValuesEditionHandler#addNewVariableValue()
   */
  @Override
  protected void addNewVariableValue() {
    addNewVariableValue(ICommonConstants.EMPTY_STRING);
  }

  /**
   * Add a new value to edited variable (non interactive)
   */
  @Override
  protected void addNewVariableValue(String value) {
    VariableValue newValue = ModelUtil.createVariableValue(_overridingVariable);
    newValue.setValue(value);
    _overridingVariable.getValues().add(newValue);
    // Add value to viewer.
    _viewer.add(newValue);

    if (value.equals(ICommonConstants.EMPTY_STRING)) {
      // Select the newly created value.
      _viewer.getTable().forceFocus();
      _viewer.editElement(newValue, 0);
    }
    // Adapt to IMultipleValueCustomizer.
    IMultipleValueCustomizer multipleValueCustomizer = (IMultipleValueCustomizer) _customizer.getAdapter(IMultipleValueCustomizer.class);
    if (null != multipleValueCustomizer) {
      multipleValueCustomizer.newVariableValueAdded(newValue);
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.MultipleValuesEditionHandler#createButtonsPart(org.eclipse.ui.forms.widgets.FormToolkit,
   *      org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Composite createButtonsPart(FormToolkit toolkit_p, Composite parent_p) {
    Composite result = super.createButtonsPart(toolkit_p, parent_p);
    // Add restore button.
    createRestoreButton(toolkit_p, result);
    return result;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.MultipleValuesEditionHandler#createContentProvider()
   */
  @Override
  protected ValueContentProvider createContentProvider() {
    // Specific behavior.
    return new ValueContentProvider() {
      /**
       * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.MultipleValuesEditionHandler.ValueContentProvider#doGetValues(com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable)
       */
      @Override
      protected Object[] doGetValues(AbstractVariable variable_p) {
        // Get existing overriding variables.
        List<OverridingVariable> overridingVariables = DataUtil.getOverridingVariables(variable_p, _editionContext);
        // Add current one, if it is not null && not included yet.
        // - It can be null, if wizard is editing a final variable in a sub context,
        // - It can be not included if the overriding variable was created by the wizard in use. It is then not yet added to the model, but to be taken into
        // account.
        if (_overridingVariable != null && !overridingVariables.contains(_overridingVariable)) {
          overridingVariables.add(_overridingVariable);
        }
        return DataUtil.getRawValues(variable_p, overridingVariables).toArray();
      }
    };
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.MultipleValuesEditionHandler#createEditingSupport()
   */
  @Override
  protected ValueEditingSupport createEditingSupport() {
    return new OVValueEditionSupport();
  }

  /**
   * Create restore button.
   * @param toolkit_p
   * @param parent_p
   * @return
   */
  protected Button createRestoreButton(FormToolkit toolkit_p, Composite parent_p) {
    _restoreButton = FormHelper.createButton(toolkit_p, parent_p, Messages.MultipleValuesEditionHandler_Button_Restore_Text, SWT.PUSH);
    GridData data = (GridData) FormHelper.updateControlLayoutDataWithLayoutTypeData(_restoreButton, LayoutType.GRID_LAYOUT);
    data.grabExcessVerticalSpace = false;
    // Associate the selection adapter.
    _restoreButton.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e_p) {
        removeVariableValue((VariableValue) ((StructuredSelection) _viewer.getSelection()).getFirstElement());
      }
    });
    _restoreButton.setEnabled(false);
    return _restoreButton;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.MultipleValuesEditionHandler#customizerEnablementAfterSelection(com.thalesgroup.orchestra.framework.model.contexts.VariableValue)
   */
  @Override
  protected boolean customizerEnablementAfterSelection(VariableValue selectedValue_p) {
    return true;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.MultipleValuesEditionHandler#doSetEnabled(boolean)
   */
  @Override
  protected void doSetEnabled(boolean enabled_p) {
    super.doSetEnabled(enabled_p);
    if (null != _restoreButton) {
      _restoreButton.setEnabled(_currentValue instanceof OverridingVariableValue);
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.MultipleValuesEditionHandler#editionCancelled()
   */
  @Override
  public void editionCancelled() {
    // Remove latest overriding variable value from model.
    if (null != _latestOverridingValue) {
      _overridingVariable.getValues().remove(_latestOverridingValue);
      _latestOverridingValue = null;
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.MultipleValuesEditionHandler#editionFinished()
   */
  @Override
  public void editionFinished() {
    // TODO Guillaume
    // Ideally, there would be a call to _viewer.replace(...) here.
    // But this is likely to be time-consuming too to find the index to replace.
    // Instead, refresh the whole viewer (modifying the initial input).
    // Should there be a performance issue here, try and call the replace method.
    _viewer.refresh();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.MultipleValuesEditionHandler#getVariableValue()
   */
  @Override
  public VariableValue getVariableValue() {
    if (_variable instanceof EnvironmentVariable && !DataUtil.isUnmodifiable(_variable, _editionContext)) {
      // For an environment variable, override the selected value to give it to the SetupEnvironmentWizard.
      Context context = ModelUtil.getContext(_currentValue);
      if ((null != context) && (context != _editionContext)) {
        OverridingVariableValue overridingValue = overrideValue(_currentValue);
        // Precondition.
        if (null == overridingValue) {
          return _currentValue;
        }
        _currentValue = overridingValue;
        return overridingValue;
      }
    }
    return super.getVariableValue();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractValueEditionHandler#isHandling(com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable)
   */
  @Override
  public boolean isHandling(AbstractVariable variable_p) {
    return super.isHandling(variable_p) || (_overridingVariable == variable_p);
  }

  /**
   * Override specified value with a new overriding one.
   * @param value_p The value to override.
   * @return <code>null</code> if specified value is <code>null</code> or is already an overriding value owned by current variable.
   */
  protected OverridingVariableValue overrideValue(VariableValue value_p) {
    _latestOverridingValue = null;
    // Precondition.
    if (null == value_p) {
      return null;
    }
    // Precondition.
    // Specified value is already the looked after overriding one.
    if (_overridingVariable.getValues().contains(value_p)) {
      return null;
    }
    // Create new overriding value.
    VariableValue parentValue = null;
    int index = getValueIndex();
    if (-1 != index) {
      List<VariableValue> rawValues = DataUtil.getRawValues(getVariable(), getEditionContext());
      if (index <= rawValues.size()) {
        parentValue = rawValues.get(index);
      }
    }
    _latestOverridingValue = ModelUtil.createOverridingVariableValue(_overridingVariable, value_p, parentValue);
    return _latestOverridingValue;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.MultipleValuesEditionHandler#removeEnablementAfterSelection(com.thalesgroup.orchestra.framework.model.contexts.VariableValue)
   */
  @Override
  protected boolean removeEnablementAfterSelection(VariableValue selectedValue_p) {
    // OverridingVariableValue are handled by the Restore button.
    return null != _overridingVariable && _overridingVariable.getValues().contains(selectedValue_p) && !(selectedValue_p instanceof OverridingVariableValue);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.MultipleValuesEditionHandler#removeVariableValue(com.thalesgroup.orchestra.framework.model.contexts.VariableValue)
   */
  @Override
  protected void removeVariableValue(VariableValue value_p) {
    if (_overridingVariable.getValues().contains(value_p)) {
      _overridingVariable.getValues().remove(value_p);
      _removeButton.setEnabled(false);
      _restoreButton.setEnabled(false);
      // TODO Guillaume
      // Ideally, there would be a call to _viewer.replace(...) here.
      // But this is likely to be time-consuming too to find the index to replace.
      // Instead, refresh the whole viewer (modifying the initial input).
      // Should there be a performance issue here, try and call the replace method.
      _viewer.refresh();
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.MultipleValuesEditionHandler#removeVariableValue(com.thalesgroup.orchestra.framework.model.contexts.VariableValue)
   */
  @Override
  protected void removeVariableValues(List<VariableValue> values_p) {
    boolean modified = false;
    for (VariableValue value : values_p) {
      if (_overridingVariable.getValues().contains(value)) {
        _overridingVariable.getValues().remove(value);
        modified = true;
      }
    }
    if (modified) {
      _removeButton.setEnabled(false);
      _restoreButton.setEnabled(false);
      // TODO Guillaume
      // Ideally, there would be a call to _viewer.replace(...) here.
      // But this is likely to be time-consuming too to find the index to replace.
      // Instead, refresh the whole viewer (modifying the initial input).
      // Should there be a performance issue here, try and call the replace method.
      _viewer.refresh();
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.MultipleValuesEditionHandler#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
   */
  @Override
  public void selectionChanged(SelectionChangedEvent event_p) {
    super.selectionChanged(event_p);
    Object value = ((StructuredSelection) event_p.getSelection()).getFirstElement();
    // Selected variable value is an override.
    if (ContextsPackage.Literals.OVERRIDING_VARIABLE_VALUE.isInstance(value)) {
      // Make sure this is owned by the context.
      _restoreButton.setEnabled(null != _overridingVariable && _overridingVariable.getValues().contains(value));
    } else {
      // Selected variable value is not an overriding one.
      _restoreButton.setEnabled(false);
    }
  }

  /**
   * An edition support specific to overriding variables and overriding values.
   * @author t0076261
   */
  protected class OVValueEditionSupport extends ValueEditingSupport {
    /**
     * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.MultipleValuesEditionHandler.ValueEditingSupport#setValue(java.lang.Object,
     *      java.lang.Object)
     */
    @Override
    protected void setValue(Object element_p, Object value_p) {
      // Precondition.
      if (null == element_p) {
        return;
      }
      // Precondition.
      if (DataUtil.isUnmodifiable(_variable, _editionContext)) {
        // Do nothing in this case.
        return;
      }
      // Precondition.
      if (!ContextsPackage.Literals.VARIABLE_VALUE.isInstance(element_p)) {
        return;
      }
      VariableValue element = (VariableValue) element_p;
      Context rootContainer = ModelUtil.getContext(element);
      // The element being edited is not contained by the edition context.
      // It looks like a new overriding variable value is to be created.
      // Make sure that the element does not belong to overriding variable first.
      if (_editionContext != rootContainer) {
        // Do not create an OverridingVariableValue if the value has not changed yet.
        String oldValue = element.getValue();
        if (value_p.equals(oldValue)) {
          return;
        }
        // Jump to root value.
        if (element instanceof OverridingVariableValue) {
          element = ((OverridingVariableValue) element).getOverriddenValue();
        }
        // Create new overriding value.
        OverridingVariableValue value = overrideValue(element);
        if (null == value) {
          super.setValue(element_p, value_p);
        } else {
          super.setValue(value, value_p);
        }
        // A refresh of the viewer is needed.
        editionFinished();
      } else {
        super.setValue(element_p, value_p);
      }
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.MultipleValuesEditionHandler#getVariableValues()
   */
  protected EList<VariableValue> getVariableValues() {
    return _overridingVariable.getValues();
  }

}