/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.connector.sdk.wizards.pages;

import org.eclipse.osgi.util.NLS;

/**
 * @author T0052089
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.connector.sdk.wizards.pages.messages"; //$NON-NLS-1$
  public static String ConnectorAssociationsPage_Button_Add;
  public static String ConnectorAssociationsPage_Button_Remove;
  public static String ConnectorAssociationsPage_ErrorMessage_AllFieldsMandatory;
  public static String ConnectorAssociationsPage_ErrorMessage_PhysicalNameExtensionMissing;
  public static String ConnectorAssociationsPage_ErrorMessage_PhysicalNameExtensionNotUnique;
  public static String ConnectorAssociationsPage_ErrorMessage_TypeNameIncorrectFormat;
  public static String ConnectorAssociationsPage_ErrorMessage_TypeNameNotUnique;
  public static String ConnectorAssociationsPage_PageTitle;
  public static String ConnectorAssociationsPage_TableColumnName_LogicalName;
  public static String ConnectorAssociationsPage_TableColumnName_PhysicalName;
  public static String ConnectorAssociationsPage_TableColumnName_TypeName;
  public static String ConnectorStructurePage_Button_Browse;
  public static String ConnectorStructurePage_Button_OutOfProcess;
  public static String ConnectorStructurePage_Button_Product;
  public static String ConnectorStructurePage_Button_UseDefaultLocation;
  public static String ConnectorStructurePage_DirectoryDialog_Title;
  public static String ConnectorStructurePage_ErrorMessage_ConnectorIdEmpty;
  public static String ConnectorStructurePage_ErrorMessage_ConnectorIdExistingProject;
  public static String ConnectorStructurePage_ErrorMessage_ConnectorIdNotValidPackageName;
  public static String ConnectorStructurePage_ErrorMessage_ConnectorIdNotValidProjectName;
  public static String ConnectorStructurePage_ErrorMessage_ConnectorNameEmpty;
  public static String ConnectorStructurePage_ErrorMessage_ConnectorNameNotValidCharacters;
  public static String ConnectorStructurePage_ErrorMessage_ConnectorNameNotValidTypeName;
  public static String ConnectorStructurePage_ErrorMessage_ProjectLocationEmpty;
  public static String ConnectorStructurePage_ErrorMessage_ProjectLocationInvalid;
  public static String ConnectorStructurePage_Group_RuntimeOptions;
  public static String ConnectorStructurePage_Label_ConnectorId;
  public static String ConnectorStructurePage_Label_ConnectorName;
  public static String ConnectorStructurePage_Label_Location;
  public static String ConnectorStructurePage_OkMessage_CreateANewConnectorProject;
  public static String ConnectorStructurePage_PageTitle;
  public static String ConnectorStructurePage_WarningMessage_ConnectorIdDiscouragedPackageName;
  public static String ConnectorStructurePage_WarningMessage_ConnectorNameDiscouragedTypeName;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
  }
}
