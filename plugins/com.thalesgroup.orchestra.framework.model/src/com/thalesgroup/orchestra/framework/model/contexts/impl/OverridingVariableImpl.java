/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.model.contexts.impl;

import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.contexts.ModelElement;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingVariable;
import com.thalesgroup.orchestra.framework.model.contexts.ReferencingElement;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Overriding Variable</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.impl.OverridingVariableImpl#getOverriddenVariable <em>Overridden Variable</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OverridingVariableImpl extends AbstractVariableImpl implements OverridingVariable {
  /**
   * The cached value of the '{@link #getOverriddenVariable() <em>Overridden Variable</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOverriddenVariable()
   * @generated
   * @ordered
   */
  protected Variable overriddenVariable;
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected OverridingVariableImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return ContextsPackage.Literals.OVERRIDING_VARIABLE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Variable getOverriddenVariable() {
    if (overriddenVariable != null && overriddenVariable.eIsProxy()) {
      InternalEObject oldOverriddenVariable = (InternalEObject)overriddenVariable;
      overriddenVariable = (Variable)eResolveProxy(oldOverriddenVariable);
      if (overriddenVariable != oldOverriddenVariable) {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, ContextsPackage.OVERRIDING_VARIABLE__OVERRIDDEN_VARIABLE, oldOverriddenVariable, overriddenVariable));
      }
    }
    return overriddenVariable;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Variable basicGetOverriddenVariable() {
    return overriddenVariable;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setOverriddenVariable(Variable newOverriddenVariable) {
    Variable oldOverriddenVariable = overriddenVariable;
    overriddenVariable = newOverriddenVariable;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContextsPackage.OVERRIDING_VARIABLE__OVERRIDDEN_VARIABLE, oldOverriddenVariable, overriddenVariable));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case ContextsPackage.OVERRIDING_VARIABLE__OVERRIDDEN_VARIABLE:
        if (resolve) return getOverriddenVariable();
        return basicGetOverriddenVariable();
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
      case ContextsPackage.OVERRIDING_VARIABLE__OVERRIDDEN_VARIABLE:
        setOverriddenVariable((Variable)newValue);
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
      case ContextsPackage.OVERRIDING_VARIABLE__OVERRIDDEN_VARIABLE:
        setOverriddenVariable((Variable)null);
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
      case ContextsPackage.OVERRIDING_VARIABLE__OVERRIDDEN_VARIABLE:
        return overriddenVariable != null;
    }
    return super.eIsSet(featureID);
  }

} //OverridingVariableImpl
