/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.UriMigration;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Migration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.UriMigration.Migration#getToolName <em>Tool Name</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.UriMigration.Migration#getRootType <em>Root Type</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.UriMigration.Migration#isInvokeConnector <em>Invoke Connector</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.thalesgroup.orchestra.framework.UriMigration.UriMigrationPackage#getMigration()
 * @model
 * @generated
 */
public interface Migration extends EObject {
  /**
   * Returns the value of the '<em><b>Tool Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Tool Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Tool Name</em>' attribute.
   * @see #setToolName(String)
   * @see com.thalesgroup.orchestra.framework.UriMigration.UriMigrationPackage#getMigration_ToolName()
   * @model
   * @generated
   */
  String getToolName();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.UriMigration.Migration#getToolName <em>Tool Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Tool Name</em>' attribute.
   * @see #getToolName()
   * @generated
   */
  void setToolName(String value);

  /**
   * Returns the value of the '<em><b>Root Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Root Type</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Root Type</em>' attribute.
   * @see #setRootType(String)
   * @see com.thalesgroup.orchestra.framework.UriMigration.UriMigrationPackage#getMigration_RootType()
   * @model
   * @generated
   */
  String getRootType();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.UriMigration.Migration#getRootType <em>Root Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Root Type</em>' attribute.
   * @see #getRootType()
   * @generated
   */
  void setRootType(String value);

  /**
   * Returns the value of the '<em><b>Invoke Connector</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Invoke Connector</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Invoke Connector</em>' attribute.
   * @see #setInvokeConnector(boolean)
   * @see com.thalesgroup.orchestra.framework.UriMigration.UriMigrationPackage#getMigration_InvokeConnector()
   * @model
   * @generated
   */
  boolean isInvokeConnector();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.UriMigration.Migration#isInvokeConnector <em>Invoke Connector</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Invoke Connector</em>' attribute.
   * @see #isInvokeConnector()
   * @generated
   */
  void setInvokeConnector(boolean value);

} // Migration
