/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.oe.connectors;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashMap;

import org.eclipse.core.runtime.IStatus;

import com.thalesgroup.orchestra.framework.oe.OrchestraExplorerActivator;
import com.thalesgroup.orchestra.framework.webservices.connector.ConnectorServices;
import com.thalesgroup.orchestra.framework.webservices.connector.ConnectorWS;

import javax.xml.rpc.ServiceException;

/**
 * @author S0024585
 */
public class ConnectorsInformationManager {
  // Paths to the images folder for a connector
  private final HashMap<String, ConnectorInformation> _connectorsInformation = new HashMap<String, ConnectorInformation>();

  /**
   * @return all managed {@link ConnectorInformation}s
   */
  public Collection<ConnectorInformation> getAll() {
    return _connectorsInformation.values();
  }

  /**
   * @param type_p
   * @return the {@link ConnectorInformation} for the type <code>type_p</code>
   */
  public ConnectorInformation getConnectorInformation(String type_p) {
    return _connectorsInformation.get(type_p);
  }

  /**
   * @return <code>true</code> if at least one connector allows for artefact creation.
   */
  public boolean isCreationSupported() {
    for (ConnectorInformation connectorInformation : _connectorsInformation.values()) {
      if (connectorInformation.isCreateSupported()) {
        return true;
      }
    }
    return false;
  }

  /**
   * Load connectors information
   */
  public void update() {
    _connectorsInformation.clear();
    try {
      ConnectorWS allConnectorWS[] = ConnectorServices.getInstance().getConnectorsInformation();
      for (ConnectorWS connectorWS : allConnectorWS) {
        if (null != connectorWS) {
          ConnectorInformation connectorInformation = new ConnectorInformation(connectorWS);
          _connectorsInformation.put(connectorInformation.getType(), connectorInformation);
        }
      }
    } catch (RemoteException e) {
      OrchestraExplorerActivator.getDefault().logMessage(e.getMessage(), IStatus.ERROR, e);
    } catch (ServiceException e) {
      OrchestraExplorerActivator.getDefault().logMessage(e.getMessage(), IStatus.ERROR, e);
    }
  }
}
