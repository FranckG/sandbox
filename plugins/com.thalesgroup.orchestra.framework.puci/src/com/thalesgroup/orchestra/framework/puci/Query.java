/**
 * Copyright (c) THALES, 2010. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.puci;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import com.thalesgroup.orchestra.framework.lib.constants.HTTPKey;
import com.thalesgroup.orchestra.framework.lib.constants.PapeeteHTTPKeyRequest;
import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;

/**
 * <p>
 * Titre : URLQuery
 * </p>
 * <p>
 * Description :
 * </p>
 * This class provides a way to create an URL to perform a query against a Web server. The query takes a set of properties that will be converted into a query
 * string.
 * @author Themis developer
 * @version 2.1
 */
final class Query {
  /**
   * Initial capacity for a String Buffer
   */
  private static final int _STRING_BUFFER_CAPACITY = 100;

  private String _command;
  private HashMap<String, String> _data = new HashMap<String, String>();

  /**
   * @param command_p
   * @param uRIs_p
   * @param clientToolName_p
   * @param readonly_p
   * @param format_p
   * @param cmpa_p
   * @param context_p
   * @param serviceParameters_p
   */
  public Query(String command_p, List<OrchestraURI> URIs_p) {
    _command = command_p;

    StringBuilder uris = new StringBuilder();
    if ((URIs_p != null) && (URIs_p.size() != 0)) {
      for (OrchestraURI uri : URIs_p) {
        uris.append(uri.getUri());
        uris.append('|');
      }
      if (uris.length() > 1) {
        uris.setLength(uris.length() - 1);
      }
    }

    _data.put(PapeeteHTTPKeyRequest._COMMAND_NAME_KEY, _command);
    _data.put(PapeeteHTTPKeyRequest._URIS, uris.toString());
  }

  /**
   * @return
   * @throws UnsupportedEncodingException
   */
  public String createHTTPQuery() throws UnsupportedEncodingException {
    StringBuffer postQuery = new StringBuffer(_STRING_BUFFER_CAPACITY);

    // Encode data
    boolean isFirst = true;
    for (String s : _data.keySet()) {
      if (isFirst) {
        isFirst = false;
      } else {
        postQuery.append('&');
      }
      postQuery.append(URLEncoder.encode(s, HTTPKey._ENCODE_FORMAT));
      postQuery.append('=');
      postQuery.append(URLEncoder.encode(_data.get(s), HTTPKey._ENCODE_FORMAT));
    }

    return postQuery.toString();
  }

}
