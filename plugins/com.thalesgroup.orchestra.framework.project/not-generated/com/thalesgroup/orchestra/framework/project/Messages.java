/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.project;

import org.eclipse.osgi.util.NLS;

/**
 * @author s0011584
 *
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.project.messages"; //$NON-NLS-1$
  public static String ProjectHandler_Clean_Error;
  public static String ProjectHandler_Export_Warning_ExistingFolder;
  public static String ProjectHandler_Export_Warning_ExistingProject;
  public static String ProjectHandler_FindUsers_Error;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
    // Auto generated code with warning...
  }
}
