/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment.baseline.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;

import com.thalesgroup.orchestra.framework.connector.CommandStatus;
import com.thalesgroup.orchestra.framework.environment.BaselineContext;
import com.thalesgroup.orchestra.framework.environment.BaselineResult;
import com.thalesgroup.orchestra.framework.environment.EnvironmentContext;
import com.thalesgroup.orchestra.framework.environment.filesystem.FileSystemEnvironment;
import com.thalesgroup.orchestra.framework.environment.filesystem.FileSystemEnvironmentHandler;

/**
 * @author t0076261
 */
public class ExtendedFSEnvironment extends FileSystemEnvironment {
  /**
   * @param monitor_p
   */
  protected void eatProgress(IProgressMonitor monitor_p) {
    if (null == monitor_p) {
      return;
    }
    int slicesCount = 50;
    int awaitStep = 100 / slicesCount;
    SubMonitor subMonitor = SubMonitor.convert(monitor_p, slicesCount);
    monitor_p.subTask("Stupidly eating time..."); //$NON-NLS-1$
    for (int i = 0; i < slicesCount; i++) {
      try {
        subMonitor.newChild(1);
        Thread.sleep(awaitStep);
      } catch (InterruptedException exception_p) {
        // Ignore exception.
      }
      subMonitor.worked(1);
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.filesystem.FileSystemEnvironment#handleInitialAttributes(java.util.Map,
   *      org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  protected Map<String, String> handleInitialAttributes(EnvironmentContext context_p, IProgressMonitor progressMonitor_p) {
    eatProgress(progressMonitor_p);
    return super.handleInitialAttributes(context_p, progressMonitor_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.filesystem.FileSystemEnvironment#isBaselineCompliant()
   */
  @Override
  public boolean isBaselineCompliant() {
    return true;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.filesystem.FileSystemEnvironment#makeBaseline(com.thalesgroup.orchestra.framework.environment.BaselineContext,
   *      org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  public BaselineResult makeBaseline(BaselineContext baselineContext_p, IProgressMonitor monitor_p) {
    // Prepare result.
    BaselineResult result = new BaselineResult();
    // Get input directories.
    List<String> inputDirectories = getInputDirectories();
    List<String> resultingDirectories = new ArrayList<String>(inputDirectories.size());
    // Invert all bytes, as a result of the make baseline process (silly, but easy to pinpoint).
    for (String directory : inputDirectories) {
      byte[] originalBytes = directory.getBytes();
      int length = originalBytes.length;
      byte[] copiedBytes = new byte[length];
      for (int i = 0; i < length; i++) {
        copiedBytes[i] = originalBytes[length - 1 - i];
      }
      resultingDirectories.add(new String(copiedBytes));
    }
    // Construct resulting map.
    Map<String, String> resultingAttributes = new HashMap<String, String>(getAttributes());
    resultingAttributes.put(FileSystemEnvironmentHandler.ATTRIBUTE_KEY_INPUT_DIRECTORIES, FileSystemEnvironmentHandler.encodeValues(resultingDirectories));
    result.setReferencingAttributes(resultingAttributes);
    // Set status to OK.
    result.setStatus(new CommandStatus(Status.OK_STATUS));

    // Stupidly eat progression for test purposes.
    eatProgress(monitor_p);
    return result;
  }
}