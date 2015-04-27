/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.models.context.impl;

import com.thalesgroup.orchestra.framework.models.context.Artifact;
import com.thalesgroup.orchestra.framework.models.context.Context;
import com.thalesgroup.orchestra.framework.models.context.ContextDefinition;
import com.thalesgroup.orchestra.framework.models.context.ContextFactory;
import com.thalesgroup.orchestra.framework.models.context.ContextPackage;

import com.thalesgroup.orchestra.framework.models.context.DocumentRoot;
import com.thalesgroup.orchestra.framework.models.context.EnvironmentProperty;
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
public class ContextPackageImpl extends EPackageImpl implements ContextPackage {
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass artifactEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass contextEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass contextDefinitionEClass = null;

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
  private EClass environmentPropertyEClass = null;

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
   * @see com.thalesgroup.orchestra.framework.models.context.ContextPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private ContextPackageImpl() {
    super(eNS_URI, ContextFactory.eINSTANCE);
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
   * <p>This method is used to initialize {@link ContextPackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static ContextPackage init() {
    if (isInited) return (ContextPackage)EPackage.Registry.INSTANCE.getEPackage(ContextPackage.eNS_URI);

    // Obtain or create and register package
    ContextPackageImpl theContextPackage = (ContextPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ContextPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ContextPackageImpl());

    isInited = true;

    // Initialize simple dependencies
    XMLTypePackage.eINSTANCE.eClass();

    // Create package meta-data objects
    theContextPackage.createPackageContents();

    // Initialize created meta-data
    theContextPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theContextPackage.freeze();

  
    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(ContextPackage.eNS_URI, theContextPackage);
    return theContextPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getArtifact() {
    return artifactEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getArtifact_Group() {
    return (EAttribute)artifactEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getArtifact_EnvironmentProperties() {
    return (EReference)artifactEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getArtifact_EnvironmentId() {
    return (EAttribute)artifactEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getArtifact_RootPhysicalPath() {
    return (EAttribute)artifactEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getArtifact_Uri() {
    return (EAttribute)artifactEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getContext() {
    return contextEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getContext_Group() {
    return (EAttribute)contextEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getContext_Artifact() {
    return (EReference)contextEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getContext_ExportFilePath() {
    return (EAttribute)contextEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getContext_Type() {
    return (EAttribute)contextEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getContext_KeepOpen() {
    return (EAttribute)contextEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getContext_LaunchArguments() {
    return (EAttribute)contextEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getContextDefinition() {
    return contextDefinitionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getContextDefinition_Context() {
    return (EReference)contextDefinitionEClass.getEStructuralFeatures().get(0);
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
  public EReference getDocumentRoot_ContextDefinition() {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getEnvironmentProperty() {
    return environmentPropertyEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getEnvironmentProperty_Name() {
    return (EAttribute)environmentPropertyEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getEnvironmentProperty_Value() {
    return (EAttribute)environmentPropertyEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ContextFactory getContextFactory() {
    return (ContextFactory)getEFactoryInstance();
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
    artifactEClass = createEClass(ARTIFACT);
    createEAttribute(artifactEClass, ARTIFACT__GROUP);
    createEReference(artifactEClass, ARTIFACT__ENVIRONMENT_PROPERTIES);
    createEAttribute(artifactEClass, ARTIFACT__ENVIRONMENT_ID);
    createEAttribute(artifactEClass, ARTIFACT__ROOT_PHYSICAL_PATH);
    createEAttribute(artifactEClass, ARTIFACT__URI);

    contextEClass = createEClass(CONTEXT);
    createEAttribute(contextEClass, CONTEXT__GROUP);
    createEReference(contextEClass, CONTEXT__ARTIFACT);
    createEAttribute(contextEClass, CONTEXT__EXPORT_FILE_PATH);
    createEAttribute(contextEClass, CONTEXT__TYPE);
    createEAttribute(contextEClass, CONTEXT__KEEP_OPEN);
    createEAttribute(contextEClass, CONTEXT__LAUNCH_ARGUMENTS);

    contextDefinitionEClass = createEClass(CONTEXT_DEFINITION);
    createEReference(contextDefinitionEClass, CONTEXT_DEFINITION__CONTEXT);

    documentRootEClass = createEClass(DOCUMENT_ROOT);
    createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
    createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
    createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
    createEReference(documentRootEClass, DOCUMENT_ROOT__CONTEXT_DEFINITION);

    environmentPropertyEClass = createEClass(ENVIRONMENT_PROPERTY);
    createEAttribute(environmentPropertyEClass, ENVIRONMENT_PROPERTY__NAME);
    createEAttribute(environmentPropertyEClass, ENVIRONMENT_PROPERTY__VALUE);
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
    initEClass(artifactEClass, Artifact.class, "Artifact", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getArtifact_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, Artifact.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getArtifact_EnvironmentProperties(), this.getEnvironmentProperty(), null, "environmentProperties", null, 1, -1, Artifact.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEAttribute(getArtifact_EnvironmentId(), theXMLTypePackage.getString(), "environmentId", null, 1, 1, Artifact.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getArtifact_RootPhysicalPath(), theXMLTypePackage.getString(), "rootPhysicalPath", null, 0, 1, Artifact.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getArtifact_Uri(), theXMLTypePackage.getString(), "uri", null, 1, 1, Artifact.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(contextEClass, Context.class, "Context", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getContext_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, Context.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getContext_Artifact(), this.getArtifact(), null, "artifact", null, 1, -1, Context.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEAttribute(getContext_ExportFilePath(), theXMLTypePackage.getString(), "exportFilePath", null, 1, 1, Context.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getContext_Type(), theXMLTypePackage.getString(), "type", null, 1, 1, Context.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getContext_KeepOpen(), theXMLTypePackage.getBoolean(), "keepOpen", null, 0, 1, Context.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getContext_LaunchArguments(), theXMLTypePackage.getString(), "launchArguments", null, 0, 1, Context.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(contextDefinitionEClass, ContextDefinition.class, "ContextDefinition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getContextDefinition_Context(), this.getContext(), null, "context", null, 1, 1, ContextDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(documentRootEClass, DocumentRoot.class, "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getDocumentRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_ContextDefinition(), this.getContextDefinition(), null, "contextDefinition", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

    initEClass(environmentPropertyEClass, EnvironmentProperty.class, "EnvironmentProperty", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getEnvironmentProperty_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, EnvironmentProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getEnvironmentProperty_Value(), theXMLTypePackage.getString(), "value", null, 1, 1, EnvironmentProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

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
      (artifactEClass, 
       source, 
       new String[] {
       "name", "Artifact",
       "kind", "elementOnly"
       });		
    addAnnotation
      (getArtifact_Group(), 
       source, 
       new String[] {
       "kind", "group",
       "name", "group:0"
       });		
    addAnnotation
      (getArtifact_EnvironmentProperties(), 
       source, 
       new String[] {
       "kind", "element",
       "name", "environmentProperties",
       "group", "#group:0"
       });		
    addAnnotation
      (getArtifact_EnvironmentId(), 
       source, 
       new String[] {
       "kind", "attribute",
       "name", "environmentId"
       });		
    addAnnotation
      (getArtifact_RootPhysicalPath(), 
       source, 
       new String[] {
       "kind", "attribute",
       "name", "rootPhysicalPath"
       });		
    addAnnotation
      (getArtifact_Uri(), 
       source, 
       new String[] {
       "kind", "attribute",
       "name", "uri"
       });		
    addAnnotation
      (contextEClass, 
       source, 
       new String[] {
       "name", "Context",
       "kind", "elementOnly"
       });		
    addAnnotation
      (getContext_Group(), 
       source, 
       new String[] {
       "kind", "group",
       "name", "group:0"
       });		
    addAnnotation
      (getContext_Artifact(), 
       source, 
       new String[] {
       "kind", "element",
       "name", "artifact",
       "group", "#group:0"
       });		
    addAnnotation
      (getContext_ExportFilePath(), 
       source, 
       new String[] {
       "kind", "attribute",
       "name", "exportFilePath"
       });		
    addAnnotation
      (getContext_Type(), 
       source, 
       new String[] {
       "kind", "attribute",
       "name", "type"
       });		
    addAnnotation
      (contextDefinitionEClass, 
       source, 
       new String[] {
       "name", "ContextDefinition",
       "kind", "elementOnly"
       });		
    addAnnotation
      (getContextDefinition_Context(), 
       source, 
       new String[] {
       "kind", "element",
       "name", "context"
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
      (getDocumentRoot_ContextDefinition(), 
       source, 
       new String[] {
       "kind", "element",
       "name", "contextDefinition",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (environmentPropertyEClass, 
       source, 
       new String[] {
       "name", "EnvironmentProperty",
       "kind", "empty"
       });		
    addAnnotation
      (getEnvironmentProperty_Name(), 
       source, 
       new String[] {
       "kind", "attribute",
       "name", "name"
       });		
    addAnnotation
      (getEnvironmentProperty_Value(), 
       source, 
       new String[] {
       "kind", "attribute",
       "name", "value"
       });
  }

} //ContextPackageImpl
