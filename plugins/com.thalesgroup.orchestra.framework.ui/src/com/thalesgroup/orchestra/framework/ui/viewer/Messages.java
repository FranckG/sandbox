/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.viewer;

import org.eclipse.osgi.util.NLS;

/**
 * @author t0076261
 */
public class Messages extends NLS {
  public static String AbstractLabelProvider_Context_Label_CurrentContext;
  public static String AbstractLabelProvider_Context_Label_User;
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.ui.viewer.messages"; //$NON-NLS-1$
  public static String ContextsViewer_ConfigureMenu_Text;
  public static String ContextsViewer_NewMenu_Text;
  public static String ContextsViewer_SectionTitle;
  public static String ContextsViewer_Tooltip_Property_ContextLocation;
  public static String ContextsViewer_Tooltip_Property_Description;
  public static String ContextsViewer_Tooltip_Property_ParentPhysicalPath;
  public static String ContextsViewer_Tooltip_Property_ParentRawPhysicalPath;
  public static String ContextsViewer_Tooltip_Property_PhysicalPath;
  public static String ContextsViewer_Tooltip_Property_Value_InWorkspace;
  public static String ContextsViewer_Tooltip_Property_Value_NoParentPath;
  public static String ContextsViewer_Tooltip_Property_Value_OutsideWorkspace;
  public static String VariablesViewer_ColumnTitle_Name;
  public static String VariablesViewer_ColumntTitle_Value;
  public static String VariablesViewer_SectionTitle;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
    // Static initialization
  }
}
