/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.oe.artefacts.internal;

import org.eclipse.osgi.util.NLS;

/**
 * @author S0024585
 */
public class Messages extends NLS {
  public static String Artefact_ToString;
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.oe.artefacts.internal.messages"; //$NON-NLS-1$
  public static String RootArtefactsBag_ErrorDuringArtefactRetrieval;
  public static String RootArtefactsBag_ErrorDuringExpand;
  public static String RootArtefactsBag_ErrorInFramework;
  public static String RootArtefactsBag_ErrorReadingRequestResult;
  public static String RootArtefactsBag_GefFileCanNotBeRead;
  public static String RootArtefactsBag_GEFUriNotValid;
  public static String RootArtefactsBag_NoArtefactsFound;
  public static String RootArtefactsBag_OrchestraExplorer;
  public static String RootArtefactsBag_ProblemsInExpand;
  public static String RootArtefactsBag_RequestStatusFileUnreadable;
  public static String RootArtefactsBag_RetreiveRootArtefactsError;
  public static String RootArtefactsBag_UnexpectedErrorInCommunicationWithFramework;
  public static String RootArtefactsBag_UpdateRootArtefacts;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
    // Nothing to do
  }
}
