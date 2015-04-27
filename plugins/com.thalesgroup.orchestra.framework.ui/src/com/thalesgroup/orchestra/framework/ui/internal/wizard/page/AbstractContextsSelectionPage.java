/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.wizard.page;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.core.runtime.Path;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreeNodeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.common.helper.ProjectHelper;
import com.thalesgroup.orchestra.framework.contextsproject.ContextReference;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsFactory;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;
import com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage;

/**
 * @author t0076261
 */
public abstract class AbstractContextsSelectionPage extends AbstractFormsWizardPage {
  /**
   * Model.
   */
  protected final Map<String, DirectoryTreeNode> _directoryNodesMap;
  /**
   * Viewer.
   */
  protected CheckboxTreeViewer _viewer;

  /**
   * Constructor.
   * @param pageId_p
   */
  public AbstractContextsSelectionPage(String pageId_p) {
    super(pageId_p);
    _directoryNodesMap = new TreeMap<String, DirectoryTreeNode>();
  }

  /**
   * Add a new RootContextsProject in the tree.
   * @param rootContextsProject_p
   * @param isInConflict_p
   */
  protected void addNewElement(RootContextsProject rootContextsProject_p, boolean isInConflict_p) {
    String parentDirectory = new Path(rootContextsProject_p.getLocation()).removeLastSegments(1).toString();
    // Get (or create) DirectoryTreeNode for parent directory.
    DirectoryTreeNode parentDirectoryNode = _directoryNodesMap.get(parentDirectory);
    if (null == parentDirectoryNode) {
      parentDirectoryNode = new DirectoryTreeNode(parentDirectory);
      // Add the new DirectoryNode in the tree and in the model.
      _viewer.add(_viewer.getInput(), parentDirectoryNode);
      _directoryNodesMap.put(parentDirectory, parentDirectoryNode);
    }
    // Create a new node for the RootContextsProject to add.
    RootContextTreeNode rootContextTreeNode = new RootContextTreeNode(rootContextsProject_p);
    rootContextTreeNode.setIsInConflict(isInConflict_p);
    // Add it to the parent directory node.
    rootContextTreeNode.setParent(parentDirectoryNode);
    if (null == parentDirectoryNode.getChildren()) {
      // No children for now -> create a new array.
      parentDirectoryNode.setChildren(new RootContextTreeNode[] { rootContextTreeNode });
    } else {
      // Directory node already contains children, add the new context node.
      TreeNode[] newChildren = Arrays.copyOf(parentDirectoryNode.getChildren(), parentDirectoryNode.getChildren().length + 1);
      newChildren[newChildren.length - 1] = rootContextTreeNode;
      parentDirectoryNode.setChildren(newChildren);
    }
    // Add the new RootContextTreeNode in the tree.
    _viewer.add(parentDirectoryNode, rootContextTreeNode);
    // Show it.
    _viewer.reveal(rootContextTreeNode);
    // Keep horizontal scroll bar to the right.
    _viewer.getTree().getHorizontalBar().setSelection(0);
  }

  /**
   * Create contexts selection part.
   * @param parent_p
   * @param toolkit_p
   * @return
   */
  protected Composite createContextsSelectionPart(Composite parent_p, FormToolkit toolkit_p) {
    // Root composite.
    Composite composite = new Composite(parent_p, SWT.NONE);
    GridLayout layout = new GridLayout();
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    composite.setLayout(layout);
    composite.setLayoutData(new GridData(GridData.FILL_BOTH));
    // Create viewer.
    _viewer = new CheckboxTreeViewer(composite, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.SINGLE);
    _viewer.setContentProvider(new TreeNodeContentProvider() {
      /**
       * @see org.eclipse.jface.viewers.TreeNodeContentProvider#getElements(java.lang.Object)
       */
      @Override
      public Object[] getElements(Object inputElement_p) {
        if (inputElement_p instanceof List) {
          return ((Map) inputElement_p).values().toArray();
        }
        return super.getElements(inputElement_p);
      }
    });
    _viewer.setLabelProvider(new LabelProvider());
    _viewer.addCheckStateListener(new ContextsCheckStateListener());
    // Add viewer filter for {ContextReference}s and {RootContextsProject}s.
    _viewer.addFilter(new ViewerFilter() {
      @Override
      public boolean select(Viewer viewer_p, Object parentElement_p, Object element_p) {
        return (element_p instanceof DirectoryTreeNode) || (element_p instanceof RootContextTreeNode);
      }
    });
    // Set tool-tip support.
    ColumnViewerToolTipSupport.enableFor(_viewer);
    _viewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
    _viewer.setAutoExpandLevel(AbstractTreeViewer.ALL_LEVELS);
    // Set initial input.
    _viewer.setInput(_directoryNodesMap);
    refreshViewer();
    return composite;
  }

  /**
   * @see org.eclipse.jface.dialogs.DialogPage#dispose()
   */
  @Override
  public void dispose() {
    try {
      super.dispose();
    } finally {
      if (null != _viewer) {
        _viewer.getContentProvider().dispose();
        _viewer.getLabelProvider().dispose();
        _viewer = null;
      }
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.common.ui.wizards.AbstractFormsWizardPage#doCreateControl(org.eclipse.swt.widgets.Composite,
   *      org.eclipse.ui.forms.widgets.FormToolkit)
   */
  @Override
  protected Composite doCreateControl(Composite parent_p, FormToolkit toolkit_p) {
    Composite composite = createContextsSelectionPart(parent_p, toolkit_p);
    // Page not complete yet.
    setPageComplete(false);
    return composite;
  }

  /**
   * Get checked projects.
   * @return checked project list (an empty list if no project selected).
   */
  public List<RootContextsProject> getCheckedProjects() {
    Object[] checkedElements = _viewer.getCheckedElements();
    // Precondition.
    if ((null == checkedElements) || (0 == checkedElements.length)) {
      return Collections.emptyList();
    }
    List<RootContextsProject> result = new ArrayList<RootContextsProject>(0);
    for (Object object : checkedElements) {
      if (object instanceof RootContextTreeNode) {
        result.add(((RootContextTreeNode) object).getValue());
      }
    }
    return result;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.common.ui.wizards.AbstractFormsWizardPage#getPageImageDescriptor()
   */
  @Override
  protected ImageDescriptor getPageImageDescriptor() {
    return null;
  }

  /**
   * Handle new check event.
   * @param event_p
   */
  protected void handleCheckEvent(CheckStateChangedEvent event_p) {
    Object targetElement = event_p.getElement();
    boolean checked = event_p.getChecked();
    if (targetElement instanceof DirectoryTreeNode) {
      // Target element is a directory.
      TreeNode directoryNode = (DirectoryTreeNode) targetElement;
      // Select/Unselected all ENABLED children of the directory.
      for (TreeNode contextNode : directoryNode.getChildren()) {
        if (contextNode instanceof EnablableTreeNode) {
          if (((EnablableTreeNode) contextNode).isEnabled()) {
            _viewer.setChecked(contextNode, checked);
          }
        }
      }
    } else if (targetElement instanceof RootContextTreeNode) {
      // Target element is a context.
      TreeNode directoryNode = ((RootContextTreeNode) targetElement).getParent();
      // Manage directory node state (unchecked, grayed or checked).
      TreeNode[] contextNodes = directoryNode.getChildren();
      // Find how many children of this directory are selected.
      int nbContextsSelected = 0;
      for (TreeNode contextNode : contextNodes) {
        if (_viewer.getChecked(contextNode)) {
          ++nbContextsSelected;
        }
      }
      // If at least one child is selected, directory node is grayed,
      // if all children are selected, directory node is selected.
      _viewer.setChecked(directoryNode, nbContextsSelected > 0);
      _viewer.setGrayed(directoryNode, nbContextsSelected != contextNodes.length);
    }
  }

  /**
   * Fill the tree viewer.
   */
  public abstract void refreshViewer();

  /**
   * Check context nodes in the tree (directory nodes are also managed).
   * @param contextsToCheck_p contexts to check.
   */
  public void setCheckedProjects(List<RootContextsProject> contextsToCheck_p) {
    // Precondition.
    if ((null == contextsToCheck_p) || contextsToCheck_p.isEmpty()) {
      return;
    }
    // Clear selection - normally not needed.
    _viewer.setCheckedElements(new Object[0]);
    // Go through directory nodes.
    for (DirectoryTreeNode directoryNode : _directoryNodesMap.values()) {
      TreeNode[] childContextNodes = directoryNode.getChildren();
      int nbSelectedContexts = 0;
      // Go through context nodes.
      for (Object contextNode : childContextNodes) {
        if (contextNode instanceof RootContextTreeNode) {
          RootContextTreeNode rootContextTreeNode = (RootContextTreeNode) contextNode;
          // Seek the RootContextsProject in the list.
          if (contextsToCheck_p.contains(rootContextTreeNode.getValue())) {
            // Check context node.
            _viewer.setChecked(contextNode, true);
            ++nbSelectedContexts;
          }
        }
      }
      // Check directory (if at least 1 context is selected).
      _viewer.setChecked(directoryNode, nbSelectedContexts > 0);
      // Gray directory (if not all contexts are selected).
      _viewer.setGrayed(directoryNode, nbSelectedContexts != childContextNodes.length);
    }

  }

  /**
   * Set widgets enablement.
   * @param enabled_p
   */
  public void setEnabled(boolean enabled_p) {
    _viewer.getControl().setEnabled(enabled_p);
  }

  /**
   * Check state listener.
   * @author t0076261
   */
  protected class ContextsCheckStateListener implements ICheckStateListener {
    /**
     * @see org.eclipse.jface.viewers.ICheckStateListener#checkStateChanged(org.eclipse.jface.viewers.CheckStateChangedEvent)
     */
    public void checkStateChanged(CheckStateChangedEvent event_p) {
      // Check the target node enabled/disabled state.
      if ((event_p.getElement() instanceof EnablableTreeNode && ((EnablableTreeNode) event_p.getElement()).isEnabled())) {
        handleCheckEvent(event_p);
        // Manage page completion.
        setPageComplete(isPageComplete());
      } else {
        // Ignore this event if target node is disabled.
        _viewer.setChecked(event_p.getElement(), false);
      }
    }
  }

  /**
   * DirectoryTreeNode (parent node in the tree).
   * @author T0052089
   */
  protected class DirectoryTreeNode extends EnablableTreeNode {
    /**
     * @param directoryLocation_p
     */
    public DirectoryTreeNode(String directoryLocation_p) {
      super(directoryLocation_p);
    }

    /**
     * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractContextsSelectionPage.EnablableTreeNode#isEnabled()
     */
    @Override
    public boolean isEnabled() {
      // Directory node enablement state is computed using state of children.
      if (null == getChildren()) {
        return true;
      }
      for (TreeNode children : getChildren()) {
        if (children instanceof EnablableTreeNode && !((EnablableTreeNode) children).isEnabled()) {
          return false;
        }
      }
      return true;
    }
  }

  /**
   * TreeNode specialization managing enabled/disabled state.
   * @author T0052089
   */
  protected abstract class EnablableTreeNode extends TreeNode {
    public EnablableTreeNode(Object value_p) {
      super(value_p);
    }

    public abstract boolean isEnabled();
  }

  /**
   * A default implementation of expected label provider.
   * @author t0076261
   */
  protected class LabelProvider extends CellLabelProvider {
    /**
     * {@link ContextReference} image.
     */
    private Image _contextReferenceImage;

    /**
     * Constructor.
     */
    public LabelProvider() {
      // Get default icon for a context.
      Context mockContext = ContextsFactory.eINSTANCE.createContext();
      // Get the adapter from the factory.
      IItemLabelProvider itemLabelProvider =
          (IItemLabelProvider) ModelHandlerActivator.getDefault().getEditingDomain().getAdapterFactory().adapt(mockContext, IItemLabelProvider.class);
      if (null != itemLabelProvider) {
        _contextReferenceImage = ExtendedImageRegistry.getInstance().getImage(itemLabelProvider.getImage(mockContext));
      }
    }

    /**
     * Get the foreground color of a node regarding its Enabled/Disabled state.
     * @param element_p
     * @return
     */
    public Color getForeground(Object element_p) {
      if (element_p instanceof EnablableTreeNode && !((EnablableTreeNode) element_p).isEnabled()) {
        return Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GRAY);
      }
      return null;
    }

    /**
     * @see org.eclipse.jface.viewers.CellLabelProvider#getToolTipText(java.lang.Object)
     */
    @Override
    public String getToolTipText(Object element_p) {
      // For RootContextTreeNode, show a tooltip with the location of the project.
      if (element_p instanceof RootContextTreeNode) {
        RootContextsProject rootContextsProject = ((RootContextTreeNode) element_p).getValue();
        // Compute "In/Outside workspace" information.
        String inOutsideWorkspace;
        if (ProjectHelper.isPhysicallyInWorkspace(rootContextsProject._description.getLocationURI())) {
          inOutsideWorkspace = Messages.AbstractContextsSelectionPage_Tooltip_ProjectLocation_Property_InWorkspace;
        } else {
          inOutsideWorkspace = Messages.AbstractContextsSelectionPage_Tooltip_ProjectLocation_Property_OutsideWorkspace;
        }
        return MessageFormat.format(Messages.AbstractContextsSelectionPage_Tooltip_ProjectLocation_Pattern, rootContextsProject.getLocation(),
            inOutsideWorkspace);
      }
      return super.getToolTipText(element_p);
    }

    /**
     * @see org.eclipse.jface.viewers.CellLabelProvider#update(org.eclipse.jface.viewers.ViewerCell)
     */
    @Override
    public void update(ViewerCell cell_p) {
      Object element = cell_p.getElement();
      String text = null;
      Image image = null;
      if (element instanceof RootContextTreeNode) {
        text = ((RootContextTreeNode) element).getValue().getAdministratedContext().getName();
        image = _contextReferenceImage;
      } else if (element instanceof DirectoryTreeNode) {
        text = ((DirectoryTreeNode) element).getValue().toString();
        image = PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FOLDER);
      }
      cell_p.setText(text);
      cell_p.setImage(image);
      cell_p.setForeground(getForeground(element));
    }
  }

  /**
   * RootContextTreeNode (child node in the tree).
   * @author T0052089
   */
  protected class RootContextTreeNode extends EnablableTreeNode {
    /**
     * Is this node representing a conflicting RootContextsProject.
     */
    boolean _isInConflict;

    /**
     * @param rootContextsProject_p
     */
    public RootContextTreeNode(RootContextsProject rootContextsProject_p) {
      super(rootContextsProject_p);
    }

    /**
     * @see org.eclipse.jface.viewers.TreeNode#getValue()
     */
    @Override
    public RootContextsProject getValue() {
      return (RootContextsProject) super.getValue();
    }

    /**
     * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractContextsSelectionPage.EnablableTreeNode#isEnabled()
     */
    @Override
    public boolean isEnabled() {
      // Conflicting node are displayed as not enabled.
      return !_isInConflict;
    }

    /**
     * Set "is in conflict" state for this node.
     * @param isInConflict_p
     */
    public void setIsInConflict(boolean isInConflict_p) {
      _isInConflict = isInConflict_p;
    }
  }
}