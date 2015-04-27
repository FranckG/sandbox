/** 
 * <p>THALES Services - Engineering & Process Management</p>
 * <p>THALES Part Number 16 262 601</p>
 * <p>Copyright (c) 2002-2008 : THALES Services - Engineering & Process Management</p>
 */
package com.thalesgroup.orchestra.framework.oe.ui.actions;

import com.thalesgroup.orchestra.framework.oe.OrchestraExplorerActivator;
import com.thalesgroup.orchestra.framework.oe.ui.OrchestraExplorerState;
import com.thalesgroup.orchestra.framework.oe.ui.constants.IImageConstants;

/**
 * This class builds the folded tree for the view "Folding" of the Orchestra Explorer
 * @author S0018747
 */
public class FoldingAction extends ArtefactAction {

  protected static final String ACTION_ID = "com.thalesgroup.orchestra.framework.oe.ui.actions.FoldingAction"; //$NON-NLS-1$

  protected OrchestraExplorerState _orchestraExplorerState;

  public FoldingAction(OrchestraExplorerState orchestraExplorerState_p) {
    super(Messages.FoldingAction_FoldingLabel);
    setToolTipText(Messages.FoldingAction_FoldingTooltip);
    setImageDescriptor(OrchestraExplorerActivator.getDefault().getImageDescriptor(IImageConstants.DESC_FOLD));
    setId(ACTION_ID);
    _orchestraExplorerState = orchestraExplorerState_p;
    setChecked(_orchestraExplorerState.isFolded());
  }

  /**
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    // Invert the current state
    _orchestraExplorerState.setFolded(!_orchestraExplorerState.isFolded());
    setChecked(_orchestraExplorerState.isFolded());
  }
}