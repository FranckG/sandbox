/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.tests.datamanagement.swtbotcondition;

import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;

/**
 * @author S0032874
 */
public class TreeItemTextCondition extends DefaultCondition {

  private SWTBotTreeItem _item;

  private String _textValue;

  public TreeItemTextCondition(SWTBotTreeItem item_p, String textValue_p) {
    _textValue = textValue_p;
    _item = item_p;
  }

  /**
   * @see org.eclipse.swtbot.swt.finder.waits.ICondition#test()
   */
  @Override
  public boolean test() throws Exception {
    if ((null == _textValue) || (null == _item)) {
      return false;
    }
    return _textValue.equals(_item.getText());
  }

  /**
   * @see org.eclipse.swtbot.swt.finder.waits.ICondition#getFailureMessage()
   */
  @Override
  public String getFailureMessage() {
    return "The text of the SWTBotTreeItem was never set to: " + _textValue; //$NON-NLS-1$
  }

}
