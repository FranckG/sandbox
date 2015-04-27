/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.UriMigration.impl;

import com.thalesgroup.orchestra.framework.UriMigration.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class UriMigrationFactoryImpl extends EFactoryImpl implements UriMigrationFactory {
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static UriMigrationFactory init() {
    try {
      UriMigrationFactory theUriMigrationFactory = (UriMigrationFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.thalesgroup.com/orchestra/framework/4_0_27/UriMigration"); 
      if (theUriMigrationFactory != null) {
        return theUriMigrationFactory;
      }
    }
    catch (Exception exception) {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new UriMigrationFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public UriMigrationFactoryImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EObject create(EClass eClass) {
    switch (eClass.getClassifierID()) {
      case UriMigrationPackage.MIGRATION: return createMigration();
      case UriMigrationPackage.MIGRATION_DEFINITION: return createMigrationDefinition();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Migration createMigration() {
    MigrationImpl migration = new MigrationImpl();
    return migration;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MigrationDefinition createMigrationDefinition() {
    MigrationDefinitionImpl migrationDefinition = new MigrationDefinitionImpl();
    return migrationDefinition;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public UriMigrationPackage getUriMigrationPackage() {
    return (UriMigrationPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static UriMigrationPackage getPackage() {
    return UriMigrationPackage.eINSTANCE;
  }

} //UriMigrationFactoryImpl
