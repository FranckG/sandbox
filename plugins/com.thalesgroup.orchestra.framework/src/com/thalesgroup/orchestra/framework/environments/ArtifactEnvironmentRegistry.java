/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environments;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.framework.connector.Artifact;
import com.thalesgroup.orchestra.framework.connector.CommandContext;
import com.thalesgroup.orchestra.framework.connector.CommandStatus;
import com.thalesgroup.orchestra.framework.environment.EnvironmentActivator;
import com.thalesgroup.orchestra.framework.environment.IEnvironment;
import com.thalesgroup.orchestra.framework.environment.IEnvironmentConstants;
import com.thalesgroup.orchestra.framework.environment.registry.EnvironmentRegistry;
import com.thalesgroup.orchestra.framework.environment.ui.AbstractVariablesHandler;
import com.thalesgroup.orchestra.framework.environment.ui.IVariablesHandler;
import com.thalesgroup.orchestra.framework.exchange.GefHandler;
import com.thalesgroup.orchestra.framework.gef.Element;
import com.thalesgroup.orchestra.framework.gef.GEF;
import com.thalesgroup.orchestra.framework.gef.GefFactory;
import com.thalesgroup.orchestra.framework.gef.GenericExportFormat;
import com.thalesgroup.orchestra.framework.gef.Properties;
import com.thalesgroup.orchestra.framework.gef.Property;
import com.thalesgroup.orchestra.framework.internal.connector.StatusModelHelper;
import com.thalesgroup.orchestra.framework.lib.constants.ICommandConstants;
import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariable;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;

/**
 * An environment registry dedicated to artifacts handling.<br>
 * Does use the {@link EnvironmentRegistry} as a pool of descriptors.<br>
 * Maintain its own list of active environments, based on current context.
 * @author t0076261
 */
public class ArtifactEnvironmentRegistry extends AbstractEnvironmentsRegistry {
  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironment#getArtifactsMetadata(com.thalesgroup.orchestra.framework.connector.CommandContext,
   *      org.eclipse.core.runtime.IProgressMonitor)
   */
  public CommandStatus getArtifactsMetadata(CommandContext context_p, IProgressMonitor progressMonitor_p) {
    List<CommandStatus> errors = new ArrayList<CommandStatus>(0);
    Map<IEnvironment, CommandContext> environmentsToContexts = new HashMap<IEnvironment, CommandContext>(0);
    GefWriter gefWriter = new GefWriter();
    // Cycle through artifacts so as to sort them by handling environment.
    for (Artifact artifact : context_p.getArtifacts()) {
      OrchestraURI uri = artifact.getUri();
      // Search for environment ID if provided by the target URI.
      String environmentId = artifact.getEnvironmentId();
      // Replace URI with absolute one.
      uri.makeAbsolute();
      // Get environment.
      IEnvironment resultingEnvironment = (null != environmentId) ? _environments.get(environmentId) : null;
      // Unable to handle this artifact.
      if (null == resultingEnvironment) {
        errors.add(new CommandStatus(IStatus.ERROR, MessageFormat.format(Messages.ArtifactEnvironmentRegistry_Command_Error_NoEnvironment, uri.getUri()), uri,
            0));
        continue;
      }
      // Sort by environment.
      CommandContext context = environmentsToContexts.get(resultingEnvironment);
      // Create context.
      if (null == context) {
        String subExportFile = StatusModelHelper.createDataFileName(ICommandConstants.GET_ARTIFACTS_METADATA, "Framework", gefWriter.getFileExtension()); //$NON-NLS-1$
        context = new CommandContext(ICommandConstants.GET_ARTIFACTS_METADATA, subExportFile);
        environmentsToContexts.put(resultingEnvironment, context);
      }
      // Add new URI.
      context.addArtifact(artifact);
    }
    // Cycle through environments and associated context.
    for (Map.Entry<IEnvironment, CommandContext> entry : environmentsToContexts.entrySet()) {
      CommandContext context = entry.getValue();
      CommandStatus metadataStatus = entry.getKey().getArtifactsMetadata(context, null);
      // Keep errors.
      for (CommandStatus commandStatus : metadataStatus.getChildren()) {
        if (!commandStatus.isOK()) {
          errors.add(commandStatus);
        }
      }
      // Load results.
      gefWriter.loadModel(context.getExportFilePath());
    }
    // Create resulting GEF and status.
    return gefWriter.createComposingGef(context_p, errors, null);
  }

  /**
   * Get environment representation from specified element.
   * @param element_p
   * @return {@link ICommonConstants#EMPTY_STRING} if no environment representation could be found.
   */
  protected String getEnvironmentRepresentation(Element element_p) {
    // Precondition.
    if (null == element_p) {
      return ICommonConstants.EMPTY_STRING;
    }
    // Get element properties container.
    List<Properties> properties = element_p.getPROPERTIES();
    // Precondition.
    if (properties.isEmpty()) {
      return ICommonConstants.EMPTY_STRING;
    }
    // Cycle through properties.
    for (Properties propertiies : properties) {
      for (Property property : propertiies.getPROPERTY()) {
        // Search for environment property.
        if (IEnvironmentConstants.ELEMENT_PROPERTY_NAME_ENVIRONMENT.equals(property.getName())) {
          // Name is the only (expected) value.
          List<String> environmentName = GefHandler.getValue(property);
          if ((null != environmentName) && !environmentName.isEmpty()) {
            return environmentName.get(0);
          }
        }
      }
    }
    return ICommonConstants.EMPTY_STRING;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environments.EnvironmentsHub.AbstractEnvironmentsRegistry#getEnvironmentsSourceVariable(com.thalesgroup.orchestra.framework.model.contexts.Context)
   */
  @Override
  protected EnvironmentVariable getEnvironmentsSourceVariable(Context currentContext_p) {
    return (EnvironmentVariable) DataUtil.getVariable(DataUtil.__ARTEFACTPATH_VARIABLE_NAME, currentContext_p);
  }

  /**
   * Get handling environment runtime ID for specified URI.
   * @param uri_p
   * @return <code>null</code> if current context does not make use of any environment that handles specified URI.
   */
  public String getHandlingEnvironmentId(OrchestraURI uri_p) {
    // Precondition.
    if (null == uri_p) {
      return null;
    }
    // Check URI parameter first.
    String environmentId = uri_p.getParameters().get("environmentId"); //$NON-NLS-1$
    if (null != environmentId) {
      return environmentId;
    }
    // Cycle through environments so as to find the one that handles this URI.
    {
      // Create a calling context first.
      CommandContext context = new CommandContext(ICommonConstants.EMPTY_STRING, null);
      Artifact artifact = new Artifact(uri_p, null);
      context.addArtifact(artifact);
      // Then search for the environment.
      if (null != _environments) {
        for (Entry<String, IEnvironment> entry : _environments.entrySet()) {
          IEnvironment environment = entry.getValue();
          CommandStatus handlingArtifacts = environment.isHandlingArtifacts(context);
          if (handlingArtifacts.isOK()) {
            return environment.getRuntimeId();
          }
        }
      }
    }
    return null;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironment#getRootArtifacts(com.thalesgroup.orchestra.framework.connector.CommandContext,
   *      org.eclipse.core.runtime.IProgressMonitor)
   */
  public CommandStatus getRootArtifacts(CommandContext context_p, IProgressMonitor progressMonitor_p) {
    // GEF writer.
    final GefWriter gefWriter = new GefWriter();
    // Cycle through environments.
    for (Entry<String, IEnvironment> entry : _environments.entrySet()) {
      IEnvironment environment = entry.getValue();
      String subExportFile = StatusModelHelper.createDataFileName(ICommandConstants.GET_ROOT_ARTIFACTS, "Framework", gefWriter.getFileExtension()); //$NON-NLS-1$
      CommandContext context = new CommandContext(context_p.getCommandType(), subExportFile);
      // Get root artifacts.
      environment.getRootArtifacts(context, null);
      gefWriter.loadEnvironmentModel(context.getExportFilePath(), environment.getRuntimeId());
    }
    return gefWriter.createComposingGef(context_p, null, new IGefVisitor() {
      // Reconstructed elements structure.
      Map<String, List<Element>> _allElements = new HashMap<String, List<Element>>(0);

      /**
       * @see com.thalesgroup.orchestra.framework.environments.ArtifactEnvironmentRegistry.IGefVisitor#dispose()
       */
      public void dispose() {
        _allElements.clear();
      }

      /**
       * @see com.thalesgroup.orchestra.framework.environments.ArtifactEnvironmentRegistry.IGefVisitor#handleElement(com.thalesgroup.orchestra.framework.gef.Element)
       */
      public CommandStatus handleElement(Element element_p) {
        String uri = element_p.getUri();
        List<Element> elements = _allElements.get(uri);
        // The URI does not point to a unique artifact.
        if (elements.size() > 1) {
          // Create children statuses.
          List<CommandStatus> duplicateEntriesStatuses = new ArrayList<CommandStatus>(elements.size());
          for (Element element : elements) {
            duplicateEntriesStatuses.add(new CommandStatus(IStatus.WARNING, getEnvironmentRepresentation(element), null, 0));
          }
          // Create parent status.
          CommandStatus parentStatus =
              new CommandStatus(IStatus.WARNING, MessageFormat.format(Messages.ArtifactEnvironmentRegistry_RootArtefacts_Error_ArtefactIsNotUnique, uri),
                  new OrchestraURI(uri), 0);
          parentStatus.addChildren(duplicateEntriesStatuses);
          return parentStatus;
        }
        return new CommandStatus(IStatus.OK, uri, new OrchestraURI(uri), 0);
      }

      /**
       * @see com.thalesgroup.orchestra.framework.environments.ArtifactEnvironmentRegistry.IGefVisitor#prepare()
       */
      public void prepare() {
        for (GEF gef : gefWriter._loadedGEFs) {
          String envId = gefWriter.getEnvironmentIdFromGef(gef);
          // Cycle through resulting elements.
          for (GenericExportFormat genericExportFormat : gef.getGENERICEXPORTFORMAT()) {
            for (Element element : genericExportFormat.getELEMENT()) {
              String uri = element.getUri();
              List<Element> elements = _allElements.get(uri);
              if (null == elements) {
                elements = new ArrayList<Element>(1);
                _allElements.put(uri, elements);
              }
              // Add element to all elements structure.
              elements.add(element);
              // Add environment runtime Id to element properties
              Property envIdProperty = GefFactory.eINSTANCE.createProperty();
              envIdProperty.setName("EnvironmentId");
              GefHandler.addValue(envIdProperty, GefHandler.ValueType.CDATA, envId);
              element.getPROPERTIES().get(0).getPROPERTY().add(envIdProperty);
            }
          }
        }
      }
    });
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.IEnvironment#isHandlingArtifacts(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  public CommandStatus isHandlingArtifacts(CommandContext context_p) {
    return null;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.AbstractEnvironment#transcript(com.thalesgroup.orchestra.framework.connector.CommandContext,
   *      org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  public CommandStatus transcript(CommandContext context_p, IProgressMonitor progressMonitor_p) {
    List<CommandStatus> results = new ArrayList<CommandStatus>(0);
    boolean atLeastOneErrorOrWarning = false;
    // Cycle through artifacts.
    for (Artifact artifact : context_p.getArtifacts()) {
      IEnvironment environment = null;
      // Get environment ID.
      String environmentId = artifact.getEnvironmentId();
      if (null == environmentId) {
        environmentId = getHandlingEnvironmentId(artifact.getUri());
      }
      // Get environment by ID.
      if (null != environmentId) {
        environment = _environments.get(environmentId);
      }
      // Precondition.
      if (null == environment) {
        atLeastOneErrorOrWarning = true;
        OrchestraURI uri = artifact.getUri();
        results.add(new CommandStatus(IStatus.WARNING, MessageFormat.format(Messages.ArtifactEnvironmentRegistry_Command_Error_NoEnvironment, uri.getUri()),
            uri, 0));
        continue;
      }
      // The environment must use transcription.
      if (!environment.useTranscription()) {
        atLeastOneErrorOrWarning = true;
        OrchestraURI uri = artifact.getUri();
        results.add(new CommandStatus(IStatus.WARNING, Messages.ArtifactEnvironmentRegistry_Transcription_Warning_NoTranscriptionAvailable, uri, 0));
        continue;
      }
      // Transcript URIs one by one within the environment.
      CommandContext context = new CommandContext(ICommandConstants.TRANSCRIPTION, null);
      context.addArtifact(artifact);
      CommandStatus status = environment.transcript(context, null);
      // The environment has returned a match for this context.
      if ((IStatus.OK == status.getSeverity()) || (IStatus.ERROR == status.getSeverity())) {
        CommandStatus statusForUri = status.getStatusForUri(artifact.getUri());
        // The environment is indeed dealing with this URI, either successfully or not.
        // In both cases, stop here.
        if (null != statusForUri) {
          results.add(statusForUri);
          atLeastOneErrorOrWarning |= (IStatus.ERROR == statusForUri.getSeverity());
          continue;
        }
      }
    }
    // Build result.
    int resultingSeverity = IStatus.OK;
    if (atLeastOneErrorOrWarning) {
      resultingSeverity = IStatus.WARNING;
    }
    CommandStatus result = new CommandStatus(resultingSeverity, ICommonConstants.EMPTY_STRING, null, 0);
    result.addChildren(results);
    return result;
  }

  /**
   * Transcript specified URI.
   * @param uri_p The URI to transcript.
   * @param environmentId_p The environment ID to use. <code>null</code> if unknown.
   * @return see {@link #transcript(CommandContext)}.
   */
  public CommandStatus transcript(OrchestraURI uri_p, String environmentId_p) {
    if (null == uri_p) {
      return new CommandStatus(IStatus.ERROR, Messages.ArtifactEnvironmentRegistry_Command_Error_InvalidArguments, uri_p, 0);
    }
    // Create calling context.
    CommandContext context = new CommandContext(ICommandConstants.TRANSCRIPTION, null);
    context.addArtifact(new Artifact(uri_p, environmentId_p));
    // Do transcript.
    return transcript(context, null);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.environment.AbstractEnvironment#useTranscription()
   */
  @Override
  public boolean useTranscription() {
    return true;
  }

  /**
   * A GEF writer that composes all previously loaded GEF into a single resulting one.
   * @author t0076261
   */
  protected class GefWriter extends GefHandler {
    /**
     * Loaded GEF models.
     */
    protected List<GEF> _loadedGEFs = new ArrayList<GEF>(0);

    protected Map<GEF, String> _gefToEnvId = new HashMap<GEF, String>();

    /**
     * Create and save composing GEF from loaded ones.<br>
     * Before calling this method, the implementation must make sure that at least one GEF model to be composed has been loaded (using the
     * {@link #loadModel(String)} method).
     * @param context_p
     * @param errors_p
     * @param visitor_p
     * @return The composed resulting status.
     */
    protected CommandStatus createComposingGef(CommandContext context_p, List<CommandStatus> errors_p, IGefVisitor visitor_p) {
      // Prepare visitor.
      if (null != visitor_p) {
        visitor_p.prepare();
      }
      // Errors.
      List<CommandStatus> errors = null;
      if (null == errors_p) {
        errors = new ArrayList<CommandStatus>(0);
      } else {
        errors = new ArrayList<CommandStatus>(errors_p);
      }
      // Resulting GEF.
      GEF resultingGef = createNewModel(context_p.getExportFilePath());
      GenericExportFormat resultingGenericExportFormat = GefFactory.eINSTANCE.createGenericExportFormat();
      resultingGef.getGENERICEXPORTFORMAT().add(resultingGenericExportFormat);
      List<Element> elementsToKeep = new ArrayList<Element>(0);
      try {
        // Cycle through loaded GEF.
        for (GEF gef : _loadedGEFs) {
          // Cycle through resulting elements.
          for (GenericExportFormat genericExportFormat : gef.getGENERICEXPORTFORMAT()) {
            for (Element element : genericExportFormat.getELEMENT()) {
              // Use visitor when available.
              if (null != visitor_p) {
                CommandStatus status = visitor_p.handleElement(element);
                if (status.isOK()) {
                  elementsToKeep.add(element);
                } else {
                  errors.add(status);
                }
              } else {
                // Otherwise, keep the element.
                elementsToKeep.add(element);
              }
            }
          }
        }
      } finally {
        if (null != visitor_p) {
          visitor_p.dispose();
        }
      }
      // Save resulting GEF.
      // Add elements.
      for (Element element : elementsToKeep) {
        resultingGenericExportFormat.getELEMENT().add(element);
      }
      // Do save the model.
      saveModel(resultingGef, false);
      // And clean data.
      elementsToKeep.clear();
      _loadedGEFs.clear();
      clean();
      // Create resulting status.
      int severity = IStatus.OK;
      if (errors.size() > 0) {
        severity = IStatus.WARNING;
      }
      CommandStatus status = new CommandStatus(severity, ICommonConstants.EMPTY_STRING, null, 0);
      status.addChildren(errors);
      return status;
    }

    /**
     * @see com.thalesgroup.orchestra.framework.common.model.AbstractModelHandler#loadModel(java.lang.String)
     */
    @Override
    public GEF loadModel(String fileModelAbsolutePath_p) {
      GEF gef = super.loadModel(fileModelAbsolutePath_p);
      if (null == _loadedGEFs) {
        _loadedGEFs = new ArrayList<GEF>(1);
      }
      _loadedGEFs.add(gef);
      return gef;
    }

    public GEF loadEnvironmentModel(String fileModelAbsolutePath_p, String environmentId_p) {
      GEF gef = loadModel(fileModelAbsolutePath_p);
      _gefToEnvId.put(gef, environmentId_p);
      return gef;
    }

    public String getEnvironmentIdFromGef(GEF gef) {
      return _gefToEnvId.get(gef);
    }
  }

  protected interface IGefVisitor {
    public void dispose();

    public CommandStatus handleElement(Element element_p);

    public void prepare();
  }

  /**
   * @param context_p the context for which we want to retrieve the environment.
   * @return a map containing the environment and the id in the key
   */
  public Couple<Map<String, IEnvironment>, IStatus> getEnvironments(final Context context_p, IProgressMonitor progressMonitor_p, boolean allowUserInteractions_p) {

    // Initialize environments structure.
    Couple<Map<String, IEnvironment>, IStatus> environments =
        new Couple<Map<String, IEnvironment>, IStatus>(new LinkedHashMap<String, IEnvironment>(0), Status.OK_STATUS);

    // Set variables handler for environments to use.
    IVariablesHandler handler = new AbstractVariablesHandler() {
      /**
       * @see com.thalesgroup.orchestra.framework.environment.ui.IVariablesHandler#getSubstitutedValue(java.lang.String)
       */
      @Override
      public String getSubstitutedValue(String rawValue_p) {
        // Precondition.
        if (null == rawValue_p) {
          return null;
        }
        return DataUtil.getSubstitutedValue(rawValue_p, context_p);
      }
    };
    {
      EnvironmentActivator.getInstance().setVariablesHandler(handler);
      try {

        // Progress reporting.
        if (null != progressMonitor_p) {
          progressMonitor_p.setTaskName(Messages.ArtifactEnvironment_GetContextEnvironmentsMessage);
        }
        environments = getContextEnvironments(context_p, progressMonitor_p, allowUserInteractions_p);
      } finally {
        EnvironmentActivator.getInstance().setVariablesHandler(null);
      }

    }

    return environments;

  }

}