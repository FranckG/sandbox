/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ecf.services;

import org.eclipse.osgi.util.NLS;

/**
 * @author t0076261
 *
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.ecf.services.messages"; //$NON-NLS-1$
  public static String EcfServicesActivator_StatusMessage_Launchers_CollectWrapUp;
  public static String EcfServicesActivator_StatusMessage_Launchers_InvalidLauncher_Error;
  public static String EcfServicesActivator_StatusMessage_Launchers_LauncherSuccessfullyLoaded;
  public static String EcfServicesActivator_StatusMessage_Launchers_NoLauncherDefined;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
    // Static initialization.
  }
}
