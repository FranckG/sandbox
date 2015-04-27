/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.connector.sdk.helpers;

import org.eclipse.osgi.util.NLS;

/**
 * @author T0052089
 *
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.connector.sdk.helpers.messages"; //$NON-NLS-1$
  public static String WindowsRegistryFileHelper_ErrorMessage_RegistryImport_Error;
  public static String WindowsRegistryFileHelper_Message_RegistryImport_Success;
  public static String WindowsRegistryFileHelper_ErrorMessage_RegistryImport_Failure;
  public static String WindowsRegistryFileHelper_ErrorMessage_RegistryImport_UnexpectedResult;
  public static String WindowsRegistryFileHelper_ErrorMessage_RegistryKeyDeletion_Error;
  public static String WindowsRegistryFileHelper_ErrorMessage_RegistryKeyDeletion_Failure;
  public static String WindowsRegistryFileHelper_ErrorMessage_RegistryKeyDeletion_UnexpectedResult;
  public static String WindowsRegistryFileHelper_Message_RegistryKeyDeletion_Success;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
  }
}
