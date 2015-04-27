/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.models.connector.impl;

import com.thalesgroup.orchestra.framework.models.connector.Connector;
import com.thalesgroup.orchestra.framework.models.connector.ConnectorDefinition;
import com.thalesgroup.orchestra.framework.models.connector.ConnectorPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Definition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.models.connector.impl.ConnectorDefinitionImpl#getConnector <em>Connector</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ConnectorDefinitionImpl extends EObjectImpl implements ConnectorDefinition {
  /**
   * The cached value of the '{@link #getConnector() <em>Connector</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getConnector()
   * @generated
   * @ordered
   */
  protected Connector connector;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ConnectorDefinitionImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return ConnectorPackage.Literals.CONNECTOR_DEFINITION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Connector getConnector() {
    return connector;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetConnector(Connector newConnector, NotificationChain msgs) {
    Connector oldConnector = connector;
    connector = newConnector;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ConnectorPackage.CONNECTOR_DEFINITION__CONNECTOR, oldConnector, newConnector);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setConnector(Connector newConnector) {
    if (newConnector != connector) {
      NotificationChain msgs = null;
      if (connector != null)
        msgs = ((InternalEObject)connector).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ConnectorPackage.CONNECTOR_DEFINITION__CONNECTOR, null, msgs);
      if (newConnector != null)
        msgs = ((InternalEObject)newConnector).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ConnectorPackage.CONNECTOR_DEFINITION__CONNECTOR, null, msgs);
      msgs = basicSetConnector(newConnector, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ConnectorPackage.CONNECTOR_DEFINITION__CONNECTOR, newConnector, newConnector));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case ConnectorPackage.CONNECTOR_DEFINITION__CONNECTOR:
        return basicSetConnector(null, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case ConnectorPackage.CONNECTOR_DEFINITION__CONNECTOR:
        return getConnector();
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
      case ConnectorPackage.CONNECTOR_DEFINITION__CONNECTOR:
        setConnector((Connector)newValue);
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
      case ConnectorPackage.CONNECTOR_DEFINITION__CONNECTOR:
        setConnector((Connector)null);
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
      case ConnectorPackage.CONNECTOR_DEFINITION__CONNECTOR:
        return connector != null;
    }
    return super.eIsSet(featureID);
  }

} //ConnectorDefinitionImpl
