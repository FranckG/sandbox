/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.validation.constraint.name;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.Category;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.contexts.ModelElement;
import com.thalesgroup.orchestra.framework.model.contexts.NamedElement;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.model.handler.data.RootElement;
import com.thalesgroup.orchestra.framework.model.validation.constraint.AbstractConstraint;

/**
 * Name uniqueness constraint regarding its siblings.
 * @author T0052089
 */
public class ElementNameUniquenessWithSiblingsConstraint extends AbstractConstraint<NamedElement> {

  /**
   * @see com.thalesgroup.orchestra.framework.model.validation.constraint.AbstractConstraint#batchValidate(com.thalesgroup.orchestra.framework.model.contexts.ModelElement,
   *      org.eclipse.emf.validation.IValidationContext)
   */
  @Override
  protected IStatus batchValidate(NamedElement target_p, IValidationContext context_p) {
    return null;
  }

  /**
   * Create an error status if an element with the same name is found in siblings of target_p.
   * @param target_p
   * @param siblings_p collection of siblings (target_p is removed form this collection).
   * @param context_p
   * @param typeName_p type name of the target to validate.
   */
  protected void checkNameUniqueness(NamedElement target_p, Collection<? extends NamedElement> siblings_p, IValidationContext context_p, String typeName_p) {
    siblings_p.remove(target_p);
    for (NamedElement sibling : siblings_p) {
      if (sibling.getName().equals(target_p.getName())) {
        addStatus(createFailureStatusWithDescription(target_p, context_p, typeName_p, target_p.getName()));
        break;
      }
    }
  }

  /**
   * Do a live validation to check if an element uses the same name as one of its siblings.
   * @see com.thalesgroup.orchestra.framework.model.validation.constraint.AbstractConstraint#liveValidate(com.thalesgroup.orchestra.framework.model.contexts.ModelElement,
   *      org.eclipse.emf.validation.IValidationContext)
   */
  @Override
  protected IStatus liveValidate(NamedElement target_p, IValidationContext context_p) {
    Collection<? extends NamedElement> siblings = new ArrayList<NamedElement>();
    String typeName = ICommonConstants.EMPTY_STRING;

    // Use logical container to find siblings of target element.
    ModelElement logicalContainer = ModelUtil.getLogicalContainer(target_p, getAskingContext());

    if (target_p instanceof Variable) {
      typeName = ContextsPackage.Literals.VARIABLE.getName();
      siblings = DataUtil.getVariables((Category) logicalContainer, getAskingContext());
    } else if (target_p instanceof Category) {
      typeName = ContextsPackage.Literals.CATEGORY.getName();
      if (logicalContainer instanceof Category) {
        siblings = DataUtil.getCategories((Category) logicalContainer);
      } else if (logicalContainer instanceof Context) {
        siblings = ModelUtil.getCategories((Context) logicalContainer);
      }
    } else if (target_p instanceof Context) {
      typeName = ContextsPackage.Literals.CONTEXT.getName();
      RootElement rootElement = ModelHandlerActivator.getDefault().getDataHandler().getAllContexts();
      siblings = rootElement.getContexts();
    }

    checkNameUniqueness(target_p, siblings, context_p, typeName);

    return null;
  }
}
