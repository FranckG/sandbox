/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.gef;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.Element#getGroup <em>Group</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.Element#getDESCRIPTION <em>DESCRIPTION</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.Element#getVERSION <em>VERSION</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.Element#getPROPERTIES <em>PROPERTIES</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.Element#getFILEREFERENCE <em>FILEREFERENCE</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.Element#getLINKSTOARTIFACTS <em>LINKSTOARTIFACTS</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.Element#getLINKSTOELEMENTS <em>LINKSTOELEMENTS</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.Element#getELEMENT <em>ELEMENT</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.Element#getFullName <em>Full Name</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.Element#getHash <em>Hash</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.Element#getLabel <em>Label</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.Element#getType <em>Type</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.Element#getUri <em>Uri</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getElement()
 * @model extendedMetaData="name='Element' kind='elementOnly'"
 * @generated
 */
public interface Element extends EObject {
  /**
   * Returns the value of the '<em><b>Group</b></em>' attribute list.
   * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Group</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Group</em>' attribute list.
   * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getElement_Group()
   * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
   *        extendedMetaData="kind='group' name='group:0'"
   * @generated
   */
  FeatureMap getGroup();

  /**
   * Returns the value of the '<em><b>DESCRIPTION</b></em>' containment reference list.
   * The list contents are of type {@link com.thalesgroup.orchestra.framework.gef.Description}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>DESCRIPTION</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>DESCRIPTION</em>' containment reference list.
   * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getElement_DESCRIPTION()
   * @model containment="true" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='DESCRIPTION' group='#group:0'"
   * @generated
   */
  EList<Description> getDESCRIPTION();

  /**
   * Returns the value of the '<em><b>VERSION</b></em>' containment reference list.
   * The list contents are of type {@link com.thalesgroup.orchestra.framework.gef.Version}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>VERSION</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>VERSION</em>' containment reference list.
   * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getElement_VERSION()
   * @model containment="true" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='VERSION' group='#group:0'"
   * @generated
   */
  EList<Version> getVERSION();

  /**
   * Returns the value of the '<em><b>PROPERTIES</b></em>' containment reference list.
   * The list contents are of type {@link com.thalesgroup.orchestra.framework.gef.Properties}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>PROPERTIES</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>PROPERTIES</em>' containment reference list.
   * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getElement_PROPERTIES()
   * @model containment="true" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='PROPERTIES' group='#group:0'"
   * @generated
   */
  EList<Properties> getPROPERTIES();

  /**
   * Returns the value of the '<em><b>FILEREFERENCE</b></em>' containment reference list.
   * The list contents are of type {@link com.thalesgroup.orchestra.framework.gef.FileReference}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>FILEREFERENCE</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>FILEREFERENCE</em>' containment reference list.
   * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getElement_FILEREFERENCE()
   * @model containment="true" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='FILE_REFERENCE' group='#group:0'"
   * @generated
   */
  EList<FileReference> getFILEREFERENCE();

  /**
   * Returns the value of the '<em><b>LINKSTOARTIFACTS</b></em>' containment reference list.
   * The list contents are of type {@link com.thalesgroup.orchestra.framework.gef.LinksToArtifacts}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>LINKSTOARTIFACTS</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>LINKSTOARTIFACTS</em>' containment reference list.
   * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getElement_LINKSTOARTIFACTS()
   * @model containment="true" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='LINKS_TO_ARTIFACTS' group='#group:0'"
   * @generated
   */
  EList<LinksToArtifacts> getLINKSTOARTIFACTS();

  /**
   * Returns the value of the '<em><b>LINKSTOELEMENTS</b></em>' containment reference list.
   * The list contents are of type {@link com.thalesgroup.orchestra.framework.gef.LinksToElements}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>LINKSTOELEMENTS</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>LINKSTOELEMENTS</em>' containment reference list.
   * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getElement_LINKSTOELEMENTS()
   * @model containment="true" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='LINKS_TO_ELEMENTS' group='#group:0'"
   * @generated
   */
  EList<LinksToElements> getLINKSTOELEMENTS();

  /**
   * Returns the value of the '<em><b>ELEMENT</b></em>' containment reference list.
   * The list contents are of type {@link com.thalesgroup.orchestra.framework.gef.Element}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>ELEMENT</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>ELEMENT</em>' containment reference list.
   * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getElement_ELEMENT()
   * @model containment="true" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='ELEMENT' group='#group:0'"
   * @generated
   */
  EList<Element> getELEMENT();

  /**
   * Returns the value of the '<em><b>Full Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Full Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Full Name</em>' attribute.
   * @see #setFullName(String)
   * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getElement_FullName()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='attribute' name='fullName'"
   * @generated
   */
  String getFullName();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.gef.Element#getFullName <em>Full Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Full Name</em>' attribute.
   * @see #getFullName()
   * @generated
   */
  void setFullName(String value);

  /**
   * Returns the value of the '<em><b>Hash</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Hash</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Hash</em>' attribute.
   * @see #setHash(String)
   * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getElement_Hash()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='attribute' name='hash'"
   * @generated
   */
  String getHash();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.gef.Element#getHash <em>Hash</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Hash</em>' attribute.
   * @see #getHash()
   * @generated
   */
  void setHash(String value);

  /**
   * Returns the value of the '<em><b>Label</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Label</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Label</em>' attribute.
   * @see #setLabel(String)
   * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getElement_Label()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
   *        extendedMetaData="kind='attribute' name='label'"
   * @generated
   */
  String getLabel();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.gef.Element#getLabel <em>Label</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Label</em>' attribute.
   * @see #getLabel()
   * @generated
   */
  void setLabel(String value);

  /**
   * Returns the value of the '<em><b>Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Type</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Type</em>' attribute.
   * @see #setType(String)
   * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getElement_Type()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
   *        extendedMetaData="kind='attribute' name='type'"
   * @generated
   */
  String getType();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.gef.Element#getType <em>Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Type</em>' attribute.
   * @see #getType()
   * @generated
   */
  void setType(String value);

  /**
   * Returns the value of the '<em><b>Uri</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Uri</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Uri</em>' attribute.
   * @see #setUri(String)
   * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getElement_Uri()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
   *        extendedMetaData="kind='attribute' name='uri'"
   * @generated
   */
  String getUri();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.gef.Element#getUri <em>Uri</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Uri</em>' attribute.
   * @see #getUri()
   * @generated
   */
  void setUri(String value);

} // Element
