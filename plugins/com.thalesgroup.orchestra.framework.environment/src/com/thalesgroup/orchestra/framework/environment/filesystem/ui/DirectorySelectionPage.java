/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment.filesystem.ui;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.environment.EnvironmentActivator;
import com.thalesgroup.orchestra.framework.lib.base.utils.OrchestraFileFilter;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;
import com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage;

/**
 * @author s0040806
 */
public class DirectorySelectionPage extends AbstractFormsWizardPage {
  /**
   * Error decoration used to fill the text field decorations.
   */
  protected static final Image ERROR_DECORATION = FieldDecorationRegistry.getDefault().getFieldDecoration(FieldDecorationRegistry.DEC_ERROR).getImage();

  private static final Image FOLDER = getImage("directories/folder.gif"); //$NON-NLS-1$

  /**
   * Filter text
   */
  private Text _filterText;
  /**
   * Selected directories visualiser
   */
  private Tree _selectedTree;
  /**
   * Bold font for emphasising selected directories
   */
  private Font _boldFont;
  /**
   * List of selected directories
   */
  private List<Path> _directories;
  /**
   * Root path for directory selection
   */
  private Path _rootPath;

  /**
   * @param rootPath_p
   */
  public DirectorySelectionPage(String rootPath_p) {
    this("DirectoryFilteringPage", rootPath_p);
  }

  /**
   * @param pageId_p
   */
  public DirectorySelectionPage(String pageId_p, String rootPath_p) {
    super(pageId_p);
    _rootPath = Paths.get(rootPath_p);
    _directories = new ArrayList<Path>();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage#doCreateControl(org.eclipse.swt.widgets.Composite,
   *      org.eclipse.ui.forms.widgets.FormToolkit)
   */
  @Override
  protected Composite doCreateControl(Composite parent_p, FormToolkit toolkit_p) {
    Composite composite = new Composite(parent_p, SWT.NONE);
    GridLayout layout = new GridLayout(2, false);
    layout.verticalSpacing = 15;
    composite.setLayout(layout);

    StyledText styledText = FormHelper.createStyledText(toolkit_p, composite, Messages.DirectorySelectionPage_IntroductionText, SWT.WRAP | SWT.READ_ONLY);
    GridData styledTextData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
    styledTextData.horizontalSpan = 2;
    styledText.setLayoutData(styledTextData);

    Map<Class, Widget> filterFields = FormHelper.createLabelAndText(toolkit_p, composite, Messages.DirectorySelectionPage_Filter_Label, "", SWT.BORDER);
    _filterText = (Text) filterFields.get(Text.class);
    Label filterLabel = (Label) filterFields.get(Label.class);
    GridData labelGridData = new GridData(SWT.FILL, SWT.BEGINNING, false, false);
    filterLabel.setLayoutData(labelGridData);

    GridData textGridData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
    textGridData.horizontalIndent = 10;
    _filterText.setLayoutData(textGridData);

    FontData fontData = _filterText.getFont().getFontData()[0];
    _boldFont = JFaceResources.getFontRegistry().getBold(fontData.getName());

    final ControlDecoration filterTextDecorator = new ControlDecoration(_filterText, SWT.LEFT | SWT.TOP);
    filterTextDecorator.setImage(ERROR_DECORATION);
    ISWTObservableValue observeProjectText = SWTObservables.observeDelayedValue(1000, SWTObservables.observeText(_filterText, SWT.Modify));
    observeProjectText.addValueChangeListener(new IValueChangeListener() {
      /**
       * @see org.eclipse.core.databinding.observable.value.IValueChangeListener#handleValueChange(org.eclipse.core.databinding.observable.value.ValueChangeEvent)
       */
      @SuppressWarnings("synthetic-access")
      public void handleValueChange(ValueChangeEvent event_p) {
        if (isFilterValid()) {
          filterTextDecorator.hide();
          updateSelectedDirectoriesTree();
        } else {
          filterTextDecorator.setDescriptionText(Messages.DirectorySelectionPage_ErrorMessage_InvalidFilter);
          filterTextDecorator.show();
          if (_filterText.getText().equals("")) {
            buildUnselectedDirectoriesTree();
          }
        }
        updateButtons();
      }
    });

    _selectedTree = toolkit_p.createTree(composite, SWT.WRAP | SWT.BORDER);
    GridData selectedTreeData = new GridData(SWT.FILL, SWT.FILL, true, true);
    selectedTreeData.horizontalSpan = 2;
    _selectedTree.setLayoutData(selectedTreeData);

    /**
     * Display whole directory tree when filter is empty
     */
    buildUnselectedDirectoriesTree();

    return composite;
  }

  /**
   * Check whether filter is valid or not
   * @return
   */
  private boolean isFilterValid() {
    String filter = _filterText.getText();
    if (filter.equals("")) { //$NON-NLS-1$
      return false;
    }
    try {
      FileSystems.getDefault().getPathMatcher("glob:" + filter); //$NON-NLS-1$
      return true;
    } catch (Exception exception_p) {
      return false;
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage#getPageImageDescriptor()
   */
  @Override
  protected ImageDescriptor getPageImageDescriptor() {
    return EnvironmentActivator.getInstance().getImageDescriptor("filters/filter_wiz_ban.gif"); //$NON-NLS-1$
  }

  /**
   * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
   */
  @Override
  public boolean isPageComplete() {
    return !_directories.isEmpty();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage#getPageTitle()
   */
  @Override
  protected String getPageTitle() {
    return Messages.DirectorySelectionPage_Title;
  }

  /**
   * Add filtered directories to directory list
   * @param directory_p
   * @param filters_p
   */
  private void findSelectedDirectories() {
    _directories.clear();
    final OrchestraFileFilter filter = new OrchestraFileFilter(_rootPath, _filterText.getText());
    try {
      Files.walkFileTree(_rootPath, new SimpleFileVisitor<Path>() {

        /**
         * @see java.nio.file.SimpleFileVisitor#preVisitDirectory(java.lang.Object, java.nio.file.attribute.BasicFileAttributes)
         */
        @Override
        @SuppressWarnings("synthetic-access")
        public FileVisitResult preVisitDirectory(Path dir_p, BasicFileAttributes arg1_p) throws IOException {
          if (filter.matchesDirectory(dir_p)) {
            _directories.add(dir_p);
            return FileVisitResult.SKIP_SUBTREE;
          }
          return FileVisitResult.CONTINUE;
        }
      });
    } catch (IOException exception_p) {
      //
    }
  }

  /**
   * Update tree with respect to current selection
   */
  private void updateSelectedDirectoriesTree() {
    // Clean tree
    _selectedTree.removeAll();
    findSelectedDirectories();
    buildSelectedDirectoriesTree();
  }

  /**
   * Add new subpath to tree
   * @param path_p
   * @param rootItem_p
   * @param level
   */
  private void addNewSubpath(Path path_p, TreeItem rootItem_p, int level) {
    TreeItem parent = rootItem_p;
    int len = path_p.getNameCount();
    for (int i = level; i < len; i++) {
      TreeItem item = new TreeItem(parent, 0);
      item.setText(path_p.getName(i).toString());
      item.setImage(FOLDER);
      parent = item;
    }
    // Set tree leaf font to bold
    parent.setFont(_boldFont);
  }

  /**
   * Add subpath to tree
   * @param path_p
   * @param rootItem_p
   * @param level
   */
  private void addSubpathToTree(Path path_p, TreeItem rootItem_p, int level) {
    // Get current node children
    TreeItem[] children = rootItem_p.getItems();

    // Find if there is already a child node with the current level name
    String name = path_p.getName(level).toString();
    TreeItem child = null;
    for (int i = 0; i < children.length; i++) {
      if (children[i].getText().equals(name)) {
        child = children[i];
        break;
      }
    }
    if (null == child) {
      addNewSubpath(path_p, rootItem_p, level);
    } else {
      int maxLevel = path_p.getNameCount() - 1;
      if (level < maxLevel) {
        addSubpathToTree(path_p, child, level + 1);
      }
    }
  }

  /**
   * Build tree from selected directories
   */
  private void buildSelectedDirectoriesTree() {
    if (!_directories.isEmpty()) {
      // Add top level directory
      TreeItem rootItem = new TreeItem(_selectedTree, 0);
      rootItem.setText(_rootPath.toString());
      rootItem.setImage(FOLDER);

      for (Path path : _directories) {
        Path relative = _rootPath.relativize(path);
        addSubpathToTree(relative, rootItem, 0);
      }

      expandTree(rootItem);
      _selectedTree.setRedraw(true);
      _selectedTree.redraw();
    } else {
      // buildUnfilteredTree();
    }
  }

  /**
   * Build whole tree from unselected directories when filter is empty
   */
  private void buildUnselectedDirectoriesTree() {
    // Clean tree
    _directories.clear();
    _selectedTree.removeAll();
    // Add top level directory
    try {
      Files.walkFileTree(_rootPath, new DirectoryTreeBuilderVisitor(_rootPath, null, _selectedTree));
    } catch (IOException exception_p) {
      //
    }
  }

  /**
   * Return selected directories as a string list
   * @return
   */
  public List<String> getSelectedDirectories() {
    List<String> list = new ArrayList<String>();
    for (Path directory : _directories) {
      list.add(directory.toString());
    }
    return list;
  }

  /**
   * Get image from resources
   * @param file_p
   * @return
   */
  private static Image getImage(String file_p) {
    return EnvironmentActivator.getInstance().getImageDescriptor(file_p).createImage();
  }

  /**
   * Update buttons with respect to selected directories
   */
  private void updateButtons() {
    if (_directories.isEmpty()) {
      setPageComplete(false);
    } else {
      setPageComplete(true);
    }
  }

  /**
   * Expand tree
   * @param parent Root tree
   */
  private void expandTree(TreeItem parent) {
    parent.setExpanded(true);
    for (TreeItem item : parent.getItems()) {
      expandTree(item);
    }
  }
}
