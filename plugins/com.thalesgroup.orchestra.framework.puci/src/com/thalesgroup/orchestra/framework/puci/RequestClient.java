/**
 * Copyright (c) THALES, 2010. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.puci;

import java.io.BufferedReader;
import java.io.StringReader;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.client.ContentExchange;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.HttpExchange;
import org.eclipse.jetty.io.ByteArrayBuffer;

import com.thalesgroup.orchestra.framework.lib.base.conf.ServerConfParam;
import com.thalesgroup.orchestra.framework.lib.base.constants.PapeeteHTTPErrorsCode;
import com.thalesgroup.orchestra.framework.lib.constants.HTTPKey;
import com.thalesgroup.orchestra.framework.lib.constants.PapeeteHTTPKeyRequest;
import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;

/**
 * <p>
 * Title : RequestClient
 * </p>
 * <p>
 * Description : this class constructs and send a request to papeeteServer and get http response.
 * @author Themis developer
 * @version 3.1
 */
final class RequestClient {
  /**
   * PATH in URL
   */
  private static final String __PATH = "/execute"; //$NON-NLS-1$
  private static final String __RETRY_REQUEST = "RetryRequest"; //$NON-NLS-1$

  /**
   * This attribute stores all parameters for request
   */
  private Query _query = null;

  /**
   * Method RequestClient.
   * @param iQueryProps - parameters
   * @param iServerAddress - server address
   * @param iPort - port
   * @throws Exception
   */
  public RequestClient(Query query_p) {
    _query = query_p;
  }

  /**
   * Parse the response string as a key/value parameters string.
   * @param response_p
   * @return The map containing all key/value pairs extracted.
   */
  protected void parseResponse(String response_p, Map<String, String> data_p) {
    String[] pairs = response_p.split("&"); //$NON-NLS-1$
    for (String pair : pairs) {
      String[] keyValue = pair.split("="); //$NON-NLS-1$
      if (keyValue.length == 2) {
        data_p.put(OrchestraURI.decode(keyValue[0]), OrchestraURI.decode(keyValue[1]));
      }
    }
  }

  /**
   * Method sendPostRequest. send an http post request
   * @return String - http response (code and message)
   */
  public Map<String, String> sendPostRequest() throws Exception {
    return sendPostRequest(3);
  }

  protected Map<String, String> sendPostRequest(final int nbRetry_p) throws Exception {
    final Map<String, String> result = new HashMap<String, String>();

    final int port = ServerConfParam.getInstance().getPort();
    String serverAddress = "localhost"; //$NON-NLS-1$

    HttpClient httpClient = PuciActivator.getInstance().getHttpClient();
    if (null == httpClient) {
      result.put(PapeeteHTTPKeyRequest.__MESSAGE, Messages.RequestClient_UnableToGetHttpClient);
      return result;
    }

    ContentExchange contentExchange = new ContentExchange() {
      @Override
      protected void onConnectionFailed(Throwable throwable_p) {
        if (nbRetry_p == 0) {
          result.put(PapeeteHTTPKeyRequest.__ERROR_CODE, Integer.toString(PapeeteHTTPErrorsCode.HTTP_CLIENT_INITIALIZATION_ERROR));
          result.put(PapeeteHTTPKeyRequest.__MESSAGE, MessageFormat.format(Messages.RequestClient_UnableToConnectToFramework, Integer.toString(port)));
        } else {
          result.put(PapeeteHTTPKeyRequest.__ERROR_CODE, __RETRY_REQUEST);
        }
      }

      @Override
      protected void onException(Throwable throwable_p) {
        result.put(PapeeteHTTPKeyRequest.__ERROR_CODE, Integer.toString(PapeeteHTTPErrorsCode.HTTP_CLIENT_INITIALIZATION_ERROR));
        result.put(PapeeteHTTPKeyRequest.__MESSAGE, Messages.RequestClient_UnexpectedError);
      }
    };

    String r = _query.createHTTPQuery();
    StringBuilder url = new StringBuilder(PapeeteHTTPKeyRequest._HTTP_HEADER_PROLOGUE);
    url.append(serverAddress);
    url.append(PapeeteHTTPKeyRequest._HEADER_VALUE_DELIM);
    url.append(port);
    url.append(__PATH);

    contentExchange.addRequestHeader("Orchestra", "orchestra"); //$NON-NLS-1$ //$NON-NLS-2$

    // Http auto-close : needed to specify http 1.1
    contentExchange.setVersion(11);
    contentExchange.setMethod(HTTPKey._HTTP_POST_METHOD);

    contentExchange.setURL(url.toString());

    contentExchange.setRequestContentType("application/x-www-form-urlencoded"); //$NON-NLS-1$
    contentExchange.setRequestContent(new ByteArrayBuffer(r));

    httpClient.send(contentExchange);
    int waitResponse = contentExchange.waitForDone();

    if (waitResponse != HttpExchange.STATUS_COMPLETED) {
      // Check if onConnectionFailed or onException were called or not.
      if (!result.containsKey(PapeeteHTTPKeyRequest.__ERROR_CODE)) {
        result.put(PapeeteHTTPKeyRequest.__ERROR_CODE, Integer.toString(waitResponse));
        result.put(PapeeteHTTPKeyRequest.__MESSAGE, Messages.RequestClient_UnexpectedError);
      } else {
        // If the error code is a retry request, retry it!
        if (result.get(PapeeteHTTPKeyRequest.__ERROR_CODE).equals(__RETRY_REQUEST)) {
          result.clear();
          try {
            Thread.sleep(1000);
          } catch (Exception exception_p) {
            // Do nothing
          }
          ServerConfParam.getInstance().readFile();
          return sendPostRequest(nbRetry_p - 1);
        }
      }
      return result;
    }

    if (contentExchange.getResponseContent() != null) {
      BufferedReader br = new BufferedReader(new StringReader(contentExchange.getResponseContent()));

      String lineRead = br.readLine();
      parseResponse(lineRead, result);
    }

    return result;
  }
}
