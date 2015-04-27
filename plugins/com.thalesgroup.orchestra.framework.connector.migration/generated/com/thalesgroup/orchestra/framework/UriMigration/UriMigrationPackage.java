/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.UriMigration;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.thalesgroup.orchestra.framework.UriMigration.UriMigrationFactory
 * @model kind="package"
 * @generated
 */
public interface UriMigrationPackage extends EPackage {
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "UriMigration";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://www.thalesgroup.com/orchestra/framework/4_0_27/UriMigration";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "UriMigration";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  UriMigrationPackage eINSTANCE = com.thalesgroup.orchestra.framework.UriMigration.impl.UriMigrationPackageImpl.init();

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.UriMigration.impl.MigrationImpl <em>Migration</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.UriMigration.impl.MigrationImpl
   * @see com.thalesgroup.orchestra.framework.UriMigration.impl.UriMigrationPackageImpl#getMigration()
   * @generated
   */
  int MIGRATION = 0;

  /**
   * The feature id for the '<em><b>Tool Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MIGRATION__TOOL_NAME = 0;

  /**
   * The feature id for the '<em><b>Root Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MIGRATION__ROOT_TYPE = 1;

  /**
   * The feature id for the '<em><b>Invoke Connector</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MIGRATION__INVOKE_CONNECTOR = 2;

  /**
   * The number of structural features of the '<em>Migration</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MIGRATION_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.UriMigration.impl.MigrationDefinitionImpl <em>Migration Definition</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.UriMigration.impl.MigrationDefinitionImpl
   * @see com.thalesgroup.orchestra.framework.UriMigration.impl.UriMigrationPackageImpl#getMigrationDefinition()
   * @generated
   */
  int MIGRATION_DEFINITION = 1;

  /**
   * The feature id for the '<em><b>Migrations</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MIGRATION_DEFINITION__MIGRATIONS = 0;

  /**
   * The number of structural features of the '<em>Migration Definition</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MIGRATION_DEFINITION_FEATURE_COUNT = 1;


  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.UriMigration.Migration <em>Migration</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Migration</em>'.
   * @see com.thalesgroup.orchestra.framework.UriMigration.Migration
   * @generated
   */
  EClass getMigration();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.UriMigration.Migration#getToolName <em>Tool Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Tool Name</em>'.
   * @see com.thalesgroup.orchestra.framework.UriMigration.Migration#getToolName()
   * @see #getMigration()
   * @generated
   */
  EAttribute getMigration_ToolName();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.UriMigration.Migration#getRootType <em>Root Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Root Type</em>'.
   * @see com.thalesgroup.orchestra.framework.UriMigration.Migration#getRootType()
   * @see #getMigration()
   * @generated
   */
  EAttribute getMigration_RootType();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.UriMigration.Migration#isInvokeConnector <em>Invoke Connector</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Invoke Connector</em>'.
   * @see com.thalesgroup.orchestra.framework.UriMigration.Migration#isInvokeConnector()
   * @see #getMigration()
   * @generated
   */
  EAttribute getMigration_InvokeConnector();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.UriMigration.MigrationDefinition <em>Migration Definition</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Migration Definition</em>'.
   * @see com.thalesgroup.orchestra.framework.UriMigration.MigrationDefinition
   * @generated
   */
  EClass getMigrationDefinition();

  /**
   * Returns the meta object for the containment reference list '{@link com.thalesgroup.orchestra.framework.UriMigration.MigrationDefinition#getMigrations <em>Migrations</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Migrations</em>'.
   * @see com.thalesgroup.orchestra.framework.UriMigration.MigrationDefinition#getMigrations()
   * @see #getMigrationDefinition()
   * @generated
   */
  EReference getMigrationDefinition_Migrations();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  UriMigrationFactory getUriMigrationFactory();

  /**
   * <!-- begin-user-doc -->
   * Defines literals for the meta objects that represent
   * <ul>
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * @generated
   */
  interface Literals {
    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.UriMigration.impl.MigrationImpl <em>Migration</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.UriMigration.impl.MigrationImpl
     * @see com.thalesgroup.orchestra.framework.UriMigration.impl.UriMigrationPackageImpl#getMigration()
     * @generated
     */
    EClass MIGRATION = eINSTANCE.getMigration();

    /**
     * The meta object literal for the '<em><b>Tool Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MIGRATION__TOOL_NAME = eINSTANCE.getMigration_ToolName();

    /**
     * The meta object literal for the '<em><b>Root Type</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MIGRATION__ROOT_TYPE = eINSTANCE.getMigration_RootType();

    /**
     * The meta object literal for the '<em><b>Invoke Connector</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MIGRATION__INVOKE_CONNECTOR = eINSTANCE.getMigration_InvokeConnector();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.UriMigration.impl.MigrationDefinitionImpl <em>Migration Definition</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.UriMigration.impl.MigrationDefinitionImpl
     * @see com.thalesgroup.orchestra.framework.UriMigration.impl.UriMigrationPackageImpl#getMigrationDefinition()
     * @generated
     */
    EClass MIGRATION_DEFINITION = eINSTANCE.getMigrationDefinition();

    /**
     * The meta object literal for the '<em><b>Migrations</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MIGRATION_DEFINITION__MIGRATIONS = eINSTANCE.getMigrationDefinition_Migrations();

  }

} //UriMigrationPackage
