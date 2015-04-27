/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.viewer.component;

import java.util.Collection;

import org.eclipse.jface.viewers.TreeNode;

import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;

/**
 * @author t0076261
 */
public class VariableValueNode extends AbstractNode<VariableValue> {
  /**
   * Constructor
   * @param variableValue_p
   */
  public VariableValueNode(VariableValue variableValue_p) {
    super(variableValue_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.viewer.component.AbstractNode#doGetChildren(java.util.Collection)
   */
  @Override
  protected void doGetChildren(Collection<TreeNode> resultingCollection_p) {
    // No child, do nothing.
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.viewer.component.AbstractNode#hasChildren()
   */
  @Override
  public boolean hasChildren() {
    return false;
  }
}