/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.model.contexts.impl;

import com.thalesgroup.orchestra.framework.model.contexts.*;
import java.util.Map;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.thalesgroup.orchestra.framework.model.contexts.Category;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsFactory;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.contexts.FileVariable;
import com.thalesgroup.orchestra.framework.model.contexts.FolderVariable;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ContextsFactoryImpl extends EFactoryImpl implements ContextsFactory {
	/**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public static ContextsFactory init() {
    try {
      ContextsFactory theContextsFactory = (ContextsFactory)EPackage.Registry.INSTANCE.getEFactory("http://com.thalesgroup.orchestra/framework/4_1_0/contexts"); 
      if (theContextsFactory != null) {
        return theContextsFactory;
      }
    }
    catch (Exception exception) {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new ContextsFactoryImpl();
  }

	/**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public ContextsFactoryImpl() {
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
      case ContextsPackage.CATEGORY: return createCategory();
      case ContextsPackage.CONTEXT: return createContext();
      case ContextsPackage.VARIABLE: return createVariable();
      case ContextsPackage.FILE_VARIABLE: return createFileVariable();
      case ContextsPackage.FOLDER_VARIABLE: return createFolderVariable();
      case ContextsPackage.OVERRIDING_VARIABLE: return createOverridingVariable();
      case ContextsPackage.VARIABLE_VALUE: return createVariableValue();
      case ContextsPackage.PENDING_ELEMENTS_CATEGORY: return createPendingElementsCategory();
      case ContextsPackage.OVERRIDING_VARIABLE_VALUE: return createOverridingVariableValue();
      case ContextsPackage.OVERRIDING_ENVIRONMENT_VARIABLE_VALUE: return createOverridingEnvironmentVariableValue();
      case ContextsPackage.INSTALLATION_CATEGORY: return createInstallationCategory();
      case ContextsPackage.CONTRIBUTED_ELEMENT: return createContributedElement();
      case ContextsPackage.REFERENCEABLE_ELEMENT: return createReferenceableElement();
      case ContextsPackage.REFERENCING_ELEMENT: return createReferencingElement();
      case ContextsPackage.ENVIRONMENT_VARIABLE_VALUE: return createEnvironmentVariableValue();
      case ContextsPackage.ENVIRONMENT_VARIABLE: return createEnvironmentVariable();
      case ContextsPackage.STRING_TO_STRING_MAP: return (EObject)createStringToStringMap();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public Category createCategory() {
    CategoryImpl category = new CategoryImpl();
    return category;
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
	public Variable createVariable() {
    VariableImpl variable = new VariableImpl();
    return variable;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public FileVariable createFileVariable() {
    FileVariableImpl fileVariable = new FileVariableImpl();
    return fileVariable;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public FolderVariable createFolderVariable() {
    FolderVariableImpl folderVariable = new FolderVariableImpl();
    return folderVariable;
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public OverridingVariable createOverridingVariable() {
    OverridingVariableImpl overridingVariable = new OverridingVariableImpl();
    return overridingVariable;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public VariableValue createVariableValue() {
    VariableValueImpl variableValue = new VariableValueImpl();
    return variableValue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public OverridingVariableValue createOverridingVariableValue() {
    OverridingVariableValueImpl overridingVariableValue = new OverridingVariableValueImpl();
    return overridingVariableValue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public OverridingEnvironmentVariableValue createOverridingEnvironmentVariableValue() {
    OverridingEnvironmentVariableValueImpl overridingEnvironmentVariableValue = new OverridingEnvironmentVariableValueImpl();
    return overridingEnvironmentVariableValue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public InstallationCategory createInstallationCategory() {
    InstallationCategoryImpl installationCategory = new InstallationCategoryImpl();
    return installationCategory;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public PendingElementsCategory createPendingElementsCategory() {
    PendingElementsCategoryImpl pendingElementsCategory = new PendingElementsCategoryImpl();
    return pendingElementsCategory;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ContributedElement createContributedElement() {
    ContributedElementImpl contributedElement = new ContributedElementImpl();
    return contributedElement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ReferenceableElement createReferenceableElement() {
    ReferenceableElementImpl referenceableElement = new ReferenceableElementImpl();
    return referenceableElement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ReferencingElement createReferencingElement() {
    ReferencingElementImpl referencingElement = new ReferencingElementImpl();
    return referencingElement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EnvironmentVariableValue createEnvironmentVariableValue() {
    EnvironmentVariableValueImpl environmentVariableValue = new EnvironmentVariableValueImpl();
    return environmentVariableValue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EnvironmentVariable createEnvironmentVariable() {
    EnvironmentVariableImpl environmentVariable = new EnvironmentVariableImpl();
    return environmentVariable;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Map.Entry<String, String> createStringToStringMap() {
    StringToStringMapImpl stringToStringMap = new StringToStringMapImpl();
    return stringToStringMap;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public ContextsPackage getContextsPackage() {
    return (ContextsPackage)getEPackage();
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
	@Deprecated
	public static ContextsPackage getPackage() {
    return ContextsPackage.eINSTANCE;
  }

} //ContextsFactoryImpl
