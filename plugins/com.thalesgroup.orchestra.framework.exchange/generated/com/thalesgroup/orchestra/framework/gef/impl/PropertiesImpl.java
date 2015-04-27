/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.gef.impl;

import com.thalesgroup.orchestra.framework.gef.GefPackage;
import com.thalesgroup.orchestra.framework.gef.Mproperty;
import com.thalesgroup.orchestra.framework.gef.Properties;
import com.thalesgroup.orchestra.framework.gef.Property;

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
 * An implementation of the model object '<em><b>Properties</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.impl.PropertiesImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.impl.PropertiesImpl#getPROPERTY <em>PROPERTY</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.impl.PropertiesImpl#getMPROPERTY <em>MPROPERTY</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PropertiesImpl extends EObjectImpl implements Properties {
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
  protected PropertiesImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return GefPackage.Literals.PROPERTIES;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FeatureMap getGroup() {
    if (group == null) {
      group = new BasicFeatureMap(this, GefPackage.PROPERTIES__GROUP);
    }
    return group;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Property> getPROPERTY() {
    return getGroup().list(GefPackage.Literals.PROPERTIES__PROPERTY);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Mproperty> getMPROPERTY() {
    return getGroup().list(GefPackage.Literals.PROPERTIES__MPROPERTY);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case GefPackage.PROPERTIES__GROUP:
        return ((InternalEList<?>)getGroup()).basicRemove(otherEnd, msgs);
      case GefPackage.PROPERTIES__PROPERTY:
        return ((InternalEList<?>)getPROPERTY()).basicRemove(otherEnd, msgs);
      case GefPackage.PROPERTIES__MPROPERTY:
        return ((InternalEList<?>)getMPROPERTY()).basicRemove(otherEnd, msgs);
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
      case GefPackage.PROPERTIES__GROUP:
        if (coreType) return getGroup();
        return ((FeatureMap.Internal)getGroup()).getWrapper();
      case GefPackage.PROPERTIES__PROPERTY:
        return getPROPERTY();
      case GefPackage.PROPERTIES__MPROPERTY:
        return getMPROPERTY();
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
      case GefPackage.PROPERTIES__GROUP:
        ((FeatureMap.Internal)getGroup()).set(newValue);
        return;
      case GefPackage.PROPERTIES__PROPERTY:
        getPROPERTY().clear();
        getPROPERTY().addAll((Collection<? extends Property>)newValue);
        return;
      case GefPackage.PROPERTIES__MPROPERTY:
        getMPROPERTY().clear();
        getMPROPERTY().addAll((Collection<? extends Mproperty>)newValue);
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
      case GefPackage.PROPERTIES__GROUP:
        getGroup().clear();
        return;
      case GefPackage.PROPERTIES__PROPERTY:
        getPROPERTY().clear();
        return;
      case GefPackage.PROPERTIES__MPROPERTY:
        getMPROPERTY().clear();
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
      case GefPackage.PROPERTIES__GROUP:
        return group != null && !group.isEmpty();
      case GefPackage.PROPERTIES__PROPERTY:
        return !getPROPERTY().isEmpty();
      case GefPackage.PROPERTIES__MPROPERTY:
        return !getMPROPERTY().isEmpty();
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

} //PropertiesImpl
