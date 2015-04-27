/**
 * Copyright (c) THALES, 2010. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.connector.ecf;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.text.MessageFormat;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ecf.core.identity.ID;
import org.eclipse.ecf.core.util.ECFException;
import org.eclipse.ecf.remoteservice.IRemoteCall;
import org.eclipse.ecf.remoteservice.IRemoteService;
import org.eclipse.ecf.remoteservice.IRemoteServiceContainerAdapter;
import org.eclipse.ecf.remoteservice.IRemoteServiceReference;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.framework.connector.AbstractConnector;
import com.thalesgroup.orchestra.framework.connector.Artifact;
import com.thalesgroup.orchestra.framework.connector.CommandContext;
import com.thalesgroup.orchestra.framework.connector.CommandStatus;
import com.thalesgroup.orchestra.framework.connector.ConnectorActivator;
import com.thalesgroup.orchestra.framework.connector.IConnectorOptionConstants;
import com.thalesgroup.orchestra.framework.ecf.services.EcfServicesActivator;
import com.thalesgroup.orchestra.framework.ecf.services.EcfServicesActivator.LauncherDescriptor;
import com.thalesgroup.orchestra.framework.ecf.services.connector.IRemoteConnectorProxy;
import com.thalesgroup.orchestra.framework.ecf.services.launcher.ILauncher.IApplicationContext;
import com.thalesgroup.orchestra.framework.variablemanager.server.model.VariableManager;

/**
 * ECF connector default implementation.
 * @author t0076261
 */
public class EcfConnector extends AbstractConnector {
  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#create(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  public CommandStatus create(CommandContext context_p) throws Exception {
    CommandStatus result = null;
    try {
      Connection remoteService = getRemoteService(context_p);
      String methodName = "create"; //$NON-NLS-1$
      if (null != remoteService) {
        result = remoteService.callRemoteMethod(methodName, context_p);
        result.addChild(remoteService.closeConnection());
      } else {
        result = new CommandStatus(IStatus.ERROR, MessageFormat.format(Messages.EcfConnector_StatusMessage_UnableToCallRemoteService, methodName), null, 0);
      }
    } catch (ECFException exception_p) {
      result = createFailureStatus(exception_p, context_p);
    }
    return result;
  }

  /**
   * Create failure status for specified ECF exception and command context.
   * @param ecfException_p
   * @param context_p
   * @return A not <code>null</code> failure status.
   */
  protected CommandStatus createFailureStatus(ECFException ecfException_p, CommandContext context_p) {
    // Preconditions.
    if ((null == ecfException_p) || (null == context_p)) {
      return new CommandStatus(IStatus.ERROR, Messages.EcfConnector_Error_ErrorOccurredButIsNotSpecified, null, 0);
    }
    // Create resulting status.
    CommandStatus result = new CommandStatus(ecfException_p.getStatus());
    // Add impacted artifacts structure.
    Artifact[] artifacts = context_p.getArtifacts();
    if (artifacts.length > 0) {
      CommandStatus subRoot = new CommandStatus(IStatus.WARNING, Messages.EcfConnector_Error_WrapUp_ImpactedArtefacts, null, 0);
      for (Artifact artifact : artifacts) {
        subRoot.addChild(new CommandStatus(IStatus.WARNING, artifact.getUri().getUri(), artifact.getUri(), 0));
      }
      result.addChild(subRoot);
    }
    return result;
  }

  /**
   * De-serialize status from specified string.
   * @param status_p
   * @return
   */
  protected CommandStatus deserializeStatus(String status_p) {
    return ConnectorActivator.getInstance().getSerializationHelper().deserializeStatus(status_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#documentaryExport(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  public CommandStatus documentaryExport(CommandContext context_p) throws Exception {
    CommandStatus result = null;
    try {
      Connection remoteService = getRemoteService(context_p);
      String methodName = "documentaryExport"; //$NON-NLS-1$
      if (null != remoteService) {
        result = remoteService.callRemoteMethod(methodName, context_p);
        result.addChild(remoteService.closeConnection());
      } else {
        result = new CommandStatus(IStatus.ERROR, MessageFormat.format(Messages.EcfConnector_StatusMessage_UnableToCallRemoteService, methodName), null, 0);
      }
    } catch (ECFException exception_p) {
      result = createFailureStatus(exception_p, context_p);
    }
    return result;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.AbstractConnector#executeSpecificCommand(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  @Override
  public CommandStatus executeSpecificCommand(CommandContext context_p) throws Exception {
    CommandStatus result = null;
    try {
      Connection remoteService = getRemoteService(context_p);
      String methodName = "executeSpecificCommand"; //$NON-NLS-1$
      if (null != remoteService) {
        result = remoteService.callRemoteMethod(methodName, context_p);
        result.addChild(remoteService.closeConnection());
      } else {
        result = new CommandStatus(IStatus.ERROR, MessageFormat.format(Messages.EcfConnector_StatusMessage_UnableToCallRemoteService, methodName), null, 0);
      }
    } catch (ECFException exception_p) {
      result = createFailureStatus(exception_p, context_p);
    }
    return result;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#expand(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  public CommandStatus expand(CommandContext context_p) throws Exception {
    CommandStatus result = null;
    try {
      Connection remoteService = getRemoteService(context_p);
      String methodName = "expand"; //$NON-NLS-1$
      if (null != remoteService) {
        result = remoteService.callRemoteMethod(methodName, context_p);
        result.addChild(remoteService.closeConnection());
      } else {
        result = new CommandStatus(IStatus.ERROR, MessageFormat.format(Messages.EcfConnector_StatusMessage_UnableToCallRemoteService, methodName), null, 0);
      }
    } catch (ECFException exception_p) {
      result = createFailureStatus(exception_p, context_p);
    }
    return result;
  }

  /**
   * Get launcher for specified type.<br>
   * @param type_p
   * @return <code>null</code> if no launcher could be found for specified tool name.
   */
  protected Launcher getLauncher(String type_p) {
    // Get launcher descriptor from type.
    LauncherDescriptor launcher = EcfServicesActivator.getInstance().getLauncher(type_p);
    // Precondition.
    if (null == launcher) {
      return null;
    }
    Launcher result = new Launcher();
    result._launcherDescriptor = launcher;
    return result;
  }

  /**
   * Get remote service.
   * @return The associated connection or <code>null</code>.
   * @throws Exception
   */
  protected final Connection getRemoteService(CommandContext context_p) throws Exception {
    // Get target values.
    String type = (String) getOptions().get(IConnectorOptionConstants.CONNECTOR_TYPE);
    // Get a connection to the server.
    Connection result = getRemoteService(type);
    // No service available, call launcher.
    if (null == result) {
      Launcher launcher = getLauncher(type);
      // Precondition.
      if (null == launcher) {
        throw new ECFException(new Status(IStatus.ERROR, ConnectorActivator.getInstance().getPluginId(), MessageFormat.format(
            Messages.EcfConnector_Error_NoLauncherAvailableForType, type)));
      }
      launcher.setLaunchArguments(context_p.getLaunchArguments());
      final IStatus launchResult = launcher.launchApplication();
      if (launchResult.isOK()) {
        // Store result.
        result = getRemoteService(type);
        if (null != result) {
          if (context_p.isKeepOpen()) {
            result._closeApplication = false;
          } else {
            result._closeApplication = true;
          }
        }
      } else {
        throw new ECFException(launchResult);
      }
    }
    // No way the application could be found !
    if (null == result) {
      throw new ECFException(new Status(IStatus.ERROR, ConnectorActivator.getInstance().getPluginId(), MessageFormat.format(
          Messages.EcfConnector_StatusMessage_ApplicationLaunchedButConnectionIsLost, type)));
    }
    CommandStatus handledStatus = result.callRemoteMethod("isHandlingArtifacts", context_p); //$NON-NLS-1$
    // Artifacts not all currently handled.
    if (!handledStatus.isOK()) {
      // Import required ones.
      // Do it through a remote call so as to bypass the 30s limitation.
      CommandStatus importedStatus = result.callRemoteMethod("importArtifacts", context_p); //$NON-NLS-1$
      if (!importedStatus.isOK()) {
        throw new ECFException(importedStatus);
      }
    }
    return result;
  }

  /**
   * Get remote service implementation for specified tool name.
   * @param type_p The tool name to test against.
   * @return <code>null</code> if no remote connection does handle the specified tool.
   * @throws Exception
   */
  protected Connection getRemoteService(String type_p) throws Exception {
    Connection result = null;
    IRemoteServiceContainerAdapter adapter = getServerRemoteServiceContainerAdapter();
    IRemoteServiceReference[] serviceReferences = adapter.getRemoteServiceReferences((ID[]) null, IRemoteConnectorProxy.class.getName(), null);
    // Get registered connector services.
    // Search for specified tool within registered services.
    if (null != serviceReferences) {
      for (IRemoteServiceReference remoteServiceReference : serviceReferences) {
        IRemoteService remoteService = adapter.getRemoteService(remoteServiceReference);
        result = new Connection();
        result._remoteService = remoteService;
        result._remoteServiceReference = remoteServiceReference;
        result._remoteConnector = (IRemoteConnectorProxy) remoteService.getProxy();
        Object remoteType = result._remoteConnector.getType();
        if (type_p.equals(remoteType)) {
          break;
        }
        result.closeConnection();
        result = null;
      }
    }
    return result;
  }

  /**
   * Get {@link IRemoteServiceContainerAdapter}
   * @return
   */
  protected IRemoteServiceContainerAdapter getServerRemoteServiceContainerAdapter() {
    return (IRemoteServiceContainerAdapter) getOptions().get(IConnectorOptionConstants.ECF_CONTAINER_ADAPTER);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#lmExport(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  @Override
  public CommandStatus lmExport(CommandContext context_p) throws Exception {
    CommandStatus result = null;
    try {
      Connection remoteService = getRemoteService(context_p);
      String methodName = "lmExport"; //$NON-NLS-1$
      if (null != remoteService) {
        result = remoteService.callRemoteMethod(methodName, context_p);
        result.addChild(remoteService.closeConnection());
      } else {
        result = new CommandStatus(IStatus.ERROR, MessageFormat.format(Messages.EcfConnector_StatusMessage_UnableToCallRemoteService, methodName), null, 0);
      }
    } catch (ECFException exception_p) {
      result = createFailureStatus(exception_p, context_p);
    }
    return result;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#navigate(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  public CommandStatus navigate(CommandContext context_p) throws Exception {
    CommandStatus result = null;
    try {
      Connection remoteService = getRemoteService(context_p);
      String methodName = "navigate"; //$NON-NLS-1$
      if (null != remoteService) {
        result = remoteService.callRemoteMethod(methodName, context_p);
        // Do not close application after a navigate.
        remoteService._closeApplication = false;
        result.addChild(remoteService.closeConnection());
      } else {
        result = new CommandStatus(IStatus.ERROR, MessageFormat.format(Messages.EcfConnector_StatusMessage_UnableToCallRemoteService, methodName), null, 0);
      }
    } catch (ECFException exception_p) {
      exception_p.printStackTrace();
      result = createFailureStatus(exception_p, context_p);
    }
    return result;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.AbstractConnector#search(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  @Override
  public CommandStatus search(CommandContext context_p) throws Exception {
    CommandStatus result = null;
    try {
      Connection remoteService = getRemoteService(context_p);
      String methodName = "search"; //$NON-NLS-1$
      if (null != remoteService) {
        result = remoteService.callRemoteMethod(methodName, context_p);
        result.addChild(remoteService.closeConnection());
      } else {
        result = new CommandStatus(IStatus.ERROR, MessageFormat.format(Messages.EcfConnector_StatusMessage_UnableToCallRemoteService, methodName), null, 0);
      }
    } catch (ECFException exception_p) {
      result = createFailureStatus(exception_p, context_p);
    }
    return result;
  }

  /**
   * Serialize specified context to string.
   * @param context_p
   * @return
   */
  protected String serializeContext(CommandContext context_p) {
    return ConnectorActivator.getInstance().getSerializationHelper().serializeContext(context_p);
  }

  /**
   * Connection with a service provider.
   * @author t0076261
   */
  protected class Connection {
    /**
     * Should application be closed before connection is disposed ?
     */
    protected boolean _closeApplication;
    /**
     * Remote connector service (ie typed remote service).
     */
    protected IRemoteConnectorProxy _remoteConnector;
    /**
     * Remote service.
     */
    protected IRemoteService _remoteService;
    /**
     * Remote service reference.
     */
    protected IRemoteServiceReference _remoteServiceReference;

    /**
     * Call specified remote method for specified context.
     * @param methodName_p
     * @param context_p
     * @return
     * @throws Exception
     */
    @SuppressWarnings("synthetic-access")
    protected CommandStatus callRemoteMethod(String methodName_p, CommandContext context_p) throws Exception {
      CommandStatus status = deserializeStatus((String) callRemoteMethod(methodName_p, serializeContext(context_p)));
      if (null == status) {
        status = EcfConnector.this.createStatusForUnsupportedCommand(context_p);
      }
      return status;
    }

    /**
     * Call remote method.
     * @param service_p
     * @param methodName_p
     * @param arguments_p
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    protected <T> T callRemoteMethod(final String methodName_p, final Object... arguments_p) throws Exception {
      return (T) _remoteService.callSync(new IRemoteCall() {
        public String getMethod() {
          return methodName_p;
        }

        public Object[] getParameters() {
          return arguments_p;
        }

        @SuppressWarnings("synthetic-access")
        public long getTimeout() {
          // Default is 3600s.
          long result = 3600000;
          BigInteger timeout = (BigInteger) getOptions().get(IConnectorOptionConstants.ECF_REMOTE_CALL_TIMEOUT);
          if (null != timeout) {
            result = timeout.longValue() * 1000;
          }
          return result;
        }
      });
    }

    /**
     * Close connection.
     * @return
     */
    protected CommandStatus closeConnection() throws Exception {
      CommandStatus result = null;
      if (_closeApplication) {
        result = deserializeStatus(_remoteConnector.closeApplication());
      }
      if (null != _remoteServiceReference) {
        getServerRemoteServiceContainerAdapter().ungetRemoteService(_remoteServiceReference);
      }
      _remoteServiceReference = null;
      _remoteService = null;
      _remoteConnector = null;
      return result;
    }
  }

  /**
   * Launcher structure as extracted from the contributors.
   * @author t0076261
   */
  protected class Launcher implements IApplicationContext {
    /**
     * Launcher descriptor.
     */
    protected LauncherDescriptor _launcherDescriptor;

    protected String _launchArguments;

    /**
     * @see com.thalesgroup.orchestra.framework.ecf.services.launcher.ILauncher.IApplicationContext#getType()
     */
    public String getType() {
      return _launcherDescriptor._type;
    }

    /**
     * Launch application.
     * @return A {@link Couple} containing :<br>
     *         * An {@link IStatus} describing the result of the launching process.<br>
     *         * An {@link ID} standing for the detected connection from the application.
     */
    @SuppressWarnings("boxing")
    protected IStatus launchApplication() {
      // Time to wait for application to launch itself.
      // Default value is 300s.
      int waitTime = 300;
      // Get value from extension, if any.
      if (null != _launcherDescriptor._launchTime) {
        waitTime = _launcherDescriptor._launchTime;
      }
      // Launch the application.
      if (null != _launcherDescriptor._launcher) {
        IStatus result = _launcherDescriptor._launcher.launchApplication(this);
        // Make sure it makes any sense to wait for the application to start.
        if ((null != result) && !result.isOK()) {
          return result;
        }
      } else {
        String executableFullPath = null;
        MultiStatus result = new MultiStatus(Activator.getDefault().getPluginId(), 0, Messages.EcfConnector_LauncherError_InvalidApplicationPath, null);
        // Look for specified variable.
        switch (_launcherDescriptor._applicationPathType) {
          case CATEGORY:
            executableFullPath = VariableManager.getValue(_launcherDescriptor._applicationPath + ICommonConstants.PATH_SEPARATOR + "Executable"); //$NON-NLS-1$
            if (null == executableFullPath) {
              result.add(new Status(IStatus.ERROR, Activator.getDefault().getPluginId(), MessageFormat.format(
                  Messages.EcfConnector_LauncherError_InvalidApplicationPath_Category, _launcherDescriptor._applicationPath, getType(), "Executable"))); //$NON-NLS-1$
            }
          break;
          case VARIABLE:
            executableFullPath = VariableManager.getValue(_launcherDescriptor._applicationPath);
            if (null == executableFullPath) {
              result.add(new Status(IStatus.ERROR, Activator.getDefault().getPluginId(), MessageFormat.format(
                  Messages.EcfConnector_LauncherError_InvalidApplicationPath_Variable, _launcherDescriptor._applicationPath, getType())));
            }
          break;
          default:
          break;
        }
        // Precondition.
        if (!result.isOK()) {
          return result;
        }
        // Test executable availability.
        File file = new File(executableFullPath);
        if ((null == executableFullPath) || !(file.exists())) {
          return new Status(IStatus.ERROR, Activator.getDefault().getPluginId(), MessageFormat.format(
              Messages.EcfConnector_StatusMessage_ApplicationPathInvalid_Error, getType(), _launcherDescriptor._applicationPath));
        }
        // Launch executable.
        try {
          Runtime.getRuntime().exec(file.getAbsolutePath() + " " + getLaunchArguments(), null, file.getParentFile());
        } catch (IOException exception_p) {
          return new Status(IStatus.ERROR, Activator.getDefault().getPluginId(), MessageFormat.format(
              Messages.EcfConnector_StatusMessage_ApplicationFailedToLaunch_Error, getType()), exception_p);
        }
      }
      // Wait for application to connect itself.
      try {
        int wait = waitTime;
        Connection connection = null;
        int timeSlot = waitTime / 10;
        // Force time slot to 5s at the upmost.
        if (timeSlot > 5) {
          timeSlot = 5;
        }
        while ((null == connection) && (wait >= 0)) {
          Thread.sleep(timeSlot * 1000);
          wait -= timeSlot;
          connection = getRemoteService(getType());
        }
        if (null == connection) {
          return new Status(IStatus.ERROR, Activator.getDefault().getPluginId(), MessageFormat.format(
              Messages.EcfConnector_StatusMessage_ApplicationFailedToLaunch_TimeOut_Error, getType()));
        }
        // Release this connection.
        connection.closeConnection();
      } catch (Exception exception_p) {
        // Current thread was interrupted by another process.
        // There is no way to tell for sure that the application has been launched.
        return new Status(IStatus.ERROR, Activator.getDefault().getPluginId(), MessageFormat.format(
            Messages.EcfConnector_StatusMessage_ApplicationFailedToLaunch_Error, getType()), exception_p);
      }
      return new Status(IStatus.OK, Activator.getDefault().getPluginId(), MessageFormat.format(
          Messages.EcfConnector_StatusMessage_ApplicationSuccessfullyLaunched, getType()));
    }

    public void setLaunchArguments(String launchArguments_p) {
      _launchArguments = launchArguments_p;
    }

    /**
     * @see com.thalesgroup.orchestra.framework.ecf.services.launcher.ILauncher.IApplicationContext#getLaunchArguments()
     */
    public String getLaunchArguments() {
      return _launchArguments;
    }
  }
}