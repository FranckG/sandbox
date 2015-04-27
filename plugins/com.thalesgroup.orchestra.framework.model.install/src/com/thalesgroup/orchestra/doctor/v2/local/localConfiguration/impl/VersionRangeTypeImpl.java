/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl;

import com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ConfigurationPackage;
import com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionRangeType;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Version Range Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.VersionRangeTypeImpl#getCompatType <em>Compat Type</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.VersionRangeTypeImpl#getRange <em>Range</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VersionRangeTypeImpl extends EObjectImpl implements VersionRangeType {
  /**
   * The default value of the '{@link #getCompatType() <em>Compat Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCompatType()
   * @generated
   * @ordered
   */
  protected static final String COMPAT_TYPE_EDEFAULT = "all";

  /**
   * The cached value of the '{@link #getCompatType() <em>Compat Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCompatType()
   * @generated
   * @ordered
   */
  protected String compatType = COMPAT_TYPE_EDEFAULT;

  /**
   * This is true if the Compat Type attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean compatTypeESet;

  /**
   * The default value of the '{@link #getRange() <em>Range</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRange()
   * @generated
   * @ordered
   */
  protected static final String RANGE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getRange() <em>Range</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRange()
   * @generated
   * @ordered
   */
  protected String range = RANGE_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected VersionRangeTypeImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return ConfigurationPackage.Literals.VERSION_RANGE_TYPE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getCompatType() {
    return compatType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setCompatType(String newCompatType) {
    String oldCompatType = compatType;
    compatType = newCompatType;
    boolean oldCompatTypeESet = compatTypeESet;
    compatTypeESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.VERSION_RANGE_TYPE__COMPAT_TYPE, oldCompatType, compatType, !oldCompatTypeESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetCompatType() {
    String oldCompatType = compatType;
    boolean oldCompatTypeESet = compatTypeESet;
    compatType = COMPAT_TYPE_EDEFAULT;
    compatTypeESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, ConfigurationPackage.VERSION_RANGE_TYPE__COMPAT_TYPE, oldCompatType, COMPAT_TYPE_EDEFAULT, oldCompatTypeESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetCompatType() {
    return compatTypeESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getRange() {
    return range;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setRange(String newRange) {
    String oldRange = range;
    range = newRange;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.VERSION_RANGE_TYPE__RANGE, oldRange, range));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case ConfigurationPackage.VERSION_RANGE_TYPE__COMPAT_TYPE:
        return getCompatType();
      case ConfigurationPackage.VERSION_RANGE_TYPE__RANGE:
        return getRange();
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
      case ConfigurationPackage.VERSION_RANGE_TYPE__COMPAT_TYPE:
        setCompatType((String)newValue);
        return;
      case ConfigurationPackage.VERSION_RANGE_TYPE__RANGE:
        setRange((String)newValue);
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
      case ConfigurationPackage.VERSION_RANGE_TYPE__COMPAT_TYPE:
        unsetCompatType();
        return;
      case ConfigurationPackage.VERSION_RANGE_TYPE__RANGE:
        setRange(RANGE_EDEFAULT);
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
      case ConfigurationPackage.VERSION_RANGE_TYPE__COMPAT_TYPE:
        return isSetCompatType();
      case ConfigurationPackage.VERSION_RANGE_TYPE__RANGE:
        return RANGE_EDEFAULT == null ? range != null : !RANGE_EDEFAULT.equals(range);
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
    result.append(" (compatType: ");
    if (compatTypeESet) result.append(compatType); else result.append("<unset>");
    result.append(", range: ");
    result.append(range);
    result.append(')');
    return result.toString();
  }

} //VersionRangeTypeImpl
