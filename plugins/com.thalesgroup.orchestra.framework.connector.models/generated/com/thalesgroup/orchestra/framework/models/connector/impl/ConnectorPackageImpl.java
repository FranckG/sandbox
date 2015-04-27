/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.models.connector.impl;

import com.thalesgroup.orchestra.framework.models.connector.Connector;
import com.thalesgroup.orchestra.framework.models.connector.ConnectorDefinition;
import com.thalesgroup.orchestra.framework.models.connector.ConnectorFactory;
import com.thalesgroup.orchestra.framework.models.connector.ConnectorPackage;
import com.thalesgroup.orchestra.framework.models.connector.DocumentRoot;
import com.thalesgroup.orchestra.framework.models.connector.KeepOpen;
import com.thalesgroup.orchestra.framework.models.connector.LaunchArguments;
import com.thalesgroup.orchestra.framework.models.connector.Service;
import com.thalesgroup.orchestra.framework.models.connector.ServiceNameType;

import com.thalesgroup.orchestra.framework.models.connector.Type;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ConnectorPackageImpl extends EPackageImpl implements ConnectorPackage {
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass connectorEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass connectorDefinitionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass documentRootEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass serviceEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass typeEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass keepOpenEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass launchArgumentsEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum serviceNameTypeEEnum = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EDataType serviceNameTypeObjectEDataType = null;

  /**
   * Creates an instance of the model <b>Package</b>, registered with
   * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
   * package URI value.
   * <p>Note: the correct way to create the package is via the static
   * factory method {@link #init init()}, which also performs
   * initialization of the package, or returns the registered package,
   * if one already exists.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.ecore.EPackage.Registry
   * @see com.thalesgroup.orchestra.framework.models.connector.ConnectorPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private ConnectorPackageImpl() {
    super(eNS_URI, ConnectorFactory.eINSTANCE);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static boolean isInited = false;

  /**
   * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
   * 
   * <p>This method is used to initialize {@link ConnectorPackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static ConnectorPackage init() {
    if (isInited) return (ConnectorPackage)EPackage.Registry.INSTANCE.getEPackage(ConnectorPackage.eNS_URI);

    // Obtain or create and register package
    ConnectorPackageImpl theConnectorPackage = (ConnectorPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ConnectorPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ConnectorPackageImpl());

    isInited = true;

    // Initialize simple dependencies
    XMLTypePackage.eINSTANCE.eClass();

    // Create package meta-data objects
    theConnectorPackage.createPackageContents();

    // Initialize created meta-data
    theConnectorPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theConnectorPackage.freeze();

  
    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(ConnectorPackage.eNS_URI, theConnectorPackage);
    return theConnectorPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getConnector() {
    return connectorEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getConnector_UnsupportedService() {
    return (EReference)connectorEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getConnector_ConnectorId() {
    return (EAttribute)connectorEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getConnector_MetadataResolver() {
    return (EAttribute)connectorEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getConnector_ProgId() {
    return (EAttribute)connectorEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getConnector_KeepOpen() {
    return (EReference)connectorEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getConnector_LaunchArguments() {
    return (EReference)connectorEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getConnector_Type() {
    return (EReference)connectorEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getConnectorDefinition() {
    return connectorDefinitionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getConnectorDefinition_Connector() {
    return (EReference)connectorDefinitionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getDocumentRoot() {
    return documentRootEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getDocumentRoot_Mixed() {
    return (EAttribute)documentRootEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getDocumentRoot_XMLNSPrefixMap() {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getDocumentRoot_XSISchemaLocation() {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getDocumentRoot_ConnectorDefinition() {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getService() {
    return serviceEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getService_ServiceName() {
    return (EAttribute)serviceEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getType() {
    return typeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getType_Name() {
    return (EAttribute)typeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getType_Timeout() {
    return (EAttribute)typeEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getKeepOpen() {
    return keepOpenEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getKeepOpen_Name() {
    return (EAttribute)keepOpenEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getLaunchArguments() {
    return launchArgumentsEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getLaunchArguments_Name() {
    return (EAttribute)launchArgumentsEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EEnum getServiceNameType() {
    return serviceNameTypeEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EDataType getServiceNameTypeObject() {
    return serviceNameTypeObjectEDataType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ConnectorFactory getConnectorFactory() {
    return (ConnectorFactory)getEFactoryInstance();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isCreated = false;

  /**
   * Creates the meta-model objects for the package.  This method is
   * guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void createPackageContents() {
    if (isCreated) return;
    isCreated = true;

    // Create classes and their features
    connectorEClass = createEClass(CONNECTOR);
    createEReference(connectorEClass, CONNECTOR__TYPE);
    createEReference(connectorEClass, CONNECTOR__UNSUPPORTED_SERVICE);
    createEAttribute(connectorEClass, CONNECTOR__CONNECTOR_ID);
    createEAttribute(connectorEClass, CONNECTOR__METADATA_RESOLVER);
    createEAttribute(connectorEClass, CONNECTOR__PROG_ID);
    createEReference(connectorEClass, CONNECTOR__KEEP_OPEN);
    createEReference(connectorEClass, CONNECTOR__LAUNCH_ARGUMENTS);

    connectorDefinitionEClass = createEClass(CONNECTOR_DEFINITION);
    createEReference(connectorDefinitionEClass, CONNECTOR_DEFINITION__CONNECTOR);

    documentRootEClass = createEClass(DOCUMENT_ROOT);
    createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
    createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
    createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
    createEReference(documentRootEClass, DOCUMENT_ROOT__CONNECTOR_DEFINITION);

    serviceEClass = createEClass(SERVICE);
    createEAttribute(serviceEClass, SERVICE__SERVICE_NAME);

    typeEClass = createEClass(TYPE);
    createEAttribute(typeEClass, TYPE__NAME);
    createEAttribute(typeEClass, TYPE__TIMEOUT);

    keepOpenEClass = createEClass(KEEP_OPEN);
    createEAttribute(keepOpenEClass, KEEP_OPEN__NAME);

    launchArgumentsEClass = createEClass(LAUNCH_ARGUMENTS);
    createEAttribute(launchArgumentsEClass, LAUNCH_ARGUMENTS__NAME);

    // Create enums
    serviceNameTypeEEnum = createEEnum(SERVICE_NAME_TYPE);

    // Create data types
    serviceNameTypeObjectEDataType = createEDataType(SERVICE_NAME_TYPE_OBJECT);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isInitialized = false;

  /**
   * Complete the initialization of the package and its meta-model.  This
   * method is guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void initializePackageContents() {
    if (isInitialized) return;
    isInitialized = true;

    // Initialize package
    setName(eNAME);
    setNsPrefix(eNS_PREFIX);
    setNsURI(eNS_URI);

    // Obtain other dependent packages
    XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes

    // Initialize classes and features; add operations and parameters
    initEClass(connectorEClass, Connector.class, "Connector", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getConnector_Type(), this.getType(), null, "type", null, 1, -1, Connector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getConnector_UnsupportedService(), this.getService(), null, "unsupportedService", null, 0, -1, Connector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getConnector_ConnectorId(), theXMLTypePackage.getString(), "connectorId", null, 1, 1, Connector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getConnector_MetadataResolver(), theXMLTypePackage.getBoolean(), "metadataResolver", null, 0, 1, Connector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getConnector_ProgId(), theXMLTypePackage.getString(), "progId", null, 0, 1, Connector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getConnector_KeepOpen(), this.getKeepOpen(), null, "keepOpen", null, 0, 1, Connector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getConnector_LaunchArguments(), this.getLaunchArguments(), null, "launchArguments", null, 0, 1, Connector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(connectorDefinitionEClass, ConnectorDefinition.class, "ConnectorDefinition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getConnectorDefinition_Connector(), this.getConnector(), null, "connector", null, 1, 1, ConnectorDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(documentRootEClass, DocumentRoot.class, "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getDocumentRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_ConnectorDefinition(), this.getConnectorDefinition(), null, "connectorDefinition", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

    initEClass(serviceEClass, Service.class, "Service", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getService_ServiceName(), this.getServiceNameType(), "serviceName", null, 1, 1, Service.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(typeEClass, Type.class, "Type", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getType_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, Type.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getType_Timeout(), theXMLTypePackage.getInteger(), "timeout", null, 0, 1, Type.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(keepOpenEClass, KeepOpen.class, "KeepOpen", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getKeepOpen_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, KeepOpen.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(launchArgumentsEClass, LaunchArguments.class, "LaunchArguments", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getLaunchArguments_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, LaunchArguments.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    // Initialize enums and add enum literals
    initEEnum(serviceNameTypeEEnum, ServiceNameType.class, "ServiceNameType");
    addEEnumLiteral(serviceNameTypeEEnum, ServiceNameType.CREATE);
    addEEnumLiteral(serviceNameTypeEEnum, ServiceNameType.EXPAND);
    addEEnumLiteral(serviceNameTypeEEnum, ServiceNameType.EXPORT_DOC);
    addEEnumLiteral(serviceNameTypeEEnum, ServiceNameType.EXPORT_LM);
    addEEnumLiteral(serviceNameTypeEEnum, ServiceNameType.NAVIGATE);
    addEEnumLiteral(serviceNameTypeEEnum, ServiceNameType.SEARCH);

    // Initialize data types
    initEDataType(serviceNameTypeObjectEDataType, ServiceNameType.class, "ServiceNameTypeObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);

    // Create resource
    createResource(eNS_URI);

    // Create annotations
    // http:///org/eclipse/emf/ecore/util/ExtendedMetaData
    createExtendedMetaDataAnnotations();
  }

  /**
   * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected void createExtendedMetaDataAnnotations() {
    String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";		
    addAnnotation
      (connectorEClass, 
       source, 
       new String[] {
       "name", "Connector",
       "kind", "elementOnly"
       });		
    addAnnotation
      (getConnector_Type(), 
       source, 
       new String[] {
       "kind", "element",
       "name", "type"
       });		
    addAnnotation
      (getConnector_UnsupportedService(), 
       source, 
       new String[] {
       "kind", "element",
       "name", "unsupportedService"
       });		
    addAnnotation
      (getConnector_ConnectorId(), 
       source, 
       new String[] {
       "kind", "attribute",
       "name", "connectorId"
       });		
    addAnnotation
      (getConnector_MetadataResolver(), 
       source, 
       new String[] {
       "kind", "attribute",
       "name", "metadataResolver"
       });		
    addAnnotation
      (getConnector_ProgId(), 
       source, 
       new String[] {
       "kind", "attribute",
       "name", "progId"
       });		
    addAnnotation
      (connectorDefinitionEClass, 
       source, 
       new String[] {
       "name", "ConnectorDefinition",
       "kind", "elementOnly"
       });		
    addAnnotation
      (getConnectorDefinition_Connector(), 
       source, 
       new String[] {
       "kind", "element",
       "name", "connector"
       });		
    addAnnotation
      (documentRootEClass, 
       source, 
       new String[] {
       "name", "",
       "kind", "mixed"
       });		
    addAnnotation
      (getDocumentRoot_Mixed(), 
       source, 
       new String[] {
       "kind", "elementWildcard",
       "name", ":mixed"
       });		
    addAnnotation
      (getDocumentRoot_XMLNSPrefixMap(), 
       source, 
       new String[] {
       "kind", "attribute",
       "name", "xmlns:prefix"
       });		
    addAnnotation
      (getDocumentRoot_XSISchemaLocation(), 
       source, 
       new String[] {
       "kind", "attribute",
       "name", "xsi:schemaLocation"
       });		
    addAnnotation
      (getDocumentRoot_ConnectorDefinition(), 
       source, 
       new String[] {
       "kind", "element",
       "name", "connectorDefinition",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (serviceEClass, 
       source, 
       new String[] {
       "name", "Service",
       "kind", "empty"
       });		
    addAnnotation
      (getService_ServiceName(), 
       source, 
       new String[] {
       "kind", "attribute",
       "name", "serviceName"
       });		
    addAnnotation
      (serviceNameTypeEEnum, 
       source, 
       new String[] {
       "name", "serviceName_._type"
       });		
    addAnnotation
      (serviceNameTypeObjectEDataType, 
       source, 
       new String[] {
       "name", "serviceName_._type:Object",
       "baseType", "serviceName_._type"
       });		
    addAnnotation
      (typeEClass, 
       source, 
       new String[] {
       "name", "Type",
       "kind", "empty"
       });		
    addAnnotation
      (getType_Name(), 
       source, 
       new String[] {
       "kind", "attribute",
       "name", "name"
       });		
    addAnnotation
      (getType_Timeout(), 
       source, 
       new String[] {
       "kind", "attribute",
       "name", "timeout"
       });
  }

} //ConnectorPackageImpl
