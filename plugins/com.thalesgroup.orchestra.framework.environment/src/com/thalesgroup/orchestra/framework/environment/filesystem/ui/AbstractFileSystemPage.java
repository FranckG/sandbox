/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment.filesystem.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.environment.filesystem.FileSystemEnvironmentHandler;
import com.thalesgroup.orchestra.framework.environment.ui.AbstractEnvironmentPage;
import com.thalesgroup.orchestra.framework.root.ui.AbstractRunnableWithDisplay;
import com.thalesgroup.orchestra.framework.root.ui.DisplayHelper;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper.LayoutType;

/**
 * An abstract FileSystem environment page.
 * @author t0076261
 */
public abstract class AbstractFileSystemPage extends AbstractEnvironmentPage implements ISelectionChangedListener {
  /**
   * Add single value button.
   */
  protected Button _addButton;
  /**
   * Duplicated elements set.
   */
  protected final Set<FileSystemElement> _duplicatedElements;
  /**
   * Elements on error and there status.
   */
  protected final Map<FileSystemElement, IStatus> _elementsOnError;
  /**
   * Table editing support (used to get the last text selection).
   */
  protected FileSystemElementEditingSupport _fileSystemEditingSupport;
  /**
   * Current list of FileSystem elements.
   */
  protected final List<FileSystemElement> _fileSystemElements;
  /**
   * Latest edited FileSystemElement (useful to know if selection is applicable to selected FileSystemElement).
   */
  protected FileSystemElement _latestEditedFileSystemElement;
  /**
   * Remove value button.
   */
  protected Button _removeButton;
  /**
   * The viewer reference.
   */
  protected TableViewer _viewer;

  /**
   * Constructor.
   * @param pageId_p
   * @param handler_p
   */
  public AbstractFileSystemPage(String pageId_p, FileSystemEnvironmentHandler handler_p) {
    super(pageId_p, handler_p);
    // Get initial values.
    List<String> values = getInitialValues(handler_p);
    // Store values as FileSystemElements and manage errors.
    _fileSystemElements = new ArrayList<FileSystemElement>(values.size());
    _elementsOnError = new HashMap<FileSystemElement, IStatus>(0);
    _duplicatedElements = new HashSet<FileSystemElement>();
    for (String value : values) {
      FileSystemElement fileSystemElement = new FileSystemElement(value);
      _fileSystemElements.add(fileSystemElement);
      // Value validation.
      validateFileSystemElement(fileSystemElement);
    }
    // Fill duplicated elements list.
    checkForDuplicatedValues();
    // Show an error message if needed.
    refreshErrorMessage();
  }

  /**
   * This is an opportunity for subclasses to add custom composites at the bottom of the page. <br>
   * Layout of parent is a {@link GridLayout} so layoutData field of children have to be a {@link GridData} or <code>null</code>. Number of columns of the
   * parent {@link GridLayout} can be accessed using {@link #getContainerColumnsCount()}.<br>
   * Default implementation does nothing.
   * @param composite_p
   * @param toolkit_p
   */
  protected void addBottomComposites(Composite parentComposite_p, FormToolkit toolkit_p) {
    // Default implementation does nothing.
  }

  /**
   * Add buttons.
   * @param composite_p
   * @param toolkit_p
   */
  protected void addButtons(Composite composite_p, FormToolkit toolkit_p) {
    // Buttons composite.
    Composite buttonsComposite = FormHelper.createCompositeWithLayoutType(toolkit_p, composite_p, LayoutType.GRID_LAYOUT, 1, false);
    GridData data = (GridData) buttonsComposite.getLayoutData();
    data.grabExcessHorizontalSpace = false;
    data.verticalAlignment = SWT.CENTER;
    // Create buttons.
    // Add button.
    _addButton = toolkit_p.createButton(buttonsComposite, Messages.DirectoriesPickingPage_Button_Text_Add, SWT.PUSH);
    _addButton.setEnabled(!_isCurrentEnvironmentReadOnly);
    data = (GridData) FormHelper.updateControlLayoutDataWithLayoutTypeData(_addButton, LayoutType.GRID_LAYOUT);
    data.grabExcessVerticalSpace = false;
    // Remove button.
    _removeButton = toolkit_p.createButton(buttonsComposite, Messages.DirectoriesPickingPage_Button_Text_Remove, SWT.PUSH);
    _removeButton.setEnabled(false);
    data = (GridData) FormHelper.updateControlLayoutDataWithLayoutTypeData(_removeButton, LayoutType.GRID_LAYOUT);
    data.grabExcessVerticalSpace = false;
    // Extra buttons.
    List<Button> extraButtons = doAddExtraButtons(buttonsComposite, toolkit_p);
    for (Button button : extraButtons) {
      data = (GridData) FormHelper.updateControlLayoutDataWithLayoutTypeData(button, LayoutType.GRID_LAYOUT);
      data.grabExcessVerticalSpace = false;
    }
    // Create selection listener and assign to buttons.
    SelectionListener listener = new SelectionAdapter() {
      @SuppressWarnings("synthetic-access")
      @Override
      public void widgetSelected(SelectionEvent e_p) {
        Object source = e_p.getSource();
        // Handle button pressed.
        boolean dataModified = handleButtonPressed((Button) source);
        // Update data up to the handler.
        if (dataModified) {
          forceUpdate();
        }
      }
    };
    // Register listener.
    _addButton.addSelectionListener(listener);
    for (Button button : extraButtons) {
      button.addSelectionListener(listener);
    }
    _removeButton.addSelectionListener(listener);
  }

  /**
   * Add a new element to the FileSystemeElements list, validate it, display it and select it in the viewer.
   * @return <code>true</code>
   */
  protected boolean addFileSystemElement() {
    FileSystemElement newElement = new FileSystemElement(ICommonConstants.EMPTY_STRING);
    _fileSystemElements.add(newElement);
    // Is the new empty FileSystemElement valid ?
    validateFileSystemElement(newElement);
    refreshErrorMessage();
    // Add the new element to the viewer.
    _viewer.add(newElement);
    _viewer.setSelection(new StructuredSelection(newElement));
    // Show and edit new added element.
    _viewer.getTable().forceFocus();
    _viewer.reveal(newElement);
    _viewer.editElement(newElement, 0);
    return true;
  }

  /**
   * Add a new element to the FileSystemeElements list, validate it, display it.
   * @param value_p
   * @return <code>true</code>
   */
  protected boolean addFileSystemElement(String value_p) {
    FileSystemElement newElement = new FileSystemElement(value_p);
    _fileSystemElements.add(newElement);
    // Is the new FileSystemElement valid ?
    validateFileSystemElement(newElement);
    refreshErrorMessage();
    // Add the new element to the viewer.
    _viewer.add(newElement);
    // Show new added element.
    _viewer.reveal(newElement);
    return true;
  }

  /**
   * Add introduction text that takes place above the viewer and the buttons.<br>
   * This is a guide to what the user should provide as values.<br>
   * It makes use of styled text (with limited possibilities though).
   * @param composite_p
   * @param toolkit_p
   */
  protected void addIntroductionText(Composite composite_p, FormToolkit toolkit_p) {
    // Try and create styled text.
    StyledText styledText = FormHelper.createStyledText(toolkit_p, composite_p, getRawIntroductionText(), SWT.WRAP | SWT.READ_ONLY);
    // Precondition.
    if (null == styledText) {
      return;
    }
    // Then adapt to composite.
    GridData data = (GridData) FormHelper.updateControlLayoutDataWithLayoutTypeData(styledText, LayoutType.GRID_LAYOUT);
    data.horizontalSpan = getContainerColumnsCount();
    data.grabExcessHorizontalSpace = true;
    data.grabExcessVerticalSpace = false;
  }

  /**
   * Add the main viewer, which is to be a {@link TableViewer}.
   * @param composite_p
   * @param toolkit_p
   */
  protected void addMainViewer(Composite composite_p, FormToolkit toolkit_p) {
    _viewer = new TableViewer(composite_p, SWT.BORDER | SWT.MULTI);
    createTableStructure();
    _viewer.addSelectionChangedListener(this);
    FormHelper.updateControlLayoutDataWithLayoutTypeData(_viewer.getControl(), LayoutType.GRID_LAYOUT);
    // Set tool-tip support.
    ColumnViewerToolTipSupport.enableFor(_viewer);
  }

  /**
   * This method clear the _duplicatedElements list and refill it using fresh data from _fileSystemElements list. Only valid elements are processed -> only
   * valid elements can be marked as duplicated.
   */
  protected void checkForDuplicatedValues() {
    // Value is valid. Check for duplications against other elements.
    Comparator<String> valueComparator = getValueComparator();
    if (null == valueComparator) {
      // No value comparator is available, stop here.
      return;
    }
    // Go through valid elements.
    List<FileSystemElement> validFileSystemElements = new ArrayList<FileSystemElement>(_fileSystemElements);
    validFileSystemElements.removeAll(_elementsOnError.keySet());
    // Reset duplicated elements list.
    _duplicatedElements.clear();
    for (int i = 0; i < validFileSystemElements.size() - 1; ++i) {
      String valueToCompare = validFileSystemElements.get(i).getValue();
      for (int j = i + 1; j < validFileSystemElements.size(); ++j) {
        if (0 == valueComparator.compare(valueToCompare, validFileSystemElements.get(j).getValue())) {
          _duplicatedElements.add(validFileSystemElements.get(i));
          _duplicatedElements.add(validFileSystemElements.get(j));
          break;
        }
      }
    }
  }

  /**
   * Create content provider.
   * @return A not <code>null</code> instance of {@link IContentProvider}.
   */
  protected IContentProvider createContentProvider() {
    // Viewer input is a simple list -> use this simple content provider.
    return ArrayContentProvider.getInstance();
  }

  /**
   * Create editing support.
   * @return A not <code>null</code> instance of FileSystemEditingSupport.
   */
  protected FileSystemElementEditingSupport createEditingSupport() {
    return new FileSystemElementEditingSupport(_viewer);
  }

  /**
   * Create label provider.
   * @return A not <code>null</code> instance of {@link AbstractFileSystemCellLabelProvider}.
   */
  protected abstract AbstractFileSystemCellLabelProvider createLabelProvider();

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
    // There is only one column so far.
    layout.addColumnData(new ColumnWeightData(100, 100, true));
    TableViewerColumn viewerColumn = new TableViewerColumn(_viewer, SWT.NONE, 0);
    viewerColumn.setLabelProvider(createLabelProvider());
    _fileSystemEditingSupport = createEditingSupport();
    viewerColumn.setEditingSupport(_fileSystemEditingSupport);
    TableColumn column = viewerColumn.getColumn();
    column.setAlignment(SWT.CENTER);
    // Manage table enablement.
    if (_isCurrentEnvironmentReadOnly) {
      table.setBackground(_viewer.getTable().getParent().getBackground());
    }
  }

  /**
   * Do add intermediate buttons for current page (ie between add and remove ones).
   * @param composite_p The parent composite to use at buttons creation time.
   * @param toolkit_p
   * @return
   */
  protected List<Button> doAddExtraButtons(Composite composite_p, FormToolkit toolkit_p) {
    return Collections.emptyList();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.common.ui.wizards.AbstractFormsWizardPage#doCreateControl(org.eclipse.swt.widgets.Composite,
   *      org.eclipse.ui.forms.widgets.FormToolkit)
   */
  @Override
  protected Composite doCreateControl(Composite parent_p, FormToolkit toolkit_p) {
    Composite composite = FormHelper.createCompositeWithLayoutType(toolkit_p, parent_p, LayoutType.GRID_LAYOUT, getContainerColumnsCount(), false);
    // Introduction text.
    addIntroductionText(composite, toolkit_p);
    // Main viewer.
    addMainViewer(composite, toolkit_p);
    // Set viewer input.
    _viewer.setInput(_fileSystemElements);
    // Buttons.
    addButtons(composite, toolkit_p);
    // Add bottom composites (if any).
    addBottomComposites(composite, toolkit_p);
    return composite;
  }

  /**
   * Get parent composite grid layout columns count.
   * @return
   */
  protected int getContainerColumnsCount() {
    return 2;
  }

  /**
   * Get values of FileSystemElements present in this page.
   * @return A non <code>null</code> (possibly empty) list of values.
   */
  protected List<String> getFileSystemElementValues() {
    List<String> values = new ArrayList<String>(_fileSystemElements.size());
    for (FileSystemElement tableElement : _fileSystemElements) {
      values.add(tableElement.getValue());
    }
    return values;
  }

  /**
   * Get handler as a {@link FileSystemEnvironmentHandler} one.
   * @return
   */
  protected FileSystemEnvironmentHandler getHandler() {
    return (FileSystemEnvironmentHandler) _handler;
  }

  /**
   * Get initial values that should be provided by this page to the user.
   * @param handler_p
   * @return
   */
  protected abstract List<String> getInitialValues(FileSystemEnvironmentHandler handler_p);

  /**
   * Get raw (ie with formatting instructions) introduction text.<br>
   * See {@link #getStyledTextParts(String)} for raw text formatting instructions.
   * @return
   */
  protected abstract String getRawIntroductionText();

  /**
   * Get currently selected table element.
   * @return <code>null</code> if no element is selected. The selected element otherwise.
   */
  protected FileSystemElement getSelectedElement() {
    IStructuredSelection structuredSelection = ((IStructuredSelection) _viewer.getSelection());
    return (FileSystemElement) structuredSelection.getFirstElement();
  }

  /**
   * Get all selected table elements.
   * @return <code>null</code> if no element is selected. The selected element otherwise.
   */
  @SuppressWarnings("unchecked")
  protected List<FileSystemElement> getSelectedElements() {
    IStructuredSelection structuredSelection = ((IStructuredSelection) _viewer.getSelection());
    return structuredSelection.toList();
  }

  /**
   * Get the index of the selected element.
   * @return <code>-1</code> if no element is selected. The index of the selected element otherwise.
   */
  protected int getSelectedIndex() {
    return _viewer.getTable().getSelectionIndex();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.ui.AbstractEnvironmentPage#getUpdatedValues()
   */
  @Override
  protected Map<String, String> getUpdatedValues() {
    Map<String, String> result = new HashMap<String, String>(0);
    result.put(getValuesKey(), FileSystemEnvironmentHandler.encodeValues(getFileSystemElementValues()));
    return result;
  }

  /**
   * Returns the value comparator to use to detect duplicated elements. If <code>null</code> is returned, no check on duplicated elements will be done.
   * Subclasses can override this method.
   * @return
   */
  protected Comparator<String> getValueComparator() {
    return null;
  }

  /**
   * Get key to use to store values in resulting updated values map.
   * @return
   */
  protected abstract String getValuesKey();

  /**
   * Handle button pressed for specified one.
   * @param button_p
   * @return
   */
  protected boolean handleButtonPressed(Button button_p) {
    if (button_p == _addButton) {
      return addFileSystemElement();
    } else if (button_p == _removeButton) {
      return removeSelectedFileSystemElements();
    }
    return false;
  }

  /**
   * Display an error message if at least one FileSystemElement is erroneous.
   */
  protected void refreshErrorMessage() {
    final String[] errorMessage = new String[1];
    if (!_elementsOnError.isEmpty() || !_duplicatedElements.isEmpty()) {
      errorMessage[0] = Messages.AbstractFileSystemPage_Error_InvalidElement;
    }
    // Update wizard page.
    DisplayHelper.displayRunnable(new AbstractRunnableWithDisplay() {
      public void run() {
        // Set error message.
        setErrorMessage(errorMessage[0]);
      }
    }, true);
  }

  /**
   * Remove one element.
   */
  protected void removeFileSystemElement(FileSystemElement element_p) {
    if (null != element_p) {
      _fileSystemElements.remove(element_p);
      _elementsOnError.remove(element_p);
    }
  }

  /**
   * Remove currently selected elements.
   */
  protected boolean removeSelectedFileSystemElements() {
    List<FileSystemElement> selectedElements = getSelectedElements();
    for (FileSystemElement element : selectedElements) {
      removeFileSystemElement(element);
    }
    checkForDuplicatedValues();
    _viewer.refresh();
    refreshErrorMessage();
    return true;
  }

  /**
   * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
   */
  public void selectionChanged(SelectionChangedEvent event_p) {
    // Keep buttons disabled if current env if is read-only.
    if (_isCurrentEnvironmentReadOnly)
      return;
    boolean isSelectionEmpty = event_p.getSelection().isEmpty();
    // Enable remove button if selection isn't empty.
    _removeButton.setEnabled(!isSelectionEmpty);
  }

  /**
   * Set the given value to specified FileSystemElement.<br>
   * @param element_p FileSystemElement to change value on.
   * @param newValue_p value to set (<code>null</code> is replaced with {@link ICommonConstants#EMPTY_STRING}).
   */
  protected void setFileSystemElementValue(final FileSystemElement element_p, final String newValue_p) {
    String newValue = newValue_p;
    // Avoid null value
    if (null == newValue) {
      newValue = ICommonConstants.EMPTY_STRING;
    }
    element_p.setValue(newValue);

    validateFileSystemElement(element_p);
    checkForDuplicatedValues();
    // Update attributes.
    forceUpdate();
    // Update wizard page.
    DisplayHelper.displayRunnable(new AbstractRunnableWithDisplay() {
      public void run() {
        // Force update of the modified element.
        _viewer.refresh();
        refreshErrorMessage();
      }
    }, true);
  }

  /**
   * Validate the given FileSystemElement using {@link #validateValue(String)} and add it or remove it from _elementsOnError list if it is valid or not valid.
   * @param elementToValidate_p
   */
  protected void validateFileSystemElement(FileSystemElement elementToValidate_p) {
    IStatus valueValidityStatus = validateValue(elementToValidate_p.getValue());
    if (null == valueValidityStatus || valueValidityStatus.isOK()) {
      // Element is valid, remove it from the error map (no problem if it is not in the map).
      _elementsOnError.remove(elementToValidate_p);
    } else {
      // Add to elements on error.
      _elementsOnError.put(elementToValidate_p, valueValidityStatus);
    }
  }

  /**
   * Method used by this page to validate a value (should be overridden by subclasses). <br>
   * Default behavior returns <code>Status.OK_STATUS</code>.
   * @param value_p
   * @return
   */
  protected IStatus validateValue(String value_p) {
    return Status.OK_STATUS;
  }

  /**
   * Base LabelProvider used to display FileSystemElements.
   * @author T0052089
   */
  protected abstract class AbstractFileSystemCellLabelProvider extends CellLabelProvider {
    /**
     * Returns an image to display beside an <b>erroneous</b> element in the table. <br>
     * Should be overridden by subclasses (default behavior returns <code>null</code>).
     * @return
     */
    public Image getErrorImage() {
      return null;
    }

    /**
     * Returns an image to display beside an element in the table. Should be overridden by subclasses (default behavior returns <code>null</code>).
     * @return
     */
    public Image getImage() {
      return null;
    }

    /**
     * @see org.eclipse.jface.viewers.CellLabelProvider#getToolTipDisplayDelayTime(java.lang.Object)
     */
    @Override
    public int getToolTipDisplayDelayTime(Object object_p) {
      return 200;
    }

    /**
     * @see org.eclipse.jface.viewers.CellLabelProvider#getToolTipForegroundColor(java.lang.Object)
     */
    @Override
    public Color getToolTipForegroundColor(Object object_p) {
      return _viewer.getControl().getDisplay().getSystemColor(SWT.COLOR_RED);
    }

    /**
     * @see org.eclipse.jface.viewers.CellLabelProvider#getToolTipText(java.lang.Object)
     */
    @Override
    public String getToolTipText(Object element_p) {
      IStatus status = _elementsOnError.get(element_p);
      if (null != status) {
        StringBuilder result = new StringBuilder(status.getMessage());
        for (IStatus childStatus : status.getChildren()) {
          result.append("\n ¤ " + childStatus.getMessage()); //$NON-NLS-1$
        }
        return result.toString();
      }
      if (_duplicatedElements.contains(element_p)) {
        return Messages.AbstractFileSystemPage_Error_DuplicatedElement;
      }
      return null;
    }

    /**
     * @see org.eclipse.jface.viewers.CellLabelProvider#getToolTipTimeDisplayed(java.lang.Object)
     */
    @Override
    public int getToolTipTimeDisplayed(Object object_p) {
      return 30000;
    }

    /**
     * @see org.eclipse.jface.viewers.CellLabelProvider#update(org.eclipse.jface.viewers.ViewerCell)
     */
    @Override
    public void update(ViewerCell cell_p) {
      // Get element.
      Object element = cell_p.getElement();
      if (element instanceof FileSystemElement) {
        FileSystemElement tableElement = (FileSystemElement) element;
        // Set text.
        cell_p.setText(tableElement.getValue());
        // Set image.
        Image fileSystemElementImage = null;
        if (null != _elementsOnError.get(tableElement) || _duplicatedElements.contains(tableElement)) {
          // Element is in error -> use error image.
          fileSystemElementImage = getErrorImage();
        } else {
          fileSystemElementImage = getImage();
        }
        cell_p.setImage(fileSystemElementImage);
      }
    }
  }

  /**
   * This class represents elements displayed in this page.
   * @author T0052089
   */
  protected static class FileSystemElement {
    /**
     * The value of this FileSystemElement.
     */
    private String value;

    /**
     * Constructor with an initial value.
     * @param value_p
     */
    public FileSystemElement(String value_p) {
      value = value_p;
    }

    /**
     * @return the value contained in this FileSystemElement.
     */
    public String getValue() {
      return value;
    }

    /**
     * @param value_p the value to put in this FileSystemElement.
     */
    public void setValue(String value_p) {
      value = value_p;
    }
  }

  /**
   * An {@link EditingSupport} implementation dedicated to FileSystem environment pages.
   * @author T0052089
   */
  protected class FileSystemElementEditingSupport extends EditingSupport {
    /**
     * TextCellEditor associated to this EditingSupport.
     */
    final protected TextCellEditor _textCellEditor;

    /**
     * Constructor.
     * @param columnViewer_p
     */
    @SuppressWarnings("synthetic-access")
    public FileSystemElementEditingSupport(ColumnViewer columnViewer_p) {
      super(columnViewer_p);
      _textCellEditor = new TextCellEditor(_viewer.getTable(), _isCurrentEnvironmentReadOnly ? SWT.READ_ONLY : SWT.NONE);
      if (_isCurrentEnvironmentReadOnly) {
        _textCellEditor.getControl().setBackground(_viewer.getTable().getParent().getBackground());
      }
    }

    /**
     * @see org.eclipse.jface.viewers.EditingSupport#canEdit(java.lang.Object)
     */
    @Override
    protected boolean canEdit(Object element_p) {
      return true;
    }

    /**
     * @see org.eclipse.jface.viewers.EditingSupport#getCellEditor(java.lang.Object)
     */
    @Override
    protected CellEditor getCellEditor(Object element_p) {
      // Keep last edited file system element.
      _latestEditedFileSystemElement = (FileSystemElement) element_p;
      return _textCellEditor;
    }

    /**
     * Get the selection of the Text widget associated with this editing support.
     * @return
     */
    protected Point getSelection() {
      return ((Text) _textCellEditor.getControl()).getSelection();
    }

    /**
     * @see org.eclipse.jface.viewers.EditingSupport#getValue(java.lang.Object)
     */
    @Override
    protected Object getValue(Object element_p) {
      if (element_p instanceof FileSystemElement) {
        return ((FileSystemElement) element_p).getValue();
      }
      return ICommonConstants.EMPTY_STRING;
    }

    /**
     * @see org.eclipse.jface.viewers.EditingSupport#setValue(java.lang.Object, java.lang.Object)
     */
    @Override
    protected void setValue(Object element_p, Object value_p) {
      if (element_p instanceof FileSystemElement) {
        setFileSystemElementValue((FileSystemElement) element_p, (String) value_p);
      }
    }
  }
}