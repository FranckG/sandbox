/**
 * Copyright (c) THALES, 2010. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.internal.connector;

import org.eclipse.osgi.util.NLS;

/**
 * @author s0024585
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.internal.connector.messages"; //$NON-NLS-1$
  public static String ConnectorRegistry_AssociationDetection_WrapUp;
  public static String ConnectorRegistry_ConnectionReport_Registration;
  public static String ConnectorRegistry_ConnectionReport_RemoteConnector;
  public static String ConnectorRegistry_ConnectionReport_RemoteContextChangeListener;
  public static String ConnectorRegistry_ConnectionReport_Unregistration;
  public static String ConnectorRegistry_ErroneousAssociations_Error;
  public static String ConnectorRegistry_FakeClient_UnableToRegister;
  public static String ConnectorRegistry_FakeClients_Initialization_Failed_For_RemoteContextChangeListener;
  public static String ConnectorRegistry_FakeClients_Initialization_Wrapup;
  public static String ConnectorRegistry_RemoteContextChangeListenerHandler_Error_CouldNotReachRemoteListeners;
  public static String ConnectorRegistry_RemoteContextChangeListenerHandler_NoDetailsForClient;
  public static String ConnectorRegistry_RemoteContextChangeListenerHandler_Warning_NoDetailsForClient;
  public static String ConnectorRegistry_RemoteContextChangeListenersStructure_Initialization_Error;
  public static String ConnectorRegistry_RemoteContextChangeListenersStructure_Initialization_Ok;
  public static String ConnectorRegistry_RemoteContextChangeListenersStructure_Initialization_Wrapup;
  public static String ConnectorRegistry_StatusMessage_CanNotInstantiateConnectorImplementation_Error;
  public static String ConnectorRegistry_StatusMessage_Connector_Loaded;
  public static String ConnectorRegistry_StatusMessage_ConnectorExtensionLoaded;
  public static String ConnectorRegistry_StatusMessage_ConnectorLogicalOnly;
  public static String ConnectorRegistry_StatusMessage_ConnectorModel_Error;
  public static String ConnectorRegistry_StatusMessage_ConnectorsAndAssociationsCheck_AssociationFound;
  public static String ConnectorRegistry_StatusMessage_ConnectorsAndAssociationsCheck_NoAssociation_Warning;
  public static String ConnectorRegistry_StatusMessage_ConnectorsAndAssociationsCheck_ConflictingAssociationFound;
  public static String ConnectorRegistry_StatusMessage_ConnectorsAndAssociationsCheck_WrapUp;
  public static String ConnectorRegistry_StatusMessage_CouldNotInvokeSpecificCommand;
  public static String ConnectorRegistry_StatusMessage_DescriptorsCreation_DescriptorAlreadyExists_Warning;
  public static String ConnectorRegistry_StatusMessage_DescriptorsCreation_WrapUp;
  public static String ConnectorRegistry_StatusMessage_DescriptorsInitialization;
  public static String ConnectorRegistry_StatusMessage_Ecf_Start_Error;
  public static String ConnectorRegistry_StatusMessage_Ecf_Started;
  public static String ConnectorRegistry_StatusMessage_NoConnectorDeclarationForType;
  public static String ConnectorRegistry_StatusMessage_NoConnectorFound;
  public static String ConnectorRegistry_StatusMessage_NoIconsPath;
  public static String ConnectorRegistry_StatusMessage_NoImplementationForConnectorId_Error;
  public static String ConnectorRegistry_StatusMessage_Root;
  public static String ConnectorRegistry_StatusMessage_UnableToLoadConfigurationFile_Error;
  public static String RequestHandler_CommandNotSupported;
  public static String RequestHandler_ConnectorServerExecution_WrapUp_Message;
  public static String RequestHandler_DisconnectedModeCancelled;
  public static String RequestHandler_DisconnectedModeResponse;
  public static String RequestHandler_CouldNotFindArtefact;
  public static String RequestHandler_NoConnectorDefined;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
    // Static initialization.
  }
}
