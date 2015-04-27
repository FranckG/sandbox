/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.connector;

import java.util.Collections;
import java.util.Map;

import org.eclipse.core.runtime.Assert;

import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;

/**
 * An Artifact is a command input defined by its {@link OrchestraURI} and extra parameters.
 * @author t0076261
 */
public class Artifact {
  /**
   * Environment runtime Id.
   */
  private String _environmentId;

  /**
   * Environment properties.<br>
   * The key is the property name.<br>
   * The value is the property one. As this is the format used by the environment, there is no predictable form as to what this value contains. A pairing must
   * be maintained with the environment implementation. So in most cases, this is unlikely to be useful. But some connectors might be developed in conjunction
   * with the corresponding environment. In this case, the connector might be able to deal with such data directly.
   */
  private Map<String, String> _environmentProperties;

  /**
   * Root physical path, if applicable.
   */
  private String _rootPhysicalPath;

  /**
   * The Orchestra URI.
   */
  private OrchestraURI _uri;

  /**
   * Constructor.
   * @param uri_p The artifact {@link OrchestraURI}. Must be not <code>null</code>.
   */
  public Artifact(OrchestraURI uri_p, String environmentId_p) {
    Assert.isNotNull(uri_p);
    _uri = uri_p;
    _environmentId = environmentId_p;
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
    Artifact other = (Artifact) obj;
    if (_environmentId == null) {
      if (other._environmentId != null)
        return false;
    } else if (!_environmentId.equals(other._environmentId))
      return false;
    if (_uri == null) {
      if (other._uri != null)
        return false;
    } else if (!_uri.equals(other._uri))
      return false;
    return true;
  }

  /**
   * Get handling environment runtime ID.
   * @return
   */
  public String getEnvironmentId() {
    return _environmentId;
  }

  /**
   * @return the environmentProperties
   */
  public Map<String, String> getEnvironmentProperties() {
    // Precondition.
    if (null == _environmentProperties) {
      _environmentProperties = Collections.emptyMap();
    }
    return _environmentProperties;
  }

  /**
   * Get root artifact absolute physical path.
   * @return <code>null</code> if it is not applicable.
   */
  public String getRootPhysicalPath() {
    return _rootPhysicalPath;
  }

  /**
   * Get the artifact {@link OrchestraURI}.
   * @return A not <code>null</code> URI.
   */
  public OrchestraURI getUri() {
    return _uri;
  }

  /**
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((_environmentId == null) ? 0 : _environmentId.hashCode());
    result = prime * result + ((_uri == null) ? 0 : _uri.hashCode());
    return result;
  }

  /**
   * @param environmentProperties_p the environmentProperties to set
   */
  public void setEnvironmentProperties(Map<String, String> environmentProperties_p) {
    _environmentProperties = environmentProperties_p;
  }

  /**
   * @param rootPhysicalPath_p the rootPhysicalPath to set
   */
  public void setRootPhysicalPath(String rootPhysicalPath_p) {
    _rootPhysicalPath = rootPhysicalPath_p;
  }
}