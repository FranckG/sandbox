/** 
 * <p>THALES Services - Engineering & Process Management</p>
 * <p>THALES Part Number 16 262 601</p>
 * <p>Copyright (c) 2002-2008 : THALES Services - Engineering & Process Management</p>
 */
package com.thalesgroup.orchestra.framework.oe.ui.actions;

import java.util.Iterator;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Tree;

import com.thalesgroup.orchestra.framework.oe.OrchestraExplorerActivator;
import com.thalesgroup.orchestra.framework.oe.ui.constants.IImageConstants;

/**
 * This class will expand the selected element or all elements if no selection is made
 * @author s0018747
 */
public class ExpandAllAction extends Action {
  private final TreeViewer _treeViewer;

  public ExpandAllAction(final TreeViewer treeViewer) {
    this._treeViewer = treeViewer;
    this.setText(""); //$NON-NLS-1$
    this.setToolTipText(Messages.ExpandAllAction_ExpandAllTooltip);
    this.setImageDescriptor(OrchestraExplorerActivator.getDefault().getImageDescriptor(IImageConstants.DESC_EXPAND));
  }

  /**
   * @see org.eclipse.jface.action.Action#run()
   */
  @SuppressWarnings("unchecked")
  @Override
  public void run() {

    IStructuredSelection selection = (IStructuredSelection) _treeViewer.getSelection();
    setRedraw(false);
    try {
      if (selection.isEmpty()) {
        _treeViewer.expandAll();
      } else {
        Iterator<Object> itSelection = selection.iterator();
        Object element = null;
        while (itSelection.hasNext()) {
          element = itSelection.next();
          if (element != null)
            _treeViewer.expandToLevel(element, AbstractTreeViewer.ALL_LEVELS);
        }
      }
    } finally {
      setRedraw(true);
    }
  }

  /**
   * Redraw assembler tree : <li>redraw = true => redraw tree</li> <li>redraw = false => avoid tree redrawing</li>
   * @param redraw
   */
  public void setRedraw(boolean redraw) {
    if (_treeViewer != null) {
      Tree tree = _treeViewer.getTree();
      if (tree != null && !tree.isDisposed()) {
        tree.setRedraw(redraw);
      }
    }
  }

}
