/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ae.creation.ui;

import org.eclipse.osgi.util.NLS;

/**
 * @author s0018747
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.ae.creation.ui.messages"; //$NON-NLS-1$

  public static String ArtifactCreationWizard_Title;

  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

}
