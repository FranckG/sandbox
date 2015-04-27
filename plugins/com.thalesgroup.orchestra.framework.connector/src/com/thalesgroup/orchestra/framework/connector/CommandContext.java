/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.connector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.Assert;

import com.thalesgroup.orchestra.framework.lib.constants.ICommandConstants;

/**
 * A command execution context.<br>
 * Defines both the command :
 * <ul>
 * <li>The inputs : the applicable artifacts list, the command type, ...
 * <li>The outputs : the export file path, ...
 * </ul>
 * @author t0076261
 */
public class CommandContext {
  /**
   * Input artifacts list.
   */
  private List<Artifact> _artifacts;
  /**
   * Command type, from {@link ICommandConstants#CREATE} to {@link ICommandConstants#NAVIGATE}.<br>
   * Can be a new type outside this range if required.
   */
  private String _commandType;
  /**
   * The export file absolute path.<br>
   * This is a generated path.<br>
   * Expected content is a Generic Export Format XML model.
   */
  private String _exportFilePath;

  private boolean _keepOpen;

  /**
   * Arguments to be passed to application launchers.
   */
  private String _launchArguments;

  /**
   * Constructor.
   * @param commandType_p A not <code>null</code> command type. Either from {@link ICommandConstants#CREATE} to {@link ICommandConstants#NAVIGATE} for default
   *          Orchestra commands, or a unique command type for external ones.
   * @param exportFilePath_p An export file absolute path, or <code>null</code> if this is not an export command.
   */
  public CommandContext(String commandType_p, String exportFilePath_p, boolean keepOpen_p, String launchedArguments_p) {
    Assert.isNotNull(commandType_p);
    _commandType = commandType_p;
    _exportFilePath = exportFilePath_p;
    _artifacts = new ArrayList<Artifact>(0);
    _keepOpen = keepOpen_p;
    _launchArguments = launchedArguments_p;
  }

  public CommandContext(String commandType_p, String exportFilePath_p) {
    this(commandType_p, exportFilePath_p, false, null);
  }

  /**
   * Add a new artifact.
   * @param artifact_p
   */
  public void addArtifact(Artifact artifact_p) {
    if (null != artifact_p) {
      _artifacts.add(artifact_p);
    }
  }

  /**
   * Add new artifacts.
   * @param artifacts_p
   */
  public void addArtifacts(Collection<Artifact> artifacts_p) {
    // Precondition.
    if (null == artifacts_p) {
      return;
    }
    _artifacts.addAll(artifacts_p);
  }

  /**
   * Get input artifacts.
   * @return A not <code>null</code> but possibly empty array of artifacts (as a command input).
   */
  public Artifact[] getArtifacts() {
    return _artifacts.toArray(new Artifact[_artifacts.size()]);
  }

  /**
   * Get command type.
   * @return A not <code>null</code> command type. Either from {@link ICommandConstants#CREATE} to {@link ICommandConstants#NAVIGATE} for default Orchestra
   *         commands, or a unique command type for external ones.
   */
  public String getCommandType() {
    return _commandType;
  }

  /**
   * Get export file absolute path.<br>
   * This is not mandatory to use this path within a command execution.<br>
   * Still if the path is used, its content must be a Generic Export Formal XML model.
   * @return <code>null</code> if specified command does not expect a GEF model as a result, a not <code>null</code> GEF model absolute path otherwise.
   */
  public String getExportFilePath() {
    return _exportFilePath;
  }

  public boolean isKeepOpen() {
    return _keepOpen;
  }

  /**
   * Get arguments to be passed to launchers
   * @return <code>null</code> when there are no arguments to be passed, a string otherwise
   */
  public String getLaunchArguments() {
    return _launchArguments;
  }

  /**
   * Remove specified artifact from context.
   * @param artifact_p
   */
  public void removeArtifact(Artifact artifact_p) {
    if (null != artifact_p) {
      _artifacts.remove(artifact_p);
    }
  }
}