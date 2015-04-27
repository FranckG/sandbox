/**
 * <p>
 * Copyright (c) 2002 : Thales Research & Technology
 * </p>
 * <p>
 * Société : Thales Research & Technology
 * </p>
 * <p>
 * Thales Part Number 16 262 601
 * </p>
 */
package com.thalesgroup.orchestra.framework.transcription;

import java.util.Collection;
import java.util.Observable;

/**
 * AssociationSourceObservable is an abstract class to get association from source. By Observer pattern, this class maintains also consistency with memory.
 * @author Themis developer
 * @version 2.2.2
 */
public abstract class AssociationSourceObservable extends Observable {
  /**
   * Adding command
   */
  public static final Integer _ADDING = new Integer(1);

  /**
   * Removing command
   */
  public static final Integer _REMOVING = new Integer(-1);

  /**
   * Constructor for AssociationSourceObservable.
   */
  protected AssociationSourceObservable() {
    super();
  }

  /**
   * Method associationsAdded. Used for notification
   * @param iAddingAssociations
   * @throws PapeeteException
   */
  protected void associationsAdded(Collection<Association> iAddingAssociations) {
    Object[] arguments = new Object[2];
    arguments[0] = _ADDING;
    arguments[1] = iAddingAssociations;
    setChanged();
    notifyObservers(arguments);
  }

  /**
   * Method associationsRemoved. Used for notification
   * @param iRemovingAssociations
   * @throws PapeeteException
   */
  protected void associationsRemoved(Collection<Association> iRemovingAssociations) {
    Object[] arguments = new Object[2];
    arguments[0] = _REMOVING;
    arguments[1] = iRemovingAssociations;
    setChanged();
    notifyObservers(arguments);
  }

  protected abstract void upToDate();
}
