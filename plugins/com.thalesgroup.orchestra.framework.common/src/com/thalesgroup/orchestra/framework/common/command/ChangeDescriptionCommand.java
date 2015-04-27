/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.common.command;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * An emf command that takes a change description into account.<br>
 * This allows these modifications to be undone/redone even if there were not done within a command.
 * @author t0076261
 */
public class ChangeDescriptionCommand extends AbstractResourcesModifyingCommand {
  /**
   * The change description reference.<br>
   * This must come from a change recorder that monitored modifications on the model outside a command.<br>
   * This is then used to allow for undo/redo mechanism.<br>
   * The description is expected to be the first one returned at the end of the recording session.
   */
  protected ChangeDescription _description;

  /**
   * Constructor.
   * @param description_p
   * @param label_p
   */
  public ChangeDescriptionCommand(ChangeDescription description_p, String label_p) {
    super(label_p);
    setDescription(description_p);
  }

  /**
   * Constructor.
   * @param label_p
   */
  public ChangeDescriptionCommand(String label_p) {
    super(label_p);
  }

  /**
   * @see org.eclipse.emf.common.command.AbstractCommand#canUndo()
   */
  @Override
  public boolean canUndo() {
    return (null != _description);
  }

  /**
   * @see org.eclipse.emf.common.command.AbstractCommand#dispose()
   */
  @Override
  public void dispose() {
    _description = null;
  }

  /**
   * @see org.eclipse.emf.common.command.Command#execute()
   */
  public void execute() {
    // Nothing to do here, the modification was done outside this command.
    // Only fill modified resources list.
    setModifiedResources(generateModifiedResources());
  }

  /**
   * Get resources that are modified by this command.
   * @return A not <code>null</code> but possibly empty collection of modified resources. There is no duplicate within returned collection.
   */
  protected Collection<Resource> generateModifiedResources() {
    // Result.
    HashSet<Resource> result = new HashSet<Resource>(0);
    // Get modified objects.
    // TODO Damien
    // Make sure this contains all addition cases.
    Set<EObject> modifiedObjects = _description.getObjectChanges().keySet();
    // Collect resources.
    for (EObject object : modifiedObjects) {
      Resource resource = object.eResource();
      if (null != resource) {
        result.add(resource);
      }
    }
    return result;
  }

  /**
   * @see org.eclipse.emf.common.command.AbstractCommand#prepare()
   */
  @Override
  protected boolean prepare() {
    return canUndo();
  }

  /**
   * @see org.eclipse.emf.common.command.Command#redo()
   */
  public void redo() {
    undo();
  }

  /**
   * Set change description.
   * @param description_p
   */
  protected void setDescription(ChangeDescription description_p) {
    _description = description_p;
  }

  /**
   * @see org.eclipse.emf.common.command.AbstractCommand#undo()
   */
  @Override
  public void undo() {
    _description.applyAndReverse();
  }
}