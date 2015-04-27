/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment.filesystem.ui;

import java.util.List;

import com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizard;

/**
 * @author s0040806
 */
public class DirectorySelectionDialog extends AbstractFormsWizard {

  private DirectorySelectionPage _selectionPage;

  /**
   * Root path for directory selection
   */
  private String _rootPath;

  /**
   * 
   */
  public DirectorySelectionDialog(String rootPath_p) {
    super();
    _rootPath = rootPath_p;
    setWindowTitle("Select directories to include");
    setNeedsProgressMonitor(false);
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#performFinish()
   */
  @Override
  public boolean performFinish() {
    return true;
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#addPages()
   */
  @Override
  public void addPages() {
    _selectionPage = new DirectorySelectionPage(_rootPath);
    addPage(_selectionPage);
  }

  public List<String> getSelectedDirectories() {
    return _selectionPage.getSelectedDirectories();
  }
}
