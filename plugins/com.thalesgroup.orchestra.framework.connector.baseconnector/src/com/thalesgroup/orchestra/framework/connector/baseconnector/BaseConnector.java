/**
 * Copyright (c) THALES, 2010. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.connector.baseconnector;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IStatus;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.connector.AbstractConnector;
import com.thalesgroup.orchestra.framework.connector.Artifact;
import com.thalesgroup.orchestra.framework.connector.CommandContext;
import com.thalesgroup.orchestra.framework.connector.CommandStatus;
import com.thalesgroup.orchestra.framework.connector.services.ConnectorFrameworkServices;
import com.thalesgroup.orchestra.framework.connector.services.ConnectorFrameworkServices.CommandResult;
import com.thalesgroup.orchestra.framework.exchange.GefHandler;
import com.thalesgroup.orchestra.framework.gef.Element;
import com.thalesgroup.orchestra.framework.gef.FileReference;
import com.thalesgroup.orchestra.framework.gef.GEF;
import com.thalesgroup.orchestra.framework.gef.GefFactory;
import com.thalesgroup.orchestra.framework.gef.GenericExportFormat;
import com.thalesgroup.orchestra.framework.lib.utils.CRCUtils;
import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;
import com.thalesgroup.orchestra.framework.util.Util;
import com.thalesgroup.orchestra.framework.variablemanager.server.model.VariableManager;

/**
 * @author s0024585
 */
public abstract class BaseConnector extends AbstractConnector {

  private static final String ARTEFACT_PATH_TOKEN = "%PATH%";

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#create(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  public CommandStatus create(CommandContext context_p) throws Exception {
    return createStatusForUnsupportedCommand(context_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#documentaryExport(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  public CommandStatus documentaryExport(CommandContext context_p) throws Exception {
    return documentaryExport(context_p, true);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#documentaryExport(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  public CommandStatus documentaryExport(CommandContext context_p, boolean fullExport_p) throws Exception {
    // Resulting status.
    CommandStatus result = new CommandStatus(ICommonConstants.EMPTY_STRING, null, 0);
    // Gef Handler.
    GefHandler handler = new GefHandler();
    // Create model.
    GEF gefModel = handler.createNewModel(context_p.getExportFilePath());
    GenericExportFormat gef = GefFactory.eINSTANCE.createGenericExportFormat();
    try {
      // Get base export directory (shareddir/uniquedirectory).
      Path baseExportDirectory = fullExport_p ? Paths.get(context_p.getExportFilePath()).getParent() : null;
      // Cycle through artifacts.
      for (Artifact artifact : context_p.getArtifacts()) {
        // Status for current artifact
        CommandStatus status = null;
        // Documentary Export Serialization
        Element element = GefFactory.eINSTANCE.createElement();
        // Add element to container right now.
        gef.getELEMENT().add(element);
        // Set element properties.
        element.setFullName(artifact.getUri().getRootName());
        element.setLabel(artifact.getUri().getRootName());
        element.setType(ICommonConstants.EMPTY_STRING);
        element.setUri(artifact.getUri().getUri());
        // Light export stops here.
        if (!fullExport_p) {
          result.addChild(new CommandStatus(ICommonConstants.EMPTY_STRING, artifact.getUri(), 0));
          continue;
        }
        // Full export does add different properties, and copies file to relative space.
        // Copy the physical file into the export directory
        Path physicalFile = Paths.get(artifact.getRootPhysicalPath());
        // Generate directory name for extracted files (absolute hashcode of the root artefact logical name, in base 36).
        String extractedFilesDirName = Integer.toString(Math.abs(artifact.getUri().getRootName().hashCode()), Character.MAX_RADIX);
        Path extractedFilesDir = Paths.get(baseExportDirectory.toString(), extractedFilesDirName);
        Files.createDirectory(extractedFilesDir);

        // Extracted file destination in generated directory.
        Path extractedDestFile = Paths.get(extractedFilesDir.toString(), physicalFile.getFileName().toString());
        try {
          // Copy physicalFile -> extractedDestFile
          Files.copy(physicalFile, extractedDestFile);

          // Hash + FilePath + FileVersion
          element.setHash(String.valueOf(CRCUtils.getFileCRC(physicalFile.toAbsolutePath().toString())));
          status = new CommandStatus(IStatus.OK, ICommonConstants.EMPTY_STRING, artifact.getUri(), 0);
        } catch (IOException ioException) {
          status =
              new CommandStatus(IStatus.ERROR, MessageFormat.format(Messages.BaseConnector_CanNotGenerateDocumentaryExport, ioException.getMessage()),
                  artifact.getUri(), 0);
          break;
        } finally {
          result.addChild(status);
        }
        // Add metadata.
        if (status.isOK()) {
          // File reference
          FileReference fileref = GefFactory.eINSTANCE.createFileReference();
          // Relative path.
          URI uriBase = baseExportDirectory.toUri();
          URI uri = uriBase.relativize(extractedDestFile.toUri());
          fileref.setUrl(uri.getPath());
          // MimeType.
          fileref.setMimeType(getMimeType(artifact.getRootPhysicalPath()));
          element.getFILEREFERENCE().add(fileref);
          // Metadata.
          OrchestraURI metadataUri = artifact.getUri().getAbsolute();
          metadataUri.addParameter(ConnectorFrameworkServices.URI_PARAMETER_METADATA_ENVIRONMENT_ID, artifact.getEnvironmentId());
          CommandResult commandResult = ConnectorFrameworkServices.getArtifactsMetadata(Collections.singletonList(metadataUri));
          if (commandResult._status.isOK()) {
            GEF metadataGef = handler.loadModel(commandResult._gefAbsolutePath);
            Element metadataElement = handler.getElementForUri(metadataGef, artifact.getUri().getUri());
            if (null != metadataElement) {
              element.getVERSION().addAll(metadataElement.getVERSION());
            }
          } else {
            status.addChild(commandResult._status);
          }
        }
      }
    } finally {
      // Retain model.
      gefModel.getGENERICEXPORTFORMAT().add(gef);
      // And save it.
      handler.saveModel(gefModel, false);
      // Clean whole handler.
      handler.clean();
    }
    return result;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#expand(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  public CommandStatus expand(CommandContext context_p) throws Exception {
    return createStatusForUnsupportedCommand(context_p);
  }

  /**
   * Get the Executable variable path, that should be used to launch the handled application.
   * @return
   */
  protected abstract String getExecutableVariablePath();

  /**
   * Get the mime type for the file described by its path.
   * @param physicalPath_p The path of the file
   * @return
   */
  protected String getMimeType(String physicalPath_p) {
    File physicalFile = new File(physicalPath_p);
    int dotPos = physicalFile.getName().lastIndexOf('.');
    String extension = ""; //$NON-NLS-1$
    if (dotPos > -1) {
      extension = physicalFile.getName().substring(dotPos);
    }
    return Util.GetMimeType(extension);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.AbstractConnector#lmExport(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  @Override
  public CommandStatus lmExport(CommandContext context_p) throws Exception {
    return documentaryExport(context_p, false);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#navigate(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  public CommandStatus navigate(CommandContext context_p) throws Exception {
    String executablePath = VariableManager.getValue(getExecutableVariablePath());
    // Precondition.
    if ((null == executablePath) || executablePath.trim().isEmpty()) {
      return new CommandStatus(IStatus.ERROR, MessageFormat.format(Messages.BaseConnector_VariableNotDefinedOrEmpty, getExecutableVariablePath()), null, 0);
    }

    List<CommandStatus> childrenStatus = new ArrayList<CommandStatus>();
    int nbSuccess = 0;
    int nbFailed = 0;
    for (Artifact artifact : context_p.getArtifacts()) {
      CommandStatus status = null;
      if (artifact.getRootPhysicalPath() != null) {
        // Build process arguments
        List<String> processArguments = new ArrayList<String>();
        processArguments.add(executablePath);
        // Process launch arguments
        String launchArguments = context_p.getLaunchArguments();
        if (null == launchArguments || launchArguments.equals("")) {
          processArguments.add(artifact.getRootPhysicalPath());
        } else {
          // Add all arguments to process arguments
          String[] arguments = launchArguments.split(" ");
          for (String arg : arguments) {
            // Replace artefact path token
            arg = arg.replace(ARTEFACT_PATH_TOKEN, artifact.getRootPhysicalPath());
            processArguments.add(arg);
          }
        }

        ProcessBuilder builder = new ProcessBuilder(processArguments);
        try {
          builder.start();
          status = new CommandStatus(IStatus.OK, "", artifact.getUri(), 0); //$NON-NLS-1$
          nbSuccess++;
        } catch (Exception exception_p) {
          status =
              new CommandStatus(IStatus.ERROR, MessageFormat.format(Messages.BaseConnector_ExecutableLaunchingError, executablePath, exception_p.getMessage()),
                  artifact.getUri(), 0);
          nbFailed++;
        }
      } else {
        status = new CommandStatus(IStatus.ERROR, MessageFormat.format(Messages.BaseConnector_NoPhysicalPathForURI, artifact.getUri()), artifact.getUri(), 0);
        nbFailed++;
      }
      childrenStatus.add(status);
    }

    int globalSeverity = IStatus.OK;
    if (nbFailed > 0) {
      globalSeverity = IStatus.WARNING;
    }
    if (nbSuccess == 0) {
      globalSeverity = IStatus.ERROR;
    }

    CommandStatus result = new CommandStatus(globalSeverity, "", null, 0); //$NON-NLS-1$
    result.addChildren(childrenStatus);
    return result;
  }
}