/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.webservices.jettyConfigurator;

import java.util.Dictionary;

import org.eclipse.equinox.http.jetty.JettyConstants;
import org.eclipse.equinox.http.jetty.JettyCustomizer;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.servlet.ServletContextHandler;

import com.thalesgroup.orchestra.framework.common.helper.ExtensionPointHelper;

/**
 * Depending on configuration, use a static or acquire and save a dynamic http port.
 * @author s0011584
 */
public class DynamicPortJettyCustomizer extends JettyCustomizer {
  private static final String PROPERTY_PREFIX = "org.eclipse.equinox.http.jetty."; //$NON-NLS-1$

  /**
   * Default constructor.
   */
  public DynamicPortJettyCustomizer() {
    super();
  }

  /**
   * @see org.eclipse.equinox.http.jetty.JettyCustomizer#customizeContext(java.lang.Object, java.util.Dictionary)
   */
  @Override
  public Object customizeContext(Object context_p, Dictionary settings_p) {
    ServletContextHandler contextHandler = (ServletContextHandler) context_p;
    // Look for implementation.
    ServletContextHandler httpHandler =
        (ServletContextHandler) ExtensionPointHelper.createExecutableExtension("com.thalesgroup.orchestra.framework.common", "httpHandler", null); //$NON-NLS-1$ //$NON-NLS-2$
    // Could not find implementation, stop here.
    if (null == httpHandler) {
      return context_p;
    }

    // Initialize new custom ServletContextHandler (HttpHandler) with jetty ServletContextHandler
    // (See HttpServerManager.createHttpContext)
    httpHandler.setMimeTypes(contextHandler.getMimeTypes());
    httpHandler.setAttributes(contextHandler.getAttributes());
    httpHandler.setClassLoader(contextHandler.getClassLoader());
    httpHandler.setContextPath(contextHandler.getContextPath());
    httpHandler.setSessionHandler(contextHandler.getSessionHandler());

    return httpHandler;
  }

  /**
   * @see org.eclipse.equinox.http.jetty.JettyCustomizer#customizeHttpConnector(java.lang.Object, java.util.Dictionary)
   */
  @Override
  public Object customizeHttpConnector(Object connector_p, Dictionary settings_p) {
    if (connector_p instanceof org.eclipse.jetty.server.Connector) {
      Connector connector = (Connector) connector_p;
      // Retrieve HTTP port from JETTY.
      int httpPort = -1;
      try {
        httpPort = new Integer(System.getProperty(PROPERTY_PREFIX + JettyConstants.HTTP_PORT)).intValue();
      } catch (Exception exception_p) {
        // Silence !
      }
      if (httpPort != -1) {
        connector.setPort(httpPort);
      }
      // Set max form size to -1 to accept any request size
      System.setProperty("org.eclipse.jetty.server.Request.maxFormContentSize", "-1"); //$NON-NLS-1$ //$NON-NLS-2$
    }
    return connector_p;
  }
}