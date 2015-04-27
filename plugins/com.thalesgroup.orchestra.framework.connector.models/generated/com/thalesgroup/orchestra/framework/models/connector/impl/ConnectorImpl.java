/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.models.connector.impl;

import com.thalesgroup.orchestra.framework.models.connector.Connector;
import com.thalesgroup.orchestra.framework.models.connector.ConnectorPackage;
import com.thalesgroup.orchestra.framework.models.connector.KeepOpen;
import com.thalesgroup.orchestra.framework.models.connector.LaunchArguments;
import com.thalesgroup.orchestra.framework.models.connector.Service;

import com.thalesgroup.orchestra.framework.models.connector.Type;
import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Connector</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.models.connector.impl.ConnectorImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.models.connector.impl.ConnectorImpl#getUnsupportedService <em>Unsupported Service</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.models.connector.impl.ConnectorImpl#getConnectorId <em>Connector Id</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.models.connector.impl.ConnectorImpl#isMetadataResolver <em>Metadata Resolver</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.models.connector.impl.ConnectorImpl#getProgId <em>Prog Id</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.models.connector.impl.ConnectorImpl#getKeepOpen <em>Keep Open</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.models.connector.impl.ConnectorImpl#getLaunchArguments <em>Launch Arguments</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ConnectorImpl extends EObjectImpl implements Connector {
  /**
   * The cached value of the '{@link #getType() <em>Type</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getType()
   * @generated
   * @ordered
   */
  protected EList<Type> type;

  /**
   * The cached value of the '{@link #getUnsupportedService() <em>Unsupported Service</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getUnsupportedService()
   * @generated
   * @ordered
   */
  protected EList<Service> unsupportedService;

  /**
   * The default value of the '{@link #getConnectorId() <em>Connector Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getConnectorId()
   * @generated
   * @ordered
   */
  protected static final String CONNECTOR_ID_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getConnectorId() <em>Connector Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getConnectorId()
   * @generated
   * @ordered
   */
  protected String connectorId = CONNECTOR_ID_EDEFAULT;

  /**
   * The default value of the '{@link #isMetadataResolver() <em>Metadata Resolver</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isMetadataResolver()
   * @generated
   * @ordered
   */
  protected static final boolean METADATA_RESOLVER_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isMetadataResolver() <em>Metadata Resolver</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isMetadataResolver()
   * @generated
   * @ordered
   */
  protected boolean metadataResolver = METADATA_RESOLVER_EDEFAULT;

  /**
   * This is true if the Metadata Resolver attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean metadataResolverESet;

  /**
   * The default value of the '{@link #getProgId() <em>Prog Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getProgId()
   * @generated
   * @ordered
   */
  protected static final String PROG_ID_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getProgId() <em>Prog Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getProgId()
   * @generated
   * @ordered
   */
  protected String progId = PROG_ID_EDEFAULT;

  /**
   * The cached value of the '{@link #getKeepOpen() <em>Keep Open</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getKeepOpen()
   * @generated
   * @ordered
   */
  protected KeepOpen keepOpen;

  /**
   * The cached value of the '{@link #getLaunchArguments() <em>Launch Arguments</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLaunchArguments()
   * @generated
   * @ordered
   */
  protected LaunchArguments launchArguments;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ConnectorImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return ConnectorPackage.Literals.CONNECTOR;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Service> getUnsupportedService() {
    if (unsupportedService == null) {
      unsupportedService = new EObjectContainmentEList<Service>(Service.class, this, ConnectorPackage.CONNECTOR__UNSUPPORTED_SERVICE);
    }
    return unsupportedService;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getConnectorId() {
    return connectorId;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setConnectorId(String newConnectorId) {
    String oldConnectorId = connectorId;
    connectorId = newConnectorId;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ConnectorPackage.CONNECTOR__CONNECTOR_ID, oldConnectorId, connectorId));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isMetadataResolver() {
    return metadataResolver;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setMetadataResolver(boolean newMetadataResolver) {
    boolean oldMetadataResolver = metadataResolver;
    metadataResolver = newMetadataResolver;
    boolean oldMetadataResolverESet = metadataResolverESet;
    metadataResolverESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ConnectorPackage.CONNECTOR__METADATA_RESOLVER, oldMetadataResolver, metadataResolver, !oldMetadataResolverESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetMetadataResolver() {
    boolean oldMetadataResolver = metadataResolver;
    boolean oldMetadataResolverESet = metadataResolverESet;
    metadataResolver = METADATA_RESOLVER_EDEFAULT;
    metadataResolverESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, ConnectorPackage.CONNECTOR__METADATA_RESOLVER, oldMetadataResolver, METADATA_RESOLVER_EDEFAULT, oldMetadataResolverESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetMetadataResolver() {
    return metadataResolverESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getProgId() {
    return progId;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setProgId(String newProgId) {
    String oldProgId = progId;
    progId = newProgId;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ConnectorPackage.CONNECTOR__PROG_ID, oldProgId, progId));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public KeepOpen getKeepOpen() {
    return keepOpen;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetKeepOpen(KeepOpen newKeepOpen, NotificationChain msgs) {
    KeepOpen oldKeepOpen = keepOpen;
    keepOpen = newKeepOpen;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ConnectorPackage.CONNECTOR__KEEP_OPEN, oldKeepOpen, newKeepOpen);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setKeepOpen(KeepOpen newKeepOpen) {
    if (newKeepOpen != keepOpen) {
      NotificationChain msgs = null;
      if (keepOpen != null)
        msgs = ((InternalEObject)keepOpen).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ConnectorPackage.CONNECTOR__KEEP_OPEN, null, msgs);
      if (newKeepOpen != null)
        msgs = ((InternalEObject)newKeepOpen).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ConnectorPackage.CONNECTOR__KEEP_OPEN, null, msgs);
      msgs = basicSetKeepOpen(newKeepOpen, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ConnectorPackage.CONNECTOR__KEEP_OPEN, newKeepOpen, newKeepOpen));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LaunchArguments getLaunchArguments() {
    return launchArguments;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetLaunchArguments(LaunchArguments newLaunchArguments, NotificationChain msgs) {
    LaunchArguments oldLaunchArguments = launchArguments;
    launchArguments = newLaunchArguments;
    if (eNotificationRequired()) {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ConnectorPackage.CONNECTOR__LAUNCH_ARGUMENTS, oldLaunchArguments, newLaunchArguments);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setLaunchArguments(LaunchArguments newLaunchArguments) {
    if (newLaunchArguments != launchArguments) {
      NotificationChain msgs = null;
      if (launchArguments != null)
        msgs = ((InternalEObject)launchArguments).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ConnectorPackage.CONNECTOR__LAUNCH_ARGUMENTS, null, msgs);
      if (newLaunchArguments != null)
        msgs = ((InternalEObject)newLaunchArguments).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ConnectorPackage.CONNECTOR__LAUNCH_ARGUMENTS, null, msgs);
      msgs = basicSetLaunchArguments(newLaunchArguments, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ConnectorPackage.CONNECTOR__LAUNCH_ARGUMENTS, newLaunchArguments, newLaunchArguments));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Type> getType() {
    if (type == null) {
      type = new EObjectContainmentEList<Type>(Type.class, this, ConnectorPackage.CONNECTOR__TYPE);
    }
    return type;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case ConnectorPackage.CONNECTOR__TYPE:
        return ((InternalEList<?>)getType()).basicRemove(otherEnd, msgs);
      case ConnectorPackage.CONNECTOR__UNSUPPORTED_SERVICE:
        return ((InternalEList<?>)getUnsupportedService()).basicRemove(otherEnd, msgs);
      case ConnectorPackage.CONNECTOR__KEEP_OPEN:
        return basicSetKeepOpen(null, msgs);
      case ConnectorPackage.CONNECTOR__LAUNCH_ARGUMENTS:
        return basicSetLaunchArguments(null, msgs);
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
      case ConnectorPackage.CONNECTOR__TYPE:
        return getType();
      case ConnectorPackage.CONNECTOR__UNSUPPORTED_SERVICE:
        return getUnsupportedService();
      case ConnectorPackage.CONNECTOR__CONNECTOR_ID:
        return getConnectorId();
      case ConnectorPackage.CONNECTOR__METADATA_RESOLVER:
        return isMetadataResolver();
      case ConnectorPackage.CONNECTOR__PROG_ID:
        return getProgId();
      case ConnectorPackage.CONNECTOR__KEEP_OPEN:
        return getKeepOpen();
      case ConnectorPackage.CONNECTOR__LAUNCH_ARGUMENTS:
        return getLaunchArguments();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue) {
    switch (featureID) {
      case ConnectorPackage.CONNECTOR__TYPE:
        getType().clear();
        getType().addAll((Collection<? extends Type>)newValue);
        return;
      case ConnectorPackage.CONNECTOR__UNSUPPORTED_SERVICE:
        getUnsupportedService().clear();
        getUnsupportedService().addAll((Collection<? extends Service>)newValue);
        return;
      case ConnectorPackage.CONNECTOR__CONNECTOR_ID:
        setConnectorId((String)newValue);
        return;
      case ConnectorPackage.CONNECTOR__METADATA_RESOLVER:
        setMetadataResolver((Boolean)newValue);
        return;
      case ConnectorPackage.CONNECTOR__PROG_ID:
        setProgId((String)newValue);
        return;
      case ConnectorPackage.CONNECTOR__KEEP_OPEN:
        setKeepOpen((KeepOpen)newValue);
        return;
      case ConnectorPackage.CONNECTOR__LAUNCH_ARGUMENTS:
        setLaunchArguments((LaunchArguments)newValue);
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
      case ConnectorPackage.CONNECTOR__TYPE:
        getType().clear();
        return;
      case ConnectorPackage.CONNECTOR__UNSUPPORTED_SERVICE:
        getUnsupportedService().clear();
        return;
      case ConnectorPackage.CONNECTOR__CONNECTOR_ID:
        setConnectorId(CONNECTOR_ID_EDEFAULT);
        return;
      case ConnectorPackage.CONNECTOR__METADATA_RESOLVER:
        unsetMetadataResolver();
        return;
      case ConnectorPackage.CONNECTOR__PROG_ID:
        setProgId(PROG_ID_EDEFAULT);
        return;
      case ConnectorPackage.CONNECTOR__KEEP_OPEN:
        setKeepOpen((KeepOpen)null);
        return;
      case ConnectorPackage.CONNECTOR__LAUNCH_ARGUMENTS:
        setLaunchArguments((LaunchArguments)null);
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
      case ConnectorPackage.CONNECTOR__TYPE:
        return type != null && !type.isEmpty();
      case ConnectorPackage.CONNECTOR__UNSUPPORTED_SERVICE:
        return unsupportedService != null && !unsupportedService.isEmpty();
      case ConnectorPackage.CONNECTOR__CONNECTOR_ID:
        return CONNECTOR_ID_EDEFAULT == null ? connectorId != null : !CONNECTOR_ID_EDEFAULT.equals(connectorId);
      case ConnectorPackage.CONNECTOR__METADATA_RESOLVER:
        return isSetMetadataResolver();
      case ConnectorPackage.CONNECTOR__PROG_ID:
        return PROG_ID_EDEFAULT == null ? progId != null : !PROG_ID_EDEFAULT.equals(progId);
      case ConnectorPackage.CONNECTOR__KEEP_OPEN:
        return keepOpen != null;
      case ConnectorPackage.CONNECTOR__LAUNCH_ARGUMENTS:
        return launchArguments != null;
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
    result.append(" (connectorId: ");
    result.append(connectorId);
    result.append(", metadataResolver: ");
    if (metadataResolverESet) result.append(metadataResolver); else result.append("<unset>");
    result.append(", progId: ");
    result.append(progId);
    result.append(')');
    return result.toString();
  }

} //ConnectorImpl
