/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.exchange.status.util;

import com.thalesgroup.orchestra.framework.exchange.status.StatusPackage;

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
public class StatusXMLProcessor extends XMLProcessor {

  /**
   * Public constructor to instantiate the helper.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public StatusXMLProcessor() {
    super((EPackage.Registry.INSTANCE));
    StatusPackage.eINSTANCE.eClass();
  }
  
  /**
   * Register for "*" and "xml" file extensions the StatusResourceFactoryImpl factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected Map<String, Resource.Factory> getRegistrations() {
    if (registrations == null) {
      super.getRegistrations();
      registrations.put(XML_EXTENSION, new StatusResourceFactoryImpl());
      registrations.put(STAR_EXTENSION, new StatusResourceFactoryImpl());
    }
    return registrations;
  }

} //StatusXMLProcessor
