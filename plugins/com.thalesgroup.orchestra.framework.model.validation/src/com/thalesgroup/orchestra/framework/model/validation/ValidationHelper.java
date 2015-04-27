/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.service.AbstractTraversalStrategy;
import org.eclipse.emf.validation.service.IBatchValidator;
import org.eclipse.emf.validation.service.ITraversalStrategy;
import org.eclipse.emf.validation.service.ModelValidationService;

import com.thalesgroup.orchestra.framework.environment.validation.EnvironmentRuleRegistry.ValidationType;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.Category;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ModelElement;
import com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch;
import com.thalesgroup.orchestra.framework.model.handler.IValidationHandler;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;

/**
 * Helper that provides validation invocations.
 * @author t0076261
 */
public class ValidationHelper implements IValidationHandler {
  /**
   * Shared instance.
   */
  private volatile static ValidationHelper __instance;

  /**
   * Default constructor.
   */
  public ValidationHelper() {
    __instance = this;
  }

  /**
   * Get new dedicated traversal strategy implementation.
   * @return A not <code>null</code> implementation of {@link ITraversalStrategy}.
   */
  public ITraversalStrategy createNewTraversalStrategy() {
    return new ContextTraversalStrategy();
  }

  /**
   * Get specified element contents for validation purpose.
   * @param element_p
   * @param context_p
   * @return
   */
  public Collection<? extends EObject> getContentsForValidation(EObject element_p, final Context context_p) {
    return new ContextsSwitch<Collection<? extends EObject>>() {
      /**
       * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseCategory(com.thalesgroup.orchestra.framework.model.contexts.Category)
       */
      @Override
      public Collection<? extends EObject> caseCategory(Category object_p) {
        Collection<EObject> result = new ArrayList<EObject>(0);
        // Add all categories.
        result.addAll(DataUtil.getCategories(object_p, context_p));
        // Make sure contributed variables are added.
        result.addAll(DataUtil.getVariables(object_p, context_p));
        return result;
      }

      /**
       * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseContext(com.thalesgroup.orchestra.framework.model.contexts.Context)
       */
      @Override
      public Collection<? extends EObject> caseContext(Context object_p) {
        // Do not validate contributed or overriding variables at this point (they will be validated when the containing category will be validated).
        ArrayList<EObject> result = new ArrayList<EObject>(object_p.getCategories());
        // Also validate transient categories.
        result.addAll(object_p.getTransientCategories());
        // Remove pending category from transient ones, as this is prone to be invalid.
        result.remove(ModelUtil.getPendingCategory(object_p));
        // Also validate parent context.
        Context parentContext = object_p.getSuperContext();
        if (null != parentContext) {
          result.add(parentContext);
        }
        return result;
      }

      /**
       * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#defaultCase(org.eclipse.emf.ecore.EObject)
       */
      @Override
      public Collection<? extends EObject> defaultCase(EObject object_p) {
        // Return at least containment for specified object.
        return object_p.eContents();
      }
    }.doSwitch(element_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.handler.IValidationHandler#validateElement(com.thalesgroup.orchestra.framework.model.contexts.ModelElement,
   *      com.thalesgroup.orchestra.framework.model.contexts.Context)
   */
  public IStatus validateElement(ModelElement element_p, Context context_p) {
    return validateElement(element_p, context_p, ValidationType.NOMINAL_TYPE, null);
  }

  /**
   * Validate specified element for specified context, and specified validation type.<br>
   * It applies both batch and live validations.
   * @param element_p
   * @param context_p
   * @param validationType_p
   * @param handler_p The validation result handler. If not <code>null</code>, the handler may also serve as {@link ValidationContext} factory.
   * @return
   */
  public synchronized IStatus validateElement(ModelElement element_p, Context context_p, ValidationType validationType_p, AbstractValidationHandler handler_p) {
    IBatchValidator validator = (IBatchValidator) ModelValidationService.getInstance().newValidator(EvaluationMode.BATCH);
    validator.setIncludeLiveConstraints(true);
    IStatus result = null;
    try {
      // Create validation context.
      ValidationContext validationContext = null;
      // Delegate to validation handler.
      if (null != handler_p) {
        validationContext = handler_p.createValidationContext(context_p);
      }
      // Make sure there is at least one default validation context.
      if (null == validationContext) {
        validationContext = new ValidationContext(context_p);
      }
      ModelValidationActivator.getInstance().setCurrentValidationContext(validationContext);
      // Set validation type.
      validationContext.setValidationType(validationType_p);
      // Set specific traversal strategy.
      validator.setTraversalStrategy(createNewTraversalStrategy());
      // Then validate.
      result = validator.validate(Collections.singletonList(element_p));
      // And deal with specific validation results, if any.
      if (null != handler_p) {
        handler_p.handleValidationResult(validationContext, result);
      }
    } finally {
      // Dispose validation context.
      ModelValidationActivator.getInstance().disposeCurrentValidationContext();
    }
    return result;
  }

  /**
   * Get shared instance.
   * @return
   */
  public static ValidationHelper getInstance() {
    return __instance;
  }

  /**
   * Dedicated traversal strategy.
   * @author t0076261
   */
  public class ContextTraversalStrategy extends AbstractTraversalStrategy {
    /**
     * @see org.eclipse.emf.validation.service.AbstractTraversalStrategy#countElements(java.util.Collection)
     */
    @Override
    protected int countElements(Collection<? extends EObject> traversalRoots_p) {
      return 0;
    }

    /**
     * @see org.eclipse.emf.validation.service.AbstractTraversalStrategy#createIterator(java.util.Collection)
     */
    @Override
    protected Iterator<? extends EObject> createIterator(Collection<? extends EObject> traversalRoots_p) {
      Collection<EObject> elements = new ArrayList<EObject>(traversalRoots_p);
      // Add elements to validate.
      Context askingContext = ModelValidationActivator.getInstance().getCurrentValidationContext().getAskingContext();
      for (EObject element : traversalRoots_p) {
        // Do not add the ContextsProject element in #getContentsForValidation(...) for it would be applied recursively many times to the same physical project.
        // Instead add it here once per administrator context to validate.
        // Note that as a consequence, user context validation does not take into account its contexts project.
        if (ProjectActivator.getInstance().isAdministrator() && (element instanceof Context) && !((Context) element).eResource().isDefault()) {
          // A context will be validated, add its ContextsProject to validate it too.
          RootContextsProject rootContextsProject = ProjectActivator.getInstance().getProjectHandler().getProjectFromContextUri(element.eResource().getURI());
          if (null != rootContextsProject) {
            elements.add(rootContextsProject._contextsProject);
          }
        }
        elements.addAll(getContents(element, askingContext));
      }
      return elements.iterator();
    }

    /**
     * Get validation contents for specified element recursively (ie traverse all sub-tree).
     * @param element_p
     * @param context_p
     * @return
     */
    protected Collection<EObject> getContents(EObject element_p, Context context_p) {
      ArrayList<EObject> result = new ArrayList<EObject>(getContentsForValidation(element_p, context_p));
      ArrayList<EObject> clonedResult = new ArrayList<EObject>(result);
      for (EObject element : clonedResult) {
        result.addAll(getContents(element, context_p));
      }
      return result;
    }
  }
}