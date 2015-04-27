/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.model.contexts.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
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
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.contexts.InstallationCategory;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;
import com.thalesgroup.orchestra.framework.model.contexts.util.ContextsResourceImpl;
import com.thalesgroup.orchestra.framework.model.contexts.util.ContextsValidator;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Context</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.impl.ContextImpl#getCategories <em>Categories</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.impl.ContextImpl#getSuperContext <em>Super Context</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.impl.ContextImpl#getTransientCategories <em>Transient Categories</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.impl.ContextImpl#getSuperCategoryVariables <em>Super Category Variables</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.impl.ContextImpl#getOverridingVariables <em>Overriding Variables</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.impl.ContextImpl#getSuperCategoryCategories <em>Super Category Categories</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.impl.ContextImpl#getSelectedVersions <em>Selected Versions</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.impl.ContextImpl#getCurrentVersions <em>Current Versions</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.model.contexts.impl.ContextImpl#getDescription <em>Description</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ContextImpl extends NamedElementImpl implements Context {
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
   * The cached value of the '{@link #getSuperContext() <em>Super Context</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSuperContext()
   * @generated
   * @ordered
   */
  protected Context superContext;

  /**
   * The cached value of the '{@link #getTransientCategories() <em>Transient Categories</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTransientCategories()
   * @generated
   * @ordered
   */
  protected EList<Category> transientCategories;

  /**
   * The cached value of the '{@link #getSuperCategoryVariables() <em>Super Category Variables</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSuperCategoryVariables()
   * @generated
   * @ordered
   */
  protected EList<Variable> superCategoryVariables;

  /**
   * The cached value of the '{@link #getOverridingVariables() <em>Overriding Variables</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOverridingVariables()
   * @generated
   * @ordered
   */
  protected EList<OverridingVariable> overridingVariables;

  /**
   * The cached value of the '{@link #getSuperCategoryCategories() <em>Super Category Categories</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSuperCategoryCategories()
   * @generated
   * @ordered
   */
  protected EList<Category> superCategoryCategories;

  /**
   * The cached value of the '{@link #getSelectedVersions() <em>Selected Versions</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSelectedVersions()
   * @generated
   * @ordered
   */
  protected EList<InstallationCategory> selectedVersions;

  /**
   * The cached value of the '{@link #getCurrentVersions() <em>Current Versions</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCurrentVersions()
   * @generated
   * @ordered
   */
  protected EList<InstallationCategory> currentVersions;

  /**
   * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDescription()
   * @generated
   * @ordered
   */
  protected static final String DESCRIPTION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDescription()
   * @generated
   * @ordered
   */
  protected String description = DESCRIPTION_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected ContextImpl() {
    super();
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	@Override
	protected EClass eStaticClass() {
    return ContextsPackage.Literals.CONTEXT;
  }

	/**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EList<Category> getCategories() {
    if (categories == null) {
      categories = new EObjectContainmentEList<Category>(Category.class, this, ContextsPackage.CONTEXT__CATEGORIES);
    }
    return categories;
  }

	/**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Context getSuperContext() {
    if (superContext != null && superContext.eIsProxy()) {
      InternalEObject oldSuperContext = (InternalEObject)superContext;
      superContext = (Context)eResolveProxy(oldSuperContext);
      if (superContext != oldSuperContext) {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, ContextsPackage.CONTEXT__SUPER_CONTEXT, oldSuperContext, superContext));
      }
    }
    return superContext;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Context basicGetSuperContext() {
    return superContext;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setSuperContext(Context newSuperContext) {
    Context oldSuperContext = superContext;
    superContext = newSuperContext;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContextsPackage.CONTEXT__SUPER_CONTEXT, oldSuperContext, superContext));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Category> getTransientCategories() {
    if (transientCategories == null) {
      transientCategories = new EObjectContainmentEList<Category>(Category.class, this, ContextsPackage.CONTEXT__TRANSIENT_CATEGORIES);
    }
    return transientCategories;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Variable> getSuperCategoryVariables() {
    if (superCategoryVariables == null) {
      superCategoryVariables = new EObjectContainmentEList<Variable>(Variable.class, this, ContextsPackage.CONTEXT__SUPER_CATEGORY_VARIABLES);
    }
    return superCategoryVariables;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<OverridingVariable> getOverridingVariables() {
    if (overridingVariables == null) {
      overridingVariables = new EObjectContainmentEList<OverridingVariable>(OverridingVariable.class, this, ContextsPackage.CONTEXT__OVERRIDING_VARIABLES);
    }
    return overridingVariables;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Category> getSuperCategoryCategories() {
    if (superCategoryCategories == null) {
      superCategoryCategories = new EObjectContainmentEList<Category>(Category.class, this, ContextsPackage.CONTEXT__SUPER_CATEGORY_CATEGORIES);
    }
    return superCategoryCategories;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<InstallationCategory> getSelectedVersions() {
    if (selectedVersions == null) {
      selectedVersions = new EObjectContainmentEList<InstallationCategory>(InstallationCategory.class, this, ContextsPackage.CONTEXT__SELECTED_VERSIONS);
    }
    return selectedVersions;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<InstallationCategory> getCurrentVersions() {
    if (currentVersions == null) {
      currentVersions = new EObjectContainmentEList<InstallationCategory>(InstallationCategory.class, this, ContextsPackage.CONTEXT__CURRENT_VERSIONS);
    }
    return currentVersions;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getDescription() {
    return description;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setDescription(String newDescription) {
    String oldDescription = description;
    description = newDescription;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContextsPackage.CONTEXT__DESCRIPTION, oldDescription, description));
  }

  /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@SuppressWarnings("unchecked")
	public boolean hasNotCategoriesWithSameName(DiagnosticChain diagnostics, Map<?, ?> context) {
		// -> specify the condition that violates the invariant
		// -> verify the details of the diagnostic, including severity and message
		// Ensure that you remove @generated or mark it @generated NOT
		Set<String> cats = new HashSet<String>(getCategories().size());
		boolean doubles = false;
		for (Category cat : getCategories()) {
			if (!cats.add(cat.getName())) {
				doubles = true;
			}
		}
		if (doubles) {
			if (diagnostics != null) {
				diagnostics.add
					(new BasicDiagnostic
						(Diagnostic.ERROR,
						 ContextsValidator.DIAGNOSTIC_SOURCE,
						 ContextsValidator.CONTEXT__HAS_NOT_CATEGORIES_WITH_SAME_NAME,
						 EcorePlugin.INSTANCE.getString("_UI_GenericInvariant_diagnostic", new Object[] { "hasNotCategoriesWithSameName", EObjectValidator.getObjectLabel(this, (Map<Object, Object>) context) }),
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
      case ContextsPackage.CONTEXT__CATEGORIES:
        return ((InternalEList<?>)getCategories()).basicRemove(otherEnd, msgs);
      case ContextsPackage.CONTEXT__TRANSIENT_CATEGORIES:
        return ((InternalEList<?>)getTransientCategories()).basicRemove(otherEnd, msgs);
      case ContextsPackage.CONTEXT__SUPER_CATEGORY_VARIABLES:
        return ((InternalEList<?>)getSuperCategoryVariables()).basicRemove(otherEnd, msgs);
      case ContextsPackage.CONTEXT__OVERRIDING_VARIABLES:
        return ((InternalEList<?>)getOverridingVariables()).basicRemove(otherEnd, msgs);
      case ContextsPackage.CONTEXT__SUPER_CATEGORY_CATEGORIES:
        return ((InternalEList<?>)getSuperCategoryCategories()).basicRemove(otherEnd, msgs);
      case ContextsPackage.CONTEXT__SELECTED_VERSIONS:
        return ((InternalEList<?>)getSelectedVersions()).basicRemove(otherEnd, msgs);
      case ContextsPackage.CONTEXT__CURRENT_VERSIONS:
        return ((InternalEList<?>)getCurrentVersions()).basicRemove(otherEnd, msgs);
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
      case ContextsPackage.CONTEXT__CATEGORIES:
        return getCategories();
      case ContextsPackage.CONTEXT__SUPER_CONTEXT:
        if (resolve) return getSuperContext();
        return basicGetSuperContext();
      case ContextsPackage.CONTEXT__TRANSIENT_CATEGORIES:
        return getTransientCategories();
      case ContextsPackage.CONTEXT__SUPER_CATEGORY_VARIABLES:
        return getSuperCategoryVariables();
      case ContextsPackage.CONTEXT__OVERRIDING_VARIABLES:
        return getOverridingVariables();
      case ContextsPackage.CONTEXT__SUPER_CATEGORY_CATEGORIES:
        return getSuperCategoryCategories();
      case ContextsPackage.CONTEXT__SELECTED_VERSIONS:
        return getSelectedVersions();
      case ContextsPackage.CONTEXT__CURRENT_VERSIONS:
        return getCurrentVersions();
      case ContextsPackage.CONTEXT__DESCRIPTION:
        return getDescription();
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
      case ContextsPackage.CONTEXT__CATEGORIES:
        getCategories().clear();
        getCategories().addAll((Collection<? extends Category>)newValue);
        return;
      case ContextsPackage.CONTEXT__SUPER_CONTEXT:
        setSuperContext((Context)newValue);
        return;
      case ContextsPackage.CONTEXT__TRANSIENT_CATEGORIES:
        getTransientCategories().clear();
        getTransientCategories().addAll((Collection<? extends Category>)newValue);
        return;
      case ContextsPackage.CONTEXT__SUPER_CATEGORY_VARIABLES:
        getSuperCategoryVariables().clear();
        getSuperCategoryVariables().addAll((Collection<? extends Variable>)newValue);
        return;
      case ContextsPackage.CONTEXT__OVERRIDING_VARIABLES:
        getOverridingVariables().clear();
        getOverridingVariables().addAll((Collection<? extends OverridingVariable>)newValue);
        return;
      case ContextsPackage.CONTEXT__SUPER_CATEGORY_CATEGORIES:
        getSuperCategoryCategories().clear();
        getSuperCategoryCategories().addAll((Collection<? extends Category>)newValue);
        return;
      case ContextsPackage.CONTEXT__SELECTED_VERSIONS:
        getSelectedVersions().clear();
        getSelectedVersions().addAll((Collection<? extends InstallationCategory>)newValue);
        return;
      case ContextsPackage.CONTEXT__CURRENT_VERSIONS:
        getCurrentVersions().clear();
        getCurrentVersions().addAll((Collection<? extends InstallationCategory>)newValue);
        return;
      case ContextsPackage.CONTEXT__DESCRIPTION:
        setDescription((String)newValue);
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
      case ContextsPackage.CONTEXT__CATEGORIES:
        getCategories().clear();
        return;
      case ContextsPackage.CONTEXT__SUPER_CONTEXT:
        setSuperContext((Context)null);
        return;
      case ContextsPackage.CONTEXT__TRANSIENT_CATEGORIES:
        getTransientCategories().clear();
        return;
      case ContextsPackage.CONTEXT__SUPER_CATEGORY_VARIABLES:
        getSuperCategoryVariables().clear();
        return;
      case ContextsPackage.CONTEXT__OVERRIDING_VARIABLES:
        getOverridingVariables().clear();
        return;
      case ContextsPackage.CONTEXT__SUPER_CATEGORY_CATEGORIES:
        getSuperCategoryCategories().clear();
        return;
      case ContextsPackage.CONTEXT__SELECTED_VERSIONS:
        getSelectedVersions().clear();
        return;
      case ContextsPackage.CONTEXT__CURRENT_VERSIONS:
        getCurrentVersions().clear();
        return;
      case ContextsPackage.CONTEXT__DESCRIPTION:
        setDescription(DESCRIPTION_EDEFAULT);
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
      case ContextsPackage.CONTEXT__CATEGORIES:
        return categories != null && !categories.isEmpty();
      case ContextsPackage.CONTEXT__SUPER_CONTEXT:
        return superContext != null;
      case ContextsPackage.CONTEXT__TRANSIENT_CATEGORIES:
        return transientCategories != null && !transientCategories.isEmpty();
      case ContextsPackage.CONTEXT__SUPER_CATEGORY_VARIABLES:
        return superCategoryVariables != null && !superCategoryVariables.isEmpty();
      case ContextsPackage.CONTEXT__OVERRIDING_VARIABLES:
        return overridingVariables != null && !overridingVariables.isEmpty();
      case ContextsPackage.CONTEXT__SUPER_CATEGORY_CATEGORIES:
        return superCategoryCategories != null && !superCategoryCategories.isEmpty();
      case ContextsPackage.CONTEXT__SELECTED_VERSIONS:
        return selectedVersions != null && !selectedVersions.isEmpty();
      case ContextsPackage.CONTEXT__CURRENT_VERSIONS:
        return currentVersions != null && !currentVersions.isEmpty();
      case ContextsPackage.CONTEXT__DESCRIPTION:
        return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString() {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (description: ");
    result.append(description);
    result.append(')');
    return result.toString();
  }

  /**
   * @see org.eclipse.emf.ecore.impl.BasicEObjectImpl#eResource()
   */
  @Override
  public ContextsResourceImpl eResource() {
    return (ContextsResourceImpl) super.eResource();
  }
} //ContextImpl
