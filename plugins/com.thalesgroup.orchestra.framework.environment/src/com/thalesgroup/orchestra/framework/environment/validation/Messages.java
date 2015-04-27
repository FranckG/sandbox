/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment.validation;

import org.eclipse.osgi.util.NLS;

/**
 * @author t0076261
 *
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.environment.validation.messages"; //$NON-NLS-1$
  public static String EnvironmentRuleRegistry_Error_ClassCannotBeInstantiated;
  public static String EnvironmentRuleRegistry_Error_ClassNotDefined;
  public static String EnvironmentRuleRegistry_Initialization_WrapUp;
  public static String EnvironmentRuleRegistry_Ok_Rule_Loaded;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
  }
}
