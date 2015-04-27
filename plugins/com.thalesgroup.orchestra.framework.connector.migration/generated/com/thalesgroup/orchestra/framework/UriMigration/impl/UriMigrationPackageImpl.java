/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.UriMigration.impl;

import com.thalesgroup.orchestra.framework.UriMigration.Migration;
import com.thalesgroup.orchestra.framework.UriMigration.MigrationDefinition;
import com.thalesgroup.orchestra.framework.UriMigration.UriMigrationFactory;
import com.thalesgroup.orchestra.framework.UriMigration.UriMigrationPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class UriMigrationPackageImpl extends EPackageImpl implements UriMigrationPackage {
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass migrationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass migrationDefinitionEClass = null;

  /**
   * Creates an instance of the model <b>Package</b>, registered with
   * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
   * package URI value.
   * <p>Note: the correct way to create the package is via the static
   * factory method {@link #init init()}, which also performs
   * initialization of the package, or returns the registered package,
   * if one already exists.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.ecore.EPackage.Registry
   * @see com.thalesgroup.orchestra.framework.UriMigration.UriMigrationPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private UriMigrationPackageImpl() {
    super(eNS_URI, UriMigrationFactory.eINSTANCE);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static boolean isInited = false;

  /**
   * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
   * 
   * <p>This method is used to initialize {@link UriMigrationPackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static UriMigrationPackage init() {
    if (isInited) return (UriMigrationPackage)EPackage.Registry.INSTANCE.getEPackage(UriMigrationPackage.eNS_URI);

    // Obtain or create and register package
    UriMigrationPackageImpl theUriMigrationPackage = (UriMigrationPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof UriMigrationPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new UriMigrationPackageImpl());

    isInited = true;

    // Create package meta-data objects
    theUriMigrationPackage.createPackageContents();

    // Initialize created meta-data
    theUriMigrationPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theUriMigrationPackage.freeze();

  
    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(UriMigrationPackage.eNS_URI, theUriMigrationPackage);
    return theUriMigrationPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getMigration() {
    return migrationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getMigration_ToolName() {
    return (EAttribute)migrationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getMigration_RootType() {
    return (EAttribute)migrationEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getMigration_InvokeConnector() {
    return (EAttribute)migrationEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getMigrationDefinition() {
    return migrationDefinitionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getMigrationDefinition_Migrations() {
    return (EReference)migrationDefinitionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public UriMigrationFactory getUriMigrationFactory() {
    return (UriMigrationFactory)getEFactoryInstance();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isCreated = false;

  /**
   * Creates the meta-model objects for the package.  This method is
   * guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void createPackageContents() {
    if (isCreated) return;
    isCreated = true;

    // Create classes and their features
    migrationEClass = createEClass(MIGRATION);
    createEAttribute(migrationEClass, MIGRATION__TOOL_NAME);
    createEAttribute(migrationEClass, MIGRATION__ROOT_TYPE);
    createEAttribute(migrationEClass, MIGRATION__INVOKE_CONNECTOR);

    migrationDefinitionEClass = createEClass(MIGRATION_DEFINITION);
    createEReference(migrationDefinitionEClass, MIGRATION_DEFINITION__MIGRATIONS);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isInitialized = false;

  /**
   * Complete the initialization of the package and its meta-model.  This
   * method is guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void initializePackageContents() {
    if (isInitialized) return;
    isInitialized = true;

    // Initialize package
    setName(eNAME);
    setNsPrefix(eNS_PREFIX);
    setNsURI(eNS_URI);

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes

    // Initialize classes and features; add operations and parameters
    initEClass(migrationEClass, Migration.class, "Migration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getMigration_ToolName(), ecorePackage.getEString(), "toolName", null, 0, 1, Migration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMigration_RootType(), ecorePackage.getEString(), "rootType", null, 0, 1, Migration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMigration_InvokeConnector(), ecorePackage.getEBoolean(), "invokeConnector", null, 0, 1, Migration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(migrationDefinitionEClass, MigrationDefinition.class, "MigrationDefinition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getMigrationDefinition_Migrations(), this.getMigration(), null, "migrations", null, 1, -1, MigrationDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    // Create resource
    createResource(eNS_URI);
  }

} //UriMigrationPackageImpl
