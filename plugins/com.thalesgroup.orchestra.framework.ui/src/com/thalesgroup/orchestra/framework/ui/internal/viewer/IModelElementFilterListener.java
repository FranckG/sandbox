/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.viewer;

import com.thalesgroup.orchestra.framework.ui.internal.viewer.ModelElementFilter.FilterState;

/**
 * {@link ModelElementFilter} listener.
 * @author T0052089
 */
public interface IModelElementFilterListener {
  /**
   * FilterState has been changed to specified one.
   * @param newFilterState_p
   */
  public void modelElementFilterChanged(FilterState newFilterState_p);
}
