/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.tests.datamanagement.swtbotcondition;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;

import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;

/**
 * SWT Bot condition used to check if a certain number of contexts is available under the given location.
 * @author T0052089
 */
public class ContextIsCreatedCondition extends DefaultCondition {
  /**
   * Awaited number of contexts.
   */
  private final int _numberOfContexts;
  /**
   * Location where to find contexts.
   */
  private final String _projectLocation;

  /**
   * Constructor.
   * @param projectLocation_p location where to find contexts
   * @param numberOfContexts_p awaited number of contexts.
   */
  public ContextIsCreatedCondition(String projectLocation_p, int numberOfContexts_p) {
    _projectLocation = projectLocation_p;
    _numberOfContexts = numberOfContexts_p;
  }

  /**
   * @see org.eclipse.swtbot.swt.finder.waits.ICondition#getFailureMessage()
   */
  public String getFailureMessage() {
    // Generate a list of found projects.
    List<RootContextsProject> foundContextsProjects = ProjectActivator.getInstance().getProjectHandler().getValidContextsFromLocation(_projectLocation);
    List<String> foundContextsProjectNames = new ArrayList<String>(foundContextsProjects.size());
    for (RootContextsProject rootContextsProject : foundContextsProjects) {
      foundContextsProjectNames.add(rootContextsProject._project.getName());
    }
    return MessageFormat
        .format(
            "{0} context(s) should have been found under ''{1}'' (found projects list : {2})", Integer.valueOf(_numberOfContexts), _projectLocation, foundContextsProjectNames); //$NON-NLS-1$
  }

  /**
   * @see org.eclipse.swtbot.swt.finder.waits.ICondition#test()
   */
  public boolean test() throws Exception {
    return _numberOfContexts == ProjectActivator.getInstance().getProjectHandler().getValidContextsFromLocation(_projectLocation).size();
  }
}
