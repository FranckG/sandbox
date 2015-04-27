/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.models.context.impl;

import com.thalesgroup.orchestra.framework.models.context.Artifact;
import com.thalesgroup.orchestra.framework.models.context.Context;
import com.thalesgroup.orchestra.framework.models.context.ContextPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Context</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.models.context.impl.ContextImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.models.context.impl.ContextImpl#getArtifact <em>Artifact</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.models.context.impl.ContextImpl#getExportFilePath <em>Export File Path</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.models.context.impl.ContextImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.models.context.impl.ContextImpl#isKeepOpen <em>Keep Open</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.models.context.impl.ContextImpl#getLaunchArguments <em>Launch Arguments</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ContextImpl extends EObjectImpl implements Context {
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
   * The default value of the '{@link #getExportFilePath() <em>Export File Path</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExportFilePath()
   * @generated
   * @ordered
   */
  protected static final String EXPORT_FILE_PATH_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getExportFilePath() <em>Export File Path</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExportFilePath()
   * @generated
   * @ordered
   */
  protected String exportFilePath = EXPORT_FILE_PATH_EDEFAULT;

  /**
   * The default value of the '{@link #getType() <em>Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getType()
   * @generated
   * @ordered
   */
  protected static final String TYPE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getType()
   * @generated
   * @ordered
   */
  protected String type = TYPE_EDEFAULT;

  /**
   * The default value of the '{@link #isKeepOpen() <em>Keep Open</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isKeepOpen()
   * @generated
   * @ordered
   */
  protected static final boolean KEEP_OPEN_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isKeepOpen() <em>Keep Open</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isKeepOpen()
   * @generated
   * @ordered
   */
  protected boolean keepOpen = KEEP_OPEN_EDEFAULT;

  /**
   * The default value of the '{@link #getLaunchArguments() <em>Launch Arguments</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLaunchArguments()
   * @generated
   * @ordered
   */
  protected static final String LAUNCH_ARGUMENTS_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getLaunchArguments() <em>Launch Arguments</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLaunchArguments()
   * @generated
   * @ordered
   */
  protected String launchArguments = LAUNCH_ARGUMENTS_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ContextImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return ContextPackage.Literals.CONTEXT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FeatureMap getGroup() {
    if (group == null) {
      group = new BasicFeatureMap(this, ContextPackage.CONTEXT__GROUP);
    }
    return group;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Artifact> getArtifact() {
    return getGroup().list(ContextPackage.Literals.CONTEXT__ARTIFACT);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getExportFilePath() {
    return exportFilePath;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setExportFilePath(String newExportFilePath) {
    String oldExportFilePath = exportFilePath;
    exportFilePath = newExportFilePath;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContextPackage.CONTEXT__EXPORT_FILE_PATH, oldExportFilePath, exportFilePath));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getType() {
    return type;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setType(String newType) {
    String oldType = type;
    type = newType;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContextPackage.CONTEXT__TYPE, oldType, type));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isKeepOpen() {
    return keepOpen;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setKeepOpen(boolean newKeepOpen) {
    boolean oldKeepOpen = keepOpen;
    keepOpen = newKeepOpen;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContextPackage.CONTEXT__KEEP_OPEN, oldKeepOpen, keepOpen));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getLaunchArguments() {
    return launchArguments;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setLaunchArguments(String newLaunchArguments) {
    String oldLaunchArguments = launchArguments;
    launchArguments = newLaunchArguments;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContextPackage.CONTEXT__LAUNCH_ARGUMENTS, oldLaunchArguments, launchArguments));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case ContextPackage.CONTEXT__GROUP:
        return ((InternalEList<?>)getGroup()).basicRemove(otherEnd, msgs);
      case ContextPackage.CONTEXT__ARTIFACT:
        return ((InternalEList<?>)getArtifact()).basicRemove(otherEnd, msgs);
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
      case ContextPackage.CONTEXT__GROUP:
        if (coreType) return getGroup();
        return ((FeatureMap.Internal)getGroup()).getWrapper();
      case ContextPackage.CONTEXT__ARTIFACT:
        return getArtifact();
      case ContextPackage.CONTEXT__EXPORT_FILE_PATH:
        return getExportFilePath();
      case ContextPackage.CONTEXT__TYPE:
        return getType();
      case ContextPackage.CONTEXT__KEEP_OPEN:
        return isKeepOpen();
      case ContextPackage.CONTEXT__LAUNCH_ARGUMENTS:
        return getLaunchArguments();
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
      case ContextPackage.CONTEXT__GROUP:
        ((FeatureMap.Internal)getGroup()).set(newValue);
        return;
      case ContextPackage.CONTEXT__ARTIFACT:
        getArtifact().clear();
        getArtifact().addAll((Collection<? extends Artifact>)newValue);
        return;
      case ContextPackage.CONTEXT__EXPORT_FILE_PATH:
        setExportFilePath((String)newValue);
        return;
      case ContextPackage.CONTEXT__TYPE:
        setType((String)newValue);
        return;
      case ContextPackage.CONTEXT__KEEP_OPEN:
        setKeepOpen((Boolean)newValue);
        return;
      case ContextPackage.CONTEXT__LAUNCH_ARGUMENTS:
        setLaunchArguments((String)newValue);
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
      case ContextPackage.CONTEXT__GROUP:
        getGroup().clear();
        return;
      case ContextPackage.CONTEXT__ARTIFACT:
        getArtifact().clear();
        return;
      case ContextPackage.CONTEXT__EXPORT_FILE_PATH:
        setExportFilePath(EXPORT_FILE_PATH_EDEFAULT);
        return;
      case ContextPackage.CONTEXT__TYPE:
        setType(TYPE_EDEFAULT);
        return;
      case ContextPackage.CONTEXT__KEEP_OPEN:
        setKeepOpen(KEEP_OPEN_EDEFAULT);
        return;
      case ContextPackage.CONTEXT__LAUNCH_ARGUMENTS:
        setLaunchArguments(LAUNCH_ARGUMENTS_EDEFAULT);
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
      case ContextPackage.CONTEXT__GROUP:
        return group != null && !group.isEmpty();
      case ContextPackage.CONTEXT__ARTIFACT:
        return !getArtifact().isEmpty();
      case ContextPackage.CONTEXT__EXPORT_FILE_PATH:
        return EXPORT_FILE_PATH_EDEFAULT == null ? exportFilePath != null : !EXPORT_FILE_PATH_EDEFAULT.equals(exportFilePath);
      case ContextPackage.CONTEXT__TYPE:
        return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
      case ContextPackage.CONTEXT__KEEP_OPEN:
        return keepOpen != KEEP_OPEN_EDEFAULT;
      case ContextPackage.CONTEXT__LAUNCH_ARGUMENTS:
        return LAUNCH_ARGUMENTS_EDEFAULT == null ? launchArguments != null : !LAUNCH_ARGUMENTS_EDEFAULT.equals(launchArguments);
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
    result.append(", exportFilePath: ");
    result.append(exportFilePath);
    result.append(", type: ");
    result.append(type);
    result.append(", keepOpen: ");
    result.append(keepOpen);
    result.append(", launchArguments: ");
    result.append(launchArguments);
    result.append(')');
    return result.toString();
  }

} //ContextImpl
