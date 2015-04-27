/**
 * VariableManagerSoapBindingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package framework.orchestra.thalesgroup.com.VariableManager;

public class VariableManagerSoapBindingSkeleton implements framework.orchestra.thalesgroup.com.VariableManager.VariableManagerWSAdapter, org.apache.axis.wsdl.Skeleton {
    private framework.orchestra.thalesgroup.com.VariableManager.VariableManagerWSAdapter impl;
    private static java.util.Map _myOperations = new java.util.Hashtable();
    private static java.util.Collection _myOperationsList = new java.util.ArrayList();

    /**
    * Returns List of OperationDesc objects with this name
    */
    public static java.util.List getOperationDescByName(java.lang.String methodName) {
        return (java.util.List)_myOperations.get(methodName);
    }

    /**
    * Returns Collection of OperationDescs
    */
    public static java.util.Collection getOperationDescs() {
        return _myOperationsList;
    }

    static {
        org.apache.axis.description.OperationDesc _oper;
        org.apache.axis.description.FaultDesc _fault;
        org.apache.axis.description.ParameterDesc [] _params;
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "categoryPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getCategories", _params, new javax.xml.namespace.QName("", "categories"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "ArrayOf_xsd_string"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "getCategories"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getCategories") == null) {
            _myOperations.put("getCategories", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getCategories")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "variablePath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getVariable", _params, new javax.xml.namespace.QName("", "variable"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "VariableWS"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "getVariable"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getVariable") == null) {
            _myOperations.put("getVariable", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getVariable")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
        };
        _oper = new org.apache.axis.description.OperationDesc("getAllContextNames", _params, new javax.xml.namespace.QName("", "contextNames"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "ArrayOf_xsd_string"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "getAllContextNames"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getAllContextNames") == null) {
            _myOperations.put("getAllContextNames", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getAllContextNames")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
        };
        _oper = new org.apache.axis.description.OperationDesc("getAllVariables", _params, new javax.xml.namespace.QName("", "variables"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "ArrayOf_impl_VariableWS"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "getAllVariables"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getAllVariables") == null) {
            _myOperations.put("getAllVariables", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getAllVariables")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "categoryPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getVariables", _params, new javax.xml.namespace.QName("", "variables"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "ArrayOf_impl_VariableWS"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "getVariables"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getVariables") == null) {
            _myOperations.put("getVariables", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getVariables")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
        };
        _oper = new org.apache.axis.description.OperationDesc("getCurrentContextName", _params, new javax.xml.namespace.QName("", "currentContextName"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "getCurrentContextName"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getCurrentContextName") == null) {
            _myOperations.put("getCurrentContextName", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getCurrentContextName")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "variablePath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getSubstitutedValue", _params, new javax.xml.namespace.QName("", "value"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "getSubstitutedValue"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getSubstitutedValue") == null) {
            _myOperations.put("getSubstitutedValue", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getSubstitutedValue")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "toolInstanceID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("registerAsVariableConsumer", _params, null);
        _oper.setElementQName(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "registerAsVariableConsumer"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("registerAsVariableConsumer") == null) {
            _myOperations.put("registerAsVariableConsumer", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("registerAsVariableConsumer")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "toolInstanceID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("unregisterAsVariableConsumer", _params, null);
        _oper.setElementQName(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "unregisterAsVariableConsumer"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("unregisterAsVariableConsumer") == null) {
            _myOperations.put("unregisterAsVariableConsumer", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("unregisterAsVariableConsumer")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "locale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getAllVariablesLocalized", _params, new javax.xml.namespace.QName("", "variables"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "ArrayOf_impl_VariableWS"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "getAllVariablesLocalized"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getAllVariablesLocalized") == null) {
            _myOperations.put("getAllVariablesLocalized", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getAllVariablesLocalized")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "variablePath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "locale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getVariableLocalized", _params, new javax.xml.namespace.QName("", "variable"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "VariableWS"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "getVariableLocalized"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getVariableLocalized") == null) {
            _myOperations.put("getVariableLocalized", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getVariableLocalized")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "categoryPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "locale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getVariablesLocalized", _params, new javax.xml.namespace.QName("", "variables"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "ArrayOf_impl_VariableWS"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "getVariablesLocalized"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getVariablesLocalized") == null) {
            _myOperations.put("getVariablesLocalized", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getVariablesLocalized")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
        };
        _oper = new org.apache.axis.description.OperationDesc("getVariableArtefactPath", _params, new javax.xml.namespace.QName("", "variable"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "VariableWS"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "getVariableArtefactPath"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getVariableArtefactPath") == null) {
            _myOperations.put("getVariableArtefactPath", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getVariableArtefactPath")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
        };
        _oper = new org.apache.axis.description.OperationDesc("getVariableConfigurationDirectory", _params, new javax.xml.namespace.QName("", "variable"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "VariableWS"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "getVariableConfigurationDirectory"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getVariableConfigurationDirectory") == null) {
            _myOperations.put("getVariableConfigurationDirectory", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getVariableConfigurationDirectory")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
        };
        _oper = new org.apache.axis.description.OperationDesc("getVariableSharedDirectory", _params, new javax.xml.namespace.QName("", "variable"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "VariableWS"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "getVariableSharedDirectory"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getVariableSharedDirectory") == null) {
            _myOperations.put("getVariableSharedDirectory", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getVariableSharedDirectory")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
        };
        _oper = new org.apache.axis.description.OperationDesc("getVariableTemporaryDirectory", _params, new javax.xml.namespace.QName("", "variable"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "VariableWS"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "getVariableTemporaryDirectory"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getVariableTemporaryDirectory") == null) {
            _myOperations.put("getVariableTemporaryDirectory", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getVariableTemporaryDirectory")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
        };
        _oper = new org.apache.axis.description.OperationDesc("getCategorySeparator", _params, new javax.xml.namespace.QName("", "categorySeparator"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "getCategorySeparator"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getCategorySeparator") == null) {
            _myOperations.put("getCategorySeparator", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getCategorySeparator")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "confDirPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "deltas"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "ConfDirDeltas"), framework.orchestra.thalesgroup.com.VariableManager.ConfDirDelta[].class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("configurationDirectoryUpdated", _params, new javax.xml.namespace.QName("", "result"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "DeltaStates"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "configurationDirectoryUpdated"));
        _oper.setSoapAction("http://com.thalesgroup.orchestra.framework/VariableManager/configurationDirectoryUpdated");
        _myOperationsList.add(_oper);
        if (_myOperations.get("configurationDirectoryUpdated") == null) {
            _myOperations.put("configurationDirectoryUpdated", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("configurationDirectoryUpdated")).add(_oper);
    }

    public VariableManagerSoapBindingSkeleton() {
        this.impl = new framework.orchestra.thalesgroup.com.VariableManager.VariableManagerSoapBindingImpl();
    }

    public VariableManagerSoapBindingSkeleton(framework.orchestra.thalesgroup.com.VariableManager.VariableManagerWSAdapter impl) {
        this.impl = impl;
    }
    public java.lang.String[] getCategories(java.lang.String categoryPath) throws java.rmi.RemoteException
    {
        java.lang.String[] ret = impl.getCategories(categoryPath);
        return ret;
    }

    public framework.orchestra.thalesgroup.com.VariableManager.VariableWS getVariable(java.lang.String variablePath) throws java.rmi.RemoteException
    {
        framework.orchestra.thalesgroup.com.VariableManager.VariableWS ret = impl.getVariable(variablePath);
        return ret;
    }

    public java.lang.String[] getAllContextNames() throws java.rmi.RemoteException
    {
        java.lang.String[] ret = impl.getAllContextNames();
        return ret;
    }

    public framework.orchestra.thalesgroup.com.VariableManager.VariableWS[] getAllVariables() throws java.rmi.RemoteException
    {
        framework.orchestra.thalesgroup.com.VariableManager.VariableWS[] ret = impl.getAllVariables();
        return ret;
    }

    public framework.orchestra.thalesgroup.com.VariableManager.VariableWS[] getVariables(java.lang.String categoryPath) throws java.rmi.RemoteException
    {
        framework.orchestra.thalesgroup.com.VariableManager.VariableWS[] ret = impl.getVariables(categoryPath);
        return ret;
    }

    public java.lang.String getCurrentContextName() throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getCurrentContextName();
        return ret;
    }

    public java.lang.String getSubstitutedValue(java.lang.String variablePath) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getSubstitutedValue(variablePath);
        return ret;
    }

    public void registerAsVariableConsumer(java.lang.String toolInstanceID) throws java.rmi.RemoteException
    {
        impl.registerAsVariableConsumer(toolInstanceID);
    }

    public void unregisterAsVariableConsumer(java.lang.String toolInstanceID) throws java.rmi.RemoteException
    {
        impl.unregisterAsVariableConsumer(toolInstanceID);
    }

    public framework.orchestra.thalesgroup.com.VariableManager.VariableWS[] getAllVariablesLocalized(java.lang.String locale) throws java.rmi.RemoteException
    {
        framework.orchestra.thalesgroup.com.VariableManager.VariableWS[] ret = impl.getAllVariablesLocalized(locale);
        return ret;
    }

    public framework.orchestra.thalesgroup.com.VariableManager.VariableWS getVariableLocalized(java.lang.String variablePath, java.lang.String locale) throws java.rmi.RemoteException
    {
        framework.orchestra.thalesgroup.com.VariableManager.VariableWS ret = impl.getVariableLocalized(variablePath, locale);
        return ret;
    }

    public framework.orchestra.thalesgroup.com.VariableManager.VariableWS[] getVariablesLocalized(java.lang.String categoryPath, java.lang.String locale) throws java.rmi.RemoteException
    {
        framework.orchestra.thalesgroup.com.VariableManager.VariableWS[] ret = impl.getVariablesLocalized(categoryPath, locale);
        return ret;
    }

    public framework.orchestra.thalesgroup.com.VariableManager.VariableWS getVariableArtefactPath() throws java.rmi.RemoteException
    {
        framework.orchestra.thalesgroup.com.VariableManager.VariableWS ret = impl.getVariableArtefactPath();
        return ret;
    }

    public framework.orchestra.thalesgroup.com.VariableManager.VariableWS getVariableConfigurationDirectory() throws java.rmi.RemoteException
    {
        framework.orchestra.thalesgroup.com.VariableManager.VariableWS ret = impl.getVariableConfigurationDirectory();
        return ret;
    }

    public framework.orchestra.thalesgroup.com.VariableManager.VariableWS getVariableSharedDirectory() throws java.rmi.RemoteException
    {
        framework.orchestra.thalesgroup.com.VariableManager.VariableWS ret = impl.getVariableSharedDirectory();
        return ret;
    }

    public framework.orchestra.thalesgroup.com.VariableManager.VariableWS getVariableTemporaryDirectory() throws java.rmi.RemoteException
    {
        framework.orchestra.thalesgroup.com.VariableManager.VariableWS ret = impl.getVariableTemporaryDirectory();
        return ret;
    }

    public java.lang.String getCategorySeparator() throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getCategorySeparator();
        return ret;
    }

    public framework.orchestra.thalesgroup.com.VariableManager.DeltaState[] configurationDirectoryUpdated(java.lang.String confDirPath, framework.orchestra.thalesgroup.com.VariableManager.ConfDirDelta[] deltas) throws java.rmi.RemoteException
    {
        framework.orchestra.thalesgroup.com.VariableManager.DeltaState[] ret = impl.configurationDirectoryUpdated(confDirPath, deltas);
        return ret;
    }

}
