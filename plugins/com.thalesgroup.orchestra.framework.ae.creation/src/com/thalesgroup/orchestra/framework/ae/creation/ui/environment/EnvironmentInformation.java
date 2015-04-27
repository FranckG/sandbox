/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ae.creation.ui.environment;

import java.util.HashMap;
import java.util.Map;

/**
 * @author s0040806
 */
public class EnvironmentInformation {

  private String _name;
  private String _type;
  private String _id;
  private boolean _credentialsSupported;

  private Map<String, String> _attributes;

  public EnvironmentInformation() {
    _attributes = new HashMap<String, String>();
  }

  public EnvironmentInformation(String name_p, String type_p, String id_p, boolean credentialsSupported_p) {
    this();
    _name = name_p;
    _type = type_p;
    _id = id_p;
    _credentialsSupported = credentialsSupported_p;
  }

  /**
   * @return the name
   */
  public String getName() {
    return _name;
  }

  /**
   * @param name_p the name to set
   */
  public void setName(String name_p) {
    _name = name_p;
  }

  /**
   * @return the type
   */
  public String getType() {
    return _type;
  }

  /**
   * @param type_p the type to set
   */
  public void setType(String type_p) {
    _type = type_p;
  }

  /**
   * @return the id
   */
  public String getId() {
    return _id;
  }

  /**
   * @param id_p the id to set
   */
  public void setId(String id_p) {
    _id = id_p;
  }

  public boolean areCredentialsSupported() {
    return _credentialsSupported;
  }

  public void setCredentialsSupported(boolean credentialsSupported_p) {
    _credentialsSupported = credentialsSupported_p;
  }

  public void addAttribute(String key_p, String value_p) {
    _attributes.put(key_p, value_p);
  }

  public Map<String, String> getAttributes() {
    return _attributes;
  }
}
