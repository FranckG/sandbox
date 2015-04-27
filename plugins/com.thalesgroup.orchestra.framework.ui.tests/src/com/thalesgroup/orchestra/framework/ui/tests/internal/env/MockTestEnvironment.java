/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.tests.internal.env;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.eclipse.core.runtime.IProgressMonitor;

import com.thalesgroup.orchestra.framework.environment.EnvironmentContext;
import com.thalesgroup.orchestra.framework.environment.filesystem.FileSystemEnvironment;
import com.thalesgroup.orchestra.framework.environment.filesystem.FileSystemEnvironmentHandler;

/**
 * @author t0122040
 */
public class MockTestEnvironment extends FileSystemEnvironment {
  /**
   * @see com.thalesgroup.orchestra.framework.environment.filesystem.FileSystemEnvironment#handleInitialAttributes(com.thalesgroup.orchestra.framework.environment.EnvironmentContext,
   *      org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  protected Map<String, String> handleInitialAttributes(EnvironmentContext context_p, IProgressMonitor progressMonitor_p) {
    Map<String, String> result = new HashMap<String, String>(0);
    // Fill directories list (variable substitution is already done in expandedAttributes map).
    result.put(FileSystemEnvironmentHandler.ATTRIBUTE_KEY_INPUT_DIRECTORIES,
        FileSystemEnvironmentHandler.encodeValues(Collections.singletonList(getRandomFolder())));
    return result;
  }

  /**
   * Return a random folder.<br>
   * @return
   */
  @SuppressWarnings("nls")
  protected String getRandomFolder() {
    String folderPath = System.getProperty("java.io.tmpdir") + "SwtBotUiTests/data/" + UUID.randomUUID().toString();
    File folder = new File(folderPath);
    if (!folder.exists()) {
      folder.mkdirs();
    }
    return folderPath;
  }

}