/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.project;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import com.thalesgroup.orchestra.framework.common.helper.FileHelper;

import junit.framework.TestCase;

/**
 * @author s0011584
 */
@SuppressWarnings("nls")
public class ProjectBrowserTest extends TestCase {
  public void testBrowseProjectSpace() throws URISyntaxException {
    URL url = FileHelper.getFileFullUrl("com.thalesgroup.orchestra.framework.project.test/resources");
    URI uri = url.toURI();
    assertTrue(String.format("Expecting URI : '%s' to be absolute...", uri.toString()), uri.isAbsolute());
    String location = new File(uri).getAbsolutePath();
    List<RootContextsProject> contextsProject = ProjectActivator.getInstance().getProjectHandler().getValidContextsFromLocation(location);
    assertNotNull("Expecting a non null list.", contextsProject);
    assertEquals("Expecting 3 context projects.", 3, contextsProject.size());
  }
}