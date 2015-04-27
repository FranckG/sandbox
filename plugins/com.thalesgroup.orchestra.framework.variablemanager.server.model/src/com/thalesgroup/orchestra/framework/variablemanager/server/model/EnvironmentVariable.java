/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.variablemanager.server.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;

import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.framework.environment.EnvironmentActivator;
import com.thalesgroup.orchestra.framework.environment.EnvironmentContext;
import com.thalesgroup.orchestra.framework.environment.IEnvironmentHandler;
import com.thalesgroup.orchestra.framework.environment.registry.EnvironmentRegistry.EnvironmentDescriptor;
import com.thalesgroup.orchestra.framework.environment.ui.AbstractVariablesHandler;
import com.thalesgroup.orchestra.framework.model.IEnvironmentVariable;
import com.thalesgroup.orchestra.framework.model.IVariable;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;

/**
 * The simplest POJO that satisfies the {@link IEnvironmentVariable} interface.
 * @author t0076261
 */
public class EnvironmentVariable extends Variable implements IEnvironmentVariable {
  /**
   * Constructor.
   * @param abstractVariable_p
   */
  public EnvironmentVariable(AbstractVariable abstractVariable_p) {
    super(abstractVariable_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.variablemanager.server.model.Variable#clone()
   */
  @Override
  public IVariable clone() throws CloneNotSupportedException {
    return new EnvironmentVariable(_variable);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IEnvironmentVariable#getEnvironments()
   */
  public List<IEnvironment> getEnvironments() {
    List<VariableValue> values = DataUtil.getValues(_variable);
    List<IEnvironment> result = new ArrayList<IEnvironment>(0);
    EnvironmentActivator.getInstance().setVariablesHandler(new VariablesHandler());
    try {
      for (VariableValue value : values) {
        // Skip unexpected content.
        if (!(value instanceof EnvironmentVariableValue)) {
          continue;
        }
        EnvironmentVariableValue environmentVariableValue = (EnvironmentVariableValue) value;
        Couple<IStatus, EnvironmentDescriptor> environmentCouple =
            EnvironmentActivator.getInstance().getEnvironmentRegistry().getEnvironmentDescriptor(environmentVariableValue.getEnvironmentId());
        // Environment is not supported.
        if (!environmentCouple.getKey().isOK()) {
          continue;
        }
        // Get to descriptor.
        EnvironmentDescriptor environmentDescriptor = environmentCouple.getValue();
        IEnvironmentHandler handler = environmentDescriptor.getHandler();
        // Create environment POJO.
        Map<String, String> environmentVariableValues = ModelUtil.convertEnvironmentVariableValues(environmentVariableValue);
        String stringRepresentation = handler.toString(environmentVariableValues);
        Environment environment = new Environment(stringRepresentation, environmentDescriptor.getCategory());
        EnvironmentContext environmentContext = new EnvironmentContext();
        environmentContext._attributes = environmentVariableValues;
        Map<String, List<String>> expandedAttributes = handler.getExpandedAttributes(environmentContext);
        for (Map.Entry<String, List<String>> entry : expandedAttributes.entrySet()) {
          // Create value POJO.
          EnvironmentValue environmentValue = new EnvironmentValue(entry.getKey());
          environmentValue._values.addAll(entry.getValue());
          environment._values.add(environmentValue);
        }
        result.add(environment);
      }
    } finally {
      EnvironmentActivator.getInstance().setVariablesHandler(null);
    }
    return result;
  }

  /**
   * The simplest POJO that satisfies the {@link IEnvironment} interface.
   * @author t0076261
   */
  public class Environment implements IEnvironment {
    private String _category;
    private String _id;
    protected List<IEnvironmentValue> _values;

    public Environment(String id_p, String category_p) {
      _id = id_p;
      _category = category_p;
      _values = new ArrayList<IEnvironmentValue>(0);
    }

    /**
     * @see com.thalesgroup.orchestra.framework.model.IEnvironmentVariable.IEnvironment#getCategory()
     */
    public String getCategory() {
      return _category;
    }

    /**
     * @see com.thalesgroup.orchestra.framework.model.IEnvironmentVariable.IEnvironment#getId()
     */
    public String getId() {
      return _id;
    }

    /**
     * @see com.thalesgroup.orchestra.framework.model.IEnvironmentVariable.IEnvironment#getValues()
     */
    public List<IEnvironmentValue> getValues() {
      return _values;
    }
  }

  /**
   * The simplest POJO that satisfies the {@link IEnvironmenValue} interface.
   * @author t0076261
   */
  public class EnvironmentValue implements IEnvironmentValue {
    private String _key;
    protected List<String> _values;

    /**
     * Constructor.
     * @param key_p
     */
    public EnvironmentValue(String key_p) {
      _key = key_p;
      _values = new ArrayList<String>(0);
    }

    /**
     * @see com.thalesgroup.orchestra.framework.model.IEnvironmentVariable.IEnvironmentValue#getKey()
     */
    public String getKey() {
      return _key;
    }

    /**
     * @see com.thalesgroup.orchestra.framework.model.IEnvironmentVariable.IEnvironmentValue#getValues()
     */
    public List<String> getValues() {
      return _values;
    }
  }

  /**
   * Variables handler for environment.
   * @author t0076261
   */
  protected class VariablesHandler extends AbstractVariablesHandler {
    /**
     * @see com.thalesgroup.orchestra.framework.environment.ui.IVariablesHandler#getSubstitutedValue(java.lang.String)
     */
    @Override
    public String getSubstitutedValue(String rawValue_p) {
      return DataUtil.getSubstitutedValue(rawValue_p, ModelHandlerActivator.getDefault().getDataHandler().getCurrentContext());
    }
  }
}