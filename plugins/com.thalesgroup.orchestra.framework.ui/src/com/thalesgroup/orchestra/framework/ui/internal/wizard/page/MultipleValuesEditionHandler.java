/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.wizard.page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.root.ui.custom.ICustomMultipleValuesVariableEditButton;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper.LayoutType;
import com.thalesgroup.orchestra.framework.ui.internal.wizard.page.type.IMultipleValueCustomizer;
import com.thalesgroup.orchestra.framework.ui.viewer.AbstractLabelProvider;
import com.thalesgroup.orchestra.framework.ui.wizard.custom.CustomVariableEditButtonRegistry;

/**
 * Multiple values handler.
 * @author t0076261
 */
public class MultipleValuesEditionHandler extends AbstractValueEditionHandler implements ISelectionChangedListener, IDoubleClickListener {
  /**
   * Add button.
   */
  protected Button _addButton;
  /**
   * Value currently selected.
   */
  protected VariableValue _currentValue;
  /**
   * In-use editing support.
   */
  protected ValueEditingSupport _editingSupport;
  /**
   * The last text cell editor got from the last selection.
   */
  protected TextCellEditor _lastTextCellEditor;
  /**
   * Remove button.
   */
  protected Button _removeButton;
  /**
   * Viewer reminder.
   */
  protected TableViewer _viewer;

  /**
   * Add a new empty value to edited variable.
   */
  protected void addNewVariableValue() {
    addNewVariableValue(ICommonConstants.EMPTY_STRING);
  }

  /**
   * Add a new value to edited variable (non interactive)
   */
  protected void addNewVariableValue(String value) {
    final VariableValue newValue = ModelUtil.createVariableValue(_variable);
    // Set a default empty value in order to always provide a non null value to editing support.
    // see MultipleValuesEditionHandler.ValueEditingSupport.getValue()
    newValue.setValue(value);
    _variable.getValues().add(newValue);

    if (value.equals(ICommonConstants.EMPTY_STRING)) {
      // Select the newly created value
      _viewer.getControl().getDisplay().asyncExec(new Runnable() {
        @Override
        public void run() {
          _viewer.getTable().forceFocus();
          _viewer.editElement(newValue, 0);
        }
      });
    }
    // Adapt to IMultipleValueCustomizer.
    IMultipleValueCustomizer multipleValueCustomizer = (IMultipleValueCustomizer) _customizer.getAdapter(IMultipleValueCustomizer.class);
    if (null != multipleValueCustomizer) {
      multipleValueCustomizer.newVariableValueAdded(newValue);
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractValueEditionHandler#createButtonsPart(org.eclipse.ui.forms.widgets.FormToolkit,
   *      org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Composite createButtonsPart(FormToolkit toolkit_p, Composite parent_p) {
    // Add buttons composite.
    Composite buttonsComposite = FormHelper.createCompositeWithLayoutType(toolkit_p, parent_p, LayoutType.GRID_LAYOUT, 1, false);
    GridData data = (GridData) buttonsComposite.getLayoutData();
    data.grabExcessHorizontalSpace = false;
    data.verticalAlignment = SWT.FILL;
    _addButton = FormHelper.createButton(toolkit_p, buttonsComposite, Messages.MultipleValuesEditionHandler_Button_Add_Text, SWT.PUSH);
    data = (GridData) FormHelper.updateControlLayoutDataWithLayoutTypeData(_addButton, LayoutType.GRID_LAYOUT);
    data.grabExcessVerticalSpace = false;
    _removeButton = FormHelper.createButton(toolkit_p, buttonsComposite, Messages.MultipleValuesEditionHandler_Button_Remove_Text, SWT.PUSH);
    data = (GridData) FormHelper.updateControlLayoutDataWithLayoutTypeData(_removeButton, LayoutType.GRID_LAYOUT);
    data.grabExcessVerticalSpace = false;
    // Handle button selection.
    SelectionAdapter adapter = new SelectionAdapter() {
      /**
       * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
       */
      @Override
      public void widgetSelected(SelectionEvent e_p) {
        Widget widget = e_p.widget;
        if (_addButton == widget) {
          addNewVariableValue();
        } else if (_removeButton == widget) {
          List<VariableValue> selectedValues = ((StructuredSelection) _viewer.getSelection()).toList();
          if (!(selectedValues.get(0) instanceof EnvironmentVariableValue)) {
            // Except from environment variables:
            // There cannot be a variable without any value. So in case all
            // items have been selected, remove all but the first one
            // and set the remaining item to empty value.
            if (_viewer.getTable().getItemCount() == selectedValues.size()) {
              // Reset first value
              selectedValues.get(0).setValue("");
              // Remove first value from deleted values (selectedValues returned from
              // StructuredSelection is an unmodifiable list, so build a new one)
              selectedValues = new ArrayList<VariableValue>(selectedValues);
              selectedValues.remove(0);
            }
          }
          removeVariableValues(selectedValues);
        }
      }
    };
    _addButton.addSelectionListener(adapter);
    _removeButton.addSelectionListener(adapter);
    _removeButton.setEnabled(false);
    return buttonsComposite;
  }

  /**
   * Create content provider.
   * @return
   */
  protected ValueContentProvider createContentProvider() {
    return new ValueContentProvider();
  }

  /**
   * Create editing support implementation for value in-place edition.
   * @return
   */
  protected ValueEditingSupport createEditingSupport() {
    return new ValueEditingSupport();
  }

  /**
   * Create label provider.
   * @return
   */
  protected ValueLabelProvider createLabelProvider() {
    return new ValueLabelProvider();
  }

  /**
   * Create table structure (not to be confused with table itself).
   */
  protected void createTableStructure() {
    Table table = _viewer.getTable();
    table.setHeaderVisible(true);
    _viewer.setContentProvider(createContentProvider());
    // Table layout.
    TableLayout layout = new TableLayout();
    table.setLayout(layout);
    table.setLinesVisible(true);
    table.setHeaderVisible(false);
    // There is only one column so far.
    layout.addColumnData(new ColumnWeightData(100, 100, true));
    TableViewerColumn viewerColumn = new TableViewerColumn(_viewer, SWT.NONE, 0);
    viewerColumn.setLabelProvider(createLabelProvider());
    _editingSupport = createEditingSupport();
    viewerColumn.setEditingSupport(_editingSupport);
    TableColumn column = viewerColumn.getColumn();
    column.setAlignment(SWT.CENTER);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractValueEditionHandler#createValuesPart(org.eclipse.ui.forms.widgets.FormToolkit,
   *      org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Composite createValuesPart(FormToolkit toolkit_p, Composite parent_p) {
    _viewer = new TableViewer(parent_p, SWT.BORDER | SWT.MULTI);
    _enabledColor = _viewer.getControl().getBackground();
    createTableStructure();
    FormHelper.updateControlLayoutDataWithLayoutTypeData(_viewer.getControl(), LayoutType.GRID_LAYOUT);
    // Add selection changed listener.
    _viewer.addSelectionChangedListener(this);
    _viewer.addDoubleClickListener(this);
    _viewer.setInput(_variable);
    return _viewer.getTable();
  }

  /**
   * Customizer enablement after latest selection occurred.
   * @param selectedValue_p
   * @return TODO Guillaume Segregate buttons enablement from parts enablement for customizers !
   */
  protected boolean customizerEnablementAfterSelection(VariableValue selectedValue_p) {
    return true;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractValueEditionHandler#dispose()
   */
  @Override
  protected void dispose() {
    if (null != _viewer) {
      _viewer.getContentProvider().dispose();
      _viewer.getLabelProvider().dispose();
      _viewer.removeDoubleClickListener(this);
      _viewer.removeSelectionChangedListener(this);
      _viewer = null;
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractValueEditionHandler#doSetEnabled(boolean)
   */
  @Override
  protected void doSetEnabled(boolean enabled_p) {
    // There is a far easier way to write this, but since both monoValue and multiValues handlers are participating,
    // it makes more sense to do this that way.
    if (enabled_p && !_variable.isMultiValued()) {
      _variable.setMultiValued(true);
    }
    Control control = _viewer.getControl();
    _editingSupport.setReadOnly(!enabled_p);
    control.setBackground(enabled_p ? _enabledColor : _disabledColor);
    if (null != _addButton) {
      _addButton.setEnabled(enabled_p);
    }
    if (null != _removeButton) {
      _removeButton.setEnabled(enabled_p && (null != _currentValue) && removeEnablementAfterSelection(_currentValue));
    }
  }

  /**
   * @see org.eclipse.jface.viewers.IDoubleClickListener#doubleClick(org.eclipse.jface.viewers.DoubleClickEvent)
   */
  public void doubleClick(DoubleClickEvent event_p) {
    if (_customizer instanceof IDoubleClickListener) {
      ((IDoubleClickListener) _customizer).doubleClick(event_p);
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.IValueHandler#editionCancelled()
   */
  public void editionCancelled() {
    // Nothing to do.
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.IValueHandler#editionFinished()
   */
  public void editionFinished() {
    // Nothing to do.
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractValueEditionHandler#getInsertionControl()
   */
  @Override
  protected Text getInsertionControl() {
    if (null != _lastTextCellEditor) {
      return (Text) _lastTextCellEditor.getControl();
    }
    return null;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractValueEditionHandler#getValue()
   */
  @Override
  public String getValue() {
    if (null != _currentValue) {
      return _editingSupport.getValue(_currentValue);
    }
    return null;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.IValueHandler#getValueIndex()
   */
  public int getValueIndex() {
    return _viewer.getTable().getSelectionIndex();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.IValueHandler#getVariableValue()
   */
  public VariableValue getVariableValue() {
    return _currentValue;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractValueEditionHandler#isEnabled()
   */
  @Override
  protected boolean isEnabled() {
    return super.isEnabled() && _variable.isMultiValued();
  }

  /**
   * Remove button enablement after latest selection occurred.
   * @param selectedValue_p
   * @return
   */
  protected boolean removeEnablementAfterSelection(VariableValue selectedValue_p) {
    // Variable must contain more than 1 value and selected value must belong to the edited variable (it's always the case normally).
    return !ModelUtil.isTransientElement(selectedValue_p) && _variable.getValues().size() > 1 && _variable.getValues().contains(selectedValue_p);
  }

  /**
   * Remove specified value for edited variable.
   * @param value_p
   */
  protected void removeVariableValue(VariableValue value_p) {
    // Removing a variable value might have an impact on children context.
    Command command =
        ModelHandlerActivator
            .getDefault()
            .getEditingDomain()
            .createCommand(DeleteCommand.class,
                new CommandParameter(_variable, ContextsPackage.Literals.ABSTRACT_VARIABLE__VALUES, Collections.singletonList(value_p)));
    if ((null != command) && command.canExecute()) {
      command.execute();
    }
  }

  /**
   * Remove a list of specified values for edited variable.
   * @param value_p
   */
  protected void removeVariableValues(List<VariableValue> values_p) {
    // Removing a variable value might have an impact on children context.
    Command command =
        ModelHandlerActivator.getDefault().getEditingDomain()
            .createCommand(DeleteCommand.class, new CommandParameter(_variable, ContextsPackage.Literals.ABSTRACT_VARIABLE__VALUES, values_p));
    if ((null != command) && command.canExecute()) {
      command.execute();
    }
  }

  /**
   * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
   */
  public void selectionChanged(SelectionChangedEvent event_p) {
    Object firstElement = ((StructuredSelection) event_p.getSelection()).getFirstElement();
    if (firstElement instanceof VariableValue) {
      _currentValue = (VariableValue) firstElement;
      _selectedElement = firstElement;
      _removeButton.setEnabled(removeEnablementAfterSelection(_currentValue));
      _customizer.setEnabled(customizerEnablementAfterSelection(_currentValue));
    } else {
      _currentValue = null;
      _removeButton.setEnabled(false);
      _customizer.setEnabled(false);
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractValueEditionHandler#setNewValue(java.lang.String)
   */
  @Override
  public void setNewValue(String newValue_p) {
    // Update model.
    if (null != _currentValue) {
      _editingSupport.setValue(_currentValue, newValue_p);
    }
    // Then update UI.
    super.setNewValue(newValue_p);
  }

  /**
   * Variable value content provider.
   * @author t0076261
   */
  protected class ValueContentProvider extends AdapterFactoryContentProvider {
    /**
     * Constructor.
     */
    public ValueContentProvider() {
      super(ModelHandlerActivator.getDefault().getEditingDomain().getAdapterFactory());
    }

    /**
     * Do get values for specified variable.
     * @param variable_p
     * @return
     */
    protected Object[] doGetValues(AbstractVariable variable_p) {
      return DataUtil.getRawValues(variable_p, _editionContext).toArray();
    }

    /**
     * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider#getElements(java.lang.Object)
     */
    @Override
    public Object[] getElements(Object object_p) {
      if (_variable == object_p) {
        // Return computed values.
        return doGetValues(_variable);
      }
      return super.getElements(object_p);
    }
  }

  /**
   * Variable value editing support.
   * @author t0076261
   */
  protected class ValueEditingSupport extends EditingSupport {
    /**
     * Should this editor be read-only ?
     */
    protected boolean _isReadOnly;

    /**
     * Constructor.
     * @param viewer_p
     */
    public ValueEditingSupport() {
      super(_viewer);
      _isReadOnly = false;
    }

    /**
     * @see org.eclipse.jface.viewers.EditingSupport#canEdit(java.lang.Object)
     */
    @Override
    protected boolean canEdit(Object element_p) {
      return ContextsPackage.Literals.VARIABLE_VALUE.isInstance(element_p) && !ContextsPackage.Literals.ENVIRONMENT_VARIABLE_VALUE.isInstance(element_p);
    }

    /**
     * @see org.eclipse.jface.viewers.EditingSupport#getCellEditor(java.lang.Object)
     */
    @Override
    protected CellEditor getCellEditor(Object element_p) {
      TextCellEditor textCellEditor = new TextCellEditor(_viewer.getTable(), _isReadOnly ? SWT.READ_ONLY : SWT.NONE);
      _lastTextCellEditor = textCellEditor;
      _editedElement = element_p;
      return textCellEditor;
    }

    /**
     * @see org.eclipse.jface.viewers.EditingSupport#getValue(java.lang.Object)
     */
    @Override
    protected String getValue(Object element_p) {
      String value = ((VariableValue) element_p).getValue();
      if (null == value) {
        value = ICommonConstants.EMPTY_STRING;
      }
      return value;
    }

    /**
     * @see org.eclipse.jface.viewers.EditingSupport#setValue(java.lang.Object, java.lang.Object)
     */
    @Override
    protected void setValue(Object element_p, Object value_p) {
      // Precondition.
      if (DataUtil.isUnmodifiable(_variable, _editionContext)) {
        // Do nothing in this case.
        return;
      }
      ((VariableValue) element_p).setValue((String) value_p);
    }

    /**
     * Set editor read-only state.
     * @param readOnly_p
     */
    protected void setReadOnly(boolean isReadOnly_p) {
      _isReadOnly = isReadOnly_p;
    }
  }

  /**
   * Value label provider.
   * @author t0076261
   */
  protected class ValueLabelProvider extends AbstractLabelProvider {
    /**
     * @see org.eclipse.jface.viewers.CellLabelProvider#update(org.eclipse.jface.viewers.ViewerCell)
     */
    @Override
    public void update(ViewerCell cell_p) {
      Object element = cell_p.getElement();
      EObject object = (EObject) element;
      cell_p.setText(getText(object, _editionContext));
      cell_p.setImage(getImage(object, _editionContext));
    }
  }

  /**
   * Add values to current variable
   * @param values List of new values
   */
  protected void addValues(List<String> values) {
    if (null != values) {
      for (String value : values) {
        addNewVariableValue(value);
      }
    }
  }

  /**
   * Create a new variable edit button
   * @param toolkit_p Form toolkit
   * @param buttonsComposite_p Buttons composite
   * @param customButton_p Button to add
   */
  protected void createCustomButton(FormToolkit toolkit_p, Composite buttonsComposite_p, final ICustomMultipleValuesVariableEditButton customButton_p) {
    Button button = FormHelper.createButton(toolkit_p, buttonsComposite_p, customButton_p.getLabel(), SWT.PUSH);
    GridData data = (GridData) FormHelper.updateControlLayoutDataWithLayoutTypeData(button, LayoutType.GRID_LAYOUT);
    data.grabExcessVerticalSpace = false;

    SelectionAdapter adapter = new SelectionAdapter() {
      /**
       * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
       */
      @Override
      public void widgetSelected(SelectionEvent e_p) {
        addValues(customButton_p.getValues());
      }
    };
    button.addSelectionListener(adapter);
  }

  /**
   * Create custom buttons
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractValueEditionHandler#createCustomButtons(org.eclipse.ui.forms.widgets.FormToolkit,
   *      org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected void createCustomButtons(FormToolkit toolkit_p, Composite buttonsComposite_p) {
    CustomVariableEditButtonRegistry registry = CustomVariableEditButtonRegistry.getInstance();
    List<ICustomMultipleValuesVariableEditButton> list = registry.getButtonListForMultipleValuesVariable(ModelUtil.getElementPath(getVariable()));
    if (null != list) {
      for (ICustomMultipleValuesVariableEditButton button : list) {
        createCustomButton(toolkit_p, buttonsComposite_p, button);
      }
    }
  }
}