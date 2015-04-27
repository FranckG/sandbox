/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.gef.impl;

import com.thalesgroup.orchestra.framework.gef.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class GefFactoryImpl extends EFactoryImpl implements GefFactory {
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static GefFactory init() {
    try {
      GefFactory theGefFactory = (GefFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.thalesgroup.com/orchestra/framework/4_0_24/Gef"); 
      if (theGefFactory != null) {
        return theGefFactory;
      }
    }
    catch (Exception exception) {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new GefFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public GefFactoryImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EObject create(EClass eClass) {
    switch (eClass.getClassifierID()) {
      case GefPackage.DESCRIPTION: return createDescription();
      case GefPackage.ELEMENT: return createElement();
      case GefPackage.ELEMENT_REFERENCE: return createElementReference();
      case GefPackage.FILE_REFERENCE: return createFileReference();
      case GefPackage.GEF: return createGEF();
      case GefPackage.GENERIC_EXPORT_FORMAT: return createGenericExportFormat();
      case GefPackage.LINKS_TO_ARTIFACTS: return createLinksToArtifacts();
      case GefPackage.LINKS_TO_ELEMENTS: return createLinksToElements();
      case GefPackage.MPROPERTY: return createMproperty();
      case GefPackage.MPROPERTY_VALUE: return createMpropertyValue();
      case GefPackage.PROPERTIES: return createProperties();
      case GefPackage.PROPERTY: return createProperty();
      case GefPackage.REFERENCE: return createReference();
      case GefPackage.TEXTUAL_DESCRIPTION: return createTextualDescription();
      case GefPackage.VERSION: return createVersion();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object createFromString(EDataType eDataType, String initialValue) {
    switch (eDataType.getClassifierID()) {
      case GefPackage.LINK_DIRECTION:
        return createLinkDirectionFromString(eDataType, initialValue);
      case GefPackage.LINK_DIRECTION_OBJECT:
        return createLinkDirectionObjectFromString(eDataType, initialValue);
      default:
        throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String convertToString(EDataType eDataType, Object instanceValue) {
    switch (eDataType.getClassifierID()) {
      case GefPackage.LINK_DIRECTION:
        return convertLinkDirectionToString(eDataType, instanceValue);
      case GefPackage.LINK_DIRECTION_OBJECT:
        return convertLinkDirectionObjectToString(eDataType, instanceValue);
      default:
        throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Description createDescription() {
    DescriptionImpl description = new DescriptionImpl();
    return description;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Element createElement() {
    ElementImpl element = new ElementImpl();
    return element;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ElementReference createElementReference() {
    ElementReferenceImpl elementReference = new ElementReferenceImpl();
    return elementReference;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FileReference createFileReference() {
    FileReferenceImpl fileReference = new FileReferenceImpl();
    return fileReference;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public GEF createGEF() {
    GEFImpl gef = new GEFImpl();
    return gef;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public GenericExportFormat createGenericExportFormat() {
    GenericExportFormatImpl genericExportFormat = new GenericExportFormatImpl();
    return genericExportFormat;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LinksToArtifacts createLinksToArtifacts() {
    LinksToArtifactsImpl linksToArtifacts = new LinksToArtifactsImpl();
    return linksToArtifacts;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LinksToElements createLinksToElements() {
    LinksToElementsImpl linksToElements = new LinksToElementsImpl();
    return linksToElements;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Mproperty createMproperty() {
    MpropertyImpl mproperty = new MpropertyImpl();
    return mproperty;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MpropertyValue createMpropertyValue() {
    MpropertyValueImpl mpropertyValue = new MpropertyValueImpl();
    return mpropertyValue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Properties createProperties() {
    PropertiesImpl properties = new PropertiesImpl();
    return properties;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Property createProperty() {
    PropertyImpl property = new PropertyImpl();
    return property;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Reference createReference() {
    ReferenceImpl reference = new ReferenceImpl();
    return reference;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TextualDescription createTextualDescription() {
    TextualDescriptionImpl textualDescription = new TextualDescriptionImpl();
    return textualDescription;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Version createVersion() {
    VersionImpl version = new VersionImpl();
    return version;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LinkDirection createLinkDirectionFromString(EDataType eDataType, String initialValue) {
    LinkDirection result = LinkDirection.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertLinkDirectionToString(EDataType eDataType, Object instanceValue) {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LinkDirection createLinkDirectionObjectFromString(EDataType eDataType, String initialValue) {
    return createLinkDirectionFromString(GefPackage.Literals.LINK_DIRECTION, initialValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertLinkDirectionObjectToString(EDataType eDataType, Object instanceValue) {
    return convertLinkDirectionToString(GefPackage.Literals.LINK_DIRECTION, instanceValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public GefPackage getGefPackage() {
    return (GefPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static GefPackage getPackage() {
    return GefPackage.eINSTANCE;
  }

} //GefFactoryImpl
