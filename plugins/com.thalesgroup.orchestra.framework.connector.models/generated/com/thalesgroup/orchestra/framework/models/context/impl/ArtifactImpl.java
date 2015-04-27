/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.models.context.impl;

import com.thalesgroup.orchestra.framework.models.context.Artifact;
import com.thalesgroup.orchestra.framework.models.context.ContextPackage;

import com.thalesgroup.orchestra.framework.models.context.EnvironmentProperty;
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
 * An implementation of the model object '<em><b>Artifact</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.models.context.impl.ArtifactImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.models.context.impl.ArtifactImpl#getEnvironmentProperties <em>Environment Properties</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.models.context.impl.ArtifactImpl#getEnvironmentId <em>Environment Id</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.models.context.impl.ArtifactImpl#getRootPhysicalPath <em>Root Physical Path</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.models.context.impl.ArtifactImpl#getUri <em>Uri</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ArtifactImpl extends EObjectImpl implements Artifact {
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
   * The default value of the '{@link #getEnvironmentId() <em>Environment Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEnvironmentId()
   * @generated
   * @ordered
   */
  protected static final String ENVIRONMENT_ID_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getEnvironmentId() <em>Environment Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEnvironmentId()
   * @generated
   * @ordered
   */
  protected String environmentId = ENVIRONMENT_ID_EDEFAULT;

  /**
   * The default value of the '{@link #getRootPhysicalPath() <em>Root Physical Path</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRootPhysicalPath()
   * @generated
   * @ordered
   */
  protected static final String ROOT_PHYSICAL_PATH_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getRootPhysicalPath() <em>Root Physical Path</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRootPhysicalPath()
   * @generated
   * @ordered
   */
  protected String rootPhysicalPath = ROOT_PHYSICAL_PATH_EDEFAULT;

  /**
   * The default value of the '{@link #getUri() <em>Uri</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getUri()
   * @generated
   * @ordered
   */
  protected static final String URI_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getUri() <em>Uri</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getUri()
   * @generated
   * @ordered
   */
  protected String uri = URI_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ArtifactImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return ContextPackage.Literals.ARTIFACT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FeatureMap getGroup() {
    if (group == null) {
      group = new BasicFeatureMap(this, ContextPackage.ARTIFACT__GROUP);
    }
    return group;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<EnvironmentProperty> getEnvironmentProperties() {
    return getGroup().list(ContextPackage.Literals.ARTIFACT__ENVIRONMENT_PROPERTIES);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getEnvironmentId() {
    return environmentId;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setEnvironmentId(String newEnvironmentId) {
    String oldEnvironmentId = environmentId;
    environmentId = newEnvironmentId;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContextPackage.ARTIFACT__ENVIRONMENT_ID, oldEnvironmentId, environmentId));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getRootPhysicalPath() {
    return rootPhysicalPath;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setRootPhysicalPath(String newRootPhysicalPath) {
    String oldRootPhysicalPath = rootPhysicalPath;
    rootPhysicalPath = newRootPhysicalPath;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContextPackage.ARTIFACT__ROOT_PHYSICAL_PATH, oldRootPhysicalPath, rootPhysicalPath));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getUri() {
    return uri;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setUri(String newUri) {
    String oldUri = uri;
    uri = newUri;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContextPackage.ARTIFACT__URI, oldUri, uri));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case ContextPackage.ARTIFACT__GROUP:
        return ((InternalEList<?>)getGroup()).basicRemove(otherEnd, msgs);
      case ContextPackage.ARTIFACT__ENVIRONMENT_PROPERTIES:
        return ((InternalEList<?>)getEnvironmentProperties()).basicRemove(otherEnd, msgs);
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
      case ContextPackage.ARTIFACT__GROUP:
        if (coreType) return getGroup();
        return ((FeatureMap.Internal)getGroup()).getWrapper();
      case ContextPackage.ARTIFACT__ENVIRONMENT_PROPERTIES:
        return getEnvironmentProperties();
      case ContextPackage.ARTIFACT__ENVIRONMENT_ID:
        return getEnvironmentId();
      case ContextPackage.ARTIFACT__ROOT_PHYSICAL_PATH:
        return getRootPhysicalPath();
      case ContextPackage.ARTIFACT__URI:
        return getUri();
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
      case ContextPackage.ARTIFACT__GROUP:
        ((FeatureMap.Internal)getGroup()).set(newValue);
        return;
      case ContextPackage.ARTIFACT__ENVIRONMENT_PROPERTIES:
        getEnvironmentProperties().clear();
        getEnvironmentProperties().addAll((Collection<? extends EnvironmentProperty>)newValue);
        return;
      case ContextPackage.ARTIFACT__ENVIRONMENT_ID:
        setEnvironmentId((String)newValue);
        return;
      case ContextPackage.ARTIFACT__ROOT_PHYSICAL_PATH:
        setRootPhysicalPath((String)newValue);
        return;
      case ContextPackage.ARTIFACT__URI:
        setUri((String)newValue);
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
      case ContextPackage.ARTIFACT__GROUP:
        getGroup().clear();
        return;
      case ContextPackage.ARTIFACT__ENVIRONMENT_PROPERTIES:
        getEnvironmentProperties().clear();
        return;
      case ContextPackage.ARTIFACT__ENVIRONMENT_ID:
        setEnvironmentId(ENVIRONMENT_ID_EDEFAULT);
        return;
      case ContextPackage.ARTIFACT__ROOT_PHYSICAL_PATH:
        setRootPhysicalPath(ROOT_PHYSICAL_PATH_EDEFAULT);
        return;
      case ContextPackage.ARTIFACT__URI:
        setUri(URI_EDEFAULT);
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
      case ContextPackage.ARTIFACT__GROUP:
        return group != null && !group.isEmpty();
      case ContextPackage.ARTIFACT__ENVIRONMENT_PROPERTIES:
        return !getEnvironmentProperties().isEmpty();
      case ContextPackage.ARTIFACT__ENVIRONMENT_ID:
        return ENVIRONMENT_ID_EDEFAULT == null ? environmentId != null : !ENVIRONMENT_ID_EDEFAULT.equals(environmentId);
      case ContextPackage.ARTIFACT__ROOT_PHYSICAL_PATH:
        return ROOT_PHYSICAL_PATH_EDEFAULT == null ? rootPhysicalPath != null : !ROOT_PHYSICAL_PATH_EDEFAULT.equals(rootPhysicalPath);
      case ContextPackage.ARTIFACT__URI:
        return URI_EDEFAULT == null ? uri != null : !URI_EDEFAULT.equals(uri);
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
    result.append(", environmentId: ");
    result.append(environmentId);
    result.append(", rootPhysicalPath: ");
    result.append(rootPhysicalPath);
    result.append(", uri: ");
    result.append(uri);
    result.append(')');
    return result.toString();
  }

} //ArtifactImpl
