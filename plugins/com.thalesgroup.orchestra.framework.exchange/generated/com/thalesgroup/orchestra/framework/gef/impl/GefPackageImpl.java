/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.gef.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import com.thalesgroup.orchestra.framework.gef.Description;
import com.thalesgroup.orchestra.framework.gef.Element;
import com.thalesgroup.orchestra.framework.gef.ElementReference;
import com.thalesgroup.orchestra.framework.gef.FileReference;
import com.thalesgroup.orchestra.framework.gef.GefFactory;
import com.thalesgroup.orchestra.framework.gef.GefPackage;
import com.thalesgroup.orchestra.framework.gef.GenericExportFormat;
import com.thalesgroup.orchestra.framework.gef.LinkDirection;
import com.thalesgroup.orchestra.framework.gef.LinksToArtifacts;
import com.thalesgroup.orchestra.framework.gef.LinksToElements;
import com.thalesgroup.orchestra.framework.gef.Mproperty;
import com.thalesgroup.orchestra.framework.gef.MpropertyValue;
import com.thalesgroup.orchestra.framework.gef.Properties;
import com.thalesgroup.orchestra.framework.gef.Property;
import com.thalesgroup.orchestra.framework.gef.Reference;
import com.thalesgroup.orchestra.framework.gef.TextualDescription;
import com.thalesgroup.orchestra.framework.gef.Version;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 * @generated
 */
public class GefPackageImpl extends EPackageImpl implements GefPackage {
  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  private EClass descriptionEClass = null;

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  private EClass elementEClass = null;

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  private EClass elementReferenceEClass = null;

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  private EClass fileReferenceEClass = null;

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  private EClass gefEClass = null;

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  private EClass genericExportFormatEClass = null;

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  private EClass linksToArtifactsEClass = null;

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  private EClass linksToElementsEClass = null;

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  private EClass mpropertyEClass = null;

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  private EClass mpropertyValueEClass = null;

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  private EClass propertiesEClass = null;

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  private EClass propertyEClass = null;

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  private EClass referenceEClass = null;

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  private EClass textualDescriptionEClass = null;

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  private EClass versionEClass = null;

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  private EEnum linkDirectionEEnum = null;

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  private EDataType linkDirectionObjectEDataType = null;

  /**
   * Creates an instance of the model <b>Package</b>, registered with {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package package
   * URI value.
   * <p>
   * Note: the correct way to create the package is via the static factory method {@link #init init()}, which also performs initialization of the package, or
   * returns the registered package, if one already exists. <!-- begin-user-doc --> <!-- end-user-doc -->
   * @see org.eclipse.emf.ecore.EPackage.Registry
   * @see com.thalesgroup.orchestra.framework.gef.GefPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private GefPackageImpl() {
    super(eNS_URI, GefFactory.eINSTANCE);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  private static boolean isInited = false;

  /**
   * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
   * <p>
   * This method is used to initialize {@link GefPackage#eINSTANCE} when that field is accessed. Clients should not invoke it directly. Instead, they should
   * simply access that field to obtain the package. <!-- begin-user-doc --> <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static GefPackage init() {
    if (isInited)
      return (GefPackage) EPackage.Registry.INSTANCE.getEPackage(GefPackage.eNS_URI);

    // Obtain or create and register package
    GefPackageImpl theGefPackage =
        (GefPackageImpl) (EPackage.Registry.INSTANCE.get(eNS_URI) instanceof GefPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new GefPackageImpl());

    isInited = true;

    // Initialize simple dependencies
    XMLTypePackage.eINSTANCE.eClass();

    // Create package meta-data objects
    theGefPackage.createPackageContents();

    // Initialize created meta-data
    theGefPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theGefPackage.freeze();

    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(GefPackage.eNS_URI, theGefPackage);
    return theGefPackage;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EClass getDescription() {
    return descriptionEClass;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getDescription_Group() {
    return (EAttribute) descriptionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EReference getDescription_TEXTUALDESCRIPTION() {
    return (EReference) descriptionEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EReference getDescription_FILEREFERENCE() {
    return (EReference) descriptionEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EClass getElement() {
    return elementEClass;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getElement_Group() {
    return (EAttribute) elementEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EReference getElement_DESCRIPTION() {
    return (EReference) elementEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EReference getElement_VERSION() {
    return (EReference) elementEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EReference getElement_PROPERTIES() {
    return (EReference) elementEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EReference getElement_FILEREFERENCE() {
    return (EReference) elementEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EReference getElement_LINKSTOARTIFACTS() {
    return (EReference) elementEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EReference getElement_LINKSTOELEMENTS() {
    return (EReference) elementEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EReference getElement_ELEMENT() {
    return (EReference) elementEClass.getEStructuralFeatures().get(7);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getElement_FullName() {
    return (EAttribute) elementEClass.getEStructuralFeatures().get(8);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getElement_Hash() {
    return (EAttribute) elementEClass.getEStructuralFeatures().get(9);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getElement_Label() {
    return (EAttribute) elementEClass.getEStructuralFeatures().get(10);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getElement_Type() {
    return (EAttribute) elementEClass.getEStructuralFeatures().get(11);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getElement_Uri() {
    return (EAttribute) elementEClass.getEStructuralFeatures().get(12);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EClass getElementReference() {
    return elementReferenceEClass;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getElementReference_FullName() {
    return (EAttribute) elementReferenceEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getElementReference_Label() {
    return (EAttribute) elementReferenceEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EClass getFileReference() {
    return fileReferenceEClass;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getFileReference_MimeType() {
    return (EAttribute) fileReferenceEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getFileReference_Nature() {
    return (EAttribute) fileReferenceEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getFileReference_Url() {
    return (EAttribute) fileReferenceEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EClass getGEF() {
    return gefEClass;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getGEF_Group() {
    return (EAttribute) gefEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EReference getGEF_GENERICEXPORTFORMAT() {
    return (EReference) gefEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EClass getGenericExportFormat() {
    return genericExportFormatEClass;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getGenericExportFormat_Group() {
    return (EAttribute) genericExportFormatEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EReference getGenericExportFormat_ELEMENT() {
    return (EReference) genericExportFormatEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EClass getLinksToArtifacts() {
    return linksToArtifactsEClass;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getLinksToArtifacts_Group() {
    return (EAttribute) linksToArtifactsEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EReference getLinksToArtifacts_ARTIFACTREFERENCE() {
    return (EReference) linksToArtifactsEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EClass getLinksToElements() {
    return linksToElementsEClass;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getLinksToElements_Group() {
    return (EAttribute) linksToElementsEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EReference getLinksToElements_ELEMENTREFERENCE() {
    return (EReference) linksToElementsEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EClass getMproperty() {
    return mpropertyEClass;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EReference getMproperty_VALUES() {
    return (EReference) mpropertyEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getMproperty_Name() {
    return (EAttribute) mpropertyEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getMproperty_Nature() {
    return (EAttribute) mpropertyEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getMproperty_Type() {
    return (EAttribute) mpropertyEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EClass getMpropertyValue() {
    return mpropertyValueEClass;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getMpropertyValue_Mixed() {
    return (EAttribute) mpropertyValueEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EClass getProperties() {
    return propertiesEClass;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getProperties_Group() {
    return (EAttribute) propertiesEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EReference getProperties_PROPERTY() {
    return (EReference) propertiesEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EReference getProperties_MPROPERTY() {
    return (EReference) propertiesEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EClass getProperty() {
    return propertyEClass;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getProperty_Mixed() {
    return (EAttribute) propertyEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getProperty_Name() {
    return (EAttribute) propertyEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getProperty_Nature() {
    return (EAttribute) propertyEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getProperty_Type() {
    return (EAttribute) propertyEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EClass getReference() {
    return referenceEClass;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getReference_LinkDirection() {
    return (EAttribute) referenceEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getReference_LinkType() {
    return (EAttribute) referenceEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getReference_Uri() {
    return (EAttribute) referenceEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EClass getTextualDescription() {
    return textualDescriptionEClass;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getTextualDescription_Mixed() {
    return (EAttribute) textualDescriptionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getTextualDescription_Type() {
    return (EAttribute) textualDescriptionEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EClass getVersion() {
    return versionEClass;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVersion_FilePath() {
    return (EAttribute) versionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVersion_Version() {
    return (EAttribute) versionEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EEnum getLinkDirection() {
    return linkDirectionEEnum;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EDataType getLinkDirectionObject() {
    return linkDirectionObjectEDataType;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public GefFactory getGefFactory() {
    return (GefFactory) getEFactoryInstance();
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  private boolean isCreated = false;

  /**
   * Creates the meta-model objects for the package. This method is guarded to have no affect on any invocation but its first. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * @generated
   */
  public void createPackageContents() {
    if (isCreated)
      return;
    isCreated = true;

    // Create classes and their features
    descriptionEClass = createEClass(DESCRIPTION);
    createEAttribute(descriptionEClass, DESCRIPTION__GROUP);
    createEReference(descriptionEClass, DESCRIPTION__TEXTUALDESCRIPTION);
    createEReference(descriptionEClass, DESCRIPTION__FILEREFERENCE);

    elementEClass = createEClass(ELEMENT);
    createEAttribute(elementEClass, ELEMENT__GROUP);
    createEReference(elementEClass, ELEMENT__DESCRIPTION);
    createEReference(elementEClass, ELEMENT__VERSION);
    createEReference(elementEClass, ELEMENT__PROPERTIES);
    createEReference(elementEClass, ELEMENT__FILEREFERENCE);
    createEReference(elementEClass, ELEMENT__LINKSTOARTIFACTS);
    createEReference(elementEClass, ELEMENT__LINKSTOELEMENTS);
    createEReference(elementEClass, ELEMENT__ELEMENT);
    createEAttribute(elementEClass, ELEMENT__FULL_NAME);
    createEAttribute(elementEClass, ELEMENT__HASH);
    createEAttribute(elementEClass, ELEMENT__LABEL);
    createEAttribute(elementEClass, ELEMENT__TYPE);
    createEAttribute(elementEClass, ELEMENT__URI);

    elementReferenceEClass = createEClass(ELEMENT_REFERENCE);
    createEAttribute(elementReferenceEClass, ELEMENT_REFERENCE__FULL_NAME);
    createEAttribute(elementReferenceEClass, ELEMENT_REFERENCE__LABEL);

    fileReferenceEClass = createEClass(FILE_REFERENCE);
    createEAttribute(fileReferenceEClass, FILE_REFERENCE__MIME_TYPE);
    createEAttribute(fileReferenceEClass, FILE_REFERENCE__NATURE);
    createEAttribute(fileReferenceEClass, FILE_REFERENCE__URL);

    gefEClass = createEClass(GEF);
    createEAttribute(gefEClass, GEF__GROUP);
    createEReference(gefEClass, GEF__GENERICEXPORTFORMAT);

    genericExportFormatEClass = createEClass(GENERIC_EXPORT_FORMAT);
    createEAttribute(genericExportFormatEClass, GENERIC_EXPORT_FORMAT__GROUP);
    createEReference(genericExportFormatEClass, GENERIC_EXPORT_FORMAT__ELEMENT);

    linksToArtifactsEClass = createEClass(LINKS_TO_ARTIFACTS);
    createEAttribute(linksToArtifactsEClass, LINKS_TO_ARTIFACTS__GROUP);
    createEReference(linksToArtifactsEClass, LINKS_TO_ARTIFACTS__ARTIFACTREFERENCE);

    linksToElementsEClass = createEClass(LINKS_TO_ELEMENTS);
    createEAttribute(linksToElementsEClass, LINKS_TO_ELEMENTS__GROUP);
    createEReference(linksToElementsEClass, LINKS_TO_ELEMENTS__ELEMENTREFERENCE);

    mpropertyEClass = createEClass(MPROPERTY);
    createEReference(mpropertyEClass, MPROPERTY__VALUES);
    createEAttribute(mpropertyEClass, MPROPERTY__NAME);
    createEAttribute(mpropertyEClass, MPROPERTY__NATURE);
    createEAttribute(mpropertyEClass, MPROPERTY__TYPE);

    mpropertyValueEClass = createEClass(MPROPERTY_VALUE);
    createEAttribute(mpropertyValueEClass, MPROPERTY_VALUE__MIXED);

    propertiesEClass = createEClass(PROPERTIES);
    createEAttribute(propertiesEClass, PROPERTIES__GROUP);
    createEReference(propertiesEClass, PROPERTIES__PROPERTY);
    createEReference(propertiesEClass, PROPERTIES__MPROPERTY);

    propertyEClass = createEClass(PROPERTY);
    createEAttribute(propertyEClass, PROPERTY__MIXED);
    createEAttribute(propertyEClass, PROPERTY__NAME);
    createEAttribute(propertyEClass, PROPERTY__NATURE);
    createEAttribute(propertyEClass, PROPERTY__TYPE);

    referenceEClass = createEClass(REFERENCE);
    createEAttribute(referenceEClass, REFERENCE__LINK_DIRECTION);
    createEAttribute(referenceEClass, REFERENCE__LINK_TYPE);
    createEAttribute(referenceEClass, REFERENCE__URI);

    textualDescriptionEClass = createEClass(TEXTUAL_DESCRIPTION);
    createEAttribute(textualDescriptionEClass, TEXTUAL_DESCRIPTION__MIXED);
    createEAttribute(textualDescriptionEClass, TEXTUAL_DESCRIPTION__TYPE);

    versionEClass = createEClass(VERSION);
    createEAttribute(versionEClass, VERSION__FILE_PATH);
    createEAttribute(versionEClass, VERSION__VERSION);

    // Create enums
    linkDirectionEEnum = createEEnum(LINK_DIRECTION);

    // Create data types
    linkDirectionObjectEDataType = createEDataType(LINK_DIRECTION_OBJECT);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  private boolean isInitialized = false;

  /**
   * Complete the initialization of the package and its meta-model. This method is guarded to have no affect on any invocation but its first. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public void initializePackageContents() {
    if (isInitialized)
      return;
    isInitialized = true;

    // Initialize package
    setName(eNAME);
    setNsPrefix(eNS_PREFIX);
    setNsURI(eNS_URI);

    // Obtain other dependent packages
    XMLTypePackage theXMLTypePackage = (XMLTypePackage) EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes
    elementReferenceEClass.getESuperTypes().add(this.getReference());

    // Initialize classes and features; add operations and parameters
    initEClass(descriptionEClass, Description.class, "Description", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getDescription_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, Description.class, !IS_TRANSIENT, !IS_VOLATILE,
        IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDescription_TEXTUALDESCRIPTION(), this.getTextualDescription(), null, "tEXTUALDESCRIPTION", null, 0, -1, Description.class, IS_TRANSIENT,
        IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDescription_FILEREFERENCE(), this.getFileReference(), null, "fILEREFERENCE", null, 0, -1, Description.class, IS_TRANSIENT, IS_VOLATILE,
        IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

    initEClass(elementEClass, Element.class, "Element", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getElement_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, Element.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
        !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getElement_DESCRIPTION(), this.getDescription(), null, "dESCRIPTION", null, 0, -1, Element.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE,
        IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getElement_VERSION(), this.getVersion(), null, "vERSION", null, 0, -1, Element.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE,
        IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getElement_PROPERTIES(), this.getProperties(), null, "pROPERTIES", null, 0, -1, Element.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE,
        IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getElement_FILEREFERENCE(), this.getFileReference(), null, "fILEREFERENCE", null, 0, -1, Element.class, IS_TRANSIENT, IS_VOLATILE,
        IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getElement_LINKSTOARTIFACTS(), this.getLinksToArtifacts(), null, "lINKSTOARTIFACTS", null, 0, -1, Element.class, IS_TRANSIENT, IS_VOLATILE,
        IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getElement_LINKSTOELEMENTS(), this.getLinksToElements(), null, "lINKSTOELEMENTS", null, 0, -1, Element.class, IS_TRANSIENT, IS_VOLATILE,
        IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getElement_ELEMENT(), this.getElement(), null, "eLEMENT", null, 0, -1, Element.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE,
        IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEAttribute(getElement_FullName(), theXMLTypePackage.getString(), "fullName", null, 0, 1, Element.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
        !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getElement_Hash(), theXMLTypePackage.getString(), "hash", null, 0, 1, Element.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
        !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getElement_Label(), theXMLTypePackage.getString(), "label", null, 1, 1, Element.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
        !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getElement_Type(), theXMLTypePackage.getString(), "type", null, 1, 1, Element.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
        !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getElement_Uri(), theXMLTypePackage.getString(), "uri", null, 1, 1, Element.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
        !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(elementReferenceEClass, ElementReference.class, "ElementReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getElementReference_FullName(), theXMLTypePackage.getString(), "fullName", null, 0, 1, ElementReference.class, !IS_TRANSIENT, !IS_VOLATILE,
        IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getElementReference_Label(), theXMLTypePackage.getString(), "label", null, 0, 1, ElementReference.class, !IS_TRANSIENT, !IS_VOLATILE,
        IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(fileReferenceEClass, FileReference.class, "FileReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getFileReference_MimeType(), theXMLTypePackage.getString(), "mimeType", null, 1, 1, FileReference.class, !IS_TRANSIENT, !IS_VOLATILE,
        IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getFileReference_Nature(), theXMLTypePackage.getString(), "nature", null, 0, 1, FileReference.class, !IS_TRANSIENT, !IS_VOLATILE,
        IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getFileReference_Url(), theXMLTypePackage.getString(), "url", null, 1, 1, FileReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
        !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(gefEClass, com.thalesgroup.orchestra.framework.gef.GEF.class, "GEF", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getGEF_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, com.thalesgroup.orchestra.framework.gef.GEF.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getGEF_GENERICEXPORTFORMAT(), this.getGenericExportFormat(), null, "gENERICEXPORTFORMAT", null, 1, -1,
        com.thalesgroup.orchestra.framework.gef.GEF.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
        IS_UNIQUE, IS_DERIVED, IS_ORDERED);

    initEClass(genericExportFormatEClass, GenericExportFormat.class, "GenericExportFormat", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getGenericExportFormat_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, GenericExportFormat.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getGenericExportFormat_ELEMENT(), this.getElement(), null, "eLEMENT", null, 0, -1, GenericExportFormat.class, IS_TRANSIENT, IS_VOLATILE,
        IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

    initEClass(linksToArtifactsEClass, LinksToArtifacts.class, "LinksToArtifacts", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getLinksToArtifacts_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, LinksToArtifacts.class, !IS_TRANSIENT, !IS_VOLATILE,
        IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getLinksToArtifacts_ARTIFACTREFERENCE(), this.getReference(), null, "aRTIFACTREFERENCE", null, 1, -1, LinksToArtifacts.class, IS_TRANSIENT,
        IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

    initEClass(linksToElementsEClass, LinksToElements.class, "LinksToElements", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getLinksToElements_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, LinksToElements.class, !IS_TRANSIENT, !IS_VOLATILE,
        IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getLinksToElements_ELEMENTREFERENCE(), this.getElementReference(), null, "eLEMENTREFERENCE", null, 1, -1, LinksToElements.class,
        IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

    initEClass(mpropertyEClass, Mproperty.class, "Mproperty", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getMproperty_VALUES(), this.getMpropertyValue(), null, "vALUES", null, 1, -1, Mproperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
        IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMproperty_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, Mproperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
        !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMproperty_Nature(), theXMLTypePackage.getString(), "nature", null, 0, 1, Mproperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
        !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMproperty_Type(), theXMLTypePackage.getString(), "type", null, 0, 1, Mproperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
        !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(mpropertyValueEClass, MpropertyValue.class, "MpropertyValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getMpropertyValue_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, MpropertyValue.class, !IS_TRANSIENT, !IS_VOLATILE,
        IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(propertiesEClass, Properties.class, "Properties", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getProperties_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, Properties.class, !IS_TRANSIENT, !IS_VOLATILE,
        IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getProperties_PROPERTY(), this.getProperty(), null, "pROPERTY", null, 0, -1, Properties.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE,
        IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getProperties_MPROPERTY(), this.getMproperty(), null, "mPROPERTY", null, 0, -1, Properties.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE,
        IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

    initEClass(propertyEClass, Property.class, "Property", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getProperty_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
        !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getProperty_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
        !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getProperty_Nature(), theXMLTypePackage.getString(), "nature", null, 0, 1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
        !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getProperty_Type(), theXMLTypePackage.getString(), "type", null, 0, 1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
        !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(referenceEClass, Reference.class, "Reference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getReference_LinkDirection(), this.getLinkDirection(), "linkDirection", null, 0, 1, Reference.class, !IS_TRANSIENT, !IS_VOLATILE,
        IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getReference_LinkType(), theXMLTypePackage.getString(), "linkType", null, 0, 1, Reference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
        !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getReference_Uri(), theXMLTypePackage.getString(), "uri", null, 1, 1, Reference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
        !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(textualDescriptionEClass, TextualDescription.class, "TextualDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getTextualDescription_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, TextualDescription.class, !IS_TRANSIENT,
        !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getTextualDescription_Type(), theXMLTypePackage.getString(), "type", null, 1, 1, TextualDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
        IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(versionEClass, Version.class, "Version", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getVersion_FilePath(), theXMLTypePackage.getString(), "filePath", null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
        !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVersion_Version(), theXMLTypePackage.getString(), "version", null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
        !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    // Initialize enums and add enum literals
    initEEnum(linkDirectionEEnum, LinkDirection.class, "LinkDirection");
    addEEnumLiteral(linkDirectionEEnum, LinkDirection.IN);
    addEEnumLiteral(linkDirectionEEnum, LinkDirection.OUT);

    // Initialize data types
    initEDataType(linkDirectionObjectEDataType, LinkDirection.class, "LinkDirectionObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);

    // Create resource
    createResource(eNS_URI);

    // Create annotations
    // http:///org/eclipse/emf/ecore/util/ExtendedMetaData
    createExtendedMetaDataAnnotations();
  }

  /**
   * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>. <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  protected void createExtendedMetaDataAnnotations() {
    String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";
    addAnnotation(descriptionEClass, source, new String[] { "name", "Description", "kind", "elementOnly" });
    addAnnotation(getDescription_Group(), source, new String[] { "kind", "group", "name", "group:0" });
    addAnnotation(getDescription_TEXTUALDESCRIPTION(), source, new String[] { "kind", "element", "name", "TEXTUAL_DESCRIPTION", "group", "#group:0" });
    addAnnotation(getDescription_FILEREFERENCE(), source, new String[] { "kind", "element", "name", "FILE_REFERENCE", "group", "#group:0" });
    addAnnotation(elementEClass, source, new String[] { "name", "Element", "kind", "elementOnly" });
    addAnnotation(getElement_Group(), source, new String[] { "kind", "group", "name", "group:0" });
    addAnnotation(getElement_DESCRIPTION(), source, new String[] { "kind", "element", "name", "DESCRIPTION", "group", "#group:0" });
    addAnnotation(getElement_VERSION(), source, new String[] { "kind", "element", "name", "VERSION", "group", "#group:0" });
    addAnnotation(getElement_PROPERTIES(), source, new String[] { "kind", "element", "name", "PROPERTIES", "group", "#group:0" });
    addAnnotation(getElement_FILEREFERENCE(), source, new String[] { "kind", "element", "name", "FILE_REFERENCE", "group", "#group:0" });
    addAnnotation(getElement_LINKSTOARTIFACTS(), source, new String[] { "kind", "element", "name", "LINKS_TO_ARTIFACTS", "group", "#group:0" });
    addAnnotation(getElement_LINKSTOELEMENTS(), source, new String[] { "kind", "element", "name", "LINKS_TO_ELEMENTS", "group", "#group:0" });
    addAnnotation(getElement_ELEMENT(), source, new String[] { "kind", "element", "name", "ELEMENT", "group", "#group:0" });
    addAnnotation(getElement_FullName(), source, new String[] { "kind", "attribute", "name", "fullName" });
    addAnnotation(getElement_Hash(), source, new String[] { "kind", "attribute", "name", "hash" });
    addAnnotation(getElement_Label(), source, new String[] { "kind", "attribute", "name", "label" });
    addAnnotation(getElement_Type(), source, new String[] { "kind", "attribute", "name", "type" });
    addAnnotation(getElement_Uri(), source, new String[] { "kind", "attribute", "name", "uri" });
    addAnnotation(elementReferenceEClass, source, new String[] { "name", "ElementReference", "kind", "empty" });
    addAnnotation(getElementReference_FullName(), source, new String[] { "kind", "attribute", "name", "fullName" });
    addAnnotation(getElementReference_Label(), source, new String[] { "kind", "attribute", "name", "label" });
    addAnnotation(fileReferenceEClass, source, new String[] { "name", "FileReference", "kind", "empty" });
    addAnnotation(getFileReference_MimeType(), source, new String[] { "kind", "attribute", "name", "mimeType" });
    addAnnotation(getFileReference_Nature(), source, new String[] { "kind", "attribute", "name", "nature" });
    addAnnotation(getFileReference_Url(), source, new String[] { "kind", "attribute", "name", "url" });
    addAnnotation(gefEClass, source, new String[] { "name", "GEF", "kind", "elementOnly" });
    addAnnotation(getGEF_Group(), source, new String[] { "kind", "group", "name", "group:0" });
    addAnnotation(getGEF_GENERICEXPORTFORMAT(), source, new String[] { "kind", "element", "name", "GENERIC_EXPORT_FORMAT", "group", "#group:0" });
    addAnnotation(genericExportFormatEClass, source, new String[] { "name", "GenericExportFormat", "kind", "elementOnly" });
    addAnnotation(getGenericExportFormat_Group(), source, new String[] { "kind", "group", "name", "group:0" });
    addAnnotation(getGenericExportFormat_ELEMENT(), source, new String[] { "kind", "element", "name", "ELEMENT", "group", "#group:0" });
    addAnnotation(linkDirectionEEnum, source, new String[] { "name", "LinkDirection" });
    addAnnotation(linkDirectionObjectEDataType, source, new String[] { "name", "LinkDirection:Object", "baseType", "LinkDirection" });
    addAnnotation(linksToArtifactsEClass, source, new String[] { "name", "LinksToArtifacts", "kind", "elementOnly" });
    addAnnotation(getLinksToArtifacts_Group(), source, new String[] { "kind", "group", "name", "group:0" });
    addAnnotation(getLinksToArtifacts_ARTIFACTREFERENCE(), source, new String[] { "kind", "element", "name", "ARTIFACT_REFERENCE", "group", "#group:0" });
    addAnnotation(linksToElementsEClass, source, new String[] { "name", "LinksToElements", "kind", "elementOnly" });
    addAnnotation(getLinksToElements_Group(), source, new String[] { "kind", "group", "name", "group:0" });
    addAnnotation(getLinksToElements_ELEMENTREFERENCE(), source, new String[] { "kind", "element", "name", "ELEMENT_REFERENCE", "group", "#group:0" });
    addAnnotation(mpropertyEClass, source, new String[] { "name", "Mproperty", "kind", "elementOnly" });
    addAnnotation(getMproperty_VALUES(), source, new String[] { "kind", "element", "name", "VALUES" });
    addAnnotation(getMproperty_Name(), source, new String[] { "kind", "attribute", "name", "name" });
    addAnnotation(getMproperty_Nature(), source, new String[] { "kind", "attribute", "name", "nature" });
    addAnnotation(getMproperty_Type(), source, new String[] { "kind", "attribute", "name", "type" });
    addAnnotation(mpropertyValueEClass, source, new String[] { "name", "MpropertyValue", "kind", "mixed" });
    addAnnotation(getMpropertyValue_Mixed(), source, new String[] { "kind", "elementWildcard", "name", ":mixed" });
    addAnnotation(propertiesEClass, source, new String[] { "name", "Properties", "kind", "elementOnly" });
    addAnnotation(getProperties_Group(), source, new String[] { "kind", "group", "name", "group:0" });
    addAnnotation(getProperties_PROPERTY(), source, new String[] { "kind", "element", "name", "PROPERTY", "group", "#group:0" });
    addAnnotation(getProperties_MPROPERTY(), source, new String[] { "kind", "element", "name", "MPROPERTY", "group", "#group:0" });
    addAnnotation(propertyEClass, source, new String[] { "name", "Property", "kind", "mixed" });
    addAnnotation(getProperty_Mixed(), source, new String[] { "kind", "elementWildcard", "name", ":mixed" });
    addAnnotation(getProperty_Name(), source, new String[] { "kind", "attribute", "name", "name" });
    addAnnotation(getProperty_Nature(), source, new String[] { "kind", "attribute", "name", "nature" });
    addAnnotation(getProperty_Type(), source, new String[] { "kind", "attribute", "name", "type" });
    addAnnotation(referenceEClass, source, new String[] { "name", "Reference", "kind", "empty" });
    addAnnotation(getReference_LinkDirection(), source, new String[] { "kind", "attribute", "name", "linkDirection" });
    addAnnotation(getReference_LinkType(), source, new String[] { "kind", "attribute", "name", "linkType" });
    addAnnotation(getReference_Uri(), source, new String[] { "kind", "attribute", "name", "uri" });
    addAnnotation(textualDescriptionEClass, source, new String[] { "name", "TextualDescription", "kind", "mixed" });
    addAnnotation(getTextualDescription_Mixed(), source, new String[] { "kind", "elementWildcard", "name", ":mixed" });
    addAnnotation(getTextualDescription_Type(), source, new String[] { "kind", "attribute", "name", "type" });
    addAnnotation(versionEClass, source, new String[] { "name", "Version", "kind", "empty" });
    addAnnotation(getVersion_FilePath(), source, new String[] { "kind", "attribute", "name", "filePath" });
    addAnnotation(getVersion_Version(), source, new String[] { "kind", "attribute", "name", "version" });
  }

} // GefPackageImpl
