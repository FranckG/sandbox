/**
 * Copyright (c) THALES, 2010. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.migration;

import org.eclipse.osgi.util.NLS;

/**
 * @author t0076261
 */
public class Messages extends NLS {
  public static String AbstractMigration_FailedToConvertStatus;
  public static String AbstractMigration_FailedToCreateContext_LocationRequired;
  public static String AbstractMigration_FailedToCreateContext_NameRequired;
  public static String AbstractMigration_FailedToCreateParticipation_HostingContextAndUserIdRequired;
  public static String AbstractMigration_FailedToImportToFrameworkWorkspace;
  public static String AbstractMigration_FailedToMigrate;
  public static String AbstractMigration_ProjectLoaded;
  public static String AbstractMigration_ProjectNotLoaded_NoProjectAtSpecifiedLocation;
  public static String AbstractMigration_ProjectNotLoaded_NullPath;
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.model.migration.messages"; //$NON-NLS-1$
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
    // Static initialization.
  }
}