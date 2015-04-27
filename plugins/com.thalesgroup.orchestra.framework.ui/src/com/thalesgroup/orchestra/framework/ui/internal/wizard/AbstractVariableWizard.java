/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.wizard;

import org.eclipse.emf.common.util.EList;

import com.thalesgroup.orchestra.framework.common.ui.wizards.AbstractChangeWizard;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsFactory;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;

/**
 * Abstract variable wizard.<br>
 * Handles variable integrity at finish time.
 * @author t0076261
 */
public abstract class AbstractVariableWizard extends AbstractChangeWizard<Variable> {
  /**
   * @see com.thalesgroup.orchestra.framework.common.ui.wizards.AbstractChangeWizard#getCommandLabel()
   */
  @Override
  protected String getCommandLabel() {
    return Messages.AbstractVariableWizard_Command_Label;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.common.ui.wizards.AbstractChangeWizard#performFinish()
   */
  @Override
  public boolean performFinish() {
    boolean result = false;
    try {
      validateEdit();
    } finally {
      result = super.performFinish();
    }
    return result;
  }

  /**
   * Validate edit.<br>
   * Allows to modify contents before stopping the model recording.
   */
  protected void validateEdit() {
    Variable variable = getObject();
    EList<VariableValue> values = variable.getValues();
    // Make sure a mono-value variable is not containing more than one value.
    if (!variable.isMultiValued()) {
      while (values.size() > 1) {
        values.remove(values.size() - 1);
      }
    }
    // Make sure a variable has at least one value.
    if (values.isEmpty()) {
      values.add(ContextsFactory.eINSTANCE.createVariableValue());
    }
  }
}