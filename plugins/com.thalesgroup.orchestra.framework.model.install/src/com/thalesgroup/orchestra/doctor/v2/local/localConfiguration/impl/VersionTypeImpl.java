/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl;

import com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.CompatibilityType;
import com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ConfigurationPackage;
import com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ParameterType;
import com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionType;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Version Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.VersionTypeImpl#getParameter <em>Parameter</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.VersionTypeImpl#getCompatibility <em>Compatibility</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.VersionTypeImpl#getConfigConnector <em>Config Connector</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.VersionTypeImpl#getExecName <em>Exec Name</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.VersionTypeImpl#getExecRelativePath <em>Exec Relative Path</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.VersionTypeImpl#getIconPath <em>Icon Path</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.VersionTypeImpl#getInstallLocation <em>Install Location</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.VersionTypeImpl#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VersionTypeImpl extends EObjectImpl implements VersionType {
  /**
   * The cached value of the '{@link #getParameter() <em>Parameter</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getParameter()
   * @generated
   * @ordered
   */
  protected EList<ParameterType> parameter;

  /**
   * The cached value of the '{@link #getCompatibility() <em>Compatibility</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCompatibility()
   * @generated
   * @ordered
   */
  protected EList<CompatibilityType> compatibility;

  /**
   * The default value of the '{@link #getConfigConnector() <em>Config Connector</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getConfigConnector()
   * @generated
   * @ordered
   */
  protected static final String CONFIG_CONNECTOR_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getConfigConnector() <em>Config Connector</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getConfigConnector()
   * @generated
   * @ordered
   */
  protected String configConnector = CONFIG_CONNECTOR_EDEFAULT;

  /**
   * The default value of the '{@link #getExecName() <em>Exec Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExecName()
   * @generated
   * @ordered
   */
  protected static final String EXEC_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getExecName() <em>Exec Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExecName()
   * @generated
   * @ordered
   */
  protected String execName = EXEC_NAME_EDEFAULT;

  /**
   * The default value of the '{@link #getExecRelativePath() <em>Exec Relative Path</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExecRelativePath()
   * @generated
   * @ordered
   */
  protected static final String EXEC_RELATIVE_PATH_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getExecRelativePath() <em>Exec Relative Path</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExecRelativePath()
   * @generated
   * @ordered
   */
  protected String execRelativePath = EXEC_RELATIVE_PATH_EDEFAULT;

  /**
   * The default value of the '{@link #getIconPath() <em>Icon Path</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getIconPath()
   * @generated
   * @ordered
   */
  protected static final String ICON_PATH_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getIconPath() <em>Icon Path</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getIconPath()
   * @generated
   * @ordered
   */
  protected String iconPath = ICON_PATH_EDEFAULT;

  /**
   * The default value of the '{@link #getInstallLocation() <em>Install Location</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInstallLocation()
   * @generated
   * @ordered
   */
  protected static final String INSTALL_LOCATION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getInstallLocation() <em>Install Location</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInstallLocation()
   * @generated
   * @ordered
   */
  protected String installLocation = INSTALL_LOCATION_EDEFAULT;

  /**
   * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getValue()
   * @generated
   * @ordered
   */
  protected static final String VALUE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getValue()
   * @generated
   * @ordered
   */
  protected String value = VALUE_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected VersionTypeImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return ConfigurationPackage.Literals.VERSION_TYPE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<ParameterType> getParameter() {
    if (parameter == null) {
      parameter = new EObjectContainmentEList<ParameterType>(ParameterType.class, this, ConfigurationPackage.VERSION_TYPE__PARAMETER);
    }
    return parameter;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<CompatibilityType> getCompatibility() {
    if (compatibility == null) {
      compatibility = new EObjectContainmentEList<CompatibilityType>(CompatibilityType.class, this, ConfigurationPackage.VERSION_TYPE__COMPATIBILITY);
    }
    return compatibility;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getConfigConnector() {
    return configConnector;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setConfigConnector(String newConfigConnector) {
    String oldConfigConnector = configConnector;
    configConnector = newConfigConnector;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.VERSION_TYPE__CONFIG_CONNECTOR, oldConfigConnector, configConnector));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getExecName() {
    return execName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setExecName(String newExecName) {
    String oldExecName = execName;
    execName = newExecName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.VERSION_TYPE__EXEC_NAME, oldExecName, execName));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getExecRelativePath() {
    return execRelativePath;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setExecRelativePath(String newExecRelativePath) {
    String oldExecRelativePath = execRelativePath;
    execRelativePath = newExecRelativePath;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.VERSION_TYPE__EXEC_RELATIVE_PATH, oldExecRelativePath, execRelativePath));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getIconPath() {
    return iconPath;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setIconPath(String newIconPath) {
    String oldIconPath = iconPath;
    iconPath = newIconPath;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.VERSION_TYPE__ICON_PATH, oldIconPath, iconPath));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getInstallLocation() {
    return installLocation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setInstallLocation(String newInstallLocation) {
    String oldInstallLocation = installLocation;
    installLocation = newInstallLocation;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.VERSION_TYPE__INSTALL_LOCATION, oldInstallLocation, installLocation));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getValue() {
    return value;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setValue(String newValue) {
    String oldValue = value;
    value = newValue;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.VERSION_TYPE__VALUE, oldValue, value));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case ConfigurationPackage.VERSION_TYPE__PARAMETER:
        return ((InternalEList<?>)getParameter()).basicRemove(otherEnd, msgs);
      case ConfigurationPackage.VERSION_TYPE__COMPATIBILITY:
        return ((InternalEList<?>)getCompatibility()).basicRemove(otherEnd, msgs);
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
      case ConfigurationPackage.VERSION_TYPE__PARAMETER:
        return getParameter();
      case ConfigurationPackage.VERSION_TYPE__COMPATIBILITY:
        return getCompatibility();
      case ConfigurationPackage.VERSION_TYPE__CONFIG_CONNECTOR:
        return getConfigConnector();
      case ConfigurationPackage.VERSION_TYPE__EXEC_NAME:
        return getExecName();
      case ConfigurationPackage.VERSION_TYPE__EXEC_RELATIVE_PATH:
        return getExecRelativePath();
      case ConfigurationPackage.VERSION_TYPE__ICON_PATH:
        return getIconPath();
      case ConfigurationPackage.VERSION_TYPE__INSTALL_LOCATION:
        return getInstallLocation();
      case ConfigurationPackage.VERSION_TYPE__VALUE:
        return getValue();
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
      case ConfigurationPackage.VERSION_TYPE__PARAMETER:
        getParameter().clear();
        getParameter().addAll((Collection<? extends ParameterType>)newValue);
        return;
      case ConfigurationPackage.VERSION_TYPE__COMPATIBILITY:
        getCompatibility().clear();
        getCompatibility().addAll((Collection<? extends CompatibilityType>)newValue);
        return;
      case ConfigurationPackage.VERSION_TYPE__CONFIG_CONNECTOR:
        setConfigConnector((String)newValue);
        return;
      case ConfigurationPackage.VERSION_TYPE__EXEC_NAME:
        setExecName((String)newValue);
        return;
      case ConfigurationPackage.VERSION_TYPE__EXEC_RELATIVE_PATH:
        setExecRelativePath((String)newValue);
        return;
      case ConfigurationPackage.VERSION_TYPE__ICON_PATH:
        setIconPath((String)newValue);
        return;
      case ConfigurationPackage.VERSION_TYPE__INSTALL_LOCATION:
        setInstallLocation((String)newValue);
        return;
      case ConfigurationPackage.VERSION_TYPE__VALUE:
        setValue((String)newValue);
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
      case ConfigurationPackage.VERSION_TYPE__PARAMETER:
        getParameter().clear();
        return;
      case ConfigurationPackage.VERSION_TYPE__COMPATIBILITY:
        getCompatibility().clear();
        return;
      case ConfigurationPackage.VERSION_TYPE__CONFIG_CONNECTOR:
        setConfigConnector(CONFIG_CONNECTOR_EDEFAULT);
        return;
      case ConfigurationPackage.VERSION_TYPE__EXEC_NAME:
        setExecName(EXEC_NAME_EDEFAULT);
        return;
      case ConfigurationPackage.VERSION_TYPE__EXEC_RELATIVE_PATH:
        setExecRelativePath(EXEC_RELATIVE_PATH_EDEFAULT);
        return;
      case ConfigurationPackage.VERSION_TYPE__ICON_PATH:
        setIconPath(ICON_PATH_EDEFAULT);
        return;
      case ConfigurationPackage.VERSION_TYPE__INSTALL_LOCATION:
        setInstallLocation(INSTALL_LOCATION_EDEFAULT);
        return;
      case ConfigurationPackage.VERSION_TYPE__VALUE:
        setValue(VALUE_EDEFAULT);
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
      case ConfigurationPackage.VERSION_TYPE__PARAMETER:
        return parameter != null && !parameter.isEmpty();
      case ConfigurationPackage.VERSION_TYPE__COMPATIBILITY:
        return compatibility != null && !compatibility.isEmpty();
      case ConfigurationPackage.VERSION_TYPE__CONFIG_CONNECTOR:
        return CONFIG_CONNECTOR_EDEFAULT == null ? configConnector != null : !CONFIG_CONNECTOR_EDEFAULT.equals(configConnector);
      case ConfigurationPackage.VERSION_TYPE__EXEC_NAME:
        return EXEC_NAME_EDEFAULT == null ? execName != null : !EXEC_NAME_EDEFAULT.equals(execName);
      case ConfigurationPackage.VERSION_TYPE__EXEC_RELATIVE_PATH:
        return EXEC_RELATIVE_PATH_EDEFAULT == null ? execRelativePath != null : !EXEC_RELATIVE_PATH_EDEFAULT.equals(execRelativePath);
      case ConfigurationPackage.VERSION_TYPE__ICON_PATH:
        return ICON_PATH_EDEFAULT == null ? iconPath != null : !ICON_PATH_EDEFAULT.equals(iconPath);
      case ConfigurationPackage.VERSION_TYPE__INSTALL_LOCATION:
        return INSTALL_LOCATION_EDEFAULT == null ? installLocation != null : !INSTALL_LOCATION_EDEFAULT.equals(installLocation);
      case ConfigurationPackage.VERSION_TYPE__VALUE:
        return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
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
    result.append(" (configConnector: ");
    result.append(configConnector);
    result.append(", execName: ");
    result.append(execName);
    result.append(", execRelativePath: ");
    result.append(execRelativePath);
    result.append(", iconPath: ");
    result.append(iconPath);
    result.append(", installLocation: ");
    result.append(installLocation);
    result.append(", value: ");
    result.append(value);
    result.append(')');
    return result.toString();
  }

} //VersionTypeImpl
