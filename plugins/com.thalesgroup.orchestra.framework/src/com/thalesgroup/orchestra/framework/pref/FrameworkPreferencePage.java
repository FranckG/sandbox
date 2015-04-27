/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.pref;

import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.pref.IPreferencesConstants;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper.LayoutType;

/**
 * Framework core preferences page.
 * @author t0076261
 */
public class FrameworkPreferencePage extends AbstractDefaultPreferencePage {
  /**
   * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
   */
  @Override
  protected void createFieldEditors() {
    // Validation level group.
    Group validationGroup =
        createGroup(Messages.FrameworkPreferencePage_ValidationGroup_Name, Messages.FrameworkPreferencePage_ValidationGroup_Tooltip, getFieldEditorParent());
    FormHelper.updateCompositeLayoutWithLayoutType(validationGroup, LayoutType.GRID_LAYOUT, 2, false);
    ((GridData) validationGroup.getLayoutData()).grabExcessVerticalSpace = false;
    {
      // Label.
      Label label = new Label(validationGroup, SWT.WRAP);
      label.setText(Messages.FrameworkPreferencePage_ValidationGroup_Levels_Label);
      GridData data = (GridData) FormHelper.updateControlLayoutDataWithLayoutTypeData(label, LayoutType.GRID_LAYOUT);
      data.grabExcessVerticalSpace = false;
      data.horizontalSpan = 2;
      // Level selection.
      String[][] levels =
          new String[][] { new String[] { Messages.FrameworkPreferencePage_ValidationGroup_Levels_Info, IPreferencesConstants.VALIDATION_LEVEL_INFO },
                          new String[] { Messages.FrameworkPreferencePage_ValidationGroup_Levels_Warning, IPreferencesConstants.VALIDATION_LEVEL_WARNING },
                          new String[] { Messages.FrameworkPreferencePage_ValidationGroup_Levels_Error, IPreferencesConstants.VALIDATION_LEVEL_ERROR } };
      ComboFieldEditor validationLevels =
          new ComboFieldEditor(IPreferencesConstants.VALIDATION_LEVEL, Messages.FrameworkPreferencePage_ValidationGroup_Levels_LevelSelectionLabel, levels,
              validationGroup);

      addField(validationLevels);
    }
  }

  /**
   * @see org.eclipse.jface.preference.PreferencePage#doGetPreferenceStore()
   */
  @Override
  protected IPreferenceStore doGetPreferenceStore() {
    return ModelHandlerActivator.getDefault().getPreferenceStore();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.pref.AbstractDefaultPreferencePage#getPageDescription()
   */
  @Override
  protected String getPageDescription() {
    return Messages.FrameworkPreferencePage_ValidationGroup_Description;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.pref.AbstractDefaultPreferencePage#getPageTitle()
   */
  @Override
  protected String getPageTitle() {
    return getPageDescription();
  }
}