/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment.ui;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * Give empty implementations for some methods of IVariablesHandler.
 * @author T0052089
 */
public abstract class AbstractVariablesHandler implements IVariablesHandler {

  private VariableType _variableType;

  /**
   * @param variableType_p the variableType to set
   */
  public void setVariableType(VariableType variableType_p) {
    _variableType = variableType_p;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.ui.IVariablesHandler#getVariableType()
   */
  public VariableType getVariableType() {
    if (null == _variableType)
      return VariableType.Unspecified;
    return _variableType;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.ui.IVariablesHandler#isVariableReadOnly()
   */
  public boolean isVariableReadOnly() {
    // Not applicable for most cases.
    return false;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.ui.IVariablesHandler#createVariablesButton(org.eclipse.ui.forms.widgets.FormToolkit,
   *      org.eclipse.swt.widgets.Composite, com.thalesgroup.orchestra.framework.environment.ui.IVariablesHandler.IReferencingValueHandler)
   */
  public Button createVariablesButton(FormToolkit toolkit_p, Composite buttonComposite_p, IReferencingValueHandler referencingValueHandler_p) {
    // Not applicable for most cases (since often there is no UI to provide with).
    return null;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.ui.IVariablesHandler#getSubstitutedValue(java.lang.String)
   */
  public abstract String getSubstitutedValue(String rawValue_p);
}
