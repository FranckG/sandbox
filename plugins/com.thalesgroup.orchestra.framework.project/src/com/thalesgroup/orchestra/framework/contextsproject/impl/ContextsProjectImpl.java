/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.contextsproject.impl;

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

import com.thalesgroup.orchestra.framework.contextsproject.Administrator;
import com.thalesgroup.orchestra.framework.contextsproject.ContextReference;
import com.thalesgroup.orchestra.framework.contextsproject.ContextsProject;
import com.thalesgroup.orchestra.framework.contextsproject.ContextsProjectPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Contexts Project</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.contextsproject.impl.ContextsProjectImpl#getParentProject <em>Parent Project</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.contextsproject.impl.ContextsProjectImpl#getAdministrators <em>Administrators</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.contextsproject.impl.ContextsProjectImpl#getContextReferences <em>Context References</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ContextsProjectImpl extends EObjectImpl implements ContextsProject {
  /**
   * The default value of the '{@link #getParentProject() <em>Parent Project</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getParentProject()
   * @generated
   * @ordered
   */
  protected static final String PARENT_PROJECT_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getParentProject() <em>Parent Project</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getParentProject()
   * @generated
   * @ordered
   */
  protected String parentProject = PARENT_PROJECT_EDEFAULT;

  /**
   * The cached value of the '{@link #getAdministrators() <em>Administrators</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAdministrators()
   * @generated
   * @ordered
   */
  protected EList<Administrator> administrators;

  /**
   * The cached value of the '{@link #getContextReferences() <em>Context References</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getContextReferences()
   * @generated
   * @ordered
   */
  protected EList<ContextReference> contextReferences;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ContextsProjectImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return ContextsProjectPackage.Literals.CONTEXTS_PROJECT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getParentProject() {
    return parentProject;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setParentProject(String newParentProject) {
    String oldParentProject = parentProject;
    parentProject = newParentProject;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContextsProjectPackage.CONTEXTS_PROJECT__PARENT_PROJECT, oldParentProject, parentProject));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Administrator> getAdministrators() {
    if (administrators == null) {
      administrators = new EObjectContainmentEList<Administrator>(Administrator.class, this, ContextsProjectPackage.CONTEXTS_PROJECT__ADMINISTRATORS);
    }
    return administrators;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<ContextReference> getContextReferences() {
    if (contextReferences == null) {
      contextReferences = new EObjectContainmentEList<ContextReference>(ContextReference.class, this, ContextsProjectPackage.CONTEXTS_PROJECT__CONTEXT_REFERENCES);
    }
    return contextReferences;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case ContextsProjectPackage.CONTEXTS_PROJECT__ADMINISTRATORS:
        return ((InternalEList<?>)getAdministrators()).basicRemove(otherEnd, msgs);
      case ContextsProjectPackage.CONTEXTS_PROJECT__CONTEXT_REFERENCES:
        return ((InternalEList<?>)getContextReferences()).basicRemove(otherEnd, msgs);
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
      case ContextsProjectPackage.CONTEXTS_PROJECT__PARENT_PROJECT:
        return getParentProject();
      case ContextsProjectPackage.CONTEXTS_PROJECT__ADMINISTRATORS:
        return getAdministrators();
      case ContextsProjectPackage.CONTEXTS_PROJECT__CONTEXT_REFERENCES:
        return getContextReferences();
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
      case ContextsProjectPackage.CONTEXTS_PROJECT__PARENT_PROJECT:
        setParentProject((String)newValue);
        return;
      case ContextsProjectPackage.CONTEXTS_PROJECT__ADMINISTRATORS:
        getAdministrators().clear();
        getAdministrators().addAll((Collection<? extends Administrator>)newValue);
        return;
      case ContextsProjectPackage.CONTEXTS_PROJECT__CONTEXT_REFERENCES:
        getContextReferences().clear();
        getContextReferences().addAll((Collection<? extends ContextReference>)newValue);
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
      case ContextsProjectPackage.CONTEXTS_PROJECT__PARENT_PROJECT:
        setParentProject(PARENT_PROJECT_EDEFAULT);
        return;
      case ContextsProjectPackage.CONTEXTS_PROJECT__ADMINISTRATORS:
        getAdministrators().clear();
        return;
      case ContextsProjectPackage.CONTEXTS_PROJECT__CONTEXT_REFERENCES:
        getContextReferences().clear();
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
      case ContextsProjectPackage.CONTEXTS_PROJECT__PARENT_PROJECT:
        return PARENT_PROJECT_EDEFAULT == null ? parentProject != null : !PARENT_PROJECT_EDEFAULT.equals(parentProject);
      case ContextsProjectPackage.CONTEXTS_PROJECT__ADMINISTRATORS:
        return administrators != null && !administrators.isEmpty();
      case ContextsProjectPackage.CONTEXTS_PROJECT__CONTEXT_REFERENCES:
        return contextReferences != null && !contextReferences.isEmpty();
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
    result.append(" (parentProject: ");
    result.append(parentProject);
    result.append(')');
    return result.toString();
  }

} //ContextsProjectImpl
