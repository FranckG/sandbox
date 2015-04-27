/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package framework.orchestra.thalesgroup.com.VariableManagerAPI;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.thalesgroup.orchestra.framework.model.IVariable;

import framework.orchestra.thalesgroup.com.VariableManager.VariableWS;

/**
 * The client-side implementation to wrap the {@link VariableWS}
 * @author s0011584
 */
public class VariableAdapter implements IVariable {
  private VariableWS _variableWS;

  /**
   * @param variable_p
   */
  public VariableAdapter(VariableWS variable_p) {
    _variableWS = variable_p;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IVariable#getDescription()
   */
  public String getDescription() {
    return _variableWS.getDescription();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IVariable#getName()
   */
  public String getName() {
    return _variableWS.getName();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IVariable#getPath()
   */
  public String getPath() {
    return _variableWS.getPath();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IVariable#getValue()
   */
  public List<String> getValues() {
    return Arrays.asList(_variableWS.getValues());
  }

  /**
   * Get underlying WS variable.
   * @return
   */
  protected VariableWS getVariable() {
    return _variableWS;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IVariable#isMultiValued()
   */
  public boolean isMultiValued() {
    return _variableWS.isMultiValued();
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return new ToStringBuilder(this).append("path", getPath()) //$NON-NLS-1$
        .append("values", getValues().toString()) //$NON-NLS-1$
        .append("multiValued", isMultiValued()) //$NON-NLS-1$
        .append("description", getDescription()).toString(); //$NON-NLS-1$
  }

}
