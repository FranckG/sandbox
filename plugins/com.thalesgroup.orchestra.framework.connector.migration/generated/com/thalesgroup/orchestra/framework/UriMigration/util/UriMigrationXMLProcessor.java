/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.UriMigration.util;

import com.thalesgroup.orchestra.framework.UriMigration.UriMigrationPackage;

import java.util.Map;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.emf.ecore.xmi.util.XMLProcessor;

/**
 * This class contains helper methods to serialize and deserialize XML documents
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class UriMigrationXMLProcessor extends XMLProcessor {

  /**
   * Public constructor to instantiate the helper.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public UriMigrationXMLProcessor() {
    super((EPackage.Registry.INSTANCE));
    UriMigrationPackage.eINSTANCE.eClass();
  }
  
  /**
   * Register for "*" and "xml" file extensions the UriMigrationResourceFactoryImpl factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected Map<String, Resource.Factory> getRegistrations() {
    if (registrations == null) {
      super.getRegistrations();
      registrations.put(XML_EXTENSION, new UriMigrationResourceFactoryImpl());
      registrations.put(STAR_EXTENSION, new UriMigrationResourceFactoryImpl());
    }
    return registrations;
  }

} //UriMigrationXMLProcessor
