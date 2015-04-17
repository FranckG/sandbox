/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.doors.framework.environment.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.doors.framework.environment.DoorsActivator;
import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.environment.ui.IVariablesHandler;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper.LayoutType;
import com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizard;
import com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage;

/**
 * @author T0052089
 */
public class EditDoorsProjectWizard extends AbstractFormsWizard {
  public static final String DOORS_PROJECT_ID_PATTERN = "[0-9a-fA-F]{1,8}"; //$NON-NLS-1$
  /**
   * 
   */
  protected final EditDoorsProjectWizardPage _editDoorsProjectWizardPage;

  protected String _rawDoorsProjectId;

  protected String _rawDoorsProjectName;

  protected static IVariablesHandler _variablesHandler;

  /**
 * 
 */
  public EditDoorsProjectWizard(String wizardTitle_p, String defaultDoorsName_p, String defaultDoorsProjectId_p, IVariablesHandler variablesHandler_p) {
    setWindowTitle(wizardTitle_p);
    _variablesHandler = variablesHandler_p;
    _editDoorsProjectWizardPage = new EditDoorsProjectWizardPage(defaultDoorsName_p, defaultDoorsProjectId_p);
    addPage(_editDoorsProjectWizardPage);
  }

  public String getRawDoorsElementName() {
    return _rawDoorsProjectName;
  }

  public String getRawDoorsProjectId() {
    return _rawDoorsProjectId;
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#performFinish()
   */
  @Override
  public boolean performFinish() {
    _rawDoorsProjectName = _editDoorsProjectWizardPage.getRawDoorsElementName();
    _rawDoorsProjectId = _editDoorsProjectWizardPage.getRawDoorsProjectId();
    return true;
  }

  protected static class EditDoorsProjectWizardPage extends AbstractFormsWizardPage {
    /**
     * Variable reference prefix.
     */
    public static final String VARIABLE_REFERENCE_PREFIX = "${"; //$NON-NLS-1$

    /**
     * Initial Doors element name.
     */
    protected final String _initialDoorsElementName;

    /**
     * Initial Doors element URI.
     */
    protected final String _initialDoorsProjectId;

    /**
     * Doors element name text field.
     */
    protected Text _rawDoorsElementNameText;

    /**
     * Doors element URI text field.
     */
    protected Text _rawDoorsProjectIdText;

    /**
     * Constructor.
     */
    public EditDoorsProjectWizardPage(String initialDoorsName_p, String initialDoorsProjectId_p) {
      super("EditDoorsElementWizardPage"); //$NON-NLS-1$
      _initialDoorsElementName = initialDoorsName_p;
      _initialDoorsProjectId = initialDoorsProjectId_p;
    }

    /**
     * @see com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage#doCreateControl(org.eclipse.swt.widgets.Composite,
     *      org.eclipse.ui.forms.widgets.FormToolkit)
     */
    @Override
    protected Composite doCreateControl(Composite parent_p, FormToolkit toolkit_p) {
      IVariablesHandler variablesHandler = _variablesHandler;
      // Main component.
      Composite composite = FormHelper.createCompositeWithLayoutType(toolkit_p, parent_p, LayoutType.GRID_LAYOUT, 3, false);
      //
      // Doors element name fields.
      //
      Label emptyLineLabel = new Label(composite, SWT.NONE);
      emptyLineLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
      _rawDoorsElementNameText =
          (Text) FormHelper.createLabelAndText(toolkit_p, composite, Messages.DoorsEnvironment_Label_DoorsName, ICommonConstants.EMPTY_STRING, SWT.BORDER).get(
              Text.class);
      _rawDoorsElementNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
      Button doorsElementNameVariableButton =
          variablesHandler.createVariablesButton(toolkit_p, composite, new VariableReferenceHandler(_rawDoorsElementNameText));
      doorsElementNameVariableButton.setLayoutData(new GridData());
      final Text substitutedDoorsElementNameText =
          (Text) FormHelper.createLabelAndText(toolkit_p, composite, Messages.DoorsEnvironmentConnectionDataPage_Label_SubstituedValue,
              ICommonConstants.EMPTY_STRING, SWT.READ_ONLY).get(Text.class);
      substitutedDoorsElementNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
      substitutedDoorsElementNameText.setBackground(toolkit_p.getColors().getBackground());
      // Padding label.
      new Label(composite, SWT.NONE);
      //
      // Doors element URI fields.
      //
      Label emptyLineLabel2 = new Label(composite, SWT.NONE);
      emptyLineLabel2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
      _rawDoorsProjectIdText =
          (Text) FormHelper.createLabelAndText(toolkit_p, composite, Messages.EditDoorsProjectWizard_0, ICommonConstants.EMPTY_STRING, SWT.BORDER).get(
              Text.class);
      _rawDoorsProjectIdText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
      Button doorsElementURIVariableButton = variablesHandler.createVariablesButton(toolkit_p, composite, new VariableReferenceHandler(_rawDoorsProjectIdText));
      doorsElementURIVariableButton.setLayoutData(new GridData());
      final Text substitutedDoorsElementURIText =
          (Text) FormHelper.createLabelAndText(toolkit_p, composite, Messages.DoorsEnvironmentConnectionDataPage_Label_SubstituedValue,
              ICommonConstants.EMPTY_STRING, SWT.READ_ONLY).get(Text.class);
      substitutedDoorsElementURIText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
      substitutedDoorsElementURIText.setBackground(toolkit_p.getColors().getBackground());
      // Padding label.
      new Label(composite, SWT.NONE);

      // Set listeners.
      ModifyListener rawTextModifyListener = new ModifyListener() {
        /**
         * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
         */
        public void modifyText(ModifyEvent event_p) {
          final IVariablesHandler localVariablesHandler = _variablesHandler;
          if (event_p.getSource() == _rawDoorsElementNameText) {
            substitutedDoorsElementNameText.setText(localVariablesHandler.getSubstitutedValue(_rawDoorsElementNameText.getText()));
          } else if (event_p.getSource() == _rawDoorsProjectIdText) {
            substitutedDoorsElementURIText.setText(localVariablesHandler.getSubstitutedValue(_rawDoorsProjectIdText.getText()));
          }
          // Update page state.
          setPageComplete(validatePage());
        }
      };
      _rawDoorsElementNameText.addModifyListener(rawTextModifyListener);
      _rawDoorsProjectIdText.addModifyListener(rawTextModifyListener);

      // Set initial values.
      _rawDoorsElementNameText.setText(_initialDoorsElementName);
      _rawDoorsProjectIdText.setText(_initialDoorsProjectId);

      // Set tab traversal order (Name -> URI).
      composite.setTabList(new Control[] { _rawDoorsElementNameText, _rawDoorsProjectIdText });

      return composite;
    }

    /**
     * @see com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage#getPageImageDescriptor()
     */
    @Override
    protected ImageDescriptor getPageImageDescriptor() {
      return DoorsActivator.getInstance().getImageDescriptor("doors_big.gif"); //$NON-NLS-1$
    }

    /**
     * @see com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage#getPageTitle()
     */
    @Override
    protected String getPageTitle() {
      return Messages.DoorsEnvironment_Create_Or_Edit_Doors_Project;
    }

    public String getRawDoorsElementName() {
      if (null != _rawDoorsElementNameText) {
        return _rawDoorsElementNameText.getText();
      }
      return null;
    }

    public String getRawDoorsProjectId() {
      if (null != _rawDoorsProjectIdText) {
        return _rawDoorsProjectIdText.getText();
      }
      return null;
    }

    /**
     * Determines when the next button is available, i.e. when a value has been entered for the port and for the name
     * @see com.thalesgroup.orchestra.framework.environment.ui.AbstractEnvironmentPage#doCanFlipToNextPage()
     */
    protected boolean validatePage() {
      //
      // Validate Doors sever.
      //
      String rawDoorsServerValue = _rawDoorsElementNameText.getText();
      // Emptiness.
      if (ICommonConstants.EMPTY_STRING.equals(rawDoorsServerValue)) {
        setMessage(Messages.EditDoorsProjectWizard_1, NONE);
        return false;
      }
      if (rawDoorsServerValue.contains(VARIABLE_REFERENCE_PREFIX)) {
        String substitutedDoorsServerValue = _variablesHandler.getSubstitutedValue(rawDoorsServerValue);
        // Emptiness after substitution.
        if (ICommonConstants.EMPTY_STRING.equals(substitutedDoorsServerValue)) {
          setMessage(Messages.EditDoorsProjectWizard_2, ERROR);
          return false;
        }
        // If substitution failed, the raw string is returned.
        if (rawDoorsServerValue.equals(substitutedDoorsServerValue)) {
          setMessage(Messages.EditDoorsProjectWizard_3, ERROR);
          return false;
        }
      }
      //
      // Validate Doors port.
      //
      String rawDoorsProjectIdValue = _rawDoorsProjectIdText.getText();
      // Emptiness.
      if (ICommonConstants.EMPTY_STRING.equals(rawDoorsProjectIdValue)) {
        setMessage(Messages.EditDoorsProjectWizard_4, NONE);
        return false;
      }
      String substitutedDoorsProjectIdValue = rawDoorsProjectIdValue;
      if (rawDoorsProjectIdValue.contains(VARIABLE_REFERENCE_PREFIX)) {
        substitutedDoorsProjectIdValue = _variablesHandler.getSubstitutedValue(rawDoorsProjectIdValue);
        // Emptiness after substitution.
        if (ICommonConstants.EMPTY_STRING.equals(substitutedDoorsProjectIdValue)) {
          setMessage(Messages.EditDoorsProjectWizard_5, ERROR);
          return false;
        }
        // If substitution failed, the raw string is returned.
        if (rawDoorsProjectIdValue.equals(substitutedDoorsProjectIdValue)) {
          setMessage(Messages.EditDoorsProjectWizard_6, ERROR);
          return false;
        }
      }
      // DOORS project ID must be an hexadecimal number, with 8 digits max.
      if (!substitutedDoorsProjectIdValue.matches(DOORS_PROJECT_ID_PATTERN)) {
        setMessage(Messages.EditDoorsProjectWizard_7, ERROR);
        return false;
      }
      // Everything is OK -> clear the message.
      setMessage(null);
      return true;
    }
  }

}
