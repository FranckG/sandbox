/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.oe.ui.dnd;

import org.eclipse.osgi.util.NLS;

/**
 * @author s0018747
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.oe.ui.dnd.messages"; //$NON-NLS-1$
  public static String ArtefactDragListener_IsNotDraggable;
  public static String ArtefactSetDialog_Page_Title;
  public static String ArtefactSetDialog_Path_Label;
  public static String ArtefactSetDialog_Types_Label;
  public static String ArtefactSetDialog_Window_Title;
  public static String ArtifactDragListener_ArtifactExplorer;
  public static String ArtifactDragListener_DragError;
  public static String ArtifactDragListener_messageError_cannotBeDraggedNotAtomicallyExtractable;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
    // Nothing
  }
}
