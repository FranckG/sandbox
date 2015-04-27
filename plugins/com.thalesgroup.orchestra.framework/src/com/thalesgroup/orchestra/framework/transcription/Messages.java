/**
 * Copyright (c) THALES, 2010. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.transcription;

import org.eclipse.osgi.util.NLS;

/**
 * @author s0024585
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.transcription.messages"; //$NON-NLS-1$
  public static String TranscriptionHelper_LogicalTranscription_Warning_NoSuchPathInEnvironment;
  public static String TranscriptionHelper_NoAssociation;
  public static String TranscriptionHelper_NotInitialized;
  public static String TranscriptionHelper_TooManyAssociationsFound;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
    // Static initialization.
  }
}