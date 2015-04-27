/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.root.ui;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.IMessageProvider;

/**
 * Utility class for Wizards.
 * @author T0052089
 */
public class UIUtils {
  /**
   * Converts an IStatus severity to a MessageProvider severity (used to display messages in dialogs).
   * @param statusSeverity_p
   * @return
   */
  public static int statusSeverityToMessageProviderConstant(int statusSeverity_p) {
    switch (statusSeverity_p) {
      case IStatus.OK:
        return IMessageProvider.NONE;
      case IStatus.INFO:
        return IMessageProvider.INFORMATION;
      case IStatus.WARNING:
        return IMessageProvider.WARNING;
      case IStatus.ERROR:
        return IMessageProvider.ERROR;
      case IStatus.CANCEL:
        return IMessageProvider.NONE;
      default:
        return IMessageProvider.NONE;
    }
  }
}
