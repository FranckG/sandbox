/**
 * ConnectorWSAdapterServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.thalesgroup.orchestra.framework.webservices.connector;

public class ConnectorWSAdapterServiceLocator extends org.apache.axis.client.Service implements
    com.thalesgroup.orchestra.framework.webservices.connector.ConnectorWSAdapterService {

  // Use to get a proxy class for connector
  private java.lang.String connector_address = "http://localhost:8082/webServicesTemporaryContent/services/connector";

  // The WSDD service name defaults to the port name.
  private java.lang.String connectorWSDDServiceName = "connector";

  private java.util.HashSet ports = null;

  public ConnectorWSAdapterServiceLocator() {
  }

  public ConnectorWSAdapterServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
    super(wsdlLoc, sName);
  }

  public ConnectorWSAdapterServiceLocator(org.apache.axis.EngineConfiguration config) {
    super(config);
  }

  public com.thalesgroup.orchestra.framework.webservices.connector.ConnectorWSAdapter getconnector() throws javax.xml.rpc.ServiceException {
    java.net.URL endpoint;
    try {
      endpoint = new java.net.URL(connector_address);
    } catch (java.net.MalformedURLException e) {
      throw new javax.xml.rpc.ServiceException(e);
    }
    return getconnector(endpoint);
  }

  public com.thalesgroup.orchestra.framework.webservices.connector.ConnectorWSAdapter getconnector(java.net.URL portAddress)
      throws javax.xml.rpc.ServiceException {
    try {
      com.thalesgroup.orchestra.framework.webservices.connector.ConnectorSoapBindingStub _stub =
          new com.thalesgroup.orchestra.framework.webservices.connector.ConnectorSoapBindingStub(portAddress, this);
      _stub.setPortName(getconnectorWSDDServiceName());
      return _stub;
    } catch (org.apache.axis.AxisFault e) {
      return null;
    }
  }

  public java.lang.String getconnectorAddress() {
    return connector_address;
  }

  public java.lang.String getconnectorWSDDServiceName() {
    return connectorWSDDServiceName;
  }

  /**
   * For the given interface, get the stub implementation. If this service has no port for the given interface, then ServiceException is thrown.
   */
  @Override
  public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
    try {
      if (com.thalesgroup.orchestra.framework.webservices.connector.ConnectorWSAdapter.class.isAssignableFrom(serviceEndpointInterface)) {
        com.thalesgroup.orchestra.framework.webservices.connector.ConnectorSoapBindingStub _stub =
            new com.thalesgroup.orchestra.framework.webservices.connector.ConnectorSoapBindingStub(new java.net.URL(connector_address), this);
        _stub.setPortName(getconnectorWSDDServiceName());
        return _stub;
      }
    } catch (java.lang.Throwable t) {
      throw new javax.xml.rpc.ServiceException(t);
    }
    throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  "
                                             + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
  }

  /**
   * For the given interface, get the stub implementation. If this service has no port for the given interface, then ServiceException is thrown.
   */
  @Override
  public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
    if (portName == null) {
      return getPort(serviceEndpointInterface);
    }
    java.lang.String inputPortName = portName.getLocalPart();
    if ("connector".equals(inputPortName)) {
      return getconnector();
    } else {
      java.rmi.Remote _stub = getPort(serviceEndpointInterface);
      ((org.apache.axis.client.Stub) _stub).setPortName(portName);
      return _stub;
    }
  }

  @Override
  public java.util.Iterator getPorts() {
    if (ports == null) {
      ports = new java.util.HashSet();
      ports.add(new javax.xml.namespace.QName("http://webservices.framework.orchestra.thalesgroup.com/connector", "connector"));
    }
    return ports.iterator();
  }

  @Override
  public javax.xml.namespace.QName getServiceName() {
    return new javax.xml.namespace.QName("http://webservices.framework.orchestra.thalesgroup.com/connector", "ConnectorWSAdapterService");
  }

  public void setconnectorEndpointAddress(java.lang.String address) {
    connector_address = address;
  }

  public void setconnectorWSDDServiceName(java.lang.String name) {
    connectorWSDDServiceName = name;
  }

  /**
   * Set the endpoint address for the specified port name.
   */
  public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {

    if ("connector".equals(portName)) {
      setconnectorEndpointAddress(address);
    } else { // Unknown Port Name
      throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
    }
  }

  /**
   * Set the endpoint address for the specified port name.
   */
  public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
    setEndpointAddress(portName.getLocalPart(), address);
  }

}
