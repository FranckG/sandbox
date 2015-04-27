/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package framework.orchestra.thalesgroup.com.VariableManagerAPI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.thalesgroup.orchestra.framework.model.IEnvironmentVariable;

import framework.orchestra.thalesgroup.com.VariableManager.EnvironmentValueWS;
import framework.orchestra.thalesgroup.com.VariableManager.EnvironmentVariableWS;
import framework.orchestra.thalesgroup.com.VariableManager.EnvironmentWS;

/**
 * @author t0076261
 */
public class EnvironmentVariableAdapter extends VariableAdapter implements IEnvironmentVariable {
  /**
   * Constructor.
   * @param variable_p
   */
  public EnvironmentVariableAdapter(EnvironmentVariableWS variable_p) {
    super(variable_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IEnvironmentVariable#getEnvironments()
   */
  public List<IEnvironment> getEnvironments() {
    List<IEnvironment> result = new ArrayList<IEnvironment>(0);
    for (EnvironmentWS environment : getVariable().getEnvironments()) {
      result.add(new EnvironmentAdapter(environment));
    }
    return result;
  }

  /**
   * @see framework.orchestra.thalesgroup.com.VariableManagerAPI.VariableAdapter#getVariable()
   */
  @Override
  protected EnvironmentVariableWS getVariable() {
    return (EnvironmentVariableWS) super.getVariable();
  }

  /**
   * @see framework.orchestra.thalesgroup.com.VariableManagerAPI.VariableAdapter#toString()
   */
  @SuppressWarnings("nls")
  @Override
  public String toString() {
    return new ToStringBuilder(this).append(super.toString()).append("environments", getEnvironments().toString()).toString();
  }

  /**
   * @author t0076261
   */
  protected class EnvironmentAdapter implements IEnvironment {
    private EnvironmentWS _environment;

    /**
     * Constructor.
     * @param environment_p
     */
    protected EnvironmentAdapter(EnvironmentWS environment_p) {
      _environment = environment_p;
    }

    /**
     * @see com.thalesgroup.orchestra.framework.model.IEnvironmentVariable.IEnvironment#getCategory()
     */
    public String getCategory() {
      return _environment.getEnvironmentCategory();
    }

    /**
     * @see com.thalesgroup.orchestra.framework.model.IEnvironmentVariable.IEnvironment#getId()
     */
    public String getId() {
      return _environment.getEnvironmentId();
    }

    /**
     * @see com.thalesgroup.orchestra.framework.model.IEnvironmentVariable.IEnvironment#getValues()
     */
    public List<IEnvironmentValue> getValues() {
      List<IEnvironmentValue> result = new ArrayList<IEnvironmentValue>(0);
      EnvironmentValueWS[] values = _environment.getValues();
      if (null != values) {
        for (EnvironmentValueWS environmentValueWS : values) {
          result.add(new EnvironmentValueAdapter(environmentValueWS));
        }
      }
      return result;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @SuppressWarnings("nls")
    @Override
    public String toString() {
      return new ToStringBuilder(this).append("category", getCategory()).append("id", getId()).append("values", getValues()).toString();
    }
  }

  /**
   * @author t0076261
   */
  protected class EnvironmentValueAdapter implements IEnvironmentValue {
    private EnvironmentValueWS _environmentValueWS;

    /**
     * Constructor.
     * @param environmentValueWS_p
     */
    protected EnvironmentValueAdapter(EnvironmentValueWS environmentValueWS_p) {
      _environmentValueWS = environmentValueWS_p;
    }

    /**
     * @see com.thalesgroup.orchestra.framework.model.IEnvironmentVariable.IEnvironmentValue#getKey()
     */
    public String getKey() {
      return _environmentValueWS.getKey();
    }

    /**
     * @see com.thalesgroup.orchestra.framework.model.IEnvironmentVariable.IEnvironmentValue#getValues()
     */
    public List<String> getValues() {
      return Arrays.asList(_environmentValueWS.getValues());
    }

    /**
     * @see java.lang.Object#toString()
     */
    @SuppressWarnings("nls")
    @Override
    public String toString() {
      return new ToStringBuilder(this).append("key", getKey()).append("values", getValues()).toString();
    }
  }
}