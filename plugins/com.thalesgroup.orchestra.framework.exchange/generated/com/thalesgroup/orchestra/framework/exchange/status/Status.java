/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.exchange.status;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Status</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.exchange.status.Status#getGroup <em>Group</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.exchange.status.Status#getStatus <em>Status</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.exchange.status.Status#getCode <em>Code</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.exchange.status.Status#getExportFilePath <em>Export File Path</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.exchange.status.Status#getMessage <em>Message</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.exchange.status.Status#getSeverity <em>Severity</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.exchange.status.Status#getUri <em>Uri</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.thalesgroup.orchestra.framework.exchange.status.StatusPackage#getStatus()
 * @model extendedMetaData="name='Status' kind='elementOnly'"
 * @generated
 */
public interface Status extends EObject {
  /**
   * Returns the value of the '<em><b>Group</b></em>' attribute list.
   * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Group</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Group</em>' attribute list.
   * @see com.thalesgroup.orchestra.framework.exchange.status.StatusPackage#getStatus_Group()
   * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
   *        extendedMetaData="kind='group' name='group:0'"
   * @generated
   */
  FeatureMap getGroup();

  /**
   * Returns the value of the '<em><b>Status</b></em>' containment reference list.
   * The list contents are of type {@link com.thalesgroup.orchestra.framework.exchange.status.Status}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Status</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Status</em>' containment reference list.
   * @see com.thalesgroup.orchestra.framework.exchange.status.StatusPackage#getStatus_Status()
   * @model containment="true" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='status' group='#group:0'"
   * @generated
   */
  EList<Status> getStatus();

  /**
   * Returns the value of the '<em><b>Code</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Code</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Code</em>' attribute.
   * @see #isSetCode()
   * @see #unsetCode()
   * @see #setCode(int)
   * @see com.thalesgroup.orchestra.framework.exchange.status.StatusPackage#getStatus_Code()
   * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
   *        extendedMetaData="kind='attribute' name='code'"
   * @generated
   */
  int getCode();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.exchange.status.Status#getCode <em>Code</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Code</em>' attribute.
   * @see #isSetCode()
   * @see #unsetCode()
   * @see #getCode()
   * @generated
   */
  void setCode(int value);

  /**
   * Unsets the value of the '{@link com.thalesgroup.orchestra.framework.exchange.status.Status#getCode <em>Code</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetCode()
   * @see #getCode()
   * @see #setCode(int)
   * @generated
   */
  void unsetCode();

  /**
   * Returns whether the value of the '{@link com.thalesgroup.orchestra.framework.exchange.status.Status#getCode <em>Code</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Code</em>' attribute is set.
   * @see #unsetCode()
   * @see #getCode()
   * @see #setCode(int)
   * @generated
   */
  boolean isSetCode();

  /**
   * Returns the value of the '<em><b>Export File Path</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Export File Path</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Export File Path</em>' attribute.
   * @see #setExportFilePath(String)
   * @see com.thalesgroup.orchestra.framework.exchange.status.StatusPackage#getStatus_ExportFilePath()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='attribute' name='exportFilePath'"
   * @generated
   */
  String getExportFilePath();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.exchange.status.Status#getExportFilePath <em>Export File Path</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Export File Path</em>' attribute.
   * @see #getExportFilePath()
   * @generated
   */
  void setExportFilePath(String value);

  /**
   * Returns the value of the '<em><b>Message</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Message</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Message</em>' attribute.
   * @see #setMessage(String)
   * @see com.thalesgroup.orchestra.framework.exchange.status.StatusPackage#getStatus_Message()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
   *        extendedMetaData="kind='attribute' name='message'"
   * @generated
   */
  String getMessage();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.exchange.status.Status#getMessage <em>Message</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Message</em>' attribute.
   * @see #getMessage()
   * @generated
   */
  void setMessage(String value);

  /**
   * Returns the value of the '<em><b>Severity</b></em>' attribute.
   * The literals are from the enumeration {@link com.thalesgroup.orchestra.framework.exchange.status.SeverityType}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Severity</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Severity</em>' attribute.
   * @see com.thalesgroup.orchestra.framework.exchange.status.SeverityType
   * @see #isSetSeverity()
   * @see #unsetSeverity()
   * @see #setSeverity(SeverityType)
   * @see com.thalesgroup.orchestra.framework.exchange.status.StatusPackage#getStatus_Severity()
   * @model unsettable="true" required="true"
   *        extendedMetaData="kind='attribute' name='severity'"
   * @generated
   */
  SeverityType getSeverity();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.exchange.status.Status#getSeverity <em>Severity</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Severity</em>' attribute.
   * @see com.thalesgroup.orchestra.framework.exchange.status.SeverityType
   * @see #isSetSeverity()
   * @see #unsetSeverity()
   * @see #getSeverity()
   * @generated
   */
  void setSeverity(SeverityType value);

  /**
   * Unsets the value of the '{@link com.thalesgroup.orchestra.framework.exchange.status.Status#getSeverity <em>Severity</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetSeverity()
   * @see #getSeverity()
   * @see #setSeverity(SeverityType)
   * @generated
   */
  void unsetSeverity();

  /**
   * Returns whether the value of the '{@link com.thalesgroup.orchestra.framework.exchange.status.Status#getSeverity <em>Severity</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Severity</em>' attribute is set.
   * @see #unsetSeverity()
   * @see #getSeverity()
   * @see #setSeverity(SeverityType)
   * @generated
   */
  boolean isSetSeverity();

  /**
   * Returns the value of the '<em><b>Uri</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Uri</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Uri</em>' attribute.
   * @see #setUri(String)
   * @see com.thalesgroup.orchestra.framework.exchange.status.StatusPackage#getStatus_Uri()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='attribute' name='uri'"
   * @generated
   */
  String getUri();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.exchange.status.Status#getUri <em>Uri</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Uri</em>' attribute.
   * @see #getUri()
   * @generated
   */
  void setUri(String value);

} // Status
