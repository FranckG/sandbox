/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.wizard.page.type;

import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;

/**
 * An additional interface that defines handling specific to multiple values variables.
 * @author t0076261
 */
public interface IMultipleValueCustomizer {
  /**
   * A new variable value has just been added.<br>
   * This is an opportunity to do specific job about value addition.
   * @param newValue_p the newly created value.
   */
  public void newVariableValueAdded(VariableValue newValue_p);
}