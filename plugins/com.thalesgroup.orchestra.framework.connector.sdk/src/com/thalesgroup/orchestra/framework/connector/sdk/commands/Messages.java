/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.connector.sdk.commands;

import org.eclipse.osgi.util.NLS;

/**
 * @author T0052089
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.connector.sdk.commands.messages"; //$NON-NLS-1$
  public static String DeployConnectorCommand_ErrorDialogMessage_ImportFailed;
  public static String DeployConnectorCommand_ErrorDialogTitle_ImportFailed;
  public static String DeployConnectorCommand_ErrorStatusMessage_FileNotFound;
  public static String DeployConnectorCommand_InfoDialogMessage_ImportSuccessful;
  public static String DeployConnectorCommand_InfoDialogTitle_ImportSuccessful;
  public static String DeployConnectorCommand_MultiStatusMessage_ImportFailed;
  public static String UnDeployConnectorCommand_ErrorDialogMessage_DeleteFailed;
  public static String UnDeployConnectorCommand_ErrorDialogTitle_DeleteFailed;
  public static String UnDeployConnectorCommand_ErrorStatusMessage_CantDeleteRegistryKey;
  public static String UnDeployConnectorCommand_ErrorStatusMessage_FileNotFound;
  public static String UnDeployConnectorCommand_InfoDialogTitle_DeleteSuccessful;
  public static String UnDeployConnectorCommand_MultiStatusMessage_SelectDetails;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
  }
}
