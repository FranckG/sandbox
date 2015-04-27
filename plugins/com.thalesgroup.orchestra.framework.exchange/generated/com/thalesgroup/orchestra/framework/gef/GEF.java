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
 * A representation of the model object '<em><b>GEF</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.GEF#getGroup <em>Group</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.GEF#getGENERICEXPORTFORMAT <em>GENERICEXPORTFORMAT</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getGEF()
 * @model extendedMetaData="name='GEF' kind='elementOnly'"
 * @generated
 */
public interface GEF extends EObject {
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
   * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getGEF_Group()
   * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
   *        extendedMetaData="kind='group' name='group:0'"
   * @generated
   */
  FeatureMap getGroup();

  /**
   * Returns the value of the '<em><b>GENERICEXPORTFORMAT</b></em>' containment reference list.
   * The list contents are of type {@link com.thalesgroup.orchestra.framework.gef.GenericExportFormat}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>GENERICEXPORTFORMAT</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>GENERICEXPORTFORMAT</em>' containment reference list.
   * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getGEF_GENERICEXPORTFORMAT()
   * @model containment="true" required="true" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='GENERIC_EXPORT_FORMAT' group='#group:0'"
   * @generated
   */
  EList<GenericExportFormat> getGENERICEXPORTFORMAT();

} // GEF
