/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.validation.constraint.contextsproject;

import java.io.File;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;

import com.thalesgroup.orchestra.framework.contextsproject.ContextsProject;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.model.validation.constraint.AbstractConstraint;
import com.thalesgroup.orchestra.framework.project.CaseUnsensitiveResourceSetImpl;
import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;

/**
 * A constraint that ensures that an administrator context parent is valid.<br>
 * This constraint DOES NOT apply to user contexts (because of the retained traversal strategy in the validation process).
 * @author T0052089
 */
public class ParentProjectPathConstraint extends AbstractConstraint<ContextsProject> {
  /**
   * @see com.thalesgroup.orchestra.framework.model.validation.constraint.AbstractConstraint#batchValidate(org.eclipse.emf.ecore.EObject,
   *      org.eclipse.emf.validation.IValidationContext)
   */
  @Override
  protected IStatus batchValidate(ContextsProject target_p, IValidationContext context_p) {
    String parentProjectPath = target_p.getParentProject();
    if (null == parentProjectPath) {
      // Parent project is default context's project -> OK.
      return null;
    }
    //
    // When a FailureStatusWithDescription is created, the target element is the asking context since ContextsProject are not displayed in the contexts tree.
    //
    // Is parent path empty ?
    if (parentProjectPath.isEmpty()) {
      return createFailureStatusWithDescription(getAskingContext(), context_p, Messages.ParentProjectPathConstraint_FailureMessage_EmptyPath);
    }
    // Is parent path containing only variable reference with env_var type ?
    if (!isPathContainingEnvVarOnly(parentProjectPath)) {
      return createFailureStatusWithDescription(getAskingContext(), context_p, Messages.ParentProjectPathConstraint_FailureMessage_OnlyEnvVar);
    }
    // Is parent path indicating an existing directory ?
    String substitutedParentContextLocation = DataUtil.getSubstitutedValue(parentProjectPath, getAskingContext());
    if (!new File(substitutedParentContextLocation).isDirectory()) {
      return createFailureStatusWithDescription(getAskingContext(), context_p, Messages.ParentProjectPathConstraint_FailureMessage_NonExistingDirectory);
    }
    // Is parent path indicating an existing context ?
    RootContextsProject parentRootContextsProject =
        ProjectActivator.getInstance().getProjectHandler().getContextFromLocation(substitutedParentContextLocation, new CaseUnsensitiveResourceSetImpl());
    if (null == parentRootContextsProject) {
      return createFailureStatusWithDescription(getAskingContext(), context_p, Messages.ParentProjectPathConstraint_FailureMessage_NoContextFound);
    }
    // Is designated context the parent context of validated context ?
    if (!getAskingContext().getSuperContext().getId().equals(parentRootContextsProject.getAdministratedContext().getId())) {
      return createFailureStatusWithDescription(getAskingContext(), context_p, Messages.ParentProjectPathConstraint_FailureMessage_FoundContextNotParent);
    }
    return null;
  }

  /**
   * Check that a parent path contains only env_var variable references.
   * @param path_p
   * @return <code>true</code> if the given path contains only env_var variable references (or no variable reference), <code>false</code> otherwise.
   */
  protected boolean isPathContainingEnvVarOnly(String path_p) {
    List<ModelUtil.VariableReferenceType> variableReferences = ModelUtil.extractVariableReferences(path_p);
    for (ModelUtil.VariableReferenceType variableReference : variableReferences) {
      if (!ModelUtil.VARIABLE_REFERENCE_TYPE_ENV_VAR.equals(variableReference._variableReferencePrefix)) {
        return false;
      }
    }
    return true;
  }

}
