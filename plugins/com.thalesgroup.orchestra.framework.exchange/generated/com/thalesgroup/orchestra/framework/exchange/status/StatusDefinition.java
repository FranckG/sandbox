/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.exchange.status;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Definition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.exchange.status.StatusDefinition#getStatus <em>Status</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.thalesgroup.orchestra.framework.exchange.status.StatusPackage#getStatusDefinition()
 * @model extendedMetaData="name='StatusDefinition' kind='elementOnly'"
 * @generated
 */
public interface StatusDefinition extends EObject {
  /**
   * Returns the value of the '<em><b>Status</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Status</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Status</em>' containment reference.
   * @see #setStatus(Status)
   * @see com.thalesgroup.orchestra.framework.exchange.status.StatusPackage#getStatusDefinition_Status()
   * @model containment="true" required="true"
   *        extendedMetaData="kind='element' name='status'"
   * @generated
   */
  Status getStatus();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.exchange.status.StatusDefinition#getStatus <em>Status</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Status</em>' containment reference.
   * @see #getStatus()
   * @generated
   */
  void setStatus(Status value);

} // StatusDefinition
