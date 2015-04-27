/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.model.contexts;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Category</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.Category#getVariables <em>Variables</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.Category#getCategories <em>Categories</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage#getCategory()
 * @model
 * @generated
 */
public interface Category extends NamedElement, ContributedElement {
	/**
   * Returns the value of the '<em><b>Variables</b></em>' containment reference list.
   * The list contents are of type {@link com.thalesgroup.orchestra.framework.model.contexts.Variable}.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Variables</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Variables</em>' containment reference list.
   * @see com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage#getCategory_Variables()
   * @model containment="true"
   * @generated
   */
	EList<Variable> getVariables();

	/**
   * Returns the value of the '<em><b>Categories</b></em>' containment reference list.
   * The list contents are of type {@link com.thalesgroup.orchestra.framework.model.contexts.Category}.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Categories</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Categories</em>' containment reference list.
   * @see com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage#getCategory_Categories()
   * @model containment="true"
   * @generated
   */
	EList<Category> getCategories();

} // Category
