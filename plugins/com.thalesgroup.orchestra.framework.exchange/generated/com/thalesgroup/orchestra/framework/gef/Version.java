/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.gef;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Version</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.Version#getFilePath <em>File Path</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.Version#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getVersion()
 * @model extendedMetaData="name='Version' kind='empty'"
 * @generated
 */
public interface Version extends EObject {
  /**
   * Returns the value of the '<em><b>File Path</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>File Path</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>File Path</em>' attribute.
   * @see #setFilePath(String)
   * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getVersion_FilePath()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='attribute' name='filePath'"
   * @generated
   */
  String getFilePath();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.gef.Version#getFilePath <em>File Path</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>File Path</em>' attribute.
   * @see #getFilePath()
   * @generated
   */
  void setFilePath(String value);

  /**
   * Returns the value of the '<em><b>Version</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Version</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Version</em>' attribute.
   * @see #setVersion(String)
   * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getVersion_Version()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='attribute' name='version'"
   * @generated
   */
  String getVersion();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.gef.Version#getVersion <em>Version</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Version</em>' attribute.
   * @see #getVersion()
   * @generated
   */
  void setVersion(String value);

} // Version
