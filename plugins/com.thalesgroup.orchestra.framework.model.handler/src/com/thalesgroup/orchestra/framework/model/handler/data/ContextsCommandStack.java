/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.handler.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.AbortExecutionException;
import org.eclipse.emf.common.command.AbstractCommand.NonDirtying;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.resource.Resource;

import com.thalesgroup.orchestra.framework.common.command.AbstractResourcesModifyingCommand;
import com.thalesgroup.orchestra.framework.common.command.ModelModifyingCommand;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;

/**
 * A command stack that makes sure not {@link NonDirtying} commands are monitoring their model changes.<br>
 * It then allows to detect if a resource was indeed changed based on existing commands pointing to this resource.
 * @author t0076261
 */
public class ContextsCommandStack extends BasicCommandStack {
  /**
   * Do execute specified command.<br>
   * Code is a copy from {@link BasicCommandStack#execute(Command)} with a slight modification after command execution.
   * @param command
   */
  protected void doExecute(Command command) {
    // If the command is executable, record and execute it.
    //
    if (command != null) {
      if (command.canExecute()) {
        try {
          command.execute();

          // Framework : Modified code starts here.
          if (command instanceof AbstractResourcesModifyingCommand) {
            // Command is supposed to modify the model, yet no modification was performed.
            // Do not add it a the top of the stack, thus avoiding save trigger issues.
            if (((AbstractResourcesModifyingCommand) command).getModifiedResources().isEmpty()) {
              return;
            }
          }
          // Framework : Modified code ends here.

          // Clear the list past the top.
          //
          for (Iterator<Command> commands = commandList.listIterator(top + 1); commands.hasNext(); commands.remove()) {
            Command otherCommand = commands.next();
            otherCommand.dispose();
          }

          // Record the successfully executed command.
          //
          mostRecentCommand = command;
          commandList.add(command);
          ++top;

          // This is kind of tricky.
          // If the saveIndex was in the redo part of the command list which has now been wiped out,
          // then we can never reach a point where a save is not necessary, not even if we undo all the way back to the beginning.
          //
          if (saveIndex >= top) {
            // This forces isSaveNeded to always be true.
            //
            saveIndex = -2;
          }
          notifyListeners();
        } catch (AbortExecutionException exception) {
          command.dispose();
        } catch (RuntimeException exception) {
          handleError(exception);
          mostRecentCommand = null;
          command.dispose();
          notifyListeners();
        }
      } else {
        command.dispose();
      }
    }
  }

  /**
   * @see org.eclipse.emf.common.command.BasicCommandStack#execute(org.eclipse.emf.common.command.Command)
   */
  @Override
  public void execute(Command command_p) {
    // Ignore null command (if no selection has been for now, CommandActionHandlers can contain a null command).
    if (null != command_p) {
      Command commandToExecute = command_p;
      // Make sure modifying commands are monitored.
      if (!(commandToExecute instanceof NonDirtying)) {
        // If the command is not already monitoring its changes on resources, make sure it will.
        if (!(commandToExecute instanceof AbstractResourcesModifyingCommand)) {
          commandToExecute = new DelegateModelModifyingCommand(command_p);
        }
      }
      // Do execute command.
      doExecute(commandToExecute);
    }
  }

  /**
   * Check whether specified resource is modified by a command currently on the command stack.
   * @param resource_p
   * @return <code>true</code> if so, <code>false</code> otherwise, or if the resource is <code>null</code>.
   */
  public boolean isModified(Resource resource_p) {
    // Precondition.
    if (null == resource_p) {
      return false;
    }
    // Clone existing commands list.
    List<Command> commands = new ArrayList<Command>(commandList);
    // Cycle through commands up to top (hence ignore undone commands).
    // This implementation suits the fact that the command stack is flushed at save time.
    // If not, then top and saveIndex should be used instead.
    for (int i = 0; i <= top; ++i) {
      // Do only take into account model modifying commands.
      if (commands.get(i) instanceof AbstractResourcesModifyingCommand) {
        AbstractResourcesModifyingCommand changeDescriptionCommand = (AbstractResourcesModifyingCommand) commands.get(i);
        if (changeDescriptionCommand.getModifiedResources().contains(resource_p)) {
          // Found a command that modifies specified resource.
          // Stop here.
          return true;
        }
      }
    }
    return false;
  }

  /**
   * A {@link ModelModifyingCommand} that delegates execution and other services to a specified command.
   * @author t0076261
   */
  protected class DelegateModelModifyingCommand extends ModelModifyingCommand {
    /**
     * The delegating command to invoke.
     */
    private Command _command;

    /**
     * Constructor.
     * @param command_p the real command to execute. Can't be <code>null</code>.
     */
    public DelegateModelModifyingCommand(Command command_p) {
      super(command_p.getLabel(), ModelHandlerActivator.getDefault().getEditingDomain());
      _command = command_p;
    }

    /**
     * @see org.eclipse.emf.common.command.AbstractCommand#canExecute()
     */
    @Override
    public boolean canExecute() {
      return _command.canExecute();
    }

    /**
     * @see com.thalesgroup.orchestra.framework.common.command.ChangeDescriptionCommand#dispose()
     */
    @Override
    public void dispose() {
      // Dispose real command first.
      try {
        _command.dispose();
      } finally {
        // Then dispose current one.
        super.dispose();
      }
    }

    /**
     * @see com.thalesgroup.orchestra.framework.common.command.ModelModifyingCommand#doExecute()
     */
    @Override
    protected void doExecute() {
      _command.execute();
    }
  }
}