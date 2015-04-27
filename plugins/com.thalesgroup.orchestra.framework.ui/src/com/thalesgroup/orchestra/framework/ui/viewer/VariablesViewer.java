/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.viewer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposedImage.Point;
import org.eclipse.emf.edit.ui.action.CreateChildAction;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.thalesgroup.orchestra.framework.common.CommonActivator;
import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.model.IEditionContextProvider;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Category;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.contexts.ModelElement;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.command.IClipboardContentChangedListener;
import com.thalesgroup.orchestra.framework.model.handler.data.ContextsEditingDomain;
import com.thalesgroup.orchestra.framework.model.handler.data.ContextsEditingDomain.ClipboardElement;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper.LayoutType;
import com.thalesgroup.orchestra.framework.ui.action.CustomPasteAction;
import com.thalesgroup.orchestra.framework.ui.action.EditElementAction;
import com.thalesgroup.orchestra.framework.ui.action.FindVariableUsagesAction;
import com.thalesgroup.orchestra.framework.ui.action.RenameVariableAction;
import com.thalesgroup.orchestra.framework.ui.action.ValidateAction;
import com.thalesgroup.orchestra.framework.ui.internal.viewer.IModelElementFilterListener;
import com.thalesgroup.orchestra.framework.ui.internal.viewer.IPatternFilterListener;
import com.thalesgroup.orchestra.framework.ui.internal.viewer.ModelElementFilter;
import com.thalesgroup.orchestra.framework.ui.internal.viewer.ModelElementFilter.FilterState;
import com.thalesgroup.orchestra.framework.ui.internal.viewer.PatternFilter;
import com.thalesgroup.orchestra.framework.ui.internal.viewer.StructuredSelectionWithContext;
import com.thalesgroup.orchestra.framework.ui.view.ISharedActionsHandler;
import com.thalesgroup.orchestra.framework.ui.view.SharedActionsUpdater;
import com.thalesgroup.orchestra.framework.ui.view.VariablesView;
import com.thalesgroup.orchestra.framework.ui.wizard.OpenChangeWizardListener;

/**
 * Variables viewer.
 * @author t0076261
 */
public class VariablesViewer implements IMenuListener, IPatternFilterListener, IModelElementFilterListener {
  /**
   * Name column index.
   */
  public static final int COLUMN_INDEX_NAME = 0;
  /**
   * Value column index.
   */
  public static final int COLUMN_INDEX_VALUE = 1;
  private static final String POPUP_MENU_GROUP_EDIT = "VARIABLES_EDIT_GROUP"; //$NON-NLS-1$
  protected static final String POPUP_MENU_GROUP_NEW = "VARIABLES_NEW_GROUP"; //$NON-NLS-1$
  private static final String POPUP_MENU_GROUP_VALIDATE = "VARIABLES_VALIDATE_GROUP"; //$NON-NLS-1$
  protected static final String POPUP_MENU_ID_NEWMENU = "VariablesNewMenu"; //$NON-NLS-1$
  protected static final String POPUP_MENU_ID_ROOTMENU = "VariablesTreePopup"; //$NON-NLS-1$
  protected static final String POPUP_MENU_ID_REFACTORMENU = "VariablesRefactorMenu"; //$NON-NLS-1$
  /**
   * Edition context.
   */
  protected Context _context;
  /**
   * Contextual menu manager.
   */
  private MenuManager _contextMenu;
  /**
   * Filter reminder.
   */
  protected PatternFilter _filter;
  /**
   * Label provider.
   */
  protected VariablesLabelProvider _labelProvider;
  /**
   * Shared actions updater.
   */
  private SharedActionsUpdater _sharedActionsUpdater;
  /**
   * Validate action.
   */
  private ValidateAction _validateAction;
  /**
   * Tree viewer reminder.
   */
  protected TreeViewer _viewer;

  /**
   * Create content provider.<br>
   * Result must not be <code>null</code> !
   * @return A new content provider for this viewer, must not be <code>null</code>.
   */
  protected VariablesContentProvider createContentProvider() {
    return new VariablesContentProvider();
  }

  /**
   * Create contextual menu.
   */
  protected void createContextualMenu(ISharedActionsHandler sharedActionsHandler_p) {
    _contextMenu = new MenuManager(ICommonConstants.EMPTY_STRING, POPUP_MENU_ID_ROOTMENU);
    _contextMenu.addMenuListener(this);
    // Add new group.
    _contextMenu.add(new Separator(POPUP_MENU_GROUP_NEW));
    MenuManager newMenu = new MenuManager(Messages.ContextsViewer_NewMenu_Text, POPUP_MENU_ID_NEWMENU);
    newMenu.setRemoveAllWhenShown(true);
    newMenu.addMenuListener(this);
    _contextMenu.add(newMenu);
    // Add edit group.
    _contextMenu.add(new Separator(POPUP_MENU_GROUP_EDIT));
    // Create edit action.
    IEditionContextProvider editionContextProvider = new IEditionContextProvider() {
      /**
       * @see com.thalesgroup.orchestra.framework.model.IEditionContextProvider#getEditionContext()
       */
      public Context getEditionContext() {
        return getContext();
      }
    };
    EditElementAction editAction = new EditElementAction(_viewer.getControl().getShell(), editionContextProvider);
    // Wait for selection to decide.
    editAction.setEnabled(false);
    _viewer.addSelectionChangedListener(editAction);
    _contextMenu.add(editAction);
    // Create find usages action.
    FindVariableUsagesAction findUsagesAction = new FindVariableUsagesAction(_viewer.getControl().getShell(), editionContextProvider);
    // Wait for selection to decide.
    findUsagesAction.setEnabled(false);
    _viewer.addSelectionChangedListener(findUsagesAction);
    _contextMenu.add(findUsagesAction);

    MenuManager refactorMenu = new MenuManager("Refactor", POPUP_MENU_ID_REFACTORMENU);
    _contextMenu.add(refactorMenu);
    RenameVariableAction renameVariableAction = new RenameVariableAction(_viewer.getControl().getShell(), editionContextProvider);
    // Wait for selection to decide.
    renameVariableAction.setEnabled(false);
    _viewer.addSelectionChangedListener(renameVariableAction);
    refactorMenu.add(renameVariableAction);

    // Add double-click listener based on edit action.
    // That explains why it sits here.
    _viewer.addDoubleClickListener(new OpenChangeWizardListener(editAction));
    if (null != sharedActionsHandler_p) {
      _contextMenu.add(new Separator());
      // Cut/copy/paste.
      _contextMenu.add(sharedActionsHandler_p.getCutAction());
      _contextMenu.add(sharedActionsHandler_p.getCopyAction());
      _contextMenu.add(sharedActionsHandler_p.getPasteAction());
      _contextMenu.add(sharedActionsHandler_p.getDeleteAction());
      // Create shared actions updater for this viewer.
      _sharedActionsUpdater = new SharedActionsUpdater(sharedActionsHandler_p, _viewer) {
        /**
         * @see com.thalesgroup.orchestra.framework.ui.view.SharedActionsUpdater#getSelectionFor(org.eclipse.jface.action.IAction)
         */
        @Override
        protected IStructuredSelection getSelectionFor(IAction action_p) {
          // For paste action, act as if the initial input was selected.
          if (CustomPasteAction.class.equals(action_p.getClass())) {
            return new StructuredSelection(_viewer.getInput());
          }
          return super.getSelectionFor(action_p);
        }
      };
      _viewer.addSelectionChangedListener(_sharedActionsUpdater);
      _viewer.getControl().addFocusListener(_sharedActionsUpdater);
    }
    // Add validate group.
    _contextMenu.add(new Separator(POPUP_MENU_GROUP_VALIDATE));
    _validateAction = new ValidateAction(editionContextProvider);
    // Wait for selection to decide.
    _validateAction.setEnabled(false);
    _viewer.addSelectionChangedListener(_validateAction);
    _contextMenu.add(_validateAction);
    // Add addition separator.
    _contextMenu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
    // Register menu.
    Control control = _viewer.getControl();
    Menu menu = _contextMenu.createContextMenu(control);
    control.setMenu(menu);
  }

  /**
   * Create viewer.
   * @param toolkit_p
   * @param parent_p
   * @param initialInput_p
   * @param context_p edition context.
   * @param filter_p Pattern filter in-use in ContextsViewer. Can be <code>null</code> if no highlighting is required.
   * @param sharedActionsHandler_p Shared actions handler, that is actions shared by all viewers in containing workbench part. Can be <code>null</code> if there
   *          is no need for sharing actions.
   */
  public void createViewer(FormToolkit toolkit_p, Composite parent_p, Object initialInput_p, Context context_p, PatternFilter filter_p,
      ISharedActionsHandler sharedActionsHandler_p) {
    // Reminder of scope context.
    _context = context_p;
    // Reminder of pattern filter.
    _filter = filter_p;
    // Update parent composite.
    FormHelper.updateCompositeLayoutWithLayoutType(parent_p, LayoutType.GRID_LAYOUT, 1, false);
    // Create table section.
    Map<Class, Control> tableSection =
        FormHelper.createSectionWithChildComposite(toolkit_p, parent_p, ExpandableComposite.TITLE_BAR | ExpandableComposite.EXPANDED, LayoutType.GRID_LAYOUT,
            1, false);
    Section section = (Section) tableSection.get(Section.class);
    section.setText(Messages.VariablesViewer_SectionTitle);
    Composite composite = (Composite) tableSection.get(Composite.class);
    // Create shared label provider.
    _labelProvider = new VariablesLabelProvider();
    // Create table within the composite.
    _viewer = new TreeViewer(composite, SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL) {
      /**
       * @see org.eclipse.jface.viewers.TreeViewer#getLabelProvider()
       */
      @Override
      public IBaseLabelProvider getLabelProvider() {
        return _labelProvider;
      }
    };
    // Create columns.
    Tree tree = _viewer.getTree();
    tree.setLinesVisible(true);
    tree.setHeaderVisible(true);
    TableLayout layout = new TableLayout();
    tree.setLayout(layout);
    layout.addColumnData(new ColumnWeightData(33, 150, true));
    layout.addColumnData(new ColumnWeightData(66, 300, true));
    TreeViewerColumn viewerColumnName = new TreeViewerColumn(_viewer, SWT.LEFT, COLUMN_INDEX_NAME);
    viewerColumnName.getColumn().setText(Messages.VariablesViewer_ColumnTitle_Name);
    TreeViewerColumn viewerColumnValue = new TreeViewerColumn(_viewer, SWT.LEFT, COLUMN_INDEX_VALUE);
    viewerColumnValue.getColumn().setText(Messages.VariablesViewer_ColumntTitle_Value);
    // Set content provider.
    _viewer.setContentProvider(createContentProvider());
    // Set label provider.
    viewerColumnName.setLabelProvider(_labelProvider);
    viewerColumnValue.setLabelProvider(_labelProvider);
    // Layout viewer.
    FormHelper.updateControlLayoutDataWithLayoutTypeData(_viewer.getControl(), LayoutType.GRID_LAYOUT);
    GridData layoutData = (GridData) _viewer.getControl().getLayoutData();
    // Force initial height.
    layoutData.heightHint = _viewer.getControl().computeSize(SWT.DEFAULT, parent_p.getSize().y).y;
    // Auto expand.
    _viewer.setAutoExpandLevel(AbstractTreeViewer.ALL_LEVELS);
    // Create contextual menu.
    createContextualMenu(sharedActionsHandler_p);
    // Register itself as pattern filter listener.
    if (null != _filter) {
      _filter.addListener(this);
    }
    // Register as model element filter listener.
    VariablesView.getSharedInstance().getModelElementFilter().addListener(this);
    // Set comparator.
    _viewer.setComparator(new ViewerComparator() {
      /**
       * @see org.eclipse.jface.viewers.ViewerComparator#compare(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
       */
      @Override
      public int compare(Viewer viewer_p, Object e1_p, Object e2_p) {
        // Do not sort (VariableValue)s
        if ((e1_p instanceof VariableValue) && (e2_p instanceof VariableValue)) {
          return 0;
        }
        return super.compare(viewer_p, e1_p, e2_p);
      }
    });
    // Set initial input.
    _viewer.setInput(initialInput_p);
  }

  /**
   * Dispose.
   */
  public void dispose() {
    preDispose();
    // Dereference viewer.
    if (null != _viewer) {
      // Dispose menu manager.
      if (null != _contextMenu) {
        _contextMenu.dispose();
        _contextMenu = null;
      }
      if (null != _validateAction) {
        _viewer.removeSelectionChangedListener(_validateAction);
        _validateAction = null;
      }
      // Dispose label provider.
      _labelProvider.dispose();
      _labelProvider = null;
      // Dispose content provider.
      _viewer.getContentProvider().dispose();
      _viewer = null;
      if (null != _filter) {
        _filter.removeListener(this);
        _filter = null;
      }
      VariablesView.getSharedInstance().getModelElementFilter().removeListener(this);
    }
  }

  /**
   * Find tree item for specified data element.
   * @param element_p
   * @return
   */
  protected TreeItem findItemFor(Object element_p) {
    // Precondition.
    if (null == element_p) {
      return null;
    }
    // Root items.
    return findItemFor(element_p, _viewer.getTree().getItems());
  }

  /**
   * Recursively find item that point to specified element within specified roots (subtrees).
   * @param element_p
   * @param items_p
   * @return
   */
  protected TreeItem findItemFor(Object element_p, TreeItem[] items_p) {
    // Precondition.
    if (null == items_p) {
      return null;
    }
    // Get element path.
    String elementPath = ModelUtil.getElementPath(element_p);
    TreeItem result = null;
    // Search within specified items.
    TreeItem firstHit = null;
    for (TreeItem item : items_p) {
      Object data = item.getData();
      // Get data path.
      String dataPath = ModelUtil.getElementPath(data);
      if (element_p == data) {
        result = item;
      } else if (elementPath.equals(dataPath)) {
        if (null == firstHit) {
          firstHit = item;
        }
      } else { // Search within subtree.
        TreeItem[] items = item.getItems();
        if (items.length > 0) {
          result = findItemFor(element_p, items);
        }
      }
      // Stop search.
      if (null != result) {
        break;
      }
    }
    // Strict equality could not be found, but an element with the same path is available.
    // Use this element instead.
    if ((null == result) && (null != firstHit)) {
      return firstHit;
    }
    return result;
  }

  /**
   * Get scope {@link Context}.
   * @return
   */
  protected Context getContext() {
    return _context;
  }

  /**
   * Get shared action updater.
   * @return
   */
  public SharedActionsUpdater getSharedActionsUpdater() {
    return _sharedActionsUpdater;
  }

  /**
   * Get real viewer.
   * @return
   */
  public TreeViewer getViewer() {
    return _viewer;
  }

  /**
   * @see org.eclipse.jface.action.IMenuListener#menuAboutToShow(org.eclipse.jface.action.IMenuManager)
   */
  public void menuAboutToShow(IMenuManager manager_p) {
    ISelection selection = _viewer.getSelection();
    if (!(selection instanceof IStructuredSelection)) {
      return;
    }
    IStructuredSelection structuredSelection = (IStructuredSelection) selection;
    Category category = (Category) _viewer.getInput();
    // Edition context.
    Context editionContext = getContext();
    if (POPUP_MENU_ID_ROOTMENU.equals(manager_p.getId())) {
      // Force selection.
      _validateAction.selectionChanged(new SelectionChangedEvent(_viewer, structuredSelection));
    } else if (POPUP_MENU_ID_NEWMENU.equals(manager_p.getId())) {
      // Add creation actions.
      ContextsEditingDomain domain = ModelHandlerActivator.getDefault().getEditingDomain();
      Collection<?> newChildDescriptors = domain.getNewChildDescriptors(category, null);
      boolean unmodifiable = DataUtil.isUnmodifiable(category, editionContext);
      ISelection categorySelection = new StructuredSelection(category);
      for (Object newChildDescriptor : newChildDescriptors) {
        // Create action in any case.
        CreateChildAction action = new CreateChildAction(domain, categorySelection, newChildDescriptor);
        // Mark the action as disabled if element is unmodifiable or mandatory filter is active.
        if (unmodifiable || VariablesView.getSharedInstance().getModelElementFilter().getCurrentFilterState().isFiltering()) {
          action.setEnabled(false);
        }
        manager_p.add(action);
      }
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.viewer.IModelElementFilterListener#modelElementFilterChanged()
   */
  public void modelElementFilterChanged(FilterState newFilterState_p) {
    if (null != _viewer && !_viewer.getTree().isDisposed()) {
      // Force viewer refresh, to apply new filter.
      _viewer.refresh();
      if (newFilterState_p.isFiltering()) {
        // Expand all tree nodes when filter is on.
        _viewer.expandAll();
      } else {
        _viewer.collapseAll();
      }
    }
    if (null != getSharedActionsUpdater()) {
      // Disable actions on displayed elements while filtering.
      getSharedActionsUpdater().setDisabledForced(newFilterState_p.isFiltering());
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.viewer.IPatternFilterListener#patternChanged(java.lang.String)
   */
  public void patternChanged(String newPattern_p) {
    if (null != _viewer) {
      // Force viewer refresh, the new pattern still applies, but differently.
      _viewer.refresh();
    }
  }

  /**
   * Early dispose, that deactivates UI features as soon as possible.
   */
  public void preDispose() {
    // Dispose shared actions updater, if any.
    if (null != _sharedActionsUpdater) {
      _viewer.removeSelectionChangedListener(_sharedActionsUpdater);
      _viewer.getControl().removeFocusListener(_sharedActionsUpdater);
      _sharedActionsUpdater.dispose();
      _sharedActionsUpdater = null;
    }
    // Dispose content provider (as model listener).
    _viewer.getContentProvider().dispose();
  }

  /**
   * Refresh specified element.
   * @param element_p
   */
  public void refreshElement(Object element_p) {
    // Precondition.
    if (null == element_p) {
      return;
    }
    // Category is not current input, stop here.
    if (_viewer.getInput() != ModelUtil.getCategory(element_p)) {
      return;
    }
    _viewer.update(element_p, null);
  }

  /**
   * Set selection to specified one.
   * @param selection_p
   */
  public void setSelection(ISelection selection_p) {
    // Precondition.
    // Unsupported selection type.
    if (!(selection_p instanceof StructuredSelectionWithContext)) {
      return;
    }
    Object element = ((StructuredSelectionWithContext) selection_p).getFirstElement();
    // If element to find is a variable value and its parent (AbstractVariable) has only ONE value,
    // use the AbstractVariable instead to find item.
    if (element instanceof VariableValue) {
      ModelElement modelElement = ModelUtil.getLogicalContainer(element, _context);
      if (modelElement instanceof AbstractVariable && ((AbstractVariable) modelElement).getValues().size() == 1) {
        element = modelElement;
      }
    }
    TreeItem item = findItemFor(element);
    if (null != item) {
      Tree tree = _viewer.getTree();
      tree.deselectAll();
      tree.select(item);
      tree.setFocus();
      tree.showSelection();
    }
  }

  /**
   * Variables content provider.
   * @author t0076261
   */
  protected class VariablesContentProvider extends AdapterFactoryContentProvider implements IClipboardContentChangedListener {
    /**
     * Model listener.
     */
    private AbstractViewerNotifier _adapter;

    /**
     * Constructor.
     */
    protected VariablesContentProvider() {
      super(ModelHandlerActivator.getDefault().getEditingDomain().getAdapterFactory());
      _adapter = new AbstractViewerNotifier(_viewer) {
        /**
         * @see com.thalesgroup.orchestra.framework.ui.viewer.AbstractViewerNotifier#createAddRunnable(java.lang.Object, java.lang.Object)
         */
        @Override
        protected Runnable createAddRunnable(Object parent_p, Object newElement_p) {
          // Make sure this has something to do with current viewer (current edited category + current edited context).
          // A check on the category is not enough because newElement_p can be in an inherited category (which is the same in every child contexts).
          if (_viewer.getInput() == ModelUtil.getCategory(newElement_p) && getContext() == ModelUtil.getContext(newElement_p)) {
            return super.createAddRunnable(parent_p, newElement_p);
          }
          return null;
        }

        /**
         * @see com.thalesgroup.orchestra.framework.ui.viewer.AbstractViewerNotifier#createRefreshRunnable(java.lang.Object)
         */
        @Override
        protected Runnable createRefreshRunnable(Object element_p) {
          // Make sure this has something to do with current viewer.
          if (_viewer.getInput() == ModelUtil.getCategory(element_p)) {
            return super.createRefreshRunnable(element_p);
          }
          return null;
        }

        /**
         * @see com.thalesgroup.orchestra.framework.ui.viewer.AbstractViewerNotifier#createUpdateRunnable(java.lang.Object)
         */
        @Override
        protected Runnable createUpdateRunnable(Object... elements_p) {
          // Make sure this has something to do with current viewer.
          for (Object element : elements_p) {
            if (_viewer.getInput() != ModelUtil.getCategory(element)) {
              return null;
            }
          }
          return super.createUpdateRunnable(elements_p);
        }

        /**
         * @see org.eclipse.emf.common.notify.impl.AdapterImpl#notifyChanged(org.eclipse.emf.common.notify.Notification)
         */
        @Override
        public void notifyChanged(Notification notification_p) {
          doHandleNotification(notification_p);
        }
      };
      // Listen to model changes.
      ModelHandlerActivator.getDefault().getEditingDomain().getNotificationsListener().addAdapter(_adapter);
      // Listen to clipboard changes (e.g. : cut elements).
      ModelHandlerActivator.getDefault().getEditingDomain().addClipboardContentChangedListener(this);
    }

    /**
     * @see com.thalesgroup.orchestra.framework.model.handler.command.IClipboardContentChangedListener#clipboardElementAdded(com.thalesgroup.orchestra.framework.model.handler.data.ContextsEditingDomain.ClipboardElement)
     */
    @Override
    public void clipboardElementAdded(ClipboardElement clipboardElement_p) {
      // A cut/copy action just occurred, an update of cut/copied elements isn't enough since it doesn't update children (variable values of a cut variable
      // are displayed in gray too) -> Don't bother to refresh only cut/copied elements, refresh the whole VariablesViewer.
      Runnable refreshRunnable = _adapter.createRefreshRunnable(_viewer.getInput());
      if (null != refreshRunnable) {
        _viewer.getControl().getDisplay().asyncExec(refreshRunnable);
      }
    }

    /**
     * @see com.thalesgroup.orchestra.framework.model.handler.command.IClipboardContentChangedListener#clipboardElementRemoved(com.thalesgroup.orchestra.framework.model.handler.data.ContextsEditingDomain.ClipboardElement)
     */
    @Override
    public void clipboardElementRemoved(ClipboardElement clipboardElement_p) {
      // See comment in the clipboardElementAdded method.
      Runnable refreshRunnable = _adapter.createRefreshRunnable(_viewer.getInput());
      if (null != refreshRunnable) {
        _viewer.getControl().getDisplay().asyncExec(refreshRunnable);
      }
    }

    /**
     * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider#dispose()
     */
    @Override
    public void dispose() {
      try {
        super.dispose();
      } finally {
        if (null != _adapter) {
          ModelHandlerActivator.getDefault().getEditingDomain().getNotificationsListener().removeAdapter(_adapter);
          _adapter = null;
        }
        ModelHandlerActivator.getDefault().getEditingDomain().removeClipboardContentChangedListener(this);
      }
    }

    /**
     * Do handle notification.
     * @param notification_p
     */
    protected void doHandleNotification(Notification notification_p) {
      try {
        Runnable updateRunnable = null;
        EStructuralFeature feature = EStructuralFeature.class.cast(notification_p.getFeature());
        Object notifier = notification_p.getNotifier();
        switch (notification_p.getEventType()) {
          case Notification.ADD:
            if (ContextsPackage.Literals.CATEGORY__VARIABLES.equals(feature)) {
              // Added a category variable.
              updateRunnable = _adapter.createAddRunnable(_viewer.getInput(), notification_p.getNewValue());
            } else if (ContextsPackage.Literals.CONTEXT__SUPER_CATEGORY_VARIABLES.equals(feature)) {
              // Added a super category variable.
              updateRunnable = _adapter.createAddRunnable(_viewer.getInput(), notification_p.getNewValue());
            } else if (ContextsPackage.Literals.CONTEXT__OVERRIDING_VARIABLES.equals(feature)) {
              // Added an overriding variable.
              // Refresh overridden variable.
              updateRunnable = _adapter.createRefreshRunnable(((OverridingVariable) notification_p.getNewValue()).getOverriddenVariable());
            } else if (ContextsPackage.Literals.ABSTRACT_VARIABLE__VALUES.equals(feature)) {
              // Added a variable value.
              if (ContextsPackage.Literals.OVERRIDING_VARIABLE.isInstance(notifier)) {
                // Refresh overridden variable.
                updateRunnable = _adapter.createRefreshRunnable(((OverridingVariable) notifier).getOverriddenVariable());
              } else if (ContextsPackage.Literals.ABSTRACT_VARIABLE.isInstance(notifier)) {
                // Add it to containing variable. The viewer comparator will be temporarily
                // disabled in order for values to be placed at the right location in the
                // multivalued list
                updateRunnable = _adapter.createAddUnsortedRunnable(notifier, notification_p.getNewValue());
              }
            }
          break;
          case Notification.REMOVE:
            updateRunnable = removeElement(notification_p.getOldValue(), feature, notifier);
          break;
          case Notification.REMOVE_MANY:
            // TODO Performance might be improved by grouping refreshes and removals
            @SuppressWarnings("unchecked")
            List<Object> oldElementList = (List<Object>) notification_p.getOldValue();
            for (Object element : oldElementList) {
              Runnable runnable = removeElement(element, feature, notifier);
              // Update the viewer.
              if (null != runnable) {
                _viewer.getControl().getDisplay().asyncExec(runnable);
              }
            }
          break;
          case Notification.SET:
            if (ContextsPackage.Literals.VARIABLE_VALUE__VALUE.equals(feature)) {
              // A value is modified within a variable value -> refresh the whole content of the viewer
              // (in fact, the modified value could be referenced by other variables in the same category).
              updateRunnable = _adapter.createRefreshRunnable(viewer.getInput());
            } else if (ContextsPackage.Literals.ABSTRACT_VARIABLE__MULTI_VALUED.equals(feature)) {
              final AbstractVariable variable = (Variable) notifier;
              // Variable is muting from single to multi (or vice-versa), refresh it.
              updateRunnable = _adapter.createRefreshRunnable(notifier);
              // Also expand tree element.
              if (variable.isMultiValued()) {
                updateRunnable = _adapter.createCompositeRunnable(updateRunnable, new Runnable() {
                  public void run() {
                    _viewer.setExpandedState(variable, true);
                  }
                });
              }
            } else if (ContextsPackage.Literals.CONTRIBUTED_ELEMENT__SUPER_CATEGORY.equals(feature)) {
              // Variable is moved from a super category to another (e.g., cut) -> refresh new category.
              // Useful when moving a contributed variable in the same context (no remove and add notifications).
              updateRunnable = _adapter.createRefreshRunnable(notification_p.getNewValue());
            } else if (ContextsPackage.Literals.STRING_TO_STRING_MAP.isInstance(notifier)
                       && ContextsPackage.Literals.ENVIRONMENT_VARIABLE_VALUE.isInstance(((EObject) notifier).eContainer())) {
              // SET on a StringToString map having an EnvironmentVariableValue as parent -> content of the environment has changed.
              updateRunnable = _adapter.createRefreshRunnable(((EObject) notifier).eContainer());
            } else {
              // Else simply refresh the notifier element.
              updateRunnable = _adapter.createRefreshRunnable(notifier);
            }
          break;
          default:
          break;
        }
        // Update the viewer.
        if (null != updateRunnable) {
          _viewer.getControl().getDisplay().asyncExec(updateRunnable);
        }
      } catch (Exception e_p) {
        // Nothing to do, ignore the notification.
        CommonActivator.getInstance().logMessage("Ignoring a notification for variables viewer.\n" + notification_p.toString(), IStatus.INFO, e_p); //$NON-NLS-1$
      }
    }

    /**
     * Remove an element from the variable viewer
     * @param element_p Element to remove
     * @param feature_p
     * @param notifier_p
     * @return Runnable that will update the viewer accordingly
     */
    private Runnable removeElement(Object element_p, EStructuralFeature feature_p, Object notifier_p) {
      Runnable updateRunnable = null;
      if (ContextsPackage.Literals.CATEGORY__VARIABLES.equals(feature_p)) {
        // Removed a category variable.
        updateRunnable = _adapter.createRemoveRunnable(element_p);
      } else if (ContextsPackage.Literals.CONTEXT__SUPER_CATEGORY_VARIABLES.equals(feature_p)) {
        // Removed a super category variable.
        updateRunnable = _adapter.createRemoveRunnable(element_p);
      } else if (ContextsPackage.Literals.CONTEXT__OVERRIDING_VARIABLES.equals(feature_p)) {
        // Removed an overriding variable (occurs when all overriding values have been removed from an overriding variable).
        // Refresh overridden one.
        updateRunnable = _adapter.createRefreshRunnable(((OverridingVariable) element_p).getOverriddenVariable());
      } else if (ContextsPackage.Literals.ABSTRACT_VARIABLE__VALUES.equals(feature_p)) {
        // Removed a variable value.
        if (ContextsPackage.Literals.OVERRIDING_VARIABLE.isInstance(notifier_p)) {
          // Refresh overridden variable.
          updateRunnable = _adapter.createRefreshRunnable(((OverridingVariable) notifier_p).getOverriddenVariable());
        } else if (ContextsPackage.Literals.ABSTRACT_VARIABLE.isInstance(notifier_p)) {
          // Remove it from containing variable.
          updateRunnable = _adapter.createRemoveRunnable(element_p);
        }
      }
      return updateRunnable;
    }

    /**
     * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider#getChildren(java.lang.Object)
     */
    @Override
    public Object[] getChildren(Object object_p) {
      if (object_p instanceof Variable) {
        Variable var = (Variable) object_p;
        if (var.isMultiValued()) {
          return DataUtil.getRawValues(var, getContext()).toArray();
        }
        return null;
      }
      return null;
    }

    /**
     * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider#getElements(java.lang.Object)
     */
    @Override
    public Object[] getElements(Object object_p) {
      if (object_p instanceof Category) {
        ModelElementFilter currentModelElementFilter = VariablesView.getSharedInstance().getModelElementFilter();
        Collection<AbstractVariable> variables = DataUtil.getVariables((Category) object_p, getContext());
        if (!currentModelElementFilter.getCurrentFilterState().isFiltering()) {
          // Filter not activated -> return children list as it is.
          return variables.toArray();
        }
        List<AbstractVariable> selectedVariables = new ArrayList<AbstractVariable>();
        for (AbstractVariable variable : variables) {
          // Select children using ModelElementFilter.
          if (currentModelElementFilter.select(variable, _context)) {
            selectedVariables.add(variable);
          }
        }
        return selectedVariables.toArray();
      }
      return super.getElements(object_p);
    }

    /**
     * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider#hasChildren(java.lang.Object)
     */
    @Override
    public boolean hasChildren(Object object_p) {
      if (object_p instanceof Variable) {
        return ((Variable) object_p).isMultiValued();
      }
      return false;
    }

    @Override
    public void notifyChanged(Notification notification_p) {
      // Don't use notification mechanisms of AdapterFactoryContentProvider.
      // TODO DAMIEN See how to get rid of AdapterFactoryContentProvider.
    }
  }

  /**
   * Variables label provider.
   * @author t0076261
   */
  protected class VariablesLabelProvider extends AbstractLabelProvider implements ITableLabelProvider {
    /**
     * Font reminder.
     */
    private Font _boldFont;

    /**
     * @see com.thalesgroup.orchestra.framework.ui.viewer.AbstractLabelProvider#addErrorDecoration(com.thalesgroup.orchestra.framework.model.contexts.ModelElement,
     *      com.thalesgroup.orchestra.framework.model.contexts.Context, java.util.List, java.util.List, java.util.List)
     */
    @Override
    protected boolean addErrorDecoration(ModelElement element_p, Context editionContext_p, List<String> keySuffixes_p, List<Object> images_p,
        List<Point> positions_p) {
      boolean result = super.addErrorDecoration(element_p, editionContext_p, keySuffixes_p, images_p, positions_p);
      if (!result && (element_p instanceof AbstractVariable)) {
        // Try and test overriding variable too.
        OverridingVariable overridingVariable = DataUtil.getOverridingVariable((AbstractVariable) element_p, editionContext_p);
        if (null != overridingVariable) {
          result = super.addErrorDecoration(overridingVariable, editionContext_p, keySuffixes_p, images_p, positions_p);
        }
      }
      return result;
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
      }
    }

    /**
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
     */
    public Image getColumnImage(Object element_p, int columnIndex_p) {
      Image result = null;
      if (element_p instanceof Variable) {
        Variable variable = (Variable) element_p;
        switch (columnIndex_p) {
          case COLUMN_INDEX_NAME:
            result = getImage(variable, getContext());
          break;
          case COLUMN_INDEX_VALUE:
            if (!variable.isMultiValued()) {
              List<VariableValue> rawValues = DataUtil.getRawValues(variable, getContext());
              // Make sure there is one value at least.
              if (rawValues.isEmpty()) {
                return result;
              }
              result = getImage(rawValues.get(0), getContext());
            }
          break;
          default:
          break;
        }
      } else if (element_p instanceof VariableValue) {
        VariableValue variableValue = (VariableValue) element_p;
        switch (columnIndex_p) {
          case COLUMN_INDEX_NAME:
          // Do nothing.
          break;
          case COLUMN_INDEX_VALUE:
            result = getImage(variableValue, getContext());
          break;
          default:
          break;
        }
      }
      return result;
    }

    /**
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
     */
    public String getColumnText(Object element_p, int columnIndex_p) {
      String result = ICommonConstants.EMPTY_STRING;
      if (element_p instanceof Variable) {
        Variable variable = (Variable) element_p;
        switch (columnIndex_p) {
          case COLUMN_INDEX_NAME:
            result = getText(variable, getContext());
          break;
          case COLUMN_INDEX_VALUE:
            if (!variable.isMultiValued()) {
              List<VariableValue> rawValues = DataUtil.getRawValues(variable, getContext());
              // Make sure there is one value at least.
              if (rawValues.isEmpty()) {
                return result;
              }
              result = getText(rawValues.get(0), getContext());
            }
          break;
          default:
          break;
        }
      } else if (element_p instanceof VariableValue) {
        VariableValue variableValue = (VariableValue) element_p;
        switch (columnIndex_p) {
          case COLUMN_INDEX_NAME:
          // Do nothing.
          break;
          case COLUMN_INDEX_VALUE:
            result = getText(variableValue, getContext());
          break;
          default:
          break;
        }
      }
      return result;
    }

    /**
     * Get font for specified label.
     * @param label_p
     * @return
     */
    public Font getFont(String label_p) {
      if ((null != _filter) && _filter.highlightLabel(label_p)) {
        if (null == _boldFont) {
          _boldFont = new Font(_viewer.getControl().getDisplay(), _viewer.getTree().getFont().toString(), 9, SWT.NORMAL | SWT.BOLD);
        }
        return _boldFont;
      }
      return null;
    }

    /**
     * @see org.eclipse.jface.viewers.CellLabelProvider#update(org.eclipse.jface.viewers.ViewerCell)
     */
    @Override
    public void update(ViewerCell cell_p) {
      Object element = cell_p.getElement();
      int columnIndex = cell_p.getColumnIndex();
      String label = getColumnText(element, columnIndex);
      cell_p.setText(label);
      cell_p.setImage(getColumnImage(element, columnIndex));
      cell_p.setFont(getFont(label));
      cell_p.setForeground(getForeground((EObject) element, getContext()));
    }
  }

}