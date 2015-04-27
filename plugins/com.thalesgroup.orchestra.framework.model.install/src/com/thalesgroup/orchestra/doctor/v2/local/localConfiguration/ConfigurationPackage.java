/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.doctor.v2.local.localConfiguration;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * 
 *     		XML Schema to identify Orchestra local Configuration, informations about all products.
 *   
 * <!-- end-model-doc -->
 * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ConfigurationFactory
 * @model kind="package"
 * @generated
 */
public interface ConfigurationPackage extends EPackage {
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "localConfiguration";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://www.thalesgroup.com/orchestraDoctor/v2/localConfiguration";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "localConfiguration";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  ConfigurationPackage eINSTANCE = com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.ConfigurationPackageImpl.init();

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.CompatibilityTypeImpl <em>Compatibility Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.CompatibilityTypeImpl
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.ConfigurationPackageImpl#getCompatibilityType()
   * @generated
   */
  int COMPATIBILITY_TYPE = 0;

  /**
   * The feature id for the '<em><b>Version Range</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COMPATIBILITY_TYPE__VERSION_RANGE = 0;

  /**
   * The feature id for the '<em><b>Target Component</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COMPATIBILITY_TYPE__TARGET_COMPONENT = 1;

  /**
   * The number of structural features of the '<em>Compatibility Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int COMPATIBILITY_TYPE_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.DocumentRootImpl <em>Document Root</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.DocumentRootImpl
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.ConfigurationPackageImpl#getDocumentRoot()
   * @generated
   */
  int DOCUMENT_ROOT = 1;

  /**
   * The feature id for the '<em><b>Mixed</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT_ROOT__MIXED = 0;

  /**
   * The feature id for the '<em><b>XMLNS Prefix Map</b></em>' map.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT_ROOT__XMLNS_PREFIX_MAP = 1;

  /**
   * The feature id for the '<em><b>XSI Schema Location</b></em>' map.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = 2;

  /**
   * The feature id for the '<em><b>Orchestra Doctor Local Configuration</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT_ROOT__ORCHESTRA_DOCTOR_LOCAL_CONFIGURATION = 3;

  /**
   * The number of structural features of the '<em>Document Root</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT_ROOT_FEATURE_COUNT = 4;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.OrchestraDoctorLocalConfigurationTypeImpl <em>Orchestra Doctor Local Configuration Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.OrchestraDoctorLocalConfigurationTypeImpl
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.ConfigurationPackageImpl#getOrchestraDoctorLocalConfigurationType()
   * @generated
   */
  int ORCHESTRA_DOCTOR_LOCAL_CONFIGURATION_TYPE = 2;

  /**
   * The feature id for the '<em><b>Product</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ORCHESTRA_DOCTOR_LOCAL_CONFIGURATION_TYPE__PRODUCT = 0;

  /**
   * The number of structural features of the '<em>Orchestra Doctor Local Configuration Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ORCHESTRA_DOCTOR_LOCAL_CONFIGURATION_TYPE_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.ParameterTypeImpl <em>Parameter Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.ParameterTypeImpl
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.ConfigurationPackageImpl#getParameterType()
   * @generated
   */
  int PARAMETER_TYPE = 3;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARAMETER_TYPE__NAME = 0;

  /**
   * The feature id for the '<em><b>Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARAMETER_TYPE__TYPE = 1;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARAMETER_TYPE__VALUE = 2;

  /**
   * The number of structural features of the '<em>Parameter Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PARAMETER_TYPE_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.ProductTypeImpl <em>Product Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.ProductTypeImpl
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.ConfigurationPackageImpl#getProductType()
   * @generated
   */
  int PRODUCT_TYPE = 4;

  /**
   * The feature id for the '<em><b>Version</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PRODUCT_TYPE__VERSION = 0;

  /**
   * The feature id for the '<em><b>Category</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PRODUCT_TYPE__CATEGORY = 1;

  /**
   * The feature id for the '<em><b>Kind</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PRODUCT_TYPE__KIND = 2;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PRODUCT_TYPE__NAME = 3;

  /**
   * The number of structural features of the '<em>Product Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PRODUCT_TYPE_FEATURE_COUNT = 4;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.VersionRangeTypeImpl <em>Version Range Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.VersionRangeTypeImpl
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.ConfigurationPackageImpl#getVersionRangeType()
   * @generated
   */
  int VERSION_RANGE_TYPE = 5;

  /**
   * The feature id for the '<em><b>Compat Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION_RANGE_TYPE__COMPAT_TYPE = 0;

  /**
   * The feature id for the '<em><b>Range</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION_RANGE_TYPE__RANGE = 1;

  /**
   * The number of structural features of the '<em>Version Range Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION_RANGE_TYPE_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.VersionTypeImpl <em>Version Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.VersionTypeImpl
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.ConfigurationPackageImpl#getVersionType()
   * @generated
   */
  int VERSION_TYPE = 6;

  /**
   * The feature id for the '<em><b>Parameter</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION_TYPE__PARAMETER = 0;

  /**
   * The feature id for the '<em><b>Compatibility</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION_TYPE__COMPATIBILITY = 1;

  /**
   * The feature id for the '<em><b>Config Connector</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION_TYPE__CONFIG_CONNECTOR = 2;

  /**
   * The feature id for the '<em><b>Exec Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION_TYPE__EXEC_NAME = 3;

  /**
   * The feature id for the '<em><b>Exec Relative Path</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION_TYPE__EXEC_RELATIVE_PATH = 4;

  /**
   * The feature id for the '<em><b>Icon Path</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION_TYPE__ICON_PATH = 5;

  /**
   * The feature id for the '<em><b>Install Location</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION_TYPE__INSTALL_LOCATION = 6;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION_TYPE__VALUE = 7;

  /**
   * The number of structural features of the '<em>Version Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION_TYPE_FEATURE_COUNT = 8;


  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.CompatibilityType <em>Compatibility Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Compatibility Type</em>'.
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.CompatibilityType
   * @generated
   */
  EClass getCompatibilityType();

  /**
   * Returns the meta object for the containment reference list '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.CompatibilityType#getVersionRange <em>Version Range</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Version Range</em>'.
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.CompatibilityType#getVersionRange()
   * @see #getCompatibilityType()
   * @generated
   */
  EReference getCompatibilityType_VersionRange();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.CompatibilityType#getTargetComponent <em>Target Component</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Target Component</em>'.
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.CompatibilityType#getTargetComponent()
   * @see #getCompatibilityType()
   * @generated
   */
  EAttribute getCompatibilityType_TargetComponent();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.DocumentRoot <em>Document Root</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Document Root</em>'.
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.DocumentRoot
   * @generated
   */
  EClass getDocumentRoot();

  /**
   * Returns the meta object for the attribute list '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.DocumentRoot#getMixed <em>Mixed</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Mixed</em>'.
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.DocumentRoot#getMixed()
   * @see #getDocumentRoot()
   * @generated
   */
  EAttribute getDocumentRoot_Mixed();

  /**
   * Returns the meta object for the map '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.DocumentRoot#getXMLNSPrefixMap()
   * @see #getDocumentRoot()
   * @generated
   */
  EReference getDocumentRoot_XMLNSPrefixMap();

  /**
   * Returns the meta object for the map '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the map '<em>XSI Schema Location</em>'.
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.DocumentRoot#getXSISchemaLocation()
   * @see #getDocumentRoot()
   * @generated
   */
  EReference getDocumentRoot_XSISchemaLocation();

  /**
   * Returns the meta object for the containment reference '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.DocumentRoot#getOrchestraDoctorLocalConfiguration <em>Orchestra Doctor Local Configuration</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Orchestra Doctor Local Configuration</em>'.
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.DocumentRoot#getOrchestraDoctorLocalConfiguration()
   * @see #getDocumentRoot()
   * @generated
   */
  EReference getDocumentRoot_OrchestraDoctorLocalConfiguration();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.OrchestraDoctorLocalConfigurationType <em>Orchestra Doctor Local Configuration Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Orchestra Doctor Local Configuration Type</em>'.
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.OrchestraDoctorLocalConfigurationType
   * @generated
   */
  EClass getOrchestraDoctorLocalConfigurationType();

  /**
   * Returns the meta object for the containment reference list '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.OrchestraDoctorLocalConfigurationType#getProduct <em>Product</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Product</em>'.
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.OrchestraDoctorLocalConfigurationType#getProduct()
   * @see #getOrchestraDoctorLocalConfigurationType()
   * @generated
   */
  EReference getOrchestraDoctorLocalConfigurationType_Product();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ParameterType <em>Parameter Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Parameter Type</em>'.
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ParameterType
   * @generated
   */
  EClass getParameterType();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ParameterType#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ParameterType#getName()
   * @see #getParameterType()
   * @generated
   */
  EAttribute getParameterType_Name();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ParameterType#getType <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Type</em>'.
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ParameterType#getType()
   * @see #getParameterType()
   * @generated
   */
  EAttribute getParameterType_Type();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ParameterType#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ParameterType#getValue()
   * @see #getParameterType()
   * @generated
   */
  EAttribute getParameterType_Value();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ProductType <em>Product Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Product Type</em>'.
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ProductType
   * @generated
   */
  EClass getProductType();

  /**
   * Returns the meta object for the containment reference list '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ProductType#getVersion <em>Version</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Version</em>'.
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ProductType#getVersion()
   * @see #getProductType()
   * @generated
   */
  EReference getProductType_Version();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ProductType#getCategory <em>Category</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Category</em>'.
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ProductType#getCategory()
   * @see #getProductType()
   * @generated
   */
  EAttribute getProductType_Category();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ProductType#getKind <em>Kind</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Kind</em>'.
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ProductType#getKind()
   * @see #getProductType()
   * @generated
   */
  EAttribute getProductType_Kind();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ProductType#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ProductType#getName()
   * @see #getProductType()
   * @generated
   */
  EAttribute getProductType_Name();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionRangeType <em>Version Range Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Version Range Type</em>'.
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionRangeType
   * @generated
   */
  EClass getVersionRangeType();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionRangeType#getCompatType <em>Compat Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Compat Type</em>'.
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionRangeType#getCompatType()
   * @see #getVersionRangeType()
   * @generated
   */
  EAttribute getVersionRangeType_CompatType();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionRangeType#getRange <em>Range</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Range</em>'.
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionRangeType#getRange()
   * @see #getVersionRangeType()
   * @generated
   */
  EAttribute getVersionRangeType_Range();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionType <em>Version Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Version Type</em>'.
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionType
   * @generated
   */
  EClass getVersionType();

  /**
   * Returns the meta object for the containment reference list '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionType#getParameter <em>Parameter</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Parameter</em>'.
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionType#getParameter()
   * @see #getVersionType()
   * @generated
   */
  EReference getVersionType_Parameter();

  /**
   * Returns the meta object for the containment reference list '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionType#getCompatibility <em>Compatibility</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Compatibility</em>'.
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionType#getCompatibility()
   * @see #getVersionType()
   * @generated
   */
  EReference getVersionType_Compatibility();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionType#getConfigConnector <em>Config Connector</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Config Connector</em>'.
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionType#getConfigConnector()
   * @see #getVersionType()
   * @generated
   */
  EAttribute getVersionType_ConfigConnector();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionType#getExecName <em>Exec Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Exec Name</em>'.
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionType#getExecName()
   * @see #getVersionType()
   * @generated
   */
  EAttribute getVersionType_ExecName();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionType#getExecRelativePath <em>Exec Relative Path</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Exec Relative Path</em>'.
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionType#getExecRelativePath()
   * @see #getVersionType()
   * @generated
   */
  EAttribute getVersionType_ExecRelativePath();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionType#getIconPath <em>Icon Path</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Icon Path</em>'.
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionType#getIconPath()
   * @see #getVersionType()
   * @generated
   */
  EAttribute getVersionType_IconPath();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionType#getInstallLocation <em>Install Location</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Install Location</em>'.
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionType#getInstallLocation()
   * @see #getVersionType()
   * @generated
   */
  EAttribute getVersionType_InstallLocation();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionType#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionType#getValue()
   * @see #getVersionType()
   * @generated
   */
  EAttribute getVersionType_Value();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  ConfigurationFactory getConfigurationFactory();

  /**
   * <!-- begin-user-doc -->
   * Defines literals for the meta objects that represent
   * <ul>
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * @generated
   */
  interface Literals {
    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.CompatibilityTypeImpl <em>Compatibility Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.CompatibilityTypeImpl
     * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.ConfigurationPackageImpl#getCompatibilityType()
     * @generated
     */
    EClass COMPATIBILITY_TYPE = eINSTANCE.getCompatibilityType();

    /**
     * The meta object literal for the '<em><b>Version Range</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference COMPATIBILITY_TYPE__VERSION_RANGE = eINSTANCE.getCompatibilityType_VersionRange();

    /**
     * The meta object literal for the '<em><b>Target Component</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute COMPATIBILITY_TYPE__TARGET_COMPONENT = eINSTANCE.getCompatibilityType_TargetComponent();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.DocumentRootImpl <em>Document Root</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.DocumentRootImpl
     * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.ConfigurationPackageImpl#getDocumentRoot()
     * @generated
     */
    EClass DOCUMENT_ROOT = eINSTANCE.getDocumentRoot();

    /**
     * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute DOCUMENT_ROOT__MIXED = eINSTANCE.getDocumentRoot_Mixed();

    /**
     * The meta object literal for the '<em><b>XMLNS Prefix Map</b></em>' map feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference DOCUMENT_ROOT__XMLNS_PREFIX_MAP = eINSTANCE.getDocumentRoot_XMLNSPrefixMap();

    /**
     * The meta object literal for the '<em><b>XSI Schema Location</b></em>' map feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = eINSTANCE.getDocumentRoot_XSISchemaLocation();

    /**
     * The meta object literal for the '<em><b>Orchestra Doctor Local Configuration</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference DOCUMENT_ROOT__ORCHESTRA_DOCTOR_LOCAL_CONFIGURATION = eINSTANCE.getDocumentRoot_OrchestraDoctorLocalConfiguration();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.OrchestraDoctorLocalConfigurationTypeImpl <em>Orchestra Doctor Local Configuration Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.OrchestraDoctorLocalConfigurationTypeImpl
     * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.ConfigurationPackageImpl#getOrchestraDoctorLocalConfigurationType()
     * @generated
     */
    EClass ORCHESTRA_DOCTOR_LOCAL_CONFIGURATION_TYPE = eINSTANCE.getOrchestraDoctorLocalConfigurationType();

    /**
     * The meta object literal for the '<em><b>Product</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ORCHESTRA_DOCTOR_LOCAL_CONFIGURATION_TYPE__PRODUCT = eINSTANCE.getOrchestraDoctorLocalConfigurationType_Product();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.ParameterTypeImpl <em>Parameter Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.ParameterTypeImpl
     * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.ConfigurationPackageImpl#getParameterType()
     * @generated
     */
    EClass PARAMETER_TYPE = eINSTANCE.getParameterType();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PARAMETER_TYPE__NAME = eINSTANCE.getParameterType_Name();

    /**
     * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PARAMETER_TYPE__TYPE = eINSTANCE.getParameterType_Type();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PARAMETER_TYPE__VALUE = eINSTANCE.getParameterType_Value();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.ProductTypeImpl <em>Product Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.ProductTypeImpl
     * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.ConfigurationPackageImpl#getProductType()
     * @generated
     */
    EClass PRODUCT_TYPE = eINSTANCE.getProductType();

    /**
     * The meta object literal for the '<em><b>Version</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference PRODUCT_TYPE__VERSION = eINSTANCE.getProductType_Version();

    /**
     * The meta object literal for the '<em><b>Category</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PRODUCT_TYPE__CATEGORY = eINSTANCE.getProductType_Category();

    /**
     * The meta object literal for the '<em><b>Kind</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PRODUCT_TYPE__KIND = eINSTANCE.getProductType_Kind();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PRODUCT_TYPE__NAME = eINSTANCE.getProductType_Name();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.VersionRangeTypeImpl <em>Version Range Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.VersionRangeTypeImpl
     * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.ConfigurationPackageImpl#getVersionRangeType()
     * @generated
     */
    EClass VERSION_RANGE_TYPE = eINSTANCE.getVersionRangeType();

    /**
     * The meta object literal for the '<em><b>Compat Type</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VERSION_RANGE_TYPE__COMPAT_TYPE = eINSTANCE.getVersionRangeType_CompatType();

    /**
     * The meta object literal for the '<em><b>Range</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VERSION_RANGE_TYPE__RANGE = eINSTANCE.getVersionRangeType_Range();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.VersionTypeImpl <em>Version Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.VersionTypeImpl
     * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.ConfigurationPackageImpl#getVersionType()
     * @generated
     */
    EClass VERSION_TYPE = eINSTANCE.getVersionType();

    /**
     * The meta object literal for the '<em><b>Parameter</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VERSION_TYPE__PARAMETER = eINSTANCE.getVersionType_Parameter();

    /**
     * The meta object literal for the '<em><b>Compatibility</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference VERSION_TYPE__COMPATIBILITY = eINSTANCE.getVersionType_Compatibility();

    /**
     * The meta object literal for the '<em><b>Config Connector</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VERSION_TYPE__CONFIG_CONNECTOR = eINSTANCE.getVersionType_ConfigConnector();

    /**
     * The meta object literal for the '<em><b>Exec Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VERSION_TYPE__EXEC_NAME = eINSTANCE.getVersionType_ExecName();

    /**
     * The meta object literal for the '<em><b>Exec Relative Path</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VERSION_TYPE__EXEC_RELATIVE_PATH = eINSTANCE.getVersionType_ExecRelativePath();

    /**
     * The meta object literal for the '<em><b>Icon Path</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VERSION_TYPE__ICON_PATH = eINSTANCE.getVersionType_IconPath();

    /**
     * The meta object literal for the '<em><b>Install Location</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VERSION_TYPE__INSTALL_LOCATION = eINSTANCE.getVersionType_InstallLocation();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VERSION_TYPE__VALUE = eINSTANCE.getVersionType_Value();

  }

} //ConfigurationPackage
