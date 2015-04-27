/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.viewer;

import java.text.MessageFormat;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.InstallationCategory;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;
import com.thalesgroup.orchestra.framework.ui.internal.viewer.PatternFilter;
import com.thalesgroup.orchestra.framework.ui.viewer.component.AbstractNode;
import com.thalesgroup.orchestra.framework.ui.viewer.component.ContextNode;

/**
 * @author s0018747
 *
 */
/**
 * Contexts specific label provider.
 * @author t0076261
 */
public class ContextsLabelProvider extends AbstractLabelProvider implements IFontProvider {
  /**
   * Font reminders.
   */
  protected Font _biggerItalicFont;
  protected Font _boldFont;

  protected PatternFilter _filter;
  protected TreeViewer _viewer;

  /**
   * @param viewer_p
   * @param filter_p
   */
  public ContextsLabelProvider(TreeViewer viewer_p, PatternFilter filter_p) {
    super();
    _viewer = viewer_p;
    _filter = filter_p;
    _boldFont = new Font(_viewer.getControl().getDisplay(), _viewer.getTree().getFont().toString(), 9, SWT.BOLD);
    _biggerItalicFont = new Font(_viewer.getControl().getDisplay(), _viewer.getTree().getFont().toString(), 11, SWT.NORMAL);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.viewer.AbstractLabelProvider#dispose()
   */
  @Override
  public void dispose() {
    try {
      super.dispose();
    } finally {
      if (null != _boldFont) {
        _boldFont.dispose();
        _boldFont = null;
      }
      if (null != _biggerItalicFont) {
        _biggerItalicFont.dispose();
        _biggerItalicFont = null;
      }
    }
  }

  /**
   * Get background color for specified element in specified edition context.
   * @param element_p
   * @param editionContext_p
   * @return
   */
  protected Color getBackground(EObject element_p, Context editionContext_p) {
    // Highlight selected versions.
    if ((element_p instanceof InstallationCategory) && DataUtil.isSelectedVersion((InstallationCategory) element_p, editionContext_p)) {
      return Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW);
    }
    return null;
  }

  /**
   * Get font for specified element in specified edition context.
   * @param object_p
   * @param editionContext_p
   * @return
   */
  protected Font getFont(EObject object_p, final Context editionContext_p) {
    if (_filter.highlightLabel(getText(object_p, editionContext_p))) {
      // Highlight filtered nodes.
      return _boldFont;
    } else if ((object_p instanceof Context) && ModelHandlerActivator.getDefault().getDataHandler().isCurrentContext((Context) object_p)) {
      // Highlight current context node.
      return _boldFont;
    } else if ((object_p instanceof InstallationCategory) && DataUtil.isSelectedVersion((InstallationCategory) object_p, editionContext_p)) {
      return _biggerItalicFont;
    }
    return null;
  }

  /**
   * @see org.eclipse.jface.viewers.IFontProvider#getFont(java.lang.Object)
   */
  public Font getFont(Object element_p) {
    // Adapt to EObject, if needed.
    Object element = element_p;
    if (element_p instanceof IAdaptable) {
      element = ((IAdaptable) element).getAdapter(EObject.class);
    }
    // Get edition context from tree node.
    Context editionContext = null;
    if (element_p instanceof AbstractNode<?>) {
      editionContext = ((AbstractNode<?>) element_p).getEditionContext();
    }
    // Go for it.
    if (element instanceof EObject) {
      return getFont((EObject) element, editionContext);
    }
    return null;
  }

  /**
   * @see org.eclipse.jface.viewers.CellLabelProvider#getToolTipDisplayDelayTime(java.lang.Object)
   */
  @Override
  public int getToolTipDisplayDelayTime(Object object_p) {
    return 200;
  }

  /**
   * @see org.eclipse.jface.viewers.CellLabelProvider#getToolTipFont(java.lang.Object)
   */
  @Override
  public Font getToolTipFont(Object object_p) {
    return null;
  }

  /**
   * @see org.eclipse.jface.viewers.CellLabelProvider#getToolTipImage(java.lang.Object)
   */
  @Override
  public Image getToolTipImage(Object object_p) {
    if (object_p instanceof ContextNode) {
      Context context = ((ContextNode) object_p).getValue();
      return getImage(context, context);
    }
    return null;
  }

  /**
   * @see org.eclipse.jface.viewers.CellLabelProvider#getToolTipStyle(java.lang.Object)
   */
  @Override
  public int getToolTipStyle(Object object_p) {
    // SWT.LEFT
    // SWT.RIGHT
    // SWT.CENTER
    // SWT.SHADOW_IN
    // SWT.SHADOW_OUT
    // SWT.SHADOW_NONE
    return SWT.SHADOW_OUT;
  }

  /**
   * @see org.eclipse.jface.viewers.CellLabelProvider#getToolTipText(java.lang.Object)
   */
  @Override
  public String getToolTipText(Object element_p) {
    if (!(element_p instanceof ContextNode)) {
      // Tooltips available only for context nodes.
      return null;
    }
    Context context = ((ContextNode) element_p).getValue();
    RootContextsProject contextProject = ProjectActivator.getInstance().getProjectHandler().getProjectFromContextUri(context.eResource().getURI());
    StringBuilder toolTipContent = new StringBuilder();
    // Context location (in/outside workspace).
    String workspaceLocation =
        contextProject.isInWorkspace() ? Messages.ContextsViewer_Tooltip_Property_Value_InWorkspace
                                      : Messages.ContextsViewer_Tooltip_Property_Value_OutsideWorkspace;
    toolTipContent.append(MessageFormat.format(Messages.ContextsViewer_Tooltip_Property_ContextLocation, workspaceLocation));
    // Context physical path.
    toolTipContent.append("\n"); //$NON-NLS-1$
    toolTipContent.append(MessageFormat.format(Messages.ContextsViewer_Tooltip_Property_PhysicalPath, contextProject.getLocation()));
    // Parent project location.
    toolTipContent.append("\n"); //$NON-NLS-1$
    String rawParentProjectLocation = contextProject._contextsProject.getParentProject();
    if (null == rawParentProjectLocation) {
      toolTipContent.append(MessageFormat.format(Messages.ContextsViewer_Tooltip_Property_ParentPhysicalPath,
          Messages.ContextsViewer_Tooltip_Property_Value_NoParentPath));
    } else {
      IPath substitutedParentProjectPath = new Path(DataUtil.getSubstitutedValue(rawParentProjectLocation, context)).removeTrailingSeparator();
      IPath rawParentProjectPath = new Path(rawParentProjectLocation).removeTrailingSeparator();
      toolTipContent.append(MessageFormat.format(Messages.ContextsViewer_Tooltip_Property_ParentPhysicalPath, substitutedParentProjectPath));
      if (!substitutedParentProjectPath.equals(rawParentProjectPath)) {
        toolTipContent.append(MessageFormat.format(Messages.ContextsViewer_Tooltip_Property_ParentRawPhysicalPath, rawParentProjectPath));
      }
    }
    // Description.
    if (null != context.getDescription() && !ICommonConstants.EMPTY_STRING.equals(context.getDescription())) {
      toolTipContent.append("\n"); //$NON-NLS-1$
      toolTipContent.append(MessageFormat.format(Messages.ContextsViewer_Tooltip_Property_Description, context.getDescription()));
    }
    return toolTipContent.toString();
  }

  /**
   * @see org.eclipse.jface.viewers.CellLabelProvider#getToolTipTimeDisplayed(java.lang.Object)
   */
  @Override
  public int getToolTipTimeDisplayed(Object object_p) {
    return 30000;
  }

  /**
   * @see org.eclipse.jface.viewers.CellLabelProvider#update(org.eclipse.jface.viewers.ViewerCell)
   */
  @Override
  public void update(ViewerCell cell_p) {
    AbstractNode<?> node = (AbstractNode<?>) cell_p.getElement();
    Context context = node.getEditionContext();
    EObject element = (EObject) node.getValue();
    cell_p.setImage(getImage(element, context));
    cell_p.setText(getText(element, context));
    cell_p.setFont(getFont(element, context));
    cell_p.setForeground(getForeground(element, context));
    cell_p.setBackground(getBackground(element, context));
  }
}