/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.model.contexts;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Overriding Variable Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.OverridingVariableValue#getOverriddenValue <em>Overridden Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage#getOverridingVariableValue()
 * @model
 * @generated
 */
public interface OverridingVariableValue extends VariableValue {
  /**
   * Returns the value of the '<em><b>Overridden Value</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Overridden Value</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Overridden Value</em>' reference.
   * @see #setOverriddenValue(VariableValue)
   * @see com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage#getOverridingVariableValue_OverriddenValue()
   * @model
   * @generated
   */
  VariableValue getOverriddenValue();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.model.contexts.OverridingVariableValue#getOverriddenValue <em>Overridden Value</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Overridden Value</em>' reference.
   * @see #getOverriddenValue()
   * @generated
   */
  void setOverriddenValue(VariableValue value);

} // OverridingVariableValue
