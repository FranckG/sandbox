/**
 * <p>
 * Copyright (c) 2002-2005 : Thales Research & Technology
 * </p>
 * <p>
 * Société : Thales Research & Technology
 * </p>
 * <p>
 * Thales Part Number 46-475-668
 * </p>
 */
package com.thalesgroup.orchestra.framework.oe.ui.dialogs;

import java.io.File;
import java.util.Date;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import com.thalesgroup.orchestra.framework.oe.OrchestraExplorerActivator;
import com.thalesgroup.orchestra.framework.oe.artefacts.IArtefact;
import com.thalesgroup.orchestra.framework.oe.artefacts.IArtefactProperties;
import com.thalesgroup.orchestra.framework.oe.ui.constants.IImageConstants;

/**
 * <p>
 * Title : PropertiesDialog
 * </p>
 * <p>
 * Description : Show Artifact Properties Dialog
 * </p>
 * @author s0018747
 * @version 3.9.0
 */
public class PropertiesDialog extends Dialog {
  protected IArtefact _element;
  protected FormToolkit _toolkit;

  /**
   * ShowPropertiesDialog Constructor
   * @param parentShell
   */
  public PropertiesDialog(Shell parentShell, IArtefact element) {
    super(parentShell);
    _element = element;
    _toolkit = new FormToolkit(parentShell.getDisplay());
    _toolkit.setBackground(parentShell.getBackground());
  }

  /**
   * @see org.eclipse.jface.dialogs.Dialog#close()
   */
  @Override
  public boolean close() {
    boolean closed = false;
    try {
      _toolkit.dispose();
    } finally {
      closed = super.close();
    }
    return closed;
  }

  /*
   * (non-Javadoc)
   * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets .Shell)
   */
  @Override
  protected void configureShell(Shell newShell) {
    super.configureShell(newShell);
    newShell.setText(Messages.PropertiesDialog_title);
    newShell.setImage(OrchestraExplorerActivator.getDefault().getImage(IImageConstants.DESC_SHOW_PROPERTIES));
  }

  /**
   * Adds buttons to this dialog's button bar.
   * <p>
   * The <code>Dialog</code> implementation of this framework method adds standard ok and cancel buttons using the <code>createButton</code> framework method.
   * These standard buttons will be accessible from <code>getCancelButton</code>, and <code>getOKButton</code>. Subclasses may override.
   * </p>
   * @param parent the button bar composite
   */
  @Override
  protected void createButtonsForButtonBar(Composite parent) {
    // create OK and Cancel buttons by default
    Button createButton = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
    _toolkit.adapt(createButton, true, true);
  }

  /**
   * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(Composite)
   */
  @Override
  protected Control createDialogArea(Composite parent) {
    Composite propertiesParam = (Composite) super.createDialogArea(parent);
    final TableWrapLayout tableWrapLayout = new TableWrapLayout();
    tableWrapLayout.numColumns = 2;
    propertiesParam.setLayout(tableWrapLayout);
    showProperties(propertiesParam, _element);
    _toolkit.adapt(parent);
    return propertiesParam;
  }

  /**
   * Creates a heading label
   */
  private Text createText(Composite parent, String text) {
    int currentBorderStyle = _toolkit.getBorderStyle();
    _toolkit.setBorderStyle(SWT.NULL);
    Text textArea = null;
    try {
      textArea = _toolkit.createText(parent, text, SWT.WRAP | SWT.READ_ONLY);
      textArea.setEditable(false);
      TableWrapData td = new TableWrapData(TableWrapData.FILL_GRAB);
      // Maximum width, to allow too long text to be multilines displayed, and fully readable.
      td.maxWidth = 300;
      textArea.setLayoutData(td);
    } finally {
      _toolkit.setBorderStyle(currentBorderStyle);
    }
    return textArea;
  }

  private void showProperties(Composite parent, IArtefact element) {
    if (element != null) {
      _toolkit.createLabel(parent, Messages.PropertiesDialog_name);
      // Create name field using label or name (if no label).
      String artefactDisplayedName = element.getLabel();
      if (null == artefactDisplayedName || artefactDisplayedName.isEmpty()) {
        artefactDisplayedName = element.getName();
      }
      createText(parent, artefactDisplayedName);
      // Create type field.
      String type = element.getType();
      if (type != null && !Messages.PropertiesDialog_default.equals(type) && !Messages.PropertiesDialog_blank.equals(type)) {
        _toolkit.createLabel(parent, Messages.PropertiesDialog_type);
        _toolkit.createLabel(parent, Messages.PropertiesDialog_space + element.getType());
      }
      String env = element.getPropertyValue(IArtefactProperties.ENVIRONMENT);
      if (null == env) {
        // element.
      }
      _toolkit.createLabel(parent, Messages.PropertiesDialog_env);
      _toolkit.createLabel(parent, Messages.PropertiesDialog_space + env);

      String path = element.getPropertyValue(IArtefactProperties.ABSOLUTE_PATH);
      if (path != null) {
        // Element Path
        File file = new File(path);

        _toolkit.createLabel(parent, Messages.PropertiesDialog_path);
        createText(parent, path);
        _toolkit.createLabel(parent, Messages.PropertiesDialog_status);
        _toolkit.createLabel(parent, file.canWrite() ? Messages.PropertiesDialog_readWriteAccess : Messages.PropertiesDialog_readOnly);
        _toolkit.createLabel(parent, Messages.PropertiesDialog_lastModification);
        _toolkit.createLabel(parent, Messages.PropertiesDialog_space + new Date(file.lastModified()).toString());
      }
      _toolkit.createLabel(parent, Messages.PropertiesDialog_OrchestraURI);
      createText(parent, element.getUri().getUri());
    }
  }
}