package com.thalesgroup.orchestra.framework.model;

import java.util.List;

/**
 * Variable properties.
 */
public interface IVariable {
  /**
   * Get variable description.
   * @return
   */
  String getDescription();

  /**
   * Get variable name.
   * @return
   */
  String getName();

  /**
   * Get variable path, as defined by {@link IRemoteVariableManager#getVariable(String)}
   * @return
   */
  String getPath();

  /**
   * Get variable values.<br>
   * Values are substituted ones, see {@link IRemoteVariableManager#getSubstitutedValue(String)}.
   * @return an empty list, if the variable has no value, or a singleton list, if the variable contains only one value, or the list of values.
   */
  List<String> getValues();

  /**
   * Is variable owning multiple values (<code>true</code> is then returned) or one value only (<code>false</code> is then returned) ?
   * @return
   */
  boolean isMultiValued();
}