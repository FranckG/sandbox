/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.validation.constraint.structure;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;

import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.InstallationCategory;
import com.thalesgroup.orchestra.framework.model.validation.constraint.AbstractConstraint;

/**
 * A constraint that ensures that each component has at least one version selected in current hierarchy.
 * @author t0076261
 */
public class VersionsAreSelectedConstraint extends AbstractConstraint<InstallationCategory> {
  /**
   * @see com.thalesgroup.orchestra.framework.model.validation.constraint.AbstractConstraint#batchValidate(com.thalesgroup.orchestra.framework.model.contexts.ModelElement,
   *      org.eclipse.emf.validation.IValidationContext)
   */
  @Override
  protected IStatus batchValidate(InstallationCategory installationCategory_p, IValidationContext context_p) {
    // Precondition.
    // By construction, only products are not referenceable.
    if (!installationCategory_p.isReferenceable()) {
      return null;
    }
    // Get element path.
    String elementPath = ModelUtil.getElementPath(installationCategory_p);
    InstallationCategory selectedVersion = ModelUtil.getSelectedVersionForPath(elementPath, getAskingContext(), false);
    // No version selected for this component !
    if (null == selectedVersion) {
      addStatus(createFailureStatusWithDescription(installationCategory_p, context_p, getFullPath(installationCategory_p)));
    }
    return null;
  }
}