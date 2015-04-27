/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.wizard.page;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;

/**
 * Edit overriding variable page. In this page, fields of the overridden variable (Name, Description, Final, Mandatory) are disabled, variable type selection
 * widgets (mono/multi) are not displayed, only value(s) can be modified/added.
 * @author t0076261
 */
public class EditOverridingVariablePage extends AbstractReadOnlyEditVariablePage {
  /**
   * Overridden variable reference.
   */
  private Variable _overriddenVariable;
  /**
   * Overriding variable.
   */
  private OverridingVariable _overridingVariable;
  /**
   * Edition context.
   */
  private Context _context;

  /**
   * Constructor.
   */
  public EditOverridingVariablePage(Variable overriddenVariable_p, OverridingVariable overridingVariable_p, Context editionContext_p) {
    super("EditOverridingVariable"); //$NON-NLS-1$
    _overriddenVariable = overriddenVariable_p;
    _overridingVariable = overridingVariable_p;
    _context = editionContext_p;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractEditVariablePage#createMonoValueEditionHandler(org.eclipse.ui.forms.widgets.FormToolkit,
   *      org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected AbstractValueEditionHandler createMonoValueEditionHandler(FormToolkit toolkit_p, Composite parent_p) {
    // Not allowed to change multi/mono property, so that this part of the edition is of no interest.
    if (_overriddenVariable.isMultiValued()) {
      return null;
    }
    return new OVMonoValueEditionHandler(_overridingVariable);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractEditVariablePage#createMultipleValuesEditionHandler(org.eclipse.ui.forms.widgets.FormToolkit,
   *      org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected AbstractValueEditionHandler createMultipleValuesEditionHandler(FormToolkit toolkit_p, Composite parent_p) {
    // Not allowed to change multi/mono property, so that this part of the edition is of no interest.
    if (!_overriddenVariable.isMultiValued()) {
      return null;
    }
    return new OVMultipleValuesEditionHandler(_overridingVariable);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractEditVariablePage#getEditionContext()
   */
  @Override
  protected Context getEditionContext() {
    return _context;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractEditVariablePage#getVariable()
   */
  @Override
  protected AbstractVariable getVariable() {
    return _overriddenVariable;
  }

}