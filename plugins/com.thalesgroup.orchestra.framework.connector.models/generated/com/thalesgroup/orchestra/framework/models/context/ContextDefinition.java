/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.models.context;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Definition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.models.context.ContextDefinition#getContext <em>Context</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.thalesgroup.orchestra.framework.models.context.ContextPackage#getContextDefinition()
 * @model extendedMetaData="name='ContextDefinition' kind='elementOnly'"
 * @generated
 */
public interface ContextDefinition extends EObject {
  /**
   * Returns the value of the '<em><b>Context</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Context</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Context</em>' containment reference.
   * @see #setContext(Context)
   * @see com.thalesgroup.orchestra.framework.models.context.ContextPackage#getContextDefinition_Context()
   * @model containment="true" required="true"
   *        extendedMetaData="kind='element' name='context'"
   * @generated
   */
  Context getContext();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.models.context.ContextDefinition#getContext <em>Context</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Context</em>' containment reference.
   * @see #getContext()
   * @generated
   */
  void setContext(Context value);

} // ContextDefinition
