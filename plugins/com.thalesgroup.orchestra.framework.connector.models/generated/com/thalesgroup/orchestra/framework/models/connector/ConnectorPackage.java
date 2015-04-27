/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.models.connector;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
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
 * @see com.thalesgroup.orchestra.framework.models.connector.ConnectorFactory
 * @model kind="package"
 * @generated
 */
public interface ConnectorPackage extends EPackage {
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "connector";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://www.thalesgroup.com/orchestra/framework/4_0_24/Connector";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "connector";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  ConnectorPackage eINSTANCE = com.thalesgroup.orchestra.framework.models.connector.impl.ConnectorPackageImpl.init();

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.models.connector.impl.ConnectorImpl <em>Connector</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.models.connector.impl.ConnectorImpl
   * @see com.thalesgroup.orchestra.framework.models.connector.impl.ConnectorPackageImpl#getConnector()
   * @generated
   */
  int CONNECTOR = 0;

  /**
   * The feature id for the '<em><b>Type</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONNECTOR__TYPE = 0;

  /**
   * The feature id for the '<em><b>Unsupported Service</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONNECTOR__UNSUPPORTED_SERVICE = 1;

  /**
   * The feature id for the '<em><b>Connector Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONNECTOR__CONNECTOR_ID = 2;

  /**
   * The feature id for the '<em><b>Metadata Resolver</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONNECTOR__METADATA_RESOLVER = 3;

  /**
   * The feature id for the '<em><b>Prog Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONNECTOR__PROG_ID = 4;

  /**
   * The feature id for the '<em><b>Keep Open</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONNECTOR__KEEP_OPEN = 5;

  /**
   * The feature id for the '<em><b>Launch Arguments</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONNECTOR__LAUNCH_ARGUMENTS = 6;

  /**
   * The number of structural features of the '<em>Connector</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONNECTOR_FEATURE_COUNT = 7;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.models.connector.impl.ConnectorDefinitionImpl <em>Definition</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.models.connector.impl.ConnectorDefinitionImpl
   * @see com.thalesgroup.orchestra.framework.models.connector.impl.ConnectorPackageImpl#getConnectorDefinition()
   * @generated
   */
  int CONNECTOR_DEFINITION = 1;

  /**
   * The feature id for the '<em><b>Connector</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONNECTOR_DEFINITION__CONNECTOR = 0;

  /**
   * The number of structural features of the '<em>Definition</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONNECTOR_DEFINITION_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.models.connector.impl.DocumentRootImpl <em>Document Root</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.models.connector.impl.DocumentRootImpl
   * @see com.thalesgroup.orchestra.framework.models.connector.impl.ConnectorPackageImpl#getDocumentRoot()
   * @generated
   */
  int DOCUMENT_ROOT = 2;

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
   * The feature id for the '<em><b>Connector Definition</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT_ROOT__CONNECTOR_DEFINITION = 3;

  /**
   * The number of structural features of the '<em>Document Root</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT_ROOT_FEATURE_COUNT = 4;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.models.connector.impl.ServiceImpl <em>Service</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.models.connector.impl.ServiceImpl
   * @see com.thalesgroup.orchestra.framework.models.connector.impl.ConnectorPackageImpl#getService()
   * @generated
   */
  int SERVICE = 3;

  /**
   * The feature id for the '<em><b>Service Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SERVICE__SERVICE_NAME = 0;

  /**
   * The number of structural features of the '<em>Service</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SERVICE_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.models.connector.impl.TypeImpl <em>Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.models.connector.impl.TypeImpl
   * @see com.thalesgroup.orchestra.framework.models.connector.impl.ConnectorPackageImpl#getType()
   * @generated
   */
  int TYPE = 4;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE__NAME = 0;

  /**
   * The feature id for the '<em><b>Timeout</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE__TIMEOUT = 1;

  /**
   * The number of structural features of the '<em>Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TYPE_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.models.connector.impl.KeepOpenImpl <em>Keep Open</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.models.connector.impl.KeepOpenImpl
   * @see com.thalesgroup.orchestra.framework.models.connector.impl.ConnectorPackageImpl#getKeepOpen()
   * @generated
   */
  int KEEP_OPEN = 5;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int KEEP_OPEN__NAME = 0;

  /**
   * The number of structural features of the '<em>Keep Open</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int KEEP_OPEN_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.models.connector.impl.LaunchArgumentsImpl <em>Launch Arguments</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.models.connector.impl.LaunchArgumentsImpl
   * @see com.thalesgroup.orchestra.framework.models.connector.impl.ConnectorPackageImpl#getLaunchArguments()
   * @generated
   */
  int LAUNCH_ARGUMENTS = 6;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LAUNCH_ARGUMENTS__NAME = 0;

  /**
   * The number of structural features of the '<em>Launch Arguments</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LAUNCH_ARGUMENTS_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.models.connector.ServiceNameType <em>Service Name Type</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.models.connector.ServiceNameType
   * @see com.thalesgroup.orchestra.framework.models.connector.impl.ConnectorPackageImpl#getServiceNameType()
   * @generated
   */
  int SERVICE_NAME_TYPE = 7;

  /**
   * The meta object id for the '<em>Service Name Type Object</em>' data type.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.models.connector.ServiceNameType
   * @see com.thalesgroup.orchestra.framework.models.connector.impl.ConnectorPackageImpl#getServiceNameTypeObject()
   * @generated
   */
  int SERVICE_NAME_TYPE_OBJECT = 8;


  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.models.connector.Connector <em>Connector</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Connector</em>'.
   * @see com.thalesgroup.orchestra.framework.models.connector.Connector
   * @generated
   */
  EClass getConnector();

  /**
   * Returns the meta object for the containment reference list '{@link com.thalesgroup.orchestra.framework.models.connector.Connector#getUnsupportedService <em>Unsupported Service</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Unsupported Service</em>'.
   * @see com.thalesgroup.orchestra.framework.models.connector.Connector#getUnsupportedService()
   * @see #getConnector()
   * @generated
   */
  EReference getConnector_UnsupportedService();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.models.connector.Connector#getConnectorId <em>Connector Id</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Connector Id</em>'.
   * @see com.thalesgroup.orchestra.framework.models.connector.Connector#getConnectorId()
   * @see #getConnector()
   * @generated
   */
  EAttribute getConnector_ConnectorId();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.models.connector.Connector#isMetadataResolver <em>Metadata Resolver</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Metadata Resolver</em>'.
   * @see com.thalesgroup.orchestra.framework.models.connector.Connector#isMetadataResolver()
   * @see #getConnector()
   * @generated
   */
  EAttribute getConnector_MetadataResolver();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.models.connector.Connector#getProgId <em>Prog Id</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Prog Id</em>'.
   * @see com.thalesgroup.orchestra.framework.models.connector.Connector#getProgId()
   * @see #getConnector()
   * @generated
   */
  EAttribute getConnector_ProgId();

  /**
   * Returns the meta object for the containment reference '{@link com.thalesgroup.orchestra.framework.models.connector.Connector#getKeepOpen <em>Keep Open</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Keep Open</em>'.
   * @see com.thalesgroup.orchestra.framework.models.connector.Connector#getKeepOpen()
   * @see #getConnector()
   * @generated
   */
  EReference getConnector_KeepOpen();

  /**
   * Returns the meta object for the containment reference '{@link com.thalesgroup.orchestra.framework.models.connector.Connector#getLaunchArguments <em>Launch Arguments</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Launch Arguments</em>'.
   * @see com.thalesgroup.orchestra.framework.models.connector.Connector#getLaunchArguments()
   * @see #getConnector()
   * @generated
   */
  EReference getConnector_LaunchArguments();

  /**
   * Returns the meta object for the containment reference list '{@link com.thalesgroup.orchestra.framework.models.connector.Connector#getType <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Type</em>'.
   * @see com.thalesgroup.orchestra.framework.models.connector.Connector#getType()
   * @see #getConnector()
   * @generated
   */
  EReference getConnector_Type();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.models.connector.ConnectorDefinition <em>Definition</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Definition</em>'.
   * @see com.thalesgroup.orchestra.framework.models.connector.ConnectorDefinition
   * @generated
   */
  EClass getConnectorDefinition();

  /**
   * Returns the meta object for the containment reference '{@link com.thalesgroup.orchestra.framework.models.connector.ConnectorDefinition#getConnector <em>Connector</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Connector</em>'.
   * @see com.thalesgroup.orchestra.framework.models.connector.ConnectorDefinition#getConnector()
   * @see #getConnectorDefinition()
   * @generated
   */
  EReference getConnectorDefinition_Connector();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.models.connector.DocumentRoot <em>Document Root</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Document Root</em>'.
   * @see com.thalesgroup.orchestra.framework.models.connector.DocumentRoot
   * @generated
   */
  EClass getDocumentRoot();

  /**
   * Returns the meta object for the attribute list '{@link com.thalesgroup.orchestra.framework.models.connector.DocumentRoot#getMixed <em>Mixed</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Mixed</em>'.
   * @see com.thalesgroup.orchestra.framework.models.connector.DocumentRoot#getMixed()
   * @see #getDocumentRoot()
   * @generated
   */
  EAttribute getDocumentRoot_Mixed();

  /**
   * Returns the meta object for the map '{@link com.thalesgroup.orchestra.framework.models.connector.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
   * @see com.thalesgroup.orchestra.framework.models.connector.DocumentRoot#getXMLNSPrefixMap()
   * @see #getDocumentRoot()
   * @generated
   */
  EReference getDocumentRoot_XMLNSPrefixMap();

  /**
   * Returns the meta object for the map '{@link com.thalesgroup.orchestra.framework.models.connector.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the map '<em>XSI Schema Location</em>'.
   * @see com.thalesgroup.orchestra.framework.models.connector.DocumentRoot#getXSISchemaLocation()
   * @see #getDocumentRoot()
   * @generated
   */
  EReference getDocumentRoot_XSISchemaLocation();

  /**
   * Returns the meta object for the containment reference '{@link com.thalesgroup.orchestra.framework.models.connector.DocumentRoot#getConnectorDefinition <em>Connector Definition</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Connector Definition</em>'.
   * @see com.thalesgroup.orchestra.framework.models.connector.DocumentRoot#getConnectorDefinition()
   * @see #getDocumentRoot()
   * @generated
   */
  EReference getDocumentRoot_ConnectorDefinition();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.models.connector.Service <em>Service</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Service</em>'.
   * @see com.thalesgroup.orchestra.framework.models.connector.Service
   * @generated
   */
  EClass getService();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.models.connector.Service#getServiceName <em>Service Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Service Name</em>'.
   * @see com.thalesgroup.orchestra.framework.models.connector.Service#getServiceName()
   * @see #getService()
   * @generated
   */
  EAttribute getService_ServiceName();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.models.connector.Type <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Type</em>'.
   * @see com.thalesgroup.orchestra.framework.models.connector.Type
   * @generated
   */
  EClass getType();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.models.connector.Type#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see com.thalesgroup.orchestra.framework.models.connector.Type#getName()
   * @see #getType()
   * @generated
   */
  EAttribute getType_Name();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.models.connector.Type#getTimeout <em>Timeout</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Timeout</em>'.
   * @see com.thalesgroup.orchestra.framework.models.connector.Type#getTimeout()
   * @see #getType()
   * @generated
   */
  EAttribute getType_Timeout();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.models.connector.KeepOpen <em>Keep Open</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Keep Open</em>'.
   * @see com.thalesgroup.orchestra.framework.models.connector.KeepOpen
   * @generated
   */
  EClass getKeepOpen();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.models.connector.KeepOpen#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see com.thalesgroup.orchestra.framework.models.connector.KeepOpen#getName()
   * @see #getKeepOpen()
   * @generated
   */
  EAttribute getKeepOpen_Name();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.models.connector.LaunchArguments <em>Launch Arguments</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Launch Arguments</em>'.
   * @see com.thalesgroup.orchestra.framework.models.connector.LaunchArguments
   * @generated
   */
  EClass getLaunchArguments();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.models.connector.LaunchArguments#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see com.thalesgroup.orchestra.framework.models.connector.LaunchArguments#getName()
   * @see #getLaunchArguments()
   * @generated
   */
  EAttribute getLaunchArguments_Name();

  /**
   * Returns the meta object for enum '{@link com.thalesgroup.orchestra.framework.models.connector.ServiceNameType <em>Service Name Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Service Name Type</em>'.
   * @see com.thalesgroup.orchestra.framework.models.connector.ServiceNameType
   * @generated
   */
  EEnum getServiceNameType();

  /**
   * Returns the meta object for data type '{@link com.thalesgroup.orchestra.framework.models.connector.ServiceNameType <em>Service Name Type Object</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for data type '<em>Service Name Type Object</em>'.
   * @see com.thalesgroup.orchestra.framework.models.connector.ServiceNameType
   * @model instanceClass="com.thalesgroup.orchestra.framework.models.connector.ServiceNameType"
   *        extendedMetaData="name='serviceName_._type:Object' baseType='serviceName_._type'"
   * @generated
   */
  EDataType getServiceNameTypeObject();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  ConnectorFactory getConnectorFactory();

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
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.models.connector.impl.ConnectorImpl <em>Connector</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.models.connector.impl.ConnectorImpl
     * @see com.thalesgroup.orchestra.framework.models.connector.impl.ConnectorPackageImpl#getConnector()
     * @generated
     */
    EClass CONNECTOR = eINSTANCE.getConnector();

    /**
     * The meta object literal for the '<em><b>Unsupported Service</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CONNECTOR__UNSUPPORTED_SERVICE = eINSTANCE.getConnector_UnsupportedService();

    /**
     * The meta object literal for the '<em><b>Connector Id</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CONNECTOR__CONNECTOR_ID = eINSTANCE.getConnector_ConnectorId();

    /**
     * The meta object literal for the '<em><b>Metadata Resolver</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CONNECTOR__METADATA_RESOLVER = eINSTANCE.getConnector_MetadataResolver();

    /**
     * The meta object literal for the '<em><b>Prog Id</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CONNECTOR__PROG_ID = eINSTANCE.getConnector_ProgId();

    /**
     * The meta object literal for the '<em><b>Keep Open</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CONNECTOR__KEEP_OPEN = eINSTANCE.getConnector_KeepOpen();

    /**
     * The meta object literal for the '<em><b>Launch Arguments</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CONNECTOR__LAUNCH_ARGUMENTS = eINSTANCE.getConnector_LaunchArguments();

    /**
     * The meta object literal for the '<em><b>Type</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CONNECTOR__TYPE = eINSTANCE.getConnector_Type();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.models.connector.impl.ConnectorDefinitionImpl <em>Definition</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.models.connector.impl.ConnectorDefinitionImpl
     * @see com.thalesgroup.orchestra.framework.models.connector.impl.ConnectorPackageImpl#getConnectorDefinition()
     * @generated
     */
    EClass CONNECTOR_DEFINITION = eINSTANCE.getConnectorDefinition();

    /**
     * The meta object literal for the '<em><b>Connector</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CONNECTOR_DEFINITION__CONNECTOR = eINSTANCE.getConnectorDefinition_Connector();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.models.connector.impl.DocumentRootImpl <em>Document Root</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.models.connector.impl.DocumentRootImpl
     * @see com.thalesgroup.orchestra.framework.models.connector.impl.ConnectorPackageImpl#getDocumentRoot()
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
     * The meta object literal for the '<em><b>Connector Definition</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference DOCUMENT_ROOT__CONNECTOR_DEFINITION = eINSTANCE.getDocumentRoot_ConnectorDefinition();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.models.connector.impl.ServiceImpl <em>Service</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.models.connector.impl.ServiceImpl
     * @see com.thalesgroup.orchestra.framework.models.connector.impl.ConnectorPackageImpl#getService()
     * @generated
     */
    EClass SERVICE = eINSTANCE.getService();

    /**
     * The meta object literal for the '<em><b>Service Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute SERVICE__SERVICE_NAME = eINSTANCE.getService_ServiceName();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.models.connector.impl.TypeImpl <em>Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.models.connector.impl.TypeImpl
     * @see com.thalesgroup.orchestra.framework.models.connector.impl.ConnectorPackageImpl#getType()
     * @generated
     */
    EClass TYPE = eINSTANCE.getType();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TYPE__NAME = eINSTANCE.getType_Name();

    /**
     * The meta object literal for the '<em><b>Timeout</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TYPE__TIMEOUT = eINSTANCE.getType_Timeout();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.models.connector.impl.KeepOpenImpl <em>Keep Open</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.models.connector.impl.KeepOpenImpl
     * @see com.thalesgroup.orchestra.framework.models.connector.impl.ConnectorPackageImpl#getKeepOpen()
     * @generated
     */
    EClass KEEP_OPEN = eINSTANCE.getKeepOpen();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute KEEP_OPEN__NAME = eINSTANCE.getKeepOpen_Name();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.models.connector.impl.LaunchArgumentsImpl <em>Launch Arguments</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.models.connector.impl.LaunchArgumentsImpl
     * @see com.thalesgroup.orchestra.framework.models.connector.impl.ConnectorPackageImpl#getLaunchArguments()
     * @generated
     */
    EClass LAUNCH_ARGUMENTS = eINSTANCE.getLaunchArguments();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute LAUNCH_ARGUMENTS__NAME = eINSTANCE.getLaunchArguments_Name();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.models.connector.ServiceNameType <em>Service Name Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.models.connector.ServiceNameType
     * @see com.thalesgroup.orchestra.framework.models.connector.impl.ConnectorPackageImpl#getServiceNameType()
     * @generated
     */
    EEnum SERVICE_NAME_TYPE = eINSTANCE.getServiceNameType();

    /**
     * The meta object literal for the '<em>Service Name Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.models.connector.ServiceNameType
     * @see com.thalesgroup.orchestra.framework.models.connector.impl.ConnectorPackageImpl#getServiceNameTypeObject()
     * @generated
     */
    EDataType SERVICE_NAME_TYPE_OBJECT = eINSTANCE.getServiceNameTypeObject();

  }

} //ConnectorPackage
