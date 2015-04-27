/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.model.baseline;

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
 * @see com.thalesgroup.orchestra.framework.model.baseline.BaselineFactory
 * @model kind="package"
 * @generated
 */
public interface BaselinePackage extends EPackage {
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "baseline";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://com.thalesgroup.orchestra/framework/4_2_2/baseline";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "baseline";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  BaselinePackage eINSTANCE = com.thalesgroup.orchestra.framework.model.baseline.impl.BaselinePackageImpl.init();

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.model.baseline.impl.RepositoryImpl <em>Repository</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.model.baseline.impl.RepositoryImpl
   * @see com.thalesgroup.orchestra.framework.model.baseline.impl.BaselinePackageImpl#getRepository()
   * @generated
   */
  int REPOSITORY = 0;

  /**
   * The feature id for the '<em><b>Baselines</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REPOSITORY__BASELINES = 0;

  /**
   * The number of structural features of the '<em>Repository</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REPOSITORY_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link com.thalesgroup.orchestra.framework.model.baseline.impl.BaselineImpl <em>Baseline</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.thalesgroup.orchestra.framework.model.baseline.impl.BaselineImpl
   * @see com.thalesgroup.orchestra.framework.model.baseline.impl.BaselinePackageImpl#getBaseline()
   * @generated
   */
  int BASELINE = 1;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BASELINE__NAME = 0;

  /**
   * The feature id for the '<em><b>Project Relative Path</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BASELINE__PROJECT_RELATIVE_PATH = 1;

  /**
   * The feature id for the '<em><b>Context Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BASELINE__CONTEXT_ID = 2;

  /**
   * The feature id for the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BASELINE__DESCRIPTION = 3;

  /**
   * The number of structural features of the '<em>Baseline</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int BASELINE_FEATURE_COUNT = 4;


  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.model.baseline.Repository <em>Repository</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Repository</em>'.
   * @see com.thalesgroup.orchestra.framework.model.baseline.Repository
   * @generated
   */
  EClass getRepository();

  /**
   * Returns the meta object for the containment reference list '{@link com.thalesgroup.orchestra.framework.model.baseline.Repository#getBaselines <em>Baselines</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Baselines</em>'.
   * @see com.thalesgroup.orchestra.framework.model.baseline.Repository#getBaselines()
   * @see #getRepository()
   * @generated
   */
  EReference getRepository_Baselines();

  /**
   * Returns the meta object for class '{@link com.thalesgroup.orchestra.framework.model.baseline.Baseline <em>Baseline</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Baseline</em>'.
   * @see com.thalesgroup.orchestra.framework.model.baseline.Baseline
   * @generated
   */
  EClass getBaseline();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.model.baseline.Baseline#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see com.thalesgroup.orchestra.framework.model.baseline.Baseline#getName()
   * @see #getBaseline()
   * @generated
   */
  EAttribute getBaseline_Name();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.model.baseline.Baseline#getProjectRelativePath <em>Project Relative Path</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Project Relative Path</em>'.
   * @see com.thalesgroup.orchestra.framework.model.baseline.Baseline#getProjectRelativePath()
   * @see #getBaseline()
   * @generated
   */
  EAttribute getBaseline_ProjectRelativePath();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.model.baseline.Baseline#getContextId <em>Context Id</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Context Id</em>'.
   * @see com.thalesgroup.orchestra.framework.model.baseline.Baseline#getContextId()
   * @see #getBaseline()
   * @generated
   */
  EAttribute getBaseline_ContextId();

  /**
   * Returns the meta object for the attribute '{@link com.thalesgroup.orchestra.framework.model.baseline.Baseline#getDescription <em>Description</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Description</em>'.
   * @see com.thalesgroup.orchestra.framework.model.baseline.Baseline#getDescription()
   * @see #getBaseline()
   * @generated
   */
  EAttribute getBaseline_Description();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  BaselineFactory getBaselineFactory();

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
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.model.baseline.impl.RepositoryImpl <em>Repository</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.model.baseline.impl.RepositoryImpl
     * @see com.thalesgroup.orchestra.framework.model.baseline.impl.BaselinePackageImpl#getRepository()
     * @generated
     */
    EClass REPOSITORY = eINSTANCE.getRepository();

    /**
     * The meta object literal for the '<em><b>Baselines</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference REPOSITORY__BASELINES = eINSTANCE.getRepository_Baselines();

    /**
     * The meta object literal for the '{@link com.thalesgroup.orchestra.framework.model.baseline.impl.BaselineImpl <em>Baseline</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.thalesgroup.orchestra.framework.model.baseline.impl.BaselineImpl
     * @see com.thalesgroup.orchestra.framework.model.baseline.impl.BaselinePackageImpl#getBaseline()
     * @generated
     */
    EClass BASELINE = eINSTANCE.getBaseline();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute BASELINE__NAME = eINSTANCE.getBaseline_Name();

    /**
     * The meta object literal for the '<em><b>Project Relative Path</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute BASELINE__PROJECT_RELATIVE_PATH = eINSTANCE.getBaseline_ProjectRelativePath();

    /**
     * The meta object literal for the '<em><b>Context Id</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute BASELINE__CONTEXT_ID = eINSTANCE.getBaseline_ContextId();

    /**
     * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute BASELINE__DESCRIPTION = eINSTANCE.getBaseline_Description();

  }

} //BaselinePackage
