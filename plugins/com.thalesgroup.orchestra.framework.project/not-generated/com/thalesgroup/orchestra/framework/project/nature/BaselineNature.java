/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.project.nature;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

/**
 * @author t0076261
 */
public class BaselineNature implements IProjectNature {
  /**
   * Nature ID.
   */
  public static final String BASELINE_PROJECT_NATURE_ID = "com.thalesgroup.orchestra.framework.project.baseline"; //$NON-NLS-1$
  /**
   * Targeted project.
   */
  private IProject _project;

  /**
   * @see org.eclipse.core.resources.IProjectNature#configure()
   */
  public void configure() throws CoreException {
    // Does nothing.
  }

  /**
   * @see org.eclipse.core.resources.IProjectNature#deconfigure()
   */
  public void deconfigure() throws CoreException {
    // Does nothing
  }

  /**
   * @see org.eclipse.core.resources.IProjectNature#getProject()
   */
  public IProject getProject() {
    return _project;
  }

  /**
   * @see org.eclipse.core.resources.IProjectNature#setProject(org.eclipse.core.resources.IProject)
   */
  public void setProject(IProject project_p) {
    _project = project_p;
  }
}