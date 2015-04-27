/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.oe;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.BundleContext;

import com.thalesgroup.orchestra.framework.ae.creation.ArtefactCreationActivator;
import com.thalesgroup.orchestra.framework.ae.creation.IOrchestraExplorerServices;
import com.thalesgroup.orchestra.framework.ae.creation.ui.environment.EnvironmentInformation;
import com.thalesgroup.orchestra.framework.exchange.GefHandler;
import com.thalesgroup.orchestra.framework.exchange.StatusHandler;
import com.thalesgroup.orchestra.framework.exchange.status.StatusDefinition;
import com.thalesgroup.orchestra.framework.gef.Element;
import com.thalesgroup.orchestra.framework.gef.GEF;
import com.thalesgroup.orchestra.framework.gef.GenericExportFormat;
import com.thalesgroup.orchestra.framework.gef.Properties;
import com.thalesgroup.orchestra.framework.gef.Property;
import com.thalesgroup.orchestra.framework.lib.constants.PapeeteHTTPKeyRequest;
import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;
import com.thalesgroup.orchestra.framework.oe.artefacts.description.ArtefactsDescriptionLoader;
import com.thalesgroup.orchestra.framework.oe.artefacts.description.IArtefactTypeDescription;
import com.thalesgroup.orchestra.framework.oe.artefacts.internal.Artefact;
import com.thalesgroup.orchestra.framework.oe.artefacts.internal.RootArtefactsBag;
import com.thalesgroup.orchestra.framework.oe.connectors.ConnectorInformation;
import com.thalesgroup.orchestra.framework.oe.connectors.ConnectorsInformationManager;
import com.thalesgroup.orchestra.framework.oe.notifier.INotifier;
import com.thalesgroup.orchestra.framework.oe.ui.constants.IImageConstants;
import com.thalesgroup.orchestra.framework.oe.ui.views.OrchestraExplorerView;
import com.thalesgroup.orchestra.framework.oe.variableloader.EnvironmentVariablesLoader;
import com.thalesgroup.orchestra.framework.puci.PUCI;
import com.thalesgroup.orchestra.framework.root.ui.activator.AbstractUIActivator;

/**
 * @author S0024585
 */
public class OrchestraExplorerActivator extends AbstractUIActivator implements IOrchestraExplorerServices {
  /**
   * Shared instance.
   */
  private static OrchestraExplorerActivator __plugin;
  // This is the ID from eventual extension point
  private static final String INOTIFIER_ID = "com.thalesgroup.orchestra.framework.ae.notifiers"; //$NON-NLS-1$
  /**
   * Connector information manager unique instance.
   */
  private ConnectorsInformationManager _connectorsInformationManager;
  /**
   * Current Orchestra Explorer view.
   */
  private OrchestraExplorerView _currentView;

  private List<EnvironmentInformation> _environmentList;

  /**
   * Create an image descriptor for given key.<br>
   * Images must be located in 'plug-in folder'/icons
   * @param key_p the key must be the file name of the related image.
   * @return an {@link ImageDescriptor} or null if error occurred
   */
  protected ImageDescriptor createImageDescriptor(String toolName, String key_p) {
    ImageDescriptor imageDescriptor = getImageDescriptor(IImageConstants.DESC_ART_DEFAULT);
    ConnectorInformation connectorInformation = getConnectorsInformationManager().getConnectorInformation(toolName);
    if (connectorInformation != null) {
      String iconPath = connectorInformation.getIconpath();
      try {
        if (null != iconPath) {
          File file = new File(iconPath + File.separator + key_p);
          // If the file does not exist => default image
          if (null != iconPath && file.exists()) {
            imageDescriptor = ImageDescriptor.createFromURL(new URL("file:///" + file.toString())); //$NON-NLS-1$ 
          }
        }
      } catch (MalformedURLException exception_p) {
        StringBuilder loggerMessage = new StringBuilder("Activator.createImageDescriptor(..) _ "); //$NON-NLS-1$
        logMessage(loggerMessage.toString(), IStatus.ERROR, new MalformedURLException());
        imageDescriptor = getImageRegistry().getDescriptor(key_p);
      }
    }
    return imageDescriptor;
  }

  /**
   * @return the connectorsInformationManager
   */
  public ConnectorsInformationManager getConnectorsInformationManager() {
    if (null == _connectorsInformationManager) {
      _connectorsInformationManager = new ConnectorsInformationManager();
    }
    return _connectorsInformationManager;
  }

  /**
   * Get current Orchestra Explorer view, if any.
   * @return <code>null</code> if none.
   */
  public OrchestraExplorerView getCurrentView() {
    return _currentView;
  }

  /**
   * getIconRelativePath(String rootType_p, String artifactType_p)
   * @param rootType_p
   * @param artifactType_p
   * @return The corresponding image (as defined by the artifact description file) and the relative path.
   */
  public Image getIconRelativeImage(String rootType_p, String artifactType_p) {
    IArtefactTypeDescription descr = ArtefactsDescriptionLoader.getInstance().getArtefactTypeDescription(rootType_p + "." + artifactType_p); //$NON-NLS-1$
    if (null != descr)
      return getImage(rootType_p, descr.getIcon());
    return getImage(IImageConstants.DESC_ART_DEFAULT);
  }

  /**
   * getIconPath(String rootType_p, String artifactType_p)
   * @param rootType_p
   * @param artifactType_p
   * @return The corresponding full path of icon image.
   */
  public String getIconPath(String rootType_p, String artifactType_p) {
    String result = ""; //$NON-NLS-1$
    IArtefactTypeDescription descr = ArtefactsDescriptionLoader.getInstance().getArtefactTypeDescription(rootType_p + "." + artifactType_p); //$NON-NLS-1$
    if (null != descr) {
      String key = descr.getIcon();
      ConnectorInformation connectorInformation = getConnectorsInformationManager().getConnectorInformation(rootType_p);
      if (connectorInformation != null) {
        String iconPath = connectorInformation.getIconpath();
        if (null != iconPath) {
          File file = new File(iconPath + File.separator + key);
          if (file.exists()) {
            result = file.getAbsolutePath();
          }
        }
      }
    }
    return result;
  }

  /**
   * Get an image for given key.<br>
   * Images must be located in 'plug-in folder'/icons
   * @param key_p the key must be the file name of the related image.
   * @return an {@link Image} or null if not found
   */
  public Image getImage(String toolName, String key_p) {
    ImageRegistry imageRegistry = getImageRegistry();
    synchronized (this) {
      Image image = imageRegistry.get(toolName + key_p);
      if (null == image) {
        // Create an image descriptor for given id.
        ImageDescriptor imageDescriptor = createImageDescriptor(toolName, key_p);
        // Store the (id, imageDescriptor) rather than (id,image)
        // because with storing (id,image) the getDescriptor method will return null in later usage
        // this way, everything is correctly initialized.
        imageRegistry.put(toolName + key_p, imageDescriptor);
        // Everything is all right at this step, let's get the real image
        image = imageRegistry.get(toolName + key_p);
      }
      return image;
    }
  }

  /**
   * Get an image descriptor for given key.<br>
   * Images must be located in 'plug-in folder'/icons
   * @param key_p the key must be the file name of the related image.
   * @return an {@link ImageDescriptor} or null if not found
   */
  @Override
  public ImageDescriptor getImageDescriptor(String key_p) {
    ImageRegistry imageRegistry = getImageRegistry();
    synchronized (this) {
      ImageDescriptor imageDescriptor = imageRegistry.getDescriptor(key_p);

      if (null == imageDescriptor) {
        imageDescriptor = createImageDescriptor(key_p);
        imageRegistry.put(key_p, imageDescriptor);
      }
      return imageDescriptor;
    }
  }

  /**
   * Log a message to the eclipse log file.
   * @param message_p The message to log. Can not be <code>null</code>.
   * @param status_p The message severity, from {@link IStatus#OK} to {@link IStatus#ERROR}.
   * @param throwable_p An error description, if needed. Can be <code>null</code>.
   */
  public void logMessage(String message_p, int status_p, Throwable throwable_p) {
    // Precondition.
    if (null == message_p) {
      return;
    }
    // Create expected status.
    IStatus resultingMessage = new Status(status_p, getPluginId(), message_p, throwable_p);
    // Log it.
    getLog().log(resultingMessage);
  }

  /**
   * Reload root artefacts.
   * @param allowUserInteractions_p
   */
  public void reloadRootArtefacts(boolean allowUserInteractions_p) {
    RootArtefactsBag.getInstance().updateRootArtefacts(allowUserInteractions_p);
  }

  /**
   * Reload root artefacts, with user interaction allowed
   */
  public void reloadRootArtefacts() {
    // For API compatibility
    reloadRootArtefacts(true);
  }

  /**
   * Search the Extension to add a listener to reload artefacts when a context switch is done
   */
  private void runNotifierExtension() {
    IConfigurationElement[] config = Platform.getExtensionRegistry().getConfigurationElementsFor(INOTIFIER_ID);
    try {
      for (IConfigurationElement e : config) {

        final Object o = e.createExecutableExtension("class"); //$NON-NLS-1$
        if (o instanceof INotifier) {
          ISafeRunnable runnable = new ISafeRunnable() {
            @Override
            public void handleException(Throwable exception) {
              System.out.println("Exception in client"); //$NON-NLS-1$
            }

            @Override
            public void run() throws Exception {
              ((INotifier) o).start();
            }
          };
          SafeRunner.run(runnable);
        }
      }
    } catch (CoreException ex) {
      System.out.println(ex.getMessage());
    }
  }

  /**
   * TODO add comment
   * @param clearSubArtefact_p
   */
  public void setClearSubArtefact(boolean clearSubArtefact_p) {
    RootArtefactsBag.getInstance().setClearSubArtefact(clearSubArtefact_p);
  }

  /**
   * Set current Orchestra Explorer view instance.
   * @param currentView the currentView to set
   */
  public void setCurrentView(OrchestraExplorerView currentView) {
    _currentView = currentView;
  }

  public final boolean orchestraUriExists(OrchestraURI uri_p) {
    // Get all artifacts and parse them
    Collection<Artefact> rootArtefacts = RootArtefactsBag.getInstance().getAllRootArtefacts();
    for (Artefact artefact : rootArtefacts) {
      if (uri_p.equalsIgnoreCase(artefact.getUri())) {
        return true;
      }
    }
    return false;
  }

  /**
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext )
   */
  @Override
  public void start(BundleContext context) throws Exception {
    super.start(context);
    __plugin = this;
    ArtefactCreationActivator.getDefault().setOrchestraExplorerServices(this);
    try {
      EnvironmentVariablesLoader.getInstance().init();
      getConnectorsInformationManager().update();
      runNotifierExtension();
      updateEnvironments();
    } catch (Throwable throwable_p) {
      // Nothing special
    }
  }

  /**
   * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext )
   */
  @Override
  public void stop(BundleContext context) throws Exception {
    __plugin = null;
    super.stop(context);
  }

  public static OrchestraExplorerActivator getDefault() {
    return __plugin;
  }

  /**
   * Get all environments of the current context along with their attributes
   */
  public void updateEnvironments() {
    _environmentList = new ArrayList<EnvironmentInformation>();
    OrchestraURI orchestraURI = new OrchestraURI("FrameworkCommands", "FC");
    List<OrchestraURI> list = new ArrayList<OrchestraURI>();
    list.add(orchestraURI);
    try {
      Map<String, String> commandResult = PUCI.executeSpecificCommand("GetContextEnvironments", list);
      String modelAbsolutePath = commandResult.get(PapeeteHTTPKeyRequest.__RESPONSE_FILE_PATH);
      if (null == modelAbsolutePath) {
        // TODO
      }
      StatusHandler statusHandler = new StatusHandler();
      StatusDefinition statusDefinition = statusHandler.loadModel(modelAbsolutePath);
      {
        // Get corresponding status.
        com.thalesgroup.orchestra.framework.exchange.status.Status status = statusHandler.getStatusForUri(statusDefinition, orchestraURI.getUri());
        // Access GEF model
        String gefModelAbsolutePath = statusHandler.getExportModelAbsolutePath(status);
        // Nothing to load, stop here.
        if (null == gefModelAbsolutePath) {
          return;
        }
        GefHandler gefHandler = new GefHandler();
        GEF gef = gefHandler.loadModel(gefModelAbsolutePath);
        if (null != gef) {
          GenericExportFormat genericExportFormat = gef.getGENERICEXPORTFORMAT().get(0);
          // Get all environments
          for (Element element : genericExportFormat.getELEMENT()) {
            Properties properties = element.getPROPERTIES().get(0);
            String name = "", type = "", id = "";
            boolean credentialsSupported = false;
            for (Property property : properties.getPROPERTY()) {
              String key = property.getName();
              String value = GefHandler.getValue(property).get(0);
              if ("name".equals(key)) {
                name = value;
              } else if ("type".equals(key)) {
                type = value;
              } else if ("id".equals(key)) {
                id = value;
              } else if ("credentialsSupported".equals(key)) {
                credentialsSupported = Boolean.parseBoolean(value);
              }
            }

            EnvironmentInformation envInfo = new EnvironmentInformation(name, type, id, credentialsSupported);
            // Environment attributes
            Element envAttributesElement = element.getELEMENT().get(0);
            Properties attributeProperties = envAttributesElement.getPROPERTIES().get(0);
            for (Property property : attributeProperties.getPROPERTY()) {
              String key = property.getName();
              List<String> values = GefHandler.getValue(property);
              String value;
              if (values.isEmpty()) {
                value = "";
              } else {
                value = values.get(0);
              }
              envInfo.addAttribute(key, value);
            }
            _environmentList.add(envInfo);
          }
          gefHandler.unloadModel(gef);
        } else {
          StringBuilder loggerMessage = new StringBuilder("OrchestraExplorerActivator.getEnvironments(..) _ "); //$NON-NLS-1$
          loggerMessage.append("Failed to load GEF");
          OrchestraExplorerActivator.getDefault().logMessage(loggerMessage.toString(), IStatus.ERROR, null);
        }
      }
      statusHandler.unloadModel(statusDefinition);
    } catch (Exception exception_p) {
      StringBuilder loggerMessage = new StringBuilder("OrchestraExplorerActivator.getEnvironments(..) _ "); //$NON-NLS-1$

      OrchestraExplorerActivator.getDefault().logMessage(loggerMessage.toString(), IStatus.ERROR, exception_p);
    }
  }

  /**
   * Get all environment information
   * @return
   */
  public List<EnvironmentInformation> getEnvironments() {
    return _environmentList;
  }

  /**
   * Get environment information by id
   * @param id_p
   * @return
   */
  public EnvironmentInformation getEnvironmentById(String id_p) {
    for (EnvironmentInformation environment : _environmentList) {
      if (id_p.equals(environment.getId())) {
        return environment;
      }
    }
    return null;
  }

}