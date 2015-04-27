/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment.filesystem.ui;

import org.eclipse.osgi.util.NLS;

/**
 * @author t0076261
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.environment.filesystem.ui.messages"; //$NON-NLS-1$
  public static String AbstractFileSystemPage_Error_DuplicatedElement;
  public static String AbstractFileSystemPage_Error_InvalidElement;
  public static String DirectorySelectionPage_ErrorMessage_InvalidFilter;
  public static String DirectorySelectionPage_IntroductionText;
  public static String DirectorySelectionPage_Filter_Label;
  public static String DirectorySelectionPage_Title;
  public static String DirectoriesPickingPage_Button_Text_Add;
  public static String DirectoriesPickingPage_Button_Text_Browse;
  public static String DirectoriesPickingPage_Button_Text_AddMany;
  public static String DirectoriesPickingPage_Button_Text_Down;
  public static String DirectoriesPickingPage_Button_Text_Remove;
  public static String DirectoriesPickingPage_Button_Text_Up;
  public static String DirectoriesPickingPage_Button_Text_Preview;
  public static String DirectoriesPickingPage_DirectorySelectionDialog_Message;
  public static String DirectoriesPickingPage_DirectoryDialog_BaseDirectorySelection_Message;
  public static String DirectoriesPickingPage_Label_SubstitutedValue;
  public static String DirectoriesPickingPage_Page_Title;
  public static String DirectoriesPickingPage_Warning_CantFindSubstitutionHandler;
  public static String FiltersPage_DefaultFilter_Label;
  public static String FiltersPage_IntroductionText;
  public static String FiltersPage_No_Default_Filter;
  public static String FiltersPage_Title;
  public static String FiltersPage_FilteringPreview_Label;

  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
  }
}
