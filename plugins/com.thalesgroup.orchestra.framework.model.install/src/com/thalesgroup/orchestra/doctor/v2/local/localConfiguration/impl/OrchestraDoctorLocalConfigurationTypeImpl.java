/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl;

import com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ConfigurationPackage;
import com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.OrchestraDoctorLocalConfigurationType;
import com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ProductType;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Orchestra Doctor Local Configuration Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.OrchestraDoctorLocalConfigurationTypeImpl#getProduct <em>Product</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OrchestraDoctorLocalConfigurationTypeImpl extends EObjectImpl implements OrchestraDoctorLocalConfigurationType {
  /**
   * The cached value of the '{@link #getProduct() <em>Product</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getProduct()
   * @generated
   * @ordered
   */
  protected EList<ProductType> product;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected OrchestraDoctorLocalConfigurationTypeImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return ConfigurationPackage.Literals.ORCHESTRA_DOCTOR_LOCAL_CONFIGURATION_TYPE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<ProductType> getProduct() {
    if (product == null) {
      product = new EObjectContainmentEList<ProductType>(ProductType.class, this, ConfigurationPackage.ORCHESTRA_DOCTOR_LOCAL_CONFIGURATION_TYPE__PRODUCT);
    }
    return product;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case ConfigurationPackage.ORCHESTRA_DOCTOR_LOCAL_CONFIGURATION_TYPE__PRODUCT:
        return ((InternalEList<?>)getProduct()).basicRemove(otherEnd, msgs);
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
      case ConfigurationPackage.ORCHESTRA_DOCTOR_LOCAL_CONFIGURATION_TYPE__PRODUCT:
        return getProduct();
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
      case ConfigurationPackage.ORCHESTRA_DOCTOR_LOCAL_CONFIGURATION_TYPE__PRODUCT:
        getProduct().clear();
        getProduct().addAll((Collection<? extends ProductType>)newValue);
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
      case ConfigurationPackage.ORCHESTRA_DOCTOR_LOCAL_CONFIGURATION_TYPE__PRODUCT:
        getProduct().clear();
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
      case ConfigurationPackage.ORCHESTRA_DOCTOR_LOCAL_CONFIGURATION_TYPE__PRODUCT:
        return product != null && !product.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //OrchestraDoctorLocalConfigurationTypeImpl
