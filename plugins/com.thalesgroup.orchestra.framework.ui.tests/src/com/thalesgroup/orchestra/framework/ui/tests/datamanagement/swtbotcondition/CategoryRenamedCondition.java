/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.tests.datamanagement.swtbotcondition;

import java.text.MessageFormat;

import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;

import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;

/**
 * @author S0032874
 */
public class CategoryRenamedCondition extends DefaultCondition {

  private Context _context;

  private String _pathName;

  public CategoryRenamedCondition(Context context_p, String categoryPathName_p) {
    _context = context_p;
    _pathName = categoryPathName_p;
  }

  /**
   * @see org.eclipse.swtbot.swt.finder.waits.ICondition#getFailureMessage()
   */
  @Override
  public String getFailureMessage() {
    String message = MessageFormat.format("No category with the path: {0} exists in the context: {1}", _pathName, _context.getName()); //$NON-NLS-1$
    return message;
  }

  /**
   * @see org.eclipse.swtbot.swt.finder.waits.ICondition#test()
   */
  @Override
  public boolean test() throws Exception {
    // If the parameters are null, the test is always failed
    if ((null == _pathName) || (null == _context)) {
      return false;
    }
    if (null != DataUtil.getCategory(_pathName, _context)) {
      return true;
    }
    return false;
  }

}
