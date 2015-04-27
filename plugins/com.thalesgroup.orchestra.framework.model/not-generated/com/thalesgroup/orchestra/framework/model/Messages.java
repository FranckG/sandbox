/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model;

import org.eclipse.osgi.util.NLS;

/**
 * @author T0052089
 *
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.model.messages"; //$NON-NLS-1$
  public static String CATEGORY_NAME_PENDINGELEMENTS;
  public static String ModelUtil_FetchOrchestraInstallation_Error_UnableToReadODResultFile;
  public static String ModelUtil_OrchestraDoctorFetch_Error_UnableToAccessOrchestraDoctorInstallationPath;
  public static String ModelUtil_OrchestraDoctorFetch_Error_UnableToResolveOrchestraDoctorInstallationPath;
  public static String ModelUtil_OrchestraDoctorFetch_Error_WrapUpMessage;
  public static String OVERRIDING_VARIABLE_VALUE_NAME;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
    // Static initialization
  }
}
