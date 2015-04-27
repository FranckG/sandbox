/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.models.context.impl;

import com.thalesgroup.orchestra.framework.models.context.*;

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
public class ContextFactoryImpl extends EFactoryImpl implements ContextFactory {
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static ContextFactory init() {
    try {
      ContextFactory theContextFactory = (ContextFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.thalesgroup.com/orchestra/framework/4_1_2/Context"); 
      if (theContextFactory != null) {
        return theContextFactory;
      }
    }
    catch (Exception exception) {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new ContextFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ContextFactoryImpl() {
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
      case ContextPackage.ARTIFACT: return createArtifact();
      case ContextPackage.CONTEXT: return createContext();
      case ContextPackage.CONTEXT_DEFINITION: return createContextDefinition();
      case ContextPackage.DOCUMENT_ROOT: return createDocumentRoot();
      case ContextPackage.ENVIRONMENT_PROPERTY: return createEnvironmentProperty();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Artifact createArtifact() {
    ArtifactImpl artifact = new ArtifactImpl();
    return artifact;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Context createContext() {
    ContextImpl context = new ContextImpl();
    return context;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ContextDefinition createContextDefinition() {
    ContextDefinitionImpl contextDefinition = new ContextDefinitionImpl();
    return contextDefinition;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DocumentRoot createDocumentRoot() {
    DocumentRootImpl documentRoot = new DocumentRootImpl();
    return documentRoot;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EnvironmentProperty createEnvironmentProperty() {
    EnvironmentPropertyImpl environmentProperty = new EnvironmentPropertyImpl();
    return environmentProperty;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ContextPackage getContextPackage() {
    return (ContextPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static ContextPackage getPackage() {
    return ContextPackage.eINSTANCE;
  }

} //ContextFactoryImpl
