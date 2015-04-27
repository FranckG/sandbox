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
 * A representation of the model object '<em><b>Contributed Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.ContributedElement#getSuperCategory <em>Super Category</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage#getContributedElement()
 * @model
 * @generated
 */
public interface ContributedElement extends EObject {
  /**
   * Returns the value of the '<em><b>Super Category</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Super Category</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Super Category</em>' reference.
   * @see #setSuperCategory(Category)
   * @see com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage#getContributedElement_SuperCategory()
   * @model
   * @generated
   */
  Category getSuperCategory();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.model.contexts.ContributedElement#getSuperCategory <em>Super Category</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Super Category</em>' reference.
   * @see #getSuperCategory()
   * @generated
   */
  void setSuperCategory(Category value);

} // ContributedElement
