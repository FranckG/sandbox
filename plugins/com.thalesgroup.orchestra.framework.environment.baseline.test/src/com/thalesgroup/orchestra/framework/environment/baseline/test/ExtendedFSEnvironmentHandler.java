/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment.baseline.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.framework.environment.EnvironmentContext;
import com.thalesgroup.orchestra.framework.environment.IEnvironmentUseBaselineHandler;
import com.thalesgroup.orchestra.framework.environment.UseBaselineEnvironmentContext;
import com.thalesgroup.orchestra.framework.environment.filesystem.FileSystemEnvironmentHandler;

/**
 * @author t0076261
 */
public class ExtendedFSEnvironmentHandler extends FileSystemEnvironmentHandler {
  /**
   * @see com.thalesgroup.orchestra.framework.environment.AbstractEnvironmentHandler#createEnvironmentUseBaselineHandler()
   */
  @Override
  protected IEnvironmentUseBaselineHandler createEnvironmentUseBaselineHandler() {
    return new ExtendedFSEnvironmentStartingPointHandler();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.AbstractEnvironmentHandler#getBaselineReferenceAttributes(EnvironmentContext,
   *      org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  public Couple<Map<String, String>, IStatus> getBaselineReferenceAttributes(UseBaselineEnvironmentContext context_p, IProgressMonitor progressMonitor_p) {
    return new Couple<Map<String, String>, IStatus>(context_p._attributes, Status.OK_STATUS);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.AbstractEnvironmentHandler#getBaselineStartingPointAttributes(com.thalesgroup.orchestra.framework.environment.EnvironmentContext)
   */
  @Override
  public Couple<Map<String, String>, IStatus> getBaselineStartingPointAttributes(UseBaselineEnvironmentContext context_p, IProgressMonitor progressMonitor_p) {
    return new Couple<Map<String, String>, IStatus>(revertAttributesValues(context_p.getUseBaselineAttributes()), Status.OK_STATUS);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.filesystem.FileSystemEnvironmentHandler#isUseBaselineCompliant(com.thalesgroup.orchestra.framework.environment.EnvironmentContext)
   */
  @Override
  public boolean isUseBaselineCompliant(EnvironmentContext environmentContext_p) {
    return true;
  }

  /**
   * Revert attributes map values.
   * @param attributes_p
   * @return
   */
  protected Map<String, String> revertAttributesValues(Map<String, String> attributes_p) {
    // Precondition.
    if ((null == attributes_p) || attributes_p.isEmpty()) {
      return Collections.emptyMap();
    }
    // Create result map initialized with values of the baselined context.
    Map<String, String> result = new HashMap<String, String>(attributes_p.size());
    // On "Make Baseline", paths of directories are inverted -> revert them.
    String encodedInvertedDirectoriesList = attributes_p.get(FileSystemEnvironmentHandler.ATTRIBUTE_KEY_INPUT_DIRECTORIES);
    List<String> decodedInvertedDirectoriesList = FileSystemEnvironmentHandler.decodeValues(encodedInvertedDirectoriesList);
    List<String> decodedRevertedDirectoriesList = new ArrayList<String>(decodedInvertedDirectoriesList.size());
    for (String decodedInvertedDirectory : decodedInvertedDirectoriesList) {
      String revertedDirectory = new StringBuilder(decodedInvertedDirectory).reverse().toString();
      decodedRevertedDirectoriesList.add(revertedDirectory);
    }
    // Fill result map with the reverted directories list.
    String encodedRevertedDirectoriesList = FileSystemEnvironmentHandler.encodeValues(decodedRevertedDirectoriesList);
    result.put(FileSystemEnvironmentHandler.ATTRIBUTE_KEY_INPUT_DIRECTORIES, encodedRevertedDirectoriesList);
    return result;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.filesystem.FileSystemEnvironmentHandler#toString(java.util.Map)
   */
  @Override
  public String toString(Map<String, String> attributes_p) {
    return "MBL" + super.toString(attributes_p); //$NON-NLS-1$
  }
}