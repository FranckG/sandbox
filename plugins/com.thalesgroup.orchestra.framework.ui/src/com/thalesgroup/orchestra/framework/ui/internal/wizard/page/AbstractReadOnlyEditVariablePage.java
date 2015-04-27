/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.wizard.page;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * This page edits a read-only variable. In this page, fields of the variable (Name, Description, Final, Mandatory) are disabled, variable type selection
 * widgets (mono/multi) are not displayed.
 * @author T0052089
 */
public abstract class AbstractReadOnlyEditVariablePage extends AbstractEditVariablePage {
  /**
   * Constructor.
   * @param pageName_p
   */
  protected AbstractReadOnlyEditVariablePage(String pageName_p) {
    super(pageName_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractEditVariablePage#addDescriptionEdition(org.eclipse.ui.forms.widgets.FormToolkit,
   *      org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Control addDescriptionEdition(FormToolkit toolkit_p, Composite parent_p) {
    Control addDescriptionEdition = super.addDescriptionEdition(toolkit_p, parent_p);
    disableControl(addDescriptionEdition, parent_p);
    return addDescriptionEdition;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractEditVariablePage#addFinalEdition(org.eclipse.ui.forms.widgets.FormToolkit,
   *      org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Control addFinalEdition(FormToolkit toolkit_p, Composite parent_p) {
    Control addFinalEdition = super.addFinalEdition(toolkit_p, parent_p);
    disableControl(addFinalEdition, parent_p);
    return addFinalEdition;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractEditVariablePage#addMandatoryEdition(org.eclipse.ui.forms.widgets.FormToolkit,
   *      org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Control addMandatoryEdition(FormToolkit toolkit_p, Composite parent_p) {
    Control addMandatoryEdition = super.addMandatoryEdition(toolkit_p, parent_p);
    disableControl(addMandatoryEdition, parent_p);
    return addMandatoryEdition;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractEditVariablePage#addNameEdition(org.eclipse.ui.forms.widgets.FormToolkit,
   *      org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Control addNameEdition(FormToolkit toolkit_p, Composite parent_p) {
    Control addNameEdition = super.addNameEdition(toolkit_p, parent_p);
    disableControl(addNameEdition, parent_p);
    return addNameEdition;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractEditVariablePage#createMonoValueEditionHandler(org.eclipse.ui.forms.widgets.FormToolkit,
   *      org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected AbstractValueEditionHandler createMonoValueEditionHandler(FormToolkit toolkit_p, Composite parent_p) {
    // Not allowed to change multi/mono property, so that this part of the edition is of no interest.
    if (getVariable().isMultiValued()) {
      return null;
    }
    return super.createMonoValueEditionHandler(toolkit_p, parent_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractEditVariablePage#createMultipleValuesEditionHandler(org.eclipse.ui.forms.widgets.FormToolkit,
   *      org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected AbstractValueEditionHandler createMultipleValuesEditionHandler(FormToolkit toolkit_p, Composite parent_p) {
    // Not allowed to change multi/mono property, so that this part of the edition is of no interest.
    if (!getVariable().isMultiValued()) {
      return null;
    }
    return super.createMultipleValuesEditionHandler(toolkit_p, parent_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractEditVariablePage#handleValueButtonsActivation(org.eclipse.swt.widgets.Button,
   *      org.eclipse.swt.widgets.Button)
   */
  @Override
  protected void handleValueButtonsActivation(Button monoValueButton_p, Button multipleValuesButton_p) {
    monoValueButton_p.setEnabled(false);
    multipleValuesButton_p.setEnabled(false);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractEditVariablePage#shouldEnableMultiplicityChoice()
   */
  @Override
  protected boolean shouldEnableMultiplicityChoice() {
    // No choice for overriding variables.
    return false;
  }
}