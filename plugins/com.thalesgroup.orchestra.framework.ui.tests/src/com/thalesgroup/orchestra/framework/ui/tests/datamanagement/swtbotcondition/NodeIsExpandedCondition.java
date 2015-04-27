/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.tests.datamanagement.swtbotcondition;

import java.text.MessageFormat;

import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;

/**
 * @author S0032874
 */
public class NodeIsExpandedCondition extends DefaultCondition {

  private SWTBotTreeItem _node;

  public NodeIsExpandedCondition(SWTBotTreeItem node_p) {
    _node = node_p;
  }

  /**
   * @see org.eclipse.swtbot.swt.finder.waits.ICondition#getFailureMessage()
   */
  @Override
  public String getFailureMessage() {
    return MessageFormat.format("The node: {0} could not be expanded", _node.getText()); //$NON-NLS-1$
  }

  /**
   * @see org.eclipse.swtbot.swt.finder.waits.ICondition#test()
   */
  @Override
  public boolean test() throws Exception {
    if (null == _node) {
      return false;
    }
    return _node.isExpanded() == true;
  }
}