/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.action;

import com.thalesgroup.orchestra.framework.common.command.ModelModifyingCommand;
import com.thalesgroup.orchestra.framework.model.IEditionContextProvider;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.InstallationCategory;
import com.thalesgroup.orchestra.framework.model.handler.command.RestoreVersionCommand;
import com.thalesgroup.orchestra.framework.ui.activator.OrchestraFrameworkUiActivator;

/**
 * @author t0076261
 */
public class RestoreVersionAction extends AbstractVersionAction {
  /**
   * Current version belonging to edition context, that should be removed.
   */
  private InstallationCategory _currentVersion;

  /**
   * Constructor.
   * @param editionContextProvider_p
   */
  public RestoreVersionAction(IEditionContextProvider editionContextProvider_p) {
    super(Messages.RestoreVersionAction_Text, editionContextProvider_p);
    setImageDescriptor(OrchestraFrameworkUiActivator.getDefault().getImageDescriptor("restoreVersion.gif")); //$NON-NLS-1$
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.action.AbstractVersionAction#createCommand()
   */
  @Override
  protected ModelModifyingCommand createCommand() {
    // Release memory.
    _installationCategory = null;
    // Create new command.
    return new RestoreVersionCommand(_currentVersion, _editionContextProvider.getEditionContext());
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.action.AbstractVersionAction#doUpdateSelection(com.thalesgroup.orchestra.framework.model.contexts.InstallationCategory)
   */
  @Override
  protected boolean doUpdateSelection(InstallationCategory installationCategory_p) {
    // Should not already be selected by current context.
    Context editionContext = _editionContextProvider.getEditionContext();
    // Get current version.
    _currentVersion = ModelUtil.getCurrentVersionForPath(ModelUtil.getElementPath(installationCategory_p), editionContext, true);
    boolean isResettable = (null != _currentVersion) && (editionContext == ModelUtil.getContext(_currentVersion));
    if (!isResettable) {
      // Release selected version.
      _currentVersion = null;
    }
    return isResettable;
  }
}