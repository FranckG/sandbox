/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.markers.MarkerViewUtil;

import com.thalesgroup.orchestra.framework.common.helper.MarkerHelper;
import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.framework.model.IEditionContextProvider;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.validation.ModelValidationActivator;
import com.thalesgroup.orchestra.framework.model.validation.ValidationContext;
import com.thalesgroup.orchestra.framework.model.validation.constraint.AbstractConstraint.ElementDescription;
import com.thalesgroup.orchestra.framework.root.ui.AbstractRunnableWithDisplay;
import com.thalesgroup.orchestra.framework.root.ui.DisplayHelper;
import com.thalesgroup.orchestra.framework.ui.activator.OrchestraFrameworkUiActivator;
import com.thalesgroup.orchestra.framework.ui.internal.validation.UIValidationHelper;
import com.thalesgroup.orchestra.framework.ui.view.VariablesView;

/**
 * Validate action.
 * @author t0076261
 */
public class ValidateAction extends org.eclipse.emf.edit.ui.action.ValidateAction {
  /**
   * Resource hosting the elements of the current diagnostic (see {@link #handleDiagnostic(Diagnostic)}).
   */
  protected Resource _currentResource;
  /**
   * Edition context provider.
   */
  private IEditionContextProvider _editionContextProvider;

  /**
   * Constructor.
   */
  public ValidateAction(IEditionContextProvider editionContextProvider_p) {
    setImageDescriptor(OrchestraFrameworkUiActivator.getDefault().getImageDescriptor("validate.gif")); //$NON-NLS-1$
    _editionContextProvider = editionContextProvider_p;
    domain = ModelHandlerActivator.getDefault().getEditingDomain();
    eclipseResourcesUtil = new EclipseResourcesUtil() {
      /**
       * @see org.eclipse.emf.edit.ui.action.ValidateAction.EclipseResourcesUtil#adjustMarker(org.eclipse.core.resources.IMarker,
       *      org.eclipse.emf.common.util.Diagnostic, org.eclipse.emf.common.util.Diagnostic)
       */
      @Override
      protected void adjustMarker(IMarker marker_p, Diagnostic diagnostic_p, Diagnostic parentDiagnostic_p) throws CoreException {
        // Default behavior.
        super.adjustMarker(marker_p, diagnostic_p, parentDiagnostic_p);
        // Take into account real target element, if any.
        List<?> data = diagnostic_p.getData();
        for (Object objectData : data) {
          // Does it have a rule id ?
          if (objectData instanceof ElementDescription) {
            ElementDescription path = (ElementDescription) objectData;
            marker_p.setAttribute(EValidator.URI_ATTRIBUTE, path._uri);
            marker_p.setAttribute(MarkerViewUtil.PATH_ATTRIBUTE, path._path);
          }
        }
        // Force cache.
        Map<Object, Object> decomposition = UIValidationHelper.getInstance().decomposeMarker(marker_p);
        // Update elements hierarchy severity.
        UIValidationHelper.getInstance().insertSeverityForHierarchy(decomposition.get(EObject.class), (Context) decomposition.get(Context.class),
            (Integer) decomposition.get(IMarker.SEVERITY));
      }

      /**
       * @see org.eclipse.emf.edit.ui.action.ValidateAction.EclipseResourcesUtil#createMarkers(org.eclipse.emf.ecore.resource.Resource,
       *      org.eclipse.emf.common.util.Diagnostic)
       */
      @Override
      public void createMarkers(Resource resource_p, Diagnostic diagnostic_p) {
        super.createMarkers((null != _currentResource) ? _currentResource : resource_p, diagnostic_p);
      }

      /**
       * @see org.eclipse.emf.common.ui.MarkerHelper#deleteMarkers(java.lang.Object)
       */
      @Override
      public void deleteMarkers(Object object_p) {
        // Do not delete any markers: deletion is taken into account manually in handleDiagnotisc below.
        return;
      }
    };
  }

  /**
   * @see org.eclipse.emf.edit.ui.action.ValidateAction#createDiagnostician(org.eclipse.emf.common.notify.AdapterFactory,
   *      org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  protected Diagnostician createDiagnostician(final AdapterFactory adapterFactory_p, final IProgressMonitor progressMonitor_p) {
    return new Diagnostician() {
      /**
       * @see org.eclipse.emf.ecore.util.Diagnostician#doValidateContents(org.eclipse.emf.ecore.EObject, org.eclipse.emf.common.util.DiagnosticChain,
       *      java.util.Map)
       */
      @Override
      protected boolean doValidateContents(EObject eObject_p, DiagnosticChain diagnostics_p, Map<Object, Object> context_p) {
        // Skip contents as it is already validated through the provided traversal strategy.
        return true;
      }

      // Taken from super implementation.
      @Override
      public String getObjectLabel(EObject eObject) {
        if (adapterFactory_p != null && !eObject.eIsProxy()) {
          IItemLabelProvider itemLabelProvider = (IItemLabelProvider) adapterFactory_p.adapt(eObject, IItemLabelProvider.class);
          if (itemLabelProvider != null) {
            return itemLabelProvider.getText(eObject);
          }
        }
        return super.getObjectLabel(eObject);
      }

      // Taken from super implementation.
      @Override
      public boolean validate(EClass eClass, EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
        progressMonitor_p.worked(1);
        return super.validate(eClass, eObject, diagnostics, context);
      }
    };
  }

  /**
   * @see org.eclipse.emf.edit.ui.action.ValidateAction#handleDiagnostic(org.eclipse.emf.common.util.Diagnostic)
   */
  @Override
  protected void handleDiagnostic(Diagnostic diagnostic_p) {
    // Set the context of the validation.
    Context validatingContext = ModelUtil.getContext(_currentResource);
    UIValidationHelper.getInstance().startNewValidation(validatingContext);
    // Prepare tree refreshing.
    final Couple<Context, Collection<Object>> oldErrors =
        new Couple<Context, Collection<Object>>(validatingContext, UIValidationHelper.getInstance().getPreviousElementsOnError());
    // Delete all markers for validating context.
    MarkerHelper.deleteValidationMarkers(validatingContext.eResource().getURI());
    // Default behavior.
    try {
      super.handleDiagnostic(diagnostic_p);
    } catch (RuntimeException ex) {
      // Ignore exceptions occurring when the workbench is closing.
      if (PlatformUI.isWorkbenchRunning() && !PlatformUI.getWorkbench().isClosing()) {
        throw ex;
      }
    }
    // Filtering types.
    EClass[] filters = new EClass[] { ContextsPackage.Literals.CONTEXT, ContextsPackage.Literals.CATEGORY };
    final Couple<Context, Collection<Object>> newErrors =
        new Couple<Context, Collection<Object>>(validatingContext, UIValidationHelper.getInstance().getElementsOnError(filters));
    // Refresh tree.
    AbstractRunnableWithDisplay decorationsRunnable = new AbstractRunnableWithDisplay() {
      public void run() {

        // Remove previous decorations.
        Context oldContext = oldErrors.getKey();
        for (Object element : oldErrors.getValue()) {
          VariablesView.getSharedInstance().refreshElement(element, oldContext);
        }
        // Add new ones.
        Context newContext = newErrors.getKey();
        for (Object element : newErrors.getValue()) {
          VariablesView.getSharedInstance().refreshElement(element, newContext);
        }
        // Blindly refresh current variables page.
        VariablesView.getSharedInstance().refreshVariables();
      }

    };
    DisplayHelper.displayRunnable(decorationsRunnable, true);
  }

  /**
   * @see org.eclipse.emf.edit.ui.action.ValidateAction#run()
   */
  @Override
  public void run() {
    // Set validation context.
    ModelValidationActivator.getInstance().setCurrentValidationContext(new ValidationContext(ModelUtil.getContext(_currentResource)));
    try {
      // Go for default behavior.
      // Markers will be tagged with current resource at creation time (see constructor).
      super.run();
    } finally {
      // Reset current resource, whatever its value may be.
      _currentResource = null;
      // Dispose validation context.
      ModelValidationActivator.getInstance().disposeCurrentValidationContext();
    }
  }

  /**
   * @see org.eclipse.emf.edit.ui.action.ValidateAction#updateSelection(org.eclipse.jface.viewers.IStructuredSelection)
   */
  @Override
  public boolean updateSelection(IStructuredSelection selection_p) {
    Context editionContext = _editionContextProvider.getEditionContext();
    if (null == editionContext) {
      _currentResource = null;
      return false;
    }
    _currentResource = editionContext.eResource();
    // Transform selection (replace selected VariableValues by their Variables).
    IStructuredSelection selection = transformSelection(selection_p);

    return super.updateSelection(selection);
  }

  /**
   * Replaces each VariablesValue contained in passed selection by its containing Variable. If no VariableValue in given selection, selection is leaved
   * untouched.
   * @param selection_p
   * @return
   */
  public IStructuredSelection transformSelection(IStructuredSelection selection_p) {
    IStructuredSelection resultSelection = selection_p;
    List<?> selectedElements = selection_p.toList();
    // Find VariableValues amongst selected elements.
    Collection<VariableValue> selectedVariableValues = EcoreUtil.getObjectsByType(selectedElements, ContextsPackage.Literals.VARIABLE_VALUE);
    if (!selectedVariableValues.isEmpty()) {
      List<Object> modifiedSelectedElements = new ArrayList<Object>(selectedElements);
      // Replace VariableValues by their containing Variables.
      for (VariableValue variableValue : selectedVariableValues) {
        modifiedSelectedElements.set(modifiedSelectedElements.indexOf(variableValue),
            ModelUtil.getLogicalContainer(variableValue, ModelUtil.getContext(_currentResource)));
      }
      // Change selection.
      resultSelection = new StructuredSelection(modifiedSelectedElements);
    }
    return resultSelection;
  }
}