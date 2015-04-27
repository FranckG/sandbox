/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.common.helper;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;

import junit.framework.TestCase;

/**
 * @author s0011584
 */
@SuppressWarnings("nls")
public class ProjectHelperTest extends TestCase {

  public final void testCopyWorkspaceProjectToTheProjectLocationShouldBeImpossible() throws CoreException {
    IProject newProject = null;
    try {
      String projectName = "testProject";
      newProject = ProjectHelper.createAnEmptyContextsProject(projectName, null, null);
      IPath destinationLocation = Platform.getLocation();
      assertEquals("The new project should have been created in the workspace.", destinationLocation.append(projectName), new Path(new File(newProject
          .getLocationURI()).getAbsolutePath()));
      IStatus copyResult = ProjectHelper.copyProjectTo(newProject, destinationLocation.toString(), false, null);
      assertFalse("A workspace project should not succeed in copying to its own location.", copyResult.isOK());
      String expectedErrorMessage =
          MessageFormat.format(Messages.ProjectHelper_Precondition_NotInCurrentLocation_Failed, "testProject", destinationLocation.toFile());
      assertEquals(expectedErrorMessage, copyResult.getMessage());
    } finally {
      if (newProject != null) {
        newProject.delete(true, new NullProgressMonitor());
      }
    }
  }

  public final void testCopyProjectToTheProjectLocationShouldBeImpossible() throws CoreException, IOException {
    IProject newProject = null;
    try {
      String projectName = "testProject";
      String destinationLocation = System.getProperty("java.io.tmpdir");
      newProject = ProjectHelper.createAnEmptyContextsProject(projectName, destinationLocation, null);
      assertEquals("The new project should have been created in the specified location.", new File(destinationLocation).getCanonicalPath() + File.separator
                                                                                          + projectName, new File(newProject.getLocationURI()).toString());
      IStatus copyResult = ProjectHelper.copyProjectTo(newProject, destinationLocation, false, null);
      assertFalse("A project created ouside workspace should not succeed in copying to its own location.", copyResult.isOK());
      String expectedErrorMessage =
          MessageFormat
              .format(Messages.ProjectHelper_Precondition_NotInCurrentLocation_Failed, "testProject", new File(destinationLocation).getCanonicalPath());
      assertEquals(expectedErrorMessage, copyResult.getMessage());
    } finally {
      if (newProject != null) {
        newProject.delete(true, new NullProgressMonitor());
      }
    }
  }
}
