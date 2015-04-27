/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.validation.constraint;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.EMFEventType;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.model.ConstraintStatus;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.environment.validation.EnvironmentRuleRegistry.ValidationType;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ModelElement;
import com.thalesgroup.orchestra.framework.model.validation.ModelValidationActivator;
import com.thalesgroup.orchestra.framework.model.validation.ValidationContext;

/**
 * An abstract constraint that allows for the handling of live vs batch validation.<br>
 * It is dedicated to {@link ModelElement}s.
 * @author t0076261
 */
public abstract class AbstractConstraint<T extends EObject> extends AbstractModelConstraint {
  /**
   * List of {@link IStatus} to return for current validation.
   */
  protected List<IStatus> _statuses;

  /**
   * Add a status to resulting composite one for current validation process.<br>
   * The composite status is flushed for each new target.
   * @param status_p
   */
  protected void addStatus(IStatus status_p) {
    if (null != _statuses) {
      _statuses.add(status_p);
    }
  }

  /**
   * Batch validate specified target in specified context.
   * @param target_p
   * @param context_p
   * @return <code>null</code> if validation is successful or composite status is to be used (see {@link #addStatus(IStatus)}).
   */
  protected IStatus batchValidate(T target_p, IValidationContext context_p) {
    return context_p.createSuccessStatus();
  }

  /**
   * Create failure status with specified target element extra description (as opposed to default constraint target element).
   * @param targetPath_p
   * @param context_p
   * @param messageArguments_p
   * @return
   */
  protected IStatus createFailureStatusWithDescription(EObject targetElement_p, IValidationContext context_p, Object... messageArguments_p) {
    Assert.isNotNull(context_p);
    IStatus result = context_p.createFailureStatus(messageArguments_p);
    if (result instanceof ConstraintStatus) {
      result = new ExtendedConstraintStatus((ConstraintStatus) result, targetElement_p);
    }
    return result;
  }

  /**
   * Get {@link Context} that is hosting current validation process.
   * @return
   */
  protected Context getAskingContext() {
    return getValidationContext().getAskingContext();
  }

  /**
   * Get specified element full path, as described by {@link ModelUtil#getElementPath(Object)} prefixed by validation context.
   * @param element_p
   * @return A not <code>null</code> path.
   */
  protected String getFullPath(Object element_p) {
    if (null == element_p) {
      return ICommonConstants.EMPTY_STRING;
    }
    return getAskingContext().getName() + ModelUtil.getElementPath(element_p);
  }

  /**
   * Get validation context.
   * @return
   */
  protected ValidationContext getValidationContext() {
    ValidationContext validationContext = ModelValidationActivator.getInstance().getCurrentValidationContext();
    if (null == validationContext) {
      // Throwing an exception will lead to the rule being disabled.
      throw new RuntimeException("Unable to find validation context !"); //$NON-NLS-1$
    }
    return validationContext;
  }

  /**
   * Get target model element.
   * @param context_p
   * @return
   */
  @SuppressWarnings("unchecked")
  protected T getTarget(IValidationContext context_p) {
    return (T) context_p.getTarget();
  }

  /**
   * Get {@link ValidationType} currently in use.
   * @return
   */
  protected ValidationType getValidationType() {
    return getValidationContext().getValidationType();
  }

  /**
   * Live validate specified target in specified context.
   * @param target_p
   * @param context_p
   * @return <code>null</code> if validation is successful or composite status is to be used (see {@link #addStatus(IStatus)}).
   */
  protected IStatus liveValidate(T target_p, IValidationContext context_p) {
    return context_p.createSuccessStatus();
  }

  /**
   * @see org.eclipse.emf.validation.AbstractModelConstraint#validate(org.eclipse.emf.validation.IValidationContext)
   */
  @Override
  public IStatus validate(IValidationContext ctx_p) {
    IStatus result = null;
    _statuses = new ArrayList<IStatus>(0);
    EMFEventType type = ctx_p.getEventType();
    // Batch mode.
    if (EMFEventType.NULL == type) {
      result = batchValidate(getTarget(ctx_p), ctx_p);
    } else { // Live mode.
      result = liveValidate(getTarget(ctx_p), ctx_p);
    }
    // Deal with the non-single result case.
    if (null == result) {
      // Nothing to report through validation, it is hence successful.
      if (_statuses.isEmpty()) {
        result = ctx_p.createSuccessStatus();
      } else {
        // Multiple results, create a composite one, and return it.
        result = ConstraintStatus.createMultiStatus(ctx_p, _statuses);
        // Clear status list.
        _statuses.clear();
      }
    }
    return result;
  }

  /**
   * Element extra description type.<br>
   * Used in {@link ExtendedConstraintStatus}.
   * @author t0076261
   */
  public class ElementDescription {
    public String _path;
    public String _uri;
  }

  /**
   * A constraint status that wraps one created during a constraint validation, and adds reference to the element that was validated, if it differs from the
   * element that hosts the rule execution.
   * @author t0076261
   */
  public class ExtendedConstraintStatus extends ConstraintStatus implements IAdaptable {
    /**
     * Constraint class.
     */
    private Class<?> _constraintClass;
    /**
     * Real status, as created by the rule.
     */
    private ConstraintStatus _status;
    /**
     * Real description path.
     */
    private ElementDescription _targetDescription;

    /**
     * Constructor.
     * @param realStatus_p
     * @param targetPath_p
     */
    public ExtendedConstraintStatus(ConstraintStatus realStatus_p, EObject targetElement_p) {
      super(realStatus_p.getConstraint(), realStatus_p.getTarget(), realStatus_p.getMessage(), realStatus_p.getResultLocus());
      // Real status.
      _status = realStatus_p;
      // Target description.
      _targetDescription = new ElementDescription();
      _targetDescription._path = getFullPath(targetElement_p);
      _targetDescription._uri = EcoreUtil.getURI(targetElement_p).toString();
      // Constraint class.
      _constraintClass = AbstractConstraint.this.getClass();
    }

    /**
     * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
     */
    public Object getAdapter(Class adapter_p) {
      // Adapt to constraint class.
      if (AbstractConstraint.class.equals(adapter_p)) {
        return _constraintClass;
      }
      return null;
    }

    /**
     * @see org.eclipse.core.runtime.Status#getChildren()
     */
    @Override
    public IStatus[] getChildren() {
      return _status.getChildren();
    }

    /**
     * @see org.eclipse.core.runtime.Status#getCode()
     */
    @Override
    public int getCode() {
      return _status.getCode();
    }

    /**
     * @see org.eclipse.core.runtime.Status#getException()
     */
    @Override
    public Throwable getException() {
      return _status.getException();
    }

    /**
     * @see org.eclipse.core.runtime.Status#getMessage()
     */
    @Override
    public String getMessage() {
      return _status.getMessage();
    }

    /**
     * @see org.eclipse.core.runtime.Status#getPlugin()
     */
    @Override
    public String getPlugin() {
      return _status.getPlugin();
    }

    /**
     * @see org.eclipse.core.runtime.Status#getSeverity()
     */
    @Override
    public int getSeverity() {
      return _status.getSeverity();
    }

    /**
     * Get target description.
     * @return
     */
    public ElementDescription getTargetDescription() {
      return _targetDescription;
    }

    /**
     * @see org.eclipse.core.runtime.Status#isMultiStatus()
     */
    @Override
    public boolean isMultiStatus() {
      return _status.isMultiStatus();
    }

    /**
     * @see org.eclipse.core.runtime.Status#isOK()
     */
    @Override
    public boolean isOK() {
      return _status.isOK();
    }

    /**
     * @see org.eclipse.core.runtime.Status#matches(int)
     */
    @Override
    public boolean matches(int severityMask_p) {
      return _status.matches(severityMask_p);
    }

    /**
     * @see org.eclipse.core.runtime.Status#toString()
     */
    @Override
    public String toString() {
      return _status.toString();
    }
  }
}