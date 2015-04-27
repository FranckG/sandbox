/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.wizard.page.type;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.layout.PixelConverter;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.environment.EnvironmentActivator;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import com.thalesgroup.orchestra.framework.root.ui.AbstractRunnableWithDisplay;
import com.thalesgroup.orchestra.framework.root.ui.DisplayHelper;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper.LayoutType;
import com.thalesgroup.orchestra.framework.ui.activator.OrchestraFrameworkUiActivator;
import com.thalesgroup.orchestra.framework.ui.internal.wizard.SetupEnvironmentWizard;
import com.thalesgroup.orchestra.framework.ui.internal.wizard.page.IValueHandler;

/**
 * A customizer dedicated to environment variables.
 * @author t0076261
 */
public class EnvironmentValueCustomizer extends DefaultValueCustomizer implements IMultipleValueCustomizer, IDoubleClickListener {
  /**
   * Environment setup button.
   */
  protected Button _openEnvironmentButton;

  /**
   * Constructor.
   * @param editionContext_p
   * @param handler_p
   */
  public EnvironmentValueCustomizer(Context editionContext_p, IValueHandler handler_p) {
    super(editionContext_p, handler_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.type.DefaultValueCustomizer#createButtonsPart(org.eclipse.ui.forms.widgets.FormToolkit,
   *      org.eclipse.swt.widgets.Composite, org.eclipse.swt.widgets.Composite)
   */
  @Override
  public Composite createButtonsPart(FormToolkit toolkit_p, Composite parent_p, Composite composite_p) {
    // Add open environment button.
    _openEnvironmentButton = createOpenEnvironmentButton(toolkit_p, composite_p);
    // Call for super behavior.
    return super.createButtonsPart(toolkit_p, parent_p, composite_p);
  }

  /**
   * Add a button to open an environment. The button text is "Read..." if the env is read-only, "Setup..." else.
   * @param toolkit_p
   * @param buttonComposite_p
   * @return
   */
  protected Button createOpenEnvironmentButton(final FormToolkit toolkit_p, final Composite buttonComposite_p) {
    String buttonText = null;
    String buttonToolTipText = null;
    if (isVariableReadOnly()) {
      buttonText = Messages.EnvironmentValueCustomizer_OpenButton_Text_Read;
      buttonToolTipText = Messages.EnvironmentValueCustomizer_OpenButton_Tooltip_Read;
    } else {
      buttonText = Messages.EnvironmentValueCustomizer_OpenButton_Text_Setup;
      buttonToolTipText = Messages.EnvironmentValueCustomizer_OpenButton_Tooltip_Setup;
    }

    Button button = FormHelper.createButton(toolkit_p, buttonComposite_p, buttonText, SWT.PUSH);
    button.setToolTipText(buttonToolTipText);
    button.addSelectionListener(new SelectionAdapter() {
      /**
       * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
       */
      @Override
      public void widgetSelected(SelectionEvent e_p) {
        handleEnvironmentSetup((EnvironmentVariableValue) _valueHandler.getVariableValue());
      }
    });
    GridData data = (GridData) FormHelper.updateControlLayoutDataWithLayoutTypeData(button, LayoutType.GRID_LAYOUT);
    data.grabExcessVerticalSpace = false;
    return button;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.type.DefaultValueCustomizer#createVariablesButton(org.eclipse.ui.forms.widgets.FormToolkit,
   *      org.eclipse.swt.widgets.Composite, com.thalesgroup.orchestra.framework.environment.ui.IVariablesBrowserHandler.IReferencingValueHandler)
   */
  @Override
  public Button createVariablesButton(FormToolkit toolkit_p, Composite buttonComposite_p, IReferencingValueHandler referencingValueHandler_p) {
    if (this == referencingValueHandler_p) {
      // Can not change the value of an environment variable in the generic value page.
      return null;
    }
    return super.createVariablesButton(toolkit_p, buttonComposite_p, referencingValueHandler_p);
  }

  /**
   * @see org.eclipse.jface.viewers.IDoubleClickListener#doubleClick(org.eclipse.jface.viewers.DoubleClickEvent)
   */
  public void doubleClick(DoubleClickEvent event_p) {
    handleEnvironmentSetup((EnvironmentVariableValue) _valueHandler.getVariableValue());
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.type.DefaultValueCustomizer#getAdapter(java.lang.Class)
   */
  @Override
  public Object getAdapter(Class adapter_p) {
    if (IMultipleValueCustomizer.class.equals(adapter_p)) {
      return this;
    }
    return super.getAdapter(adapter_p);
  }

  /**
   * Handle environment setup for specified {@link EnvironmentVariableValue}.
   * @param environmentVariableValue_p
   */
  protected void handleEnvironmentSetup(final EnvironmentVariableValue environmentVariableValue_p) {
    if (null == environmentVariableValue_p) {
      return;
    }
    DisplayHelper.displayRunnable(new AbstractRunnableWithDisplay() {
      /**
       * @see java.lang.Runnable#run()
       */
      public void run() {
        WizardDialog dialog = new WizardDialog(getDisplay().getActiveShell(), new SetupEnvironmentWizard(_valueHandler));
        int result = Window.OK;
        try {
          EnvironmentActivator.getInstance().setVariablesHandler(EnvironmentValueCustomizer.this);
          // Limit the dialog horizontal size to avoid big dialog if big texts in it.
          PixelConverter converter = new PixelConverter(JFaceResources.getDialogFont());
          dialog.setPageSize(converter.convertWidthInCharsToPixels(70), SWT.DEFAULT);
          result = dialog.open();
        } catch (Throwable throwable_p) {
          result = Window.CANCEL;
          OrchestraFrameworkUiActivator
              .getDefault()
              .getLog()
              .log(
                  new Status(IStatus.ERROR, OrchestraFrameworkUiActivator.getDefault().getPluginId(), Messages.EnvironmentValueCustomizer_ErrorMessage_Setup,
                      throwable_p));
          if (null != dialog) {
            dialog.close();
          }
        } finally {
          EnvironmentActivator.getInstance().setVariablesHandler(null);
          if (Window.OK != result) {
            _valueHandler.editionCancelled();
          } else {
            _valueHandler.editionFinished();
          }
        }
      }
    }, true);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.type.IMultipleValueCustomizer#newVariableValueAdded(com.thalesgroup.orchestra.framework.model.contexts.VariableValue)
   */
  public void newVariableValueAdded(VariableValue newValue_p) {
    handleEnvironmentSetup((EnvironmentVariableValue) newValue_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.type.DefaultValueCustomizer#setEnabled(boolean)
   */
  @Override
  public void setEnabled(boolean enabled_p) {
    super.setEnabled(enabled_p);
    // A value must be accessible.
    if (null != _valueHandler.getValue()) {
      _openEnvironmentButton.setEnabled(enabled_p);
    } else {
      _openEnvironmentButton.setEnabled(false);
    }
  }
}