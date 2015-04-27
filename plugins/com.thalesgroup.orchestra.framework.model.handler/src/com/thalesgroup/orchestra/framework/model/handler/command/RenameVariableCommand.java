/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.handler.command;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.ecore.change.util.ChangeRecorder;

import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.data.ContextsEditingDomain;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.model.handler.helper.RenameVariableHelper;

/**
 * @author s0040806
 */
public class RenameVariableCommand extends AbstractCommand {

  protected String _odmVariable;
  protected String _newOdmVariable;
  protected String _name;

  protected Variable _variable;
  protected String _variablePath;
  protected Context _context;

  protected Set<Context> _inheritedContexts;
  protected Set<Context> _skippedContexts;
  protected Set<AbstractVariable> _referencingVariables;
  protected Set<AbstractVariable> _overridingReferencingVariables;
  protected Set<AbstractVariable> _skippedVariables;
  private RenameVariableHelper _renameHelper;

  public RenameVariableCommand(Variable variable_p, Context context_p, String name_p) {
    _variable = variable_p;
    _variablePath = ModelUtil.getElementPath(variable_p);
    _context = context_p;
    _name = name_p;
    _odmVariable = "${" + ModelUtil.VARIABLE_REFERENCE_TYPE_ODM + ":" + _variablePath + "}"; //$NON-NLS-1$
    _newOdmVariable = "${" + ModelUtil.VARIABLE_REFERENCE_TYPE_ODM + ":" + _variablePath.replaceFirst("([^\\\\]*)$", name_p) + "}"; //$NON-NLS-1$

    _renameHelper = new RenameVariableHelper(variable_p, context_p);
    _overridingReferencingVariables = _renameHelper.getOverridingReferencingVariables();
    _inheritedContexts = _renameHelper.getInheritedContexts();
  }

  /**
   * Rename variable in all inherited contexts in order to avoid synchronising
   */
  private void renameVariableInContexts() {
    for (Context context : _inheritedContexts) {
      AbstractVariable variable = DataUtil.getVariable(_variablePath, context);
      variable.setName(_name);
    }
  }

  private void renameVariableReferences() {
	for (AbstractVariable variable : _overridingReferencingVariables) {
      List<VariableValue> values = variable.getValues();
      for (VariableValue value : values) {
        value.setValue(value.getValue().replace(_odmVariable, _newOdmVariable));
      }
    }
  }

  /**
   * @see org.eclipse.emf.common.command.Command#execute()
   */
  public void execute() {
    ContextsEditingDomain editingDomain = ModelHandlerActivator.getDefault().getEditingDomain();
    ChangeRecorder recorder = new ChangeRecorder(editingDomain.getResourceSet());
    renameVariableInContexts();
    renameVariableReferences();
    ChangeDescription change = recorder.endRecording();
    recorder.dispose();
  }

  /**
   * @see org.eclipse.emf.common.command.Command#redo()
   */
  public void redo() {
    // TODO Auto-generated method stub

  }

  /**
   * @see org.eclipse.emf.common.command.AbstractCommand#prepare()
   */
  @Override
  protected boolean prepare() {
    return true;
  }

}
