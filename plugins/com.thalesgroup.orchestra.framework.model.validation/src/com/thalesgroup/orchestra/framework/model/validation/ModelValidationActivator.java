/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.validation;

import org.osgi.framework.BundleContext;

import com.thalesgroup.orchestra.framework.common.activator.AbstractActivator;

/**
 * @author t0076261
 */
public class ModelValidationActivator extends AbstractActivator {
  /**
   * Shared instance.
   */
  private static ModelValidationActivator __instance;
  /**
   * Current validation context. Attached to a validation process.
   */
  private ValidationContext _currentValidationContext;

  /**
   * Dispose currently registered {@link ValidationContext}.
   */
  public void disposeCurrentValidationContext() {
    if (null != _currentValidationContext) {
      _currentValidationContext.dispose();
      _currentValidationContext = null;
    }
  }

  /**
   * @return the currentValidationContext
   */
  public ValidationContext getCurrentValidationContext() {
    return _currentValidationContext;
  }

  /**
   * @param currentValidationContext_p the currentValidationContext to set
   */
  public void setCurrentValidationContext(ValidationContext validationContext_p) {
    _currentValidationContext = validationContext_p;
  }

  /**
   * @see org.eclipse.core.runtime.Plugin#start(org.osgi.framework.BundleContext)
   */
  @Override
  public void start(BundleContext context_p) throws Exception {
    super.start(context_p);
    __instance = this;
  }

  /**
   * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
   */
  @Override
  public void stop(BundleContext context_p) throws Exception {
    super.stop(context_p);
    __instance = null;
  }

  /**
   * Get shared instance.
   * @return
   */
  public static ModelValidationActivator getInstance() {
    return __instance;
  }
}