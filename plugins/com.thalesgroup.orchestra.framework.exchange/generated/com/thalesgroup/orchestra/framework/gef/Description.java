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
 * A representation of the model object '<em><b>Description</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.Description#getGroup <em>Group</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.Description#getTEXTUALDESCRIPTION <em>TEXTUALDESCRIPTION</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.Description#getFILEREFERENCE <em>FILEREFERENCE</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getDescription()
 * @model extendedMetaData="name='Description' kind='elementOnly'"
 * @generated
 */
public interface Description extends EObject {
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
   * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getDescription_Group()
   * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
   *        extendedMetaData="kind='group' name='group:0'"
   * @generated
   */
  FeatureMap getGroup();

  /**
   * Returns the value of the '<em><b>TEXTUALDESCRIPTION</b></em>' containment reference list.
   * The list contents are of type {@link com.thalesgroup.orchestra.framework.gef.TextualDescription}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>TEXTUALDESCRIPTION</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>TEXTUALDESCRIPTION</em>' containment reference list.
   * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getDescription_TEXTUALDESCRIPTION()
   * @model containment="true" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='TEXTUAL_DESCRIPTION' group='#group:0'"
   * @generated
   */
  EList<TextualDescription> getTEXTUALDESCRIPTION();

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
   * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getDescription_FILEREFERENCE()
   * @model containment="true" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='FILE_REFERENCE' group='#group:0'"
   * @generated
   */
  EList<FileReference> getFILEREFERENCE();

} // Description
