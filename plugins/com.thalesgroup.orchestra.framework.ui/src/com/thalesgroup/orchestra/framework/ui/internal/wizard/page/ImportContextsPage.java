/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.wizard.page;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.common.CommonActivator;
import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.data.DataHandler;
import com.thalesgroup.orchestra.framework.project.CaseUnsensitiveResourceSetImpl;
import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.project.ProjectHandler;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper.LayoutType;

/**
 * @author t0076261
 */
public class ImportContextsPage extends AbstractContextsSelectionWithLocationPage {
  /**
   * FileFilter used to find contexts in the file system.
   */
  protected static final FileFilter DIRECTORY_FILTER = new FileFilter() {
    public boolean accept(File pathname_p) {
      return pathname_p.isDirectory();
    }
  };
  /**
   * Title of the part containing the contexts selection tree.
   */
  protected Label _contextSelectionPartTitle;
  /**
   * Should contexts be searched deeply in the file system.
   */
  protected Button _deepSearchCheckBox;
  /**
   * Should projects be imported as copy into the workspace ?
   */
  protected Button _importAsCopyCheckBox;
  /**
   * Currently managed contexts names.
   */
  protected List<String> _managedContextsNames;
  /**
   * Currently managed context projects workspace paths.
   */
  protected List<IPath> _managedProjectsWorkspacePaths;

  /**
   * List of RootContextsProject currently loaded in the Framework.
   */
  protected final Collection<RootContextsProject> _projectsInWorkspace = ProjectActivator.getInstance().getProjectHandler().getAllProjectsInWorkspace();

  /**
   * Refresh button.
   */
  protected Button _refreshButton;
  /**
   * Is there conflicts between some contexts ?
   */
  protected volatile boolean _treeContainsConflictingItems = false;

  /**
   * Managed elements initialization thread
   */
  protected Thread _initManagedElementsThread;

  /**
   * Constructor.
   */
  public ImportContextsPage() {
    this("ImportContexts"); //$NON-NLS-1$
  }

  /**
   * Constructor.
   * @param pageId_p
   */
  public ImportContextsPage(String pageId_p) {
    super(pageId_p);
    // Run initialization in background in order to be able to show
    // the wizard as soon as possible
    _initManagedElementsThread = new Thread() {
      /**
       * @see java.lang.Thread#run()
       */
      @Override
      public void run() {
        synchronized (_initManagedElementsThread) {
          initializeManagedElementsLists();
        }
      }
    };
    _initManagedElementsThread.start();
  }

  /**
   * Should given RootContextsProject be displayed in the contexts selection tree ?
   * @param rootContextsProject_p
   * @return
   */
  protected boolean accept(RootContextsProject rootContextsProject_p) {
    // Filter baseline contexts.
    if (rootContextsProject_p.isBaseline()) {
      return false;
    }
    // Filter for current administrator.
    if (!rootContextsProject_p.isAdministrator(ProjectActivator.getInstance().getCurrentUserId())) {
      return false;
    }
    // Filter already existing projects (in the workspace).
    if (_projectsInWorkspace.contains(rootContextsProject_p)) {
      return false;
    }
    return true;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractContextsSelectionPage#createContextsSelectionPart(org.eclipse.swt.widgets.Composite,
   *      org.eclipse.ui.forms.widgets.FormToolkit)
   */
  @Override
  protected Composite createContextsSelectionPart(Composite parent_p, FormToolkit toolkit_p) {
    Composite composite = FormHelper.createCompositeWithLayoutType(toolkit_p, parent_p, LayoutType.GRID_LAYOUT, 2, false);
    _contextSelectionPartTitle = new Label(composite, SWT.NONE);
    _contextSelectionPartTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
    _contextSelectionPartTitle.setText(Messages.ImportContextsPage_ContextSelectionPart_Title);
    super.createContextsSelectionPart(composite, toolkit_p);
    _refreshButton = FormHelper.createButton(toolkit_p, composite, Messages.ImportContextsPage_Button_Text_Refresh, SWT.PUSH);
    GridData data = setButtonLayoutData(_refreshButton);
    data.verticalAlignment = GridData.BEGINNING;
    _refreshButton.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e_p) {
        refreshViewer();
        setPageComplete(isPageComplete());
      }
    });
    return composite;
  }

  /**
   * Deep search option part.
   * @param parent_p
   * @param toolkit_p
   */
  protected void createDeepSearchCheckBoxPart(Composite parent_p, FormToolkit toolkit_p) {
    Composite composite = FormHelper.createCompositeWithLayoutType(toolkit_p, parent_p, LayoutType.GRID_LAYOUT, 1, false);
    composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    _deepSearchCheckBox = FormHelper.createButton(toolkit_p, composite, Messages.ImportContextsPage_ImportContextsPage_Button_ImportAsCopy_Text, SWT.CHECK);
    _deepSearchCheckBox.setToolTipText(Messages.ImportContextsPage_ImportContextsPage_Button_ImportAsCopy_Tooltip);
  }

  /**
   * Import as copy option part.
   * @param parent_p
   * @param toolkit_p
   */
  protected void createImportAsCopyPart(Composite parent_p, FormToolkit toolkit_p) {
    Composite composite = FormHelper.createCompositeWithLayoutType(toolkit_p, parent_p, LayoutType.GRID_LAYOUT, 1, false);
    composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    _importAsCopyCheckBox = FormHelper.createButton(toolkit_p, composite, Messages.ImportContextsPage_Button_ImportAsCopy_Text, SWT.CHECK);
    _importAsCopyCheckBox.setToolTipText(Messages.ImportContextsPage_Button_ImportAsCopy_Tooltip);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractContextsSelectionPage#doCreateControl(org.eclipse.swt.widgets.Composite,
   *      org.eclipse.ui.forms.widgets.FormToolkit)
   */
  @Override
  protected Composite doCreateControl(Composite parent_p, FormToolkit toolkit_p) {
    Composite parent = new Composite(parent_p, SWT.NONE);
    GridLayout layout = new GridLayout();
    layout.marginWidth = 0;
    parent.setLayout(layout);
    parent.setLayoutData(new GridData(GridData.FILL_BOTH));
    // Create external directory choice.
    createChooseLocationPart(parent, toolkit_p);
    // Create deep search check box.
    createDeepSearchCheckBoxPart(parent, toolkit_p);
    // Create parent part.
    super.doCreateControl(parent, toolkit_p);
    // Create copy choice.
    createImportAsCopyPart(parent, toolkit_p);
    return parent;
  }

  /**
   * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
   */
  @Override
  public boolean doIsPageComplete() {
    return validateLocation() && validateSelection();
  }

  /**
   * @param startLocation_p
   * @param maxDepth_p
   * @param progressMonitor_p
   * @param resourceSet_p
   */
  protected void fillContextsSelectionTree(String startLocation_p, int maxDepth_p, IProgressMonitor progressMonitor_p, ResourceSet resourceSet_p) {
    // Check cancellation state.
    if (progressMonitor_p.isCanceled()) {
      throw new OperationCanceledException();
    }
    // Is max depth reached ?
    if (maxDepth_p < 0) {
      return;
    }
    ProjectHandler projectHandler = ProjectActivator.getInstance().getProjectHandler();
    progressMonitor_p.subTask(startLocation_p);
    // Find context contained in the current location.
    final RootContextsProject currentProject = projectHandler.getContextFromLocation(startLocation_p, resourceSet_p);
    if (null != currentProject && accept(currentProject)) {
      final boolean isConflicting = isInConflict(currentProject);
      _treeContainsConflictingItems |= isConflicting;
      // Add new element in the tree using the display thread.
      PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
        public void run() {
          addNewElement(currentProject, isConflicting);
        }
      });
      // A context has been found in this location -> no need to check child directories.
      return;
    }
    // Search contexts in child directories.
    File[] listFiles = new File(startLocation_p).listFiles(DIRECTORY_FILTER);
    if (null == listFiles) {
      return;
    }
    for (File file : listFiles) {
      fillContextsSelectionTree(file.getAbsolutePath(), maxDepth_p - 1, progressMonitor_p, resourceSet_p);
    }
  }

  @Override
  protected String getInvalidLocationErrorMessage() {
    return Messages.ImportContextsPage_ErrorMessage_InvalidLocation;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractContextsSelectionWithLocationPage#getLocationDialogMessage()
   */
  @Override
  protected String getLocationDialogMessage() {
    return Messages.ImportContextsPage_Dialog_Message;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractContextsSelectionWithLocationPage#getLocationLabelText()
   */
  @Override
  protected String getLocationLabelText() {
    return Messages.ImportContextsPage_Label_Text_Location;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractContextsSelectionWithLocationPage#getPageCompleteMessage()
   */
  @Override
  protected Couple<String, Integer> getPageCompleteMessage() {
    if (_treeContainsConflictingItems) {
      // If some items in conflict, display a warning message.
      return new Couple<String, Integer>(Messages.ImportContextsPage_Contexts_In_Workspace, new Integer(WARNING));
    }
    return null;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.common.ui.wizards.AbstractFormsWizardPage#getPageTitle()
   */
  @Override
  protected String getPageTitle() {
    return Messages.ImportContextsPage_Page_Title;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractContextsSelectionWithLocationPage#getPromptForContextLocationMessage()
   */
  @Override
  protected String getPromptForContextLocationMessage() {
    return Messages.ImportContextsPage_Message_PromptForLocationPath;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractContextsSelectionWithLocationPage#getPromptForContextSelectionMessage()
   */
  @Override
  protected String getPromptForContextSelectionMessage() {
    return Messages.ImportContextsPage_Message_PromptForContextSelection;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractContextsSelectionWithLocationPage#handleLocation()
   */
  @Override
  protected void handleLocation() {
    super.handleLocation();
    refreshViewer();
  }

  /**
   * Initialize lists used to detect conflicts between contexts.
   */
  protected void initializeManagedElementsLists() {
    // Populate lists of projects paths (in workspace) and contexts names for loaded contexts.
    _managedProjectsWorkspacePaths = new ArrayList<IPath>();
    _managedContextsNames = new ArrayList<String>();
    for (RootContextsProject rootContextsProject : _projectsInWorkspace) {
      // Get context's project path.
      _managedProjectsWorkspacePaths.add(rootContextsProject._project.getFullPath());
      // Get current context name (to have the current name, load it from the context itself, NOT from its ContextReference).
      DataHandler dataHandler = ModelHandlerActivator.getDefault().getDataHandler();
      String currentAdminContextName = dataHandler.getContext(rootContextsProject.getAdministratedContext()).getName();
      _managedContextsNames.add(currentAdminContextName);
    }

  }

  /**
   * Conflicting contexts are contexts which have the same context name or the same project path (in the workspace) as an already loaded context.
   * @param rootContextProject_p
   * @return
   */
  protected boolean isInConflict(RootContextsProject rootContextProject_p) {
    // Is given context in conflict ?
    IPath projectWorkspacePath = rootContextProject_p._project.getFullPath();
    String contextName = rootContextProject_p.getAdministratedContext().getName();
    synchronized (_initManagedElementsThread) {
      return _managedProjectsWorkspacePaths.contains(projectWorkspacePath) || _managedContextsNames.contains(contextName);
    }
  }

  /**
   * Refresh the whole viewer.
   */
  @Override
  public void refreshViewer() {
    if (validateLocation()) {
      // Clear viewer content.
      _directoryNodesMap.clear();
      _viewer.refresh();
      _treeContainsConflictingItems = false;
      // Start with a new search.
      final String location = getLocation();
      final int maxSearchDepth = _deepSearchCheckBox.getSelection() ? Integer.MAX_VALUE : 1;
      try {
        getContainer().run(true, true, new IRunnableWithProgress() {
          public void run(IProgressMonitor monitor_p) throws InvocationTargetException, InterruptedException {
            monitor_p.beginTask(Messages.ImportContextsPage_ProgressMonitor_TaskName, IProgressMonitor.UNKNOWN);
            // Use a temporary model bag to load the tree.
            final ResourceSet resourceSet = new CaseUnsensitiveResourceSetImpl();
            fillContextsSelectionTree(location, maxSearchDepth, monitor_p, resourceSet);
          }
        });
      } catch (InvocationTargetException exception_p) {
        CommonActivator.getInstance().logMessage("An error occurred during contexts search.", IStatus.ERROR, exception_p); //$NON-NLS-1$
      } catch (InterruptedException exception_p) {
        // OK, contexts loading has probably been canceled by the user.
      }
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractContextsSelectionWithLocationPage#setEnabled(boolean)
   */
  @Override
  public void setEnabled(boolean enabled_p) {
    _contextSelectionPartTitle.setEnabled(enabled_p);
    super.setEnabled(enabled_p);
    _deepSearchCheckBox.setEnabled(enabled_p);
    _refreshButton.setEnabled(enabled_p);
  }

  /**
   * Should projects be imported as copies into the workspace ?
   * @return <code>true</code> if so, <code>false</code> if they should be imported as linked projects.
   */
  public boolean shouldImportAsCopy() {
    if (null == _importAsCopyCheckBox) {
      return false;
    }
    return _importAsCopyCheckBox.getSelection();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractContextsSelectionWithLocationPage#validateSelection()
   */
  @Override
  protected boolean validateSelection() {
    List<RootContextsProject> selectedProjects = getCheckedProjects();
    // If selection is empty, it is not valid.
    if (!super.validateSelection()) {
      if (_treeContainsConflictingItems) {
        // If some items in conflict, display a warning message.
        setMessage(Messages.ImportContextsPage_Contexts_In_Workspace, WARNING);
      }
      return false;
    }
    // If only 1 context selected, no possible conflict.
    if (1 == selectedProjects.size()) {
      return true;
    }
    // Look for possible conflicts (two contexts with the same name or with the same project name).
    final Set<IPath> projectPaths = new HashSet<IPath>();
    final Set<String> contextNames = new HashSet<String>();
    for (RootContextsProject rootContextsProject : selectedProjects) {
      projectPaths.add(rootContextsProject._project.getFullPath());
      contextNames.add(rootContextsProject.getAdministratedContext().getName());
    }
    if (projectPaths.size() != selectedProjects.size() || contextNames.size() != selectedProjects.size()) {
      setMessage(Messages.ImportContextsPage_Not_Valid_Selection, ERROR);
      return false;
    }
    return true;
  }
}