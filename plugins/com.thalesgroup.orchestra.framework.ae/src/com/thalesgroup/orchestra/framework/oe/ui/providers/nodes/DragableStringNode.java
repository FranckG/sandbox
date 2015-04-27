/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.oe.ui.providers.nodes;

/**
 * A {@link StringNode} that can be drag'n'droped from the Orchestra Explorer.
 * @author t0076261
 */
public class DragableStringNode extends StringNode {
  /**
   * Constructor.
   * @param value_p
   * @param parent_p
   */
  public DragableStringNode(String value_p, AbstractNode<?> parent_p) {
    super(value_p, parent_p);
  }

  /**
   * @see org.eclipse.jface.viewers.TreeNode#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object_p) {
    if (object_p instanceof DragableStringNode) {
      return getLabel().equals(((DragableStringNode) object_p).getLabel());
    }
    return super.equals(object_p);
  }
}