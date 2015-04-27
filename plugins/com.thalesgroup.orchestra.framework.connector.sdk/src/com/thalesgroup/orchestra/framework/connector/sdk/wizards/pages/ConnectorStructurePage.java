/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.connector.sdk.wizards.pages;

import java.text.MessageFormat;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.JavaConventions;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.connector.sdk.SdkWizardsActivator;
import com.thalesgroup.orchestra.framework.connector.sdk.operation.ConnectorProjectParameters;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper.LayoutType;
import com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage;

/**
 * @author t0076261
 */
public class ConnectorStructurePage extends AbstractFormsWizardPage {
  /**
   * Connector id field. Connector id is used to name the project.
   */
  protected Text _connectorIdText;
  /**
   * Connector name field.
   */
  protected Text _connectorNameText;
  /**
   * Project project parameters.
   */
  protected ConnectorProjectParameters _connectorProjectCreationParameters;

  protected Button _locationBrowseButton;
  /**
   * Use computed default location (workspace_path/project_name) ?
   */
  protected Button _locationDefaultButton;
  /**
   * Project location field, for user given location.
   */
  protected Text _locationText;
  protected Composite _locationUserChoiceComposite;
  /**
   * Out of process connector ?
   */
  protected Button _outOfProcessButton;
  /**
   * Page validator object.
   */
  protected Validator _pageValidator;
  /**
   * Create a product with the out of process connector ?
   */
  protected Button _productButton;
  /**
   * Technical attribute, avoid recursive listener calls.
   */
  protected boolean _selfModification;
  /**
   * Has connector id been customized by the user ?
   */
  protected boolean _userCustomConnectorId;
  /**
   * User given project location.
   */
  protected String _userProjectLocation = ICommonConstants.EMPTY_STRING;

  /**
   * Constructor.
   * @param pageId_p
   */
  public ConnectorStructurePage(ConnectorProjectParameters connectorProjectCreationParameters_p) {
    super("ConnectorStructure"); //$NON-NLS-1$
    _connectorProjectCreationParameters = connectorProjectCreationParameters_p;
  }

  /**
   * Add id structure.
   * @param parent_p
   * @param toolkit_p
   */
  protected void addIdStructure(Composite parent_p, FormToolkit toolkit_p) {
    // Add connector id field.
    Map<Class, Widget> idWidgets = FormHelper.createLabelAndText(toolkit_p, parent_p, Messages.ConnectorStructurePage_Label_ConnectorId, null, SWT.NONE);
    _connectorIdText = (Text) idWidgets.get(Text.class);
    _connectorIdText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
    _connectorIdText.addModifyListener(new ModifyListener() {
      /**
       * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
       */
      public void modifyText(ModifyEvent e_p) {
        if (_selfModification)
          return;
        _selfModification = true;

        if (_locationDefaultButton.getSelection()) {
          _locationText.setText(computeProjectLocation());
        }
        // Connector id is now customized, don't update it if connector name field is modified.
        _userCustomConnectorId |= true;

        _pageValidator.validatePage();
        _selfModification = false;
      }
    });
  }

  /**
   * Add location picking structure.
   * @param parent_p
   * @param toolkit_p
   * @return
   */
  protected void addLocationStructure(Composite parent_p, FormToolkit toolkit_p) {
    // Use default location button.
    _locationDefaultButton = toolkit_p.createButton(parent_p, Messages.ConnectorStructurePage_Button_UseDefaultLocation, SWT.CHECK);
    _locationDefaultButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
    _locationUserChoiceComposite = FormHelper.createCompositeWithLayoutType(toolkit_p, parent_p, LayoutType.GRID_LAYOUT, 3, false);
    ((GridLayout) _locationUserChoiceComposite.getLayout()).marginHeight = 0;
    ((GridLayout) _locationUserChoiceComposite.getLayout()).marginWidth = 0;
    _locationUserChoiceComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
    Map<Class, Widget> locationCouple =
        FormHelper.createLabelAndText(toolkit_p, _locationUserChoiceComposite, Messages.ConnectorStructurePage_Label_Location, computeProjectLocation(),
            SWT.NONE);
    _locationText = (Text) locationCouple.get(Text.class);
    _locationText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
    _locationBrowseButton = toolkit_p.createButton(_locationUserChoiceComposite, Messages.ConnectorStructurePage_Button_Browse, SWT.PUSH);

    _locationBrowseButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));

    // Add selection listener.
    _locationDefaultButton.addSelectionListener(new SelectionAdapter() {
      /**
       * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
       */
      @Override
      public void widgetSelected(SelectionEvent e_p) {
        boolean useDefaultLocation = _locationDefaultButton.getSelection();
        setChildrenEnabled(_locationUserChoiceComposite, !useDefaultLocation);
        _locationText.setText(useDefaultLocation ? computeProjectLocation() : _userProjectLocation);
      }
    });

    _locationText.addModifyListener(new ModifyListener() {
      /**
       * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
       */
      public void modifyText(ModifyEvent e_p) {
        if (_selfModification)
          return;
        if (!_locationDefaultButton.getSelection()) {
          _userProjectLocation = _locationText.getText();
        }
        _pageValidator.validatePage();
      }
    });

    _locationBrowseButton.addSelectionListener(new SelectionAdapter() {
      /**
       * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
       */
      @Override
      public void widgetSelected(SelectionEvent e_p) {
        String selectedDirectory = null;
        String dirNameFromField = _locationText.getText();

        DirectoryDialog dialog = new DirectoryDialog(_locationText.getShell(), SWT.SHEET);
        dialog.setMessage(Messages.ConnectorStructurePage_DirectoryDialog_Title);
        dialog.setFilterPath(dirNameFromField);
        selectedDirectory = dialog.open();

        if (null != selectedDirectory) {
          _locationText.setText(selectedDirectory);
          // Validation will be called from _locationText modify listener.
        }
      }
    });
  }

  /**
   * Add name structure.
   * @param parent_p
   * @param toolkit_p
   * @return
   */
  protected void addNameStructure(Composite parent_p, FormToolkit toolkit_p) {
    // Add connector name field.
    Map<Class, Widget> connectorNameWidgets =
        FormHelper.createLabelAndText(toolkit_p, parent_p, Messages.ConnectorStructurePage_Label_ConnectorName, null, SWT.NONE);
    _connectorNameText = (Text) connectorNameWidgets.get(Text.class);
    _connectorNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));

    _connectorNameText.addModifyListener(new ModifyListener() {
      /**
       * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
       */
      public void modifyText(ModifyEvent e_p) {
        if (_selfModification)
          return;
        _selfModification = true;

        if (!_userCustomConnectorId) {
          _connectorIdText.setText(computeConnectorId());
          if (_locationDefaultButton.getSelection()) {
            _locationText.setText(computeProjectLocation());
          }
        }
        _pageValidator.validatePage();
        _selfModification = false;
      }
    });

  }

  /**
   * Add process structure.
   * @param parent_p
   * @param toolkit_p
   */
  protected void addProcessStructure(Composite parent_p, FormToolkit toolkit_p) {
    Group runtimeGroup = new Group(parent_p, SWT.NONE);
    runtimeGroup.setText(Messages.ConnectorStructurePage_Group_RuntimeOptions);
    FormHelper.updateCompositeLayoutWithLayoutType(runtimeGroup, LayoutType.GRID_LAYOUT, 1, false);
    runtimeGroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

    _outOfProcessButton = toolkit_p.createButton(runtimeGroup, Messages.ConnectorStructurePage_Button_OutOfProcess, SWT.CHECK);
    _productButton = toolkit_p.createButton(runtimeGroup, Messages.ConnectorStructurePage_Button_Product, SWT.CHECK);
    GridData data = (GridData) FormHelper.updateControlLayoutDataWithLayoutTypeData(_productButton, LayoutType.GRID_LAYOUT);
    data.horizontalIndent = 20;
    // Add selection listener for out of process.
    _outOfProcessButton.addSelectionListener(new SelectionAdapter() {
      /**
       * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
       */
      @Override
      public void widgetSelected(SelectionEvent e_p) {
        _productButton.setEnabled(_outOfProcessButton.getSelection());
      }
    });
  }

  /**
   * Compute connector id based on current user choices.
   * @return
   */
  protected String computeConnectorId() {
    String connectorId = MessageFormat.format("com.thalesgroup.{0}.orchestra.framework.connector", _connectorNameText.getText()); //$NON-NLS-1$
    return connectorId.toLowerCase();
  }

  /**
   * Compute location based on current user choices.
   * @return
   */
  protected String computeProjectLocation() {
    return Platform.getLocation().append(_connectorIdText.getText()).toOSString();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.common.ui.wizards.AbstractFormsWizardPage#doCreateControl(org.eclipse.swt.widgets.Composite,
   *      org.eclipse.ui.forms.widgets.FormToolkit)
   */
  @Override
  protected Composite doCreateControl(Composite parent_p, FormToolkit toolkit_p) {
    // Create main composite.
    Composite mainComposite = FormHelper.createCompositeWithLayoutType(toolkit_p, parent_p, LayoutType.GRID_LAYOUT, 2, false);
    ((GridLayout) mainComposite.getLayout()).verticalSpacing = 10;
    // Create connector name providing.
    addNameStructure(mainComposite, toolkit_p);
    // Create id providing.
    addIdStructure(mainComposite, toolkit_p);
    // Create location picking.
    addLocationStructure(mainComposite, toolkit_p);
    // Create process group.
    addProcessStructure(mainComposite, toolkit_p);
    _pageValidator = new Validator();
    init();
    return mainComposite;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.common.ui.wizards.AbstractFormsWizardPage#getPageImageDescriptor()
   */
  @Override
  protected ImageDescriptor getPageImageDescriptor() {
    return SdkWizardsActivator.getDefault().getImageDescriptor("connector/new_connector_wiz_ban.png"); //$NON-NLS-1$
  }

  /**
   * @see com.thalesgroup.orchestra.framework.common.ui.wizards.AbstractFormsWizardPage#getPageTitle()
   */
  @Override
  protected String getPageTitle() {
    return Messages.ConnectorStructurePage_PageTitle;
  }

  protected void init() {
    _locationDefaultButton.setSelection(true);
    setChildrenEnabled(_locationUserChoiceComposite, false);
    _outOfProcessButton.setSelection(true);

    setPageComplete(false);
  }

  /**
   * Toggle user choice for project location.
   * @param locationUserChoiceComposite_p
   * @param enabled_p
   */
  protected void setChildrenEnabled(Composite parentComposite_p, boolean enabled_p) {
    // Precondition.
    if (null == parentComposite_p) {
      return;
    }
    // Enable/disable all children controls.
    for (Control control : parentComposite_p.getChildren()) {
      control.setEnabled(enabled_p);
    }
  }

  /**
   * @see org.eclipse.jface.dialogs.DialogPage#setVisible(boolean)
   */
  @Override
  public void setVisible(boolean visible) {
    super.setVisible(visible);
    if (visible) {
      // Give the focus to the first field.
      _connectorNameText.setFocus();
    }
  }

  public void updateData() {
    _connectorProjectCreationParameters.setConnectorName(_connectorNameText.getText());
    _connectorProjectCreationParameters.setConnectorId(_connectorIdText.getText());

    if (_locationDefaultButton.getSelection()) {
      _connectorProjectCreationParameters.setConnectorProjectLocationPath(Platform.getLocation().toOSString());
    } else {
      _connectorProjectCreationParameters.setConnectorProjectLocationPath(_locationText.getText());
    }

    _connectorProjectCreationParameters.setOutOfProcess(_outOfProcessButton.getSelection());
    _connectorProjectCreationParameters.setProduct(_productButton.getSelection());

  }

  protected final class Validator {
    /**
     * Applies the status to the status line of a dialog page.
     * @param page the dialog page
     * @param status the status to apply
     */
    protected void applyToStatusLine(IStatus status) {
      String message = status.getMessage();
      if (message != null && message.length() == 0) {
        message = null;
      }
      switch (status.getSeverity()) {
        case IStatus.OK:
          setMessage(message, IMessageProvider.NONE);
          setErrorMessage(null);
        break;
        case IStatus.WARNING:
          setMessage(message, IMessageProvider.WARNING);
          setErrorMessage(null);
        break;
        case IStatus.INFO:
          setMessage(message, IMessageProvider.INFORMATION);
          setErrorMessage(null);
        break;
        default:
          setMessage(null);
          setErrorMessage(message);
        break;
      }
    }

    /**
     * Helper method to create a status.
     * @param severity_p
     * @param message
     * @return
     */
    private IStatus createStatus(int severity_p, String message) {
      return new Status(severity_p, SdkWizardsActivator.getDefault().getPluginId(), message);
    }

    /**
     * @param firstStatus_p
     * @param secondStatus_p
     * @return
     */
    private IStatus getWorstStatus(IStatus firstStatus_p, IStatus secondStatus_p) {
      if (null == firstStatus_p) {
        return secondStatus_p;
      } else if (null == secondStatus_p) {
        return firstStatus_p;
      }
      if (firstStatus_p.getSeverity() >= secondStatus_p.getSeverity()) {
        return firstStatus_p;
      }
      return secondStatus_p;
    }

    /**
     * Validate Connector id field.
     * @return
     */
    protected IStatus validateConnectorID() {
      IStatus intermediateStatus = null;
      final String connectorId = _connectorIdText.getText();
      if (connectorId.isEmpty()) {
        return createStatus(IStatus.ERROR, Messages.ConnectorStructurePage_ErrorMessage_ConnectorIdEmpty);
      }

      // check whether the connector id is a valid project name.
      final IStatus nameStatus = ResourcesPlugin.getWorkspace().validateName(connectorId, IResource.PROJECT);
      if (!nameStatus.isOK()) {
        return createStatus(IStatus.ERROR,
            MessageFormat.format(Messages.ConnectorStructurePage_ErrorMessage_ConnectorIdNotValidProjectName, nameStatus.getMessage()));
      }
      // check whether the connector id is a valid package name.
      IStatus val = JavaConventions.validatePackageName(connectorId, null, null);
      if (val.getSeverity() == IStatus.ERROR) {
        return createStatus(IStatus.ERROR, MessageFormat.format(Messages.ConnectorStructurePage_ErrorMessage_ConnectorIdNotValidPackageName, val.getMessage()));
      } else if (val.getSeverity() == IStatus.WARNING) {
        intermediateStatus =
            createStatus(IStatus.WARNING,
                MessageFormat.format(Messages.ConnectorStructurePage_WarningMessage_ConnectorIdDiscouragedPackageName, val.getMessage()));
        // continue checking
      }

      // check whether project already exists
      final IProject handle = ResourcesPlugin.getWorkspace().getRoot().getProject(connectorId);
      if (handle.exists()) {
        return createStatus(IStatus.ERROR, Messages.ConnectorStructurePage_ErrorMessage_ConnectorIdExistingProject);
      }
      if (null != intermediateStatus) {
        return intermediateStatus;
      }
      return createStatus(IStatus.OK, Messages.ConnectorStructurePage_OkMessage_CreateANewConnectorProject);
    }

    /**
     * Validate connector name field.
     * @return
     */
    protected IStatus validateConnectorName() {
      final String connectorName = _connectorNameText.getText();
      // check whether the connector name is empty.
      if (connectorName.isEmpty()) {
        return createStatus(IStatus.ERROR, Messages.ConnectorStructurePage_ErrorMessage_ConnectorNameEmpty);
      }
      if (!connectorName.matches("\\w+")) { //$NON-NLS-1$
        return createStatus(IStatus.ERROR, Messages.ConnectorStructurePage_ErrorMessage_ConnectorNameNotValidCharacters);
      }
      IStatus typeNameValidationStatus = JavaConventions.validateJavaTypeName(connectorName, null, null);
      if (typeNameValidationStatus.getSeverity() == IStatus.ERROR) {
        return createStatus(IStatus.ERROR,
            MessageFormat.format(Messages.ConnectorStructurePage_ErrorMessage_ConnectorNameNotValidTypeName, typeNameValidationStatus.getMessage()));
      } else if (typeNameValidationStatus.getSeverity() == IStatus.WARNING) {
        return createStatus(IStatus.WARNING,
            MessageFormat.format(Messages.ConnectorStructurePage_WarningMessage_ConnectorNameDiscouragedTypeName, typeNameValidationStatus.getMessage()));
      }
      return createStatus(IStatus.OK, Messages.ConnectorStructurePage_OkMessage_CreateANewConnectorProject);
    }

    /**
     * Validate the whole page.
     */
    protected void validatePage() {
      IStatus validationResult = validateConnectorName();
      // Page is erroneous, no need to do more validation.
      if (IStatus.ERROR != validationResult.getSeverity()) {
        validationResult = getWorstStatus(validationResult, validateConnectorID());
      }
      // Page is erroneous, no need to do more validation.
      if (IStatus.ERROR != validationResult.getSeverity()) {
        validationResult = getWorstStatus(validationResult, validateProjectLocation());
      }
      applyToStatusLine(validationResult);
      setPageComplete(validationResult.getSeverity() < IStatus.ERROR);
    }

    /**
     * Validate project location.
     */
    protected IStatus validateProjectLocation() {
      final String projectLocation = _locationText.getText();
      // Check whether entered location is empty.
      if (projectLocation.trim().isEmpty()) {
        return createStatus(IStatus.ERROR, Messages.ConnectorStructurePage_ErrorMessage_ProjectLocationEmpty);
      }
      // Check whether entered location is a syntactically correct path.
      if (!Path.EMPTY.isValidPath(projectLocation)) {
        return createStatus(IStatus.ERROR, Messages.ConnectorStructurePage_ErrorMessage_ProjectLocationInvalid);
      }
      // Check whether entered location is a valid project location.
      if (!_locationDefaultButton.getSelection()) {
        final IProject handle = ResourcesPlugin.getWorkspace().getRoot().getProject(_connectorIdText.getText());
        IStatus locationStatus = ResourcesPlugin.getWorkspace().validateProjectLocation(handle, Path.fromOSString(projectLocation));
        if (!locationStatus.isOK()) {
          return createStatus(IStatus.ERROR, locationStatus.getMessage());
        }
      }
      return createStatus(IStatus.OK, Messages.ConnectorStructurePage_OkMessage_CreateANewConnectorProject);
    }
  }
}