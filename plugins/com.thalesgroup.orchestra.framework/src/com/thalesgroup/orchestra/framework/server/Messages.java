/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.server;

import org.eclipse.osgi.util.NLS;

/**
 * @author t0076261
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.server.messages"; //$NON-NLS-1$
  public static String ServerManager_CheckConfigurationFiles;
  public static String ServerManager_ContextFailedToSwitchTo;
  public static String ServerManager_ContextSwitchedTo;
  public static String ServerManager_ContextSwitching;
  public static String ServerManager_HttpServerFailedToStart;
  public static String ServerManager_HttpServerStarted;
  public static String ServerManager_Initialization;
  public static String ServerManager_RequiredFileIsFound;
  public static String ServerManager_RequiredFileIsMissing;
  public static String ServerManager_SharedDirectoryDeletion_Error_HeldByAnotherApplication;
  public static String ServerManager_SharedDirectoryDeletion_Job_Message;
  public static String ServerManager_SharedVariablesHandling;
  public static String ServerManager_StatusMessage_CustomSharedDirDeletionError;
  public static String ServerManager_StatusMessage_CustomSharedDirNotValid;
  public static String ServerManager_StatusMessage_TempDirNotCreated;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
    // Static initialization
  }
}
