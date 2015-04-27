/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.remote.services.context;

import org.eclipse.core.runtime.IStatus;

/**
 * A context change listener implemented by remote applications.
 * @author t0076261
 */
@Deprecated
public interface IRemoteContextChangeListener {
  /**
   * The name of the remote listener.
   * @return A not <code>null</code>, neither empty {@link String}.
   */
  String getName();

  /**
   * Named context has just been applied.<br>
   * The listener should try and adapt to the change.
   * @param contextName_p
   * @param contextName_p The name of the context about to be applied.
   * @return A not <code>null</code> status with the following fields :<br>
   *         <ul>
   *         <li>Severity (mandatory):
   *         <ul>
   *         <li>{@link IStatus#OK} if the remote listener has successfully taken into account the change.</li>
   *         <li>{@link IStatus#ERROR} if the remote listener has failed to take into account the change.</li>
   *         <li>{@link IStatus#WARNING} if the remote listener has done nothing to take into account the change.</li>
   *         </ul>
   *         </li>
   *         <li>Message (mandatory) : a human-readable message that tells the user what to do/expect.</li>
   *         <li>Children (optional) : the status may contain children, if required.</li>
   *         </ul>
   */
  IStatus postContextChange(String contextName_p);

  /**
   * Named context is about to be applied by the user.<br>
   * The listener is to tell whether it is able to handle such a change or not.
   * @param contextName_p The name of the context about to be applied.
   * @return A not <code>null</code> status with the following fields :<br>
   *         <ul>
   *         <li>Severity (mandatory):
   *         <ul>
   *         <li>{@link IStatus#OK} if the remote listener is currently able to switch context.</li>
   *         <li>{@link IStatus#ERROR} if the remote listener is currently processing current context data, and is not ready to switch context yet.</li>
   *         <li>{@link IStatus#WARNING} if the remote listener is not using current context data, but is unable to adapt itself to a context switch if it
   *         occurs.</li>
   *         </ul>
   *         </li>
   *         <li>Message (mandatory) : a human-readable message that tells the user what to do/expect.</li>
   *         <li>Children (optional) : the status may contain children, if required.</li>
   *         </ul>
   */
  IStatus preContextChange(String contextName_p);
}