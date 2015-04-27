/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.variablemanager.server.model;

import org.eclipse.osgi.util.NLS;

/**
 * @author s0011584
 *
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.variablemanager.server.model.messages"; //$NON-NLS-1$
  public static String VariableManager_NewCategoryNameDefaultValue;
  public static String VariableManager_NewVariableNameDefaultValue;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
    // Do nothing.
  }
}
