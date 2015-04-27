package com.thalesgroup.orchestra.framework.ui.wizard;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.common.helper.ProjectHelper;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper.LayoutType;
import com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizard;
import com.thalesgroup.orchestra.framework.ui.activator.OrchestraFrameworkUiActivator;
import com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractNewContextPage;

public class NewContextWizard extends AbstractFormsWizard {
  /**
   * UI plug-in ID, useful to create statuses.
   */
  public static final String UI_PLUGIN_ID = OrchestraFrameworkUiActivator.getDefault().getPluginId();
  /**
   * The new context wizard name and parent selection page.
   */
  protected NewContextWizardParentContextPage _newContextParametersPage;
  /**
   * The new context wizard location page.
   */
  protected NewContextWizardLocationPage _newContextWizardLocationPage;

  /**
   * The project location.
   */
  protected String _newProjectLocation;

  /**
   * Whether the user want to use parent project.
   */
  protected boolean _useParentProject;

  /**
   * Whether the user want to specify a custom project location.
   */
  protected boolean _useProjectLocation;

  /**
   * Constructor.
   */
  public NewContextWizard() {
    setNeedsProgressMonitor(true);
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#addPages()
   */
  @Override
  public void addPages() {
    _newContextParametersPage = new NewContextWizardParentContextPage(true);
    _newContextParametersPage.setTitle(Messages.MainNewContextWizardPage_3);
    _newContextParametersPage.setDescription(Messages.MainNewContextWizardPage_4);
    addPage(_newContextParametersPage);
    _newContextWizardLocationPage = new NewContextWizardLocationPage();
    _newContextWizardLocationPage.setTitle(Messages.MainNewContextWizardPage_3);
    _newContextWizardLocationPage.setDescription(Messages.MainNewContextWizardPage_4);
    addPage(_newContextWizardLocationPage);
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#canFinish()
   */
  @Override
  public boolean canFinish() {
    // Check every pages are OK to finish.
    for (IWizardPage page : getPages()) {
      if (!page.isPageComplete()) {
        return false;
      }
    }
    return true;
  }

  /**
   * @return the newContextParametersPage
   */
  public NewContextWizardParentContextPage getNewContextParametersPage() {
    return _newContextParametersPage;
  }

  /**
   * @return the newContextWizardLocationPage
   */
  public NewContextWizardLocationPage getNewContextWizardLocationPage() {
    return _newContextWizardLocationPage;
  }

  /**
   * @return the newProjectLocation
   */
  public String getNewProjectLocation() {
    return _newProjectLocation;
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#getWindowTitle()
   */
  @Override
  public String getWindowTitle() {
    return Messages.NewContextWizard_1;
  }

  /**
   * @return the useProjectLocation
   */
  public boolean isUseProjectLocation() {
    return _useProjectLocation;
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#performFinish()
   */
  @Override
  public boolean performFinish() {
    String projectLocation = _useProjectLocation ? _newProjectLocation : null;
    RootContextsProject parentProject = _useParentProject ? _newContextParametersPage.getSelectedParent() : null;
    IStatus status =
        ModelHandlerActivator.getDefault().getDataHandler()
            .createNewContextStructure(_newContextParametersPage.getNewContextName(), projectLocation, parentProject);
    if (!status.isOK()) {
      // Display an error message to the user.
      if (getContainer().getCurrentPage() instanceof WizardPage) {
        WizardPage currentPage = (WizardPage) getContainer().getCurrentPage();
        currentPage.setMessage(status.getMessage(), IMessageProvider.ERROR);
      }
    }
    return status.isOK();
  }

  /**
   * Context location wizard page.
   */
  public class NewContextWizardLocationPage extends WizardPage {
    /**
     * Constructor.
     */
    protected NewContextWizardLocationPage() {
      super(NewContextWizardLocationPage.class.getName());
    }

    /**
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl(Composite parent_p) {
      initializeDialogUnits(parent_p);
      final Composite composite = new Composite(parent_p, SWT.NONE);
      composite.setLayout(new GridLayout());
      // Project location group.
      final Group contextLocationGroup = new Group(composite, SWT.NONE);
      contextLocationGroup.setText(Messages.NewContextWizardLocationPage_ContextLocationGroup_Title);
      contextLocationGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      contextLocationGroup.setLayout(new GridLayout());
      // Add context to a new default project in workspace.
      final Button defaultLocationButton = new Button(contextLocationGroup, SWT.RADIO);
      defaultLocationButton.setText(Messages.NewContextWizardLocationPage_CreateWorkspaceProject_Button_Text);
      // Add context to a new project created outside the workspace.
      final Button remoteLocationButton = new Button(contextLocationGroup, SWT.RADIO);
      remoteLocationButton.setText(Messages.NewContextWizardLocationPage_CreateOutsideProject_Button_Text);
      //
      // Create new context remote location group.
      //
      final Composite remoteLocationSelectionGroup = new Composite(contextLocationGroup, SWT.NONE);
      remoteLocationSelectionGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      remoteLocationSelectionGroup.setLayout(new GridLayout(3, false));
      final Label locationLabel = new Label(remoteLocationSelectionGroup, SWT.NONE);
      locationLabel.setText(Messages.NewContextWizardLocationPage_ContextLocation_Label);
      final Text locationPath = new Text(remoteLocationSelectionGroup, SWT.BORDER);
      locationPath.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      locationPath.addModifyListener(new ModifyListener() {
        public void modifyText(ModifyEvent e_p) {
          _newProjectLocation = locationPath.getText();
          setPageComplete(isPageComplete());
        }
      });
      final Button browseButton = new Button(remoteLocationSelectionGroup, SWT.PUSH);
      browseButton.setText(Messages.NewContextWizard_ProjectLocation_BrowseButton_Text);
      setButtonLayoutData(browseButton);
      browseButton.addSelectionListener(new SelectionAdapter() {
        /**
         * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
         */
        @Override
        public void widgetSelected(SelectionEvent e_p) {
          handleProjectLocation(locationPath);
          setPageComplete(isPageComplete());
        }
      });
      // "Default"/"Remote" switch handling.
      remoteLocationButton.addSelectionListener(new SelectionAdapter() {
        /**
         * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
         */
        @Override
        public void widgetSelected(SelectionEvent e_p) {
          boolean useRemoteLocation = remoteLocationButton.getSelection();
          _useProjectLocation = useRemoteLocation;
          remoteLocationSelectionGroup.setEnabled(useRemoteLocation);
          locationLabel.setEnabled(useRemoteLocation);
          locationPath.setEnabled(useRemoteLocation);
          browseButton.setEnabled(useRemoteLocation);
          setPageComplete(isPageComplete());
        }
      });
      // Set start state.
      defaultLocationButton.setSelection(true);
      remoteLocationSelectionGroup.setEnabled(false);
      locationLabel.setEnabled(false);
      locationPath.setEnabled(false);
      browseButton.setEnabled(false);
      // Set page control.
      setControl(composite);
    }

    /**
     * Handle project location choice.
     * @param textToUpdate_p
     * @return
     */
    protected void handleProjectLocation(Text textToUpdate_p) {
      DirectoryDialog dialog = new DirectoryDialog(getShell());
      dialog.setMessage(Messages.NewContextWizard_ChooseDestinationDirectory_Dialog_Title);
      dialog.setFilterPath(_newProjectLocation);
      String selectedLocation = dialog.open();
      if ((null != textToUpdate_p) && (null != selectedLocation)) {
        textToUpdate_p.setText(selectedLocation);
        _newProjectLocation = selectedLocation;
      }
    }

    /**
     * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
     */
    @Override
    public boolean isPageComplete() {
      boolean isPageComplete = true;
      if (_useProjectLocation) {
        // Check context location path.
        if ((null == _newProjectLocation) || _newProjectLocation.isEmpty()) {
          setMessage(Messages.NewContextWizardLocationPage_Message_PromptForLocationPath, NONE);
          isPageComplete = false;
        } else {
          IStatus validLocationStatus = isValidLocation(_newProjectLocation);
          if (!validLocationStatus.isOK()) {
            setMessage(validLocationStatus.getMessage(), ERROR);
            // setMessage(Messages.NewContextWizardLocationPage_ErrorMessage_InvalidLocationPath, ERROR);
            isPageComplete = false;
          }
        }
      }
      if (isPageComplete) {
        // Everything is correct, no message to display.
        setMessage(null);
      }
      return isPageComplete;
    }

    /**
     * Is user given location valid :
     * <ol>
     * <li>Not in the workspace,</li>
     * <li>An absolute path to an existing directory,</li>
     * <li>Given location doesn't already contain a directory with the same name as the context to create,</li>*
     * <li>Given location and its parents doesn't contain a context.</li>
     * </ol>
     * @param locationAbsolutePath_p
     * @return
     */
    protected IStatus isValidLocation(String locationAbsolutePath_p) {
      // Precondition.
      if (null == locationAbsolutePath_p) {
        return new Status(IStatus.ERROR, OrchestraFrameworkUiActivator.getDefault().getPluginId(), Messages.NewContextWizard_ErrorMessage_LocationCantBeNull);
      }
      File expectedBaseLocationDir = new File(locationAbsolutePath_p);
      // It's forbidden to give a location inside the workspace.
      String expectedBaseLocationDirCanonicalPath = null;
      try {
        expectedBaseLocationDirCanonicalPath = expectedBaseLocationDir.getCanonicalPath();
      } catch (IOException exception_p) {
        expectedBaseLocationDirCanonicalPath = expectedBaseLocationDir.getAbsolutePath();
      }
      if (ResourcesPlugin.getWorkspace().getRoot().getLocation().isPrefixOf(Path.fromOSString(expectedBaseLocationDirCanonicalPath))) {
        return new Status(IStatus.ERROR, UI_PLUGIN_ID, MessageFormat.format(Messages.NewContextWizard_ErrorMessage_CantCreateContextInWorkspace,
            Messages.NewContextWizardLocationPage_CreateWorkspaceProject_Button_Text));
      }
      // Location path must be an absolute path to an existing directory.
      if (!expectedBaseLocationDir.isDirectory() || !expectedBaseLocationDir.isAbsolute()) {
        return new Status(IStatus.ERROR, UI_PLUGIN_ID, Messages.NewContextWizard_ErrorMessage_LocationMustBeAnAbsoluteDirectoryPath);
      }
      // Location path mustn't already contain a folder with the name of the project.
      String contextDirectoryName = ProjectHelper.generateContextProjectName(_newContextParametersPage.getNewContextName());
      File expectedProjectLocationDir = new File(locationAbsolutePath_p, contextDirectoryName);
      if (expectedProjectLocationDir.exists()) {
        return new Status(IStatus.ERROR, UI_PLUGIN_ID, MessageFormat.format(
            Messages.NewContextWizard_ErrorMessage_DestinationAlreadyContainsADirectoryWithContextName, contextDirectoryName));
      }
      // Check given location and its parents don't already contain a context.
      File directoryToCheck = expectedBaseLocationDir;
      while ((null != directoryToCheck)
             && (null == ProjectActivator.getInstance().getProjectHandler().getContextFromLocation(directoryToCheck.getAbsolutePath(), null))) {
        directoryToCheck = directoryToCheck.getParentFile();
      }
      if (null != directoryToCheck) {
        return new Status(IStatus.ERROR, UI_PLUGIN_ID, Messages.NewContextWizard_ErrorMessage_ContextCantBeNested);
      }
      return Status.OK_STATUS;
    }
  }

  /**
   * First page.<br>
   * Handles context name and context parent.
   */
  public class NewContextWizardParentContextPage extends AbstractNewContextPage {
    /**
     * Use default context as parent.
     */
    protected Button _buttonUseDefaultContext;
    /**
     * Use another context as parent.
     */
    protected Button _buttonUseOtherContext;
    /**
     * This page can be used with its parent selection part disabled.
     */
    protected boolean _isParentSelectionEnabled;

    /**
     * Constructor.
     * @param isParentSelectionEnabled_p allows to enable/disable parent selection buttons.
     */
    protected NewContextWizardParentContextPage(boolean isParentSelectionEnabled_p) {
      super(NewContextWizardParentContextPage.class.getName());
      _isParentSelectionEnabled = isParentSelectionEnabled_p;
    }

    /**
     * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.ImportContextsPage#accept(com.thalesgroup.orchestra.framework.project.RootContextsProject)
     */
    @Override
    protected boolean accept(RootContextsProject rootContextsProject_p) {
      // Every contexts must be displayed, except baseline ones.
      return !rootContextsProject_p.isBaseline();
    }

    /**
     * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractNewContextPage#createParentSelectionButton(org.eclipse.swt.widgets.Composite,
     *      int)
     */
    @Override
    protected void createParentSelectionButton(Composite parent_p, int numColumns) {
      _buttonUseDefaultContext = new Button(parent_p, SWT.RADIO);
      _buttonUseDefaultContext.setText(Messages.NewContextWizard_UseDefaultParentContext);
      GridData data = new GridData(GridData.FILL_HORIZONTAL);
      data.horizontalSpan = numColumns;
      _buttonUseDefaultContext.setLayoutData(data);
      _buttonUseDefaultContext.setSelection(true);

      _buttonUseOtherContext = new Button(parent_p, SWT.RADIO);
      _buttonUseOtherContext.setText(Messages.NewContextWizard_UseAnotherParentContext);
      data = new GridData(GridData.FILL_HORIZONTAL);
      data.horizontalSpan = numColumns;
      _buttonUseOtherContext.setLayoutData(data);

      // Add button selection listener.
      SelectionListener parentContextSelection = new SelectionAdapter() {
        /**
         * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
         */
        @SuppressWarnings("synthetic-access")
        @Override
        public void widgetSelected(SelectionEvent e) {
          boolean useOtherContextAsParent = _buttonUseOtherContext.getSelection();
          _useParentProject = useOtherContextAsParent;
          setEnabled(useOtherContextAsParent);
          if (!useOtherContextAsParent) {
            _selectedParent = null;
          }
          setPageComplete(isPageComplete());
        }
      };
      _buttonUseOtherContext.addSelectionListener(parentContextSelection);
    }

    /**
     * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.ImportContextsPage#doCreateControl(org.eclipse.swt.widgets.Composite,
     *      org.eclipse.ui.forms.widgets.FormToolkit)
     */
    @Override
    protected Composite doCreateControl(Composite parent_p, FormToolkit toolkit_p) {
      Composite composite = FormHelper.createCompositeWithLayoutType(toolkit_p, parent_p, LayoutType.GRID_LAYOUT, 2, false);
      // Name selection.
      createNameSelection(composite, Messages.MainNewContextWizardPage_0);
      // Parent selection.
      Composite group = createParentSelection(composite);
      // Do get content from import.
      super.doCreateControl(group, toolkit_p);
      // Disable parent selection part.
      setEnabled(false);
      // Enable/Disable parent selection buttons ?
      _buttonUseDefaultContext.setEnabled(_isParentSelectionEnabled);
      _buttonUseOtherContext.setEnabled(_isParentSelectionEnabled);
      return composite;
    }

    /**
     * Inspired by {@link WizardNewProjectCreationPage#validatePage()}.
     */
    @Override
    public boolean doIsPageComplete() {
      // Check entered name validity.
      if (!isContextNameValid()) {
        return false;
      }
      // Is there a loaded project with the same name ?
      String newContextName = getNewContextName();
      String contextProjectName = ProjectHelper.generateContextProjectName(newContextName);
      if (ProjectHelper.isProjectExisting(contextProjectName)) {
        setMessage(MessageFormat.format(Messages.NewContextWizard_projectExistsMessage, newContextName), ERROR);
        return false;
      }
      // If new context doesn't use Default Context as parent, check parent selection is complete.
      if (_useParentProject) {
        return super.doIsPageComplete();
      }
      return true;
    }

    /**
     * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.ImportContextsPage#isInConflict(com.thalesgroup.orchestra.framework.project.RootContextsProject)
     */
    @Override
    protected boolean isInConflict(RootContextsProject rootContextProject_p) {
      // Nothing to disable when creating a new admin context.
      return false;
    }
  }
}