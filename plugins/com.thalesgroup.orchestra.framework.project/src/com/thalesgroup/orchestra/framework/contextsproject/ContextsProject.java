/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.contextsproject;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Contexts Project</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.contextsproject.ContextsProject#getParentProject <em>Parent Project</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.contextsproject.ContextsProject#getAdministrators <em>Administrators</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.contextsproject.ContextsProject#getContextReferences <em>Context References</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.thalesgroup.orchestra.framework.contextsproject.ContextsProjectPackage#getContextsProject()
 * @model
 * @generated
 */
public interface ContextsProject extends EObject {
  /**
   * Returns the value of the '<em><b>Parent Project</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Parent Project</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Parent Project</em>' attribute.
   * @see #setParentProject(String)
   * @see com.thalesgroup.orchestra.framework.contextsproject.ContextsProjectPackage#getContextsProject_ParentProject()
   * @model
   * @generated
   */
  String getParentProject();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.contextsproject.ContextsProject#getParentProject <em>Parent Project</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Parent Project</em>' attribute.
   * @see #getParentProject()
   * @generated
   */
  void setParentProject(String value);

  /**
   * Returns the value of the '<em><b>Administrators</b></em>' containment reference list.
   * The list contents are of type {@link com.thalesgroup.orchestra.framework.contextsproject.Administrator}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Administrators</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Administrators</em>' containment reference list.
   * @see com.thalesgroup.orchestra.framework.contextsproject.ContextsProjectPackage#getContextsProject_Administrators()
   * @model containment="true" required="true"
   * @generated
   */
  EList<Administrator> getAdministrators();

  /**
   * Returns the value of the '<em><b>Context References</b></em>' containment reference list.
   * The list contents are of type {@link com.thalesgroup.orchestra.framework.contextsproject.ContextReference}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Context References</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Context References</em>' containment reference list.
   * @see com.thalesgroup.orchestra.framework.contextsproject.ContextsProjectPackage#getContextsProject_ContextReferences()
   * @model containment="true" required="true"
   * @generated
   */
  EList<ContextReference> getContextReferences();

} // ContextsProject
