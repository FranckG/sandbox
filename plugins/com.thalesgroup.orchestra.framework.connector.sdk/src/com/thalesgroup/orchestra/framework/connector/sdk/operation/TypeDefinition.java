/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.connector.sdk.operation;

/**
 * @author T0052089
 */
public class TypeDefinition {
  /**
   * Logical name of the type.
   */
  protected String _logicalName;
  /**
   * Physical name of the type.
   */
  protected String _physicalName;
  /**
   * Name of the type.
   */
  protected String _typeName;

  /**
   * Constructor.
   * @param typeName_p
   * @param logicalName_p
   * @param physicalName_p
   */
  public TypeDefinition(String typeName_p, String logicalName_p, String physicalName_p) {
    _typeName = typeName_p;
    _logicalName = logicalName_p;
    _physicalName = physicalName_p;
  }

  /**
   * @return the logicalName
   */
  public String getLogicalName() {
    return _logicalName;
  }

  /**
   * @return the physicalName
   */
  public String getPhysicalName() {
    return _physicalName;
  }

  /**
   * @return the typeName
   */
  public String getTypeName() {
    return _typeName;
  }

  /**
   * @param logicalName_p the logicalName to set
   */
  public void setLogicalName(String logicalName_p) {
    _logicalName = logicalName_p;
  }

  /**
   * @param physicalName_p the physicalName to set
   */
  public void setPhysicalName(String physicalName_p) {
    _physicalName = physicalName_p;
  }

  /**
   * @param typeName_p the typeName to set
   */
  public void setTypeName(String typeName_p) {
    _typeName = typeName_p;
  }
}
