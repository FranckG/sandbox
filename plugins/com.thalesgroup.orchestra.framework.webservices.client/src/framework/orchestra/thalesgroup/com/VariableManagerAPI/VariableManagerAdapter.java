package framework.orchestra.thalesgroup.com.VariableManagerAPI;

import java.rmi.RemoteException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.thalesgroup.orchestra.framework.lib.base.conf.ServerConfParam;
import com.thalesgroup.orchestra.framework.model.IEnvironmentVariable;
import com.thalesgroup.orchestra.framework.model.IEnvironmentVariable.IEnvironment;
import com.thalesgroup.orchestra.framework.model.IEnvironmentVariable.IEnvironmentValue;
import com.thalesgroup.orchestra.framework.model.IRemoteVariableManager;
import com.thalesgroup.orchestra.framework.model.IVariable;

import framework.orchestra.thalesgroup.com.VariableManager.ConfDirDelta;
import framework.orchestra.thalesgroup.com.VariableManager.DeltaState;
import framework.orchestra.thalesgroup.com.VariableManager.EnvironmentVariableWS;
import framework.orchestra.thalesgroup.com.VariableManager.VariableManagerWSAdapter;
import framework.orchestra.thalesgroup.com.VariableManager.VariableManagerWSAdapterServiceLocator;
import framework.orchestra.thalesgroup.com.VariableManager.VariableWS;

import javax.xml.rpc.ServiceException;

public class VariableManagerAdapter implements IRemoteVariableManager {
  /**
   * Unique instance.
   */
  private static VariableManagerAdapter __instance = null;
  /**
   * Connection maximum retry rounds.
   */
  protected static final int CONNECTION_RETRY_MAX = 3;
  /**
   * Address pattern.
   */
  private static final String VARIABLE_MANAGER_ADDRESS = "http://localhost:{0}/services/VariableManager"; //$NON-NLS-1$

  /**
   * Segment variable
   */
  private static final String SEGMENT_VARIABLE = "\\Orchestra\\Segment";

  /**
   * WS proxy to services.
   */
  private VariableManagerWSAdapter _variableManagerWSAdapter;

  /**
   * Constructor.
   * @throws ServiceException
   */
  private VariableManagerAdapter() throws ServiceException {
    init();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IRemoteVariableManager#getAllContextNames()
   */
  public List<String> getAllContextNames() throws RemoteException {
    return getAllContextNames(CONNECTION_RETRY_MAX);
  }

  /**
   * @param nbRetry_p
   * @return
   * @throws RemoteException
   */
  protected List<String> getAllContextNames(int nbRetry_p) throws RemoteException {
    try {
      init();
      String[] objects = _variableManagerWSAdapter.getAllContextNames();
      return Arrays.asList(objects);
    } catch (Exception exception_p) {
      if (nbRetry_p != 0) {
        try {
          reconnect();
        } catch (ServiceException exception_p1) {
          // Sorry reconnection failed
          // retry will retry the reconnection too...
        }
        return getAllContextNames(nbRetry_p - 1);
      }
      throw new RemoteException("", exception_p); //$NON-NLS-1$
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IRemoteVariableManager#getAllVariables()
   */
  public List<IVariable> getAllVariables() throws RemoteException {
    return getAllVariables(CONNECTION_RETRY_MAX);
  }

  /**
   * @param nbRetry_p
   * @return
   * @throws RemoteException
   */
  protected List<IVariable> getAllVariables(int nbRetry_p) throws RemoteException {
    try {
      init();
      VariableWS[] objects = _variableManagerWSAdapter.getAllVariables();
      return variableWSToIVariable(objects);
    } catch (Exception exception_p) {
      if (nbRetry_p != 0) {
        try {
          reconnect();
        } catch (ServiceException exception_p1) {
          // Sorry reconnection failed
          // retry will retry the reconnection too...
        }
        return getAllVariables(nbRetry_p - 1);
      }
      throw new RemoteException("", exception_p); //$NON-NLS-1$
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IRemoteVariableManager#getAllVariablesLocalized(java.lang.String)
   */
  public List<IVariable> getAllVariablesLocalized(String locale) throws RemoteException {
    return getAllVariablesLocalized(locale, CONNECTION_RETRY_MAX);
  }

  /**
   * @param locale_p
   * @param nbRetry_p
   * @return
   * @throws RemoteException
   */
  protected List<IVariable> getAllVariablesLocalized(String locale_p, int nbRetry_p) throws RemoteException {
    try {
      init();
      VariableWS[] objects = _variableManagerWSAdapter.getAllVariablesLocalized(locale_p);
      return variableWSToIVariable(objects);
    } catch (Exception exception_p) {
      if (nbRetry_p != 0) {
        try {
          reconnect();
        } catch (ServiceException exception_p1) {
          // Sorry reconnection failed
          // retry will retry the reconnection too...
        }
        return getAllVariablesLocalized(locale_p, nbRetry_p - 1);
      }
      throw new RemoteException("", exception_p); //$NON-NLS-1$
    }
  }

  /**
   * Get all directories from ArtefactPath variable contributed by File System category environments.
   * @return
   * @throws RemoteException
   */
  public List<String> getArtefactDirectories() throws RemoteException {
    return getDirectoriesFromVariable((IEnvironmentVariable) getVariableArtefactPath());
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IRemoteVariableManager#getCategories(java.lang.String)
   */
  public List<String> getCategories(String path) throws RemoteException {
    return getCategories(path, CONNECTION_RETRY_MAX);
  }

  /**
   * @param path_p
   * @param nbRetry_p
   * @return
   * @throws RemoteException
   */
  protected List<String> getCategories(String path_p, int nbRetry_p) throws RemoteException {
    try {
      init();
      String[] objects = _variableManagerWSAdapter.getCategories(path_p);
      return Arrays.asList(objects);
    } catch (Exception exception_p) {
      if (nbRetry_p != 0) {
        try {
          reconnect();
        } catch (ServiceException exception_p1) {
          // Sorry reconnection failed
          // retry will retry the reconnection too...
        }
        return getCategories(path_p, nbRetry_p - 1);
      }
      throw new RemoteException("", exception_p); //$NON-NLS-1$
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IRemoteVariableManager#getCategorySeparator()
   */
  public String getCategorySeparator() throws RemoteException {
    return getCategorySeparator(CONNECTION_RETRY_MAX);
  }

  /**
   * @param nbRetry_p
   * @return
   * @throws RemoteException
   */
  protected String getCategorySeparator(int nbRetry_p) throws RemoteException {
    try {
      init();
      return _variableManagerWSAdapter.getCategorySeparator();
    } catch (Exception exception_p) {
      if (nbRetry_p != 0) {
        try {
          reconnect();
        } catch (ServiceException exception_p1) {
          // Sorry reconnection failed
          // retry will retry the reconnection too...
        }
        return getCategorySeparator(nbRetry_p - 1);
      }
      throw new RemoteException("", exception_p); //$NON-NLS-1$
    }
  }

  /**
   * Get all directories from ConfigurationDirectories variable contributed by File System category environments.
   * @return
   * @throws RemoteException
   */
  public List<String> getConfigurationDirectories() throws RemoteException {
    return getDirectoriesFromVariable((IEnvironmentVariable) getVariable("\\Orchestra\\ConfigurationDirectories")); //$NON-NLS-1$
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IRemoteVariableManager#getCurrentContextName()
   */
  public String getCurrentContextName() throws RemoteException {
    return getCurrentContextName(CONNECTION_RETRY_MAX);
  }

  /**
   * @param nbRetry_p
   * @return
   * @throws RemoteException
   */
  protected String getCurrentContextName(int nbRetry_p) throws RemoteException {
    try {
      init();
      return _variableManagerWSAdapter.getCurrentContextName();
    } catch (Exception exception_p) {
      if (nbRetry_p != 0) {
        try {
          reconnect();
        } catch (ServiceException exception_p1) {
          // Sorry reconnection failed
          // retry will retry the reconnection too...
        }
        return getCurrentContextName(nbRetry_p - 1);
      }
      throw new RemoteException("", exception_p); //$NON-NLS-1$
    }
  }

  /**
   * Extract directories from specified environment variable.
   * @param environmentVariable_p
   * @return A not <code>null</code> but possibly empty list of directories absolute paths.
   */
  protected List<String> getDirectoriesFromVariable(IEnvironmentVariable environmentVariable_p) {
    // Precondition.
    if (null == environmentVariable_p) {
      return Collections.emptyList();
    }
    // Get paths.
    List<String> paths = new ArrayList<String>(0);
    for (IEnvironment environment : environmentVariable_p.getEnvironments()) {
      // TODO Guillaume
      // This is a loose coupling dependency towards environment of File System type.
      // This is likely not to work anymore with a change in such environments.
      // Should be bound to integration tests.
      if (!"File System".equals(environment.getCategory())) { //$NON-NLS-1$
        continue;
      }
      // Search for directories values.
      IEnvironmentValue directoriesValue = null;
      for (IEnvironmentValue value : environment.getValues()) {
        if ("directories".equals(value.getKey())) { //$NON-NLS-1$
          directoriesValue = value;
          break;
        }
      }
      // Unlikely, but still...
      if (null == directoriesValue) {
        continue;
      }
      paths.addAll(directoriesValue.getValues());
    }
    return paths;
  }

  /**
   * Get first value of specified variable.
   * @param variable
   * @return
   */
  public String getFirstValue(IVariable variable) {
    String value = null;
    List<String> values = getValues(variable);
    if (null != values) {
      value = values.get(0);
    }
    return value;
  }

  /**
   * Get first value of targeted variable.
   * @param variablePath_p
   * @return
   * @throws ServiceException
   * @throws RemoteException
   */
  public String getFirstValue(String variablePath_p) throws ServiceException, RemoteException {
    final IVariable variable = getVariable(variablePath_p);
    return getFirstValue(variable);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IRemoteVariableManager#getSubstitutedValue(java.lang.String)
   */
  public String getSubstitutedValue(String variablePathName) throws RemoteException {
    return getSubstitutedValue(variablePathName, CONNECTION_RETRY_MAX);
  }

  /**
   * @param variablePathName_p
   * @param nbRetry_p
   * @return
   * @throws RemoteException
   */
  protected String getSubstitutedValue(String variablePathName_p, int nbRetry_p) throws RemoteException {
    try {
      init();
      return _variableManagerWSAdapter.getSubstitutedValue(variablePathName_p);
    } catch (Exception exception_p) {
      if (nbRetry_p != 0) {
        try {
          reconnect();
        } catch (ServiceException exception_p1) {
          // Sorry reconnection failed
          // retry will retry the reconnection too...
        }
        return getSubstitutedValue(variablePathName_p, nbRetry_p - 1);
      }
      throw new RemoteException("", exception_p); //$NON-NLS-1$
    }
  }

  /**
   * Get all values for specified variable.
   * @param variable
   * @return
   */
  private List<String> getValues(IVariable variable) {
    List<String> values = null;
    if (null != variable) {
      values = variable.getValues();
    }
    return values;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IRemoteVariableManager#getVariable(java.lang.String)
   */
  public IVariable getVariable(String variablePathName) throws RemoteException {
    return getVariable(variablePathName, CONNECTION_RETRY_MAX);
  }

  /**
   * @param variablePathName_p
   * @param nbRetry_p
   * @return
   * @throws RemoteException
   */
  protected IVariable getVariable(String variablePathName_p, int nbRetry_p) throws RemoteException {
    try {
      init();
      VariableWS variable = _variableManagerWSAdapter.getVariable(variablePathName_p);
      // Precondition.
      if (null == variable) {
        return null;
      }
      return variableWSToIVariable(new VariableWS[] { variable }).get(0);
    } catch (Exception exception_p) {
      if (nbRetry_p != 0) {
        try {
          reconnect();
        } catch (ServiceException exception_p1) {
          // Sorry reconnection failed
          // retry will retry the reconnection too...
        }
        return getVariable(variablePathName_p, nbRetry_p - 1);
      }
      throw new RemoteException("", exception_p); //$NON-NLS-1$
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IRemoteVariableManager#getVariableArtefactPath()
   */
  public IVariable getVariableArtefactPath() throws RemoteException {
    return getVariableArtefactPath(CONNECTION_RETRY_MAX);
  }

  /**
   * @param nbRetry_p
   * @return
   * @throws RemoteException
   */
  protected IVariable getVariableArtefactPath(int nbRetry_p) throws RemoteException {
    try {
      init();
      VariableWS variableArtefactPath = _variableManagerWSAdapter.getVariableArtefactPath();
      // Precondition.
      if (null == variableArtefactPath) {
        return null;
      }
      return variableWSToIVariable(new VariableWS[] { variableArtefactPath }).get(0);
    } catch (Exception exception_p) {
      if (nbRetry_p != 0) {
        try {
          reconnect();
        } catch (ServiceException exception_p1) {
          // Sorry reconnection failed
          // retry will retry the reconnection too...
        }
        return getVariableArtefactPath(nbRetry_p - 1);
      }
      throw new RemoteException("", exception_p); //$NON-NLS-1$
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IRemoteVariableManager#getVariableConfigurationDirectory()
   */
  public IVariable getVariableConfigurationDirectory() throws RemoteException {
    return getVariableConfigurationDirectory(CONNECTION_RETRY_MAX);
  }

  /**
   * @param nbRetry_p
   * @return
   * @throws RemoteException
   */
  protected IVariable getVariableConfigurationDirectory(int nbRetry_p) throws RemoteException {
    try {
      init();
      VariableWS variableConfigurationDirectory = _variableManagerWSAdapter.getVariableConfigurationDirectory();
      // Precondition.
      if (null == variableConfigurationDirectory) {
        return null;
      }
      return variableWSToIVariable(new VariableWS[] { variableConfigurationDirectory }).get(0);
    } catch (Exception exception_p) {
      if (nbRetry_p != 0) {
        try {
          reconnect();
        } catch (ServiceException exception_p1) {
          // Sorry reconnection failed
          // retry will retry the reconnection too...
        }
        return getVariableConfigurationDirectory(nbRetry_p - 1);
      }
      throw new RemoteException("", exception_p); //$NON-NLS-1$
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IRemoteVariableManager#getVariableLocalized(java.lang.String, java.lang.String)
   */
  public IVariable getVariableLocalized(String variablePathName, String locale) throws RemoteException {
    return getVariableLocalized(variablePathName, locale, CONNECTION_RETRY_MAX);
  }

  /**
   * @param variablePathName_p
   * @param locale_p
   * @param nbRetry_p
   * @return
   * @throws RemoteException
   */
  protected IVariable getVariableLocalized(String variablePathName_p, String locale_p, int nbRetry_p) throws RemoteException {
    try {
      init();
      VariableWS variableLocalized = _variableManagerWSAdapter.getVariableLocalized(variablePathName_p, locale_p);
      // Precondition.
      if (null == variableLocalized) {
        return null;
      }
      return variableWSToIVariable(new VariableWS[] { variableLocalized }).get(0);
    } catch (Exception exception_p) {
      if (nbRetry_p != 0) {
        try {
          reconnect();
        } catch (ServiceException exception_p1) {
          // Sorry reconnection failed
          // retry will retry the reconnection too...
        }
        return getVariableLocalized(variablePathName_p, locale_p, nbRetry_p - 1);
      }
      throw new RemoteException("", exception_p); //$NON-NLS-1$
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IRemoteVariableManager#getVariables(java.lang.String)
   */
  public List<IVariable> getVariables(String categoryPath) throws RemoteException {
    return getVariables(categoryPath, CONNECTION_RETRY_MAX);
  }

  /**
   * @param categoryPath_p
   * @param nbRetry_p
   * @return
   * @throws RemoteException
   */
  protected List<IVariable> getVariables(String categoryPath_p, int nbRetry_p) throws RemoteException {
    try {
      init();
      VariableWS[] objects = _variableManagerWSAdapter.getVariables(categoryPath_p);
      return variableWSToIVariable(objects);
    } catch (Exception exception_p) {
      if (nbRetry_p != 0) {
        try {
          reconnect();
        } catch (ServiceException exception_p1) {
          // Sorry reconnection failed
          // retry will retry the reconnection too...
        }
        return getVariables(categoryPath_p, nbRetry_p - 1);
      }
      throw new RemoteException("", exception_p); //$NON-NLS-1$
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IRemoteVariableManager#getVariableSharedDirectory()
   */
  public IVariable getVariableSharedDirectory() throws RemoteException {
    return getVariableSharedDirectory(CONNECTION_RETRY_MAX);
  }

  /**
   * @param nbRetry_p
   * @return
   * @throws RemoteException
   */
  protected IVariable getVariableSharedDirectory(int nbRetry_p) throws RemoteException {
    try {
      init();
      VariableWS variableSharedDirectory = _variableManagerWSAdapter.getVariableSharedDirectory();
      // Precondition.
      if (null == variableSharedDirectory) {
        return null;
      }
      return variableWSToIVariable(new VariableWS[] { variableSharedDirectory }).get(0);
    } catch (Exception exception_p) {
      if (nbRetry_p != 0) {
        try {
          reconnect();
        } catch (ServiceException exception_p1) {
          // Sorry reconnection failed
          // retry will retry the reconnection too...
        }
        return getVariableSharedDirectory(nbRetry_p - 1);
      }
      throw new RemoteException("", exception_p); //$NON-NLS-1$
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IRemoteVariableManager#getVariablesLocalized(java.lang.String, java.lang.String)
   */
  public List<IVariable> getVariablesLocalized(String categoryPath, String locale) throws RemoteException {
    return getVariablesLocalized(categoryPath, locale, CONNECTION_RETRY_MAX);
  }

  /**
   * @param categoryPath
   * @param locale
   * @param nbRetry_p
   * @return
   * @throws RemoteException
   */
  protected List<IVariable> getVariablesLocalized(String categoryPath, String locale, int nbRetry_p) throws RemoteException {
    try {
      init();
      VariableWS[] objects = _variableManagerWSAdapter.getVariablesLocalized(categoryPath, locale);
      return variableWSToIVariable(objects);
    } catch (Exception exception_p) {
      if (nbRetry_p != 0) {
        try {
          reconnect();
        } catch (ServiceException exception_p1) {
          // Sorry reconnection failed
          // retry will retry the reconnection too...
        }
        return getVariablesLocalized(categoryPath, locale, nbRetry_p - 1);
      }
      throw new RemoteException("", exception_p); //$NON-NLS-1$
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IRemoteVariableManager#getVariableTemporaryDirectory()
   */
  public IVariable getVariableTemporaryDirectory() throws RemoteException {
    return getVariableTemporaryDirectory(CONNECTION_RETRY_MAX);
  }

  /**
   * @param nbRetry_p
   * @return
   * @throws RemoteException
   */
  protected IVariable getVariableTemporaryDirectory(int nbRetry_p) throws RemoteException {
    try {
      init();
      VariableWS variableTemporaryDirectory = _variableManagerWSAdapter.getVariableTemporaryDirectory();
      if (null == variableTemporaryDirectory) {
        return null;
      }
      return variableWSToIVariable(new VariableWS[] { variableTemporaryDirectory }).get(0);
    } catch (Exception exception_p) {
      if (nbRetry_p != 0) {
        try {
          reconnect();
        } catch (ServiceException exception_p1) {
          // Sorry reconnection failed
          // retry will retry the reconnection too...
        }
        return getVariableTemporaryDirectory(nbRetry_p - 1);
      }
      throw new RemoteException("", exception_p); //$NON-NLS-1$
    }
  }

  /**
   * Get value of the Segment variable
   * @return Segment name
   * @throws RemoteException
   */
  public String getSegmentName() throws RemoteException {
    String segment = _variableManagerWSAdapter.getSubstitutedValue(SEGMENT_VARIABLE);
    return segment;
  }

  /**
   * Initialize adapter.<br>
   * Basically, try and connect to webservices.
   * @throws ServiceException
   */
  protected void init() throws ServiceException {
    VariableManagerWSAdapterServiceLocator locator = new VariableManagerWSAdapterServiceLocator();
    Integer port;
    try {
      port = new Integer(ServerConfParam.getInstance().getPort());
      if (null != port) {
        locator.setVariableManagerEndpointAddress(MessageFormat.format(VARIABLE_MANAGER_ADDRESS, port.toString()));
        _variableManagerWSAdapter = locator.getVariableManager();
      }
    } catch (Exception exception_p) {
      throw new ServiceException(Messages.getString("VariableManagerAdapter.ERROR_NO_PORT"), exception_p); //$NON-NLS-1$
    }
  }

  /**
   * Configuration directory updated.
   * @param confDirPath_p
   * @param deltas_p
   * @return
   * @throws RemoteException
   * @throws ServiceException
   */
  public DeltaState[] notifyConfigurationDirectoryUpdated(String confDirPath_p, ConfDirDelta[] deltas_p) throws RemoteException, ServiceException {
    return _variableManagerWSAdapter.configurationDirectoryUpdated(confDirPath_p, deltas_p);
  }

  /**
   * Try and reconnect to the web services server.
   * @throws ServiceException
   */
  protected void reconnect() throws ServiceException {
    try {
      Thread.sleep(1000);
    } catch (Exception exception_p) {
      // Do nothing
    }
    ServerConfParam.getInstance().readFile();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IRemoteVariableManager#registerAsVariableConsumer(java.lang.String)
   */
  public void registerAsVariableConsumer(String toolInstanceId) throws RemoteException {
    registerAsVariableConsumer(toolInstanceId, CONNECTION_RETRY_MAX);
  }

  /**
   * @param toolInstanceId
   * @param nbRetry_p
   * @throws RemoteException
   */
  protected void registerAsVariableConsumer(String toolInstanceId, int nbRetry_p) throws RemoteException {
    try {
      init();
      _variableManagerWSAdapter.registerAsVariableConsumer(toolInstanceId);
    } catch (Exception exception_p) {
      if (nbRetry_p != 0) {
        try {
          reconnect();
        } catch (ServiceException exception_p1) {
          // Sorry reconnection failed
          // retry will retry the reconnection too...
        }
        registerAsVariableConsumer(toolInstanceId, nbRetry_p - 1);
      }
      throw new RemoteException("", exception_p); //$NON-NLS-1$
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IRemoteVariableManager#unregisterAsVariableConsumer(java.lang.String)
   */
  public void unregisterAsVariableConsumer(String toolInstanceId) throws RemoteException {
    unregisterAsVariableConsumer(toolInstanceId, CONNECTION_RETRY_MAX);
  }

  /**
   * @param toolInstanceId
   * @param nbRetry_p
   * @throws RemoteException
   */
  protected void unregisterAsVariableConsumer(String toolInstanceId, int nbRetry_p) throws RemoteException {
    try {
      init();
      _variableManagerWSAdapter.unregisterAsVariableConsumer(toolInstanceId);
    } catch (Exception exception_p) {
      if (nbRetry_p != 0) {
        try {
          reconnect();
        } catch (ServiceException exception_p1) {
          // Sorry reconnection failed
          // retry will retry the reconnection too...
        }
        unregisterAsVariableConsumer(toolInstanceId, nbRetry_p - 1);
      }
      throw new RemoteException("", exception_p); //$NON-NLS-1$
    }
  }

  /**
   * Convert specified array of {@link VariableWS} to a {@link List} of {@link IVariable} elements.
   * @param objects
   * @return
   */
  private List<IVariable> variableWSToIVariable(VariableWS[] objects) {
    List<IVariable> list = new ArrayList<IVariable>(0);
    for (VariableWS variable : objects) {
      if (variable instanceof EnvironmentVariableWS) {
        list.add(new EnvironmentVariableAdapter((EnvironmentVariableWS) variable));
      } else if (null != variable) {
        list.add(new VariableAdapter(variable));
      }
    }
    return list;
  }

  /**
   * Configuration directory updated.
   * @param confDirPath_p
   * @param deltas_p
   * @return
   * @throws RemoteException
   * @throws ServiceException
   * @deprecated use {@link #notifyConfigurationDirectoryUpdated(String, ConfDirDelta[])} instead.
   */
  public static DeltaState[] configurationDirectoryUpdated(String confDirPath_p, ConfDirDelta[] deltas_p) throws RemoteException, ServiceException {
    return getInstance().notifyConfigurationDirectoryUpdated(confDirPath_p, deltas_p);
  }

  /**
   * Get artifact paths.
   * @return
   * @throws ServiceException
   * @throws RemoteException
   * @deprecated use {@link #getArtefactDirectories()} instead.
   */
  public static String[] getArtefactPaths() throws ServiceException, RemoteException {
    List<String> artefactDirectories = getInstance().getArtefactDirectories();
    return artefactDirectories.toArray(new String[artefactDirectories.size()]);
  }

  /**
   * Get configuration directory unique value.
   * @return
   * @throws ServiceException
   * @throws RemoteException
   * @deprecated use {@link #getVariableConfigurationDirectory()} instead.
   */
  public static String getConfigurationDirectory() throws ServiceException, RemoteException {
    IVariable variable = getInstance().getVariableConfigurationDirectory();
    String directory = getInstance().getFirstValue(variable);
    return directory;
  }

  /**
   * Get unique instance.
   * @return
   * @throws ServiceException
   */
  public static VariableManagerAdapter getInstance() throws ServiceException {
    if (__instance == null) {
      __instance = new VariableManagerAdapter();
    }
    return __instance;
  }

  /**
   * Get temporary directory unique value.
   * @return
   * @throws ServiceException
   * @throws RemoteException
   * @deprecated use {@link #getVariableTemporaryDirectory()} instead.
   */
  public static String getTemporaryDirectory() throws ServiceException, RemoteException {
    IVariable variable = getInstance().getVariableTemporaryDirectory();
    String directory = getInstance().getFirstValue(variable);
    return directory;
  }

  /**
   * Get first value of targeted variable.
   * @param variablePath_p
   * @return
   * @throws ServiceException
   * @throws RemoteException
   * @deprecated use {@link #getFirstValue(String)} instead.
   */
  public static String getValue(String variablePath_p) throws ServiceException, RemoteException {
    return getInstance().getFirstValue(variablePath_p);
  }
}