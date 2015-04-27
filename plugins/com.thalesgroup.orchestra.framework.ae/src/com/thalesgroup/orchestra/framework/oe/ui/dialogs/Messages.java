/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.oe.ui.dialogs;

import org.eclipse.osgi.util.NLS;

/**
 * @author s0018747
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.oe.ui.dialogs.messages"; //$NON-NLS-1$
  public static String PropertiesDialog_blank;
  public static String PropertiesDialog_default;
  public static String PropertiesDialog_env;
  public static String PropertiesDialog_lastModification;
  public static String PropertiesDialog_name;
  public static String PropertiesDialog_notFoundOnSystem;
  public static String PropertiesDialog_OrchestraURI;
  public static String PropertiesDialog_path;
  public static String PropertiesDialog_readOnly;
  public static String PropertiesDialog_readWriteAccess;
  public static String PropertiesDialog_space;
  public static String PropertiesDialog_status;
  public static String PropertiesDialog_title;
  public static String PropertiesDialog_type;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
    // Nothing
  }
}
