/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.viewer.component;

import java.util.Collection;
import java.util.List;

import org.eclipse.jface.viewers.TreeNode;

import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.Category;
import com.thalesgroup.orchestra.framework.model.contexts.Context;

/**
 * A {@link TreeNode} specialized for the handling of a {@link Context} model element.
 * @author t0076261
 */
public class ContextNode extends AbstractNode<Context> {
  /**
   * Constructor.
   * @param context_p
   */
  public ContextNode(Context context_p) {
    super(context_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.viewer.component.AbstractNode#doGetChildren(java.util.Collection)
   */
  @Override
  protected void doGetChildren(Collection<TreeNode> resultingCollection_p) {
    // Get all categories.
    List<Category> categories = ModelUtil.getCategories(getValue());
    for (Category category : categories) {
      // Add to resulting collection.
      resultingCollection_p.add(RootElementNode.createNode(category));
    }
  }
}