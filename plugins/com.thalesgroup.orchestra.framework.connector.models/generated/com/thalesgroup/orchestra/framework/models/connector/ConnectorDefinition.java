/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.models.connector;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Definition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.models.connector.ConnectorDefinition#getConnector <em>Connector</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.thalesgroup.orchestra.framework.models.connector.ConnectorPackage#getConnectorDefinition()
 * @model extendedMetaData="name='ConnectorDefinition' kind='elementOnly'"
 * @generated
 */
public interface ConnectorDefinition extends EObject {
  /**
   * Returns the value of the '<em><b>Connector</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Connector</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Connector</em>' containment reference.
   * @see #setConnector(Connector)
   * @see com.thalesgroup.orchestra.framework.models.connector.ConnectorPackage#getConnectorDefinition_Connector()
   * @model containment="true" required="true"
   *        extendedMetaData="kind='element' name='connector'"
   * @generated
   */
  Connector getConnector();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.models.connector.ConnectorDefinition#getConnector <em>Connector</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Connector</em>' containment reference.
   * @see #getConnector()
   * @generated
   */
  void setConnector(Connector value);

} // ConnectorDefinition
