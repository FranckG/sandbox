/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.validation.constraint;

import org.eclipse.osgi.util.NLS;

/**
 * @author t0076261
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.model.validation.constraint.messages"; //$NON-NLS-1$
  public static String ReadableWritableDirectoryConstraint_Folder_No_Rights;
  public static String ReadableWritableDirectoryConstraint_Incoherent_Folder_Path;
  public static String ReadableWritableDirectoryConstraint_Parent_Folder_No_Rigths;
  public static String ReferencedVariableConstraint_ReferencedVariableIsMultiValues;
  public static String ReferencedVariableConstraint_ReferencedVariableNotFound;
  public static String VariableValuesConstraint_File_Type;
  public static String VariableValuesConstraint_Folder_Type;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
    // Static initialization.
  }
}