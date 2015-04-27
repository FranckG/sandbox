/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.view;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.emf.edit.ui.action.CommandActionHandler;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;

import com.thalesgroup.orchestra.framework.common.CommonActivator;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.command.ICommandUpdater;

/**
 * Shared actions updater.<br>
 * Provide with update mechanism on a per action basis.<br>
 * Default implementation relies on the providing of a viewer as an updater.<br>
 * Actions are then updated on the viewer selection either when a selection occurs or when focus is gained.
 * @author t0076261
 */
public class SharedActionsUpdater implements ICommandUpdater, FocusListener, ISelectionChangedListener {
  /**
   * Shared actions handler.
   */
  protected ISharedActionsHandler _actionsHandler;
  /**
   * Are all actions forced to disabled.
   */
  protected boolean _disabledForced;
  /**
   * Update listeners.
   */
  protected ListenerList _listeners;

  /**
   * Selection provider.
   */
  protected ISelectionProvider _selectionProvider;

  /**
   * Constructor.
   * @param actionsHandler_p A not <code>null</code> implementation of {@link ISharedActionsHandler}.
   */
  public SharedActionsUpdater(ISharedActionsHandler actionsHandler_p, ISelectionProvider selectionProvider_p) {
    _actionsHandler = actionsHandler_p;
    _selectionProvider = selectionProvider_p;
    // Actions handler can not be null !
    Assert.isNotNull(_actionsHandler);
    // By default, actions are not forced to disabled.
    _disabledForced = false;
    _listeners = new ListenerList();
    initialize();
  }

  /**
   * Dispose update mechanisms.<br>
   * Note that the updater does not necessarily need to be unreferenced.<br>
   * It can be re-initialized through the {@link #initialize()} method.
   */
  public void dispose() {
    // Remove actions as update listeners.
    _listeners.remove(_actionsHandler.getCutAction());
    _listeners.remove(_actionsHandler.getCopyAction());
    _listeners.remove(_actionsHandler.getDeleteAction());
    _listeners.remove(_actionsHandler.getPasteAction());
  }

  /**
   * @see org.eclipse.swt.events.FocusListener#focusGained(org.eclipse.swt.events.FocusEvent)
   */
  public void focusGained(FocusEvent focusEvent_p) {
    // Declare updater as command updater too.
    ModelHandlerActivator.getDefault().getEditingDomain().setCommandUpdater(SharedActionsUpdater.this);
    // Force update for actions.
    forceUpdate();
  }

  /**
   * @see org.eclipse.swt.events.FocusListener#focusLost(org.eclipse.swt.events.FocusEvent)
   */
  public void focusLost(FocusEvent focusEvent_p) {
    // Nothing to do on focusLost
  }

  /**
   * Force update of all (registered) shared actions.<br>
   * @see com.thalesgroup.orchestra.framework.model.handler.command.ICommandUpdater#forceUpdate()
   */
  public void forceUpdate() {
    // Loop through registered listeners (ie shared actions).
    for (Object object : _listeners.getListeners()) {
      try {
        IAction action = IAction.class.cast(object);
        CommandActionHandler handler = CommandActionHandler.class.cast(object);
        // Force to disabled or ask command.
        handler.setEnabled(!_disabledForced && handler.updateSelection(getSelectionFor(action)));
      } catch (RuntimeException e_p) {
        StringBuilder loggerMessage = new StringBuilder("SharedActionsUpdater.forceUpdate() _ "); //$NON-NLS-1$
        loggerMessage.append("An exception occurs when updating selection for action : ").append(object); //$NON-NLS-1$
        CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.WARNING, e_p);
        // Make sure this does not stop the event handling.
      }
    }
  }

  /**
   * Get shared actions handler.
   * @return
   */
  public ISharedActionsHandler getHandler() {
    return _actionsHandler;
  }

  /**
   * Get selection for specified action.<br>
   * By default, this implementation is based on the viewer selection at call time.
   * @param action_p
   * @return
   */
  protected IStructuredSelection getSelectionFor(IAction action_p) {
    if (null != _selectionProvider) {
      return (IStructuredSelection) _selectionProvider.getSelection();
    }
    return null;
  }

  /**
   * Initialize update mechanisms.<br>
   * This is up to the caller to decide when the updater it to be initialized.<br>
   * This is not done automatically at creation time.
   */
  public void initialize() {
    // Add actions as update listeners.
    _listeners.add(_actionsHandler.getCutAction());
    _listeners.add(_actionsHandler.getCopyAction());
    _listeners.add(_actionsHandler.getDeleteAction());
    _listeners.add(_actionsHandler.getPasteAction());
  }

  /**
   * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
   */
  public void selectionChanged(SelectionChangedEvent event_p) {
    // Nothing to do if actions are forced to disabled.
    if (_disabledForced) {
      return;
    }
    // Loop through registered listeners (ie shared actions).
    for (Object object : _listeners.getListeners()) {
      try {
        IAction action = IAction.class.cast(object);
        ISelectionChangedListener listener = ISelectionChangedListener.class.cast(object);
        SelectionChangedEvent event = new SelectionChangedEvent(event_p.getSelectionProvider(), getSelectionFor(action));
        listener.selectionChanged(event);
      } catch (RuntimeException e_p) {
        StringBuilder loggerMessage = new StringBuilder("SharedActionsUpdater.selectionChanged(...) _ "); //$NON-NLS-1$
        loggerMessage.append("An exception occurs when changing selection for action : ").append(object); //$NON-NLS-1$
        CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.WARNING, e_p);
        // Make sure this does not stop the event handling.
      }
    }
  }

  /**
   * When set to <code>true</code>, force all actions to disabled state ignoring future events.
   * @param disabledForced_p
   */
  public void setDisabledForced(boolean disabledForced_p) {
    _disabledForced = disabledForced_p;
    forceUpdate();
  }
}