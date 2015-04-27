/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.viewer.component;

import java.util.Collection;
import java.util.List;

import org.eclipse.jface.viewers.TreeNode;

import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;

/**
 * A {@link TreeNode} specialized for the handling of a {@link AbstractVariable} model element.
 * @author t0076261
 */
public class VariableNode extends AbstractNode<AbstractVariable> {
  /**
   * Constructor
   * @param variable_p
   */
  public VariableNode(AbstractVariable variable_p) {
    super(variable_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.viewer.component.AbstractNode#doGetChildren(java.util.Collection)
   */
  @Override
  protected void doGetChildren(Collection<TreeNode> resultingCollection_p) {
    // Get raw values, substitution will be performed by the label provider.
    List<VariableValue> values = DataUtil.getRawValues(getValue(), getEditionContext());
    for (VariableValue variableValue : values) {
      resultingCollection_p.add(RootElementNode.createNode(variableValue));
    }
  }
}