/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.model.contexts.impl;

import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.contexts.InstallationCategory;

import com.thalesgroup.orchestra.framework.model.contexts.ModelElement;
import com.thalesgroup.orchestra.framework.model.contexts.ReferenceableElement;
import com.thalesgroup.orchestra.framework.model.contexts.ReferencingElement;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Installation Category</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.impl.InstallationCategoryImpl#isReferenceable <em>Referenceable</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.impl.InstallationCategoryImpl#getReferencePath <em>Reference Path</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.impl.InstallationCategoryImpl#getResolvedReference <em>Resolved Reference</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InstallationCategoryImpl extends CategoryImpl implements InstallationCategory {
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
   * The default value of the '{@link #getReferencePath() <em>Reference Path</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getReferencePath()
   * @generated
   * @ordered
   */
  protected static final String REFERENCE_PATH_EDEFAULT = null;
  /**
   * The cached value of the '{@link #getReferencePath() <em>Reference Path</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getReferencePath()
   * @generated
   * @ordered
   */
  protected String referencePath = REFERENCE_PATH_EDEFAULT;
  /**
   * The cached value of the '{@link #getResolvedReference() <em>Resolved Reference</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getResolvedReference()
   * @generated
   * @ordered
   */
  protected ModelElement resolvedReference;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected InstallationCategoryImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return ContextsPackage.Literals.INSTALLATION_CATEGORY;
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
      eNotify(new ENotificationImpl(this, Notification.SET, ContextsPackage.INSTALLATION_CATEGORY__REFERENCEABLE, oldReferenceable, referenceable));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getReferencePath() {
    return referencePath;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setReferencePath(String newReferencePath) {
    String oldReferencePath = referencePath;
    referencePath = newReferencePath;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContextsPackage.INSTALLATION_CATEGORY__REFERENCE_PATH, oldReferencePath, referencePath));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ModelElement getResolvedReference() {
    if (resolvedReference != null && resolvedReference.eIsProxy()) {
      InternalEObject oldResolvedReference = (InternalEObject)resolvedReference;
      resolvedReference = (ModelElement)eResolveProxy(oldResolvedReference);
      if (resolvedReference != oldResolvedReference) {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, ContextsPackage.INSTALLATION_CATEGORY__RESOLVED_REFERENCE, oldResolvedReference, resolvedReference));
      }
    }
    return resolvedReference;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ModelElement basicGetResolvedReference() {
    return resolvedReference;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setResolvedReference(ModelElement newResolvedReference) {
    ModelElement oldResolvedReference = resolvedReference;
    resolvedReference = newResolvedReference;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContextsPackage.INSTALLATION_CATEGORY__RESOLVED_REFERENCE, oldResolvedReference, resolvedReference));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case ContextsPackage.INSTALLATION_CATEGORY__REFERENCEABLE:
        return isReferenceable();
      case ContextsPackage.INSTALLATION_CATEGORY__REFERENCE_PATH:
        return getReferencePath();
      case ContextsPackage.INSTALLATION_CATEGORY__RESOLVED_REFERENCE:
        if (resolve) return getResolvedReference();
        return basicGetResolvedReference();
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
      case ContextsPackage.INSTALLATION_CATEGORY__REFERENCEABLE:
        setReferenceable((Boolean)newValue);
        return;
      case ContextsPackage.INSTALLATION_CATEGORY__REFERENCE_PATH:
        setReferencePath((String)newValue);
        return;
      case ContextsPackage.INSTALLATION_CATEGORY__RESOLVED_REFERENCE:
        setResolvedReference((ModelElement)newValue);
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
      case ContextsPackage.INSTALLATION_CATEGORY__REFERENCEABLE:
        setReferenceable(REFERENCEABLE_EDEFAULT);
        return;
      case ContextsPackage.INSTALLATION_CATEGORY__REFERENCE_PATH:
        setReferencePath(REFERENCE_PATH_EDEFAULT);
        return;
      case ContextsPackage.INSTALLATION_CATEGORY__RESOLVED_REFERENCE:
        setResolvedReference((ModelElement)null);
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
      case ContextsPackage.INSTALLATION_CATEGORY__REFERENCEABLE:
        return referenceable != REFERENCEABLE_EDEFAULT;
      case ContextsPackage.INSTALLATION_CATEGORY__REFERENCE_PATH:
        return REFERENCE_PATH_EDEFAULT == null ? referencePath != null : !REFERENCE_PATH_EDEFAULT.equals(referencePath);
      case ContextsPackage.INSTALLATION_CATEGORY__RESOLVED_REFERENCE:
        return resolvedReference != null;
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
    if (baseClass == ReferenceableElement.class) {
      switch (derivedFeatureID) {
        case ContextsPackage.INSTALLATION_CATEGORY__REFERENCEABLE: return ContextsPackage.REFERENCEABLE_ELEMENT__REFERENCEABLE;
        default: return -1;
      }
    }
    if (baseClass == ReferencingElement.class) {
      switch (derivedFeatureID) {
        case ContextsPackage.INSTALLATION_CATEGORY__REFERENCE_PATH: return ContextsPackage.REFERENCING_ELEMENT__REFERENCE_PATH;
        case ContextsPackage.INSTALLATION_CATEGORY__RESOLVED_REFERENCE: return ContextsPackage.REFERENCING_ELEMENT__RESOLVED_REFERENCE;
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
    if (baseClass == ReferenceableElement.class) {
      switch (baseFeatureID) {
        case ContextsPackage.REFERENCEABLE_ELEMENT__REFERENCEABLE: return ContextsPackage.INSTALLATION_CATEGORY__REFERENCEABLE;
        default: return -1;
      }
    }
    if (baseClass == ReferencingElement.class) {
      switch (baseFeatureID) {
        case ContextsPackage.REFERENCING_ELEMENT__REFERENCE_PATH: return ContextsPackage.INSTALLATION_CATEGORY__REFERENCE_PATH;
        case ContextsPackage.REFERENCING_ELEMENT__RESOLVED_REFERENCE: return ContextsPackage.INSTALLATION_CATEGORY__RESOLVED_REFERENCE;
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
    result.append(" (referenceable: ");
    result.append(referenceable);
    result.append(", referencePath: ");
    result.append(referencePath);
    result.append(')');
    return result.toString();
  }

} //InstallationCategoryImpl
