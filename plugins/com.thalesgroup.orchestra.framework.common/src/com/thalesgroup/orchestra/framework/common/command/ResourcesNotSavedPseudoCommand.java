/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.common.command;

import java.util.Collection;

import org.eclipse.emf.ecore.resource.Resource;

/**
 * This pseudo command is placed in the command stack when one or several contexts couldn't be saved.
 * @author T0052089
 */
public class ResourcesNotSavedPseudoCommand extends AbstractResourcesModifyingCommand {

  /**
   * Constructor.
   * @param label_p
   * @param notSavedResources_p list of resources that couldn't be saved.
   */
  public ResourcesNotSavedPseudoCommand(String label_p, Collection<Resource> notSavedResources_p) {
    super(label_p);
    setModifiedResources(notSavedResources_p);
  }

  /**
   * @see org.eclipse.emf.common.command.Command#execute()
   */
  public void execute() {
    // Nothing to do.
  }

  /**
   * @see org.eclipse.emf.common.command.Command#redo()
   */
  public void redo() {
    // Nothing to do.
  }

  /**
   * @see org.eclipse.emf.common.command.AbstractCommand#prepare()
   */
  @Override
  protected boolean prepare() {
    return true;
  }

  /**
   * @see org.eclipse.emf.common.command.AbstractCommand#canUndo()
   */
  @Override
  public boolean canUndo() {
    return false;
  }
}
