/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.models.context;

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
 * @see com.thalesgroup.orchestra.framework.models.context.ContextFactory
 * @model kind="package"
 * @generated
 */
public interface ContextPackage extends EPackage {
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "context";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://www.thalesgroup.com/orchestra/framework/4_1_2/Context";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "context";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  ContextPackage eINSTANCE = com.thalesgroup.orchestra.framework.models.context.impl.ContextPackageImpl.init();

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.models.context.impl.ArtifactImpl <em>Artifact</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.models.context.impl.ArtifactImpl
   * @see com.thalesgroup.orchestra.framework.models.context.impl.ContextPackageImpl#getArtifact()
   * @generated
   */
  int ARTIFACT = 0;

  /**
   * The feature id for the '<em><b>Group</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ARTIFACT__GROUP = 0;

  /**
   * The feature id for the '<em><b>Environment Properties</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ARTIFACT__ENVIRONMENT_PROPERTIES = 1;

  /**
   * The feature id for the '<em><b>Environment Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ARTIFACT__ENVIRONMENT_ID = 2;

  /**
   * The feature id for the '<em><b>Root Physical Path</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ARTIFACT__ROOT_PHYSICAL_PATH = 3;

  /**
   * The feature id for the '<em><b>Uri</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ARTIFACT__URI = 4;

  /**
   * The number of structural features of the '<em>Artifact</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ARTIFACT_FEATURE_COUNT = 5;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.models.context.impl.ContextImpl <em>Context</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.models.context.impl.ContextImpl
   * @see com.thalesgroup.orchestra.framework.models.context.impl.ContextPackageImpl#getContext()
   * @generated
   */
  int CONTEXT = 1;

  /**
   * The feature id for the '<em><b>Group</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTEXT__GROUP = 0;

  /**
   * The feature id for the '<em><b>Artifact</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTEXT__ARTIFACT = 1;

  /**
   * The feature id for the '<em><b>Export File Path</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTEXT__EXPORT_FILE_PATH = 2;

  /**
   * The feature id for the '<em><b>Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTEXT__TYPE = 3;

  /**
   * The feature id for the '<em><b>Keep Open</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTEXT__KEEP_OPEN = 4;

  /**
   * The feature id for the '<em><b>Launch Arguments</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTEXT__LAUNCH_ARGUMENTS = 5;

  /**
   * The number of structural features of the '<em>Context</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTEXT_FEATURE_COUNT = 6;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.models.context.impl.ContextDefinitionImpl <em>Definition</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.models.context.impl.ContextDefinitionImpl
   * @see com.thalesgroup.orchestra.framework.models.context.impl.ContextPackageImpl#getContextDefinition()
   * @generated
   */
  int CONTEXT_DEFINITION = 2;

  /**
   * The feature id for the '<em><b>Context</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTEXT_DEFINITION__CONTEXT = 0;

  /**
   * The number of structural features of the '<em>Definition</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTEXT_DEFINITION_FEATURE_COUNT = 1;


  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.models.context.impl.DocumentRootImpl <em>Document Root</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.models.context.impl.DocumentRootImpl
   * @see com.thalesgroup.orchestra.framework.models.context.impl.ContextPackageImpl#getDocumentRoot()
   * @generated
   */
  int DOCUMENT_ROOT = 3;

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
   * The feature id for the '<em><b>Context Definition</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT_ROOT__CONTEXT_DEFINITION = 3;

  /**
   * The number of structural features of the '<em>Document Root</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT_ROOT_FEATURE_COUNT = 4;


  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.models.context.impl.EnvironmentPropertyImpl <em>Environment Property</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.models.context.impl.EnvironmentPropertyImpl
   * @see com.thalesgroup.orchestra.framework.models.context.impl.ContextPackageImpl#getEnvironmentProperty()
   * @generated
   */
  int ENVIRONMENT_PROPERTY = 4;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENVIRONMENT_PROPERTY__NAME = 0;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENVIRONMENT_PROPERTY__VALUE = 1;

  /**
   * The number of structural features of the '<em>Environment Property</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENVIRONMENT_PROPERTY_FEATURE_COUNT = 2;


  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.models.context.Artifact <em>Artifact</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Artifact</em>'.
   * @see com.thalesgroup.orchestra.framework.models.context.Artifact
   * @generated
   */
  EClass getArtifact();

  /**
   * Returns the meta object for the attribute list '{@link com.thalesgroup.orchestra.framework.models.context.Artifact#getGroup <em>Group</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Group</em>'.
   * @see com.thalesgroup.orchestra.framework.models.context.Artifact#getGroup()
   * @see #getArtifact()
   * @generated
   */
  EAttribute getArtifact_Group();

  /**
   * Returns the meta object for the containment reference list '{@link com.thalesgroup.orchestra.framework.models.context.Artifact#getEnvironmentProperties <em>Environment Properties</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Environment Properties</em>'.
   * @see com.thalesgroup.orchestra.framework.models.context.Artifact#getEnvironmentProperties()
   * @see #getArtifact()
   * @generated
   */
  EReference getArtifact_EnvironmentProperties();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.models.context.Artifact#getEnvironmentId <em>Environment Id</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Environment Id</em>'.
   * @see com.thalesgroup.orchestra.framework.models.context.Artifact#getEnvironmentId()
   * @see #getArtifact()
   * @generated
   */
  EAttribute getArtifact_EnvironmentId();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.models.context.Artifact#getRootPhysicalPath <em>Root Physical Path</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Root Physical Path</em>'.
   * @see com.thalesgroup.orchestra.framework.models.context.Artifact#getRootPhysicalPath()
   * @see #getArtifact()
   * @generated
   */
  EAttribute getArtifact_RootPhysicalPath();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.models.context.Artifact#getUri <em>Uri</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Uri</em>'.
   * @see com.thalesgroup.orchestra.framework.models.context.Artifact#getUri()
   * @see #getArtifact()
   * @generated
   */
  EAttribute getArtifact_Uri();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.models.context.Context <em>Context</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Context</em>'.
   * @see com.thalesgroup.orchestra.framework.models.context.Context
   * @generated
   */
  EClass getContext();

  /**
   * Returns the meta object for the attribute list '{@link com.thalesgroup.orchestra.framework.models.context.Context#getGroup <em>Group</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Group</em>'.
   * @see com.thalesgroup.orchestra.framework.models.context.Context#getGroup()
   * @see #getContext()
   * @generated
   */
  EAttribute getContext_Group();

  /**
   * Returns the meta object for the containment reference list '{@link com.thalesgroup.orchestra.framework.models.context.Context#getArtifact <em>Artifact</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Artifact</em>'.
   * @see com.thalesgroup.orchestra.framework.models.context.Context#getArtifact()
   * @see #getContext()
   * @generated
   */
  EReference getContext_Artifact();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.models.context.Context#getExportFilePath <em>Export File Path</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Export File Path</em>'.
   * @see com.thalesgroup.orchestra.framework.models.context.Context#getExportFilePath()
   * @see #getContext()
   * @generated
   */
  EAttribute getContext_ExportFilePath();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.models.context.Context#getType <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Type</em>'.
   * @see com.thalesgroup.orchestra.framework.models.context.Context#getType()
   * @see #getContext()
   * @generated
   */
  EAttribute getContext_Type();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.models.context.Context#isKeepOpen <em>Keep Open</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Keep Open</em>'.
   * @see com.thalesgroup.orchestra.framework.models.context.Context#isKeepOpen()
   * @see #getContext()
   * @generated
   */
  EAttribute getContext_KeepOpen();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.models.context.Context#getLaunchArguments <em>Launch Arguments</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Launch Arguments</em>'.
   * @see com.thalesgroup.orchestra.framework.models.context.Context#getLaunchArguments()
   * @see #getContext()
   * @generated
   */
  EAttribute getContext_LaunchArguments();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.models.context.ContextDefinition <em>Definition</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Definition</em>'.
   * @see com.thalesgroup.orchestra.framework.models.context.ContextDefinition
   * @generated
   */
  EClass getContextDefinition();

  /**
   * Returns the meta object for the containment reference '{@link com.thalesgroup.orchestra.framework.models.context.ContextDefinition#getContext <em>Context</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Context</em>'.
   * @see com.thalesgroup.orchestra.framework.models.context.ContextDefinition#getContext()
   * @see #getContextDefinition()
   * @generated
   */
  EReference getContextDefinition_Context();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.models.context.DocumentRoot <em>Document Root</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Document Root</em>'.
   * @see com.thalesgroup.orchestra.framework.models.context.DocumentRoot
   * @generated
   */
  EClass getDocumentRoot();

  /**
   * Returns the meta object for the attribute list '{@link com.thalesgroup.orchestra.framework.models.context.DocumentRoot#getMixed <em>Mixed</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Mixed</em>'.
   * @see com.thalesgroup.orchestra.framework.models.context.DocumentRoot#getMixed()
   * @see #getDocumentRoot()
   * @generated
   */
  EAttribute getDocumentRoot_Mixed();

  /**
   * Returns the meta object for the map '{@link com.thalesgroup.orchestra.framework.models.context.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
   * @see com.thalesgroup.orchestra.framework.models.context.DocumentRoot#getXMLNSPrefixMap()
   * @see #getDocumentRoot()
   * @generated
   */
  EReference getDocumentRoot_XMLNSPrefixMap();

  /**
   * Returns the meta object for the map '{@link com.thalesgroup.orchestra.framework.models.context.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the map '<em>XSI Schema Location</em>'.
   * @see com.thalesgroup.orchestra.framework.models.context.DocumentRoot#getXSISchemaLocation()
   * @see #getDocumentRoot()
   * @generated
   */
  EReference getDocumentRoot_XSISchemaLocation();

  /**
   * Returns the meta object for the containment reference '{@link com.thalesgroup.orchestra.framework.models.context.DocumentRoot#getContextDefinition <em>Context Definition</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Context Definition</em>'.
   * @see com.thalesgroup.orchestra.framework.models.context.DocumentRoot#getContextDefinition()
   * @see #getDocumentRoot()
   * @generated
   */
  EReference getDocumentRoot_ContextDefinition();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.models.context.EnvironmentProperty <em>Environment Property</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Environment Property</em>'.
   * @see com.thalesgroup.orchestra.framework.models.context.EnvironmentProperty
   * @generated
   */
  EClass getEnvironmentProperty();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.models.context.EnvironmentProperty#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see com.thalesgroup.orchestra.framework.models.context.EnvironmentProperty#getName()
   * @see #getEnvironmentProperty()
   * @generated
   */
  EAttribute getEnvironmentProperty_Name();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.models.context.EnvironmentProperty#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see com.thalesgroup.orchestra.framework.models.context.EnvironmentProperty#getValue()
   * @see #getEnvironmentProperty()
   * @generated
   */
  EAttribute getEnvironmentProperty_Value();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  ContextFactory getContextFactory();

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
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.models.context.impl.ArtifactImpl <em>Artifact</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.models.context.impl.ArtifactImpl
     * @see com.thalesgroup.orchestra.framework.models.context.impl.ContextPackageImpl#getArtifact()
     * @generated
     */
    EClass ARTIFACT = eINSTANCE.getArtifact();

    /**
     * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ARTIFACT__GROUP = eINSTANCE.getArtifact_Group();

    /**
     * The meta object literal for the '<em><b>Environment Properties</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ARTIFACT__ENVIRONMENT_PROPERTIES = eINSTANCE.getArtifact_EnvironmentProperties();

    /**
     * The meta object literal for the '<em><b>Environment Id</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ARTIFACT__ENVIRONMENT_ID = eINSTANCE.getArtifact_EnvironmentId();

    /**
     * The meta object literal for the '<em><b>Root Physical Path</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ARTIFACT__ROOT_PHYSICAL_PATH = eINSTANCE.getArtifact_RootPhysicalPath();

    /**
     * The meta object literal for the '<em><b>Uri</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ARTIFACT__URI = eINSTANCE.getArtifact_Uri();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.models.context.impl.ContextImpl <em>Context</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.models.context.impl.ContextImpl
     * @see com.thalesgroup.orchestra.framework.models.context.impl.ContextPackageImpl#getContext()
     * @generated
     */
    EClass CONTEXT = eINSTANCE.getContext();

    /**
     * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CONTEXT__GROUP = eINSTANCE.getContext_Group();

    /**
     * The meta object literal for the '<em><b>Artifact</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CONTEXT__ARTIFACT = eINSTANCE.getContext_Artifact();

    /**
     * The meta object literal for the '<em><b>Export File Path</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CONTEXT__EXPORT_FILE_PATH = eINSTANCE.getContext_ExportFilePath();

    /**
     * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CONTEXT__TYPE = eINSTANCE.getContext_Type();

    /**
     * The meta object literal for the '<em><b>Keep Open</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CONTEXT__KEEP_OPEN = eINSTANCE.getContext_KeepOpen();

    /**
     * The meta object literal for the '<em><b>Launch Arguments</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CONTEXT__LAUNCH_ARGUMENTS = eINSTANCE.getContext_LaunchArguments();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.models.context.impl.ContextDefinitionImpl <em>Definition</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.models.context.impl.ContextDefinitionImpl
     * @see com.thalesgroup.orchestra.framework.models.context.impl.ContextPackageImpl#getContextDefinition()
     * @generated
     */
    EClass CONTEXT_DEFINITION = eINSTANCE.getContextDefinition();

    /**
     * The meta object literal for the '<em><b>Context</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CONTEXT_DEFINITION__CONTEXT = eINSTANCE.getContextDefinition_Context();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.models.context.impl.DocumentRootImpl <em>Document Root</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.models.context.impl.DocumentRootImpl
     * @see com.thalesgroup.orchestra.framework.models.context.impl.ContextPackageImpl#getDocumentRoot()
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
     * The meta object literal for the '<em><b>Context Definition</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference DOCUMENT_ROOT__CONTEXT_DEFINITION = eINSTANCE.getDocumentRoot_ContextDefinition();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.models.context.impl.EnvironmentPropertyImpl <em>Environment Property</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.models.context.impl.EnvironmentPropertyImpl
     * @see com.thalesgroup.orchestra.framework.models.context.impl.ContextPackageImpl#getEnvironmentProperty()
     * @generated
     */
    EClass ENVIRONMENT_PROPERTY = eINSTANCE.getEnvironmentProperty();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ENVIRONMENT_PROPERTY__NAME = eINSTANCE.getEnvironmentProperty_Name();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ENVIRONMENT_PROPERTY__VALUE = eINSTANCE.getEnvironmentProperty_Value();

  }

} //ContextPackage
