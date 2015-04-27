/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.handler.command;

import java.util.Collection;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.AbstractCommand.NonDirtying;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.data.ContextsEditingDomain;
import com.thalesgroup.orchestra.framework.model.handler.data.ContextsEditingDomain.ClipboardElement;
import com.thalesgroup.orchestra.framework.project.ProjectActivator;

/**
 * Copy to clipboard command.<br>
 * This command is not undoable.<br>
 * It does no harm to the existing model.
 * @author t0076261
 */
public class CopyToClipboardCommand extends AbstractCommand implements NonDirtying {
  /**
   * Edition context.<br>
   * That is the context which holds the responsibility at the time of selection.
   */
  protected Context _editionContext;
  /**
   * Selected elements.
   */
  protected Collection<EObject> _filteredSelectedModelElements;

  /**
   * Constructor.
   * @param selectedElements_p
   * @param context_p
   */
  @SuppressWarnings("unchecked")
  public CopyToClipboardCommand(Collection<?> selectedElements_p, Context context_p) {
    super(Messages.CopyToClipboardCommand_Label, Messages.CopyToClipboardCommand_Description);
    // Remove nested elements from selection to fill _selectedModelElements.
    Collection<EObject> selectedModelElements = ((Collection<EObject>) selectedElements_p);
    _filteredSelectedModelElements = EcoreUtil.filterDescendants(selectedModelElements);
    _editionContext = context_p;
  }

  /**
   * @see org.eclipse.emf.common.command.AbstractCommand#canUndo()
   */
  @Override
  public boolean canUndo() {
    return false;
  }

  /**
   * @see org.eclipse.emf.common.command.Command#execute()
   */
  public void execute() {
    ContextsEditingDomain domain = ModelHandlerActivator.getDefault().getEditingDomain();
    try {
      // Set clipboard content with copied elements.
      ClipboardElement clipboardElement = new ClipboardElement(this.getClass(), _editionContext, _filteredSelectedModelElements);
      domain.setClipboardContent(clipboardElement);
    } finally {
      // Force update for coupled commands/actions.
      ICommandUpdater commandUpdater = domain.getCommandUpdater();
      if (null != commandUpdater) {
        commandUpdater.forceUpdate();
      }
    }
  }

  /**
   * @see org.eclipse.emf.common.command.AbstractCommand#prepare()
   */
  @Override
  protected boolean prepare() {
    // Selection is empty, copy is impossible.
    if (_filteredSelectedModelElements.isEmpty()) {
      return false;
    }
    // No edition context is set, copy is impossible.
    if (null == _editionContext) {
      return false;
    }
    // Is it a context copy ?
    Collection<EObject> selectedContexts = EcoreUtil.getObjectsByType(_filteredSelectedModelElements, ContextsPackage.Literals.CONTEXT);
    // Check only contexts are selected.
    if (_filteredSelectedModelElements.size() == selectedContexts.size()) {
      // Only copy is allowed, not cut !
      if (CutToClipboardCommand.class.equals(this.getClass())) {
        return false;
      }
      // Context copy is allowed only in admin mode.
      if (!ProjectActivator.getInstance().isAdministrator()) {
        return false;
      }
      // Only one context can be copied.
      if (1 != selectedContexts.size()) {
        return false;
      }
      return true;
    }
    // Categories or variables are copied.
    boolean result = true;
    for (EObject selectedElement : _filteredSelectedModelElements) {
      // By construction, the list contains only ModelElements. Disable copy if the selected elements list contains a context, a "pending elements" category or
      // a variable value.
      result &=
          !ContextsPackage.Literals.CONTEXT.isInstance(selectedElement) && !ContextsPackage.Literals.PENDING_ELEMENTS_CATEGORY.isInstance(selectedElement)
              && !ContextsPackage.Literals.VARIABLE_VALUE.isInstance(selectedElement)
              && !ContextsPackage.Literals.ENVIRONMENT_VARIABLE.isInstance(selectedElement);
      // Stop search here.
      if (!result) {
        break;
      }
    }
    return result;
  }

  /**
   * @see org.eclipse.emf.common.command.Command#redo()
   */
  public void redo() {
    // Do nothing.
  }
}