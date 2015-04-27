/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.validation;

import org.eclipse.core.runtime.IStatus;

import com.thalesgroup.orchestra.framework.model.contexts.Context;

/**
 * A validation callback that is invoked before the validation is disposed, and gives the opportunity to handle the {@link ValidationContext} content.
 * @author t0076261
 */
public abstract class AbstractValidationHandler {
  /**
   * Create {@link ValidationContext} instance to use for current validation process.<br>
   * Default implementation returns <code>null</code>.
   * @param askingContext_p
   * @return
   */
  public ValidationContext createValidationContext(Context askingContext_p) {
    return null;
  }

  /**
   * Handle validation result for specified {@link ValidationContext}.
   * @param validationContext_p
   * @param validationStatus_p The status as returned by the regular validation process.
   */
  public abstract void handleValidationResult(ValidationContext validationContext_p, IStatus validationStatus_p);
}