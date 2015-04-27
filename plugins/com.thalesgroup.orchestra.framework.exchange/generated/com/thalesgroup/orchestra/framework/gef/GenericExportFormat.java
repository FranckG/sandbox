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
 * A representation of the model object '<em><b>Generic Export Format</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.GenericExportFormat#getGroup <em>Group</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.GenericExportFormat#getELEMENT <em>ELEMENT</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getGenericExportFormat()
 * @model extendedMetaData="name='GenericExportFormat' kind='elementOnly'"
 * @generated
 */
public interface GenericExportFormat extends EObject {
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
   * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getGenericExportFormat_Group()
   * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
   *        extendedMetaData="kind='group' name='group:0'"
   * @generated
   */
  FeatureMap getGroup();

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
   * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getGenericExportFormat_ELEMENT()
   * @model containment="true" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='ELEMENT' group='#group:0'"
   * @generated
   */
  EList<Element> getELEMENT();

} // GenericExportFormat
