/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.common.command;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * An abstract command containing the list of modified resources.
 * @author T0052089
 */
public abstract class AbstractResourcesModifyingCommand extends AbstractCommand {
  /**
   * List of modified resources.
   */
  protected Collection<Resource> _modifiedResources;

  /**
   * Constructor.
   * @param label_p
   */
  public AbstractResourcesModifyingCommand(String label_p) {
    super(label_p);
    _modifiedResources = Collections.emptyList();
  }

  /**
   * Get the modified resources list.
   * @return an unmodifiable view of the list.
   */
  public Collection<Resource> getModifiedResources() {
    return Collections.unmodifiableCollection(_modifiedResources);
  }

  /**
   * Set the modified resources list.
   * @param modifiedResources_p
   */
  protected void setModifiedResources(Collection<Resource> modifiedResources_p) {
    // Precondition.
    if (null == modifiedResources_p)
      throw new NullPointerException();
    _modifiedResources = modifiedResources_p;
  }
}
