/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.wizard.page.type;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.FileDialog;

import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.ui.internal.wizard.page.IValueHandler;

/**
 * @author s0011584
 */
public class FileVariableValueCustomizer extends AbstractBrowseValueCustomizer {
  /**
   * Constructor.
   * @param editionContext_p
   * @param handler_p
   */
  public FileVariableValueCustomizer(Context editionContext_p, IValueHandler handler_p) {
    super(editionContext_p, handler_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.type.DefaultValueCustomizer#getButtonLabel()
   */
  @Override
  public String getButtonLabel() {
    return Messages.FileVariableValueEditionHandler_Button_Text_Browse;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.type.DefaultValueCustomizer#getToolTipText()
   */
  @Override
  public String getToolTipText() {
    return Messages.FileVariableValueEditionHandler_Button_ToolTip_Browse;
  }

  /**
   * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
   */
  public void widgetSelected(SelectionEvent e_p) {
    FileDialog fileDialog = new FileDialog(e_p.display.getActiveShell());
    fileDialog.setFilterPath(_valueHandler.getValue());
    String fileName = fileDialog.open();
    if (fileName != null) {
      _valueHandler.setNewValue(fileName);
    }
  }
}