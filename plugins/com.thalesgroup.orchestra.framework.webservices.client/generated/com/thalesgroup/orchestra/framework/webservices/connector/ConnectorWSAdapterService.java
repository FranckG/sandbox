/**
 * ConnectorWSAdapterService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.thalesgroup.orchestra.framework.webservices.connector;

public interface ConnectorWSAdapterService extends javax.xml.rpc.Service {
    public java.lang.String getconnectorAddress();

    public com.thalesgroup.orchestra.framework.webservices.connector.ConnectorWSAdapter getconnector() throws javax.xml.rpc.ServiceException;

    public com.thalesgroup.orchestra.framework.webservices.connector.ConnectorWSAdapter getconnector(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
