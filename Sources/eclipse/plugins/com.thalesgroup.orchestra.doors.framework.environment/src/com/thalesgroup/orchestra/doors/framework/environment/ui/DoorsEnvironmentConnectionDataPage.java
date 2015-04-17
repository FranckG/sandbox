/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.doors.framework.environment.ui;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.doors.framework.environment.DoorsActivator;
import com.thalesgroup.orchestra.doors.framework.environment.DoorsEnvironment;
import com.thalesgroup.orchestra.doors.framework.environment.DoorsEnvironmentHandler;
import com.thalesgroup.orchestra.framework.common.CommonActivator;
import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.environment.EnvironmentActivator;
import com.thalesgroup.orchestra.framework.environment.ui.AbstractEnvironmentPage;
import com.thalesgroup.orchestra.framework.environment.ui.IVariablesHandler;
import com.thalesgroup.orchestra.framework.exchange.status.SeverityType;
import com.thalesgroup.orchestra.framework.exchange.status.Status;
import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper.LayoutType;

/**
 * @author S0032874 <br>
 *         This implementation of AbstractEnvironmentPage is a page allowing to edit the name and port values
 */
public class DoorsEnvironmentConnectionDataPage extends AbstractEnvironmentPage {
  /**
   * Default value of the launch arguments
   */
  private static final String DOORS_LAUNCH_ARGUMENTS = "${odm:\\Orchestra\\Connectors\\Doors\\LaunchArguments}"; //$NON-NLS-1$

  /**
   * Variable reference prefix.
   */
  public static final String VARIABLE_REFERENCE_PREFIX = "${"; //$NON-NLS-1$

  /**
   * Default value of the name
   */
  private final String _defaultName;

  /**
   * Default value of the port
   */
  private final String _defaultPort;

  /**
   * Default value of the autoLogin
   */
  private final String _autoLogin;

  /**
   * DoorsEnvironment handler
   */
  protected DoorsEnvironmentHandler _doorsEnvironmentHandler;

  /**
   * Optional parameters for Doors launch
   */
  protected String _DoorsParameters;

  /**
   * Text field to enter the port of the Doors database
   */
  protected Text _rawDoorsPortText;

  /**
   * Text field to enter the name of the Doors database
   */
  protected Text _rawDoorsServerText;

  /**
   * Button check to select autologin option
   */
  protected Button _Autologin;

  /**
   * Constructor
   * @param handler_p DoorsEnvironmentHandler containing the attributes of the environment
   */
  @SuppressWarnings("nls")
  public DoorsEnvironmentConnectionDataPage(DoorsEnvironmentHandler handler_p) {
    super("DoorsEnvironmentConnectionDataPage", handler_p);//$NON-NLS-1$
    _doorsEnvironmentHandler = handler_p;
    _defaultName = handler_p.getDataBaseName();
    _defaultPort = handler_p.getDataBasePort();
    if (_defaultName.equals("") && _defaultPort.equals("")) {
      _autoLogin = "false";
      _DoorsParameters = DOORS_LAUNCH_ARGUMENTS;
    } else {
      _autoLogin = handler_p.getAutoLoginValue();
      _DoorsParameters = handler_p.getOptionalParameters();
    }
  }

  /**
   * Add the Advanced Options button.
   */
  public Button createAdvancedOptionsButton(final FormToolkit toolkit_p, Composite buttonComposite_p, final IVariablesHandler variablesHandler_p,
      final StringBuilder referencingValue) {

    Button button = FormHelper.createButton(toolkit_p, buttonComposite_p, Messages.DoorsEnvironmentAdvancedOptions_Button_Text, SWT.PUSH);
    button.setToolTipText(Messages.DoorsEnvironmentAdvancedOptions_Button_ToolTip);
    button.addSelectionListener(new SelectionAdapter() {
      /**
       * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
       */
      @Override
      public void widgetSelected(SelectionEvent e_p) {
        final Display display = PlatformUI.getWorkbench().getDisplay();
        AdvancedOptionsDialog dialog =
            new AdvancedOptionsDialog(e_p.display.getActiveShell(), Messages.DoorsEnvironmentAdvancedOptions_Dialog_Title, null,
                Messages.DoorsEnvironmentAdvancedOptions_Dialog_Message, MessageDialog.NONE, new String[] { IDialogConstants.OK_LABEL,
                                                                                                           IDialogConstants.CANCEL_LABEL }, 0, display,
                toolkit_p, variablesHandler_p, referencingValue, _doorsEnvironmentHandler, _DoorsParameters);
        int returnCode = dialog.open();
        if (0 == returnCode) {
          _DoorsParameters = referencingValue.toString();
          Map<String, String> result = new HashMap<String, String>();
          result.put(DoorsEnvironment.ATTRIBUT_KEY_PARAMETERS, OrchestraURI.encode(_DoorsParameters));
          _doorsEnvironmentHandler.updateAttributes(result);
        }
      }
    });
    GridData data = (GridData) FormHelper.updateControlLayoutDataWithLayoutTypeData(button, LayoutType.GRID_LAYOUT);
    data.grabExcessVerticalSpace = false;
    return button;
  }

  /**
   * Determines when the next button is available, i.e. when a value has been entered for the port and for the name
   * @see com.thalesgroup.orchestra.framework.environment.ui.AbstractEnvironmentPage#doCanFlipToNextPage()
   */
  @Override
  protected boolean doCanFlipToNextPage() {
    //
    // Validate Doors sever.
    //
    String rawDoorsServerValue = _rawDoorsServerText.getText();
    // Emptiness.
    if (ICommonConstants.EMPTY_STRING.equals(rawDoorsServerValue)) {
      setMessage(Messages.DoorsEnvironmentConnectionDataPage_Message_EnterADoorsServer, NONE);
      return false;
    }
    if (rawDoorsServerValue.contains(VARIABLE_REFERENCE_PREFIX)) {
      String substitutedDoorsServerValue = _handler.getVariablesHandler().getSubstitutedValue(rawDoorsServerValue);
      // Emptiness after substitution.
      if (ICommonConstants.EMPTY_STRING.equals(substitutedDoorsServerValue)) {
        setMessage(Messages.DoorsEnvironmentConnectionDataPage_ErrorMessage_DoorsServerIsEmpty, ERROR);
        return false;
      }
      // If substitution failed, the raw string is returned.
      if (rawDoorsServerValue.equals(substitutedDoorsServerValue)) {
        setMessage(Messages.DoorsEnvironmentConnectionDataPage_ErrorMessage_DoorsServerNotResolvedVariableReference, ERROR);
        return false;
      }
    }
    //
    // Validate Doors port.
    //
    String rawDoorsPortValue = _rawDoorsPortText.getText();
    // Emptiness.
    if (ICommonConstants.EMPTY_STRING.equals(rawDoorsPortValue)) {
      setMessage(Messages.DoorsEnvironmentConnectionDataPage_Message_EnterADoorsPort, NONE);
      return false;
    }
    String substitutedDoorsPortValue = rawDoorsPortValue;
    if (rawDoorsPortValue.contains(VARIABLE_REFERENCE_PREFIX)) {
      substitutedDoorsPortValue = _handler.getVariablesHandler().getSubstitutedValue(rawDoorsPortValue);
      // Emptiness after substitution.
      if (ICommonConstants.EMPTY_STRING.equals(substitutedDoorsPortValue)) {
        setMessage(Messages.DoorsEnvironmentConnectionDataPage_ErrorMessage_DoorsPortIsEmpty, ERROR);
        return false;
      }
      // If substitution failed, the raw string is returned.
      if (rawDoorsPortValue.equals(substitutedDoorsPortValue)) {
        setMessage(Messages.DoorsEnvironmentConnectionDataPage_ErrorMessage_DoorsPortNotResolvedVariableReference, ERROR);
        return false;
      }
    }
    boolean isDoorsPortPositiveInteger = false;
    try {
      isDoorsPortPositiveInteger = Integer.parseInt(substitutedDoorsPortValue) > 0;
    } catch (NumberFormatException exception_p) {
      // Nothing to do.
    }
    if (!isDoorsPortPositiveInteger) {
      setMessage(Messages.DoorsEnvironment_Error_Message_DoorsPortNotAPositiveInteger, ERROR);
      return false;
    }
    // Everything is OK -> clear the message.
    setMessage(null);
    return true;
  }

  /**
   * Create the elements of the page: name and port field + advanced options button
   * @see com.thalesgroup.orchestra.framework.common.ui.wizards.AbstractFormsWizardPage#doCreateControl(org.eclipse.swt.widgets.Composite,
   *      org.eclipse.ui.forms.widgets.FormToolkit)
   */
  @SuppressWarnings("boxing")
  @Override
  protected Composite doCreateControl(Composite parent_p, FormToolkit toolkit_p) {
    IVariablesHandler variablesHandler = _handler.getVariablesHandler();
    int reference;

    if (variablesHandler.isVariableReadOnly()) {
      reference = SWT.BORDER | SWT.READ_ONLY;
    } else {
      reference = SWT.BORDER;
    }

    // Main component.
    Composite composite = FormHelper.createCompositeWithLayoutType(toolkit_p, parent_p, LayoutType.GRID_LAYOUT, 3, false);
    //
    // Doors server fields.
    //
    Label emptyLineLabel = new Label(composite, SWT.NONE);
    emptyLineLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
    _rawDoorsServerText =
        (Text) FormHelper.createLabelAndText(toolkit_p, composite, Messages.DoorsEnvironment_Label_DoorsServer, ICommonConstants.EMPTY_STRING, reference).get(
            Text.class);
    _rawDoorsServerText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
    Button doorsServerVariablesButton = variablesHandler.createVariablesButton(toolkit_p, composite, new VariableReferenceHandler(_rawDoorsServerText));
    doorsServerVariablesButton.setLayoutData(new GridData());
    final Text substitutedDoorsServerText =
        (Text) FormHelper.createLabelAndText(toolkit_p, composite, Messages.DoorsEnvironmentConnectionDataPage_Label_SubstituedValue,
            ICommonConstants.EMPTY_STRING, SWT.BORDER | SWT.READ_ONLY).get(Text.class);
    substitutedDoorsServerText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
    substitutedDoorsServerText.setBackground(toolkit_p.getColors().getBackground());

    // Padding label.
    new Label(composite, SWT.NONE);
    //
    // Doors port fields.
    //
    Label emptyLineLabel2 = new Label(composite, SWT.NONE);
    emptyLineLabel2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
    _rawDoorsPortText =
        (Text) FormHelper.createLabelAndText(toolkit_p, composite, Messages.DoorsEnvironment_Label_DoorsPort, ICommonConstants.EMPTY_STRING, SWT.NONE).get(
            Text.class);
    _rawDoorsPortText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
    Button portNumberVariableButton = variablesHandler.createVariablesButton(toolkit_p, composite, new VariableReferenceHandler(_rawDoorsPortText));
    portNumberVariableButton.setLayoutData(new GridData());
    final Text substitutedDoorsPortText =
        (Text) FormHelper.createLabelAndText(toolkit_p, composite, Messages.DoorsEnvironmentConnectionDataPage_Label_SubstituedValue,
            ICommonConstants.EMPTY_STRING, SWT.BORDER | SWT.READ_ONLY).get(Text.class);
    substitutedDoorsPortText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
    substitutedDoorsPortText.setBackground(toolkit_p.getColors().getBackground());
    // Padding label.
    new Label(composite, SWT.NONE);
    // Padding label.
    new Label(composite, SWT.NONE);
    // Padding label.
    new Label(composite, SWT.NONE);
    // Padding label.
    new Label(composite, SWT.NONE);

    _Autologin = FormHelper.createButton(toolkit_p, composite, "AutoLogin", SWT.CHECK);
    _Autologin.setLayoutData(new GridData());
    _Autologin.setToolTipText("Enable the autoLogin option allows to log in with Windows user account.");

    // Padding label.
    new Label(composite, SWT.NONE);
    // Padding label.
    new Label(composite, SWT.NONE);

    // Advanced options button
    final StringBuilder referencingValue = new StringBuilder(ICommonConstants.EMPTY_STRING);
    Button advancedOptionsVariablesButton = createAdvancedOptionsButton(toolkit_p, composite, variablesHandler, referencingValue);
    advancedOptionsVariablesButton.setLayoutData(new GridData());

    // Set page read only if provided by a baseline.
    if (variablesHandler.isVariableReadOnly()) {
      _rawDoorsServerText.setBackground(toolkit_p.getColors().getBackground());
      doorsServerVariablesButton.setEnabled(false);
      _rawDoorsPortText.setBackground(toolkit_p.getColors().getBackground());
      portNumberVariableButton.setEnabled(false);
      _Autologin.setEnabled(false);
      // advancedOptionsVariablesButton.setEnabled(false);
    }

    // Set listeners.
    ModifyListener rawTextModifyListener = new ModifyListener() {
      /**
       * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
       */
      public void modifyText(ModifyEvent event_p) {
        @SuppressWarnings("synthetic-access")
        final IVariablesHandler localVariablesHandler = _handler.getVariablesHandler();
        if (event_p.getSource() == _rawDoorsServerText) {
          substitutedDoorsServerText.setText(localVariablesHandler.getSubstitutedValue(_rawDoorsServerText.getText()));
        } else if (event_p.getSource() == _rawDoorsPortText) {
          substitutedDoorsPortText.setText(localVariablesHandler.getSubstitutedValue(_rawDoorsPortText.getText()));
        }
        // Modified values. Environment no valid anymore.
        _doorsEnvironmentHandler.setUpdatedAddress(true);
        // Update page state.
        setPageComplete(canFlipToNextPage());
      }
    };
    _rawDoorsServerText.addModifyListener(rawTextModifyListener);
    _rawDoorsPortText.addModifyListener(rawTextModifyListener);

    // Set initial values.
    _rawDoorsServerText.setText(_defaultName);
    _rawDoorsPortText.setText(_defaultPort);
    Boolean autoLoginSelection = false;
    if (_autoLogin.equalsIgnoreCase("true") || _autoLogin.equalsIgnoreCase("false")) { //$NON-NLS-1$ //$NON-NLS-2$
      autoLoginSelection = Boolean.valueOf(_autoLogin);
    }
    _Autologin.setSelection(autoLoginSelection);
    _Autologin.addSelectionListener(new SelectionListener() {

      public void widgetSelected(SelectionEvent arg0_p) {
        // Retrieve the autoLogin option
        Boolean autologinBool = _Autologin.getSelection();
        String autologinStr = String.valueOf(autologinBool);
        Map<String, String> result = new HashMap<String, String>();
        result.put(DoorsEnvironment.ATTRIBUT_KEY_AUTOLOGIN_VALUE, OrchestraURI.encode(autologinStr));
        _doorsEnvironmentHandler.updateAttributes(result);
      }

      public void widgetDefaultSelected(SelectionEvent arg0_p) {
        // Retrieve the autoLogin option
        Boolean autologinBool = _Autologin.getSelection();
        String autologinStr = String.valueOf(autologinBool);
        Map<String, String> result = new HashMap<String, String>();
        result.put(DoorsEnvironment.ATTRIBUT_KEY_AUTOLOGIN_VALUE, OrchestraURI.encode(autologinStr));
        _doorsEnvironmentHandler.updateAttributes(result);
      }
    });

    // Set tab traversal order (Server -> Port).
    composite.setTabList(new Control[] { _rawDoorsServerText, _rawDoorsPortText });

    // Activate the Progress bar
    Wizard wizard = (Wizard) getWizard();
    wizard.setNeedsProgressMonitor(true);
    // Initialization of _updatedAddress in the handler.
    _doorsEnvironmentHandler.setUpdatedAddress(false);
    return composite;
  }

  /**
   * The returned next page is DoorsEnvironmentProjectAndModulePage. However the page can only be returned if DOORS could be accessed and the data for the page
   * retrieved <br>
   * As the connection and retrieval of data is not instant, a {@link IProgressMonitor} is used when accessing DOORS. If the operation fails, a error message is
   * displayed and the next page is not loader
   * @see com.thalesgroup.orchestra.framework.environment.ui.AbstractEnvironmentPage#getNextPage()
   */
  @Override
  public IWizardPage getNextPage() {
    forceUpdate();
    final DoorsEnvironmentHandler handler = (DoorsEnvironmentHandler) _handler;
    final Status[] LoadedStatus = new Status[1];
    final IVariablesHandler variablesHandler = _handler.getVariablesHandler();

    // Avoid connector calling in case of Baseline Env used as reference.
    if (variablesHandler.isVariableReadOnly()) {
      return super.getNextPage();
    }

    // Use the IProgressMonitor provided by the Wizard class to display a progress bar while accessing DOORS
    try {
      getContainer().run(true, false, new IRunnableWithProgress() {
        /**
         * Display waiting message, access DOORS through the handler
         * @see org.eclipse.jface.operation.IRunnableWithProgress#run(org.eclipse.core.runtime.IProgressMonitor)
         */
        @Override
        public void run(IProgressMonitor monitor_p) throws InvocationTargetException, InterruptedException {
          // Set variables handler for current thread.
          EnvironmentActivator.getInstance().setVariablesHandler(variablesHandler);
          // Start the monitor
          monitor_p.beginTask(Messages.DoorsEnvironment_Connection_Waiting_Message, IProgressMonitor.UNKNOWN);
          // Retrieve the result of the operation
          LoadedStatus[0] = handler.accessDoorsAndRetrieveProjectsWithStatus();
          // Unset variables handler for current thread.
          EnvironmentActivator.getInstance().setVariablesHandler(null);
        }
      });
    } catch (InvocationTargetException exception_p) {
      StringBuilder loggerMessage = new StringBuilder("DoorsEnvironmentConnectionDataPage.getNextPage(..) _ "); //$NON-NLS-1$
      CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.ERROR, exception_p);
    } catch (InterruptedException exception_p) {
      StringBuilder loggerMessage = new StringBuilder("DoorsEnvironmentConnectionDataPage.getNextPage(..) _ "); //$NON-NLS-1$
      CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.ERROR, exception_p);
    }
    if (null == LoadedStatus[0]) {
      // Test if a version of Doors environment is selected or not (to show related error message).
      // Get Doors Executable path
      String doorsPath = variablesHandler.getSubstitutedValue(DoorsEnvironmentHandler.ODM_DOORS_EXECUTABLE_PATH);
      // Add Doors Executable path (needed when there is no current context yet in Orchestra).
      if ((null == doorsPath) || DoorsEnvironmentHandler.ODM_DOORS_EXECUTABLE_PATH.equals(doorsPath)) {
        // Failure with null return. General error message to display.
        setErrorMessage(Messages.DoorsEnvironment_No_Version_Selected_In_COTS);
        return null;
      }
      // Failure with null return. General error message to display.
      setErrorMessage(Messages.DoorsEnvironment_Unable_To_Retrieve_Projects_Error_Message);
      return null;
    }
    if ((SeverityType.OK == LoadedStatus[0].getSeverity()) || (SeverityType.WARNING == LoadedStatus[0].getSeverity())) {
      // If success the projects, modules and baselineSets were retrieved from Doors, move to the next page
      _doorsEnvironmentHandler.setUpdatedAddress(false);
      return super.getNextPage();
    }
    // Failure, display specific error message and do not go to next page
    setErrorMessage(LoadedStatus[0].getMessage());
    return null;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.ui.AbstractEnvironmentPage#getPageImageDescriptor()
   */
  @Override
  protected ImageDescriptor getPageImageDescriptor() {
    return DoorsActivator.getInstance().getImageDescriptor("doors_big.gif"); //$NON-NLS-1$
  }

  /**
   * @see com.thalesgroup.orchestra.framework.common.ui.wizards.AbstractFormsWizardPage#getPageTitle()
   */
  @Override
  protected String getPageTitle() {
    return Messages.DoorsEnvironment_Connnection_Data_Page_Title;
  }

  /**
   * The returned result is the port and name values and parameters if any
   * @see com.thalesgroup.orchestra.framework.environment.ui.AbstractEnvironmentPage#getUpdatedValues()
   */
  @Override
  protected Map<String, String> getUpdatedValues() {
    Map<String, String> result = new HashMap<String, String>();
    // Retrieve the name and port values
    if ((null != _rawDoorsServerText) && (null != _rawDoorsPortText)) {
      String name = _rawDoorsServerText.getText();
      if (ICommonConstants.EMPTY_STRING != name) {
        result.put(DoorsEnvironment.ATTRIBUT_KEY_DB_NAME, OrchestraURI.encode(name));
      }
      String port = _rawDoorsPortText.getText();
      if (ICommonConstants.EMPTY_STRING != port) {
        result.put(DoorsEnvironment.ATTRIBUT_KEY_PORT, OrchestraURI.encode(port));
      }
      // Retrieve the autoLogin option
      @SuppressWarnings("boxing")
      Boolean autologinBool = _Autologin.getSelection();
      String autologinStr = String.valueOf(autologinBool);
      if ((null != autologinStr) && !ICommonConstants.EMPTY_STRING.equals(autologinStr)) {
        result.put(DoorsEnvironment.ATTRIBUT_KEY_AUTOLOGIN_VALUE, OrchestraURI.encode(autologinStr));
      }
      // Retrieve the optional parameters values
      if ((null != _DoorsParameters) && !ICommonConstants.EMPTY_STRING.equals(_DoorsParameters)) {
        result.put(DoorsEnvironment.ATTRIBUT_KEY_PARAMETERS, OrchestraURI.encode(_DoorsParameters));
      }
    }
    return result;
  }

  /**
   * Advanced Options dialog.
   */
  protected class AdvancedOptionsDialog extends MessageDialog {
    private Display _display;
    /**
     * Text field to enter the name of the Doors database
     */
    protected Text _rawDoorsParametersText;
    private StringBuilder _referencingValue;
    private FormToolkit _toolkit;
    /**
     * Default value of the parameters
     */
    private final String _defaultParameters;

    private IVariablesHandler _variablesHandler;

    /**
     * Constructor based on {@link MessageDialog#MessageDialog(Shell, String, Image, String, int, String[], int)}.
     */
    public AdvancedOptionsDialog(Shell parentShell_p, String dialogTitle_p, Image dialogTitleImage_p, String dialogMessage_p, int dialogImageType_p,
        String[] dialogButtonLabels_p, int defaultIndex_p, Display display_p, FormToolkit toolkit_p, IVariablesHandler variablesHandler_p,
        final StringBuilder referencingValue_p, DoorsEnvironmentHandler handler_p, String parameters_p) {
      super(parentShell_p, dialogTitle_p, dialogTitleImage_p, dialogMessage_p, dialogImageType_p, dialogButtonLabels_p, defaultIndex_p);
      _display = display_p;
      _toolkit = toolkit_p;
      _variablesHandler = variablesHandler_p;
      _referencingValue = referencingValue_p;
      _defaultParameters = parameters_p;// handler_p.getOptionalParameters();
    }

    /**
     * @see org.eclipse.jface.dialogs.MessageDialog#configureShell(org.eclipse.swt.widgets.Shell)
     */
    @Override
    protected void configureShell(Shell shell_p) {
      super.configureShell(shell_p);
      int defaultDialogWidth = 550;
      int defaultDialogHeight = 200;
      Shell activeShell = _display.getActiveShell();
      if (null != activeShell) {
        // Center dialog on active parent shell.
        Rectangle bounds = activeShell.getBounds();
        shell_p.setBounds(bounds.x + ((bounds.width - defaultDialogWidth) / 2), bounds.y + ((bounds.height - defaultDialogHeight) / 2), defaultDialogWidth,
            defaultDialogHeight);
      }
    }

    /**
     * @see org.eclipse.jface.dialogs.IconAndMessageDialog#createContents(org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected Control createContents(Composite parent_p) {
      Control contents = super.createContents(parent_p);
      // Set the initial button enabled state.
      getButton(OK).setEnabled(false);
      return contents;
    }

    /**
     * @see org.eclipse.jface.dialogs.MessageDialog#createCustomArea(org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected Control createCustomArea(Composite parent_p) {
      int reference;

      if (_variablesHandler.isVariableReadOnly()) {
        reference = SWT.BORDER | SWT.READ_ONLY;
      } else {
        reference = SWT.BORDER;
      }
      // Main component.
      Composite composite = FormHelper.createCompositeWithLayoutType(_toolkit, parent_p, LayoutType.GRID_LAYOUT, 3, false);

      // DEBUG TEST ############################################################################################
      // composite.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE));

      //
      // Doors parameters fields.
      //
      Label emptyLineLabel = new Label(composite, SWT.NONE);
      emptyLineLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));

      // / ############################################################################################
      // emptyLineLabel.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_GREEN));

      _rawDoorsParametersText =
          (Text) FormHelper.createLabelAndText(_toolkit, composite, Messages.DoorsEnvironment_Label_DoorsParameters, ICommonConstants.EMPTY_STRING, reference)
              .get(Text.class);
      _rawDoorsParametersText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

      if (_variablesHandler.isVariableReadOnly()) {
        _rawDoorsParametersText.setBackground(_toolkit.getColors().getBackground());
      }

      // / ############################################################################################
      // _rawDoorsParametersText.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));

      final Button doorsServerVariablesButton =
          _variablesHandler.createVariablesButton(_toolkit, composite, new VariableReferenceHandler(_rawDoorsParametersText));
      doorsServerVariablesButton.setLayoutData(new GridData());

      final Text substitutedDoorsParametersText =
          (Text) FormHelper.createLabelAndText(_toolkit, composite, Messages.DoorsEnvironmentConnectionDataPage_Label_SubstituedValue,
              ICommonConstants.EMPTY_STRING, SWT.BORDER | SWT.READ_ONLY).get(Text.class);
      substitutedDoorsParametersText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
      substitutedDoorsParametersText.setBackground(_toolkit.getColors().getBackground());

      // Padding label.
      new Label(composite, SWT.NONE);

      // Set listeners.
      ModifyListener rawTextModifyListener = new ModifyListener() {
        /**
         * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
         */
        @SuppressWarnings("synthetic-access")
        public void modifyText(ModifyEvent event_p) {
          final IVariablesHandler localVariablesHandler = _variablesHandler;
          if (event_p.getSource() == _rawDoorsParametersText) {
            substitutedDoorsParametersText.setText(localVariablesHandler.getSubstitutedValue(_rawDoorsParametersText.getText()));
          }
          try {
            getButton(OK).setEnabled(true);
          } catch (NullPointerException e) {
            // Button not yet created, return NullPointerException. Do nothing.
          }
          _referencingValue.replace(0, _referencingValue.length(), _rawDoorsParametersText.getText());
        }
      };
      _rawDoorsParametersText.addModifyListener(rawTextModifyListener);

      // Set initial values.
      _rawDoorsParametersText.setText(_defaultParameters);

      if (_variablesHandler.isVariableReadOnly()) {
        doorsServerVariablesButton.setEnabled(false);
      }

      return composite;
    }

    /**
     * @see org.eclipse.jface.dialogs.Dialog#isResizable()
     */
    @Override
    protected boolean isResizable() {
      return true;
    }
  }
}
