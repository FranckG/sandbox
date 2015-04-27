/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.root.ui.wizards;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * A wizard page that displays contents using Forms.
 * @author t0076261
 */
public abstract class AbstractFormsWizardPage extends WizardPage {
  /**
   * In-use form toolkit.
   */
  private FormToolkit _toolkit;

  /**
   * Constructor.
   * @param pageId_p
   */
  public AbstractFormsWizardPage(String pageId_p) {
    super(pageId_p);
    setTitle(getPageTitle());
    setImageDescriptor(getPageImageDescriptor());
  }

  /**
   * Create control decoration for specified control.
   * @param control_p
   * @param errorMessage_p The message to display along with the decoration.
   * @param errorLevel_p The error level. From {@link FieldDecorationRegistry#DEC_CONTENT_PROPOSAL} to {@link FieldDecorationRegistry#DEC_ERROR_QUICKFIX}.
   * @return
   */
  protected ControlDecoration createDecoration(Control control_p, String errorMessage_p, String errorLevel_p) {
    // Precondition.
    if (null == control_p) {
      return null;
    }
    // Create decoration.
    ControlDecoration controlDecoration = new ControlDecoration(control_p, SWT.LEFT | SWT.TOP);
    // Add message.
    if (null != errorMessage_p) {
      controlDecoration.setDescriptionText(errorMessage_p);
    }
    // Add image.
    FieldDecoration fieldDecoration = FieldDecorationRegistry.getDefault().getFieldDecoration(errorLevel_p);
    if (null != fieldDecoration) {
      controlDecoration.setImage(fieldDecoration.getImage());
    }
    return controlDecoration;
  }

  /**
   * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
   */
  public void createControl(Composite parent_p) {
    initializeDialogUnits(parent_p);
    Composite result = doCreateControl(parent_p, getToolkit());
    getToolkit().adapt(result);
    setControl(result);
  }

  /**
   * Disable specified control for specified parent.
   * @param control_p
   * @param parent_p
   */
  protected void disableControl(Control control_p, Composite parent_p) {
    // Precondition.
    if ((null == control_p) || (null == parent_p)) {
      return;
    }
    if (control_p instanceof Text) {
      ((Text) control_p).setEditable(false);
      control_p.setBackground(parent_p.getBackground());
    } else {
      control_p.setEnabled(false);
    }
  }

  /**
   * Do create page control.
   * @param parent_p
   * @param toolkit_p
   * @return
   */
  protected abstract Composite doCreateControl(Composite parent_p, FormToolkit toolkit_p);

  /**
   * Get page image descriptor.
   * @return
   */
  protected abstract ImageDescriptor getPageImageDescriptor();

  /**
   * Get page title.
   * @return
   */
  protected abstract String getPageTitle();

  /**
   * Get in-use form toolkit.
   * @return
   */
  public FormToolkit getToolkit() {
    return _toolkit;
  }

  /**
   * Set form toolkit to use.
   * @param toolkit_p
   */
  public void setToolkit(FormToolkit toolkit_p) {
    _toolkit = toolkit_p;
  }
}