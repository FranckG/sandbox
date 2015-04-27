/**
 * Copyright (c) THALES, 2010. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.connector.migration;

import org.eclipse.osgi.util.NLS;

/**
 * @author t0076261
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.connector.migration.messages"; //$NON-NLS-1$
  public static String MigrationConnector_ContextMigration_ImportFailed_NoContextAtSpecifiedLocation;
  public static String MigrationConnector_ContextMigration_ImportFailed_UnableToImportNull;
  public static String MigrationConnector_ContextMigration_ValidateContextName_FutureFolderIsAlreadyExistingInTheWorkspace;
  public static String MigrationConnector_ContextMigration_ValidateContextName_NameIsAlreadyAssignedInTheWorkspace;
  public static String MigrationConnector_ContextMigration_ValidateContextName_NullIsNotAValidName;
  public static String MigrationConnector_ImportFailed_UnableToImportContextFromSpecifiedLocation;
  public static String MigrationConnector_InvalidV4UriFormat_CantBeMigrated;
  public static String MigrationConnector_InvalidV4UrlFormat_CantBeMigrated;
  public static String MigrationConnector_MigrationConnector_ImportFailed_CanNotUnloadExistingContext;
  public static String MigrationConnector_MigrationFailed_CanNotCreateNewUri;
  public static String MigrationConnector_NoConnectorToMigrateUri;
  public static String MigrationConnector_ParentsCandidate_Error_CanNotBeInUserMode;
  public static String MigrationConnector_ParentsCandidate_ResultMessage;
  public static String MigrationConnector_UriMigration_StructureFailed_ErrorWhileReadingFile;
  public static String MigrationConnector_UriMigration_StructureFailed_ErrorWhileReadingFileResult;
  public static String MigrationConnector_UriMigration_StructureFailed_NoFileFound;
  public static String MigrationConnector_UriMigration_StructureFailed_NoFolderFound;
  public static String MigrationConnector_UriMigration_StructureFailed_NoValidFileFound;
  public static String MigrationConnector_UriMigrationIsMissingUriParameter;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
    // Static initialization.
  }
}