/**
 * <p>
 * Copyright (c) 2002-2004 : Thales Research & Technology
 * </p>
 * <p>
 * Société : Thales Research & Technology
 * </p>
 * <p>
 * Thales Part Number 16 262 601
 * </p>
 */
package com.thalesgroup.orchestra.framework.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

import com.thalesgroup.orchestra.framework.environment.EnvironmentVariables;

/**
 * <p>
 * Title : Export
 * </p>
 * @author Papeete Tool Suite developer
 * @version 3.7.0
 */
public class Util {
  static HashMap<String, String> _mimeTypes = null;

  @SuppressWarnings("nls")
  public static String GetMimeType(String extension_p) {
    String extension = extension_p;
    if (extension != null) {
      extension = extension_p.toLowerCase();
    }
    if (_mimeTypes == null) {
      _mimeTypes = new HashMap<String, String>();
      Properties mimeTypes = new Properties();
      File mimeTypesFile = new File(EnvironmentVariables.GetPapeeteLib(), "mimeTypes.properties");
      if (mimeTypesFile.exists()) {
        try {
          FileInputStream mimeTypesInputStream = new FileInputStream(mimeTypesFile);
          mimeTypes.load(mimeTypesInputStream);
          Set<Object> keySet = mimeTypes.keySet();
          for (Object object : keySet) {
            String propertyName = object.toString();
            _mimeTypes.put(propertyName.startsWith(".") ? propertyName.toLowerCase() : "." + propertyName.toLowerCase(), mimeTypes.getProperty(propertyName)
                .trim());
          }
        } catch (IOException e) {
          populateDefaultMimeTypes();
        }
      } else {
        populateDefaultMimeTypes();
      }
    }
    String result = _mimeTypes.get(extension);
    if (result == null) {
      result = "unknown";
    }
    return result;
  }

  @SuppressWarnings("nls")
  private static void populateDefaultMimeTypes() {
    _mimeTypes.put(".txt", "text/plain");
    _mimeTypes.put(".cpp", "text/plain");
    _mimeTypes.put(".log", "text/plain");
    _mimeTypes.put(".xml", "text/xml");
    _mimeTypes.put(".htm", "text/html");
    _mimeTypes.put(".html", "text/html");
    _mimeTypes.put(".xsl", "text/xsl");
    _mimeTypes.put(".rtf", "application/rtf");
    _mimeTypes.put(".doc", "application/msword");
    _mimeTypes.put(".xls", "application/x-excel");
    _mimeTypes.put(".ppt", "application/vnd.ms-powerpoint");
    _mimeTypes.put(".pdf", "application/pdf");
    _mimeTypes.put(".eps", "image/eps");
    _mimeTypes.put(".jpeg", "image/jpeg");
    _mimeTypes.put(".jpg", "image/jpeg");
    _mimeTypes.put(".png", "image/png");
    _mimeTypes.put(".bmp", "image/bmp");
    _mimeTypes.put(".gif", "image/gif");
    _mimeTypes.put(".wmf", "image/x-wmf");
    _mimeTypes.put(".emf", "image/x-emf");
    _mimeTypes.put(".zip", "application/zip");
    _mimeTypes.put(".tiff", "image/tiff");
    _mimeTypes.put(".tif", "image/tiff");
    _mimeTypes.put(".pcx", "image/pcx");
  }
}