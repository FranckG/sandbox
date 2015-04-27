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
 * A representation of the model object '<em><b>Properties</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.Properties#getGroup <em>Group</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.Properties#getPROPERTY <em>PROPERTY</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.Properties#getMPROPERTY <em>MPROPERTY</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getProperties()
 * @model extendedMetaData="name='Properties' kind='elementOnly'"
 * @generated
 */
public interface Properties extends EObject {
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
   * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getProperties_Group()
   * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
   *        extendedMetaData="kind='group' name='group:0'"
   * @generated
   */
  FeatureMap getGroup();

  /**
   * Returns the value of the '<em><b>PROPERTY</b></em>' containment reference list.
   * The list contents are of type {@link com.thalesgroup.orchestra.framework.gef.Property}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>PROPERTY</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>PROPERTY</em>' containment reference list.
   * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getProperties_PROPERTY()
   * @model containment="true" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='PROPERTY' group='#group:0'"
   * @generated
   */
  EList<Property> getPROPERTY();

  /**
   * Returns the value of the '<em><b>MPROPERTY</b></em>' containment reference list.
   * The list contents are of type {@link com.thalesgroup.orchestra.framework.gef.Mproperty}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>MPROPERTY</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>MPROPERTY</em>' containment reference list.
   * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getProperties_MPROPERTY()
   * @model containment="true" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='MPROPERTY' group='#group:0'"
   * @generated
   */
  EList<Mproperty> getMPROPERTY();

} // Properties
