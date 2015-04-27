/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.oe.ui.viewer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.part.PluginTransfer;
import org.eclipse.ui.progress.WorkbenchJob;

import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;
import com.thalesgroup.orchestra.framework.oe.OrchestraExplorerActivator;
import com.thalesgroup.orchestra.framework.oe.artefacts.IArtefact;
import com.thalesgroup.orchestra.framework.oe.artefacts.IArtefactProperties;
import com.thalesgroup.orchestra.framework.oe.artefacts.internal.Artefact;
import com.thalesgroup.orchestra.framework.oe.artefacts.internal.IArtefactBagListener;
import com.thalesgroup.orchestra.framework.oe.artefacts.internal.RootArtefactsBag;
import com.thalesgroup.orchestra.framework.oe.ui.IOrchestraExplorerStateListener;
import com.thalesgroup.orchestra.framework.oe.ui.OrchestraExplorerState;
import com.thalesgroup.orchestra.framework.oe.ui.OrchestraExplorerState.GroupType;
import com.thalesgroup.orchestra.framework.oe.ui.OrchestraExplorerState.SortType;
import com.thalesgroup.orchestra.framework.oe.ui.actions.CollapseAllAction;
import com.thalesgroup.orchestra.framework.oe.ui.actions.CopyURIAction;
import com.thalesgroup.orchestra.framework.oe.ui.actions.CreateArtefactAction;
import com.thalesgroup.orchestra.framework.oe.ui.actions.ExpandAllAction;
import com.thalesgroup.orchestra.framework.oe.ui.actions.ExpandArtefactAction;
import com.thalesgroup.orchestra.framework.oe.ui.actions.FoldingAction;
import com.thalesgroup.orchestra.framework.oe.ui.actions.GetRootArtefactsAction;
import com.thalesgroup.orchestra.framework.oe.ui.actions.GroupByAction;
import com.thalesgroup.orchestra.framework.oe.ui.actions.NavigateAction;
import com.thalesgroup.orchestra.framework.oe.ui.actions.SetCredentialAction;
import com.thalesgroup.orchestra.framework.oe.ui.actions.ShowArtifactPropertiesAction;
import com.thalesgroup.orchestra.framework.oe.ui.actions.SortAction;
import com.thalesgroup.orchestra.framework.oe.ui.dnd.ArtefactDragSourceAdapter;
import com.thalesgroup.orchestra.framework.oe.ui.dnd.ArtefactTransfer;
import com.thalesgroup.orchestra.framework.oe.ui.providers.AbstractTreeContentProvider;
import com.thalesgroup.orchestra.framework.oe.ui.providers.OverlayedImageDescriptor;
import com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.ArtefactNode;
import com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.DragableStringNode;
import com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.StringNode;
import com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.TypeStringNode;
import com.thalesgroup.orchestra.framework.oe.ui.views.Messages;

/**
 * @author s0040806
 */
public class OrchestraExplorerViewer implements IOrchestraExplorerStateListener, IArtefactBagListener {

  // ID
  public static final String ID = "com.thalesgroup.themis.papeetedoc.ui.views.explorer.artifacts.PapeeteArtifactNavigator";//$NON-NLS-1$

  protected OrchestraExplorerActivator _activator = OrchestraExplorerActivator.getDefault();
  // Gui declaration

  protected static final String UNSELECTABLE_DECORATOR = "decorators/not_selectable.gif"; //$NON-NLS-1$
  protected static final String UNSELECTABLE = "unselectable"; //$NON-NLS-1$

  protected SortAction _alphabeticalSorterAction;

  protected CollapseAllAction _collapseAllAction;
  // Contextual menu actions
  protected CreateArtefactAction _createArtefactAction;

  // Listener for Double Click options
  private IDoubleClickListener _doubleClickListener;
  protected ExpandAllAction _expandAllAction;
  protected ExpandArtefactAction _expandArtefactAction;
  protected SortAction _fileSorterAction;
  // Filtered Tree
  protected FilteredTree _filteredTree;
  // Menu actions
  protected FoldingAction _foldingAction;
  protected GetRootArtefactsAction _getRootArtefactsAction;
  protected GroupByAction _groupByLocationAction;
  // GroupBy actions
  protected GroupByAction _groupByNoneAction;
  protected GroupByAction _groupByTypeAction;
  /**
   * Is Orchestra Explorer view disposed ?
   */
  protected volatile boolean _isDisposed;
  // Layout actions
  protected Action _layoutHorizontalAction;

  protected Action _layoutVerticalAction;

  protected NavigateAction _navigateAction;
  protected SetCredentialAction _reInitializeCredentialAction;
  // MODE FOLDING
  protected SashForm _sash;
  protected ShowArtifactPropertiesAction _showArtifactPropertiesAction;
  protected CopyURIAction _copyURIAction;

  // TreeViewer objects
  // The main tree holding the artifacts
  protected TreeViewer _treeViewer;
  // The subtree for the folding view
  protected TreeViewer _treeViewerFolder;
  // Sorter actions
  protected SortAction _typeSorterAction;

  protected IMenuManager _menuManager;

  protected IToolBarManager _toolbarManager;

  protected List<String> _filteredTypes;
  protected List<OrchestraURI> _selectedURIs;
  protected boolean _isMultiSelectable;

  protected boolean _updateArtefactsOnCreate;

  protected OrchestraExplorerState _orchestraExplorerState;

  protected AbstractTreeContentProvider _treeContentProvider;

  /**
   * Constructor.
   */
  public OrchestraExplorerViewer(OrchestraExplorerState orchestraExplorerState_p, IMenuManager menuManager_p, IToolBarManager toolbarManager_p,
      List<String> filteredTypes_p, List<String> selectedURIs_p, boolean isMultiSelectable_p, boolean updateArtefactsOnCreate_p) {
    _menuManager = menuManager_p;
    _toolbarManager = toolbarManager_p;
    _filteredTypes = filteredTypes_p;
    buildURIs(selectedURIs_p);
    _isMultiSelectable = isMultiSelectable_p;
    _updateArtefactsOnCreate = updateArtefactsOnCreate_p;
    // Make sure view is not tagged has disposed.
    _isDisposed = false;

    _orchestraExplorerState = orchestraExplorerState_p;
  }

  protected void buildURIs(List<String> uris_p) {
    if (null != uris_p) {
      _selectedURIs = new ArrayList<OrchestraURI>();
      for (String uri : uris_p) {
        _selectedURIs.add(new OrchestraURI(uri));
      }
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.artefacts.internal.IArtefactBagListener#artefactAdded(com.thalesgroup.orchestra.framework.oe.artefacts.internal.Artefact)
   */
  @Override
  public void artefactAdded(final Artefact artefactAdded_p) {
    _treeViewer.getControl().getDisplay().asyncExec(new Runnable() {
      @Override
      public void run() {
        ISelection selection = _treeViewer.getSelection();
        _treeViewer.refresh();
        _treeViewer.setSelection(selection);
        if (_orchestraExplorerState.isFolded()) {
          selection = _treeViewerFolder.getSelection();
          _treeViewerFolder.refresh();
          _treeViewerFolder.setSelection(selection);
        }
      }
    });
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.artefacts.internal.IArtefactBagListener#artefactModified(com.thalesgroup.orchestra.framework.oe.artefacts.internal.Artefact)
   */
  @Override
  public void artefactModified(final Artefact modifiedArtefact_p) {
    _treeViewer.getControl().getDisplay().asyncExec(new Runnable() {
      @Override
      public void run() {
        ArtefactNode updatedArtefact = new ArtefactNode(modifiedArtefact_p, null, _orchestraExplorerState);
        _treeViewer.expandToLevel(updatedArtefact, 1);
        // Refresh the tree to take into account the fact that the children from the artefact may have changed prod00082041
        _treeViewer.refresh(updatedArtefact);
        if (null != _selectedURIs) {
          selectURIs();
        }
        // Sub-artefact adding on expand when folding is activated prod00082720
        if (_orchestraExplorerState.isFolded()) {
          _treeViewerFolder.expandToLevel(updatedArtefact, 1);
          _treeViewerFolder.refresh();
        }
      }
    });
  }

  /**
   * Create the different actions for right click menu and associate them with respective actions plus creation of Load Artifacts action
   */
  protected void createActions() {
    // Toolbar actions
    // Load artifacts list
    _getRootArtefactsAction = new GetRootArtefactsAction();
    // Expand
    _expandAllAction = new ExpandAllAction(_treeViewer);
    // Collapse
    _collapseAllAction = new CollapseAllAction(_treeViewer);
    // -- Folding Action
    _foldingAction = new FoldingAction(_orchestraExplorerState);

    // Contextual menu actions
    // Create artefact action
    _createArtefactAction = new CreateArtefactAction(_orchestraExplorerState);
    // Import Artifact Objects i.e. Expand
    _expandArtefactAction = new ExpandArtefactAction();
    // Navigate action
    _navigateAction = new NavigateAction();
    // Reinitialize credentials action
    _reInitializeCredentialAction = new SetCredentialAction(_orchestraExplorerState);
    // Properties
    _showArtifactPropertiesAction = new ShowArtifactPropertiesAction();
    // Copy URI
    _copyURIAction = new CopyURIAction();

    // Sort actions
    // -- Sort by file path
    _fileSorterAction =
        new SortAction(SortType.ENVIRONMENT, Messages.OrchestraExplorerView_FileSystem, Messages.OrchestraExplorerView_SortByFileSystem,
            _orchestraExplorerState);
    _alphabeticalSorterAction =
        new SortAction(SortType.NAME, Messages.OrchestraExplorerView_Name, Messages.OrchestraExplorerView_SortByName, _orchestraExplorerState);
    _typeSorterAction = new SortAction(SortType.TYPE, Messages.OrchestraExplorerView_Type, Messages.OrchestraExplorerView_SortByType, _orchestraExplorerState);

    // GroupBy actions
    _groupByLocationAction =
        new GroupByAction(GroupType.ENVIRONMENT, Messages.OrchestraExplorerView_FileSystem, Messages.OrchestraExplorerView_GroupByRootArtefactFilePath,
            _orchestraExplorerState);
    _groupByTypeAction =
        new GroupByAction(GroupType.TYPE, Messages.OrchestraExplorerView_Type, Messages.OrchestraExplorerView_GroupByRootArtefactType, _orchestraExplorerState);
    _groupByNoneAction =
        new GroupByAction(GroupType.NONE, Messages.OrchestraExplorerView_Flat, Messages.OrchestraExplorerView_DoNotGroup, _orchestraExplorerState);

    // Layout actions
    _layoutHorizontalAction = new Action() {
      @Override
      public void run() {
        doLayoutAction(_layoutHorizontalAction);
      }
    };
    _layoutHorizontalAction.setText(Messages.OrchestraExplorerView_HorizontalViewOrientation);
    _layoutHorizontalAction.setToolTipText(Messages.OrchestraExplorerView_HorizontalViewOrientation);
    _layoutHorizontalAction.setChecked(false);
    _layoutHorizontalAction.setEnabled(_orchestraExplorerState.isFolded());

    _layoutVerticalAction = new Action() {
      @Override
      public void run() {
        doLayoutAction(_layoutVerticalAction);
      }
    };
    _layoutVerticalAction.setText(Messages.OrchestraExplorerView_VerticalViewOrientation);
    _layoutVerticalAction.setToolTipText(Messages.OrchestraExplorerView_VerticalViewOrientation);
    _layoutVerticalAction.setChecked(true);
    _layoutVerticalAction.setEnabled(_orchestraExplorerState.isFolded());
  }

  /**
   * Create right-click menu associated to Orchestra Explorer
   */
  protected void createMenu() {
    MenuManager menuMgr = new MenuManager();
    menuMgr.setRemoveAllWhenShown(false);
    menuMgr.add(_createArtefactAction);
    menuMgr.add(new Separator());
    menuMgr.add(_navigateAction);
    _navigateAction.setEnabled(false);
    menuMgr.add(_expandArtefactAction);
    _expandArtefactAction.setEnabled(false);
    menuMgr.add(new Separator());
    menuMgr.add(_reInitializeCredentialAction);
    _reInitializeCredentialAction.setEnabled(false);
    menuMgr.add(new Separator());
    menuMgr.add(_showArtifactPropertiesAction);
    _showArtifactPropertiesAction.setEnabled(false);
    menuMgr.add(_copyURIAction);
    _copyURIAction.setEnabled(false);

    Control viewerControl = _treeViewer.getControl();
    Menu menu = menuMgr.createContextMenu(viewerControl);
    viewerControl.setMenu(menu);
    Control viewerFolderControl = _treeViewerFolder.getControl();
    menu = menuMgr.createContextMenu(viewerFolderControl);
    viewerFolderControl.setMenu(menu);

    _treeViewer.addSelectionChangedListener(_navigateAction);
    _treeViewer.addSelectionChangedListener(_expandArtefactAction);
    _treeViewer.addSelectionChangedListener(_showArtifactPropertiesAction);
    _treeViewer.addSelectionChangedListener(_copyURIAction);
    _treeViewer.addSelectionChangedListener(_createArtefactAction);
    _treeViewer.addSelectionChangedListener(_reInitializeCredentialAction);

    _treeViewerFolder.addSelectionChangedListener(_createArtefactAction);
    _treeViewerFolder.addSelectionChangedListener(_navigateAction);
    _treeViewerFolder.addSelectionChangedListener(_expandArtefactAction);
    _treeViewerFolder.addSelectionChangedListener(_showArtifactPropertiesAction);
    _treeViewerFolder.addSelectionChangedListener(_copyURIAction);
    _treeViewerFolder.addSelectionChangedListener(_reInitializeCredentialAction);

    // Associate navigation menu action with double click
    _doubleClickListener = new IDoubleClickListener() {
      @Override
      public void doubleClick(DoubleClickEvent event_p) {
        if (_navigateAction.isEnabled()) {
          _navigateAction.run();
        }
      }
    };

    _treeViewer.addDoubleClickListener(_doubleClickListener);
    _treeViewerFolder.addDoubleClickListener(_doubleClickListener);
  }

  public void createControl(Composite parent) {
    _sash = new SashForm(parent, SWT.VERTICAL);

    GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
    _sash.setLayoutData(gridData);

    createTreeViewer(_sash);
    createTreeViewerFolder(_sash);

    if (_orchestraExplorerState.isFolded()) {
      _sash.setWeights(new int[] { 65, 35 });
    } else {
      _sash.setWeights(new int[] { 100, 0 });
    }

    // Create the actions for the tree
    createActions();
    // Create the menu of sorters for the tree
    createSorterMenus();
    // Create the Load Artifacts action and add to the tools menu for the tree
    createToolbar();
    // Create the right click popup menu of Actions
    createMenu();

    _orchestraExplorerState.registerStateListener(this);
    RootArtefactsBag.getInstance().addArtefactBagListener(this);

    if (null != _selectedURIs) {
      OrchestraURI uri = _selectedURIs.get(0);
      // If at least one URI is an artefact set, switch to folded mode
      if ("ArtefactSet".equals(uri.getRootType())) {
        _foldingAction.run();
      }
    }

    if (_updateArtefactsOnCreate) {
      // Update root artefact bag only if empty
      if (0 == RootArtefactsBag.getInstance().getBagSize()) {
        RootArtefactsBag.getInstance().updateRootArtefacts();
      } else {
        // If artefact bag is not empty, force bag modification events
        // like URIs auto-selection, anyway
        fullBagModified();
      }
    }
  }

  /**
   * Create sorter menu
   */
  protected void createSorterMenus() {
    _menuManager.setRemoveAllWhenShown(true);
    _menuManager.addMenuListener(new IMenuListener() {
      @Override
      public void menuAboutToShow(IMenuManager mgr) {
        fillMenu(mgr);
      }
    });
    fillMenu(_menuManager);
  }

  /**
   * Create Orchestra Explorer View tool bar
   */
  protected void createToolbar() {
    _toolbarManager.add(_getRootArtefactsAction);
    _toolbarManager.add(_foldingAction);
    _toolbarManager.add(_expandAllAction);
    _toolbarManager.add(_collapseAllAction);
  }

  /**
   * Create the tree viewer part
   * @param parent
   * @return
   */
  protected Composite createTreeViewer(Composite parent_p) {
    Composite parent = new PageBook(parent_p, SWT.BORDER);
    // Create the layout for the tree
    GridLayout layout = new GridLayout();
    layout.numColumns = 1;
    layout.verticalSpacing = 2;
    layout.marginWidth = 0;
    layout.marginHeight = 2;
    parent.setLayout(layout);

    GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
    parent.setLayoutData(gridData);

    // Info label
    Display _display = parent.getDisplay();
    Color _colorFore = _display.getSystemColor(SWT.COLOR_DARK_GREEN);
    Color _colorBack = _display.getSystemColor(SWT.COLOR_WHITE);
    // Create text box to be displayed at the bottom of the tree
    int style = SWT.H_SCROLL | SWT.V_SCROLL | (_isMultiSelectable ? SWT.MULTI : SWT.SINGLE);
    _filteredTree = new OrchestraExplorerFilteredTree(parent, style, new PatternFilter(), true);
    GridData layoutData = new GridData();
    layoutData = new GridData();
    layoutData.grabExcessHorizontalSpace = true;
    layoutData.horizontalAlignment = GridData.FILL;
    // Set filteredTree properties
    _filteredTree.setBackground(_colorBack);
    _filteredTree.setForeground(_colorFore);
    // Create the tree object
    _treeViewer = _filteredTree.getViewer();
    // DND Management
    // add drag support
    int ops = DND.DROP_COPY | DND.DROP_MOVE;
    Transfer[] transfers = new Transfer[] { ArtefactTransfer.getInstance() };
    _treeViewer.addDragSupport(ops, transfers, new ArtefactDragSourceAdapter(_treeViewer));
    // Content provider init
    _treeContentProvider = _orchestraExplorerState.getCurrentContentProvider();
    _treeViewer.setContentProvider(_treeContentProvider);
    // Default sorter
    _treeViewer.setSorter(_orchestraExplorerState.getCurrentTreeSorter());
    // label provider init
    _treeViewer.setLabelProvider(_orchestraExplorerState.getCurrentLabelProvider());
    _treeViewer.setUseHashlookup(true);
    // layout the text field below the tree viewer
    layoutData = new GridData();
    layoutData.grabExcessHorizontalSpace = true;
    layoutData.grabExcessVerticalSpace = true;
    layoutData.horizontalAlignment = GridData.FILL;
    layoutData.verticalAlignment = GridData.FILL;
    _treeViewer.getControl().setLayoutData(layoutData);

    // Listeners management
    // Filter
    _treeViewer.addFilter(new ViewerFilter() {
      @Override
      public boolean select(Viewer viewer, Object parentElement, Object element) {
        boolean select = true;
        // Element is not null and instance of ArtifactsTree class
        if (element != null) {
          if (null != _filteredTypes && element instanceof TypeStringNode) {
            TypeStringNode type = (TypeStringNode) element;
            select = _filteredTypes.contains(type.getLabel());
          } else if (element instanceof ArtefactNode) {
            ArtefactNode artefactNode = (ArtefactNode) element;
            Artefact artefact = artefactNode.getValue();
            // Filter base - Artefact is visible and displayable
            select = artefactNode.getValue().getGenericArtifact().isDisplayable() && !_orchestraExplorerState.isFolded();
            // Keep filtered types only
            if (select && null != _filteredTypes) {
              select = _filteredTypes.contains(artefact.getUri().getRootType());
            }
          } else if (element instanceof RootArtefactsBag) {
            select = false;
          }
        }
        return select;
      }
    });

    _treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
      @Override
      public void selectionChanged(SelectionChangedEvent event_p) {
        if (_orchestraExplorerState.isFolded()) {
          _treeViewerFolder.setInput(((IStructuredSelection) event_p.getSelection()).getFirstElement());
        }
      }
    });

    parent.addControlListener(new ControlListener() {
      @Override
      public void controlMoved(ControlEvent e) {
        if (!_orchestraExplorerState.isFolded()) {
          _sash.setWeights(new int[] { 100, 0 });
        }
      }

      @Override
      public void controlResized(ControlEvent e) {
        if (!_orchestraExplorerState.isFolded()) {
          _sash.setWeights(new int[] { 100, 0 });
        }
      }
    });

    ColumnViewerToolTipSupport.enableFor(_treeViewer);
    _treeViewer.setInput(RootArtefactsBag.getInstance());

    return parent;
  }

  /**
   * Create the Sub Part for the folding View
   * @param parent
   */
  protected Composite createTreeViewerFolder(Composite parent_p) {
    Composite parent = new PageBook(parent_p, SWT.BORDER);

    GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
    parent.setLayoutData(gridData);

    FillLayout fillLayout = new FillLayout(SWT.HORIZONTAL | SWT.VERTICAL);
    parent.setLayout(fillLayout);

    int style = SWT.H_SCROLL | SWT.V_SCROLL | (_isMultiSelectable ? SWT.MULTI : SWT.SINGLE);
    _treeViewerFolder = new TreeViewer(parent, style);
    int ops = DND.DROP_COPY | DND.DROP_MOVE;
    Transfer[] transfers = new Transfer[] { ArtefactTransfer.getInstance(), PluginTransfer.getInstance() };
    _treeViewerFolder.addDragSupport(ops, transfers, new ArtefactDragSourceAdapter(_treeViewerFolder));
    // Content provider init
    _treeViewerFolder.setContentProvider(_orchestraExplorerState.getCurrentContentProvider());
    _treeViewerFolder.setLabelProvider(_orchestraExplorerState.getCurrentLabelProvider());
    _treeViewerFolder.setUseHashlookup(true);
    ColumnViewerToolTipSupport.enableFor(_treeViewerFolder);
    _treeViewerFolder.setSorter(_orchestraExplorerState.getDefaultTreeSorter());
    return parent;
  }

  public void dispose() {
    _treeViewer.removeDoubleClickListener(_doubleClickListener);
    _treeViewerFolder.removeDoubleClickListener(_doubleClickListener);
    _orchestraExplorerState.removeStateListener(this);
    RootArtefactsBag.getInstance().removeArtefactBagListener(this);
  }

  /**
   * Change the orientation of the Tree for the folding view
   * @param layoutVerticalAction
   */
  protected void doLayoutAction(Action layoutVerticalAction) {
    if (layoutVerticalAction == _layoutVerticalAction) {
      _sash.setOrientation(SWT.VERTICAL);
      _layoutHorizontalAction.setChecked(false);
    }
    if (layoutVerticalAction == _layoutHorizontalAction) {
      _sash.setOrientation(SWT.HORIZONTAL);
      _layoutVerticalAction.setChecked(false);
    }
  }

  /**
   * Fill the sorter menu with various options
   * @param rootMenuManager Create the menu
   */
  protected void fillMenu(IMenuManager rootMenuManager) {
    IMenuManager sortSubmenu = new MenuManager(Messages.OrchestraExplorerView_SortLabel);
    rootMenuManager.add(sortSubmenu);
    sortSubmenu.add(_fileSorterAction);
    sortSubmenu.add(_alphabeticalSorterAction);
    sortSubmenu.add(_typeSorterAction);

    IMenuManager groupBySubmenu = new MenuManager(Messages.OrchestraExplorerView_GroupByLabel);
    rootMenuManager.add(groupBySubmenu);
    groupBySubmenu.add(_groupByLocationAction);
    groupBySubmenu.add(_groupByTypeAction);
    groupBySubmenu.add(_groupByNoneAction);

    IMenuManager layoutSubmenu = new MenuManager(Messages.OrchestraExplorerView_LayoutLabel);
    rootMenuManager.add(layoutSubmenu);
    layoutSubmenu.add(_layoutVerticalAction);
    layoutSubmenu.add(_layoutHorizontalAction);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.ui.IOrchestraExplorerStateListener#foldingChanged()
   */
  @Override
  public void foldingChanged() {
    _treeViewer.getControl().getDisplay().asyncExec(new Runnable() {
      @Override
      public void run() {
        _treeViewer.getTree().setRedraw(false);
        _treeViewerFolder.getTree().setRedraw(false);
        try {
          if (_orchestraExplorerState.isFolded()) {
            _layoutHorizontalAction.setEnabled(true);
            _layoutVerticalAction.setEnabled(true);
            _sash.setWeights(new int[] { 65, 35 });
            IStructuredSelection selection = (IStructuredSelection) _treeViewer.getSelection();
            if ((selection != null) && (selection.size() > 0)) {
              Object selectedItem = selection.getFirstElement();
              _treeViewerFolder.setInput(selectedItem);
            } else {
              _treeViewerFolder.setInput(null);
            }
          } else {
            _layoutHorizontalAction.setEnabled(false);
            _layoutVerticalAction.setEnabled(false);
            _sash.setWeights(new int[] { 100, 0 });
            _treeViewerFolder.setInput(null);
          }
          _treeViewer.refresh();
        } finally {
          _treeViewer.getTree().setRedraw(true);
          _treeViewerFolder.getTree().setRedraw(true);
        }
      }
    });
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.artefacts.internal.IArtefactBagListener#fullBagModified()
   */
  @Override
  public void fullBagModified() {
    _treeViewer.getControl().getDisplay().asyncExec(new Runnable() {
      @Override
      public void run() {
        _treeViewer.setInput(RootArtefactsBag.getInstance());
        // Pre select artefacts
        if (null != _selectedURIs) {
          selectURIs();
        }
      }
    });
  }

  /**
   * Decorate a tree item with a refresh decorator
   * @param item
   */
  protected void decorateItem(TreeItem item) {
    Artefact artefact = ((ArtefactNode) item.getData()).getValue();
    String rootType = artefact.getUri().getRootType();
    String objectType = artefact.getUri().getObjectType();
    String key = rootType + objectType + UNSELECTABLE;

    // Synchronization is required because the image registry is shared
    // among multiple orchestra explorers
    synchronized (_activator) {
      ImageDescriptor descriptor = OrchestraExplorerActivator.getDefault().getImageDescriptor(key);
      if (null == descriptor) {
        Image image = OrchestraExplorerActivator.getDefault().getImage(rootType, artefact.getGenericArtifact().getIcon());
        Image decoratorImage = OrchestraExplorerActivator.getDefault().getImage(UNSELECTABLE_DECORATOR);
        ImageDescriptor imageDescriptor = new OverlayedImageDescriptor(image, decoratorImage, false, true);
        OrchestraExplorerActivator.getDefault().getImageRegistry().put(key, imageDescriptor);
      }
      Image decoratedImage = OrchestraExplorerActivator.getDefault().getImage(key);
      item.setImage(decoratedImage);
    }
  }

  /**
   * Select artefacts from selected URIs
   */
  protected void selectArtefacts() {
    RootArtefactsBag bag = RootArtefactsBag.getInstance();
    List<ArtefactNode> selectedNodes = new ArrayList<ArtefactNode>();
    List<ArtefactNode> decoratedNodes = new ArrayList<ArtefactNode>();

    // Force refreshing the tree viewer by expanding it
    // in order for the content provider to run for items
    _treeViewer.getControl().setRedraw(false);
    _treeViewer.refresh(true);
    _treeViewer.expandAll();
    _treeViewer.collapseAll();
    _treeViewer.getControl().setRedraw(true);

    // Find selected nodes and decorated nodes
    for (OrchestraURI uri : _selectedURIs) {
      IArtefact artefact = bag.getArtefactForURI(uri);
      if (null == artefact) {
        // Find root node to decorate
        OrchestraURI rootUri = new OrchestraURI(uri.getRootType(), uri.getRootName());
        artefact = bag.getRootArtefactForURI(rootUri);
        if (null != artefact) {
          ArtefactNode node = _treeContentProvider.getArtefactNodeFromArtefact(artefact);
          decoratedNodes.add(node);
        }
      } else {
        ArtefactNode node = _treeContentProvider.getArtefactNodeFromArtefact(artefact);
        selectedNodes.add(node);
      }
    }

    // Decorate nodes
    decorateArtefacts(decoratedNodes);

    // Select nodes
    if (!selectedNodes.isEmpty()) {
      for (ArtefactNode node : selectedNodes) {
        _treeViewer.expandToLevel(buildTreePath(node), 1);
      }
      _treeViewer.setSelection(new StructuredSelection(selectedNodes.toArray()), true);
    }

  }

  /**
   * Select artefact sect
   */
  protected void selectArtefactSets() {
    List<DragableStringNode> selectedNodes = new ArrayList<DragableStringNode>();
    // Force refreshing the tree viewer by expanding it
    // in order for the content provider to run for items
    _treeViewer.getControl().setRedraw(false);
    _treeViewer.refresh(true);
    _treeViewer.expandAll();
    _treeViewer.collapseAll();
    _treeViewer.getControl().setRedraw(true);

    switch (_orchestraExplorerState.getGroupType()) {
      case TYPE:
      case ENVIRONMENT:
        List<String> rootLabels = getRootLabels();
        for (OrchestraURI uri : _selectedURIs) {
          // Get artefact set logical path
          String logicalPath = uri.getParameters().get("LogicalFolderPath");
          for (String label : rootLabels) {
            DragableStringNode node = _treeContentProvider.getDragableStringNodesFromPath(label + "/" + logicalPath);
            if (null != node) {
              selectedNodes.add(node);
            }
          }
        }
      break;
      default:
        for (OrchestraURI uri : _selectedURIs) {
          // Get artefact set logical path
          String logicalPath = uri.getParameters().get("LogicalFolderPath");
          DragableStringNode node = _treeContentProvider.getDragableStringNodesFromPath(logicalPath);
          if (null != node) {
            selectedNodes.add(node);
          }
        }
    }

    // Select nodes
    if (!selectedNodes.isEmpty()) {
      List<TreePath> paths = new ArrayList<TreePath>();
      for (DragableStringNode node : selectedNodes) {
        TreePath path = buildTreePath(node);
        paths.add(path);
        _treeViewer.expandToLevel(path, 0);
      }
      _treeViewer.setSelection(new StructuredSelection(paths.toArray()), true);
    }
  }

  protected void selectURIs() {
    if (_orchestraExplorerState.isFolded()) {
      selectArtefactSets();
    } else {
      selectArtefacts();
    }
  }

  /**
   * Get root labels from tree viewer
   * @return
   */
  protected List<String> getRootLabels() {
    Tree tree = _treeViewer.getTree();
    List<String> labels = new ArrayList<String>();
    for (TreeItem item : tree.getItems()) {
      labels.add(item.getText());
    }
    return labels;
  }

  /**
   * Build tree path from tree node for selection
   * @param node_p
   * @return Tree path
   */
  protected TreePath buildTreePath(TreeNode node_p) {
    List<TreeNode> path = new ArrayList<TreeNode>();
    path.add(node_p);
    TreeNode parent = node_p.getParent();
    while (null != parent) {
      path.add(0, parent);
      parent = parent.getParent();
    }
    return new TreePath(path.toArray());
  }

  /**
   * Decorate artefact nodes
   * @param nodes_p
   */
  protected void decorateArtefacts(List<ArtefactNode> nodes_p) {
    switch (_orchestraExplorerState.getGroupType()) {
    // Group by type
      case TYPE:
        for (ArtefactNode node : nodes_p) {
          _treeViewer.expandToLevel(buildTreePath(node), 1);
          Artefact artefact = node.getValue();
          OrchestraURI uri = artefact.getUri();
          String type = uri.getRootType();
          String rootName = uri.getRootName();
          for (TreeItem item : _treeViewer.getTree().getItems()) {
            TypeStringNode rootNode = (TypeStringNode) item.getData();
            if (type.equals(rootNode.getLabel())) {
              for (TreeItem child : item.getItems()) {
                if (rootName.equals(child.getText())) {
                  decorateItem(child);
                  break;
                }
              }
              break;
            }
          }
        }
      break;
      // Group by environment
      case ENVIRONMENT:
        for (ArtefactNode node : nodes_p) {
          _treeViewer.expandToLevel(buildTreePath(node), 1);
          Artefact artefact = node.getValue();
          OrchestraURI uri = artefact.getUri();
          String rootName = uri.getRootName();
          String environment = artefact.getProperties().get(IArtefactProperties.ENVIRONMENT);
          for (TreeItem item : _treeViewer.getTree().getItems()) {
            StringNode rootNode = (StringNode) item.getData();
            if (environment.equals(rootNode.getLabel())) {
              for (TreeItem child : item.getItems()) {
                if (rootName.equals(child.getText())) {
                  decorateItem(child);
                  break;
                }
              }
              break;
            }
          }
        }

      break;
      // No grouping
      default:
        for (ArtefactNode node : nodes_p) {
          Artefact artefact = node.getValue();
          OrchestraURI uri = artefact.getUri();
          for (TreeItem item : _treeViewer.getTree().getItems()) {
            ArtefactNode rootNode = (ArtefactNode) item.getData();
            Artefact rootArtefact = rootNode.getValue();
            if (uri.equals(rootArtefact.getUri())) {
              decorateItem(item);
              break;
            }
          }
        }
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.ui.IOrchestraExplorerStateListener#groupByChanged()
   */
  @Override
  public void groupByChanged() {
    _treeViewer.getControl().getDisplay().asyncExec(new Runnable() {
      public void run() {
        _treeViewer.getTree().setRedraw(false);
        _treeViewerFolder.getTree().setRedraw(false);
        try {
          _treeContentProvider = _orchestraExplorerState.getCurrentContentProvider();
          _treeViewer.setContentProvider(_treeContentProvider);
          _treeViewerFolder.setContentProvider(_orchestraExplorerState.getCurrentContentProvider());
        } finally {
          _treeViewer.getTree().setRedraw(true);
          _treeViewerFolder.getTree().setRedraw(true);
        }
        // Pre select artefacts
        if (null != _selectedURIs) {
          selectURIs();
        }
      }
    });

  }

  /*
   * (non-Javadoc)
   * @see org.eclipse.ui.IViewPart#init(org.eclipse.ui.IViewSite, org.eclipse.ui.IMemento)
   */
  // @Override
  // public void init(IViewSite site_p, IMemento memento_p) throws PartInitException {
  // super.init(site_p, memento_p);
  // OrchestraExplorerState.getInstance().loadMemento(memento_p);
  // }

  /**
   * Is Orchestra Explorer view disposed ?
   * @return <code>true</code> if so, <code>false</code> otherwise.
   */
  public boolean isDisposed() {
    return _isDisposed;
  }

  /**
   * @see org.eclipse.ui.part.ViewPart#saveState(org.eclipse.ui.IMemento)
   */
  // @Override
  // public void saveState(IMemento memento_p) {
  // OrchestraExplorerState.getInstance().saveMemento(memento_p);
  // }

  public void setFocus() {
    _treeViewer.getControl().getDisplay().asyncExec(new Runnable() {
      public void run() {
        _treeViewer.getControl().setFocus();
      }
    });
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.ui.IOrchestraExplorerStateListener#sortChanged()
   */
  @Override
  public void sortChanged() {
    _treeViewer.getControl().getDisplay().asyncExec(new Runnable() {
      public void run() {
        _treeViewer.setSorter(_orchestraExplorerState.getCurrentTreeSorter());
        // Pre select artefacts
        if (null != _selectedURIs) {
          selectURIs();
        }
      }
    });

  }

  public ISelection getSelection() {
    return _treeViewer.getSelection();
  }

  public void addSelectionChangedListener(ISelectionChangedListener selectionChangeListener_p) {
    _treeViewer.addSelectionChangedListener(selectionChangeListener_p);
  }

  /**
   * Subscribe to get root artefacts action events
   * @param listener_p
   */
  public void subscribeGetRootArtefactsAction(IJobChangeListener listener_p) {
    _getRootArtefactsAction.addJobListener(listener_p);
  }

  /**
   * Unsubscribe from get root artefacts action events
   * @param listener_p
   */
  public void unsubscribeGetRootArtefactsAction(IJobChangeListener listener_p) {
    _getRootArtefactsAction.removeJobListener(listener_p);
  }

  /**
   * Subscribe to navigate artefacts action events
   * @param listener_p
   */
  public void subscribeNavigateArtefactAction(IJobChangeListener listener_p) {
    _navigateAction.addJobListener(listener_p);
  }

  /**
   * Unubscribe from navigate artefacts action events
   * @param listener_p
   */
  public void unsubscribeNavigateArtefactAction(IJobChangeListener listener_p) {
    _navigateAction.removeJobListener(listener_p);
  }

  /**
   * Subscribe to expand artefacts action events
   * @param listener_p
   */
  public void subscribeExpandArtefactAction(IJobChangeListener listener_p) {
    _expandArtefactAction.addJobListener(listener_p);
  }

  /**
   * Unsubscribe from expand artefacts action events
   * @param listener_p
   */
  public void unsubscribeExpandArtefactAction(IJobChangeListener listener_p) {
    _expandArtefactAction.removeJobListener(listener_p);
  }

  class OrchestraExplorerFilteredTree extends FilteredTree {

    /**
     * @param parent_p
     * @param treeStyle_p
     * @param filter_p
     * @param useNewLook_p
     */
    public OrchestraExplorerFilteredTree(Composite parent_p, int treeStyle_p, PatternFilter filter_p, boolean useNewLook_p) {
      super(parent_p, treeStyle_p, filter_p, useNewLook_p);
    }

    /**
     * @see org.eclipse.ui.dialogs.FilteredTree#doCreateRefreshJob()
     */
    @Override
    protected WorkbenchJob doCreateRefreshJob() {
      WorkbenchJob job = super.doCreateRefreshJob();
      job.setDisplay(getDisplay());
      return job;
    }
  }
}
