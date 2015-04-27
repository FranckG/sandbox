/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.model.contexts.impl;

import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.contexts.ModelElement;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingEnvironmentVariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingVariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.ReferencingElement;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Overriding Environment Variable Value</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.impl.OverridingEnvironmentVariableValueImpl#getOverriddenValue <em>Overridden Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OverridingEnvironmentVariableValueImpl extends EnvironmentVariableValueImpl implements OverridingEnvironmentVariableValue {
  /**
   * The cached value of the '{@link #getOverriddenValue() <em>Overridden Value</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOverriddenValue()
   * @generated
   * @ordered
   */
  protected VariableValue overriddenValue;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected OverridingEnvironmentVariableValueImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return ContextsPackage.Literals.OVERRIDING_ENVIRONMENT_VARIABLE_VALUE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public VariableValue getOverriddenValue() {
    if (overriddenValue != null && overriddenValue.eIsProxy()) {
      InternalEObject oldOverriddenValue = (InternalEObject)overriddenValue;
      overriddenValue = (VariableValue)eResolveProxy(oldOverriddenValue);
      if (overriddenValue != oldOverriddenValue) {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, ContextsPackage.OVERRIDING_ENVIRONMENT_VARIABLE_VALUE__OVERRIDDEN_VALUE, oldOverriddenValue, overriddenValue));
      }
    }
    return overriddenValue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public VariableValue basicGetOverriddenValue() {
    return overriddenValue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setOverriddenValue(VariableValue newOverriddenValue) {
    VariableValue oldOverriddenValue = overriddenValue;
    overriddenValue = newOverriddenValue;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContextsPackage.OVERRIDING_ENVIRONMENT_VARIABLE_VALUE__OVERRIDDEN_VALUE, oldOverriddenValue, overriddenValue));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case ContextsPackage.OVERRIDING_ENVIRONMENT_VARIABLE_VALUE__OVERRIDDEN_VALUE:
        if (resolve) return getOverriddenValue();
        return basicGetOverriddenValue();
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
      case ContextsPackage.OVERRIDING_ENVIRONMENT_VARIABLE_VALUE__OVERRIDDEN_VALUE:
        setOverriddenValue((VariableValue)newValue);
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
      case ContextsPackage.OVERRIDING_ENVIRONMENT_VARIABLE_VALUE__OVERRIDDEN_VALUE:
        setOverriddenValue((VariableValue)null);
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
      case ContextsPackage.OVERRIDING_ENVIRONMENT_VARIABLE_VALUE__OVERRIDDEN_VALUE:
        return overriddenValue != null;
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
    if (baseClass == OverridingVariableValue.class) {
      switch (derivedFeatureID) {
        case ContextsPackage.OVERRIDING_ENVIRONMENT_VARIABLE_VALUE__OVERRIDDEN_VALUE: return ContextsPackage.OVERRIDING_VARIABLE_VALUE__OVERRIDDEN_VALUE;
        default: return -1;
      }
    }
    return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
    if (baseClass == OverridingVariableValue.class) {
      switch (baseFeatureID) {
        case ContextsPackage.OVERRIDING_VARIABLE_VALUE__OVERRIDDEN_VALUE: return ContextsPackage.OVERRIDING_ENVIRONMENT_VARIABLE_VALUE__OVERRIDDEN_VALUE;
        default: return -1;
      }
    }
    return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
  }

} //OverridingEnvironmentVariableValueImpl
