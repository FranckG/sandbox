/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.viewer.component;

import java.util.Collection;

import org.eclipse.jface.viewers.TreeNode;

import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Category;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;

/**
 * A {@link TreeNode} specialized for the handling of a {@link Category} model element.
 * @author t0076261
 */
public class CategoryNode extends AbstractNode<Category> {
  /**
   * Constructor.
   * @param category_p
   */
  public CategoryNode(Category category_p) {
    super(category_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.viewer.component.AbstractNode#doGetChildren(java.util.Collection)
   */
  @Override
  protected void doGetChildren(Collection<TreeNode> resultingCollection_p) {
    Category category = getValue();
    // Add all sub-categories.
    for (Category subCategory : DataUtil.getCategories(category, getEditionContext())) {
      resultingCollection_p.add(RootElementNode.createNode(subCategory));
    }
    // Add all variables.
    for (AbstractVariable variable : DataUtil.getVariables(category, getEditionContext())) {
      resultingCollection_p.add(RootElementNode.createNode(variable));
    }
  }
}