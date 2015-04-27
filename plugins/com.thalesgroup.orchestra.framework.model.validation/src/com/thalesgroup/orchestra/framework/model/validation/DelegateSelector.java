/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.validation;

import org.eclipse.emf.validation.model.IClientSelector;

import com.thalesgroup.orchestra.framework.model.contexts.ModelElement;

/**
 * @author t0076261
 */
public class DelegateSelector implements IClientSelector {
  /**
   * @see org.eclipse.emf.validation.model.IClientSelector#selects(java.lang.Object)
   */
  public boolean selects(Object object_p) {
    return object_p instanceof ModelElement;
  }
}