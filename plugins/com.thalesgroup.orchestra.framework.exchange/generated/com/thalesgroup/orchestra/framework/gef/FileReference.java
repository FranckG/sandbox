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
 * A representation of the model object '<em><b>File Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.FileReference#getMimeType <em>Mime Type</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.FileReference#getNature <em>Nature</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.FileReference#getUrl <em>Url</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getFileReference()
 * @model extendedMetaData="name='FileReference' kind='empty'"
 * @generated
 */
public interface FileReference extends EObject {
  /**
   * Returns the value of the '<em><b>Mime Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Mime Type</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Mime Type</em>' attribute.
   * @see #setMimeType(String)
   * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getFileReference_MimeType()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
   *        extendedMetaData="kind='attribute' name='mimeType'"
   * @generated
   */
  String getMimeType();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.gef.FileReference#getMimeType <em>Mime Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Mime Type</em>' attribute.
   * @see #getMimeType()
   * @generated
   */
  void setMimeType(String value);

  /**
   * Returns the value of the '<em><b>Nature</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Nature</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Nature</em>' attribute.
   * @see #setNature(String)
   * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getFileReference_Nature()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='attribute' name='nature'"
   * @generated
   */
  String getNature();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.gef.FileReference#getNature <em>Nature</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Nature</em>' attribute.
   * @see #getNature()
   * @generated
   */
  void setNature(String value);

  /**
   * Returns the value of the '<em><b>Url</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Url</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Url</em>' attribute.
   * @see #setUrl(String)
   * @see com.thalesgroup.orchestra.framework.gef.GefPackage#getFileReference_Url()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
   *        extendedMetaData="kind='attribute' name='url'"
   * @generated
   */
  String getUrl();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.gef.FileReference#getUrl <em>Url</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Url</em>' attribute.
   * @see #getUrl()
   * @generated
   */
  void setUrl(String value);

} // FileReference
