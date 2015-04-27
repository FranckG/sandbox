/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.model.contexts;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Referencing Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.ReferencingElement#getReferencePath <em>Reference Path</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.ReferencingElement#getResolvedReference <em>Resolved Reference</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage#getReferencingElement()
 * @model
 * @generated
 */
public interface ReferencingElement extends EObject {
  /**
   * Returns the value of the '<em><b>Reference Path</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Reference Path</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Reference Path</em>' attribute.
   * @see #setReferencePath(String)
   * @see com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage#getReferencingElement_ReferencePath()
   * @model
   * @generated
   */
  String getReferencePath();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.model.contexts.ReferencingElement#getReferencePath <em>Reference Path</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Reference Path</em>' attribute.
   * @see #getReferencePath()
   * @generated
   */
  void setReferencePath(String value);

  /**
   * Returns the value of the '<em><b>Resolved Reference</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Resolved Reference</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Resolved Reference</em>' reference.
   * @see #setResolvedReference(ModelElement)
   * @see com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage#getReferencingElement_ResolvedReference()
   * @model transient="true"
   * @generated
   */
  ModelElement getResolvedReference();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.model.contexts.ReferencingElement#getResolvedReference <em>Resolved Reference</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Resolved Reference</em>' reference.
   * @see #getResolvedReference()
   * @generated
   */
  void setResolvedReference(ModelElement value);

} // ReferencingElement
