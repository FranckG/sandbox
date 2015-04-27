/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.connector.ecf;

import org.eclipse.osgi.util.NLS;

/**
 * @author t0076261
 *
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.connector.ecf.messages"; //$NON-NLS-1$
  public static String EcfConnector_Error_ErrorOccurredButIsNotSpecified;
  public static String EcfConnector_Error_NoLauncherAvailableForType;
  public static String EcfConnector_Error_WrapUp_ImpactedArtefacts;
  public static String EcfConnector_LauncherError_InvalidApplicationPath;
  public static String EcfConnector_LauncherError_InvalidApplicationPath_Category;
  public static String EcfConnector_LauncherError_InvalidApplicationPath_Variable;
  public static String EcfConnector_StatusMessage_ApplicationFailedToLaunch_Error;
  public static String EcfConnector_StatusMessage_ApplicationFailedToLaunch_TimeOut_Error;
  public static String EcfConnector_StatusMessage_ApplicationLaunchedButConnectionIsLost;
  public static String EcfConnector_StatusMessage_ApplicationPathInvalid_Error;
  public static String EcfConnector_StatusMessage_ApplicationSuccessfullyLaunched;
  public static String EcfConnector_StatusMessage_UnableToCallRemoteService;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
    // Static initialization.
  }
}
