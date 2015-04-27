/**
 * Copyright (c) THALES, 2010. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.puci;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.osgi.framework.BundleContext;

import com.thalesgroup.orchestra.framework.lib.base.constants.HTTPConstants;

/**
 * PUCI activator.<br>
 * Handles the unique HttpClient reference.
 * @author t0076261
 */
public class PuciActivator extends Plugin {
  /**
   * Shared instance.
   */
  private static PuciActivator __instance;
  /**
   * HttpClient unique instance.
   */
  private HttpClient _httpClient;

  /**
   * Get {@link HttpClient} to use.
   * @return
   * @throws Exception
   */
  public synchronized HttpClient getHttpClient() throws Exception {
    // Lazy starting.
    if (null == _httpClient) {
      _httpClient = new HttpClient();
      _httpClient.setTimeout(HTTPConstants.HTTP_TIMEOUT);
      // Max number of concurrent connections.
      _httpClient.setMaxConnectionsPerAddress(100);
      // Max number of client threads.
      _httpClient.setThreadPool(new QueuedThreadPool(100));
      _httpClient.start();
    }
    return _httpClient;
  }

  /**
   * @see org.eclipse.core.runtime.Plugin#start(org.osgi.framework.BundleContext)
   */
  @Override
  public void start(BundleContext context_p) throws Exception {
    super.start(context_p);
    __instance = this;
  }

  /**
   * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
   */
  @Override
  public void stop(BundleContext context_p) throws Exception {
    super.stop(context_p);
    __instance = null;
    if (null != _httpClient) {
      _httpClient.stop();
      _httpClient = null;
    }
  }

  /**
   * Get shared instance.
   * @return
   */
  public static PuciActivator getInstance() {
    // Java application case (ie not an Eclipse application).
    if (!Platform.isRunning()) {
      if (null == __instance) {
        __instance = new PuciActivator();
      }
    }
    return __instance;
  }
}