package com.thalesgroup.orchestra.framework.ui.view;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.ui.view.messages"; //$NON-NLS-1$
  public static String EnvUseBaselineBlock_StartingPointMasterSectionTitle;
  public static String EnvUseBaselineDetailsPage_ImpactedEnvironmentGroupTitle;
  public static String EnvUseBaselineDetailsPage_StartingPointDetailsSectionTitle;
  public static String EnvUseBaselineDetailsPage_ReferenceDetailsSectionTitle;
  public static String EnvUseBaselineDetailsPage_StartingPointParametersGroupTitle;
  public static String ModelElementFilterContribution_MenuItem_ShowAllElements;
  public static String ModelElementFilterContribution_MenuItem_ShowContributedElements;
  public static String ModelElementFilterContribution_MenuItem_ShowEmptyMandatoryVariables;
  public static String ModelElementFilterContribution_MenuItem_ShowOverriddenVariables;
  public static String NewContextAction_Label_Admin;
  public static String NewContextAction_Label_User;
  public static String VariablesView_ContextSaveError_Message;
  public static String VariablesView_ContextSaveError_Reason;
  public static String VariablesView_ContextSaveError_Title;
  public static String UnreachableContextsView_Label_ProjectName;
  public static String UnreachableContextsView_Label_Location;
  public static String UnreachableContextsView_Label_ReloadContext;
  public static String UnreachableContextsView_Label_ReloadAllContexts;
  public static String UnreachableContextsView_Label_RemoveContext;
  public static String UnreachableContextsView_ImportError_Title;
  public static String UnreachableContextsView_ImportError_Message;
  public static String UnreachableContextsView_ImportError_Reason;
  public static String UnreachableContextsView_ImportError_ContextStillUnreachable;

  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
    // Static initialization
  }
}
