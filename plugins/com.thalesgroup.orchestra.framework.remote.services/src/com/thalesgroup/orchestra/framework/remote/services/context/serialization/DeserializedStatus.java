/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.remote.services.context.serialization;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * A status read from a de-serialization that is able to store children as they appear.
 * @author t0076261
 */
public class DeserializedStatus extends Status {
  /**
   * Children statuses.
   */
  protected List<IStatus> _children;

  /**
   * @param severity_p
   * @param message_p
   */
  public DeserializedStatus(int severity_p, String message_p) {
    super(severity_p, "com.thalesgroup.orchestra.framework.remote.services", 0, message_p, null); //$NON-NLS-1$
    _children = new ArrayList<IStatus>(0);
  }

  /**
   * Add specified child status.
   * @param childStatus_p
   */
  public void addChild(DeserializedStatus childStatus_p) {
    if (null != childStatus_p) {
      _children.add(childStatus_p);
    }
  }

  /**
   * @see org.eclipse.core.runtime.Status#getChildren()
   */
  @Override
  public IStatus[] getChildren() {
    return _children.toArray(new IStatus[_children.size()]);
  }

  /**
   * @see org.eclipse.core.runtime.Status#isMultiStatus()
   */
  @Override
  public boolean isMultiStatus() {
    return !_children.isEmpty();
  }

  /**
   * @see org.eclipse.core.runtime.Status#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder(super.toString());
    if (isMultiStatus()) {
      for (IStatus childStatus : getChildren()) {
        builder.append('\n').append('\t').append(childStatus.toString()).append('\n');
      }
    }
    return builder.toString();
  }
}