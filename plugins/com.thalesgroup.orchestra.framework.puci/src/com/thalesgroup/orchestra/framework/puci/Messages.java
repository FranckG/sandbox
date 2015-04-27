/**
 * Copyright (c) THALES, 2010. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.puci;

import org.eclipse.osgi.util.NLS;

/**
 * @author s0024585
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.puci.messages"; //$NON-NLS-1$
  public static String PUCI_EncodingNotSupported;
  public static String PUCI_InternalError;
  public static String PUCI_NullURI;
  public static String PUCI_PathNotExist;
  public static String PUCI_PathNullOrEmpty;
  public static String PUCI_ServerNotStarted;
  public static String PUCI_ServiceInterrupted;
  public static String PUCI_URIListNullOrEmpty;
  public static String PUCI_URIListWithNullValue;
  public static String RequestClient_UnableToConnectToFramework;
  public static String RequestClient_UnableToGetHttpClient;
  public static String RequestClient_UnexpectedError;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
    // Static initialization
  }
}
