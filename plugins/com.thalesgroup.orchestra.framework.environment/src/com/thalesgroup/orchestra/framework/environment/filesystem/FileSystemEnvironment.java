/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment.filesystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;

import com.thalesgroup.orchestra.framework.common.CommonActivator;
import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.connector.Artifact;
import com.thalesgroup.orchestra.framework.connector.CommandContext;
import com.thalesgroup.orchestra.framework.connector.CommandStatus;
import com.thalesgroup.orchestra.framework.environment.AbstractEnvironment;
import com.thalesgroup.orchestra.framework.environment.BaselineContext;
import com.thalesgroup.orchestra.framework.environment.BaselineResult;
import com.thalesgroup.orchestra.framework.environment.EnvironmentActivator;
import com.thalesgroup.orchestra.framework.environment.EnvironmentContext;
import com.thalesgroup.orchestra.framework.environment.IEnvironmentConstants;
import com.thalesgroup.orchestra.framework.exchange.GefHandler;
import com.thalesgroup.orchestra.framework.gef.Element;
import com.thalesgroup.orchestra.framework.gef.GEF;
import com.thalesgroup.orchestra.framework.gef.GefFactory;
import com.thalesgroup.orchestra.framework.gef.GenericExportFormat;
import com.thalesgroup.orchestra.framework.gef.Properties;
import com.thalesgroup.orchestra.framework.gef.Property;
import com.thalesgroup.orchestra.framework.gef.Version;
import com.thalesgroup.orchestra.framework.lib.base.utils.OrchestraFileFilter;
import com.thalesgroup.orchestra.framework.lib.constants.PapeeteHTTPKeyRequest;
import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;

//import org.eclipse.core.runtime.Path;

/**
 * File System default environment implementation as provided by the Framework.
 * @author t0076261
 */
public class FileSystemEnvironment extends AbstractEnvironment {
  /**
   * Unique FileSystem Environment ID.
   */
  public static final String FILE_SYSTEM_ENVIRONMENT_ID = "com.thalesgroup.orchestra.framework.environment.filesystem"; //$NON-NLS-1$

  /**
   * Element absolute path property name.
   */
  public static final String ELEMENT_PROPERTY_NAME_ABSOLUTE_PATH = "AbsolutePath"; //$NON-NLS-1$
  /**
   * Element relative path property name.
   */
  public static final String ELEMENT_PROPERTY_NAME_RELATIVE_PATH = "RelativePath"; //$NON-NLS-1$

  /**
   * List of sub-directories that should be excluded.
   */
  private List<String> _filteredDirectories;
  /**
   * List of input directories to consider.
   */
  private List<String> _inputDirectories;

  /**
   * Add Metadata properties for specified artifact to specified GEF element.
   * @param artifact_p A not <code>null</code> artifact.
   * @param element_p A not <code>null</code> GEF element, standing for specified artifact Metadata values.
   */
  protected void addMetadataProperties(Artifact artifact_p, Element element_p) {
    // Root physical path.
    String rootPhysicalPath = artifact_p.getRootPhysicalPath();
    // Precondition.
    if (null == rootPhysicalPath) {
      return;
    }
    // Add properties container.
    Properties properties = GefFactory.eINSTANCE.createProperties();
    element_p.getPROPERTIES().add(properties);
    // Then add the rootPhysicalPath property.
    Property property = GefFactory.eINSTANCE.createProperty();
    properties.getPROPERTY().add(property);
    // Fill it.
    property.setName("rootPhysicalPath"); //$NON-NLS-1$
    GefHandler.addValue(property, GefHandler.ValueType.CDATA, rootPhysicalPath);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironment#getArtifactsMetadata(com.thalesgroup.orchestra.framework.connector.CommandContext,
   *      org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  public CommandStatus getArtifactsMetadata(CommandContext context_p, IProgressMonitor progressMonitor_p) {
    CommandStatus result = new CommandStatus(ICommonConstants.EMPTY_STRING, null, 0);
    GefHandler handler = new GefHandler();
    GEF gefModel = handler.createNewModel(context_p.getExportFilePath());
    GenericExportFormat gef = GefFactory.eINSTANCE.createGenericExportFormat();
    // Time optimization.
    Map<Artifact, Version> artifactToVersion = new HashMap<Artifact, Version>(0);
    // Cycle through artifacts.
    for (Artifact artifact : context_p.getArtifacts()) {
      // Create an element that points to provided URI only...
      Element element = GefFactory.eINSTANCE.createElement();
      element.setUri(artifact.getUri().getUri());
      // ... and contains the expected metadata.
      Version version = GefFactory.eINSTANCE.createVersion();
      artifactToVersion.put(artifact, version);
      element.getVERSION().add(version);
      // Attach element to GEF model.
      gef.getELEMENT().add(element);

    }
    // Populate metadata.
    Map<Artifact, CommandStatus> metadataResults = populateMetadata(artifactToVersion);
    // And deal with results.
    for (Entry<Artifact, CommandStatus> entry : metadataResults.entrySet()) {
      // Check status first.
      CommandStatus status = entry.getValue();
      // No status, or status is not OK.
      if ((null == status) || !status.isOK()) {
        // Remove version from GEF model.
        Version version = artifactToVersion.get(entry.getValue());
        ((Element) version.eContainer()).getVERSION().remove(version);
      }
      // Add status to result.
      if (null != status) {
        result.addChild(status);
      }
    }
    // Save GEF model.
    gefModel.getGENERICEXPORTFORMAT().add(gef);
    handler.saveModel(gefModel, true);
    return result;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.AbstractEnvironment#getEnvironmentHandler()
   */
  @Override
  public FileSystemEnvironmentHandler getEnvironmentHandler() {
    return (FileSystemEnvironmentHandler) super.getEnvironmentHandler();
  }

  /**
   * Get filtered sub-directories name.
   * @return
   */
  public List<String> getFilteredDirectories() {
    // Get directories value from attributes and decode them.
    return FileSystemEnvironmentHandler.decodeValues(getAttributes().get(FileSystemEnvironmentHandler.ATTRIBUTE_KEY_FILTERS));
  }

  /**
   * Get input directories absolute paths.
   * @return
   */
  public List<String> getInputDirectories() {
    // Get path value from attributes and decode them.
    return FileSystemEnvironmentHandler.decodeValues(getAttributes().get(FileSystemEnvironmentHandler.ATTRIBUTE_KEY_INPUT_DIRECTORIES));
  }

  /**
   * Get this root artifact file path relative to one of the root artifacts paths, as set by current context.
   * @param rootArtifactPhysicalFile_p
   * @return The absolute path if no relative path could be computed, a relative path otherwise.
   */
  protected String getPathRelativeToArtifactPaths(File rootArtifactPhysicalFile_p) {
    List<String> inputDirectories = getInputDirectories();
    // No way a relative path can be returned.
    if (inputDirectories.isEmpty()) {
      return rootArtifactPhysicalFile_p.getAbsolutePath();
    }
    // Get root artifact path.
    org.eclipse.core.runtime.IPath rootArtifactPath = new org.eclipse.core.runtime.Path(rootArtifactPhysicalFile_p.getAbsolutePath());
    // Cycle through root artifacts directories.
    for (String directoryPath : inputDirectories) {
      // Search for prefixing path.
      org.eclipse.core.runtime.IPath path = new org.eclipse.core.runtime.Path(directoryPath).removeTrailingSeparator();
      if (path.isPrefixOf(rootArtifactPath)) {
        // Remove prefix and return relative path.
        int matchingFirstSegments = rootArtifactPath.matchingFirstSegments(path);
        return rootArtifactPath.removeFirstSegments(matchingFirstSegments).setDevice(null).toOSString();
      }
    }
    // Unable to find relative path, return absolute one.
    return rootArtifactPhysicalFile_p.getAbsolutePath();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironment#getRootArtifacts(com.thalesgroup.orchestra.framework.connector.CommandContext,
   *      org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  public CommandStatus getRootArtifacts(CommandContext context_p, IProgressMonitor progressMonitor_p) {
    List<String> files = new ArrayList<String>();

    _inputDirectories = getInputDirectories();
    _filteredDirectories = getFilteredDirectories();

    // Special case of environments inheriting from FileSystem for which
    // input directories are optional
    if (_inputDirectories.isEmpty()) {
      Path rootDir = Paths.get("");
      OrchestraFileFilter fileFilter = new OrchestraFileFilter(Paths.get(""), _filteredDirectories);
      parseFileSystem(rootDir, fileFilter, files);
    } else {
      for (String artefactPath : _inputDirectories) {
        Path rootDir = Paths.get(artefactPath);
        OrchestraFileFilter fileFilter = new OrchestraFileFilter(rootDir, _filteredDirectories);
        parseFileSystem(rootDir, fileFilter, files);
      }
    }

    Map<String, List<String>> transcribedFiles = transcribeFiles(files);
    generateGefFile(context_p, transcribedFiles);

    return new CommandStatus(IStatus.OK, "", null, 0); //$NON-NLS-1$
  }

  /**
   * For FileSystemEnvironment, the following operations are done : <br>
   * - Variables in directory paths are substituted, <br>
   * - Default filters are added to the substituted filters list.
   * @see com.thalesgroup.orchestra.framework.environment.AbstractEnvironment#handleInitialAttributes(com.thalesgroup.orchestra.framework.environment.EnvironmentContext,
   *      org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  protected Map<String, String> handleInitialAttributes(EnvironmentContext context_p, IProgressMonitor progressMonitor_p) {
    Map<String, String> result = new HashMap<String, String>(0);
    Map<String, List<String>> expandedAttributes = getEnvironmentHandler().getExpandedAttributes(context_p);
    // Fill directories list (variable substitution is already done in expandedAttributes map).
    result.put(FileSystemEnvironmentHandler.ATTRIBUTE_KEY_INPUT_DIRECTORIES,
        FileSystemEnvironmentHandler.encodeValues(expandedAttributes.get(FileSystemEnvironmentHandler.ATTRIBUTE_KEY_INPUT_DIRECTORIES)));
    // Fill filters set, to avoid duplicate elements (add default filters if any).
    Set<String> fullFilterSet = new HashSet<String>();
    Set<String> fileSystemEnvironmentDefaultFilters =
        EnvironmentActivator.getInstance().getEnvironmentRegistry().getFileSystemEnvironmentDefaultFiltersRegistry().getDefaultFilters();
    fullFilterSet.addAll(fileSystemEnvironmentDefaultFilters);
    List<String> modelFilterList = expandedAttributes.get(FileSystemEnvironmentHandler.ATTRIBUTE_KEY_FILTERS);
    fullFilterSet.addAll(modelFilterList);
    result.put(FileSystemEnvironmentHandler.ATTRIBUTE_KEY_FILTERS, FileSystemEnvironmentHandler.encodeValues(fullFilterSet));
    return result;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.AbstractEnvironment#isBaselineCompliant()
   */
  @Override
  public boolean isBaselineCompliant() {
    // FileSystem environment does not handle baseline yet.
    return false;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironment#isHandlingArtifacts(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  @Override
  public CommandStatus isHandlingArtifacts(CommandContext context_p) {
    // Precondition.
    if (null == context_p) {
      return new CommandStatus(IStatus.ERROR, Messages.FileSystemEnvironment_Command_InvalidParameters, null, 0);
    }
    return transcript(context_p, null);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironment#makeBaseline(com.thalesgroup.orchestra.framework.environment.BaselineContext,
   *      org.eclipse.core.runtime.IProgressMonitor)
   */
  public BaselineResult makeBaseline(BaselineContext baselineContext_p, IProgressMonitor monitor_p) {
    BaselineResult result = new BaselineResult();
    result.setStatus(new CommandStatus(IStatus.ERROR, Messages.FileSystemEnvironment_Error_Message_BaselineNotSupported, null, 0));
    return result;
  }

  /**
   * Populate specified versions for specified artifacts.
   * @param artifactToVersion_p
   * @return A not <code>null</code> (artifact, status) map that binds a resulting status to an artifact.
   */
  protected Map<Artifact, CommandStatus> populateMetadata(Map<Artifact, Version> artifactToVersion_p) {
    Map<Artifact, CommandStatus> result = new HashMap<Artifact, CommandStatus>(0);
    // Precondition.
    if ((null == artifactToVersion_p) || artifactToVersion_p.isEmpty()) {
      return result;
    }
    // Date format.
    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a"); //$NON-NLS-1$
    // Cycle through artifacts and versions.
    for (Entry<Artifact, Version> entry : artifactToVersion_p.entrySet()) {
      Version version = entry.getValue();
      Artifact artifact = entry.getKey();
      OrchestraURI artifactUri = artifact.getUri();
      try {
        // Basic properties.
        File physicalFile = new File(artifact.getRootPhysicalPath());
        version.setFilePath(getPathRelativeToArtifactPaths(physicalFile));
        long lastModified = physicalFile.lastModified();
        version.setVersion(format.format(new Date(lastModified)));
        // Add extra properties, if required.
        addMetadataProperties(artifact, (Element) version.eContainer());
        // Result is OK.
        result.put(artifact, new CommandStatus(ICommonConstants.EMPTY_STRING, artifactUri, 0));
      } catch (Exception exception_p) {
        // ERROR.
        result.put(
            artifact,
            new CommandStatus(IStatus.ERROR, MessageFormat.format(Messages.FileSystemEnvironment_Error_Message_NoVersionForSpecifiedArtefact,
                artifactUri.getUri()), artifactUri, 0));
      }
    }
    return result;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.AbstractEnvironment#transcript(com.thalesgroup.orchestra.framework.connector.CommandContext,
   *      org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  public CommandStatus transcript(CommandContext context_p, IProgressMonitor progressMonitor_p) {
    // Precondition.
    if (null == context_p) {
      return new CommandStatus(IStatus.ERROR, Messages.FileSystemEnvironment_Command_InvalidParameters, null, 0);
    }
    // Resulting children statuses.
    List<CommandStatus> results = new ArrayList<CommandStatus>(0);
    boolean hasErrors = false;
    boolean onlyWarnings = true;
    // Get transcription provider.
    ITranscriptionProvider provider = EnvironmentActivator.getInstance().getTranscriptionProvider();
    // Cycle through artifacts.
    for (Artifact artifact : context_p.getArtifacts()) {
      // URI
      OrchestraURI uri = artifact.getUri();
      // Do transcript.
      IStatus status = provider.transcript(uri, getInputDirectories());
      if (!status.isOK()) {
        onlyWarnings &= (IStatus.WARNING == status.getSeverity());
        hasErrors = true;
      } else {
        onlyWarnings = false;
      }
      // Retain result.
      results.add(new CommandStatus(status.getSeverity(), status.getMessage(), uri, 0));
    }
    int resultingSeverity = IStatus.OK;
    String resultingMessage = ICommonConstants.EMPTY_STRING;
    if (onlyWarnings) {
      resultingSeverity = IStatus.WARNING;
      resultingMessage = MessageFormat.format(Messages.FileSystemEnvironment_Transcription_Warning_URIs_NotHandled, toString());
    } else if (hasErrors) {
      resultingSeverity = IStatus.ERROR;
    }
    // Return result
    CommandStatus result = new CommandStatus(resultingSeverity, resultingMessage, null, 0);
    result.addChildren(results);
    return result;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.AbstractEnvironment#useTranscription()
   */
  @Override
  public boolean useTranscription() {
    // FileSystem environment does make use of transcriptions.
    return true;
  }

  /***
   * Method getRelativePath : returns a Project Name of an artifact for example : iPath = c:\opt\themis\artifacts\tests\test1.cpp => tests\test1.cpp
   * @param iPath_p
   * @return
   */
  private String getRelativePath(String iPath_p) {
    String relativePath = null;

    Path pathToRelativise = Paths.get(iPath_p);
    for (String artifactPath : _inputDirectories) {
      try {
        Path path = Paths.get(artifactPath).relativize(pathToRelativise);
        if (!pathToRelativise.equals("")) { //$NON-NLS-1$
          relativePath = path.toString();
          break;
        }
      } catch (IllegalArgumentException exception_p) {
        // Impossible to relativise path, continue
      }
    }
    return relativePath;
  }

  /**
   * @param context_p
   * @param files_p
   */
  protected void generateGefFile(CommandContext context_p, Map<String, List<String>> files_p) {
    // Write the GEF file for found artifacts
    GefHandler handler = new GefHandler();
    GEF gef = handler.createNewModel(context_p.getExportFilePath());

    GenericExportFormat artifactSet = GefFactory.eINSTANCE.createGenericExportFormat();

    // Cycle through URIs.
    for (String uri : files_p.keySet()) {
      List<String> absolutePaths = files_p.get(uri);
      // Cycle through associated absolute paths.
      // Ideally, there should be only one path per URI.
      // Let the Framework core detect such an issue.
      for (String absolutePath : absolutePaths) {
        Element pa = GefFactory.eINSTANCE.createElement();
        pa.setType(""); //$NON-NLS-1$
        pa.setLabel(""); //$NON-NLS-1$

        Property relativePathProperty = GefFactory.eINSTANCE.createProperty();
        relativePathProperty.setName(ELEMENT_PROPERTY_NAME_RELATIVE_PATH);

        Property absolutePathProperty = GefFactory.eINSTANCE.createProperty();
        absolutePathProperty.setName(ELEMENT_PROPERTY_NAME_ABSOLUTE_PATH);

        Property environmentProperty = GefFactory.eINSTANCE.createProperty();
        environmentProperty.setName(IEnvironmentConstants.ELEMENT_PROPERTY_NAME_ENVIRONMENT);

        GefHandler.addValue(relativePathProperty, GefHandler.ValueType.CDATA, getRelativePath(absolutePath));
        GefHandler.addValue(absolutePathProperty, GefHandler.ValueType.CDATA, absolutePath);

        // Set environment string representation.
        GefHandler.addValue(environmentProperty, GefHandler.ValueType.CDATA, toString());

        Properties properties = GefFactory.eINSTANCE.createProperties();
        pa.getPROPERTIES().add(properties);
        properties.getPROPERTY().add(relativePathProperty);
        properties.getPROPERTY().add(absolutePathProperty);
        properties.getPROPERTY().add(environmentProperty);

        pa.setUri(uri);

        artifactSet.getELEMENT().add(pa);
      }
    }

    gef.getGENERICEXPORTFORMAT().add(artifactSet);

    handler.saveModel(gef, true);
    handler.clean();
  }

  /**
   * @param rootDirectory_p
   * @param fileFilter_p
   * @param foundFiles_p
   */
  protected void parseFileSystem(Path rootDirectory_p, final OrchestraFileFilter fileFilter_p, final List<String> files_p) {
    Assert.isNotNull(_inputDirectories);
    Assert.isTrue(!_inputDirectories.isEmpty());
    try {
      Files.walkFileTree(rootDirectory_p, new SimpleFileVisitor<Path>() {
        /**
         * @see java.nio.file.SimpleFileVisitor#preVisitDirectory(java.lang.Object, java.nio.file.attribute.BasicFileAttributes)
         */
        @Override
        public FileVisitResult preVisitDirectory(Path dir_p, BasicFileAttributes attrs_p) throws IOException {
          if (fileFilter_p.matchesDirectory(dir_p)) {
            return FileVisitResult.SKIP_SUBTREE;
          }
          return FileVisitResult.CONTINUE;
        }

        /**
         * @see java.nio.file.SimpleFileVisitor#visitFile(java.lang.Object, java.nio.file.attribute.BasicFileAttributes)
         */
        @Override
        public FileVisitResult visitFile(Path file_p, BasicFileAttributes attrs_p) throws IOException {
          files_p.add(file_p.toAbsolutePath().toString());
          return FileVisitResult.CONTINUE;
        }

        /**
         * @see java.nio.file.SimpleFileVisitor#visitFileFailed(java.lang.Object, java.io.IOException)
         */
        @Override
        public FileVisitResult visitFileFailed(Path file_p, IOException exc_p) throws IOException {
          return FileVisitResult.CONTINUE;
        }

      });
    } catch (IOException exception_p) {
      StringBuilder loggerMessage = new StringBuilder("RootArtifactsHelper.parseFileSystem(..) _ "); //$NON-NLS-1$
      CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.ERROR, exception_p);
    }
  }

  /**
   * @param file_p File to transcribe
   * @return Logical name of the transcribed file
   */
  private String transcribeFile(String file_p) {
    // transcript the file to retrieve the logical name
    OrchestraURI uri = new OrchestraURI(PapeeteHTTPKeyRequest._FILESYSTEM_TOOL_NAME, file_p);
    IStatus transcriptionStatus = EnvironmentActivator.getInstance().getTranscriptionProvider().transcript(uri, _inputDirectories);
    // Transcription ok.
    if (transcriptionStatus.isOK()) {
      String fileLogicalName = transcriptionStatus.getMessage();
      return fileLogicalName;
    }
    return null;
  }

  /**
   * Transcribe a list of files
   * @param files_p List of files to transcript
   * @return
   */
  private Map<String, List<String>> transcribeFiles(List<String> files_p) {
    Map<String, List<String>> transcribedFiles = new HashMap<String, List<String>>();
    for (String file : files_p) {
      String fileLogicalName = transcribeFile(file);
      if (null != fileLogicalName) {
        List<String> list = transcribedFiles.get(fileLogicalName);
        if (null == list) {
          list = new ArrayList<String>(1);
          transcribedFiles.put(fileLogicalName, list);
        }
        list.add(file);
      }
    }
    return transcribedFiles;
  }
}