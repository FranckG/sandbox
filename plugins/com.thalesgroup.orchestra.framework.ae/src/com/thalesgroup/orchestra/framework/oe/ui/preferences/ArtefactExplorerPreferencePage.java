/** 
 * <p>THALES Services - Engineering & Process Management</p>
 * <p>THALES Part Number 16 262 601</p>
 * <p>Copyright (c) 2002-2008 : THALES Services - Engineering & Process Management</p>
 */

package com.thalesgroup.orchestra.framework.oe.ui.preferences;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.thalesgroup.orchestra.framework.oe.OrchestraExplorerActivator;

/**
 * <p>
 * Title : ArtifactExplorerPreferencePage
 * </p>
 * <p>
 * Description : The class defines the preference page for the Artifact Explorer
 * </p>
 * @author Orchestra Framework Tool Suite developer
 * @version 3.7.0
 */
public class ArtefactExplorerPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {
  // Combo of various sort criterion
  private Combo _comboDefaultSortCriterion;
  private Combo _comboDefaultGroupCriterion;
  private Button _checkBoxInformationMessages;
  private Button _checkBoxWarningMessages;
  private Button _checkBoxErrorMessages;

  public ArtefactExplorerPreferencePage() {
    setDescription(Messages.ArtefactExplorerPreferencePage_OrchestraExplorerPreferences);
  }

  /**
   * @param parent
   * @param label
   * @return
   */
  public Button createCheckBox(Composite parent, String label) {
    Button button = new Button(parent, SWT.LEFT | SWT.CHECK);
    button.setText(label);
    GridData data = new GridData();
    button.setLayoutData(data);
    return button;
  }

  /**
   * Creates the contents for the combo box
   */
  @Override
  protected Control createContents(Composite parent) {
    Composite composite = new Composite(parent, 0);
    GridLayout layout = new GridLayout();
    layout.numColumns = 4;
    composite.setLayout(layout);
    GridData data = new GridData();
    data.verticalAlignment = 4;
    data.horizontalAlignment = 4;
    composite.setLayoutData(data);

    Label label = new Label(composite, SWT.LEFT);
    label.setText((new StringBuilder(Messages.ArtefactExplorerPreferencePage_DefaultSortCriterion)).append(": ").toString()); //$NON-NLS-1$
    data = new GridData();
    data.horizontalSpan = 2;
    data.horizontalAlignment = 4;
    label.setLayoutData(data);

    _comboDefaultSortCriterion = new Combo(composite, 8);
    data = new GridData();
    data.horizontalAlignment = 4;
    data.grabExcessHorizontalSpace = true;
    data.verticalAlignment = 2;
    data.grabExcessVerticalSpace = false;
    _comboDefaultSortCriterion.setLayoutData(data);

    label = new Label(composite, SWT.LEFT);
    label.setText((new StringBuilder(Messages.ArtefactExplorerPreferencePage_DefaultGroupByCriterion)).append(": ").toString()); //$NON-NLS-1$
    data = new GridData();
    data.horizontalSpan = 2;
    data.horizontalAlignment = 4;
    label.setLayoutData(data);

    _comboDefaultGroupCriterion = new Combo(composite, 8);
    data = new GridData();
    data.horizontalAlignment = 4;
    data.grabExcessHorizontalSpace = true;
    data.verticalAlignment = 2;
    data.grabExcessVerticalSpace = false;
    _comboDefaultGroupCriterion.setLayoutData(data);

    Group groupDisplayMessage = new Group(parent, SWT.LEFT);
    layout = new GridLayout();
    layout.numColumns = 1;
    groupDisplayMessage.setLayout(layout);
    data = new GridData();
    data.verticalAlignment = 4;
    data.horizontalAlignment = 4;
    groupDisplayMessage.setLayoutData(data);
    groupDisplayMessage.setText(Messages.ArtefactExplorerPreferencePage_DisplayPopup);

    _checkBoxInformationMessages = createCheckBox(groupDisplayMessage, Messages.ArtefactExplorerPreferencePage_InformationMessages);
    _checkBoxWarningMessages = createCheckBox(groupDisplayMessage, Messages.ArtefactExplorerPreferencePage_WaringMessages);
    _checkBoxErrorMessages = createCheckBox(groupDisplayMessage, Messages.ArtefactExplorerPreferencePage_ErrorMessages);
    initializeValues();
    return new Composite(parent, 0);
  }

  /**
   * Gets the PreferenceStore of the plugin
   */
  @Override
  protected IPreferenceStore doGetPreferenceStore() {
    return OrchestraExplorerActivator.getDefault().getPreferenceStore();
  }

  public void init(IWorkbench iworkbench) {
    // Nothing
  }

  /**
   * Initializes the preference page settings
   */
  private void initializeDefaults() {
    IPreferenceStore store = getPreferenceStore();
    _comboDefaultSortCriterion.setItems(PreferenceManager.getInstance().getDefaultSortCriterionValues());
    _comboDefaultSortCriterion.setText(store.getDefaultString(IPreferenceIDs.SORT_CRITERION_ID));
    _comboDefaultGroupCriterion.setItems(PreferenceManager.getInstance().getDefaultGroupCriterionValues());
    _comboDefaultGroupCriterion.setText(store.getDefaultString(IPreferenceIDs.GROUP_CRITERION_ID));
    _checkBoxInformationMessages.setSelection((store.getDefaultBoolean(IPreferenceIDs.DISPLAY_POPUP_INFORMATION_MESSAGES_ID)));
    _checkBoxWarningMessages.setSelection((store.getDefaultBoolean(IPreferenceIDs.DISPLAY_POPUP_WARNING_MESSAGES_ID)));
    _checkBoxErrorMessages.setSelection((store.getDefaultBoolean(IPreferenceIDs.DISPLAY_POPUP_ERROR_MESSAGES_ID)));
  }

  /**
   * Initializes the preference page values
   */
  private void initializeValues() {
    IPreferenceStore store = getPreferenceStore();
    _comboDefaultSortCriterion.setItems(PreferenceManager.getInstance().getDefaultSortCriterionValues());
    _comboDefaultSortCriterion.setText(store.getString(IPreferenceIDs.SORT_CRITERION_ID));
    _comboDefaultGroupCriterion.setItems(PreferenceManager.getInstance().getDefaultGroupCriterionValues());
    _comboDefaultGroupCriterion.setText(store.getString(IPreferenceIDs.GROUP_CRITERION_ID));
    _checkBoxInformationMessages.setSelection((store.getBoolean(IPreferenceIDs.DISPLAY_POPUP_INFORMATION_MESSAGES_ID)));
    _checkBoxWarningMessages.setSelection((store.getBoolean(IPreferenceIDs.DISPLAY_POPUP_WARNING_MESSAGES_ID)));
    _checkBoxErrorMessages.setSelection((store.getBoolean(IPreferenceIDs.DISPLAY_POPUP_ERROR_MESSAGES_ID)));
  }

  @Override
  protected void performDefaults() {
    super.performDefaults();
    initializeDefaults();
  }

  @SuppressWarnings("deprecation")
  @Override
  public boolean performOk() {
    storeValues();
    OrchestraExplorerActivator.getDefault().savePluginPreferences();
    return true;
  }

  /**
   * Stores the value of the sort criteria in the preference store
   */
  private void storeValues() {
    IPreferenceStore store = getPreferenceStore();
    store.setValue(IPreferenceIDs.SORT_CRITERION_ID, _comboDefaultSortCriterion.getText());
    store.setValue(IPreferenceIDs.GROUP_CRITERION_ID, _comboDefaultGroupCriterion.getText());
    store.setValue(IPreferenceIDs.DISPLAY_POPUP_INFORMATION_MESSAGES_ID, _checkBoxInformationMessages.getSelection());
    store.setValue(IPreferenceIDs.DISPLAY_POPUP_WARNING_MESSAGES_ID, _checkBoxWarningMessages.getSelection());
    store.setValue(IPreferenceIDs.DISPLAY_POPUP_ERROR_MESSAGES_ID, _checkBoxErrorMessages.getSelection());
  }
}