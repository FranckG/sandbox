/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl;

import com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.CompatibilityType;
import com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ConfigurationFactory;
import com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ConfigurationPackage;
import com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.DocumentRoot;
import com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.OrchestraDoctorLocalConfigurationType;
import com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ParameterType;
import com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ProductType;
import com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionRangeType;
import com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionType;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
public class ConfigurationPackageImpl extends EPackageImpl implements ConfigurationPackage {
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass compatibilityTypeEClass = null;

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
  private EClass orchestraDoctorLocalConfigurationTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass parameterTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass productTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass versionRangeTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass versionTypeEClass = null;

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
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ConfigurationPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private ConfigurationPackageImpl() {
    super(eNS_URI, ConfigurationFactory.eINSTANCE);
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
   * <p>This method is used to initialize {@link ConfigurationPackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static ConfigurationPackage init() {
    if (isInited) return (ConfigurationPackage)EPackage.Registry.INSTANCE.getEPackage(ConfigurationPackage.eNS_URI);

    // Obtain or create and register package
    ConfigurationPackageImpl theConfigurationPackage = (ConfigurationPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ConfigurationPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ConfigurationPackageImpl());

    isInited = true;

    // Initialize simple dependencies
    XMLTypePackage.eINSTANCE.eClass();

    // Create package meta-data objects
    theConfigurationPackage.createPackageContents();

    // Initialize created meta-data
    theConfigurationPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theConfigurationPackage.freeze();

  
    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(ConfigurationPackage.eNS_URI, theConfigurationPackage);
    return theConfigurationPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getCompatibilityType() {
    return compatibilityTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getCompatibilityType_VersionRange() {
    return (EReference)compatibilityTypeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getCompatibilityType_TargetComponent() {
    return (EAttribute)compatibilityTypeEClass.getEStructuralFeatures().get(1);
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
  public EReference getDocumentRoot_OrchestraDoctorLocalConfiguration() {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getOrchestraDoctorLocalConfigurationType() {
    return orchestraDoctorLocalConfigurationTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getOrchestraDoctorLocalConfigurationType_Product() {
    return (EReference)orchestraDoctorLocalConfigurationTypeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getParameterType() {
    return parameterTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getParameterType_Name() {
    return (EAttribute)parameterTypeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getParameterType_Type() {
    return (EAttribute)parameterTypeEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getParameterType_Value() {
    return (EAttribute)parameterTypeEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getProductType() {
    return productTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getProductType_Version() {
    return (EReference)productTypeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getProductType_Category() {
    return (EAttribute)productTypeEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getProductType_Kind() {
    return (EAttribute)productTypeEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getProductType_Name() {
    return (EAttribute)productTypeEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getVersionRangeType() {
    return versionRangeTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVersionRangeType_CompatType() {
    return (EAttribute)versionRangeTypeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVersionRangeType_Range() {
    return (EAttribute)versionRangeTypeEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getVersionType() {
    return versionTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getVersionType_Parameter() {
    return (EReference)versionTypeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getVersionType_Compatibility() {
    return (EReference)versionTypeEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVersionType_ConfigConnector() {
    return (EAttribute)versionTypeEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVersionType_ExecName() {
    return (EAttribute)versionTypeEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVersionType_ExecRelativePath() {
    return (EAttribute)versionTypeEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVersionType_IconPath() {
    return (EAttribute)versionTypeEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVersionType_InstallLocation() {
    return (EAttribute)versionTypeEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVersionType_Value() {
    return (EAttribute)versionTypeEClass.getEStructuralFeatures().get(7);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ConfigurationFactory getConfigurationFactory() {
    return (ConfigurationFactory)getEFactoryInstance();
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
    compatibilityTypeEClass = createEClass(COMPATIBILITY_TYPE);
    createEReference(compatibilityTypeEClass, COMPATIBILITY_TYPE__VERSION_RANGE);
    createEAttribute(compatibilityTypeEClass, COMPATIBILITY_TYPE__TARGET_COMPONENT);

    documentRootEClass = createEClass(DOCUMENT_ROOT);
    createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
    createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
    createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
    createEReference(documentRootEClass, DOCUMENT_ROOT__ORCHESTRA_DOCTOR_LOCAL_CONFIGURATION);

    orchestraDoctorLocalConfigurationTypeEClass = createEClass(ORCHESTRA_DOCTOR_LOCAL_CONFIGURATION_TYPE);
    createEReference(orchestraDoctorLocalConfigurationTypeEClass, ORCHESTRA_DOCTOR_LOCAL_CONFIGURATION_TYPE__PRODUCT);

    parameterTypeEClass = createEClass(PARAMETER_TYPE);
    createEAttribute(parameterTypeEClass, PARAMETER_TYPE__NAME);
    createEAttribute(parameterTypeEClass, PARAMETER_TYPE__TYPE);
    createEAttribute(parameterTypeEClass, PARAMETER_TYPE__VALUE);

    productTypeEClass = createEClass(PRODUCT_TYPE);
    createEReference(productTypeEClass, PRODUCT_TYPE__VERSION);
    createEAttribute(productTypeEClass, PRODUCT_TYPE__CATEGORY);
    createEAttribute(productTypeEClass, PRODUCT_TYPE__KIND);
    createEAttribute(productTypeEClass, PRODUCT_TYPE__NAME);

    versionRangeTypeEClass = createEClass(VERSION_RANGE_TYPE);
    createEAttribute(versionRangeTypeEClass, VERSION_RANGE_TYPE__COMPAT_TYPE);
    createEAttribute(versionRangeTypeEClass, VERSION_RANGE_TYPE__RANGE);

    versionTypeEClass = createEClass(VERSION_TYPE);
    createEReference(versionTypeEClass, VERSION_TYPE__PARAMETER);
    createEReference(versionTypeEClass, VERSION_TYPE__COMPATIBILITY);
    createEAttribute(versionTypeEClass, VERSION_TYPE__CONFIG_CONNECTOR);
    createEAttribute(versionTypeEClass, VERSION_TYPE__EXEC_NAME);
    createEAttribute(versionTypeEClass, VERSION_TYPE__EXEC_RELATIVE_PATH);
    createEAttribute(versionTypeEClass, VERSION_TYPE__ICON_PATH);
    createEAttribute(versionTypeEClass, VERSION_TYPE__INSTALL_LOCATION);
    createEAttribute(versionTypeEClass, VERSION_TYPE__VALUE);
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
    initEClass(compatibilityTypeEClass, CompatibilityType.class, "CompatibilityType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getCompatibilityType_VersionRange(), this.getVersionRangeType(), null, "versionRange", null, 0, -1, CompatibilityType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getCompatibilityType_TargetComponent(), theXMLTypePackage.getString(), "targetComponent", null, 1, 1, CompatibilityType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(documentRootEClass, DocumentRoot.class, "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getDocumentRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_OrchestraDoctorLocalConfiguration(), this.getOrchestraDoctorLocalConfigurationType(), null, "orchestraDoctorLocalConfiguration", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

    initEClass(orchestraDoctorLocalConfigurationTypeEClass, OrchestraDoctorLocalConfigurationType.class, "OrchestraDoctorLocalConfigurationType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getOrchestraDoctorLocalConfigurationType_Product(), this.getProductType(), null, "product", null, 0, -1, OrchestraDoctorLocalConfigurationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(parameterTypeEClass, ParameterType.class, "ParameterType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getParameterType_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, ParameterType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getParameterType_Type(), theXMLTypePackage.getString(), "type", null, 1, 1, ParameterType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getParameterType_Value(), theXMLTypePackage.getString(), "value", null, 1, 1, ParameterType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(productTypeEClass, ProductType.class, "ProductType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getProductType_Version(), this.getVersionType(), null, "version", null, 0, -1, ProductType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getProductType_Category(), theXMLTypePackage.getString(), "category", null, 0, 1, ProductType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getProductType_Kind(), theXMLTypePackage.getString(), "kind", null, 0, 1, ProductType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getProductType_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, ProductType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(versionRangeTypeEClass, VersionRangeType.class, "VersionRangeType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getVersionRangeType_CompatType(), theXMLTypePackage.getString(), "compatType", "all", 0, 1, VersionRangeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVersionRangeType_Range(), theXMLTypePackage.getString(), "range", null, 1, 1, VersionRangeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(versionTypeEClass, VersionType.class, "VersionType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getVersionType_Parameter(), this.getParameterType(), null, "parameter", null, 0, -1, VersionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getVersionType_Compatibility(), this.getCompatibilityType(), null, "compatibility", null, 0, -1, VersionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVersionType_ConfigConnector(), theXMLTypePackage.getString(), "configConnector", null, 0, 1, VersionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVersionType_ExecName(), theXMLTypePackage.getString(), "execName", null, 0, 1, VersionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVersionType_ExecRelativePath(), theXMLTypePackage.getString(), "execRelativePath", null, 0, 1, VersionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVersionType_IconPath(), theXMLTypePackage.getString(), "iconPath", null, 0, 1, VersionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVersionType_InstallLocation(), theXMLTypePackage.getString(), "installLocation", null, 1, 1, VersionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVersionType_Value(), theXMLTypePackage.getString(), "value", null, 1, 1, VersionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

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
      (compatibilityTypeEClass, 
       source, 
       new String[] {
       "name", "compatibility_._type",
       "kind", "elementOnly"
       });		
    addAnnotation
      (getCompatibilityType_VersionRange(), 
       source, 
       new String[] {
       "kind", "element",
       "name", "version_range"
       });		
    addAnnotation
      (getCompatibilityType_TargetComponent(), 
       source, 
       new String[] {
       "kind", "attribute",
       "name", "targetComponent"
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
      (getDocumentRoot_OrchestraDoctorLocalConfiguration(), 
       source, 
       new String[] {
       "kind", "element",
       "name", "OrchestraDoctorLocalConfiguration",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (orchestraDoctorLocalConfigurationTypeEClass, 
       source, 
       new String[] {
       "name", "OrchestraDoctorLocalConfiguration_._type",
       "kind", "elementOnly"
       });		
    addAnnotation
      (getOrchestraDoctorLocalConfigurationType_Product(), 
       source, 
       new String[] {
       "kind", "element",
       "name", "product"
       });		
    addAnnotation
      (parameterTypeEClass, 
       source, 
       new String[] {
       "name", "parameter_._type",
       "kind", "empty"
       });		
    addAnnotation
      (getParameterType_Name(), 
       source, 
       new String[] {
       "kind", "attribute",
       "name", "name"
       });		
    addAnnotation
      (getParameterType_Type(), 
       source, 
       new String[] {
       "kind", "attribute",
       "name", "type"
       });		
    addAnnotation
      (getParameterType_Value(), 
       source, 
       new String[] {
       "kind", "attribute",
       "name", "value"
       });		
    addAnnotation
      (productTypeEClass, 
       source, 
       new String[] {
       "name", "product_._type",
       "kind", "elementOnly"
       });		
    addAnnotation
      (getProductType_Version(), 
       source, 
       new String[] {
       "kind", "element",
       "name", "version"
       });		
    addAnnotation
      (getProductType_Category(), 
       source, 
       new String[] {
       "kind", "attribute",
       "name", "category"
       });		
    addAnnotation
      (getProductType_Kind(), 
       source, 
       new String[] {
       "kind", "attribute",
       "name", "kind"
       });		
    addAnnotation
      (getProductType_Name(), 
       source, 
       new String[] {
       "kind", "attribute",
       "name", "name"
       });		
    addAnnotation
      (versionRangeTypeEClass, 
       source, 
       new String[] {
       "name", "version_range_._type",
       "kind", "empty"
       });		
    addAnnotation
      (getVersionRangeType_CompatType(), 
       source, 
       new String[] {
       "kind", "attribute",
       "name", "compatType"
       });		
    addAnnotation
      (getVersionRangeType_Range(), 
       source, 
       new String[] {
       "kind", "attribute",
       "name", "range"
       });		
    addAnnotation
      (versionTypeEClass, 
       source, 
       new String[] {
       "name", "version_._type",
       "kind", "elementOnly"
       });		
    addAnnotation
      (getVersionType_Parameter(), 
       source, 
       new String[] {
       "kind", "element",
       "name", "parameter"
       });		
    addAnnotation
      (getVersionType_Compatibility(), 
       source, 
       new String[] {
       "kind", "element",
       "name", "compatibility"
       });		
    addAnnotation
      (getVersionType_ConfigConnector(), 
       source, 
       new String[] {
       "kind", "attribute",
       "name", "configConnector"
       });		
    addAnnotation
      (getVersionType_ExecName(), 
       source, 
       new String[] {
       "kind", "attribute",
       "name", "execName"
       });		
    addAnnotation
      (getVersionType_ExecRelativePath(), 
       source, 
       new String[] {
       "kind", "attribute",
       "name", "execRelativePath"
       });		
    addAnnotation
      (getVersionType_IconPath(), 
       source, 
       new String[] {
       "kind", "attribute",
       "name", "iconPath"
       });		
    addAnnotation
      (getVersionType_InstallLocation(), 
       source, 
       new String[] {
       "kind", "attribute",
       "name", "installLocation"
       });		
    addAnnotation
      (getVersionType_Value(), 
       source, 
       new String[] {
       "kind", "attribute",
       "name", "value"
       });
  }

} //ConfigurationPackageImpl
