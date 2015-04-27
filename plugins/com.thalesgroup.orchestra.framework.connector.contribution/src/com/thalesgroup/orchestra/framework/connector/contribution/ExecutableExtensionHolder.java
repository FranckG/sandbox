package com.thalesgroup.orchestra.framework.connector.contribution;

import java.util.HashMap;
import java.util.Map;

/**
 * This class holds the Executable Extension points implementation to avoid to have to reload definition when needed
 * @author s0018747
 */
public final class ExecutableExtensionHolder {

  /* The SingletonObject Object */
  private static ExecutableExtensionHolder __instance;

  /* The Collection of handlers to be stored */
  private Map<String, AbstractParametersHandler> _toolToHandler;

  /* The Constructor */
  private ExecutableExtensionHolder() {
    _toolToHandler = new HashMap<String, AbstractParametersHandler>();
  }

  public Map<String, AbstractParametersHandler> getHandlers() {
    return _toolToHandler;
  }

  /**
   * Get handler for specified tool.
   * @param toolName_p
   * @return <code>null</code> if no such handler could be found.
   */
  public AbstractParametersHandler getHandler(String toolName_p) {
    return _toolToHandler.get(toolName_p);
  }

  /* Method to get only one object of the SingletonObject */
  public static ExecutableExtensionHolder getInstance() {
    if (null == __instance) {
      __instance = new ExecutableExtensionHolder();
    }
    return __instance;
  }
}