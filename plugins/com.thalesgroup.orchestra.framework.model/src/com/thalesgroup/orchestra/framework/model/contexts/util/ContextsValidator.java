/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.model.contexts.util;

import java.util.Map;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.util.EObjectValidator;

import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Category;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.contexts.FileVariable;
import com.thalesgroup.orchestra.framework.model.contexts.FolderVariable;
import com.thalesgroup.orchestra.framework.model.contexts.InstallationCategory;
import com.thalesgroup.orchestra.framework.model.contexts.ModelElement;
import com.thalesgroup.orchestra.framework.model.contexts.NamedElement;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingVariable;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingVariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 * @see com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage
 * @generated
 */
public class ContextsValidator extends EObjectValidator {
	/**
   * The cached model package
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public static final ContextsValidator INSTANCE = new ContextsValidator();

	/**
   * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see org.eclipse.emf.common.util.Diagnostic#getSource()
   * @see org.eclipse.emf.common.util.Diagnostic#getCode()
   * @generated
   */
	public static final String DIAGNOSTIC_SOURCE = "com.thalesgroup.orchestra.framework.model.contexts";

	/**
   * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Has Name' of 'Category'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public static final int CATEGORY__HAS_NAME = 1;

	/**
   * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Has Not Variables With Same Name' of 'Category'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public static final int CATEGORY__HAS_NOT_VARIABLES_WITH_SAME_NAME = 2;

	/**
   * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Has Not Categories With Same Name' of 'Context'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public static final int CONTEXT__HAS_NOT_CATEGORIES_WITH_SAME_NAME = 3;

	/**
   * A constant with a fixed name that can be used as the base value for additional hand written constants.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 3;

	/**
   * A constant with a fixed name that can be used as the base value for additional hand written constants in a derived class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected static final int DIAGNOSTIC_CODE_COUNT = GENERATED_DIAGNOSTIC_CODE_COUNT;

	/**
   * Creates an instance of the switch.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public ContextsValidator() {
    super();
  }

	/**
   * Returns the package of this validator switch.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
	protected EPackage getEPackage() {
    return ContextsPackage.eINSTANCE;
  }

	/**
   * Calls <code>validateXXX</code> for the corresponding classifier of the model.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
	protected boolean validate(int classifierID, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
    switch (classifierID) {
      case ContextsPackage.CATEGORY:
        return validateCategory((Category)value, diagnostics, context);
      case ContextsPackage.CONTEXT:
        return validateContext((Context)value, diagnostics, context);
      case ContextsPackage.VARIABLE:
        return validateVariable((Variable)value, diagnostics, context);
      case ContextsPackage.FILE_VARIABLE:
        return validateFileVariable((FileVariable)value, diagnostics, context);
      case ContextsPackage.FOLDER_VARIABLE:
        return validateFolderVariable((FolderVariable)value, diagnostics, context);
      case ContextsPackage.MODEL_ELEMENT:
        return validateModelElement((ModelElement)value, diagnostics, context);
      case ContextsPackage.NAMED_ELEMENT:
        return validateNamedElement((NamedElement)value, diagnostics, context);
      case ContextsPackage.ABSTRACT_VARIABLE:
        return validateAbstractVariable((AbstractVariable)value, diagnostics, context);
      case ContextsPackage.OVERRIDING_VARIABLE:
        return validateOverridingVariable((OverridingVariable)value, diagnostics, context);
      case ContextsPackage.VARIABLE_VALUE:
        return validateVariableValue((VariableValue)value, diagnostics, context);
      case ContextsPackage.OVERRIDING_VARIABLE_VALUE:
        return validateOverridingVariableValue((OverridingVariableValue)value, diagnostics, context);
      case ContextsPackage.INSTALLATION_CATEGORY:
        return validateInstallationCategory((InstallationCategory)value, diagnostics, context);
      default:
        return true;
    }
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public boolean validateCategory(Category category, DiagnosticChain diagnostics, Map<Object, Object> context) {
    boolean result = validate_EveryMultiplicityConforms(category, diagnostics, context);
    if (result || diagnostics != null) result &= validate_EveryDataValueConforms(category, diagnostics, context);
    if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(category, diagnostics, context);
    if (result || diagnostics != null) result &= validate_EveryProxyResolves(category, diagnostics, context);
    if (result || diagnostics != null) result &= validate_UniqueID(category, diagnostics, context);
    if (result || diagnostics != null) result &= validate_EveryKeyUnique(category, diagnostics, context);
    if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(category, diagnostics, context);
    return result;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public boolean validateContext(Context context, DiagnosticChain diagnostics, Map<Object, Object> theContext) {
    boolean result = validate_EveryMultiplicityConforms(context, diagnostics, theContext);
    if (result || diagnostics != null) result &= validate_EveryDataValueConforms(context, diagnostics, theContext);
    if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(context, diagnostics, theContext);
    if (result || diagnostics != null) result &= validate_EveryProxyResolves(context, diagnostics, theContext);
    if (result || diagnostics != null) result &= validate_UniqueID(context, diagnostics, theContext);
    if (result || diagnostics != null) result &= validate_EveryKeyUnique(context, diagnostics, theContext);
    if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(context, diagnostics, theContext);
    return result;
  }

	public static boolean validateObject(EObject eObject) {
		Diagnostic diagnostic = Diagnostician.INSTANCE.validate(eObject);
		return diagnostic.getSeverity() == Diagnostic.OK;
	}
	
	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public boolean validateVariable(Variable variable, DiagnosticChain diagnostics, Map<Object, Object> context) {
    return validate_EveryDefaultConstraint(variable, diagnostics, context);
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public boolean validateFileVariable(FileVariable fileVariable, DiagnosticChain diagnostics, Map<Object, Object> context) {
    return validate_EveryDefaultConstraint(fileVariable, diagnostics, context);
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public boolean validateFolderVariable(FolderVariable folderVariable, DiagnosticChain diagnostics, Map<Object, Object> context) {
    return validate_EveryDefaultConstraint(folderVariable, diagnostics, context);
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean validateModelElement(ModelElement modelElement, DiagnosticChain diagnostics, Map<Object, Object> context) {
    return validate_EveryDefaultConstraint(modelElement, diagnostics, context);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean validateNamedElement(NamedElement namedElement, DiagnosticChain diagnostics, Map<Object, Object> context) {
    return validate_EveryDefaultConstraint(namedElement, diagnostics, context);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean validateAbstractVariable(AbstractVariable abstractVariable, DiagnosticChain diagnostics, Map<Object, Object> context) {
    return validate_EveryDefaultConstraint(abstractVariable, diagnostics, context);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean validateOverridingVariable(OverridingVariable overridingVariable, DiagnosticChain diagnostics, Map<Object, Object> context) {
    return validate_EveryDefaultConstraint(overridingVariable, diagnostics, context);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean validateVariableValue(VariableValue variableValue, DiagnosticChain diagnostics, Map<Object, Object> context) {
    return validate_EveryDefaultConstraint(variableValue, diagnostics, context);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean validateOverridingVariableValue(OverridingVariableValue overridingVariableValue, DiagnosticChain diagnostics, Map<Object, Object> context) {
    return validate_EveryDefaultConstraint(overridingVariableValue, diagnostics, context);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean validateInstallationCategory(InstallationCategory installationCategory, DiagnosticChain diagnostics, Map<Object, Object> context) {
    boolean result = validate_EveryMultiplicityConforms(installationCategory, diagnostics, context);
    if (result || diagnostics != null) result &= validate_EveryDataValueConforms(installationCategory, diagnostics, context);
    if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(installationCategory, diagnostics, context);
    if (result || diagnostics != null) result &= validate_EveryProxyResolves(installationCategory, diagnostics, context);
    if (result || diagnostics != null) result &= validate_UniqueID(installationCategory, diagnostics, context);
    if (result || diagnostics != null) result &= validate_EveryKeyUnique(installationCategory, diagnostics, context);
    if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(installationCategory, diagnostics, context);
    return result;
  }

  /**
	 * Returns the resource locator that will be used to fetch messages for this validator's diagnostics.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public ResourceLocator getResourceLocator() {
		// TODO
		// Specialize this to return a resource locator for messages specific to this validator.
		// Ensure that you remove @generated or mark it @generated NOT
		return super.getEcoreResourceLocator();
	}

} //ContextsValidator
