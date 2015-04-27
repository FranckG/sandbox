/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.oe.variableloader;

import org.eclipse.osgi.util.NLS;

/**
 * @author S0024585
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.oe.variableloader.messages"; //$NON-NLS-1$
  public static String EnvironmentVariablesLoader_ExceptionWhileAccessingFrameworkServices;
  public static String EnvironmentVariablesLoader_NoWriteAccessOnTemporaryDirectory;
  public static String EnvironmentVariablesLoader_OrchestraExplorer;
  public static String EnvironmentVariablesLoader_ProblemWithOrchestraServices;
  public static String EnvironmentVariablesLoader_ProblemWithTemporaryDirectory;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
    // Nothing to do
  }
}
