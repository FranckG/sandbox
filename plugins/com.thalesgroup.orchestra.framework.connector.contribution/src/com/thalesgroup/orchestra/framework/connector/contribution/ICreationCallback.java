/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.connector.contribution;

/**
 * @author s0018747
 * Call back to get object from the Orchestra Explorer from the contributions
 */

public interface ICreationCallback {
  /**
   * 
   * @param contextKey_p
   * @return
   */
  public Object getContext(String contextKey_p);
}
