/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.oe.connectors;

import com.thalesgroup.orchestra.framework.webservices.connector.ConnectorWS;

/**
 * @author S0024585
 */
public class ConnectorInformation {
  protected ConnectorWS _connectorData;

  public ConnectorInformation(ConnectorWS connector_p) {
    _connectorData = connector_p;
  }

  /**
   * @return the logical part of the association for this connector
   */
  public String getAssociationLogicalPart() {
    if (hasAssociationData()) {
      return _connectorData.getAssociation().getLogical();
    }
    return null;
  }

  /**
   * @return the physical part of the association for this connector
   */
  public String getAssociationPhysicalPart() {
    if (hasAssociationData()) {
      return _connectorData.getAssociation().getPhysical();
    }
    return null;
  }

  /**
   * @return the icon path of the connector.
   */
  public String getIconpath() {
    return _connectorData.getIconpath();
  }

  /**
   * @return the type of the connector
   */
  public String getType() {
    return _connectorData.getType();
  }

  /**
   * @return <code>true</code> if an association exist for this connector
   */
  public boolean hasAssociationData() {
    return (_connectorData.getAssociation() != null);
  }

  /**
   * @return <code>true</code> if creation is possible from this connector
   */
  public boolean isCreateSupported() {
    return _connectorData.isCreatepossible();
  }
}