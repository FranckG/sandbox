/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.action;

import com.thalesgroup.orchestra.framework.common.command.ModelModifyingCommand;
import com.thalesgroup.orchestra.framework.model.IEditionContextProvider;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.InstallationCategory;
import com.thalesgroup.orchestra.framework.model.handler.command.SelectVersionCommand;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.ui.activator.OrchestraFrameworkUiActivator;

/**
 * Select product version.
 * @author t0076261
 */
public class SelectVersionAction extends AbstractVersionAction {
  /**
   * Constructor.
   */
  public SelectVersionAction(IEditionContextProvider editionContextProvider_p) {
    super(Messages.SelectVersionAction_Label, editionContextProvider_p);
    setImageDescriptor(OrchestraFrameworkUiActivator.getDefault().getImageDescriptor("selectVersion.png")); //$NON-NLS-1$
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.action.AbstractVersionAction#createCommand()
   */
  @Override
  protected ModelModifyingCommand createCommand() {
    return new SelectVersionCommand(_installationCategory, _editionContextProvider.getEditionContext());
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.action.AbstractVersionAction#doUpdateSelection(com.thalesgroup.orchestra.framework.model.contexts.InstallationCategory)
   */
  @Override
  protected boolean doUpdateSelection(InstallationCategory installationCategory_p) {
    return !DataUtil.isUnmodifiable(_editionContextProvider.getEditionContext(), _editionContextProvider.getEditionContext())
           && ModelUtil.isSelectableVersionCategory(installationCategory_p);
  }
}