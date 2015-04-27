/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl;

import com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.CompatibilityType;
import com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ConfigurationPackage;
import com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionRangeType;

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
 * An implementation of the model object '<em><b>Compatibility Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.CompatibilityTypeImpl#getVersionRange <em>Version Range</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.CompatibilityTypeImpl#getTargetComponent <em>Target Component</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CompatibilityTypeImpl extends EObjectImpl implements CompatibilityType {
  /**
   * The cached value of the '{@link #getVersionRange() <em>Version Range</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getVersionRange()
   * @generated
   * @ordered
   */
  protected EList<VersionRangeType> versionRange;

  /**
   * The default value of the '{@link #getTargetComponent() <em>Target Component</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTargetComponent()
   * @generated
   * @ordered
   */
  protected static final String TARGET_COMPONENT_EDEFAULT = null;
  /**
   * The cached value of the '{@link #getTargetComponent() <em>Target Component</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTargetComponent()
   * @generated
   * @ordered
   */
  protected String targetComponent = TARGET_COMPONENT_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected CompatibilityTypeImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return ConfigurationPackage.Literals.COMPATIBILITY_TYPE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<VersionRangeType> getVersionRange() {
    if (versionRange == null) {
      versionRange = new EObjectContainmentEList<VersionRangeType>(VersionRangeType.class, this, ConfigurationPackage.COMPATIBILITY_TYPE__VERSION_RANGE);
    }
    return versionRange;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getTargetComponent() {
    return targetComponent;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setTargetComponent(String newTargetComponent) {
    String oldTargetComponent = targetComponent;
    targetComponent = newTargetComponent;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.COMPATIBILITY_TYPE__TARGET_COMPONENT, oldTargetComponent, targetComponent));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case ConfigurationPackage.COMPATIBILITY_TYPE__VERSION_RANGE:
        return ((InternalEList<?>)getVersionRange()).basicRemove(otherEnd, msgs);
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
      case ConfigurationPackage.COMPATIBILITY_TYPE__VERSION_RANGE:
        return getVersionRange();
      case ConfigurationPackage.COMPATIBILITY_TYPE__TARGET_COMPONENT:
        return getTargetComponent();
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
      case ConfigurationPackage.COMPATIBILITY_TYPE__VERSION_RANGE:
        getVersionRange().clear();
        getVersionRange().addAll((Collection<? extends VersionRangeType>)newValue);
        return;
      case ConfigurationPackage.COMPATIBILITY_TYPE__TARGET_COMPONENT:
        setTargetComponent((String)newValue);
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
      case ConfigurationPackage.COMPATIBILITY_TYPE__VERSION_RANGE:
        getVersionRange().clear();
        return;
      case ConfigurationPackage.COMPATIBILITY_TYPE__TARGET_COMPONENT:
        setTargetComponent(TARGET_COMPONENT_EDEFAULT);
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
      case ConfigurationPackage.COMPATIBILITY_TYPE__VERSION_RANGE:
        return versionRange != null && !versionRange.isEmpty();
      case ConfigurationPackage.COMPATIBILITY_TYPE__TARGET_COMPONENT:
        return TARGET_COMPONENT_EDEFAULT == null ? targetComponent != null : !TARGET_COMPONENT_EDEFAULT.equals(targetComponent);
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
    result.append(" (targetComponent: ");
    result.append(targetComponent);
    result.append(')');
    return result.toString();
  }

} //CompatibilityTypeImpl
