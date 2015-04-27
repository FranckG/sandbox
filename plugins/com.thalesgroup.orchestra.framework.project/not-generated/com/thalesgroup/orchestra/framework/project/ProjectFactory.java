/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.project;

import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

import com.thalesgroup.orchestra.framework.common.helper.ProjectHelper;
import com.thalesgroup.orchestra.framework.contextsproject.Administrator;
import com.thalesgroup.orchestra.framework.contextsproject.ContextsProject;
import com.thalesgroup.orchestra.framework.contextsproject.ContextsProjectFactory;
import com.thalesgroup.orchestra.framework.project.nature.ProjectNature;

/**
 * Manage creation of context projects.
 * @author s0011584
 */
public class ProjectFactory {
  /**
   * 
   */
  public static final String DEFAULT_CONTEXTS_PROJECT_DESCRIPTION = "description.contextsproject"; //$NON-NLS-1$

  /**
   * Create a new context project.
   * @param projectName_p
   * @param newProjectLocation_p can be <code>null</code> if new project is to be created in the workspace directly.
   * @param parentProjectUri_p can be <code>null</code> if new project is not initialized from another project.
   * @param shouldAddCurrentUserAsAdministrator_p <code>true</code> to add current user as the administrator for new project, <code>false</code> to leave
   *          administrators list empty.
   * @return <code>null</code> if project could not be created for specified parameters, or an error occurred.
   */
  public static IProject createNewProject(String projectName_p, String newProjectLocation_p, URI parentProjectUri_p, boolean shouldAddCurrentUserAsAdministrator_p) {
    IProject project = ProjectHelper.createAnEmptyContextsProject(projectName_p, newProjectLocation_p, ProjectNature.CONTEXT_PROJECT_NATURE_ID);
    // Precondition.
    if (null == project) {
      return null;
    }
    // Create contexts project description resource.
    URI resourceUri = ProjectActivator.getInstance().getProjectHandler().getContextsProjectDescriptionUri(project);
    ProjectsEditingDomain editingDomain = ProjectActivator.getInstance().getEditingDomain();
    ResourceSet resourceSet = editingDomain.getResourceSet();
    Resource resource = resourceSet.createResource(resourceUri);
    // Create root element.
    ContextsProject contextsProject = ContextsProjectFactory.eINSTANCE.createContextsProject();
    // Add parent project URI, if any.
    if (null != parentProjectUri_p) {
      contextsProject.setParentProject(parentProjectUri_p.toFileString());
    }
    // Add administrator informations.
    if (shouldAddCurrentUserAsAdministrator_p) {
      Administrator administrator = ContextsProjectFactory.eINSTANCE.createAdministrator();
      administrator.setId(ProjectActivator.getInstance().getCurrentUserId());
      contextsProject.getAdministrators().add(administrator);
    }
    // Add element to resource.
    resource.getContents().add(contextsProject);
    // Save it.
    try {
      editingDomain.saveResource(resource);
    } catch (IOException ioException_p) {
      // Could not save contents, hence remove the project.
      ProjectHelper.deleteProject(projectName_p, true);
      // And return null.
      return null;
    }
    return project;
  }
}