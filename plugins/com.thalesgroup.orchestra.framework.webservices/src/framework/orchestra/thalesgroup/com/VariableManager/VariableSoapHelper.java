/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package framework.orchestra.thalesgroup.com.VariableManager;

import java.util.ArrayList;
import java.util.List;

import com.thalesgroup.orchestra.framework.model.IEnvironmentVariable;
import com.thalesgroup.orchestra.framework.model.IEnvironmentVariable.IEnvironment;
import com.thalesgroup.orchestra.framework.model.IEnvironmentVariable.IEnvironmentValue;
import com.thalesgroup.orchestra.framework.model.IVariable;

/**
 * Provide methods to convert from {@link IVariable} to {@link VariableWS}
 * @author s0011584
 */
public class VariableSoapHelper {
  /**
   * Convert the {@link IVariable} list to a {@link VariableWS} array.
   */
  public static VariableWS[] iVariablesToVariableWSs(List<IVariable> allVariables_p) {
    List<VariableWS> variables = new ArrayList<VariableWS>();
    for (IVariable iVariable : allVariables_p) {
      variables.add(iVariableToVariableWS(iVariable));
    }
    return variables.toArray(new VariableWS[variables.size()]);
  }

  /**
   * Convert an {@link IVariable} to a {@link VariableWS}.
   */
  public static VariableWS iVariableToVariableWS(IVariable variable_p) {
    // Shared attributes.
    String description = variable_p.getDescription();
    boolean isMultiValued = variable_p.isMultiValued();
    String name = variable_p.getName();
    String path = variable_p.getPath();
    // Specific case for environment variables.
    if (variable_p instanceof IEnvironmentVariable) {
      IEnvironmentVariable environmentVariable = (IEnvironmentVariable) variable_p;
      List<EnvironmentWS> environments = new ArrayList<EnvironmentWS>(0);
      List<IEnvironment> environmentsList = environmentVariable.getEnvironments();
      for (IEnvironment iEnvironment : environmentsList) {
        List<EnvironmentValueWS> environmentValues = new ArrayList<EnvironmentValueWS>(0);
        for (IEnvironmentValue environmentValue : iEnvironment.getValues()) {
          List<String> values = environmentValue.getValues();
          environmentValues.add(new EnvironmentValueWS(environmentValue.getKey(), values.toArray(new String[values.size()])));
        }
        environments.add(new EnvironmentWS(iEnvironment.getId(), iEnvironment.getCategory(), environmentValues.toArray(new EnvironmentValueWS[environmentValues
            .size()])));
      }
      return new EnvironmentVariableWS(description, isMultiValued, name, path, new String[0], environments.toArray(new EnvironmentWS[environments.size()]));
    }
    // Variable case.
    List<String> values = variable_p.getValues();
    return new VariableWS(description, isMultiValued, name, path, values.toArray(new String[values.size()]));
  }
}