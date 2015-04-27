/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.gef;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Element Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.ElementReference#getFullName <em>Full Name</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.ElementReference#getLabel <em>Label</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getElementReference()
 * @model extendedMetaData="name='ElementReference' kind='empty'"
 * @generated
 */
public interface ElementReference extends Reference {
  /**
   * Returns the value of the '<em><b>Full Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Full Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Full Name</em>' attribute.
   * @see #setFullName(String)
   * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getElementReference_FullName()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='attribute' name='fullName'"
   * @generated
   */
  String getFullName();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.gef.ElementReference#getFullName <em>Full Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Full Name</em>' attribute.
   * @see #getFullName()
   * @generated
   */
  void setFullName(String value);

  /**
   * Returns the value of the '<em><b>Label</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Label</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Label</em>' attribute.
   * @see #setLabel(String)
   * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getElementReference_Label()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='attribute' name='label'"
   * @generated
   */
  String getLabel();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.gef.ElementReference#getLabel <em>Label</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Label</em>' attribute.
   * @see #getLabel()
   * @generated
   */
  void setLabel(String value);

} // ElementReference
