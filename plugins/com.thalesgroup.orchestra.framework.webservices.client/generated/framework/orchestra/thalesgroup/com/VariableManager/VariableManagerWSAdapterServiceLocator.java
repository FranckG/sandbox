/**
 * VariableManagerWSAdapterServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package framework.orchestra.thalesgroup.com.VariableManager;

public class VariableManagerWSAdapterServiceLocator extends org.apache.axis.client.Service implements framework.orchestra.thalesgroup.com.VariableManager.VariableManagerWSAdapterService {

    public VariableManagerWSAdapterServiceLocator() {
    }


    public VariableManagerWSAdapterServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public VariableManagerWSAdapterServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for VariableManager
    private java.lang.String VariableManager_address = "http://localhost:8097/services/VariableManager";

    public java.lang.String getVariableManagerAddress() {
        return VariableManager_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String VariableManagerWSDDServiceName = "VariableManager";

    public java.lang.String getVariableManagerWSDDServiceName() {
        return VariableManagerWSDDServiceName;
    }

    public void setVariableManagerWSDDServiceName(java.lang.String name) {
        VariableManagerWSDDServiceName = name;
    }

    public framework.orchestra.thalesgroup.com.VariableManager.VariableManagerWSAdapter getVariableManager() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(VariableManager_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getVariableManager(endpoint);
    }

    public framework.orchestra.thalesgroup.com.VariableManager.VariableManagerWSAdapter getVariableManager(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            framework.orchestra.thalesgroup.com.VariableManager.VariableManagerSoapBindingStub _stub = new framework.orchestra.thalesgroup.com.VariableManager.VariableManagerSoapBindingStub(portAddress, this);
            _stub.setPortName(getVariableManagerWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setVariableManagerEndpointAddress(java.lang.String address) {
        VariableManager_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (framework.orchestra.thalesgroup.com.VariableManager.VariableManagerWSAdapter.class.isAssignableFrom(serviceEndpointInterface)) {
                framework.orchestra.thalesgroup.com.VariableManager.VariableManagerSoapBindingStub _stub = new framework.orchestra.thalesgroup.com.VariableManager.VariableManagerSoapBindingStub(new java.net.URL(VariableManager_address), this);
                _stub.setPortName(getVariableManagerWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("VariableManager".equals(inputPortName)) {
            return getVariableManager();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "VariableManagerWSAdapterService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "VariableManager"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("VariableManager".equals(portName)) {
            setVariableManagerEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
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
