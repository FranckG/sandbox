/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.model.contexts.impl;

import com.thalesgroup.orchestra.framework.model.contexts.Category;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.contexts.ContributedElement;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Contributed Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.impl.ContributedElementImpl#getSuperCategory <em>Super Category</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ContributedElementImpl extends EObjectImpl implements ContributedElement {
  /**
   * The cached value of the '{@link #getSuperCategory() <em>Super Category</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSuperCategory()
   * @generated
   * @ordered
   */
  protected Category superCategory;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ContributedElementImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return ContextsPackage.Literals.CONTRIBUTED_ELEMENT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Category getSuperCategory() {
    if (superCategory != null && superCategory.eIsProxy()) {
      InternalEObject oldSuperCategory = (InternalEObject)superCategory;
      superCategory = (Category)eResolveProxy(oldSuperCategory);
      if (superCategory != oldSuperCategory) {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, ContextsPackage.CONTRIBUTED_ELEMENT__SUPER_CATEGORY, oldSuperCategory, superCategory));
      }
    }
    return superCategory;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Category basicGetSuperCategory() {
    return superCategory;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setSuperCategory(Category newSuperCategory) {
    Category oldSuperCategory = superCategory;
    superCategory = newSuperCategory;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContextsPackage.CONTRIBUTED_ELEMENT__SUPER_CATEGORY, oldSuperCategory, superCategory));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case ContextsPackage.CONTRIBUTED_ELEMENT__SUPER_CATEGORY:
        if (resolve) return getSuperCategory();
        return basicGetSuperCategory();
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
      case ContextsPackage.CONTRIBUTED_ELEMENT__SUPER_CATEGORY:
        setSuperCategory((Category)newValue);
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
      case ContextsPackage.CONTRIBUTED_ELEMENT__SUPER_CATEGORY:
        setSuperCategory((Category)null);
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
      case ContextsPackage.CONTRIBUTED_ELEMENT__SUPER_CATEGORY:
        return superCategory != null;
    }
    return super.eIsSet(featureID);
  }

} //ContributedElementImpl
