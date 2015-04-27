/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.connector.sdk.operation;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;

/**
 * Project parameters.<br>
 * Handles all creation parameters so as to end with the connector expected template project.
 * @author t0076261
 */
public class ConnectorProjectParameters {
  /**
   * Connector ID.
   */
  protected String _connectorId;
  /**
   * Connector name.
   */
  protected String _connectorName;
  /**
   * Latest computed location.
   */
  protected String _connectorProjectLocationPath;
  /**
   * Is connector an out of process one ?
   */
  protected boolean _isOutOfProcess;
  /**
   * Is connector an independent product ?
   */
  protected boolean _isProduct;
  /**
   * Type definitions.
   */
  protected TypeDefinition[] _typeDefinitions;

  /**
   * Constructor.
   */
  public ConnectorProjectParameters() {
    _connectorName = ICommonConstants.EMPTY_STRING;
    _connectorId = ICommonConstants.EMPTY_STRING;
    _connectorProjectLocationPath = ICommonConstants.EMPTY_STRING;
    _isOutOfProcess = true;
    _isProduct = false;
    _typeDefinitions = new TypeDefinition[0];
  }

  /**
   * @return the connectorId
   */
  public String getConnectorId() {
    return _connectorId;
  }

  /**
   * @return the connectorName
   */
  public String getConnectorName() {
    return _connectorName;
  }

  /**
   * @return the connectorProjectLocationPath
   */
  public String getConnectorProjectLocationPath() {
    return _connectorProjectLocationPath;
  }

  /**
   * @return the typeDefinitions
   */
  public TypeDefinition[] getTypeDefinitions() {
    return _typeDefinitions;
  }

  /**
   * @return the isOutOfProcess
   */
  public boolean isOutOfProcess() {
    return _isOutOfProcess;
  }

  /**
   * @return the isProduct
   */
  public boolean isProduct() {
    return _isProduct;
  }

  /**
   * @param connectorId_p the connectorId to set
   */
  public void setConnectorId(String connectorId_p) {
    _connectorId = connectorId_p;
  }

  /**
   * @param connectorName_p the connectorName to set
   */
  public void setConnectorName(String connectorName_p) {
    _connectorName = connectorName_p;
  }

  /**
   * @param connectorProjectLocationPath_p the connectorProjectLocationPath to set
   */
  public void setConnectorProjectLocationPath(String connectorProjectLocationPath_p) {
    _connectorProjectLocationPath = connectorProjectLocationPath_p;
  }

  /**
   * @param isOutOfProcess_p the isOutOfProcess to set
   */
  public void setOutOfProcess(boolean isOutOfProcess_p) {
    _isOutOfProcess = isOutOfProcess_p;
  }

  /**
   * @param isProduct_p the isProduct to set
   */
  public void setProduct(boolean isProduct_p) {
    _isProduct = isProduct_p;
  }

  /**
   * @param typeDefinitions_p the typeDefinitions to set
   */
  public void setTypeDefinitions(TypeDefinition[] typeDefinitions_p) {
    _typeDefinitions = typeDefinitions_p;
  }
}