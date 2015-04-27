/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.doctor.v2.local.localConfiguration;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Version Range Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionRangeType#getCompatType <em>Compat Type</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionRangeType#getRange <em>Range</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ConfigurationPackage#getVersionRangeType()
 * @model extendedMetaData="name='version_range_._type' kind='empty'"
 * @generated
 */
public interface VersionRangeType extends EObject {
  /**
   * Returns the value of the '<em><b>Compat Type</b></em>' attribute.
   * The default value is <code>"all"</code>.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Compat Type</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Compat Type</em>' attribute.
   * @see #isSetCompatType()
   * @see #unsetCompatType()
   * @see #setCompatType(String)
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ConfigurationPackage#getVersionRangeType_CompatType()
   * @model default="all" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='attribute' name='compatType'"
   * @generated
   */
  String getCompatType();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionRangeType#getCompatType <em>Compat Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Compat Type</em>' attribute.
   * @see #isSetCompatType()
   * @see #unsetCompatType()
   * @see #getCompatType()
   * @generated
   */
  void setCompatType(String value);

  /**
   * Unsets the value of the '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionRangeType#getCompatType <em>Compat Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetCompatType()
   * @see #getCompatType()
   * @see #setCompatType(String)
   * @generated
   */
  void unsetCompatType();

  /**
   * Returns whether the value of the '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionRangeType#getCompatType <em>Compat Type</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Compat Type</em>' attribute is set.
   * @see #unsetCompatType()
   * @see #getCompatType()
   * @see #setCompatType(String)
   * @generated
   */
  boolean isSetCompatType();

  /**
   * Returns the value of the '<em><b>Range</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Range</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Range</em>' attribute.
   * @see #setRange(String)
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ConfigurationPackage#getVersionRangeType_Range()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
   *        extendedMetaData="kind='attribute' name='range'"
   * @generated
   */
  String getRange();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionRangeType#getRange <em>Range</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Range</em>' attribute.
   * @see #getRange()
   * @generated
   */
  void setRange(String value);

} // VersionRangeType
