/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.gef;

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
 * @see com.thalesgroup.orchestra.framework.gef.GefFactory
 * @model kind="package"
 * @generated
 */
public interface GefPackage extends EPackage {
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "gef";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://www.thalesgroup.com/orchestra/framework/4_0_24/Gef";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "gef";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  GefPackage eINSTANCE = com.thalesgroup.orchestra.framework.gef.impl.GefPackageImpl.init();

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.gef.impl.DescriptionImpl <em>Description</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.gef.impl.DescriptionImpl
   * @see com.thalesgroup.orchestra.framework.gef.impl.GefPackageImpl#getDescription()
   * @generated
   */
  int DESCRIPTION = 0;

  /**
   * The feature id for the '<em><b>Group</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DESCRIPTION__GROUP = 0;

  /**
   * The feature id for the '<em><b>TEXTUALDESCRIPTION</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DESCRIPTION__TEXTUALDESCRIPTION = 1;

  /**
   * The feature id for the '<em><b>FILEREFERENCE</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DESCRIPTION__FILEREFERENCE = 2;

  /**
   * The number of structural features of the '<em>Description</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DESCRIPTION_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.gef.impl.ElementImpl <em>Element</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.gef.impl.ElementImpl
   * @see com.thalesgroup.orchestra.framework.gef.impl.GefPackageImpl#getElement()
   * @generated
   */
  int ELEMENT = 1;

  /**
   * The feature id for the '<em><b>Group</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ELEMENT__GROUP = 0;

  /**
   * The feature id for the '<em><b>DESCRIPTION</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ELEMENT__DESCRIPTION = 1;

  /**
   * The feature id for the '<em><b>VERSION</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ELEMENT__VERSION = 2;

  /**
   * The feature id for the '<em><b>PROPERTIES</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ELEMENT__PROPERTIES = 3;

  /**
   * The feature id for the '<em><b>FILEREFERENCE</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ELEMENT__FILEREFERENCE = 4;

  /**
   * The feature id for the '<em><b>LINKSTOARTIFACTS</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ELEMENT__LINKSTOARTIFACTS = 5;

  /**
   * The feature id for the '<em><b>LINKSTOELEMENTS</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ELEMENT__LINKSTOELEMENTS = 6;

  /**
   * The feature id for the '<em><b>ELEMENT</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ELEMENT__ELEMENT = 7;

  /**
   * The feature id for the '<em><b>Full Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ELEMENT__FULL_NAME = 8;

  /**
   * The feature id for the '<em><b>Hash</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ELEMENT__HASH = 9;

  /**
   * The feature id for the '<em><b>Label</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ELEMENT__LABEL = 10;

  /**
   * The feature id for the '<em><b>Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ELEMENT__TYPE = 11;

  /**
   * The feature id for the '<em><b>Uri</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ELEMENT__URI = 12;

  /**
   * The number of structural features of the '<em>Element</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ELEMENT_FEATURE_COUNT = 13;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.gef.impl.ReferenceImpl <em>Reference</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.gef.impl.ReferenceImpl
   * @see com.thalesgroup.orchestra.framework.gef.impl.GefPackageImpl#getReference()
   * @generated
   */
  int REFERENCE = 12;

  /**
   * The feature id for the '<em><b>Link Direction</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REFERENCE__LINK_DIRECTION = 0;

  /**
   * The feature id for the '<em><b>Link Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REFERENCE__LINK_TYPE = 1;

  /**
   * The feature id for the '<em><b>Uri</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REFERENCE__URI = 2;

  /**
   * The number of structural features of the '<em>Reference</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REFERENCE_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.gef.impl.ElementReferenceImpl <em>Element Reference</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.gef.impl.ElementReferenceImpl
   * @see com.thalesgroup.orchestra.framework.gef.impl.GefPackageImpl#getElementReference()
   * @generated
   */
  int ELEMENT_REFERENCE = 2;

  /**
   * The feature id for the '<em><b>Link Direction</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ELEMENT_REFERENCE__LINK_DIRECTION = REFERENCE__LINK_DIRECTION;

  /**
   * The feature id for the '<em><b>Link Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ELEMENT_REFERENCE__LINK_TYPE = REFERENCE__LINK_TYPE;

  /**
   * The feature id for the '<em><b>Uri</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ELEMENT_REFERENCE__URI = REFERENCE__URI;

  /**
   * The feature id for the '<em><b>Full Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ELEMENT_REFERENCE__FULL_NAME = REFERENCE_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Label</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ELEMENT_REFERENCE__LABEL = REFERENCE_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Element Reference</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ELEMENT_REFERENCE_FEATURE_COUNT = REFERENCE_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.gef.impl.FileReferenceImpl <em>File Reference</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.gef.impl.FileReferenceImpl
   * @see com.thalesgroup.orchestra.framework.gef.impl.GefPackageImpl#getFileReference()
   * @generated
   */
  int FILE_REFERENCE = 3;

  /**
   * The feature id for the '<em><b>Mime Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FILE_REFERENCE__MIME_TYPE = 0;

  /**
   * The feature id for the '<em><b>Nature</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FILE_REFERENCE__NATURE = 1;

  /**
   * The feature id for the '<em><b>Url</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FILE_REFERENCE__URL = 2;

  /**
   * The number of structural features of the '<em>File Reference</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FILE_REFERENCE_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.gef.impl.GEFImpl <em>GEF</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.gef.impl.GEFImpl
   * @see com.thalesgroup.orchestra.framework.gef.impl.GefPackageImpl#getGEF()
   * @generated
   */
  int GEF = 4;

  /**
   * The feature id for the '<em><b>Group</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GEF__GROUP = 0;

  /**
   * The feature id for the '<em><b>GENERICEXPORTFORMAT</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GEF__GENERICEXPORTFORMAT = 1;

  /**
   * The number of structural features of the '<em>GEF</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GEF_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.gef.impl.GenericExportFormatImpl <em>Generic Export Format</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.gef.impl.GenericExportFormatImpl
   * @see com.thalesgroup.orchestra.framework.gef.impl.GefPackageImpl#getGenericExportFormat()
   * @generated
   */
  int GENERIC_EXPORT_FORMAT = 5;

  /**
   * The feature id for the '<em><b>Group</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GENERIC_EXPORT_FORMAT__GROUP = 0;

  /**
   * The feature id for the '<em><b>ELEMENT</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GENERIC_EXPORT_FORMAT__ELEMENT = 1;

  /**
   * The number of structural features of the '<em>Generic Export Format</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GENERIC_EXPORT_FORMAT_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.gef.impl.LinksToArtifactsImpl <em>Links To Artifacts</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.gef.impl.LinksToArtifactsImpl
   * @see com.thalesgroup.orchestra.framework.gef.impl.GefPackageImpl#getLinksToArtifacts()
   * @generated
   */
  int LINKS_TO_ARTIFACTS = 6;

  /**
   * The feature id for the '<em><b>Group</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LINKS_TO_ARTIFACTS__GROUP = 0;

  /**
   * The feature id for the '<em><b>ARTIFACTREFERENCE</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LINKS_TO_ARTIFACTS__ARTIFACTREFERENCE = 1;

  /**
   * The number of structural features of the '<em>Links To Artifacts</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LINKS_TO_ARTIFACTS_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.gef.impl.LinksToElementsImpl <em>Links To Elements</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.gef.impl.LinksToElementsImpl
   * @see com.thalesgroup.orchestra.framework.gef.impl.GefPackageImpl#getLinksToElements()
   * @generated
   */
  int LINKS_TO_ELEMENTS = 7;

  /**
   * The feature id for the '<em><b>Group</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LINKS_TO_ELEMENTS__GROUP = 0;

  /**
   * The feature id for the '<em><b>ELEMENTREFERENCE</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LINKS_TO_ELEMENTS__ELEMENTREFERENCE = 1;

  /**
   * The number of structural features of the '<em>Links To Elements</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int LINKS_TO_ELEMENTS_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.gef.impl.MpropertyImpl <em>Mproperty</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.gef.impl.MpropertyImpl
   * @see com.thalesgroup.orchestra.framework.gef.impl.GefPackageImpl#getMproperty()
   * @generated
   */
  int MPROPERTY = 8;

  /**
   * The feature id for the '<em><b>VALUES</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MPROPERTY__VALUES = 0;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MPROPERTY__NAME = 1;

  /**
   * The feature id for the '<em><b>Nature</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MPROPERTY__NATURE = 2;

  /**
   * The feature id for the '<em><b>Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MPROPERTY__TYPE = 3;

  /**
   * The number of structural features of the '<em>Mproperty</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MPROPERTY_FEATURE_COUNT = 4;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.gef.impl.MpropertyValueImpl <em>Mproperty Value</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.gef.impl.MpropertyValueImpl
   * @see com.thalesgroup.orchestra.framework.gef.impl.GefPackageImpl#getMpropertyValue()
   * @generated
   */
  int MPROPERTY_VALUE = 9;

  /**
   * The feature id for the '<em><b>Mixed</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MPROPERTY_VALUE__MIXED = 0;

  /**
   * The number of structural features of the '<em>Mproperty Value</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MPROPERTY_VALUE_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.gef.impl.PropertiesImpl <em>Properties</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.gef.impl.PropertiesImpl
   * @see com.thalesgroup.orchestra.framework.gef.impl.GefPackageImpl#getProperties()
   * @generated
   */
  int PROPERTIES = 10;

  /**
   * The feature id for the '<em><b>Group</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTIES__GROUP = 0;

  /**
   * The feature id for the '<em><b>PROPERTY</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTIES__PROPERTY = 1;

  /**
   * The feature id for the '<em><b>MPROPERTY</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTIES__MPROPERTY = 2;

  /**
   * The number of structural features of the '<em>Properties</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTIES_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.gef.impl.PropertyImpl <em>Property</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.gef.impl.PropertyImpl
   * @see com.thalesgroup.orchestra.framework.gef.impl.GefPackageImpl#getProperty()
   * @generated
   */
  int PROPERTY = 11;

  /**
   * The feature id for the '<em><b>Mixed</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY__MIXED = 0;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY__NAME = 1;

  /**
   * The feature id for the '<em><b>Nature</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY__NATURE = 2;

  /**
   * The feature id for the '<em><b>Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY__TYPE = 3;

  /**
   * The number of structural features of the '<em>Property</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY_FEATURE_COUNT = 4;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.gef.impl.TextualDescriptionImpl <em>Textual Description</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.gef.impl.TextualDescriptionImpl
   * @see com.thalesgroup.orchestra.framework.gef.impl.GefPackageImpl#getTextualDescription()
   * @generated
   */
  int TEXTUAL_DESCRIPTION = 13;

  /**
   * The feature id for the '<em><b>Mixed</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TEXTUAL_DESCRIPTION__MIXED = 0;

  /**
   * The feature id for the '<em><b>Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TEXTUAL_DESCRIPTION__TYPE = 1;

  /**
   * The number of structural features of the '<em>Textual Description</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TEXTUAL_DESCRIPTION_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.gef.impl.VersionImpl <em>Version</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.gef.impl.VersionImpl
   * @see com.thalesgroup.orchestra.framework.gef.impl.GefPackageImpl#getVersion()
   * @generated
   */
  int VERSION = 14;

  /**
   * The feature id for the '<em><b>File Path</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__FILE_PATH = 0;

  /**
   * The feature id for the '<em><b>Version</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION__VERSION = 1;

  /**
   * The number of structural features of the '<em>Version</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VERSION_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.gef.LinkDirection <em>Link Direction</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.gef.LinkDirection
   * @see com.thalesgroup.orchestra.framework.gef.impl.GefPackageImpl#getLinkDirection()
   * @generated
   */
  int LINK_DIRECTION = 15;

  /**
   * The meta object id for the '<em>Link Direction Object</em>' data type.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.gef.LinkDirection
   * @see com.thalesgroup.orchestra.framework.gef.impl.GefPackageImpl#getLinkDirectionObject()
   * @generated
   */
  int LINK_DIRECTION_OBJECT = 16;


  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.gef.Description <em>Description</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Description</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.Description
   * @generated
   */
  EClass getDescription();

  /**
   * Returns the meta object for the attribute list '{@link com.thalesgroup.orchestra.framework.gef.Description#getGroup <em>Group</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Group</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.Description#getGroup()
   * @see #getDescription()
   * @generated
   */
  EAttribute getDescription_Group();

  /**
   * Returns the meta object for the containment reference list '{@link com.thalesgroup.orchestra.framework.gef.Description#getTEXTUALDESCRIPTION <em>TEXTUALDESCRIPTION</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>TEXTUALDESCRIPTION</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.Description#getTEXTUALDESCRIPTION()
   * @see #getDescription()
   * @generated
   */
  EReference getDescription_TEXTUALDESCRIPTION();

  /**
   * Returns the meta object for the containment reference list '{@link com.thalesgroup.orchestra.framework.gef.Description#getFILEREFERENCE <em>FILEREFERENCE</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>FILEREFERENCE</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.Description#getFILEREFERENCE()
   * @see #getDescription()
   * @generated
   */
  EReference getDescription_FILEREFERENCE();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.gef.Element <em>Element</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Element</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.Element
   * @generated
   */
  EClass getElement();

  /**
   * Returns the meta object for the attribute list '{@link com.thalesgroup.orchestra.framework.gef.Element#getGroup <em>Group</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Group</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.Element#getGroup()
   * @see #getElement()
   * @generated
   */
  EAttribute getElement_Group();

  /**
   * Returns the meta object for the containment reference list '{@link com.thalesgroup.orchestra.framework.gef.Element#getDESCRIPTION <em>DESCRIPTION</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>DESCRIPTION</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.Element#getDESCRIPTION()
   * @see #getElement()
   * @generated
   */
  EReference getElement_DESCRIPTION();

  /**
   * Returns the meta object for the containment reference list '{@link com.thalesgroup.orchestra.framework.gef.Element#getVERSION <em>VERSION</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>VERSION</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.Element#getVERSION()
   * @see #getElement()
   * @generated
   */
  EReference getElement_VERSION();

  /**
   * Returns the meta object for the containment reference list '{@link com.thalesgroup.orchestra.framework.gef.Element#getPROPERTIES <em>PROPERTIES</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>PROPERTIES</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.Element#getPROPERTIES()
   * @see #getElement()
   * @generated
   */
  EReference getElement_PROPERTIES();

  /**
   * Returns the meta object for the containment reference list '{@link com.thalesgroup.orchestra.framework.gef.Element#getFILEREFERENCE <em>FILEREFERENCE</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>FILEREFERENCE</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.Element#getFILEREFERENCE()
   * @see #getElement()
   * @generated
   */
  EReference getElement_FILEREFERENCE();

  /**
   * Returns the meta object for the containment reference list '{@link com.thalesgroup.orchestra.framework.gef.Element#getLINKSTOARTIFACTS <em>LINKSTOARTIFACTS</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>LINKSTOARTIFACTS</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.Element#getLINKSTOARTIFACTS()
   * @see #getElement()
   * @generated
   */
  EReference getElement_LINKSTOARTIFACTS();

  /**
   * Returns the meta object for the containment reference list '{@link com.thalesgroup.orchestra.framework.gef.Element#getLINKSTOELEMENTS <em>LINKSTOELEMENTS</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>LINKSTOELEMENTS</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.Element#getLINKSTOELEMENTS()
   * @see #getElement()
   * @generated
   */
  EReference getElement_LINKSTOELEMENTS();

  /**
   * Returns the meta object for the containment reference list '{@link com.thalesgroup.orchestra.framework.gef.Element#getELEMENT <em>ELEMENT</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>ELEMENT</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.Element#getELEMENT()
   * @see #getElement()
   * @generated
   */
  EReference getElement_ELEMENT();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.gef.Element#getFullName <em>Full Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Full Name</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.Element#getFullName()
   * @see #getElement()
   * @generated
   */
  EAttribute getElement_FullName();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.gef.Element#getHash <em>Hash</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Hash</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.Element#getHash()
   * @see #getElement()
   * @generated
   */
  EAttribute getElement_Hash();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.gef.Element#getLabel <em>Label</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Label</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.Element#getLabel()
   * @see #getElement()
   * @generated
   */
  EAttribute getElement_Label();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.gef.Element#getType <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Type</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.Element#getType()
   * @see #getElement()
   * @generated
   */
  EAttribute getElement_Type();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.gef.Element#getUri <em>Uri</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Uri</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.Element#getUri()
   * @see #getElement()
   * @generated
   */
  EAttribute getElement_Uri();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.gef.ElementReference <em>Element Reference</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Element Reference</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.ElementReference
   * @generated
   */
  EClass getElementReference();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.gef.ElementReference#getFullName <em>Full Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Full Name</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.ElementReference#getFullName()
   * @see #getElementReference()
   * @generated
   */
  EAttribute getElementReference_FullName();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.gef.ElementReference#getLabel <em>Label</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Label</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.ElementReference#getLabel()
   * @see #getElementReference()
   * @generated
   */
  EAttribute getElementReference_Label();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.gef.FileReference <em>File Reference</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>File Reference</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.FileReference
   * @generated
   */
  EClass getFileReference();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.gef.FileReference#getMimeType <em>Mime Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Mime Type</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.FileReference#getMimeType()
   * @see #getFileReference()
   * @generated
   */
  EAttribute getFileReference_MimeType();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.gef.FileReference#getNature <em>Nature</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Nature</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.FileReference#getNature()
   * @see #getFileReference()
   * @generated
   */
  EAttribute getFileReference_Nature();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.gef.FileReference#getUrl <em>Url</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Url</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.FileReference#getUrl()
   * @see #getFileReference()
   * @generated
   */
  EAttribute getFileReference_Url();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.gef.GEF <em>GEF</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>GEF</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.GEF
   * @generated
   */
  EClass getGEF();

  /**
   * Returns the meta object for the attribute list '{@link com.thalesgroup.orchestra.framework.gef.GEF#getGroup <em>Group</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Group</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.GEF#getGroup()
   * @see #getGEF()
   * @generated
   */
  EAttribute getGEF_Group();

  /**
   * Returns the meta object for the containment reference list '{@link com.thalesgroup.orchestra.framework.gef.GEF#getGENERICEXPORTFORMAT <em>GENERICEXPORTFORMAT</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>GENERICEXPORTFORMAT</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.GEF#getGENERICEXPORTFORMAT()
   * @see #getGEF()
   * @generated
   */
  EReference getGEF_GENERICEXPORTFORMAT();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.gef.GenericExportFormat <em>Generic Export Format</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Generic Export Format</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.GenericExportFormat
   * @generated
   */
  EClass getGenericExportFormat();

  /**
   * Returns the meta object for the attribute list '{@link com.thalesgroup.orchestra.framework.gef.GenericExportFormat#getGroup <em>Group</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Group</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.GenericExportFormat#getGroup()
   * @see #getGenericExportFormat()
   * @generated
   */
  EAttribute getGenericExportFormat_Group();

  /**
   * Returns the meta object for the containment reference list '{@link com.thalesgroup.orchestra.framework.gef.GenericExportFormat#getELEMENT <em>ELEMENT</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>ELEMENT</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.GenericExportFormat#getELEMENT()
   * @see #getGenericExportFormat()
   * @generated
   */
  EReference getGenericExportFormat_ELEMENT();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.gef.LinksToArtifacts <em>Links To Artifacts</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Links To Artifacts</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.LinksToArtifacts
   * @generated
   */
  EClass getLinksToArtifacts();

  /**
   * Returns the meta object for the attribute list '{@link com.thalesgroup.orchestra.framework.gef.LinksToArtifacts#getGroup <em>Group</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Group</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.LinksToArtifacts#getGroup()
   * @see #getLinksToArtifacts()
   * @generated
   */
  EAttribute getLinksToArtifacts_Group();

  /**
   * Returns the meta object for the containment reference list '{@link com.thalesgroup.orchestra.framework.gef.LinksToArtifacts#getARTIFACTREFERENCE <em>ARTIFACTREFERENCE</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>ARTIFACTREFERENCE</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.LinksToArtifacts#getARTIFACTREFERENCE()
   * @see #getLinksToArtifacts()
   * @generated
   */
  EReference getLinksToArtifacts_ARTIFACTREFERENCE();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.gef.LinksToElements <em>Links To Elements</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Links To Elements</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.LinksToElements
   * @generated
   */
  EClass getLinksToElements();

  /**
   * Returns the meta object for the attribute list '{@link com.thalesgroup.orchestra.framework.gef.LinksToElements#getGroup <em>Group</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Group</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.LinksToElements#getGroup()
   * @see #getLinksToElements()
   * @generated
   */
  EAttribute getLinksToElements_Group();

  /**
   * Returns the meta object for the containment reference list '{@link com.thalesgroup.orchestra.framework.gef.LinksToElements#getELEMENTREFERENCE <em>ELEMENTREFERENCE</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>ELEMENTREFERENCE</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.LinksToElements#getELEMENTREFERENCE()
   * @see #getLinksToElements()
   * @generated
   */
  EReference getLinksToElements_ELEMENTREFERENCE();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.gef.Mproperty <em>Mproperty</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Mproperty</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.Mproperty
   * @generated
   */
  EClass getMproperty();

  /**
   * Returns the meta object for the containment reference list '{@link com.thalesgroup.orchestra.framework.gef.Mproperty#getVALUES <em>VALUES</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>VALUES</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.Mproperty#getVALUES()
   * @see #getMproperty()
   * @generated
   */
  EReference getMproperty_VALUES();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.gef.Mproperty#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.Mproperty#getName()
   * @see #getMproperty()
   * @generated
   */
  EAttribute getMproperty_Name();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.gef.Mproperty#getNature <em>Nature</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Nature</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.Mproperty#getNature()
   * @see #getMproperty()
   * @generated
   */
  EAttribute getMproperty_Nature();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.gef.Mproperty#getType <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Type</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.Mproperty#getType()
   * @see #getMproperty()
   * @generated
   */
  EAttribute getMproperty_Type();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.gef.MpropertyValue <em>Mproperty Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Mproperty Value</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.MpropertyValue
   * @generated
   */
  EClass getMpropertyValue();

  /**
   * Returns the meta object for the attribute list '{@link com.thalesgroup.orchestra.framework.gef.MpropertyValue#getMixed <em>Mixed</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Mixed</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.MpropertyValue#getMixed()
   * @see #getMpropertyValue()
   * @generated
   */
  EAttribute getMpropertyValue_Mixed();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.gef.Properties <em>Properties</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Properties</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.Properties
   * @generated
   */
  EClass getProperties();

  /**
   * Returns the meta object for the attribute list '{@link com.thalesgroup.orchestra.framework.gef.Properties#getGroup <em>Group</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Group</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.Properties#getGroup()
   * @see #getProperties()
   * @generated
   */
  EAttribute getProperties_Group();

  /**
   * Returns the meta object for the containment reference list '{@link com.thalesgroup.orchestra.framework.gef.Properties#getPROPERTY <em>PROPERTY</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>PROPERTY</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.Properties#getPROPERTY()
   * @see #getProperties()
   * @generated
   */
  EReference getProperties_PROPERTY();

  /**
   * Returns the meta object for the containment reference list '{@link com.thalesgroup.orchestra.framework.gef.Properties#getMPROPERTY <em>MPROPERTY</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>MPROPERTY</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.Properties#getMPROPERTY()
   * @see #getProperties()
   * @generated
   */
  EReference getProperties_MPROPERTY();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.gef.Property <em>Property</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Property</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.Property
   * @generated
   */
  EClass getProperty();

  /**
   * Returns the meta object for the attribute list '{@link com.thalesgroup.orchestra.framework.gef.Property#getMixed <em>Mixed</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Mixed</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.Property#getMixed()
   * @see #getProperty()
   * @generated
   */
  EAttribute getProperty_Mixed();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.gef.Property#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.Property#getName()
   * @see #getProperty()
   * @generated
   */
  EAttribute getProperty_Name();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.gef.Property#getNature <em>Nature</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Nature</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.Property#getNature()
   * @see #getProperty()
   * @generated
   */
  EAttribute getProperty_Nature();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.gef.Property#getType <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Type</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.Property#getType()
   * @see #getProperty()
   * @generated
   */
  EAttribute getProperty_Type();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.gef.Reference <em>Reference</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Reference</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.Reference
   * @generated
   */
  EClass getReference();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.gef.Reference#getLinkDirection <em>Link Direction</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Link Direction</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.Reference#getLinkDirection()
   * @see #getReference()
   * @generated
   */
  EAttribute getReference_LinkDirection();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.gef.Reference#getLinkType <em>Link Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Link Type</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.Reference#getLinkType()
   * @see #getReference()
   * @generated
   */
  EAttribute getReference_LinkType();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.gef.Reference#getUri <em>Uri</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Uri</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.Reference#getUri()
   * @see #getReference()
   * @generated
   */
  EAttribute getReference_Uri();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.gef.TextualDescription <em>Textual Description</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Textual Description</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.TextualDescription
   * @generated
   */
  EClass getTextualDescription();

  /**
   * Returns the meta object for the attribute list '{@link com.thalesgroup.orchestra.framework.gef.TextualDescription#getMixed <em>Mixed</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Mixed</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.TextualDescription#getMixed()
   * @see #getTextualDescription()
   * @generated
   */
  EAttribute getTextualDescription_Mixed();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.gef.TextualDescription#getType <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Type</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.TextualDescription#getType()
   * @see #getTextualDescription()
   * @generated
   */
  EAttribute getTextualDescription_Type();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.gef.Version <em>Version</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Version</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.Version
   * @generated
   */
  EClass getVersion();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.gef.Version#getFilePath <em>File Path</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>File Path</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.Version#getFilePath()
   * @see #getVersion()
   * @generated
   */
  EAttribute getVersion_FilePath();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.gef.Version#getVersion <em>Version</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Version</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.Version#getVersion()
   * @see #getVersion()
   * @generated
   */
  EAttribute getVersion_Version();

  /**
   * Returns the meta object for enum '{@link com.thalesgroup.orchestra.framework.gef.LinkDirection <em>Link Direction</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Link Direction</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.LinkDirection
   * @generated
   */
  EEnum getLinkDirection();

  /**
   * Returns the meta object for data type '{@link com.thalesgroup.orchestra.framework.gef.LinkDirection <em>Link Direction Object</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for data type '<em>Link Direction Object</em>'.
   * @see com.thalesgroup.orchestra.framework.gef.LinkDirection
   * @model instanceClass="com.thalesgroup.orchestra.framework.gef.LinkDirection"
   *        extendedMetaData="name='LinkDirection:Object' baseType='LinkDirection'"
   * @generated
   */
  EDataType getLinkDirectionObject();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  GefFactory getGefFactory();

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
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.gef.impl.DescriptionImpl <em>Description</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.gef.impl.DescriptionImpl
     * @see com.thalesgroup.orchestra.framework.gef.impl.GefPackageImpl#getDescription()
     * @generated
     */
    EClass DESCRIPTION = eINSTANCE.getDescription();

    /**
     * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute DESCRIPTION__GROUP = eINSTANCE.getDescription_Group();

    /**
     * The meta object literal for the '<em><b>TEXTUALDESCRIPTION</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference DESCRIPTION__TEXTUALDESCRIPTION = eINSTANCE.getDescription_TEXTUALDESCRIPTION();

    /**
     * The meta object literal for the '<em><b>FILEREFERENCE</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference DESCRIPTION__FILEREFERENCE = eINSTANCE.getDescription_FILEREFERENCE();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.gef.impl.ElementImpl <em>Element</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.gef.impl.ElementImpl
     * @see com.thalesgroup.orchestra.framework.gef.impl.GefPackageImpl#getElement()
     * @generated
     */
    EClass ELEMENT = eINSTANCE.getElement();

    /**
     * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ELEMENT__GROUP = eINSTANCE.getElement_Group();

    /**
     * The meta object literal for the '<em><b>DESCRIPTION</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ELEMENT__DESCRIPTION = eINSTANCE.getElement_DESCRIPTION();

    /**
     * The meta object literal for the '<em><b>VERSION</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ELEMENT__VERSION = eINSTANCE.getElement_VERSION();

    /**
     * The meta object literal for the '<em><b>PROPERTIES</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ELEMENT__PROPERTIES = eINSTANCE.getElement_PROPERTIES();

    /**
     * The meta object literal for the '<em><b>FILEREFERENCE</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ELEMENT__FILEREFERENCE = eINSTANCE.getElement_FILEREFERENCE();

    /**
     * The meta object literal for the '<em><b>LINKSTOARTIFACTS</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ELEMENT__LINKSTOARTIFACTS = eINSTANCE.getElement_LINKSTOARTIFACTS();

    /**
     * The meta object literal for the '<em><b>LINKSTOELEMENTS</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ELEMENT__LINKSTOELEMENTS = eINSTANCE.getElement_LINKSTOELEMENTS();

    /**
     * The meta object literal for the '<em><b>ELEMENT</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ELEMENT__ELEMENT = eINSTANCE.getElement_ELEMENT();

    /**
     * The meta object literal for the '<em><b>Full Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ELEMENT__FULL_NAME = eINSTANCE.getElement_FullName();

    /**
     * The meta object literal for the '<em><b>Hash</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ELEMENT__HASH = eINSTANCE.getElement_Hash();

    /**
     * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ELEMENT__LABEL = eINSTANCE.getElement_Label();

    /**
     * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ELEMENT__TYPE = eINSTANCE.getElement_Type();

    /**
     * The meta object literal for the '<em><b>Uri</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ELEMENT__URI = eINSTANCE.getElement_Uri();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.gef.impl.ElementReferenceImpl <em>Element Reference</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.gef.impl.ElementReferenceImpl
     * @see com.thalesgroup.orchestra.framework.gef.impl.GefPackageImpl#getElementReference()
     * @generated
     */
    EClass ELEMENT_REFERENCE = eINSTANCE.getElementReference();

    /**
     * The meta object literal for the '<em><b>Full Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ELEMENT_REFERENCE__FULL_NAME = eINSTANCE.getElementReference_FullName();

    /**
     * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ELEMENT_REFERENCE__LABEL = eINSTANCE.getElementReference_Label();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.gef.impl.FileReferenceImpl <em>File Reference</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.gef.impl.FileReferenceImpl
     * @see com.thalesgroup.orchestra.framework.gef.impl.GefPackageImpl#getFileReference()
     * @generated
     */
    EClass FILE_REFERENCE = eINSTANCE.getFileReference();

    /**
     * The meta object literal for the '<em><b>Mime Type</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FILE_REFERENCE__MIME_TYPE = eINSTANCE.getFileReference_MimeType();

    /**
     * The meta object literal for the '<em><b>Nature</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FILE_REFERENCE__NATURE = eINSTANCE.getFileReference_Nature();

    /**
     * The meta object literal for the '<em><b>Url</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FILE_REFERENCE__URL = eINSTANCE.getFileReference_Url();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.gef.impl.GEFImpl <em>GEF</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.gef.impl.GEFImpl
     * @see com.thalesgroup.orchestra.framework.gef.impl.GefPackageImpl#getGEF()
     * @generated
     */
    EClass GEF = eINSTANCE.getGEF();

    /**
     * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute GEF__GROUP = eINSTANCE.getGEF_Group();

    /**
     * The meta object literal for the '<em><b>GENERICEXPORTFORMAT</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GEF__GENERICEXPORTFORMAT = eINSTANCE.getGEF_GENERICEXPORTFORMAT();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.gef.impl.GenericExportFormatImpl <em>Generic Export Format</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.gef.impl.GenericExportFormatImpl
     * @see com.thalesgroup.orchestra.framework.gef.impl.GefPackageImpl#getGenericExportFormat()
     * @generated
     */
    EClass GENERIC_EXPORT_FORMAT = eINSTANCE.getGenericExportFormat();

    /**
     * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute GENERIC_EXPORT_FORMAT__GROUP = eINSTANCE.getGenericExportFormat_Group();

    /**
     * The meta object literal for the '<em><b>ELEMENT</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GENERIC_EXPORT_FORMAT__ELEMENT = eINSTANCE.getGenericExportFormat_ELEMENT();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.gef.impl.LinksToArtifactsImpl <em>Links To Artifacts</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.gef.impl.LinksToArtifactsImpl
     * @see com.thalesgroup.orchestra.framework.gef.impl.GefPackageImpl#getLinksToArtifacts()
     * @generated
     */
    EClass LINKS_TO_ARTIFACTS = eINSTANCE.getLinksToArtifacts();

    /**
     * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute LINKS_TO_ARTIFACTS__GROUP = eINSTANCE.getLinksToArtifacts_Group();

    /**
     * The meta object literal for the '<em><b>ARTIFACTREFERENCE</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference LINKS_TO_ARTIFACTS__ARTIFACTREFERENCE = eINSTANCE.getLinksToArtifacts_ARTIFACTREFERENCE();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.gef.impl.LinksToElementsImpl <em>Links To Elements</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.gef.impl.LinksToElementsImpl
     * @see com.thalesgroup.orchestra.framework.gef.impl.GefPackageImpl#getLinksToElements()
     * @generated
     */
    EClass LINKS_TO_ELEMENTS = eINSTANCE.getLinksToElements();

    /**
     * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute LINKS_TO_ELEMENTS__GROUP = eINSTANCE.getLinksToElements_Group();

    /**
     * The meta object literal for the '<em><b>ELEMENTREFERENCE</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference LINKS_TO_ELEMENTS__ELEMENTREFERENCE = eINSTANCE.getLinksToElements_ELEMENTREFERENCE();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.gef.impl.MpropertyImpl <em>Mproperty</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.gef.impl.MpropertyImpl
     * @see com.thalesgroup.orchestra.framework.gef.impl.GefPackageImpl#getMproperty()
     * @generated
     */
    EClass MPROPERTY = eINSTANCE.getMproperty();

    /**
     * The meta object literal for the '<em><b>VALUES</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MPROPERTY__VALUES = eINSTANCE.getMproperty_VALUES();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MPROPERTY__NAME = eINSTANCE.getMproperty_Name();

    /**
     * The meta object literal for the '<em><b>Nature</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MPROPERTY__NATURE = eINSTANCE.getMproperty_Nature();

    /**
     * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MPROPERTY__TYPE = eINSTANCE.getMproperty_Type();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.gef.impl.MpropertyValueImpl <em>Mproperty Value</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.gef.impl.MpropertyValueImpl
     * @see com.thalesgroup.orchestra.framework.gef.impl.GefPackageImpl#getMpropertyValue()
     * @generated
     */
    EClass MPROPERTY_VALUE = eINSTANCE.getMpropertyValue();

    /**
     * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MPROPERTY_VALUE__MIXED = eINSTANCE.getMpropertyValue_Mixed();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.gef.impl.PropertiesImpl <em>Properties</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.gef.impl.PropertiesImpl
     * @see com.thalesgroup.orchestra.framework.gef.impl.GefPackageImpl#getProperties()
     * @generated
     */
    EClass PROPERTIES = eINSTANCE.getProperties();

    /**
     * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PROPERTIES__GROUP = eINSTANCE.getProperties_Group();

    /**
     * The meta object literal for the '<em><b>PROPERTY</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference PROPERTIES__PROPERTY = eINSTANCE.getProperties_PROPERTY();

    /**
     * The meta object literal for the '<em><b>MPROPERTY</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference PROPERTIES__MPROPERTY = eINSTANCE.getProperties_MPROPERTY();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.gef.impl.PropertyImpl <em>Property</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.gef.impl.PropertyImpl
     * @see com.thalesgroup.orchestra.framework.gef.impl.GefPackageImpl#getProperty()
     * @generated
     */
    EClass PROPERTY = eINSTANCE.getProperty();

    /**
     * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PROPERTY__MIXED = eINSTANCE.getProperty_Mixed();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PROPERTY__NAME = eINSTANCE.getProperty_Name();

    /**
     * The meta object literal for the '<em><b>Nature</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PROPERTY__NATURE = eINSTANCE.getProperty_Nature();

    /**
     * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PROPERTY__TYPE = eINSTANCE.getProperty_Type();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.gef.impl.ReferenceImpl <em>Reference</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.gef.impl.ReferenceImpl
     * @see com.thalesgroup.orchestra.framework.gef.impl.GefPackageImpl#getReference()
     * @generated
     */
    EClass REFERENCE = eINSTANCE.getReference();

    /**
     * The meta object literal for the '<em><b>Link Direction</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute REFERENCE__LINK_DIRECTION = eINSTANCE.getReference_LinkDirection();

    /**
     * The meta object literal for the '<em><b>Link Type</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute REFERENCE__LINK_TYPE = eINSTANCE.getReference_LinkType();

    /**
     * The meta object literal for the '<em><b>Uri</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute REFERENCE__URI = eINSTANCE.getReference_Uri();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.gef.impl.TextualDescriptionImpl <em>Textual Description</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.gef.impl.TextualDescriptionImpl
     * @see com.thalesgroup.orchestra.framework.gef.impl.GefPackageImpl#getTextualDescription()
     * @generated
     */
    EClass TEXTUAL_DESCRIPTION = eINSTANCE.getTextualDescription();

    /**
     * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TEXTUAL_DESCRIPTION__MIXED = eINSTANCE.getTextualDescription_Mixed();

    /**
     * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TEXTUAL_DESCRIPTION__TYPE = eINSTANCE.getTextualDescription_Type();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.gef.impl.VersionImpl <em>Version</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.gef.impl.VersionImpl
     * @see com.thalesgroup.orchestra.framework.gef.impl.GefPackageImpl#getVersion()
     * @generated
     */
    EClass VERSION = eINSTANCE.getVersion();

    /**
     * The meta object literal for the '<em><b>File Path</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VERSION__FILE_PATH = eINSTANCE.getVersion_FilePath();

    /**
     * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VERSION__VERSION = eINSTANCE.getVersion_Version();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.gef.LinkDirection <em>Link Direction</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.gef.LinkDirection
     * @see com.thalesgroup.orchestra.framework.gef.impl.GefPackageImpl#getLinkDirection()
     * @generated
     */
    EEnum LINK_DIRECTION = eINSTANCE.getLinkDirection();

    /**
     * The meta object literal for the '<em>Link Direction Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.gef.LinkDirection
     * @see com.thalesgroup.orchestra.framework.gef.impl.GefPackageImpl#getLinkDirectionObject()
     * @generated
     */
    EDataType LINK_DIRECTION_OBJECT = eINSTANCE.getLinkDirectionObject();

  }

} //GefPackage
