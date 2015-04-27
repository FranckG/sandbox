/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.doctor.v2.local.localConfiguration;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ConfigurationPackage
 * @generated
 */
public interface ConfigurationFactory extends EFactory {
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  ConfigurationFactory eINSTANCE = com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.ConfigurationFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Compatibility Type</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Compatibility Type</em>'.
   * @generated
   */
  CompatibilityType createCompatibilityType();

  /**
   * Returns a new object of class '<em>Document Root</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Document Root</em>'.
   * @generated
   */
  DocumentRoot createDocumentRoot();

  /**
   * Returns a new object of class '<em>Orchestra Doctor Local Configuration Type</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Orchestra Doctor Local Configuration Type</em>'.
   * @generated
   */
  OrchestraDoctorLocalConfigurationType createOrchestraDoctorLocalConfigurationType();

  /**
   * Returns a new object of class '<em>Parameter Type</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Parameter Type</em>'.
   * @generated
   */
  ParameterType createParameterType();

  /**
   * Returns a new object of class '<em>Product Type</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Product Type</em>'.
   * @generated
   */
  ProductType createProductType();

  /**
   * Returns a new object of class '<em>Version Range Type</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Version Range Type</em>'.
   * @generated
   */
  VersionRangeType createVersionRangeType();

  /**
   * Returns a new object of class '<em>Version Type</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Version Type</em>'.
   * @generated
   */
  VersionType createVersionType();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  ConfigurationPackage getConfigurationPackage();

} //ConfigurationFactory
