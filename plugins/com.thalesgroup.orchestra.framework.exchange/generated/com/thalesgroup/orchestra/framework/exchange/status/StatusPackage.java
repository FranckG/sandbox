/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.exchange.status;

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
 * @see com.thalesgroup.orchestra.framework.exchange.status.StatusFactory
 * @model kind="package"
 * @generated
 */
public interface StatusPackage extends EPackage {
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "status";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://www.thalesgroup.com/orchestra/framework/4_0_25/Status";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "status";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  StatusPackage eINSTANCE = com.thalesgroup.orchestra.framework.exchange.status.impl.StatusPackageImpl.init();

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.exchange.status.impl.DocumentRootImpl <em>Document Root</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.exchange.status.impl.DocumentRootImpl
   * @see com.thalesgroup.orchestra.framework.exchange.status.impl.StatusPackageImpl#getDocumentRoot()
   * @generated
   */
  int DOCUMENT_ROOT = 0;

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
   * The feature id for the '<em><b>Status Definition</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT_ROOT__STATUS_DEFINITION = 3;

  /**
   * The number of structural features of the '<em>Document Root</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT_ROOT_FEATURE_COUNT = 4;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.exchange.status.impl.StatusImpl <em>Status</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.exchange.status.impl.StatusImpl
   * @see com.thalesgroup.orchestra.framework.exchange.status.impl.StatusPackageImpl#getStatus()
   * @generated
   */
  int STATUS = 1;

  /**
   * The feature id for the '<em><b>Group</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATUS__GROUP = 0;

  /**
   * The feature id for the '<em><b>Status</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATUS__STATUS = 1;

  /**
   * The feature id for the '<em><b>Code</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATUS__CODE = 2;

  /**
   * The feature id for the '<em><b>Export File Path</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATUS__EXPORT_FILE_PATH = 3;

  /**
   * The feature id for the '<em><b>Message</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATUS__MESSAGE = 4;

  /**
   * The feature id for the '<em><b>Severity</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATUS__SEVERITY = 5;

  /**
   * The feature id for the '<em><b>Uri</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATUS__URI = 6;

  /**
   * The number of structural features of the '<em>Status</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATUS_FEATURE_COUNT = 7;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.exchange.status.impl.StatusDefinitionImpl <em>Definition</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.exchange.status.impl.StatusDefinitionImpl
   * @see com.thalesgroup.orchestra.framework.exchange.status.impl.StatusPackageImpl#getStatusDefinition()
   * @generated
   */
  int STATUS_DEFINITION = 2;

  /**
   * The feature id for the '<em><b>Status</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATUS_DEFINITION__STATUS = 0;

  /**
   * The number of structural features of the '<em>Definition</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATUS_DEFINITION_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.exchange.status.SeverityType <em>Severity Type</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.exchange.status.SeverityType
   * @see com.thalesgroup.orchestra.framework.exchange.status.impl.StatusPackageImpl#getSeverityType()
   * @generated
   */
  int SEVERITY_TYPE = 3;

  /**
   * The meta object id for the '<em>Severity Type Object</em>' data type.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.exchange.status.SeverityType
   * @see com.thalesgroup.orchestra.framework.exchange.status.impl.StatusPackageImpl#getSeverityTypeObject()
   * @generated
   */
  int SEVERITY_TYPE_OBJECT = 4;


  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.exchange.status.DocumentRoot <em>Document Root</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Document Root</em>'.
   * @see com.thalesgroup.orchestra.framework.exchange.status.DocumentRoot
   * @generated
   */
  EClass getDocumentRoot();

  /**
   * Returns the meta object for the attribute list '{@link com.thalesgroup.orchestra.framework.exchange.status.DocumentRoot#getMixed <em>Mixed</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Mixed</em>'.
   * @see com.thalesgroup.orchestra.framework.exchange.status.DocumentRoot#getMixed()
   * @see #getDocumentRoot()
   * @generated
   */
  EAttribute getDocumentRoot_Mixed();

  /**
   * Returns the meta object for the map '{@link com.thalesgroup.orchestra.framework.exchange.status.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
   * @see com.thalesgroup.orchestra.framework.exchange.status.DocumentRoot#getXMLNSPrefixMap()
   * @see #getDocumentRoot()
   * @generated
   */
  EReference getDocumentRoot_XMLNSPrefixMap();

  /**
   * Returns the meta object for the map '{@link com.thalesgroup.orchestra.framework.exchange.status.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the map '<em>XSI Schema Location</em>'.
   * @see com.thalesgroup.orchestra.framework.exchange.status.DocumentRoot#getXSISchemaLocation()
   * @see #getDocumentRoot()
   * @generated
   */
  EReference getDocumentRoot_XSISchemaLocation();

  /**
   * Returns the meta object for the containment reference '{@link com.thalesgroup.orchestra.framework.exchange.status.DocumentRoot#getStatusDefinition <em>Status Definition</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Status Definition</em>'.
   * @see com.thalesgroup.orchestra.framework.exchange.status.DocumentRoot#getStatusDefinition()
   * @see #getDocumentRoot()
   * @generated
   */
  EReference getDocumentRoot_StatusDefinition();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.exchange.status.Status <em>Status</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Status</em>'.
   * @see com.thalesgroup.orchestra.framework.exchange.status.Status
   * @generated
   */
  EClass getStatus();

  /**
   * Returns the meta object for the attribute list '{@link com.thalesgroup.orchestra.framework.exchange.status.Status#getGroup <em>Group</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Group</em>'.
   * @see com.thalesgroup.orchestra.framework.exchange.status.Status#getGroup()
   * @see #getStatus()
   * @generated
   */
  EAttribute getStatus_Group();

  /**
   * Returns the meta object for the containment reference list '{@link com.thalesgroup.orchestra.framework.exchange.status.Status#getStatus <em>Status</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Status</em>'.
   * @see com.thalesgroup.orchestra.framework.exchange.status.Status#getStatus()
   * @see #getStatus()
   * @generated
   */
  EReference getStatus_Status();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.exchange.status.Status#getCode <em>Code</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Code</em>'.
   * @see com.thalesgroup.orchestra.framework.exchange.status.Status#getCode()
   * @see #getStatus()
   * @generated
   */
  EAttribute getStatus_Code();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.exchange.status.Status#getExportFilePath <em>Export File Path</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Export File Path</em>'.
   * @see com.thalesgroup.orchestra.framework.exchange.status.Status#getExportFilePath()
   * @see #getStatus()
   * @generated
   */
  EAttribute getStatus_ExportFilePath();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.exchange.status.Status#getMessage <em>Message</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Message</em>'.
   * @see com.thalesgroup.orchestra.framework.exchange.status.Status#getMessage()
   * @see #getStatus()
   * @generated
   */
  EAttribute getStatus_Message();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.exchange.status.Status#getSeverity <em>Severity</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Severity</em>'.
   * @see com.thalesgroup.orchestra.framework.exchange.status.Status#getSeverity()
   * @see #getStatus()
   * @generated
   */
  EAttribute getStatus_Severity();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.exchange.status.Status#getUri <em>Uri</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Uri</em>'.
   * @see com.thalesgroup.orchestra.framework.exchange.status.Status#getUri()
   * @see #getStatus()
   * @generated
   */
  EAttribute getStatus_Uri();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.exchange.status.StatusDefinition <em>Definition</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Definition</em>'.
   * @see com.thalesgroup.orchestra.framework.exchange.status.StatusDefinition
   * @generated
   */
  EClass getStatusDefinition();

  /**
   * Returns the meta object for the containment reference '{@link com.thalesgroup.orchestra.framework.exchange.status.StatusDefinition#getStatus <em>Status</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Status</em>'.
   * @see com.thalesgroup.orchestra.framework.exchange.status.StatusDefinition#getStatus()
   * @see #getStatusDefinition()
   * @generated
   */
  EReference getStatusDefinition_Status();

  /**
   * Returns the meta object for enum '{@link com.thalesgroup.orchestra.framework.exchange.status.SeverityType <em>Severity Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Severity Type</em>'.
   * @see com.thalesgroup.orchestra.framework.exchange.status.SeverityType
   * @generated
   */
  EEnum getSeverityType();

  /**
   * Returns the meta object for data type '{@link com.thalesgroup.orchestra.framework.exchange.status.SeverityType <em>Severity Type Object</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for data type '<em>Severity Type Object</em>'.
   * @see com.thalesgroup.orchestra.framework.exchange.status.SeverityType
   * @model instanceClass="com.thalesgroup.orchestra.framework.exchange.status.SeverityType"
   *        extendedMetaData="name='severity_._type:Object' baseType='severity_._type'"
   * @generated
   */
  EDataType getSeverityTypeObject();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  StatusFactory getStatusFactory();

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
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.exchange.status.impl.DocumentRootImpl <em>Document Root</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.exchange.status.impl.DocumentRootImpl
     * @see com.thalesgroup.orchestra.framework.exchange.status.impl.StatusPackageImpl#getDocumentRoot()
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
     * The meta object literal for the '<em><b>Status Definition</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference DOCUMENT_ROOT__STATUS_DEFINITION = eINSTANCE.getDocumentRoot_StatusDefinition();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.exchange.status.impl.StatusImpl <em>Status</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.exchange.status.impl.StatusImpl
     * @see com.thalesgroup.orchestra.framework.exchange.status.impl.StatusPackageImpl#getStatus()
     * @generated
     */
    EClass STATUS = eINSTANCE.getStatus();

    /**
     * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute STATUS__GROUP = eINSTANCE.getStatus_Group();

    /**
     * The meta object literal for the '<em><b>Status</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference STATUS__STATUS = eINSTANCE.getStatus_Status();

    /**
     * The meta object literal for the '<em><b>Code</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute STATUS__CODE = eINSTANCE.getStatus_Code();

    /**
     * The meta object literal for the '<em><b>Export File Path</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute STATUS__EXPORT_FILE_PATH = eINSTANCE.getStatus_ExportFilePath();

    /**
     * The meta object literal for the '<em><b>Message</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute STATUS__MESSAGE = eINSTANCE.getStatus_Message();

    /**
     * The meta object literal for the '<em><b>Severity</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute STATUS__SEVERITY = eINSTANCE.getStatus_Severity();

    /**
     * The meta object literal for the '<em><b>Uri</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute STATUS__URI = eINSTANCE.getStatus_Uri();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.exchange.status.impl.StatusDefinitionImpl <em>Definition</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.exchange.status.impl.StatusDefinitionImpl
     * @see com.thalesgroup.orchestra.framework.exchange.status.impl.StatusPackageImpl#getStatusDefinition()
     * @generated
     */
    EClass STATUS_DEFINITION = eINSTANCE.getStatusDefinition();

    /**
     * The meta object literal for the '<em><b>Status</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference STATUS_DEFINITION__STATUS = eINSTANCE.getStatusDefinition_Status();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.exchange.status.SeverityType <em>Severity Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.exchange.status.SeverityType
     * @see com.thalesgroup.orchestra.framework.exchange.status.impl.StatusPackageImpl#getSeverityType()
     * @generated
     */
    EEnum SEVERITY_TYPE = eINSTANCE.getSeverityType();

    /**
     * The meta object literal for the '<em>Severity Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.exchange.status.SeverityType
     * @see com.thalesgroup.orchestra.framework.exchange.status.impl.StatusPackageImpl#getSeverityTypeObject()
     * @generated
     */
    EDataType SEVERITY_TYPE_OBJECT = eINSTANCE.getSeverityTypeObject();

  }

} //StatusPackage
