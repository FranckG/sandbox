/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.handler.helper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;

import com.thalesgroup.orchestra.framework.contextsproject.ContextsProject;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.data.DataHandler;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.model.handler.data.RootElement;
import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;

/**
 * Compute impacted contexts and variables by variable renaming
 * @author s0040806
 */
public class RenameVariableHelper {

  private String _odmVariable;

  private Context _context;
  /*
   * Absolute path of the context
   */
  private Path _contextPath;

  /**
   * Impacted contexts
   */
  protected Set<Context> _referencingContexts = new HashSet<Context>();

  /**
   * Inherited contexts
   */
  private Set<Context> _inheritedContexts = new HashSet<Context>();

  /**
   * Impacted variables
   */
  private Set<AbstractVariable> _referencingVariables = new HashSet<AbstractVariable>();

  /**
   * Overriding Impacted variables
   */
  private Set<AbstractVariable> _overridingReferencingVariables = new HashSet<AbstractVariable>();

  /**
   * Workspace contexts
   */
  private List<Context> _workspaceContexts;

  public RenameVariableHelper(Variable variable_p, Context context_p) {
    _context = context_p;
    _odmVariable = "${" + ModelUtil.VARIABLE_REFERENCE_TYPE_ODM + ":" + ModelUtil.getElementPath(variable_p) + "}"; //$NON-NLS-1$
    _workspaceContexts = getWorkspaceContextList();
    _contextPath = Paths.get(getContextsProject(_context).getLocation());
    findReferencingVariables();
  }

  /**
   * Find all variables referencing the searched variable within a context
   * @param context_p Context
   */
  private void findReferencingVariablesInContext(Context context_p) {
    int nbOverridingReferencingVariables = _overridingReferencingVariables.size();

    Collection<AbstractVariable> variables = DataUtil.getVariables(context_p);
    // Cycle through variables.
    for (AbstractVariable variable : variables) {
      // Lookup variable even if it was overriden in order to be consistant with
      // parent context
      Collection<VariableValue> values = variable.getValues();
      // Then cycle through values, that are raw ones.
      for (VariableValue variableValue : values) {
        String value = variableValue.getValue();
        if (null != value && value.contains(_odmVariable)) {
          _referencingVariables.add(variable);
          _overridingReferencingVariables.add(variable);
          break;
        }
      }

      // Lookup overriding variable if variable was overriden
      OverridingVariable overridingVariable = DataUtil.getOverridingVariable(variable, context_p);
      if (null != overridingVariable) {
        values = overridingVariable.getValues();
        boolean isReferencingVariable = false;
        // Then cycle through values, that are raw ones.
        for (VariableValue variableValue : values) {
          String value = variableValue.getValue();
          if (null != value && value.contains(_odmVariable)) {
            _referencingVariables.add(variable);
            _overridingReferencingVariables.add(overridingVariable);
            isReferencingVariable = true;
            break;
          }
        }
        // If overriding variable does not reference the variable
        // remove it from referencing variables
        if (!isReferencingVariable) {
          _referencingVariables.remove(variable);
        }
      }
    }
    // if at least one referencing variable was added, add current context
    // to referencing contexts
    if (_overridingReferencingVariables.size() != nbOverridingReferencingVariables) {
      _referencingContexts.add(context_p);
    }
  }

  /**
   * Check whether two paths point to the same object on disk
   * @param path1_p
   * @param path2_p
   * @return
   */
  private boolean arePathsEqual(Path path1_p, Path path2_p) {
    try {
      return Files.isSameFile(path1_p, path2_p);
    } catch (IOException exception_p) {
      return false;
    }
  }

  /**
   * Find if a context is inherited from the context where the search variable is located
   * @param context_p
   * @return
   */
  private boolean isInheritedContext(Context referencingContext_p, Context context_p, String parentPath_p) {
    if (context_p.getId().equals(_context.getId())) {
      if (null == parentPath_p || arePathsEqual(Paths.get(parentPath_p), _contextPath)) {
        _inheritedContexts.add(referencingContext_p);
      }
      return true;
    }
    Context parentContext = getParentContext(context_p);
    if (null == parentContext) {
      return false;
    }

    ContextsProject p = getContextsProject(context_p)._contextsProject;
    return isInheritedContext(referencingContext_p, parentContext, p.getParentProject());
  }

  /**
   * Find referencing variable within the workspace
   */
  private void findReferencingVariables() {
    if (ProjectActivator.getInstance().isAdministrator()) {
      for (Context context : _workspaceContexts) {
        if (isInheritedContext(context, context, null)) {
          findReferencingVariablesInContext(context);
        }
      }
    } else {
      _inheritedContexts.add(_context);
      _referencingContexts.add(_context);
      findReferencingVariablesInContext(_context);
    }
  }

  /**
   * Get all contexts from workspace
   * @return
   */
  private static List<Context> getWorkspaceContextList() {
    DataHandler dataHandler = ModelHandlerActivator.getDefault().getDataHandler();
    RootElement rootElement = dataHandler.getAllContextsInWorkspace();
    return rootElement.getContexts();
  }

  /**
   * Get root context project of a given context
   * @param context_p
   * @return
   */
  private RootContextsProject getContextsProject(Context context_p) {
    URI uri = context_p.eResource().getURI();
    RootContextsProject projectFromContextUri = ProjectActivator.getInstance().getProjectHandler().getProjectFromContextUri(uri);
    return projectFromContextUri;
  }

  /**
   * Get parent context from context list
   * @param context_p Context
   * @return Parent context, <code>null</code> if parent context is default context
   */
  private Context getParentContext(Context context_p) {
    // Get parent from context project
    Context parentFromProject = context_p.getSuperContext();

    // Parent context is default context
    if (parentFromProject.eResource().isDefault()) {
      return null;
    }

    // Get context from the context list matching the same context Id
    Context parent = getContextById(parentFromProject.getId());

    return parent;
  }

  /**
   * Get a context from a context list, by context Id
   * @param contextId_p
   * @param contextList_p
   * @return Context if found, <code>null</code>Q otherwise
   */
  private Context getContextById(String contextId_p) {
    for (Context context : _workspaceContexts) {
      if (context.getId().equals(contextId_p)) {
        return context;
      }
    }
    return null;
  }

  /**
   * @return the Impacted contexts
   */
  public Set<Context> getReferencingContexts() {
    return _referencingContexts;
  }

  /**
   * @return the referencingVariables
   */
  public Set<AbstractVariable> getReferencingVariables() {
    return _referencingVariables;
  }

  /**
   * @return the Overriding Impacted variables
   */
  public Set<AbstractVariable> getOverridingReferencingVariables() {
    return _overridingReferencingVariables;
  }

  /**
   * @return the Inherited context
   */
  public Set<Context> getInheritedContexts() {
    return _inheritedContexts;
  }
}