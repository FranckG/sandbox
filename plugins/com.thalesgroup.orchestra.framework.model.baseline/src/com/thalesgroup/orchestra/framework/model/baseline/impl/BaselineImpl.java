/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.model.baseline.impl;

import com.thalesgroup.orchestra.framework.model.baseline.Baseline;
import com.thalesgroup.orchestra.framework.model.baseline.BaselinePackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Baseline</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.baseline.impl.BaselineImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.baseline.impl.BaselineImpl#getProjectRelativePath <em>Project Relative Path</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.baseline.impl.BaselineImpl#getContextId <em>Context Id</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.baseline.impl.BaselineImpl#getDescription <em>Description</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BaselineImpl extends MinimalEObjectImpl.Container implements Baseline {
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
   * The default value of the '{@link #getProjectRelativePath() <em>Project Relative Path</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getProjectRelativePath()
   * @generated
   * @ordered
   */
  protected static final String PROJECT_RELATIVE_PATH_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getProjectRelativePath() <em>Project Relative Path</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getProjectRelativePath()
   * @generated
   * @ordered
   */
  protected String projectRelativePath = PROJECT_RELATIVE_PATH_EDEFAULT;

  /**
   * The default value of the '{@link #getContextId() <em>Context Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getContextId()
   * @generated
   * @ordered
   */
  protected static final String CONTEXT_ID_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getContextId() <em>Context Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getContextId()
   * @generated
   * @ordered
   */
  protected String contextId = CONTEXT_ID_EDEFAULT;

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
  protected BaselineImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return BaselinePackage.Literals.BASELINE;
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
      eNotify(new ENotificationImpl(this, Notification.SET, BaselinePackage.BASELINE__NAME, oldName, name));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getProjectRelativePath() {
    return projectRelativePath;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setProjectRelativePath(String newProjectRelativePath) {
    String oldProjectRelativePath = projectRelativePath;
    projectRelativePath = newProjectRelativePath;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, BaselinePackage.BASELINE__PROJECT_RELATIVE_PATH, oldProjectRelativePath, projectRelativePath));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getContextId() {
    return contextId;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setContextId(String newContextId) {
    String oldContextId = contextId;
    contextId = newContextId;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, BaselinePackage.BASELINE__CONTEXT_ID, oldContextId, contextId));
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
      eNotify(new ENotificationImpl(this, Notification.SET, BaselinePackage.BASELINE__DESCRIPTION, oldDescription, description));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case BaselinePackage.BASELINE__NAME:
        return getName();
      case BaselinePackage.BASELINE__PROJECT_RELATIVE_PATH:
        return getProjectRelativePath();
      case BaselinePackage.BASELINE__CONTEXT_ID:
        return getContextId();
      case BaselinePackage.BASELINE__DESCRIPTION:
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
      case BaselinePackage.BASELINE__NAME:
        setName((String)newValue);
        return;
      case BaselinePackage.BASELINE__PROJECT_RELATIVE_PATH:
        setProjectRelativePath((String)newValue);
        return;
      case BaselinePackage.BASELINE__CONTEXT_ID:
        setContextId((String)newValue);
        return;
      case BaselinePackage.BASELINE__DESCRIPTION:
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
      case BaselinePackage.BASELINE__NAME:
        setName(NAME_EDEFAULT);
        return;
      case BaselinePackage.BASELINE__PROJECT_RELATIVE_PATH:
        setProjectRelativePath(PROJECT_RELATIVE_PATH_EDEFAULT);
        return;
      case BaselinePackage.BASELINE__CONTEXT_ID:
        setContextId(CONTEXT_ID_EDEFAULT);
        return;
      case BaselinePackage.BASELINE__DESCRIPTION:
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
      case BaselinePackage.BASELINE__NAME:
        return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
      case BaselinePackage.BASELINE__PROJECT_RELATIVE_PATH:
        return PROJECT_RELATIVE_PATH_EDEFAULT == null ? projectRelativePath != null : !PROJECT_RELATIVE_PATH_EDEFAULT.equals(projectRelativePath);
      case BaselinePackage.BASELINE__CONTEXT_ID:
        return CONTEXT_ID_EDEFAULT == null ? contextId != null : !CONTEXT_ID_EDEFAULT.equals(contextId);
      case BaselinePackage.BASELINE__DESCRIPTION:
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
  public String toString() {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (name: ");
    result.append(name);
    result.append(", projectRelativePath: ");
    result.append(projectRelativePath);
    result.append(", contextId: ");
    result.append(contextId);
    result.append(", description: ");
    result.append(description);
    result.append(')');
    return result.toString();
  }

} //BaselineImpl
