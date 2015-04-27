/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.contextsproject.util;

import com.thalesgroup.orchestra.framework.contextsproject.*;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import com.thalesgroup.orchestra.framework.contextsproject.Administrator;
import com.thalesgroup.orchestra.framework.contextsproject.ContextReference;
import com.thalesgroup.orchestra.framework.contextsproject.ContextsProject;
import com.thalesgroup.orchestra.framework.contextsproject.ContextsProjectPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see com.thalesgroup.orchestra.framework.contextsproject.ContextsProjectPackage
 * @generated
 */
public class ContextsProjectSwitch<T> extends Switch<T> {
  /**
   * The cached model package
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static ContextsProjectPackage modelPackage;

  /**
   * Creates an instance of the switch.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ContextsProjectSwitch() {
    if (modelPackage == null) {
      modelPackage = ContextsProjectPackage.eINSTANCE;
    }
  }

  /**
   * Checks whether this is a switch for the given package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @parameter ePackage the package in question.
   * @return whether this is a switch for the given package.
   * @generated
   */
  @Override
  protected boolean isSwitchFor(EPackage ePackage) {
    return ePackage == modelPackage;
  }

  /**
   * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the first non-null result returned by a <code>caseXXX</code> call.
   * @generated
   */
  @Override
  protected T doSwitch(int classifierID, EObject theEObject) {
    switch (classifierID) {
      case ContextsProjectPackage.CONTEXTS_PROJECT: {
        ContextsProject contextsProject = (ContextsProject)theEObject;
        T result = caseContextsProject(contextsProject);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ContextsProjectPackage.ADMINISTRATOR: {
        Administrator administrator = (Administrator)theEObject;
        T result = caseAdministrator(administrator);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ContextsProjectPackage.CONTEXT_REFERENCE: {
        ContextReference contextReference = (ContextReference)theEObject;
        T result = caseContextReference(contextReference);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      default: return defaultCase(theEObject);
    }
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Contexts Project</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Contexts Project</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseContextsProject(ContextsProject object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Administrator</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Administrator</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAdministrator(Administrator object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Context Reference</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Context Reference</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseContextReference(ContextReference object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch, but this is the last case anyway.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject)
   * @generated
   */
  @Override
  public T defaultCase(EObject object) {
    return null;
  }

} //ContextsProjectSwitch
