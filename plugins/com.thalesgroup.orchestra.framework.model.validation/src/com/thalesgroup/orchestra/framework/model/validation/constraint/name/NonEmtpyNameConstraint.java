/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.validation.constraint.name;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.contexts.NamedElement;
import com.thalesgroup.orchestra.framework.model.validation.constraint.AbstractConstraint;

/**
 * A named element should have a non-empty name.
 * @author t0076261
 */
public class NonEmtpyNameConstraint extends AbstractConstraint<NamedElement> {
  /**
   * @see com.thalesgroup.orchestra.framework.model.validation.constraint.AbstractConstraint#batchValidate(com.thalesgroup.orchestra.framework.model.contexts.ModelElement,
   *      org.eclipse.emf.validation.IValidationContext)
   */
  @Override
  protected IStatus batchValidate(NamedElement target_p, IValidationContext context_p) {
    String name = target_p.getName();
    // Overriding variables are not to be tested.
    if (ContextsPackage.Literals.OVERRIDING_VARIABLE.isInstance(target_p)) {
      return null;
    }
    if ((null == name) || ICommonConstants.EMPTY_STRING.equals(name.trim())) {
      return createFailureStatusWithDescription(target_p, context_p, getFullPath(target_p), target_p.eClass().getName());
    }
    return null;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.validation.constraint.AbstractConstraint#liveValidate(com.thalesgroup.orchestra.framework.model.contexts.ModelElement,
   *      org.eclipse.emf.validation.IValidationContext)
   */
  @Override
  protected IStatus liveValidate(NamedElement target_p, IValidationContext context_p) {
    return batchValidate(target_p, context_p);
  }
}