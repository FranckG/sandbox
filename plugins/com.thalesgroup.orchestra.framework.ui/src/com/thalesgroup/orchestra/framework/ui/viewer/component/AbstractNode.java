/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.viewer.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.TreeNode;

import com.thalesgroup.orchestra.framework.model.contexts.Context;

/**
 * An abstract node for a model element, that is able to retrieve its current edition {@link Context} through parents/children links.
 * @author t0076261
 */
public abstract class AbstractNode<E> extends TreeNode implements IAdaptable {
  /**
   * Used to know if children have been computed (and to compute them only once).
   */
  private boolean _doGetChildrenDone;

  /**
   * Constructor.
   * @param value_p
   */
  public AbstractNode(E value_p) {
    super(value_p);
    _doGetChildrenDone = false;
  }

  /**
   * Add a new child node to current one.
   * @param childNode_p A not <code>null</code> and not already known child.
   */
  public void addChildNode(AbstractNode<?> childNode_p) {
    // Precondition.
    if (null == childNode_p) {
      return;
    }
    AbstractNode<?> childNode = getChildNode(childNode_p.getValue());
    // Node already exists, stop here.
    if (null != childNode) {
      return;
    }
    // Set parent link.
    childNode_p.setParent(this);
    // Resulting list.
    List<TreeNode> result = null;
    // Populate with existing children.
    TreeNode[] nodes = getChildren();
    if (null != nodes) {
      result = new ArrayList<TreeNode>(Arrays.asList(nodes));
    }
    // Or create a new one.
    if (null == result) {
      result = new ArrayList<TreeNode>(1);
    }
    // Add new child.
    result.add(childNode_p);
    // Set resulting children.
    setChildren(result.toArray(new TreeNode[result.size()]));
  }

  /**
   * Do get children for current node.<br>
   * Add them to specified resulting collection.<br>
   * Children/parent links are resolved automatically, there is no need to set them manually.
   * @param resultingCollection_p A not <code>null</code> collection of {@link TreeNode}s.
   */
  protected abstract void doGetChildren(Collection<TreeNode> resultingCollection_p);

  /**
   * @see org.eclipse.jface.viewers.TreeNode#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object_p) {
    return this == object_p;
  }

  /**
   * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
   */
  public Object getAdapter(Class adapter_p) {
    if (EObject.class.equals(adapter_p)) {
      return getValue();
    }
    return null;
  }

  /**
   * Get child node for specified value.<br>
   * This includes current level value.<br>
   * Note that it does search recursively at all levels, if it fails at current one.
   * @param value_p
   * @return
   */
  public AbstractNode<?> getChildNode(Object value_p) {
    // Precondition.
    if (null == value_p) {
      return null;
    }
    // Check current value.
    if (value_p == getValue()) {
      return this;
    }
    // Get children.
    TreeNode[] children = getChildren();
    // Nothing to look for, stop here.
    if (null == children) {
      return null;
    }
    // Cycle through children.
    AbstractNode<?> result = null;
    for (TreeNode treeNode : children) {
      // Assuming this is an AbstractNode.
      result = ((AbstractNode<?>) treeNode).getChildNode(value_p);
      if (null != result) {
        break;
      }
    }
    return result;
  }

  /**
   * @see org.eclipse.jface.viewers.TreeNode#getChildren()
   */
  @Override
  public TreeNode[] getChildren() {
    TreeNode[] nodes = super.getChildren();
    // If computation is already done -> don't redo computation and simply return children.
    if (_doGetChildrenDone) {
      // Avoid to return null array, return an empty array instead.
      return (null != nodes) ? nodes : new TreeNode[0];
    }
    // Do get children.
    Collection<TreeNode> result = new ArrayList<TreeNode>(0);
    doGetChildren(result);
    _doGetChildrenDone = true;
    // Convert to expected format.
    nodes = result.toArray(new TreeNode[result.size()]);
    // Set as children.
    setChildren(nodes);
    // Set parent to current node.
    for (TreeNode node : nodes) {
      node.setParent(this);
    }
    return nodes;
  }

  /**
   * Get edition context for this node.
   * @return <code>null</code> if no context could be found, the context of edition otherwise.
   */
  public Context getEditionContext() {
    TreeNode parent = this;
    while (null != parent) {
      Object currentValue = parent.getValue();
      if (currentValue instanceof Context) {
        return (Context) currentValue;
      }
      parent = parent.getParent();
    }
    return null;
  }

  /**
   * @see org.eclipse.jface.viewers.TreeNode#getParent()
   */
  @Override
  public AbstractNode<?> getParent() {
    return (AbstractNode<?>) super.getParent();
  }

  /**
   * @see org.eclipse.jface.viewers.TreeNode#getValue()
   */
  @SuppressWarnings("unchecked")
  @Override
  public E getValue() {
    return (E) super.getValue();
  }

  /**
   * @see org.eclipse.jface.viewers.TreeNode#hasChildren()
   */
  @Override
  public boolean hasChildren() {
    // Force nor computation, neither the need for children to be set.
    return true;
  }

  /**
   * This method must be called to force calculation of this node children on the next getChildren() call.<br>
   * i.e. : every children of this node will be recreated when getChildren() will be called (so the whole tree starting from this node will be recreated).
   */
  public void invalidateChildrenList() {
    _doGetChildrenDone = false;
  }

  /**
   * Remove specified child node from current one.
   * @param childNode_p A not <code>null</code> and already known child.
   */
  public void removeChildNode(AbstractNode<?> childNode_p) {
    // Precondition.
    if (null == childNode_p) {
      return;
    }
    // Unset parent link.
    childNode_p.setParent(null);
    // Resulting list.
    List<TreeNode> result = null;
    // Populate with existing children.
    TreeNode[] nodes = getChildren();
    if (null != nodes) {
      result = new ArrayList<TreeNode>(Arrays.asList(nodes));
      // Child is unknown, stop here.
      if (!result.contains(childNode_p)) {
        return;
      }
      // Remove child.
      result.remove(childNode_p);
      // Set resulting children.
      setChildren(result.toArray(new TreeNode[result.size()]));
    }
  }
}