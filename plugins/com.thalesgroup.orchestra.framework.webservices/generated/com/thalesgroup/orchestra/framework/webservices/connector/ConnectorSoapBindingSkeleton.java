/**
 * ConnectorSoapBindingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.thalesgroup.orchestra.framework.webservices.connector;

public class ConnectorSoapBindingSkeleton implements com.thalesgroup.orchestra.framework.webservices.connector.ConnectorWSAdapter,
    org.apache.axis.wsdl.Skeleton {
  private static java.util.Map _myOperations = new java.util.Hashtable();
  private static java.util.Collection _myOperationsList = new java.util.ArrayList();
  static {
    org.apache.axis.description.OperationDesc _oper;
    org.apache.axis.description.FaultDesc _fault;
    org.apache.axis.description.ParameterDesc[] _params;
    _params = new org.apache.axis.description.ParameterDesc[] {};
    _oper = new org.apache.axis.description.OperationDesc("getConnectorsInformation", _params, new javax.xml.namespace.QName("", "connectors"));
    _oper.setReturnType(new javax.xml.namespace.QName("http://webservices.framework.orchestra.thalesgroup.com/connector", "ArrayOf_impl_ConnectorWS"));
    _oper.setElementQName(new javax.xml.namespace.QName("http://webservices.framework.orchestra.thalesgroup.com/connector", "getConnectorsInformation"));
    _oper.setSoapAction("");
    _myOperationsList.add(_oper);
    if (_myOperations.get("getConnectorsInformation") == null) {
      _myOperations.put("getConnectorsInformation", new java.util.ArrayList());
    }
    ((java.util.List) _myOperations.get("getConnectorsInformation")).add(_oper);
  }

  private com.thalesgroup.orchestra.framework.webservices.connector.ConnectorWSAdapter impl;

  public ConnectorSoapBindingSkeleton() {
    this.impl = new com.thalesgroup.orchestra.framework.webservices.connector.ConnectorSoapBindingImpl();
  }

  public ConnectorSoapBindingSkeleton(com.thalesgroup.orchestra.framework.webservices.connector.ConnectorWSAdapter impl) {
    this.impl = impl;
  }

  public com.thalesgroup.orchestra.framework.webservices.connector.ConnectorWS[] getConnectorsInformation() throws java.rmi.RemoteException {
    com.thalesgroup.orchestra.framework.webservices.connector.ConnectorWS[] ret = impl.getConnectorsInformation();
    return ret;
  }

  /**
   * Returns List of OperationDesc objects with this name
   */
  public static java.util.List getOperationDescByName(java.lang.String methodName) {
    return (java.util.List) _myOperations.get(methodName);
  }

  /**
   * Returns Collection of OperationDescs
   */
  public static java.util.Collection getOperationDescs() {
    return _myOperationsList;
  }

}
