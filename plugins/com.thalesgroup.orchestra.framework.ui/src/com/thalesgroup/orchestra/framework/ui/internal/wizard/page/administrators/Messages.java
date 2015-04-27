/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.wizard.page.administrators;

import org.eclipse.osgi.util.NLS;

/**
 * @author s0011584
 *
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.ui.internal.wizard.page.administrators.messages"; //$NON-NLS-1$
  public static String AddAdministratorAction_AlreadyExists_Message;
  public static String AddAdministratorAction_Wizard_WindowTitle;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
    // Nothing to do.
  }
}
