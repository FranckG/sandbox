/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.eclipse.variablesresolver.contribution;

import org.eclipse.osgi.util.NLS;

/**
 * @author T0052089
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.eclipse.variablesresolver.contribution.messages"; //$NON-NLS-1$
  public static String OdmVariableResolver_Error_CantResolveVariable;
  public static String OdmVariableResolver_Error_Initialization;
  public static String OdmVariableResolver_Error_NotFoundVariable;
  public static String OdmVariableResolver_Error_NoValueInVariable;
  public static String OdmVariableResolver_Warning_MoreThanOneValueInVariable;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
  }
}
