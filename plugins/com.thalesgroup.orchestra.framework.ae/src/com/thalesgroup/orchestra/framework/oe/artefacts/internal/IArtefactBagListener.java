/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.oe.artefacts.internal;

/**
 * Interface to implement to be notified by artefact bag modification.
 * @author S0024585
 */
public interface IArtefactBagListener {

  /**
   * A new artefact is added. This event is not launched for a full bag modification.
   */
  public void artefactAdded(Artefact artefactAdded_p);

  /**
   * An artefact has been modified.
   */
  public void artefactModified(Artefact modifiedArtefact_p);

  /**
   * The bag is globally modified.
   */
  public void fullBagModified();

}
