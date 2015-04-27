/**
 * Copyright (c) THALES, 2010. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.connector.text;

import com.thalesgroup.orchestra.framework.connector.baseconnector.BaseConnector;

/**
 * @author s0024585
 */
public class TextConnector extends BaseConnector {
  /**
   * @see com.thalesgroup.orchestra.framework.connector.baseconnector.BaseConnector#getExecutableVariablePath()
   */
  @Override
  protected String getExecutableVariablePath() {
    return "\\Orchestra\\Connectors\\Text\\Path"; //$NON-NLS-1$
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.baseconnector.BaseConnector#getMimeType()
   */
  @Override
  protected String getMimeType(String physicalPath) {
    return "text/plain"; //$NON-NLS-1$
  }
}