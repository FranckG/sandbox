/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.oe.ui.actions;

import org.eclipse.osgi.util.NLS;

/**
 * @author s0018747
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.oe.ui.actions.messages"; //$NON-NLS-1$
  public static String CollapseAllAction_CollapseAllTooltip;
  public static String CreateArtefactAction_OrchestraExplorer;
  public static String CreateArtefactAction_CreateArtefactLabel;
  public static String CreateArtefactAction_CreateArtefactTooltip;
  public static String CopyURIAction_CopyURILabel;
  public static String CopyURIAction_CopyURITooltip;
  public static String ExpandAllAction_ExpandAllTooltip;
  public static String ExpandArtefactAction_GetSubArtefactListLabel;
  public static String ExpandArtefactAction_GetSubArtefactListTooltip;
  public static String ExpandArtefactAction_OrchestraExplorer;
  public static String ExpandArtefactAction_TaskName;
  public static String FoldingAction_FoldingLabel;
  public static String FoldingAction_FoldingTooltip;
  public static String GetRootArtefactAction_OrchestraExplorer;
  public static String GetRootartefactAction_TaskName;
  public static String GetRootArtefactsAction_FrameworkReconnectionError;
  public static String GetRootArtefactsAction_UpdateRootArtefactsLabel;
  public static String GetRootArtefactsAction_UpdateRootArtefactsTooltip;
  public static String NavigateAction_NavigateLabel;
  public static String NavigateAction_NavigateTooltip;
  public static String NavigateAction_NavigationError;
  public static String NavigateAction_TaskName;
  public static String ReInitializeCredentialActionError;
  public static String ReInitializeCredentialActionLabel;
  public static String ReInitializeCredentialActionTooltip;
  public static String ShowArtifactPropertiesAction_PropertiesLabel;
  public static String ShowArtifactPropertiesAction_PropertiesToolTip;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
    // Nothing
  }
}
