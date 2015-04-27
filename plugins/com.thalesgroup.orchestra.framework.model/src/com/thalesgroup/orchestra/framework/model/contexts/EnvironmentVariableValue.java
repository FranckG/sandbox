/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.model.contexts;

import org.eclipse.emf.common.util.EMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Environment Variable Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariableValue#getEnvironmentId <em>Environment Id</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariableValue#getValues <em>Values</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage#getEnvironmentVariableValue()
 * @model
 * @generated
 */
public interface EnvironmentVariableValue extends VariableValue {
  /**
   * Returns the value of the '<em><b>Environment Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Environment Id</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Environment Id</em>' attribute.
   * @see #setEnvironmentId(String)
   * @see com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage#getEnvironmentVariableValue_EnvironmentId()
   * @model
   * @generated
   */
  String getEnvironmentId();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariableValue#getEnvironmentId <em>Environment Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Environment Id</em>' attribute.
   * @see #getEnvironmentId()
   * @generated
   */
  void setEnvironmentId(String value);

  /**
   * Returns the value of the '<em><b>Values</b></em>' map.
   * The key is of type {@link java.lang.String},
   * and the value is of type {@link java.lang.String},
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Values</em>' map isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Values</em>' map.
   * @see com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage#getEnvironmentVariableValue_Values()
   * @model mapType="com.thalesgroup.orchestra.framework.model.contexts.StringToStringMap<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>"
   * @generated
   */
  EMap<String, String> getValues();

} // EnvironmentVariableValue
