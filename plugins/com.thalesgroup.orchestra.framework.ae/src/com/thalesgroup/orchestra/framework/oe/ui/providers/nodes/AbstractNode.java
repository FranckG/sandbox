/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.oe.ui.providers.nodes;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.TreeNode;

/**
 * @author S0024585
 */
public abstract class AbstractNode<E> extends TreeNode {

  /**
   * Constructor
   * @param value_p
   */
  public AbstractNode(E value_p) {
    super(value_p);
  }

  /**
   * Constructor
   * @param value_p
   * @param parent_p
   */
  public AbstractNode(E value_p, AbstractNode<?> parent_p) {
    super(value_p);
    setParent(parent_p);
  }

  /**
   * @see org.eclipse.jface.viewers.TreeNode#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object_p) {
    // Super method calls equals on the value contained in nodes but this is not enough discriminating (especially for StringNodes).
    return this == object_p;
  }

  /**
   * @return the label to display
   */
  public abstract String getLabel();

  /**
   * @return the path for this node.
   */
  public IPath getNodePath(boolean includeRoot_p) {
    if (getParent() != null) {
      return getParent().getNodePath(includeRoot_p).append(getNodeSegment());
    }
    if (includeRoot_p) {
      return new Path(getNodeSegment());
    }
    return new Path(""); //$NON-NLS-1$
  }

  /**
   * @return the value for the segment in the path
   */
  public abstract String getNodeSegment();

  /**
   * @see org.eclipse.jface.viewers.TreeNode#getParent()
   */
  @Override
  public AbstractNode<?> getParent() {
    return (AbstractNode<?>) super.getParent();
  }

  /**
   * @return the top most node
   */
  public AbstractNode<?> getRootNode() {
    if (getParent() != null) {
      return getParent().getRootNode();
    }
    return this;
  }

  /**
   * @see org.eclipse.jface.viewers.TreeNode#getValue()
   */
  @SuppressWarnings("unchecked")
  @Override
  public E getValue() {
    return (E) super.getValue();
  }
}
