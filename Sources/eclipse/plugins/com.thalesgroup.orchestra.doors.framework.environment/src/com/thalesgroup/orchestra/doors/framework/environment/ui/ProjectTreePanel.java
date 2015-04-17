/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.doors.framework.environment.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeNodeContentProvider;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.actions.BaseSelectionListenerAction;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.doors.framework.environment.model.AbstractDoorsContainer;
import com.thalesgroup.orchestra.doors.framework.environment.model.AbstractDoorsElement;
import com.thalesgroup.orchestra.doors.framework.environment.model.BaselineSetDefinitionModel;
import com.thalesgroup.orchestra.doors.framework.environment.model.BaselineSetModel;
import com.thalesgroup.orchestra.doors.framework.environment.model.DoorsFolder;
import com.thalesgroup.orchestra.doors.framework.environment.model.DoorsProject;
import com.thalesgroup.orchestra.doors.framework.environment.model.TreeMenuAction;
import com.thalesgroup.orchestra.framework.environment.ui.IVariablesHandler;
import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper.LayoutType;

/**
 * Panel to manage the Doors elements
 * @author S0032874
 */

public class ProjectTreePanel {
  /**
   * {@link DoorsEnvironmentTreePage} to force the update of the data
   */
  protected final DoorsEnvironmentTreePage _doorsEnvironmentTreePage;

  /**
   * Map of values for the tree
   */
  protected final List<DoorsProject> _doorsProjects;

  /**
   * CheckBoxTree viewer
   */
  protected CheckboxTreeViewer _fullTreeViewer;

  protected final List<BaselineSetModel> _initialSelectedBaselineSets;

  protected final List<DoorsProject> _initialSelectedProjects;

  /**
   * Default constructor
   * @param parent_p The parent {@link Composite}
   * @param toolkit_p The {@link FormToolkit}
   * @param handler_p The {@link IVariablesHandler} as is may be null when accessed from a different thread
   * @param treePage_p The {@link DoorsEnvironmentTreePage} for update purpose
   * @param projectMap_p The map of projects
   */
  public ProjectTreePanel(Composite parent_p, FormToolkit toolkit_p, List<DoorsProject> projectList_p, List<DoorsProject> selectedDoorsProjects_p,
      List<BaselineSetModel> selectedBaselineSets_p, DoorsEnvironmentTreePage treePage_p) {
    // reset _doorsProjects?
    _doorsProjects = projectList_p;
    _initialSelectedProjects = selectedDoorsProjects_p;
    _initialSelectedBaselineSets = selectedBaselineSets_p;
    _doorsEnvironmentTreePage = treePage_p;
    doCreateControl(parent_p, toolkit_p);
  }

  protected void checkBaselineSet(BaselineSetModel baselineSetToCheck_p) {
    // Check Baselineset (not very useful when called from ICheckStateListener).
    _fullTreeViewer.setChecked(baselineSetToCheck_p, true);
    BaselineSetDefinitionModel baselineSetDefinition = baselineSetToCheck_p.getParent();
    // Uncheck siblings.
    for (Object baselineset : baselineSetDefinition.getChildren()) {
      if (baselineset != baselineSetToCheck_p) {
        _fullTreeViewer.setChecked(baselineset, false);
      }
    }
    // Check parents.
    AbstractDoorsElement parentDoorsElement = baselineSetToCheck_p.getParent();
    while ((null != parentDoorsElement) && !_fullTreeViewer.getChecked(parentDoorsElement)) {
      _fullTreeViewer.setChecked(parentDoorsElement, true);
      parentDoorsElement = parentDoorsElement.getParent();
    }
  }

  protected void checkProject(DoorsProject doorsProjectToCheck_p) {
    _fullTreeViewer.setChecked(doorsProjectToCheck_p, true);
  }

  /**
   * Create the popup menu which allows to call the create actions
   * @param tree The {@link Tree}
   */
  protected void createPopupMenu() {
    // No virtual node.
    // return;

    MenuManager menuMgr = new MenuManager();
    CreateProjectAction createProjectAction = new CreateProjectAction(this);
    CreateFolderAction createFolderAction = new CreateFolderAction(this);
    CreateBaselineSetDefinitionAction createBaselineSetDefinitionAction = new CreateBaselineSetDefinitionAction(this);
    CreateBaselineSetAction createBaselineSetAction = new CreateBaselineSetAction(this);
    DeleteVirtualNodeAction deleteVirtualNodeAction = new DeleteVirtualNodeAction(this);

    menuMgr.add(createProjectAction);
    menuMgr.add(createFolderAction);
    menuMgr.add(createBaselineSetDefinitionAction);
    menuMgr.add(createBaselineSetAction);
    menuMgr.add(deleteVirtualNodeAction);

    _fullTreeViewer.addSelectionChangedListener(createProjectAction);
    _fullTreeViewer.addSelectionChangedListener(createFolderAction);
    _fullTreeViewer.addSelectionChangedListener(createBaselineSetDefinitionAction);
    _fullTreeViewer.addSelectionChangedListener(createBaselineSetAction);
    _fullTreeViewer.addSelectionChangedListener(deleteVirtualNodeAction);

    Control viewerControl = _fullTreeViewer.getControl();
    Menu menu = menuMgr.createContextMenu(viewerControl);
    viewerControl.setMenu(menu);
  }

  /**
   * The {@link TreeNodeContentProvider}
   * @return The {@link TreeNodeContentProvider}
   */
  protected ITreeContentProvider createTreeContentProvider() {
    TreeNodeContentProvider provider = new TreeNodeContentProvider() {
      @Override
      public Object[] getChildren(final Object parentElement_p) {
        AbstractDoorsElement element = (AbstractDoorsElement) parentElement_p;
        return element.getChildren();
      }

      @Override
      public Object[] getElements(Object inputElement_p) {
        if (inputElement_p instanceof List) {
          return ((List) inputElement_p).toArray();
        }
        return null;
      }

      @Override
      public Object getParent(Object element_p) {
        if (element_p instanceof AbstractDoorsElement) {
          return ((AbstractDoorsElement) element_p).getParent();
        }
        return null;
      }

      @Override
      public boolean hasChildren(Object element_p) {
        AbstractDoorsElement element = (AbstractDoorsElement) element_p;
        return element.hasChildren();
      }
    };
    return provider;
  }

  /**
   * Create the controls
   * @param parent_p The parent {@link Composite}
   * @param toolkit_p The {@link FormToolkit}
   */
  private void doCreateControl(Composite parent_p, FormToolkit toolkit_p) {
    ScrolledComposite scrolledContainer = null;
    Composite viewerComposite = null;
    if (_doorsEnvironmentTreePage.getVariablesHandler().isVariableReadOnly()) {
      scrolledContainer = new ScrolledComposite(parent_p, SWT.V_SCROLL);
      viewerComposite = FormHelper.createCompositeWithLayoutType(toolkit_p, scrolledContainer, LayoutType.GRID_LAYOUT, 1, false);
      viewerComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
      scrolledContainer.setContent(viewerComposite);
      scrolledContainer.setLayout(new GridLayout(1, false));
      scrolledContainer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
      scrolledContainer.getVerticalBar().setVisible(false);
      _fullTreeViewer = new CheckboxTreeViewer(viewerComposite);
      scrolledContainer.setContent(viewerComposite);
    } else {
      viewerComposite = FormHelper.createCompositeWithLayoutType(toolkit_p, parent_p, LayoutType.GRID_LAYOUT, 1, false);
      viewerComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
      _fullTreeViewer = new CheckboxTreeViewer(viewerComposite);
      _fullTreeViewer.getTree().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
    }
    _fullTreeViewer.getTree().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
    // Add a listener
    _fullTreeViewer.addCheckStateListener(new ICheckStateListener() {
      public void checkStateChanged(CheckStateChangedEvent event_p) {
        Object changedElement = event_p.getElement();
        boolean checked = event_p.getChecked();
        if (checked && ((changedElement instanceof BaselineSetModel) || (changedElement instanceof DoorsProject))) {
          if (changedElement instanceof BaselineSetModel) {
            checkBaselineSet((BaselineSetModel) changedElement);
          }
        } else if (!checked) {
          uncheckDoorsElement((AbstractDoorsElement) changedElement);
        } else {
          _fullTreeViewer.setChecked(changedElement, !checked);
        }
        _doorsEnvironmentTreePage.forceUpdate();
      }
    });
    // Add a cell selection listener
    _fullTreeViewer.addDoubleClickListener(new TreeViewerDoubleClickListener());
    TreeViewerColumn viewer = new TreeViewerColumn(_fullTreeViewer, SWT.LEFT);
    viewer.getColumn().setWidth(600);

    // Set a label provider

    viewer.setLabelProvider(new CellLabelProvider() {

      /**
       * @see org.eclipse.jface.viewers.CellLabelProvider#getToolTipText(java.lang.Object)
       */
      @Override
      public String getToolTipText(Object element_p) {
        AbstractDoorsElement model = (AbstractDoorsElement) element_p;
        return model.getDescription();
      }

      /**
       * @see org.eclipse.jface.viewers.CellLabelProvider#getToolTipTimeDisplayed(java.lang.Object)
       */
      @Override
      public int getToolTipTimeDisplayed(Object object_p) {
        // Display toolTip for 10 seconds
        return 10000;
      }

      @Override
      public void update(ViewerCell cell_p) {
        AbstractDoorsElement element = (AbstractDoorsElement) cell_p.getElement();
        String doorsName = element.getDoorsName();
        // Convert the value in case it was an ODM variable*
        doorsName = OrchestraURI.decode(doorsName);
        doorsName = _doorsEnvironmentTreePage.getVariablesHandler().getSubstitutedValue(doorsName);
        cell_p.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
        // Use a different font color if the node is virtual
        if (element.isVirtual()) {
          cell_p.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE));
        }
        cell_p.setImage(element.getImage());
        cell_p.setText(doorsName);
      }
    });
    ColumnViewerToolTipSupport.enableFor(viewer.getViewer());
    _fullTreeViewer.setContentProvider(createTreeContentProvider());
    // Add an alphabetical sorter for the projects (and folders)
    _fullTreeViewer.setSorter(new ViewerSorter() {
      /**
       * @see org.eclipse.jface.viewers.ViewerComparator#compare(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
       */
      @Override
      public int compare(Viewer viewer_p, Object e1_p, Object e2_p) {
        // Only AbstractDoorsContainer (DoorsProject and DoorsProject) are sorted.
        if (!(e1_p instanceof AbstractDoorsContainer) && !(e2_p instanceof AbstractDoorsContainer)) {
          // Given elements are not AbstractDoorsContainer -> consider them as equals, so they are not sorted.
          return 0;
        } else if ((e1_p instanceof AbstractDoorsContainer) && !(e2_p instanceof AbstractDoorsContainer)) {
          // AbstractDoorsContainer are considered as greater than non AbstractDoorsContainer -> They are displayed after.
          return 1;
        } else if (!(e1_p instanceof AbstractDoorsContainer) && (e2_p instanceof AbstractDoorsContainer)) {
          // AbstractDoorsContainer are considered as greater than non AbstractDoorsContainer -> They are displayed after.
          return -1;
        }
        // 2 AbstractDoorsContainers -> sort them ignoring case using there substituted Doors name.
        IVariablesHandler variablesHandler = _doorsEnvironmentTreePage.getVariablesHandler();
        String e1SubstitutedName = variablesHandler.getSubstitutedValue(((AbstractDoorsContainer) e1_p).getDoorsName());
        String e2SubstitutedName = variablesHandler.getSubstitutedValue(((AbstractDoorsContainer) e2_p).getDoorsName());
        return e1SubstitutedName.compareToIgnoreCase(e2SubstitutedName);
      }
    });
    if (_doorsEnvironmentTreePage.getVariablesHandler().isVariableReadOnly()) {
      _fullTreeViewer.setInput(_initialSelectedProjects);
    } else
      _fullTreeViewer.setInput(_doorsProjects);
    // Initialize selection.
    for (DoorsProject doorsProjectToSelect : _initialSelectedProjects) {
      checkProject(doorsProjectToSelect);
    }
    for (BaselineSetModel baselineSetToSelect : _initialSelectedBaselineSets) {
      checkBaselineSet(baselineSetToSelect);
    }
    if (_doorsEnvironmentTreePage.getVariablesHandler().isVariableReadOnly()) {
      _fullTreeViewer.expandAll();
      _fullTreeViewer.getControl().setEnabled(false);
      scrolledContainer.getVerticalBar().setVisible(true);
      scrolledContainer.getVerticalBar().setEnabled(true);
    }

    // TODO fix this, does not set a fix size
    viewerComposite.setSize(600, 600);
    viewerComposite.pack();

    if (!_doorsEnvironmentTreePage.getVariablesHandler().isVariableReadOnly()) {
      createPopupMenu();
    }
  }

  public List<String> getDoorsElementsNames(List<? extends AbstractDoorsElement> doorsElements_p) {
    List<String> doorsElementsNames = new ArrayList<String>(doorsElements_p.size());
    for (AbstractDoorsElement doorsElement : doorsElements_p) {
      doorsElementsNames.add(doorsElement.getDoorsName());
    }
    return doorsElementsNames;
  }

  /**
   * @return the doorsEnvironmentTreePage
   */
  public DoorsEnvironmentTreePage getDoorsEnvironmentTreePage() {
    return _doorsEnvironmentTreePage;
  }

  /**
   * @return the values map
   */
  public List<DoorsProject> getDoorsProjects() {
    return _doorsProjects;
  }

  /**
   * @return the fullTreeViewer
   */
  public CheckboxTreeViewer getFullTreeViewer() {
    return _fullTreeViewer;
  }

  public List<BaselineSetModel> getSelectedBaselineSets() {
    Object[] checkedElements = _fullTreeViewer.getCheckedElements();
    List<BaselineSetModel> selectedBaselineSets = new ArrayList<BaselineSetModel>();
    for (Object checkedElement : checkedElements) {
      if (checkedElement instanceof BaselineSetModel) {
        selectedBaselineSets.add((BaselineSetModel) checkedElement);
      }
    }
    return selectedBaselineSets;
  }

  public List<DoorsProject> getSelectedDoorsProjects() {
    Object[] checkedElements = _fullTreeViewer.getCheckedElements();
    List<DoorsProject> selectedDoorsprojects = new ArrayList<DoorsProject>();
    for (Object checkedElement : checkedElements) {
      if (checkedElement instanceof DoorsProject) {
        selectedDoorsprojects.add((DoorsProject) checkedElement);
      }
    }
    return selectedDoorsprojects;
  }

  protected void uncheckDoorsElement(AbstractDoorsElement doorsElementToUncheck_p) {
    // Uncheck given element and all children.
    _fullTreeViewer.setSubtreeChecked(doorsElementToUncheck_p, false);
    // Uncheck parent if it contains no other checked children.
    AbstractDoorsElement parent = doorsElementToUncheck_p.getParent();
    while ((null != parent) && _fullTreeViewer.getChecked(parent)) {
      boolean hasACheckedChild = false;
      for (Object child : parent.getChildren()) {
        if (_fullTreeViewer.getChecked(child)) {
          hasACheckedChild = true;
          break;
        }
      }
      if (!hasACheckedChild) {
        _fullTreeViewer.setChecked(parent, false);
        parent = parent.getParent();
      } else {
        break;
      }

    }
  }

  /**
   * Internal class providing a Create BaselineSet action
   * @author S0032874
   */
  private class CreateBaselineSetAction extends TreeMenuAction {

    /**
     * Constructor
     * @param panel_p The {@link ProjectTreePanel}
     */
    public CreateBaselineSetAction(ProjectTreePanel panel_p) {
      super(Messages.DoorsEnvironment_Bs_Label, Messages.Default_ProjectTreePanel_Element_Name0, panel_p);
    }

    /**
     * @see com.thalesgroup.orchestra.doors.framework.environment.model.TreeMenuAction#canBeEnabled(com.thalesgroup.orchestra.doors.framework.environment.model.DefaultModel)
     */
    @Override
    protected boolean canBeEnabled(AbstractDoorsElement model_p) {
      // True if the model is virtual and is a BaselineSetDefinition
      if (model_p.isVirtual() && (model_p instanceof BaselineSetDefinitionModel)) {
        return true;
      }
      return false;
    }

    /**
     * @see com.thalesgroup.orchestra.doors.framework.environment.model.TreeMenuAction#getNewElementSiblings(com.thalesgroup.orchestra.doors.framework.environment.model.AbstractDoorsElement)
     */
    @Override
    protected List<? extends AbstractDoorsElement> getNewElementSiblings(AbstractDoorsElement parentElement_p) {
      return ((BaselineSetDefinitionModel) parentElement_p).getBaselineSets();
    }

    @Override
    public void run() {
      super.run();
      if ((null != _resultDoorsElementName) && !_resultDoorsElementName.isEmpty()) {
        BaselineSetDefinitionModel fatherModel = (BaselineSetDefinitionModel) _selectedDoorsElement;
        BaselineSetModel baselineSet = new BaselineSetModel(fatherModel, null, _resultDoorsElementName, true);
        fatherModel.addBaselineSet(baselineSet);
        super.updateTree();
        _projectTreePanel.getFullTreeViewer().expandToLevel(_selectedDoorsElement, AbstractTreeViewer.ALL_LEVELS);
        checkBaselineSet(baselineSet);

        _doorsEnvironmentTreePage.forceUpdate();
      }
    }
  }

  /**
   * Internal class providing a Create BaselineSet Definition action
   * @author S0032874
   */
  private class CreateBaselineSetDefinitionAction extends TreeMenuAction {

    /**
     * Constructor
     * @param panel_p The {@link ProjectTreePanel}
     */
    public CreateBaselineSetDefinitionAction(ProjectTreePanel panel_p) {
      super(Messages.DoorsEnvironment_Bsd_Label, Messages.Default_ProjectTreePanel_Element_Name1, panel_p);
    }

    /**
     * @see com.thalesgroup.orchestra.doors.framework.environment.model.TreeMenuAction#canBeEnabled(com.thalesgroup.orchestra.doors.framework.environment.model.DefaultModel)
     */
    @Override
    protected boolean canBeEnabled(AbstractDoorsElement model_p) {
      // True if the model is virtual and if it is either a folder or a project
      if (model_p.isVirtual() && (model_p instanceof AbstractDoorsContainer)) {
        return true;
      }
      return false;
    }

    /**
     * @see com.thalesgroup.orchestra.doors.framework.environment.model.TreeMenuAction#getNewElementSiblings(com.thalesgroup.orchestra.doors.framework.environment.model.AbstractDoorsElement)
     */
    @Override
    protected List<? extends AbstractDoorsElement> getNewElementSiblings(AbstractDoorsElement parentElement_p) {
      return ((AbstractDoorsContainer) parentElement_p).getBaselineSetDefinitions();
    }

    @Override
    public void run() {
      super.run();
      if ((null != _resultDoorsElementName) && !_resultDoorsElementName.isEmpty()) {
        AbstractDoorsContainer fatherModel = (AbstractDoorsContainer) _selectedDoorsElement;
        BaselineSetDefinitionModel baselineSetDef = new BaselineSetDefinitionModel(fatherModel, null, _resultDoorsElementName, true);
        fatherModel.addBaselineSetDefinition(baselineSetDef);
        super.updateTree();
        _projectTreePanel.getFullTreeViewer().expandToLevel(_selectedDoorsElement, AbstractTreeViewer.ALL_LEVELS);
        _doorsEnvironmentTreePage.forceUpdate();
      }
    }
  }

  /**
   * Internal class providing a Create folder action
   * @author S0032874
   */
  private class CreateFolderAction extends TreeMenuAction {

    /**
     * Constructor
     * @param panel_p The {@link ProjectTreePanel}
     */
    public CreateFolderAction(ProjectTreePanel panel_p) {
      super(Messages.DoorsEnvironment_Folder_Label, Messages.Default_ProjectTreePanel_Element_Name2, panel_p);
    }

    /**
     * @see com.thalesgroup.orchestra.doors.framework.environment.model.TreeMenuAction#canBeEnabled(com.thalesgroup.orchestra.doors.framework.environment.model.DefaultModel)
     */
    @Override
    protected boolean canBeEnabled(AbstractDoorsElement model_p) {
      // True if the model is virtual and is either a project or a folder
      if (model_p.isVirtual() && (model_p instanceof AbstractDoorsContainer)) {
        return true;
      }
      return false;
    }

    /**
     * @see com.thalesgroup.orchestra.doors.framework.environment.model.TreeMenuAction#getNewElementSiblings(com.thalesgroup.orchestra.doors.framework.environment.model.AbstractDoorsElement)
     */
    @Override
    protected List<? extends AbstractDoorsElement> getNewElementSiblings(AbstractDoorsElement parentElement_p) {
      return ((AbstractDoorsContainer) parentElement_p).getFoldersList();
    }

    @Override
    public void run() {
      super.run();

      if ((null != _resultDoorsElementName) && !_resultDoorsElementName.isEmpty()) {
        AbstractDoorsContainer fatherModel = (AbstractDoorsContainer) _selectedDoorsElement;
        DoorsFolder folder = new DoorsFolder(fatherModel, null, _resultDoorsElementName, true);
        fatherModel.addFolder(folder);
        super.updateTree();
        _projectTreePanel.getFullTreeViewer().expandToLevel(_selectedDoorsElement, AbstractTreeViewer.ALL_LEVELS);
        _doorsEnvironmentTreePage.forceUpdate();
      }
    }
  }

  /**
   * Internal class providing a Create project action
   * @author S0032874
   */
  private class CreateProjectAction extends TreeMenuAction {

    /**
     * Constructor
     * @param panel_p The {@link ProjectTreePanel}
     */
    public CreateProjectAction(ProjectTreePanel panel_p) {
      super(Messages.DoorsEnvironment_Project_Label, Messages.Default_ProjectTreePanel_Element_Name3, panel_p);
    }

    /**
     * @see com.thalesgroup.orchestra.doors.framework.environment.model.TreeMenuAction#getNewElementSiblings(com.thalesgroup.orchestra.doors.framework.environment.model.AbstractDoorsElement)
     */
    @Override
    protected List<? extends AbstractDoorsElement> getNewElementSiblings(AbstractDoorsElement parentElement_p) {
      return getDoorsProjects();
    }

    @Override
    public void run() {
      // Create default values
      String defaultName = generateDefaultName();
      String _resultDoorsProjectId = null;
      EditDoorsProjectWizard editDoorsElementWizard =
          new EditDoorsProjectWizard(getText(), defaultName, defaultName, _doorsEnvironmentTreePage.getVariablesHandler());
      WizardDialog editDoorsElementWizardDialog = new WizardDialog(_projectTreePanel.getFullTreeViewer().getControl().getShell(), editDoorsElementWizard);
      if (Window.OK == editDoorsElementWizardDialog.open()) {
        _resultDoorsElementName = editDoorsElementWizard.getRawDoorsElementName();
        _resultDoorsProjectId = editDoorsElementWizard.getRawDoorsProjectId();
      }

      if ((null != _resultDoorsElementName) && !_resultDoorsElementName.isEmpty() && (null != _resultDoorsProjectId) && !_resultDoorsProjectId.isEmpty()) {
        IVariablesHandler variablesHandler = _doorsEnvironmentTreePage.getVariablesHandler();
        DoorsProject projectModel =
            new DoorsProject(null, _resultDoorsElementName, true, _resultDoorsProjectId, variablesHandler.getSubstitutedValue(_resultDoorsElementName));
        _doorsProjects.add(projectModel);
        super.updateTree();
        checkProject(projectModel);
        _doorsEnvironmentTreePage.forceUpdate();
      }
    }

    /**
     * @see com.thalesgroup.orchestra.doors.framework.environment.model.TreeMenuAction#updateSelection(org.eclipse.jface.viewers.IStructuredSelection)
     */
    @Override
    protected boolean updateSelection(IStructuredSelection selection_p) {
      return true;
    }
  }

  /**
   * Internal class providing a Delete virtual node action
   * @author S0032874
   */
  protected class DeleteVirtualNodeAction extends BaseSelectionListenerAction {
    /**
     * Constructor
     * @param panel_p The {@link ProjectTreePanel}
     */
    public DeleteVirtualNodeAction(ProjectTreePanel panel_p) {
      super(Messages.DoorsEnvironment_Delete_Node_Label);
    }

    @Override
    public void run() {
      Object currentSelection = getStructuredSelection().getFirstElement();
      if (currentSelection instanceof DoorsProject) {
        _doorsProjects.remove(currentSelection);
      } else if (currentSelection instanceof DoorsFolder) {
        ((DoorsFolder) currentSelection).getParent().getFoldersList().remove(currentSelection);
      } else if (currentSelection instanceof BaselineSetDefinitionModel) {
        ((BaselineSetDefinitionModel) currentSelection).getParent().getBaselineSetDefinitions().remove(currentSelection);
      } else if (currentSelection instanceof BaselineSetModel) {
        ((BaselineSetModel) currentSelection).getParent().getBaselineSets().remove(currentSelection);
      }
      _fullTreeViewer.refresh();
      _doorsEnvironmentTreePage.forceUpdate();
    }

    /**
     * @see org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org.eclipse.jface.viewers.IStructuredSelection)
     */
    @Override
    protected boolean updateSelection(IStructuredSelection selection_p) {
      Object currentSelection = selection_p.getFirstElement();
      return ((currentSelection instanceof AbstractDoorsElement) && ((AbstractDoorsElement) currentSelection).isVirtual());
    }
  }

  /**
   * Internal class providing a {@link ISelectionChangedListener}
   * @author S0032874
   */
  protected class TreeViewerDoubleClickListener implements IDoubleClickListener {

    /**
     * Action to do when the selection changes in the tree: <br>
     * @see org.eclipse.jface.viewers.IDoubleClickListener#doubleClick(org.eclipse.jface.viewers.DoubleClickEvent)
     */
    public void doubleClick(DoubleClickEvent event_p) {
      ISelection selection = event_p.getSelection();
      if (selection instanceof StructuredSelection) {
        StructuredSelection structuredSelection = (StructuredSelection) selection;
        if (structuredSelection.getFirstElement() instanceof AbstractDoorsElement) {
          AbstractDoorsElement model = (AbstractDoorsElement) structuredSelection.getFirstElement();
          // Name can be changed only if node is virtual
          if (model.isVirtual()) {
            if (model instanceof DoorsProject) {
              EditDoorsProjectWizard editDoorsElementWizard =
                  new EditDoorsProjectWizard(Messages.DoorsEnvironment_Title_Edit_Doors_Element, model.getDoorsName(), model.getDoorsProjectId(),
                      _doorsEnvironmentTreePage.getVariablesHandler());
              WizardDialog editDoorsElementWizardDialog = new WizardDialog(_fullTreeViewer.getControl().getShell(), editDoorsElementWizard);
              if (Window.OK == editDoorsElementWizardDialog.open()) {
                ((DoorsProject) model).setDoorsProjectId(editDoorsElementWizard.getRawDoorsProjectId());
                model.setDoorsName(editDoorsElementWizard.getRawDoorsElementName());
              }
            } else {
              EditDoorsElementWizard editDoorsElementWizard =
                  new EditDoorsElementWizard(Messages.DoorsEnvironment_Title_Edit_Doors_Element, model.getDoorsName(),
                      _doorsEnvironmentTreePage.getVariablesHandler());
              WizardDialog editDoorsElementWizardDialog = new WizardDialog(_fullTreeViewer.getControl().getShell(), editDoorsElementWizard);
              if (Window.OK == editDoorsElementWizardDialog.open()) {
                model.setDoorsName(editDoorsElementWizard.getRawDoorsElementName());
              }
            }
            getFullTreeViewer().refresh();
            _doorsEnvironmentTreePage.forceUpdate();
          }
        }

      }

    }
  }

}
