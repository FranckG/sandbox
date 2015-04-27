/**
 */
package com.thalesgroup.orchestra.framework.gef;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Mproperty</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.Mproperty#getVALUES <em>VALUES</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.Mproperty#getName <em>Name</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.Mproperty#getNature <em>Nature</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.Mproperty#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getMproperty()
 * @model extendedMetaData="name='Mproperty' kind='elementOnly'"
 * @generated
 */
public interface Mproperty extends EObject {
  /**
   * Returns the value of the '<em><b>VALUES</b></em>' containment reference list.
   * The list contents are of type {@link com.thalesgroup.orchestra.framework.gef.MpropertyValue}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>VALUES</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>VALUES</em>' containment reference list.
   * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getMproperty_VALUES()
   * @model containment="true" required="true"
   *        extendedMetaData="kind='element' name='VALUES'"
   * @generated
   */
  EList<MpropertyValue> getVALUES();

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
   * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getMproperty_Name()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
   *        extendedMetaData="kind='attribute' name='name'"
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.gef.Mproperty#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Nature</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Nature</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Nature</em>' attribute.
   * @see #setNature(String)
   * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getMproperty_Nature()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='attribute' name='nature'"
   * @generated
   */
  String getNature();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.gef.Mproperty#getNature <em>Nature</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Nature</em>' attribute.
   * @see #getNature()
   * @generated
   */
  void setNature(String value);

  /**
   * Returns the value of the '<em><b>Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Type</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Type</em>' attribute.
   * @see #setType(String)
   * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getMproperty_Type()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='attribute' name='type'"
   * @generated
   */
  String getType();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.gef.Mproperty#getType <em>Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Type</em>' attribute.
   * @see #getType()
   * @generated
   */
  void setType(String value);

} // Mproperty
