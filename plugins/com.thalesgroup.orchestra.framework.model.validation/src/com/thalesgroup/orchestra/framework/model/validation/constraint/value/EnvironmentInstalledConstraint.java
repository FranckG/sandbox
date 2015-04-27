/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.validation.constraint.value;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;

import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.framework.environment.EnvironmentActivator;
import com.thalesgroup.orchestra.framework.environment.IEnvironment;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.util.ContextsResourceImpl;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.model.validation.constraint.AbstractConstraint;

/**
 * @author s0040806
 */
public class EnvironmentInstalledConstraint extends AbstractConstraint<Variable> {

  /**
   * @see com.thalesgroup.orchestra.framework.model.validation.constraint.AbstractConstraint#batchValidate(org.eclipse.emf.ecore.EObject,
   *      org.eclipse.emf.validation.IValidationContext)
   */
  @Override
  protected IStatus batchValidate(Variable target_p, IValidationContext context_p) {
    String targetPath = ModelUtil.getElementPath(target_p);
    ContextsResourceImpl resource = getAskingContext().eResource();
    // Validate ArtefactPath and ConfigurationDirectories only
    if ((null != resource) && resource.isDefault()
        || (!DataUtil.__ARTEFACTPATH_VARIABLE_NAME.equals(targetPath) && !DataUtil.__CONFIGURATIONDIRECTORIES_VARIABLE_NAME.equals(targetPath))) {
      return null;
    }

    Context askingContext = getAskingContext();
    AbstractVariable variable = DataUtil.getOverridingVariable(target_p, askingContext);
    if (null == variable) {
      variable = DataUtil.getVariable(targetPath, askingContext);
    }
    List<VariableValue> values = DataUtil.getValues(variable, askingContext);
    for (VariableValue variableValue : values) {
      // Get environment.
      EnvironmentVariableValue environmentVariableValue = (EnvironmentVariableValue) variableValue;
      String declarationId = environmentVariableValue.getEnvironmentId();
      String runtimeId = environmentVariableValue.getId();
      Couple<IStatus, IEnvironment> environmentCouple = EnvironmentActivator.getInstance().getEnvironmentRegistry().getEnvironment(declarationId, runtimeId);
      if (!environmentCouple.getKey().isOK()) {
        addStatus(createFailureStatusWithDescription(target_p, context_p, getFullPath(target_p), declarationId));
      }
    }

    return null;
  }
}
