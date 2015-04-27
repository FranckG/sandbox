/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment;

import org.eclipse.osgi.util.NLS;

/**
 * @author t0076261
 *
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.environment.messages"; //$NON-NLS-1$
  public static String AbstractEnvironmentHandler_InitializeHandler_Initialize_Successful;
  public static String AbstractEnvironmentHandler_InitializeHandler_Reset_Successful;
  public static String AbstractEnvironmentHandler_UpdateAttributes_Error_AttributesCantBeNull;
  public static String AbstractEnvironmentHandler_UpdateAttributes_Successful;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
  }
}
