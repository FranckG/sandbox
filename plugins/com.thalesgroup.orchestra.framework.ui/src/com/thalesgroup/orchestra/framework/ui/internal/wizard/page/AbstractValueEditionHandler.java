/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.wizard.page;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper.LayoutType;
import com.thalesgroup.orchestra.framework.ui.internal.wizard.page.type.DefaultValueCustomizer;
import com.thalesgroup.orchestra.framework.ui.internal.wizard.page.type.ValueCustomizerFactory;

/**
 * Value edition handler for either a single value or multiple ones.<br>
 * Allows for the creation of the control in charge of the edition.<br>
 * Also handles the life-cycle of the control and the edited value.
 * @author t0076261
 */
public abstract class AbstractValueEditionHandler implements IValueHandler {
  /**
   * Data binding context.
   */
  protected DataBindingContext _bindingContext;
  /**
   * The handler that provides custom UI/behavior for editing variable value.
   */
  protected DefaultValueCustomizer _customizer;
  /**
   * Disabled color.
   */
  protected Color _disabledColor;
  /**
   * Edition context.
   */
  protected Context _editionContext;
  /**
   * Enabled color.
   */
  protected Color _enabledColor;
  /**
   * Variable being edited.
   */
  protected AbstractVariable _variable;

  /**
   * Element being edited in the multiple variable list
   */
  protected Object _editedElement = null;
  /**
   * Selected element in the multiple variable list
   */
  protected Object _selectedElement = null;

  /**
   * Create buttons part of the edition.
   * @param toolkit_p
   * @param parent_p
   * @return A dedicated composite, child of parent one. Should not be provided parent one directly.
   */
  protected abstract Composite createButtonsPart(FormToolkit toolkit_p, Composite parent_p);

  /**
   * Create variable edit buttons
   * @param toolkit_p Form toolkit
   * @param buttonsComposite_p Buttons composite
   */
  protected abstract void createCustomButtons(FormToolkit toolkit_p, Composite buttonsComposite_p);

  /**
   * Create control.
   * @param toolkit_p
   * @param parent_p
   * @return
   */
  protected Composite createControl(FormToolkit toolkit_p, Composite parent_p) {
    // Replace with a call to getVariable();
    _customizer = createCustomizationValueHandler();
    // Create base composite.
    Composite composite = FormHelper.createCompositeWithLayoutType(toolkit_p, parent_p, LayoutType.GRID_LAYOUT, 2, false);
    // Values part.
    Composite valuesComposite = createValuesPart(toolkit_p, composite);
    if (null != _customizer) {
      _customizer.createValuesPart(toolkit_p, composite, valuesComposite);
    }
    // Buttons part.
    Composite buttonsComposite = createButtonsPart(toolkit_p, composite);
    if (null != _customizer) {
      _customizer.createButtonsPart(toolkit_p, composite, buttonsComposite);
    }

    createCustomButtons(toolkit_p, buttonsComposite);

    return composite;
  }

  /**
   * Create customization handler, if any.
   * @return <code>null</code> if no customization is required.
   */
  protected DefaultValueCustomizer createCustomizationValueHandler() {
    return ValueCustomizerFactory.createVariableValueEditionHandlerFor(_variable, _editionContext, this);
  }

  /**
   * Create values part of the edition.
   * @param toolkit_p
   * @param parent_p
   * @return A dedicated composite, child of parent one. Should not be provided parent one directly.
   */
  protected abstract Composite createValuesPart(FormToolkit toolkit_p, Composite parent_p);

  /**
   * Dispose.
   */
  protected void dispose() {
    // Do nothing.
  }

  /**
   * Do set enabled state.
   * @param enabled_p <code>true</code> if handler should activate its UI and behavior, <code>false</code> to deactivate them.
   */
  protected abstract void doSetEnabled(boolean enabled_p);

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.IValueHandler#getEditionContext()
   */
  public Context getEditionContext() {
    return _editionContext;
  }

  /**
   * Get control responsible for new textual value insertion.
   * @return <code>null</code> if not applicable or none found.
   */
  protected Text getInsertionControl() {
    return null;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.IValueHandler#getValue()
   */
  public String getValue() {
    // Default implementation.
    return null;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.IValueHandler#getVariable()
   */
  public AbstractVariable getVariable() {
    return _variable;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.IValueHandler#insertValue(java.lang.String)
   */
  public void insertValue(String newValue_p) {
    String oldValue = getValue();
    if (null == oldValue) {
      return;
    }

    // The selected value is the one which is edited
    if (_selectedElement == _editedElement) {
      // Assuming it is always a text control that allows for edition.
      Text text = getInsertionControl();
      if (null == text) {
        return;
      }
      // Get current text control.
      Point textSelection = text.getSelection();
      // Compose resulting string.
      StringBuilder newValue = new StringBuilder();
      newValue.append(oldValue.substring(0, textSelection.x));
      newValue.append(newValue_p);
      newValue.append(oldValue.substring(textSelection.y));
      // Set it.
      setNewValue(newValue.toString());
    } else {
      // When the selected value is not being edited, replace the whole value
      setNewValue(newValue_p.toString());
    }
  }

  /**
   * Is handler enabled ?
   * @return
   */
  protected boolean isEnabled() {
    // Never enabled if variable is not modifiable in edition context.
    return !DataUtil.isUnmodifiable(_variable, _editionContext);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.IValueHandler#isHandling(com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable)
   */
  public boolean isHandling(AbstractVariable variable_p) {
    return _variable == variable_p;
  }

  /**
   * Set binding context.
   * @param context_p
   */
  public void setBindingContext(DataBindingContext context_p) {
    _bindingContext = context_p;
  }

  /**
   * Set disabled color.
   * @param color_p
   */
  protected void setDisabledColor(Color color_p) {
    _disabledColor = color_p;
  }

  /**
   * Set context in use for edition.
   * @param context_p
   */
  public void setEditionContext(Context context_p) {
    _editionContext = context_p;
  }

  /**
   * Set enabled state.
   * @param enabled_p
   */
  protected void setEnabled(boolean enabled_p) {
    doSetEnabled(enabled_p);
    if (null != _customizer) {
      _customizer.setEnabled(enabled_p);
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.IValueHandler#setNewValue(java.lang.String)
   */
  public void setNewValue(String newValue_p) {
    // Default implementation does only update the text control.
    // This is up to the implementors to update the model.
    Text text = getInsertionControl();
    text.setText(newValue_p);
  }

  /**
   * Set handled variable.
   * @param variable_p
   */
  protected void setVariable(AbstractVariable variable_p) {
    _variable = variable_p;
  }
}