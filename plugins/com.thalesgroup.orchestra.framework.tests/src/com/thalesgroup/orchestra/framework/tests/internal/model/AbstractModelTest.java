/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.tests.internal.model;

import com.thalesgroup.orchestra.framework.model.validation.ValidationHelper;

import junit.framework.TestCase;

/**
 * A base test class that loads tests models into the workspace.<br>
 * Also allows for the reading of specified contexts among loaded ones.
 * @author t0076261
 */
public abstract class AbstractModelTest extends TestCase {
  /**
   * Get validation helper.
   * @return
   */
  protected ValidationHelper getValidationHelper() {
    // In product situation, this is instantiated by an extension.
    // Mock this up with direct instantiation.
    ValidationHelper validationHelper = ValidationHelper.getInstance();
    if (null == validationHelper) {
      validationHelper = new ValidationHelper();
    }
    return validationHelper;
  }
}