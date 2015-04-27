/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.model.IConstraintStatus;
import org.eclipse.emf.validation.service.IBatchValidator;
import org.eclipse.emf.validation.service.ModelValidationService;

import com.thalesgroup.orchestra.framework.model.validation.constraint.AbstractConstraint.ExtendedConstraintStatus;

/**
 * Delegates to EMF Validation Framework the evaluation of all active constraints on a sub-tree of a model.
 * @author t0076261
 */
public class EValidatorAdapter extends EObjectValidator {
  /**
   * Batch validator.
   */
  private IBatchValidator _validator;

  /**
   * Constructor.
   */
  public EValidatorAdapter() {
    _validator = (IBatchValidator) ModelValidationService.getInstance().newValidator(EvaluationMode.BATCH);
    _validator.setIncludeLiveConstraints(true);
    _validator.setReportSuccesses(false);
    // Add specific traversal strategy for model structure is to be reconstructed at validation time.
    _validator.setTraversalStrategy(ValidationHelper.getInstance().createNewTraversalStrategy());
  }

  /**
   * Append specified status results as diagnostics to specified {@link DiagnosticChain}.
   * @param status_p
   * @param diagnostics_p
   */
  protected void appendDiagnostics(IStatus status_p, DiagnosticChain diagnostics_p) {
    if (status_p.isMultiStatus()) {
      IStatus[] children = status_p.getChildren();
      for (IStatus iStatus : children) {
        appendDiagnostics(iStatus, diagnostics_p);
      }
    } else if (status_p instanceof IConstraintStatus) {
      IConstraintStatus constraintStatus = (IConstraintStatus) status_p;
      // Get data.
      Collection<Object> data = new ArrayList<Object>(constraintStatus.getResultLocus());
      if (status_p instanceof ExtendedConstraintStatus) {
        data.add(((ExtendedConstraintStatus) status_p).getTargetDescription());
      }
      // Create resulting diagnostic.
      diagnostics_p.add(new BasicDiagnostic(status_p.getSeverity(), status_p.getPlugin(), status_p.getCode(), status_p.getMessage(), data.toArray()));
    }
  }

  /**
   * Has specified object already been processed ?
   * @param eObject_p
   * @param context_p
   * @return
   */
  protected boolean hasProcessed(EObject eObject_p, Map<Object, Object> context_p) {
    boolean result = false;
    if (null != context_p) {
      EObject eObject = eObject_p;
      while (null != eObject) {
        if (context_p.containsKey(eObject)) {
          result = true;
          break;
        }
        eObject = eObject.eContainer();
      }
    }
    return result;
  }

  /**
   * Remember that specified object has already been processed by the validation framework.
   * @param eObject_p
   * @param context_p
   * @param status_p
   */
  protected void processed(EObject eObject_p, Map<Object, Object> context_p, IStatus status_p) {
    if (null != context_p) {
      context_p.put(eObject_p, status_p);
    }
  }

  /**
   * @see org.eclipse.emf.ecore.util.EObjectValidator#validate(org.eclipse.emf.ecore.EClass, org.eclipse.emf.ecore.EObject,
   *      org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
   */
  @Override
  public boolean validate(EClass eClass_p, EObject eObject_p, DiagnosticChain diagnostics_p, Map<Object, Object> context_p) {
    // TODO Guillaume
    // No structural validation done here !
    // Originally done by super implementation, but it does collide with the batch validator work (using specific traversal strategy).
    // Something should be done at this level to ensure structural validness.
    //
    // Possible fix :
    // Try and extend batch validator so that it does call the structural validation for each tested element, and then retains elements already validated.
    // This would remove the responsibility to do so from EValidatorAdapter.
    // What's more, make sure the same validator class is used here and in ValidationHelper, so that the validation at switch time does apply structural checks
    // too.
    IStatus status = Status.OK_STATUS;
    if (null != diagnostics_p) {
      if (!hasProcessed(eObject_p, context_p)) {
        status = _validator.validate(eObject_p, null);
        processed(eObject_p, context_p, status);
        appendDiagnostics(status, diagnostics_p);
      }
    }
    return status.isOK();
  }
}