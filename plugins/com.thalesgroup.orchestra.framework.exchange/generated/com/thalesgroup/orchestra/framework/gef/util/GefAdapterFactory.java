/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.gef.util;

import com.thalesgroup.orchestra.framework.gef.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.thalesgroup.orchestra.framework.gef.GefPackage
 * @generated
 */
public class GefAdapterFactory extends AdapterFactoryImpl {
  /**
   * The cached model package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static GefPackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public GefAdapterFactory() {
    if (modelPackage == null) {
      modelPackage = GefPackage.eINSTANCE;
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
  protected GefSwitch<Adapter> modelSwitch =
    new GefSwitch<Adapter>() {
      @Override
      public Adapter caseDescription(Description object) {
        return createDescriptionAdapter();
      }
      @Override
      public Adapter caseElement(Element object) {
        return createElementAdapter();
      }
      @Override
      public Adapter caseElementReference(ElementReference object) {
        return createElementReferenceAdapter();
      }
      @Override
      public Adapter caseFileReference(FileReference object) {
        return createFileReferenceAdapter();
      }
      @Override
      public Adapter caseGEF(GEF object) {
        return createGEFAdapter();
      }
      @Override
      public Adapter caseGenericExportFormat(GenericExportFormat object) {
        return createGenericExportFormatAdapter();
      }
      @Override
      public Adapter caseLinksToArtifacts(LinksToArtifacts object) {
        return createLinksToArtifactsAdapter();
      }
      @Override
      public Adapter caseLinksToElements(LinksToElements object) {
        return createLinksToElementsAdapter();
      }
      @Override
      public Adapter caseMproperty(Mproperty object) {
        return createMpropertyAdapter();
      }
      @Override
      public Adapter caseMpropertyValue(MpropertyValue object) {
        return createMpropertyValueAdapter();
      }
      @Override
      public Adapter caseProperties(Properties object) {
        return createPropertiesAdapter();
      }
      @Override
      public Adapter caseProperty(Property object) {
        return createPropertyAdapter();
      }
      @Override
      public Adapter caseReference(Reference object) {
        return createReferenceAdapter();
      }
      @Override
      public Adapter caseTextualDescription(TextualDescription object) {
        return createTextualDescriptionAdapter();
      }
      @Override
      public Adapter caseVersion(Version object) {
        return createVersionAdapter();
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
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.framework.gef.Description <em>Description</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.framework.gef.Description
   * @generated
   */
  public Adapter createDescriptionAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.framework.gef.Element <em>Element</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.framework.gef.Element
   * @generated
   */
  public Adapter createElementAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.framework.gef.ElementReference <em>Element Reference</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.framework.gef.ElementReference
   * @generated
   */
  public Adapter createElementReferenceAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.framework.gef.FileReference <em>File Reference</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.framework.gef.FileReference
   * @generated
   */
  public Adapter createFileReferenceAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.framework.gef.GEF <em>GEF</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.framework.gef.GEF
   * @generated
   */
  public Adapter createGEFAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.framework.gef.GenericExportFormat <em>Generic Export Format</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.framework.gef.GenericExportFormat
   * @generated
   */
  public Adapter createGenericExportFormatAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.framework.gef.LinksToArtifacts <em>Links To Artifacts</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.framework.gef.LinksToArtifacts
   * @generated
   */
  public Adapter createLinksToArtifactsAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.framework.gef.LinksToElements <em>Links To Elements</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.framework.gef.LinksToElements
   * @generated
   */
  public Adapter createLinksToElementsAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.framework.gef.Mproperty <em>Mproperty</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.framework.gef.Mproperty
   * @generated
   */
  public Adapter createMpropertyAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.framework.gef.MpropertyValue <em>Mproperty Value</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.framework.gef.MpropertyValue
   * @generated
   */
  public Adapter createMpropertyValueAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.framework.gef.Properties <em>Properties</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.framework.gef.Properties
   * @generated
   */
  public Adapter createPropertiesAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.framework.gef.Property <em>Property</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.framework.gef.Property
   * @generated
   */
  public Adapter createPropertyAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.framework.gef.Reference <em>Reference</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.framework.gef.Reference
   * @generated
   */
  public Adapter createReferenceAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.framework.gef.TextualDescription <em>Textual Description</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.framework.gef.TextualDescription
   * @generated
   */
  public Adapter createTextualDescriptionAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.thalesgroup.orchestra.framework.gef.Version <em>Version</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.thalesgroup.orchestra.framework.gef.Version
   * @generated
   */
  public Adapter createVersionAdapter() {
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

} //GefAdapterFactory
