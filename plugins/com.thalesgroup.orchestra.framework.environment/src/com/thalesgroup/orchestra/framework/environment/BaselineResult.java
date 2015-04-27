/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment;

import java.util.Map;

import com.thalesgroup.orchestra.framework.connector.CommandStatus;

/**
 * Make baseline service result.<br>
 * It is made of the baseline referencing attributes and a {@link CommandStatus} indicating how the service was processed.
 * @author t0076261
 */
public class BaselineResult {
  /**
   * Referencing attributes for environment producing the baseline.
   */
  private Map<String, String> _referencingAttributes;
  /**
   * Resulting status.
   */
  private CommandStatus _status;

  /**
   * Get environment referencing attributes as a result of the baseline creation.
   * @return A possibly <code>null</code>, or empty, map if baseline creation failed. Otherwise it contains environment attributes referencing the newly created
   *         baseline.
   */
  public Map<String, String> getReferencingAttributes() {
    return _referencingAttributes;
  }

  /**
   * Get make baseline resulting status for current environment.
   * @return A not <code>null</code> resulting {@link CommandStatus}.
   */
  public CommandStatus getStatus() {
    return _status;
  }

  /**
   * Set environment referencing attributes as a result of the baseline creation.
   * @param referencingAttributes_p A possibly <code>null</code>, or empty, map if baseline creation failed. Otherwise it contains environment attributes
   *          referencing the newly created baseline.
   */
  public void setReferencingAttributes(Map<String, String> referencingAttributes_p) {
    _referencingAttributes = referencingAttributes_p;
  }

  /**
   * Set baseline resulting status.
   * @param status_p A not <code>null</code> resulting {@link CommandStatus}.
   */
  public void setStatus(CommandStatus status_p) {
    _status = status_p;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    // Use status.
    if (null != _status) {
      return _status.toString();
    }
    // Default case.
    return super.toString();
  }
}