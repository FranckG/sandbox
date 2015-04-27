/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.exchange.status.impl;

import com.thalesgroup.orchestra.framework.exchange.status.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
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
public class StatusFactoryImpl extends EFactoryImpl implements StatusFactory {
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static StatusFactory init() {
    try {
      StatusFactory theStatusFactory = (StatusFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.thalesgroup.com/orchestra/framework/4_0_25/Status"); 
      if (theStatusFactory != null) {
        return theStatusFactory;
      }
    }
    catch (Exception exception) {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new StatusFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public StatusFactoryImpl() {
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
      case StatusPackage.DOCUMENT_ROOT: return createDocumentRoot();
      case StatusPackage.STATUS: return createStatus();
      case StatusPackage.STATUS_DEFINITION: return createStatusDefinition();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object createFromString(EDataType eDataType, String initialValue) {
    switch (eDataType.getClassifierID()) {
      case StatusPackage.SEVERITY_TYPE:
        return createSeverityTypeFromString(eDataType, initialValue);
      case StatusPackage.SEVERITY_TYPE_OBJECT:
        return createSeverityTypeObjectFromString(eDataType, initialValue);
      default:
        throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String convertToString(EDataType eDataType, Object instanceValue) {
    switch (eDataType.getClassifierID()) {
      case StatusPackage.SEVERITY_TYPE:
        return convertSeverityTypeToString(eDataType, instanceValue);
      case StatusPackage.SEVERITY_TYPE_OBJECT:
        return convertSeverityTypeObjectToString(eDataType, instanceValue);
      default:
        throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
    }
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
  public Status createStatus() {
    StatusImpl status = new StatusImpl();
    return status;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public StatusDefinition createStatusDefinition() {
    StatusDefinitionImpl statusDefinition = new StatusDefinitionImpl();
    return statusDefinition;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public SeverityType createSeverityTypeFromString(EDataType eDataType, String initialValue) {
    SeverityType result = SeverityType.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertSeverityTypeToString(EDataType eDataType, Object instanceValue) {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public SeverityType createSeverityTypeObjectFromString(EDataType eDataType, String initialValue) {
    return createSeverityTypeFromString(StatusPackage.Literals.SEVERITY_TYPE, initialValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertSeverityTypeObjectToString(EDataType eDataType, Object instanceValue) {
    return convertSeverityTypeToString(StatusPackage.Literals.SEVERITY_TYPE, instanceValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public StatusPackage getStatusPackage() {
    return (StatusPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static StatusPackage getPackage() {
    return StatusPackage.eINSTANCE;
  }

} //StatusFactoryImpl
