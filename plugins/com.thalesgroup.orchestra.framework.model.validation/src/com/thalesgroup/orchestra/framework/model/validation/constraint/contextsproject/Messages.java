/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.validation.constraint.contextsproject;

import org.eclipse.osgi.util.NLS;

/**
 * @author T0052089
 *
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.model.validation.constraint.contextsproject.messages"; //$NON-NLS-1$
  public static String ParentProjectPathConstraint_FailureMessage_EmptyPath;
  public static String ParentProjectPathConstraint_FailureMessage_FoundContextNotParent;
  public static String ParentProjectPathConstraint_FailureMessage_NoContextFound;
  public static String ParentProjectPathConstraint_FailureMessage_NonExistingDirectory;
  public static String ParentProjectPathConstraint_FailureMessage_OnlyEnvVar;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
  }
}
