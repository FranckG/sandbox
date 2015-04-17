package com.thalesgroup.orchestra.doors.framework.environment.model;

import org.eclipse.swt.graphics.Image;

import com.thalesgroup.orchestra.doors.framework.environment.DoorsActivator;

/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */

/**
 * Data model of a Doors BaselineSet
 * @author S0032874
 */
public class BaselineSetModel extends AbstractDoorsElement {
  public static final String DOORS_BASELINESET_IMAGE_FILE = "doors_baselineset.gif"; //$NON-NLS-1$

  /**
   * Constructor
   * @param doorsId_p The Doors URI
   * @param name_p The Doors name
   */
  public BaselineSetModel(BaselineSetDefinitionModel parent_p, String doorsId_p, String name_p, boolean isVirtual_p) {
    super(parent_p, doorsId_p, name_p, isVirtual_p);
  }

  /**
   * @see IDoorsTreeElement#getChildren()
   */
  @Override
  public Object[] getChildren() {
    return new Object[0];
  }

  /**
   * @see com.thalesgroup.orchestra.doors.framework.environment.model.DefaultModel#getImage()
   */
  @Override
  public Image getImage() {
    return DoorsActivator.getInstance().getImage(DOORS_BASELINESET_IMAGE_FILE);
  }

  /**
   * @see com.thalesgroup.orchestra.doors.framework.environment.model.AbstractDoorsElement#getParent()
   */
  @Override
  public BaselineSetDefinitionModel getParent() {
    return (BaselineSetDefinitionModel) super.getParent();
  }

  /**
   * @see IDoorsTreeElement#hasChildren()
   */
  @Override
  public boolean hasChildren() {
    return false;
  }

}
