/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.model.contexts.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Category;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsFactory;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.contexts.ContributedElement;
import com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariable;
import com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.FileVariable;
import com.thalesgroup.orchestra.framework.model.contexts.FolderVariable;
import com.thalesgroup.orchestra.framework.model.contexts.InstallationCategory;
import com.thalesgroup.orchestra.framework.model.contexts.ModelElement;
import com.thalesgroup.orchestra.framework.model.contexts.NamedElement;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingEnvironmentVariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingVariable;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingVariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.PendingElementsCategory;
import com.thalesgroup.orchestra.framework.model.contexts.ReferenceableElement;
import com.thalesgroup.orchestra.framework.model.contexts.ReferencingElement;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import java.util.Map;
import com.thalesgroup.orchestra.framework.model.contexts.util.ContextsValidator;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ContextsPackageImpl extends EPackageImpl implements ContextsPackage {
	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	private EClass categoryEClass = null;

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	private EClass contextEClass = null;

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	private EClass variableEClass = null;

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	private EClass fileVariableEClass = null;

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	private EClass folderVariableEClass = null;

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass modelElementEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass namedElementEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass abstractVariableEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass overridingVariableEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass variableValueEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass overridingVariableValueEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass overridingEnvironmentVariableValueEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass installationCategoryEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass pendingElementsCategoryEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass contributedElementEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass referenceableElementEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass referencingElementEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass environmentVariableValueEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass environmentVariableEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass stringToStringMapEClass = null;

  /**
   * Creates an instance of the model <b>Package</b>, registered with
   * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
   * package URI value.
   * <p>Note: the correct way to create the package is via the static
   * factory method {@link #init init()}, which also performs
   * initialization of the package, or returns the registered package,
   * if one already exists.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see org.eclipse.emf.ecore.EPackage.Registry
   * @see com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage#eNS_URI
   * @see #init()
   * @generated
   */
	private ContextsPackageImpl() {
    super(eNS_URI, ContextsFactory.eINSTANCE);
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	private static boolean isInited = false;

	/**
   * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
   * 
   * <p>This method is used to initialize {@link ContextsPackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
	public static ContextsPackage init() {
    if (isInited) return (ContextsPackage)EPackage.Registry.INSTANCE.getEPackage(ContextsPackage.eNS_URI);

    // Obtain or create and register package
    ContextsPackageImpl theContextsPackage = (ContextsPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ContextsPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ContextsPackageImpl());

    isInited = true;

    // Initialize simple dependencies
    XMLTypePackage.eINSTANCE.eClass();

    // Create package meta-data objects
    theContextsPackage.createPackageContents();

    // Initialize created meta-data
    theContextsPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theContextsPackage.freeze();

  
    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(ContextsPackage.eNS_URI, theContextsPackage);
    return theContextsPackage;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EClass getCategory() {
    return categoryEClass;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getCategory_Variables() {
    return (EReference)categoryEClass.getEStructuralFeatures().get(0);
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getCategory_Categories() {
    return (EReference)categoryEClass.getEStructuralFeatures().get(1);
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EClass getContext() {
    return contextEClass;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getContext_Categories() {
    return (EReference)contextEClass.getEStructuralFeatures().get(0);
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getContext_SuperContext() {
    return (EReference)contextEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getContext_TransientCategories() {
    return (EReference)contextEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getContext_SuperCategoryVariables() {
    return (EReference)contextEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getContext_OverridingVariables() {
    return (EReference)contextEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getContext_SuperCategoryCategories() {
    return (EReference)contextEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getContext_SelectedVersions() {
    return (EReference)contextEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getContext_CurrentVersions() {
    return (EReference)contextEClass.getEStructuralFeatures().get(7);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getContext_Description() {
    return (EAttribute)contextEClass.getEStructuralFeatures().get(8);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EClass getVariable() {
    return variableEClass;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getVariable_Description() {
    return (EAttribute)variableEClass.getEStructuralFeatures().get(1);
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getVariable_Mandatory() {
    return (EAttribute)variableEClass.getEStructuralFeatures().get(0);
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EClass getFileVariable() {
    return fileVariableEClass;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EClass getFolderVariable() {
    return folderVariableEClass;
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getModelElement() {
    return modelElementEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getModelElement_Id() {
    return (EAttribute)modelElementEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getNamedElement() {
    return namedElementEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getNamedElement_Name() {
    return (EAttribute)namedElementEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getAbstractVariable() {
    return abstractVariableEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getAbstractVariable_Values() {
    return (EReference)abstractVariableEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getAbstractVariable_Final() {
    return (EAttribute)abstractVariableEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getAbstractVariable_MultiValued() {
    return (EAttribute)abstractVariableEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getOverridingVariable() {
    return overridingVariableEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getOverridingVariable_OverriddenVariable() {
    return (EReference)overridingVariableEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getVariableValue() {
    return variableValueEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVariableValue_Value() {
    return (EAttribute)variableValueEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getOverridingVariableValue() {
    return overridingVariableValueEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getOverridingVariableValue_OverriddenValue() {
    return (EReference)overridingVariableValueEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getOverridingEnvironmentVariableValue() {
    return overridingEnvironmentVariableValueEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getInstallationCategory() {
    return installationCategoryEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getPendingElementsCategory() {
    return pendingElementsCategoryEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getContributedElement() {
    return contributedElementEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getContributedElement_SuperCategory() {
    return (EReference)contributedElementEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getReferenceableElement() {
    return referenceableElementEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getReferenceableElement_Referenceable() {
    return (EAttribute)referenceableElementEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getReferencingElement() {
    return referencingElementEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getReferencingElement_ReferencePath() {
    return (EAttribute)referencingElementEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getReferencingElement_ResolvedReference() {
    return (EReference)referencingElementEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getEnvironmentVariableValue() {
    return environmentVariableValueEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getEnvironmentVariableValue_EnvironmentId() {
    return (EAttribute)environmentVariableValueEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getEnvironmentVariableValue_Values() {
    return (EReference)environmentVariableValueEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getEnvironmentVariable() {
    return environmentVariableEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getStringToStringMap() {
    return stringToStringMapEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getStringToStringMap_Key() {
    return (EAttribute)stringToStringMapEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getStringToStringMap_Value() {
    return (EAttribute)stringToStringMapEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public ContextsFactory getContextsFactory() {
    return (ContextsFactory)getEFactoryInstance();
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	private boolean isCreated = false;

	/**
   * Creates the meta-model objects for the package.  This method is
   * guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void createPackageContents() {
    if (isCreated) return;
    isCreated = true;

    // Create classes and their features
    categoryEClass = createEClass(CATEGORY);
    createEReference(categoryEClass, CATEGORY__VARIABLES);
    createEReference(categoryEClass, CATEGORY__CATEGORIES);

    contextEClass = createEClass(CONTEXT);
    createEReference(contextEClass, CONTEXT__CATEGORIES);
    createEReference(contextEClass, CONTEXT__SUPER_CONTEXT);
    createEReference(contextEClass, CONTEXT__TRANSIENT_CATEGORIES);
    createEReference(contextEClass, CONTEXT__SUPER_CATEGORY_VARIABLES);
    createEReference(contextEClass, CONTEXT__OVERRIDING_VARIABLES);
    createEReference(contextEClass, CONTEXT__SUPER_CATEGORY_CATEGORIES);
    createEReference(contextEClass, CONTEXT__SELECTED_VERSIONS);
    createEReference(contextEClass, CONTEXT__CURRENT_VERSIONS);
    createEAttribute(contextEClass, CONTEXT__DESCRIPTION);

    variableEClass = createEClass(VARIABLE);
    createEAttribute(variableEClass, VARIABLE__MANDATORY);
    createEAttribute(variableEClass, VARIABLE__DESCRIPTION);

    fileVariableEClass = createEClass(FILE_VARIABLE);

    folderVariableEClass = createEClass(FOLDER_VARIABLE);

    modelElementEClass = createEClass(MODEL_ELEMENT);
    createEAttribute(modelElementEClass, MODEL_ELEMENT__ID);

    namedElementEClass = createEClass(NAMED_ELEMENT);
    createEAttribute(namedElementEClass, NAMED_ELEMENT__NAME);

    abstractVariableEClass = createEClass(ABSTRACT_VARIABLE);
    createEAttribute(abstractVariableEClass, ABSTRACT_VARIABLE__FINAL);
    createEAttribute(abstractVariableEClass, ABSTRACT_VARIABLE__MULTI_VALUED);
    createEReference(abstractVariableEClass, ABSTRACT_VARIABLE__VALUES);

    overridingVariableEClass = createEClass(OVERRIDING_VARIABLE);
    createEReference(overridingVariableEClass, OVERRIDING_VARIABLE__OVERRIDDEN_VARIABLE);

    variableValueEClass = createEClass(VARIABLE_VALUE);
    createEAttribute(variableValueEClass, VARIABLE_VALUE__VALUE);

    pendingElementsCategoryEClass = createEClass(PENDING_ELEMENTS_CATEGORY);

    overridingVariableValueEClass = createEClass(OVERRIDING_VARIABLE_VALUE);
    createEReference(overridingVariableValueEClass, OVERRIDING_VARIABLE_VALUE__OVERRIDDEN_VALUE);

    overridingEnvironmentVariableValueEClass = createEClass(OVERRIDING_ENVIRONMENT_VARIABLE_VALUE);

    installationCategoryEClass = createEClass(INSTALLATION_CATEGORY);

    contributedElementEClass = createEClass(CONTRIBUTED_ELEMENT);
    createEReference(contributedElementEClass, CONTRIBUTED_ELEMENT__SUPER_CATEGORY);

    referenceableElementEClass = createEClass(REFERENCEABLE_ELEMENT);
    createEAttribute(referenceableElementEClass, REFERENCEABLE_ELEMENT__REFERENCEABLE);

    referencingElementEClass = createEClass(REFERENCING_ELEMENT);
    createEAttribute(referencingElementEClass, REFERENCING_ELEMENT__REFERENCE_PATH);
    createEReference(referencingElementEClass, REFERENCING_ELEMENT__RESOLVED_REFERENCE);

    environmentVariableValueEClass = createEClass(ENVIRONMENT_VARIABLE_VALUE);
    createEAttribute(environmentVariableValueEClass, ENVIRONMENT_VARIABLE_VALUE__ENVIRONMENT_ID);
    createEReference(environmentVariableValueEClass, ENVIRONMENT_VARIABLE_VALUE__VALUES);

    environmentVariableEClass = createEClass(ENVIRONMENT_VARIABLE);

    stringToStringMapEClass = createEClass(STRING_TO_STRING_MAP);
    createEAttribute(stringToStringMapEClass, STRING_TO_STRING_MAP__KEY);
    createEAttribute(stringToStringMapEClass, STRING_TO_STRING_MAP__VALUE);
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	private boolean isInitialized = false;

	/**
   * Complete the initialization of the package and its meta-model.  This
   * method is guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void initializePackageContents() {
    if (isInitialized) return;
    isInitialized = true;

    // Initialize package
    setName(eNAME);
    setNsPrefix(eNS_PREFIX);
    setNsURI(eNS_URI);

    // Obtain other dependent packages
    XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes
    categoryEClass.getESuperTypes().add(this.getNamedElement());
    categoryEClass.getESuperTypes().add(this.getContributedElement());
    contextEClass.getESuperTypes().add(this.getNamedElement());
    variableEClass.getESuperTypes().add(this.getAbstractVariable());
    variableEClass.getESuperTypes().add(this.getContributedElement());
    fileVariableEClass.getESuperTypes().add(this.getVariable());
    folderVariableEClass.getESuperTypes().add(this.getVariable());
    namedElementEClass.getESuperTypes().add(this.getModelElement());
    abstractVariableEClass.getESuperTypes().add(this.getNamedElement());
    overridingVariableEClass.getESuperTypes().add(this.getAbstractVariable());
    variableValueEClass.getESuperTypes().add(this.getModelElement());
    pendingElementsCategoryEClass.getESuperTypes().add(this.getCategory());
    overridingVariableValueEClass.getESuperTypes().add(this.getVariableValue());
    overridingEnvironmentVariableValueEClass.getESuperTypes().add(this.getEnvironmentVariableValue());
    overridingEnvironmentVariableValueEClass.getESuperTypes().add(this.getOverridingVariableValue());
    installationCategoryEClass.getESuperTypes().add(this.getCategory());
    installationCategoryEClass.getESuperTypes().add(this.getReferenceableElement());
    installationCategoryEClass.getESuperTypes().add(this.getReferencingElement());
    environmentVariableValueEClass.getESuperTypes().add(this.getVariableValue());
    environmentVariableEClass.getESuperTypes().add(this.getVariable());

    // Initialize classes and features; add operations and parameters
    initEClass(categoryEClass, Category.class, "Category", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getCategory_Variables(), this.getVariable(), null, "variables", null, 0, -1, Category.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getCategory_Categories(), this.getCategory(), null, "categories", null, 0, -1, Category.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(contextEClass, Context.class, "Context", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getContext_Categories(), this.getCategory(), null, "categories", null, 0, -1, Context.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getContext_SuperContext(), this.getContext(), null, "superContext", null, 0, 1, Context.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getContext_TransientCategories(), this.getCategory(), null, "transientCategories", null, 0, -1, Context.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getContext_SuperCategoryVariables(), this.getVariable(), null, "superCategoryVariables", null, 0, -1, Context.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getContext_OverridingVariables(), this.getOverridingVariable(), null, "overridingVariables", null, 0, -1, Context.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getContext_SuperCategoryCategories(), this.getCategory(), null, "superCategoryCategories", null, 0, -1, Context.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getContext_SelectedVersions(), this.getInstallationCategory(), null, "selectedVersions", null, 0, -1, Context.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getContext_CurrentVersions(), this.getInstallationCategory(), null, "currentVersions", null, 0, -1, Context.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getContext_Description(), ecorePackage.getEString(), "description", null, 0, 1, Context.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(variableEClass, Variable.class, "Variable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getVariable_Mandatory(), ecorePackage.getEBoolean(), "mandatory", "false", 1, 1, Variable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVariable_Description(), ecorePackage.getEString(), "description", null, 0, 1, Variable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(fileVariableEClass, FileVariable.class, "FileVariable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(folderVariableEClass, FolderVariable.class, "FolderVariable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(modelElementEClass, ModelElement.class, "ModelElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getModelElement_Id(), ecorePackage.getEString(), "id", null, 0, 1, ModelElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(namedElementEClass, NamedElement.class, "NamedElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getNamedElement_Name(), ecorePackage.getEString(), "name", null, 0, 1, NamedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(abstractVariableEClass, AbstractVariable.class, "AbstractVariable", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getAbstractVariable_Final(), ecorePackage.getEBoolean(), "final", null, 0, 1, AbstractVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getAbstractVariable_MultiValued(), theXMLTypePackage.getBoolean(), "multiValued", null, 0, 1, AbstractVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getAbstractVariable_Values(), this.getVariableValue(), null, "values", null, 0, -1, AbstractVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(overridingVariableEClass, OverridingVariable.class, "OverridingVariable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getOverridingVariable_OverriddenVariable(), this.getVariable(), null, "overriddenVariable", null, 1, 1, OverridingVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(variableValueEClass, VariableValue.class, "VariableValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getVariableValue_Value(), ecorePackage.getEString(), "value", null, 0, 1, VariableValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(pendingElementsCategoryEClass, PendingElementsCategory.class, "PendingElementsCategory", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(overridingVariableValueEClass, OverridingVariableValue.class, "OverridingVariableValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getOverridingVariableValue_OverriddenValue(), this.getVariableValue(), null, "overriddenValue", null, 0, 1, OverridingVariableValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(overridingEnvironmentVariableValueEClass, OverridingEnvironmentVariableValue.class, "OverridingEnvironmentVariableValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(installationCategoryEClass, InstallationCategory.class, "InstallationCategory", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(contributedElementEClass, ContributedElement.class, "ContributedElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getContributedElement_SuperCategory(), this.getCategory(), null, "superCategory", null, 0, 1, ContributedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(referenceableElementEClass, ReferenceableElement.class, "ReferenceableElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getReferenceableElement_Referenceable(), ecorePackage.getEBoolean(), "referenceable", "false", 0, 1, ReferenceableElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(referencingElementEClass, ReferencingElement.class, "ReferencingElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getReferencingElement_ReferencePath(), ecorePackage.getEString(), "referencePath", null, 0, 1, ReferencingElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getReferencingElement_ResolvedReference(), this.getModelElement(), null, "resolvedReference", null, 0, 1, ReferencingElement.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(environmentVariableValueEClass, EnvironmentVariableValue.class, "EnvironmentVariableValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getEnvironmentVariableValue_EnvironmentId(), ecorePackage.getEString(), "environmentId", null, 0, 1, EnvironmentVariableValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getEnvironmentVariableValue_Values(), this.getStringToStringMap(), null, "values", null, 0, -1, EnvironmentVariableValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(environmentVariableEClass, EnvironmentVariable.class, "EnvironmentVariable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(stringToStringMapEClass, Map.Entry.class, "StringToStringMap", !IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getStringToStringMap_Key(), ecorePackage.getEString(), "key", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getStringToStringMap_Value(), ecorePackage.getEString(), "value", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    // Create resource
    createResource(eNS_URI);
  }

} //ContextsPackageImpl
