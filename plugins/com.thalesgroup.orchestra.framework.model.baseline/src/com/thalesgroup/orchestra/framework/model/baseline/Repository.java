/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.model.baseline;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Repository</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.baseline.Repository#getBaselines <em>Baselines</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.thalesgroup.orchestra.framework.model.baseline.BaselinePackage#getRepository()
 * @model
 * @generated
 */
public interface Repository extends EObject {
  /**
   * Returns the value of the '<em><b>Baselines</b></em>' containment reference list.
   * The list contents are of type {@link com.thalesgroup.orchestra.framework.model.baseline.Baseline}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Baselines</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Baselines</em>' containment reference list.
   * @see com.thalesgroup.orchestra.framework.model.baseline.BaselinePackage#getRepository_Baselines()
   * @model containment="true"
   * @generated
   */
  EList<Baseline> getBaselines();

} // Repository
