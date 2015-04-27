/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.connector.sdk.wizards;

import org.eclipse.osgi.util.NLS;

/**
 * @author T0052089
 *
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.connector.sdk.wizards.messages"; //$NON-NLS-1$
  public static String NewConnectorWizard_ConnectorCreationFailed_Message;
  public static String NewConnectorWizard_Title;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
  }
}
