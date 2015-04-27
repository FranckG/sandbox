package com.thalesgroup.orchestra.framework.variablemanager.server.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.model.IVariable;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingVariable;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;

/** The simplest POJO that satisfies the {@link IVariable} interface. */
public class Variable implements IVariable {
  /**
   * Real variable implementation.
   */
  protected AbstractVariable _variable;

  /**
   * Create a variable from an abstract variable.
   * @param abstractVariable_p
   */
  public Variable(AbstractVariable abstractVariable_p) {
    _variable = abstractVariable_p;
  }

  /**
   * @see java.lang.Object#clone()
   */
  @Override
  public IVariable clone() throws CloneNotSupportedException {
    return new Variable(_variable);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IVariable#getDescription()
   */
  public String getDescription() {
    com.thalesgroup.orchestra.framework.model.contexts.Variable variable = null;
    if (_variable instanceof OverridingVariable) {
      variable = ((OverridingVariable) _variable).getOverriddenVariable();
    } else {
      variable = (com.thalesgroup.orchestra.framework.model.contexts.Variable) _variable;
    }
    return variable.getDescription();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IVariable#getName()
   */
  public String getName() {
    String name = ICommonConstants.EMPTY_STRING;
    if (null != _variable) {
      name = _variable.getName();
    }
    return name;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IVariable#getPath()
   */
  public String getPath() {
    return ModelUtil.getElementPath(_variable);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IVariable#getValues()
   */
  public List<String> getValues() {
    List<VariableValue> values = DataUtil.getValues(_variable);
    List<String> stringValues = new ArrayList<String>(values.size());
    for (VariableValue value : values) {
      stringValues.add(value.getValue());
    }
    return stringValues;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IVariable#isMultiValued()
   */
  public boolean isMultiValued() {
    return _variable.isMultiValued();
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return new ToStringBuilder(this).append("path", getPath()) //$NON-NLS-1$
        .append("values", getValues().toString()) //$NON-NLS-1$
        .append("description", getDescription()).toString(); //$NON-NLS-1$
  }
}
