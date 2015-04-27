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
 * A representation of the model object '<em><b>Service</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.models.connector.Service#getServiceName <em>Service Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.thalesgroup.orchestra.framework.models.connector.ConnectorPackage#getService()
 * @model extendedMetaData="name='Service' kind='empty'"
 * @generated
 */
public interface Service extends EObject {
  /**
   * Returns the value of the '<em><b>Service Name</b></em>' attribute.
   * The literals are from the enumeration {@link com.thalesgroup.orchestra.framework.models.connector.ServiceNameType}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Service Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Service Name</em>' attribute.
   * @see com.thalesgroup.orchestra.framework.models.connector.ServiceNameType
   * @see #isSetServiceName()
   * @see #unsetServiceName()
   * @see #setServiceName(ServiceNameType)
   * @see com.thalesgroup.orchestra.framework.models.connector.ConnectorPackage#getService_ServiceName()
   * @model unsettable="true" required="true"
   *        extendedMetaData="kind='attribute' name='serviceName'"
   * @generated
   */
  ServiceNameType getServiceName();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.models.connector.Service#getServiceName <em>Service Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Service Name</em>' attribute.
   * @see com.thalesgroup.orchestra.framework.models.connector.ServiceNameType
   * @see #isSetServiceName()
   * @see #unsetServiceName()
   * @see #getServiceName()
   * @generated
   */
  void setServiceName(ServiceNameType value);

  /**
   * Unsets the value of the '{@link com.thalesgroup.orchestra.framework.models.connector.Service#getServiceName <em>Service Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetServiceName()
   * @see #getServiceName()
   * @see #setServiceName(ServiceNameType)
   * @generated
   */
  void unsetServiceName();

  /**
   * Returns whether the value of the '{@link com.thalesgroup.orchestra.framework.models.connector.Service#getServiceName <em>Service Name</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Service Name</em>' attribute is set.
   * @see #unsetServiceName()
   * @see #getServiceName()
   * @see #setServiceName(ServiceNameType)
   * @generated
   */
  boolean isSetServiceName();

} // Service
