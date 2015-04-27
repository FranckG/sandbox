package com.thalesgroup.orchestra.framework.webservices.connector;

public class ConnectorWSAdapterProxy implements com.thalesgroup.orchestra.framework.webservices.connector.ConnectorWSAdapter {
  private String _endpoint = null;
  private com.thalesgroup.orchestra.framework.webservices.connector.ConnectorWSAdapter connectorWSAdapter = null;
  
  public ConnectorWSAdapterProxy() {
    _initConnectorWSAdapterProxy();
  }
  
  public ConnectorWSAdapterProxy(String endpoint) {
    _endpoint = endpoint;
    _initConnectorWSAdapterProxy();
  }
  
  private void _initConnectorWSAdapterProxy() {
    try {
      connectorWSAdapter = (new com.thalesgroup.orchestra.framework.webservices.connector.ConnectorWSAdapterServiceLocator()).getconnector();
      if (connectorWSAdapter != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)connectorWSAdapter)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)connectorWSAdapter)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (connectorWSAdapter != null)
      ((javax.xml.rpc.Stub)connectorWSAdapter)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.thalesgroup.orchestra.framework.webservices.connector.ConnectorWSAdapter getConnectorWSAdapter() {
    if (connectorWSAdapter == null)
      _initConnectorWSAdapterProxy();
    return connectorWSAdapter;
  }
  
  public com.thalesgroup.orchestra.framework.webservices.connector.ConnectorWS[] getConnectorsInformation() throws java.rmi.RemoteException{
    if (connectorWSAdapter == null)
      _initConnectorWSAdapterProxy();
    return connectorWSAdapter.getConnectorsInformation();
  }
  
  
}