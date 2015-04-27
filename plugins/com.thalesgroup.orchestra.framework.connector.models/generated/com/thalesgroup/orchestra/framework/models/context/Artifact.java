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
 * A representation of the model object '<em><b>Artifact</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.models.context.Artifact#getGroup <em>Group</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.models.context.Artifact#getEnvironmentProperties <em>Environment Properties</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.models.context.Artifact#getEnvironmentId <em>Environment Id</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.models.context.Artifact#getRootPhysicalPath <em>Root Physical Path</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.models.context.Artifact#getUri <em>Uri</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.thalesgroup.orchestra.framework.models.context.ContextPackage#getArtifact()
 * @model extendedMetaData="name='Artifact' kind='elementOnly'"
 * @generated
 */
public interface Artifact extends EObject {
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
   * @see com.thalesgroup.orchestra.framework.models.context.ContextPackage#getArtifact_Group()
   * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
   *        extendedMetaData="kind='group' name='group:0'"
   * @generated
   */
  FeatureMap getGroup();

  /**
   * Returns the value of the '<em><b>Environment Properties</b></em>' containment reference list.
   * The list contents are of type {@link com.thalesgroup.orchestra.framework.models.context.EnvironmentProperty}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Environment Properties</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Environment Properties</em>' containment reference list.
   * @see com.thalesgroup.orchestra.framework.models.context.ContextPackage#getArtifact_EnvironmentProperties()
   * @model containment="true" required="true" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='environmentProperties' group='#group:0'"
   * @generated
   */
  EList<EnvironmentProperty> getEnvironmentProperties();

  /**
   * Returns the value of the '<em><b>Environment Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Environment Id</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Environment Id</em>' attribute.
   * @see #setEnvironmentId(String)
   * @see com.thalesgroup.orchestra.framework.models.context.ContextPackage#getArtifact_EnvironmentId()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
   *        extendedMetaData="kind='attribute' name='environmentId'"
   * @generated
   */
  String getEnvironmentId();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.models.context.Artifact#getEnvironmentId <em>Environment Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Environment Id</em>' attribute.
   * @see #getEnvironmentId()
   * @generated
   */
  void setEnvironmentId(String value);

  /**
   * Returns the value of the '<em><b>Root Physical Path</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Root Physical Path</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Root Physical Path</em>' attribute.
   * @see #setRootPhysicalPath(String)
   * @see com.thalesgroup.orchestra.framework.models.context.ContextPackage#getArtifact_RootPhysicalPath()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='attribute' name='rootPhysicalPath'"
   * @generated
   */
  String getRootPhysicalPath();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.models.context.Artifact#getRootPhysicalPath <em>Root Physical Path</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Root Physical Path</em>' attribute.
   * @see #getRootPhysicalPath()
   * @generated
   */
  void setRootPhysicalPath(String value);

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
   * @see com.thalesgroup.orchestra.framework.models.context.ContextPackage#getArtifact_Uri()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
   *        extendedMetaData="kind='attribute' name='uri'"
   * @generated
   */
  String getUri();

  /**
   * Sets the value of the '{@link com.thalesgroup.orchestra.framework.models.context.Artifact#getUri <em>Uri</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Uri</em>' attribute.
   * @see #getUri()
   * @generated
   */
  void setUri(String value);

} // Artifact
