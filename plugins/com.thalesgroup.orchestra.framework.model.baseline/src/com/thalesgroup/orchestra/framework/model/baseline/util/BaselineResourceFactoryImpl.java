/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.model.baseline.util;

import org.eclipse.emf.common.util.URI;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;

/**
 * <!-- begin-user-doc -->
 * The <b>Resource Factory</b> associated with the package.
 * <!-- end-user-doc -->
 * @see com.thalesgroup.orchestra.framework.model.baseline.util.BaselineResourceImpl
 * @generated
 */
public class BaselineResourceFactoryImpl extends ResourceFactoryImpl {
  /**
   * Creates an instance of the resource factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BaselineResourceFactoryImpl() {
    super();
  }

  /**
   * Creates an instance of the resource.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Resource createResource(URI uri) {
    Resource result = new BaselineResourceImpl(uri);
    return result;
  }

} //BaselineResourceFactoryImpl
