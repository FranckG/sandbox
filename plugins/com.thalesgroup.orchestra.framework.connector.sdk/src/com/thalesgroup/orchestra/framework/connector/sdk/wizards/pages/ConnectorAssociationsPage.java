/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.connector.sdk.wizards.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.connector.sdk.SdkWizardsActivator;
import com.thalesgroup.orchestra.framework.connector.sdk.operation.ConnectorProjectParameters;
import com.thalesgroup.orchestra.framework.connector.sdk.operation.TypeDefinition;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper.LayoutType;
import com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage;

/**
 * @author T0052089
 */
public class ConnectorAssociationsPage extends AbstractFormsWizardPage {
  /**
   * Properties of table columns.
   */
  protected static String[] COLUMN_PROPERTIES = new String[] { "typename", "logicalname", "physicalname" }; //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
  /**
   * Pattern matching the file extension in physical names.
   */
  public static String PHYSICAL_NAME_EXTENSION_PATTERN = "\\*\\.\\w+$"; //$NON-NLS-1$
  /**
   * Type names must be composed of word characters.
   */
  public static String TYPE_NAME_FORMAT = "\\w+"; //$NON-NLS-1$
  /**
   * Connector project parameters.
   */
  protected ConnectorProjectParameters _projectParameters;
  /**
   * Types definition table.
   */
  protected TableViewer _typesTableViewer;

  /**
   * @param projectParameters_p
   */
  public ConnectorAssociationsPage(ConnectorProjectParameters projectParameters_p) {
    super("ConnectorAssociations"); //$NON-NLS-1$
    _projectParameters = projectParameters_p;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage#doCreateControl(org.eclipse.swt.widgets.Composite,
   *      org.eclipse.ui.forms.widgets.FormToolkit)
   */
  @Override
  protected Composite doCreateControl(Composite parent_p, FormToolkit toolkit_p) {
    // Create main composite.
    Composite mainComposite = FormHelper.createCompositeWithLayoutType(toolkit_p, parent_p, LayoutType.GRID_LAYOUT, 2, false);

    final Table table = new Table(mainComposite, SWT.FULL_SELECTION | SWT.SINGLE | SWT.BORDER);
    table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2));
    _typesTableViewer = new TableViewer(table);

    table.setHeaderVisible(true);
    table.setLinesVisible(true);

    TableColumn typeNameColumn = new TableColumn(table, SWT.NONE);
    typeNameColumn.setText(Messages.ConnectorAssociationsPage_TableColumnName_TypeName);
    typeNameColumn.setWidth(100);

    TableColumn logicalNameColumn = new TableColumn(table, SWT.NONE);
    logicalNameColumn.setText(Messages.ConnectorAssociationsPage_TableColumnName_LogicalName);
    logicalNameColumn.setWidth(100);

    TableColumn physicalNameColumn = new TableColumn(table, SWT.NONE);
    physicalNameColumn.setText(Messages.ConnectorAssociationsPage_TableColumnName_PhysicalName);
    physicalNameColumn.setWidth(100);

    // _typesTableViewer.setContentProvider(ArrayContentProvider.getInstance());
    _typesTableViewer.setLabelProvider(new ExtensionLabelProvider());
    _typesTableViewer.setColumnProperties(COLUMN_PROPERTIES);
    _typesTableViewer.setCellEditors(new CellEditor[] { new TextCellEditor(table), new TextCellEditor(table), new TextCellEditor(table) });
    _typesTableViewer.setCellModifier(new ExtensionTableCellModifier(_typesTableViewer));

    Button addButton = new Button(mainComposite, SWT.NONE);
    addButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
    addButton.setText(Messages.ConnectorAssociationsPage_Button_Add);
    addButton.addSelectionListener(new SelectionAdapter() {
      /**
       * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
       */
      @Override
      public void widgetSelected(SelectionEvent e_p) {
        TypeDefinition newType = new TypeDefinition("TypeName", "*", "*.typeextension"); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
        _typesTableViewer.add(newType);
        validatePage();
      }
    });

    Button removeButton = new Button(mainComposite, SWT.NONE);
    removeButton.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, false, true));
    removeButton.setText(Messages.ConnectorAssociationsPage_Button_Remove);
    removeButton.addSelectionListener(new SelectionAdapter() {
      /**
       * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
       */
      @Override
      public void widgetSelected(SelectionEvent e_p) {
        IStructuredSelection selection = (IStructuredSelection) _typesTableViewer.getSelection();
        if (!selection.isEmpty()) {
          Object selectedElement = selection.getFirstElement();
          _typesTableViewer.remove(selectedElement);
          validatePage();
        }
      }
    });

    return mainComposite;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage#getPageImageDescriptor()
   */
  @Override
  protected ImageDescriptor getPageImageDescriptor() {
    return SdkWizardsActivator.getDefault().getImageDescriptor("connector/new_connector_wiz_ban.png"); //$NON-NLS-1$
  }

  /**
   * @see com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage#getPageTitle()
   */
  @Override
  protected String getPageTitle() {
    return Messages.ConnectorAssociationsPage_PageTitle;
  }

  public void updateData() {
    TableItem[] tableItems = _typesTableViewer.getTable().getItems();
    TypeDefinition[] typeDefinitions = new TypeDefinition[tableItems.length];
    for (int i = 0; i < tableItems.length; ++i) {
      typeDefinitions[i] = (TypeDefinition) tableItems[i].getData();
    }
    _projectParameters.setTypeDefinitions(typeDefinitions);
  }

  public void validatePage() {
    TableItem[] tableItems = _typesTableViewer.getTable().getItems();
    // The associations table is empty -> the page is valid.
    if (0 == tableItems.length) {
      setErrorMessage(null);
      setPageComplete(true);
      return;
    }
    // Every fields have to be filled.
    for (TableItem tableItem : tableItems) {
      TypeDefinition typeDefinition = (TypeDefinition) tableItem.getData();
      if (typeDefinition.getTypeName().isEmpty() || typeDefinition.getLogicalName().isEmpty() || typeDefinition.getPhysicalName().isEmpty()) {
        setErrorMessage(Messages.ConnectorAssociationsPage_ErrorMessage_AllFieldsMandatory);
        setPageComplete(false);
        return;
      }
    }
    // Type name format.
    for (TableItem tableItem : tableItems) {
      TypeDefinition typeDefinition = (TypeDefinition) tableItem.getData();
      if (!typeDefinition.getTypeName().matches(TYPE_NAME_FORMAT)) {
        setErrorMessage(Messages.ConnectorAssociationsPage_ErrorMessage_TypeNameIncorrectFormat);
        setPageComplete(false);
        return;
      }
    }
    // Type name uniqueness.
    Set<String> typeNameList = new HashSet<String>();
    for (TableItem tableItem : tableItems) {
      TypeDefinition typeDefinition = (TypeDefinition) tableItem.getData();
      if (!typeNameList.add(typeDefinition.getTypeName())) {
        setErrorMessage(Messages.ConnectorAssociationsPage_ErrorMessage_TypeNameNotUnique);
        setPageComplete(false);
        return;
      }
    }
    // Physical name extension format.
    // TODO only the file extension part is checked but the whole physical name format should be checked.
    List<String> physicalNameExtensionList = new ArrayList<String>();
    Pattern physicalNameExtensionPattern = Pattern.compile(PHYSICAL_NAME_EXTENSION_PATTERN);
    for (TableItem tableItem : tableItems) {
      TypeDefinition typeDefinition = (TypeDefinition) tableItem.getData();
      Matcher physicalNameExtensionMatcher = physicalNameExtensionPattern.matcher(typeDefinition.getPhysicalName());
      if (!physicalNameExtensionMatcher.find()) {
        setErrorMessage(Messages.ConnectorAssociationsPage_ErrorMessage_PhysicalNameExtensionMissing);
        setPageComplete(false);
        return;
      }
      // Keep found extension to check uniqueness.
      physicalNameExtensionList.add(physicalNameExtensionMatcher.group());
    }
    // Physical name extension uniqueness.
    HashSet<String> physicalNameExtensionSet = new HashSet<String>(physicalNameExtensionList);
    if (physicalNameExtensionList.size() != physicalNameExtensionSet.size()) {
      setErrorMessage(Messages.ConnectorAssociationsPage_ErrorMessage_PhysicalNameExtensionNotUnique);
      setPageComplete(false);
      return;
    }

    setErrorMessage(null);
    setPageComplete(true);
  }

  protected static class ExtensionLabelProvider extends CellLabelProvider {
    /**
     * @see org.eclipse.jface.viewers.CellLabelProvider#update(org.eclipse.jface.viewers.ViewerCell)
     */
    @Override
    public void update(ViewerCell cell_p) {
      TypeDefinition type = (TypeDefinition) cell_p.getElement();
      String cellText = null;
      switch (cell_p.getColumnIndex()) {
        case 0:
          cellText = type.getTypeName();
        break;
        case 1:
          cellText = type.getLogicalName();
        break;
        case 2:
          cellText = type.getPhysicalName();
        break;
      }
      cell_p.setText(cellText);
    }
  }

  protected class ExtensionTableCellModifier implements ICellModifier {
    private TableViewer _tableViewer;

    /**
     * @param tableViewer_p
     */
    public ExtensionTableCellModifier(TableViewer tableViewer_p) {
      _tableViewer = tableViewer_p;
    }

    /**
     * @see org.eclipse.jface.viewers.ICellModifier#canModify(java.lang.Object, java.lang.String)
     */
    public boolean canModify(Object element_p, String property_p) {
      return true;
    }

    /**
     * @see org.eclipse.jface.viewers.ICellModifier#getValue(java.lang.Object, java.lang.String)
     */
    public Object getValue(Object element_p, String property_p) {
      // For the given element, get the value of the column identified by property_p.
      int columnIndex = Arrays.asList(COLUMN_PROPERTIES).indexOf(property_p);
      switch (columnIndex) {
        case 0:
          return ((TypeDefinition) element_p).getTypeName();
        case 1:
          return ((TypeDefinition) element_p).getLogicalName();
        case 2:
          return ((TypeDefinition) element_p).getPhysicalName();
        default:
          return ICommonConstants.EMPTY_STRING;
      }
    }

    /**
     * @see org.eclipse.jface.viewers.ICellModifier#modify(java.lang.Object, java.lang.String, java.lang.Object)
     */
    public void modify(Object element_p, String property_p, Object value_p) {
      // For the given element, set the value corresponding to the given property.
      int columnIndex = Arrays.asList(COLUMN_PROPERTIES).indexOf(property_p);
      if (element_p instanceof Item) {
        TypeDefinition type = (TypeDefinition) ((Item) element_p).getData();
        switch (columnIndex) {
          case 0:
            type.setTypeName((String) value_p);
          break;
          case 1:
            type.setLogicalName((String) value_p);
          break;
          case 2:
            type.setPhysicalName((String) value_p);
          break;
        }
        _tableViewer.update(type, new String[] { property_p });
        validatePage();
      }
    }
  }

}
