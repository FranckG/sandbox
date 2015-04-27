/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.viewer;

import org.eclipse.jface.viewers.StructuredSelection;

import com.thalesgroup.orchestra.framework.model.contexts.Context;

/**
 * A {@link StructuredSelection} with additional context information.
 * @author t0076261
 */
public class StructuredSelectionWithContext extends StructuredSelection {
  /**
   * Context reminder.
   */
  private Context _context;

  /**
   * Constructor.
   * @param element_p
   */
  public StructuredSelectionWithContext(Object element_p) {
    super(element_p);
  }

  /**
   * Get associated context.
   * @return
   */
  public Context getContext() {
    return _context;
  }

  /**
   * Set associated context.
   * @param context
   */
  public void setContext(Context context) {
    _context = context;
  }
}