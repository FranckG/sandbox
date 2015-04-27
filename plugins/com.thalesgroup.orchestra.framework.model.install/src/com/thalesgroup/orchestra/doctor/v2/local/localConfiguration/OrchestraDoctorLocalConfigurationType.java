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
 * A representation of the model object '<em><b>Orchestra Doctor Local Configuration Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.OrchestraDoctorLocalConfigurationType#getProduct <em>Product</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ConfigurationPackage#getOrchestraDoctorLocalConfigurationType()
 * @model extendedMetaData="name='OrchestraDoctorLocalConfiguration_._type' kind='elementOnly'"
 * @generated
 */
public interface OrchestraDoctorLocalConfigurationType extends EObject {
  /**
   * Returns the value of the '<em><b>Product</b></em>' containment reference list.
   * The list contents are of type {@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ProductType}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Product</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Product</em>' containment reference list.
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ConfigurationPackage#getOrchestraDoctorLocalConfigurationType_Product()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='product'"
   * @generated
   */
  EList<ProductType> getProduct();

} // OrchestraDoctorLocalConfigurationType
