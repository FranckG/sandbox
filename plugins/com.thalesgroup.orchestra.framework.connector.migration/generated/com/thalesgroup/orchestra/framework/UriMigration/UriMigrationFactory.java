/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.UriMigration;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.thalesgroup.orchestra.framework.UriMigration.UriMigrationPackage
 * @generated
 */
public interface UriMigrationFactory extends EFactory {
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  UriMigrationFactory eINSTANCE = com.thalesgroup.orchestra.framework.UriMigration.impl.UriMigrationFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Migration</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Migration</em>'.
   * @generated
   */
  Migration createMigration();

  /**
   * Returns a new object of class '<em>Migration Definition</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Migration Definition</em>'.
   * @generated
   */
  MigrationDefinition createMigrationDefinition();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  UriMigrationPackage getUriMigrationPackage();

} //UriMigrationFactory
