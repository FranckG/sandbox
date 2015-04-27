/**
 * Copyright (c) THALES, 2010. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.connector.baseconnector;

import org.eclipse.osgi.util.NLS;

/**
 * @author s0024585
 */
public class Messages extends NLS {
  public static String BaseConnector_CanNotGenerateDocumentaryExport;
  public static String BaseConnector_ExecutableLaunchingError;
  public static String BaseConnector_FileNotFound;
  public static String BaseConnector_NoPhysicalPathForURI;
  public static String BaseConnector_VariableNotDefinedOrEmpty;
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.connector.baseconnector.messages"; //$NON-NLS-1$
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
    // Static initialization
  }
}
