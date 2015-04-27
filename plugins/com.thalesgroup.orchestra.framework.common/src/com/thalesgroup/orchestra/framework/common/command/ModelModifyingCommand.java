/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.common.command;

import org.eclipse.emf.ecore.change.util.ChangeRecorder;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * A command that modifies the model structure.<br>
 * It is (undo/redo)able, in that it reverses the model contents according to modifications done in the
 * @author t0076261
 */
public class ModelModifyingCommand extends ChangeDescriptionCommand {
  /**
   * Editing domain.
   */
  protected EditingDomain _editingDomain;

  /**
   * Constructor.
   * @param label_p
   * @param editingDomain_p
   */
  public ModelModifyingCommand(String label_p, EditingDomain editingDomain_p) {
    super(label_p);
    _editingDomain = editingDomain_p;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.common.command.ChangeDescriptionCommand#canUndo()
   */
  @Override
  public boolean canUndo() {
    return true;
  }

  /**
   * Do execute this command.
   */
  protected void doExecute() {
    // Default implementation does nothing !
  }

  /**
   * @see com.thalesgroup.orchestra.framework.common.command.ChangeDescriptionCommand#execute()
   */
  @Override
  public final void execute() {
    ChangeRecorder recorder = startRecording();
    // Precondition.
    if (null == recorder) {
      return;
    }
    try {
      doExecute();
    } catch (Exception exception_p) {
      System.out.println(exception_p);
      exception_p.printStackTrace();
    } finally {
      setDescription(recorder.endRecording());
      // Dispose the ChangeRecorder (very important : it removes it from ContextsResourceSet.eAdapters).
      recorder.dispose();
      // Keep trace of resources modified by this command.
      setModifiedResources(generateModifiedResources());
    }
  }

  /**
   * Start recording model modifications, before executing command.
   * @return
   */
  protected ChangeRecorder startRecording() {
    return new ChangeRecorder(_editingDomain.getResourceSet());
  }
}