/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.doors.framework.environment.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.doors.framework.environment.DoorsActivator;
import com.thalesgroup.orchestra.doors.framework.environment.DoorsEnvironment;
import com.thalesgroup.orchestra.doors.framework.environment.DoorsEnvironment.ProjectEnvironmentData;
import com.thalesgroup.orchestra.doors.framework.environment.DoorsEnvironmentHandler;
import com.thalesgroup.orchestra.doors.framework.environment.model.DoorsProject;
import com.thalesgroup.orchestra.framework.environment.ui.AbstractEnvironmentPage;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper.LayoutType;

/**
 * This page allows to set a logical name on selected Doors projects.
 * @author S0032874
 */
public class DoorsEnvironmentProjectNamePage extends AbstractEnvironmentPage {

  protected final List<DoorsProject> _doorsProject;

  protected TableViewer _nameViewer;

  /**
   * Default constructor
   * @param handler_p
   */
  public DoorsEnvironmentProjectNamePage(DoorsEnvironmentHandler handler_p) {
    super(Messages.DoorsEnvironment_Project_Name_page_title, handler_p);

    List<ProjectEnvironmentData> projectsEnvironmentData =
        DoorsEnvironment.deserializeProjectList(handler_p.getAttributes().get(DoorsEnvironment.ATTRIBUT_KEY_PROJECT_LIST));
    _doorsProject = new ArrayList<DoorsProject>();
    for (ProjectEnvironmentData projectEnvData : projectsEnvironmentData) {
      String projectDoorsName = projectEnvData._doorsProjectName;
      if (handler_p.getDoorsProjectMap().containsKey(projectEnvData._doorsProjectId)) {
        projectDoorsName = handler_p.getDoorsProjectMap().get(projectEnvData._doorsProjectId).getDoorsName();
      }
      DoorsProject model = new DoorsProject(null, projectDoorsName, false, projectEnvData._doorsProjectId, projectEnvData._doorsProjectLogicalName);
      _doorsProject.add(model);
    }
  }

  /**
   * @return the {@link EditingSupport}
   */
  protected EditingSupport createDoorsLogicalNameEditingSupport() {
    return new DoorsLogicalNameEditingSupport(_nameViewer);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.ui.AbstractEnvironmentPage#doCanFlipToNextPage()
   */
  @Override
  protected boolean doCanFlipToNextPage() {
    // No next page
    return false;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage#doCreateControl(org.eclipse.swt.widgets.Composite,
   *      org.eclipse.ui.forms.widgets.FormToolkit)
   */
  @Override
  protected Composite doCreateControl(Composite parent_p, final FormToolkit toolkit_p) {
    Wizard wizard = (Wizard) getWizard();
    wizard.getShell().setSize(600, 600);
    Composite viewerComposite = FormHelper.createCompositeWithLayoutType(toolkit_p, parent_p, LayoutType.GRID_LAYOUT, 1, false);
    viewerComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
    _nameViewer = new TableViewer(viewerComposite, SWT.BORDER | SWT.FULL_SELECTION);

    Table table = _nameViewer.getTable();
    table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
    table.setHeaderVisible(true);
    table.setLinesVisible(true);
    TableLayout layout = new TableLayout();
    table.setLayout(layout);

    // create second column
    layout.addColumnData(new ColumnWeightData(20, 150, true));
    TableViewerColumn doorsNameColumn = new TableViewerColumn(_nameViewer, SWT.LEFT);
    doorsNameColumn.getColumn().setText(Messages.DoorsEnvironment_Project_doors_name_Column_Title);
    doorsNameColumn.setLabelProvider(new CellLabelProvider() {

      @SuppressWarnings("synthetic-access")
      @Override
      public void update(ViewerCell cell_p) {
        DoorsProject model = (DoorsProject) cell_p.getElement();
        cell_p.setText(_handler.getVariablesHandler().getSubstitutedValue(model.getDoorsName()));
        cell_p.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_DARK_GRAY));
      }
    });

    // Create one column
    layout.addColumnData(new ColumnWeightData(20, 150, true));
    TableViewerColumn logicalNameColumn = new TableViewerColumn(_nameViewer, SWT.LEFT);
    logicalNameColumn.getColumn().setText(Messages.DoorsEnvironment_Project_user_name_Column_Title);
    logicalNameColumn.setLabelProvider(new CellLabelProvider() {

      @Override
      public void update(ViewerCell cell_p) {
        DoorsProject model = (DoorsProject) cell_p.getElement();
        cell_p.setText(model.getLogicalName());
      }
    });

    logicalNameColumn.setEditingSupport(createDoorsLogicalNameEditingSupport());

    _nameViewer.addDoubleClickListener(new IDoubleClickListener() {
      public void doubleClick(DoubleClickEvent event) {
        final ISelection selection = event.getSelection();
        if (selection == null || !(selection instanceof IStructuredSelection)) {
          return;
        }
        _nameViewer.editElement(((IStructuredSelection) selection).getFirstElement(), 1);
      }
    });

    _nameViewer.setComparator(new ViewerComparator());
    _nameViewer.setContentProvider(ArrayContentProvider.getInstance());
    _nameViewer.setInput(_doorsProject);

    return viewerComposite;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.ui.AbstractEnvironmentPage#forceUpdate()
   */
  @Override
  public void forceUpdate() {
    // TODO Auto-generated method stub
    super.forceUpdate();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.ui.AbstractEnvironmentPage#getPageImageDescriptor()
   */
  @Override
  protected ImageDescriptor getPageImageDescriptor() {
    return DoorsActivator.getInstance().getImageDescriptor("doors_big.gif"); //$NON-NLS-1$
  }

  /**
   * @see com.thalesgroup.orchestra.framework.common.ui.wizards.AbstractFormsWizardPage#getPageTitle()
   */
  @Override
  protected String getPageTitle() {
    return Messages.DoorsEnvironment_Project_Name_page_title;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.ui.AbstractEnvironmentPage#getUpdatedValues()
   */
  @Override
  protected Map<String, String> getUpdatedValues() {
    Map<String, String> stringMap = new HashMap<String, String>();
    stringMap.put(DoorsEnvironment.ATTRIBUT_KEY_PROJECT_LIST, DoorsEnvironment.serializeDoorsProject(_doorsProject));
    return stringMap;
  }

  protected class DoorsLogicalNameEditingSupport extends EditingSupport {

    protected final TextCellEditor _textCellEditor;

    public DoorsLogicalNameEditingSupport(TableViewer tableViewer_p) {
      super(tableViewer_p);
      _textCellEditor = new TextCellEditor(tableViewer_p.getTable());
    }

    @Override
    protected boolean canEdit(Object element_p) {
      return true;
    }

    @Override
    protected TextCellEditor getCellEditor(Object element_p) {
      return _textCellEditor;
    }

    @Override
    protected Object getValue(Object element_p) {
      DoorsProject model = (DoorsProject) element_p;
      return model.getLogicalName();
    }

    @Override
    protected void setValue(Object element_p, Object value_p) {
      DoorsProject model = (DoorsProject) element_p;
      model.setLogicalName(value_p.toString());
      getViewer().update(model, null);
      forceUpdate();
    }
  }
}
