/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.gef.impl;

import com.thalesgroup.orchestra.framework.gef.ElementReference;
import com.thalesgroup.orchestra.framework.gef.GefPackage;
import com.thalesgroup.orchestra.framework.gef.LinksToElements;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Links To Elements</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.impl.LinksToElementsImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.impl.LinksToElementsImpl#getELEMENTREFERENCE <em>ELEMENTREFERENCE</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LinksToElementsImpl extends EObjectImpl implements LinksToElements {
  /**
   * The cached value of the '{@link #getGroup() <em>Group</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getGroup()
   * @generated
   * @ordered
   */
  protected FeatureMap group;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected LinksToElementsImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return GefPackage.Literals.LINKS_TO_ELEMENTS;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FeatureMap getGroup() {
    if (group == null) {
      group = new BasicFeatureMap(this, GefPackage.LINKS_TO_ELEMENTS__GROUP);
    }
    return group;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<ElementReference> getELEMENTREFERENCE() {
    return getGroup().list(GefPackage.Literals.LINKS_TO_ELEMENTS__ELEMENTREFERENCE);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case GefPackage.LINKS_TO_ELEMENTS__GROUP:
        return ((InternalEList<?>)getGroup()).basicRemove(otherEnd, msgs);
      case GefPackage.LINKS_TO_ELEMENTS__ELEMENTREFERENCE:
        return ((InternalEList<?>)getELEMENTREFERENCE()).basicRemove(otherEnd, msgs);
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
      case GefPackage.LINKS_TO_ELEMENTS__GROUP:
        if (coreType) return getGroup();
        return ((FeatureMap.Internal)getGroup()).getWrapper();
      case GefPackage.LINKS_TO_ELEMENTS__ELEMENTREFERENCE:
        return getELEMENTREFERENCE();
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
      case GefPackage.LINKS_TO_ELEMENTS__GROUP:
        ((FeatureMap.Internal)getGroup()).set(newValue);
        return;
      case GefPackage.LINKS_TO_ELEMENTS__ELEMENTREFERENCE:
        getELEMENTREFERENCE().clear();
        getELEMENTREFERENCE().addAll((Collection<? extends ElementReference>)newValue);
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
      case GefPackage.LINKS_TO_ELEMENTS__GROUP:
        getGroup().clear();
        return;
      case GefPackage.LINKS_TO_ELEMENTS__ELEMENTREFERENCE:
        getELEMENTREFERENCE().clear();
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
      case GefPackage.LINKS_TO_ELEMENTS__GROUP:
        return group != null && !group.isEmpty();
      case GefPackage.LINKS_TO_ELEMENTS__ELEMENTREFERENCE:
        return !getELEMENTREFERENCE().isEmpty();
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
    result.append(" (group: ");
    result.append(group);
    result.append(')');
    return result.toString();
  }

} //LinksToElementsImpl
