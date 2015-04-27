/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.model.contexts;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Overriding Variable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.OverridingVariable#getOverriddenVariable <em>Overridden Variable</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage#getOverridingVariable()
 * @model
 * @generated
 */
public interface OverridingVariable extends AbstractVariable {
  /**
   * Returns the value of the '<em><b>Overridden Variable</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Overridden Variable</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Overridden Variable</em>' reference.
   * @see #setOverriddenVariable(Variable)
   * @see com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage#getOverridingVariable_OverriddenVariable()
   * @model required="true"
   * @generated
   */
  Variable getOverriddenVariable();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.model.contexts.OverridingVariable#getOverriddenVariable <em>Overridden Variable</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Overridden Variable</em>' reference.
   * @see #getOverriddenVariable()
   * @generated
   */
  void setOverriddenVariable(Variable value);

} // OverridingVariable
