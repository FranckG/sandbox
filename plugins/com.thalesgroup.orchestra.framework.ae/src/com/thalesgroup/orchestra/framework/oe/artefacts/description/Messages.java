/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.oe.artefacts.description;

import org.eclipse.osgi.util.NLS;

/**
 * @author s0018747
 */
public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.thalesgroup.orchestra.framework.oe.artefacts.description.messages"; //$NON-NLS-1$
  public static String ArtefactsDescriptionLoader_DirectoryDoesNotExist;
  public static String ArtefactsDescriptionLoader_NoArtefactsDescriptionInFiles;
  public static String ArtefactsDescriptionLoader_NoDirectoryProvided;
  public static String ArtefactsDescriptionLoader_ParsingError;
  public static String ArtifactsDescriptionLoader_CunstomizedConf;
  public static String ArtifactsDescriptionLoader_customisedConfigurationFile;
  public static String ArtifactsDescriptionLoader_errorLoadingConf;
  public static String ArtifactsDescriptionLoader_errorLoadingConf2;
  public static String ArtifactsDescriptionLoader_errorLoadingConf3;
  public static String ArtifactsDescriptionLoader_errorLoadingConf4;
  public static String ArtifactsDescriptionLoader_ErrorWhenLoadingconf;
  public static String ArtifactsDescriptionLoader_makeSureTheServerIsRunning;
  public static String ArtifactsDescriptionLoader_MalformedFile;
  public static String ArtifactsDescriptionLoader_staticInitFailed;
  public static String ArtifactsDescriptionLoader_ThisFile;
  public static String ArtifactsDescriptionLoader_WontBePossibleToRestoreState;
  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {
    // NOTHING TO DO
  }
}
