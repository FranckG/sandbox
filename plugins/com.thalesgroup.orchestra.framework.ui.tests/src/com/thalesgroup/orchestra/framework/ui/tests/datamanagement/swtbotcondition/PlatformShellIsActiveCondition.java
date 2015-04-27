/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.tests.datamanagement.swtbotcondition;

import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;

/**
 * A condition that checks that current shell is platform one.
 * @author t0076261
 */
public class PlatformShellIsActiveCondition extends DefaultCondition {
  /**
   * @see org.eclipse.swtbot.swt.finder.waits.ICondition#getFailureMessage()
   */
  public String getFailureMessage() {
    return "Application did not restore platform shell as active one. There might be an user dialog pending."; //$NON-NLS-1$
  }

  /**
   * @see org.eclipse.swtbot.swt.finder.waits.ICondition#test()
   */
  public boolean test() throws Exception {
    // Get active shell text.
    String shellText = bot.activeShell().getText();
    return shellText.startsWith("Orchestra Framework") || shellText.startsWith("Orchestra Framework Application Test"); //$NON-NLS-1$ //$NON-NLS-2$
  }
}