/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.model.contexts.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abstract Variable</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.impl.AbstractVariableImpl#isFinal <em>Final</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.impl.AbstractVariableImpl#isMultiValued <em>Multi Valued</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.impl.AbstractVariableImpl#getValues <em>Values</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class AbstractVariableImpl extends NamedElementImpl implements AbstractVariable {
  /**
   * The default value of the '{@link #isFinal() <em>Final</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isFinal()
   * @generated
   * @ordered
   */
  protected static final boolean FINAL_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isFinal() <em>Final</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isFinal()
   * @generated
   * @ordered
   */
  protected boolean final_ = FINAL_EDEFAULT;

  /**
   * The default value of the '{@link #isMultiValued() <em>Multi Valued</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isMultiValued()
   * @generated
   * @ordered
   */
  protected static final boolean MULTI_VALUED_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isMultiValued() <em>Multi Valued</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isMultiValued()
   * @generated
   * @ordered
   */
  protected boolean multiValued = MULTI_VALUED_EDEFAULT;

  /**
   * The cached value of the '{@link #getValues() <em>Values</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getValues()
   * @generated
   * @ordered
   */
  protected EList<VariableValue> values;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected AbstractVariableImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return ContextsPackage.Literals.ABSTRACT_VARIABLE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<VariableValue> getValues() {
    if (values == null) {
      values = new EObjectContainmentEList<VariableValue>(VariableValue.class, this, ContextsPackage.ABSTRACT_VARIABLE__VALUES);
    }
    return values;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case ContextsPackage.ABSTRACT_VARIABLE__VALUES:
        return ((InternalEList<?>)getValues()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isFinal() {
    return final_;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setFinal(boolean newFinal) {
    boolean oldFinal = final_;
    final_ = newFinal;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContextsPackage.ABSTRACT_VARIABLE__FINAL, oldFinal, final_));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isMultiValued() {
    return multiValued;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setMultiValued(boolean newMultiValued) {
    boolean oldMultiValued = multiValued;
    multiValued = newMultiValued;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContextsPackage.ABSTRACT_VARIABLE__MULTI_VALUED, oldMultiValued, multiValued));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case ContextsPackage.ABSTRACT_VARIABLE__FINAL:
        return isFinal();
      case ContextsPackage.ABSTRACT_VARIABLE__MULTI_VALUED:
        return isMultiValued();
      case ContextsPackage.ABSTRACT_VARIABLE__VALUES:
        return getValues();
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
      case ContextsPackage.ABSTRACT_VARIABLE__FINAL:
        setFinal((Boolean)newValue);
        return;
      case ContextsPackage.ABSTRACT_VARIABLE__MULTI_VALUED:
        setMultiValued((Boolean)newValue);
        return;
      case ContextsPackage.ABSTRACT_VARIABLE__VALUES:
        getValues().clear();
        getValues().addAll((Collection<? extends VariableValue>)newValue);
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
      case ContextsPackage.ABSTRACT_VARIABLE__FINAL:
        setFinal(FINAL_EDEFAULT);
        return;
      case ContextsPackage.ABSTRACT_VARIABLE__MULTI_VALUED:
        setMultiValued(MULTI_VALUED_EDEFAULT);
        return;
      case ContextsPackage.ABSTRACT_VARIABLE__VALUES:
        getValues().clear();
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
      case ContextsPackage.ABSTRACT_VARIABLE__FINAL:
        return final_ != FINAL_EDEFAULT;
      case ContextsPackage.ABSTRACT_VARIABLE__MULTI_VALUED:
        return multiValued != MULTI_VALUED_EDEFAULT;
      case ContextsPackage.ABSTRACT_VARIABLE__VALUES:
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
    result.append(" (final: ");
    result.append(final_);
    result.append(", multiValued: ");
    result.append(multiValued);
    result.append(')');
    return result.toString();
  }

} //AbstractVariableImpl
