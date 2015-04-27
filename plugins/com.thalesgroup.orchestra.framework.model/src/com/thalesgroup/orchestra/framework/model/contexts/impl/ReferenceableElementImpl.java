/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.model.contexts.impl;

import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.contexts.ReferenceableElement;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Referenceable Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.impl.ReferenceableElementImpl#isReferenceable <em>Referenceable</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ReferenceableElementImpl extends EObjectImpl implements ReferenceableElement {
  /**
   * The default value of the '{@link #isReferenceable() <em>Referenceable</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isReferenceable()
   * @generated
   * @ordered
   */
  protected static final boolean REFERENCEABLE_EDEFAULT = false;
  /**
   * The cached value of the '{@link #isReferenceable() <em>Referenceable</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isReferenceable()
   * @generated
   * @ordered
   */
  protected boolean referenceable = REFERENCEABLE_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ReferenceableElementImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return ContextsPackage.Literals.REFERENCEABLE_ELEMENT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isReferenceable() {
    return referenceable;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setReferenceable(boolean newReferenceable) {
    boolean oldReferenceable = referenceable;
    referenceable = newReferenceable;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContextsPackage.REFERENCEABLE_ELEMENT__REFERENCEABLE, oldReferenceable, referenceable));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case ContextsPackage.REFERENCEABLE_ELEMENT__REFERENCEABLE:
        return isReferenceable();
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
      case ContextsPackage.REFERENCEABLE_ELEMENT__REFERENCEABLE:
        setReferenceable((Boolean)newValue);
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
      case ContextsPackage.REFERENCEABLE_ELEMENT__REFERENCEABLE:
        setReferenceable(REFERENCEABLE_EDEFAULT);
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
      case ContextsPackage.REFERENCEABLE_ELEMENT__REFERENCEABLE:
        return referenceable != REFERENCEABLE_EDEFAULT;
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
    result.append(" (referenceable: ");
    result.append(referenceable);
    result.append(')');
    return result.toString();
  }

} //ReferenceableElementImpl
