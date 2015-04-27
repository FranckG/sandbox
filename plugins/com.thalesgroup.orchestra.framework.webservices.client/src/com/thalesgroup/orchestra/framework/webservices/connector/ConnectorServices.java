package com.thalesgroup.orchestra.framework.webservices.connector;

import java.rmi.RemoteException;
import java.text.MessageFormat;

import com.thalesgroup.orchestra.framework.lib.base.conf.ServerConfParam;

import framework.orchestra.thalesgroup.com.VariableManagerAPI.Messages;

import javax.xml.rpc.ServiceException;

public class ConnectorServices {

  private static ConnectorServices connectorServices = null;

  private static final String VARIABLE_MANAGER_ADRESS = "http://localhost:{0}/services/connector"; //$NON-NLS-1$

  private ConnectorWSAdapter ca;

  private ConnectorServices() throws ServiceException {
    ConnectorWSAdapterServiceLocator locator = new ConnectorWSAdapterServiceLocator();
    Integer port;
    try {
      port = new Integer(ServerConfParam.getInstance().getPort());
      if (null != port) {
        locator.setconnectorEndpointAddress(MessageFormat.format(VARIABLE_MANAGER_ADRESS, port.toString()));
        ca = locator.getconnector();
      }
    } catch (Exception exception_p) {
      throw new ServiceException(Messages.getString("VariableManagerAdapter.ERROR_NO_PORT"), exception_p); //$NON-NLS-1$
    }
  }

  public ConnectorWS[] getConnectorsInformation() throws RemoteException {
    return ca.getConnectorsInformation();
  }

  public static ConnectorServices getInstance() throws ServiceException {
    if (connectorServices == null) {
      connectorServices = new ConnectorServices();
    }
    return connectorServices;
  }

}
