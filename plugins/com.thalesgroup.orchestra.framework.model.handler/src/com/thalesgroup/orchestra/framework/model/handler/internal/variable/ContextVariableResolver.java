/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.handler.internal.variable;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.variables.IDynamicVariable;
import org.eclipse.core.variables.IDynamicVariableResolver;
import org.eclipse.core.variables.VariablesPlugin;

import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;

/**
 * Context variable resolver.<br>
 * Be advised, this instance is created by the Eclipse extension point mechanism.<br>
 * This class should not be instantiated directly.
 * @author s0011584
 */
public class ContextVariableResolver implements IDynamicVariableResolver {
  /**
   * @see org.eclipse.core.variables.IDynamicVariableResolver#resolveValue(org.eclipse.core.variables.IDynamicVariable, java.lang.String)
   */
  public String resolveValue(IDynamicVariable variable_p, String argument_p) throws CoreException {
    AbstractVariable variable = DataUtil.getVariable(argument_p, DataUtil.getSubstitutionContext());
    List<VariableValue> values = DataUtil.getRawValues(variable, DataUtil.getSubstitutionContext());
    if (null != values) {
      // Multiple values are not supported.
      // Size must be 1 exactly.
      if (ModelUtil.isMonoValuedVariable(variable) && (1 == values.size())) {
        return values.get(0).getValue();
      }
    }
    // Variable is not found, return its reference string : ${odm:/Cat1/Cat2/Var1}.
    return VariablesPlugin.getDefault().getStringVariableManager().generateVariableExpression(ModelUtil.VARIABLE_REFERENCE_TYPE_ODM, argument_p);
  }
}