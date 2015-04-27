/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.connector;

/**
 * Connector options constants.<br>
 * Set in the map of options at connector creation time.
 * @author t0076261
 */
@SuppressWarnings("nls")
public interface IConnectorOptionConstants {
  /**
   * COM ProgId option key.
   */
  public static final String COM_PROG_ID = "progId";
  /**
   * Connector type option key.
   */
  public static final String CONNECTOR_TYPE = "connectorType";
  /**
   * ECF container adapter on server side.
   */
  public static final String ECF_CONTAINER_ADAPTER = "ecfContainerAdapter";
  /**
   * ECF remote call timeout value.
   */
  public static final String ECF_REMOTE_CALL_TIMEOUT = "ecfRemoteCallTimeout";
}