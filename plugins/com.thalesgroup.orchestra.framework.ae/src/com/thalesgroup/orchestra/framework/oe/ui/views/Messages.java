/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.oe.ui.views;

import org.eclipse.osgi.util.NLS;

/**
 * @author S0024585
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.oe.ui.views.messages"; //$NON-NLS-1$
  public static String OrchestraExplorerView_DoNotGroup;
  public static String OrchestraExplorerView_FileSystem;
  public static String OrchestraExplorerView_Flat;
  public static String OrchestraExplorerView_GroupByLabel;
  public static String OrchestraExplorerView_GroupByRootArtefactFilePath;
  public static String OrchestraExplorerView_GroupByRootArtefactType;
  public static String OrchestraExplorerView_HorizontalViewOrientation;
  public static String OrchestraExplorerView_LayoutLabel;
  public static String OrchestraExplorerView_Name;
  public static String OrchestraExplorerView_SortByFileSystem;
  public static String OrchestraExplorerView_SortByName;
  public static String OrchestraExplorerView_SortByType;
  public static String OrchestraExplorerView_SortLabel;
  public static String OrchestraExplorerView_Type;
  public static String OrchestraExplorerView_VerticalViewOrientation;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
    // Nothing to do
  }
}
