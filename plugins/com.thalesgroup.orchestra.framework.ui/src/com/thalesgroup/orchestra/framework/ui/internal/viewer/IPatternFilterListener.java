/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.viewer;

/**
 * {@link PatternFilter} listener.
 * @author t0076261
 */
public interface IPatternFilterListener {
  /**
   * Pattern has been changed to specified one.
   * @param newPattern_p
   */
  public void patternChanged(String newPattern_p);
}