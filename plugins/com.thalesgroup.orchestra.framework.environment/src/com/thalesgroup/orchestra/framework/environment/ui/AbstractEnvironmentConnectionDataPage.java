/**
 * Copyright (c) THALES, 2013. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.environment.IEnvironmentHandler;
import com.thalesgroup.orchestra.framework.environment.ui.IVariablesHandler.IReferencingValueHandler;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper.LayoutType;

/**
 * An abstract implementation of the connection data page that provides a simple UI using a connection string.
 * @author S0035580
 */
public abstract class AbstractEnvironmentConnectionDataPage extends AbstractEnvironmentPage {
  /**
   * Default value of the connection string
   */
  protected String _defaultConnexionString;
  /**
   * Combo field to enter the connection string
   */
  protected Combo _rawConnectionStringCombo;

  /**
   * @param pageId_p
   * @param handler_p The environment handler responsible for currently edited environment.
   */
  public AbstractEnvironmentConnectionDataPage(String pageId_p, IEnvironmentHandler handler_p) {
    super(pageId_p, handler_p);
  }

  /**
   * Create the elements of the page: connection string field
   * @see com.thalesgroup.orchestra.framework.common.ui.wizards.AbstractFormsWizardPage#doCreateControl(org.eclipse.swt.widgets.Composite,
   *      org.eclipse.ui.forms.widgets.FormToolkit)
   */
  @Override
  protected Composite doCreateControl(Composite parent_p, FormToolkit toolkit_p) {
    IVariablesHandler variablesHandler = _handler.getVariablesHandler();
    int reference;

    if (!getComboMode()) {
      reference = SWT.BORDER | SWT.READ_ONLY;
    } else {
      reference = SWT.BORDER;
    }

    // Main component.
    Composite composite = FormHelper.createCompositeWithLayoutType(toolkit_p, parent_p, LayoutType.GRID_LAYOUT, 3, false);
    //
    // Connection string fields.
    //
    Label emptyLineLabel = new Label(composite, SWT.NONE);
    emptyLineLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
    String label = getLabelConnectionString();
    if (null == label) {
      label = ICommonConstants.EMPTY_STRING;
    }
    _rawConnectionStringCombo = (Combo) createLabelAndCombo(toolkit_p, composite, label, ICommonConstants.EMPTY_STRING, reference).get(Combo.class);
    _rawConnectionStringCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
    Button ConnectionStringVariablesButton =
        variablesHandler.createVariablesButton(toolkit_p, composite, new VariableReferenceHandler(_rawConnectionStringCombo));
    ConnectionStringVariablesButton.setLayoutData(new GridData());
    ConnectionStringVariablesButton.setVisible(true);
    Map<Class, Widget> labelAndText =
        FormHelper.createLabelAndText(toolkit_p, composite, "Substituted Value", ICommonConstants.EMPTY_STRING, SWT.BORDER | SWT.READ_ONLY); //$NON-NLS-1$
    final Text substitutedConnectionStringVariablesButtonText = (Text) labelAndText.get(Text.class);
    final Label substitutedConnectionStringVariablesButtonLabel = (Label) labelAndText.get(Label.class);
    substitutedConnectionStringVariablesButtonText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
    substitutedConnectionStringVariablesButtonText.setBackground(toolkit_p.getColors().getBackground());
    substitutedConnectionStringVariablesButtonText.setVisible(true);
    substitutedConnectionStringVariablesButtonLabel.setVisible(true);

    if (!getComboMode()) {
      List<String> connectionStringList = getConnectionStringList();
      if ((null != connectionStringList) && !connectionStringList.isEmpty()) {
        _rawConnectionStringCombo.setItems(connectionStringList.toArray(new String[connectionStringList.size()]));
      } else {
        String errorMessage = getEmptyComboErrorMessage();
        if (null != errorMessage) {
          setErrorMessage(errorMessage);
        }
      }
    }

    // Padding label.
    new Label(composite, SWT.NONE);

    // Set page read only if provided by a baseline.
    if (variablesHandler.isVariableReadOnly()) {
      _rawConnectionStringCombo.setBackground(toolkit_p.getColors().getBackground());
      ConnectionStringVariablesButton.setEnabled(false);
    }

    // Set listeners.
    ModifyListener rawComboModifyListener = new ModifyListener() {
      /**
       * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
       */
      public void modifyText(ModifyEvent event_p) {
        final IVariablesHandler localVariablesHandler = _handler.getVariablesHandler();
        if (event_p.getSource() == _rawConnectionStringCombo) {
          substitutedConnectionStringVariablesButtonText.setText(localVariablesHandler.getSubstitutedValue(_rawConnectionStringCombo.getText()));
        }
        forceUpdate();
        // Update page state.
        setPageComplete(canFlipToNextPage());
      }
    };
    _rawConnectionStringCombo.addModifyListener(rawComboModifyListener);

    // Set initial value.
    if (null != _defaultConnexionString) {
      if ((-1 == _rawConnectionStringCombo.indexOf(_defaultConnexionString)) && !_defaultConnexionString.isEmpty())
        _rawConnectionStringCombo.add(_defaultConnexionString);
      _rawConnectionStringCombo.setText(_defaultConnexionString);
    }

    // Activate the Progress bar
    Wizard wizard = (Wizard) getWizard();
    wizard.setNeedsProgressMonitor(true);

    return composite;
  }

  /**
   * Is combo used like a read-only Combo or like a TextBox?
   * @return <code>true</code> to use TextBox mode, <code>false</code> to use Combo mode.
   */
  protected abstract boolean getComboMode();

  /**
   * Get the initialized values for the Combo.
   * @return A list of the initialized values
   */
  protected abstract List<String> getConnectionStringList();

  /**
   * Get the error message when the Combo is empty.
   * @return the error message
   */
  protected abstract String getEmptyComboErrorMessage();

  /**
   * Get the label to the field.
   * @return the label of the combo
   */
  protected abstract String getLabelConnectionString();

  /**
   * Create a user combo widget with preceding label.<br>
   * Requires at least a two columns layout so that both the label and the combo are displayed on the same line.
   * @param toolkit_p
   * @param parent_p
   * @param labelMessage_p
   * @param initialText_p
   * @param textStyle_p
   * @return
   */
  public static Map<Class, Widget> createLabelAndCombo(FormToolkit toolkit_p, Composite parent_p, String labelMessage_p, String initialText_p, int textStyle_p) {
    // Create label.
    Label label = toolkit_p.createLabel(parent_p, labelMessage_p, SWT.WRAP);
    label.setForeground(toolkit_p.getColors().getColor(IFormColors.TITLE));
    // Create text.
    Combo combo = new Combo(parent_p, textStyle_p);
    combo.setBackground(parent_p.getDisplay().getSystemColor(SWT.COLOR_WHITE));
    Map<Class, Widget> result = new HashMap<Class, Widget>(2);
    result.put(Label.class, label);
    result.put(Combo.class, combo);
    return result;
  }
}

/**
 * Callbacks for the variable selection dialog.
 * @author S0035580
 */
final class VariableReferenceHandler implements IReferencingValueHandler {
  /**
   * Field to fill when a variable reference has been selected.
   */
  private final Combo _field;

  /**
   * Constructor.
   * @param textField_p
   */
  public VariableReferenceHandler(Combo textField_p) {
    _field = textField_p;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.ui.IVariablesHandler.IReferencingValueHandler#handleSelectedValue(java.lang.String)
   */
  @Override
  public void handleSelectedValue(String referencingValue_p) {
    _field.add(referencingValue_p);
    _field.setText(referencingValue_p);
  }
}
