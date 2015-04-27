/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.oe.ui;

/**
 * Interface to implement to receive states changes.
 * @author S0024585
 */
public interface IOrchestraExplorerStateListener {
  /**
   * Notify the folding changes
   */
  public void foldingChanged();

  /**
   * Notify the "group by" changes
   */
  public void groupByChanged();

  /**
   * Notify the sort changes
   */
  public void sortChanged();
}
