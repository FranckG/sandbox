/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environments.actions;

import org.eclipse.osgi.util.NLS;

/**
 * @author t0076261
 *
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.environments.actions.messages"; //$NON-NLS-1$
  public static String BaselineActions_ExecutionErrorMessage;
  public static String MakeBaselineAction_Text;
  public static String UseBaselineAction_Text;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
  }
}
