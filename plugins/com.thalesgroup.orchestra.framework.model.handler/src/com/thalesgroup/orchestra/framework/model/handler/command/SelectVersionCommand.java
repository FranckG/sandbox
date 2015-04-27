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
public class SelectVersionCommand extends ModelModifyingCommand {
  /**
   * Edition context.<br>
   * That is the context which holds the responsibility at the time of selection.
   */
  private Context _editionContext;
  /**
   * The installation category to use as selected version.
   */
  private InstallationCategory _installationCategory;

  /**
   * Constructor.
   * @param installationCategory_p
   * @param context_p
   */
  public SelectVersionCommand(InstallationCategory installationCategory_p, Context context_p) {
    super(Messages.SelectVersionCommand_Label, ModelHandlerActivator.getDefault().getEditingDomain());
    setDescription(Messages.SelectVersionCommand_Description);
    _installationCategory = installationCategory_p;
    _editionContext = context_p;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.common.command.ModelModifyingCommand#doExecute()
   */
  @Override
  protected void doExecute() {
    // Precondition.
    if ((null == _editionContext) || (null == _installationCategory)) {
      return;
    }
    // Create/update selected version.
    InstallationCategory selectedVersion = ModelUtil.createOrUpdateSelectedVersionFor(_installationCategory, _editionContext);
    // Release memory.
    _installationCategory = null;
    // TODO Guillaume
    // Something should be done here, in case an error happened.
    if (null == selectedVersion) {
      return;
    }
    // Then create current version, based on selected one.
    ModelUtil.createOrUpdateCurrentVersionFor(selectedVersion);
  }
}