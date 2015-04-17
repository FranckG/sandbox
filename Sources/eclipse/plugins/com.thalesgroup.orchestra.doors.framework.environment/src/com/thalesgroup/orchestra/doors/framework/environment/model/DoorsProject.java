/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.doors.framework.environment.model;

import org.eclipse.swt.graphics.Image;

import com.thalesgroup.orchestra.doors.framework.environment.DoorsActivator;

/**
 * @author T0052089
 */
public class DoorsProject extends AbstractDoorsContainer {
  /**
   * Logical name of the project, not unique and specified by the user
   */
  private String _logicalName;

  protected String doorsProjectId;

  public DoorsProject(String doorsURI_p, String doorsName_p, boolean isVirtual_p, String doorsProjectID_p, String doorsProjectLogicalName_p) {
    super(null, doorsURI_p, doorsName_p, isVirtual_p);
    doorsProjectId = doorsProjectID_p;
    _logicalName = doorsProjectLogicalName_p;
  }

  @Override
  public String getDoorsProjectId() {
    return doorsProjectId;
  }

  /**
   * @see com.thalesgroup.orchestra.doors.framework.environment.model.DefaultModel#getImage()
   */
  @Override
  public Image getImage() {
    return DoorsActivator.getInstance().getImage("doors_project.gif"); //$NON-NLS-1$
  }

  /**
   * @return the logicalName
   */
  public String getLogicalName() {
    return _logicalName;
  }

  public void setDoorsProjectId(String newProjectId_p) {
    doorsProjectId = newProjectId_p;
  }

  /**
   * @param logicalName_p the logicalName to set
   */
  public void setLogicalName(String logicalName_p) {
    _logicalName = logicalName_p;
  }
}
