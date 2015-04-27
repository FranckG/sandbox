/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.project;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

import com.thalesgroup.orchestra.framework.common.helper.FileHelper;
import com.thalesgroup.orchestra.framework.common.helper.ProjectHelper;
import com.thalesgroup.orchestra.framework.contextsproject.ContextsProject;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsFactory;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * @author s0011584
 */
@SuppressWarnings("nls")
public class NewProjectTest extends TestCase {
  private static final String TEST_PROJECT_NAME = "test"; //$NON-NLS-1$

  /**
   * @see junit.framework.TestCase#tearDown()
   */
  @Override
  protected void tearDown() throws Exception {
    ProjectHelper.deleteProject(TEST_PROJECT_NAME, true);
    // Projects are re-created with the same name, need to clean also the
    ModelHandlerActivator.getDefault().cleanEditingDomain();
  }

  public void testCreateOfAnEmptyContextsProjectShouldSucceed() throws Exception {
    Context createdContext = ContextsFactory.eINSTANCE.createContext();
    IProject createdProject = ProjectFactory.createNewProject(TEST_PROJECT_NAME, null, null, true);
    assertTrue("Expecting a nature for a newly created project.", createdProject.hasNature("com.thalesgroup.orchestra.framework.project"));
    createdContext.setName(TEST_PROJECT_NAME);
    ModelHandlerActivator.getDefault().getDataHandler()
        .populateContextsDescription(createdProject, createdContext, FileHelper.getFileFullUri("/test/test.contexts")); //$NON-NLS-1$
    assertNotNull("The creation should have succeeded.", createdProject);
    IPath descriptionFile = createdProject.getLocation().append(String.valueOf(IPath.SEPARATOR)).append("description.contextsproject");
    assertTrue("Expected description file '" + descriptionFile + "' to exist.", descriptionFile.toFile().exists());
    // Read and verify model.
    ContextsProject contextsProject = ProjectActivator.getInstance().getProjectHandler().getContextsProject(createdProject);
    assertNotNull("Expecting to read model successfully.", contextsProject);
    assertTrue("Expecting to get the user name in the list of adminstrators.",
        System.getProperty("user.name").equals(contextsProject.getAdministrators().get(0).getId()));
    assertFalse("Expecting a list of context references not empty.", contextsProject.getContextReferences().isEmpty());
    assertEquals("Expecting one context reference.", 1, contextsProject.getContextReferences().size());
    assertEquals("Expecting to find the context.", createdContext.getId(), contextsProject.getContextReferences().get(0).getId());
    assertNotNull("Expecting to get a non null URI.", contextsProject.getContextReferences().get(0).getUri());
  }

  public void testCreateAnEmptyContextsProjectInTheDefaultLocation() throws CoreException {
    assertNotNull("The creation should have succeed.", ProjectHelper.createAnEmptyContextsProject(TEST_PROJECT_NAME, null, "testNature"));
    IProject createdProject = ProjectHelper.getProject(TEST_PROJECT_NAME);
    assertNotNull("Expecting a not null created project.", createdProject);
    assertTrue("Expecting a nature for a newly created project.", createdProject.hasNature("testNature"));
  }

  public void testCreateAnEmptyContextsProjectInASpecialLocation() {
    Assert.assertFalse("Expecting the test project not in the workspace before tests.", ProjectHelper.getProject(TEST_PROJECT_NAME).exists());
    IProject emptyContext = ProjectHelper.createAnEmptyContextsProject(TEST_PROJECT_NAME, System.getProperty("java.io.temp"), "testNature");
    assertNotNull("Expecting a non null created project.", emptyContext);
    Assert.assertTrue(ProjectHelper.getProject(TEST_PROJECT_NAME).exists());
  }
}
