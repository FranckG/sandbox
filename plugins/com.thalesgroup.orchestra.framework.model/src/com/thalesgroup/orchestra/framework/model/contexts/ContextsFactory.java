/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.model.contexts;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage
 * @generated
 */
public interface ContextsFactory extends EFactory {
	/**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	ContextsFactory eINSTANCE = com.thalesgroup.orchestra.framework.model.contexts.impl.ContextsFactoryImpl.init();

	/**
   * Returns a new object of class '<em>Category</em>'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return a new object of class '<em>Category</em>'.
   * @generated
   */
	Category createCategory();

	/**
   * Returns a new object of class '<em>Context</em>'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return a new object of class '<em>Context</em>'.
   * @generated
   */
	Context createContext();

	/**
   * Returns a new object of class '<em>Variable</em>'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return a new object of class '<em>Variable</em>'.
   * @generated
   */
	Variable createVariable();

	/**
   * Returns a new object of class '<em>File Variable</em>'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return a new object of class '<em>File Variable</em>'.
   * @generated
   */
	FileVariable createFileVariable();

	/**
   * Returns a new object of class '<em>Folder Variable</em>'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return a new object of class '<em>Folder Variable</em>'.
   * @generated
   */
	FolderVariable createFolderVariable();

	/**
   * Returns a new object of class '<em>Overriding Variable</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Overriding Variable</em>'.
   * @generated
   */
  OverridingVariable createOverridingVariable();

  /**
   * Returns a new object of class '<em>Variable Value</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Variable Value</em>'.
   * @generated
   */
  VariableValue createVariableValue();

  /**
   * Returns a new object of class '<em>Overriding Variable Value</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Overriding Variable Value</em>'.
   * @generated
   */
  OverridingVariableValue createOverridingVariableValue();

  /**
   * Returns a new object of class '<em>Overriding Environment Variable Value</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Overriding Environment Variable Value</em>'.
   * @generated
   */
  OverridingEnvironmentVariableValue createOverridingEnvironmentVariableValue();

  /**
   * Returns a new object of class '<em>Installation Category</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Installation Category</em>'.
   * @generated
   */
  InstallationCategory createInstallationCategory();

  /**
   * Returns a new object of class '<em>Pending Elements Category</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Pending Elements Category</em>'.
   * @generated
   */
  PendingElementsCategory createPendingElementsCategory();

  /**
   * Returns a new object of class '<em>Contributed Element</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Contributed Element</em>'.
   * @generated
   */
  ContributedElement createContributedElement();

  /**
   * Returns a new object of class '<em>Referenceable Element</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Referenceable Element</em>'.
   * @generated
   */
  ReferenceableElement createReferenceableElement();

  /**
   * Returns a new object of class '<em>Referencing Element</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Referencing Element</em>'.
   * @generated
   */
  ReferencingElement createReferencingElement();

  /**
   * Returns a new object of class '<em>Environment Variable Value</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Environment Variable Value</em>'.
   * @generated
   */
  EnvironmentVariableValue createEnvironmentVariableValue();

  /**
   * Returns a new object of class '<em>Environment Variable</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Environment Variable</em>'.
   * @generated
   */
  EnvironmentVariable createEnvironmentVariable();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
	ContextsPackage getContextsPackage();

} //ContextsFactory
