/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.internal.connector;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.internal.registry.ConfigurationElementHandle;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ecf.core.ContainerFactory;
import org.eclipse.ecf.core.IContainer;
import org.eclipse.ecf.core.IContainerListener;
import org.eclipse.ecf.core.events.IContainerEvent;
import org.eclipse.ecf.core.identity.ID;
import org.eclipse.ecf.core.identity.IDFactory;
import org.eclipse.ecf.remoteservice.IRemoteCall;
import org.eclipse.ecf.remoteservice.IRemoteService;
import org.eclipse.ecf.remoteservice.IRemoteServiceContainerAdapter;
import org.eclipse.ecf.remoteservice.IRemoteServiceListener;
import org.eclipse.ecf.remoteservice.IRemoteServiceReference;
import org.eclipse.ecf.remoteservice.events.IRemoteServiceEvent;
import org.eclipse.ecf.remoteservice.events.IRemoteServiceRegisteredEvent;
import org.eclipse.ecf.remoteservice.events.IRemoteServiceUnregisteredEvent;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.thalesgroup.orchestra.framework.FrameworkActivator;
import com.thalesgroup.orchestra.framework.application.FrameworkConsoleView.LogLevel;
import com.thalesgroup.orchestra.framework.common.CommonActivator;
import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.common.helper.ExtensionPointHelper;
import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.framework.config.ConfigServer;
import com.thalesgroup.orchestra.framework.connector.AbstractConnector;
import com.thalesgroup.orchestra.framework.connector.CommandContext;
import com.thalesgroup.orchestra.framework.connector.CommandStatus;
import com.thalesgroup.orchestra.framework.connector.ConnectorActivator;
import com.thalesgroup.orchestra.framework.connector.IConnector;
import com.thalesgroup.orchestra.framework.connector.IConnectorOptionConstants;
import com.thalesgroup.orchestra.framework.connector.IConnectorRegistry;
import com.thalesgroup.orchestra.framework.ecf.services.EcfServicesActivator;
import com.thalesgroup.orchestra.framework.ecf.services.connector.IRemoteConnector;
import com.thalesgroup.orchestra.framework.ecf.services.connector.IRemoteConnectorProxy;
import com.thalesgroup.orchestra.framework.ecf.services.handler.ClientConnectorHandler;
import com.thalesgroup.orchestra.framework.lib.base.conf.ServerConfParam;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.Category;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.model.handler.data.IRemoteContextChangeListenersHandler;
import com.thalesgroup.orchestra.framework.models.connector.Connector;
import com.thalesgroup.orchestra.framework.models.connector.ConnectorDefinition;
import com.thalesgroup.orchestra.framework.models.connector.DocumentRoot;
import com.thalesgroup.orchestra.framework.models.connector.Service;
import com.thalesgroup.orchestra.framework.models.connector.Type;
import com.thalesgroup.orchestra.framework.remote.services.context.IRemoteContextChangeListener;
import com.thalesgroup.orchestra.framework.remote.services.context.IRemoteContextChangeListenerProxy;
import com.thalesgroup.orchestra.framework.remote.services.context.IRemoteContextChangeListenerProxy2;
import com.thalesgroup.orchestra.framework.remote.services.context.serialization.DeserializedStatus;
import com.thalesgroup.orchestra.framework.remote.services.context.serialization.SerializationHelper;
import com.thalesgroup.orchestra.framework.transcription.Association;
import com.thalesgroup.orchestra.framework.transcription.TranscriptionHelper;

/**
 * Connector registry.<br>
 * Creates connectors objects and handles their life-cycles.
 * @author t0076261
 */
public class ConnectorRegistry implements IConnectorRegistry {
  /**
   * NO_CONFIGURATION_ELEMENT configuration element instance.<br>
   * Used when there is no way to find the connector configuration element in the platform.
   */
  protected static final IConfigurationElement NO_CONFIGURATION_ELEMENT = new ConfigurationElementHandle(null, 0);
  /**
   * NO_CONNECTOR connector implementation.<br>
   * Used when there is no way to find the connector implementation in the platform.
   */
  protected static final IConnector NO_CONNECTOR = new AbstractConnector() {
    /**
     * @see com.thalesgroup.orchestra.framework.connector.IConnector#create(com.thalesgroup.orchestra.framework.connector.CommandContext)
     */
    public CommandStatus create(CommandContext context_p) throws Exception {
      return null;
    }

    /**
     * @see com.thalesgroup.orchestra.framework.connector.IConnector#documentaryExport(com.thalesgroup.orchestra.framework.connector.CommandContext)
     */
    public CommandStatus documentaryExport(CommandContext context_p) throws Exception {
      return null;
    }

    /**
     * @see com.thalesgroup.orchestra.framework.connector.IConnector#expand(com.thalesgroup.orchestra.framework.connector.CommandContext)
     */
    public CommandStatus expand(CommandContext context_p) throws Exception {
      return null;
    }

    /**
     * @see com.thalesgroup.orchestra.framework.connector.IConnector#lmExport(com.thalesgroup.orchestra.framework.connector.CommandContext)
     */
    @Override
    public CommandStatus lmExport(CommandContext context_p) throws Exception {
      return null;
    }

    /**
     * @see com.thalesgroup.orchestra.framework.connector.IConnector#navigate(com.thalesgroup.orchestra.framework.connector.CommandContext)
     */
    public CommandStatus navigate(CommandContext context_p) throws Exception {
      return null;
    }
  };
  /**
   * Internal remote client handler.<br>
   * Used to fake ECF hub into thinking that at least one client of each type has been registered.<br>
   * This allows for better search overall performance on ECF hub.
   */
  protected ClientConnectorHandler _clientHandler;
  /**
   * A resource set dedicated to connector configuration files.
   */
  protected ResourceSet _configurationResourceSet;
  /**
   * ECF connection.
   */
  protected Connection _ecfConnection;
  /**
   * Fake ECF remote connector that allows for faster search times in ECF hub.
   */
  protected TrivialRemoteConnector _fakeEcfConnector;
  /**
   * Fake remote context change listener that allows for faster search times in ECF hub.
   */
  private TrivialRemoteContextChangeListener _fakeRemoteContextChangeListener;
  /**
   * Connector id to configuration element map.
   */
  protected Map<String, IConfigurationElement> _idToContribution;
  /**
   * Type to connector implementation map.
   */
  protected Map<String, IConnector> _typeToConnector;
  /**
   * Type to connector descriptor map.
   */
  protected Map<String, ConnectorDescriptor> _typeToDescriptor;

  /**
   * Constructor.
   */
  public ConnectorRegistry() {
    // Initialize maps.
    _idToContribution = new HashMap<String, IConfigurationElement>(0);
    _typeToConnector = new HashMap<String, IConnector>(0);
    _typeToDescriptor = new HashMap<String, ConnectorDescriptor>(0);
    // Initialize resource set.
    _configurationResourceSet = new ResourceSetImpl();
  }

  /**
   * Activate {@link IRemoteContextChangeListenersHandler}.
   * @return A not <code>null</code> status whether external listeners are allowed to register or not.
   */
  protected IStatus activateRemoteListenersHandler() {
    String pluginId = FrameworkActivator.getDefault().getPluginId();
    MultiStatus result = new MultiStatus(pluginId, 0, Messages.ConnectorRegistry_RemoteContextChangeListenersStructure_Initialization_Wrapup, null);
    try {
      RemoteChangeContextListenersHandler handler = new RemoteChangeContextListenersHandler();
      ModelHandlerActivator.getDefault().setRemoteContextChangeListenerHandler(handler);
      result.add(new Status(IStatus.OK, pluginId, Messages.ConnectorRegistry_RemoteContextChangeListenersStructure_Initialization_Ok));
    } catch (Exception exception_p) {
      result.add(new Status(IStatus.ERROR, pluginId, Messages.ConnectorRegistry_RemoteContextChangeListenersStructure_Initialization_Error, exception_p));
    }
    return result;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnectorRegistry#checkConnectorsAssociationAvailability()
   */
  public IStatus checkConnectorsAssociationAvailability() {
    MultiStatus result =
        new MultiStatus(FrameworkActivator.getDefault().getPluginId(), 0, Messages.ConnectorRegistry_StatusMessage_ConnectorsAndAssociationsCheck_WrapUp, null);
    // If erroneous associations have been found, display them.
    Collection<Association> erroneousAssocations = TranscriptionHelper.getInstance().getErrorneousAssocations();
    if (null != erroneousAssocations) {
      for (Association association : erroneousAssocations) {
        String type = association.getSourceArtifact().getRootType();
        result.add(new Status(IStatus.ERROR, FrameworkActivator.getDefault().getPluginId(), MessageFormat.format(
            Messages.ConnectorRegistry_ErroneousAssociations_Error, type, association.getSourceArtifact().getContent(), association.getTargetArtifact()
                .getContent())));
      }
    }
    Map<String, Collection<Association>> conflictingAssociationsMap = TranscriptionHelper.getInstance().getConflictingAssociations();
    for (ConnectorDescriptor descriptor : _typeToDescriptor.values()) {
      String type = descriptor.getType();
      Collection<Association> associations = TranscriptionHelper.getInstance().getLogicalPhysicalAssociationsCollection(type);
      Collection<Association> conflictingAssociations = conflictingAssociationsMap.get(type);
      if (associations.isEmpty()) {
        if (descriptor.isLogicalOnly()) {
          result.add(new Status(IStatus.OK, FrameworkActivator.getDefault().getPluginId(), MessageFormat.format(
              Messages.ConnectorRegistry_StatusMessage_ConnectorLogicalOnly, type)));
        } else {
          result.add(new Status(IStatus.WARNING, FrameworkActivator.getDefault().getPluginId(), MessageFormat.format(
              Messages.ConnectorRegistry_StatusMessage_ConnectorsAndAssociationsCheck_NoAssociation_Warning, type)));
        }
      } else {
        // Display existing associations
        MultiStatus rootStatus =
            new MultiStatus(FrameworkActivator.getDefault().getPluginId(), 0,
                MessageFormat.format(Messages.ConnectorRegistry_AssociationDetection_WrapUp, type), null);
        for (Association association : associations) {
          String associationMessage = association.getSourceArtifact().getContent() + " -> " + association.getTargetArtifact().getContent(); //$NON-NLS-1$
          MultiStatus status =
              new MultiStatus(FrameworkActivator.getDefault().getPluginId(), 0, MessageFormat.format(
                  Messages.ConnectorRegistry_StatusMessage_ConnectorsAndAssociationsCheck_AssociationFound, associationMessage), null);
          // Search for conflicts.
          if (null != conflictingAssociations) {
            for (Association conflictingAssociation : conflictingAssociations) {
              if (conflictingAssociation.isEqualsByValues(association)) {
                status.add(new Status(IStatus.ERROR, FrameworkActivator.getDefault().getPluginId(),
                    Messages.ConnectorRegistry_StatusMessage_ConnectorsAndAssociationsCheck_ConflictingAssociationFound));
                break;
              }
            }
          }
          rootStatus.add(status);
        }
        result.add(rootStatus);
      }
    }
    return result;
  }

  /**
   * Initialize descriptor.<br>
   * That is, load its configuration file, and validate contents.
   * @return
   */
  protected IStatus createDescriptors(String configConnectorPath_p, String iconsDirectoryPath_p, String categoryName_p) {
    MultiStatus result =
        new MultiStatus(FrameworkActivator.getDefault().getPluginId(), 0, MessageFormat.format(
            Messages.ConnectorRegistry_StatusMessage_DescriptorsCreation_WrapUp, configConnectorPath_p, categoryName_p), null);
    // Try and load configuration file.
    try {
      URI configurationFileUri = URI.createFileURI(configConnectorPath_p);
      Resource confResource = _configurationResourceSet.getResource(configurationFileUri, true);
      DocumentRoot root = (DocumentRoot) confResource.getContents().get(0);
      ConnectorDefinition connectorDefinition = root.getConnectorDefinition();
      if (null == connectorDefinition) {
        throw new RuntimeException(MessageFormat.format(Messages.ConnectorRegistry_StatusMessage_ConnectorModel_Error, categoryName_p));
      }
      Connector connector = connectorDefinition.getConnector();
      if (null == connector) {
        throw new RuntimeException(MessageFormat.format(Messages.ConnectorRegistry_StatusMessage_ConnectorModel_Error, categoryName_p));
      }
      // Cycle through types.
      for (Type type : connector.getType()) {
        // Create descriptor.
        ConnectorDescriptor descriptor = new ConnectorDescriptor();
        descriptor._configurationFilePath = configConnectorPath_p;
        descriptor._connector = connector;
        descriptor._iconsDirectoryPath = iconsDirectoryPath_p;
        descriptor._odmCategory = categoryName_p;
        descriptor._type = type.getName();
        descriptor._timeout = type.getTimeout();
        ConnectorDescriptor existingDescriptor = _typeToDescriptor.get(descriptor._type);
        if (null != existingDescriptor) {
          result.add(new Status(IStatus.WARNING, FrameworkActivator.getDefault().getPluginId(), MessageFormat.format(
              Messages.ConnectorRegistry_StatusMessage_DescriptorsCreation_DescriptorAlreadyExists_Warning, existingDescriptor._odmCategory, descriptor._type,
              descriptor._odmCategory)));
          continue;
        }
        IStatus initializationStatus = descriptor.initialize();
        result.add(initializationStatus);
        if (initializationStatus.isOK()) {
          _typeToDescriptor.put(descriptor._type, descriptor);
          if (!descriptor.isLogicalOnly() && (null == iconsDirectoryPath_p)) {
            result.add(new Status(IStatus.WARNING, FrameworkActivator.getDefault().getPluginId(), MessageFormat.format(
                Messages.ConnectorRegistry_StatusMessage_NoIconsPath, categoryName_p, descriptor._type)));
          }
          result.add(new Status(IStatus.OK, FrameworkActivator.getDefault().getPluginId(), MessageFormat.format(
              Messages.ConnectorRegistry_StatusMessage_Connector_Loaded, descriptor._type)));
        }
      }
    } catch (Exception exception_p) {
      result.add(new Status(IStatus.ERROR, FrameworkActivator.getDefault().getPluginId(), MessageFormat.format(
          Messages.ConnectorRegistry_StatusMessage_UnableToLoadConfigurationFile_Error, categoryName_p), exception_p));
    }
    return result;
  }

  /**
   * Dispose registry.
   */
  public void dispose() {
    if (null != _clientHandler) {
      _clientHandler.unregister(_fakeEcfConnector);
      _fakeEcfConnector = null;
      _clientHandler.unregister(_fakeRemoteContextChangeListener);
      _fakeRemoteContextChangeListener = null;
      _clientHandler = null;
    }
    if (null != _configurationResourceSet) {
      for (Resource resource : _configurationResourceSet.getResources()) {
        resource.unload();
      }
      _configurationResourceSet.getResources().clear();
      _configurationResourceSet = null;
    }
    if (null != _ecfConnection) {
      // TODO Guillaume
      // Release hub.
      _ecfConnection = null;
    }
    if (null != _idToContribution) {
      _idToContribution.clear();
      _idToContribution = null;
    }
    if (null != _typeToConnector) {
      _typeToConnector.clear();
      _typeToConnector = null;
    }
    if (null != _typeToDescriptor) {
      _typeToDescriptor.clear();
      _typeToDescriptor = null;
    }
  }

  /**
   * Get connector implementation for specified root type.<br>
   * See connector configuration file for association between a connector implementation and an artifact root type.
   * @param rootType_p A not <code>null</code> root type.
   * @return The associated connector implementation, or <code>null</code> if none could be found.
   */
  public IConnector getConnector(String rootType_p) {
    ConnectorDescriptor connectorDescriptor = _typeToDescriptor.get(rootType_p);
    if (null != connectorDescriptor) {
      return connectorDescriptor.getConnector();
    }
    return null;
  }

  /**
   * Get all connector definitions for specified category sub-hierarchy.
   * @param category_p
   * @return An empty collection if none.
   */
  protected Collection<ConnectorProxy> getConnectorDefinitions(Category category_p) {
    // Precondition.
    if (null == category_p) {
      return Collections.emptyList();
    }
    // Resulting collection.
    Collection<ConnectorProxy> result = new ArrayList<ConnectorProxy>(0);
    // Category path.
    String categoryPath = ModelUtil.getElementPath(category_p);
    // Try and get configuration file path variable.
    VariableValue value = DataUtil.getValue(categoryPath + ICommonConstants.PATH_SEPARATOR + "configConnector"); //$NON-NLS-1$
    if ((null != value) && (null != value.getValue())) {
      ConnectorProxy proxy = new ConnectorProxy();
      proxy._category = category_p;
      proxy._configConnectorPath = value.getValue();
      // Try and get icon path variable.
      value = DataUtil.getValue(categoryPath + ICommonConstants.PATH_SEPARATOR + "iconPath"); //$NON-NLS-1$
      if ((null != value) && (null != value.getValue())) {
        proxy._iconPath = value.getValue();
      }
      // Add proxy to result.
      result.add(proxy);
    }
    // Search through children categories.
    for (Category childCategory : category_p.getCategories()) {
      result.addAll(getConnectorDefinitions(childCategory));
    }
    return result;
  }

  /**
   * Return the {@link ConnectorDescriptor} for specified type.
   * @param type_p
   * @return
   */
  public ConnectorDescriptor getConnectorDescriptor(String type_p) {
    return _typeToDescriptor.get(type_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnectorRegistry#getDescriptors()
   */
  public Collection<IConnectorDescriptor> getDescriptors() {
    return new ArrayList<IConnectorDescriptor>(_typeToDescriptor.values());
  }

  /**
   * Initialize registry.
   * @return
   */
  public IStatus initialize() {
    MultiStatus result = new MultiStatus(FrameworkActivator.getDefault().getPluginId(), 0, Messages.ConnectorRegistry_StatusMessage_Root, null);
    // Start ECF server.
    IStatus status = startEcfServer();
    result.add(status);
    // Activate remote client listeners handler.
    status = activateRemoteListenersHandler();
    result.add(status);
    // Add fake clients to boost ECF hub search times.
    status = registerFakeEcfClients();
    if (null != status) {
      result.add(status);
    }
    // Read remote applications launchers.
    status = EcfServicesActivator.getInstance().collectLaunchers();
    result.add(status);
    // Get connectors from ODM and file definitions.
    // Do not instantiate connectors yet.
    status = populateDescriptors();
    result.add(status);
    return result;
  }

  /**
   * Populate internal descriptors structure.<br>
   * That is find and read all installed connectors configuration data.
   * @return
   */
  protected IStatus populateDescriptors() {
    MultiStatus result =
        new MultiStatus(FrameworkActivator.getDefault().getPluginId(), 0, Messages.ConnectorRegistry_StatusMessage_DescriptorsInitialization, null);
    // Connector proxies.
    Collection<ConnectorProxy> proxies = new ArrayList<ConnectorProxy>(0);
    // Get root category.
    Collection<Category> categories = DataUtil.getCategories(DataUtil.getCategory("\\Orchestra installation\\Products")); //$NON-NLS-1$
    // Cycle through sub-categories.
    for (Category category : categories) {
      // Add all proxies.
      proxies.addAll(getConnectorDefinitions(category));
    }
    // Cycle through proxies.
    for (ConnectorProxy proxy : proxies) {
      // Create descriptors.
      IStatus descriptorsInitializationStatus = createDescriptors(proxy._configConnectorPath, proxy._iconPath, proxy._category.getName());
      result.add(descriptorsInitializationStatus);
    }
    // Add error status if no descriptor has been created.
    if (_typeToDescriptor.isEmpty()) {
      result.add(new Status(IStatus.ERROR, FrameworkActivator.getDefault().getPluginId(), Messages.ConnectorRegistry_StatusMessage_NoConnectorFound));
    }
    return result;
  }

  /**
   * Register a fake ECF client so as to boost ECF hub search when no connector is registered.
   * @return <code>null</code> if registration is successful, a status otherwise.
   */
  protected IStatus registerFakeEcfClients() {
    if (null == _clientHandler) {
      _clientHandler = new ClientConnectorHandler();
      _fakeEcfConnector = new TrivialRemoteConnector();
      IStatus fakeConnectorRegistration = _clientHandler.register(_fakeEcfConnector);
      _fakeRemoteContextChangeListener = new TrivialRemoteContextChangeListener();
      IStatus fakeRemoteContextChangeListenerRegistration = _clientHandler.register(_fakeRemoteContextChangeListener);
      if (!fakeConnectorRegistration.isOK() || !fakeRemoteContextChangeListenerRegistration.isOK()) {
        MultiStatus result =
            new MultiStatus(FrameworkActivator.getDefault().getPluginId(), 0, Messages.ConnectorRegistry_FakeClients_Initialization_Wrapup, null);
        if (!fakeConnectorRegistration.isOK()) {
          result.add(new Status(IStatus.WARNING, FrameworkActivator.getDefault().getPluginId(), Messages.ConnectorRegistry_FakeClient_UnableToRegister));
        }
        if (!fakeRemoteContextChangeListenerRegistration.isOK()) {
          result.add(new Status(IStatus.WARNING, FrameworkActivator.getDefault().getPluginId(),
              Messages.ConnectorRegistry_FakeClients_Initialization_Failed_For_RemoteContextChangeListener));
        }
      }
    }
    // Nothing to report.
    return null;
  }

  /**
   * Start ECF server.
   * @return Either successful or error status, giving an indication as to why (or where).
   */
  @SuppressWarnings("boxing")
  protected IStatus startEcfServer() {
    ID serverID = null;
    String containerServerName = "ecf.generic.server"; //$NON-NLS-1$
    try {
      // ECF Mode
      _ecfConnection = new Connection();
      String ecfServerLocation = ServerConfParam.getInstance().getEcfServerLocation();
      serverID = IDFactory.getDefault().createStringID(ecfServerLocation);
      Object[] serverArguments = new Object[] { serverID, ConfigServer.getInstance().getECFTimeOut() };
      _ecfConnection._serverContainer = ContainerFactory.getDefault().createContainer(containerServerName, serverArguments);
      // TODO Guillaume
      // Debug version only.
      // To be removed.
      _ecfConnection._serverContainer.addListener(new IContainerListener() {
        /**
         * @see org.eclipse.ecf.core.IContainerListener#handleEvent(org.eclipse.ecf.core.events.IContainerEvent)
         */
        @SuppressWarnings("nls")
        public void handleEvent(IContainerEvent event_p) {
          // Result to display in the console.
          StringBuilder result = new StringBuilder();
          result.append("Dealing with container listener event on server container ").append(_ecfConnection._serverContainer.getID()).append('\n');
          result.append("-> ").append(event_p).append('\n');
          IStatus resultStatus = new Status(IStatus.ERROR, FrameworkActivator.getDefault().getPluginId(), result.toString());
          // Log to the console.
          if (null != resultStatus) {
            CommonActivator.getInstance().getLog().log(resultStatus);
          }
        }
      });
      // End of TODO
      _ecfConnection._container = ContainerFactory.getDefault().createContainer("ecf.generic.client"); //$NON-NLS-1$
      _ecfConnection._container.connect(serverID, null);
      // TODO Guillaume
      // Debug version only.
      // To be removed.
      _ecfConnection._container.addListener(new IContainerListener() {
        /**
         * @see org.eclipse.ecf.core.IContainerListener#handleEvent(org.eclipse.ecf.core.events.IContainerEvent)
         */
        @SuppressWarnings("nls")
        public void handleEvent(IContainerEvent event_p) {
          // Result to display in the console.
          StringBuilder result = new StringBuilder();
          result.append("Dealing with container listener event on client container ").append(_ecfConnection._container.getID()).append('\n');
          result.append("-> ").append(event_p).append('\n');
          IStatus resultStatus = new Status(IStatus.WARNING, FrameworkActivator.getDefault().getPluginId(), result.toString());
          // Log to the console.
          if (null != resultStatus) {
            CommonActivator.getInstance().getLog().log(resultStatus);
          }
        }
      });
      // End of TODO
      // Get adapter.
      _ecfConnection._adapter = (IRemoteServiceContainerAdapter) _ecfConnection._container.getAdapter(IRemoteServiceContainerAdapter.class);
      // Add remote services (ECF) events listener.
      _ecfConnection._adapter.addRemoteServiceListener(new ServerRemoteServiceListener());
      return new Status(IStatus.INFO, FrameworkActivator.getDefault().getPluginId(), MessageFormat.format(Messages.ConnectorRegistry_StatusMessage_Ecf_Started,
          containerServerName, ecfServerLocation, ConfigServer.getInstance().getECFTimeOut()));
    } catch (Exception exception_p) {
      return new Status(IStatus.ERROR, FrameworkActivator.getDefault().getPluginId(), MessageFormat.format(
          Messages.ConnectorRegistry_StatusMessage_Ecf_Start_Error, serverID), exception_p);
    }
  }

  /**
   * Connection with a service provider.
   * @author t0076261
   */
  public class Connection {
    /**
     * Adapter.
     */
    public IRemoteServiceContainerAdapter _adapter;
    /**
     * Connection container.
     */
    public IContainer _container;
    /**
     * Server container.
     */
    public IContainer _serverContainer;
  }

  /**
   * Connector descriptor.
   * @author t0076261
   */
  public class ConnectorDescriptor implements IConnectorDescriptor {
    /**
     * Configuration file absolute path.
     */
    protected String _configurationFilePath;
    /**
     * Connector model.
     */
    protected Connector _connector;
    /**
     * Icons directory absolute path.
     */
    protected String _iconsDirectoryPath;
    /**
     * Is connector dealing with logical data exclusively ?
     */
    protected Boolean _isLogicalOnly;
    /**
     * Name of the ODM category that defines the connector.
     */
    protected String _odmCategory;
    /**
     * Connector associated type timeout.<br>
     * This is typically used in remote invocations to provide with an upper bound of waiting time before a remote call is over.
     */
    protected BigInteger _timeout;
    /**
     * Connector type.
     */
    protected String _type;

    /**
     * @see com.thalesgroup.orchestra.framework.connector.IConnectorRegistry.IConnectorDescriptor#executeSpecificCommand(com.thalesgroup.orchestra.framework.connector.CommandContext)
     */
    public CommandStatus executeSpecificCommand(CommandContext context_p) {
      IConnector connector = getConnector();
      if ((null == connector) || (null == context_p)) {
        return new CommandStatus(IStatus.ERROR, MessageFormat.format(Messages.ConnectorRegistry_StatusMessage_CouldNotInvokeSpecificCommand, getType()), null,
            0);
      }
      CommandStatus result = null;
      try {
        result = connector.executeSpecificCommand(context_p);
      } catch (Exception exception_p) {
        result = new CommandStatus(IStatus.ERROR, exception_p.getLocalizedMessage(), null, 0);
      }
      return result;
    }

    /**
     * Get configuration file absolute path.
     * @return A not <code>null</code> path.
     */
    public String getConfigurationFilePath() {
      return _configurationFilePath;
    }

    /**
     * Get connector implementation.
     * @return <code>null</code> if no connector can be found for current description. In most cases, that would mean that referred ID in the configuration file
     *         does not point to an existing deployed bundle that declares that same ID through the connector extension point. This is also possible that this
     *         bundle dependencies are not valid and thus that the bundle is not resolved by the platform.
     */
    public IConnector getConnector() {
      // Precondition.
      if (null == _connector) {
        return null;
      }
      String connectorId = _connector.getConnectorId();
      // Get result from map.
      IConnector result = _typeToConnector.get(_type);
      if (null != result) {
        if (NO_CONNECTOR == result) {
          return null;
        }
        return result;
      }
      // Search for implementation.
      IConfigurationElement configurationElement = _idToContribution.get(connectorId);
      if (NO_CONFIGURATION_ELEMENT == configurationElement) {
        configurationElement = null;
      }
      // Try and load new implementation.
      if (null != configurationElement) {
        try {
          // Create a new instance dedicated to connector type.
          result = (IConnector) ExtensionPointHelper.createInstance(configurationElement, ExtensionPointHelper.ATT_CLASS);
          _typeToConnector.put(_type, result);
          // Set its options.
          Map<String, Object> options = new HashMap<String, Object>(1);
          options.put(IConnectorOptionConstants.CONNECTOR_TYPE, _type);
          options.put(IConnectorOptionConstants.ECF_CONTAINER_ADAPTER, _ecfConnection._adapter);
          if (null != _connector.getProgId()) {
            options.put(IConnectorOptionConstants.COM_PROG_ID, _connector.getProgId());
          }
          if ((null != _timeout) && (_timeout.longValue() > 0)) {
            options.put(IConnectorOptionConstants.ECF_REMOTE_CALL_TIMEOUT, _timeout);
          }
          result.setOptions(options);
        } catch (Exception exception_p) {
          FrameworkActivator.getDefault().log(
              new Status(IStatus.ERROR, FrameworkActivator.getDefault().getPluginId(), MessageFormat.format(
                  Messages.ConnectorRegistry_StatusMessage_CanNotInstantiateConnectorImplementation_Error, _type)), LogLevel.ERROR);
        }
      }
      // Definitely, no way this connector could be loaded.
      if (null == result) {
        // Do not try and load this connector again.
        _typeToConnector.put(_type, NO_CONNECTOR);
      }
      return result;
    }

    /**
     * @see com.thalesgroup.orchestra.framework.connector.IConnectorRegistry.IConnectorDescriptor#getConnectorModel()
     */
    public Connector getConnectorModel() {
      return _connector;
    }

    /**
     * Get icons directory absolute path.
     * @return A not <code>null</code> path.
     */
    public String getIconsDirectoryPath() {
      return _iconsDirectoryPath;
    }

    /**
     * Get associated type.
     * @return The string representation of the associated type.
     */
    public String getType() {
      return _type;
    }

    /**
     * Initialize connector descriptor.
     * @return
     */
    protected IStatus initialize() {
      String pluginId = FrameworkActivator.getDefault().getPluginId();
      // Precondition.
      if (null == _connector) {
        return new Status(IStatus.ERROR, pluginId, MessageFormat.format(Messages.ConnectorRegistry_StatusMessage_NoConnectorDeclarationForType, getType()));
      }
      String connectorId = _connector.getConnectorId();
      // Get result from map.
      IConfigurationElement configurationElement = _idToContribution.get(connectorId);
      if (null == configurationElement) {
        // TODO Guillaume
        // There could be a performance improvement in caching the configuration elements here.
        // For now, stick to ExtensionPointHelper services strictly.
        // BTW, this could be ExtensionPointHelper that does the caching.
        configurationElement =
            ExtensionPointHelper.getConfigurationElement(ConnectorActivator.getInstance().getPluginId(), "connectorDeclaration", connectorId); //$NON-NLS-1$
        if (null != configurationElement) {
          _idToContribution.put(connectorId, configurationElement);
          _isLogicalOnly = ExtensionPointHelper.getBooleanValue(configurationElement, "logicalOnly"); //$NON-NLS-1$
        } else {
          _idToContribution.put(connectorId, NO_CONFIGURATION_ELEMENT);
          return new Status(IStatus.ERROR, pluginId, MessageFormat.format(Messages.ConnectorRegistry_StatusMessage_NoImplementationForConnectorId_Error,
              connectorId));
        }
      }

      // When a connector declare multiple types, configuration element is added to the _idToContribution map the first time.
      // the logical only have to be tested out of the first null configuationElement test then
      if (null != configurationElement) {
        _isLogicalOnly = ExtensionPointHelper.getBooleanValue(configurationElement, "logicalOnly"); //$NON-NLS-1$
      }

      return new Status(IStatus.OK, pluginId, MessageFormat.format(Messages.ConnectorRegistry_StatusMessage_ConnectorExtensionLoaded, getType()));
    }

    /**
     * Is specified command supported by this connector ?
     * @param command_p
     * @return <code>true</code> if so, <code>false</code> if it is either not supported or <code>null</code>.
     */
    public boolean isCommandSupported(String command_p) {
      // Precondition.
      if (null == command_p) {
        return false;
      }
      // Cycle through unsupported services.
      List<Service> unsupportedServices = _connector.getUnsupportedService();
      for (Service service : unsupportedServices) {
        if (command_p.equals(service.getServiceName().getName())) {
          return false;
        }
      }
      return true;
    }

    /**
     * Is connector only dealing with logical data ?
     * @return <code>true</code> if so, <code>false</code> otherwise.
     */
    public boolean isLogicalOnly() {
      return (null == _isLogicalOnly) ? false : _isLogicalOnly.booleanValue();
    }
  }

  /**
   * A connector proxy is a first lightweight object used to fetch existing connectors on the platform.<br>
   * No other model than ODM one is loaded at this time, so this is not a connector descriptor yet.<br>
   * It is indeed used to create connector descriptors once the fetch is over.
   * @author t0076261
   */
  protected class ConnectorProxy {
    protected String _configConnectorPath;
    protected String _iconPath;
    protected Category _category;
  }

  /**
   * {@link IRemoteContextChangeListenersHandler} internal implementation.
   * @author t0076261
   */
  protected class RemoteChangeContextListenersHandler implements IRemoteContextChangeListenersHandler {
    /**
     * Serialization helper.
     */
    private SerializationHelper _serializationHelper = new SerializationHelper();

    /**
     * Call remote listeners specified method through IRemoteContextChangeListenerProxy.
     * @param methodName_p The method to call.
     * @param asynchronous_p Should the method be invoked asynchronously ?
     * @param timeout_p The time-out in seconds.
     * @param arguments_p
     * @return A not <code>null</code> but possibly empty map.
     * @throws Exception
     */
    protected Map<String, IStatus> callRemoteListenersMethod(String methodName_p, boolean asynchronous_p, int timeout_p, Object... arguments_p)
        throws Exception {
      // Result.
      Map<String, IStatus> result = new HashMap<String, IStatus>(0);
      // Get container adapter.
      IRemoteServiceContainerAdapter adapter = _ecfConnection._adapter;
      // Try and connect to remote services.
      IRemoteServiceReference[] serviceReferences = adapter.getRemoteServiceReferences((ID[]) null, IRemoteContextChangeListenerProxy.class.getName(), null);
      if (null != serviceReferences) {
        // Cycle through registered remote proxies.
        for (IRemoteServiceReference remoteServiceReference : serviceReferences) {
          IRemoteService remoteService = adapter.getRemoteService(remoteServiceReference);
          IRemoteContextChangeListenerProxy proxy = (IRemoteContextChangeListenerProxy) remoteService.getProxy();
          // Get remote listener name.
          String name = proxy.getName();
          // Ignore trivial proxy.
          if (TrivialRemoteContextChangeListener.TRIVIAL_NAME.equals(name)) {
            adapter.ungetRemoteService(remoteServiceReference);
            continue;
          }
          String resultingStatus = callRemoteMethod(remoteService, methodName_p, asynchronous_p, timeout_p, arguments_p);
          // Close connection.
          adapter.ungetRemoteService(remoteServiceReference);
          // Add result.
          IStatus deserializedStatus = _serializationHelper.deserializeStatus(resultingStatus);
          if (null != deserializedStatus) {
            result.put(name, deserializedStatus);
          }
        }
      }
      return result;
    }

    /**
     * Call remote listeners specified method through IRemoteContextChangeListenerProxy2.
     * @param methodName_p The method to call.
     * @param asynchronous_p Should the method be invoked asynchronously ?
     * @param timeout_p The time-out in seconds.
     * @param arguments_p
     * @return A not <code>null</code> but possibly empty map.
     * @throws Exception
     */
    protected Map<String, IStatus> callRemoteListenersMethod2(String methodName_p, boolean asynchronous_p, int timeout_p, Object... arguments_p)
        throws Exception {
      // Result.
      Map<String, IStatus> result = new HashMap<String, IStatus>(0);
      // Get container adapter.
      IRemoteServiceContainerAdapter adapter = _ecfConnection._adapter;
      // Try and connect to remote services.
      IRemoteServiceReference[] serviceReferences = adapter.getRemoteServiceReferences((ID[]) null, IRemoteContextChangeListenerProxy2.class.getName(), null);
      if (null != serviceReferences) {
        // Cycle through registered remote proxies.
        for (IRemoteServiceReference remoteServiceReference : serviceReferences) {
          IRemoteService remoteService = adapter.getRemoteService(remoteServiceReference);
          IRemoteContextChangeListenerProxy2 proxy = (IRemoteContextChangeListenerProxy2) remoteService.getProxy();
          // Get remote listener name.
          String name = proxy.getName();
          // Ignore trivial proxy.
          if (TrivialRemoteContextChangeListener.TRIVIAL_NAME.equals(name)) {
            adapter.ungetRemoteService(remoteServiceReference);
            continue;
          }
          String resultingStatus = callRemoteMethod(remoteService, methodName_p, asynchronous_p, timeout_p, arguments_p);
          // Add result.
          IStatus deserializedStatus = _serializationHelper.deserializeStatus(resultingStatus);
          if (null != deserializedStatus) {
            result.put(name, deserializedStatus);
          }
          // Close connection.
          adapter.ungetRemoteService(remoteServiceReference);
        }
      }
      return result;
    }

    /**
     * Call remote method.
     * @param remoteService_p The hosting remote service.
     * @param methodName_p The method to call.
     * @param asynchronous_p Should the method be invoked asynchronously ?
     * @param timeout_p The time-out in seconds.
     * @param arguments_p Any arguments the remote method shall take as parameters.
     * @return The string serialized response.
     * @throws Exception
     */
    protected String callRemoteMethod(final IRemoteService remoteService_p, final String methodName_p, boolean asynchronous_p, final int timeout_p,
        final Object... arguments_p) throws Exception {
      // Create remote call.
      IRemoteCall remoteCall = new IRemoteCall() {
        /**
         * @see org.eclipse.ecf.remoteservice.IRemoteCall#getMethod()
         */
        public String getMethod() {
          return methodName_p;
        }

        /**
         * @see org.eclipse.ecf.remoteservice.IRemoteCall#getParameters()
         */
        public Object[] getParameters() {
          return arguments_p;
        }

        /**
         * @see org.eclipse.ecf.remoteservice.IRemoteCall#getTimeout()
         */
        public long getTimeout() {
          return timeout_p * 1000;
        }
      };
      // Asynchronous case.
      if (asynchronous_p) {
        remoteService_p.callAsync(remoteCall);
        return null;
      }
      // Synchronous case.
      return (String) remoteService_p.callSync(remoteCall);
    }

    /**
     * @see com.thalesgroup.orchestra.framework.model.handler.data.IRemoteContextChangeListenersHandler#postContextChangeCollectRemoteStatus(java.lang.String)
     */
    public Map<String, IStatus> postContextChangeCollectRemoteStatus(String newContextName_p, boolean allowUserInteractions_p) {
      Map<String, IStatus> result = new HashMap<String, IStatus>(0);
      // First collect web-services based registration.
      // Those are unable to provide with a feedback.
      // According to the expected contract, they will be tagged as clients at warning level.
      Set<String> registeredConsumers = ModelHandlerActivator.getDefault().getRegisteredConsumers();
      for (String consumer : registeredConsumers) {
        DeserializedStatus status =
            new DeserializedStatus(IStatus.WARNING, Messages.ConnectorRegistry_RemoteContextChangeListenerHandler_Warning_NoDetailsForClient);
        result.put(consumer, status);
      }
      // Then collect ECF remote listeners.
      try {
        Map<String, IStatus> remotePostChangeResult = callRemoteListenersMethod("postContextChange", false, 120, newContextName_p); //$NON-NLS-1$
        result.putAll(remotePostChangeResult);
        Map<String, IStatus> remotePostChangeResult2 =
            callRemoteListenersMethod2("postContextChange", false, 120, newContextName_p, new Boolean(allowUserInteractions_p)); //$NON-NLS-1$
        result.putAll(remotePostChangeResult2);
      } catch (Exception exception_p) {
        FrameworkActivator.getDefault().log(
            new Status(IStatus.ERROR, FrameworkActivator.getDefault().getPluginId(),
                Messages.ConnectorRegistry_RemoteContextChangeListenerHandler_Error_CouldNotReachRemoteListeners, exception_p), LogLevel.ERROR);
      }
      return result;
    }

    /**
     * @see com.thalesgroup.orchestra.framework.model.handler.data.IRemoteContextChangeListenersHandler#preContextChangeCollectRemoteStatus(java.lang.String)
     */
    public Map<String, IStatus> preContextChangeCollectRemoteStatus(String newContextName_p) {
      Map<String, IStatus> result = new HashMap<String, IStatus>(0);
      // First collect web-services based registration.
      // Those are unable to provide with a feedback.
      // According to the expected contract, they will be tagged as clients at warning level.
      Set<String> registeredConsumers = ModelHandlerActivator.getDefault().getRegisteredConsumers();
      for (String consumer : registeredConsumers) {
        DeserializedStatus status = new DeserializedStatus(IStatus.WARNING, Messages.ConnectorRegistry_RemoteContextChangeListenerHandler_NoDetailsForClient);
        result.put(consumer, status);
      }
      // Then collect ECF remote listeners.
      try {
        Map<String, IStatus> remotePreChangeResult = callRemoteListenersMethod("preContextChange", false, 120, newContextName_p); //$NON-NLS-1$
        result.putAll(remotePreChangeResult);
        Map<String, IStatus> remotePreChangeResult2 = callRemoteListenersMethod2("preContextChange", false, 120, newContextName_p); //$NON-NLS-1$
        result.putAll(remotePreChangeResult2);
      } catch (Exception exception_p) {
        FrameworkActivator.getDefault().log(
            new Status(IStatus.ERROR, FrameworkActivator.getDefault().getPluginId(),
                Messages.ConnectorRegistry_RemoteContextChangeListenerHandler_Error_CouldNotReachRemoteListeners, exception_p), LogLevel.ERROR);
      }
      return result;
    }
  }

  /**
   * Remote service event type, for remote service listener.
   * @author t0076261
   */
  protected enum RemoteServiceEventType {
    REGISTRATION, UNREGISTRATION
  }

  /**
   * Remote service listener on server side.<br>
   * Used to log addition/removal to the ECF server hub.
   * @author t0076261
   */
  protected class ServerRemoteServiceListener implements IRemoteServiceListener {

    /**
     * Set of registered services per proxy class
     */
    protected Map<Object, HashSet<String>> _proxyToServiceSet = new HashMap<Object, HashSet<String>>();

    /**
     * Get remote services that are currently registered after the incoming event
     * @param containerId_p
     * @param className_p
     * @return Couple (proxy interface class, set of service names)
     * @throws Exception
     */
    protected Couple<Object, HashSet<String>> getEventRemoteServices(ID containerId_p, String className_p) throws Exception {
      // Get container adapter.
      IRemoteServiceContainerAdapter adapter = _ecfConnection._adapter;
      // Try and connect to remote services.
      IRemoteServiceReference[] serviceReferences = adapter.getRemoteServiceReferences(containerId_p, className_p, null);
      // Precondition.
      if ((null == serviceReferences) || (0 == serviceReferences.length)) {
        return null;
      }

      Object proxyClass = null;
      HashSet<String> set = new HashSet<String>();
      for (IRemoteServiceReference r : serviceReferences) {
        IRemoteService remoteService = adapter.getRemoteService(r);
        Object proxy = remoteService.getProxy();
        String name = null;
        if (proxy instanceof IRemoteContextChangeListenerProxy) {
          name = ((IRemoteContextChangeListenerProxy) proxy).getName();
          proxyClass = IRemoteContextChangeListenerProxy.class;
          set.add(name);
        } else if (proxy instanceof IRemoteContextChangeListenerProxy2) {
          name = ((IRemoteContextChangeListenerProxy2) proxy).getName();
          proxyClass = IRemoteContextChangeListenerProxy2.class;
          set.add(name);
        } else if (proxy instanceof IRemoteConnectorProxy) {
          name = ((IRemoteConnectorProxy) proxy).getType();
          proxyClass = IRemoteConnectorProxy.class;
          set.add(name);
        }
        adapter.ungetRemoteService(r);
      }
      return new Couple<Object, HashSet<String>>(proxyClass, set);
    }

    /**
     * Get remote services that have registered
     * @param containerId_p
     * @param className_p
     * @return (proxy interface class, set of registered service names)
     * @throws Exception
     */
    protected Couple<Object, HashSet<String>> getRegisteredRemoteServices(ID containerId_p, String className_p) throws Exception {
      Couple<Object, HashSet<String>> proxyServices = getEventRemoteServices(containerId_p, className_p);
      Object proxyClass = proxyServices.getKey();
      HashSet<String> services = proxyServices.getValue();

      HashSet<String> currentServices = _proxyToServiceSet.get(proxyClass);

      _proxyToServiceSet.put(proxyClass, services);
      if (null == currentServices) {
        return new Couple<Object, HashSet<String>>(proxyClass, services);
      }
      HashSet<String> registered = new HashSet<String>();
      for (String service : services) {
        if (!currentServices.contains(service)) {
          registered.add(service);
        }
      }
      return new Couple<Object, HashSet<String>>(proxyClass, registered);
    }

    /**
     * Get remote services that have unregistered
     * @param containerId_p
     * @param className_p
     * @return (proxy interface class, set of unregistered service names)
     * @throws Exception
     */
    protected Couple<Object, HashSet<String>> getUnregisteredRemoteServices(ID containerId_p, String className_p) throws Exception {
      Couple<Object, HashSet<String>> proxyServices = getEventRemoteServices(containerId_p, className_p);
      Object proxyClass = proxyServices.getKey();
      HashSet<String> services = proxyServices.getValue();

      HashSet<String> currentServices = _proxyToServiceSet.get(proxyClass);
      _proxyToServiceSet.put(proxyClass, services);

      HashSet<String> unregistered = new HashSet<String>();
      for (String service : currentServices) {
        if (!services.contains(service)) {
          unregistered.add(service);
        }
      }
      return new Couple<Object, HashSet<String>>(proxyClass, unregistered);
    }

    /**
     * Get remote service applicative raw message, if any.<br>
     * Such a message is typically made of the remote service type and a supported type (for connectors) or a name (for listeners).
     * @param proxyClass_p
     * @param name_p
     * @return <code>null</code> if no such remote service could be found.
     * @throws Exception
     */
    protected String getServiceMessage(Object proxyClass_p, String name_p) {
      String result = null;
      if (IRemoteContextChangeListenerProxy.class == proxyClass_p) {
        result = MessageFormat.format(Messages.ConnectorRegistry_ConnectionReport_RemoteContextChangeListener, name_p);
      } else if (IRemoteContextChangeListenerProxy2.class == proxyClass_p) {
        result = MessageFormat.format(Messages.ConnectorRegistry_ConnectionReport_RemoteContextChangeListener, name_p);
      } else if (IRemoteConnectorProxy.class == proxyClass_p) {
        result = MessageFormat.format(Messages.ConnectorRegistry_ConnectionReport_RemoteConnector, name_p);
      }
      return result;
    }

    /**
     * @see org.eclipse.ecf.remoteservice.IRemoteServiceListener#handleServiceEvent(org.eclipse.ecf.remoteservice.events.IRemoteServiceEvent)
     */
    public void handleServiceEvent(IRemoteServiceEvent event_p) {
      // Get container ID.
      final ID containerId = event_p.getContainerID();
      // Get proxy class.
      String[] clazzes = event_p.getClazzes();
      final String proxyClassName = (0 < clazzes.length) ? clazzes[0] : null;
      // Get event type.
      final RemoteServiceEventType[] eventType = new RemoteServiceEventType[] { null };
      if (event_p instanceof IRemoteServiceRegisteredEvent) {
        eventType[0] = RemoteServiceEventType.REGISTRATION;
      } else if (event_p instanceof IRemoteServiceUnregisteredEvent) {
        eventType[0] = RemoteServiceEventType.UNREGISTRATION;
      }
      // Deal with event.
      if (null != eventType[0]) {
        new Thread(new Runnable() {
          /**
           * @see java.lang.Runnable#run()
           */
          public void run() {
            // Resulting status to be displayed to the console.
            IStatus resultStatus = null;
            switch (eventType[0]) {
              case REGISTRATION:
                try {
                  Couple<Object, HashSet<String>> registered = getRegisteredRemoteServices(containerId, proxyClassName);
                  Object proxyClass = registered.getKey();
                  for (String service : registered.getValue()) {
                    String message = MessageFormat.format(Messages.ConnectorRegistry_ConnectionReport_Registration, getServiceMessage(proxyClass, service));
                    resultStatus = new Status(IStatus.INFO, FrameworkActivator.getDefault().getPluginId(), message);
                    // Log to the console.
                    FrameworkActivator.getDefault().log(resultStatus, LogLevel.INFO);
                  }
                } catch (Exception exception_p) {
                  // Nothing to log.
                }
              break;
              case UNREGISTRATION:
                try {
                  Couple<Object, HashSet<String>> unregistered = getUnregisteredRemoteServices(containerId, proxyClassName);
                  Object proxyClass = unregistered.getKey();
                  for (String service : unregistered.getValue()) {
                    String message = MessageFormat.format(Messages.ConnectorRegistry_ConnectionReport_Unregistration, getServiceMessage(proxyClass, service));
                    resultStatus = new Status(IStatus.INFO, FrameworkActivator.getDefault().getPluginId(), message);
                    // Log to the console.
                    FrameworkActivator.getDefault().log(resultStatus, LogLevel.INFO);
                  }
                } catch (Exception exception_p) {
                  // Nothing to log.
                }
              break;
              default:
              break;
            }
          }
        }).start();
      }
    }
  }

  /**
   * Trivial remote connector that does nothing.
   * @author t0076261
   */
  protected class TrivialRemoteConnector extends AbstractConnector implements IRemoteConnector {
    /**
     * Trivial remote connector unique type.<br>
     * Used to ignore this implementation at runtime.<br>
     * The sole goal of this implementation is to boost ecf hub searching capabilities.
     */
    protected static final String TRIVIAL_TYPE = "TRC.Type"; //$NON-NLS-1$

    /**
     * @see com.thalesgroup.orchestra.framework.ecf.services.connector.IRemoteConnector#closeApplication()
     */
    @Override
    public CommandStatus closeApplication() throws Exception {
      CommandContext context = new CommandContext("closeApplication", null); //$NON-NLS-1$
      return super.createStatusForUnsupportedCommand(context);
    }

    /**
     * @see com.thalesgroup.orchestra.framework.connector.IConnector#create(com.thalesgroup.orchestra.framework.connector.CommandContext)
     */
    @Override
    public CommandStatus create(CommandContext context_p) throws Exception {
      return super.createStatusForUnsupportedCommand(context_p);
    }

    /**
     * @see com.thalesgroup.orchestra.framework.connector.IConnector#documentaryExport(com.thalesgroup.orchestra.framework.connector.CommandContext)
     */
    @Override
    public CommandStatus documentaryExport(CommandContext context_p) throws Exception {
      return super.createStatusForUnsupportedCommand(context_p);
    }

    /**
     * @see com.thalesgroup.orchestra.framework.connector.IConnector#expand(com.thalesgroup.orchestra.framework.connector.CommandContext)
     */
    @Override
    public CommandStatus expand(CommandContext context_p) throws Exception {
      return super.createStatusForUnsupportedCommand(context_p);
    }

    /**
     * @see com.thalesgroup.orchestra.framework.ecf.services.connector.IRemoteConnector#getType()
     */
    @Override
    public String getType() throws Exception {
      return TRIVIAL_TYPE;
    }

    /**
     * @see com.thalesgroup.orchestra.framework.ecf.services.connector.IRemoteConnector#importArtifacts(com.thalesgroup.orchestra.framework.connector.CommandContext)
     */
    @Override
    public CommandStatus importArtifacts(CommandContext context_p) throws Exception {
      return super.createStatusForUnsupportedCommand(context_p);
    }

    /**
     * @see com.thalesgroup.orchestra.framework.ecf.services.connector.IRemoteConnector#isHandlingArtifacts(com.thalesgroup.orchestra.framework.connector.CommandContext)
     */
    @Override
    public CommandStatus isHandlingArtifacts(CommandContext context_p) throws Exception {
      return super.createStatusForUnsupportedCommand(context_p);
    }

    /**
     * @see com.thalesgroup.orchestra.framework.connector.IConnector#navigate(com.thalesgroup.orchestra.framework.connector.CommandContext)
     */
    @Override
    public CommandStatus navigate(CommandContext context_p) throws Exception {
      return super.createStatusForUnsupportedCommand(context_p);
    }
  }

  /**
   * Trivial remote context change listener, that does nothing.
   * @author T0076261
   */
  protected class TrivialRemoteContextChangeListener implements IRemoteContextChangeListener {
    /**
     * Trivial remote context change listener unique name.<br>
     * Used to ignore this implementation at runtime.<br>
     * The sole goal of this implementation is to boost ecf hub searching capabilities.
     */
    protected static final String TRIVIAL_NAME = "TRCCL.Name"; //$NON-NLS-1$

    /**
     * @see com.thalesgroup.orchestra.framework.remote.services.context.IRemoteContextChangeListener#getName()
     */
    public String getName() {
      return TRIVIAL_NAME;
    }

    /**
     * @see com.thalesgroup.orchestra.framework.remote.services.context.IRemoteContextChangeListener#postContextChange(java.lang.String)
     */
    public IStatus postContextChange(String contextName_p) {
      // Should never be invoked.
      return null;
    }

    /**
     * @see com.thalesgroup.orchestra.framework.remote.services.context.IRemoteContextChangeListener#preContextChange(java.lang.String)
     */
    public IStatus preContextChange(String contextName_p) {
      // Should never be invoked.
      return null;
    }
  }
}