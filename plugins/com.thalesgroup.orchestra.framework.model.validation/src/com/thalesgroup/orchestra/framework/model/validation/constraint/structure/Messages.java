/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.validation.constraint.structure;

import org.eclipse.osgi.util.NLS;

/**
 * @author t0076261
 *
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.model.validation.constraint.structure.messages"; //$NON-NLS-1$
  public static String VersionsAreValidConstraint_InvalidCompatibilityPattern;
  public static String VersionsAreValidConstraint_Malformed_EverythingButVersionPattern;
  public static String VersionsAreValidConstraint_Malformed_RangePattern;
  public static String VersionsAreValidConstraint_Malformed_SelectedVersion;
  public static String VersionsAreValidConstraint_Malformed_SingleVersionPattern;
  public static String VersionsAreValidConstraint_Malformed_VersionPattern;
  public static String VersionsAreValidConstraint_MissingVersion;
  public static String VersionsAreValidConstraint_NotCompatibleVersion;
  public static String VersionsAreValidConstraint_NoVersionProvided;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
  }
}
