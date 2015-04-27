/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.models.connector;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Connector</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.models.connector.Connector#getType <em>Type</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.models.connector.Connector#getUnsupportedService <em>Unsupported Service</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.models.connector.Connector#getConnectorId <em>Connector Id</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.models.connector.Connector#isMetadataResolver <em>Metadata Resolver</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.models.connector.Connector#getProgId <em>Prog Id</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.models.connector.Connector#getKeepOpen <em>Keep Open</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.models.connector.Connector#getLaunchArguments <em>Launch Arguments</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.thalesgroup.orchestra.framework.models.connector.ConnectorPackage#getConnector()
 * @model extendedMetaData="name='Connector' kind='elementOnly'"
 * @generated
 */
public interface Connector extends EObject {
  /**
   * Returns the value of the '<em><b>Unsupported Service</b></em>' containment reference list.
   * The list contents are of type {@link com.thalesgroup.orchestra.framework.models.connector.Service}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Unsupported Service</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Unsupported Service</em>' containment reference list.
   * @see com.thalesgroup.orchestra.framework.models.connector.ConnectorPackage#getConnector_UnsupportedService()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='unsupportedService'"
   * @generated
   */
  EList<Service> getUnsupportedService();

  /**
   * Returns the value of the '<em><b>Connector Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Connector Id</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Connector Id</em>' attribute.
   * @see #setConnectorId(String)
   * @see com.thalesgroup.orchestra.framework.models.connector.ConnectorPackage#getConnector_ConnectorId()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
   *        extendedMetaData="kind='attribute' name='connectorId'"
   * @generated
   */
  String getConnectorId();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.models.connector.Connector#getConnectorId <em>Connector Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Connector Id</em>' attribute.
   * @see #getConnectorId()
   * @generated
   */
  void setConnectorId(String value);

  /**
   * Returns the value of the '<em><b>Metadata Resolver</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Metadata Resolver</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Metadata Resolver</em>' attribute.
   * @see #isSetMetadataResolver()
   * @see #unsetMetadataResolver()
   * @see #setMetadataResolver(boolean)
   * @see com.thalesgroup.orchestra.framework.models.connector.ConnectorPackage#getConnector_MetadataResolver()
   * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
   *        extendedMetaData="kind='attribute' name='metadataResolver'"
   * @generated
   */
  boolean isMetadataResolver();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.models.connector.Connector#isMetadataResolver <em>Metadata Resolver</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Metadata Resolver</em>' attribute.
   * @see #isSetMetadataResolver()
   * @see #unsetMetadataResolver()
   * @see #isMetadataResolver()
   * @generated
   */
  void setMetadataResolver(boolean value);

  /**
   * Unsets the value of the '{@link com.thalesgroup.orchestra.framework.models.connector.Connector#isMetadataResolver <em>Metadata Resolver</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetMetadataResolver()
   * @see #isMetadataResolver()
   * @see #setMetadataResolver(boolean)
   * @generated
   */
  void unsetMetadataResolver();

  /**
   * Returns whether the value of the '{@link com.thalesgroup.orchestra.framework.models.connector.Connector#isMetadataResolver <em>Metadata Resolver</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Metadata Resolver</em>' attribute is set.
   * @see #unsetMetadataResolver()
   * @see #isMetadataResolver()
   * @see #setMetadataResolver(boolean)
   * @generated
   */
  boolean isSetMetadataResolver();

  /**
   * Returns the value of the '<em><b>Prog Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Prog Id</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Prog Id</em>' attribute.
   * @see #setProgId(String)
   * @see com.thalesgroup.orchestra.framework.models.connector.ConnectorPackage#getConnector_ProgId()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='attribute' name='progId'"
   * @generated
   */
  String getProgId();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.models.connector.Connector#getProgId <em>Prog Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Prog Id</em>' attribute.
   * @see #getProgId()
   * @generated
   */
  void setProgId(String value);

  /**
   * Returns the value of the '<em><b>Keep Open</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Keep Open</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Keep Open</em>' containment reference.
   * @see #setKeepOpen(KeepOpen)
   * @see com.thalesgroup.orchestra.framework.models.connector.ConnectorPackage#getConnector_KeepOpen()
   * @model containment="true"
   * @generated
   */
  KeepOpen getKeepOpen();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.models.connector.Connector#getKeepOpen <em>Keep Open</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Keep Open</em>' containment reference.
   * @see #getKeepOpen()
   * @generated
   */
  void setKeepOpen(KeepOpen value);

  /**
   * Returns the value of the '<em><b>Launch Arguments</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Launch Arguments</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Launch Arguments</em>' containment reference.
   * @see #setLaunchArguments(LaunchArguments)
   * @see com.thalesgroup.orchestra.framework.models.connector.ConnectorPackage#getConnector_LaunchArguments()
   * @model containment="true"
   * @generated
   */
  LaunchArguments getLaunchArguments();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.models.connector.Connector#getLaunchArguments <em>Launch Arguments</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Launch Arguments</em>' containment reference.
   * @see #getLaunchArguments()
   * @generated
   */
  void setLaunchArguments(LaunchArguments value);

  /**
   * Returns the value of the '<em><b>Type</b></em>' containment reference list.
   * The list contents are of type {@link com.thalesgroup.orchestra.framework.models.connector.Type}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Type</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Type</em>' containment reference list.
   * @see com.thalesgroup.orchestra.framework.models.connector.ConnectorPackage#getConnector_Type()
   * @model containment="true" required="true"
   *        extendedMetaData="kind='element' name='type'"
   * @generated
   */
  EList<Type> getType();

} // Connector
