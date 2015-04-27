/**
 * Copyright (c) THALES, 2010. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.http;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.servlet.ServletContextHandler;

import com.thalesgroup.orchestra.framework.FrameworkActivator;
import com.thalesgroup.orchestra.framework.application.FrameworkConsoleView;
import com.thalesgroup.orchestra.framework.application.FrameworkConsoleView.LogLevel;
import com.thalesgroup.orchestra.framework.exchange.StatusHandler;
import com.thalesgroup.orchestra.framework.exchange.status.SeverityType;
import com.thalesgroup.orchestra.framework.exchange.status.StatusDefinition;
import com.thalesgroup.orchestra.framework.exchange.status.StatusFactory;
import com.thalesgroup.orchestra.framework.internal.connector.RequestHandler;
import com.thalesgroup.orchestra.framework.internal.connector.StatusModelHelper;
import com.thalesgroup.orchestra.framework.lib.constants.HTTPKey;
import com.thalesgroup.orchestra.framework.lib.constants.PapeeteHTTPKeyRequest;
import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class handles HTTP requests received by Jetty server, extracts the data, executes the request then send the response.
 * @author t0076261
 */
public class HttpHandler extends ServletContextHandler {

  /**
   * Execute the request.
   * @param request_p : The request received.
   * @param response_p : The response that will be return to the client.
   */
  protected void executeHTTPService(HttpServletRequest request_p, HttpServletResponse response_p) {

    Map<String, String> httpBodyDataResponse = new HashMap<>(); // HTTP response - HTML body Key/Value
    String responseFilePath = null;

    // retrieving needed target
    Map<String, String> arguments = new HashMap<String, String>();
    try {
      extractData(request_p, arguments);
      RequestHandler requestHandler = new RequestHandler(arguments);
      httpBodyDataResponse = requestHandler.execute(); // execute request and get result map values.
      responseFilePath = httpBodyDataResponse.get(PapeeteHTTPKeyRequest.__RESPONSE_FILE_PATH);
      if (null != responseFilePath) {
        FrameworkActivator.getDefault().log(new Status(IStatus.OK, FrameworkActivator.getDefault().getPluginId(), '\t' + responseFilePath), null);
      }
    } catch (Throwable e) {
      responseFilePath = generateResponseFile(e.getMessage());
      httpBodyDataResponse.put(PapeeteHTTPKeyRequest.__RESPONSE_FILE_PATH, responseFilePath);
      FrameworkActivator.getDefault().log(new Status(IStatus.ERROR, FrameworkActivator.getDefault().getPluginId(), '\t' + responseFilePath), LogLevel.ERROR);
    } finally {
      // return map result of the request
      writeResponse(httpBodyDataResponse, response_p);
    }
  }

  /**
   * Extract data from the request <code>request_p</code> into the map <code>arguments_p</code>
   * @param request_p The input request.
   * @param arguments_p The map into which data are stored.
   */
  protected void extractData(HttpServletRequest request_p, Map<String, String> arguments_p) {
    Enumeration<String> headers = request_p.getHeaderNames();
    for (; headers.hasMoreElements();) {
      String name = headers.nextElement();
      String value = request_p.getHeader(name);
      arguments_p.put(name, value);
    }

    Enumeration<String> paramNames = request_p.getParameterNames();
    StringBuilder query = new StringBuilder("Request=("); //$NON-NLS-1$
    for (; paramNames.hasMoreElements();) {
      String name = paramNames.nextElement();
      String[] values = request_p.getParameterValues(name);
      String value = values[0];
      arguments_p.put(name, value);
      query.append(name);
      query.append('=');
      query.append(value);
      query.append(';');
    }
    query.append(')');
    arguments_p.put(HTTPKey._QUERY_KEY, query.toString());

    String host = request_p.getRemoteHost();
    String port = Integer.toString(request_p.getRemotePort());

    StringBuffer message = new StringBuffer("Get request "); //$NON-NLS-1$
    message.append("from : " + host + " (" + port + ')'); //$NON-NLS-1$ //$NON-NLS-2$
    FrameworkActivator.getDefault().log(new Status(IStatus.OK, FrameworkActivator.getDefault().getPluginId(), message.toString()), null);
    FrameworkActivator.getDefault().log(new Status(IStatus.OK, FrameworkActivator.getDefault().getPluginId(), '\t' + query.toString()), null);
  }

  /**
   * @param message_p
   */
  private String generateResponseFile(String message_p) {
    StatusHandler statusHandler = new StatusHandler();
    String responseFilePath = StatusModelHelper.createDataFileName("error", "status", statusHandler.getFileExtension()); //$NON-NLS-1$ //$NON-NLS-2$

    StatusDefinition statusDefinitionRoot = statusHandler.createNewModel(responseFilePath);
    com.thalesgroup.orchestra.framework.exchange.status.Status statusRoot = StatusModelHelper.getNewContainerStatus();

    com.thalesgroup.orchestra.framework.exchange.status.Status status = StatusFactory.eINSTANCE.createStatus();
    status.setSeverity(SeverityType.ERROR);
    status.setMessage(message_p);

    statusRoot.getStatus().add(status);
    statusDefinitionRoot.setStatus(statusRoot);

    // Build and save the complete model
    statusHandler.saveModel(statusDefinitionRoot, true);

    return responseFilePath;
  }

  /**
   * Fill the response which will be send to the client
   * @param ack_p The response code
   * @param arguments_p All data from the query modified by the execution
   * @param response_p The response that will be send
   */
  protected void writeResponse(Map<String, String> data_p, HttpServletResponse response_p) {
    try {
      response_p.setContentType(PapeeteHTTPKeyRequest.__HTTP_RESPONSE_CONTENT_TYPE);

      StringBuilder responseMessage = new StringBuilder();
      boolean firstDone = false;
      for (String key : data_p.keySet()) {
        String value = data_p.get(key);
        if (firstDone) {
          responseMessage.append('&');
        } else {
          firstDone = true;
        }
        responseMessage.append(OrchestraURI.encode(key));
        responseMessage.append('=');
        responseMessage.append(OrchestraURI.encode(value));
      }
      response_p.getWriter().println(responseMessage);
      response_p.setStatus(HttpServletResponse.SC_OK);
    } catch (Throwable e) {
      FrameworkConsoleView.writeErrorMessageToConsole(e.getMessage());
    }
  }

  /**
   * @see org.eclipse.jetty.server.handler.ScopedHandler#doHandle(java.lang.String, org.eclipse.jetty.server.Request, javax.servlet.http.HttpServletRequest,
   *      javax.servlet.http.HttpServletResponse)
   */
  @Override
  public void doHandle(String target_p, Request baseRequest_p, HttpServletRequest request_p, HttpServletResponse response_p) throws IOException,
      ServletException {
    String header = request_p.getHeader("Orchestra"); //$NON-NLS-1$
    if (null != header) {
      executeHTTPService(request_p, response_p);
      baseRequest_p.setHandled(true);
    } else {
      super.doHandle(target_p, baseRequest_p, request_p, response_p);
    }
  }

}