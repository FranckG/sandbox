/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.util;

import com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ConfigurationPackage
 * @generated
 */
public class ConfigurationSwitch<T> extends Switch<T> {
  /**
   * The cached model package
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static ConfigurationPackage modelPackage;

  /**
   * Creates an instance of the switch.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ConfigurationSwitch() {
    if (modelPackage == null) {
      modelPackage = ConfigurationPackage.eINSTANCE;
    }
  }

  /**
   * Checks whether this is a switch for the given package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @parameter ePackage the package in question.
   * @return whether this is a switch for the given package.
   * @generated
   */
  @Override
  protected boolean isSwitchFor(EPackage ePackage) {
    return ePackage == modelPackage;
  }

  /**
   * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the first non-null result returned by a <code>caseXXX</code> call.
   * @generated
   */
  @Override
  protected T doSwitch(int classifierID, EObject theEObject) {
    switch (classifierID) {
      case ConfigurationPackage.COMPATIBILITY_TYPE: {
        CompatibilityType compatibilityType = (CompatibilityType)theEObject;
        T result = caseCompatibilityType(compatibilityType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ConfigurationPackage.DOCUMENT_ROOT: {
        DocumentRoot documentRoot = (DocumentRoot)theEObject;
        T result = caseDocumentRoot(documentRoot);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ConfigurationPackage.ORCHESTRA_DOCTOR_LOCAL_CONFIGURATION_TYPE: {
        OrchestraDoctorLocalConfigurationType orchestraDoctorLocalConfigurationType = (OrchestraDoctorLocalConfigurationType)theEObject;
        T result = caseOrchestraDoctorLocalConfigurationType(orchestraDoctorLocalConfigurationType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ConfigurationPackage.PARAMETER_TYPE: {
        ParameterType parameterType = (ParameterType)theEObject;
        T result = caseParameterType(parameterType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ConfigurationPackage.PRODUCT_TYPE: {
        ProductType productType = (ProductType)theEObject;
        T result = caseProductType(productType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ConfigurationPackage.VERSION_RANGE_TYPE: {
        VersionRangeType versionRangeType = (VersionRangeType)theEObject;
        T result = caseVersionRangeType(versionRangeType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ConfigurationPackage.VERSION_TYPE: {
        VersionType versionType = (VersionType)theEObject;
        T result = caseVersionType(versionType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      default: return defaultCase(theEObject);
    }
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Compatibility Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Compatibility Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseCompatibilityType(CompatibilityType object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Document Root</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Document Root</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseDocumentRoot(DocumentRoot object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Orchestra Doctor Local Configuration Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Orchestra Doctor Local Configuration Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseOrchestraDoctorLocalConfigurationType(OrchestraDoctorLocalConfigurationType object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Parameter Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Parameter Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseParameterType(ParameterType object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Product Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Product Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseProductType(ProductType object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Version Range Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Version Range Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseVersionRangeType(VersionRangeType object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Version Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Version Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseVersionType(VersionType object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch, but this is the last case anyway.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject)
   * @generated
   */
  @Override
  public T defaultCase(EObject object) {
    return null;
  }

} //ConfigurationSwitch
