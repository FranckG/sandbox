/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.handler.command;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;

import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;

/**
 * @author t0076261
 */
public class DeleteContextServiceCommand extends DeleteContextCommand {
  /**
   * Should project structure be deleted on disk, whatever mode (user/admin) and deletion flag may be ?
   */
  private boolean _forceStructureDeletion;

  /**
   * Constructor.
   * @param contexts_p The list of contexts to delete.
   * @param deleteContents_p <code>true</code> to delete contents on file system, <code>false</code> to remove from workspace only.
   */
  public DeleteContextServiceCommand(Collection<Context> contexts_p, boolean deleteContents_p) {
    this(contexts_p, deleteContents_p, false);
  }

  /**
   * Constructor.
   * @param contexts_p The list of contexts to delete.
   * @param deleteContents_p <code>true</code> to delete contents on file system, <code>false</code> to remove from workspace only.
   * @param forceStructureDeletion_p Should project structure be deleted on disk, whatever mode (user/admin) and deletion flag may be ?<br>
   *          <code>true</code> to force project deletion, <code>false</code> to leave default behavior, which is : remove only the participation in user mode,
   *          if delete contents flag is on, or remove the whole structure in admin mode, if delete contents flag is on.
   */
  public DeleteContextServiceCommand(Collection<Context> contexts_p, boolean deleteContents_p, boolean forceStructureDeletion_p) {
    super(contexts_p);
    _deleteContents = deleteContents_p;
    _forceStructureDeletion = forceStructureDeletion_p;
  }

  /**
   * Constructor.
   * @param context_p The context to delete.
   * @param deleteContents_p <code>true</code> to delete contents on file system, <code>false</code> to remove from workspace only.
   */
  public DeleteContextServiceCommand(Context context_p, boolean deleteContents_p) {
    this(Collections.singletonList(context_p), deleteContents_p);
  }

  /**
   * Constructor.
   * @param context_p The context to delete.
   * @param deleteContents_p <code>true</code> to delete contents on file system, <code>false</code> to remove from workspace only.
   * @param forceStructureDeletion_p Should project structure be deleted on disk, whatever mode (user/admin) and deletion flag may be ?<br>
   *          <code>true</code> to force project deletion, <code>false</code> to leave default behavior, which is : remove only the participation in user mode,
   *          if delete contents flag is on, or remove the whole structure in admin mode, if delete contents flag is on.
   */
  public DeleteContextServiceCommand(Context context_p, boolean deleteContents_p, boolean forceStructureDeletion_p) {
    this(Collections.singletonList(context_p), deleteContents_p, forceStructureDeletion_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.handler.command.DeleteCommandWithConfirmation#confirmDeletion(java.util.Collection, java.util.Map)
   */
  @Override
  protected boolean confirmDeletion(Collection<EObject> deletedElements_p, Map<EObject, Collection<Setting>> usages_p) {
    return true;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.handler.command.DeleteContextCommand#isInUserModeOnly()
   */
  @Override
  protected boolean isInUserModeOnly() {
    // If the delete command does invoke this method (ie user case), make sure it will call the unloadStructure() one if force structure deletion flag is set to
    // true.
    return _forceStructureDeletion || super.isInUserModeOnly();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.handler.command.DeleteContextCommand#preparePreConditions()
   */
  @Override
  protected boolean preparePreConditions() {
    return true;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.handler.command.DeleteContextCommand#unloadStructure(java.lang.String, boolean,
   *      com.thalesgroup.orchestra.framework.project.RootContextsProject)
   */
  @Override
  protected void unloadStructure(String projectName_p, boolean deleteContents_p, RootContextsProject project_p) {
    super.unloadStructure(projectName_p, deleteContents_p || _forceStructureDeletion, project_p);
  }
}