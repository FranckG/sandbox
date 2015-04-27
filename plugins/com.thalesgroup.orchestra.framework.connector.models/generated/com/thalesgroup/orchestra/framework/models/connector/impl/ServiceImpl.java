/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.models.connector.impl;

import com.thalesgroup.orchestra.framework.models.connector.ConnectorPackage;
import com.thalesgroup.orchestra.framework.models.connector.Service;
import com.thalesgroup.orchestra.framework.models.connector.ServiceNameType;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Service</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.models.connector.impl.ServiceImpl#getServiceName <em>Service Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ServiceImpl extends EObjectImpl implements Service {
  /**
   * The default value of the '{@link #getServiceName() <em>Service Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getServiceName()
   * @generated
   * @ordered
   */
  protected static final ServiceNameType SERVICE_NAME_EDEFAULT = ServiceNameType.CREATE;

  /**
   * The cached value of the '{@link #getServiceName() <em>Service Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getServiceName()
   * @generated
   * @ordered
   */
  protected ServiceNameType serviceName = SERVICE_NAME_EDEFAULT;

  /**
   * This is true if the Service Name attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean serviceNameESet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ServiceImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return ConnectorPackage.Literals.SERVICE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ServiceNameType getServiceName() {
    return serviceName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setServiceName(ServiceNameType newServiceName) {
    ServiceNameType oldServiceName = serviceName;
    serviceName = newServiceName == null ? SERVICE_NAME_EDEFAULT : newServiceName;
    boolean oldServiceNameESet = serviceNameESet;
    serviceNameESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ConnectorPackage.SERVICE__SERVICE_NAME, oldServiceName, serviceName, !oldServiceNameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetServiceName() {
    ServiceNameType oldServiceName = serviceName;
    boolean oldServiceNameESet = serviceNameESet;
    serviceName = SERVICE_NAME_EDEFAULT;
    serviceNameESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, ConnectorPackage.SERVICE__SERVICE_NAME, oldServiceName, SERVICE_NAME_EDEFAULT, oldServiceNameESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetServiceName() {
    return serviceNameESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case ConnectorPackage.SERVICE__SERVICE_NAME:
        return getServiceName();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue) {
    switch (featureID) {
      case ConnectorPackage.SERVICE__SERVICE_NAME:
        setServiceName((ServiceNameType)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID) {
    switch (featureID) {
      case ConnectorPackage.SERVICE__SERVICE_NAME:
        unsetServiceName();
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID) {
    switch (featureID) {
      case ConnectorPackage.SERVICE__SERVICE_NAME:
        return isSetServiceName();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString() {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (serviceName: ");
    if (serviceNameESet) result.append(serviceName); else result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //ServiceImpl
