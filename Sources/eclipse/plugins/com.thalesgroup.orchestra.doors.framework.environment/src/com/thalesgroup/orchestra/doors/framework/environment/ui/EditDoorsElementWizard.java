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
public class EditDoorsElementWizard extends AbstractFormsWizard {
  /**
   * 
   */
  protected final EditDoorsElementWizardPage _editDoorsElementWizardPage;

  protected String _rawDoorsElementName;

  protected static IVariablesHandler _variablesHandler;

  /**
 * 
 */
  public EditDoorsElementWizard(String wizardTitle_p, String defaultDoorsName_p, IVariablesHandler variablesHandler_p) {
    setWindowTitle(wizardTitle_p);
    _variablesHandler = variablesHandler_p;
    _editDoorsElementWizardPage = new EditDoorsElementWizardPage(defaultDoorsName_p);
    addPage(_editDoorsElementWizardPage);
  }

  public String getRawDoorsElementName() {
    return _rawDoorsElementName;
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#performFinish()
   */
  @Override
  public boolean performFinish() {
    _rawDoorsElementName = _editDoorsElementWizardPage.getRawDoorsElementName();
    return true;
  }

  protected static class EditDoorsElementWizardPage extends AbstractFormsWizardPage {
    /**
     * Variable reference prefix.
     */
    public static final String VARIABLE_REFERENCE_PREFIX = "${"; //$NON-NLS-1$

    /**
     * Initial Doors element name.
     */
    protected final String _initialDoorsElementName;

    /**
     * Doors element name text field.
     */
    protected Text _rawDoorsElementNameText;

    /**
     * Constructor.
     */
    public EditDoorsElementWizardPage(String initialDoorsName_p) {
      super("EditDoorsElementWizardPage"); //$NON-NLS-1$
      _initialDoorsElementName = initialDoorsName_p;
    }

    /**
     * @see com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage#doCreateControl(org.eclipse.swt.widgets.Composite,
     *      org.eclipse.ui.forms.widgets.FormToolkit)
     */
    @Override
    protected Composite doCreateControl(Composite parent_p, FormToolkit toolkit_p) {
      // Main component.
      Composite composite = FormHelper.createCompositeWithLayoutType(toolkit_p, parent_p, LayoutType.GRID_LAYOUT, 3, false);
      //
      // Doors element name fields.
      //
      Label emptyLineLabel = new Label(composite, SWT.NONE);
      emptyLineLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
      _rawDoorsElementNameText =
          (Text) FormHelper.createLabelAndText(toolkit_p, composite, Messages.DoorsEnvironment_Label_DoorsName, ICommonConstants.EMPTY_STRING, SWT.NONE).get(
              Text.class);
      _rawDoorsElementNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
      Button doorsElementNameVariableButton =
          _variablesHandler.createVariablesButton(toolkit_p, composite, new VariableReferenceHandler(_rawDoorsElementNameText));
      doorsElementNameVariableButton.setLayoutData(new GridData());
      final Text substitutedDoorsElementNameText =
          (Text) FormHelper.createLabelAndText(toolkit_p, composite, Messages.DoorsEnvironmentConnectionDataPage_Label_SubstituedValue,
              ICommonConstants.EMPTY_STRING, SWT.READ_ONLY).get(Text.class);
      substitutedDoorsElementNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
      substitutedDoorsElementNameText.setBackground(toolkit_p.getColors().getBackground());
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
          }
          // Update page state.
          setPageComplete(validatePage());
        }
      };
      _rawDoorsElementNameText.addModifyListener(rawTextModifyListener);

      // Set initial values.
      _rawDoorsElementNameText.setText(_initialDoorsElementName);

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
      return null;
    }

    public String getRawDoorsElementName() {
      if (null != _rawDoorsElementNameText) {
        return _rawDoorsElementNameText.getText();
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
        setMessage(Messages.EditDoorsElementNameWizard_0, NONE);
        return false;
      }
      if (rawDoorsServerValue.contains(VARIABLE_REFERENCE_PREFIX)) {
        String substitutedDoorsServerValue = _variablesHandler.getSubstitutedValue(rawDoorsServerValue);
        // Emptiness after substitution.
        if (ICommonConstants.EMPTY_STRING.equals(substitutedDoorsServerValue)) {
          setMessage(Messages.EditDoorsElementNameWizard_1, ERROR);
          return false;
        }
        // If substitution failed, the raw string is returned.
        if (rawDoorsServerValue.equals(substitutedDoorsServerValue)) {
          setMessage(Messages.EditDoorsElementNameWizard_2, ERROR);
          return false;
        }
      }
      setMessage(null);
      return true;
    }

  }

}
