/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.view;

import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.internal.views.markers.ExtendedMarkersView;
import org.eclipse.ui.part.EditorPart;

import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.ui.internal.validation.UIValidationHelper;

/**
 * An hidden editor associated to {@link Context} model file resources.<br>
 * It does only handle the {@link IGotoMarker} implementation since a view can't do that as of Eclipse 3.5.<br>
 * Life-cycle is as follows :<br>
 * -> The editor is automatically created by {@link ExtendedMarkersView} when trying to open the marker in an editor.<br>
 * -> At construction time, it only tells the shell not to redraw itself anymore.<br>
 * -> Then the {@link IGotoMarker} implementation is triggered. This one jumps to the unique {@link VariablesView} instance, and ask for selection of the marker
 * content.<br>
 * -> Then, asynchronously, the editor is closed and redraw on shell is restored.
 * @author t0076261
 */
public class HiddenMarkerHandlerEditor extends EditorPart implements IGotoMarker {
  /**
   * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
   */
  @Override
  public void createPartControl(Composite parent_p) {
    // Disable drawing.
    getSite().getShell().setRedraw(false);
  }

  /**
   * @see org.eclipse.ui.part.EditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  public void doSave(IProgressMonitor monitor_p) {
    // Nothing to do here.
  }

  /**
   * @see org.eclipse.ui.part.EditorPart#doSaveAs()
   */
  @Override
  public void doSaveAs() {
    // Nothing to do here.
  }

  /**
   * @see org.eclipse.ui.ide.IGotoMarker#gotoMarker(org.eclipse.core.resources.IMarker)
   */
  public void gotoMarker(IMarker marker_p) {
    Map<Object, Object> decomposition = UIValidationHelper.getInstance().decomposeMarker(marker_p);
    if (null != decomposition) {
      setSelectionToView(decomposition.get(EObject.class), (Context) decomposition.get(Context.class));
    }
  }

  /**
   * @see org.eclipse.ui.part.EditorPart#init(org.eclipse.ui.IEditorSite, org.eclipse.ui.IEditorInput)
   */
  @Override
  public void init(IEditorSite site_p, IEditorInput input_p) throws PartInitException {
    setSite(site_p);
    setInput(input_p);
  }

  /**
   * @see org.eclipse.ui.part.EditorPart#isDirty()
   */
  @Override
  public boolean isDirty() {
    // Nothing to do here.
    return false;
  }

  /**
   * @see org.eclipse.ui.part.EditorPart#isSaveAsAllowed()
   */
  @Override
  public boolean isSaveAsAllowed() {
    // Nothing to do here.
    return false;
  }

  /**
   * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
   */
  @Override
  public void setFocus() {
    // Nothing to do here.
  }

  /**
   * Set selection to variables view.
   * @param object_p
   */
  protected void setSelectionToView(Object object_p, Context context_p) {
    VariablesView view = VariablesView.getSharedInstance();
    // Precondition.
    if (null == view) {
      return;
    }
    // Do handle selection.
    view.setSelection(object_p, context_p);
    // Asynchronously close this editor.
    PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
      public void run() {
        // Get site.
        IWorkbenchPartSite site = getSite();
        // Get page.
        IWorkbenchPage page = site.getPage();
        // Close editor.
        page.closeEditor(HiddenMarkerHandlerEditor.this, false);
        // Hide editor area.
        page.setEditorAreaVisible(false);
        // Re-enable drawing.
        site.getShell().setRedraw(true);
      }
    });
  }
}