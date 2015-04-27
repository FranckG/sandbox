/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.doctor.v2.local.localConfiguration;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Compatibility Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.CompatibilityType#getVersionRange <em>Version Range</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.CompatibilityType#getTargetComponent <em>Target Component</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ConfigurationPackage#getCompatibilityType()
 * @model extendedMetaData="name='compatibility_._type' kind='elementOnly'"
 * @generated
 */
public interface CompatibilityType extends EObject {
  /**
   * Returns the value of the '<em><b>Version Range</b></em>' containment reference list.
   * The list contents are of type {@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionRangeType}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Version Range</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Version Range</em>' containment reference list.
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ConfigurationPackage#getCompatibilityType_VersionRange()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='version_range'"
   * @generated
   */
  EList<VersionRangeType> getVersionRange();

  /**
   * Returns the value of the '<em><b>Target Component</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Target Component</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Target Component</em>' attribute.
   * @see #setTargetComponent(String)
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ConfigurationPackage#getCompatibilityType_TargetComponent()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
   *        extendedMetaData="kind='attribute' name='targetComponent'"
   * @generated
   */
  String getTargetComponent();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.CompatibilityType#getTargetComponent <em>Target Component</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Target Component</em>' attribute.
   * @see #getTargetComponent()
   * @generated
   */
  void setTargetComponent(String value);

} // CompatibilityType
