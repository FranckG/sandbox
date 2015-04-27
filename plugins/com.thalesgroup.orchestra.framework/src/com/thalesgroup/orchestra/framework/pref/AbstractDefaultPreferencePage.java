/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.pref;

import java.io.IOException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPersistentPreferenceStore;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * @author t0076333
 */
public abstract class AbstractDefaultPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
  /**
   * Constructor.
   */
  public AbstractDefaultPreferencePage() {
    super(GRID);
    setTitle(getPageTitle());
    setDescription(getPageDescription());
  }

  /**
   * Create a new group in the page.
   * @param label_p
   * @param tooltip_p
   * @param parent_p
   * @return
   */
  protected Group createGroup(String label_p, String tooltip_p, Composite parent_p) {
    Group group = new Group(parent_p, SWT.NONE);
    group.setText(label_p);
    group.setToolTipText(tooltip_p);
    GridData gridData = new GridData();
    gridData.horizontalAlignment = GridData.FILL;
    gridData.grabExcessHorizontalSpace = true;
    gridData.verticalAlignment = GridData.CENTER;
    gridData.grabExcessVerticalSpace = false;
    group.setLayoutData(gridData);
    return group;
  }

  /**
   * Get the description of this page
   * @return
   */
  protected abstract String getPageDescription();

  /**
   * Get the title of this page
   * @return
   */
  protected abstract String getPageTitle();

  /**
   * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
   */
  public void init(IWorkbench workbench_p) {
    // Nothing to do.
  }

  /**
   * @see org.eclipse.jface.preference.FieldEditorPreferencePage#performOk()
   */
  @Override
  public boolean performOk() {
    boolean ok = super.performOk();
    IPreferenceStore store = getPreferenceStore();
    if (store instanceof IPersistentPreferenceStore) {
      try {
        ((IPersistentPreferenceStore) store).save();
      } catch (IOException e) {
        MessageDialog.openError(getShell(), Messages.AbstractDefaultPreferencePage_Error_UnableToSavePreferences, e.getMessage());
        return false;
      }
    }
    return ok;
  }
}
