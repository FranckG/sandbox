/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.gef;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.Reference#getLinkDirection <em>Link Direction</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.Reference#getLinkType <em>Link Type</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.Reference#getUri <em>Uri</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getReference()
 * @model extendedMetaData="name='Reference' kind='empty'"
 * @generated
 */
public interface Reference extends EObject {
  /**
   * Returns the value of the '<em><b>Link Direction</b></em>' attribute.
   * The literals are from the enumeration {@link com.thalesgroup.orchestra.framework.gef.LinkDirection}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Link Direction</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Link Direction</em>' attribute.
   * @see com.thalesgroup.orchestra.framework.gef.LinkDirection
   * @see #isSetLinkDirection()
   * @see #unsetLinkDirection()
   * @see #setLinkDirection(LinkDirection)
   * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getReference_LinkDirection()
   * @model unsettable="true"
   *        extendedMetaData="kind='attribute' name='linkDirection'"
   * @generated
   */
  LinkDirection getLinkDirection();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.gef.Reference#getLinkDirection <em>Link Direction</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Link Direction</em>' attribute.
   * @see com.thalesgroup.orchestra.framework.gef.LinkDirection
   * @see #isSetLinkDirection()
   * @see #unsetLinkDirection()
   * @see #getLinkDirection()
   * @generated
   */
  void setLinkDirection(LinkDirection value);

  /**
   * Unsets the value of the '{@link com.thalesgroup.orchestra.framework.gef.Reference#getLinkDirection <em>Link Direction</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetLinkDirection()
   * @see #getLinkDirection()
   * @see #setLinkDirection(LinkDirection)
   * @generated
   */
  void unsetLinkDirection();

  /**
   * Returns whether the value of the '{@link com.thalesgroup.orchestra.framework.gef.Reference#getLinkDirection <em>Link Direction</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Link Direction</em>' attribute is set.
   * @see #unsetLinkDirection()
   * @see #getLinkDirection()
   * @see #setLinkDirection(LinkDirection)
   * @generated
   */
  boolean isSetLinkDirection();

  /**
   * Returns the value of the '<em><b>Link Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Link Type</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Link Type</em>' attribute.
   * @see #setLinkType(String)
   * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getReference_LinkType()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='attribute' name='linkType'"
   * @generated
   */
  String getLinkType();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.gef.Reference#getLinkType <em>Link Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Link Type</em>' attribute.
   * @see #getLinkType()
   * @generated
   */
  void setLinkType(String value);

  /**
   * Returns the value of the '<em><b>Uri</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Uri</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Uri</em>' attribute.
   * @see #setUri(String)
   * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getReference_Uri()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
   *        extendedMetaData="kind='attribute' name='uri'"
   * @generated
   */
  String getUri();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.gef.Reference#getUri <em>Uri</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Uri</em>' attribute.
   * @see #getUri()
   * @generated
   */
  void setUri(String value);

} // Reference
