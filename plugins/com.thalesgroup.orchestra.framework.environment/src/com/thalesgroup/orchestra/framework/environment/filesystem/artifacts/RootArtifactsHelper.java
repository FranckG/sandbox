/**
 * Copyright (c) THALES, 2010. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment.filesystem.artifacts;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;

import com.thalesgroup.orchestra.framework.common.CommonActivator;
import com.thalesgroup.orchestra.framework.connector.CommandContext;
import com.thalesgroup.orchestra.framework.connector.CommandStatus;
import com.thalesgroup.orchestra.framework.environment.EnvironmentActivator;
import com.thalesgroup.orchestra.framework.environment.IEnvironmentConstants;
import com.thalesgroup.orchestra.framework.environment.filesystem.FileSystemEnvironment;
import com.thalesgroup.orchestra.framework.exchange.GefHandler;
import com.thalesgroup.orchestra.framework.gef.Element;
import com.thalesgroup.orchestra.framework.gef.GEF;
import com.thalesgroup.orchestra.framework.gef.GefFactory;
import com.thalesgroup.orchestra.framework.gef.GenericExportFormat;
import com.thalesgroup.orchestra.framework.gef.Properties;
import com.thalesgroup.orchestra.framework.gef.Property;
import com.thalesgroup.orchestra.framework.lib.base.utils.OrchestraFileFilter;
import com.thalesgroup.orchestra.framework.lib.constants.PapeeteHTTPKeyRequest;
import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;

/**
 * @author s0024585
 */
public class RootArtifactsHelper {
  /**
   * Element absolute path property name.
   */
  public static final String ELEMENT_PROPERTY_NAME_ABSOLUTE_PATH = "AbsolutePath"; //$NON-NLS-1$
  /**
   * Element relative path property name.
   */
  public static final String ELEMENT_PROPERTY_NAME_RELATIVE_PATH = "RelativePath"; //$NON-NLS-1$
  /**
   * In-use {@link FileSystemEnvironment}.
   */
  private FileSystemEnvironment _fileSystemEnvironment;
  /**
   * List of sub-directories that should be excluded.
   */
  private List<String> _filteredDirectories;
  /**
   * List of input directories to consider.
   */
  private List<String> _inputDirectories;

  /**
   * Constructor.
   * @param fileSystemEnvironment_p The file system environment to use.
   * @param inputDirectories_p Directories path to consider while searching for artifacts. Must be neither <code>null</code> nor empty.
   * @param filteredDirectories_p Sub-directories to ignore within specified input directories. Can be <code>null</code> or empty if none.
   */
  public RootArtifactsHelper(FileSystemEnvironment fileSystemEnvironment_p) {
    Assert.isNotNull(fileSystemEnvironment_p);
    _fileSystemEnvironment = fileSystemEnvironment_p;
    _inputDirectories = fileSystemEnvironment_p.getInputDirectories();
    Assert.isNotNull(_inputDirectories);
    Assert.isTrue(!_inputDirectories.isEmpty());
    _filteredDirectories = fileSystemEnvironment_p.getFilteredDirectories();
  }

  /**
   * @param context_p
   * @param foundFiles
   */
  private void generateGefFile(CommandContext context_p, Map<String, List<String>> foundFiles) {
    // Write the GEF file for found artifacts
    GefHandler handler = new GefHandler();
    GEF gef = handler.createNewModel(context_p.getExportFilePath());

    GenericExportFormat artifactSet = GefFactory.eINSTANCE.createGenericExportFormat();

    // Cycle through URIs.
    for (String uri : foundFiles.keySet()) {
      List<String> absolutePaths = foundFiles.get(uri);
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

        GefHandler.addValue(relativePathProperty, GefHandler.ValueType.CDATA, getRelativePaths(absolutePath));
        GefHandler.addValue(absolutePathProperty, GefHandler.ValueType.CDATA, absolutePath);

        // Set environment string representation.
        GefHandler.addValue(environmentProperty, GefHandler.ValueType.CDATA, _fileSystemEnvironment.toString());

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

  /***
   * Method getRelativePaths : returns a Project Name of an artifact for example : iPath = c:\opt\themis\artifacts\tests\test1.cpp => tests\test1.cpp
   * @param iPath_p
   * @return
   */
  private String getRelativePaths(String iPath_p) {
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

  public CommandStatus getRootArtifacts(CommandContext context_p) {
    Map<String, List<String>> foundFiles = new HashMap<String, List<String>>();

    for (String artefactPath : _inputDirectories) {
      Path rootDir = Paths.get(artefactPath);
      OrchestraFileFilter fileFilter = new OrchestraFileFilter(rootDir, _filteredDirectories);
      parseFileSystem(rootDir, fileFilter, foundFiles);
    }

    generateGefFile(context_p, foundFiles);

    return new CommandStatus(IStatus.OK, "", null, 0); //$NON-NLS-1$
  }

  /**
   * @param rootDirectory_p
   * @param fileFilter_p
   * @param foundFiles_p
   */
  private void parseFileSystem(Path rootDirectory_p, final OrchestraFileFilter fileFilter_p, final Map<String, List<String>> foundFiles_p) {
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
        @SuppressWarnings("synthetic-access")
        @Override
        public FileVisitResult visitFile(Path file_p, BasicFileAttributes attrs_p) throws IOException {
          // transcript the file to retrieve the logical name
          String file = file_p.toAbsolutePath().toString();
          OrchestraURI uri = new OrchestraURI(PapeeteHTTPKeyRequest._FILESYSTEM_TOOL_NAME, file);
          IStatus transcriptionStatus = EnvironmentActivator.getInstance().getTranscriptionProvider().transcript(uri, _inputDirectories);
          // Transcription ok.
          if (transcriptionStatus.isOK()) {
            String fileLogicalName = transcriptionStatus.getMessage();
            // There should be only one absolute path per URI.
            // Still, let the Framework core detect such an issue.
            List<String> list = foundFiles_p.get(fileLogicalName);
            if (null == list) {
              list = new ArrayList<String>(1);
              foundFiles_p.put(fileLogicalName, list);
            }
            list.add(file);
          }
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
}