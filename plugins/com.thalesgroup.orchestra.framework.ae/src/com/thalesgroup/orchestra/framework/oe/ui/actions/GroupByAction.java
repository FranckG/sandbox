/** 
 * <p>THALES Services - Engineering & Process Management</p>
 * <p>THALES Part Number 16 262 601</p>
 * <p>Copyright (c) 2002-2008 : THALES Services - Engineering & Process Management</p>
 */
package com.thalesgroup.orchestra.framework.oe.ui.actions;

import com.thalesgroup.orchestra.framework.oe.ui.OrchestraExplorerState;
import com.thalesgroup.orchestra.framework.oe.ui.OrchestraExplorerState.GroupType;

/**
 * This class handles the group by tool and the group by location operation
 * @author S0018747
 */
public class GroupByAction extends ArtefactAction {
  private final GroupType _groupType;
  protected OrchestraExplorerState _orchestraExplorerState;

  public GroupByAction(GroupType groupType_p, String label_p, String tooltip_p, OrchestraExplorerState orchestraExplorerState_p) {
    super(label_p);
    setToolTipText(tooltip_p);
    _groupType = groupType_p;
    setId("com.thalesgroup.orchestra.framework.oe.ui.actions.GroupByAction"); //$NON-NLS-1$
    _orchestraExplorerState = orchestraExplorerState_p;
    setChecked(isChecked());
  }

  /**
   * @see org.eclipse.jface.action.Action#isChecked()
   */
  @Override
  public boolean isChecked() {
    return (_orchestraExplorerState.getGroupType() == _groupType);
  }

  /**
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    _orchestraExplorerState.setGroupType(_groupType);
  }
}
