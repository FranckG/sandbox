/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.contextsproject;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.thalesgroup.orchestra.framework.contextsproject.ContextsProjectPackage
 * @generated
 */
public interface ContextsProjectFactory extends EFactory {
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  ContextsProjectFactory eINSTANCE = com.thalesgroup.orchestra.framework.contextsproject.impl.ContextsProjectFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Contexts Project</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Contexts Project</em>'.
   * @generated
   */
  ContextsProject createContextsProject();

  /**
   * Returns a new object of class '<em>Administrator</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Administrator</em>'.
   * @generated
   */
  Administrator createAdministrator();

  /**
   * Returns a new object of class '<em>Context Reference</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Context Reference</em>'.
   * @generated
   */
  ContextReference createContextReference();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  ContextsProjectPackage getContextsProjectPackage();

} //ContextsProjectFactory
