/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.view;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.DetailsPart;
import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IDetailsPageProvider;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.MasterDetailsBlock;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.framework.model.contexts.Category;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.validation.LiveValidationContentAdapter;
import com.thalesgroup.orchestra.framework.ui.internal.viewer.StructuredSelectionWithContext;
import com.thalesgroup.orchestra.framework.ui.viewer.ContextsViewer;
import com.thalesgroup.orchestra.framework.ui.viewer.VariablesViewContextsViewer;

/**
 * Variables block, composed of one tree on the master part, and one table on the details part.
 * @author t0076261
 */
public class VariablesBlock extends MasterDetailsBlock implements IDetailsPageProvider {
  /**
   * Sash left weight.
   */
  private static final String SASH_WEIGHT_LEFT = "sashLeftWeight"; //$NON-NLS-1$
  /**
   * Memento from container.<br>
   * Allows restoration of specific behaviors.
   */
  private IMemento _memento;
  /**
   * Shared actions provider.
   */
  protected ISharedActionsHandler _sharedActionsHandler;
  /**
   * Site.
   */
  private IWorkbenchPartSite _site;
  /**
   * The form toolkit reference.
   */
  private FormToolkit _toolkit;
  /**
   * Contexts viewer.
   */
  private ContextsViewer _viewer;

  /**
   * Constructor.
   * @param toolkit_p
   * @param memento_p
   * @param site_p
   * @param sharedActionsHandler_p
   */
  public VariablesBlock(FormToolkit toolkit_p, IMemento memento_p, IWorkbenchPartSite site_p, ISharedActionsHandler sharedActionsHandler_p) {
    _toolkit = toolkit_p;
    _memento = memento_p;
    _site = site_p;
    _sharedActionsHandler = sharedActionsHandler_p;
  }

  /**
   * @see org.eclipse.ui.forms.MasterDetailsBlock#createMasterPart(org.eclipse.ui.forms.IManagedForm, org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected void createMasterPart(final IManagedForm managedForm_p, Composite parent_p) {
    // Create contexts viewer.
    _viewer = new VariablesViewContextsViewer();
    _viewer.createViewer(_toolkit, parent_p, _site, _sharedActionsHandler);
    // Section part.
    final SectionPart sectionPart = new SectionPart(_viewer.getSection());
    managedForm_p.addPart(sectionPart);

    // Register selection changed listener.
    _viewer.getSelectionProvider().addSelectionChangedListener(new ISelectionChangedListener() {
      @SuppressWarnings({ "synthetic-access", "unchecked" })
      public void selectionChanged(SelectionChangedEvent event_p) {
        // Get selection.
        ISelection selection = event_p.getSelection();
        // Get current page.
        VariablesDetailsPage page = (VariablesDetailsPage) detailsPart.getCurrentPage();
        if (null != page) {
          // Get key for selected element.
          Couple<Context, Category> newKey = (Couple<Context, Category>) getPageKey(((IStructuredSelection) selection).getFirstElement());
          // Get key for previously selected element.
          Couple<Context, Category> oldKey = new Couple<Context, Category>(page._context, (Category) page._initialInput);
          // Make sure selection has changed before pre-disposing current page.
          if (!oldKey.equals(newKey)) {
            // Note that comparing categories directly is not sufficient for the default context is only loaded once in memory, and its categories are spread
            // within loaded contexts.
            page.preDispose();
          }
        }
        // Trigger page change.
        managedForm_p.fireSelectionChanged(sectionPart, selection);
        // Update live validation process.
        LiveValidationContentAdapter.getInstance().setValidationContext(_viewer.findContextForCurrentSelection());
      }
    });
    // Restore block specifics.
    restoreBlockValues(_memento);
    _memento = null;
  }

  /**
   * @see org.eclipse.ui.forms.MasterDetailsBlock#createToolBarActions(org.eclipse.ui.forms.IManagedForm)
   */
  @Override
  protected void createToolBarActions(IManagedForm managedForm_p) {

    // No contribution to the tool bar.
  }

  /**
   * @see org.eclipse.ui.forms.IDetailsPageProvider#getPage(java.lang.Object)
   */
  @SuppressWarnings("unchecked")
  public IDetailsPage getPage(Object key_p) {
    // Precondition.
    if (!(key_p instanceof Couple)) {
      return null;
    }
    Couple<Context, Category> couple = (Couple<Context, Category>) key_p;
    return new VariablesDetailsPage(couple.getValue(), couple.getKey(), _viewer.getPatternFilter(), _sharedActionsHandler);
  }

  /**
   * @see org.eclipse.ui.forms.IDetailsPageProvider#getPageKey(java.lang.Object)
   */
  public Object getPageKey(Object object_p) {
    Context contextForCurrentSelection = _viewer.findContextForCurrentSelection();
    // A page key is generated only if a category is selected in contextsViewer AND a context can be found for this category.
    if (object_p instanceof Category && null != contextForCurrentSelection) {
      return new Couple<Context, Category>(contextForCurrentSelection, (Category) object_p);
    }
    return null;
  }

  /**
   * Refresh current page.
   */
  public void refreshCurrentPage() {
    // Refresh details page.
    // Get associated details part, if any.
    VariablesDetailsPage page = (VariablesDetailsPage) detailsPart.getCurrentPage();
    // Stop here.
    if ((null == page) || page.isDisposed()) {
      return;
    }
    page.refreshPage();

  }

  /**
   * Refresh specified element within specified context.
   * @param element_p
   * @param context_p
   */
  public void refreshElement(Object element_p, Context context_p) {
    // Precondition.
    if ((null == element_p) || (null == context_p)) {
      return;
    }
    // Refresh contexts viewer only so far.
    // Refreshing variable details page per element induces really poor performances so far.
    _viewer.refreshElement(element_p, context_p);
  }

  /**
   * @see org.eclipse.ui.forms.MasterDetailsBlock#registerPages(org.eclipse.ui.forms.DetailsPart)
   */
  @Override
  protected void registerPages(DetailsPart detailsPart_p) {
    detailsPart_p.setPageLimit(0);
    detailsPart_p.setPageProvider(this);
  }

  /**
   * Restore block values.
   * @param memento_p
   */
  protected void restoreBlockValues(IMemento memento_p) {
    // Precondition.
    if (null == memento_p) {
      return;
    }
    // Master weight.
    Float masterWeight = memento_p.getFloat(SASH_WEIGHT_LEFT);
    if (null != masterWeight) {
      // int msWeight = (int) (100 * masterWeight.floatValue());
      // sashForm.setWeights(new int[] { msWeight, 100 - msWeight });
    }
  }

  /**
   * Save block values that should be restored at next launch.
   * @param memento_p
   */
  public void saveBlockValues(IMemento memento_p) {
    // Precondition.
    // Need a memento first.
    if (null == memento_p) {
      return;
    }
    // Save master weight.
    int[] weights = sashForm.getWeights();
    if ((null != weights) && (2 == weights.length)) {
      memento_p.putFloat(SASH_WEIGHT_LEFT, weights[0] * 1.0f / (weights[0] + weights[1]));
    }
  }

  /**
   * Set selection to specified object for specified context.
   * @param object_p
   * @param context_p
   */
  public void setSelection(Object object_p, Context context_p) {
    // Precondition.
    if ((null == object_p) || (null == context_p) || PlatformUI.getWorkbench().isClosing()) {
      return;
    }
    // Create selection for master part.
    StructuredSelectionWithContext selection = new StructuredSelectionWithContext(object_p);
    selection.setContext(context_p);
    // And set it.
    _viewer.getSelectionProvider().setSelection(selection);
    // Get associated details part, if any.
    VariablesDetailsPage page = (VariablesDetailsPage) detailsPart.getCurrentPage();
    // Stop here.
    if ((null == page) || page.isDisposed()) {
      return;
    }
    // Set selection to page.
    page.setSelection(selection);
  }
}