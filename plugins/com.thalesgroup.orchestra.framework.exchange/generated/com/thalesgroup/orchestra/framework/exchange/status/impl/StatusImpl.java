/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.exchange.status.impl;

import com.thalesgroup.orchestra.framework.exchange.status.SeverityType;
import com.thalesgroup.orchestra.framework.exchange.status.Status;
import com.thalesgroup.orchestra.framework.exchange.status.StatusPackage;

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
 * An implementation of the model object '<em><b>Status</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.exchange.status.impl.StatusImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.exchange.status.impl.StatusImpl#getStatus <em>Status</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.exchange.status.impl.StatusImpl#getCode <em>Code</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.exchange.status.impl.StatusImpl#getExportFilePath <em>Export File Path</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.exchange.status.impl.StatusImpl#getMessage <em>Message</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.exchange.status.impl.StatusImpl#getSeverity <em>Severity</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.exchange.status.impl.StatusImpl#getUri <em>Uri</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class StatusImpl extends EObjectImpl implements Status {
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
   * The default value of the '{@link #getCode() <em>Code</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCode()
   * @generated
   * @ordered
   */
  protected static final int CODE_EDEFAULT = 0;

  /**
   * The cached value of the '{@link #getCode() <em>Code</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCode()
   * @generated
   * @ordered
   */
  protected int code = CODE_EDEFAULT;

  /**
   * This is true if the Code attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean codeESet;

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
   * The default value of the '{@link #getMessage() <em>Message</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMessage()
   * @generated
   * @ordered
   */
  protected static final String MESSAGE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getMessage() <em>Message</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMessage()
   * @generated
   * @ordered
   */
  protected String message = MESSAGE_EDEFAULT;

  /**
   * The default value of the '{@link #getSeverity() <em>Severity</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSeverity()
   * @generated
   * @ordered
   */
  protected static final SeverityType SEVERITY_EDEFAULT = SeverityType.OK;

  /**
   * The cached value of the '{@link #getSeverity() <em>Severity</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSeverity()
   * @generated
   * @ordered
   */
  protected SeverityType severity = SEVERITY_EDEFAULT;

  /**
   * This is true if the Severity attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean severityESet;

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
  protected StatusImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return StatusPackage.Literals.STATUS;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FeatureMap getGroup() {
    if (group == null) {
      group = new BasicFeatureMap(this, StatusPackage.STATUS__GROUP);
    }
    return group;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Status> getStatus() {
    return getGroup().list(StatusPackage.Literals.STATUS__STATUS);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public int getCode() {
    return code;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setCode(int newCode) {
    int oldCode = code;
    code = newCode;
    boolean oldCodeESet = codeESet;
    codeESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, StatusPackage.STATUS__CODE, oldCode, code, !oldCodeESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetCode() {
    int oldCode = code;
    boolean oldCodeESet = codeESet;
    code = CODE_EDEFAULT;
    codeESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, StatusPackage.STATUS__CODE, oldCode, CODE_EDEFAULT, oldCodeESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetCode() {
    return codeESet;
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
      eNotify(new ENotificationImpl(this, Notification.SET, StatusPackage.STATUS__EXPORT_FILE_PATH, oldExportFilePath, exportFilePath));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getMessage() {
    return message;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setMessage(String newMessage) {
    String oldMessage = message;
    message = newMessage;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, StatusPackage.STATUS__MESSAGE, oldMessage, message));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public SeverityType getSeverity() {
    return severity;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setSeverity(SeverityType newSeverity) {
    SeverityType oldSeverity = severity;
    severity = newSeverity == null ? SEVERITY_EDEFAULT : newSeverity;
    boolean oldSeverityESet = severityESet;
    severityESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, StatusPackage.STATUS__SEVERITY, oldSeverity, severity, !oldSeverityESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetSeverity() {
    SeverityType oldSeverity = severity;
    boolean oldSeverityESet = severityESet;
    severity = SEVERITY_EDEFAULT;
    severityESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, StatusPackage.STATUS__SEVERITY, oldSeverity, SEVERITY_EDEFAULT, oldSeverityESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetSeverity() {
    return severityESet;
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
      eNotify(new ENotificationImpl(this, Notification.SET, StatusPackage.STATUS__URI, oldUri, uri));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case StatusPackage.STATUS__GROUP:
        return ((InternalEList<?>)getGroup()).basicRemove(otherEnd, msgs);
      case StatusPackage.STATUS__STATUS:
        return ((InternalEList<?>)getStatus()).basicRemove(otherEnd, msgs);
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
      case StatusPackage.STATUS__GROUP:
        if (coreType) return getGroup();
        return ((FeatureMap.Internal)getGroup()).getWrapper();
      case StatusPackage.STATUS__STATUS:
        return getStatus();
      case StatusPackage.STATUS__CODE:
        return getCode();
      case StatusPackage.STATUS__EXPORT_FILE_PATH:
        return getExportFilePath();
      case StatusPackage.STATUS__MESSAGE:
        return getMessage();
      case StatusPackage.STATUS__SEVERITY:
        return getSeverity();
      case StatusPackage.STATUS__URI:
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
      case StatusPackage.STATUS__GROUP:
        ((FeatureMap.Internal)getGroup()).set(newValue);
        return;
      case StatusPackage.STATUS__STATUS:
        getStatus().clear();
        getStatus().addAll((Collection<? extends Status>)newValue);
        return;
      case StatusPackage.STATUS__CODE:
        setCode((Integer)newValue);
        return;
      case StatusPackage.STATUS__EXPORT_FILE_PATH:
        setExportFilePath((String)newValue);
        return;
      case StatusPackage.STATUS__MESSAGE:
        setMessage((String)newValue);
        return;
      case StatusPackage.STATUS__SEVERITY:
        setSeverity((SeverityType)newValue);
        return;
      case StatusPackage.STATUS__URI:
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
      case StatusPackage.STATUS__GROUP:
        getGroup().clear();
        return;
      case StatusPackage.STATUS__STATUS:
        getStatus().clear();
        return;
      case StatusPackage.STATUS__CODE:
        unsetCode();
        return;
      case StatusPackage.STATUS__EXPORT_FILE_PATH:
        setExportFilePath(EXPORT_FILE_PATH_EDEFAULT);
        return;
      case StatusPackage.STATUS__MESSAGE:
        setMessage(MESSAGE_EDEFAULT);
        return;
      case StatusPackage.STATUS__SEVERITY:
        unsetSeverity();
        return;
      case StatusPackage.STATUS__URI:
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
      case StatusPackage.STATUS__GROUP:
        return group != null && !group.isEmpty();
      case StatusPackage.STATUS__STATUS:
        return !getStatus().isEmpty();
      case StatusPackage.STATUS__CODE:
        return isSetCode();
      case StatusPackage.STATUS__EXPORT_FILE_PATH:
        return EXPORT_FILE_PATH_EDEFAULT == null ? exportFilePath != null : !EXPORT_FILE_PATH_EDEFAULT.equals(exportFilePath);
      case StatusPackage.STATUS__MESSAGE:
        return MESSAGE_EDEFAULT == null ? message != null : !MESSAGE_EDEFAULT.equals(message);
      case StatusPackage.STATUS__SEVERITY:
        return isSetSeverity();
      case StatusPackage.STATUS__URI:
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
    result.append(", code: ");
    if (codeESet) result.append(code); else result.append("<unset>");
    result.append(", exportFilePath: ");
    result.append(exportFilePath);
    result.append(", message: ");
    result.append(message);
    result.append(", severity: ");
    if (severityESet) result.append(severity); else result.append("<unset>");
    result.append(", uri: ");
    result.append(uri);
    result.append(')');
    return result.toString();
  }

} //StatusImpl
