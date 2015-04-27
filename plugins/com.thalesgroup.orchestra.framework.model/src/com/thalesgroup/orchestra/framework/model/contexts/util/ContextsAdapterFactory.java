/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.model.contexts.util;

import com.thalesgroup.orchestra.framework.model.contexts.*;
import java.util.Map;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Category;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.contexts.FileVariable;
import com.thalesgroup.orchestra.framework.model.contexts.FolderVariable;
import com.thalesgroup.orchestra.framework.model.contexts.ModelElement;
import com.thalesgroup.orchestra.framework.model.contexts.NamedElement;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage
 * @generated
 */
public class ContextsAdapterFactory extends AdapterFactoryImpl {
	/**
   * The cached model package.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected static ContextsPackage modelPackage;

	/**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public ContextsAdapterFactory() {
    if (modelPackage == null) {
      modelPackage = ContextsPackage.eINSTANCE;
    }
  }

	/**
   * Returns whether this factory is applicable for the type of the object.
   * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
   * @return whether this factory is applicable for the type of the object.
   * @generated
   */
	@Override
	public boolean isFactoryForType(Object object) {
    if (object == modelPackage) {
      return true;
    }
    if (object instanceof EObject) {
      return ((EObject)object).eClass().getEPackage() == modelPackage;
    }
    return false;
  }

	/**
   * The switch that delegates to the <code>createXXX</code> methods.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected ContextsSwitch<Adapter> modelSwitch =
		new ContextsSwitch<Adapter>() {
      @Override
      public Adapter caseCategory(Category object) {
        return createCategoryAdapter();
      }
      @Override
      public Adapter caseContext(Context object) {
        return createContextAdapter();
      }
      @Override
      public Adapter caseVariable(Variable object) {
        return createVariableAdapter();
      }
      @Override
      public Adapter caseFileVariable(FileVariable object) {
        return createFileVariableAdapter();
      }
      @Override
      public Adapter caseFolderVariable(FolderVariable object) {
        return createFolderVariableAdapter();
      }
      @Override
      public Adapter caseModelElement(ModelElement object) {
        return createModelElementAdapter();
      }
      @Override
      public Adapter caseNamedElement(NamedElement object) {
        return createNamedElementAdapter();
      }
      @Override
      public Adapter caseAbstractVariable(AbstractVariable object) {
        return createAbstractVariableAdapter();
      }
      @Override
      public Adapter caseOverridingVariable(OverridingVariable object) {
        return createOverridingVariableAdapter();
      }
      @Override
      public Adapter caseVariableValue(VariableValue object) {
        return createVariableValueAdapter();
      }
      @Override
      public Adapter casePendingElementsCategory(PendingElementsCategory object) {
        return createPendingElementsCategoryAdapter();
      }
      @Override
      public Adapter caseOverridingVariableValue(OverridingVariableValue object) {
        return createOverridingVariableValueAdapter();
      }
      @Override
      public Adapter caseOverridingEnvironmentVariableValue(OverridingEnvironmentVariableValue object) {
        return createOverridingEnvironmentVariableValueAdapter();
      }
      @Override
      public Adapter caseInstallationCategory(InstallationCategory object) {
        return createInstallationCategoryAdapter();
      }
      @Override
      public Adapter caseContributedElement(ContributedElement object) {
        return createContributedElementAdapter();
      }
      @Override
      public Adapter caseReferenceableElement(ReferenceableElement object) {
        return createReferenceableElementAdapter();
      }
      @Override
      public Adapter caseReferencingElement(ReferencingElement object) {
        return createReferencingElementAdapter();
      }
      @Override
      public Adapter caseEnvironmentVariableValue(EnvironmentVariableValue object) {
        return createEnvironmentVariableValueAdapter();
      }
      @Override
      public Adapter caseEnvironmentVariable(EnvironmentVariable object) {
        return createEnvironmentVariableAdapter();
      }
      @Override
      public Adapter caseStringToStringMap(Map.Entry<String, String> object) {
        return createStringToStringMapAdapter();
      }
      @Override
      public Adapter defaultCase(EObject object) {
        return createEObjectAdapter();
      }
    };

	/**
   * Creates an adapter for the <code>target</code>.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param target the object to adapt.
   * @return the adapter for the <code>target</code>.
   * @generated
   */
	@Override
	public Adapter createAdapter(Notifier target) {
    return modelSwitch.doSwitch((EObject)target);
  }


	/**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.framework.model.contexts.Category <em>Category</em>}'.
   * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.framework.model.contexts.Category
   * @generated
   */
	public Adapter createCategoryAdapter() {
    return null;
  }

	/**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.framework.model.contexts.Context <em>Context</em>}'.
   * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.framework.model.contexts.Context
   * @generated
   */
	public Adapter createContextAdapter() {
    return null;
  }

	/**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.framework.model.contexts.Variable <em>Variable</em>}'.
   * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.framework.model.contexts.Variable
   * @generated
   */
	public Adapter createVariableAdapter() {
    return null;
  }

	/**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.framework.model.contexts.FileVariable <em>File Variable</em>}'.
   * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.framework.model.contexts.FileVariable
   * @generated
   */
	public Adapter createFileVariableAdapter() {
    return null;
  }

	/**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.framework.model.contexts.FolderVariable <em>Folder Variable</em>}'.
   * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.framework.model.contexts.FolderVariable
   * @generated
   */
	public Adapter createFolderVariableAdapter() {
    return null;
  }

	/**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.framework.model.contexts.ModelElement <em>Model Element</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.framework.model.contexts.ModelElement
   * @generated
   */
  public Adapter createModelElementAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.framework.model.contexts.NamedElement <em>Named Element</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.framework.model.contexts.NamedElement
   * @generated
   */
  public Adapter createNamedElementAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable <em>Abstract Variable</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable
   * @generated
   */
  public Adapter createAbstractVariableAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.framework.model.contexts.OverridingVariable <em>Overriding Variable</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.framework.model.contexts.OverridingVariable
   * @generated
   */
  public Adapter createOverridingVariableAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.framework.model.contexts.VariableValue <em>Variable Value</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.framework.model.contexts.VariableValue
   * @generated
   */
  public Adapter createVariableValueAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.framework.model.contexts.OverridingVariableValue <em>Overriding Variable Value</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.framework.model.contexts.OverridingVariableValue
   * @generated
   */
  public Adapter createOverridingVariableValueAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.framework.model.contexts.OverridingEnvironmentVariableValue <em>Overriding Environment Variable Value</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.framework.model.contexts.OverridingEnvironmentVariableValue
   * @generated
   */
  public Adapter createOverridingEnvironmentVariableValueAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.framework.model.contexts.InstallationCategory <em>Installation Category</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.framework.model.contexts.InstallationCategory
   * @generated
   */
  public Adapter createInstallationCategoryAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.framework.model.contexts.PendingElementsCategory <em>Pending Elements Category</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.framework.model.contexts.PendingElementsCategory
   * @generated
   */
  public Adapter createPendingElementsCategoryAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.framework.model.contexts.ContributedElement <em>Contributed Element</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.framework.model.contexts.ContributedElement
   * @generated
   */
  public Adapter createContributedElementAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.framework.model.contexts.ReferenceableElement <em>Referenceable Element</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.framework.model.contexts.ReferenceableElement
   * @generated
   */
  public Adapter createReferenceableElementAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.framework.model.contexts.ReferencingElement <em>Referencing Element</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.framework.model.contexts.ReferencingElement
   * @generated
   */
  public Adapter createReferencingElementAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariableValue <em>Environment Variable Value</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariableValue
   * @generated
   */
  public Adapter createEnvironmentVariableValueAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariable <em>Environment Variable</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariable
   * @generated
   */
  public Adapter createEnvironmentVariableAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link java.util.Map.Entry <em>String To String Map</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see java.util.Map.Entry
   * @generated
   */
  public Adapter createStringToStringMapAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for the default case.
   * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
   * @return the new adapter.
   * @generated
   */
	public Adapter createEObjectAdapter() {
    return null;
  }

} //ContextsAdapterFactory
