/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.handler;

import org.eclipse.core.runtime.IStatus;

import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ModelElement;

/**
 * Provide with validation handler implementation at model handler level.<br>
 * This is needed to avoid cycle dependencies.<br>
 * Applications should use <code>com.thalesgroup.orchestra.framework.model.validation</code> bundle directly.
 * @author t0076261
 */
public interface IValidationHandler {
  /**
   * Apply validation to specified model element within specified context.
   * @param element_p
   * @param context_p
   * @return <code>null</code> if validation is successful, an {@link IStatus} otherwise (possibly a multi status one).
   */
  public IStatus validateElement(ModelElement element_p, Context context_p);
}