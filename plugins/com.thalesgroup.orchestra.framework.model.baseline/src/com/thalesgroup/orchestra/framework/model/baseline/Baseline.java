/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.model.baseline;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Baseline</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.baseline.Baseline#getName <em>Name</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.baseline.Baseline#getProjectRelativePath <em>Project Relative Path</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.baseline.Baseline#getContextId <em>Context Id</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.baseline.Baseline#getDescription <em>Description</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.thalesgroup.orchestra.framework.model.baseline.BaselinePackage#getBaseline()
 * @model
 * @generated
 */
public interface Baseline extends EObject {
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
   * @see com.thalesgroup.orchestra.framework.model.baseline.BaselinePackage#getBaseline_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.model.baseline.Baseline#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Project Relative Path</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Project Relative Path</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Project Relative Path</em>' attribute.
   * @see #setProjectRelativePath(String)
   * @see com.thalesgroup.orchestra.framework.model.baseline.BaselinePackage#getBaseline_ProjectRelativePath()
   * @model
   * @generated
   */
  String getProjectRelativePath();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.model.baseline.Baseline#getProjectRelativePath <em>Project Relative Path</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Project Relative Path</em>' attribute.
   * @see #getProjectRelativePath()
   * @generated
   */
  void setProjectRelativePath(String value);

  /**
   * Returns the value of the '<em><b>Context Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Context Id</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Context Id</em>' attribute.
   * @see #setContextId(String)
   * @see com.thalesgroup.orchestra.framework.model.baseline.BaselinePackage#getBaseline_ContextId()
   * @model
   * @generated
   */
  String getContextId();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.model.baseline.Baseline#getContextId <em>Context Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Context Id</em>' attribute.
   * @see #getContextId()
   * @generated
   */
  void setContextId(String value);

  /**
   * Returns the value of the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Description</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Description</em>' attribute.
   * @see #setDescription(String)
   * @see com.thalesgroup.orchestra.framework.model.baseline.BaselinePackage#getBaseline_Description()
   * @model
   * @generated
   */
  String getDescription();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.model.baseline.Baseline#getDescription <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Description</em>' attribute.
   * @see #getDescription()
   * @generated
   */
  void setDescription(String value);

} // Baseline
