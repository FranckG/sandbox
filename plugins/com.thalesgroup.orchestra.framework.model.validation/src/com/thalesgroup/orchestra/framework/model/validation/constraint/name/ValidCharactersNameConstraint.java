/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.validation.constraint.name;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;

import com.thalesgroup.orchestra.framework.common.helper.ProjectHelper;
import com.thalesgroup.orchestra.framework.common.helper.ProjectHelper.ValidationException;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.validation.constraint.AbstractConstraint;

/**
 * A named element should have a valid name without special characters.
 * @author t0076261
 */
public class ValidCharactersNameConstraint extends AbstractConstraint<Context> {
	
	/**
	 * @see com.thalesgroup.orchestra.framework.model.validation.constraint.AbstractConstraint#batchValidate(com.thalesgroup.orchestra.framework.model.contexts.ModelElement,
	 *      org.eclipse.emf.validation.IValidationContext)
	 */
	@Override
	protected IStatus batchValidate(final Context target_p, final IValidationContext context_p) {
		return null;
	}
	
	/**
	 * @see com.thalesgroup.orchestra.framework.model.validation.constraint.AbstractConstraint#liveValidate(com.thalesgroup.orchestra.framework.model.contexts.ModelElement,
	 *      org.eclipse.emf.validation.IValidationContext)
	 */
	@Override
	protected IStatus liveValidate(final Context target_p, final IValidationContext context_p) {
		final String name = target_p.getName();
		// Overriding variables are not to be tested.
		if (ContextsPackage.Literals.OVERRIDING_VARIABLE.isInstance(target_p)) {
			return null;
		}
		try {
			ProjectHelper.validateProjectName(name);
		}
		catch (final ValidationException ve_p) {
			return this.createFailureStatusWithDescription(target_p, context_p, this.getFullPath(target_p), ve_p.getMessage());
		}
		return null;
	}
}