/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.model.contexts;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Referenceable Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.ReferenceableElement#isReferenceable <em>Referenceable</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage#getReferenceableElement()
 * @model
 * @generated
 */
public interface ReferenceableElement extends EObject {

  /**
   * Returns the value of the '<em><b>Referenceable</b></em>' attribute.
   * The default value is <code>"false"</code>.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Referenceable</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Referenceable</em>' attribute.
   * @see #setReferenceable(boolean)
   * @see com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage#getReferenceableElement_Referenceable()
   * @model default="false"
   * @generated
   */
  boolean isReferenceable();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.model.contexts.ReferenceableElement#isReferenceable <em>Referenceable</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Referenceable</em>' attribute.
   * @see #isReferenceable()
   * @generated
   */
  void setReferenceable(boolean value);
} // ReferenceableElement
