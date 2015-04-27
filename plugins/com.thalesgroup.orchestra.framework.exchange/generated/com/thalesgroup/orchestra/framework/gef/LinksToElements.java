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
 * A representation of the model object '<em><b>Links To Elements</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.LinksToElements#getGroup <em>Group</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.LinksToElements#getELEMENTREFERENCE <em>ELEMENTREFERENCE</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getLinksToElements()
 * @model extendedMetaData="name='LinksToElements' kind='elementOnly'"
 * @generated
 */
public interface LinksToElements extends EObject {
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
   * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getLinksToElements_Group()
   * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
   *        extendedMetaData="kind='group' name='group:0'"
   * @generated
   */
  FeatureMap getGroup();

  /**
   * Returns the value of the '<em><b>ELEMENTREFERENCE</b></em>' containment reference list.
   * The list contents are of type {@link com.thalesgroup.orchestra.framework.gef.ElementReference}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>ELEMENTREFERENCE</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>ELEMENTREFERENCE</em>' containment reference list.
   * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getLinksToElements_ELEMENTREFERENCE()
   * @model containment="true" required="true" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='ELEMENT_REFERENCE' group='#group:0'"
   * @generated
   */
  EList<ElementReference> getELEMENTREFERENCE();

} // LinksToElements
