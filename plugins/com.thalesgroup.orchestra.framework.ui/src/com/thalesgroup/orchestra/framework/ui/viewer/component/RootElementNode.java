/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.viewer.component;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.TreeNode;

import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Category;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch;
import com.thalesgroup.orchestra.framework.model.handler.data.RootElement;

/**
 * A {@link TreeNode} specialized for the handling of a {@link RootElement}.
 * @author t0076261
 */
public class RootElementNode extends AbstractNode<RootElement> {
  /**
   * Factory switch.
   */
  private static ContextsSwitch<AbstractNode<?>> __nodeFactorySwitch;

  /**
   * Constructor.
   * @param rootElement_p
   */
  public RootElementNode(RootElement rootElement_p) {
    super(rootElement_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.viewer.component.AbstractNode#doGetChildren(java.util.Collection)
   */
  @Override
  protected void doGetChildren(Collection<TreeNode> resultingCollection_p) {
    Collection<Context> contexts = getValue().getContexts();
    for (Context context : contexts) {
      resultingCollection_p.add(RootElementNode.createNode(context));
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.viewer.component.AbstractNode#getAdapter(java.lang.Class)
   */
  @Override
  public Object getAdapter(Class adapter_p) {
    // Break adaptation to EObject.
    return null;
  }

  /**
   * Create a new node for specified element.
   * @param element_p
   * @return <code>null</code> if no node is handling specified element, or element is <code>null</code>.
   */
  public static AbstractNode<?> createNode(Object element_p) {
    if (!(element_p instanceof EObject)) {
      return null;
    }
    // Create factory, if needed.
    if (null == __nodeFactorySwitch) {
      __nodeFactorySwitch = new ContextsSwitch<AbstractNode<?>>() {
        /**
         * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseAbstractVariable(com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable)
         */
        @Override
        public AbstractNode<?> caseAbstractVariable(AbstractVariable object_p) {
          return new VariableNode(object_p);
        }

        /**
         * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseCategory(com.thalesgroup.orchestra.framework.model.contexts.Category)
         */
        @Override
        public AbstractNode<?> caseCategory(Category object_p) {
          return new CategoryNode(object_p);
        }

        /**
         * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseContext(com.thalesgroup.orchestra.framework.model.contexts.Context)
         */
        @Override
        public AbstractNode<?> caseContext(Context object_p) {
          return new ContextNode(object_p);
        }

        /**
         * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseVariableValue(com.thalesgroup.orchestra.framework.model.contexts.VariableValue)
         */
        @Override
        public AbstractNode<?> caseVariableValue(VariableValue object_p) {
          return new VariableValueNode(object_p);
        }
      };
    }
    // Call factory.
    return __nodeFactorySwitch.doSwitch((EObject) element_p);
  }
}