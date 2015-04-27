/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.model.contexts.util;

import com.thalesgroup.orchestra.framework.model.contexts.*;
import java.util.List;

import java.util.Map;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Category;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.contexts.ContributedElement;
import com.thalesgroup.orchestra.framework.model.contexts.FileVariable;
import com.thalesgroup.orchestra.framework.model.contexts.FolderVariable;
import com.thalesgroup.orchestra.framework.model.contexts.InstallationCategory;
import com.thalesgroup.orchestra.framework.model.contexts.ModelElement;
import com.thalesgroup.orchestra.framework.model.contexts.NamedElement;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingVariable;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingVariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.PendingElementsCategory;
import com.thalesgroup.orchestra.framework.model.contexts.ReferenceableElement;
import com.thalesgroup.orchestra.framework.model.contexts.ReferencingElement;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance hierarchy. It supports the call {@link #doSwitch(EObject) doSwitch(object)} to invoke
 * the <code>caseXXX</code> method for each class of the model, starting with the actual class of the object and proceeding up the inheritance hierarchy until a
 * non-null result is returned, which is the result of the switch. <!-- end-user-doc -->
 * @see com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage
 * @generated
 */
public class ContextsSwitch<T> extends Switch<T> {
  /**
   * The cached model package
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  protected static ContextsPackage modelPackage;

  /**
   * Creates an instance of the switch.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public ContextsSwitch() {
    if (modelPackage == null) {
      modelPackage = ContextsPackage.eINSTANCE;
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
   * <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * @return the first non-null result returned by a <code>caseXXX</code> call.
   * @generated
   */
  @Override
  protected T doSwitch(int classifierID, EObject theEObject) {
    switch (classifierID) {
      case ContextsPackage.CATEGORY: {
        Category category = (Category)theEObject;
        T result = caseCategory(category);
        if (result == null) result = caseNamedElement(category);
        if (result == null) result = caseContributedElement(category);
        if (result == null) result = caseModelElement(category);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ContextsPackage.CONTEXT: {
        Context context = (Context)theEObject;
        T result = caseContext(context);
        if (result == null) result = caseNamedElement(context);
        if (result == null) result = caseModelElement(context);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ContextsPackage.VARIABLE: {
        Variable variable = (Variable)theEObject;
        T result = caseVariable(variable);
        if (result == null) result = caseAbstractVariable(variable);
        if (result == null) result = caseContributedElement(variable);
        if (result == null) result = caseNamedElement(variable);
        if (result == null) result = caseModelElement(variable);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ContextsPackage.FILE_VARIABLE: {
        FileVariable fileVariable = (FileVariable)theEObject;
        T result = caseFileVariable(fileVariable);
        if (result == null) result = caseVariable(fileVariable);
        if (result == null) result = caseAbstractVariable(fileVariable);
        if (result == null) result = caseContributedElement(fileVariable);
        if (result == null) result = caseNamedElement(fileVariable);
        if (result == null) result = caseModelElement(fileVariable);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ContextsPackage.FOLDER_VARIABLE: {
        FolderVariable folderVariable = (FolderVariable)theEObject;
        T result = caseFolderVariable(folderVariable);
        if (result == null) result = caseVariable(folderVariable);
        if (result == null) result = caseAbstractVariable(folderVariable);
        if (result == null) result = caseContributedElement(folderVariable);
        if (result == null) result = caseNamedElement(folderVariable);
        if (result == null) result = caseModelElement(folderVariable);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ContextsPackage.MODEL_ELEMENT: {
        ModelElement modelElement = (ModelElement)theEObject;
        T result = caseModelElement(modelElement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ContextsPackage.NAMED_ELEMENT: {
        NamedElement namedElement = (NamedElement)theEObject;
        T result = caseNamedElement(namedElement);
        if (result == null) result = caseModelElement(namedElement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ContextsPackage.ABSTRACT_VARIABLE: {
        AbstractVariable abstractVariable = (AbstractVariable)theEObject;
        T result = caseAbstractVariable(abstractVariable);
        if (result == null) result = caseNamedElement(abstractVariable);
        if (result == null) result = caseModelElement(abstractVariable);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ContextsPackage.OVERRIDING_VARIABLE: {
        OverridingVariable overridingVariable = (OverridingVariable)theEObject;
        T result = caseOverridingVariable(overridingVariable);
        if (result == null) result = caseAbstractVariable(overridingVariable);
        if (result == null) result = caseNamedElement(overridingVariable);
        if (result == null) result = caseModelElement(overridingVariable);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ContextsPackage.VARIABLE_VALUE: {
        VariableValue variableValue = (VariableValue)theEObject;
        T result = caseVariableValue(variableValue);
        if (result == null) result = caseModelElement(variableValue);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ContextsPackage.PENDING_ELEMENTS_CATEGORY: {
        PendingElementsCategory pendingElementsCategory = (PendingElementsCategory)theEObject;
        T result = casePendingElementsCategory(pendingElementsCategory);
        if (result == null) result = caseCategory(pendingElementsCategory);
        if (result == null) result = caseNamedElement(pendingElementsCategory);
        if (result == null) result = caseContributedElement(pendingElementsCategory);
        if (result == null) result = caseModelElement(pendingElementsCategory);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ContextsPackage.OVERRIDING_VARIABLE_VALUE: {
        OverridingVariableValue overridingVariableValue = (OverridingVariableValue)theEObject;
        T result = caseOverridingVariableValue(overridingVariableValue);
        if (result == null) result = caseVariableValue(overridingVariableValue);
        if (result == null) result = caseModelElement(overridingVariableValue);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ContextsPackage.OVERRIDING_ENVIRONMENT_VARIABLE_VALUE: {
        OverridingEnvironmentVariableValue overridingEnvironmentVariableValue = (OverridingEnvironmentVariableValue)theEObject;
        T result = caseOverridingEnvironmentVariableValue(overridingEnvironmentVariableValue);
        if (result == null) result = caseEnvironmentVariableValue(overridingEnvironmentVariableValue);
        if (result == null) result = caseOverridingVariableValue(overridingEnvironmentVariableValue);
        if (result == null) result = caseVariableValue(overridingEnvironmentVariableValue);
        if (result == null) result = caseModelElement(overridingEnvironmentVariableValue);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ContextsPackage.INSTALLATION_CATEGORY: {
        InstallationCategory installationCategory = (InstallationCategory)theEObject;
        T result = caseInstallationCategory(installationCategory);
        if (result == null) result = caseCategory(installationCategory);
        if (result == null) result = caseReferenceableElement(installationCategory);
        if (result == null) result = caseReferencingElement(installationCategory);
        if (result == null) result = caseNamedElement(installationCategory);
        if (result == null) result = caseContributedElement(installationCategory);
        if (result == null) result = caseModelElement(installationCategory);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ContextsPackage.CONTRIBUTED_ELEMENT: {
        ContributedElement contributedElement = (ContributedElement)theEObject;
        T result = caseContributedElement(contributedElement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ContextsPackage.REFERENCEABLE_ELEMENT: {
        ReferenceableElement referenceableElement = (ReferenceableElement)theEObject;
        T result = caseReferenceableElement(referenceableElement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ContextsPackage.REFERENCING_ELEMENT: {
        ReferencingElement referencingElement = (ReferencingElement)theEObject;
        T result = caseReferencingElement(referencingElement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ContextsPackage.ENVIRONMENT_VARIABLE_VALUE: {
        EnvironmentVariableValue environmentVariableValue = (EnvironmentVariableValue)theEObject;
        T result = caseEnvironmentVariableValue(environmentVariableValue);
        if (result == null) result = caseVariableValue(environmentVariableValue);
        if (result == null) result = caseModelElement(environmentVariableValue);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ContextsPackage.ENVIRONMENT_VARIABLE: {
        EnvironmentVariable environmentVariable = (EnvironmentVariable)theEObject;
        T result = caseEnvironmentVariable(environmentVariable);
        if (result == null) result = caseVariable(environmentVariable);
        if (result == null) result = caseAbstractVariable(environmentVariable);
        if (result == null) result = caseContributedElement(environmentVariable);
        if (result == null) result = caseNamedElement(environmentVariable);
        if (result == null) result = caseModelElement(environmentVariable);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case ContextsPackage.STRING_TO_STRING_MAP: {
        @SuppressWarnings("unchecked") Map.Entry<String, String> stringToStringMap = (Map.Entry<String, String>)theEObject;
        T result = caseStringToStringMap(stringToStringMap);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      default: return defaultCase(theEObject);
    }
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Category</em>'.
   * <!-- begin-user-doc --> This implementation returns null; returning a
   * non-null result will terminate the switch. <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Category</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseCategory(Category object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Context</em>'.
   * <!-- begin-user-doc --> This implementation returns null; returning a
   * non-null result will terminate the switch. <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Context</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseContext(Context object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Variable</em>'.
   * <!-- begin-user-doc --> This implementation returns null; returning a
   * non-null result will terminate the switch. <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Variable</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseVariable(Variable object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>File Variable</em>'.
   * <!-- begin-user-doc --> This implementation returns null;
   * returning a non-null result will terminate the switch. <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>File Variable</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseFileVariable(FileVariable object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Folder Variable</em>'.
   * <!-- begin-user-doc --> This implementation returns null;
   * returning a non-null result will terminate the switch. <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Folder Variable</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseFolderVariable(FolderVariable object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Model Element</em>'.
   * <!-- begin-user-doc --> This implementation returns null;
   * returning a non-null result will terminate the switch. <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Model Element</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseModelElement(ModelElement object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Named Element</em>'.
   * <!-- begin-user-doc --> This implementation returns null;
   * returning a non-null result will terminate the switch. <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Named Element</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseNamedElement(NamedElement object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Abstract Variable</em>'.
   * <!-- begin-user-doc --> This implementation returns null;
   * returning a non-null result will terminate the switch. <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Abstract Variable</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAbstractVariable(AbstractVariable object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Overriding Variable</em>'.
   * <!-- begin-user-doc --> This implementation returns null;
   * returning a non-null result will terminate the switch. <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Overriding Variable</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseOverridingVariable(OverridingVariable object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Variable Value</em>'.
   * <!-- begin-user-doc --> This implementation returns null;
   * returning a non-null result will terminate the switch. <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Variable Value</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseVariableValue(VariableValue object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Overriding Variable Value</em>'.
   * <!-- begin-user-doc --> This implementation returns
   * null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Overriding Variable Value</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseOverridingVariableValue(OverridingVariableValue object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Overriding Environment Variable Value</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Overriding Environment Variable Value</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseOverridingEnvironmentVariableValue(OverridingEnvironmentVariableValue object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Installation Category</em>'.
   * <!-- begin-user-doc --> This implementation returns null;
   * returning a non-null result will terminate the switch. <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Installation Category</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseInstallationCategory(InstallationCategory object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Pending Elements Category</em>'.
   * <!-- begin-user-doc --> This implementation returns
   * null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Pending Elements Category</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casePendingElementsCategory(PendingElementsCategory object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Contributed Element</em>'.
   * <!-- begin-user-doc --> This implementation returns null;
   * returning a non-null result will terminate the switch. <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Contributed Element</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseContributedElement(ContributedElement object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Referenceable Element</em>'.
   * <!-- begin-user-doc --> This implementation returns null;
   * returning a non-null result will terminate the switch. <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Referenceable Element</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseReferenceableElement(ReferenceableElement object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Referencing Element</em>'.
   * <!-- begin-user-doc --> This implementation returns null;
   * returning a non-null result will terminate the switch. <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Referencing Element</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseReferencingElement(ReferencingElement object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Environment Variable Value</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Environment Variable Value</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseEnvironmentVariableValue(EnvironmentVariableValue object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Environment Variable</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Environment Variable</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseEnvironmentVariable(EnvironmentVariable object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>String To String Map</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>String To String Map</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseStringToStringMap(Map.Entry<String, String> object) {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
   * <!-- begin-user-doc --> This implementation returns null; returning a
   * non-null result will terminate the switch, but this is the last case anyway. <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject)
   * @generated
   */
  @Override
  public T defaultCase(EObject object) {
    return null;
  }

} // ContextsSwitch
