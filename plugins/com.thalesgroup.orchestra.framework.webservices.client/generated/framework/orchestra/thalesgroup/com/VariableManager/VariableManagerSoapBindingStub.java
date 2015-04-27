/**
 * VariableManagerSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package framework.orchestra.thalesgroup.com.VariableManager;

public class VariableManagerSoapBindingStub extends org.apache.axis.client.Stub implements framework.orchestra.thalesgroup.com.VariableManager.VariableManagerWSAdapter {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[18];
        _initOperationDesc1();
        _initOperationDesc2();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCategories");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "categoryPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "ArrayOf_xsd_string"));
        oper.setReturnClass(java.lang.String[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "categories"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getVariable");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "variablePath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "VariableWS"));
        oper.setReturnClass(framework.orchestra.thalesgroup.com.VariableManager.VariableWS.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "variable"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getAllContextNames");
        oper.setReturnType(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "ArrayOf_xsd_string"));
        oper.setReturnClass(java.lang.String[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "contextNames"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getAllVariables");
        oper.setReturnType(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "ArrayOf_impl_VariableWS"));
        oper.setReturnClass(framework.orchestra.thalesgroup.com.VariableManager.VariableWS[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "variables"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getVariables");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "categoryPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "ArrayOf_impl_VariableWS"));
        oper.setReturnClass(framework.orchestra.thalesgroup.com.VariableManager.VariableWS[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "variables"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCurrentContextName");
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "currentContextName"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getSubstitutedValue");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "variablePath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "value"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("registerAsVariableConsumer");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "toolInstanceID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("unregisterAsVariableConsumer");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "toolInstanceID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getAllVariablesLocalized");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "locale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "ArrayOf_impl_VariableWS"));
        oper.setReturnClass(framework.orchestra.thalesgroup.com.VariableManager.VariableWS[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "variables"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[9] = oper;

    }

    private static void _initOperationDesc2(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getVariableLocalized");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "variablePath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "locale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "VariableWS"));
        oper.setReturnClass(framework.orchestra.thalesgroup.com.VariableManager.VariableWS.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "variable"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[10] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getVariablesLocalized");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "categoryPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "locale"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "ArrayOf_impl_VariableWS"));
        oper.setReturnClass(framework.orchestra.thalesgroup.com.VariableManager.VariableWS[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "variables"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[11] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getVariableArtefactPath");
        oper.setReturnType(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "VariableWS"));
        oper.setReturnClass(framework.orchestra.thalesgroup.com.VariableManager.VariableWS.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "variable"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[12] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getVariableConfigurationDirectory");
        oper.setReturnType(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "VariableWS"));
        oper.setReturnClass(framework.orchestra.thalesgroup.com.VariableManager.VariableWS.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "variable"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[13] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getVariableSharedDirectory");
        oper.setReturnType(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "VariableWS"));
        oper.setReturnClass(framework.orchestra.thalesgroup.com.VariableManager.VariableWS.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "variable"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[14] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getVariableTemporaryDirectory");
        oper.setReturnType(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "VariableWS"));
        oper.setReturnClass(framework.orchestra.thalesgroup.com.VariableManager.VariableWS.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "variable"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[15] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCategorySeparator");
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "categorySeparator"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[16] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("configurationDirectoryUpdated");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "confDirPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "deltas"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "ConfDirDeltas"), framework.orchestra.thalesgroup.com.VariableManager.ConfDirDelta[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("", "delta"));
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "DeltaStates"));
        oper.setReturnClass(framework.orchestra.thalesgroup.com.VariableManager.DeltaState[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "result"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("", "deltaState"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[17] = oper;

    }

    public VariableManagerSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public VariableManagerSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public VariableManagerSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", ">ConfDirDelta>deltaType");
            cachedSerQNames.add(qName);
            cls = framework.orchestra.thalesgroup.com.VariableManager.ConfDirDeltaDeltaType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "ArrayOf_impl_EnvironmentValueWS");
            cachedSerQNames.add(qName);
            cls = framework.orchestra.thalesgroup.com.VariableManager.EnvironmentValueWS[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "EnvironmentValueWS");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "ArrayOf_impl_EnvironmentWS");
            cachedSerQNames.add(qName);
            cls = framework.orchestra.thalesgroup.com.VariableManager.EnvironmentWS[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "EnvironmentWS");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "ArrayOf_impl_VariableWS");
            cachedSerQNames.add(qName);
            cls = framework.orchestra.thalesgroup.com.VariableManager.VariableWS[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "VariableWS");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "ArrayOf_xsd_string");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "ConfDirDelta");
            cachedSerQNames.add(qName);
            cls = framework.orchestra.thalesgroup.com.VariableManager.ConfDirDelta.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "ConfDirDeltas");
            cachedSerQNames.add(qName);
            cls = framework.orchestra.thalesgroup.com.VariableManager.ConfDirDelta[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "ConfDirDelta");
            qName2 = new javax.xml.namespace.QName("", "delta");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "DeltaState");
            cachedSerQNames.add(qName);
            cls = framework.orchestra.thalesgroup.com.VariableManager.DeltaState.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "DeltaStates");
            cachedSerQNames.add(qName);
            cls = framework.orchestra.thalesgroup.com.VariableManager.DeltaState[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "DeltaState");
            qName2 = new javax.xml.namespace.QName("", "deltaState");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "EnvironmentValueWS");
            cachedSerQNames.add(qName);
            cls = framework.orchestra.thalesgroup.com.VariableManager.EnvironmentValueWS.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "EnvironmentVariableWS");
            cachedSerQNames.add(qName);
            cls = framework.orchestra.thalesgroup.com.VariableManager.EnvironmentVariableWS.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "EnvironmentWS");
            cachedSerQNames.add(qName);
            cls = framework.orchestra.thalesgroup.com.VariableManager.EnvironmentWS.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "VariableWS");
            cachedSerQNames.add(qName);
            cls = framework.orchestra.thalesgroup.com.VariableManager.VariableWS.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public java.lang.String[] getCategories(java.lang.String categoryPath) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "getCategories"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {categoryPath});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String[]) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public framework.orchestra.thalesgroup.com.VariableManager.VariableWS getVariable(java.lang.String variablePath) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "getVariable"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {variablePath});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (framework.orchestra.thalesgroup.com.VariableManager.VariableWS) _resp;
            } catch (java.lang.Exception _exception) {
                return (framework.orchestra.thalesgroup.com.VariableManager.VariableWS) org.apache.axis.utils.JavaUtils.convert(_resp, framework.orchestra.thalesgroup.com.VariableManager.VariableWS.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String[] getAllContextNames() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "getAllContextNames"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String[]) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public framework.orchestra.thalesgroup.com.VariableManager.VariableWS[] getAllVariables() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "getAllVariables"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (framework.orchestra.thalesgroup.com.VariableManager.VariableWS[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (framework.orchestra.thalesgroup.com.VariableManager.VariableWS[]) org.apache.axis.utils.JavaUtils.convert(_resp, framework.orchestra.thalesgroup.com.VariableManager.VariableWS[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public framework.orchestra.thalesgroup.com.VariableManager.VariableWS[] getVariables(java.lang.String categoryPath) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "getVariables"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {categoryPath});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (framework.orchestra.thalesgroup.com.VariableManager.VariableWS[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (framework.orchestra.thalesgroup.com.VariableManager.VariableWS[]) org.apache.axis.utils.JavaUtils.convert(_resp, framework.orchestra.thalesgroup.com.VariableManager.VariableWS[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String getCurrentContextName() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "getCurrentContextName"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String getSubstitutedValue(java.lang.String variablePath) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "getSubstitutedValue"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {variablePath});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void registerAsVariableConsumer(java.lang.String toolInstanceID) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "registerAsVariableConsumer"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {toolInstanceID});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        extractAttachments(_call);
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void unregisterAsVariableConsumer(java.lang.String toolInstanceID) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "unregisterAsVariableConsumer"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {toolInstanceID});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        extractAttachments(_call);
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public framework.orchestra.thalesgroup.com.VariableManager.VariableWS[] getAllVariablesLocalized(java.lang.String locale) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[9]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "getAllVariablesLocalized"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {locale});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (framework.orchestra.thalesgroup.com.VariableManager.VariableWS[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (framework.orchestra.thalesgroup.com.VariableManager.VariableWS[]) org.apache.axis.utils.JavaUtils.convert(_resp, framework.orchestra.thalesgroup.com.VariableManager.VariableWS[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public framework.orchestra.thalesgroup.com.VariableManager.VariableWS getVariableLocalized(java.lang.String variablePath, java.lang.String locale) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[10]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "getVariableLocalized"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {variablePath, locale});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (framework.orchestra.thalesgroup.com.VariableManager.VariableWS) _resp;
            } catch (java.lang.Exception _exception) {
                return (framework.orchestra.thalesgroup.com.VariableManager.VariableWS) org.apache.axis.utils.JavaUtils.convert(_resp, framework.orchestra.thalesgroup.com.VariableManager.VariableWS.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public framework.orchestra.thalesgroup.com.VariableManager.VariableWS[] getVariablesLocalized(java.lang.String categoryPath, java.lang.String locale) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[11]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "getVariablesLocalized"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {categoryPath, locale});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (framework.orchestra.thalesgroup.com.VariableManager.VariableWS[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (framework.orchestra.thalesgroup.com.VariableManager.VariableWS[]) org.apache.axis.utils.JavaUtils.convert(_resp, framework.orchestra.thalesgroup.com.VariableManager.VariableWS[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public framework.orchestra.thalesgroup.com.VariableManager.VariableWS getVariableArtefactPath() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[12]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "getVariableArtefactPath"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (framework.orchestra.thalesgroup.com.VariableManager.VariableWS) _resp;
            } catch (java.lang.Exception _exception) {
                return (framework.orchestra.thalesgroup.com.VariableManager.VariableWS) org.apache.axis.utils.JavaUtils.convert(_resp, framework.orchestra.thalesgroup.com.VariableManager.VariableWS.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public framework.orchestra.thalesgroup.com.VariableManager.VariableWS getVariableConfigurationDirectory() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[13]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "getVariableConfigurationDirectory"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (framework.orchestra.thalesgroup.com.VariableManager.VariableWS) _resp;
            } catch (java.lang.Exception _exception) {
                return (framework.orchestra.thalesgroup.com.VariableManager.VariableWS) org.apache.axis.utils.JavaUtils.convert(_resp, framework.orchestra.thalesgroup.com.VariableManager.VariableWS.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public framework.orchestra.thalesgroup.com.VariableManager.VariableWS getVariableSharedDirectory() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[14]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "getVariableSharedDirectory"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (framework.orchestra.thalesgroup.com.VariableManager.VariableWS) _resp;
            } catch (java.lang.Exception _exception) {
                return (framework.orchestra.thalesgroup.com.VariableManager.VariableWS) org.apache.axis.utils.JavaUtils.convert(_resp, framework.orchestra.thalesgroup.com.VariableManager.VariableWS.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public framework.orchestra.thalesgroup.com.VariableManager.VariableWS getVariableTemporaryDirectory() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[15]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "getVariableTemporaryDirectory"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (framework.orchestra.thalesgroup.com.VariableManager.VariableWS) _resp;
            } catch (java.lang.Exception _exception) {
                return (framework.orchestra.thalesgroup.com.VariableManager.VariableWS) org.apache.axis.utils.JavaUtils.convert(_resp, framework.orchestra.thalesgroup.com.VariableManager.VariableWS.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String getCategorySeparator() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[16]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "getCategorySeparator"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public framework.orchestra.thalesgroup.com.VariableManager.DeltaState[] configurationDirectoryUpdated(java.lang.String confDirPath, framework.orchestra.thalesgroup.com.VariableManager.ConfDirDelta[] deltas) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[17]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://com.thalesgroup.orchestra.framework/VariableManager/configurationDirectoryUpdated");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "configurationDirectoryUpdated"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {confDirPath, deltas});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (framework.orchestra.thalesgroup.com.VariableManager.DeltaState[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (framework.orchestra.thalesgroup.com.VariableManager.DeltaState[]) org.apache.axis.utils.JavaUtils.convert(_resp, framework.orchestra.thalesgroup.com.VariableManager.DeltaState[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
