/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.connector.sdk.operation;

import org.eclipse.osgi.util.NLS;

/**
 * @author T0052089
 *
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.connector.sdk.operation.messages"; //$NON-NLS-1$
  public static String NewConnectorCreationOperation_Warning_FormatJavaSources;
  public static String NewConnectorCreationOperation_ExceptionMessage_CantFormat;
  public static String NewConnectorCreationOperation_SubTask_AddingConfDeploymentFiles;
  public static String NewConnectorCreationOperation_SubTask_AddingSources;
  public static String NewConnectorCreationOperation_SubTask_CreatingPlugin;
  public static String NewConnectorCreationOperation_SubTask_RefreshingProject;
  public static String NewConnectorCreationOperation_TaskName_CreatingProject;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
  }
}
