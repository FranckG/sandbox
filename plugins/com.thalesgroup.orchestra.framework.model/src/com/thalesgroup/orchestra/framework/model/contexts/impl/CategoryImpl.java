/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.model.contexts.impl;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.emf.ecore.util.InternalEList;

import com.thalesgroup.orchestra.framework.model.contexts.Category;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.contexts.ContributedElement;
import com.thalesgroup.orchestra.framework.model.contexts.ModelElement;
import com.thalesgroup.orchestra.framework.model.contexts.ReferenceableElement;
import com.thalesgroup.orchestra.framework.model.contexts.ReferencingElement;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;
import com.thalesgroup.orchestra.framework.model.contexts.util.ContextsValidator;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Category</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.impl.CategoryImpl#getSuperCategory <em>Super Category</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.impl.CategoryImpl#getVariables <em>Variables</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.impl.CategoryImpl#getCategories <em>Categories</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CategoryImpl extends NamedElementImpl implements Category {
	/**
   * The cached value of the '{@link #getSuperCategory() <em>Super Category</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSuperCategory()
   * @generated
   * @ordered
   */
  protected Category superCategory;

  /**
   * The cached value of the '{@link #getVariables() <em>Variables</em>}' containment reference list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getVariables()
   * @generated
   * @ordered
   */
	protected EList<Variable> variables;

	/**
   * The cached value of the '{@link #getCategories() <em>Categories</em>}' containment reference list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getCategories()
   * @generated
   * @ordered
   */
	protected EList<Category> categories;

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected CategoryImpl() {
    super();
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
	protected EClass eStaticClass() {
    return ContextsPackage.Literals.CATEGORY;
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Category getSuperCategory() {
    if (superCategory != null && superCategory.eIsProxy()) {
      InternalEObject oldSuperCategory = (InternalEObject)superCategory;
      superCategory = (Category)eResolveProxy(oldSuperCategory);
      if (superCategory != oldSuperCategory) {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, ContextsPackage.CATEGORY__SUPER_CATEGORY, oldSuperCategory, superCategory));
      }
    }
    return superCategory;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Category basicGetSuperCategory() {
    return superCategory;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setSuperCategory(Category newSuperCategory) {
    Category oldSuperCategory = superCategory;
    superCategory = newSuperCategory;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContextsPackage.CATEGORY__SUPER_CATEGORY, oldSuperCategory, superCategory));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EList<Variable> getVariables() {
    if (variables == null) {
      variables = new EObjectContainmentEList<Variable>(Variable.class, this, ContextsPackage.CATEGORY__VARIABLES);
    }
    return variables;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EList<Category> getCategories() {
    if (categories == null) {
      categories = new EObjectContainmentEList<Category>(Category.class, this, ContextsPackage.CATEGORY__CATEGORIES);
    }
    return categories;
  }

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@SuppressWarnings("unchecked")
	public boolean hasName(DiagnosticChain diagnostics, Map<?, ?> context) {
		if ("".equals(getName())) {
			if (diagnostics != null) {
				diagnostics.add
					(new BasicDiagnostic
						(Diagnostic.ERROR,
						 ContextsValidator.DIAGNOSTIC_SOURCE,
						 ContextsValidator.CATEGORY__HAS_NAME,
						 EcorePlugin.INSTANCE.getString("_UI_GenericInvariant_diagnostic", new Object[] { "hasName", EObjectValidator.getObjectLabel(this, (Map<Object, Object>) context) }),
						 new Object [] { this }));
			}
			return false;
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@SuppressWarnings("unchecked")
	public boolean hasNotVariablesWithSameName(DiagnosticChain diagnostics, Map<?, ?> context) {
		// -> specify the condition that violates the invariant
		// -> verify the details of the diagnostic, including severity and message
		// Ensure that you remove @generated or mark it @generated NOT
		Set<String> vars = new HashSet<String>(getVariables().size());
		boolean doubles = false;
		for (Variable var : getVariables()) {
			if (!vars.add(var.getName())) {
				doubles = true;
			}
		}
		if (doubles) {
			if (diagnostics != null) {
				diagnostics.add
					(new BasicDiagnostic
						(Diagnostic.ERROR,
						 ContextsValidator.DIAGNOSTIC_SOURCE,
						 ContextsValidator.CATEGORY__HAS_NOT_VARIABLES_WITH_SAME_NAME,
						 EcorePlugin.INSTANCE.getString("_UI_GenericInvariant_diagnostic", new Object[] { "hasNotVariablesWithSameName", EObjectValidator.getObjectLabel(this, (Map<Object, Object>) context) }),
						 new Object [] { this }));
			}
			return false;
		}
		return true;
	}

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case ContextsPackage.CATEGORY__VARIABLES:
        return ((InternalEList<?>)getVariables()).basicRemove(otherEnd, msgs);
      case ContextsPackage.CATEGORY__CATEGORIES:
        return ((InternalEList<?>)getCategories()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case ContextsPackage.CATEGORY__SUPER_CATEGORY:
        if (resolve) return getSuperCategory();
        return basicGetSuperCategory();
      case ContextsPackage.CATEGORY__VARIABLES:
        return getVariables();
      case ContextsPackage.CATEGORY__CATEGORIES:
        return getCategories();
    }
    return super.eGet(featureID, resolve, coreType);
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
    switch (featureID) {
      case ContextsPackage.CATEGORY__SUPER_CATEGORY:
        setSuperCategory((Category)newValue);
        return;
      case ContextsPackage.CATEGORY__VARIABLES:
        getVariables().clear();
        getVariables().addAll((Collection<? extends Variable>)newValue);
        return;
      case ContextsPackage.CATEGORY__CATEGORIES:
        getCategories().clear();
        getCategories().addAll((Collection<? extends Category>)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
	public void eUnset(int featureID) {
    switch (featureID) {
      case ContextsPackage.CATEGORY__SUPER_CATEGORY:
        setSuperCategory((Category)null);
        return;
      case ContextsPackage.CATEGORY__VARIABLES:
        getVariables().clear();
        return;
      case ContextsPackage.CATEGORY__CATEGORIES:
        getCategories().clear();
        return;
    }
    super.eUnset(featureID);
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
	public boolean eIsSet(int featureID) {
    switch (featureID) {
      case ContextsPackage.CATEGORY__SUPER_CATEGORY:
        return superCategory != null;
      case ContextsPackage.CATEGORY__VARIABLES:
        return variables != null && !variables.isEmpty();
      case ContextsPackage.CATEGORY__CATEGORIES:
        return categories != null && !categories.isEmpty();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
    if (baseClass == ContributedElement.class) {
      switch (derivedFeatureID) {
        case ContextsPackage.CATEGORY__SUPER_CATEGORY: return ContextsPackage.CONTRIBUTED_ELEMENT__SUPER_CATEGORY;
        default: return -1;
      }
    }
    return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
    if (baseClass == ContributedElement.class) {
      switch (baseFeatureID) {
        case ContextsPackage.CONTRIBUTED_ELEMENT__SUPER_CATEGORY: return ContextsPackage.CATEGORY__SUPER_CATEGORY;
        default: return -1;
      }
    }
    return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
  }

} //CategoryImpl
