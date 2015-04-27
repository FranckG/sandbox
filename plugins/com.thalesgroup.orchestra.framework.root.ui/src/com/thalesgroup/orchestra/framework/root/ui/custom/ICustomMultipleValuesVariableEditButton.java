/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.root.ui.custom;

import java.util.List;

/**
 * Define a new button for a given variable
 * @author s0040806
 */
public interface ICustomMultipleValuesVariableEditButton {

  /**
   * Get button label
   */
  String getLabel();

  /**
   * Get new value for variable
   */
  List<String> getValues();
}
