/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.project;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;

import com.thalesgroup.orchestra.framework.common.helper.FileHelper;
import com.thalesgroup.orchestra.framework.common.helper.ProjectHelper;

import junit.framework.TestCase;

/**
 * @author s0011584
 */
@SuppressWarnings("nls")
public class ExportProjectTest extends TestCase {

  private String _destinationLocation;
  private File _tempDir;
  private IProject _workspaceProject;
  private IProject _workspaceProject2;

  private void assertProjectExportedSuccessfully(String name_p) {
    List<RootContextsProject> validContextsFromLocation =
        ProjectActivator.getInstance().getProjectHandler().getValidContextsFromLocation(_tempDir.getAbsolutePath());
    assertEquals("Expecting to find the exported context in the destination directory.", 1, validContextsFromLocation.size());
    RootContextsProject exportedContext = validContextsFromLocation.get(0);
    assertNotNull("Expecting a non null exported context.", exportedContext);
    assertEquals("Expecting to have exported the right context.", name_p, exportedContext.getAdministratedContext().getName());
  }

  /**
   * @see junit.framework.TestCase#setUp()
   */
  @Override
  protected void setUp() throws Exception {
    super.setUp();
    URL url = FileHelper.getFileFullUrl("com.thalesgroup.orchestra.framework.project.test/resources");
    URI uri = url.toURI();
    String location = new File(uri).getAbsolutePath();
    List<RootContextsProject> contextsProject = ProjectActivator.getInstance().getProjectHandler().getValidContextsFromLocation(location);
    assertEquals("Expecting to find 3 test projects in the location.", 3, contextsProject.size());
    RootContextsProject rootContextsProject;
    rootContextsProject = contextsProject.get(0);
    assertEquals("Expected to be the right project at position 0.", "emptyProjectWithoutParticipation", rootContextsProject.getAdministratedContext().getName());
    ProjectHelper.copyProjectAndImportToWorkspace(rootContextsProject._project, rootContextsProject._description);
    rootContextsProject = contextsProject.get(1);
    assertEquals("Expected to be the right project at position 1.", "yyy", rootContextsProject.getAdministratedContext().getName());
    ProjectHelper.copyProjectAndImportToWorkspace(rootContextsProject._project, rootContextsProject._description);

    _destinationLocation = System.getProperty("java.io.tmpdir") + UUID.randomUUID().toString();
    _tempDir = new File(_destinationLocation);
    assertTrue("Expecting to be able to create an empty directory in the temp dir.", _tempDir.mkdir());
    _workspaceProject = ProjectHelper.getProject("yyy");
    _workspaceProject2 = ProjectHelper.getProject("emptyProjectWithoutParticipation");
    assertNotNull("Expecting an imported workspace project.", _workspaceProject);
  }

  /**
   * @see junit.framework.TestCase#tearDown()
   */
  @Override
  protected void tearDown() throws Exception {
    assertTrue("Expecting to delete test project from workspace with success.", ProjectHelper.deleteProject("yyy", true));
    assertTrue("Expecting to delete test project from workspace with success.", ProjectHelper.deleteProject("emptyProjectWithoutParticipation", true));
    if (null != _tempDir) {
      FileUtils.deleteDirectory(_tempDir);
    }
  }

  public void testExportProjectToALocationThatAlreadyContainsADirectoryNamedAsTheProject() throws IOException {
    File subDir = new File(_tempDir, "yyy");
    assertTrue("Expecting to be able to create temp sub directory", subDir.mkdir());
    File subFile = new File(subDir, "empty.log");
    assertTrue("Expecting to be able to create an empty file in the sub directory.", subFile.createNewFile());
    IStatus exportStatus;
    exportStatus = ProjectActivator.getInstance().getProjectHandler().exportProjectTo(_workspaceProject, _destinationLocation, false);
    String expectedWarningMessage = MessageFormat.format(Messages.ProjectHandler_Export_Warning_ExistingFolder, "yyy");
    assertEquals("Expecting a WARNING message.", expectedWarningMessage, exportStatus.getMessage());
    assertFalse("Expecting a failed export.", exportStatus.isOK());
    // Simulate user choice to confirm the export.
    exportStatus = ProjectActivator.getInstance().getProjectHandler().exportProjectTo(_workspaceProject, _destinationLocation, true);
    assertEquals("Expecting an empty message.", "OK", exportStatus.getMessage());
    assertTrue("Expecting an sucessfull export.", exportStatus.isOK());
    assertProjectExportedSuccessfully("yyy");
    assertFalse("Expecting files previously present in directory to have been removed.", subFile.exists());
  }

  public void testExportProjectToALocationThatAlreadyContainsTheProject() throws Exception {
    // Re-execute test to have the project exported in the destination.
    testExportProjectToAnEmptyLocation();
    File projectDestination = new File(_tempDir, "yyy");
    assertTrue("Expecting to have a directory.", projectDestination.exists());
    File subFile = new File(projectDestination, "empty.log");
    assertTrue("Expecting to be able to create an empty file in the sub directory.", subFile.createNewFile());
    File userDirectory = new File(projectDestination, "users");
    assertTrue("Expecting to have a user directory.", userDirectory.exists());
    File userFile = new File(userDirectory, "empty.log");
    assertTrue("Expecting to be able to create an empty file in the user directory.", userFile.createNewFile());
    IStatus exportStatus;
    exportStatus = ProjectActivator.getInstance().getProjectHandler().exportProjectTo(_workspaceProject, _destinationLocation, false);
    String expectedWarningMessage = MessageFormat.format(Messages.ProjectHandler_Export_Warning_ExistingProject, "yyy");
    assertEquals("Expecting to retrieve a warning message.", expectedWarningMessage, exportStatus.getMessage());
    assertFalse("Expecting to not being able to re-export on the same location", exportStatus.isOK());
    assertTrue("Expecting files previously present in directory to not have been removed.", subFile.exists());
    exportStatus = ProjectActivator.getInstance().getProjectHandler().exportProjectTo(_workspaceProject, _destinationLocation, true);
    assertEquals("Expecting no particular message.", "OK", exportStatus.getMessage());
    assertTrue("Expecting a sucessfull export.", exportStatus.isOK());
    assertProjectExportedSuccessfully("yyy");
    assertFalse("Expecting files previously present in directory to have been removed.", subFile.exists());
    assertTrue("Expecting files previously present in user directory to have not been removed.", userFile.exists());
  }

  public void testExportProjectToAnEmptyLocation() {
    IStatus exportStatus = ProjectActivator.getInstance().getProjectHandler().exportProjectTo(_workspaceProject, _destinationLocation, false);
    assertEquals("Expecting an OK message.", "OK", exportStatus.getMessage());
    assertTrue("Expecting an export with success.", exportStatus.isOK());
    assertProjectExportedSuccessfully("yyy");
  }

  public void testExportProjectWithNoParticipationToAnEmptyLocation() {
    IStatus exportStatus = ProjectActivator.getInstance().getProjectHandler().exportProjectTo(_workspaceProject2, _destinationLocation, false);
    assertEquals("Expecting an OK message.", "OK", exportStatus.getMessage());
    assertTrue("Expecting an export with success.", exportStatus.isOK());
    assertProjectExportedSuccessfully("emptyProjectWithoutParticipation");
  }
}