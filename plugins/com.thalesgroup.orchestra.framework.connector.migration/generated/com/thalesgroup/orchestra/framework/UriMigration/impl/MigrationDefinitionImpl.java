/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.UriMigration.impl;

import com.thalesgroup.orchestra.framework.UriMigration.Migration;
import com.thalesgroup.orchestra.framework.UriMigration.MigrationDefinition;
import com.thalesgroup.orchestra.framework.UriMigration.UriMigrationPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Migration Definition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.UriMigration.impl.MigrationDefinitionImpl#getMigrations <em>Migrations</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MigrationDefinitionImpl extends EObjectImpl implements MigrationDefinition {
  /**
   * The cached value of the '{@link #getMigrations() <em>Migrations</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMigrations()
   * @generated
   * @ordered
   */
  protected EList<Migration> migrations;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected MigrationDefinitionImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return UriMigrationPackage.Literals.MIGRATION_DEFINITION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Migration> getMigrations() {
    if (migrations == null) {
      migrations = new EObjectContainmentEList<Migration>(Migration.class, this, UriMigrationPackage.MIGRATION_DEFINITION__MIGRATIONS);
    }
    return migrations;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case UriMigrationPackage.MIGRATION_DEFINITION__MIGRATIONS:
        return ((InternalEList<?>)getMigrations()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case UriMigrationPackage.MIGRATION_DEFINITION__MIGRATIONS:
        return getMigrations();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue) {
    switch (featureID) {
      case UriMigrationPackage.MIGRATION_DEFINITION__MIGRATIONS:
        getMigrations().clear();
        getMigrations().addAll((Collection<? extends Migration>)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID) {
    switch (featureID) {
      case UriMigrationPackage.MIGRATION_DEFINITION__MIGRATIONS:
        getMigrations().clear();
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID) {
    switch (featureID) {
      case UriMigrationPackage.MIGRATION_DEFINITION__MIGRATIONS:
        return migrations != null && !migrations.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //MigrationDefinitionImpl
