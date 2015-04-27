/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.connector.com;

import org.eclipse.osgi.util.NLS;

/**
 * @author t0076261
 *
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.connector.com.messages"; //$NON-NLS-1$
  public static String ComConnector_ErrorMessage_Configuration_NoProgId;
  public static String ComConnector_ErrorMessage_Deserialization_UnableToDeserialize;
  public static String ComConnector_ErrorMessage_Serialization_Failed;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
    // Static initialization.
  }
}
