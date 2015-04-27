/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.wizard.page;

import org.eclipse.emf.databinding.EMFObservables;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.contexts.NamedElement;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;
import com.thalesgroup.orchestra.framework.ui.internal.wizard.Messages;

/**
 * Abstract edit variable wizard page.
 * @author t0076261
 */
public abstract class AbstractEditVariablePage extends AbstractEditElementDescriptionPage {
  /**
   * Mono value handler.
   */
  protected AbstractValueEditionHandler _monoValueHandler;
  /**
   * Multiple values handler.
   */
  protected AbstractValueEditionHandler _multipleValuesHandler;

  /**
   * Constructor.
   * @param pageName_p
   */
  protected AbstractEditVariablePage(String pageName_p) {
    super(pageName_p);
  }

  /**
   * Add edition for final.
   * @param toolkit_p
   * @param parent_p
   * @return
   */
  protected Control addFinalEdition(FormToolkit toolkit_p, Composite parent_p) {
    Button button = FormHelper.createButton(toolkit_p, parent_p, Messages.AbstractEditVariablePage_Button_Title_Final, SWT.CHECK);
    GridData data = new GridData();
    data.horizontalAlignment = SWT.FILL;
    data.horizontalSpan = getContainerColumnsCount();
    button.setLayoutData(data);
    _bindingContext.bindValue(SWTObservables.observeSelection(button),
        EMFObservables.observeValue(getVariable(), ContextsPackage.Literals.ABSTRACT_VARIABLE__FINAL));
    return button;
  }

  /**
   * Add edition for mandatory.
   * @param toolkit_p
   * @param parent_p
   * @return
   */
  protected Control addMandatoryEdition(FormToolkit toolkit_p, Composite parent_p) {
    Button button = FormHelper.createButton(toolkit_p, parent_p, Messages.AbstractEditVariablePage_Button_Title_Mandatory, SWT.CHECK);
    GridData data = new GridData();
    data.horizontalAlignment = SWT.FILL;
    data.horizontalSpan = getContainerColumnsCount();
    button.setLayoutData(data);
    _bindingContext
        .bindValue(SWTObservables.observeSelection(button), EMFObservables.observeValue(getVariable(), ContextsPackage.Literals.VARIABLE__MANDATORY));
    return button;
  }

  /**
   * Add edition for value.
   * @param toolkit_p
   * @param parent_p
   * @return
   */
  protected Control addValueEdition(FormToolkit toolkit_p, final Composite parent_p) {
    // Group.
    Group group = new Group(parent_p, SWT.NONE);
    group.setText(Messages.AbstractEditVariablePage_Label_Title_Value);
    GridData data = new GridData();
    data.horizontalAlignment = SWT.FILL;
    data.horizontalSpan = getContainerColumnsCount();
    data.grabExcessHorizontalSpace = true;
    data.verticalAlignment = SWT.FILL;
    data.grabExcessVerticalSpace = true;
    group.setLayoutData(data);
    group.setLayout(new GridLayout(1, false));
    // Value handlers.
    boolean buttonsEnabled = shouldEnableMultiplicityChoice();
    // Key = mono button.
    // Value = multiple button.
    final Couple<Button, Button> buttons = new Couple<Button, Button>(null, null);
    _monoValueHandler = createMonoValueEditionHandler(toolkit_p, group);
    if (null != _monoValueHandler) {
      if (buttonsEnabled) {
        buttons.setKey(FormHelper.createButton(toolkit_p, group, Messages.AbstractEditVariablePage_Button_Title_SingleValue, SWT.RADIO));
      }
      _monoValueHandler.setVariable(getVariable());
      _monoValueHandler.setBindingContext(_bindingContext);
      _monoValueHandler.setEditionContext(getEditionContext());
      _monoValueHandler.setDisabledColor(group.getBackground());
      _monoValueHandler.createControl(toolkit_p, group);
      boolean enabled = _monoValueHandler.isEnabled();
      if (buttonsEnabled) {
        buttons.getKey().setSelection(enabled);
      }
      _monoValueHandler.setEnabled(enabled);
    }
    _multipleValuesHandler = createMultipleValuesEditionHandler(toolkit_p, group);
    if (null != _multipleValuesHandler) {
      if (buttonsEnabled) {
        buttons.setValue(FormHelper.createButton(toolkit_p, group, Messages.AbstractEditVariablePage_Button_Title_MultipleValues, SWT.RADIO));
      }
      _multipleValuesHandler.setVariable(getVariable());
      _multipleValuesHandler.setEditionContext(getEditionContext());
      _multipleValuesHandler.setBindingContext(_bindingContext);
      _multipleValuesHandler.setDisabledColor(group.getBackground());
      Composite multipleValueComposite = _multipleValuesHandler.createControl(toolkit_p, group);
      // Give a preferred vertical size to the table to avoid too high dialog.
      GridData gridData = (GridData) multipleValueComposite.getLayoutData();
      gridData.minimumHeight = 160;
      gridData.grabExcessVerticalSpace = true;
      boolean enabled = _multipleValuesHandler.isEnabled();
      if (buttonsEnabled) {
        buttons.getValue().setSelection(enabled);
      }
      _multipleValuesHandler.setEnabled(enabled);
    }
    // Manually bind variable state to buttons.
    if (buttonsEnabled) {
      SelectionAdapter adapter = new SelectionAdapter() {
        /**
         * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
         */
        @Override
        public void widgetSelected(SelectionEvent e_p) {
          Widget widget = e_p.widget;
          if (buttons.getKey() == widget) {
            if (null != _monoValueHandler) {
              _monoValueHandler.setEnabled(true);
            }
            if (null != _multipleValuesHandler) {
              _multipleValuesHandler.setEnabled(false);
            }
          } else if (buttons.getValue() == widget) {
            if (null != _monoValueHandler) {
              _monoValueHandler.setEnabled(false);
            }
            if (null != _multipleValuesHandler) {
              _multipleValuesHandler.setEnabled(true);
            }
          }
        }
      };
      buttons.getKey().addSelectionListener(adapter);
      buttons.getValue().addSelectionListener(adapter);
      // Gives the implementation the opportunity to activate/deactivate these buttons.
      handleValueButtonsActivation(buttons.getKey(), buttons.getValue());
    }
    return group;
  }

  /**
   * Create mono value edition handler.
   * @param toolkit_p
   * @param parent_p
   * @return
   */
  protected AbstractValueEditionHandler createMonoValueEditionHandler(FormToolkit toolkit_p, Composite parent_p) {
    // An EnvironmentVariable is necessarily a multi values variable -> no need to create a mono value handler.
    if (ContextsPackage.Literals.ENVIRONMENT_VARIABLE.isInstance(getVariable())) {
      return null;
    }
    return new MonoValueEditionHandler();
  }

  /**
   * Create multiple values edition handler.
   * @param toolkit_p
   * @param parent_p
   * @return
   */
  protected AbstractValueEditionHandler createMultipleValuesEditionHandler(FormToolkit toolkit_p, Composite parent_p) {
    return new MultipleValuesEditionHandler();
  }

  /**
   * @see org.eclipse.jface.dialogs.DialogPage#dispose()
   */
  @Override
  public void dispose() {
    try {
      super.dispose();
    } finally {
      if (null != _monoValueHandler) {
        _monoValueHandler.dispose();
        _monoValueHandler = null;
      }
      if (null != _multipleValuesHandler) {
        _multipleValuesHandler.dispose();
        _multipleValuesHandler = null;
      }
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.common.ui.wizards.AbstractFormsWizardPage#doCreateControl(org.eclipse.swt.widgets.Composite,
   *      org.eclipse.ui.forms.widgets.FormToolkit)
   */
  @Override
  protected Composite doCreateControl(Composite parent_p, FormToolkit toolkit_p) {
    Composite composite = super.doCreateControl(parent_p, toolkit_p);
    /* Value */
    addValueEdition(toolkit_p, composite);
    /* Description */
    addDescriptionEdition(toolkit_p, composite);
    /* Final */
    addFinalEdition(toolkit_p, composite);
    /* Mandatory */
    addMandatoryEdition(toolkit_p, composite);
    return composite;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractEditElementDescriptionPage#getDescriptionEAttribute()
   */
  @Override
  protected EAttribute getDescriptionEAttribute() {
    return ContextsPackage.Literals.VARIABLE__DESCRIPTION;
  }

  /**
   * Get edition context.
   * @return
   */
  protected abstract Context getEditionContext();

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractEditNamedElementPage#getNamedElement()
   */
  @Override
  protected NamedElement getNamedElement() {
    return getVariable();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.common.ui.wizards.AbstractFormsWizardPage#getPageTitle()
   */
  @Override
  protected String getPageTitle() {
    return Messages.AbstractEditVariablePage_Page_Title;
  }

  /**
   * Get edited variable.
   * @return
   */
  protected abstract AbstractVariable getVariable();

  /**
   * Handle specified buttons status.<br>
   * Default implementation does nothing.
   * @param monoValueButton_p
   * @param multipleValuesButton_p
   */
  protected void handleValueButtonsActivation(Button monoValueButton_p, Button multipleValuesButton_p) {
    // Do nothing.
  }

  /**
   * Should multiplicity choice buttons be enabled ?<br>
   * Default implementation is true as this is intended to represent a variable currently owned by the user.<br>
   * For EnvironmentVariables, false is returned since this is necessarily a multi values variable.
   * @return
   */
  protected boolean shouldEnableMultiplicityChoice() {
    if (ContextsPackage.Literals.ENVIRONMENT_VARIABLE.isInstance(getVariable())) {
      return false;
    }
    return true;
  }
}