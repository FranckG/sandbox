package com.thalesgroup.orchestra.framework.oe.ui.wizard.creation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.ae.creation.ui.connector.ConnectorArtefactCreationHandlers;
import com.thalesgroup.orchestra.framework.ae.creation.ui.connector.IConnectorArtefactCreationHandler;
import com.thalesgroup.orchestra.framework.ae.creation.ui.environment.EnvironmentArtefactCreationHandlers;
import com.thalesgroup.orchestra.framework.ae.creation.ui.environment.EnvironmentInformation;
import com.thalesgroup.orchestra.framework.ae.creation.ui.environment.IEnvironmentArtefactCreationHandler;
import com.thalesgroup.orchestra.framework.oe.OrchestraExplorerActivator;
import com.thalesgroup.orchestra.framework.oe.connectors.ConnectorInformation;
import com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage;

public class CreateArtefactWizardPage extends AbstractFormsWizardPage {
  private String _currentContext;

  private static final int MAX_ENVIRONMENT_COMBO_SIZE = 40;

  protected Combo _environmentsCombo;

  protected Combo _artefactTypesCombo;

  // MAP tool <> Association
  HashMap<String, String> _associations = new HashMap<String, String>();
  HashMap<String, String> _extensions = new HashMap<String, String>();

  protected IWizardPage _nextPage;

  /**
   * Type of the artefact
   */
  String _selectedRootType;
  private ModifyListener _typeModifyListener;

  List<EnvironmentInformation> _environmentList;
  EnvironmentInformation _selectedEnvironment;
  private ModifyListener _environmentModifyListener;
  String _environmentType;
  String _environmentName;
  String _environmentId;
  protected Set<String> _supportedEnvironments;

  private Map<String, String> _parameters;

  ArtefactCreationWizard _wizard;

  String _defaultRootType;
  String _defaultEnvironment;

  /**
   * Constructor
   * @param type_p
   * @param rootPath_p
   * @param namePrefix_p
   */
  public CreateArtefactWizardPage(ArtefactCreationWizard wizard_p) {
    super(Messages.CreateArtifactWizardPage_page_name);
    setTitle(Messages.ArtifactCreationWizard_Title);
    setDescription(Messages.ArtifactCreationWizard_Description);
    _wizard = wizard_p;
    _currentContext = wizard_p.getCurrentContext();
    _parameters = wizard_p.getParameters();
    _defaultRootType = wizard_p.getDefaultRootType();
    _defaultEnvironment = wizard_p.getDefaultEnvironment();
    _environmentList = new ArrayList<EnvironmentInformation>();
  }

  /**
   * Check if the fields from the page are valid (neither null nor empty) <br>
   * An error message is displayed in case of field invalidity
   * @return true if type, name and rootName are valid
   */
  public boolean areFieldsValid() {
    // Check if environment has been selected
    if (null == _selectedEnvironment) {
      setErrorMessage(Messages.CreateArtefactWizardPage_SelectEnvironment);
      return false;
    }
    // Check if a type has been selected
    if (null == _selectedRootType) {
      setErrorMessage(Messages.CreateArtefactWizardPage_SelectType);
      return false;
    }
    super.setErrorMessage(null);
    return true;
  }

  /**
   * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Composite doCreateControl(Composite parent_p, FormToolkit toolkit_p) {
    Composite container = new Composite(parent_p, SWT.NULL);
    final GridLayout gridLayoutForEntries = new GridLayout();
    gridLayoutForEntries.numColumns = 2;
    container.setLayout(gridLayoutForEntries);
    container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

    getEnvironments();

    // Environment
    final Label labelEnvironment = new Label(container, SWT.NONE);
    labelEnvironment.setText(Messages.CreateArtifactWizardPage_Environment);

    _environmentsCombo = new Combo(container, SWT.DROP_DOWN | SWT.BORDER | SWT.READ_ONLY);
    for (EnvironmentInformation envInfo : _environmentList) {
      String name = envInfo.getName();
      _environmentsCombo.add(name);
    }
    _environmentsCombo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

    // If a default environment has been selected by the user, select it
    if (!_defaultEnvironment.isEmpty()) {
      selectEnvironment(_defaultEnvironment);
    }

    _environmentModifyListener = new ModifyListener() {
      public void modifyText(ModifyEvent e_p) {
        updateSelectedEnvironment(_environmentsCombo.getSelectionIndex());
        updateArtefactTypeCombo();
        updatePages();
        updateButtons();
      }
    };
    _environmentsCombo.addModifyListener(_environmentModifyListener);

    // Artefact type
    final Label labelArtefactType = new Label(container, SWT.NONE);
    labelArtefactType.setText(Messages.CreateArtifactWizardPage_Type);
    _artefactTypesCombo = new Combo(container, SWT.DROP_DOWN | SWT.BORDER | SWT.READ_ONLY);

    for (ConnectorInformation connectorInfo : OrchestraExplorerActivator.getDefault().getConnectorsInformationManager().getAll()) {
      if (connectorInfo.isCreateSupported() && connectorInfo.hasAssociationData()) {
        _artefactTypesCombo.add(connectorInfo.getType());
        _associations.put(connectorInfo.getType(), connectorInfo.getAssociationLogicalPart());
        _extensions.put(connectorInfo.getType(), connectorInfo.getAssociationPhysicalPart());
      }
    }
    // Sort the combo in alphabetical order
    String[] items = _artefactTypesCombo.getItems();
    Arrays.sort(items);
    _artefactTypesCombo.setItems(items);

    // If a default root type has been selected by the user, select it
    if (!_defaultRootType.isEmpty()) {
      selectRootType(_defaultRootType);
    }

    _typeModifyListener = new ModifyListener() {
      public void modifyText(ModifyEvent e_p) {
        _selectedRootType = _artefactTypesCombo.getText();
        updatePages();
        updateButtons();
      }
    };
    _artefactTypesCombo.addModifyListener(_typeModifyListener);

    // Initialise error messages
    areFieldsValid();

    // When both environments and root type have been pre-selected, update pages
    if (null != _selectedEnvironment && null != _selectedRootType) {
      updatePages();
    }

    return container;
  }

  /**
   * Select root type in the combo
   * @param rootType_p Root type
   */
  protected void selectRootType(String rootType_p) {
    for (int i = 0; i < _artefactTypesCombo.getItemCount(); i++) {
      if (_artefactTypesCombo.getItem(i).equals(rootType_p)) {
        _artefactTypesCombo.select(i);
        _selectedRootType = rootType_p;
        break;
      }
    }
  }

  /**
   * Select environment in the combo by name
   * @param environment_p Environment
   */
  protected void selectEnvironment(String environment_p) {
    for (int i = 0; i < _environmentsCombo.getItemCount(); i++) {
      if (_environmentsCombo.getItem(i).equals(environment_p)) {
        _environmentsCombo.select(i);
        updateSelectedEnvironment(i);
        break;
      }
    }
  }

  /**
   * Select environment in the combo by index
   * @param index_p Index within the combo
   */
  protected void updateSelectedEnvironment(int index_p) {
    _selectedEnvironment = _environmentList.get(index_p);
    _environmentType = _selectedEnvironment.getType();
    _environmentName = _selectedEnvironment.getName();
    _environmentId = _selectedEnvironment.getId();
    _environmentsCombo.setToolTipText(_selectedEnvironment.getName());
  }

  /**
   * Get all environments of the current context along with their attributes
   */
  private void getEnvironments() {
    List<EnvironmentInformation> environments = OrchestraExplorerActivator.getDefault().getEnvironments();
    for (EnvironmentInformation environment : environments) {
      if (isSupportedEnvironement(environment.getType())) {
        _environmentList.add(environment);
      }
    }
  }

  /**
   * Check whether an environment supports artefact creation
   * @param type_p environment type
   * @return
   */
  private boolean isSupportedEnvironement(String type_p) {
    if (null == _supportedEnvironments) {
      _supportedEnvironments = EnvironmentArtefactCreationHandlers.getInstance().getAvailableEnvironmentTypes();
    }
    for (String environment : _supportedEnvironments) {
      if (environment.equals(type_p)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Remove all listeners on dispose
   */
  @Override
  public void dispose() {
    super.dispose();
    _environmentsCombo.removeModifyListener(_environmentModifyListener);
    _artefactTypesCombo.removeModifyListener(_typeModifyListener);
  }

  /**
   * @return
   */
  public String getArtefactRootType() {
    return _selectedRootType;
  }

  public String getEnvironmentName() {
    return _environmentName;
  }

  public String getEnvironmentId() {
    return _environmentId;
  }

  /**
   * Update pages and handlers with respect to selected environment and selected root type
   */
  protected void updatePages() {
    IEnvironmentArtefactCreationHandler environmentHandler =
        EnvironmentArtefactCreationHandlers.getInstance().getEnvironmentArtefactCreationHandler(_environmentType);
    if (null == environmentHandler || null == _selectedRootType) {
      _nextPage = null;
      return;
    }

    // reset parameters
    _parameters.clear();

    environmentHandler.initialize(_selectedEnvironment, _extensions.get(_selectedRootType), _associations.get(_selectedRootType), _selectedRootType,
        _parameters);
    AbstractFormsWizardPage page = environmentHandler.getFirstPage();
    page.setWizard(getWizard());
    page.setToolkit(getToolkit());
    page.setPreviousPage(this);
    _nextPage = page;

    _wizard.setEnvironmentHandler(environmentHandler);

    IConnectorArtefactCreationHandler connectorHandler = ConnectorArtefactCreationHandlers.getInstance().getConnectorArtefactCreationHandler(_selectedRootType);
    if (null != connectorHandler) {
      connectorHandler.initialize(_parameters);
      page = connectorHandler.getFirstPage();
      // Set first connector page as last environment page
      environmentHandler.setLastPage(page);
      page.setPreviousPage(_nextPage);
    }
    _wizard.setConnectorHandler(connectorHandler);
  }

  /**
   * Build next pages list with respect to chosen environment and artefact type
   * @see org.eclipse.jface.wizard.WizardPage#getNextPage()
   */
  @Override
  public IWizardPage getNextPage() {
    return _nextPage;
  }

  /**
   * The "Finish" button can be accessed is this method returns true <br>
   * True is returned if the fields are valid and if there is either no next page or the current page of the wizard is not this one
   * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
   */
  @Override
  public boolean isPageComplete() {
    // Return true only if the fields from the first page are valid
    return areFieldsValid();
  }

  /**
   * Update root type combo box with respect to excluded root types
   */
  void updateArtefactTypeCombo() {
    Set<String> excludedSet = EnvironmentArtefactCreationHandlers.getInstance().getExcludedRootTypes(_environmentType);
    if (null != excludedSet) {
      // Reset combo
      _artefactTypesCombo.removeAll();
      for (ConnectorInformation connectorInfo : OrchestraExplorerActivator.getDefault().getConnectorsInformationManager().getAll()) {
        if (connectorInfo.isCreateSupported() && connectorInfo.hasAssociationData() && (null == excludedSet || !excludedSet.contains(connectorInfo.getType()))) {
          _artefactTypesCombo.add(connectorInfo.getType());
        }
      }
      // Sort the combo in alphabetical order
      String[] items = _artefactTypesCombo.getItems();
      Arrays.sort(items);
      _artefactTypesCombo.setItems(items);

      // Attempt to select previously selected root type
      selectRootType(_selectedRootType);
      // If not selected then it must be excluded. Reset it
      if (-1 == _artefactTypesCombo.getSelectionIndex()) {
        _selectedRootType = null;
      }
    }
  }

  /**
   * Will update the buttons states
   */
  protected void updateButtons() {
    getContainer().updateButtons();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage#getPageImageDescriptor()
   */
  protected ImageDescriptor getPageImageDescriptor() {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage#getPageTitle()
   */
  protected String getPageTitle() {
    // TODO Auto-generated method stub
    return null;
  }
}
