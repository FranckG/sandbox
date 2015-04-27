/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.handler.command;

import java.util.List;

import com.thalesgroup.orchestra.framework.common.command.ModelModifyingCommand;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;

/**
 * Initialize current versions command.<br>
 * Note that provided contexts might not be children of default context directly.<br>
 * A warning is then displayed to the user, because it will modify the local copy of such a parent context, but not the original one directly.<br>
 * Synchronization might then lead to a loss of versions.
 * @author t0076261
 */
public class InitializeCurrentVersionsCommand extends ModelModifyingCommand {
  /**
   * The contexts which require an initialization of their current versions.
   */
  private List<Context> _contexts;

  /**
   * Constructor.
   * @param contexts_p
   */
  public InitializeCurrentVersionsCommand(List<Context> contexts_p) {
    super(Messages.InitializeCurrentVersionsCommand_Label, ModelHandlerActivator.getDefault().getEditingDomain());
    _contexts = contexts_p;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.common.command.ModelModifyingCommand#doExecute()
   */
  @Override
  protected void doExecute() {
    // Initialize current versions for all specified contexts.
    for (Context context : _contexts) {
      boolean initialized = initializeVersionsFor(context);
      // Update current versions...
      if (initialized) {
        // ...for selected context.
        ModelUtil.addCurrentVersionsCategories(context);
      }
    }
  }

  /**
   * Initialize current versions for specified context.
   * @param context_p
   */
  protected boolean initializeVersionsFor(final Context context_p) {
    // Precondition.
    if (null == context_p) {
      return false;
    }
    // Do update it.
    ModelUtil.selectVersionsForContext(context_p);
    return true;
  }
}