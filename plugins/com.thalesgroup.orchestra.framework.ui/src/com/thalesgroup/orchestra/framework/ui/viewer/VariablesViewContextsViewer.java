/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.viewer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ModelElement;
import com.thalesgroup.orchestra.framework.ui.internal.viewer.ModelElementFilter;
import com.thalesgroup.orchestra.framework.ui.view.VariablesView;
import com.thalesgroup.orchestra.framework.ui.viewer.component.AbstractNode;
import com.thalesgroup.orchestra.framework.ui.viewer.component.VariableNode;

/**
 * ContextsViewer with ContentProvider and LabelProvider using the ModelElementFilter of VariablesView.
 * @author T0052089
 */
public class VariablesViewContextsViewer extends ContextsViewer {
  /**
   * Current model element filter (from VariablesView).
   */
  protected final ModelElementFilter _currentModelElementFilter = VariablesView.getSharedInstance().getModelElementFilter();

  /**
   * @see com.thalesgroup.orchestra.framework.ui.viewer.ContextsViewer#createContentProvider()
   */
  @Override
  protected ContextsContentProvider createContentProvider() {
    // Use a filtering ContentProvider to avoid interferences with PatternFilter in FilteredTree.
    return new ContextsContentProvider() {
      /**
       * @see org.eclipse.jface.viewers.TreeNodeContentProvider#getChildren(java.lang.Object)
       */
      @Override
      public Object[] getChildren(Object parentElement_p) {
        Object[] children = super.getChildren(parentElement_p);
        if (!_currentModelElementFilter.getCurrentFilterState().isFiltering()) {
          // Filter not activated -> return children list as it is.
          return children;
        }
        if (parentElement_p instanceof VariableNode) {
          // VariableValue nodes are not filtered.
          return children;
        }
        List<Object> selectedChildren = new ArrayList<Object>();
        for (Object child : children) {
          // Select children using ModelElementFilter.
          if (child instanceof AbstractNode && ((AbstractNode) child).getValue() instanceof ModelElement) {
            ModelElement modelElement = (ModelElement) ((AbstractNode) child).getValue();
            Context editionContext = ((AbstractNode) child).getEditionContext();
            if (_currentModelElementFilter.select(modelElement, editionContext)) {
              selectedChildren.add(child);
            }
          }
        }
        return selectedChildren.toArray();
      }
    };
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.viewer.ContextsViewer#createLabelProvider()
   */
  @Override
  protected ContextsLabelProvider createLabelProvider() {
    return new ContextsLabelProvider(_viewer, _filter) {
      /**
       * @see com.thalesgroup.orchestra.framework.ui.viewer.ContextsLabelProvider#getBackground(org.eclipse.emf.ecore.EObject,
       *      com.thalesgroup.orchestra.framework.model.contexts.Context)
       */
      @Override
      protected Color getBackground(EObject element_p, Context editionContext_p) {
        Color backgroupColor = super.getBackground(element_p, editionContext_p);
        // Ask ModelElementFilter if this element must be highlighted.
        if (null == backgroupColor && (element_p instanceof ModelElement) && _currentModelElementFilter.highlight((ModelElement) element_p, editionContext_p)) {
          return Display.getCurrent().getSystemColor(SWT.COLOR_YELLOW);
        }
        return null;
      }
    };
  }
}
