/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.models.connector;

import java.math.BigInteger;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.models.connector.Type#getName <em>Name</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.models.connector.Type#getTimeout <em>Timeout</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.thalesgroup.orchestra.framework.models.connector.ConnectorPackage#getType()
 * @model extendedMetaData="name='Type' kind='empty'"
 * @generated
 */
public interface Type extends EObject {
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
   * @see com.thalesgroup.orchestra.framework.models.connector.ConnectorPackage#getType_Name()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
   *        extendedMetaData="kind='attribute' name='name'"
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.models.connector.Type#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Timeout</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Timeout</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Timeout</em>' attribute.
   * @see #setTimeout(BigInteger)
   * @see com.thalesgroup.orchestra.framework.models.connector.ConnectorPackage#getType_Timeout()
   * @model dataType="org.eclipse.emf.ecore.xml.type.Integer"
   *        extendedMetaData="kind='attribute' name='timeout'"
   * @generated
   */
  BigInteger getTimeout();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.models.connector.Type#getTimeout <em>Timeout</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Timeout</em>' attribute.
   * @see #getTimeout()
   * @generated
   */
  void setTimeout(BigInteger value);

} // Type
