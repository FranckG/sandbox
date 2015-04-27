/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment.registry;

import org.eclipse.osgi.util.NLS;

/**
 * @author t0076261
 *
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.environment.registry.messages"; //$NON-NLS-1$
  public static String EnvironmentRegistry_Descriptor_GetEnvironment_Error_CouldNotCreateNewInstance;
  public static String EnvironmentRegistry_Descriptor_Initialize_Error_CouldNotCreateNewHandlerInstance;
  public static String EnvironmentRegistry_Descriptor_Initialize_Error_ExtensionDoesNotProvideImplementations;
  public static String EnvironmentRegistry_Descriptor_Initialize_Error_ExtensionDoesNotProvideWithId;
  public static String EnvironmentRegistry_Descriptor_Initialize_Error_IncorrectParameters;
  public static String EnvironmentRegistry_Descriptor_Initialize_Successful;
  public static String EnvironmentRegistry_GetEnvironment_Error_CouldNotCreateNewInstance;
  public static String EnvironmentRegistry_GetEnvironment_Error_IncorrectParameters;
  public static String EnvironmentRegistry_GetEnvironment_Error_NoDescriptorForId;
  public static String EnvironmentRegistry_GetEnvironment_Successful;
  public static String EnvironmentRegistry_GetEnvironmentDescriptor_Error_IncorrectParameters;
  public static String EnvironmentRegistry_GetEnvironmentDescriptor_Error_NoDescriptorForId;
  public static String EnvironmentRegistry_GetEnvironmentDescriptor_Successful;
  public static String EnvironmentRegistry_GetEnvironmentHandler_Error_CouldNotCreateNewInstance;
  public static String EnvironmentRegistry_GetEnvironmentHandler_Error_IncorrectParameters;
  public static String EnvironmentRegistry_GetEnvironmentHandler_Error_NoDescriptorForId;
  public static String EnvironmentRegistry_GetEnvironmentHandler_Successful;
  public static String EnvironmentRegistry_Status_Initialization;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
  }
}
