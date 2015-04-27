/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.handler.data;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.CopyToClipboardCommand;
import org.eclipse.emf.edit.command.CutToClipboardCommand;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.command.PasteFromClipboardCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.thalesgroup.orchestra.framework.common.CommonActivator;
import com.thalesgroup.orchestra.framework.common.command.ResourcesNotSavedPseudoCommand;
import com.thalesgroup.orchestra.framework.model.IEditionContextProvider;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.contexts.ModelElement;
import com.thalesgroup.orchestra.framework.model.contexts.util.ContextsResourceImpl;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.command.DeleteCommandWithConfirmation;
import com.thalesgroup.orchestra.framework.model.handler.command.DeleteContextCommand;
import com.thalesgroup.orchestra.framework.model.handler.command.IClipboardContentChangedListener;
import com.thalesgroup.orchestra.framework.model.handler.command.ICommandUpdater;
import com.thalesgroup.orchestra.framework.model.handler.command.SynchronizeCommand;
import com.thalesgroup.orchestra.framework.model.handler.command.UnableToDeleteContextCommand;
import com.thalesgroup.orchestra.framework.model.handler.data.ContextsResourceSet.NotificationsListener;
import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.root.ui.AbstractRunnableWithDisplay;
import com.thalesgroup.orchestra.framework.root.ui.DisplayHelper;

/**
 * An editing domain specific to the Contexts M².<br>
 * It is adaptable to {@link IEditionContextProvider} for item providing purposes.<br>
 * It is also directly an {@link IEditionContextProvider}.
 * @author t0076261
 */
public class ContextsEditingDomain extends AdapterFactoryEditingDomain implements IAdaptable, IEditionContextProvider {
  /**
   * Command updater.<br>
   * Execution of commands defined in model.handler might have an impact on UI at some point (defined elsewhere).<br>
   * This is pure Inversion Of Control.
   */
  private ICommandUpdater _commandUpdater;
  /**
   * Edition context.
   */
  private Context _editionContext;
  /**
   * Should {@link DataHandler} be notified that a context resource has been saved ?<br>
   * Default value is true.
   */
  private volatile boolean _notifyHandlerOnResourceSaved;
  /**
   * Listeners on clipboard changes.
   */
  private final Collection<IClipboardContentChangedListener> clipboardContentChangeListeners;

  /**
   * Constructor.
   * @param adapterFactory_p
   */
  public ContextsEditingDomain(AdapterFactory adapterFactory_p) {
    super(adapterFactory_p, new ContextsCommandStack(), new ContextsResourceSet());
    getResourceSet().setEditingDomain(this);
    clipboard = new ArrayList<Object>(0);
    clipboardContentChangeListeners = new HashSet<IClipboardContentChangedListener>();
    notifyHandlerOnResourceSaved(true);
  }

  /**
   * Add a new listener which will be notified on clipboard changes.
   * @param clipboardContentChangedListener_p
   */
  public void addClipboardContentChangedListener(IClipboardContentChangedListener clipboardContentChangedListener_p) {
    clipboardContentChangeListeners.add(clipboardContentChangedListener_p);
  }

  /**
   * Check if a collection of contexts contains the current context.
   * @param contexts_p
   * @return <code>true</code> if passed collection contains current context, <code>false</code> otherwise.
   */
  public boolean containsCurrentContext(Collection<Context> contexts_p) {
    for (Context context : contexts_p) {
      if (ModelHandlerActivator.getDefault().getDataHandler().isCurrentContext(context)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Check if a collection of contexts contains the parent of the current user context.
   * @param contexts_p
   * @return <code>true</code> if passed collection contains the parent of the current user context, <code>false</code> otherwise.
   */
  public boolean containsParentOfCurrentUserContext(Collection<Context> contexts_p) {
    for (Context context : contexts_p) {
      Context userContext = ModelHandlerActivator.getDefault().getDataHandler().getUserContext(context);
      if (null != userContext && ModelHandlerActivator.getDefault().getDataHandler().isCurrentContext(userContext)) {
        return true;
      }
    }
    return false;
  }

  /**
   * @see org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain#createCommand(java.lang.Class, org.eclipse.emf.edit.command.CommandParameter)
   */
  @Override
  public Command createCommand(Class<? extends Command> commandClass_p, CommandParameter commandParameter_p) {
    Collection<?> selectedElements = commandParameter_p.getCollection();
    if (DeleteCommand.class.equals(commandClass_p)) {
      // Delete command.
      // No selection -> nothing to do.
      if (selectedElements.isEmpty()) {
        return UnexecutableCommand.INSTANCE;
      }
      // Check that list contains only contexts.
      Collection<Context> selectedContexts = EcoreUtil.getObjectsByType(selectedElements, ContextsPackage.Literals.CONTEXT);
      if (selectedElements.size() == selectedContexts.size()) {
        // Can not delete current context or admin context parent of current user context.
        if (containsCurrentContext(selectedContexts) || ProjectActivator.getInstance().isAdministrator()
            && containsParentOfCurrentUserContext(selectedContexts)) {
          return new UnableToDeleteContextCommand();
        }
        // Go for contexts deletion.
        return new DeleteContextCommand(selectedContexts);
      }
      // Check that list contains only model elements.
      Collection<ModelElement> selectedModelElements = EcoreUtil.getObjectsByType(selectedElements, ContextsPackage.Literals.MODEL_ELEMENT);
      if (selectedElements.size() == selectedModelElements.size()) {
        // Elements contained in a context, delete with confirmation first.
        return new DeleteCommandWithConfirmation(selectedModelElements, _editionContext);
      }
    } else if (CopyToClipboardCommand.class.equals(commandClass_p)) {
      // Replace with custom one.
      return new com.thalesgroup.orchestra.framework.model.handler.command.CopyToClipboardCommand(selectedElements, _editionContext);
    } else if (CutToClipboardCommand.class.equals(commandClass_p)) {
      // Replace with custom one.
      return new com.thalesgroup.orchestra.framework.model.handler.command.CutToClipboardCommand(selectedElements, _editionContext);
    } else if (PasteFromClipboardCommand.class.equals(commandClass_p)) {
      return new com.thalesgroup.orchestra.framework.model.handler.command.PasteFromClipboardCommand(Collections.singletonList(commandParameter_p.getOwner()),
          _editionContext);
    }
    return super.createCommand(commandClass_p, commandParameter_p);
  }

  /**
   * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
   */
  public Object getAdapter(Class adapter_p) {
    if (IEditionContextProvider.class.equals(adapter_p)) {
      return this;
    }
    return null;
  }

  /**
   * Get clipboard content resulting of the last cut/copy operation.
   * @return clipboard content.
   */
  public ClipboardElement getClipboardContent() {
    if (null != clipboard && !clipboard.isEmpty()) {
      Object clipboardContent = clipboard.iterator().next();
      if (clipboardContent instanceof ClipboardElement) {
        return (ClipboardElement) clipboardContent;
      }
    }
    return null;
  }

  /**
   * @see org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain#getCommandStack()
   */
  @Override
  public ContextsCommandStack getCommandStack() {
    return (ContextsCommandStack) super.getCommandStack();
  }

  /**
   * Get in-use command updater.
   * @return
   */
  public ICommandUpdater getCommandUpdater() {
    return _commandUpdater;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.IEditionContextProvider#getEditionContext()
   */
  public Context getEditionContext() {
    return _editionContext;
  }

  /**
   * Get first selected element in specified command parameter.
   * @param parameter_p
   * @return
   */
  protected Object getFirstSelectionFromParameter(CommandParameter parameter_p) {
    Collection<?> collection = parameter_p.getCollection();
    if ((null != collection) && (1 == collection.size())) {
      return collection.iterator().next();
    }
    return null;
  }

  /**
   * Get this editing domain model context from specified context.<br>
   * This is mainly useful when looking for currently modified version of the current context.
   * @param externalContext_p
   * @return <code>null</code> if specified context is <code>null</code> or this editing domain does not contain a model for this context.
   */
  public Context getLocalContextFrom(Context externalContext_p) {
    // Precondition.
    if (null == externalContext_p) {
      return null;
    }
    URI uri = externalContext_p.eResource().getURI();
    return ModelHandlerActivator.getDefault().getDataHandler().getContext(uri, getResourceSet());
  }

  /**
   * Get notifications listener.
   * @return
   */
  public NotificationsListener getNotificationsListener() {
    return getResourceSet().getListener();
  }

  /**
   * @see org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain#getResourceSet()
   */
  @Override
  public ContextsResourceSet getResourceSet() {
    return (ContextsResourceSet) super.getResourceSet();
  }

  /**
   * Is editing domain dirty ?
   * @return
   */
  public boolean isDirty() {
    return getCommandStack().isSaveNeeded();
  }

  /**
   * Check if an element is present in the clipboard and has been added by a cut operation.
   * @param context_p
   * @param element_p
   * @return
   */
  public boolean isElementCut(Context context_p, EObject element_p) {
    ClipboardElement clipboardElement = getClipboardContent();
    if (null != clipboardElement && clipboardElement._commandClass == com.thalesgroup.orchestra.framework.model.handler.command.CutToClipboardCommand.class
        && clipboardElement._context == context_p && clipboardElement._elements.contains(element_p)) {
      return true;
    }
    return false;
  }

  /**
   * Is specified resource a modified one ?
   * @param resource_p
   * @return <code>true</code> if resource is modified, <code>false</code> if not, or if it is <code>null</code>.
   */
  public boolean isModified(Resource resource_p) {
    return getCommandStack().isModified(resource_p);
  }

  /**
   * Should {@link DataHandler} be notified that a context resource has been saved ?<br>
   * By default, this value is set to <code>true</code> and should remain so, except for intrusive commands (such as {@link SynchronizeCommand}).<br>
   * If this value is set to <code>false</code>, this should be restored to <code>true</code> safely, and as soon as possible.
   * @param notify_p <code>true</code> to notify {@link DataHandler}, <code>false</code> to not notify {@link DataHandler}.
   */
  public void notifyHandlerOnResourceSaved(boolean notify_p) {
    _notifyHandlerOnResourceSaved = notify_p;
  }

  /**
   * Remove a listener from the list. Nothing is done if the listener to remove isn't present.
   * @param clipboardContentChangedListener_p
   */
  public void removeClipboardContentChangedListener(IClipboardContentChangedListener clipboardContentChangedListener_p) {
    clipboardContentChangeListeners.remove(clipboardContentChangedListener_p);
  }

  /**
   * Remove specified resource.
   * @param resource_p
   */
  public void removeResource(Resource resource_p) {
    // This is not a local resource, skip it.
    EList<Resource> resources = getResourceSet().getResources();
    if ((null == resource_p) || !(resources.contains(resource_p))) {
      return;
    }
    // Unload the resource.
    if (resource_p.isLoaded()) {
      // Mark resource as soon as possible, to avoid future events handling.
      if (resource_p instanceof ContextsResourceImpl) {
        ((ContextsResourceImpl) resource_p).markUnloading();
      }
      resource_p.unload();
    }
    // Remove it from resource set.
    resources.remove(resource_p);
  }

  /**
   * Save all dirty resources within this editing domain.
   */
  public IStatus save() {
    // Save resources.
    MultiStatus status = new MultiStatus(ModelHandlerActivator.getDefault().getPluginId(), 0, Messages.ContextsEditingDomain_Save_Problems, null);
    // Save them all.
    // Do a copy of the list of resources present in the ResourceSet to avoid ConcurrentModificationException.
    List<Resource> resourcesToSave = new ArrayList<Resource>(getResourceSet().getResources());
    // Contexts which saving process has succeeded.
    final List<Context> savedContexts = new ArrayList<Context>(0);
    // Resources which saving process has failed.
    List<Resource> resourcesFailedToSave = new ArrayList<Resource>(0);
    for (Resource resource : resourcesToSave) {
      // Ignore unmodified resources (and migration ecore2xml files).
      if (!isModified(resource)) {
        continue;
      }
      IStatus saveResourceResult = saveResource(resource, false);
      // Give data handler the opportunity to handle the context.
      if (saveResourceResult.isOK() && _notifyHandlerOnResourceSaved) {
        // Retain context so that it will be processed after the stack is flushed.
        Context context = ModelUtil.getContext(resource);
        // Ignore invalid resources.
        if (null == context) {
          continue;
        }
        // Do not take DefaultContext into account.
        if (!context.eResource().isDefault()) {
          savedContexts.add(context);
        }
      }
      // Compose status.
      status.add(saveResourceResult);
      // If save process failed, retain resource.
      if (IStatus.ERROR == saveResourceResult.getSeverity()) {
        resourcesFailedToSave.add(resource);
      }
    }
    // Flush command stack.
    getCommandStack().flush();
    // Some resources couldn't be saved -> add a pseudo command in the command stack to keep the dirty state.
    if (!resourcesFailedToSave.isEmpty()) {
      getCommandStack().execute(new ResourcesNotSavedPseudoCommand("Resources not saved", resourcesFailedToSave)); //$NON-NLS-1$
    }
    // Notify DataHandler that the following contexts have been saved.
    if (!savedContexts.isEmpty()) {
      // Post notification asynchronously.
      DisplayHelper.displayRunnable(new AbstractRunnableWithDisplay() {
        /**
         * @see java.lang.Runnable#run()
         */
        public void run() {
          // Cycle through saved contexts to notify DataHandler.
          for (Context context : savedContexts) {
            try {
              ModelHandlerActivator.getDefault().getDataHandler().contextSaved(context);
            } catch (Exception exception_p) {
              StringBuilder loggerMessage = new StringBuilder("ContextsEditingDomain.save(..) _ "); //$NON-NLS-1$
              CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.ERROR, exception_p);
            }
          }
        }
      }, true);
    }
    return status;
  }

  /**
   * Save specified resource.
   * @param resource_p
   * @param forceSave_p <code>true</code> to force save, even if nothing has been modified in underlying model. <code>false</code> to rely on underlying model
   *          modification detection.
   * @return an {@link IStatus}
   *         <ul>
   *         <li>with severity OK if the resource was successfully saved,</li>
   *         <li>with severity WARNING if the resource wasn't saved because it was <code>null</code>, not contained in the resource set of this editing domain
   *         or not modified,
   *         <li>with severity ERROR if an error occurred during the save operation.</li>
   *         </ul>
   */
  public IStatus saveResource(Resource resource_p, boolean forceSave_p) {
    // This is not a local resource, skip it.
    if ((null == resource_p) || !(getResourceSet().getResources().contains(resource_p))) {
      return new Status(IStatus.WARNING, ModelHandlerActivator.getDefault().getPluginId(), MessageFormat.format(
          Messages.ContextsEditingDomain_Save_Resource_Skipped_Warning, resource_p.getURI()));
    }
    // Test resource modification flag.
    if (forceSave_p || isModified(resource_p)) {
      try {
        resource_p.save(null);
      } catch (IOException exception_p) {
        StringBuilder loggerMessage = new StringBuilder("ContextsEditingDomain.save(..) _ "); //$NON-NLS-1$
        loggerMessage.append("Unable to save resource ").append(resource_p.getURI()); //$NON-NLS-1$
        CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.ERROR, exception_p);
        return new Status(IStatus.ERROR, ModelHandlerActivator.getDefault().getPluginId(), MessageFormat.format(
            Messages.ContextsEditingDomain_Save_Resource_Error, ModelUtil.getContext(resource_p).getName()), exception_p);
      }
    } else {
      return new Status(IStatus.WARNING, ModelHandlerActivator.getDefault().getPluginId(), MessageFormat.format(
          Messages.ContextsEditingDomain_Save_Resource_NotModified_Warning, resource_p.getURI()));
    }
    return Status.OK_STATUS;
  }

  /**
   * @see org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain#setClipboard(java.util.Collection)
   */
  @Override
  public void setClipboard(Collection<Object> clipboard_p) {
    // Precondition.
    if (null == clipboard_p) {
      return;
    }
    // Find the first object of type ClipboardElement and set it in the clipboard.
    for (Object clipboardElement : clipboard_p) {
      if (clipboardElement instanceof ClipboardElement) {
        setClipboardContent((ClipboardElement) clipboardElement);
        return;
      }
    }
    // No object of type ClipboardElement found, clear clipboard.
    setClipboardContent(null);
  }

  /**
   * Set specified element to clipboard and call listeners. <br>
   * If given element is <code>null</code> clipboard is cleared (if an element is present, it is removed and listeners are called). <br>
   * If given element is not <code>null</code> and clipboard is not empty, clipboard is cleared and listeners called then the new element is set to the
   * clipboard and listeners are called.
   * @param element_p
   */
  public void setClipboardContent(ClipboardElement element_p) {
    // Set a new content to the clipboard.
    if (null != element_p) {
      // Remove old content of the clipboard (if any).
      setClipboardContent(null);
      // Add new clipboard element and notify listeners.
      clipboard.add(element_p);
      for (IClipboardContentChangedListener listener : clipboardContentChangeListeners) {
        listener.clipboardElementAdded(element_p);
      }
    } else if (!clipboard.isEmpty()) {
      // Clear clipboard content and notify listeners.
      ClipboardElement elementToRemove = getClipboardContent();
      clipboard.clear();
      for (IClipboardContentChangedListener listener : clipboardContentChangeListeners) {
        listener.clipboardElementRemoved(elementToRemove);
      }
    }
  }

  /**
   * Set in-use command updater.
   * @param commandUpdater_p
   */
  public void setCommandUpdater(ICommandUpdater commandUpdater_p) {
    _commandUpdater = commandUpdater_p;
  }

  /**
   * Set specified context as current edition one.
   * @param context_p
   */
  public void setEditionContext(Context context_p) {
    _editionContext = context_p;
  }

  /**
   * A clip-board element.<br>
   * Be advised that only elements inheriting from this class will be taken into account.<br>
   * Elements pushed as a simple {@link EditingDomain} will be ignored by specific methods and commands.
   * @author t0076261
   */
  public static class ClipboardElement {
    /**
     * The command type that was used to push the elements on the clipboard.
     */
    public Class<? extends Command> _commandClass;
    /**
     * The context environment, which the elements was pushed for.<br>
     * This does not necessarily translate to the destination one.
     */
    public Context _context;
    /**
     * The elements themselves.
     */
    public Set<EObject> _elements;

    public ClipboardElement(Class<? extends Command> commandClass_p, Context context_p, Collection<EObject> elements_p) {
      _commandClass = commandClass_p;
      _context = context_p;
      _elements = new HashSet<EObject>(elements_p);
    }

    /**
     * @return <code>true</code> if this Clipboard element doesn't contain any element or if some of its attributes are <code>null</code>, <code>false</code>
     *         otherwise.
     */
    public boolean isEmpty() {
      return null == _commandClass || null == _context || null == _elements || _elements.isEmpty();
    }
  }
}