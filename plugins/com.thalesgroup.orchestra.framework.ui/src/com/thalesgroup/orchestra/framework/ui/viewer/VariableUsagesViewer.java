/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.viewer;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;
import com.thalesgroup.orchestra.framework.model.handler.helper.RenameVariableHelper;
import com.thalesgroup.orchestra.framework.ui.activator.OrchestraFrameworkUiActivator;
import com.thalesgroup.orchestra.framework.ui.internal.viewer.PatternFilter;
import com.thalesgroup.orchestra.framework.ui.view.ISharedActionsHandler;
import com.thalesgroup.orchestra.framework.ui.viewer.component.CategoryNode;
import com.thalesgroup.orchestra.framework.ui.viewer.component.ContextNode;
import com.thalesgroup.orchestra.framework.ui.viewer.component.VariableNode;

/**
 * @author s0040806
 */
public class VariableUsagesViewer extends ContextsViewer {

  protected String _odmVariable;

  protected Context _context;
  protected Set<Context> _referencingContexts;
  Set<AbstractVariable> _referencingVariables;

  private RenameVariableHelper _renameHelper;

  public VariableUsagesViewer(Variable variable_p, Context context_p) {
    _context = context_p;

    _renameHelper = new RenameVariableHelper(variable_p, context_p);
    _referencingVariables = _renameHelper.getReferencingVariables();
    _referencingContexts = _renameHelper.getReferencingContexts();
  }

  /**
   * @param treeNode_p
   * @return
   */
  protected boolean hasReferencingVariable(TreeNode treeNode_p) {
    for (TreeNode node : treeNode_p.getChildren()) {
      if (node instanceof CategoryNode) {
        if (hasReferencingVariable(node)) {
          return true;
        }
      } else if (node instanceof VariableNode) {
        AbstractVariable variable = ((VariableNode) node).getValue();
        if (_referencingVariables.contains(variable)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Check if a variable is part of the referencing variables
   * @param variable_p
   * @return
   */
  protected boolean isReferencingVariable(VariableNode variable_p) {
    AbstractVariable variable = variable_p.getValue();
    if (_referencingVariables.contains(variable)) {
      return true;
    }
    return false;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.viewer.ContextsViewer#customizeViewer(org.eclipse.jface.viewers.TreeViewer,
   *      org.eclipse.ui.forms.widgets.FormToolkit, org.eclipse.swt.widgets.Composite, org.eclipse.ui.IWorkbenchPartSite)
   */
  @Override
  protected void customizeViewer(TreeViewer treeViewer_p, FormToolkit toolkit_p, Composite parent_p, IWorkbenchPartSite site_p) {
    treeViewer_p.addFilter(new ViewerFilter() {
      @Override
      public boolean select(Viewer viewer_p, Object parentElement_p, Object element_p) {
        if (element_p instanceof ContextNode) {
          Context context = ((ContextNode) element_p).getValue();
          if (_referencingContexts.contains(context) && hasReferencingVariable((TreeNode) element_p)) {
            return true;
          }
        } else if (element_p instanceof CategoryNode) {
          return hasReferencingVariable((TreeNode) element_p);
        } else if (element_p instanceof VariableNode) {
          return isReferencingVariable((VariableNode) element_p);
        }
        return false;
      }
    });
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.viewer.ContextsViewer#createViewerParentComposite(org.eclipse.ui.forms.widgets.FormToolkit,
   *      org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Composite createViewerParentComposite(FormToolkit toolkit_p, Composite parent_p) {
    ToolBar toolBar = new ToolBar(parent_p, SWT.FLAT | SWT.HORIZONTAL);
    ToolItem expandItem = new ToolItem(toolBar, SWT.PUSH);
    expandItem.setImage(OrchestraFrameworkUiActivator.getDefault().getImageDescriptor("expand_all.gif").createImage());
    ToolItem collapseItem = new ToolItem(toolBar, SWT.PUSH);
    collapseItem.setImage(OrchestraFrameworkUiActivator.getDefault().getImageDescriptor("collapse_all.gif").createImage());

    expandItem.addSelectionListener(new SelectionListener() {
      public void widgetSelected(SelectionEvent e_p) {
        _viewer.expandAll();
      }

      public void widgetDefaultSelected(SelectionEvent e_p) {
        // Nothing to do
      }
    });

    collapseItem.addSelectionListener(new SelectionListener() {
      public void widgetSelected(SelectionEvent e_p) {
        _viewer.collapseAll();
      }

      public void widgetDefaultSelected(SelectionEvent e_p) {
        // Nothing to do
      }
    });

    // Do not create a section.
    return parent_p;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.viewer.ContextsViewer#createContextualMenu(org.eclipse.ui.IWorkbenchPartSite,
   *      com.thalesgroup.orchestra.framework.ui.view.ISharedActionsHandler)
   */
  @Override
  protected void createContextualMenu(IWorkbenchPartSite site_p, ISharedActionsHandler sharedActionsHandler_p) {
    // No contextual menu
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.viewer.ContextsViewer#createLabelProvider()
   */
  @Override
  protected ContextsLabelProvider createLabelProvider() {
    VariableUsagesLabelProvider labelProvider = new VariableUsagesLabelProvider(_viewer, _filter);
    setLabelProvider(labelProvider);
    return labelProvider;
  }

  public class VariableUsagesLabelProvider extends ContextsLabelProvider {
    /**
     * @param viewer_p
     * @param filter_p
     */
    public VariableUsagesLabelProvider(TreeViewer viewer_p, PatternFilter filter_p) {
      super(viewer_p, filter_p);
    }

    /**
     * @see com.thalesgroup.orchestra.framework.ui.viewer.ContextsLabelProvider#getFont(org.eclipse.emf.ecore.EObject,
     *      com.thalesgroup.orchestra.framework.model.contexts.Context)
     */
    @Override
    protected Font getFont(EObject object_p, Context editionContext_p) {
      // Highlight variables in bold
      if (object_p instanceof Variable) {
        return _boldFont;
      }
      return super.getFont(object_p, editionContext_p);
    }
  }
}
