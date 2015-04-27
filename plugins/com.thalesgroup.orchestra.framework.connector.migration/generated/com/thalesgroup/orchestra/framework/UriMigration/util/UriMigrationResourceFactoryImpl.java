/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.UriMigration.util;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;

/**
 * <!-- begin-user-doc --> The <b>Resource Factory</b> associated with the package. <!-- end-user-doc -->
 * @see com.thalesgroup.orchestra.framework.UriMigration.util.UriMigrationResourceImpl
 * @generated
 */
public class UriMigrationResourceFactoryImpl extends ResourceFactoryImpl {
  /**
   * Creates an instance of the resource factory.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public UriMigrationResourceFactoryImpl() {
    super();
  }

  /**
   * Creates an instance of the resource. <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public Resource createResource(URI uri) {
    UriMigrationResourceImpl result = new UriMigrationResourceImpl(uri);
    // Force UTF-8 encoding.
    result.getDefaultSaveOptions().put(XMLResource.OPTION_ENCODING, "UTF-8"); //$NON-NLS-1$
    return result;
  }
} // UriMigrationResourceFactoryImpl