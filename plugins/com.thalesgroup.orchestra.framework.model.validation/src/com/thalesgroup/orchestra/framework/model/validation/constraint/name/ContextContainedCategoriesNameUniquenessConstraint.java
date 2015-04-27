/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.validation.constraint.name;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;

import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.Category;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.contexts.NamedElement;

/**
 * Name uniqueness constraint for categories contained in a context.
 * @author t0076261
 */
public class ContextContainedCategoriesNameUniquenessConstraint extends AbstractNameUniquenessConstraint<Context> {
  /**
   * @see com.thalesgroup.orchestra.framework.model.validation.constraint.name.AbstractNameUniquenessConstraint#getChildren(com.thalesgroup.orchestra.framework.model.contexts.ModelElement)
   */
  @Override
  protected Collection<NamedElement> getChildren(Context target_p) {
    // Get all categories.
    List<Category> categories = ModelUtil.getCategories(target_p);
    // Remove pending elements from resulting list.
    Collection<Object> pendingCategories = EcoreUtil.getObjectsByType(categories, ContextsPackage.Literals.PENDING_ELEMENTS_CATEGORY);
    categories.removeAll(pendingCategories);
    // Return a copy as a result.
    return new ArrayList<NamedElement>(categories);
  }
}