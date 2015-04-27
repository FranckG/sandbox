/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.oe.ui.providers;

import org.eclipse.osgi.util.NLS;

/**
 * @author S0024585
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.oe.ui.providers.messages"; //$NON-NLS-1$
  public static String TreeLabelProvider_Env;
  public static String TreeLabelProvider_Env_Error;
  public static String TreeLabelProvider_Error_Dialog_Cause;
  public static String TreeLabelProvider_Error_Dialog_Message;
  public static String TreeLabelProvider_Error_Dialog_Title;
  public static String TreeLabelProvider_Path;
  public static String TreeLabelProvider_Type;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
    // Nothing to do
  }
}
