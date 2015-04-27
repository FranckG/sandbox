/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.project;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.List;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Platform;

import com.thalesgroup.orchestra.framework.common.helper.FileHelper;
import com.thalesgroup.orchestra.framework.common.helper.ProjectHelper;

import junit.framework.TestCase;

/**
 * @author s0011584
 */
@SuppressWarnings("nls")
public class ImportProjectTest extends TestCase {
  private String _location;

  /**
   * @see junit.framework.TestCase#setUp()
   */
  @Override
  protected void setUp() throws Exception {
    super.setUp();
    URL url = FileHelper.getFileFullUrl("com.thalesgroup.orchestra.framework.project.test/resources");
    URI uri = url.toURI();
    _location = new File(uri).getAbsolutePath();
  }

  /**
   * @see junit.framework.TestCase#tearDown()
   */
  @Override
  protected void tearDown() throws Exception {
    assertTrue("Expecting to delete test project from workspace with success.", ProjectHelper.deleteProject("yyy", false));
  }

  public void testImportUnreferencedProjectAlreadyPhysicallyInWorkspace() {
    List<RootContextsProject> contextsProject;
    RootContextsProject rootContextsProject;

    contextsProject = ProjectActivator.getInstance().getProjectHandler().getValidContextsFromLocation(_location);
    assertEquals("Expecting to find 3 test projects in the location.", 3, contextsProject.size());
    rootContextsProject = contextsProject.get(1);
    assertEquals("Expected to be the right project at position 1.", "yyy", rootContextsProject.getAdministratedContext().getName());
    assertTrue("Expected to import project successfully.", ProjectHelper.copyProjectAndImportToWorkspace(rootContextsProject._project,
        rootContextsProject._description).isOK());
    assertTrue("Expected to dereference project successfully.", ProjectHelper.deleteProject("yyy", false));

    contextsProject =
        ProjectActivator.getInstance().getProjectHandler().getValidContextsFromLocation(ResourcesPlugin.getWorkspace().getRoot().getLocation().toString());
    assertEquals("Expecting to find 1 test projects in the location.", 1, contextsProject.size());
    rootContextsProject = contextsProject.get(0);
    // Update project description.
    assertTrue("Expected to import project successfully.", ProjectHelper.importExistingProject(rootContextsProject._project, rootContextsProject._description)
        .isOK());
  }

  public void testIsPhysicallyInWorkspace() throws Exception {
    List<RootContextsProject> contextsProject;
    RootContextsProject rootContextsProject;

    contextsProject = ProjectActivator.getInstance().getProjectHandler().getValidContextsFromLocation(_location);
    rootContextsProject = contextsProject.get(1);
    assertEquals("Expecting to find the 'yyy' project at position 1.", "yyy", rootContextsProject._project.getName());
    assertFalse("Project from bundle should not be physically in workspace.", ProjectHelper.isPhysicallyInWorkspace(rootContextsProject._description
        .getLocationURI()));

    assertTrue("Expected to import project successfully.", ProjectHelper.copyProjectAndImportToWorkspace(rootContextsProject._project,
        rootContextsProject._description).isOK());
    contextsProject = ProjectActivator.getInstance().getProjectHandler().getValidContextsFromLocation(Platform.getLocation().toString());
    rootContextsProject = contextsProject.get(0);
    assertTrue("Expecting the project to be physically in workspace.", Platform.getLocation().isPrefixOf(rootContextsProject._project.getLocation()));
    assertTrue("Project imported in workspace should be physically in workspace.", ProjectHelper.isPhysicallyInWorkspace(rootContextsProject._description
        .getLocationURI()));
  }

}
