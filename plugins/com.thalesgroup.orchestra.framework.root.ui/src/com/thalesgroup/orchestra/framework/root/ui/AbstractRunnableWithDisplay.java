/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.root.ui;

import org.eclipse.swt.widgets.Display;

/**
 * A {@link Runnable} that has access to display currently in use (if any).
 * @author t0076261
 */
public abstract class AbstractRunnableWithDisplay implements Runnable {
  /**
   * Display reference.
   */
  private Display _display;

  /**
   * Get display.
   * @return
   */
  public Display getDisplay() {
    return _display;
  }

  /**
   * Set display to use.
   * @param display_p
   */
  public void setDisplay(Display display_p) {
    _display = display_p;
  }
}