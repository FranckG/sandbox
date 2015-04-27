/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.model.contexts;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.thalesgroup.orchestra.framework.model.contexts.ContextsFactory
 * @model kind="package"
 * @generated
 */
public interface ContextsPackage extends EPackage {
	/**
   * The package name.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	String eNAME = "contexts";

	/**
   * The package namespace URI.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	String eNS_URI = "http://com.thalesgroup.orchestra/framework/4_1_0/contexts";

	/**
   * The package namespace name.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	String eNS_PREFIX = "contexts";

	/**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	ContextsPackage eINSTANCE = com.thalesgroup.orchestra.framework.model.contexts.impl.ContextsPackageImpl.init();

	/**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.model.contexts.impl.ModelElementImpl <em>Model Element</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ModelElementImpl
   * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ContextsPackageImpl#getModelElement()
   * @generated
   */
  int MODEL_ELEMENT = 5;

  /**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODEL_ELEMENT__ID = 0;

  /**
   * The number of structural features of the '<em>Model Element</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODEL_ELEMENT_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.model.contexts.impl.NamedElementImpl <em>Named Element</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.model.contexts.impl.NamedElementImpl
   * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ContextsPackageImpl#getNamedElement()
   * @generated
   */
  int NAMED_ELEMENT = 6;

  /**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NAMED_ELEMENT__ID = MODEL_ELEMENT__ID;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NAMED_ELEMENT__NAME = MODEL_ELEMENT_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Named Element</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int NAMED_ELEMENT_FEATURE_COUNT = MODEL_ELEMENT_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.model.contexts.impl.CategoryImpl <em>Category</em>}' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.model.contexts.impl.CategoryImpl
   * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ContextsPackageImpl#getCategory()
   * @generated
   */
	int CATEGORY = 0;

	/**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CATEGORY__ID = NAMED_ELEMENT__ID;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int CATEGORY__NAME = NAMED_ELEMENT__NAME;

  /**
   * The feature id for the '<em><b>Super Category</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CATEGORY__SUPER_CATEGORY = NAMED_ELEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Variables</b></em>' containment reference list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int CATEGORY__VARIABLES = NAMED_ELEMENT_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Categories</b></em>' containment reference list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int CATEGORY__CATEGORIES = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
   * The number of structural features of the '<em>Category</em>' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int CATEGORY_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.model.contexts.impl.ContextImpl <em>Context</em>}' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ContextImpl
   * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ContextsPackageImpl#getContext()
   * @generated
   */
	int CONTEXT = 1;

	/**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTEXT__ID = NAMED_ELEMENT__ID;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int CONTEXT__NAME = NAMED_ELEMENT__NAME;

	/**
   * The feature id for the '<em><b>Categories</b></em>' containment reference list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int CONTEXT__CATEGORIES = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
   * The feature id for the '<em><b>Super Context</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTEXT__SUPER_CONTEXT = NAMED_ELEMENT_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Transient Categories</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTEXT__TRANSIENT_CATEGORIES = NAMED_ELEMENT_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Super Category Variables</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTEXT__SUPER_CATEGORY_VARIABLES = NAMED_ELEMENT_FEATURE_COUNT + 3;

  /**
   * The feature id for the '<em><b>Overriding Variables</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTEXT__OVERRIDING_VARIABLES = NAMED_ELEMENT_FEATURE_COUNT + 4;

  /**
   * The feature id for the '<em><b>Super Category Categories</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTEXT__SUPER_CATEGORY_CATEGORIES = NAMED_ELEMENT_FEATURE_COUNT + 5;

  /**
   * The feature id for the '<em><b>Selected Versions</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTEXT__SELECTED_VERSIONS = NAMED_ELEMENT_FEATURE_COUNT + 6;

  /**
   * The feature id for the '<em><b>Current Versions</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTEXT__CURRENT_VERSIONS = NAMED_ELEMENT_FEATURE_COUNT + 7;

  /**
   * The feature id for the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTEXT__DESCRIPTION = NAMED_ELEMENT_FEATURE_COUNT + 8;

  /**
   * The number of structural features of the '<em>Context</em>' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int CONTEXT_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 9;

	/**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.model.contexts.impl.AbstractVariableImpl <em>Abstract Variable</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.model.contexts.impl.AbstractVariableImpl
   * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ContextsPackageImpl#getAbstractVariable()
   * @generated
   */
  int ABSTRACT_VARIABLE = 7;

  /**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ABSTRACT_VARIABLE__ID = NAMED_ELEMENT__ID;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ABSTRACT_VARIABLE__NAME = NAMED_ELEMENT__NAME;

  /**
   * The feature id for the '<em><b>Final</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ABSTRACT_VARIABLE__FINAL = NAMED_ELEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Multi Valued</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ABSTRACT_VARIABLE__MULTI_VALUED = NAMED_ELEMENT_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Values</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ABSTRACT_VARIABLE__VALUES = NAMED_ELEMENT_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>Abstract Variable</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ABSTRACT_VARIABLE_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.model.contexts.impl.VariableImpl <em>Variable</em>}' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.model.contexts.impl.VariableImpl
   * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ContextsPackageImpl#getVariable()
   * @generated
   */
	int VARIABLE = 2;

	/**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VARIABLE__ID = ABSTRACT_VARIABLE__ID;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int VARIABLE__NAME = ABSTRACT_VARIABLE__NAME;

	/**
   * The feature id for the '<em><b>Final</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int VARIABLE__FINAL = ABSTRACT_VARIABLE__FINAL;

  /**
   * The feature id for the '<em><b>Multi Valued</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VARIABLE__MULTI_VALUED = ABSTRACT_VARIABLE__MULTI_VALUED;

  /**
   * The feature id for the '<em><b>Values</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VARIABLE__VALUES = ABSTRACT_VARIABLE__VALUES;

  /**
   * The feature id for the '<em><b>Super Category</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VARIABLE__SUPER_CATEGORY = ABSTRACT_VARIABLE_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Mandatory</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int VARIABLE__MANDATORY = ABSTRACT_VARIABLE_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int VARIABLE__DESCRIPTION = ABSTRACT_VARIABLE_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>Variable</em>' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int VARIABLE_FEATURE_COUNT = ABSTRACT_VARIABLE_FEATURE_COUNT + 3;


	/**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.model.contexts.impl.FileVariableImpl <em>File Variable</em>}' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.model.contexts.impl.FileVariableImpl
   * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ContextsPackageImpl#getFileVariable()
   * @generated
   */
	int FILE_VARIABLE = 3;

	/**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FILE_VARIABLE__ID = VARIABLE__ID;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int FILE_VARIABLE__NAME = VARIABLE__NAME;

	/**
   * The feature id for the '<em><b>Final</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int FILE_VARIABLE__FINAL = VARIABLE__FINAL;

  /**
   * The feature id for the '<em><b>Multi Valued</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FILE_VARIABLE__MULTI_VALUED = VARIABLE__MULTI_VALUED;

  /**
   * The feature id for the '<em><b>Values</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FILE_VARIABLE__VALUES = VARIABLE__VALUES;

  /**
   * The feature id for the '<em><b>Super Category</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FILE_VARIABLE__SUPER_CATEGORY = VARIABLE__SUPER_CATEGORY;

  /**
   * The feature id for the '<em><b>Mandatory</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int FILE_VARIABLE__MANDATORY = VARIABLE__MANDATORY;

  /**
   * The feature id for the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int FILE_VARIABLE__DESCRIPTION = VARIABLE__DESCRIPTION;

  /**
   * The number of structural features of the '<em>File Variable</em>' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int FILE_VARIABLE_FEATURE_COUNT = VARIABLE_FEATURE_COUNT + 0;

	/**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.model.contexts.impl.FolderVariableImpl <em>Folder Variable</em>}' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.model.contexts.impl.FolderVariableImpl
   * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ContextsPackageImpl#getFolderVariable()
   * @generated
   */
	int FOLDER_VARIABLE = 4;

	/**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FOLDER_VARIABLE__ID = VARIABLE__ID;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int FOLDER_VARIABLE__NAME = VARIABLE__NAME;

	/**
   * The feature id for the '<em><b>Final</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int FOLDER_VARIABLE__FINAL = VARIABLE__FINAL;

  /**
   * The feature id for the '<em><b>Multi Valued</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FOLDER_VARIABLE__MULTI_VALUED = VARIABLE__MULTI_VALUED;

  /**
   * The feature id for the '<em><b>Values</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FOLDER_VARIABLE__VALUES = VARIABLE__VALUES;

  /**
   * The feature id for the '<em><b>Super Category</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FOLDER_VARIABLE__SUPER_CATEGORY = VARIABLE__SUPER_CATEGORY;

  /**
   * The feature id for the '<em><b>Mandatory</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int FOLDER_VARIABLE__MANDATORY = VARIABLE__MANDATORY;

  /**
   * The feature id for the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int FOLDER_VARIABLE__DESCRIPTION = VARIABLE__DESCRIPTION;

  /**
   * The number of structural features of the '<em>Folder Variable</em>' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int FOLDER_VARIABLE_FEATURE_COUNT = VARIABLE_FEATURE_COUNT + 0;


	/**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.model.contexts.impl.OverridingVariableImpl <em>Overriding Variable</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.model.contexts.impl.OverridingVariableImpl
   * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ContextsPackageImpl#getOverridingVariable()
   * @generated
   */
  int OVERRIDING_VARIABLE = 8;

  /**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OVERRIDING_VARIABLE__ID = ABSTRACT_VARIABLE__ID;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OVERRIDING_VARIABLE__NAME = ABSTRACT_VARIABLE__NAME;

  /**
   * The feature id for the '<em><b>Final</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OVERRIDING_VARIABLE__FINAL = ABSTRACT_VARIABLE__FINAL;

  /**
   * The feature id for the '<em><b>Multi Valued</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OVERRIDING_VARIABLE__MULTI_VALUED = ABSTRACT_VARIABLE__MULTI_VALUED;

  /**
   * The feature id for the '<em><b>Values</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OVERRIDING_VARIABLE__VALUES = ABSTRACT_VARIABLE__VALUES;

  /**
   * The feature id for the '<em><b>Overridden Variable</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OVERRIDING_VARIABLE__OVERRIDDEN_VARIABLE = ABSTRACT_VARIABLE_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Overriding Variable</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OVERRIDING_VARIABLE_FEATURE_COUNT = ABSTRACT_VARIABLE_FEATURE_COUNT + 1;


  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.model.contexts.impl.VariableValueImpl <em>Variable Value</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.model.contexts.impl.VariableValueImpl
   * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ContextsPackageImpl#getVariableValue()
   * @generated
   */
  int VARIABLE_VALUE = 9;

  /**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VARIABLE_VALUE__ID = MODEL_ELEMENT__ID;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VARIABLE_VALUE__VALUE = MODEL_ELEMENT_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Variable Value</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int VARIABLE_VALUE_FEATURE_COUNT = MODEL_ELEMENT_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.model.contexts.impl.OverridingVariableValueImpl <em>Overriding Variable Value</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.model.contexts.impl.OverridingVariableValueImpl
   * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ContextsPackageImpl#getOverridingVariableValue()
   * @generated
   */
  int OVERRIDING_VARIABLE_VALUE = 11;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.model.contexts.impl.InstallationCategoryImpl <em>Installation Category</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.model.contexts.impl.InstallationCategoryImpl
   * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ContextsPackageImpl#getInstallationCategory()
   * @generated
   */
  int INSTALLATION_CATEGORY = 13;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.model.contexts.impl.ReferencingElementImpl <em>Referencing Element</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ReferencingElementImpl
   * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ContextsPackageImpl#getReferencingElement()
   * @generated
   */
  int REFERENCING_ELEMENT = 16;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.model.contexts.impl.PendingElementsCategoryImpl <em>Pending Elements Category</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.model.contexts.impl.PendingElementsCategoryImpl
   * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ContextsPackageImpl#getPendingElementsCategory()
   * @generated
   */
  int PENDING_ELEMENTS_CATEGORY = 10;

  /**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PENDING_ELEMENTS_CATEGORY__ID = CATEGORY__ID;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PENDING_ELEMENTS_CATEGORY__NAME = CATEGORY__NAME;

  /**
   * The feature id for the '<em><b>Super Category</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PENDING_ELEMENTS_CATEGORY__SUPER_CATEGORY = CATEGORY__SUPER_CATEGORY;

  /**
   * The feature id for the '<em><b>Variables</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PENDING_ELEMENTS_CATEGORY__VARIABLES = CATEGORY__VARIABLES;

  /**
   * The feature id for the '<em><b>Categories</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PENDING_ELEMENTS_CATEGORY__CATEGORIES = CATEGORY__CATEGORIES;

  /**
   * The number of structural features of the '<em>Pending Elements Category</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PENDING_ELEMENTS_CATEGORY_FEATURE_COUNT = CATEGORY_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OVERRIDING_VARIABLE_VALUE__ID = VARIABLE_VALUE__ID;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OVERRIDING_VARIABLE_VALUE__VALUE = VARIABLE_VALUE__VALUE;

  /**
   * The feature id for the '<em><b>Overridden Value</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OVERRIDING_VARIABLE_VALUE__OVERRIDDEN_VALUE = VARIABLE_VALUE_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Overriding Variable Value</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OVERRIDING_VARIABLE_VALUE_FEATURE_COUNT = VARIABLE_VALUE_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.model.contexts.impl.EnvironmentVariableValueImpl <em>Environment Variable Value</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.model.contexts.impl.EnvironmentVariableValueImpl
   * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ContextsPackageImpl#getEnvironmentVariableValue()
   * @generated
   */
  int ENVIRONMENT_VARIABLE_VALUE = 17;

  /**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENVIRONMENT_VARIABLE_VALUE__ID = VARIABLE_VALUE__ID;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENVIRONMENT_VARIABLE_VALUE__VALUE = VARIABLE_VALUE__VALUE;

  /**
   * The feature id for the '<em><b>Environment Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENVIRONMENT_VARIABLE_VALUE__ENVIRONMENT_ID = VARIABLE_VALUE_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Values</b></em>' map.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENVIRONMENT_VARIABLE_VALUE__VALUES = VARIABLE_VALUE_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Environment Variable Value</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENVIRONMENT_VARIABLE_VALUE_FEATURE_COUNT = VARIABLE_VALUE_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.model.contexts.impl.OverridingEnvironmentVariableValueImpl <em>Overriding Environment Variable Value</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.model.contexts.impl.OverridingEnvironmentVariableValueImpl
   * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ContextsPackageImpl#getOverridingEnvironmentVariableValue()
   * @generated
   */
  int OVERRIDING_ENVIRONMENT_VARIABLE_VALUE = 12;

  /**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OVERRIDING_ENVIRONMENT_VARIABLE_VALUE__ID = ENVIRONMENT_VARIABLE_VALUE__ID;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OVERRIDING_ENVIRONMENT_VARIABLE_VALUE__VALUE = ENVIRONMENT_VARIABLE_VALUE__VALUE;

  /**
   * The feature id for the '<em><b>Environment Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OVERRIDING_ENVIRONMENT_VARIABLE_VALUE__ENVIRONMENT_ID = ENVIRONMENT_VARIABLE_VALUE__ENVIRONMENT_ID;

  /**
   * The feature id for the '<em><b>Values</b></em>' map.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OVERRIDING_ENVIRONMENT_VARIABLE_VALUE__VALUES = ENVIRONMENT_VARIABLE_VALUE__VALUES;

  /**
   * The feature id for the '<em><b>Overridden Value</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OVERRIDING_ENVIRONMENT_VARIABLE_VALUE__OVERRIDDEN_VALUE = ENVIRONMENT_VARIABLE_VALUE_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Overriding Environment Variable Value</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OVERRIDING_ENVIRONMENT_VARIABLE_VALUE_FEATURE_COUNT = ENVIRONMENT_VARIABLE_VALUE_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INSTALLATION_CATEGORY__ID = CATEGORY__ID;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INSTALLATION_CATEGORY__NAME = CATEGORY__NAME;

  /**
   * The feature id for the '<em><b>Super Category</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INSTALLATION_CATEGORY__SUPER_CATEGORY = CATEGORY__SUPER_CATEGORY;

  /**
   * The feature id for the '<em><b>Variables</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INSTALLATION_CATEGORY__VARIABLES = CATEGORY__VARIABLES;

  /**
   * The feature id for the '<em><b>Categories</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INSTALLATION_CATEGORY__CATEGORIES = CATEGORY__CATEGORIES;

  /**
   * The feature id for the '<em><b>Referenceable</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INSTALLATION_CATEGORY__REFERENCEABLE = CATEGORY_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Reference Path</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INSTALLATION_CATEGORY__REFERENCE_PATH = CATEGORY_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Resolved Reference</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INSTALLATION_CATEGORY__RESOLVED_REFERENCE = CATEGORY_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>Installation Category</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INSTALLATION_CATEGORY_FEATURE_COUNT = CATEGORY_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.model.contexts.impl.ContributedElementImpl <em>Contributed Element</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ContributedElementImpl
   * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ContextsPackageImpl#getContributedElement()
   * @generated
   */
  int CONTRIBUTED_ELEMENT = 14;

  /**
   * The feature id for the '<em><b>Super Category</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTRIBUTED_ELEMENT__SUPER_CATEGORY = 0;

  /**
   * The number of structural features of the '<em>Contributed Element</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTRIBUTED_ELEMENT_FEATURE_COUNT = 1;


  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.model.contexts.impl.ReferenceableElementImpl <em>Referenceable Element</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ReferenceableElementImpl
   * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ContextsPackageImpl#getReferenceableElement()
   * @generated
   */
  int REFERENCEABLE_ELEMENT = 15;

  /**
   * The feature id for the '<em><b>Referenceable</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REFERENCEABLE_ELEMENT__REFERENCEABLE = 0;

  /**
   * The number of structural features of the '<em>Referenceable Element</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REFERENCEABLE_ELEMENT_FEATURE_COUNT = 1;

  /**
   * The feature id for the '<em><b>Reference Path</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REFERENCING_ELEMENT__REFERENCE_PATH = 0;

  /**
   * The feature id for the '<em><b>Resolved Reference</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REFERENCING_ELEMENT__RESOLVED_REFERENCE = 1;

  /**
   * The number of structural features of the '<em>Referencing Element</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REFERENCING_ELEMENT_FEATURE_COUNT = 2;


  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.model.contexts.impl.EnvironmentVariableImpl <em>Environment Variable</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.model.contexts.impl.EnvironmentVariableImpl
   * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ContextsPackageImpl#getEnvironmentVariable()
   * @generated
   */
  int ENVIRONMENT_VARIABLE = 18;

  /**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENVIRONMENT_VARIABLE__ID = VARIABLE__ID;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENVIRONMENT_VARIABLE__NAME = VARIABLE__NAME;

  /**
   * The feature id for the '<em><b>Final</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENVIRONMENT_VARIABLE__FINAL = VARIABLE__FINAL;

  /**
   * The feature id for the '<em><b>Multi Valued</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENVIRONMENT_VARIABLE__MULTI_VALUED = VARIABLE__MULTI_VALUED;

  /**
   * The feature id for the '<em><b>Values</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENVIRONMENT_VARIABLE__VALUES = VARIABLE__VALUES;

  /**
   * The feature id for the '<em><b>Super Category</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENVIRONMENT_VARIABLE__SUPER_CATEGORY = VARIABLE__SUPER_CATEGORY;

  /**
   * The feature id for the '<em><b>Mandatory</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENVIRONMENT_VARIABLE__MANDATORY = VARIABLE__MANDATORY;

  /**
   * The feature id for the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENVIRONMENT_VARIABLE__DESCRIPTION = VARIABLE__DESCRIPTION;

  /**
   * The number of structural features of the '<em>Environment Variable</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ENVIRONMENT_VARIABLE_FEATURE_COUNT = VARIABLE_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.model.contexts.impl.StringToStringMapImpl <em>String To String Map</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.model.contexts.impl.StringToStringMapImpl
   * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ContextsPackageImpl#getStringToStringMap()
   * @generated
   */
  int STRING_TO_STRING_MAP = 19;

  /**
   * The feature id for the '<em><b>Key</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STRING_TO_STRING_MAP__KEY = 0;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STRING_TO_STRING_MAP__VALUE = 1;

  /**
   * The number of structural features of the '<em>String To String Map</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STRING_TO_STRING_MAP_FEATURE_COUNT = 2;


  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.model.contexts.Category <em>Category</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for class '<em>Category</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.Category
   * @generated
   */
	EClass getCategory();

	/**
   * Returns the meta object for the containment reference list '{@link com.thalesgroup.orchestra.framework.model.contexts.Category#getVariables <em>Variables</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Variables</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.Category#getVariables()
   * @see #getCategory()
   * @generated
   */
	EReference getCategory_Variables();

	/**
   * Returns the meta object for the containment reference list '{@link com.thalesgroup.orchestra.framework.model.contexts.Category#getCategories <em>Categories</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Categories</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.Category#getCategories()
   * @see #getCategory()
   * @generated
   */
	EReference getCategory_Categories();

	/**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.model.contexts.Context <em>Context</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for class '<em>Context</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.Context
   * @generated
   */
	EClass getContext();

	/**
   * Returns the meta object for the containment reference list '{@link com.thalesgroup.orchestra.framework.model.contexts.Context#getCategories <em>Categories</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Categories</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.Context#getCategories()
   * @see #getContext()
   * @generated
   */
	EReference getContext_Categories();

	/**
   * Returns the meta object for the reference '{@link com.thalesgroup.orchestra.framework.model.contexts.Context#getSuperContext <em>Super Context</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Super Context</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.Context#getSuperContext()
   * @see #getContext()
   * @generated
   */
  EReference getContext_SuperContext();

  /**
   * Returns the meta object for the containment reference list '{@link com.thalesgroup.orchestra.framework.model.contexts.Context#getTransientCategories <em>Transient Categories</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Transient Categories</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.Context#getTransientCategories()
   * @see #getContext()
   * @generated
   */
  EReference getContext_TransientCategories();

  /**
   * Returns the meta object for the containment reference list '{@link com.thalesgroup.orchestra.framework.model.contexts.Context#getSuperCategoryVariables <em>Super Category Variables</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Super Category Variables</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.Context#getSuperCategoryVariables()
   * @see #getContext()
   * @generated
   */
  EReference getContext_SuperCategoryVariables();

  /**
   * Returns the meta object for the containment reference list '{@link com.thalesgroup.orchestra.framework.model.contexts.Context#getOverridingVariables <em>Overriding Variables</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Overriding Variables</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.Context#getOverridingVariables()
   * @see #getContext()
   * @generated
   */
  EReference getContext_OverridingVariables();

  /**
   * Returns the meta object for the containment reference list '{@link com.thalesgroup.orchestra.framework.model.contexts.Context#getSuperCategoryCategories <em>Super Category Categories</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Super Category Categories</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.Context#getSuperCategoryCategories()
   * @see #getContext()
   * @generated
   */
  EReference getContext_SuperCategoryCategories();

  /**
   * Returns the meta object for the containment reference list '{@link com.thalesgroup.orchestra.framework.model.contexts.Context#getSelectedVersions <em>Selected Versions</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Selected Versions</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.Context#getSelectedVersions()
   * @see #getContext()
   * @generated
   */
  EReference getContext_SelectedVersions();

  /**
   * Returns the meta object for the containment reference list '{@link com.thalesgroup.orchestra.framework.model.contexts.Context#getCurrentVersions <em>Current Versions</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Current Versions</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.Context#getCurrentVersions()
   * @see #getContext()
   * @generated
   */
  EReference getContext_CurrentVersions();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.model.contexts.Context#getDescription <em>Description</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Description</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.Context#getDescription()
   * @see #getContext()
   * @generated
   */
  EAttribute getContext_Description();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.model.contexts.Variable <em>Variable</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for class '<em>Variable</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.Variable
   * @generated
   */
	EClass getVariable();

	/**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.model.contexts.Variable#getDescription <em>Description</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Description</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.Variable#getDescription()
   * @see #getVariable()
   * @generated
   */
	EAttribute getVariable_Description();

	/**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.model.contexts.Variable#isMandatory <em>Mandatory</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Mandatory</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.Variable#isMandatory()
   * @see #getVariable()
   * @generated
   */
	EAttribute getVariable_Mandatory();

	/**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.model.contexts.FileVariable <em>File Variable</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for class '<em>File Variable</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.FileVariable
   * @generated
   */
	EClass getFileVariable();

	/**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.model.contexts.FolderVariable <em>Folder Variable</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for class '<em>Folder Variable</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.FolderVariable
   * @generated
   */
	EClass getFolderVariable();

	/**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.model.contexts.ModelElement <em>Model Element</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Model Element</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.ModelElement
   * @generated
   */
  EClass getModelElement();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.model.contexts.ModelElement#getId <em>Id</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Id</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.ModelElement#getId()
   * @see #getModelElement()
   * @generated
   */
  EAttribute getModelElement_Id();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.model.contexts.NamedElement <em>Named Element</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Named Element</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.NamedElement
   * @generated
   */
  EClass getNamedElement();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.model.contexts.NamedElement#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.NamedElement#getName()
   * @see #getNamedElement()
   * @generated
   */
  EAttribute getNamedElement_Name();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable <em>Abstract Variable</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Abstract Variable</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable
   * @generated
   */
  EClass getAbstractVariable();

  /**
   * Returns the meta object for the containment reference list '{@link com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable#getValues <em>Values</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Values</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable#getValues()
   * @see #getAbstractVariable()
   * @generated
   */
  EReference getAbstractVariable_Values();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable#isFinal <em>Final</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Final</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable#isFinal()
   * @see #getAbstractVariable()
   * @generated
   */
  EAttribute getAbstractVariable_Final();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable#isMultiValued <em>Multi Valued</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Multi Valued</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable#isMultiValued()
   * @see #getAbstractVariable()
   * @generated
   */
  EAttribute getAbstractVariable_MultiValued();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.model.contexts.OverridingVariable <em>Overriding Variable</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Overriding Variable</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.OverridingVariable
   * @generated
   */
  EClass getOverridingVariable();

  /**
   * Returns the meta object for the reference '{@link com.thalesgroup.orchestra.framework.model.contexts.OverridingVariable#getOverriddenVariable <em>Overridden Variable</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Overridden Variable</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.OverridingVariable#getOverriddenVariable()
   * @see #getOverridingVariable()
   * @generated
   */
  EReference getOverridingVariable_OverriddenVariable();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.model.contexts.VariableValue <em>Variable Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Variable Value</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.VariableValue
   * @generated
   */
  EClass getVariableValue();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.model.contexts.VariableValue#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.VariableValue#getValue()
   * @see #getVariableValue()
   * @generated
   */
  EAttribute getVariableValue_Value();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.model.contexts.OverridingVariableValue <em>Overriding Variable Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Overriding Variable Value</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.OverridingVariableValue
   * @generated
   */
  EClass getOverridingVariableValue();

  /**
   * Returns the meta object for the reference '{@link com.thalesgroup.orchestra.framework.model.contexts.OverridingVariableValue#getOverriddenValue <em>Overridden Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Overridden Value</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.OverridingVariableValue#getOverriddenValue()
   * @see #getOverridingVariableValue()
   * @generated
   */
  EReference getOverridingVariableValue_OverriddenValue();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.model.contexts.OverridingEnvironmentVariableValue <em>Overriding Environment Variable Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Overriding Environment Variable Value</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.OverridingEnvironmentVariableValue
   * @generated
   */
  EClass getOverridingEnvironmentVariableValue();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.model.contexts.InstallationCategory <em>Installation Category</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Installation Category</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.InstallationCategory
   * @generated
   */
  EClass getInstallationCategory();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.model.contexts.PendingElementsCategory <em>Pending Elements Category</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Pending Elements Category</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.PendingElementsCategory
   * @generated
   */
  EClass getPendingElementsCategory();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.model.contexts.ContributedElement <em>Contributed Element</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Contributed Element</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.ContributedElement
   * @generated
   */
  EClass getContributedElement();

  /**
   * Returns the meta object for the reference '{@link com.thalesgroup.orchestra.framework.model.contexts.ContributedElement#getSuperCategory <em>Super Category</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Super Category</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.ContributedElement#getSuperCategory()
   * @see #getContributedElement()
   * @generated
   */
  EReference getContributedElement_SuperCategory();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.model.contexts.ReferenceableElement <em>Referenceable Element</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Referenceable Element</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.ReferenceableElement
   * @generated
   */
  EClass getReferenceableElement();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.model.contexts.ReferenceableElement#isReferenceable <em>Referenceable</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Referenceable</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.ReferenceableElement#isReferenceable()
   * @see #getReferenceableElement()
   * @generated
   */
  EAttribute getReferenceableElement_Referenceable();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.model.contexts.ReferencingElement <em>Referencing Element</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Referencing Element</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.ReferencingElement
   * @generated
   */
  EClass getReferencingElement();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.model.contexts.ReferencingElement#getReferencePath <em>Reference Path</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Reference Path</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.ReferencingElement#getReferencePath()
   * @see #getReferencingElement()
   * @generated
   */
  EAttribute getReferencingElement_ReferencePath();

  /**
   * Returns the meta object for the reference '{@link com.thalesgroup.orchestra.framework.model.contexts.ReferencingElement#getResolvedReference <em>Resolved Reference</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Resolved Reference</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.ReferencingElement#getResolvedReference()
   * @see #getReferencingElement()
   * @generated
   */
  EReference getReferencingElement_ResolvedReference();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariableValue <em>Environment Variable Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Environment Variable Value</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariableValue
   * @generated
   */
  EClass getEnvironmentVariableValue();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariableValue#getEnvironmentId <em>Environment Id</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Environment Id</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariableValue#getEnvironmentId()
   * @see #getEnvironmentVariableValue()
   * @generated
   */
  EAttribute getEnvironmentVariableValue_EnvironmentId();

  /**
   * Returns the meta object for the map '{@link com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariableValue#getValues <em>Values</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the map '<em>Values</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariableValue#getValues()
   * @see #getEnvironmentVariableValue()
   * @generated
   */
  EReference getEnvironmentVariableValue_Values();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariable <em>Environment Variable</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Environment Variable</em>'.
   * @see com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariable
   * @generated
   */
  EClass getEnvironmentVariable();

  /**
   * Returns the meta object for class '{@link java.util.Map.Entry <em>String To String Map</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>String To String Map</em>'.
   * @see java.util.Map.Entry
   * @model keyDataType="org.eclipse.emf.ecore.EString"
   *        valueDataType="org.eclipse.emf.ecore.EString"
   * @generated
   */
  EClass getStringToStringMap();

  /**
   * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Key</em>'.
   * @see java.util.Map.Entry
   * @see #getStringToStringMap()
   * @generated
   */
  EAttribute getStringToStringMap_Key();

  /**
   * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see java.util.Map.Entry
   * @see #getStringToStringMap()
   * @generated
   */
  EAttribute getStringToStringMap_Value();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
	ContextsFactory getContextsFactory();

	/**
   * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
   * @generated
   */
	interface Literals {
		/**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.model.contexts.impl.CategoryImpl <em>Category</em>}' class.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.model.contexts.impl.CategoryImpl
     * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ContextsPackageImpl#getCategory()
     * @generated
     */
		EClass CATEGORY = eINSTANCE.getCategory();

		/**
     * The meta object literal for the '<em><b>Variables</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference CATEGORY__VARIABLES = eINSTANCE.getCategory_Variables();

		/**
     * The meta object literal for the '<em><b>Categories</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference CATEGORY__CATEGORIES = eINSTANCE.getCategory_Categories();

		/**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.model.contexts.impl.ContextImpl <em>Context</em>}' class.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ContextImpl
     * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ContextsPackageImpl#getContext()
     * @generated
     */
		EClass CONTEXT = eINSTANCE.getContext();

		/**
     * The meta object literal for the '<em><b>Categories</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference CONTEXT__CATEGORIES = eINSTANCE.getContext_Categories();

		/**
     * The meta object literal for the '<em><b>Super Context</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CONTEXT__SUPER_CONTEXT = eINSTANCE.getContext_SuperContext();

    /**
     * The meta object literal for the '<em><b>Transient Categories</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CONTEXT__TRANSIENT_CATEGORIES = eINSTANCE.getContext_TransientCategories();

    /**
     * The meta object literal for the '<em><b>Super Category Variables</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CONTEXT__SUPER_CATEGORY_VARIABLES = eINSTANCE.getContext_SuperCategoryVariables();

    /**
     * The meta object literal for the '<em><b>Overriding Variables</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CONTEXT__OVERRIDING_VARIABLES = eINSTANCE.getContext_OverridingVariables();

    /**
     * The meta object literal for the '<em><b>Super Category Categories</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CONTEXT__SUPER_CATEGORY_CATEGORIES = eINSTANCE.getContext_SuperCategoryCategories();

    /**
     * The meta object literal for the '<em><b>Selected Versions</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CONTEXT__SELECTED_VERSIONS = eINSTANCE.getContext_SelectedVersions();

    /**
     * The meta object literal for the '<em><b>Current Versions</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CONTEXT__CURRENT_VERSIONS = eINSTANCE.getContext_CurrentVersions();

    /**
     * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CONTEXT__DESCRIPTION = eINSTANCE.getContext_Description();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.model.contexts.impl.VariableImpl <em>Variable</em>}' class.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.model.contexts.impl.VariableImpl
     * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ContextsPackageImpl#getVariable()
     * @generated
     */
		EClass VARIABLE = eINSTANCE.getVariable();

		/**
     * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute VARIABLE__DESCRIPTION = eINSTANCE.getVariable_Description();

		/**
     * The meta object literal for the '<em><b>Mandatory</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute VARIABLE__MANDATORY = eINSTANCE.getVariable_Mandatory();

		/**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.model.contexts.impl.FileVariableImpl <em>File Variable</em>}' class.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.model.contexts.impl.FileVariableImpl
     * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ContextsPackageImpl#getFileVariable()
     * @generated
     */
		EClass FILE_VARIABLE = eINSTANCE.getFileVariable();

		/**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.model.contexts.impl.FolderVariableImpl <em>Folder Variable</em>}' class.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.model.contexts.impl.FolderVariableImpl
     * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ContextsPackageImpl#getFolderVariable()
     * @generated
     */
		EClass FOLDER_VARIABLE = eINSTANCE.getFolderVariable();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.model.contexts.impl.ModelElementImpl <em>Model Element</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ModelElementImpl
     * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ContextsPackageImpl#getModelElement()
     * @generated
     */
    EClass MODEL_ELEMENT = eINSTANCE.getModelElement();

    /**
     * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MODEL_ELEMENT__ID = eINSTANCE.getModelElement_Id();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.model.contexts.impl.NamedElementImpl <em>Named Element</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.model.contexts.impl.NamedElementImpl
     * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ContextsPackageImpl#getNamedElement()
     * @generated
     */
    EClass NAMED_ELEMENT = eINSTANCE.getNamedElement();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute NAMED_ELEMENT__NAME = eINSTANCE.getNamedElement_Name();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.model.contexts.impl.AbstractVariableImpl <em>Abstract Variable</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.model.contexts.impl.AbstractVariableImpl
     * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ContextsPackageImpl#getAbstractVariable()
     * @generated
     */
    EClass ABSTRACT_VARIABLE = eINSTANCE.getAbstractVariable();

    /**
     * The meta object literal for the '<em><b>Values</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ABSTRACT_VARIABLE__VALUES = eINSTANCE.getAbstractVariable_Values();

    /**
     * The meta object literal for the '<em><b>Final</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ABSTRACT_VARIABLE__FINAL = eINSTANCE.getAbstractVariable_Final();

    /**
     * The meta object literal for the '<em><b>Multi Valued</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ABSTRACT_VARIABLE__MULTI_VALUED = eINSTANCE.getAbstractVariable_MultiValued();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.model.contexts.impl.OverridingVariableImpl <em>Overriding Variable</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.model.contexts.impl.OverridingVariableImpl
     * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ContextsPackageImpl#getOverridingVariable()
     * @generated
     */
    EClass OVERRIDING_VARIABLE = eINSTANCE.getOverridingVariable();

    /**
     * The meta object literal for the '<em><b>Overridden Variable</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference OVERRIDING_VARIABLE__OVERRIDDEN_VARIABLE = eINSTANCE.getOverridingVariable_OverriddenVariable();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.model.contexts.impl.VariableValueImpl <em>Variable Value</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.model.contexts.impl.VariableValueImpl
     * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ContextsPackageImpl#getVariableValue()
     * @generated
     */
    EClass VARIABLE_VALUE = eINSTANCE.getVariableValue();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute VARIABLE_VALUE__VALUE = eINSTANCE.getVariableValue_Value();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.model.contexts.impl.OverridingVariableValueImpl <em>Overriding Variable Value</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.model.contexts.impl.OverridingVariableValueImpl
     * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ContextsPackageImpl#getOverridingVariableValue()
     * @generated
     */
    EClass OVERRIDING_VARIABLE_VALUE = eINSTANCE.getOverridingVariableValue();

    /**
     * The meta object literal for the '<em><b>Overridden Value</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference OVERRIDING_VARIABLE_VALUE__OVERRIDDEN_VALUE = eINSTANCE.getOverridingVariableValue_OverriddenValue();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.model.contexts.impl.OverridingEnvironmentVariableValueImpl <em>Overriding Environment Variable Value</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.model.contexts.impl.OverridingEnvironmentVariableValueImpl
     * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ContextsPackageImpl#getOverridingEnvironmentVariableValue()
     * @generated
     */
    EClass OVERRIDING_ENVIRONMENT_VARIABLE_VALUE = eINSTANCE.getOverridingEnvironmentVariableValue();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.model.contexts.impl.InstallationCategoryImpl <em>Installation Category</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.model.contexts.impl.InstallationCategoryImpl
     * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ContextsPackageImpl#getInstallationCategory()
     * @generated
     */
    EClass INSTALLATION_CATEGORY = eINSTANCE.getInstallationCategory();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.model.contexts.impl.PendingElementsCategoryImpl <em>Pending Elements Category</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.model.contexts.impl.PendingElementsCategoryImpl
     * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ContextsPackageImpl#getPendingElementsCategory()
     * @generated
     */
    EClass PENDING_ELEMENTS_CATEGORY = eINSTANCE.getPendingElementsCategory();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.model.contexts.impl.ContributedElementImpl <em>Contributed Element</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ContributedElementImpl
     * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ContextsPackageImpl#getContributedElement()
     * @generated
     */
    EClass CONTRIBUTED_ELEMENT = eINSTANCE.getContributedElement();

    /**
     * The meta object literal for the '<em><b>Super Category</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CONTRIBUTED_ELEMENT__SUPER_CATEGORY = eINSTANCE.getContributedElement_SuperCategory();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.model.contexts.impl.ReferenceableElementImpl <em>Referenceable Element</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ReferenceableElementImpl
     * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ContextsPackageImpl#getReferenceableElement()
     * @generated
     */
    EClass REFERENCEABLE_ELEMENT = eINSTANCE.getReferenceableElement();

    /**
     * The meta object literal for the '<em><b>Referenceable</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute REFERENCEABLE_ELEMENT__REFERENCEABLE = eINSTANCE.getReferenceableElement_Referenceable();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.model.contexts.impl.ReferencingElementImpl <em>Referencing Element</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ReferencingElementImpl
     * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ContextsPackageImpl#getReferencingElement()
     * @generated
     */
    EClass REFERENCING_ELEMENT = eINSTANCE.getReferencingElement();

    /**
     * The meta object literal for the '<em><b>Reference Path</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute REFERENCING_ELEMENT__REFERENCE_PATH = eINSTANCE.getReferencingElement_ReferencePath();

    /**
     * The meta object literal for the '<em><b>Resolved Reference</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference REFERENCING_ELEMENT__RESOLVED_REFERENCE = eINSTANCE.getReferencingElement_ResolvedReference();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.model.contexts.impl.EnvironmentVariableValueImpl <em>Environment Variable Value</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.model.contexts.impl.EnvironmentVariableValueImpl
     * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ContextsPackageImpl#getEnvironmentVariableValue()
     * @generated
     */
    EClass ENVIRONMENT_VARIABLE_VALUE = eINSTANCE.getEnvironmentVariableValue();

    /**
     * The meta object literal for the '<em><b>Environment Id</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ENVIRONMENT_VARIABLE_VALUE__ENVIRONMENT_ID = eINSTANCE.getEnvironmentVariableValue_EnvironmentId();

    /**
     * The meta object literal for the '<em><b>Values</b></em>' map feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ENVIRONMENT_VARIABLE_VALUE__VALUES = eINSTANCE.getEnvironmentVariableValue_Values();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.model.contexts.impl.EnvironmentVariableImpl <em>Environment Variable</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.model.contexts.impl.EnvironmentVariableImpl
     * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ContextsPackageImpl#getEnvironmentVariable()
     * @generated
     */
    EClass ENVIRONMENT_VARIABLE = eINSTANCE.getEnvironmentVariable();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.model.contexts.impl.StringToStringMapImpl <em>String To String Map</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.model.contexts.impl.StringToStringMapImpl
     * @see com.thalesgroup.orchestra.framework.model.contexts.impl.ContextsPackageImpl#getStringToStringMap()
     * @generated
     */
    EClass STRING_TO_STRING_MAP = eINSTANCE.getStringToStringMap();

    /**
     * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute STRING_TO_STRING_MAP__KEY = eINSTANCE.getStringToStringMap_Key();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute STRING_TO_STRING_MAP__VALUE = eINSTANCE.getStringToStringMap_Value();

	}

} //ContextsPackage
