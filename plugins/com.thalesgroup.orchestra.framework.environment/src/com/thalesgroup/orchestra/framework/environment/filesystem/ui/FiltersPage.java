/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment.filesystem.ui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.layout.PixelConverter;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.environment.EnvironmentActivator;
import com.thalesgroup.orchestra.framework.environment.filesystem.FileSystemEnvironmentHandler;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;

/**
 * @author t0076261
 */
public class FiltersPage extends AbstractFileSystemPage {

  /**
   * Add single value button.
   */
  protected Button _previewButton;

  protected Tree _previewTree;

  protected List<String> _directories;

  protected FileSystemEnvironmentHandler _fileSystemHandler;

  protected Set<String> _defaultFilters;

  /**
   * Constructor.
   * @param pageId_p
   * @param handler_p
   */
  public FiltersPage(FileSystemEnvironmentHandler handler_p) {
    super("DefineFilters", handler_p); //$NON-NLS-1$
    _fileSystemHandler = handler_p;
    _directories = handler_p.getDirectoriesValues();
    _defaultFilters = EnvironmentActivator.getInstance().getEnvironmentRegistry().getFileSystemEnvironmentDefaultFiltersRegistry().getDefaultFilters();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.filesystem.ui.AbstractFileSystemPage#addBottomComposites(org.eclipse.swt.widgets.Composite,
   *      org.eclipse.ui.forms.widgets.FormToolkit)
   */
  @Override
  public void addBottomComposites(Composite parentComposite_p, FormToolkit toolkit_p) {
    // Composite bottomComposite = toolkit_p.createComposite(parentComposite_p, SWT.NONE);
    // bottomComposite.setLayout(new GridLayout());
    // Generate default filters description text.
    StringBuilder defaultFiltersDescription = new StringBuilder();
    if (_defaultFilters.isEmpty()) {
      defaultFiltersDescription.append(Messages.FiltersPage_No_Default_Filter);
    } else {
      Iterator<String> defaultFiltersListIterator = _defaultFilters.iterator();
      while (defaultFiltersListIterator.hasNext()) {
        defaultFiltersDescription.append(defaultFiltersListIterator.next() + (defaultFiltersListIterator.hasNext() ? "\n" : "")); //$NON-NLS-1$//$NON-NLS-2$
      }
    }
    // Create label.
    Label label = toolkit_p.createLabel(parentComposite_p, Messages.FiltersPage_DefaultFilter_Label, SWT.WRAP);
    label.setForeground(toolkit_p.getColors().getColor(IFormColors.TITLE));
    label.setLayoutData(new GridData(SWT.BEGINNING, SWT.FILL, false, false, getContainerColumnsCount(), 1));
    // Create styled text.
    StyledText styledText =
        FormHelper.createStyledText(toolkit_p, parentComposite_p, defaultFiltersDescription.toString(), SWT.WRAP | SWT.READ_ONLY | SWT.BORDER | SWT.V_SCROLL);
    // Limit size to avoid too big dialog.
    GridData textGridData = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
    PixelConverter converter = new PixelConverter(JFaceResources.getDialogFont());
    textGridData.widthHint = converter.convertWidthInCharsToPixels(70);
    textGridData.heightHint = converter.convertWidthInCharsToPixels(8);
    styledText.setLayoutData(textGridData);
    if (hasPreview()) {
      // Create preview Label
      Label previewLabel = toolkit_p.createLabel(parentComposite_p, Messages.FiltersPage_FilteringPreview_Label, SWT.WRAP);
      previewLabel.setForeground(toolkit_p.getColors().getColor(IFormColors.TITLE));
      previewLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.FILL, false, false, getContainerColumnsCount(), 1));
      _previewTree = toolkit_p.createTree(parentComposite_p, SWT.WRAP | SWT.BORDER);
      GridData previewGridData = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
      _previewTree.setLayoutData(previewGridData);
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.filesystem.ui.AbstractFileSystemPage#createLabelProvider()
   */
  @Override
  protected AbstractFileSystemCellLabelProvider createLabelProvider() {
    // Create a label provider with images of filter.
    return new AbstractFileSystemCellLabelProvider() {
      @Override
      public Image getErrorImage() {
        return EnvironmentActivator.getInstance().getImage("filters/error.gif");//$NON-NLS-1$
      }

      @Override
      public Image getImage() {
        return EnvironmentActivator.getInstance().getImage("filters/filter.gif");//$NON-NLS-1$
      }
    };
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.ui.AbstractEnvironmentPage#doCanFlipToNextPage()
   */
  @Override
  protected boolean doCanFlipToNextPage() {
    return false;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.filesystem.ui.AbstractFileSystemPage#getInitialValues(com.thalesgroup.orchestra.framework.environment.filesystem.FileSystemEnvironmentHandler)
   */
  @Override
  protected List<String> getInitialValues(FileSystemEnvironmentHandler handler_p) {
    return handler_p.getFiltersValues();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.ui.AbstractEnvironmentPage#getPageImageDescriptor()
   */
  @Override
  protected ImageDescriptor getPageImageDescriptor() {
    return EnvironmentActivator.getInstance().getImageDescriptor("filters/filter_wiz_ban.gif"); //$NON-NLS-1$
  }

  /**
   * @see com.thalesgroup.orchestra.framework.common.ui.wizards.AbstractFormsWizardPage#getPageTitle()
   */
  @Override
  protected String getPageTitle() {
    return Messages.FiltersPage_Title;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.filesystem.ui.AbstractFileSystemPage#getRawIntroductionText()
   */
  @Override
  protected String getRawIntroductionText() {
    return Messages.FiltersPage_IntroductionText;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.filesystem.ui.AbstractFileSystemPage#getValueComparator()
   */
  @Override
  protected Comparator<String> getValueComparator() {
    // To compare filters, use the not case case sensitive string comparator.
    return String.CASE_INSENSITIVE_ORDER;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.filesystem.ui.AbstractFileSystemPage#getValuesKey()
   */
  @Override
  protected String getValuesKey() {
    return FileSystemEnvironmentHandler.ATTRIBUTE_KEY_FILTERS;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.filesystem.ui.AbstractFileSystemPage#validateValue(java.lang.String)
   */
  @Override
  protected IStatus validateValue(String value_p) {
    // Apply early validation, so as to warn the user about potential errors.
    return getHandler().validateFilter(value_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.filesystem.ui.AbstractFileSystemPage#addButtons(org.eclipse.swt.widgets.Composite,
   *      org.eclipse.ui.forms.widgets.FormToolkit)
   */
  @Override
  protected List<Button> doAddExtraButtons(Composite composite_p, FormToolkit toolkit_p) {
    if (hasPreview()) {
      _previewButton = toolkit_p.createButton(composite_p, Messages.DirectoriesPickingPage_Button_Text_Preview, SWT.PUSH);
      SelectionListener previewListener = new SelectionAdapter() {
        @SuppressWarnings("synthetic-access")
        @Override
        public void widgetSelected(SelectionEvent e_p) {
          updatePreviewTree();
        }
      };
      _previewButton.addSelectionListener(previewListener);

      List<Button> result = new ArrayList<Button>();
      result.add(_previewButton);

      // Empty preview tree when adding or removing elements
      SelectionListener cleanListener = new SelectionAdapter() {
        /**
         * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
         */
        @Override
        public void widgetSelected(SelectionEvent e_p) {
          // Reset tree
          _previewTree.removeAll();
        }
      };
      _addButton.addSelectionListener(cleanListener);
      _removeButton.addSelectionListener(cleanListener);

      return result;
    }
    return super.doAddExtraButtons(composite_p, toolkit_p);
  }

  /**
   * Add filtered directory to tree
   * @param directory_p
   * @param filters_p
   */
  private void addFilteredDirectory(String directory_p, List<String> filters_p) {
    Path rootPath = Paths.get(directory_p);
    try {
      Files.walkFileTree(rootPath, new DirectoryTreeBuilderVisitor(rootPath, filters_p, _previewTree));
    } catch (IOException exception_p) {
      //
    }
  }

  /**
   * Update preview tree with respect to filters
   */
  private void updatePreviewTree() {
    // Save previous cursor
    Cursor origCursor = _previewTree.getCursor();
    // Create wait cursor and set it
    Cursor cursor = new Cursor(Display.getCurrent(), SWT.CURSOR_WAIT);
    getControl().setCursor(cursor);

    // Reset tree
    _previewTree.removeAll();

    // Fill tree
    List<String> filters = new ArrayList<String>();
    // Add default filters first
    filters.addAll(_defaultFilters);
    // Add user filters
    filters.addAll(_fileSystemHandler.getFiltersValues());
    for (String directory : _directories) {
      addFilteredDirectory(directory, filters);
    }

    // Restore previous cursor
    getControl().setCursor(origCursor);

    // Dispose wait cursor
    cursor.dispose();
  }

  /**
   * Enable/Disable filtering preview
   * @return true if enable, false otherwise
   */
  protected boolean hasPreview() {
    return true;
  }
}