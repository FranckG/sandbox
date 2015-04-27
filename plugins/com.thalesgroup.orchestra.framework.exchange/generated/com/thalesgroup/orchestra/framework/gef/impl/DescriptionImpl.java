/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.gef.impl;

import com.thalesgroup.orchestra.framework.gef.Description;
import com.thalesgroup.orchestra.framework.gef.FileReference;
import com.thalesgroup.orchestra.framework.gef.GefPackage;
import com.thalesgroup.orchestra.framework.gef.TextualDescription;

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
 * An implementation of the model object '<em><b>Description</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.impl.DescriptionImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.impl.DescriptionImpl#getTEXTUALDESCRIPTION <em>TEXTUALDESCRIPTION</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.impl.DescriptionImpl#getFILEREFERENCE <em>FILEREFERENCE</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DescriptionImpl extends EObjectImpl implements Description {
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
  protected DescriptionImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return GefPackage.Literals.DESCRIPTION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FeatureMap getGroup() {
    if (group == null) {
      group = new BasicFeatureMap(this, GefPackage.DESCRIPTION__GROUP);
    }
    return group;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<TextualDescription> getTEXTUALDESCRIPTION() {
    return getGroup().list(GefPackage.Literals.DESCRIPTION__TEXTUALDESCRIPTION);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<FileReference> getFILEREFERENCE() {
    return getGroup().list(GefPackage.Literals.DESCRIPTION__FILEREFERENCE);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case GefPackage.DESCRIPTION__GROUP:
        return ((InternalEList<?>)getGroup()).basicRemove(otherEnd, msgs);
      case GefPackage.DESCRIPTION__TEXTUALDESCRIPTION:
        return ((InternalEList<?>)getTEXTUALDESCRIPTION()).basicRemove(otherEnd, msgs);
      case GefPackage.DESCRIPTION__FILEREFERENCE:
        return ((InternalEList<?>)getFILEREFERENCE()).basicRemove(otherEnd, msgs);
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
      case GefPackage.DESCRIPTION__GROUP:
        if (coreType) return getGroup();
        return ((FeatureMap.Internal)getGroup()).getWrapper();
      case GefPackage.DESCRIPTION__TEXTUALDESCRIPTION:
        return getTEXTUALDESCRIPTION();
      case GefPackage.DESCRIPTION__FILEREFERENCE:
        return getFILEREFERENCE();
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
      case GefPackage.DESCRIPTION__GROUP:
        ((FeatureMap.Internal)getGroup()).set(newValue);
        return;
      case GefPackage.DESCRIPTION__TEXTUALDESCRIPTION:
        getTEXTUALDESCRIPTION().clear();
        getTEXTUALDESCRIPTION().addAll((Collection<? extends TextualDescription>)newValue);
        return;
      case GefPackage.DESCRIPTION__FILEREFERENCE:
        getFILEREFERENCE().clear();
        getFILEREFERENCE().addAll((Collection<? extends FileReference>)newValue);
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
      case GefPackage.DESCRIPTION__GROUP:
        getGroup().clear();
        return;
      case GefPackage.DESCRIPTION__TEXTUALDESCRIPTION:
        getTEXTUALDESCRIPTION().clear();
        return;
      case GefPackage.DESCRIPTION__FILEREFERENCE:
        getFILEREFERENCE().clear();
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
      case GefPackage.DESCRIPTION__GROUP:
        return group != null && !group.isEmpty();
      case GefPackage.DESCRIPTION__TEXTUALDESCRIPTION:
        return !getTEXTUALDESCRIPTION().isEmpty();
      case GefPackage.DESCRIPTION__FILEREFERENCE:
        return !getFILEREFERENCE().isEmpty();
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

} //DescriptionImpl
