package com.thalesgroup.orchestra.doors.framework.environment.model;

import org.eclipse.swt.graphics.Image;

/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */

/**
 * @author S0032874 Default model used to describe an element retrieved from a Doors database
 */
public abstract class AbstractDoorsElement {

  /**
   * Free text to provide a description to the model
   */
  protected String _description;

  /**
   * Name provided by DOORS.
   */
  protected String _doorsName;

  /**
   * Unique URI of the element
   */
  protected String _doorsURI;

  /**
   * True if the element was created by the user, may not be present in a database
   */
  protected boolean _isVirtual;

  protected final AbstractDoorsElement _parent;

  public AbstractDoorsElement(AbstractDoorsElement parent_p, String doorsURI_p, String doorsName_p, boolean isVirtual_p) {
    _parent = parent_p;
    _doorsURI = doorsURI_p;
    _doorsName = doorsName_p;
    _isVirtual = isVirtual_p;
  }

  /**
   * Retrieve the children of an element
   * @return
   */
  public abstract Object[] getChildren();

  /**
   * @return the description
   */
  public String getDescription() {
    return _description;
  }

  /**
   * @return the doorsName
   */
  public String getDoorsName() {
    return _doorsName;
  }

  /**
   * Unique Doors ID of the project carrying this model
   * @return The fatherProjectDoorsId
   */
  public String getDoorsProjectId() {
    return _parent.getDoorsProjectId();
  }

  /**
   * @return the doorsId
   */
  public String getDoorsURI() {
    return _doorsURI;
  }

  public abstract Image getImage();

  public AbstractDoorsElement getParent() {
    return _parent;
  }

  /**
   * Check if an element has children
   * @return
   */
  public abstract boolean hasChildren();

  /**
   * Flag
   * @return True if the element was created by the user
   */
  public boolean isVirtual() {
    return _isVirtual;
  }

  /**
   * @param description_p the description to set
   */
  public void setDescription(String description_p) {
    _description = description_p;
  }

  /**
   * @param doorsName_p the doorsName to set
   */
  public void setDoorsName(String doorsName_p) {
    _doorsName = doorsName_p;
  }

  /**
   * @param doorsId_p the doorsId to set
   */
  public void setDoorsURI(String doorsURI_p) {
    _doorsURI = doorsURI_p;
  }

  /**
   * @param isVirtual_p the isVirtual to set
   */
  public void setVirtual(boolean isVirtual_p) {
    _isVirtual = isVirtual_p;
  }
}
