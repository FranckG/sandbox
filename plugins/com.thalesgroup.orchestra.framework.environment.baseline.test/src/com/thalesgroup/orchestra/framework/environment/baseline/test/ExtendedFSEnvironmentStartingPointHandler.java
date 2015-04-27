/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment.baseline.test;

import java.io.File;
import java.util.List;

import com.thalesgroup.orchestra.framework.environment.DefaultEnvironmentUseBaselineHandler;
import com.thalesgroup.orchestra.framework.environment.UseBaselineEnvironmentContext;
import com.thalesgroup.orchestra.framework.environment.filesystem.FileSystemEnvironmentHandler;
import com.thalesgroup.orchestra.framework.environment.ui.AbstractEnvironmentUseBaselineViewer;

/**
 * @author T0052089
 */
public class ExtendedFSEnvironmentStartingPointHandler extends DefaultEnvironmentUseBaselineHandler {
  /**
   * @see com.thalesgroup.orchestra.framework.environment.AbstractEnvironmentHandler#getStartingPointViewer(com.thalesgroup.orchestra.framework.environment.UseBaselineEnvironmentContext)
   */
  @Override
  public AbstractEnvironmentUseBaselineViewer getStartingPointViewer(UseBaselineEnvironmentContext startingPointContext_p) {
    startingPointContext_p._useBaselineAttributes = startingPointContext_p._attributes;
    return new ExtendedFSEnvStartingPointViewer(startingPointContext_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.DefaultEnvironmentUseBaselineHandler#isSameUseBaselineContext(com.thalesgroup.orchestra.framework.environment.UseBaselineEnvironmentContext,
   *      com.thalesgroup.orchestra.framework.environment.UseBaselineEnvironmentContext)
   */
  @Override
  public boolean isSameUseBaselineContext(UseBaselineEnvironmentContext envCtx1_p, UseBaselineEnvironmentContext envCtx2_p) {
    // Check env IDs are identical.
    if (!envCtx1_p._environmentId.equals(envCtx2_p._environmentId)) {
      return false;
    }
    // Check attributes are identical.
    if (!envCtx1_p._attributes.equals(envCtx2_p._attributes)) {
      return false;
    }
    return true;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.AbstractEnvironmentHandler#isStartingPointContextValid(com.thalesgroup.orchestra.framework.environment.UseBaselineEnvironmentContext)
   */
  @Override
  public boolean isStartingPointContextValid(UseBaselineEnvironmentContext envCtx_p) {
    String encodedDirectoriesList = envCtx_p.getUseBaselineAttributes().get(FileSystemEnvironmentHandler.ATTRIBUTE_KEY_INPUT_DIRECTORIES);
    List<String> decodedDirectoriesList = FileSystemEnvironmentHandler.decodeValues(encodedDirectoriesList);
    for (String decodedDirectory : decodedDirectoriesList) {
      String revertedDiretoryPath = new StringBuilder(decodedDirectory).reverse().toString();
      File directory = new File(revertedDiretoryPath);
      if (!directory.isDirectory()) {
        return false;
      }
    }
    return true;
  }
}
