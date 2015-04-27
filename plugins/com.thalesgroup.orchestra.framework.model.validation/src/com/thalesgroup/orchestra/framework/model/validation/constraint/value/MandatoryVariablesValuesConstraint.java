/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.validation.constraint.value;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;

import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;
import com.thalesgroup.orchestra.framework.model.contexts.util.ContextsResourceImpl;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.model.validation.constraint.AbstractConstraint;

/**
 * Mandatory values constraint.<br>
 * All mandatory variables should have a value
 * @author s0018747
 */
public class MandatoryVariablesValuesConstraint extends AbstractConstraint<Variable> {
  /**
   * @see com.thalesgroup.orchestra.framework.model.validation.constraint.AbstractConstraint#batchValidate(com.thalesgroup.orchestra.framework.model.contexts.ModelElement,
   *      org.eclipse.emf.validation.IValidationContext)
   */
  @Override
  protected IStatus batchValidate(Variable target_p, IValidationContext context_p) {
    // Don't apply this rule on ArtefactPath and ConfigurationDirectories variables for DefaultContext.
    String targetPath = ModelUtil.getElementPath(target_p);
    ContextsResourceImpl resource = getAskingContext().eResource();
    if ((null != resource) && resource.isDefault()
        && (DataUtil.__ARTEFACTPATH_VARIABLE_NAME.equals(targetPath) || DataUtil.__CONFIGURATIONDIRECTORIES_VARIABLE_NAME.equals(targetPath))) {
      return null;
    }
    // Apply the rule.
    if (target_p.isMandatory() && !DataUtil.isVariableFilled(target_p, getAskingContext())) {
      addStatus(createFailureStatusWithDescription(target_p, context_p, getFullPath(target_p)));
    }

    return null;
  }
}