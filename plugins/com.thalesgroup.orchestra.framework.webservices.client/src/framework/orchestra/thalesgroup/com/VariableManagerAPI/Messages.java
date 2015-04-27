/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package framework.orchestra.thalesgroup.com.VariableManagerAPI;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author s0011584
 */
public class Messages {
  private static final String BUNDLE_NAME = "framework.orchestra.thalesgroup.com.VariableManagerAPI.messages"; //$NON-NLS-1$

  private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

  private Messages() {
    // Static initialization.
  }

  public static String getString(String key) {
    try {
      return RESOURCE_BUNDLE.getString(key);
    } catch (MissingResourceException e) {
      return '!' + key + '!';
    }
  }
}