/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment.registry;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;

import com.thalesgroup.orchestra.framework.common.CommonActivator;
import com.thalesgroup.orchestra.framework.common.helper.ExtensionPointHelper;
import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.framework.connector.CommandContext;
import com.thalesgroup.orchestra.framework.connector.CommandStatus;
import com.thalesgroup.orchestra.framework.environment.AbstractEnvironment;
import com.thalesgroup.orchestra.framework.environment.AbstractEnvironmentHandler;
import com.thalesgroup.orchestra.framework.environment.BaselineContext;
import com.thalesgroup.orchestra.framework.environment.BaselineResult;
import com.thalesgroup.orchestra.framework.environment.EnvironmentActivator;
import com.thalesgroup.orchestra.framework.environment.IEnvironment;
import com.thalesgroup.orchestra.framework.environment.IEnvironmentHandler;
import com.thalesgroup.orchestra.framework.environment.filesystem.FileSystemEnvironmentDefaultFiltersRegistry;
import com.thalesgroup.orchestra.framework.environment.validation.EnvironmentRuleRegistry;

/**
 * Environment registry.<br>
 * Creates environment objects and handles their life-cycles.
 * @author t0076261
 */
public class EnvironmentRegistry {
  /**
   * NO_ENVIRONMENT environment implementation.<br>
   * Used when there is no way to find/use the environment implementation in the platform.
   */
  protected static final IEnvironment NO_ENVIRONMENT = new AbstractEnvironment() {
    /**
     * @see com.thalesgroup.orchestra.framework.environment.IEnvironment#getArtifactsMetadata(com.thalesgroup.orchestra.framework.connector.CommandContext,
     *      org.eclipse.core.runtime.IProgressMonitor)
     */
    public CommandStatus getArtifactsMetadata(CommandContext context_p, IProgressMonitor progressMonitor_p) {
      return null;
    }

    /**
     * @see com.thalesgroup.orchestra.framework.environment.IEnvironment#getRootArtifacts(com.thalesgroup.orchestra.framework.connector.CommandContext,
     *      org.eclipse.core.runtime.IProgressMonitor)
     */
    public CommandStatus getRootArtifacts(CommandContext context_p, IProgressMonitor progressMonitor_p) {
      return null;
    }

    /**
     * @see com.thalesgroup.orchestra.framework.environment.IEnvironment#isHandlingArtifacts(com.thalesgroup.orchestra.framework.connector.CommandContext)
     */
    public CommandStatus isHandlingArtifacts(CommandContext context_p) {
      return null;
    }

    /**
     * @see com.thalesgroup.orchestra.framework.environment.IEnvironment#makeBaseline(com.thalesgroup.orchestra.framework.environment.BaselineContext,
     *      org.eclipse.core.runtime.IProgressMonitor)
     */
    public BaselineResult makeBaseline(BaselineContext baselineContext_p, IProgressMonitor monitor_p) {
      return null;
    }
  };
  /**
   * Default filters registry for FileSystem Environments.
   */
  private final FileSystemEnvironmentDefaultFiltersRegistry _fileSystemEnvironmentDefaultFiltersRegistry;
  /**
   * Environment declaration id to descriptor map.
   */
  private final Map<String, EnvironmentDescriptor> _idToDescriptor;
  /**
   * Rules registry.
   */
  private final EnvironmentRuleRegistry _rulesRegistry;

  /**
   * Constructor.
   */
  public EnvironmentRegistry() {
    // Initialize maps.
    _idToDescriptor = new HashMap<String, EnvironmentDescriptor>(0);
    // And rules registry.
    _rulesRegistry = new EnvironmentRuleRegistry();
    // Default filters registry.
    _fileSystemEnvironmentDefaultFiltersRegistry = new FileSystemEnvironmentDefaultFiltersRegistry();
  }

  /**
   * Clear all runtime data, but keep descriptors alive.
   */
  public void clear() {
    // Clear runtime data by cycling through descriptors.
    for (EnvironmentDescriptor descriptor : _idToDescriptor.values()) {
      descriptor.clear();
    }
  }

  /**
   * Dispose registry resources.<br>
   * All descriptors are lost.<br>
   * This should only be called at application shutdown time.
   */
  public void dispose() {
    _idToDescriptor.clear();
  }

  /**
   * Get all available descriptors.
   * @return
   */
  public Collection<EnvironmentDescriptor> getDescriptors() {
    return new ArrayList<EnvironmentRegistry.EnvironmentDescriptor>(_idToDescriptor.values());
  }

  /**
   * Get or create environment for specified parameters.
   * @param declarationId_p The environment declaration id, as defined by its contributing extension. This is the environment type.
   * @param runtimeId_p A runtime id, used to identify this environment for specific parameters. This is the environment object identifier.
   * @return A {@link Couple} of ({@link IStatus}, {@link IEnvironment}). The status is never <code>null</code>, whereas the environment can be (in an error
   *         case).
   */
  public Couple<IStatus, IEnvironment> getEnvironment(String declarationId_p, String runtimeId_p) {
    String pluginId = EnvironmentActivator.getInstance().getPluginId();
    if ((null == declarationId_p) || (null == runtimeId_p)) {
      return new Couple<IStatus, IEnvironment>(new Status(IStatus.ERROR, pluginId, Messages.EnvironmentRegistry_GetEnvironment_Error_IncorrectParameters), null);
    }
    EnvironmentDescriptor environmentDescriptor = _idToDescriptor.get(declarationId_p);
    if (null == environmentDescriptor) {
      return new Couple<IStatus, IEnvironment>(new Status(IStatus.ERROR, pluginId, MessageFormat.format(
          Messages.EnvironmentRegistry_GetEnvironment_Error_NoDescriptorForId, declarationId_p)), null);
    }
    IEnvironment environment = environmentDescriptor.getEnvironment(runtimeId_p);
    if (NO_ENVIRONMENT == environment) {
      return new Couple<IStatus, IEnvironment>(new Status(IStatus.ERROR, pluginId, MessageFormat.format(
          Messages.EnvironmentRegistry_GetEnvironment_Error_CouldNotCreateNewInstance, declarationId_p)), null);
    }
    return new Couple<IStatus, IEnvironment>(new Status(IStatus.OK, pluginId, MessageFormat.format(Messages.EnvironmentRegistry_GetEnvironment_Successful,
        declarationId_p, runtimeId_p)), environment);
  }

  /**
   * Get descriptor for specified ID.
   * @param declarationId_p The environment declaration id, as defined by its contributing extension. This is the environment type.
   * @return
   */
  public Couple<IStatus, EnvironmentDescriptor> getEnvironmentDescriptor(String declarationId_p) {
    String pluginId = EnvironmentActivator.getInstance().getPluginId();
    if (null == declarationId_p) {
      return new Couple<IStatus, EnvironmentDescriptor>(new Status(IStatus.ERROR, pluginId,
          Messages.EnvironmentRegistry_GetEnvironmentDescriptor_Error_IncorrectParameters), null);
    }
    EnvironmentDescriptor environmentDescriptor = _idToDescriptor.get(declarationId_p);
    if (null == environmentDescriptor) {
      return new Couple<IStatus, EnvironmentDescriptor>(new Status(IStatus.ERROR, pluginId, MessageFormat.format(
          Messages.EnvironmentRegistry_GetEnvironmentDescriptor_Error_NoDescriptorForId, declarationId_p)), null);
    }
    return new Couple<IStatus, EnvironmentDescriptor>(new Status(IStatus.OK, pluginId, MessageFormat.format(
        Messages.EnvironmentRegistry_GetEnvironmentDescriptor_Successful, declarationId_p)), environmentDescriptor);
  }

  /**
   * Get environment handler for specified parameters.
   * @param declarationId_p The environment declaration id, as defined by its contributing extension. This is the environment type.
   * @return A {@link Couple} of ({@link IStatus}, {@link IEnvironmentHandler}). The status is never <code>null</code>, whereas the environment handler can be
   *         (in an error case).
   */
  public Couple<IStatus, IEnvironmentHandler> getEnvironmentHandler(String declarationId_p) {
    String pluginId = EnvironmentActivator.getInstance().getPluginId();
    if (null == declarationId_p) {
      return new Couple<IStatus, IEnvironmentHandler>(new Status(IStatus.ERROR, pluginId,
          Messages.EnvironmentRegistry_GetEnvironmentHandler_Error_IncorrectParameters), null);
    }
    EnvironmentDescriptor environmentDescriptor = _idToDescriptor.get(declarationId_p);
    if (null == environmentDescriptor) {
      return new Couple<IStatus, IEnvironmentHandler>(new Status(IStatus.ERROR, pluginId, MessageFormat.format(
          Messages.EnvironmentRegistry_GetEnvironmentHandler_Error_NoDescriptorForId, declarationId_p)), null);
    }
    IEnvironmentHandler environmentHandler = environmentDescriptor._handler;
    if (null == environmentHandler) {
      return new Couple<IStatus, IEnvironmentHandler>(new Status(IStatus.ERROR, pluginId, MessageFormat.format(
          Messages.EnvironmentRegistry_GetEnvironmentHandler_Error_CouldNotCreateNewInstance, declarationId_p)), null);
    }
    return new Couple<IStatus, IEnvironmentHandler>(new Status(IStatus.OK, pluginId, MessageFormat.format(
        Messages.EnvironmentRegistry_GetEnvironmentHandler_Successful, declarationId_p)), environmentHandler);
  }

  /**
   * Get {@link FileSystemEnvironmentDefaultFiltersRegistry} unique instance.
   * @return
   */
  public FileSystemEnvironmentDefaultFiltersRegistry getFileSystemEnvironmentDefaultFiltersRegistry() {
    return _fileSystemEnvironmentDefaultFiltersRegistry;
  }

  /**
   * Get {@link EnvironmentRuleRegistry} unique instance.
   * @return
   */
  public EnvironmentRuleRegistry getRuleRegistry() {
    return _rulesRegistry;
  }

  /**
   * Initialize registry.
   * @return
   */
  public IStatus initialize() {
    MultiStatus result = new MultiStatus(EnvironmentActivator.getInstance().getPluginId(), 0, Messages.EnvironmentRegistry_Status_Initialization, null);
    // Create descriptors from extensions.
    IConfigurationElement[] configurationElements =
        ExtensionPointHelper.getConfigurationElements("com.thalesgroup.orchestra.framework.environment", "environmentDeclaration"); //$NON-NLS-1$ //$NON-NLS-2$
    // Cycle through extensions.
    for (IConfigurationElement configurationElement : configurationElements) {
      EnvironmentDescriptor descriptor = new EnvironmentDescriptor();
      IStatus status = descriptor.initialize(configurationElement);
      if (status.isOK()) {
        _idToDescriptor.put(descriptor._declarationId, descriptor);
      }
      result.add(status);
    }
    // Initialize rules registry.
    IStatus rulesRegistryInitStatus = _rulesRegistry.initialize();
    result.add(rulesRegistryInitStatus);
    // Initialize Default filters for FileSystem Environments.
    IStatus defaultFiltersInitStatus = _fileSystemEnvironmentDefaultFiltersRegistry.initialize();
    result.add(defaultFiltersInitStatus);
    return result;
  }

  /**
   * Environment descriptor.
   * @author t0076261
   */
  public class EnvironmentDescriptor {
    /**
     * Environment logical container label (per id).
     */
    protected String _category;
    /**
     * Extension structure.
     */
    protected IConfigurationElement _configurationElement;
    /**
     * Environment declaration id as defined by the extension.
     */
    protected String _declarationId;
    /**
     * Unique handler for this descriptor scope.
     */
    protected IEnvironmentHandler _handler;
    /**
     * Runtime id to instance map for this descriptor scope.
     */
    protected Map<String, IEnvironment> _instances;
    /**
     * Environment displayable label (per id).
     */
    protected String _label;
    /**
     * Optional version.
     */
    protected String _version;

    /**
     * Constructor.
     */
    protected EnvironmentDescriptor() {
      _instances = new HashMap<String, IEnvironment>(0);
    }

    /**
     * Clear descriptor temporary resources.
     */
    protected void clear() {
      _instances.clear();
    }

    /**
     * Get category label.
     * @return
     */
    public String getCategory() {
      return _category;
    }

    /**
     * Get environment declaration id.
     * @return
     */
    public String getDeclarationId() {
      return _declarationId;
    }

    /**
     * Get or create an environment object for specified runtime id.
     * @param runtimeId_p The model id used within current context to refer to a specific use of an environment.
     * @return {@link EnvironmentRegistry#NO_ENVIRONMENT} if no environment could be successfully loaded.
     */
    protected IEnvironment getEnvironment(String runtimeId_p) {
      // Get result from map.
      IEnvironment environment = _instances.get(runtimeId_p);
      if (null != environment) {
        return environment;
      }
      // Search for implementation.
      try {
        environment = (IEnvironment) ExtensionPointHelper.createInstance(_configurationElement, ExtensionPointHelper.ATT_CLASS);
      } catch (Exception exception_p) {
        CommonActivator
            .getInstance()
            .getLog()
            .log(
                new Status(IStatus.ERROR, EnvironmentActivator.getInstance().getPluginId(), MessageFormat.format(
                    Messages.EnvironmentRegistry_Descriptor_GetEnvironment_Error_CouldNotCreateNewInstance, _declarationId)));
      }
      // No way this environment could be loaded.
      if (null == environment) {
        environment = NO_ENVIRONMENT;
      }
      // Set declaration id.
      if (environment instanceof AbstractEnvironment) {
        AbstractEnvironment abstractEnvironment = (AbstractEnvironment) environment;
        abstractEnvironment.setDeclarationId(_declarationId);
        abstractEnvironment.setRuntimeId(runtimeId_p);
        abstractEnvironment.setEnvironmentHandler(_handler);
      }
      // Retain reference.
      _instances.put(runtimeId_p, environment);
      return environment;
    }

    /**
     * Get all known instances of environment for this descriptor.
     * @return
     */
    protected Collection<IEnvironment> getEnvironments() {
      return _instances.values();
    }

    /**
     * Get environment handler instance.
     * @return
     */
    public IEnvironmentHandler getHandler() {
      return _handler;
    }

    /**
     * Get label.
     * @return
     */
    public String getLabel() {
      return _label;
    }

    /**
     * Get version.
     * @return
     */
    public String getVersion() {
      return _version;
    }

    /**
     * Initialize descriptor.
     * @param configurationElement_p
     * @return
     */
    protected IStatus initialize(IConfigurationElement configurationElement_p) {
      String pluginId = EnvironmentActivator.getInstance().getPluginId();
      // Unlikely precondition.
      if (null == configurationElement_p) {
        return new Status(IStatus.ERROR, pluginId, Messages.EnvironmentRegistry_Descriptor_Initialize_Error_IncorrectParameters);
      }
      _configurationElement = configurationElement_p;
      _declarationId = ExtensionPointHelper.getId(configurationElement_p);
      // There must be an id.
      if (null == _declarationId) {
        return new Status(IStatus.ERROR, pluginId, MessageFormat.format(Messages.EnvironmentRegistry_Descriptor_Initialize_Error_ExtensionDoesNotProvideWithId,
            configurationElement_p.getContributor().getName()));
      }
      // Class and handler are required too.
      if ((null == _configurationElement.getAttribute(ExtensionPointHelper.ATT_CLASS)) || (null == _configurationElement.getAttribute("handler"))) { //$NON-NLS-1$
        return new Status(IStatus.ERROR, pluginId, MessageFormat.format(
            Messages.EnvironmentRegistry_Descriptor_Initialize_Error_ExtensionDoesNotProvideImplementations, _declarationId));
      }
      _handler = (IEnvironmentHandler) ExtensionPointHelper.createInstance(configurationElement_p, "handler"); //$NON-NLS-1$
      // Set declaration ID.
      if (_handler instanceof AbstractEnvironmentHandler) {
        ((AbstractEnvironmentHandler) _handler).setDeclarationId(_declarationId);
      }
      if (null == _handler) {
        return new Status(IStatus.ERROR, pluginId, MessageFormat.format(
            Messages.EnvironmentRegistry_Descriptor_Initialize_Error_CouldNotCreateNewHandlerInstance, _declarationId));
      }
      _label = _configurationElement.getAttribute("label"); //$NON-NLS-1$
      _category = _configurationElement.getAttribute("category"); //$NON-NLS-1$
      _version = _configurationElement.getAttribute("version"); //$NON-NLS-1$
      return new Status(IStatus.OK, pluginId, MessageFormat.format(Messages.EnvironmentRegistry_Descriptor_Initialize_Successful, _declarationId));
    }
  }
}