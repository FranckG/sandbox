/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.wizard.page;

import java.io.File;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.framework.contextsproject.ContextsProject;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.project.CaseUnsensitiveResourceSetImpl;
import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper.LayoutType;
import com.thalesgroup.orchestra.framework.ui.internal.wizard.page.administrators.AddAdministratorAction;
import com.thalesgroup.orchestra.framework.ui.internal.wizard.page.administrators.AdministratorsContentProvider;
import com.thalesgroup.orchestra.framework.ui.internal.wizard.page.administrators.AdministratorsLabelProvider;
import com.thalesgroup.orchestra.framework.ui.internal.wizard.page.administrators.AdministratorsTableSorter;
import com.thalesgroup.orchestra.framework.ui.internal.wizard.page.administrators.RemoveAdministratorAction;
import com.thalesgroup.orchestra.ldap.model.OrganisationUser;

/**
 * Edit context wizard page.
 * @author t0076261
 */
public class EditContextPage extends AbstractEditElementDescriptionPage {
  /**
   * UI model for the administrator list.
   */
  protected final List<Couple<String, OrganisationUser>> _administrators;
  /**
   * Edited context.
   */
  protected final Context _context;
  /**
   * ContextsProject of edited context.
   */
  protected final ContextsProject _contextsProject;
  /**
   * Is edited context parent the default context ?
   */
  protected final boolean _parentIsDefaultContext;
  /**
   * Parent project raw path text.
   */
  protected Text _parentProjectRawPathText;
  /**
   * Parent project substituted path text.
   */
  protected Text _parentProjectSubstitutedPathText;
  /**
   * The listener to let the remove button aware of the administrator selection.
   */
  private ISelectionChangedListener _removeListener;
  /**
   * The administrator table viewer.
   */
  private TableViewer _viewer;

  /**
   * Constructor.
   * @param context_p
   * @param administrators_p
   */
  public EditContextPage(Context context_p, ContextsProject contextsProject_p, List<Couple<String, OrganisationUser>> administrators_p) {
    super("EditContext"); //$NON-NLS-1$
    _context = context_p;
    _contextsProject = contextsProject_p;
    _administrators = administrators_p;
    _parentIsDefaultContext = (null == contextsProject_p.getParentProject());
  }

  /**
   * Add administrator list edition.
   * @param toolkit_p
   * @param parent_p
   */
  protected void addAdministratorsEdition(FormToolkit toolkit_p, final Composite parent_p) {
    // Add the label.
    Label label = toolkit_p.createLabel(parent_p, Messages.EditContextPage_Administrators_Label_Text, SWT.WRAP);
    label.setForeground(toolkit_p.getColors().getColor(IFormColors.TITLE));
    label.setLayoutData(new GridData(SWT.BEGINNING, SWT.FILL, false, false, 1, 2));
    // Add the table to present administrators.
    final Table table = createAdministratorTable(toolkit_p, parent_p);
    _viewer = new TableViewer(table);
    _viewer.setContentProvider(new AdministratorsContentProvider());
    _viewer.setSorter(new AdministratorsTableSorter());
    createColumns(_viewer);
    // Set initial input.
    _viewer.setInput(_administrators);
    // Add administrator button.
    Button addButton = FormHelper.createButton(toolkit_p, parent_p, Messages.EditContextPage_AdministratorsAdd_Button_Text, SWT.NONE);
    addButton.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, false, false));
    addButton.addSelectionListener(new AddAdministratorAction(parent_p, _viewer, _administrators));
    addButton.setEnabled(currentUserIsAdministrator());
    // Remove administrator button.
    final Button removeButton = FormHelper.createButton(toolkit_p, parent_p, Messages.EditContextPage_AdministratorsRemove_Button_Text, SWT.NONE);
    removeButton.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, false, false));
    removeButton.addSelectionListener(new RemoveAdministratorAction(_viewer, _administrators));
    removeButton.setEnabled(false);
    _removeListener = new ISelectionChangedListener() {
      @SuppressWarnings("unchecked")
      public void selectionChanged(SelectionChangedEvent event_p) {
        if (event_p.getSelection().isEmpty()) {
          removeButton.setEnabled(false);
        } else {
          String currentUserId = ProjectActivator.getInstance().getCurrentUserId();
          String selectedUserId = ((Couple<String, OrganisationUser>) ((StructuredSelection) event_p.getSelection()).getFirstElement()).getKey();
          if (currentUserId.equalsIgnoreCase(selectedUserId)) {
            removeButton.setEnabled(false);
          } else {
            removeButton.setEnabled(currentUserIsAdministrator());
          }
        }
      }
    };
    _viewer.addSelectionChangedListener(_removeListener);
  }

  /**
   * Add parent edition.
   * @param toolkit_p
   * @param parent_p
   */
  protected void addParentEdition(FormToolkit toolkit_p, Composite parent_p) {
    // Parent context name.
    String parentContextName = getNamedElement().getSuperContext().getName();
    Text parentContextNameText =
        (Text) FormHelper.createLabelAndText(toolkit_p, parent_p, Messages.EditContextPage_ParentContext_Label_Text, parentContextName, SWT.NONE).get(
            Text.class);
    parentContextNameText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, getContainerColumnsCount() - 1, 1));
    parentContextNameText.setEnabled(false);
  }

  /**
   * Add parent project path fields.
   * @param toolkit_p
   * @param parent_p
   */
  protected void addParentProjectPathPart(FormToolkit toolkit_p, Composite parent_p) {
    // Parent project raw path.
    Label parentProjectPathLabel = FormHelper.createLabel(toolkit_p, parent_p, Messages.EditContextPage_ParentPath_Label_Text);
    parentProjectPathLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 2));
    _parentProjectRawPathText = FormHelper.createText(toolkit_p, parent_p, null, SWT.NONE);
    _parentProjectRawPathText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    // Browse button
    Button parentProjectPathBrowseButton = FormHelper.createButton(toolkit_p, parent_p, Messages.EditContextPage_Button_Text_Browse, SWT.PUSH);
    setButtonLayoutData(parentProjectPathBrowseButton);
    ((GridData) parentProjectPathBrowseButton.getLayoutData()).verticalSpan = 2;
    parentProjectPathBrowseButton.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e_p) {
        DirectoryDialog directoryDialog = new DirectoryDialog(getShell());
        directoryDialog.setFilterPath(_parentProjectRawPathText.getText());
        String selectedFolder = directoryDialog.open();
        if (null != selectedFolder) {
          _parentProjectRawPathText.setText(selectedFolder);
        }
      }
    });
    // Parent project substituted path.
    _parentProjectSubstitutedPathText = new Text(parent_p, SWT.BORDER);
    _parentProjectSubstitutedPathText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    _parentProjectSubstitutedPathText.setEditable(false);

    String parentProjectPath = _contextsProject.getParentProject();
    if (_parentIsDefaultContext) {
      // Parent context is default context, disable parent paths fields.
      _parentProjectRawPathText.setText(Messages.EditContextPage_Text_NoPathToParent);
      _parentProjectSubstitutedPathText.setText(Messages.EditContextPage_Text_NoPathToParent);
      _parentProjectRawPathText.setEnabled(false);
      _parentProjectSubstitutedPathText.setEnabled(false);
      parentProjectPathBrowseButton.setEnabled(false);
    } else {
      _parentProjectRawPathText.setText(parentProjectPath);
      _parentProjectRawPathText.addModifyListener(new ModifyListener() {
        public void modifyText(ModifyEvent e_p) {
          setPageComplete(isPageComplete());
        }
      });
    }
  }

  /**
   * Creates the administrator table.
   * @param toolkit_p the toolkit used to create table.
   * @param parent_p the parent composite where to create table.
   * @return the created table.
   */
  private Table createAdministratorTable(FormToolkit toolkit_p, final Composite parent_p) {
    Table table = toolkit_p.createTable(parent_p, SWT.NONE);
    table.setLinesVisible(true);
    table.setHeaderVisible(true);
    table.setBackground(parent_p.getDisplay().getSystemColor(SWT.COLOR_WHITE));
    FormHelper.updateControlLayoutDataWithLayoutTypeData(table, LayoutType.GRID_LAYOUT);
    GridData data = (GridData) table.getLayoutData();
    data.verticalSpan = 2;
    // Give a preferred vertical size to avoid very high dialog.
    data.heightHint = 100;
    return table;
  }

  /**
   * Create the table columns.
   * @param viewer_p the viewer to create columns of.
   */
  private void createColumns(final TableViewer viewer_p) {
    TableLayout tableLayout = new TableLayout();
    viewer_p.getTable().setLayout(tableLayout);
    tableLayout.addColumnData(new ColumnWeightData(33, 40, true));
    tableLayout.addColumnData(new ColumnWeightData(66, 80, true));
    String[] labels = new String[] { Messages.EditContextPage_AdministratorsId_Column_Text, Messages.EditContextPage_AdministratorsName_Column_Text };
    for (int i = 0; i < 2; i++) {
      final int columnIndex = i;
      TableViewerColumn viewerColumn = new TableViewerColumn(viewer_p, SWT.LEFT, columnIndex);
      final TableColumn column = viewerColumn.getColumn();
      column.setText(labels[columnIndex]);
      // Set the sorter on each column.
      column.addSelectionListener(new SelectionAdapter() {
        @Override
        public void widgetSelected(SelectionEvent e) {
          ((AdministratorsTableSorter) viewer_p.getSorter()).setColumn(columnIndex);
          int direction = viewer_p.getTable().getSortDirection();
          if (viewer_p.getTable().getSortColumn() == column) {
            direction = direction == SWT.UP ? SWT.DOWN : SWT.UP;
          } else {
            direction = SWT.DOWN;
          }
          viewer_p.getTable().setSortDirection(direction);
          viewer_p.getTable().setSortColumn(column);
          viewer_p.refresh();
        }
      });
      viewerColumn.setLabelProvider(new AdministratorsLabelProvider());
    }
  }

  /**
   * @return <code>true</code> if the current user belong to the administrator list, <code>false</code> otherwise.
   */
  protected boolean currentUserIsAdministrator() {
    String currentUserId = ProjectActivator.getInstance().getCurrentUserId();
    for (Couple<String, OrganisationUser> existingUsers : _administrators) {
      if (currentUserId.equalsIgnoreCase(existingUsers.getKey())) {
        return true;
      }
    }
    return false;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractEditNamedElementPage#dispose()
   */
  @Override
  public void dispose() {
    try {
      super.dispose();
    } finally {
      if (null != _viewer) {
        _viewer.removeSelectionChangedListener(_removeListener);
        _viewer = null;
      }
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractEditNamedElementPage#doCreateControl(org.eclipse.swt.widgets.Composite,
   *      org.eclipse.ui.forms.widgets.FormToolkit)
   */
  @Override
  protected Composite doCreateControl(Composite parent_p, FormToolkit toolkit_p) {
    Composite result = super.doCreateControl(parent_p, toolkit_p);
    // Add parent context value.
    addParentEdition(toolkit_p, result);
    if (ProjectActivator.getInstance().isAdministrator()) {
      addParentProjectPathPart(toolkit_p, result);
      addAdministratorsEdition(toolkit_p, result);
    }
    addDescriptionEdition(toolkit_p, result);
    return result;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractPageWithLiveValidation#doIsPageComplete()
   */
  @Override
  protected IStatus doIsPageComplete() {
    IStatus superStatus = super.doIsPageComplete();
    // Not admin ? -> stop here.
    if (!ProjectActivator.getInstance().isAdministrator()) {
      return superStatus;
    }
    // Admin ? -> validate the parent project path.
    if (IStatus.ERROR == superStatus.getSeverity()) {
      // super method returned an error status, can't return a worst status -> stop here.
      return superStatus;
    }
    IStatus parentPathStatus = validateParentProjectPath();
    // Return the worst status.
    return (superStatus.getSeverity() >= parentPathStatus.getSeverity()) ? superStatus : parentPathStatus;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractEditElementDescriptionPage#getDescriptionEAttribute()
   */
  @Override
  protected EAttribute getDescriptionEAttribute() {
    return ContextsPackage.Literals.CONTEXT__DESCRIPTION;
  }

  @Override
  protected Context getNamedElement() {
    return _context;
  }

  @Override
  protected String getPageTitle() {
    return Messages.EditContextPage_Page_Title;
  }

  /**
   * @return parent project raw path from text field or <code>null</code> if the text field was't created.
   */
  public String getParentProjectRawPath() {
    if (null == _parentProjectRawPathText) {
      return null;
    }
    return _parentProjectRawPathText.getText();
  }

  /**
   * Check that a parent path contains only env_var variable references.
   * @param path_p
   * @return <code>true</code> if the given path contains only env_var variable references (or no variable reference), <code>false</code> otherwise.
   */
  protected boolean isPathContainingEnvVarOnly(String path_p) {
    List<ModelUtil.VariableReferenceType> variableReferences = ModelUtil.extractVariableReferences(path_p);
    for (ModelUtil.VariableReferenceType variableReference : variableReferences) {
      if (!ModelUtil.VARIABLE_REFERENCE_TYPE_ENV_VAR.equals(variableReference._variableReferencePrefix)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Validate parent project raw path value.
   * @return
   */
  protected IStatus validateParentProjectPath() {
    // TODO Damien see how to use the EMF validation constraint on ContextsProject.
    // Parent context is default context -> path hasn't to be validated.
    if (_parentIsDefaultContext) {
      return Status.OK_STATUS;
    }
    String parentContextRawLocation = _parentProjectRawPathText.getText();
    // Is parent path empty ?
    if (parentContextRawLocation.isEmpty()) {
      _parentProjectSubstitutedPathText.setText(ICommonConstants.EMPTY_STRING);
      return new Status(IStatus.ERROR, UI_PLUGIN_ID, Messages.EditContextPage_ErrorMessage_EmptyPath);
    }
    // Is parent path containing only variable reference with env_var type ?
    if (!isPathContainingEnvVarOnly(parentContextRawLocation)) {
      _parentProjectSubstitutedPathText.setText(ICommonConstants.EMPTY_STRING);
      return new Status(IStatus.ERROR, UI_PLUGIN_ID, Messages.EditContextPage_ErrorMessage_OnlyEnvVar);
    }
    // Is parent path indicating an existing directory ?
    String substitutedParentContextLocation = DataUtil.getSubstitutedValue(parentContextRawLocation, getNamedElement());
    _parentProjectSubstitutedPathText.setText(substitutedParentContextLocation);
    if (!new File(substitutedParentContextLocation).isDirectory()) {
      return new Status(IStatus.ERROR, UI_PLUGIN_ID, Messages.EditContextPage_WarningMessage_NonExistingDirectory);
    }
    // Is parent path indicating an existing context ?
    RootContextsProject rootContextsProject =
        ProjectActivator.getInstance().getProjectHandler().getContextFromLocation(substitutedParentContextLocation, new CaseUnsensitiveResourceSetImpl());
    if (null == rootContextsProject) {
      return new Status(IStatus.ERROR, UI_PLUGIN_ID, Messages.EditContextPage_WarningMessage_NoContextFound);
    }
    // Is designated context the parent context of validated context ?
    if (!_context.getSuperContext().getId().equals(rootContextsProject.getAdministratedContext().getId())) {
      return new Status(IStatus.ERROR, UI_PLUGIN_ID, Messages.EditContextPage_ErrorMessage_FoundContextNotParent);
    }
    return Status.OK_STATUS;
  }
}