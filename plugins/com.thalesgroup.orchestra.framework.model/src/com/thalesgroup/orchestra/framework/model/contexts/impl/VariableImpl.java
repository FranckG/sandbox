/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.model.contexts.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.thalesgroup.orchestra.framework.model.contexts.Category;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.contexts.ContributedElement;
import com.thalesgroup.orchestra.framework.model.contexts.ModelElement;
import com.thalesgroup.orchestra.framework.model.contexts.ReferenceableElement;
import com.thalesgroup.orchestra.framework.model.contexts.ReferencingElement;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Variable</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.impl.VariableImpl#getSuperCategory <em>Super Category</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.impl.VariableImpl#isMandatory <em>Mandatory</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.impl.VariableImpl#getDescription <em>Description</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VariableImpl extends AbstractVariableImpl implements Variable {
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
   * The default value of the '{@link #isMandatory() <em>Mandatory</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #isMandatory()
   * @generated
   * @ordered
   */
	protected static final boolean MANDATORY_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isMandatory() <em>Mandatory</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #isMandatory()
   * @generated
   * @ordered
   */
	protected boolean mandatory = MANDATORY_EDEFAULT;

  /**
   * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getDescription()
   * @generated
   * @ordered
   */
	protected static final String DESCRIPTION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getDescription()
   * @generated
   * @ordered
   */
	protected String description = DESCRIPTION_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected VariableImpl() {
    super();
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
	protected EClass eStaticClass() {
    return ContextsPackage.Literals.VARIABLE;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public String getDescription() {
    return description;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setDescription(String newDescription) {
    String oldDescription = description;
    description = newDescription;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContextsPackage.VARIABLE__DESCRIPTION, oldDescription, description));
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public boolean isMandatory() {
    return mandatory;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setMandatory(boolean newMandatory) {
    boolean oldMandatory = mandatory;
    mandatory = newMandatory;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContextsPackage.VARIABLE__MANDATORY, oldMandatory, mandatory));
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
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, ContextsPackage.VARIABLE__SUPER_CATEGORY, oldSuperCategory, superCategory));
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
      eNotify(new ENotificationImpl(this, Notification.SET, ContextsPackage.VARIABLE__SUPER_CATEGORY, oldSuperCategory, superCategory));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case ContextsPackage.VARIABLE__SUPER_CATEGORY:
        if (resolve) return getSuperCategory();
        return basicGetSuperCategory();
      case ContextsPackage.VARIABLE__MANDATORY:
        return isMandatory();
      case ContextsPackage.VARIABLE__DESCRIPTION:
        return getDescription();
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
      case ContextsPackage.VARIABLE__SUPER_CATEGORY:
        setSuperCategory((Category)newValue);
        return;
      case ContextsPackage.VARIABLE__MANDATORY:
        setMandatory((Boolean)newValue);
        return;
      case ContextsPackage.VARIABLE__DESCRIPTION:
        setDescription((String)newValue);
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
      case ContextsPackage.VARIABLE__SUPER_CATEGORY:
        setSuperCategory((Category)null);
        return;
      case ContextsPackage.VARIABLE__MANDATORY:
        setMandatory(MANDATORY_EDEFAULT);
        return;
      case ContextsPackage.VARIABLE__DESCRIPTION:
        setDescription(DESCRIPTION_EDEFAULT);
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
      case ContextsPackage.VARIABLE__SUPER_CATEGORY:
        return superCategory != null;
      case ContextsPackage.VARIABLE__MANDATORY:
        return mandatory != MANDATORY_EDEFAULT;
      case ContextsPackage.VARIABLE__DESCRIPTION:
        return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
    }
    return super.eIsSet(featureID);
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
    if (baseClass == ContributedElement.class) {
      switch (derivedFeatureID) {
        case ContextsPackage.VARIABLE__SUPER_CATEGORY: return ContextsPackage.CONTRIBUTED_ELEMENT__SUPER_CATEGORY;
        default: return -1;
      }
    }
    return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
    if (baseClass == ContributedElement.class) {
      switch (baseFeatureID) {
        case ContextsPackage.CONTRIBUTED_ELEMENT__SUPER_CATEGORY: return ContextsPackage.VARIABLE__SUPER_CATEGORY;
        default: return -1;
      }
    }
    return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
    result.append(" (mandatory: ");
    result.append(mandatory);
    result.append(", description: ");
    result.append(description);
    result.append(')');
    return result.toString();
  }

} //VariableImpl
