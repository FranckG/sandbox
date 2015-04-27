/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.connector;

import org.eclipse.osgi.util.NLS;

/**
 * @author t0076261
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.connector.messages"; //$NON-NLS-1$
  public static String AbstractConnector_ConnectorId_Implementation;
  public static String AbstractConnector_ConnectorId_Type;
  public static String AbstractConnector_ErrorMessage_CommandNotSupported;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
    // Static initialization
  }
}
