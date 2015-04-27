/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.handler.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ModelElement;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.data.ContextsEditingDomain;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper.LayoutType;

/**
 * {@link DeleteCommand} with confirmation dialog.<br>
 * So far, the confirmation displays impacted contexts, if any.
 * @author t0076261
 */
public class DeleteCommandWithConfirmation extends DeleteCommand {
  /**
   * Constructor.
   * @param collection_p
   * @param editionContext_p
   */
  public DeleteCommandWithConfirmation(Collection<ModelElement> collection_p, Context editionContext_p) {
    super(collection_p, editionContext_p);
  }

  /**
   * Add specified element context to resulting contexts list.<br>
   * It is added only if it is not already contained in specified list.
   * @param element_p
   * @param resultingContexts_p
   */
  protected void addContext(EObject element_p, List<Context> resultingContexts_p) {
    Context context = ModelUtil.getContext(element_p);
    if ((null != context) && !resultingContexts_p.contains(context)) {
      resultingContexts_p.add(context);
    }
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.handler.command.DeleteCommand#confirmDeletion(java.util.Collection, java.util.Map)
   */
  @Override
  protected boolean confirmDeletion(Collection<EObject> deletedElements_p, Map<EObject, Collection<Setting>> usages_p) {
    final List<Context> impactedContexts = new ArrayList<Context>(0);
    for (Map.Entry<EObject, Collection<EStructuralFeature.Setting>> entry : usages_p.entrySet()) {
      addContext(entry.getKey(), impactedContexts);
      for (EStructuralFeature.Setting setting : entry.getValue()) {
        addContext(setting.getEObject(), impactedContexts);
      }
    }
    final Display display = PlatformUI.getWorkbench().getDisplay();
    final int dialogResult[] = new int[] { 0 };
    display.syncExec(new Runnable() {
      /**
       * @see java.lang.Runnable#run()
       */
      public void run() {
        MessageDialog dialog =
            new MessageDialog(display.getActiveShell(), getDialogTitle(), null, Messages.DeleteCommandWithConfirmation_ConfirmationDialog_Question,
                MessageDialog.QUESTION, new String[] { IDialogConstants.YES_LABEL, IDialogConstants.NO_LABEL }, 0) {
              /**
               * @see org.eclipse.jface.dialogs.MessageDialog#createCustomArea(org.eclipse.swt.widgets.Composite)
               */
              @Override
              protected Control createCustomArea(Composite parent_p) {
                if (impactedContexts.isEmpty()) {
                  return parent_p;
                }
                final FormToolkit toolkit = new FormToolkit(parent_p.getDisplay());
                toolkit.setBackground(parent_p.getBackground());
                parent_p.addDisposeListener(new DisposeListener() {
                  public void widgetDisposed(DisposeEvent e_p) {
                    toolkit.dispose();
                  }
                });
                return createCustomConfirmationArea(toolkit, parent_p, impactedContexts);
              }

              /**
               * @see org.eclipse.jface.dialogs.Dialog#isResizable()
               */
              @Override
              protected boolean isResizable() {
                return true;
              }
            };
        dialogResult[0] = dialog.open();
      }
    });
    return (0 == dialogResult[0]);
  }

  /**
   * Create custom confirmation area.
   * @param parent_p
   * @param impactedContexts_p
   * @return
   */
  protected Composite createCustomConfirmationArea(final FormToolkit toolkit_p, Composite parent_p, final List<Context> impactedContexts_p) {
    Composite composite = FormHelper.createCompositeWithLayoutType(toolkit_p, parent_p, LayoutType.GRID_LAYOUT, 1, false);
    // Add label.
    toolkit_p.createLabel(composite, Messages.DeleteCommandWithConfirmation_ConfirmationDialog_Text);
    // Add list viewer.
    final TreeViewer viewer = new TreeViewer(composite, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
    Control control = viewer.getControl();
    FormHelper.updateControlLayoutDataWithLayoutTypeData(control, LayoutType.GRID_LAYOUT);
    GridData layoutData = (GridData) control.getLayoutData();
    layoutData.heightHint = 250;
    viewer.addFilter(new ViewerFilter() {
      /**
       * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
       */
      @Override
      public boolean select(Viewer viewer_p, Object parentElement_p, Object element_p) {
        return (element_p instanceof Context);
      }
    });
    ContextsEditingDomain editingDomain = ModelHandlerActivator.getDefault().getEditingDomain();
    viewer.setLabelProvider(new AdapterFactoryLabelProvider(editingDomain.getAdapterFactory()));
    viewer.setContentProvider(new AdapterFactoryContentProvider(editingDomain.getAdapterFactory()) {
      @Override
      public Object[] getElements(Object inputElement_p) {
        if (inputElement_p == impactedContexts_p) {
          return impactedContexts_p.toArray();
        }
        return null;
      }
    });
    viewer.setInput(impactedContexts_p);
    // Add dispose listener.
    composite.addDisposeListener(new DisposeListener() {
      public void widgetDisposed(DisposeEvent e_p) {
        viewer.getContentProvider().dispose();
        viewer.getLabelProvider().dispose();
      }
    });
    return composite;
  }

  /**
   * The dialog title is retrieved with this method.
   * @return the dialog title.
   */
  protected String getDialogTitle() {
    return Messages.DeleteCommandWithConfirmation_ConfirmationDialog_Title;
  }
}