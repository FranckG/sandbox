/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.UriMigration.impl;

import com.thalesgroup.orchestra.framework.UriMigration.Migration;
import com.thalesgroup.orchestra.framework.UriMigration.UriMigrationPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Migration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.UriMigration.impl.MigrationImpl#getToolName <em>Tool Name</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.UriMigration.impl.MigrationImpl#getRootType <em>Root Type</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.UriMigration.impl.MigrationImpl#isInvokeConnector <em>Invoke Connector</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MigrationImpl extends EObjectImpl implements Migration {
  /**
   * The default value of the '{@link #getToolName() <em>Tool Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getToolName()
   * @generated
   * @ordered
   */
  protected static final String TOOL_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getToolName() <em>Tool Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getToolName()
   * @generated
   * @ordered
   */
  protected String toolName = TOOL_NAME_EDEFAULT;

  /**
   * The default value of the '{@link #getRootType() <em>Root Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRootType()
   * @generated
   * @ordered
   */
  protected static final String ROOT_TYPE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getRootType() <em>Root Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRootType()
   * @generated
   * @ordered
   */
  protected String rootType = ROOT_TYPE_EDEFAULT;

  /**
   * The default value of the '{@link #isInvokeConnector() <em>Invoke Connector</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isInvokeConnector()
   * @generated
   * @ordered
   */
  protected static final boolean INVOKE_CONNECTOR_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isInvokeConnector() <em>Invoke Connector</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isInvokeConnector()
   * @generated
   * @ordered
   */
  protected boolean invokeConnector = INVOKE_CONNECTOR_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected MigrationImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return UriMigrationPackage.Literals.MIGRATION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getToolName() {
    return toolName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setToolName(String newToolName) {
    String oldToolName = toolName;
    toolName = newToolName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, UriMigrationPackage.MIGRATION__TOOL_NAME, oldToolName, toolName));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getRootType() {
    return rootType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setRootType(String newRootType) {
    String oldRootType = rootType;
    rootType = newRootType;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, UriMigrationPackage.MIGRATION__ROOT_TYPE, oldRootType, rootType));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isInvokeConnector() {
    return invokeConnector;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setInvokeConnector(boolean newInvokeConnector) {
    boolean oldInvokeConnector = invokeConnector;
    invokeConnector = newInvokeConnector;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, UriMigrationPackage.MIGRATION__INVOKE_CONNECTOR, oldInvokeConnector, invokeConnector));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case UriMigrationPackage.MIGRATION__TOOL_NAME:
        return getToolName();
      case UriMigrationPackage.MIGRATION__ROOT_TYPE:
        return getRootType();
      case UriMigrationPackage.MIGRATION__INVOKE_CONNECTOR:
        return isInvokeConnector();
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
      case UriMigrationPackage.MIGRATION__TOOL_NAME:
        setToolName((String)newValue);
        return;
      case UriMigrationPackage.MIGRATION__ROOT_TYPE:
        setRootType((String)newValue);
        return;
      case UriMigrationPackage.MIGRATION__INVOKE_CONNECTOR:
        setInvokeConnector((Boolean)newValue);
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
      case UriMigrationPackage.MIGRATION__TOOL_NAME:
        setToolName(TOOL_NAME_EDEFAULT);
        return;
      case UriMigrationPackage.MIGRATION__ROOT_TYPE:
        setRootType(ROOT_TYPE_EDEFAULT);
        return;
      case UriMigrationPackage.MIGRATION__INVOKE_CONNECTOR:
        setInvokeConnector(INVOKE_CONNECTOR_EDEFAULT);
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
      case UriMigrationPackage.MIGRATION__TOOL_NAME:
        return TOOL_NAME_EDEFAULT == null ? toolName != null : !TOOL_NAME_EDEFAULT.equals(toolName);
      case UriMigrationPackage.MIGRATION__ROOT_TYPE:
        return ROOT_TYPE_EDEFAULT == null ? rootType != null : !ROOT_TYPE_EDEFAULT.equals(rootType);
      case UriMigrationPackage.MIGRATION__INVOKE_CONNECTOR:
        return invokeConnector != INVOKE_CONNECTOR_EDEFAULT;
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
    result.append(" (toolName: ");
    result.append(toolName);
    result.append(", rootType: ");
    result.append(rootType);
    result.append(", invokeConnector: ");
    result.append(invokeConnector);
    result.append(')');
    return result.toString();
  }

} //MigrationImpl
