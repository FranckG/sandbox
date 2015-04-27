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

import com.thalesgroup.orchestra.framework.model.contexts.util.ContextsResourceImpl;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Context</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.Context#getCategories <em>Categories</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.Context#getSuperContext <em>Super Context</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.Context#getTransientCategories <em>Transient Categories</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.Context#getSuperCategoryVariables <em>Super Category Variables</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.Context#getOverridingVariables <em>Overriding Variables</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.Context#getSuperCategoryCategories <em>Super Category Categories</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.Context#getSelectedVersions <em>Selected Versions</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.Context#getCurrentVersions <em>Current Versions</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.Context#getDescription <em>Description</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage#getContext()
 * @model
 * @generated
 */
public interface Context extends NamedElement {
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
   * @see com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage#getContext_Categories()
   * @model containment="true"
   * @generated
   */
	EList<Category> getCategories();

	/**
   * Returns the value of the '<em><b>Super Context</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Super Context</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Super Context</em>' reference.
   * @see #setSuperContext(Context)
   * @see com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage#getContext_SuperContext()
   * @model
   * @generated
   */
  Context getSuperContext();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.model.contexts.Context#getSuperContext <em>Super Context</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Super Context</em>' reference.
   * @see #getSuperContext()
   * @generated
   */
  void setSuperContext(Context value);

  /**
   * Returns the value of the '<em><b>Transient Categories</b></em>' containment reference list.
   * The list contents are of type {@link com.thalesgroup.orchestra.framework.model.contexts.Category}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Transient Categories</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Transient Categories</em>' containment reference list.
   * @see com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage#getContext_TransientCategories()
   * @model containment="true" transient="true"
   * @generated
   */
  EList<Category> getTransientCategories();

  /**
   * Returns the value of the '<em><b>Super Category Variables</b></em>' containment reference list.
   * The list contents are of type {@link com.thalesgroup.orchestra.framework.model.contexts.Variable}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Super Category Variables</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Super Category Variables</em>' containment reference list.
   * @see com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage#getContext_SuperCategoryVariables()
   * @model containment="true"
   * @generated
   */
  EList<Variable> getSuperCategoryVariables();

  /**
   * Returns the value of the '<em><b>Overriding Variables</b></em>' containment reference list.
   * The list contents are of type {@link com.thalesgroup.orchestra.framework.model.contexts.OverridingVariable}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Overriding Variables</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Overriding Variables</em>' containment reference list.
   * @see com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage#getContext_OverridingVariables()
   * @model containment="true"
   * @generated
   */
  EList<OverridingVariable> getOverridingVariables();

  /**
   * Returns the value of the '<em><b>Super Category Categories</b></em>' containment reference list.
   * The list contents are of type {@link com.thalesgroup.orchestra.framework.model.contexts.Category}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Super Category Categories</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Super Category Categories</em>' containment reference list.
   * @see com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage#getContext_SuperCategoryCategories()
   * @model containment="true"
   * @generated
   */
  EList<Category> getSuperCategoryCategories();

  /**
   * Returns the value of the '<em><b>Selected Versions</b></em>' containment reference list.
   * The list contents are of type {@link com.thalesgroup.orchestra.framework.model.contexts.InstallationCategory}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Selected Versions</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Selected Versions</em>' containment reference list.
   * @see com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage#getContext_SelectedVersions()
   * @model containment="true"
   * @generated
   */
  EList<InstallationCategory> getSelectedVersions();

  /**
   * Returns the value of the '<em><b>Current Versions</b></em>' containment reference list.
   * The list contents are of type {@link com.thalesgroup.orchestra.framework.model.contexts.InstallationCategory}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Current Versions</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Current Versions</em>' containment reference list.
   * @see com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage#getContext_CurrentVersions()
   * @model containment="true" transient="true"
   * @generated
   */
  EList<InstallationCategory> getCurrentVersions();

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
   * @see com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage#getContext_Description()
   * @model
   * @generated
   */
  String getDescription();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.model.contexts.Context#getDescription <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Description</em>' attribute.
   * @see #getDescription()
   * @generated
   */
  void setDescription(String value);

  /**
	 * Get containing resource as a {@link ContextsResourceImpl}.
	 * @see org.eclipse.emf.ecore.EObject#eResource()
	 */
	ContextsResourceImpl eResource();
} // Context