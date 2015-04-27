/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.contextsproject;

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
 * @see com.thalesgroup.orchestra.framework.contextsproject.ContextsProjectFactory
 * @model kind="package"
 * @generated
 */
public interface ContextsProjectPackage extends EPackage {
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "contextsproject";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://com.thalesgroup.orchestra/framework/4_0_13/contextsproject";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "contextsproject";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  ContextsProjectPackage eINSTANCE = com.thalesgroup.orchestra.framework.contextsproject.impl.ContextsProjectPackageImpl.init();

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.contextsproject.impl.ContextsProjectImpl <em>Contexts Project</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.contextsproject.impl.ContextsProjectImpl
   * @see com.thalesgroup.orchestra.framework.contextsproject.impl.ContextsProjectPackageImpl#getContextsProject()
   * @generated
   */
  int CONTEXTS_PROJECT = 0;

  /**
   * The feature id for the '<em><b>Parent Project</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTEXTS_PROJECT__PARENT_PROJECT = 0;

  /**
   * The feature id for the '<em><b>Administrators</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTEXTS_PROJECT__ADMINISTRATORS = 1;

  /**
   * The feature id for the '<em><b>Context References</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTEXTS_PROJECT__CONTEXT_REFERENCES = 2;

  /**
   * The number of structural features of the '<em>Contexts Project</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTEXTS_PROJECT_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.contextsproject.impl.AdministratorImpl <em>Administrator</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.contextsproject.impl.AdministratorImpl
   * @see com.thalesgroup.orchestra.framework.contextsproject.impl.ContextsProjectPackageImpl#getAdministrator()
   * @generated
   */
  int ADMINISTRATOR = 1;

  /**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ADMINISTRATOR__ID = 0;

  /**
   * The number of structural features of the '<em>Administrator</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ADMINISTRATOR_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.contextsproject.impl.ContextReferenceImpl <em>Context Reference</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.contextsproject.impl.ContextReferenceImpl
   * @see com.thalesgroup.orchestra.framework.contextsproject.impl.ContextsProjectPackageImpl#getContextReference()
   * @generated
   */
  int CONTEXT_REFERENCE = 2;

  /**
   * The feature id for the '<em><b>Uri</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTEXT_REFERENCE__URI = 0;

  /**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTEXT_REFERENCE__ID = 1;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTEXT_REFERENCE__NAME = 2;

  /**
   * The number of structural features of the '<em>Context Reference</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CONTEXT_REFERENCE_FEATURE_COUNT = 3;


  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.contextsproject.ContextsProject <em>Contexts Project</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Contexts Project</em>'.
   * @see com.thalesgroup.orchestra.framework.contextsproject.ContextsProject
   * @generated
   */
  EClass getContextsProject();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.contextsproject.ContextsProject#getParentProject <em>Parent Project</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Parent Project</em>'.
   * @see com.thalesgroup.orchestra.framework.contextsproject.ContextsProject#getParentProject()
   * @see #getContextsProject()
   * @generated
   */
  EAttribute getContextsProject_ParentProject();

  /**
   * Returns the meta object for the containment reference list '{@link com.thalesgroup.orchestra.framework.contextsproject.ContextsProject#getAdministrators <em>Administrators</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Administrators</em>'.
   * @see com.thalesgroup.orchestra.framework.contextsproject.ContextsProject#getAdministrators()
   * @see #getContextsProject()
   * @generated
   */
  EReference getContextsProject_Administrators();

  /**
   * Returns the meta object for the containment reference list '{@link com.thalesgroup.orchestra.framework.contextsproject.ContextsProject#getContextReferences <em>Context References</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Context References</em>'.
   * @see com.thalesgroup.orchestra.framework.contextsproject.ContextsProject#getContextReferences()
   * @see #getContextsProject()
   * @generated
   */
  EReference getContextsProject_ContextReferences();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.contextsproject.Administrator <em>Administrator</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Administrator</em>'.
   * @see com.thalesgroup.orchestra.framework.contextsproject.Administrator
   * @generated
   */
  EClass getAdministrator();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.contextsproject.Administrator#getId <em>Id</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Id</em>'.
   * @see com.thalesgroup.orchestra.framework.contextsproject.Administrator#getId()
   * @see #getAdministrator()
   * @generated
   */
  EAttribute getAdministrator_Id();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.contextsproject.ContextReference <em>Context Reference</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Context Reference</em>'.
   * @see com.thalesgroup.orchestra.framework.contextsproject.ContextReference
   * @generated
   */
  EClass getContextReference();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.contextsproject.ContextReference#getUri <em>Uri</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Uri</em>'.
   * @see com.thalesgroup.orchestra.framework.contextsproject.ContextReference#getUri()
   * @see #getContextReference()
   * @generated
   */
  EAttribute getContextReference_Uri();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.contextsproject.ContextReference#getId <em>Id</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Id</em>'.
   * @see com.thalesgroup.orchestra.framework.contextsproject.ContextReference#getId()
   * @see #getContextReference()
   * @generated
   */
  EAttribute getContextReference_Id();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.contextsproject.ContextReference#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see com.thalesgroup.orchestra.framework.contextsproject.ContextReference#getName()
   * @see #getContextReference()
   * @generated
   */
  EAttribute getContextReference_Name();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  ContextsProjectFactory getContextsProjectFactory();

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
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.contextsproject.impl.ContextsProjectImpl <em>Contexts Project</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.contextsproject.impl.ContextsProjectImpl
     * @see com.thalesgroup.orchestra.framework.contextsproject.impl.ContextsProjectPackageImpl#getContextsProject()
     * @generated
     */
    EClass CONTEXTS_PROJECT = eINSTANCE.getContextsProject();

    /**
     * The meta object literal for the '<em><b>Parent Project</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CONTEXTS_PROJECT__PARENT_PROJECT = eINSTANCE.getContextsProject_ParentProject();

    /**
     * The meta object literal for the '<em><b>Administrators</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CONTEXTS_PROJECT__ADMINISTRATORS = eINSTANCE.getContextsProject_Administrators();

    /**
     * The meta object literal for the '<em><b>Context References</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CONTEXTS_PROJECT__CONTEXT_REFERENCES = eINSTANCE.getContextsProject_ContextReferences();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.contextsproject.impl.AdministratorImpl <em>Administrator</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.contextsproject.impl.AdministratorImpl
     * @see com.thalesgroup.orchestra.framework.contextsproject.impl.ContextsProjectPackageImpl#getAdministrator()
     * @generated
     */
    EClass ADMINISTRATOR = eINSTANCE.getAdministrator();

    /**
     * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ADMINISTRATOR__ID = eINSTANCE.getAdministrator_Id();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.contextsproject.impl.ContextReferenceImpl <em>Context Reference</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.contextsproject.impl.ContextReferenceImpl
     * @see com.thalesgroup.orchestra.framework.contextsproject.impl.ContextsProjectPackageImpl#getContextReference()
     * @generated
     */
    EClass CONTEXT_REFERENCE = eINSTANCE.getContextReference();

    /**
     * The meta object literal for the '<em><b>Uri</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CONTEXT_REFERENCE__URI = eINSTANCE.getContextReference_Uri();

    /**
     * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CONTEXT_REFERENCE__ID = eINSTANCE.getContextReference_Id();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CONTEXT_REFERENCE__NAME = eINSTANCE.getContextReference_Name();

  }

} //ContextsProjectPackage
