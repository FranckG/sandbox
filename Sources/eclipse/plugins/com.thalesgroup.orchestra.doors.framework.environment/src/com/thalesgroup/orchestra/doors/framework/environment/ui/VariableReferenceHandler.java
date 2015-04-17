/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.doors.framework.environment.ui;

import org.eclipse.swt.widgets.Text;

import com.thalesgroup.orchestra.framework.environment.ui.IVariablesHandler.IReferencingValueHandler;

/**
 * Callbacks for the variable selection dialog.
 * @author S0032874
 */
public class VariableReferenceHandler implements IReferencingValueHandler {
  /**
   * Field to fill when a variable reference has been selected.
   */
  private final Text _field;

  /**
   * Constructor.
   * @param textField_p
   */
  public VariableReferenceHandler(Text textField_p) {
    _field = textField_p;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.ui.IVariablesHandler.IReferencingValueHandler#handleSelectedValue(java.lang.String)
   */
  @Override
  public void handleSelectedValue(String referencingValue_p) {
    _field.insert(referencingValue_p);
  }

}