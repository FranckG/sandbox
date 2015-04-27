/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.validation;

import org.eclipse.emf.validation.IValidationContext;

import com.thalesgroup.orchestra.framework.environment.validation.EnvironmentRuleRegistry.ValidationType;
import com.thalesgroup.orchestra.framework.model.contexts.Context;

/**
 * A context that can be used to retain whatever is to be shared by constraints for a validation round.<br>
 * This is already the work of the {@link IValidationContext} but it can't be initialized from outside the rules.<br>
 * This context on the contrary is handled by the validation callers.
 * @author t0076261
 */
public class ValidationContext {
  /**
   * Asking context, that is the context which validation has been asked for.
   */
  private Context _askingContext;
  /**
   * Type of validation to conduct.
   */
  private ValidationType _validationType;

  /**
   * Constructor.
   * @param askingContext_p
   */
  public ValidationContext(Context askingContext_p) {
    _askingContext = askingContext_p;
  }

  /**
   * Return asking context.
   * @return
   */
  public Context getAskingContext() {
    return _askingContext;
  }

  /**
   * Get current validation type.
   * @return A not <code>null</code> {@link ValidationType}.
   */
  public ValidationType getValidationType() {
    // Default case.
    if (null == _validationType) {
      return ValidationType.NOMINAL_TYPE;
    }
    return _validationType;
  }

  /**
   * Set current validation type.
   * @param validationType_p
   */
  public void setValidationType(ValidationType validationType_p) {
    _validationType = validationType_p;
  }

  /**
   * Free current instance reminder.
   */
  public void dispose() {
    _askingContext = null;
    _validationType = null;
  }
}