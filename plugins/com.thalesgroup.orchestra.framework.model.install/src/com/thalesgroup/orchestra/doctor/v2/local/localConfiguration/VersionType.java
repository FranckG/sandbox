/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.doctor.v2.local.localConfiguration;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Version Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionType#getParameter <em>Parameter</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionType#getCompatibility <em>Compatibility</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionType#getConfigConnector <em>Config Connector</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionType#getExecName <em>Exec Name</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionType#getExecRelativePath <em>Exec Relative Path</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionType#getIconPath <em>Icon Path</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionType#getInstallLocation <em>Install Location</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionType#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ConfigurationPackage#getVersionType()
 * @model extendedMetaData="name='version_._type' kind='elementOnly'"
 * @generated
 */
public interface VersionType extends EObject {
  /**
   * Returns the value of the '<em><b>Parameter</b></em>' containment reference list.
   * The list contents are of type {@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ParameterType}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Parameter</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Parameter</em>' containment reference list.
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ConfigurationPackage#getVersionType_Parameter()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='parameter'"
   * @generated
   */
  EList<ParameterType> getParameter();

  /**
   * Returns the value of the '<em><b>Compatibility</b></em>' containment reference list.
   * The list contents are of type {@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.CompatibilityType}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Compatibility</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Compatibility</em>' containment reference list.
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ConfigurationPackage#getVersionType_Compatibility()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='compatibility'"
   * @generated
   */
  EList<CompatibilityType> getCompatibility();

  /**
   * Returns the value of the '<em><b>Config Connector</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Config Connector</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Config Connector</em>' attribute.
   * @see #setConfigConnector(String)
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ConfigurationPackage#getVersionType_ConfigConnector()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='attribute' name='configConnector'"
   * @generated
   */
  String getConfigConnector();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionType#getConfigConnector <em>Config Connector</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Config Connector</em>' attribute.
   * @see #getConfigConnector()
   * @generated
   */
  void setConfigConnector(String value);

  /**
   * Returns the value of the '<em><b>Exec Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Exec Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Exec Name</em>' attribute.
   * @see #setExecName(String)
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ConfigurationPackage#getVersionType_ExecName()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='attribute' name='execName'"
   * @generated
   */
  String getExecName();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionType#getExecName <em>Exec Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Exec Name</em>' attribute.
   * @see #getExecName()
   * @generated
   */
  void setExecName(String value);

  /**
   * Returns the value of the '<em><b>Exec Relative Path</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Exec Relative Path</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Exec Relative Path</em>' attribute.
   * @see #setExecRelativePath(String)
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ConfigurationPackage#getVersionType_ExecRelativePath()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='attribute' name='execRelativePath'"
   * @generated
   */
  String getExecRelativePath();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionType#getExecRelativePath <em>Exec Relative Path</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Exec Relative Path</em>' attribute.
   * @see #getExecRelativePath()
   * @generated
   */
  void setExecRelativePath(String value);

  /**
   * Returns the value of the '<em><b>Icon Path</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Icon Path</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Icon Path</em>' attribute.
   * @see #setIconPath(String)
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ConfigurationPackage#getVersionType_IconPath()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='attribute' name='iconPath'"
   * @generated
   */
  String getIconPath();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionType#getIconPath <em>Icon Path</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Icon Path</em>' attribute.
   * @see #getIconPath()
   * @generated
   */
  void setIconPath(String value);

  /**
   * Returns the value of the '<em><b>Install Location</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Install Location</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Install Location</em>' attribute.
   * @see #setInstallLocation(String)
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ConfigurationPackage#getVersionType_InstallLocation()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
   *        extendedMetaData="kind='attribute' name='installLocation'"
   * @generated
   */
  String getInstallLocation();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionType#getInstallLocation <em>Install Location</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Install Location</em>' attribute.
   * @see #getInstallLocation()
   * @generated
   */
  void setInstallLocation(String value);

  /**
   * Returns the value of the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Value</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Value</em>' attribute.
   * @see #setValue(String)
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ConfigurationPackage#getVersionType_Value()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
   *        extendedMetaData="kind='attribute' name='value'"
   * @generated
   */
  String getValue();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionType#getValue <em>Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Value</em>' attribute.
   * @see #getValue()
   * @generated
   */
  void setValue(String value);

} // VersionType
