/**
 * Copyright (c) THALES, 2010. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.transcription;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;

import com.thalesgroup.orchestra.framework.FrameworkActivator;
import com.thalesgroup.orchestra.framework.common.CommonActivator;
import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.connector.CommandStatus;
import com.thalesgroup.orchestra.framework.environment.filesystem.ITranscriptionProvider;
import com.thalesgroup.orchestra.framework.lib.base.UtilFunction;
import com.thalesgroup.orchestra.framework.lib.constants.PapeeteHTTPKeyRequest;
import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;

/**
 * Helper to do transcriptions
 * @author s0024585
 */
public class TranscriptionHelper implements ITranscriptionProvider {
  /**
   * The singleton
   */
  private static TranscriptionHelper __instance;

  /**
   * Association source table
   */
  private FilesManager _filesMgr = null;

  /**
   * Association memory table
   */
  private AssociationTableManager _tablesMgr = null;

  /**
   * The constructor of the singleton.
   */
  private TranscriptionHelper() {
    // init(String) has to be called before using TranscriptionHelper.
  }

  /**
   * Get from the relative physical path of the element, the absolute path.
   * @param relativeFilePath_p the relative physical path
   * @param inputPaths_p List of root absolute path to use. Must not be <code>null</code> or empty.
   * @return the absolute path or <code>null</code> if not found
   */
  private String getAbsolutePath(String relativeFilePath_p, List<String> inputPaths_p) {
    if ((null == inputPaths_p) || inputPaths_p.isEmpty()) {
      return null;
    }
    String physicalPath = null;
    for (String path : inputPaths_p) {
      File physicalArtifact = new File(path, UtilFunction.UnixWindowsNameAdapter(relativeFilePath_p));
      physicalPath = physicalArtifact.getPath();
      if (UtilFunction.IsExistedFile(physicalPath)) {
        return physicalPath;
      }
    }
    return null;
  }

  /**
   * Get all associations for specified type.
   * @param type_p
   * @return A possibly empty (ie no association) collection of {@link Association}.
   */
  @SuppressWarnings("unchecked")
  protected Collection<Association> getAssociations(String type_p) {
    // Preconditions.
    if (null == _tablesMgr || null == type_p) {
      return Collections.emptyList();
    }
    Collection<Association> result = new ArrayList<Association>(0);
    Object[] associations = _tablesMgr.getAssociationCols();
    for (Object obj : associations) {
      Collection<Association> colAssoc = (Collection<Association>) obj;
      for (Association assoc : colAssoc) {
        if (assoc.getSourceArtifact().getRootType().equals(type_p)) {
          result.add(assoc);
        }
      }
    }
    return result;
  }

  /**
   * Get collections of conflicting associations, sorted by (association) types.
   * @return A not <code>null</code> but possibly empty map of conflicting associations.
   */
  public Map<String, Collection<Association>> getConflictingAssociations() {
    // Precondition.
    if (null == _tablesMgr) {
      return Collections.emptyMap();
    }
    return _tablesMgr.getErrorSet(PapeeteHTTPKeyRequest._TRANSCRIPTION_LOGICAL_PHYSICAL);
  }

  /**
   * Get erroneous associations.
   * @return A possibly <code>null</code> collection of erroneous associations.
   */
  public Collection<Association> getErrorneousAssocations() {
    // Precondition.
    if (null == _tablesMgr) {
      return Collections.emptyList();
    }
    return _tablesMgr.getErroneousAssociationsFromRelationType(PapeeteHTTPKeyRequest._TRANSCRIPTION_LOGICAL_PHYSICAL);
  }

  /**
   * Transcript the {@link OrchestraURI} into its logical name (i.e. filepath).<br/>
   * The URI must respect the following content:
   * <ul>
   * <li>RootType must be FILESYSTEM.
   * <li>RootName must be a valid filepath.<br/>
   * @param uri_p The {@link OrchestraURI} to transcript.
   * @param inputPaths_p List of root absolute path to use. If empty, transcription to relative path is ignored. Must not be <code>null</code>.
   * @return An {@link IStatus} giving the transcription result:
   *         <ul>
   *         <li>if <code>severity</code> equals {@link IStatus#OK}, the <code>message</code> contains the OrchestraURI; <li>otherwise <code>severity </code>
   *         equals {@link IStatus#ERROR} and the <code>message</code> contains the error message.
   */
  private IStatus getLogicalName(OrchestraURI uri_p, List<String> inputPaths_p) {
    String minimalPath;
    if (inputPaths_p.isEmpty()) {
      minimalPath = uri_p.getRootName();
    } else {
      minimalPath = getMinimalPath(uri_p.getRootName(), inputPaths_p);
    }
    // Path does not match the specified input ones. Stop here.
    if (null == minimalPath) {
      return new Status(IStatus.WARNING, FrameworkActivator.getDefault().getPluginId(),
          Messages.TranscriptionHelper_LogicalTranscription_Warning_NoSuchPathInEnvironment);
    }
    AssociationArtifact artifact = new AssociationArtifact(uri_p.getRootType(), minimalPath);
    CommandStatus result = transcript(artifact, "*", PapeeteHTTPKeyRequest._TRANSCRIPTION_LOGICAL_PHYSICAL); //$NON-NLS-1$
    // Stop here.
    if (!result.isOK()) {
      return result;
    }
    OrchestraURI uri = result.getUri();
    return new Status(IStatus.OK, FrameworkActivator.getDefault().getPluginId(), uri.getUri());
  }

  /**
   * Get logical-physical association for specified type.
   * @param type_p
   * @return <code>null</code> if there is no such association for this type (or type is <code>null</code>).
   */
  public Association getLogicalPhysicalAssociation(String type_p) {
    Collection<Association> associations = getLogicalPhysicalAssociationsCollection(type_p);
    if (associations.isEmpty()) {
      return null;
    }
    return associations.iterator().next();
  }

  /**
   * Get all logical-physical associations for specified type.
   * @param type_p
   * @return An empty {@link Collection} of {@link Association} if there is no such association for this type (or type is <code>null</code>).
   */
  public Collection<Association> getLogicalPhysicalAssociationsCollection(String type_p) {
    // Cycle through associations.
    Collection<Association> associations = getAssociations(type_p);
    Collection<Association> result = new ArrayList<Association>();
    for (Association association : associations) {
      if (PapeeteHTTPKeyRequest._TRANSCRIPTION_LOGICAL_PHYSICAL.equals(association.getRelationType())) {
        result.add(association);
      }
    }
    return result;
  }

  /**
   * Transcript the {@link OrchestraURI} into its physical name (i.e. filepath).
   * @param uri_p The {@link OrchestraURI} to transcript.
   * @param inputPaths_p List of root absolute path to use. If empty, transcription to absolute path is ignored. Must not be <code>null</code>.
   * @return An {@link IStatus} giving the transcription result:
   *         <ul>
   *         <li>if <code>severity</code> equals {@link IStatus#OK}, the <code>message</code> contains the physical path;
   *         <li>otherwise <code>severity</code> equals {@link IStatus#ERROR} and the <code>message</code> contains the error message.
   */
  private IStatus getPhysicalPath(OrchestraURI uri_p, List<String> inputPaths_p) {
    AssociationArtifact artifact = new AssociationArtifact(uri_p.getRootType(), uri_p.getRootName());
    CommandStatus result = transcript(artifact, "FILESYSTEM", PapeeteHTTPKeyRequest._TRANSCRIPTION_LOGICAL_PHYSICAL); //$NON-NLS-1$
    // Stop here.
    if (!result.isOK()) {
      return result;
    }
    OrchestraURI uri = result.getUri();
    String physicalPath;
    if (inputPaths_p.isEmpty()) {
      physicalPath = uri.getRootName();
    } else {
      physicalPath = getAbsolutePath(uri.getRootName(), inputPaths_p);
    }
    if (null == physicalPath) {
      return new Status(IStatus.WARNING, FrameworkActivator.getDefault().getPluginId(), ICommonConstants.EMPTY_STRING);
    }
    return new Status(IStatus.OK, FrameworkActivator.getDefault().getPluginId(), physicalPath);
  }

  /**
   * Initialize/Reinitialize transcription using the given configuration directory.
   * @param configurationDirectoryPath_p
   */
  public void init(String configurationDirectoryPath_p) {
    if (null == configurationDirectoryPath_p) {
      return;
    }
    _tablesMgr = new AssociationTableManager();
    _filesMgr = new FilesManager(configurationDirectoryPath_p);
    _filesMgr.addObserver(_tablesMgr);
    refresh();
  }

  /**
   * Refreshes the associations data from the disk.
   */
  public void refresh() {
    synchronized (__instance) {
      if (_filesMgr != null) {
        try {
          _filesMgr.upToDate();
        } catch (Throwable e) {
          StringBuilder loggerMessage = new StringBuilder("TranscriptionHelper.getPhysicalName(..) _ "); //$NON-NLS-1$
          loggerMessage.append("Unable to update associations."); //$NON-NLS-1$
          CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.ERROR, e);
        }
      }
    }
  }

  /**
   * Transcript an artefact for a specified tool and a specified association type.
   * @param artifactSource_p The {@link AssociationArtifact} to transcript.
   * @param iToolToTranscript The tool into which to transcript.
   * @param iRelationType The type of transcription. Only LOGICAL-PHYSICAL is supported yet.
   * @return The transcription result.
   */
  private CommandStatus transcript(AssociationArtifact artifactSource_p, String iToolToTranscript, String iRelationType) {
    // Precondition.
    if (null == _tablesMgr) {
      String message = Messages.TranscriptionHelper_NotInitialized;
      return new CommandStatus(IStatus.ERROR, message, null, 0);
    }
    List<Association> associationsMatching = _tablesMgr.getAssociations(artifactSource_p, iToolToTranscript, iRelationType);
    switch (associationsMatching.size()) {
      case 0:
        String message = MessageFormat.format(Messages.TranscriptionHelper_NoAssociation, artifactSource_p.getContent(), artifactSource_p.getRootType());
        return new CommandStatus(IStatus.ERROR, message, null, 0);
      case 1:
        Association crt_asso = null;
        String content = null;
        crt_asso = associationsMatching.get(0);
        AssociationArtifact arSrc = null;
        AssociationArtifact arDes = null;
        String encodedContent = null;

        if (iToolToTranscript.equals(crt_asso.getTargetArtifact().getRootType())) {
          arDes = crt_asso.getTargetArtifact();
          arSrc = crt_asso.getSourceArtifact();
        } else {
          arSrc = crt_asso.getTargetArtifact();
          arDes = crt_asso.getSourceArtifact();
        }
        encodedContent = arSrc.getContent();

        content = StringMapping.GetTranscribeContent(artifactSource_p.getContent(), encodedContent, arDes.getContent());
        return new CommandStatus(IStatus.OK, ICommonConstants.EMPTY_STRING, new OrchestraURI(arDes.getRootType(), content), 0);
      default:
        return new CommandStatus(IStatus.ERROR, MessageFormat.format(Messages.TranscriptionHelper_TooManyAssociationsFound, associationsMatching.get(0)
            .getTargetArtifact().getContent()), null, 0);
    }
  }

  /**
   * Transcript the {@link OrchestraURI}.<br/>
   * Realize physical to logical, or logical to physical transcriptions.
   * @param uri_p The {@link OrchestraURI} to transcript.
   * @param inputPaths_p List of root absolute path to use. Must not be <code>null</code> or empty.
   * @return An {@link IStatus} giving the transcription result:
   *         <ul>
   *         <li>if <code>severity</code> equals {@link IStatus#OK}, the <code>message</code> contains the transcription result;
   *         <li>otherwise <code>severity</code> equals {@link IStatus#ERROR} and the <code>message</code> contains the error message.
   */
  public IStatus transcript(OrchestraURI uri_p, List<String> inputPaths_p) {
    String rootType = uri_p.getRootType();
    IStatus result;
    if (rootType.equals(PapeeteHTTPKeyRequest._FILESYSTEM_TOOL_NAME)) {
      result = getLogicalName(uri_p, inputPaths_p);
    } else {
      result = getPhysicalPath(uri_p, inputPaths_p);
    }
    return result;
  }

  /**
   * Return the {@link TranscriptionHelper} singleton
   * @return
   */
  public static TranscriptionHelper getInstance() {
    if (__instance == null) {
      __instance = new TranscriptionHelper();
    }
    return __instance;
  }

  /**
   * Extract a sub-path from <code>fullPath_P</code>, depending of the artifact path variable content.
   * @param fullPath_p
   * @param inputPaths_p List of root absolute path to use. Must not be <code>null</code> or empty.
   * @return The sub-path from fullPath_p
   */
  static protected String getMinimalPath(String fullPath_p, List<String> inputPaths_p) {
    // Precondition.
    if ((null == inputPaths_p) || inputPaths_p.isEmpty()) {
      return null;
    }

    int index = -1;
    String fullPathLowerCase = fullPath_p.toLowerCase().replace('/', '\\');

    for (String path : inputPaths_p) {
      IPath p = new Path(path);
      path = p.addTrailingSeparator().toString();
      index = fullPathLowerCase.lastIndexOf(path.toLowerCase().replace('/', '\\'));
      if (index != -1) {

        index += path.length();
        break;
      }
    }

    if (index != -1) {

      return fullPath_p.substring(index).replace('\\', '/');
    }
    return null;
  }
}