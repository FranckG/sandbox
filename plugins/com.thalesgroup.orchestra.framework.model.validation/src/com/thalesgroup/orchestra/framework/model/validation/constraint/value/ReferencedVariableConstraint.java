/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.validation.constraint.value;

import java.text.MessageFormat;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;

import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.model.validation.constraint.AbstractConstraint;
import com.thalesgroup.orchestra.framework.model.validation.constraint.Messages;

/**
 * This constraint detects references to non existing or multi values variables in variable values.
 * @author T0052089
 */
public class ReferencedVariableConstraint extends AbstractConstraint<Variable> {
  // TODO Guillaume
  // Purposely kept here without relying on the SCM tool capabilities.
  // /**
  // * Pattern instance (group(1) -> variable type (odm, env_var...), group(2) -> variable path).
  // */
  //  private static final Pattern VARIABLE_REFERENCE_PATTERN = Pattern.compile("\\$\\{([^:]+):([^\\}]+)\\}"); //$NON-NLS-1$

  /**
   * @see com.thalesgroup.orchestra.framework.model.validation.constraint.AbstractConstraint#batchValidate(com.thalesgroup.orchestra.framework.model.contexts.ModelElement,
   *      org.eclipse.emf.validation.IValidationContext)
   */
  @Override
  protected IStatus batchValidate(Variable target_p, IValidationContext context_p) {
    final Context askingContext = getAskingContext();
    final List<VariableValue> rawVariableValues = DataUtil.getRawValues(target_p, askingContext);
    final String referenceStringPrefix = ModelUtil.getReferenceStringPrefix();
    // Go through values.
    for (VariableValue rawVariableValue : rawVariableValues) {
      String rawValue = rawVariableValue.getValue();
      if (rawValue != null && rawValue.contains(referenceStringPrefix)) {
        Matcher variableReferenceMatcher = CyclesConstraint.ODM_VARIABLE_REFERENCE_PATTERN.matcher(rawValue);
        Set<String> notFoundVariables = new TreeSet<String>();
        Set<String> multiValuesVariables = new TreeSet<String>();
        // Find variables references in the variable value.
        while (variableReferenceMatcher.find()) {
          String variableReference = variableReferenceMatcher.group();
          // Try to find the referenced variable.
          // Don't do a recursive resolution, just try to resolve references present in the variable value.
          String variablePathName = variableReferenceMatcher.group(1);
          AbstractVariable referencedOdmVariable = DataUtil.getVariable(variablePathName, askingContext);
          if (null == referencedOdmVariable) {
            // Referenced variable can't be found.
            notFoundVariables.add(variableReference);
          } else if (referencedOdmVariable != target_p && referencedOdmVariable.isMultiValued()) {
            // Referenced variable is multi valued but it's not the target variable (avoid cycle detection).
            multiValuesVariables.add(variableReference);
          }
        }
        if (!notFoundVariables.isEmpty()) {
          String notFoundVariablesString = StringUtils.join(notFoundVariables.toArray(), ", "); //$NON-NLS-1$
          String statusDetails = MessageFormat.format(Messages.ReferencedVariableConstraint_ReferencedVariableNotFound, notFoundVariablesString);
          addStatus(createFailureStatusWithDescription(rawVariableValue, context_p, getFullPath(target_p), rawValue, statusDetails));
        }
        if (!multiValuesVariables.isEmpty()) {
          String multiValuesVariablesString = StringUtils.join(multiValuesVariables.toArray(), ", "); //$NON-NLS-1$
          String statusDetails = MessageFormat.format(Messages.ReferencedVariableConstraint_ReferencedVariableIsMultiValues, multiValuesVariablesString);
          addStatus(createFailureStatusWithDescription(rawVariableValue, context_p, getFullPath(target_p), rawValue, statusDetails));
        }
      }
    }
    return null;
  }
}
