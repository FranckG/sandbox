/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.UriMigration;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Migration Definition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.UriMigration.MigrationDefinition#getMigrations <em>Migrations</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.thalesgroup.orchestra.framework.UriMigration.UriMigrationPackage#getMigrationDefinition()
 * @model
 * @generated
 */
public interface MigrationDefinition extends EObject {
  /**
   * Returns the value of the '<em><b>Migrations</b></em>' containment reference list.
   * The list contents are of type {@link com.thalesgroup.orchestra.framework.UriMigration.Migration}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Migrations</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Migrations</em>' containment reference list.
   * @see com.thalesgroup.orchestra.framework.UriMigration.UriMigrationPackage#getMigrationDefinition_Migrations()
   * @model containment="true" required="true"
   * @generated
   */
  EList<Migration> getMigrations();

} // MigrationDefinition
