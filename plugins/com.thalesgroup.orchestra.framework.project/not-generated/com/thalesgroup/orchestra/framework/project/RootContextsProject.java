/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.project;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.contextsproject.Administrator;
import com.thalesgroup.orchestra.framework.contextsproject.ContextReference;
import com.thalesgroup.orchestra.framework.contextsproject.ContextsProject;
import com.thalesgroup.orchestra.framework.contextsproject.ContextsProjectFactory;
import com.thalesgroup.orchestra.framework.project.nature.BaselineNature;

/**
 * The container of the project and its contexts.
 * @author s0011584
 */
public class RootContextsProject {
  /**
   * The name of the directory in a project which contains users customizations.
   */
  public static final String USERS_DIR = "users"; //$NON-NLS-1$
  /**
   * The description of the contexts within the project.
   */
  public ContextsProject _contextsProject;
  /**
   * The project description associated to the project.<br>
   * Note that this is not necessarily the description contained in the project since this one might not belong to the workspace yet.
   */
  public IProjectDescription _description;
  /**
   * The project hosting available contexts.
   */
  public IProject _project;

  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (null == obj)
      return false;
    if (getClass() != obj.getClass())
      return false;
    RootContextsProject other = (RootContextsProject) obj;
    String location = getLocation();
    String objLocation = other.getLocation();
    String projectName = _project.getName();
    String objProjectName = other._project.getName();
    if (null != location) {
      if (!location.equals(objLocation)) {
        return false;
      }
    } else if ((null != objLocation)) {
      return false;
    }
    if (!projectName.equals(objProjectName)) {
      return false;
    }
    return true;
  }

  /**
   * Get administration context defined at this project level.<br>
   * Caller does not have to have the administration rights on this context to get its reference. return <code>null</code> if no context is defined at project
   * level, the {@link ContextReference} otherwise.
   */
  public ContextReference getAdministratedContext() {
    // Precondition
    if (_contextsProject.getContextReferences().isEmpty()) {
      return null;
    }
    return _contextsProject.getContextReferences().get(0);
  }

  /**
   * Get editable context for this project.<br>
   * This is either the administration context, with current user registered as administrator, or a participation to the project (as a user).
   * @return <code>null</code> if no context is defined at project level, the {@link ContextReference} otherwise.
   */
  public ContextReference getContext() {
    return getContext(true);
  }

  /**
   * Get editable context for this project.<br>
   * This is either the administration context, with current user registered as administrator, or a participation to the project (as a user).
   * @param checkAdministrator_p Check that user id belong to administrators of the context
   * @return <code>null</code> if no context is defined at project level, the {@link ContextReference} otherwise.
   */
  public ContextReference getContext(boolean checkAdministrator_p) {
    // Default Context.
    if (null == _project) {
      return getAdministratedContext();
    }
    boolean administrator = ProjectActivator.getInstance().isAdministrator();
    String userId = ProjectActivator.getInstance().getCurrentUserId();
    if (administrator) {
      // Select the context only if current user belongs to administrators.
      if (!checkAdministrator_p || isAdministrator(userId)) {
        return getAdministratedContext();
      }
    } else {
      // Find the user participation.
      String userContextUri = getUserContextUri(userId);
      if (null != userContextUri) {
        ContextReference contextReference = ContextsProjectFactory.eINSTANCE.createContextReference();
        contextReference.setUri(userContextUri);
        return contextReference;
      }
    }
    return null;
  }

  /**
   * Get project absolute location.
   * @return <code>null</code> if the project could not be located.
   */
  public String getLocation() {
    // Get location from description first.
    java.net.URI locationURI = _description.getLocationURI();
    // Then from the project.
    if (null == locationURI) {
      locationURI = _project.getLocationURI();
    }
    // No location.
    if (null == locationURI) {
      return null;
    }
    // Try to canonicalize the path.
    String rootContextProjectPath = locationURI.getPath();
    try {
      rootContextProjectPath = new File(locationURI.getPath()).getCanonicalPath();
    } catch (IOException ex) {
      // Can't canonicalize the path, keep original path.
    }
    return new Path(rootContextProjectPath).removeTrailingSeparator().toString();
  }

  /**
   * Get specified user context {@link URI} as a {@link String}.
   * @param userId_p
   * @return <code>null</code> if no participation for specified user could be found, <code>true</code> otherwise.
   */
  public String getUserContextUri(String userId_p) {
    IFile file = _project.getFile(new Path(getUserContextPath(userId_p)));
    // If file.exists() is used IResource.refreshLocal() should be called to refresh the workspace tree.
    if ((null != file) && file.getLocation().toFile().exists()) {
      return URI.createPlatformResourceURI(file.getFullPath().toString(), true).toString();
    }
    return null;
  }

  /**
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    String location = getLocation();
    String projectName = _project.getName();
    final int prime = 31;
    int result = 1;
    result = prime * result + ((location == null) ? 0 : location.hashCode());
    result = prime * result + ((projectName == null) ? 0 : projectName.hashCode());
    return result;
  }

  /**
   * Is specified user belonging to administrators of this project ?
   * @param userId_p
   * @return
   */
  public boolean isAdministrator(String userId_p) {
    // Precondition.
    if (null == userId_p) {
      return false;
    }
    for (Administrator admin : _contextsProject.getAdministrators()) {
      if (userId_p.equalsIgnoreCase(admin.getId())) {
        return true;
      }
    }
    return false;
  }

  /**
   * Is project representing a baseline context ?
   * @return <code>true</code> if so, <code>false</code> if not, or an error occurred.
   */
  public boolean isBaseline() {
    try {
      // Try with description first.
      if (null != _description) {
        return _description.hasNature(BaselineNature.BASELINE_PROJECT_NATURE_ID);
      }
      // Then with project.
      if (null != _project) {
        return _project.hasNature(BaselineNature.BASELINE_PROJECT_NATURE_ID);
      }
    } catch (CoreException exception_p) {
      return false;
    }
    return false;
  }

  /**
   * Is element handling specified project, as pointed by its name ?
   * @param projectName_p
   * @return <code>false</code> if parameter is invalid, or if specified project is not handled.
   */
  public boolean isHandling(String projectName_p) {
    if (null == projectName_p) {
      return false;
    }
    return projectName_p.equals(_project.getName());
  }

  /**
   * Is element handling specified context, as pointed by its {@link URI} ?
   * @param contextUri_p
   * @return <code>false</code> if parameter is invalid, or no such context could be found.
   */
  public boolean isHandling(URI contextUri_p) {
    if (null == contextUri_p) {
      return false;
    }
    ContextReference context = getContext();
    if ((null != context) && contextUri_p.toString().equals(context.getUri())) {
      return true;
    }
    return false;
  }

  /**
   * Is project located in the workspace ?
   * @return
   */
  public boolean isInWorkspace() {
    return (null == _description.getLocationURI());
  }

  /**
   * Build the local path to the user context for current user ID.
   * @return
   */
  public static String getUserContextPath() {
    return getUserContextPath(ProjectActivator.getInstance().getCurrentUserId());
  }

  /**
   * Build the local path to the user context for specified user ID.
   * @param userId_p
   * @return
   */
  public static String getUserContextPath(String userId_p) {
    // Precondition.
    if (null == userId_p) {
      return null;
    }
    StringBuilder sb = new StringBuilder();
    sb.append(IPath.SEPARATOR).append(USERS_DIR).append(IPath.SEPARATOR).append(userId_p).append(ICommonConstants.POINT_CHARACTER)
        .append(ICommonConstants.FILE_EXTENSION_CONTEXTS);
    return sb.toString();
  }
}