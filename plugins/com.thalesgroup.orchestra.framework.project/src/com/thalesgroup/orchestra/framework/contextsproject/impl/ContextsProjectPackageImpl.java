/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.contextsproject.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import com.thalesgroup.orchestra.framework.contextsproject.Administrator;
import com.thalesgroup.orchestra.framework.contextsproject.ContextReference;
import com.thalesgroup.orchestra.framework.contextsproject.ContextsProject;
import com.thalesgroup.orchestra.framework.contextsproject.ContextsProjectFactory;
import com.thalesgroup.orchestra.framework.contextsproject.ContextsProjectPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ContextsProjectPackageImpl extends EPackageImpl implements ContextsProjectPackage {
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass contextsProjectEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass administratorEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass contextReferenceEClass = null;

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
   * @see com.thalesgroup.orchestra.framework.contextsproject.ContextsProjectPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private ContextsProjectPackageImpl() {
    super(eNS_URI, ContextsProjectFactory.eINSTANCE);
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
   * <p>This method is used to initialize {@link ContextsProjectPackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static ContextsProjectPackage init() {
    if (isInited) return (ContextsProjectPackage)EPackage.Registry.INSTANCE.getEPackage(ContextsProjectPackage.eNS_URI);

    // Obtain or create and register package
    ContextsProjectPackageImpl theContextsProjectPackage = (ContextsProjectPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ContextsProjectPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ContextsProjectPackageImpl());

    isInited = true;

    // Create package meta-data objects
    theContextsProjectPackage.createPackageContents();

    // Initialize created meta-data
    theContextsProjectPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theContextsProjectPackage.freeze();

  
    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(ContextsProjectPackage.eNS_URI, theContextsProjectPackage);
    return theContextsProjectPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getContextsProject() {
    return contextsProjectEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getContextsProject_ParentProject() {
    return (EAttribute)contextsProjectEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getContextsProject_Administrators() {
    return (EReference)contextsProjectEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getContextsProject_ContextReferences() {
    return (EReference)contextsProjectEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getAdministrator() {
    return administratorEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getAdministrator_Id() {
    return (EAttribute)administratorEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getContextReference() {
    return contextReferenceEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getContextReference_Uri() {
    return (EAttribute)contextReferenceEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getContextReference_Id() {
    return (EAttribute)contextReferenceEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getContextReference_Name() {
    return (EAttribute)contextReferenceEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ContextsProjectFactory getContextsProjectFactory() {
    return (ContextsProjectFactory)getEFactoryInstance();
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
    contextsProjectEClass = createEClass(CONTEXTS_PROJECT);
    createEAttribute(contextsProjectEClass, CONTEXTS_PROJECT__PARENT_PROJECT);
    createEReference(contextsProjectEClass, CONTEXTS_PROJECT__ADMINISTRATORS);
    createEReference(contextsProjectEClass, CONTEXTS_PROJECT__CONTEXT_REFERENCES);

    administratorEClass = createEClass(ADMINISTRATOR);
    createEAttribute(administratorEClass, ADMINISTRATOR__ID);

    contextReferenceEClass = createEClass(CONTEXT_REFERENCE);
    createEAttribute(contextReferenceEClass, CONTEXT_REFERENCE__URI);
    createEAttribute(contextReferenceEClass, CONTEXT_REFERENCE__ID);
    createEAttribute(contextReferenceEClass, CONTEXT_REFERENCE__NAME);
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
    initEClass(contextsProjectEClass, ContextsProject.class, "ContextsProject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getContextsProject_ParentProject(), ecorePackage.getEString(), "parentProject", null, 0, 1, ContextsProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getContextsProject_Administrators(), this.getAdministrator(), null, "administrators", null, 1, -1, ContextsProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getContextsProject_ContextReferences(), this.getContextReference(), null, "contextReferences", null, 1, -1, ContextsProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(administratorEClass, Administrator.class, "Administrator", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getAdministrator_Id(), ecorePackage.getEString(), "id", null, 0, 1, Administrator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(contextReferenceEClass, ContextReference.class, "ContextReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getContextReference_Uri(), ecorePackage.getEString(), "uri", null, 0, 1, ContextReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getContextReference_Id(), ecorePackage.getEString(), "id", null, 0, 1, ContextReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getContextReference_Name(), ecorePackage.getEString(), "name", null, 0, 1, ContextReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    // Create resource
    createResource(eNS_URI);
  }

} //ContextsProjectPackageImpl
