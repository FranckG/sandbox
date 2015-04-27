/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.view;

import java.text.MessageFormat;
import java.util.EventObject;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.edit.ui.action.CopyAction;
import org.eclipse.emf.edit.ui.action.CutAction;
import org.eclipse.emf.edit.ui.action.DeleteAction;
import org.eclipse.emf.edit.ui.action.RedoAction;
import org.eclipse.emf.edit.ui.action.UndoAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.ISaveablePart;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.ViewPart;

import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.util.ContextsResourceImpl;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.data.ContextsEditingDomain;
import com.thalesgroup.orchestra.framework.model.handler.data.DataHandler;
import com.thalesgroup.orchestra.framework.model.handler.data.ICurrentContextChangeListener;
import com.thalesgroup.orchestra.framework.root.ui.AbstractRunnableWithDisplay;
import com.thalesgroup.orchestra.framework.root.ui.DisplayHelper;
import com.thalesgroup.orchestra.framework.ui.action.CustomPasteAction;
import com.thalesgroup.orchestra.framework.ui.activator.OrchestraFrameworkUiActivator;
import com.thalesgroup.orchestra.framework.ui.internal.viewer.ModelElementFilter;
import com.thalesgroup.orchestra.framework.ui.internal.viewer.ModelElementFilter.FilterState;

/**
 * Variables management view.
 * @author t0076261
 */
public class VariablesView extends ViewPart implements ISaveablePart, ISharedActionsHandler, ICurrentContextChangeListener {
  /**
   * Shared instance.
   */
  private static VariablesView __instance;
  /**
   * Variables master/details block.
   */
  private VariablesBlock _block;
  /**
   * Parent form.
   */
  private ScrolledForm _container;
  /**
   * Copy action.
   */
  protected CopyAction _copyAction;
  /**
   * Cut action.
   */
  protected CutAction _cutAction;
  /**
   * Delete action.
   */
  protected DeleteAction _deleteAction;
  /**
   * Memento reminder.
   */
  private IMemento _memento;
  /**
   * Model element filter.
   */
  protected final ModelElementFilter _modelElementFilter;
  /**
   * Paste action.
   */
  protected CustomPasteAction _pasteAction;
  /**
   * Redo action.
   */
  protected RedoAction _redoAction;
  /**
   * Save action.
   */
  private IAction _saveAction;
  /**
   * Undo action.
   */
  protected UndoAction _undoAction;
  /**
   * Saved context on save action
   */
  protected Context _savedContext;
  /**
   * Display of the composite
   */
  protected Display _display;

  /**
   * Default constructor.
   */
  public VariablesView() {
    __instance = this;
    // Initialize model element filter (by default, nothing is filtered).
    FilterState initialFilterState = new FilterState(false, false, false);
    _modelElementFilter = new ModelElementFilter(initialFilterState);

    DataHandler dataHandler = ModelHandlerActivator.getDefault().getDataHandler();
    // Register framework implementations.
    dataHandler.addCurrentContextChangeListener(this);
  }

  /**
   * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
   */
  @Override
  public void createPartControl(Composite parent_p) {
    // Create a new form toolkit.
    _display = parent_p.getDisplay();
    FormToolkit toolkit = new FormToolkit(_display);
    // Scrolled form.
    _container = toolkit.createScrolledForm(parent_p);
    // Get sub composite.
    Composite content = _container.getBody();
    content.setLayoutData(new GridData(GridData.FILL_BOTH));
    content.setLayout(new GridLayout());
    _block = new VariablesBlock(toolkit, _memento, getSite(), this);
    _block.createContent(new ManagedForm(toolkit, _container));
    // Register dirty listener.
    ModelHandlerActivator.getDefault().getEditingDomain().getCommandStack().addCommandStackListener(new DirtyListener());
  }

  /**
   * @see org.eclipse.ui.ISaveablePart#doSave(org.eclipse.core.runtime.IProgressMonitor)
   */
  public void doSave(IProgressMonitor monitor_p) {
    // Save current context to be saved
    DataHandler dataHandler = ModelHandlerActivator.getDefault().getDataHandler();
    _savedContext = ModelHandlerActivator.getDefault().getEditingDomain().getLocalContextFrom(dataHandler.getCurrentContext());

    ContextsEditingDomain editingDomain = ModelHandlerActivator.getDefault().getEditingDomain();
    IStatus saveStatus = editingDomain.save();
    if (!saveStatus.isOK()) {
      // A problem occurred during save, show an error dialog.
      MultiStatus displayStatus =
          new MultiStatus(OrchestraFrameworkUiActivator.getDefault().getPluginId(), 0, MessageFormat.format(Messages.VariablesView_ContextSaveError_Reason,
              IDialogConstants.SHOW_DETAILS_LABEL), null);
      displayStatus.add(saveStatus);
      DisplayHelper.displayErrorDialog(Messages.VariablesView_ContextSaveError_Title, Messages.VariablesView_ContextSaveError_Message, displayStatus);
      // Report operation failure using the given progress monitor (if any).
      if (null != monitor_p) {
        monitor_p.setCanceled(true);
      }
    }
  }

  /**
   * @see org.eclipse.ui.ISaveablePart#doSaveAs()
   */
  public void doSaveAs() {
    // This is not an option so far.
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.view.ISharedActionsHandler#getCopyAction()
   */
  public CopyAction getCopyAction() {
    return _copyAction;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.view.ISharedActionsHandler#getCutAction()
   */
  public CutAction getCutAction() {
    return _cutAction;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.view.ISharedActionsHandler#getDeleteAction()
   */
  @Override
  public DeleteAction getDeleteAction() {
    return _deleteAction;
  }

  /**
   * Return the model element filter.
   * @return
   */
  public ModelElementFilter getModelElementFilter() {
    return _modelElementFilter;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.view.ISharedActionsHandler#getPasteAction()
   */
  public CustomPasteAction getPasteAction() {
    return _pasteAction;
  }

  /**
   * @see org.eclipse.ui.part.ViewPart#init(org.eclipse.ui.IViewSite, org.eclipse.ui.IMemento)
   */
  @Override
  public void init(IViewSite site_p, IMemento memento_p) throws PartInitException {
    super.init(site_p, memento_p);
    _memento = memento_p;
    IActionBars actionBars = site_p.getActionBars();
    // Add save action.
    _saveAction = ActionFactory.SAVE.create(site_p.getWorkbenchWindow());
    _saveAction.setEnabled(false);
    actionBars.setGlobalActionHandler(ActionFactory.SAVE.getId(), _saveAction);
    // Undo action.
    ContextsEditingDomain editingDomain = ModelHandlerActivator.getDefault().getEditingDomain();
    _undoAction = new UndoAction(editingDomain);
    actionBars.setGlobalActionHandler(ActionFactory.UNDO.getId(), _undoAction);
    // Redo action.
    _redoAction = new RedoAction(editingDomain);
    actionBars.setGlobalActionHandler(ActionFactory.REDO.getId(), _redoAction);
    // Register command stack listener.
    editingDomain.getCommandStack().addCommandStackListener(new CommandStackListener() {
      public void commandStackChanged(EventObject event_p) {
        _undoAction.update();
        _redoAction.update();
      }
    });

    ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
    // Cut action.
    _cutAction = new CutAction(editingDomain);
    _cutAction.setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_CUT));
    _cutAction.setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_CUT_DISABLED));
    actionBars.setGlobalActionHandler(ActionFactory.CUT.getId(), _cutAction);
    // Copy action.
    _copyAction = new CopyAction(editingDomain);
    _copyAction.setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
    _copyAction.setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_COPY_DISABLED));
    actionBars.setGlobalActionHandler(ActionFactory.COPY.getId(), _copyAction);
    // Paste action.
    _pasteAction = new CustomPasteAction(editingDomain);
    _pasteAction.setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));
    _pasteAction.setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE_DISABLED));
    actionBars.setGlobalActionHandler(ActionFactory.PASTE.getId(), _pasteAction);
    // Delete action.
    _deleteAction = new DeleteAction(editingDomain, true);
    _deleteAction.setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
    _deleteAction.setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE_DISABLED));
    actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(), _deleteAction);
  }

  /**
   * @see org.eclipse.ui.ISaveablePart#isDirty()
   */
  public boolean isDirty() {
    return ModelHandlerActivator.getDefault().getEditingDomain().isDirty();
  }

  /**
   * @see org.eclipse.ui.ISaveablePart#isSaveAsAllowed()
   */
  public boolean isSaveAsAllowed() {
    return false;
  }

  /**
   * @see org.eclipse.ui.ISaveablePart#isSaveOnCloseNeeded()
   */
  public boolean isSaveOnCloseNeeded() {
    return isDirty();
  }

  /**
   * Refresh specified element/hierarchy within specified context.
   * @param object_p
   * @param context_p
   */
  public void refreshElement(Object element_p, Context context_p) {
    _block.refreshElement(element_p, context_p);
  }

  /**
   * Refresh variables part.
   */
  public void refreshVariables() {
    _block.refreshCurrentPage();
  }

  /**
   * @see org.eclipse.ui.part.ViewPart#saveState(org.eclipse.ui.IMemento)
   */
  @Override
  public void saveState(IMemento memento_p) {
    super.saveState(memento_p);
    _block.saveBlockValues(memento_p);
  }

  /**
   * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
   */
  @Override
  public void setFocus() {
    _container.setFocus();
  }

  /**
   * Set selection for given element.<br>
   * This might need to select both the master and details parts.
   * @param object_p
   */
  public void setSelection(Object object_p, Context context_p) {
    _block.setSelection(object_p, context_p);
  }

  /**
   * Get shared instance.
   * @return
   */
  public static VariablesView getSharedInstance() {
    return __instance;
  }

  /**
   * Model dirty listener.<br>
   * Allows for the modification of the dirty state of this view.
   * @author t0076261
   */
  protected class DirtyListener implements CommandStackListener {
    /**
     * @see org.eclipse.emf.common.command.CommandStackListener#commandStackChanged(java.util.EventObject)
     */
    @SuppressWarnings("synthetic-access")
    public void commandStackChanged(EventObject event_p) {
      DisplayHelper.displayRunnable(new AbstractRunnableWithDisplay() {
        public void run() {
          firePropertyChange(PROP_DIRTY);
        }
      }, true);
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.handler.data.ICurrentContextChangeListener#contextChanged(com.thalesgroup.orchestra.framework.model.contexts.Context,
   *      org.eclipse.core.runtime.IStatus, org.eclipse.core.runtime.IProgressMonitor, boolean)
   */
  public IStatus contextChanged(Context currentContext_p, IStatus errorStatus_p, IProgressMonitor progressMonitor_p, boolean allowUserInteractions_p) {
    // On save action only
    if (null != _savedContext) {
      ContextsResourceImpl resource = currentContext_p.eResource();
      boolean isDefaultContext = (null != resource) && resource.isDefault();
      // If new context is default context
      if (isDefaultContext) {
        _display.asyncExec(new Runnable() {
          public void run() {
            // Refresh saved context
            refreshElement(_savedContext, _savedContext);
            // Reset save action
            _savedContext = null;
          }
        });
      }
    }
    return Status.OK_STATUS;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.handler.data.ICurrentContextChangeListener#preContextChange(com.thalesgroup.orchestra.framework.model.contexts.Context,
   *      org.eclipse.core.runtime.IProgressMonitor, boolean)
   */
  public void preContextChange(Context futureContext_p, IProgressMonitor progressMonitor_p, boolean allowUserInteractions_p) {
    // Nothing to do
  }
}