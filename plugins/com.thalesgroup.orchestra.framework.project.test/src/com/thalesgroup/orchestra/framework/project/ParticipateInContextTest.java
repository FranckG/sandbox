/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.project;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.thalesgroup.orchestra.framework.common.helper.FileHelper;
import com.thalesgroup.orchestra.framework.common.helper.ProjectHelper;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;

import junit.framework.TestCase;

/**
 * @author s0011584
 */
@SuppressWarnings("nls")
public class ParticipateInContextTest extends TestCase {

  private String _originalUserName;

  private void actAsOriginalUser() {
    actAsUser(_originalUserName);
  }

  private void actAsUser(String userName_p) {
    _originalUserName = System.getProperty("user.name");
    System.setProperty("user.name", userName_p);
  }

  private RootContextsProject importYYYProject() throws URISyntaxException {
    URL url = FileHelper.getFileFullUrl("com.thalesgroup.orchestra.framework.project.test/resources");
    URI uri = url.toURI();
    String location = new File(uri).getAbsolutePath();
    List<RootContextsProject> contextsProject = ProjectActivator.getInstance().getProjectHandler().getValidContextsFromLocation(location);
    RootContextsProject rootContextsProject = contextsProject.get(1);
    assertEquals("Expecting to retrieve the 'yyy' project at position 1.", "yyy", rootContextsProject._project.getName());
    return rootContextsProject;
  }

  public void testThatParticipationCreatesUserParticipation() throws URISyntaxException, CoreException {
    actAsUser("thisTGIDoesNotParticipateInProjectYYY");
    IFile file = null;
    try {
      RootContextsProject rootContextsProject = importYYYProject();
      assertTrue("Expecting to be able to participate to the project.", ModelHandlerActivator.getDefault().getDataHandler().participateInAProject("",
          rootContextsProject).isOK());
      IProject createdProject = ProjectHelper.getProject("yyy");
      assertNotNull("Expecting to find a project in workspace.", createdProject);
      IFolder folder = createdProject.getFolder("users");
      assertNotNull(folder);
      assertTrue(folder.exists());
      file = folder.getFile(System.getProperty("user.name") + ".contexts");
      assertNotNull(file);
      assertTrue("Expecting a user participation file to have been created.", file.exists());
    } finally {
      actAsOriginalUser();
      if (null != file) {
        file.delete(true, new NullProgressMonitor());
      }
      ProjectHelper.deleteProject("yyy", false);
    }
  }

  public void testThatParticipationImportContext() throws URISyntaxException {
    actAsUser("s1111111");
    try {
      RootContextsProject rootContextsProject = importYYYProject();
      IStatus participateInAProjectStatus = ModelHandlerActivator.getDefault().getDataHandler().participateInAProject(null,
          rootContextsProject);
      assertEquals("Expecting to successfully participate in project.", "OK", participateInAProjectStatus.getMessage());
      assertTrue("Expecting to successfully participate in project.", participateInAProjectStatus.isOK());
      IProject createdProject = ProjectHelper.getProject("yyy");
      assertNotNull("Expecting to find a project in workspace.", createdProject);
    } finally {
      actAsOriginalUser();
      ProjectHelper.deleteProject("yyy", false);
    }
  }

}
