/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.contextsproject;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Context Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.contextsproject.ContextReference#getUri <em>Uri</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.contextsproject.ContextReference#getId <em>Id</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.contextsproject.ContextReference#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.thalesgroup.orchestra.framework.contextsproject.ContextsProjectPackage#getContextReference()
 * @model
 * @generated
 */
public interface ContextReference extends EObject {
  /**
   * Returns the value of the '<em><b>Uri</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Uri</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Uri</em>' attribute.
   * @see #setUri(String)
   * @see com.thalesgroup.orchestra.framework.contextsproject.ContextsProjectPackage#getContextReference_Uri()
   * @model
   * @generated
   */
  String getUri();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.contextsproject.ContextReference#getUri <em>Uri</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Uri</em>' attribute.
   * @see #getUri()
   * @generated
   */
  void setUri(String value);

  /**
   * Returns the value of the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Id</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Id</em>' attribute.
   * @see #setId(String)
   * @see com.thalesgroup.orchestra.framework.contextsproject.ContextsProjectPackage#getContextReference_Id()
   * @model
   * @generated
   */
  String getId();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.contextsproject.ContextReference#getId <em>Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Id</em>' attribute.
   * @see #getId()
   * @generated
   */
  void setId(String value);

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
   * @see com.thalesgroup.orchestra.framework.contextsproject.ContextsProjectPackage#getContextReference_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.contextsproject.ContextReference#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

} // ContextReference
