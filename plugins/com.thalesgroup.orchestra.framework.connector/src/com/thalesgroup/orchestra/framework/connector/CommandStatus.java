/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.connector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;

/**
 * A command status implementation.<br>
 * @author t0076261
 */
public class CommandStatus extends Status {
  /**
   * Children statuses.
   */
  protected Set<CommandStatus> _children;
  /**
   * Wrapped status.
   */
  protected IStatus _status;
  /**
   * The {@link OrchestraURI} of the targeted element, if any.
   */
  protected OrchestraURI _uri;

  /**
   * Constructor.
   * @param severity_p The status severity from {@link IStatus#CANCEL} to {@link IStatus#ERROR}
   * @param message_p A not <code>null</code> - but possibly empty - message describing the result.
   * @param uri_p The targeted element {@link OrchestraURI} if any, <code>null</code> otherwise (specifically for composite status).
   * @param code_p The optional applicative error code.
   */
  public CommandStatus(int severity_p, String message_p, OrchestraURI uri_p, int code_p) {
    super(severity_p, ConnectorActivator.getInstance().getPluginId(), code_p, message_p, null);
    _uri = uri_p;
    _children = new LinkedHashSet<CommandStatus>(0);
  }

  /**
   * Constructor.
   * @param status_p A not <code>null</code> status that should be wrapped into this command status.
   */
  public CommandStatus(IStatus status_p) {
    super(IStatus.OK, ConnectorActivator.getInstance().getPluginId(), ""); //$NON-NLS-1$
    Assert.isNotNull(status_p);
    _status = status_p;
    _children = new LinkedHashSet<CommandStatus>(0);
  }

  /**
   * Constructor.
   * @param status_p A not <code>null</code> status that should be wrapped into this command status.
   * @param uri_p The targeted element {@link OrchestraURI} if any, <code>null</code> otherwise (specifically for composite status).
   */
  public CommandStatus(IStatus status_p, OrchestraURI uri_p) {
    this(status_p);
    _uri = uri_p;
  }

  /**
   * Constructor.
   * @param message_p A not <code>null</code> - but possibly empty - message describing the result.
   * @param uri_p The targeted element {@link OrchestraURI} if any, <code>null</code> otherwise (specifically for composite status).
   * @param code_p The optional applicative error code. <br>
   * <br>
   *          Default severity is {@link IStatus#OK}.
   */
  public CommandStatus(String message_p, OrchestraURI uri_p, int code_p) {
    this(IStatus.OK, message_p, uri_p, code_p);
  }

  /**
   * Add child status.
   * @param status_p A not <code>null</code> status.
   */
  public void addChild(CommandStatus status_p) {
    // Precondition.
    if (null == status_p) {
      return;
    }
    // Add to wrapped status first.
    if (_status instanceof CommandStatus) {
      // Add to wrapped status.
      ((CommandStatus) _status).addChild(status_p);
      return;
    }
    // Add to current status.
    int statusSeverity = status_p.getSeverity();
    if (_children.isEmpty()) {
      // ... and status has no child yet.
      // Stick to new severity.
      setSeverity(statusSeverity);
    } else {
      // Compute new severity.
      int currentSeverity = getSeverity();
      switch (currentSeverity) {
      // Current severity is OK...
        case IStatus.OK:
          // ... and status has a higher severity (ie ERROR or WARNING).
          // It will be WARNING in the end.
          if (statusSeverity > currentSeverity) {
            setSeverity(IStatus.WARNING);
          }
        break;
        // Current severity is ERROR...
        case IStatus.ERROR:
          // ... and status has a lower severity (ie OK or WARNING).
          // It will be WARNING in the end.
          if (statusSeverity < currentSeverity) {
            setSeverity(IStatus.WARNING);
          }
        break;
      }
    }
    _children.add(status_p);
  }

  /**
   * Add children statuses.
   * @param children_p A not <code>null</code> collection of {@link CommandStatus}.
   */
  public void addChildren(Collection<CommandStatus> children_p) {
    // Preconditions.
    if ((null == children_p) || children_p.isEmpty()) {
      return;
    }
    // Add to current status.
    if (null != children_p) {
      // Add each status individually.
      for (CommandStatus commandStatus : children_p) {
        addChild(commandStatus);
      }
    }
  }

  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    CommandStatus other = (CommandStatus) obj;
    // Compare message.
    if (getMessage() == null) {
      if (other.getMessage() != null)
        return false;
    } else if (!getMessage().equals(other.getMessage()))
      return false;
    // Compare severity.
    if (getSeverity() != other.getSeverity())
      return false;
    // Compare URI.
    if (_uri == null) {
      if (other._uri != null)
        return false;
    } else if (!_uri.equals(other._uri))
      return false;
    return true;
  }

  /**
   * @see org.eclipse.core.runtime.Status#getChildren()
   */
  @Override
  public CommandStatus[] getChildren() {
    Collection<CommandStatus> result = null;
    // Wrapped status...
    if (null != _status) {
      // ... is a command status already.
      if (_status instanceof CommandStatus) {
        result = ((CommandStatus) _status)._children;
      } else { // ... is another kind of status.
        result = new ArrayList<CommandStatus>(0);
        IStatus[] children = _status.getChildren();
        if ((null != children) && (children.length > 0)) {
          for (IStatus childStatus : children) {
            result.add(new CommandStatus(childStatus));
          }
        }
        // Add local children, if any.
        result.addAll(_children);
      }
    } else { // No wrapped status.
      result = _children;
    }
    return result.toArray(new CommandStatus[result.size()]);
  }

  /**
   * @see org.eclipse.core.runtime.Status#getCode()
   */
  @Override
  public int getCode() {
    if (null != _status) {
      return _status.getCode();
    }
    return super.getCode();
  }

  /**
   * @see org.eclipse.core.runtime.Status#getException()
   */
  @Override
  public Throwable getException() {
    if (null != _status) {
      return _status.getException();
    }
    return super.getException();
  }

  /**
   * @see org.eclipse.core.runtime.Status#getMessage()
   */
  @Override
  public String getMessage() {
    if (null != _status) {
      return _status.getMessage();
    }
    return super.getMessage();
  }

  /**
   * @see org.eclipse.core.runtime.Status#getPlugin()
   */
  @Override
  public String getPlugin() {
    if (null != _status) {
      return _status.getPlugin();
    }
    return super.getPlugin();
  }

  /**
   * @see org.eclipse.core.runtime.Status#getSeverity()
   */
  @Override
  public int getSeverity() {
    if (null != _status) {
      return _status.getSeverity();
    }
    return super.getSeverity();
  }

  /**
   * Get {@link CommandStatus} targeting specified {@link OrchestraURI}.
   * @param uri_p
   * @return <code>null</code> if none could be found (or specified parameter is <code>null</code>).
   */
  public CommandStatus getStatusForUri(OrchestraURI uri_p) {
    // Precondition.
    if (null == uri_p) {
      return null;
    }
    // Current status is handling specified URI.
    if (uri_p.equals(_uri)) {
      return this;
    }
    // Search within children.
    for (CommandStatus child : getChildren()) {
      // Test child sub-tree.
      CommandStatus result = child.getStatusForUri(uri_p);
      if (null != result) {
        return result;
      }
    }
    return null;
  }

  /**
   * Get targeted element URI.
   * @return <code>null</code> if no element is available or the status is a composite one.
   */
  public OrchestraURI getUri() {
    if ((null != _status) && (_status instanceof CommandStatus)) {
      return ((CommandStatus) _status).getUri();
    }
    return _uri;
  }

  /**
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((getMessage() == null) ? 0 : getMessage().hashCode());
    result = prime * result + getSeverity();
    result = prime * result + ((_uri == null) ? 0 : _uri.hashCode());
    return result;
  }

  /**
   * @see org.eclipse.core.runtime.Status#isMultiStatus()
   */
  @Override
  public boolean isMultiStatus() {
    if (null != _status) {
      return _status.isMultiStatus();
    }
    return !_children.isEmpty();
  }

  /**
   * @see org.eclipse.core.runtime.Status#isOK()
   */
  @Override
  public boolean isOK() {
    if (null != _status) {
      return _status.isOK();
    }
    return super.isOK();
  }

  /**
   * @see org.eclipse.core.runtime.Status#matches(int)
   */
  @Override
  public boolean matches(int severityMask_p) {
    if (null != _status) {
      return _status.matches(severityMask_p);
    }
    return super.matches(severityMask_p);
  }

  /**
   * Set URI to use.
   * @param uri_p
   */
  public void setUri(OrchestraURI uri_p) {
    if (null != uri_p) {
      _uri = uri_p;
    }
  }

  /**
   * @see org.eclipse.core.runtime.Status#toString()
   */
  @Override
  public String toString() {
    if (null != _status) {
      return _status.toString();
    }
    return super.toString();
  }
}