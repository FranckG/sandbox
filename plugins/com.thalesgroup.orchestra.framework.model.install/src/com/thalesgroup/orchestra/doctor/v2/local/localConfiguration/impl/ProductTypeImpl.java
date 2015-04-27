/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl;

import com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ConfigurationPackage;
import com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ProductType;
import com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionType;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Product Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.ProductTypeImpl#getVersion <em>Version</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.ProductTypeImpl#getCategory <em>Category</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.ProductTypeImpl#getKind <em>Kind</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl.ProductTypeImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProductTypeImpl extends EObjectImpl implements ProductType {
  /**
   * The cached value of the '{@link #getVersion() <em>Version</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getVersion()
   * @generated
   * @ordered
   */
  protected EList<VersionType> version;

  /**
   * The default value of the '{@link #getCategory() <em>Category</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCategory()
   * @generated
   * @ordered
   */
  protected static final String CATEGORY_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getCategory() <em>Category</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCategory()
   * @generated
   * @ordered
   */
  protected String category = CATEGORY_EDEFAULT;

  /**
   * The default value of the '{@link #getKind() <em>Kind</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getKind()
   * @generated
   * @ordered
   */
  protected static final String KIND_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getKind() <em>Kind</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getKind()
   * @generated
   * @ordered
   */
  protected String kind = KIND_EDEFAULT;

  /**
   * The default value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected static final String NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected String name = NAME_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ProductTypeImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return ConfigurationPackage.Literals.PRODUCT_TYPE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<VersionType> getVersion() {
    if (version == null) {
      version = new EObjectContainmentEList<VersionType>(VersionType.class, this, ConfigurationPackage.PRODUCT_TYPE__VERSION);
    }
    return version;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getCategory() {
    return category;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setCategory(String newCategory) {
    String oldCategory = category;
    category = newCategory;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.PRODUCT_TYPE__CATEGORY, oldCategory, category));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getKind() {
    return kind;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setKind(String newKind) {
    String oldKind = kind;
    kind = newKind;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.PRODUCT_TYPE__KIND, oldKind, kind));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getName() {
    return name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setName(String newName) {
    String oldName = name;
    name = newName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.PRODUCT_TYPE__NAME, oldName, name));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case ConfigurationPackage.PRODUCT_TYPE__VERSION:
        return ((InternalEList<?>)getVersion()).basicRemove(otherEnd, msgs);
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
      case ConfigurationPackage.PRODUCT_TYPE__VERSION:
        return getVersion();
      case ConfigurationPackage.PRODUCT_TYPE__CATEGORY:
        return getCategory();
      case ConfigurationPackage.PRODUCT_TYPE__KIND:
        return getKind();
      case ConfigurationPackage.PRODUCT_TYPE__NAME:
        return getName();
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
      case ConfigurationPackage.PRODUCT_TYPE__VERSION:
        getVersion().clear();
        getVersion().addAll((Collection<? extends VersionType>)newValue);
        return;
      case ConfigurationPackage.PRODUCT_TYPE__CATEGORY:
        setCategory((String)newValue);
        return;
      case ConfigurationPackage.PRODUCT_TYPE__KIND:
        setKind((String)newValue);
        return;
      case ConfigurationPackage.PRODUCT_TYPE__NAME:
        setName((String)newValue);
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
      case ConfigurationPackage.PRODUCT_TYPE__VERSION:
        getVersion().clear();
        return;
      case ConfigurationPackage.PRODUCT_TYPE__CATEGORY:
        setCategory(CATEGORY_EDEFAULT);
        return;
      case ConfigurationPackage.PRODUCT_TYPE__KIND:
        setKind(KIND_EDEFAULT);
        return;
      case ConfigurationPackage.PRODUCT_TYPE__NAME:
        setName(NAME_EDEFAULT);
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
      case ConfigurationPackage.PRODUCT_TYPE__VERSION:
        return version != null && !version.isEmpty();
      case ConfigurationPackage.PRODUCT_TYPE__CATEGORY:
        return CATEGORY_EDEFAULT == null ? category != null : !CATEGORY_EDEFAULT.equals(category);
      case ConfigurationPackage.PRODUCT_TYPE__KIND:
        return KIND_EDEFAULT == null ? kind != null : !KIND_EDEFAULT.equals(kind);
      case ConfigurationPackage.PRODUCT_TYPE__NAME:
        return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
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
    result.append(" (category: ");
    result.append(category);
    result.append(", kind: ");
    result.append(kind);
    result.append(", name: ");
    result.append(name);
    result.append(')');
    return result.toString();
  }

} //ProductTypeImpl
