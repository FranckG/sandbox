/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.util;

import com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ConfigurationPackage
 * @generated
 */
public class ConfigurationAdapterFactory extends AdapterFactoryImpl {
  /**
   * The cached model package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static ConfigurationPackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ConfigurationAdapterFactory() {
    if (modelPackage == null) {
      modelPackage = ConfigurationPackage.eINSTANCE;
    }
  }

  /**
   * Returns whether this factory is applicable for the type of the object.
   * <!-- begin-user-doc -->
   * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
   * <!-- end-user-doc -->
   * @return whether this factory is applicable for the type of the object.
   * @generated
   */
  @Override
  public boolean isFactoryForType(Object object) {
    if (object == modelPackage) {
      return true;
    }
    if (object instanceof EObject) {
      return ((EObject)object).eClass().getEPackage() == modelPackage;
    }
    return false;
  }

  /**
   * The switch that delegates to the <code>createXXX</code> methods.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ConfigurationSwitch<Adapter> modelSwitch =
    new ConfigurationSwitch<Adapter>() {
      @Override
      public Adapter caseCompatibilityType(CompatibilityType object) {
        return createCompatibilityTypeAdapter();
      }
      @Override
      public Adapter caseDocumentRoot(DocumentRoot object) {
        return createDocumentRootAdapter();
      }
      @Override
      public Adapter caseOrchestraDoctorLocalConfigurationType(OrchestraDoctorLocalConfigurationType object) {
        return createOrchestraDoctorLocalConfigurationTypeAdapter();
      }
      @Override
      public Adapter caseParameterType(ParameterType object) {
        return createParameterTypeAdapter();
      }
      @Override
      public Adapter caseProductType(ProductType object) {
        return createProductTypeAdapter();
      }
      @Override
      public Adapter caseVersionRangeType(VersionRangeType object) {
        return createVersionRangeTypeAdapter();
      }
      @Override
      public Adapter caseVersionType(VersionType object) {
        return createVersionTypeAdapter();
      }
      @Override
      public Adapter defaultCase(EObject object) {
        return createEObjectAdapter();
      }
    };

  /**
   * Creates an adapter for the <code>target</code>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param target the object to adapt.
   * @return the adapter for the <code>target</code>.
   * @generated
   */
  @Override
  public Adapter createAdapter(Notifier target) {
    return modelSwitch.doSwitch((EObject)target);
  }


  /**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.CompatibilityType <em>Compatibility Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.CompatibilityType
   * @generated
   */
  public Adapter createCompatibilityTypeAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.DocumentRoot <em>Document Root</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.DocumentRoot
   * @generated
   */
  public Adapter createDocumentRootAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.OrchestraDoctorLocalConfigurationType <em>Orchestra Doctor Local Configuration Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.OrchestraDoctorLocalConfigurationType
   * @generated
   */
  public Adapter createOrchestraDoctorLocalConfigurationTypeAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ParameterType <em>Parameter Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ParameterType
   * @generated
   */
  public Adapter createParameterTypeAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ProductType <em>Product Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ProductType
   * @generated
   */
  public Adapter createProductTypeAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionRangeType <em>Version Range Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionRangeType
   * @generated
   */
  public Adapter createVersionRangeTypeAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionType <em>Version Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.VersionType
   * @generated
   */
  public Adapter createVersionTypeAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for the default case.
   * <!-- begin-user-doc -->
   * This default implementation returns null.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @generated
   */
  public Adapter createEObjectAdapter() {
    return null;
  }

} //ConfigurationAdapterFactory
