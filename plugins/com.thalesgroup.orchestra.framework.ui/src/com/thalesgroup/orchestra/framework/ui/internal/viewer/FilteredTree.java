/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.viewer;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.progress.WorkbenchJob;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;

/**
 * Copied from org.eclipse.ui.dialogs due to {@link PatternFilter} methods visibility.<br>
 * A simple control that provides a text widget and a tree viewer. The contents of the text widget are used to drive a PatternFilter that is on the viewer.
 * @see PatternFilter
 * @since 3.2
 */
public class FilteredTree extends Composite {
  /**
   * The filter text widget to be used by this tree. This value may be <code>null</code> if there is no filter widget, or if the controls have not yet been
   * created.
   */
  protected Text filterText;

  /**
   * The control representing the clear button for the filter text entry. This value may be <code>null</code> if no such button exists, or if the controls have
   * not yet been created.
   */
  protected ToolBarManager filterToolBar;

  /**
   * The viewer for the filtered tree. This value should never be <code>null</code> after the widget creation methods are complete.
   */
  protected TreeViewer treeViewer;

  /**
   * The Composite on which the filter controls are created. This is used to set the background color of the filter controls to match the surrounding controls.
   */
  protected Composite filterComposite;

  /**
   * The pattern filter for the tree. This value must not be <code>null</code>.
   */
  private PatternFilter patternFilter;

  /**
   * The text to initially show in the filter text control.
   */
  protected String initialText;

  /**
   * The job used to refresh the tree.
   */
  private Job refreshJob;

  /**
   * The parent composite of the filtered tree.
   * @since 3.3
   */
  protected Composite parent;

  /**
   * Whether or not to show the filter controls (text and clear button). The default is to show these controls. This can be overridden by providing a setting in
   * the product configuration file. The setting to add to not show these controls is: org.eclipse.ui/SHOW_FILTERED_TEXTS=false
   */
  protected boolean showFilterControls;

  /**
   * @since 3.3
   */
  protected Composite treeComposite;

  /**
   * Image descriptor for enabled clear button.
   */
  private static final String CLEAR_ICON = "org.eclipse.ui.internal.dialogs.CLEAR_ICON"; //$NON-NLS-1$

  /**
   * Image descriptor for disabled clear button.
   */
  private static final String DCLEAR_ICON = "org.eclipse.ui.internal.dialogs.DCLEAR_ICON"; //$NON-NLS-1$

  /**
   * Get image descriptors for the clear button.
   */
  static {
    ImageDescriptor descriptor = AbstractUIPlugin.imageDescriptorFromPlugin(PlatformUI.PLUGIN_ID, "$nl$/icons/full/etool16/clear_co.gif"); //$NON-NLS-1$
    if (descriptor != null) {
      JFaceResources.getImageRegistry().put(CLEAR_ICON, descriptor);
    }
    descriptor = AbstractUIPlugin.imageDescriptorFromPlugin(PlatformUI.PLUGIN_ID, "$nl$/icons/full/dtool16/clear_co.gif"); //$NON-NLS-1$
    if (descriptor != null) {
      JFaceResources.getImageRegistry().put(DCLEAR_ICON, descriptor);
    }
  }

  private String previousFilterText;

  private boolean narrowingDown;

  /**
   * Create a new instance of the receiver. Subclasses that wish to override the default creation behavior may use this constructor, but must ensure that the
   * <code>init(composite, int, PatternFilter)</code> method is called in the overriding constructor.
   * @param parent_p the parent <code>Composite</code>
   * @see #init(int, PatternFilter)
   * @since 3.3
   */
  protected FilteredTree(Composite parent_p) {
    super(parent_p, SWT.NONE);
    this.parent = parent_p;
  }

  /**
   * Create a new instance of the receiver.
   * @param parent_p the parent <code>Composite</code>
   * @param treeStyle the style bits for the <code>Tree</code>
   * @param filter the filter to be used
   */
  public FilteredTree(Composite parent_p, int treeStyle, PatternFilter filter) {
    super(parent_p, SWT.NONE);
    this.parent = parent_p;
    init(treeStyle, filter);
  }

  /**
   * Clears the text in the filter text widget. Also removes the optional additional filter that is provided via addFilter(ViewerFilter).
   */
  public void clearText() {
    setFilterText(ICommonConstants.EMPTY_STRING);
    textChanged();
  }

  /**
   * Create the button that clears the text.
   * @param parent_p parent <code>Composite</code> of toolbar button
   */
  private void createClearText(Composite parent_p) {
    // only create the button if the text widget doesn't support one natively
    if ((filterText.getStyle() & SWT.CANCEL) == 0) {
      filterToolBar = new ToolBarManager(SWT.FLAT | SWT.HORIZONTAL);
      filterToolBar.createControl(parent_p);

      IAction clearTextAction = new Action("", IAction.AS_PUSH_BUTTON) {//$NON-NLS-1$
            /*
             * (non-Javadoc)
             * @see org.eclipse.jface.action.Action#run()
             */
            @Override
            public void run() {
              clearText();
            }
          };

      clearTextAction.setToolTipText(WorkbenchMessages.FilteredTree_ClearToolTip);
      clearTextAction.setImageDescriptor(JFaceResources.getImageRegistry().getDescriptor(CLEAR_ICON));
      clearTextAction.setDisabledImageDescriptor(JFaceResources.getImageRegistry().getDescriptor(DCLEAR_ICON));

      filterToolBar.add(clearTextAction);
    }
  }

  /**
   * Create the filtered tree's controls. Subclasses should override.
   * @param parent_p
   * @param treeStyle
   */
  protected void createControl(Composite parent_p, int treeStyle) {
    GridLayout layout = new GridLayout();
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    setLayout(layout);
    setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

    if (showFilterControls) {
      filterComposite = new Composite(this, SWT.NONE);
      GridLayout filterLayout = new GridLayout(2, false);
      filterLayout.marginHeight = 0;
      filterLayout.marginWidth = 0;
      filterComposite.setLayout(filterLayout);
      filterComposite.setFont(parent_p.getFont());

      createFilterControls(filterComposite);
      filterComposite.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
    }

    treeComposite = new Composite(this, SWT.NONE);
    GridLayout treeCompositeLayout = new GridLayout();
    treeCompositeLayout.marginHeight = 0;
    treeCompositeLayout.marginWidth = 0;
    treeComposite.setLayout(treeCompositeLayout);
    GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
    treeComposite.setLayoutData(data);
    createTreeControl(treeComposite, treeStyle);
  }

  /**
   * Create the filter controls. By default, a text and corresponding tool bar button that clears the contents of the text is created. Subclasses may override.
   * @param parent_p parent <code>Composite</code> of the filter controls
   * @return the <code>Composite</code> that contains the filter controls
   */
  protected Composite createFilterControls(Composite parent_p) {
    createFilterText(parent_p);
    createClearText(parent_p);
    if (filterToolBar != null) {
      filterToolBar.update(false);
      // initially there is no text to clear
      filterToolBar.getControl().setVisible(false);
    }
    return parent_p;
  }

  /**
   * Creates the filter text and adds listeners. This method calls {@link #doCreateFilterText(Composite)} to create the text control. Subclasses should override
   * {@link #doCreateFilterText(Composite)} instead of overriding this method.
   * @param parent_p <code>Composite</code> of the filter text
   */
  protected void createFilterText(Composite parent_p) {
    filterText = doCreateFilterText(parent_p);
    filterText.getAccessible().addAccessibleListener(new AccessibleAdapter() {
      /*
       * (non-Javadoc)
       * @see org.eclipse.swt.accessibility.AccessibleListener#getName(org.eclipse.swt.accessibility.AccessibleEvent)
       */
      @Override
      public void getName(AccessibleEvent e) {
        String filterTextString = filterText.getText();
        if (filterTextString.length() == 0) {
          e.result = initialText;
        } else {
          e.result = filterTextString;
        }
      }
    });

    filterText.addFocusListener(new FocusAdapter() {
      /*
       * (non-Javadoc)
       * @see org.eclipse.swt.events.FocusListener#focusLost(org.eclipse.swt.events.FocusEvent)
       */
      @Override
      public void focusGained(FocusEvent e) {
        /*
         * Running in an asyncExec because the selectAll() does not appear to work when using mouse to give focus to text.
         */
        Display display = filterText.getDisplay();
        display.asyncExec(new Runnable() {
          public void run() {
            if (!filterText.isDisposed()) {
              if (getInitialText().equals(filterText.getText().trim())) {
                filterText.selectAll();
              }
            }
          }
        });
      }
    });
    filterText.addKeyListener(new KeyAdapter() {
      /*
       * (non-Javadoc)
       * @see org.eclipse.swt.events.KeyAdapter#keyReleased(org.eclipse.swt.events.KeyEvent)
       */
      @Override
      public void keyPressed(KeyEvent e) {
        boolean hasItems = getViewer().getTree().getItemCount() > 0;
        if (hasItems && e.keyCode == SWT.ARROW_DOWN) {
          treeViewer.getTree().setFocus();
        } else if (SWT.CR == e.character) {
          return;
        }
      }
    });

    filterText.addModifyListener(new ModifyListener() {
      /*
       * (non-Javadoc)
       * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
       */
      public void modifyText(ModifyEvent e) {
        textChanged();
      }
    });

    GridData gridData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);

    // if the text widget supported cancel then it will have it's own
    // integrated button. We can take all of the space.
    if ((filterText.getStyle() & SWT.CANCEL) != 0) {
      gridData.horizontalSpan = 2;
    }
    filterText.setLayoutData(gridData);
  }

  /**
   * Create the refresh job for the receiver.
   */
  private void createRefreshJob() {
    refreshJob = new WorkbenchJob("Refresh Filter") {//$NON-NLS-1$
          /**
           * Returns true if the job should be canceled (because of timeout or actual cancellation).
           * @param items
           * @param provider
           * @param monitor
           * @param cancelTime
           * @param numItemsLeft
           * @return true if canceled
           */
          private boolean recursiveExpand(TreeItem[] items, IProgressMonitor monitor) {
            treeViewer.expandAll();
            return false;
          }

          /*
           * (non-Javadoc)
           * @see org.eclipse.ui.progress.UIJob#runInUIThread(org.eclipse.core.runtime.IProgressMonitor)
           */
          @SuppressWarnings("synthetic-access")
          @Override
          public IStatus runInUIThread(IProgressMonitor monitor) {
            if (treeViewer.getControl().isDisposed()) {
              return Status.CANCEL_STATUS;
            }

            String text = getFilterString();
            if (text == null) {
              return Status.OK_STATUS;
            }

            boolean initial = initialText != null && initialText.equals(text);
            String newPatternToApply = null;
            if (!initial && (text.length() > 0)) {
              newPatternToApply = text;
            }
            patternFilter.setPattern(newPatternToApply);

            Control redrawFalseControl = treeComposite != null ? treeComposite : treeViewer.getControl();
            try {
              // don't want the user to see updates that will be made to the tree
              // we are setting redraw(false) on the composite to avoid dancing scroll bar
              redrawFalseControl.setRedraw(false);
              if (!narrowingDown) {
                // collapse all
                TreeItem[] is = treeViewer.getTree().getItems();
                for (int i = 0; i < is.length; i++) {
                  TreeItem item = is[i];
                  if (item.getExpanded()) {
                    treeViewer.setExpandedState(item.getData(), false);
                  }
                }
              }
              // Get currently expanded elements just before the tree viewer is refreshed.
              Object[] expandedElements = treeViewer.getExpandedElements();
              // Refresh the tree viewer to take into account the new pattern filter value.
              treeViewer.refresh(true);

              if (!initial) {
                if (text.length() > 0) {
                  // enabled toolbar - there is text to clear
                  // and the list is currently being filtered
                  updateToolbar(true);
                  /*
                   * Expand elements one at a time. After each is expanded, check to see if the filter text has been modified. If it has, then cancel the
                   * refresh job so the user doesn't have to endure expansion of all the nodes.
                   */
                  TreeItem[] items = getViewer().getTree().getItems();
                  if (items.length > 0 && recursiveExpand(items, monitor)) {
                    return Status.CANCEL_STATUS;
                  }
                } else {
                  // Disabled toolbar - there is no text to clear and the list is no longer filtered
                  updateToolbar(false);
                  // Expand previously (i.e when the tree was filtered) expanded elements.
                  treeViewer.setExpandedElements(expandedElements);
                }
              } else {
                // No filter has been used since view was open.
                // Disabled toolbar - there is no text to clear and the list is currently not filtered
                updateToolbar(false);
              }
            } finally {
              // done updating the tree - set redraw back to true
              TreeItem[] items = getViewer().getTree().getItems();
              if (items.length > 0 && getViewer().getTree().getSelectionCount() == 0) {
                treeViewer.getTree().setTopItem(items[0]);
              }
              redrawFalseControl.setRedraw(true);
            }
            return Status.OK_STATUS;
          }
        };
    refreshJob.setSystem(true);
  }

  /**
   * Creates and set up the tree and tree viewer. This method calls {@link #doCreateTreeViewer(Composite, int)} to create the tree viewer. Subclasses should
   * override {@link #doCreateTreeViewer(Composite, int)} instead of overriding this method.
   * @param parent_p parent <code>Composite</code>
   * @param style SWT style bits used to create the tree
   * @return the tree
   */
  protected Control createTreeControl(Composite parent_p, int style) {
    treeViewer = doCreateTreeViewer(parent_p, style);
    GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
    treeViewer.getControl().setLayoutData(data);
    treeViewer.getControl().addDisposeListener(new DisposeListener() {
      /*
       * (non-Javadoc)
       * @see org.eclipse.swt.events.DisposeListener#widgetDisposed(org.eclipse.swt.events.DisposeEvent)
       */
      @SuppressWarnings("synthetic-access")
      public void widgetDisposed(DisposeEvent e) {
        refreshJob.cancel();
      }
    });
    if (treeViewer instanceof NotifyingTreeViewer) {
      patternFilter.setUseCache(true);
    }
    treeViewer.addFilter(patternFilter);
    return treeViewer.getControl();
  }

  /**
   * Creates the text control for entering the filter text. Subclasses may override.
   * @param parent_p the parent composite
   * @return the text widget
   * @since 3.3
   */
  protected Text doCreateFilterText(Composite parent_p) {
    return new Text(parent_p, SWT.SINGLE | SWT.BORDER | SWT.SEARCH | SWT.CANCEL);
  }

  /**
   * Creates the tree viewer. Subclasses may override.
   * @param parent_p the parent composite
   * @param style SWT style bits used to create the tree viewer
   * @return the tree viewer
   * @since 3.3
   */
  protected TreeViewer doCreateTreeViewer(Composite parent_p, int style) {
    return new NotifyingTreeViewer(parent_p, style);
  }

  /**
   * Get the filter text for the receiver, if it was created. Otherwise return <code>null</code>.
   * @return the filter Text, or null if it was not created
   */
  public Text getFilterControl() {
    return filterText;
  }

  /**
   * Convenience method to return the text of the filter control. If the text widget is not created, then null is returned.
   * @return String in the text, or null if the text does not exist
   */
  protected String getFilterString() {
    return (filterText != null) && !filterText.isDisposed() ? filterText.getText() : ICommonConstants.EMPTY_STRING;
  }

  /**
   * Get the initial text for the receiver.
   * @return String
   */
  protected String getInitialText() {
    return initialText;
  }

  /**
   * Returns the pattern filter used by this tree.
   * @return The pattern filter; never <code>null</code>.
   */
  public final PatternFilter getPatternFilter() {
    return patternFilter;
  }

  /**
   * Get the tree viewer of the receiver.
   * @return the tree viewer
   */
  public TreeViewer getViewer() {
    return treeViewer;
  }

  /**
   * Create the filtered tree.
   * @param treeStyle the style bits for the <code>Tree</code>
   * @param filter the filter to be used
   * @since 3.3
   */
  protected void init(int treeStyle, PatternFilter filter) {
    patternFilter = filter;
    showFilterControls = PlatformUI.getPreferenceStore().getBoolean(IWorkbenchPreferenceConstants.SHOW_FILTERED_TEXTS);
    createControl(parent, treeStyle);
    createRefreshJob();
    setInitialText(WorkbenchMessages.FilteredTree_FilterMessage);
    setFont(parent.getFont());
  }

  /**
   * Select all text in the filter text field.
   */
  protected void selectAll() {
    if (filterText != null) {
      filterText.selectAll();
    }
  }

  /**
   * Set the background for the widgets that support the filter text area.
   * @param background background <code>Color</code> to set
   */
  @Override
  public void setBackground(Color background) {
    super.setBackground(background);
    if (filterComposite != null) {
      filterComposite.setBackground(background);
    }
    if (filterToolBar != null && filterToolBar.getControl() != null) {
      filterToolBar.getControl().setBackground(background);
    }
  }

  /**
   * Set the text in the filter control.
   * @param string
   */
  protected void setFilterText(String string) {
    if ((filterText != null) && !filterText.isDisposed()) {
      filterText.setText(string);
      selectAll();
    }
  }

  /**
   * Set the text that will be shown until the first focus. A default value is provided, so this method only need be called if overriding the default initial
   * text is desired.
   * @param text initial text to appear in text field
   */
  public void setInitialText(String text) {
    initialText = text;
    filterText.setMessage(text);
    if (filterText.isFocusControl()) {
      setFilterText(initialText);
      textChanged();
    } else {
      getDisplay().asyncExec(new Runnable() {
        public void run() {
          if (!filterText.isDisposed() && filterText.isFocusControl()) {
            setFilterText(initialText);
            textChanged();
          }
        }
      });
    }
  }

  /**
   * Update the receiver after the text has changed.
   */
  protected void textChanged() {
    narrowingDown = previousFilterText == null || getFilterString().startsWith(previousFilterText);
    previousFilterText = getFilterString();
    // cancel currently running job first, to prevent unnecessary redraw
    refreshJob.cancel();
    // 300ms is just what we need to get a responsive text field.
    refreshJob.schedule(300);
  }

  protected void updateToolbar(boolean visible) {
    if (filterToolBar != null) {
      filterToolBar.getControl().setVisible(visible);
    }
  }

  /**
   * Return a bold font if the given element matches the given pattern. Clients can opt to call this method from a Viewer's label provider to get a bold font
   * for which to highlight the given element in the tree.
   * @param element element for which a match should be determined
   * @param tree FilteredTree in which the element resides
   * @param filter PatternFilter which determines a match
   * @return bold font
   */
  public static Font getBoldFont(Object element, FilteredTree tree, PatternFilter filter) {
    String filterText = tree.getFilterString();

    if (filterText == null) {
      return null;
    }

    // Do nothing if it's empty string
    String initialText = tree.getInitialText();
    if (!("".equals(filterText) || initialText.equals(filterText))) {//$NON-NLS-1$
      boolean initial = initialText != null && initialText.equals(filterText);
      if (initial) {
        filter.setPattern(null);
      } else if (filterText != null) {
        filter.setPattern(filterText);
      }

      if (filter.isElementVisible(tree.getViewer(), element) && filter.isLeafMatch(tree.getViewer(), element)) {
        return JFaceResources.getFontRegistry().getBold(JFaceResources.DIALOG_FONT);
      }
    }
    return null;
  }

  /**
   * Custom tree viewer subclass that clears the caches in patternFilter on any change to the tree. See bug 187200.
   * @since 3.3
   */
  class NotifyingTreeViewer extends TreeViewer {

    /**
     * @param parent_p
     * @param style
     */
    public NotifyingTreeViewer(Composite parent_p, int style) {
      super(parent_p, style);
    }

    @Override
    public void add(Object parentElementOrTreePath, Object childElement) {
      getPatternFilter().clearCaches();
      super.add(parentElementOrTreePath, childElement);
    }

    @Override
    public void add(Object parentElementOrTreePath, Object[] childElements) {
      getPatternFilter().clearCaches();
      super.add(parentElementOrTreePath, childElements);
    }

    @Override
    protected void inputChanged(Object input, Object oldInput) {
      getPatternFilter().clearCaches();
      super.inputChanged(input, oldInput);
    }

    @Override
    public void insert(Object parentElementOrTreePath, Object element, int position) {
      getPatternFilter().clearCaches();
      super.insert(parentElementOrTreePath, element, position);
    }

    @Override
    public void refresh() {
      getPatternFilter().clearCaches();
      super.refresh();
    }

    @Override
    public void refresh(boolean updateLabels) {
      getPatternFilter().clearCaches();
      super.refresh(updateLabels);
    }

    @Override
    public void refresh(Object element) {
      getPatternFilter().clearCaches();
      super.refresh(element);
    }

    @Override
    public void refresh(Object element, boolean updateLabels) {
      getPatternFilter().clearCaches();
      super.refresh(element, updateLabels);
    }

    @Override
    public void remove(Object elementsOrTreePaths) {
      getPatternFilter().clearCaches();
      super.remove(elementsOrTreePaths);
    }

    @Override
    public void remove(Object parent_p, Object[] elements) {
      getPatternFilter().clearCaches();
      super.remove(parent_p, elements);
    }

    @Override
    public void remove(Object[] elementsOrTreePaths) {
      getPatternFilter().clearCaches();
      super.remove(elementsOrTreePaths);
    }

    @Override
    public void replace(Object parentElementOrTreePath, int index, Object element) {
      getPatternFilter().clearCaches();
      super.replace(parentElementOrTreePath, index, element);
    }

    @Override
    public void setChildCount(Object elementOrTreePath, int count) {
      getPatternFilter().clearCaches();
      super.setChildCount(elementOrTreePath, count);
    }

    @Override
    public void setContentProvider(IContentProvider provider) {
      getPatternFilter().clearCaches();
      super.setContentProvider(provider);
    }

    @Override
    public void setHasChildren(Object elementOrTreePath, boolean hasChildren) {
      getPatternFilter().clearCaches();
      super.setHasChildren(elementOrTreePath, hasChildren);
    }
  }
}
