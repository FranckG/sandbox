/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.wizard.page.type;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.DirectoryDialog;

import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.ui.internal.wizard.page.IValueHandler;

/**
 * @author s0011584
 */
public class FolderVariableValueCustomizer extends AbstractBrowseValueCustomizer {
  /**
   * Constructor.
   * @param editionContext_p
   * @param handler_p
   */
  public FolderVariableValueCustomizer(Context editionContext_p, IValueHandler handler_p) {
    super(editionContext_p, handler_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.type.DefaultValueCustomizer#getButtonLabel()
   */
  @Override
  public String getButtonLabel() {
    return Messages.FolderVariableValueEditionHandler_Button_Text_Browse;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.type.DefaultValueCustomizer#getToolTipText()
   */
  @Override
  public String getToolTipText() {
    return Messages.FolderVariableValueEditionHandler_Button_ToolTip_Browse;
  }

  /**
   * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
   */
  public void widgetSelected(SelectionEvent e_p) {
    DirectoryDialog dialog = new DirectoryDialog(e_p.display.getActiveShell());
    dialog.setFilterPath(_valueHandler.getValue());
    String folder = dialog.open();
    if (folder != null) {
      _valueHandler.setNewValue(folder);
    }
  }
}