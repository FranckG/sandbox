/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.models.connector.impl;

import com.thalesgroup.orchestra.framework.models.connector.*;

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
public class ConnectorFactoryImpl extends EFactoryImpl implements ConnectorFactory {
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static ConnectorFactory init() {
    try {
      ConnectorFactory theConnectorFactory = (ConnectorFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.thalesgroup.com/orchestra/framework/4_0_24/Connector"); 
      if (theConnectorFactory != null) {
        return theConnectorFactory;
      }
    }
    catch (Exception exception) {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new ConnectorFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ConnectorFactoryImpl() {
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
      case ConnectorPackage.CONNECTOR: return createConnector();
      case ConnectorPackage.CONNECTOR_DEFINITION: return createConnectorDefinition();
      case ConnectorPackage.DOCUMENT_ROOT: return createDocumentRoot();
      case ConnectorPackage.SERVICE: return createService();
      case ConnectorPackage.TYPE: return createType();
      case ConnectorPackage.KEEP_OPEN: return createKeepOpen();
      case ConnectorPackage.LAUNCH_ARGUMENTS: return createLaunchArguments();
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
      case ConnectorPackage.SERVICE_NAME_TYPE:
        return createServiceNameTypeFromString(eDataType, initialValue);
      case ConnectorPackage.SERVICE_NAME_TYPE_OBJECT:
        return createServiceNameTypeObjectFromString(eDataType, initialValue);
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
      case ConnectorPackage.SERVICE_NAME_TYPE:
        return convertServiceNameTypeToString(eDataType, instanceValue);
      case ConnectorPackage.SERVICE_NAME_TYPE_OBJECT:
        return convertServiceNameTypeObjectToString(eDataType, instanceValue);
      default:
        throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Connector createConnector() {
    ConnectorImpl connector = new ConnectorImpl();
    return connector;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ConnectorDefinition createConnectorDefinition() {
    ConnectorDefinitionImpl connectorDefinition = new ConnectorDefinitionImpl();
    return connectorDefinition;
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
  public Service createService() {
    ServiceImpl service = new ServiceImpl();
    return service;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Type createType() {
    TypeImpl type = new TypeImpl();
    return type;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public KeepOpen createKeepOpen() {
    KeepOpenImpl keepOpen = new KeepOpenImpl();
    return keepOpen;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LaunchArguments createLaunchArguments() {
    LaunchArgumentsImpl launchArguments = new LaunchArgumentsImpl();
    return launchArguments;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ServiceNameType createServiceNameTypeFromString(EDataType eDataType, String initialValue) {
    ServiceNameType result = ServiceNameType.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertServiceNameTypeToString(EDataType eDataType, Object instanceValue) {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ServiceNameType createServiceNameTypeObjectFromString(EDataType eDataType, String initialValue) {
    return createServiceNameTypeFromString(ConnectorPackage.Literals.SERVICE_NAME_TYPE, initialValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertServiceNameTypeObjectToString(EDataType eDataType, Object instanceValue) {
    return convertServiceNameTypeToString(ConnectorPackage.Literals.SERVICE_NAME_TYPE, instanceValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ConnectorPackage getConnectorPackage() {
    return (ConnectorPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static ConnectorPackage getPackage() {
    return ConnectorPackage.eINSTANCE;
  }

} //ConnectorFactoryImpl
