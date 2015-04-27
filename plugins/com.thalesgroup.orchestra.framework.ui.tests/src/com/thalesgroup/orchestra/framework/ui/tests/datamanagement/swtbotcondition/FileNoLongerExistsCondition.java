/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.tests.datamanagement.swtbotcondition;

import java.io.File;
import java.text.MessageFormat;

import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;

/**
 * @author S0032874
 */
public class FileNoLongerExistsCondition extends DefaultCondition {

  private String _filePath;

  public FileNoLongerExistsCondition(String filePath_p) {
    _filePath = filePath_p;
  }

  /**
   * @see org.eclipse.swtbot.swt.finder.waits.ICondition#test()
   */
  @Override
  public boolean test() throws Exception {
    if (null == _filePath) {
      return false;
    }
    File file = null;
    try {
      file = new File(_filePath);
    } catch (Exception exception_p) {
      file = null;
    }
    if (null == file) {
      return false;
    }
    // The file must no longer exists to succeed the condition
    return !file.exists();
  }

  /**
   * @see org.eclipse.swtbot.swt.finder.waits.ICondition#getFailureMessage()
   */
  @Override
  public String getFailureMessage() {
    return MessageFormat.format("The file at: {0} could not be deleted", _filePath); //$NON-NLS-1$
  }

}
