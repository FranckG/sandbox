/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.model.contexts.impl;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariableValue;
import org.eclipse.emf.common.notify.Notification;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Environment Variable Value</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.impl.EnvironmentVariableValueImpl#getEnvironmentId <em>Environment Id</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.impl.EnvironmentVariableValueImpl#getValues <em>Values</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EnvironmentVariableValueImpl extends VariableValueImpl implements EnvironmentVariableValue {
  /**
   * The default value of the '{@link #getEnvironmentId() <em>Environment Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEnvironmentId()
   * @generated
   * @ordered
   */
  protected static final String ENVIRONMENT_ID_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getEnvironmentId() <em>Environment Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEnvironmentId()
   * @generated
   * @ordered
   */
  protected String environmentId = ENVIRONMENT_ID_EDEFAULT;

  /**
   * The cached value of the '{@link #getValues() <em>Values</em>}' map.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @see #getValues()
   * @generated
   * @ordered
   */
  protected EMap<String, String> values;

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  protected EnvironmentVariableValueImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return ContextsPackage.Literals.ENVIRONMENT_VARIABLE_VALUE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getEnvironmentId() {
    return environmentId;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setEnvironmentId(String newEnvironmentId) {
    String oldEnvironmentId = environmentId;
    environmentId = newEnvironmentId;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContextsPackage.ENVIRONMENT_VARIABLE_VALUE__ENVIRONMENT_ID, oldEnvironmentId, environmentId));
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated NOT
   */
  public String getName() {
    return getValue();
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated NOT
   */
  public void setName(String newName) {
    setValue(newName);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EMap<String, String> getValues() {
    if (values == null) {
      values = new EcoreEMap<String,String>(ContextsPackage.Literals.STRING_TO_STRING_MAP, StringToStringMapImpl.class, this, ContextsPackage.ENVIRONMENT_VARIABLE_VALUE__VALUES);
    }
    return values;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case ContextsPackage.ENVIRONMENT_VARIABLE_VALUE__VALUES:
        return ((InternalEList<?>)getValues()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case ContextsPackage.ENVIRONMENT_VARIABLE_VALUE__ENVIRONMENT_ID:
        return getEnvironmentId();
      case ContextsPackage.ENVIRONMENT_VARIABLE_VALUE__VALUES:
        if (coreType) return getValues();
        else return getValues().map();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue) {
    switch (featureID) {
      case ContextsPackage.ENVIRONMENT_VARIABLE_VALUE__ENVIRONMENT_ID:
        setEnvironmentId((String)newValue);
        return;
      case ContextsPackage.ENVIRONMENT_VARIABLE_VALUE__VALUES:
        ((EStructuralFeature.Setting)getValues()).set(newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID) {
    switch (featureID) {
      case ContextsPackage.ENVIRONMENT_VARIABLE_VALUE__ENVIRONMENT_ID:
        setEnvironmentId(ENVIRONMENT_ID_EDEFAULT);
        return;
      case ContextsPackage.ENVIRONMENT_VARIABLE_VALUE__VALUES:
        getValues().clear();
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID) {
    switch (featureID) {
      case ContextsPackage.ENVIRONMENT_VARIABLE_VALUE__ENVIRONMENT_ID:
        return ENVIRONMENT_ID_EDEFAULT == null ? environmentId != null : !ENVIRONMENT_ID_EDEFAULT.equals(environmentId);
      case ContextsPackage.ENVIRONMENT_VARIABLE_VALUE__VALUES:
        return values != null && !values.isEmpty();
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
    result.append(" (environmentId: ");
    result.append(environmentId);
    result.append(')');
    return result.toString();
  }

} // EnvironmentVariableValueImpl
