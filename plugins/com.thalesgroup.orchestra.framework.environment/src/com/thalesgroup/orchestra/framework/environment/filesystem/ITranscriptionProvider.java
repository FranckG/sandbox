/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment.filesystem;

import java.util.List;

import org.eclipse.core.runtime.IStatus;

import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;

/**
 * Transcription (and associations) provider services.<br>
 * Allows to inject this dependency even though the centralization takes place in the Framework.
 * @author t0076261
 */
public interface ITranscriptionProvider {
  /**
   * Transcript the {@link OrchestraURI}.<br/>
   * Realize physical to logical, or logical to physical transcriptions.
   * @param uri_p The {@link OrchestraURI} to transcript.
   * @param inputPaths_p List of root absolute path to use. Must not be <code>null</code> or empty.
   * @return An {@link IStatus} giving the transcription result:
   *         <ul>
   *         <li>if <code>severity</code> equals {@link IStatus#OK}, the <code>message</code> contains the transcription result;
   *         <li>otherwise <code>severity</code> equals {@link IStatus#ERROR} and the <code>message</code> contains the error message.
   */
  public IStatus transcript(OrchestraURI uri_p, List<String> inputPaths_p);
}