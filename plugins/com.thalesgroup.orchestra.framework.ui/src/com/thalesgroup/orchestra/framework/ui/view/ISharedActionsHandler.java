/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.view;

import org.eclipse.emf.edit.ui.action.CopyAction;
import org.eclipse.emf.edit.ui.action.CutAction;
import org.eclipse.emf.edit.ui.action.DeleteAction;
import org.eclipse.emf.edit.ui.action.PasteAction;

/**
 * Shared actions (between master and details parts) handler.<br>
 * @author t0076261
 */
public interface ISharedActionsHandler {
  /**
   * Get copy action.
   * @return A not <code>null</code> implementation of {@link CopyAction}.
   */
  public CopyAction getCopyAction();

  /**
   * Get cut action.
   * @return A not <code>null</code> implementation of {@link CutAction}.
   */
  public CutAction getCutAction();

  /**
   * Get delete action.
   * @return A not <code>null</code> implementation of {@link DeleteAction}.
   */
  public DeleteAction getDeleteAction();

  /**
   * Get paste action.
   * @return A not <code>null</code> implementation of {@link PasteAction}.
   */
  public PasteAction getPasteAction();
}