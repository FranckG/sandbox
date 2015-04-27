package com.thalesgroup.orchestra.framework.config;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.text.MessageFormat;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;

import com.thalesgroup.orchestra.framework.FrameworkActivator;
import com.thalesgroup.orchestra.framework.environment.EnvironmentVariables;
import com.thalesgroup.orchestra.framework.lib.base.UtilFunction;
import com.thalesgroup.orchestra.framework.lib.base.conf.ServerConfParam;
import com.thalesgroup.orchestra.framework.lib.base.constants.PapeeteDTD;

public final class ConfigServer {
  private static ConfigServer _SINGLETON;
  private static final String JETTY_HTTP_PORT = "org.eclipse.equinox.http.jetty.http.port"; //$NON-NLS-1$
  private final String _sepCharacter = "?"; //$NON-NLS-1$
  private final String _SERVER_NAME = "Orchestra Framework " + FrameworkActivator.getDefault().getVersion(); //$NON-NLS-1$
  private String _serverIP;
  private long _timeout = 7200000;

  private ConfigServer() {
    // Private constructor.
  }

  /**
   * @return the timeout of the ECF Server
   */
  public long getECFTimeOut() {
    return _timeout;
  }

  /**
   * Method GetPapeeteName.
   * @return String
   */
  public String GetPapeeteName() {
    return _SERVER_NAME;
  }

  /**
   * Method GetPort.
   * @return int
   */
  public int GetPort() {
    return ServerConfParam.getInstance().getPort();
  }

  /**
   * Method GetSepCharacter.
   * @return String
   */
  public String GetSepCharacter() {
    return _sepCharacter;
  }

  /**
   * Method GetServerIP.
   * @return String
   */
  public String GetServerIP() {
    return _serverIP;
  }

  /**
   * Format the type of port (Dynamic or Static).
   * @return The type of port
   */
  public String GetServerPortType() {
    String result = PapeeteDTD._SERVER_PORT_MODE_STATIC;
    if (ServerConfParam.getInstance().isDynamicPort()) {
      result = PapeeteDTD._SERVER_PORT_MODE_DYNAMIC;
    }
    return MessageFormat.format("{0}{1}", result.substring(0, 1).toUpperCase(), result.substring(1)); //$NON-NLS-1$
  }

  /**
   * Method initFromXML.
   */
  private void initFromXML() {
    initXMLServerParameters();
  }

  /**
   * Initialization.
   * @return
   */
  public IStatus initialization() {
    MultiStatus result = new MultiStatus(FrameworkActivator.getDefault().getPluginId(), 0, Messages.ConfigServer_Initialization, null);
    InetAddress inetAddress = null;
    try {
      inetAddress = InetAddress.getLocalHost();
    } catch (UnknownHostException exception_p) {
      // Leave it null.
    }
    if (inetAddress != null) {
      _serverIP = inetAddress.getHostAddress();
    } else {
      _serverIP = "localhost"; //$NON-NLS-1$
    }
    initFromXML();
    if (ServerConfParam.getInstance().isDynamicPort()) {
      result.add(serialize());
    }
    return result;
  }

  /**
   * Method initXMLServerParameters.
   * @throws MalformedURLException
   */
  @SuppressWarnings("boxing")
  private void initXMLServerParameters() {
    Integer port = null;
    String ecfServerLocation = null;
    // Dynamic
    if (ServerConfParam.getInstance().isDynamicPort()) {
      int lowerBound = ServerConfParam.getInstance().getDynamicLowerBound();
      int upperBound = ServerConfParam.getInstance().getDynamicUpperBound();
      port = UtilFunction.getFreePortInRange(lowerBound, upperBound);
      // Select a dynamic ECF port.
      int portBegin = ServerConfParam.getInstance().getDynamicLowerBound();
      int portEnd = ServerConfParam.getInstance().getDynamicUpperBound();
      Integer candidatePort = UtilFunction.getFreePortInRange(portBegin, portEnd);
      int ecfPort = candidatePort.intValue();
      ecfServerLocation = MessageFormat.format("ecftcp://localhost:{0}/server", String.valueOf(ecfPort)); //$NON-NLS-1$
    }
    // else Static
    else {
      port = new Integer(ServerConfParam.getInstance().getPort());
      ecfServerLocation = ServerConfParam.getInstance().getEcfServerLocation();
    }
    // Save the port in the system property in order to be serialized later.
    ServerConfParam.getInstance().setPort(port);
    System.setProperty(JETTY_HTTP_PORT, port.toString());
    ServerConfParam.getInstance().setEcfServerLocation(ecfServerLocation);
    long timeout = ServerConfParam.getInstance().getTimeout();
    if (-1 != timeout) {
      _timeout = timeout;
    }
  }

  private IStatus serialize() {
    MultiStatus result = new MultiStatus(FrameworkActivator.getDefault().getPluginId(), 0, "Update of configuration files", null); //$NON-NLS-1$
    result.add(ServerConfParam.getInstance().writeServerFile(EnvironmentVariables.GetConfigServerFilePathName(),
        EnvironmentVariables.GetConfigServerDtdPathName()));
    return result;
  }

  public static ConfigServer getInstance() {
    if (null == _SINGLETON) {
      _SINGLETON = new ConfigServer();
    }
    return _SINGLETON;
  }
}
