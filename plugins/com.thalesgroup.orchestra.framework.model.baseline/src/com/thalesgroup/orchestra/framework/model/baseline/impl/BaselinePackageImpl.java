/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.model.baseline.impl;

import com.thalesgroup.orchestra.framework.model.baseline.Baseline;
import com.thalesgroup.orchestra.framework.model.baseline.BaselineFactory;
import com.thalesgroup.orchestra.framework.model.baseline.BaselinePackage;
import com.thalesgroup.orchestra.framework.model.baseline.Repository;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class BaselinePackageImpl extends EPackageImpl implements BaselinePackage {
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass repositoryEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass baselineEClass = null;

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
   * @see com.thalesgroup.orchestra.framework.model.baseline.BaselinePackage#eNS_URI
   * @see #init()
   * @generated
   */
  private BaselinePackageImpl() {
    super(eNS_URI, BaselineFactory.eINSTANCE);
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
   * <p>This method is used to initialize {@link BaselinePackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static BaselinePackage init() {
    if (isInited) return (BaselinePackage)EPackage.Registry.INSTANCE.getEPackage(BaselinePackage.eNS_URI);

    // Obtain or create and register package
    BaselinePackageImpl theBaselinePackage = (BaselinePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof BaselinePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new BaselinePackageImpl());

    isInited = true;

    // Create package meta-data objects
    theBaselinePackage.createPackageContents();

    // Initialize created meta-data
    theBaselinePackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theBaselinePackage.freeze();

  
    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(BaselinePackage.eNS_URI, theBaselinePackage);
    return theBaselinePackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getRepository() {
    return repositoryEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getRepository_Baselines() {
    return (EReference)repositoryEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getBaseline() {
    return baselineEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getBaseline_Name() {
    return (EAttribute)baselineEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getBaseline_ProjectRelativePath() {
    return (EAttribute)baselineEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getBaseline_ContextId() {
    return (EAttribute)baselineEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getBaseline_Description() {
    return (EAttribute)baselineEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BaselineFactory getBaselineFactory() {
    return (BaselineFactory)getEFactoryInstance();
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
    repositoryEClass = createEClass(REPOSITORY);
    createEReference(repositoryEClass, REPOSITORY__BASELINES);

    baselineEClass = createEClass(BASELINE);
    createEAttribute(baselineEClass, BASELINE__NAME);
    createEAttribute(baselineEClass, BASELINE__PROJECT_RELATIVE_PATH);
    createEAttribute(baselineEClass, BASELINE__CONTEXT_ID);
    createEAttribute(baselineEClass, BASELINE__DESCRIPTION);
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

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes

    // Initialize classes and features; add operations and parameters
    initEClass(repositoryEClass, Repository.class, "Repository", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getRepository_Baselines(), this.getBaseline(), null, "baselines", null, 0, -1, Repository.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(baselineEClass, Baseline.class, "Baseline", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getBaseline_Name(), ecorePackage.getEString(), "name", null, 0, 1, Baseline.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getBaseline_ProjectRelativePath(), ecorePackage.getEString(), "projectRelativePath", null, 0, 1, Baseline.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getBaseline_ContextId(), ecorePackage.getEString(), "contextId", null, 0, 1, Baseline.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getBaseline_Description(), ecorePackage.getEString(), "description", null, 0, 1, Baseline.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    // Create resource
    createResource(eNS_URI);
  }

} //BaselinePackageImpl
