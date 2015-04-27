/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.model.contexts;

import org.eclipse.emf.common.util.EList;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Variable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable#isFinal <em>Final</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable#isMultiValued <em>Multi Valued</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable#getValues <em>Values</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage#getAbstractVariable()
 * @model abstract="true"
 * @generated
 */
public interface AbstractVariable extends NamedElement {
  /**
   * Returns the value of the '<em><b>Values</b></em>' containment reference list.
   * The list contents are of type {@link com.thalesgroup.orchestra.framework.model.contexts.VariableValue}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Values</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Values</em>' containment reference list.
   * @see com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage#getAbstractVariable_Values()
   * @model containment="true"
   * @generated
   */
  EList<VariableValue> getValues();

  /**
   * Returns the value of the '<em><b>Final</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Final</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Final</em>' attribute.
   * @see #setFinal(boolean)
   * @see com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage#getAbstractVariable_Final()
   * @model
   * @generated
   */
  boolean isFinal();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable#isFinal <em>Final</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Final</em>' attribute.
   * @see #isFinal()
   * @generated
   */
  void setFinal(boolean value);

  /**
   * Returns the value of the '<em><b>Multi Valued</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Multi Valued</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Multi Valued</em>' attribute.
   * @see #setMultiValued(boolean)
   * @see com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage#getAbstractVariable_MultiValued()
   * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
   * @generated
   */
  boolean isMultiValued();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable#isMultiValued <em>Multi Valued</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Multi Valued</em>' attribute.
   * @see #isMultiValued()
   * @generated
   */
  void setMultiValued(boolean value);

} // AbstractVariable
