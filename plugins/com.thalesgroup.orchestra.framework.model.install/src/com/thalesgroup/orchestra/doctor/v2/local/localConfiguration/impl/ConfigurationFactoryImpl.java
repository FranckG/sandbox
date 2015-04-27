/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.impl;

import com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.*;

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
public class ConfigurationFactoryImpl extends EFactoryImpl implements ConfigurationFactory {
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static ConfigurationFactory init() {
    try {
      ConfigurationFactory theConfigurationFactory = (ConfigurationFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.thalesgroup.com/orchestraDoctor/v2/localConfiguration"); 
      if (theConfigurationFactory != null) {
        return theConfigurationFactory;
      }
    }
    catch (Exception exception) {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new ConfigurationFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ConfigurationFactoryImpl() {
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
      case ConfigurationPackage.COMPATIBILITY_TYPE: return createCompatibilityType();
      case ConfigurationPackage.DOCUMENT_ROOT: return createDocumentRoot();
      case ConfigurationPackage.ORCHESTRA_DOCTOR_LOCAL_CONFIGURATION_TYPE: return createOrchestraDoctorLocalConfigurationType();
      case ConfigurationPackage.PARAMETER_TYPE: return createParameterType();
      case ConfigurationPackage.PRODUCT_TYPE: return createProductType();
      case ConfigurationPackage.VERSION_RANGE_TYPE: return createVersionRangeType();
      case ConfigurationPackage.VERSION_TYPE: return createVersionType();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CompatibilityType createCompatibilityType() {
    CompatibilityTypeImpl compatibilityType = new CompatibilityTypeImpl();
    return compatibilityType;
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
  public OrchestraDoctorLocalConfigurationType createOrchestraDoctorLocalConfigurationType() {
    OrchestraDoctorLocalConfigurationTypeImpl orchestraDoctorLocalConfigurationType = new OrchestraDoctorLocalConfigurationTypeImpl();
    return orchestraDoctorLocalConfigurationType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ParameterType createParameterType() {
    ParameterTypeImpl parameterType = new ParameterTypeImpl();
    return parameterType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ProductType createProductType() {
    ProductTypeImpl productType = new ProductTypeImpl();
    return productType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public VersionRangeType createVersionRangeType() {
    VersionRangeTypeImpl versionRangeType = new VersionRangeTypeImpl();
    return versionRangeType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public VersionType createVersionType() {
    VersionTypeImpl versionType = new VersionTypeImpl();
    return versionType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ConfigurationPackage getConfigurationPackage() {
    return (ConfigurationPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static ConfigurationPackage getPackage() {
    return ConfigurationPackage.eINSTANCE;
  }

} //ConfigurationFactoryImpl
