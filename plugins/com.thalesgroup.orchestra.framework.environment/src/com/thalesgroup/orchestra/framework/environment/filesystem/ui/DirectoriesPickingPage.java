/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment.filesystem.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.environment.EnvironmentActivator;
import com.thalesgroup.orchestra.framework.environment.filesystem.FileSystemEnvironmentHandler;
import com.thalesgroup.orchestra.framework.environment.ui.IVariablesHandler;
import com.thalesgroup.orchestra.framework.environment.ui.IVariablesHandler.IReferencingValueHandler;
import com.thalesgroup.orchestra.framework.environment.ui.IVariablesHandler.VariableType;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper.LayoutType;

/**
 * @author t0076261
 */
public class DirectoriesPickingPage extends AbstractFileSystemPage {
  /**
   * Comparator used to compare file paths.
   */
  protected static final Comparator<String> FILE_PATHS_COMPARATOR = new Comparator<String>() {
    public int compare(String value1_p, String value2_p) {
      return new File(value1_p).compareTo(new File(value2_p));
    }
  };
  /**
   * Browse folder button.
   */
  protected Button _browseButton;
  /**
   * Move value down in the list button.
   */
  protected Button _downButton;
  /**
   * Substituted selected value {@link Text} widget.
   */
  protected Text _substitutedValueText;
  /**
   * Move value up in the list button.
   */
  protected Button _upButton;
  /**
   * Make a reference to another variable button.
   */
  protected Button _variablesButton;
  /**
   * Add many directories at once
   */
  protected Button _addManyButton;

  protected FormToolkit _toolkit;

  /**
   * Constructor.
   * @param handler_p
   */
  public DirectoriesPickingPage(FileSystemEnvironmentHandler handler_p) {
    super("PickFileSystemDirectories", handler_p); //$NON-NLS-1$
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.filesystem.ui.AbstractFileSystemPage#addMainViewer(org.eclipse.swt.widgets.Composite,
   *      org.eclipse.ui.forms.widgets.FormToolkit)
   */
  @Override
  protected void addMainViewer(Composite composite_p, FormToolkit toolkit_p) {
    _toolkit = toolkit_p;
    // Create viewer composite.
    Composite viewerComposite = FormHelper.createCompositeWithLayoutType(toolkit_p, composite_p, LayoutType.GRID_LAYOUT, 2, false);
    GridData data = (GridData) viewerComposite.getLayoutData();
    data.horizontalSpan = 1;
    data.grabExcessHorizontalSpace = true;
    data.grabExcessVerticalSpace = false;
    viewerComposite.setLayoutData(data);
    // Do create table viewer.
    super.addMainViewer(viewerComposite, toolkit_p);
    // Adapt it to composite.
    data = (GridData) _viewer.getControl().getLayoutData();
    data.horizontalSpan = 2;
    // Substituted value Text.
    Map<Class, Widget> labelAndText =
        FormHelper.createLabelAndText(toolkit_p, viewerComposite, Messages.DirectoriesPickingPage_Label_SubstitutedValue, ICommonConstants.EMPTY_STRING,
            SWT.BORDER);
    _substitutedValueText = (Text) labelAndText.get(Text.class);
    FormHelper.updateControlLayoutDataWithLayoutTypeData(_substitutedValueText, LayoutType.GRID_LAYOUT);
    data = (GridData) _substitutedValueText.getLayoutData();
    data.grabExcessHorizontalSpace = true;
    data.grabExcessVerticalSpace = false;
    _substitutedValueText.setLayoutData(data);
    _substitutedValueText.setEditable(false);
    _substitutedValueText.setBackground(composite_p.getBackground());
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.filesystem.ui.AbstractFileSystemPage#createLabelProvider()
   */
  @Override
  protected AbstractFileSystemCellLabelProvider createLabelProvider() {
    // Create a label provider with images of directories.
    return new AbstractFileSystemCellLabelProvider() {
      @Override
      public Image getErrorImage() {
        return EnvironmentActivator.getInstance().getImage("directories/folder_error.gif"); //$NON-NLS-1$
      }

      @Override
      public Image getImage() {
        return EnvironmentActivator.getInstance().getImage("directories/folder.gif"); //$NON-NLS-1$
      }
    };
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.filesystem.ui.AbstractFileSystemPage#doAddExtraButtons(org.eclipse.swt.widgets.Composite,
   *      org.eclipse.ui.forms.widgets.FormToolkit)
   */
  @Override
  protected List<Button> doAddExtraButtons(Composite composite_p, FormToolkit toolkit_p) {
    List<Button> result = new ArrayList<Button>(0);
    IVariablesHandler variablesBrowserHandler = _handler.getVariablesHandler();
    if (null != variablesBrowserHandler) {
      _variablesButton = variablesBrowserHandler.createVariablesButton(toolkit_p, composite_p, new IReferencingValueHandler() {
        /**
         * @see com.thalesgroup.orchestra.framework.environment.ui.IVariablesHandler.IReferencingValueHandler#handleSelectedValue(java.lang.String)
         */
        public void handleSelectedValue(String selectedVariableReference_p) {
          FileSystemElement selectedTableElement = getSelectedElement();
          // Precondition.
          if (null == selectedTableElement) {
            return;
          }
          String oldValue = selectedTableElement.getValue();
          String newValue = null;
          if (selectedTableElement == _latestEditedFileSystemElement) {
            // Currently selected element has been edited -> get the selection from this edition operation.
            Point textSelection = _fileSystemEditingSupport.getSelection();
            // Compose resulting string (replace current selection by the selected variable reference).
            StringBuilder newValueStringBuilder = new StringBuilder(oldValue);
            newValueStringBuilder.replace(textSelection.x, textSelection.y, selectedVariableReference_p);
            newValue = newValueStringBuilder.toString();
          } else {
            // Currently selected element has not been edited -> append the variable reference to existing value.
            newValue = oldValue + selectedVariableReference_p;
          }
          // Set new value.
          setFileSystemElementValue(selectedTableElement, newValue);
        }
      });
      _variablesButton.setEnabled(false);
      result.add(_variablesButton);
    }
    if (hasAddManyButton()) {
      _addManyButton = toolkit_p.createButton(composite_p, Messages.DirectoriesPickingPage_Button_Text_AddMany, SWT.PUSH);
      result.add(_addManyButton);
    }
    _browseButton = toolkit_p.createButton(composite_p, Messages.DirectoriesPickingPage_Button_Text_Browse, SWT.PUSH);
    _browseButton.setEnabled(false);
    result.add(_browseButton);
    _upButton = toolkit_p.createButton(composite_p, Messages.DirectoriesPickingPage_Button_Text_Up, SWT.PUSH);
    _upButton.setEnabled(false);
    result.add(_upButton);
    _downButton = toolkit_p.createButton(composite_p, Messages.DirectoriesPickingPage_Button_Text_Down, SWT.PUSH);
    _downButton.setEnabled(false);
    result.add(_downButton);

    return result;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.ui.AbstractEnvironmentPage#doCanFlipToNextPage()
   */
  @Override
  protected boolean doCanFlipToNextPage() {
    // Can flip to next page if :
    // - Filters are needed for currently edited variable,
    // - Entered directory list is valid.
    return VariableType.Artefacts.equals(_handler.getVariablesHandler().getVariableType())
           && getHandler().validateDirectories(getFileSystemElementValues()).isOK();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.filesystem.ui.AbstractFileSystemPage#getInitialValues(com.thalesgroup.orchestra.framework.environment.filesystem.FileSystemEnvironmentHandler)
   */
  @Override
  protected List<String> getInitialValues(FileSystemEnvironmentHandler handler_p) {
    return handler_p.getDirectoriesValues();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.ui.AbstractEnvironmentPage#getPageImageDescriptor()
   */
  @Override
  protected ImageDescriptor getPageImageDescriptor() {
    return EnvironmentActivator.getInstance().getImageDescriptor("directories/folder_wiz_ban.png"); //$NON-NLS-1$
  }

  /**
   * @see com.thalesgroup.orchestra.framework.common.ui.wizards.AbstractFormsWizardPage#getPageTitle()
   */
  @Override
  protected String getPageTitle() {
    return Messages.DirectoriesPickingPage_Page_Title;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.filesystem.ui.AbstractFileSystemPage#getRawIntroductionText()
   */
  @Override
  protected String getRawIntroductionText() {
    return null;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.filesystem.ui.AbstractFileSystemPage#getValueComparator()
   */
  @Override
  protected Comparator<String> getValueComparator() {
    return FILE_PATHS_COMPARATOR;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.filesystem.ui.AbstractFileSystemPage#getValuesKey()
   */
  @Override
  protected String getValuesKey() {
    return FileSystemEnvironmentHandler.ATTRIBUTE_KEY_INPUT_DIRECTORIES;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.filesystem.ui.AbstractFileSystemPage#handleButtonPressed(org.eclipse.swt.widgets.Button)
   */
  @Override
  protected boolean handleButtonPressed(Button button_p) {
    if (button_p == _browseButton) {
      return (null != selectElementValue());
    } else if (button_p == _upButton) {
      return moveElementUp();
    } else if (button_p == _downButton) {
      return moveElementDown();
    } else if (button_p == _addManyButton) {
      return addSelectedDirectories();
    }
    return super.handleButtonPressed(button_p);
  }

  /**
   * Select directories through wildcard
   * @param viewPath_p Filter path or null if none
   * @return
   */
  protected List<String> getSelectedDirectories(String viewPath_p) {
    DirectoryDialog directoryDialog = new DirectoryDialog(getShell());
    directoryDialog.setMessage(Messages.DirectoriesPickingPage_DirectoryDialog_BaseDirectorySelection_Message);
    if (null != viewPath_p) {
      directoryDialog.setFilterPath(viewPath_p);
    }
    String folder = directoryDialog.open();
    if (folder != null) {
      DirectorySelectionDialog filteringDialog = new DirectorySelectionDialog(folder);
      final WizardDialog dialog = new WizardDialog(getShell(), filteringDialog);
      dialog.setPageSize(500, 350);
      int result = dialog.open();
      if (Window.OK == result) {
        return filteringDialog.getSelectedDirectories();
      }
    }
    return null;
  }

  /**
   * Add selected directories to FileSystem element list
   * @return
   */
  protected boolean addSelectedDirectories() {
    List<String> selectedDirectories = getSelectedDirectories(null);
    if (null != selectedDirectories) {
      for (String directory : selectedDirectories) {
        addFileSystemElement(directory);
      }
      return true;
    }
    return false;
  }

  /**
   * Move currently selected element down the values list.
   */
  protected boolean moveElementDown() {
    int currentIndex = getSelectedIndex();
    // Precondition.
    if (currentIndex >= _fileSystemElements.size()) {
      return false;
    }
    FileSystemElement value = _fileSystemElements.remove(currentIndex);
    int newIndex = currentIndex + 1;
    if (_fileSystemElements.size() <= newIndex) {
      _fileSystemElements.add(value);
    } else {
      _fileSystemElements.add(newIndex, value);
    }
    _viewer.refresh();
    return true;
  }

  /**
   * Move currently selected element up the values list.
   */
  protected boolean moveElementUp() {
    int currentIndex = getSelectedIndex();
    // Precondition.
    if (0 == currentIndex) {
      return false;
    }
    FileSystemElement value = _fileSystemElements.remove(currentIndex);
    int newIndex = currentIndex - 1;
    if (0 >= newIndex) {
      _fileSystemElements.add(0, value);
    } else {
      _fileSystemElements.add(newIndex, value);
    }
    _viewer.refresh();
    return true;
  }

  /**
   * Refresh _substitutedValue text field.
   */
  public void refreshSubstituedValueText() {
    FileSystemElement selectedElement = getSelectedElement();
    if (null == selectedElement) {
      // No selection -> empty the text field.
      _substitutedValueText.setText(ICommonConstants.EMPTY_STRING);
    } else {
      // Refresh text field, substituting variables.
      IVariablesHandler variablesHandler = _handler.getVariablesHandler();
      if (null != variablesHandler) {
        _substitutedValueText.setText(variablesHandler.getSubstitutedValue(getSelectedElement().getValue()));
      } else {
        _substitutedValueText.setText(Messages.DirectoriesPickingPage_Warning_CantFindSubstitutionHandler);
      }
    }
  }

  /**
   * Select a directory path for the selected FileSystemElement.
   */
  protected String selectElementValue() {
    // Precondition.
    FileSystemElement selectedElement = getSelectedElement();
    if (null == selectedElement) {
      return null;
    }
    // Initialize directory selection dialog.
    DirectoryDialog dialog = new DirectoryDialog(getShell());
    dialog.setMessage(Messages.DirectoriesPickingPage_DirectorySelectionDialog_Message);
    String selectedElementValue = selectedElement.getValue();
    if (null != selectedElementValue) {
      // Set filter with current value.
      dialog.setFilterPath(selectedElementValue);
    }
    // Show the directory selection dialog.
    String selectedFolder = dialog.open();
    if (null != selectedFolder) {
      // Set new value to selected element (replace existing value if any).
      setFileSystemElementValue(selectedElement, selectedFolder);
    }
    return selectedFolder;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.filesystem.ui.AbstractFileSystemPage#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
   */
  @Override
  public void selectionChanged(SelectionChangedEvent event_p) {
    // Keep buttons disabled if current env if is read-only.
    if (_isCurrentEnvironmentReadOnly)
      return;
    super.selectionChanged(event_p);

    boolean isSingleSelection = 1 == _viewer.getTable().getSelectionCount();

    // Enable buttons if selection isn't unique.
    _browseButton.setEnabled(isSingleSelection);
    if (null != _variablesButton) {
      _variablesButton.setEnabled(isSingleSelection);
    }
    _upButton.setEnabled(isSingleSelection);
    _downButton.setEnabled(isSingleSelection);
    // Refresh _substitutedValueText.
    refreshSubstituedValueText();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.filesystem.ui.AbstractFileSystemPage#setFileSystemElementValue(com.thalesgroup.orchestra.framework.environment.filesystem.ui.AbstractFileSystemPage.FileSystemElement,
   *      java.lang.String)
   */
  @Override
  protected void setFileSystemElementValue(FileSystemElement element_p, String newValue_p) {
    super.setFileSystemElementValue(element_p, newValue_p);
    refreshSubstituedValueText();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.filesystem.ui.AbstractFileSystemPage#validateValue(java.lang.String)
   */
  @Override
  protected IStatus validateValue(String value_p) {
    return getHandler().validateDirectory(value_p);
  }

  /**
   * Enable/Disable "Add Many..." button
   * @return true if enable, false otherwise
   */
  protected boolean hasAddManyButton() {
    return true;
  }
}