/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment.baseline.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.environment.UseBaselineEnvironmentContext;
import com.thalesgroup.orchestra.framework.environment.filesystem.FileSystemEnvironmentHandler;
import com.thalesgroup.orchestra.framework.environment.ui.AbstractEnvironmentUseBaselineViewer;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;

/**
 * @author T0052089
 */
@SuppressWarnings("nls")
public class ExtendedFSEnvStartingPointViewer extends AbstractEnvironmentUseBaselineViewer {

  protected List<StringHolder> _tableContent;

  protected IWizardContainer _wizardContainer;

  /**
   * StringHolder objects are displayed in the table.
   * @author T0052089
   */
  protected static class StringHolder {
    protected String _value;

    protected StringHolder(String initialValue_p) {
      _value = initialValue_p;
    }

    @Override
    public String toString() {
      return _value;
    }
  }

  /**
   * Constructor.
   * @param startingPointContext_p
   */
  public ExtendedFSEnvStartingPointViewer(UseBaselineEnvironmentContext startingPointContext_p) {
    super(startingPointContext_p);
  }

  @Override
  public void createControl(Composite parent_p, FormToolkit toolkit_p, IWizardContainer wizardContainer_p) {
    _wizardContainer = wizardContainer_p;
    // Create an empty list.
    _tableContent = new ArrayList<StringHolder>(10);
    // Add existing values.
    Map<String, String> startingPointAttributes = _useBaselineContext.getUseBaselineAttributes();
    List<String> values = FileSystemEnvironmentHandler.decodeValues(startingPointAttributes.get(FileSystemEnvironmentHandler.ATTRIBUTE_KEY_INPUT_DIRECTORIES));
    for (String value : values) {
      _tableContent.add(new StringHolder(value));
    }
    // Add new values.
    for (int i = 0; i < 10; ++i) {
      _tableContent.add(new StringHolder(ICommonConstants.EMPTY_STRING));
    }
    // Create controls of the viewer.
    parent_p.setLayout(new GridLayout());
    // Title label.
    String titleText = "Directories list (" + (_useBaselineContext._allowUserInteractions ? "" : "not ") + "editable): ";
    Label titleLabel = FormHelper.createLabel(toolkit_p, parent_p, titleText);
    titleLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
    // Table.
    TableViewer tableViewer = new TableViewer(parent_p, SWT.NONE);
    tableViewer.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
    // Table layout.
    TableLayout layout = new TableLayout();
    // There is only one column so far.
    layout.addColumnData(new ColumnWeightData(100, 100, true));
    tableViewer.getTable().setLayout(layout);
    tableViewer.getTable().setLinesVisible(true);
    tableViewer.setContentProvider(ArrayContentProvider.getInstance());
    TableViewerColumn tableColumn = new TableViewerColumn(tableViewer, SWT.NONE);
    tableColumn.setLabelProvider(new ColumnLabelProvider());
    tableColumn.setEditingSupport(new ExtendedFSDirectoryEditingSupport(tableViewer));
    tableViewer.setInput(_tableContent);
    // Set columns size to match content.
    for (TableColumn tColumn : tableViewer.getTable().getColumns()) {
      tColumn.pack();
    }
  }

  /**
   * @return
   */
  protected List<String> getTableContent() {
    List<String> result = new ArrayList<String>();
    for (StringHolder sh : _tableContent) {
      // Ignore empty lines.
      if (!sh._value.isEmpty()) {
        result.add(sh._value);
      }
    }
    return result;
  }

  protected class ExtendedFSDirectoryEditingSupport extends EditingSupport {
    /**
     * TextCellEditor used to edit values.
     */
    protected final TextCellEditor _currentTextCellEditor;

    /**
     * 
     */
    public ExtendedFSDirectoryEditingSupport(TableViewer tableViewer_p) {
      super(tableViewer_p);
      _currentTextCellEditor = new TextCellEditor(tableViewer_p.getTable());
    }

    /**
     * @see org.eclipse.jface.viewers.EditingSupport#getCellEditor(java.lang.Object)
     */
    @Override
    protected CellEditor getCellEditor(Object element_p) {
      return _currentTextCellEditor;
    }

    /**
     * @see org.eclipse.jface.viewers.EditingSupport#canEdit(java.lang.Object)
     */
    @SuppressWarnings("synthetic-access")
    @Override
    protected boolean canEdit(Object element_p) {
      return _useBaselineContext._allowUserInteractions;
    }

    /**
     * @see org.eclipse.jface.viewers.EditingSupport#getValue(java.lang.Object)
     */
    @Override
    protected Object getValue(Object element_p) {
      return element_p.toString();
    }

    /**
     * @see org.eclipse.jface.viewers.EditingSupport#setValue(java.lang.Object, java.lang.Object)
     */
    @SuppressWarnings("synthetic-access")
    @Override
    protected void setValue(Object element_p, Object value_p) {
      if ((element_p instanceof StringHolder) && (value_p instanceof String)) {
        // Update model element.
        ((StringHolder) element_p)._value = (String) value_p;
        // Update viewer.
        getViewer().update(element_p, null);
        // Update starting point context.
        List<String> tableContent = getTableContent();
        _useBaselineContext.getUseBaselineAttributes().put(FileSystemEnvironmentHandler.ATTRIBUTE_KEY_INPUT_DIRECTORIES,
            FileSystemEnvironmentHandler.encodeValues(tableContent));
        // Update wizard buttons.
        _wizardContainer.updateButtons();
      }

    }

  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.ui.AbstractEnvironmentUseBaselineViewer#setEnabled(boolean)
   */
  public void setEnabled(boolean enabled_p) {
    // TODO Auto-generated method stub

  }
}
