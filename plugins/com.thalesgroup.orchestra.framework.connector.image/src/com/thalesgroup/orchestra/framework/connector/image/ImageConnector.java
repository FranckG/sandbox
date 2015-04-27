/**
 * Copyright (c) THALES, 2010. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.connector.image;

import com.thalesgroup.orchestra.framework.connector.baseconnector.BaseConnector;

/**
 * @author s0024585
 */
public class ImageConnector extends BaseConnector {
  /**
   * @see com.thalesgroup.orchestra.framework.connector.baseconnector.BaseConnector#getExecutableVariablePath()
   */
  @Override
  protected String getExecutableVariablePath() {
    return "\\Orchestra\\Connectors\\Image\\Path"; //$NON-NLS-1$
  }
}