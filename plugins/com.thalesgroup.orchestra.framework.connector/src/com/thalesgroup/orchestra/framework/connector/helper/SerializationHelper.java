/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.connector.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.resource.Resource;

import com.thalesgroup.orchestra.framework.connector.CommandContext;
import com.thalesgroup.orchestra.framework.connector.CommandStatus;
import com.thalesgroup.orchestra.framework.exchange.status.SeverityType;
import com.thalesgroup.orchestra.framework.exchange.status.Status;
import com.thalesgroup.orchestra.framework.exchange.status.StatusDefinition;
import com.thalesgroup.orchestra.framework.exchange.status.StatusFactory;
import com.thalesgroup.orchestra.framework.exchange.status.util.StatusResourceFactoryImpl;
import com.thalesgroup.orchestra.framework.lib.utils.uri.BadOrchestraURIException;
import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;
import com.thalesgroup.orchestra.framework.models.context.Artifact;
import com.thalesgroup.orchestra.framework.models.context.Context;
import com.thalesgroup.orchestra.framework.models.context.ContextDefinition;
import com.thalesgroup.orchestra.framework.models.context.ContextFactory;
import com.thalesgroup.orchestra.framework.models.context.DocumentRoot;
import com.thalesgroup.orchestra.framework.models.context.EnvironmentProperty;
import com.thalesgroup.orchestra.framework.models.context.util.ContextResourceFactoryImpl;

/**
 * Serialization helper.
 * @author t0076261
 */
public class SerializationHelper {
  /**
   * UTF-8 charset constant
   */
  private static final String UTF_8 = "UTF-8"; //$NON-NLS-1$

  /**
   * Build {@link CommandContext} from specified {@link Context}.
   * @param context_p
   * @return <code>null</code> if context could not be built.
   */
  protected final CommandContext buildContext(Context context_p) {
    // Precondition.
    if (null == context_p) {
      return null;
    }
    // Create context.
    CommandContext result = new CommandContext(context_p.getType(), context_p.getExportFilePath(), context_p.isKeepOpen(), context_p.getLaunchArguments());
    // Create artifacts.
    try {
      for (Artifact artifact : context_p.getArtifact()) {
        com.thalesgroup.orchestra.framework.connector.Artifact targetArtifact =
            new com.thalesgroup.orchestra.framework.connector.Artifact(new OrchestraURI(artifact.getUri()), artifact.getEnvironmentId());
        targetArtifact.setRootPhysicalPath(artifact.getRootPhysicalPath());
        // Set properties.
        Map<String, String> properties = new HashMap<String, String>(0);
        for (EnvironmentProperty property : artifact.getEnvironmentProperties()) {
          properties.put(property.getName(), property.getValue());
        }
        targetArtifact.setEnvironmentProperties(properties);
        result.addArtifact(targetArtifact);
      }
    } catch (Exception exception_p) {
      // Do not return a corrupted context.
      result = null;
    }
    return result;
  }

  /**
   * Build {@link Status} from specified {@link CommandStatus}.<br>
   * This is a deep build, that also builds all children of specified {@link CommandStatus}.
   * @param status_p
   * @return <code>null</code> if status could not be built.
   */
  protected final Status buildStatus(CommandStatus status_p) {
    // Precondition.
    if (null == status_p) {
      return null;
    }
    Status result = StatusFactory.eINSTANCE.createStatus();
    result.setCode(status_p.getCode());
    result.setMessage(status_p.getMessage());
    result.setSeverity(convertSeverity(status_p.getSeverity()));
    OrchestraURI uri = status_p.getUri();
    if (null != uri) {
      result.setUri(uri.getUri());
    }
    for (CommandStatus childStatus : status_p.getChildren()) {
      Status convertedChildStatus = buildStatus(childStatus);
      if (null == convertedChildStatus) {
        // Do not return a corrupted status.
        return null;
      }
      result.getStatus().add(convertedChildStatus);
    }
    return result;
  }

  /**
   * Build {@link CommandStatus} from specified {@link Status}.
   * @param status_p
   * @return <code>null</code> if status could not be built.
   */
  public final CommandStatus buildStatus(Status status_p) {
    // Precondition.
    if (null == status_p) {
      return null;
    }
    // Get status.
    OrchestraURI uri = null;
    try {
      if (null != status_p.getUri()) {
        uri = new OrchestraURI(status_p.getUri());
      }
    } catch (BadOrchestraURIException exception_p) {
      // No URI to provide with.
      // Go on anyway.
    }
    CommandStatus result = new CommandStatus(convertSeverity(status_p.getSeverity()), status_p.getMessage(), uri, status_p.getCode());
    for (Status childStatus : status_p.getStatus()) {
      CommandStatus resultingChildStatus = buildStatus(childStatus);
      if (null != resultingChildStatus) {
        result.addChild(resultingChildStatus);
      }
    }
    return result;
  }

  /**
   * Convert severity from {@link IStatus} value to {@link SeverityType}.
   * @param severity_p
   * @return
   */
  protected final SeverityType convertSeverity(int severity_p) {
    switch (severity_p) {
      case IStatus.ERROR:
        return SeverityType.ERROR;
      case IStatus.INFO:
        return SeverityType.INFO;
      case IStatus.OK:
        return SeverityType.OK;
      case IStatus.WARNING:
        return SeverityType.WARNING;
    }
    return SeverityType.INFO;
  }

  /**
   * Convert severity from {@link SeverityType} to {@link IStatus} value.
   * @param severityType_p
   * @return
   */
  protected final int convertSeverity(SeverityType severityType_p) {
    switch (severityType_p) {
      case ERROR:
        return IStatus.ERROR;
      case WARNING:
        return IStatus.WARNING;
      case INFO:
        return IStatus.INFO;
      case OK:
        return IStatus.OK;
    }
    return IStatus.INFO;
  }

  /**
   * De-serialize context from specified string.
   * @param string_p
   * @return <code>null</code> if the context could not be de-serialized.
   */
  public final CommandContext deserializeContext(String context_p) {
    // Read model from string.
    Resource resource = new ContextResourceFactoryImpl().createResource(null);
    DocumentRoot root = null;
    try {
      ByteArrayInputStream inputStream = new ByteArrayInputStream(context_p.getBytes(UTF_8));
      resource.load(inputStream, null);
      root = (DocumentRoot) resource.getContents().get(0);
    } catch (Exception exception_p) {
      root = null;
    }
    // Precondition.
    if (null == root) {
      return null;
    }
    // Get context definition.
    ContextDefinition contextDefinition = root.getContextDefinition();
    if (null == contextDefinition) {
      return null;
    }
    // Get root status.
    Context context = contextDefinition.getContext();
    if (null == context) {
      return null;
    }
    return buildContext(context);
  }

  /**
   * De-serialize status from specified string.
   * @param string_p
   * @return <code>null</code> if the status could not be de-serialized.
   */
  public final CommandStatus deserializeStatus(String status_p) {
    // Read model from string.
    Resource resource = new StatusResourceFactoryImpl().createResource(null);
    com.thalesgroup.orchestra.framework.exchange.status.DocumentRoot root = null;
    try {
      ByteArrayInputStream inputStream = new ByteArrayInputStream(status_p.getBytes(UTF_8));
      resource.load(inputStream, null);
      root = (com.thalesgroup.orchestra.framework.exchange.status.DocumentRoot) resource.getContents().get(0);
    } catch (Exception exception_p) {
      root = null;
    }
    // Precondition.
    if (null == root) {
      return null;
    }
    // Get status definition.
    StatusDefinition statusDefinition = root.getStatusDefinition();
    if (null == statusDefinition) {
      return null;
    }
    // Get root status.
    Status status = statusDefinition.getStatus();
    if (null == status) {
      return null;
    }
    return buildStatus(status);
  }

  /**
   * Serialize specified context to string.
   * @param context_p
   * @return <code>null</code> if the context could not be serialized to a {@link String}.
   */
  public final String serializeContext(CommandContext context_p) {
    String result = null;
    // Create structure.
    Resource resource = new ContextResourceFactoryImpl().createResource(null);
    DocumentRoot root = ContextFactory.eINSTANCE.createDocumentRoot();
    ContextDefinition definition = ContextFactory.eINSTANCE.createContextDefinition();
    root.setContextDefinition(definition);
    Context context = ContextFactory.eINSTANCE.createContext();
    definition.setContext(context);
    // Convert context to model.
    context.setExportFilePath(context_p.getExportFilePath());
    context.setType(context_p.getCommandType());
    context.setKeepOpen(context_p.isKeepOpen());
    context.setLaunchArguments(context_p.getLaunchArguments());
    for (com.thalesgroup.orchestra.framework.connector.Artifact artifact : context_p.getArtifacts()) {
      Artifact contextArtifact = ContextFactory.eINSTANCE.createArtifact();
      contextArtifact.setRootPhysicalPath(artifact.getRootPhysicalPath());
      contextArtifact.setUri(artifact.getUri().getUri());
      contextArtifact.setEnvironmentId(artifact.getEnvironmentId());
      // Set properties.
      for (Map.Entry<String, String> entry : artifact.getEnvironmentProperties().entrySet()) {
        EnvironmentProperty property = ContextFactory.eINSTANCE.createEnvironmentProperty();
        property.setName(entry.getKey());
        property.setValue(entry.getValue());
        contextArtifact.getEnvironmentProperties().add(property);
      }
      context.getArtifact().add(contextArtifact);
    }
    // Add to resource.
    resource.getContents().add(root);
    // Try and persist it to String.
    try {
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      resource.save(outputStream, null);
      result = outputStream.toString(UTF_8);
      outputStream.close();
    } catch (Exception exception_p) {
      // Can't do it.
    }
    return result;
  }

  /**
   * Serialize specified status to string.
   * @param status_p
   * @return <code>null</code> if the status could not be serialized to a {@link String}.
   */
  public final String serializeStatus(CommandStatus status_p) {
    String result = null;
    Status status = buildStatus(status_p);
    // Precondition.
    if (null == status) {
      return null;
    }
    // Create structure.
    Resource resource = new StatusResourceFactoryImpl().createResource(null);
    com.thalesgroup.orchestra.framework.exchange.status.DocumentRoot root = StatusFactory.eINSTANCE.createDocumentRoot();
    StatusDefinition definition = StatusFactory.eINSTANCE.createStatusDefinition();
    root.setStatusDefinition(definition);
    definition.setStatus(status);
    // Add to resource.
    resource.getContents().add(root);
    // Try and persist it to String.
    try {
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      resource.save(outputStream, null);
      result = outputStream.toString(UTF_8);
      outputStream.close();
    } catch (Exception exception_p) {
      // Can't do it.
    }
    return result;
  }
}