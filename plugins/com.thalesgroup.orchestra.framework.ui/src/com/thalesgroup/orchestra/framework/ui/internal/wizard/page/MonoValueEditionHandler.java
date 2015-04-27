/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.wizard.page;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.databinding.EMFObservables;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.common.CommonActivator;
import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.root.ui.custom.ICustomMonoValueVariableEditButton;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper.LayoutType;
import com.thalesgroup.orchestra.framework.ui.wizard.custom.CustomVariableEditButtonRegistry;

/**
 * Mono value handler.
 * @author t0076261
 */
public class MonoValueEditionHandler extends AbstractValueEditionHandler {
  /**
   * The text that present the substituted value of the variable.
   */
  protected Text _substitutedText;
  /**
   * Text reminder.
   */
  protected Text _text;

  /**
   * Activate binding.<br>
   * That is, ensure that both representations and model are updated accordingly.
   */
  protected void activateBinding() {
    try {
      VariableValue value = getVariableValue();
      _bindingContext.bindValue(SWTObservables.observeText(_text, SWT.FocusOut),
          EMFObservables.observeValue(value, ContextsPackage.Literals.VARIABLE_VALUE__VALUE));
    } catch (Exception e_p) {
      CommonActivator.getInstance().logMessage("Unable to bind mono value for " + _variable.getName(), IStatus.ERROR, e_p); //$NON-NLS-1$
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractValueEditionHandler#createButtonsPart(org.eclipse.ui.forms.widgets.FormToolkit,
   *      org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Composite createButtonsPart(FormToolkit toolkit_p, Composite parent_p) {
    // Add the composite to contains buttons.
    Composite buttonComposite = FormHelper.createCompositeWithLayoutType(toolkit_p, parent_p, LayoutType.GRID_LAYOUT, 1, true);
    GridData data = (GridData) buttonComposite.getLayoutData();
    data.grabExcessHorizontalSpace = false;
    return buttonComposite;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractValueEditionHandler#createValuesPart(org.eclipse.ui.forms.widgets.FormToolkit,
   *      org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Composite createValuesPart(FormToolkit toolkit_p, Composite parent_p) {
    _enabledColor = parent_p.getDisplay().getSystemColor(SWT.COLOR_WHITE);
    // Disallow to change this size when the dialog is resized.
    GridData data = (GridData) parent_p.getLayoutData();
    data.grabExcessVerticalSpace = false;
    // Add value composite.
    Composite monoValueComposite = FormHelper.createCompositeWithLayoutType(toolkit_p, parent_p, LayoutType.GRID_LAYOUT, 1, true);
    data = (GridData) monoValueComposite.getLayoutData();
    data.verticalAlignment = SWT.BEGINNING;
    _text = toolkit_p.createText(monoValueComposite, ICommonConstants.EMPTY_STRING, SWT.FILL | SWT.BORDER);
    _text.setFocus();
    FormHelper.updateControlLayoutDataWithLayoutTypeData(_text, LayoutType.GRID_LAYOUT);
    _substitutedText = toolkit_p.createText(monoValueComposite, ICommonConstants.EMPTY_STRING, SWT.FILL);
    FormHelper.updateControlLayoutDataWithLayoutTypeData(_substitutedText, LayoutType.GRID_LAYOUT);
    _substitutedText.setEditable(false);
    _substitutedText.setToolTipText(Messages.MonoValueEditionHandler_ToolTip_Text_Substituted);
    _text.addModifyListener(new ModifyListener() {
      public void modifyText(ModifyEvent e_p) {
        _substitutedText.setText(DataUtil.getSubstitutedValue(((Text) e_p.getSource()).getText(), _editionContext));
      }
    });
    // Finally, activate binding.
    activateBinding();
    return monoValueComposite;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractValueEditionHandler#doSetEnabled(boolean)
   */
  @Override
  protected void doSetEnabled(boolean enabled_p) {
    // There is a far easier way to write this, but since both monoValue and multiValues handlers are participating,
    // it makes more sense to do this that way.
    if (enabled_p && _variable.isMultiValued()) {
      _variable.setMultiValued(false);
    }
    _text.setEditable(enabled_p);
    _text.setBackground(enabled_p ? _enabledColor : _disabledColor);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.IValueHandler#editionCancelled()
   */
  public void editionCancelled() {
    // Nothing to do.
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.IValueHandler#editionFinished()
   */
  public void editionFinished() {
    // Nothing to do.
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractValueEditionHandler#getInsertionControl()
   */
  @Override
  protected Text getInsertionControl() {
    return _text;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractValueEditionHandler#getValue()
   */
  @Override
  public String getValue() {
    String value = getVariableValue().getValue();
    if (null == value) {
      value = ICommonConstants.EMPTY_STRING;
    }
    return value;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.IValueHandler#getValueIndex()
   */
  public int getValueIndex() {
    return 0;
  }

  /**
   * Get handled variable value.
   * @return
   */
  public VariableValue getVariableValue() {
    // Mono edition case.
    return _variable.getValues().get(getValueIndex());
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractValueEditionHandler#isEnabled()
   */
  @Override
  protected boolean isEnabled() {
    return super.isEnabled() && !_variable.isMultiValued();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractValueEditionHandler#setNewValue(java.lang.String)
   */
  @Override
  public void setNewValue(String newValue_p) {
    // Update model.
    getVariableValue().setValue(newValue_p);
    // Then update UI.
    super.setNewValue(newValue_p);
  }

  /**
   * Create a new variable edit button
   * @param toolkit_p Form toolkit
   * @param buttonsComposite_p Buttons composite
   * @param customButton_p Button to add
   */
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
        setNewValue(newValue);
      }
    };
    button.addSelectionListener(adapter);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractValueEditionHandler#createCustomButtons(org.eclipse.ui.forms.widgets.FormToolkit,
   *      org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected void createCustomButtons(FormToolkit toolkit_p, Composite buttonsComposite_p) {
    CustomVariableEditButtonRegistry registry = CustomVariableEditButtonRegistry.getInstance();
    List<ICustomMonoValueVariableEditButton> list = registry.getButtonListForMonoValueVariable(ModelUtil.getElementPath(getVariable()));
    if (null != list) {
      for (ICustomMonoValueVariableEditButton button : list) {
        createCustomButton(toolkit_p, buttonsComposite_p, button);
      }
    }
  }

}