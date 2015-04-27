/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.oe.ui.actions;

import org.eclipse.jface.action.Action;

import com.thalesgroup.orchestra.framework.oe.ui.OrchestraExplorerState;
import com.thalesgroup.orchestra.framework.oe.ui.OrchestraExplorerState.SortType;

/**
 * @author S0024585
 */
public class SortAction extends Action {
  protected static final String ACTION_ID = "com.thalesgroup.orchestra.framework.oe.ui.actions.SortAction"; //$NON-NLS-1$
  protected SortType _type;
  protected OrchestraExplorerState _orchestraExplorerState;

  public SortAction(SortType type_p, String label_p, String tooltip_p, OrchestraExplorerState orchestraExplorerState_p) {
    super(ACTION_ID, AS_CHECK_BOX);
    setId(ACTION_ID);
    _type = type_p;
    setText(label_p);
    setToolTipText(tooltip_p);
    _orchestraExplorerState = orchestraExplorerState_p;
  }

  /**
   * @see org.eclipse.jface.action.Action#isChecked()
   */
  @Override
  public boolean isChecked() {
    return ((_type == _orchestraExplorerState.getSortType()) && !_orchestraExplorerState.isFolded());
  }

  /**
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    _orchestraExplorerState.setSortType(_type);
  }
}
