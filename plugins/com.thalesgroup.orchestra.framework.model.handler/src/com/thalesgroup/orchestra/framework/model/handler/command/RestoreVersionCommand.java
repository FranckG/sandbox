/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.handler.command;

import com.thalesgroup.orchestra.framework.common.command.ModelModifyingCommand;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.InstallationCategory;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;

/**
 * @author t0076261
 */
public class RestoreVersionCommand extends ModelModifyingCommand {
  /**
   * The installation category being current version to remove.
   */
  private InstallationCategory _currentVersion;
  /**
   * Edition context.<br>
   * That is the context which holds the responsibility at the time of selection.
   */
  private Context _editionContext;

  /**
   * @param currentVersion_p
   * @param context_p
   */
  public RestoreVersionCommand(InstallationCategory currentVersion_p, Context context_p) {
    super(Messages.RestoreVersionCommand_Label, ModelHandlerActivator.getDefault().getEditingDomain());
    setDescription(Messages.RestoreVersionCommand_Description);
    _currentVersion = currentVersion_p;
    _editionContext = context_p;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.common.command.ModelModifyingCommand#doExecute()
   */
  @Override
  protected void doExecute() {
    // Precondition.
    if ((null == _editionContext) || (null == _currentVersion)) {
      return;
    }
    // Get selected version.
    InstallationCategory selectedVersion = ModelUtil.getSelectedVersionForPath(_currentVersion.getReferencePath(), _editionContext, true);
    // Precondition.
    if (null == selectedVersion) {
      return;
    }
    // Get its reference path.
    String versionPath = selectedVersion.getReferencePath();
    // And remove it.
    _editionContext.getSelectedVersions().remove(selectedVersion);
    // Then remove current version.
    _editionContext.getCurrentVersions().remove(_currentVersion);
    // Release memory.
    _currentVersion = null;
    // Get existing selected version.
    selectedVersion = ModelUtil.getSelectedVersionForPath(versionPath, _editionContext, false);
    // And apply it.
    ModelUtil.createOrUpdateCurrentVersionFor(selectedVersion);
  }
}