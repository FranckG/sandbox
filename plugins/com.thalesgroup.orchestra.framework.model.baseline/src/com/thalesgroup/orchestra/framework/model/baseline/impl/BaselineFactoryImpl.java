/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.model.baseline.impl;

import com.thalesgroup.orchestra.framework.model.baseline.*;

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
public class BaselineFactoryImpl extends EFactoryImpl implements BaselineFactory {
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static BaselineFactory init() {
    try {
      BaselineFactory theBaselineFactory = (BaselineFactory)EPackage.Registry.INSTANCE.getEFactory("http://com.thalesgroup.orchestra/framework/4_2_2/baseline"); 
      if (theBaselineFactory != null) {
        return theBaselineFactory;
      }
    }
    catch (Exception exception) {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new BaselineFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BaselineFactoryImpl() {
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
      case BaselinePackage.REPOSITORY: return createRepository();
      case BaselinePackage.BASELINE: return createBaseline();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Repository createRepository() {
    RepositoryImpl repository = new RepositoryImpl();
    return repository;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Baseline createBaseline() {
    BaselineImpl baseline = new BaselineImpl();
    return baseline;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BaselinePackage getBaselinePackage() {
    return (BaselinePackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static BaselinePackage getPackage() {
    return BaselinePackage.eINSTANCE;
  }

} //BaselineFactoryImpl
