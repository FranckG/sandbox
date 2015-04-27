/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.validation.constraint.name;

import java.util.ArrayList;
import java.util.Collection;

import com.thalesgroup.orchestra.framework.model.contexts.Category;
import com.thalesgroup.orchestra.framework.model.contexts.NamedElement;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;

/**
 * Name uniqueness constraint for categories contained in a category.
 * @author t0076261
 */
public class CategoryContainedCategoriesNameUniquenessConstraint extends AbstractNameUniquenessConstraint<Category> {
  /**
   * @see com.thalesgroup.orchestra.framework.model.validation.constraint.name.AbstractNameUniquenessConstraint#getChildren(com.thalesgroup.orchestra.framework.model.contexts.ModelElement)
   */
  @Override
  protected Collection<NamedElement> getChildren(Category target_p) {
    return new ArrayList<NamedElement>(DataUtil.getCategories(target_p, getAskingContext()));
  }
}