/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.validation.constraint.value;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;

import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.model.validation.constraint.AbstractConstraint;

/**
 * Cycles detection constraint.
 * @author t0076261
 */
public class CyclesConstraint extends AbstractConstraint<Context> {
  /**
   * Pattern instance matching ODM variables (group(1) -> variable path).
   */
  protected static final Pattern ODM_VARIABLE_REFERENCE_PATTERN = Pattern.compile("\\$\\{" + ModelUtil.VARIABLE_REFERENCE_TYPE_ODM + ":([^\\}]+)\\}"); //$NON-NLS-1$ //$NON-NLS-2$

  /**
   * @see com.thalesgroup.orchestra.framework.model.validation.constraint.AbstractConstraint#batchValidate(com.thalesgroup.orchestra.framework.model.contexts.ModelElement,
   *      org.eclipse.emf.validation.IValidationContext)
   */
  @Override
  protected IStatus batchValidate(Context target_p, IValidationContext context_p) {
    Context askingContext = getAskingContext();
    // Precondition.
    if (askingContext != target_p) {
      return null;
    }
    Collection<AbstractVariable> variables = DataUtil.getVariables(target_p);
    // Get variables that reference others.
    ArrayList<AbstractVariable> referencingVariables = new ArrayList<AbstractVariable>(0);
    // Cycle through variables.
    for (AbstractVariable variable : variables) {
      Collection<VariableValue> values = variable.getValues();
      // Then cycle through values, that are raw ones.
      for (VariableValue variableValue : values) {
        String value = variableValue.getValue();
        // If value contains the ODM prefix, then it is referencing another variable.
        if ((null != value) && value.contains(ModelUtil.getReferenceStringPrefix())) {
          referencingVariables.add(variable);
          break;
        }
      }
    }
    // Get cycling references.
    Collection<AbstractVariable> cyclingReferences = getCyclingReferences(referencingVariables, target_p);
    for (AbstractVariable abstractVariable : cyclingReferences) {
      addStatus(createFailureStatusWithDescription(abstractVariable, context_p, target_p.getName(), getFullPath(abstractVariable)));
    }
    return null;
  }

  /**
   * Get cycling references among specified set of referencing variables.
   * @param referencingVariables_p
   * @return A not <code>null</code> but possibly empty collection of {@link AbstractVariable} whose references are involved into cycles.
   */
  protected Collection<AbstractVariable> getCyclingReferences(ArrayList<AbstractVariable> referencingVariables_p, Context context_p) {
    Collection<AbstractVariable> result = new ArrayList<AbstractVariable>(0);
    int length = referencingVariables_p.size();
    List<Vertex> dependenceGraph = new ArrayList<Vertex>();
    HashMap<String, Vertex> listOfVertexAlreadyCreated = new HashMap<String, Vertex>();
    for (int i = 0; i < length; i++) {
      List<Vertex> references = getReferences(referencingVariables_p.get(i), context_p, listOfVertexAlreadyCreated);
      Vertex vertex;
      if (listOfVertexAlreadyCreated.containsKey(referencingVariables_p.get(i).getId())) {
        vertex = listOfVertexAlreadyCreated.get(referencingVariables_p.get(i).getId());
        vertex.Dependencies.addAll(references);
      } else {
        vertex = new Vertex(referencingVariables_p.get(i), references);
        listOfVertexAlreadyCreated.put(referencingVariables_p.get(i).getId(), vertex);
      }
      dependenceGraph.add(vertex);
    }
    StronglyConnectedComponentList resultingList = TarjanCycleDetect.detectCycle(dependenceGraph);
    for (StronglyConnectedComponent component : resultingList.cycles()) {
      for (Vertex vertex : component.list) {
        result.add(vertex.Value);
      }
    }
    return result;
  }

  /**
   * Get the list of referenced variable for specified (referencing) variable.<br>
   * This is a level-1 (shallow) decomposition.
   * @param variable_p
   * @return
   */
  protected List<Vertex> getReferences(AbstractVariable variable_p, Context context_p, HashMap<String, Vertex> listOfVertexAlreadyCreated_p) {
    List<Vertex> resultingList = new ArrayList<Vertex>(0);
    // Go through values.
    for (VariableValue variableValue : variable_p.getValues()) {
      String value = variableValue.getValue();
      // Precondition.
      if (null == value) {
        return null;
      }
      // Cycle on references.
      Matcher odmVariableReferenceMatcher = ODM_VARIABLE_REFERENCE_PATTERN.matcher(value);
      while (odmVariableReferenceMatcher.find()) {
        String odmVariablePath = odmVariableReferenceMatcher.group(1);
        // Work with result.
        AbstractVariable variable = DataUtil.getVariable(odmVariablePath, context_p);
        if (null != variable) {
          Vertex vertex;
          if (listOfVertexAlreadyCreated_p.containsKey(variable.getId())) {
            vertex = listOfVertexAlreadyCreated_p.get(variable.getId());
          } else {
            vertex = new Vertex(variable);
            listOfVertexAlreadyCreated_p.put(variable.getId(), vertex);
          }
          resultingList.add(vertex);
        }
      }
    }
    return resultingList;
  }

}