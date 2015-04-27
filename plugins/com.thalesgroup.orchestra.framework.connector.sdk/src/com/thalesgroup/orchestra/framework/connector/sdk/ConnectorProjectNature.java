package com.thalesgroup.orchestra.framework.connector.sdk;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

/**
 * Framework connector project nature.
 * @author T0052089
 */
public class ConnectorProjectNature implements IProjectNature {
  /**
   * Project nature id.
   */
  public static final String CONNECTOR_PROJECT_NATURE_ID = "com.thalesgroup.orchestra.connector"; //$NON-NLS-1$
  /**
   * 
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
    // Does nothing.
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
