/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.remote.services;

import org.eclipse.osgi.util.NLS;

/**
 * @author t0076261
 *
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.remote.services.messages"; //$NON-NLS-1$
  public static String ConnectionHandler_Error_NoImplementationFound;
  public static String ConnectionHandler_Error_NoImplementationProvided;
  public static String ConnectionHandler_Error_ServerNotResolved;
  public static String ConnectionHandler_Error_UnableToCreateContainer;
  public static String ConnectionHandler_Error_UnableToGetServerConfiguration;
  public static String ConnectionHandler_Ok_ImplementationSuccessfullyRegistered;
  public static String ConnectionHandler_Ok_ImplementationSuccessfullyUnregistered;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
  }
}
