/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model;

import com.thalesgroup.orchestra.framework.model.contexts.Context;

/**
 * A provider of the context being edited (referred to as the edition context).
 * @author t0076261
 */
public interface IEditionContextProvider {
  /**
   * Get context in use for edition.
   * @return
   */
  Context getEditionContext();
}