/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.viewer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.ui.action.CreateChildAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreeNodeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.model.IEditionContextProvider;
import com.thalesgroup.orchestra.framework.model.ISelectedContextsProvider;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Category;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.contexts.ContributedElement;
import com.thalesgroup.orchestra.framework.model.contexts.ModelElement;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingVariable;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingVariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.util.ContextsResourceImpl;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.command.IClipboardContentChangedListener;
import com.thalesgroup.orchestra.framework.model.handler.data.ContextsEditingDomain;
import com.thalesgroup.orchestra.framework.model.handler.data.ContextsEditingDomain.ClipboardElement;
import com.thalesgroup.orchestra.framework.model.handler.data.DataHandler;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.model.handler.data.RootElement;
import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;
import com.thalesgroup.orchestra.framework.project.mode.IAdministratorModeListener;
import com.thalesgroup.orchestra.framework.root.ui.AbstractRunnableWithDisplay;
import com.thalesgroup.orchestra.framework.root.ui.DisplayHelper;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper.LayoutType;
import com.thalesgroup.orchestra.framework.ui.action.EditElementAction;
import com.thalesgroup.orchestra.framework.ui.action.ExportContextsAction;
import com.thalesgroup.orchestra.framework.ui.action.ImportContextsAction;
import com.thalesgroup.orchestra.framework.ui.action.InitializeCurrentVersionsAction;
import com.thalesgroup.orchestra.framework.ui.action.NewContextAction;
import com.thalesgroup.orchestra.framework.ui.action.RestoreVersionAction;
import com.thalesgroup.orchestra.framework.ui.action.SelectVersionAction;
import com.thalesgroup.orchestra.framework.ui.action.SetAsCurrentContextAction;
import com.thalesgroup.orchestra.framework.ui.action.SynchronizeAction;
import com.thalesgroup.orchestra.framework.ui.action.SynchronizeAllAction;
import com.thalesgroup.orchestra.framework.ui.action.ValidateAction;
import com.thalesgroup.orchestra.framework.ui.internal.viewer.FilteredTree;
import com.thalesgroup.orchestra.framework.ui.internal.viewer.IModelElementFilterListener;
import com.thalesgroup.orchestra.framework.ui.internal.viewer.ModelElementFilter.FilterState;
import com.thalesgroup.orchestra.framework.ui.internal.viewer.PatternFilter;
import com.thalesgroup.orchestra.framework.ui.internal.viewer.StructuredSelectionWithContext;
import com.thalesgroup.orchestra.framework.ui.view.ISharedActionsHandler;
import com.thalesgroup.orchestra.framework.ui.view.SharedActionsUpdater;
import com.thalesgroup.orchestra.framework.ui.view.VariablesView;
import com.thalesgroup.orchestra.framework.ui.viewer.component.AbstractNode;
import com.thalesgroup.orchestra.framework.ui.viewer.component.CategoryNode;
import com.thalesgroup.orchestra.framework.ui.viewer.component.RootElementNode;
import com.thalesgroup.orchestra.framework.ui.wizard.OpenChangeWizardListener;

/**
 * @author T0076261
 */
// TODO Guillaume
// Add dispose method.
public class ContextsViewer implements IMenuListener, IAdministratorModeListener, IModelElementFilterListener {
  private static final String POPUP_MENU_GROUP_ACTION = "ACTION_GROUP"; //$NON-NLS-1$
  private static final String POPUP_MENU_GROUP_EDIT = "EDIT_GROUP"; //$NON-NLS-1$
  private static final String POPUP_MENU_GROUP_EXPORT = "EXPORT_GROUP"; //$NON-NLS-1$
  protected static final String POPUP_MENU_GROUP_NEW = "NEW_GROUP"; //$NON-NLS-1$
  protected static final String POPUP_MENU_GROUP_NEW_CONTEXT = "NEW_CONTEXT_GROUP"; //$NON-NLS-1$
  private static final String POPUP_MENU_GROUP_SELECT_VERSION = "SELECT_VERSION_GROUP"; //$NON-NLS-1$
  private static final String POPUP_MENU_GROUP_VALIDATE = "VALIDATE_GROUP"; //$NON-NLS-1$
  protected static final String POPUP_MENU_ID_NEWMENU = "NewMenu"; //$NON-NLS-1$
  protected static final String POPUP_MENU_ID_ROOTMENU = "TreePopup"; //$NON-NLS-1$
  /**
   * 
   */
  private static final String POPUP_MENU_ID_VERSIONS_CONFIGURE = "ConfigureMenu"; //$NON-NLS-1$
  /**
   * Content provider
   */
  private ContextsContentProvider _contextsContentProvider;
  /**
   * Label provider.
   */
  private ContextsLabelProvider _contextsLabelProvider;
  /**
   * Filter reference.
   */
  protected PatternFilter _filter;
  /**
   * Filtered tree reference.
   */
  private FilteredTree _filteredTree;
  /**
   * Action to create a new admin/user context.
   */
  private NewContextAction _newContextAction;
  /**
   * Section reminder.
   */
  private Section _section;
  /**
   * Selection provider adapter.
   */
  protected SelectionProviderAdapter _selectionProviderAdapter;
  /**
   * Set as current context action.
   */
  private SetAsCurrentContextAction _setAsCurrentAction;
  /**
   * Shared actions updater.
   */
  private SharedActionsUpdater _sharedActionsUpdater;
  /**
   * Validate action contribution.
   */
  private ValidateAction _validateAction;
  /**
   * Viewer reminder.
   */
  protected TreeViewer _viewer;
  /**
   * Synchronize all contexts action
   */
  private SynchronizeAllAction _synchronizeAllAction;

  /**
   * Create content provider.<br>
   * Result must not be <code>null</code> !
   * @return A new content provider for this viewer, must not be <code>null</code>.
   */
  protected ContextsContentProvider createContentProvider() {
    _contextsContentProvider = new ContextsContentProvider();
    return _contextsContentProvider;
  }

  /**
   * Check if contexts are available within the workspace
   * @return
   */
  protected boolean hasContexts() {
    DataHandler dataHandler = ModelHandlerActivator.getDefault().getDataHandler();
    RootElement rootElement = dataHandler.getAllContextsInWorkspace();
    List<Context> contextList = rootElement.getContexts();
    return contextList != null && !contextList.isEmpty();
  }

  /**
   * Enable/Disable "Synchronize All" action state with respect to mode and available contexts
   */
  protected void updateSynchronizeAllActionState() {
    // _synchronizeAllAction.setEnabled(ProjectActivator.getInstance().isAdministrator() && hasContexts());
    _synchronizeAllAction.setEnabled(ProjectActivator.getInstance().isAdministrator());
  }

  /**
   * Create contextual menu.
   */
  protected void createContextualMenu(IWorkbenchPartSite site_p, ISharedActionsHandler sharedActionsHandler_p) {
    // Customized MenuManager to manage "New" menu enablement.
    MenuManager contextMenu = new MenuManager(ICommonConstants.EMPTY_STRING, POPUP_MENU_ID_ROOTMENU) {
      /**
       * @see org.eclipse.jface.action.MenuManager#update(boolean, boolean)
       */
      @Override
      protected void update(boolean force_p, boolean recursive_p) {
        super.update(force_p, recursive_p);
        Item[] items = getMenuItems();
        if (null == items) {
          return;
        }
        // Find the "New" menu item amongst items of the contextual menu and set its enabled state.
        for (Item item : items) {
          // MenuItems contain in there data field the ContributionManager which created them (see super.update(force_p, recursive_p)).
          if (item instanceof MenuItem && item.getData() instanceof MenuManager && POPUP_MENU_ID_NEWMENU == ((MenuManager) item.getData()).getId()) {
            // "New" menu is enabled only if one item is selected
            IStructuredSelection structuredSelection = (IStructuredSelection) _selectionProviderAdapter.getSelection();
            ((MenuItem) item).setEnabled(1 == structuredSelection.size());
            break;
          }
        }
      }
    };
    contextMenu.addMenuListener(this);
    // Add new context group.
    contextMenu.add(new Separator(POPUP_MENU_GROUP_NEW_CONTEXT));
    _newContextAction = new NewContextAction();
    contextMenu.add(_newContextAction);
    // Add new group.
    contextMenu.add(new Separator(POPUP_MENU_GROUP_NEW));
    MenuManager newMenu = new MenuManager(Messages.ContextsViewer_NewMenu_Text, POPUP_MENU_ID_NEWMENU) {
      /**
       * @see org.eclipse.jface.action.MenuManager#isVisible()
       */
      @Override
      public boolean isVisible() {
        // Force "New" menu visibility to true: this menu must always be visible even if it contains no item.
        return true;
      }
    };
    // Register menu listener.
    newMenu.addMenuListener(this);
    contextMenu.add(newMenu);
    // Add external actions group.
    contextMenu.add(new Separator(POPUP_MENU_GROUP_ACTION));
    // Add set as current action.
    _setAsCurrentAction = new SetAsCurrentContextAction();
    // Wait for selection to decide.
    _setAsCurrentAction.setEnabled(false);
    _selectionProviderAdapter.addSelectionChangedListener(_setAsCurrentAction);
    contextMenu.add(_setAsCurrentAction);
    // Add edit group.
    contextMenu.add(new Separator(POPUP_MENU_GROUP_EDIT));
    // Create edit action.
    IEditionContextProvider editionContextProvider = new IEditionContextProvider() {
      /**
       * @see com.thalesgroup.orchestra.framework.model.IEditionContextProvider#getEditionContext()
       */
      @Override
      public Context getEditionContext() {
        return findContextForCurrentSelection();
      }
    };
    EditElementAction editAction = new EditElementAction(site_p.getShell(), editionContextProvider);
    // Wait for selection to decide.
    editAction.setEnabled(false);
    _selectionProviderAdapter.addSelectionChangedListener(editAction);
    contextMenu.add(editAction);
    // Add double-click listener based on edit action.
    // That explains why it sits here.
    _selectionProviderAdapter.addDoubleClickListener(new OpenChangeWizardListener(editAction));
    // Add shared edition actions, if any.
    if (null != sharedActionsHandler_p) {
      contextMenu.add(sharedActionsHandler_p.getCutAction());
      contextMenu.add(sharedActionsHandler_p.getCopyAction());
      contextMenu.add(sharedActionsHandler_p.getPasteAction());
      contextMenu.add(sharedActionsHandler_p.getDeleteAction());
      // Create shared actions updater for this viewer.
      _sharedActionsUpdater = new SharedActionsUpdater(sharedActionsHandler_p, _selectionProviderAdapter);
      _selectionProviderAdapter.addSelectionChangedListener(_sharedActionsUpdater);
      _viewer.getControl().addFocusListener(_sharedActionsUpdater);
    }
    // Add product version selection group.
    contextMenu.add(new Separator(POPUP_MENU_GROUP_SELECT_VERSION));
    // Select version.
    SelectVersionAction selectVersionAction = new SelectVersionAction(editionContextProvider);
    contextMenu.add(selectVersionAction);
    // Wait for selection to decide.
    selectVersionAction.setEnabled(false);
    _selectionProviderAdapter.addSelectionChangedListener(selectVersionAction);
    // De-select version.
    RestoreVersionAction restoreVersionAction = new RestoreVersionAction(editionContextProvider);
    contextMenu.add(restoreVersionAction);
    // Wait for selection to decide.
    restoreVersionAction.setEnabled(false);
    _selectionProviderAdapter.addSelectionChangedListener(restoreVersionAction);
    // Add configure group.
    MenuManager configureMenu = new MenuManager(Messages.ContextsViewer_ConfigureMenu_Text, POPUP_MENU_ID_VERSIONS_CONFIGURE);
    InitializeCurrentVersionsAction initializeCurrentVersionsAction = new InitializeCurrentVersionsAction();
    configureMenu.add(initializeCurrentVersionsAction);
    contextMenu.add(configureMenu);
    _selectionProviderAdapter.addSelectionChangedListener(initializeCurrentVersionsAction);
    // Add import/export group.
    contextMenu.add(new Separator(POPUP_MENU_GROUP_EXPORT));
    final ImportContextsAction importAction = new ImportContextsAction(site_p.getShell());
    final ExportContextsAction exportAction = new ExportContextsAction(site_p.getShell(), new ISelectedContextsProvider() {
      @Override
      public List<Context> getSelectedContexts() {
        return findContextsForCurrentSelection();
      }
    });
    final SynchronizeAction synchronizeAction = new SynchronizeAction();
    contextMenu.add(importAction);
    contextMenu.add(exportAction);
    contextMenu.add(synchronizeAction);
    _synchronizeAllAction = new SynchronizeAllAction();
    contextMenu.add(_synchronizeAllAction);
    updateSynchronizeAllActionState();
    // Initial enablement.
    boolean isAdmin = ProjectActivator.getInstance().isAdministrator();
    importAction.setEnabled(isAdmin);
    exportAction.setEnabled(isAdmin);
    // Wait for selection to decide.
    synchronizeAction.setEnabled(false);
    _selectionProviderAdapter.addSelectionChangedListener(synchronizeAction);
    // Add administrator mode listener.
    ProjectActivator.getInstance().addAdministratorModeChangeListener(new IAdministratorModeListener() {
      public void modeChanged(boolean newAdministratorState_p) {
        importAction.setEnabled(newAdministratorState_p);
        exportAction.setEnabled(newAdministratorState_p);
      }
    });
    // Add validate group.
    contextMenu.add(new Separator(POPUP_MENU_GROUP_VALIDATE));
    _validateAction = new ValidateAction(editionContextProvider);
    // Wait for selection to decide.
    _validateAction.setEnabled(false);
    _selectionProviderAdapter.addSelectionChangedListener(_validateAction);
    contextMenu.add(_validateAction);
    // Add addition separator.
    contextMenu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
    // Register menu.
    Control control = _viewer.getControl();
    Menu menu = contextMenu.createContextMenu(control);
    control.setMenu(menu);
  }

  /**
   * Create label provider.<br>
   * Result must not be <code>null</code> !
   * @return A new label provider for this viewer, must not be <code>null</code>.
   */
  protected ContextsLabelProvider createLabelProvider() {
    _contextsLabelProvider = new ContextsLabelProvider(_viewer, _filter);
    return _contextsLabelProvider;
  }

  /**
   * Create viewer.
   * @param parent_p
   */
  public void createViewer(FormToolkit toolkit_p, Composite parent_p, IWorkbenchPartSite site_p, ISharedActionsHandler sharedActionsHandler_p) {
    // Tree parent composite.
    Composite parent = createViewerParentComposite(toolkit_p, FormHelper.createCompositeWithLayoutType(toolkit_p, parent_p, LayoutType.GRID_LAYOUT, 1, false));
    // Create filtered tree.
    _filter = new PatternFilter();
    _filteredTree = new FilteredTree(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL, _filter);
    _viewer = _filteredTree.getViewer();
    // Create content/label providers.
    _viewer.setContentProvider(createContentProvider());
    _viewer.setLabelProvider(createLabelProvider());
    // Create sorter.
    _viewer.setSorter(new ViewerSorter());
    // Create selection adapter.
    _selectionProviderAdapter = new SelectionProviderAdapter();
    // Layout viewer.
    FormHelper.updateControlLayoutDataWithLayoutTypeData(_viewer.getControl(), LayoutType.GRID_LAYOUT);
    // Enable customization.
    customizeViewer(_viewer, toolkit_p, parent_p, site_p);
    // Create contextual menu.
    createContextualMenu(site_p, sharedActionsHandler_p);
    // Add context management listener.
    ContextsManagementListener contextsManagementListener = new ContextsManagementListener();
    ModelHandlerActivator.getDefault().getEditingDomain().getNotificationsListener().addAdapter(contextsManagementListener);
    ModelHandlerActivator.getDefault().getEditingDomain().addClipboardContentChangedListener(contextsManagementListener);
    // Register as administrator mode listener.
    ProjectActivator.getInstance().addAdministratorModeChangeListener(this);
    // Register as model element filter listener.
    VariablesView.getSharedInstance().getModelElementFilter().addListener(this);
    // Set initial input.
    _viewer.setInput(getInitialInput());
  }

  /**
   * Create viewer parent composite.
   * @param toolkit_p
   * @param parent_p
   * @return Must not be <code>null</code>.
   */
  protected Composite createViewerParentComposite(FormToolkit toolkit_p, Composite parent_p) {
    // Create a section with a contained composite.
    Map<Class, Control> treeSection =
        FormHelper.createSectionWithChildComposite(toolkit_p, parent_p, ExpandableComposite.TITLE_BAR | ExpandableComposite.EXPANDED, LayoutType.GRID_LAYOUT,
            1, false);
    _section = (Section) treeSection.get(Section.class);
    _section.setText(Messages.ContextsViewer_SectionTitle);
    return (Composite) treeSection.get(Composite.class);
  }

  /**
   * Customize viewer before initial input is applied, and viewer displayed.
   * @param viewer_p
   * @param toolkit_p
   * @param parent_p
   * @param site_p
   */
  protected void customizeViewer(TreeViewer viewer_p, FormToolkit toolkit_p, Composite parent_p, IWorkbenchPartSite site_p) {
    // Set filter.
    viewer_p.addFilter(new ViewerFilter() {
      @Override
      public boolean select(Viewer selectedViewer_p, Object parentElement_p, Object element_p) {
        // Don't display Variables (and therefore VariableValues).
        return !(((TreeNode) element_p).getValue() instanceof AbstractVariable);
      }
    });
    // Set context listener.
    _selectionProviderAdapter.addSelectionChangedListener(new ISelectionChangedListener() {
      public void selectionChanged(SelectionChangedEvent event_p) {
        // Set edition context.
        ModelHandlerActivator.getDefault().getEditingDomain().setEditionContext(findContextForCurrentSelection());
      }
    });
    // Set tool-tip support.
    ColumnViewerToolTipSupport.enableFor(viewer_p);
  }

  /**
   * Find logically containing context for current selection in the tree.
   * @return <code>null</code> if current selection results to several contexts.
   */
  public Context findContextForCurrentSelection() {
    List<Context> contextsContainingSelection = findContextsForCurrentSelection();
    if (1 != contextsContainingSelection.size()) {
      return null;
    }
    return contextsContainingSelection.get(0);
  }

  /**
   * Returns all contexts selected or containing selected elements. Returned list contained no duplicate contexts.
   * @return a list of contexts.
   */
  public List<Context> findContextsForCurrentSelection() {
    final List<Context> result = new ArrayList<Context>(0);
    DisplayHelper.displayRunnable(new AbstractRunnableWithDisplay() {
      /**
       * @see java.lang.Runnable#run()
       */
      public void run() {
        ISelection selection = _viewer.getSelection();
        if (!(selection instanceof IStructuredSelection)) {
          return;
        }
        IStructuredSelection structuredSelection = (IStructuredSelection) selection;
        for (Object selectedElement : structuredSelection.toList()) {
          if (selectedElement instanceof AbstractNode<?>) {
            Context selectedContext = ((AbstractNode<?>) selectedElement).getEditionContext();
            if (null != selectedContext && !result.contains(selectedContext)) {
              result.add(selectedContext);
            }
          }
        }
      }
    }, false);
    return result;
  }

  /**
   * Get content provider in use.
   * @return
   */
  protected ContextsContentProvider getContentProvider() {
    return (ContextsContentProvider) _viewer.getContentProvider();
  }

  /**
   * @return the contextsContentProvider
   */
  public ContextsContentProvider getContextsContentProvider() {
    return _contextsContentProvider;
  }

  /**
   * @return the contextsLabelProvider
   */
  public ContextsLabelProvider getContextsLabelProvider() {
    return _contextsLabelProvider;
  }

  /**
   * Get initial input.
   * @return Can not be <code>null</code> !
   */
  protected RootElement getInitialInput() {
    return ModelHandlerActivator.getDefault().getDataHandler().getAllContextsInWorkspace();
  }

  /**
   * Get in-use pattern filter.
   * @return
   */
  public PatternFilter getPatternFilter() {
    return _filter;
  }

  /**
   * Get parent section.
   * @return
   */
  public Section getSection() {
    return _section;
  }

  /**
   * Get selection provider.
   * @return
   */
  public ISelectionProvider getSelectionProvider() {
    return _selectionProviderAdapter;
  }

  /**
   * Get shared action updater.
   * @return
   */
  public SharedActionsUpdater getSharedActionsUpdater() {
    return _sharedActionsUpdater;
  }

  /**
   * Get internal viewer.
   * @return
   */
  public TreeViewer getViewer() {
    return _viewer;
  }

  /**
   * @see org.eclipse.jface.action.IMenuListener#menuAboutToShow(org.eclipse.jface.action.IMenuManager)
   */
  public void menuAboutToShow(IMenuManager manager_p) {
    ISelection selection = _selectionProviderAdapter.getSelection();
    if (!(selection instanceof IStructuredSelection)) {
      return;
    }
    IStructuredSelection structuredSelection = (IStructuredSelection) selection;
    if (POPUP_MENU_ID_ROOTMENU.equals(manager_p.getId())) {
      // Update "New Context" action state.
      _newContextAction.setEnabled(!VariablesView.getSharedInstance().getModelElementFilter().getCurrentFilterState().isFiltering());
      // Update "Set current context" action state.
      _setAsCurrentAction.selectionChanged(structuredSelection);
      // Update "Validate" action state.
      _validateAction.selectionChanged(new SelectionChangedEvent(_viewer, structuredSelection));

      // Update "Synchronize All" action state
      updateSynchronizeAllActionState();
    } else if (POPUP_MENU_ID_NEWMENU.equals(manager_p.getId())) {
      // Empty the menu manager before filling it.
      manager_p.removeAll();
      // New element actions available only if unique selection.
      if (1 == structuredSelection.size()) {
        // Get edition context.
        Context editionContext = findContextForCurrentSelection();
        // Get the only selected element.
        EObject object = (EObject) structuredSelection.getFirstElement();
        // Add creation actions.
        ContextsEditingDomain domain = ModelHandlerActivator.getDefault().getEditingDomain();
        Collection<?> newChildDescriptors = domain.getNewChildDescriptors(object, null);
        boolean unmodifiable = DataUtil.isUnmodifiable(object, editionContext);
        for (Object newChildDescriptor : newChildDescriptors) {
          // Create action in any case.
          CreateChildAction action = new CreateChildAction(domain, selection, newChildDescriptor);
          // Mark the action as disabled if element is unmodifiable or mandatory filter is active.
          if (unmodifiable || VariablesView.getSharedInstance().getModelElementFilter().getCurrentFilterState().isFiltering()) {
            action.setEnabled(false);
          }
          manager_p.add(action);
        }
      }
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.project.mode.IAdministratorModeListener#modeChanged(boolean)
   */
  public void modeChanged(boolean newAdministratorState_p) {
    // Clear filter text before clearing initial input.
    _filteredTree.clearText();
    // Clear initial input.
    _viewer.setInput(ModelHandlerActivator.getDefault().getDataHandler().getAllContextsInWorkspace());
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.viewer.IModelElementFilterListener#modelElementFilterChanged()
   */
  public void modelElementFilterChanged(FilterState newFilterState_p) {
    if (null != _viewer && !_viewer.getTree().isDisposed()) {
      // Force viewer refresh, to apply new filter.
      _viewer.refresh();
      boolean isPatternFilterActive = (null != _filteredTree.getFilterControl().getText()) && !_filteredTree.getFilterControl().getText().isEmpty();
      if (newFilterState_p.isFiltering() || isPatternFilterActive) {
        // Expand all tree nodes when at least one filter is on.
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
   * Refresh specified element within specified context.
   * @param element_p
   * @param context_p
   */
  public void refreshElement(Object element_p, Context context_p) {
    // Get context node.
    ContextsContentProvider contentProvider = getContentProvider();
    // Precondition.
    if (null == contentProvider) {
      return;
    }
    RootElementNode root = contentProvider.getRootElement();
    AbstractNode<?> parentNode = root.getChildNode(context_p);
    // Nothing to refresh.
    if (null == parentNode) {
      return;
    }
    // Get relevant element.
    Object selectedElement = element_p;
    Category category = ModelUtil.getCategory(selectedElement);
    if (null != category) {
      selectedElement = category;
    } // If category is null, then element is most likely a context. Leave it that way.
    AbstractNode<?> childNode = parentNode.getChildNode(selectedElement);
    // Still nothing to refresh.
    if (null == childNode) {
      return;
    }
    _viewer.update(childNode, null);
  }

  /**
   * Set content provider in use.
   * @return
   */
  public void setContentProvider(ContextsContentProvider provider) {
    _viewer.setContentProvider(provider);
  }

  /**
   * @param filter_p the filter to set
   */
  public void setFilter(ViewerFilter filter_p) {

    _viewer.setFilters(new ViewerFilter[] { filter_p });
  }

  /**
   * Set label provider in use.
   * @return
   */
  public void setLabelProvider(ContextsLabelProvider provider) {
    _viewer.setLabelProvider(provider);
  }

  /**
   * Content provider that enables the use of the root element.
   * @author t0076261
   */
  public static class ContextsContentProvider extends TreeNodeContentProvider {
    /**
     * Root node.
     */
    protected RootElementNode _rootNode;

    /**
     * @see org.eclipse.jface.viewers.TreeNodeContentProvider#getElements(java.lang.Object)
     */
    @Override
    public Object[] getElements(Object object_p) {
      if (object_p instanceof RootElement) {
        // Either this is a first time, or the input has changed.
        if ((null == _rootNode) || (object_p != _rootNode.getValue())) {
          // Recompute the whole thing.
          _rootNode = new RootElementNode((RootElement) object_p);
        }
        return _rootNode.getChildren();
      }
      return super.getElements(object_p);
    }

    /**
     * Get root element.
     * @return
     */
    protected RootElementNode getRootElement() {
      return _rootNode;
    }
  }

  /**
   * Contexts addition/removal listener.
   * @author t0076261
   */
  protected class ContextsManagementListener extends AbstractViewerNotifier implements IClipboardContentChangedListener {
    /**
     * Constructor.
     */
    public ContextsManagementListener() {
      super(getViewer());
    }

    /**
     * @see com.thalesgroup.orchestra.framework.model.handler.command.IClipboardContentChangedListener#clipboardElementAdded(com.thalesgroup.orchestra.framework.model.handler.data.ContextsEditingDomain.ClipboardElement)
     */
    @Override
    public void clipboardElementAdded(ClipboardElement clipboardElement_p) {
      for (EObject modelElement : clipboardElement_p._elements) {
        Runnable updateRunnable = createUpdateRunnable(modelElement, clipboardElement_p._context);
        // Refresh the viewer, if needed.
        if (null != updateRunnable) {
          getViewer().getControl().getDisplay().asyncExec(updateRunnable);
        }
      }

    }

    /**
     * @see com.thalesgroup.orchestra.framework.model.handler.command.IClipboardContentChangedListener#clipboardElementRemoved(com.thalesgroup.orchestra.framework.model.handler.data.ContextsEditingDomain.ClipboardElement)
     */
    @Override
    public void clipboardElementRemoved(ClipboardElement clipboardElement_p) {
      for (EObject modelElement : clipboardElement_p._elements) {
        Runnable updateRunnable = createUpdateRunnable(modelElement, clipboardElement_p._context);
        // Refresh the viewer, if needed.
        if (null != updateRunnable) {
          getViewer().getControl().getDisplay().asyncExec(updateRunnable);
        }
      }
    }

    /**
     * @see com.thalesgroup.orchestra.framework.ui.viewer.AbstractViewerNotifier#createAddRunnable(java.lang.Object, java.lang.Object)
     */
    protected Runnable createAddRunnable(Object parent_p, Object newElement_p, Object rootParent_p) {
      // Get existing parent node.
      Object parent = parent_p;
      RootElementNode root = getContentProvider().getRootElement();
      AbstractNode<?> parentNode = null;
      if (null != rootParent_p) {
        parentNode = root.getChildNode(rootParent_p);
      } else {
        parentNode = root.getChildNode(parent);
      }
      if (null == parentNode) {
        return null;
      } else if (parentNode != root) { // Does not apply to root.
        parent = parentNode;
      }
      // Get new element node.
      final Object newElement = RootElementNode.createNode(newElement_p);
      if (null == newElement) {
        return null;
      }
      // Add it to parent node.
      AbstractNode<?> containingNode = parentNode.getChildNode(parent_p);
      if (null != containingNode) {
        containingNode.addChildNode((AbstractNode<?>) newElement);
        if (!(containingNode instanceof RootElementNode)) { // Does not apply to root.
          parent = containingNode;
        }
      }

      final Object parentElement = parent;
      Runnable runnable = new Runnable() {
        public void run() {
          TreeViewer viewer = getViewer();
          // Add element to tree
          viewer.add(parentElement, newElement);
          // If element is a category, reveal and select it
          if (newElement instanceof CategoryNode) {
            viewer.setSelection(new StructuredSelection(newElement), true);
          }
        }
      };
      return runnable;
    }

    /**
     * Create refresh runnable for specified element in specified context.
     * @param element_p
     * @param context_p
     * @return
     */
    protected Runnable createRefreshRunnable(Object element_p, Context context_p) {
      // Get existing node.
      RootElementNode root = getContentProvider().getRootElement();
      // Get parent node.
      AbstractNode<?> parentNode = root;
      if (null != context_p) {
        // Try and get from context element.
        parentNode = root.getChildNode(context_p);
        // Fall-back to root node.
        if (null == parentNode) {
          parentNode = root;
        }
      }
      // Get child node.
      AbstractNode<?> node = parentNode.getChildNode(element_p);
      if (null == node) {
        return null;
      }
      // Be sure all children will be recalculated.
      node.invalidateChildrenList();
      // Call super behavior.
      return super.createRefreshRunnable(node);
    }

    /**
     * @see com.thalesgroup.orchestra.framework.ui.viewer.AbstractViewerNotifier#createRemoveRunnable(java.lang.Object)
     */
    @Override
    protected Runnable createRemoveRunnable(Object oldElement_p) {
      // Get existing node.
      RootElementNode root = getContentProvider().getRootElement();
      AbstractNode<?> node = root.getChildNode(oldElement_p);
      if (null == node) {
        return null;
      }
      AbstractNode<?> parentNode = node.getParent();
      if (null != parentNode) {
        parentNode.removeChildNode(node);
      }
      // Call super behavior.
      return super.createRemoveRunnable(node);
    }

    /**
     * Create update runnable within specified context.
     * @param element_p
     * @param context_p
     * @return
     */
    protected Runnable createUpdateRunnable(Object element_p, Context context_p) {
      // Get existing node.
      RootElementNode root = getContentProvider().getRootElement();
      // Get parent node.
      AbstractNode<?> parentNode = root;
      if (null != context_p) {
        // Try and get from context element.
        parentNode = root.getChildNode(context_p);
        // Fall-back to root node.
        if (null == parentNode) {
          parentNode = root;
        }
      }
      // Get child node.
      TreeNode node = parentNode.getChildNode(element_p);
      if (null == node) {
        return null;
      }
      // Call super behavior.
      return super.createUpdateRunnable(node);
    }

    /**
     * @see org.eclipse.emf.common.notify.impl.AdapterImpl#notifyChanged(org.eclipse.emf.common.notify.Notification)
     */
    @Override
    public void notifyChanged(Notification notification_p) {
      Object input = getViewer().getInput();
      // Get notifier.
      Object notifier = notification_p.getNotifier();
      int notificationType = notification_p.getEventType();
      Runnable updateRunnable = null;
      // Get associated context.
      Context context = ModelUtil.getContext(notifier);
      if (null != context) {
        ContextsResourceImpl resource = context.eResource();
        // Stop here if resource is loading.
        if ((null != resource) && resource.isLoading()) {
          return;
        }
      }
      Object newElement = notification_p.getNewValue();
      Object feature = notification_p.getFeature();
      Object oldElement = notification_p.getOldValue();
      // Go on with modification type.
      switch (notificationType) {
        case Notification.ADD:
          if (newElement instanceof Context) {
            // A new context has been added.
            Context newContext = (Context) newElement;
            // Make sure RootElement is updated too, because of the filtered tree behavior.
            if (input instanceof RootElement) {
              RootElement rootElement = (RootElement) input;
              URI contextURI = newContext.eResource().getURI();
              if (rootElement.isHandling(contextURI)) {
                // User case.
                // The project might exist in the workspace as an administrator.
                // But there was no participation for this user yet.
                if (!ProjectActivator.getInstance().isAdministrator()) {
                  // Add it to root entry.
                  updateRunnable = createAddRunnable(input, newContext, null);
                }
                // Do nothing more !
                break;
              }
              // Get contexts project for context URI.
              RootContextsProject contextsProject = ProjectActivator.getInstance().getProjectHandler().getProjectFromContextUri(contextURI);
              // Make sure that this is the expected context.
              if ((null != contextsProject) && contextsProject.isHandling(contextURI)) {
                updateRunnable = createAddRunnable(input, newContext, null);
                if (null != contextsProject) {
                  rootElement.addProject(contextsProject);
                }
              }
            }
          } else if (newElement instanceof Category || newElement instanceof Variable) {
            // Find parent object (in the tree).
            // Default parent is notifier.
            Object parent = notifier;
            ContributedElement newContributedElement = (ContributedElement) newElement;
            if (null != newContributedElement.getSuperCategory()) {
              // newElement is contributed to an inherited category -> use this category as parent.
              parent = newContributedElement.getSuperCategory();
            }
            // If this is a current version category, do not add element to parent, but instead refresh whole targeted element.
            if (ContextsPackage.Literals.CONTEXT__CURRENT_VERSIONS.equals(feature)) {
              // Make sure this is context for current selection that is refreshed first.
              Context contextToRefresh = findContextForCurrentSelection();
              if (null == contextToRefresh) {
                contextToRefresh = context;
              }
              updateRunnable = createRefreshRunnable(parent, contextToRefresh);
            }
            // Add it to parent object.
            // Exclude selected versions.
            else if (!ContextsPackage.Literals.CONTEXT__SELECTED_VERSIONS.equals(feature)) {
              updateRunnable = createAddRunnable(parent, newElement, notifier);
            }
          } else if (newElement instanceof OverridingVariable) {
            // A new overriding variable has been created -> refresh the node containing the variable.
            Variable overriddenVariable = ((OverridingVariable) newElement).getOverriddenVariable();
            Runnable refreshVariableRunnable = createRefreshRunnable(overriddenVariable, context);
            // Update its category to reflect new decoration (if any).
            Runnable updateCategory = createUpdateRunnable(ModelUtil.getCategory(newElement), context);
            updateRunnable = createCompositeRunnable(refreshVariableRunnable, updateCategory);
          } else if (newElement instanceof VariableValue && !(newElement instanceof OverridingVariableValue)) {
            // A new value has been added to the variable (normal Variable or OverridingVariable).
            ModelElement parent = ModelUtil.getLogicalContainer(newElement, context);
            updateRunnable = createAddRunnable(parent, newElement, context);
          } else if (newElement instanceof OverridingVariableValue) {
            // A new overriding variable value has been added to the variable, refresh it.
            updateRunnable = createRefreshRunnable(ModelUtil.getLogicalContainer(newElement, context), context);
          }
        break;
        case Notification.REMOVE:
          // Precondition.
          if (!(oldElement instanceof ModelElement)) {
            break;
          }
          updateRunnable = removeElement(input, notifier, context, oldElement);
        break;
        case Notification.REMOVE_MANY:
          // TODO Performance might be improved by grouping refreshes and removals
          @SuppressWarnings("unchecked")
          List<Object> oldElementList = (List<Object>) oldElement;
          for (Object element : oldElementList) {
            if (element instanceof ModelElement) {
              Runnable runnable = removeElement(input, notifier, context, element);
              if (null != runnable) {
                getViewer().getControl().getDisplay().asyncExec(runnable);
              }
            }
          }
        break;
        case Notification.SET:
          // When a category is copied from an inherited category to another inherited category, no ADD/REMOVE notifications, only super category is changed.
          if (notifier instanceof ContributedElement && ContextsPackage.Literals.CONTRIBUTED_ELEMENT__SUPER_CATEGORY.equals(feature) && null != oldElement
              && null != newElement) {
            // Remove the category from its first parent.
            Runnable removeRunnable = createRemoveRunnable(notifier);
            // The new logical container is the new element (that is a foreign category).
            // The notifier, the moved category, is the element to add.
            Runnable addRunnable = createAddRunnable(newElement, notifier, context);
            updateRunnable = createCompositeRunnable(removeRunnable, addRunnable);
          } else {
            // Refresh impacted context.
            updateRunnable = createUpdateRunnable(notifier, context);
          }
        break;
        case ContextsResourceImpl.NOTIFICATION_TYPE_CONTEXT_SYNCHRONIZED:
          // Context has been synchronized, that is removed from the viewer.
          // Add it back.
          updateRunnable = createAddRunnable(input, context, null);
        break;
        default:
        break;
      }
      // Refresh the viewer, if needed.
      if (null != updateRunnable) {
        getViewer().getControl().getDisplay().asyncExec(updateRunnable);
      }
    }

    /**
     * Remove an element from the Context Viewer
     * @param input_p Viewer input
     * @param notifier_p Object affected by the removal
     * @param context_p Conext of the notifier
     * @param element_p Element to remove
     * @return Runnable object for asynchronous update
     */
    private Runnable removeElement(Object input_p, Object notifier_p, Context context_p, Object element_p) {
      Runnable updateRunnable;
      // Remove from tree.
      if (notifier_p instanceof Resource) {
        Resource resource = (Resource) notifier_p;
        // A context has been removed, remove its node from the tree.
        updateRunnable = createRemoveRunnable(element_p);
        // Make sure RootElement is updated too, because of the filtered tree behavior.
        if (input_p instanceof RootElement) {
          ((RootElement) input_p).removeProjectFor(resource.getURI());
        }
      } else if ((element_p instanceof OverridingVariable) || (element_p instanceof OverridingVariableValue)) {
        ModelElement variableToRefresh = null;
        if (element_p instanceof OverridingVariable) {
          // An OverriddenVariable has been removed -> call for a refresh of the variable.
          variableToRefresh = ((OverridingVariable) element_p).getOverriddenVariable();
        } else if (element_p instanceof OverridingVariableValue) {
          // An OverridingVariableValue has been removed -> call for a refresh of the variable.
          variableToRefresh = ModelUtil.getLogicalContainer(element_p, context_p);
        }
        Runnable refreshVariableRunnable = createRefreshRunnable(variableToRefresh, context_p);
        // An existing overriding variable has been removed.
        // Update its category to reflect new decoration (if any).
        Runnable updateCategoryRunnable = createUpdateRunnable(ModelUtil.getCategory(variableToRefresh), context_p);
        updateRunnable = createCompositeRunnable(refreshVariableRunnable, updateCategoryRunnable);
      } else {
        updateRunnable = createRemoveRunnable(element_p);
      }
      return updateRunnable;
    }
  }

  /**
   * A selection adapter that adapts selection from nodes to model elements.<br>
   * Auto-registers to current viewer.
   * @author t0076261
   */
  protected class SelectionProviderAdapter implements ISelectionProvider, ISelectionChangedListener, IDoubleClickListener {
    /**
     * Double click listeners.
     */
    protected Collection<IDoubleClickListener> _doubleClickListeners;
    /**
     * Selection listeners.
     */
    protected Collection<ISelectionChangedListener> _selectionListeners;

    /**
     * Constructor.
     */
    public SelectionProviderAdapter() {
      // Initialize listeners lists.
      _selectionListeners = new ArrayList<ISelectionChangedListener>(0);
      _doubleClickListeners = new ArrayList<IDoubleClickListener>(0);
      // Register as listener against the viewer.
      _viewer.addSelectionChangedListener(SelectionProviderAdapter.this);
      _viewer.addDoubleClickListener(this);
    }

    /**
     * Adapt selection from nodes to model elements, if needed.
     * @param selectionToAdapt_p
     * @return
     */
    public ISelection adaptSelection(ISelection selectionToAdapt_p) {
      // Precondition.
      if (!(selectionToAdapt_p instanceof IStructuredSelection)) {
        return selectionToAdapt_p;
      }
      // Selection to adapt.
      IStructuredSelection structuredSelection = (IStructuredSelection) selectionToAdapt_p;
      // Collection of adapted elements.
      List<Object> adaptedElements = new ArrayList<Object>(structuredSelection.size());
      for (Object selectedObject : structuredSelection.toArray()) {
        Object objectToAdapt = getModelElement(selectedObject);
        if (null == objectToAdapt) {
          objectToAdapt = selectedObject;
        }
        adaptedElements.add(objectToAdapt);
      }
      return new StructuredSelection(adaptedElements);
    }

    /**
     * Add double click listener.
     * @param listener_p
     */
    public void addDoubleClickListener(IDoubleClickListener listener_p) {
      _doubleClickListeners.add(listener_p);
    }

    /**
     * @see org.eclipse.jface.viewers.ISelectionProvider#addSelectionChangedListener(org.eclipse.jface.viewers.ISelectionChangedListener)
     */
    public void addSelectionChangedListener(ISelectionChangedListener listener_p) {
      _selectionListeners.add(listener_p);
    }

    /**
     * @see org.eclipse.jface.viewers.IDoubleClickListener#doubleClick(org.eclipse.jface.viewers.DoubleClickEvent)
     */
    public void doubleClick(DoubleClickEvent event_p) {
      // Adapt selection...
      ISelection selection = adaptSelection(event_p.getSelection());
      // ... then event.
      DoubleClickEvent event = new DoubleClickEvent(_viewer, selection);
      // Launch new event.
      for (IDoubleClickListener listener : _doubleClickListeners) {
        listener.doubleClick(event);
      }
    }

    /**
     * Get model element from selected (tree) one.
     * @param selectedElement_p
     * @return <code>null</code> if model element could not be found.
     */
    public EObject getModelElement(Object selectedElement_p) {
      if (selectedElement_p instanceof IAdaptable) {
        return (EObject) ((IAdaptable) selectedElement_p).getAdapter(EObject.class);
      }
      return null;
    }

    /**
     * @see org.eclipse.jface.viewers.ISelectionProvider#getSelection()
     */
    public ISelection getSelection() {
      return adaptSelection(_viewer.getSelection());
    }

    /**
     * Remove double click listener.
     * @param listener_p
     */
    public void removeDoubleClickListener(IDoubleClickListener listener_p) {
      _doubleClickListeners.remove(listener_p);
    }

    /**
     * @see org.eclipse.jface.viewers.ISelectionProvider#removeSelectionChangedListener(org.eclipse.jface.viewers.ISelectionChangedListener)
     */
    public void removeSelectionChangedListener(ISelectionChangedListener listener_p) {
      _selectionListeners.remove(listener_p);
    }

    /**
     * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
     */
    public void selectionChanged(SelectionChangedEvent event_p) {
      // Adapt selection...
      ISelection selection = adaptSelection(event_p.getSelection());

      // ... then event.
      SelectionChangedEvent event = new SelectionChangedEvent(_viewer, selection);
      // Launch new event.
      for (ISelectionChangedListener listener : _selectionListeners) {
        listener.selectionChanged(event);
      }
    }

    /**
     * @see org.eclipse.jface.viewers.ISelectionProvider#setSelection(org.eclipse.jface.viewers.ISelection)
     */
    public void setSelection(ISelection selection_p) {
      // Do nothing, this is not supported.
      if (!(selection_p instanceof StructuredSelectionWithContext)) {
        return;
      }
      StructuredSelectionWithContext selection = (StructuredSelectionWithContext) selection_p;
      // Get context node.
      if (null == getContentProvider()) {
        return;
      }
      RootElementNode root = getContentProvider().getRootElement();
      AbstractNode<?> parentNode = root.getChildNode(selection.getContext());
      // Can't handle selection.
      if (null == parentNode) {
        return;
      }
      // Get relevant element.
      Object selectedElement = selection.getFirstElement();
      Category category = ModelUtil.getCategory(selectedElement);
      if (null != category) {
        selectedElement = category;
      } // If category is null, then element is most likely a context. Leave it that way.
      AbstractNode<?> childNode = parentNode.getChildNode(selectedElement);
      if (null != childNode) {
        _viewer.setSelection(new StructuredSelection(childNode));
        _viewer.getTree().setFocus();
      }
    }
  }

}