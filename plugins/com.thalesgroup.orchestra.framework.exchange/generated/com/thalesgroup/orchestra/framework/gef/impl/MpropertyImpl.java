/**
 */
package com.thalesgroup.orchestra.framework.gef.impl;

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

import com.thalesgroup.orchestra.framework.gef.GefPackage;
import com.thalesgroup.orchestra.framework.gef.Mproperty;
import com.thalesgroup.orchestra.framework.gef.MpropertyValue;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Mproperty</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link com.thalesgroup.orchestra.framework.gef.impl.MpropertyImpl#getVALUES <em>VALUES</em>}</li>
 * <li>{@link com.thalesgroup.orchestra.framework.gef.impl.MpropertyImpl#getName <em>Name</em>}</li>
 * <li>{@link com.thalesgroup.orchestra.framework.gef.impl.MpropertyImpl#getNature <em>Nature</em>}</li>
 * <li>{@link com.thalesgroup.orchestra.framework.gef.impl.MpropertyImpl#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public class MpropertyImpl extends EObjectImpl implements Mproperty {
  /**
   * The cached value of the '{@link #getVALUES() <em>VALUES</em>}' containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
   * @see #getVALUES()
   * @generated
   * @ordered
   */
  protected EList<MpropertyValue> vALUES;

  /**
   * The default value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected static final String NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected String name = NAME_EDEFAULT;

  /**
   * The default value of the '{@link #getNature() <em>Nature</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * @see #getNature()
   * @generated
   * @ordered
   */
  protected static final String NATURE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getNature() <em>Nature</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * @see #getNature()
   * @generated
   * @ordered
   */
  protected String nature = NATURE_EDEFAULT;

  /**
   * The default value of the '{@link #getType() <em>Type</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * @see #getType()
   * @generated
   * @ordered
   */
  protected static final String TYPE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getType() <em>Type</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * @see #getType()
   * @generated
   * @ordered
   */
  protected String type = TYPE_EDEFAULT;

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  protected MpropertyImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return GefPackage.Literals.MPROPERTY;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public EList<MpropertyValue> getVALUES() {
    if (vALUES == null) {
      vALUES = new EObjectContainmentEList<MpropertyValue>(MpropertyValue.class, this, GefPackage.MPROPERTY__VALUES);
    }
    return vALUES;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public String getName() {
    return name;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public void setName(String newName) {
    String oldName = name;
    name = newName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GefPackage.MPROPERTY__NAME, oldName, name));
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public String getNature() {
    return nature;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public void setNature(String newNature) {
    String oldNature = nature;
    nature = newNature;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GefPackage.MPROPERTY__NATURE, oldNature, nature));
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public String getType() {
    return type;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public void setType(String newType) {
    String oldType = type;
    type = newType;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GefPackage.MPROPERTY__TYPE, oldType, type));
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case GefPackage.MPROPERTY__VALUES:
        return ((InternalEList<?>) getVALUES()).basicRemove(otherEnd, msgs);
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
      case GefPackage.MPROPERTY__VALUES:
        return getVALUES();
      case GefPackage.MPROPERTY__NAME:
        return getName();
      case GefPackage.MPROPERTY__NATURE:
        return getNature();
      case GefPackage.MPROPERTY__TYPE:
        return getType();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue) {
    switch (featureID) {
      case GefPackage.MPROPERTY__VALUES:
        getVALUES().clear();
        getVALUES().addAll((Collection<? extends MpropertyValue>) newValue);
        return;
      case GefPackage.MPROPERTY__NAME:
        setName((String) newValue);
        return;
      case GefPackage.MPROPERTY__NATURE:
        setNature((String) newValue);
        return;
      case GefPackage.MPROPERTY__TYPE:
        setType((String) newValue);
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
      case GefPackage.MPROPERTY__VALUES:
        getVALUES().clear();
        return;
      case GefPackage.MPROPERTY__NAME:
        setName(NAME_EDEFAULT);
        return;
      case GefPackage.MPROPERTY__NATURE:
        setNature(NATURE_EDEFAULT);
        return;
      case GefPackage.MPROPERTY__TYPE:
        setType(TYPE_EDEFAULT);
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
      case GefPackage.MPROPERTY__VALUES:
        return vALUES != null && !vALUES.isEmpty();
      case GefPackage.MPROPERTY__NAME:
        return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
      case GefPackage.MPROPERTY__NATURE:
        return NATURE_EDEFAULT == null ? nature != null : !NATURE_EDEFAULT.equals(nature);
      case GefPackage.MPROPERTY__TYPE:
        return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString() {
    if (eIsProxy())
      return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (name: ");
    result.append(name);
    result.append(", nature: ");
    result.append(nature);
    result.append(", type: ");
    result.append(type);
    result.append(')');
    return result.toString();
  }

} // MpropertyImpl
