/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.connector.sdk.commands;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

/**
 * @author T0052089
 */
public class ConnectorCommandHelper {
  /**
   * Get the first selected project amongst given selection.
   * @param selection_p
   * @return
   */
  public static IProject getFirstSelectedProject(ISelection selection_p) {
    if (!(selection_p instanceof IStructuredSelection)) {
      return null;
    }
    IStructuredSelection currentStructuredSelection = (IStructuredSelection) selection_p;
    if (currentStructuredSelection.getFirstElement() instanceof IResource) {
      return ((IResource) currentStructuredSelection.getFirstElement()).getProject();
    } else if (currentStructuredSelection.getFirstElement() instanceof IJavaProject) {
      return ((IJavaProject) currentStructuredSelection.getFirstElement()).getProject();
    } else {
      return null;
    }
  }

}
