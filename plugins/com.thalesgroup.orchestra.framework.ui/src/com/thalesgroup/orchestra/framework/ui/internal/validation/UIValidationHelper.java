/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ModelElement;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;

/**
 * Singleton class that provides with tests for elements currently on error, and methods to address validation markers.
 * @author t0076261
 */
public class UIValidationHelper {
  /**
   * Unique instance.
   */
  private static UIValidationHelper __instance;
  /**
   * The current validating context
   */
  ValidatedContextData _currentValidatingContext = null;

  /**
   * The map of validated contexts data
   */
  Map<String, ValidatedContextData> _validatedContexts = null;

  /**
   * Private constructor.
   */
  private UIValidationHelper() {
    clearCache();
  }

  /**
   * Clear memory cache for all contexts.
   */
  protected void clearCache() {

    if (_validatedContexts == null) {
      _validatedContexts = new HashMap<String, ValidatedContextData>(0);
    } else {
      _validatedContexts.clear();
    }
  }

  /**
   * Clear memory cache for a specified context.
   * @param contextName_p The name of the context to clear.
   */
  protected void clearCache(String contextName_p) {
    ValidatedContextData contextData = _validatedContexts.get(contextName_p);
    if (contextData != null) {
      contextData.clear();
    }
  }

  /**
   * Get relevant elements out of specified marker.<br>
   * The marker has to conform to {@link EValidator#MARKER} type.
   * @param marker_p
   * @return <code>null</code> if this is not a validation marker. Included values might be <code>null</code> depending on decomposition results.<br>
   *         Resulting map is composed of the following keys :
   *         <ul>
   *         <li> {@link Context}.class, value = the validated context.
   *         <li> {@link EObject}.class, value = the validated element.
   *         <li> {@link IMarker#SEVERITY}, value = the severity of the validation result (as defined by {@link IMarker#SEVERITY_INFO} to
   *         {@link IMarker#SEVERITY_ERROR} integer values).
   *         </ul>
   */
  @SuppressWarnings("boxing")
  public Map<Object, Object> decomposeMarker(IMarker marker_p) {
    // Precondition.
    if (null == marker_p) {
      return null;
    }
    // Get from cache.
    Map<Object, Object> result = null;
    if (_currentValidatingContext != null) {
      result = _currentValidatingContext._markerToDecomposition.get(marker_p);
      if (null != result) {
        return result;
      }
    }
    // Create it.
    boolean isValidationMarker = false;
    try {
      isValidationMarker = EValidator.MARKER.equals(marker_p.getType());
    } catch (Exception e_p) {
      // Won't proceed with this marker.
    }
    // Precondition.
    if (!isValidationMarker) {
      return null;
    }
    Context context = null;
    EObject object = null;
    // Equivalent to null for severity.
    int severity = Integer.MIN_VALUE;
    // Get severity first.
    severity = marker_p.getAttribute(IMarker.SEVERITY, severity);
    // Then target element.
    String uriAttribute = marker_p.getAttribute(EValidator.URI_ATTRIBUTE, null);
    if (null != uriAttribute) {
      URI uri = URI.createURI(uriAttribute);
      object = ModelHandlerActivator.getDefault().getEditingDomain().getResourceSet().getEObject(uri, true);
    }
    // And context.
    IResource resource = marker_p.getResource();
    String path = (null != resource) ? resource.getFullPath().toString() : null;
    if (null != path) {
      context = ModelHandlerActivator.getDefault().getDataHandler().getContext(path);
    }
    // Set validated context.
    if (null != context) {
      if (null == _currentValidatingContext._validatedContext) {
        _currentValidatingContext._validatedContext = context;
      }
    }

    result = new HashMap<Object, Object>(3);
    result.put(Context.class, context);
    result.put(EObject.class, object);
    result.put(IMarker.SEVERITY, severity);
    // Add to cache.
    _currentValidatingContext._markerToDecomposition.put(marker_p, result);
    Collection<Map<Object, Object>> errors = _currentValidatingContext._elementToErrors.get(object);
    if (null == errors) {
      errors = new ArrayList<Map<Object, Object>>(1);
      _currentValidatingContext._elementToErrors.put(object, errors);
    }
    // No need to make sure this does not exist yet, since the marker cache is filtering when the marker is unknown at the beginning of this method.
    errors.add(result);
    return result;
  }

  /**
   * Get all elements on error.
   * @param types_p Optional filter types. Can be <code>null</code> or empty.
   * @return A cloned collection of {@link Object}s.
   */
  public Collection<Object> getElementsOnError(EClass[] types_p) {
    return _currentValidatingContext.getElementsOnError(types_p);
  }

  /**
   * Get all errors attached to specified model element.
   * @param element_p
   * @return A not empty collection of error decompositions, if on error, <code>null</code> or empty otherwise. See {@link #decomposeMarker(IMarker)} for
   *         decomposition content.
   */
  public Collection<Map<Object, Object>> getErrors(ModelElement element_p) {
    return _currentValidatingContext.getErrors(element_p);
  }

  /**
   * Get maximum severity among specified errors decompositions.
   * @param context_p
   * @param error_p
   * @return the severity of the validation result of <code>context_p</code> (as defined by {@link IMarker#SEVERITY_INFO} to {@link IMarker#SEVERITY_ERROR}
   *         integer values), or <code>null</code> if no error is available.
   */
  public Integer getMaximumSeverity(Context context_p, Object error_p) {
    ValidatedContextData validatedContextData = _validatedContexts.get(context_p.getName());
    if (validatedContextData == null) {
      return null;
    }
    return validatedContextData.getMaximumSeverity(error_p);
  }

  /**
   * @return The previous {@link Collection} of errors on the last validated context.
   */
  public Collection<Object> getPreviousElementsOnError() {
    return _currentValidatingContext.getPreviousElementsOnError();
  }

  /**
   * Get currently validated context.
   * @return
   */
  public Context getValidatedContext() {
    if (null == _currentValidatingContext) {
      return null;
    }
    return _currentValidatingContext._validatedContext;
  }

  /**
   * Insert severity for specified element and its hierarchy.<br>
   * That is compute maximum of existing severity and newly specified one for these elements.<br>
   * The result is accessible through {@link #getMaximumSeverity(Object)}.
   * @param element_p The validated element.
   * @param context_p The context of validation.
   * @param severity_p The error severity to apply.
   */
  public void insertSeverityForHierarchy(Object element_p, Context context_p, Integer severity_p) {
    // Do not tag with null severity.
    if (null == severity_p) {
      return;
    }
    ValidatedContextData validatedContextData = _validatedContexts.get(context_p.getName());
    if (validatedContextData == null) {
      return;
    }
    validatedContextData.insertSeverityForHierarchy(element_p, context_p, severity_p);
  }

  /**
   * Set the current validation context for a new validation session
   * @param validatingContext_p
   */
  public void startNewValidation(Context validatingContext_p) {
    _currentValidatingContext = _validatedContexts.get(validatingContext_p.getName());
    if (null == _currentValidatingContext) {
      _currentValidatingContext = new ValidatedContextData();
      _validatedContexts.put(validatingContext_p.getName(), _currentValidatingContext);
    }
    _currentValidatingContext.clear();
  }

  /**
   * Get unique instance.
   * @return
   */
  public static UIValidationHelper getInstance() {
    if (null == __instance) {
      __instance = new UIValidationHelper();
    }
    return __instance;
  }

  class ValidatedContextData {

    /**
     * Previous element to maximum applicable error severity.
     */
    protected ArrayList<Object> _elementsOnError;
    /**
     * Element to all direct errors (at element level only, not considering sub-elements).
     */
    protected Map<Object, Collection<Map<Object, Object>>> _elementToErrors;
    /**
     * Element to maximum applicable error severity.
     */
    protected Map<Object, Integer> _elementToSeverity;
    /**
     * Marker to decomposition (ie error).
     */
    protected Map<IMarker, Map<Object, Object>> _markerToDecomposition;
    /**
     * Previous element to maximum applicable error severity.
     */
    protected ArrayList<Object> _previousElementsOnError;
    /**
     * Context currently validated.
     */
    protected Context _validatedContext;

    /**
     * Constructor
     */
    public ValidatedContextData() {
      clear();
    }

    /**
     * Reset maps and shift the results if possible.
     */
    protected void clear() {
      // Errors.
      if (null == _elementToErrors) {
        _elementToErrors = new HashMap<Object, Collection<Map<Object, Object>>>(0);
      } else {
        _elementToErrors.clear();
      }
      // Max severity.
      if (null == _elementToSeverity) {
        _elementToSeverity = new HashMap<Object, Integer>(0);
      } else {
        _elementToSeverity.clear();
      }
      // Markers decompositions.
      if (null == _markerToDecomposition) {
        _markerToDecomposition = new HashMap<IMarker, Map<Object, Object>>(0);
      } else {
        _markerToDecomposition.clear();
      }

      // Reset results.
      if (null == _previousElementsOnError) {
        _previousElementsOnError = new ArrayList<Object>(0);
      }

      if (null == _elementsOnError) {
        _elementsOnError = new ArrayList<Object>(0);
      } else {
        _previousElementsOnError.clear();
        _previousElementsOnError.addAll(_elementsOnError);
        _elementsOnError.clear();
      }
    }

    /**
     * Get all elements on error.
     * @param types_p Optional filter types. Can be <code>null</code> or empty.
     * @return A cloned collection of {@link Object}s.
     */
    public Collection<Object> getElementsOnError(EClass[] types_p) {
      // Precondition.
      Collection<Object> allElements = _elementToSeverity.keySet();
      if ((null != types_p) && (0 < types_p.length)) {
        for (EClass type : types_p) {
          _elementsOnError.addAll(EcoreUtil.getObjectsByType(allElements, type));
        }
      } else {
        // Clone elements collection.
        _elementsOnError.addAll(allElements);
      }
      return _elementsOnError;
    }

    /**
     * Get all errors attached to specified model element.
     * @param element_p
     * @return A not empty collection of error decompositions, if on error, <code>null</code> or empty otherwise. See {@link #decomposeMarker(IMarker)} for
     *         decomposition content.
     */
    public Collection<Map<Object, Object>> getErrors(ModelElement element_p) {
      // Precondition.
      if (null == element_p) {
        return null;
      }
      // Start search.
      return _elementToErrors.get(element_p);
    }

    /**
     * Get maximum severity among specified errors decompositions.
     * @param context_p
     * @param error_p
     * @return the severity of the validation result of <code>context_p</code> (as defined by {@link IMarker#SEVERITY_INFO} to {@link IMarker#SEVERITY_ERROR}
     *         integer values), or <code>null</code> if no error is available.
     */
    public Integer getMaximumSeverity(Object element_p) {
      return _elementToSeverity.get(element_p);
    }

    /**
     * @return The previous {@link Collection} of errors on the last validated context.
     */
    public Collection<Object> getPreviousElementsOnError() {
      return _previousElementsOnError;
    }

    /**
     * Insert severity for specified element and its hierarchy.<br>
     * That is compute maximum of existing severity and newly specified one for these elements.<br>
     * The result is accessible through {@link #getMaximumSeverity(Object)}.
     * @param element_p The validated element.
     * @param context_p The context of validation.
     * @param severity_p The error severity to apply.
     */
    @SuppressWarnings("boxing")
    public void insertSeverityForHierarchy(Object element_p, Context context_p, Integer severity_p) {
      // Cycle through hierarchy.
      Object currentElement = element_p;
      while (null != currentElement) {
        // Get new maximum severity.
        Integer currentSeverity = _elementToSeverity.get(currentElement);
        if (null == currentSeverity) {
          currentSeverity = Integer.MIN_VALUE;
        }
        // Stop updating hierarchy for a greater (or equal) error is already known.
        if (currentSeverity >= severity_p) {
          break;
        }
        _elementToSeverity.put(currentElement, severity_p);
        currentElement = ModelUtil.getLogicalContainer(currentElement, context_p);
      }
    }
  }
}