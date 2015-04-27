/**
 * ConnectorSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */
package com.thalesgroup.orchestra.framework.webservices.connector;

import java.util.ArrayList;
import java.util.Collection;

import com.thalesgroup.orchestra.framework.FrameworkActivator;
import com.thalesgroup.orchestra.framework.connector.IConnectorRegistry;
import com.thalesgroup.orchestra.framework.connector.IConnectorRegistry.IConnectorDescriptor;
import com.thalesgroup.orchestra.framework.lib.constants.ICommandConstants;
import com.thalesgroup.orchestra.framework.transcription.Association;
import com.thalesgroup.orchestra.framework.transcription.TranscriptionHelper;

public class ConnectorSoapBindingImpl implements com.thalesgroup.orchestra.framework.webservices.connector.ConnectorWSAdapter {
  /**
   * @see com.thalesgroup.orchestra.framework.webservices.connector.ConnectorWSAdapter#getConnectorsInformation()
   */
  public com.thalesgroup.orchestra.framework.webservices.connector.ConnectorWS[] getConnectorsInformation() throws java.rmi.RemoteException {
    IConnectorRegistry connectorRegistry = FrameworkActivator.getDefault().getConnectorRegistry();
    // Precondition.
    if (null == connectorRegistry) {
      return new ConnectorWS[0];
    }
    Collection<ConnectorWS> result = new ArrayList<ConnectorWS>(0);
    Collection<IConnectorDescriptor> descriptors = connectorRegistry.getDescriptors();
    // Create connectors.
    for (IConnectorDescriptor connectorDescriptor : descriptors) {
      // Deal with associations.
      AssociationWS associationWS = null;
      String type = connectorDescriptor.getType();
      Association association = TranscriptionHelper.getInstance().getLogicalPhysicalAssociation(type);
      if (null != association) {
        String logicalPattern = association.getSourceArtifact().getContent();
        String physicalPattern = association.getTargetArtifact().getContent();
        associationWS = new AssociationWS(logicalPattern, physicalPattern, association.isBalanced(), association.isValid());
      }
      result.add(new ConnectorWS(type, associationWS, connectorDescriptor.getIconsDirectoryPath(), connectorDescriptor
          .isCommandSupported(ICommandConstants.CREATE)));
    }
    return result.toArray(new ConnectorWS[result.size()]);
  }
}