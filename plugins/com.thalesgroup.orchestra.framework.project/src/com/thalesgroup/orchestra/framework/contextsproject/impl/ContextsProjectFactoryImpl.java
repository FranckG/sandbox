/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.contextsproject.impl;

import com.thalesgroup.orchestra.framework.contextsproject.*;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.thalesgroup.orchestra.framework.contextsproject.Administrator;
import com.thalesgroup.orchestra.framework.contextsproject.ContextReference;
import com.thalesgroup.orchestra.framework.contextsproject.ContextsProject;
import com.thalesgroup.orchestra.framework.contextsproject.ContextsProjectFactory;
import com.thalesgroup.orchestra.framework.contextsproject.ContextsProjectPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ContextsProjectFactoryImpl extends EFactoryImpl implements ContextsProjectFactory {
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static ContextsProjectFactory init() {
    try {
      ContextsProjectFactory theContextsProjectFactory = (ContextsProjectFactory)EPackage.Registry.INSTANCE.getEFactory("http://com.thalesgroup.orchestra/framework/4_0_13/contextsproject"); 
      if (theContextsProjectFactory != null) {
        return theContextsProjectFactory;
      }
    }
    catch (Exception exception) {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new ContextsProjectFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ContextsProjectFactoryImpl() {
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
      case ContextsProjectPackage.CONTEXTS_PROJECT: return createContextsProject();
      case ContextsProjectPackage.ADMINISTRATOR: return createAdministrator();
      case ContextsProjectPackage.CONTEXT_REFERENCE: return createContextReference();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ContextsProject createContextsProject() {
    ContextsProjectImpl contextsProject = new ContextsProjectImpl();
    return contextsProject;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Administrator createAdministrator() {
    AdministratorImpl administrator = new AdministratorImpl();
    return administrator;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ContextReference createContextReference() {
    ContextReferenceImpl contextReference = new ContextReferenceImpl();
    return contextReference;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ContextsProjectPackage getContextsProjectPackage() {
    return (ContextsProjectPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static ContextsProjectPackage getPackage() {
    return ContextsProjectPackage.eINSTANCE;
  }

} //ContextsProjectFactoryImpl
