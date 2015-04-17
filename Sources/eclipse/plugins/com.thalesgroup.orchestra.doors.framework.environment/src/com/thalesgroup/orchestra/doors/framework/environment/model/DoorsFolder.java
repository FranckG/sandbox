/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.doors.framework.environment.model;

import org.eclipse.swt.graphics.Image;

import com.thalesgroup.orchestra.doors.framework.environment.DoorsActivator;

/**
 * @author T0052089
 */
public class DoorsFolder extends AbstractDoorsContainer {

  public DoorsFolder(AbstractDoorsContainer parent_p, String doorsId_p, String doorsName_p, boolean isVirtual_p) {
    super(parent_p, doorsId_p, doorsName_p, isVirtual_p);
  }

  /**
   * @see com.thalesgroup.orchestra.doors.framework.environment.model.DefaultModel#getImage()
   */
  @Override
  public Image getImage() {
    return DoorsActivator.getInstance().getImage("doors_folder.gif"); //$NON-NLS-1$
  }
}
