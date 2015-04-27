/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ecf.services.internal.connector;

import org.eclipse.core.runtime.Assert;

import com.thalesgroup.orchestra.framework.connector.CommandContext;
import com.thalesgroup.orchestra.framework.connector.CommandStatus;
import com.thalesgroup.orchestra.framework.connector.ConnectorActivator;
import com.thalesgroup.orchestra.framework.ecf.services.connector.IRemoteConnector;
import com.thalesgroup.orchestra.framework.ecf.services.connector.IRemoteConnectorProxy;

/**
 * Remote connector proxy implementation.<br>
 * Wraps an {@link IRemoteConnector} implementation so as to handle the serialization issue.
 * @author t0076261
 */
public class RemoteConnectorProxy implements IRemoteConnectorProxy {
  /**
   * Serial UID.
   */
  private static final long serialVersionUID = -5442329984099482166L;
  /**
   * Real connector implementation.
   */
  private IRemoteConnector _connector;

  /**
   * Constructor.
   * @param connector_p The connector implementation. Must be not <code>null</code>.
   */
  public RemoteConnectorProxy(IRemoteConnector connector_p) {
    Assert.isNotNull(connector_p);
    _connector = connector_p;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ecf.services.connector.IRemoteConnectorProxy#closeApplication()
   */
  public String closeApplication() throws Exception {
    return serializeStatus(_connector.closeApplication());
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ecf.services.connector.IRemoteConnectorProxy#create(java.lang.String)
   */
  public String create(String context_p) throws Exception {
    return serializeStatus(_connector.create(deserializeContext(context_p)));
  }

  /**
   * De-serialize context from specified string.
   * @param context_p
   * @return
   */
  protected CommandContext deserializeContext(String context_p) {
    return ConnectorActivator.getInstance().getSerializationHelper().deserializeContext(context_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ecf.services.connector.IRemoteConnectorProxy#documentaryExport(java.lang.String)
   */
  public String documentaryExport(String context_p) throws Exception {
    return serializeStatus(_connector.documentaryExport(deserializeContext(context_p)));
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ecf.services.connector.IRemoteConnectorProxy#executeSpecificCommand(java.lang.String)
   */
  public String executeSpecificCommand(String context_p) throws Exception {
    return serializeStatus(_connector.executeSpecificCommand(deserializeContext(context_p)));
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ecf.services.connector.IRemoteConnectorProxy#expand(java.lang.String)
   */
  public String expand(String context_p) throws Exception {
    return serializeStatus(_connector.expand(deserializeContext(context_p)));
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ecf.services.connector.IRemoteConnectorProxy#getType()
   */
  public String getType() throws Exception {
    return _connector.getType();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ecf.services.connector.IRemoteConnectorProxy#importArtifacts(java.lang.String)
   */
  public String importArtifacts(String context_p) throws Exception {
    return serializeStatus(_connector.importArtifacts(deserializeContext(context_p)));
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ecf.services.connector.IRemoteConnectorProxy#isHandlingArtifacts(java.lang.String)
   */
  public String isHandlingArtifacts(String context_p) throws Exception {
    return serializeStatus(_connector.isHandlingArtifacts(deserializeContext(context_p)));
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ecf.services.connector.IRemoteConnectorProxy#lmExport(java.lang.String)
   */
  public String lmExport(String context_p) throws Exception {
    return serializeStatus(_connector.lmExport(deserializeContext(context_p)));
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ecf.services.connector.IRemoteConnectorProxy#navigate(java.lang.String)
   */
  public String navigate(String context_p) throws Exception {
    return serializeStatus(_connector.navigate(deserializeContext(context_p)));
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ecf.services.connector.IRemoteConnectorProxy#search(java.lang.String)
   */
  public String search(String context_p) throws Exception {
    return serializeStatus(_connector.search(deserializeContext(context_p)));
  }

  /**
   * Serialize specified status to string.
   * @param status_p
   * @return
   */
  protected String serializeStatus(CommandStatus status_p) {
    return ConnectorActivator.getInstance().getSerializationHelper().serializeStatus(status_p);
  }
}