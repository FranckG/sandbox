/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.service.ILiveValidator;
import org.eclipse.emf.validation.service.ModelValidationService;

import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.Context;

/**
 * Live validation adapter.
 * @author t0076261
 */
public class LiveValidationContentAdapter extends AdapterImpl {
  /**
   * Unique instance.
   */
  private static LiveValidationContentAdapter __instance;
  /**
   * Validation context.
   */
  protected Context _context;
  /**
   * Live validation handlers.
   */
  private Collection<ILiveValidationHandler> _handlers;
  /**
   * Live validator.
   */
  private ILiveValidator _validator;

  /**
   * Constructor.
   */
  private LiveValidationContentAdapter() {
    // Create validator.
    _validator = (ILiveValidator) ModelValidationService.getInstance().newValidator(EvaluationMode.LIVE);
    // Initialize handlers.
    _handlers = new HashSet<ILiveValidationHandler>(0);
  }

  /**
   * Add validation handler.
   * @param handler_p
   */
  public void addLiveValidationHandler(ILiveValidationHandler handler_p) {
    if (null != handler_p) {
      _handlers.add(handler_p);
    }
  }

  /**
   * @see org.eclipse.emf.ecore.util.EContentAdapter#notifyChanged(org.eclipse.emf.common.notify.Notification)
   */
  @Override
  public void notifyChanged(Notification notification_p) {
    Object notifier = notification_p.getNotifier();
    // Precondition.
    if (!(notifier instanceof EObject)) {
      return;
    }
    Context context = ModelUtil.getContext(notifier);
    // Preconditions.
    if ((null == _context) || (null == context) || (null == context.eResource()) || context.eResource().isLoading()) {
      return;
    }
    // Set validation context.
    ModelValidationActivator.getInstance().setCurrentValidationContext(new ValidationContext(_context));
    try {
      // Trigger validation.
      IStatus status = _validator.validate(notification_p);
      // Clone handlers collection, so that handlers can be removed dynamically from implementation.
      Collection<ILiveValidationHandler> handlers = new ArrayList<ILiveValidationHandler>(_handlers);
      // Invoke handlers.
      for (ILiveValidationHandler handler : handlers) {
        Couple<EObject, EStructuralFeature> changedElement =
            new Couple<EObject, EStructuralFeature>((EObject) notifier, (EStructuralFeature) notification_p.getFeature());
        if (!status.isOK()) {
          handler.handleValidationFailed(status, changedElement);
        } else {
          handler.handleValidationSuccessful(changedElement);
        }
      }
    } finally {
      // Dispose validation context.
      ModelValidationActivator.getInstance().disposeCurrentValidationContext();
    }
  }

  /**
   * Remove validation handler.
   * @param handler_p
   */
  public void removeLiveValidationHandler(ILiveValidationHandler handler_p) {
    _handlers.remove(handler_p);
  }

  /**
   * Set context to use for validation process.
   * @param context_p
   */
  public void setValidationContext(Context context_p) {
    _context = context_p;
  }

  /**
   * Get unique instance.
   * @return
   */
  public static LiveValidationContentAdapter getInstance() {
    if (null == __instance) {
      __instance = new LiveValidationContentAdapter();
    }
    return __instance;
  }

  /**
   * Live validation handler.
   * @author t0076261
   */
  public interface ILiveValidationHandler {
    /**
     * Handle validation failure.
     * @param validationStatus_p The failure status.
     * @param changedElement_p element changed in model.
     */
    public void handleValidationFailed(IStatus validationStatus_p, Couple<EObject, EStructuralFeature> changedElement_p);

    /**
     * No error detected during validation.
     * @param changedElement_p element changed in model.
     */
    public void handleValidationSuccessful(Couple<EObject, EStructuralFeature> changedElement_p);
  }
}