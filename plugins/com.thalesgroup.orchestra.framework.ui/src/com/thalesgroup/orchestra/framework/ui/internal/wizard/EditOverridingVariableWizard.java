/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.wizard;

import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsFactory;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.ui.internal.wizard.page.EditOverridingVariablePage;

/**
 * Edit overriding variable wizard structure.
 * @author t0076261
 */
public class EditOverridingVariableWizard extends AbstractVariableWizard {
  /**
   * Edition context.
   */
  private Context _context;
  /**
   * Is overriding variable newly created ?
   */
  protected boolean _isNewlyCreated;
  /**
   * Overriding variable.
   */
  protected OverridingVariable _overridingVariable;

  /**
   * Constructor.
   * @param overriddenVariable_p
   * @param editionContext_p
   */
  public EditOverridingVariableWizard(Variable overriddenVariable_p, Context editionContext_p) {
    _context = editionContext_p;
    _isNewlyCreated = false;
    setObject(overriddenVariable_p);
    setNeedsProgressMonitor(false);
    setWindowTitle(Messages.EditOverridingVariableWizard_Wizard_Title);
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#addPages()
   */
  @Override
  public void addPages() {
    addPage(new EditOverridingVariablePage(getObject(), _overridingVariable, _context));
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.AbstractVariableWizard#performFinish()
   */
  @Override
  public boolean performFinish() {
    // Precondition.
    if (null == _overridingVariable) {
      return true;
    }
    boolean result = false;
    try {
      // Remove overriding variable if it is empty (then useless).
      if (_overridingVariable.getValues().isEmpty()) {
        // Remove existing variable.
        if (!_isNewlyCreated) {
          _context.getOverridingVariables().remove(_overridingVariable);
        }
      } else if (_isNewlyCreated) {
        // Add newly created overriding variable.
        _context.getOverridingVariables().add(_overridingVariable);
      }
    } finally {
      result = super.performFinish();
    }
    return result;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.common.ui.wizards.AbstractChangeWizard#startRecording()
   */
  @Override
  protected void startRecording() {
    // Start recording on the resource set.
    super.startRecording();
    // Stop here if variable is not modifiable.
    if (DataUtil.isUnmodifiable(getObject(), _context)) {
      return;
    }
    // Get overriding variable, if any, and edit it.
    _overridingVariable = DataUtil.getOverridingVariable(getObject(), _context);
    if ((null == _overridingVariable) || (_context != ModelUtil.getContext(_overridingVariable))) {
      // It does not exist, create it.
      _overridingVariable = ContextsFactory.eINSTANCE.createOverridingVariable();
      _overridingVariable.setOverriddenVariable(getObject());
      // Do not add it yet for it changes the model with dirty listener being upset.
      _isNewlyCreated = true;
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.AbstractVariableWizard#validateEdit()
   */
  @Override
  protected void validateEdit() {
    // Disable super behavior.
  }
}