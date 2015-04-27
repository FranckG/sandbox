/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.gef;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.thalesgroup.orchestra.framework.gef.GefPackage
 * @generated
 */
public interface GefFactory extends EFactory {
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  GefFactory eINSTANCE = com.thalesgroup.orchestra.framework.gef.impl.GefFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Description</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Description</em>'.
   * @generated
   */
  Description createDescription();

  /**
   * Returns a new object of class '<em>Element</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Element</em>'.
   * @generated
   */
  Element createElement();

  /**
   * Returns a new object of class '<em>Element Reference</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Element Reference</em>'.
   * @generated
   */
  ElementReference createElementReference();

  /**
   * Returns a new object of class '<em>File Reference</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>File Reference</em>'.
   * @generated
   */
  FileReference createFileReference();

  /**
   * Returns a new object of class '<em>GEF</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>GEF</em>'.
   * @generated
   */
  GEF createGEF();

  /**
   * Returns a new object of class '<em>Generic Export Format</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Generic Export Format</em>'.
   * @generated
   */
  GenericExportFormat createGenericExportFormat();

  /**
   * Returns a new object of class '<em>Links To Artifacts</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Links To Artifacts</em>'.
   * @generated
   */
  LinksToArtifacts createLinksToArtifacts();

  /**
   * Returns a new object of class '<em>Links To Elements</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Links To Elements</em>'.
   * @generated
   */
  LinksToElements createLinksToElements();

  /**
   * Returns a new object of class '<em>Mproperty</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Mproperty</em>'.
   * @generated
   */
  Mproperty createMproperty();

  /**
   * Returns a new object of class '<em>Mproperty Value</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Mproperty Value</em>'.
   * @generated
   */
  MpropertyValue createMpropertyValue();

  /**
   * Returns a new object of class '<em>Properties</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Properties</em>'.
   * @generated
   */
  Properties createProperties();

  /**
   * Returns a new object of class '<em>Property</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Property</em>'.
   * @generated
   */
  Property createProperty();

  /**
   * Returns a new object of class '<em>Reference</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Reference</em>'.
   * @generated
   */
  Reference createReference();

  /**
   * Returns a new object of class '<em>Textual Description</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Textual Description</em>'.
   * @generated
   */
  TextualDescription createTextualDescription();

  /**
   * Returns a new object of class '<em>Version</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Version</em>'.
   * @generated
   */
  Version createVersion();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  GefPackage getGefPackage();

} //GefFactory
