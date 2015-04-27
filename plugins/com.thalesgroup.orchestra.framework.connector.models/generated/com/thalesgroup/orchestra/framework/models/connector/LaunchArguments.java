/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.models.connector;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Launch Arguments</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.models.connector.LaunchArguments#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.thalesgroup.orchestra.framework.models.connector.ConnectorPackage#getLaunchArguments()
 * @model
 * @generated
 */
public interface LaunchArguments extends EObject {
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see com.thalesgroup.orchestra.framework.models.connector.ConnectorPackage#getLaunchArguments_Name()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.models.connector.LaunchArguments#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

} // LaunchArguments
