/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.models.connector.util;

import com.thalesgroup.orchestra.framework.models.connector.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.thalesgroup.orchestra.framework.models.connector.ConnectorPackage
 * @generated
 */
public class ConnectorAdapterFactory extends AdapterFactoryImpl {
  /**
   * The cached model package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static ConnectorPackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ConnectorAdapterFactory() {
    if (modelPackage == null) {
      modelPackage = ConnectorPackage.eINSTANCE;
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
  protected ConnectorSwitch<Adapter> modelSwitch =
    new ConnectorSwitch<Adapter>() {
      @Override
      public Adapter caseConnector(Connector object) {
        return createConnectorAdapter();
      }
      @Override
      public Adapter caseConnectorDefinition(ConnectorDefinition object) {
        return createConnectorDefinitionAdapter();
      }
      @Override
      public Adapter caseDocumentRoot(DocumentRoot object) {
        return createDocumentRootAdapter();
      }
      @Override
      public Adapter caseService(Service object) {
        return createServiceAdapter();
      }
      @Override
      public Adapter caseType(Type object) {
        return createTypeAdapter();
      }
      @Override
      public Adapter caseKeepOpen(KeepOpen object) {
        return createKeepOpenAdapter();
      }
      @Override
      public Adapter caseLaunchArguments(LaunchArguments object) {
        return createLaunchArgumentsAdapter();
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
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.framework.models.connector.Connector <em>Connector</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.framework.models.connector.Connector
   * @generated
   */
  public Adapter createConnectorAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.framework.models.connector.ConnectorDefinition <em>Definition</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.framework.models.connector.ConnectorDefinition
   * @generated
   */
  public Adapter createConnectorDefinitionAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.framework.models.connector.DocumentRoot <em>Document Root</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.framework.models.connector.DocumentRoot
   * @generated
   */
  public Adapter createDocumentRootAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.framework.models.connector.Service <em>Service</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.framework.models.connector.Service
   * @generated
   */
  public Adapter createServiceAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.framework.models.connector.Type <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.framework.models.connector.Type
   * @generated
   */
  public Adapter createTypeAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.framework.models.connector.KeepOpen <em>Keep Open</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.framework.models.connector.KeepOpen
   * @generated
   */
  public Adapter createKeepOpenAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.framework.models.connector.LaunchArguments <em>Launch Arguments</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.framework.models.connector.LaunchArguments
   * @generated
   */
  public Adapter createLaunchArgumentsAdapter() {
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

} //ConnectorAdapterFactory
