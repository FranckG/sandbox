/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.model.contexts;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Variable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.Variable#isMandatory <em>Mandatory</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.Variable#getDescription <em>Description</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage#getVariable()
 * @model
 * @generated
 */
public interface Variable extends AbstractVariable, ContributedElement {
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
   * @see com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage#getVariable_Description()
   * @model
   * @generated
   */
	String getDescription();

	/**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.model.contexts.Variable#getDescription <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Description</em>' attribute.
   * @see #getDescription()
   * @generated
   */
	void setDescription(String value);

	/**
   * Returns the value of the '<em><b>Mandatory</b></em>' attribute.
   * The default value is <code>"false"</code>.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mandatory</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Mandatory</em>' attribute.
   * @see #setMandatory(boolean)
   * @see com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage#getVariable_Mandatory()
   * @model default="false" required="true"
   * @generated
   */
	boolean isMandatory();

	/**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.model.contexts.Variable#isMandatory <em>Mandatory</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Mandatory</em>' attribute.
   * @see #isMandatory()
   * @generated
   */
	void setMandatory(boolean value);

} // Variable
