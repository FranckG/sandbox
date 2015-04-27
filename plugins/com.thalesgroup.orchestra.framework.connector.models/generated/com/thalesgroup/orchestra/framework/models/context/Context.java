/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.models.context;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Context</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.models.context.Context#getGroup <em>Group</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.models.context.Context#getArtifact <em>Artifact</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.models.context.Context#getExportFilePath <em>Export File Path</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.models.context.Context#getType <em>Type</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.models.context.Context#isKeepOpen <em>Keep Open</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.models.context.Context#getLaunchArguments <em>Launch Arguments</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.thalesgroup.orchestra.framework.models.context.ContextPackage#getContext()
 * @model extendedMetaData="name='Context' kind='elementOnly'"
 * @generated
 */
public interface Context extends EObject {
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
   * @see com.thalesgroup.orchestra.framework.models.context.ContextPackage#getContext_Group()
   * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
   *        extendedMetaData="kind='group' name='group:0'"
   * @generated
   */
  FeatureMap getGroup();

  /**
   * Returns the value of the '<em><b>Artifact</b></em>' containment reference list.
   * The list contents are of type {@link com.thalesgroup.orchestra.framework.models.context.Artifact}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Artifact</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Artifact</em>' containment reference list.
   * @see com.thalesgroup.orchestra.framework.models.context.ContextPackage#getContext_Artifact()
   * @model containment="true" required="true" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='artifact' group='#group:0'"
   * @generated
   */
  EList<Artifact> getArtifact();

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
   * @see com.thalesgroup.orchestra.framework.models.context.ContextPackage#getContext_ExportFilePath()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
   *        extendedMetaData="kind='attribute' name='exportFilePath'"
   * @generated
   */
  String getExportFilePath();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.models.context.Context#getExportFilePath <em>Export File Path</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Export File Path</em>' attribute.
   * @see #getExportFilePath()
   * @generated
   */
  void setExportFilePath(String value);

  /**
   * Returns the value of the '<em><b>Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Type</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Type</em>' attribute.
   * @see #setType(String)
   * @see com.thalesgroup.orchestra.framework.models.context.ContextPackage#getContext_Type()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
   *        extendedMetaData="kind='attribute' name='type'"
   * @generated
   */
  String getType();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.models.context.Context#getType <em>Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Type</em>' attribute.
   * @see #getType()
   * @generated
   */
  void setType(String value);

  /**
   * Returns the value of the '<em><b>Keep Open</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Keep Open</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Keep Open</em>' attribute.
   * @see #setKeepOpen(boolean)
   * @see com.thalesgroup.orchestra.framework.models.context.ContextPackage#getContext_KeepOpen()
   * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
   * @generated
   */
  boolean isKeepOpen();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.models.context.Context#isKeepOpen <em>Keep Open</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Keep Open</em>' attribute.
   * @see #isKeepOpen()
   * @generated
   */
  void setKeepOpen(boolean value);

  /**
   * Returns the value of the '<em><b>Launch Arguments</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Launch Arguments</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Launch Arguments</em>' attribute.
   * @see #setLaunchArguments(String)
   * @see com.thalesgroup.orchestra.framework.models.context.ContextPackage#getContext_LaunchArguments()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String"
   * @generated
   */
  String getLaunchArguments();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.models.context.Context#getLaunchArguments <em>Launch Arguments</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Launch Arguments</em>' attribute.
   * @see #getLaunchArguments()
   * @generated
   */
  void setLaunchArguments(String value);

} // Context
