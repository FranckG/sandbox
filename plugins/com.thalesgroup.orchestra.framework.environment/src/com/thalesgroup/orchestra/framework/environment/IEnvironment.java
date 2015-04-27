/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment;

import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.SubMonitor;

import com.thalesgroup.orchestra.framework.connector.CommandContext;
import com.thalesgroup.orchestra.framework.connector.CommandStatus;
import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;
import com.thalesgroup.orchestra.framework.models.context.Artifact;

/**
 * An artifact environment services interface.
 * @author t0076261
 */
public interface IEnvironment {
  /**
   * Get metadata for specified artifacts (within provided context).
   * @param context_p
   * @param monitor_p A progress monitor, possibly <code>null</code> (if no progress is expected).<br>
   *          Rules of thumb about progress reporting (taken from http://wiki.eclipse.org/Progress_Reporting) :
   *          <ul>
   *          <li>Use {@link SubMonitor}.
   *          <li>Always begin by converting the monitor you get to a {@link SubMonitor}.
   *          <li>Use <code>newChild(n)</code> to create a sub monitor to pass to methods that take an {@link IProgressMonitor} as argument.
   *          <li>Never call <code>done</code>, but document that the callers must do so unless they used a {@link SubMonitor}.
   *          </ul>
   * @return A valid GEF model written in its xml form at specified path.
   */
  public CommandStatus getArtifactsMetadata(CommandContext context_p, IProgressMonitor progressMonitor_p);

  /**
   * Get current environment attributes.
   * @return
   */
  public Map<String, String> getAttributes();

  /**
   * Get environment declaration id. This is the environment type.
   * @return A not <code>null</code> id.
   */
  public String getDeclarationId();

  /**
   * Get associated {@link IEnvironmentHandler} implementation.
   * @return A not <code>null</code> environment handler implementation.
   */
  public IEnvironmentHandler getEnvironmentHandler();

  /**
   * Get root artifacts for specified context, based on current attributes.
   * @param context_p
   * @param monitor_p A progress monitor, possibly <code>null</code> (if no progress is expected).<br>
   *          Rules of thumb about progress reporting (taken from http://wiki.eclipse.org/Progress_Reporting) :
   *          <ul>
   *          <li>Use {@link SubMonitor}.
   *          <li>Always begin by converting the monitor you get to a {@link SubMonitor}.
   *          <li>Use <code>newChild(n)</code> to create a sub monitor to pass to methods that take an {@link IProgressMonitor} as argument.
   *          <li>Never call <code>done</code>, but document that the callers must do so unless they used a {@link SubMonitor}.
   *          </ul>
   * @return A valid GEF model written in its xml form at specified path.
   */
  public CommandStatus getRootArtifacts(CommandContext context_p, IProgressMonitor progressMonitor_p);

  /**
   * Get environment runtime id.
   * @return
   */
  public String getRuntimeId();

  /**
   * Is the environment able to create/use a baseline ?
   * @return <code>true</code> if so, <code>false</code> otherwise.
   */
  public boolean isBaselineCompliant();

  /**
   * Are artifacts within specified context handled by current environment.
   * @param context_p
   * @return A composite status, that contains one status per input artifact, with {@link CommandStatus#getSeverity()} set to {@link IStatus#OK} if the artifact
   *         is handled, or any other value if it is not handled.
   */
  public CommandStatus isHandlingArtifacts(CommandContext context_p);

  /**
   * Make baseline for current environment using current attributes.
   * @param baselineContext_p The baseline creation context.
   * @param monitor_p A progress monitor, possibly <code>null</code> (if no progress is expected).<br>
   *          Rules of thumb about progress reporting (taken from http://wiki.eclipse.org/Progress_Reporting) :
   *          <ul>
   *          <li>Use {@link SubMonitor}.
   *          <li>Always begin by converting the monitor you get to a {@link SubMonitor}.
   *          <li>Use <code>newChild(n)</code> to create a sub monitor to pass to methods that take an {@link IProgressMonitor} as argument.
   *          <li>Never call <code>done</code>, but document that the callers must do so unless they used a {@link SubMonitor}.
   *          </ul>
   * @return A not <code>null</code> {@link BaselineResult} element.
   */
  public BaselineResult makeBaseline(BaselineContext baselineContext_p, IProgressMonitor monitor_p);

  /**
   * Set environment attributes, as returned by the user interface or reloaded by the Framework model.
   * @param context_p Contains the attributes as they are currently known by context being applied.<br>
   *          In this precise case, this could be anything from raw live attributes to reference/starting point ones.<br>
   *          This is up to the environment implementation to ensure it will be able to distinguish such cases by storing different attributes for each case (if
   *          needed).<br>
   *          Note that the flag about
   * @param monitor_p A progress monitor, possibly <code>null</code> (if no progress is expected).<br>
   *          Rules of thumb about progress reporting (taken from http://wiki.eclipse.org/Progress_Reporting) :
   *          <ul>
   *          <li>Use {@link SubMonitor}.
   *          <li>Always begin by converting the monitor you get to a {@link SubMonitor}.
   *          <li>Use <code>newChild(n)</code> to create a sub monitor to pass to methods that take an {@link IProgressMonitor} as argument.
   *          <li>Never call <code>done</code>, but document that the callers must do so unless they used a {@link SubMonitor}.
   *          </ul>
   * @return Status
   */
  public IStatus setAttributes(EnvironmentContext context_p, IProgressMonitor monitor_p);

  /**
   * Transcript the artifacts of the specified {@link CommandContext}.<br/>
   * Realize physical to logical, or logical to physical transcriptions.
   * @param context_p
   * @param monitor_p A progress monitor, possibly <code>null</code> (if no progress is expected).<br>
   *          Rules of thumb about progress reporting (taken from http://wiki.eclipse.org/Progress_Reporting) :
   *          <ul>
   *          <li>Use {@link SubMonitor}.
   *          <li>Always begin by converting the monitor you get to a {@link SubMonitor}.
   *          <li>Use <code>newChild(n)</code> to create a sub monitor to pass to methods that take an {@link IProgressMonitor} as argument.
   *          <li>Never call <code>done</code>, but document that the callers must do so unless they used a {@link SubMonitor}.
   *          </ul>
   * @return A {@link CommandStatus} giving the transcription result, and containing, for each {@link Artifact} contained in specified context a child status
   *         with the following properties :
   *         <ul>
   *         <li>if {@link CommandStatus#getSeverity()} equals {@link IStatus#OK}, {@link CommandStatus#getMessage()} contains the transcription result,
   *         <li>if {@link CommandStatus#getSeverity()} equals {@link IStatus#WARNING}, the environment does not handle this artifact,
   *         <li>finally, if {@link CommandStatus#getSeverity()} equals {@link IStatus#ERROR}, the environment does handle this artifact, but an error described
   *         by {@link CommandStatus#getMessage()} occurred.
   *         </ul>
   */
  public CommandStatus transcript(CommandContext context_p, IProgressMonitor progressMonitor_p);

  /**
   * Is the environment based on logical-physical transcription for root artifacts ?
   * @return <code>true</code> if so, <code>false</code> to disable such services.
   */
  public boolean useTranscription();

  /**
   * Execute environment specific command
   * @param command_p Command name
   * @param uri_p Uri
   * @return Command status
   */
  public CommandStatus executeCommand(String command_p, OrchestraURI uri_p);

  /**
   * Does the environment support credentials?
   * @return <code>true</code> if the environment does support credentials, <code>false</code> otherwise.
   */
  public boolean areCredentialsSupported();
}