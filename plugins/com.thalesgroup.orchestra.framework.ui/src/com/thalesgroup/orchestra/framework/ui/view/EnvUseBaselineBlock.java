/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.view;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.DetailsPart;
import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IDetailsPageProvider;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.MasterDetailsBlock;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.environment.UseBaselineEnvironmentContext;
import com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariableValue;
import com.thalesgroup.orchestra.framework.ui.dialog.ChooseBaselineDialog.BaselineSelectionType;
import com.thalesgroup.orchestra.framework.ui.internal.viewer.UseBaselineEnvironmentVariablesViewer;
import com.thalesgroup.orchestra.framework.ui.wizard.NewUseBaselineContextWizard.UseBaselineContextsModel;

/**
 * Master/Details block used to configure master StartingPointContexts.
 * @author T0052089
 */
public class EnvUseBaselineBlock extends MasterDetailsBlock implements IDetailsPageProvider {
  /**
   * TreeViewer displaying EnvironmentVariables and EnvironmentVariableValues for master UseBaselineContexts.
   */
  private UseBaselineEnvironmentVariablesViewer _viewer;
  /**
   * The form toolkit reference.
   */
  private FormToolkit _toolkit;
  /**
   * The starting point context model.
   */
  private UseBaselineContextsModel _useBaselineContextsModel;
  /**
   * WizardContainer reference used to update wizard buttons state.
   */
  private IWizardContainer _wizardContainer;
  /**
   * Baseline type
   */
  private BaselineSelectionType _baselineSelection;

  /**
   * Constructor.
   * @param formToolkit_p
   * @param wizardContainer_p
   */
  public EnvUseBaselineBlock(FormToolkit formToolkit_p, IWizardContainer wizardContainer_p, BaselineSelectionType baselineSelection_p) {
    _toolkit = formToolkit_p;
    _wizardContainer = wizardContainer_p;
    _baselineSelection = baselineSelection_p;
  }

  /**
   * @see org.eclipse.ui.forms.MasterDetailsBlock#createMasterPart(org.eclipse.ui.forms.IManagedForm, org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected void createMasterPart(final IManagedForm managedForm_p, Composite parent_p) {
    // Create section.
    final SectionPart masterPart = new SectionPart(parent_p, _toolkit, ExpandableComposite.TITLE_BAR | ExpandableComposite.EXPANDED);
    masterPart.getSection().setText(Messages.EnvUseBaselineBlock_StartingPointMasterSectionTitle);
    _viewer = new UseBaselineEnvironmentVariablesViewer();
    // Create viewer.
    Control viewerControl = _viewer.createViewer(masterPart.getSection());
    _viewer.addSelectionChangedListener(new ISelectionChangedListener() {
      /**
       * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
       */
      public void selectionChanged(SelectionChangedEvent event_p) {
        // Get selection.
        ISelection selection = event_p.getSelection();
        managedForm_p.fireSelectionChanged(masterPart, selection);
      }
    });

    masterPart.getSection().setClient(viewerControl);
    managedForm_p.addPart(masterPart);
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
  public IDetailsPage getPage(Object key_p) {
    if (key_p instanceof UseBaselinePageKey) {
      return new EnvUseBaselineDetailsPage(_toolkit, ((UseBaselinePageKey) key_p)._useBaselineContext, _useBaselineContextsModel, _wizardContainer,
          _baselineSelection);
    }
    return null;
  }

  /**
   * If an EnvironmentVariableValue is given, returns its StartingPointContext.<br>
   * Else, returns null.
   * @see org.eclipse.ui.forms.IDetailsPageProvider#getPageKey(java.lang.Object)
   */
  public Object getPageKey(Object object_p) {
    if (object_p instanceof EnvironmentVariableValue) {
      // A new key is created at each call to force a recreation of the details page event if the StartingPointContext has to be displayed.
      return new UseBaselinePageKey(_useBaselineContextsModel.getEnvValueToUseBaselineContext().get(object_p));
    }
    return null;
  }

  /**
   * @see org.eclipse.ui.forms.MasterDetailsBlock#registerPages(org.eclipse.ui.forms.DetailsPart)
   */
  @Override
  protected void registerPages(DetailsPart detailsPart_p) {
    detailsPart_p.setPageLimit(0);
    detailsPart_p.setPageProvider(this);
  }

  public void setInput(UseBaselineContextsModel useBaselineContextsModel_p) {
    _useBaselineContextsModel = useBaselineContextsModel_p;
    _viewer.setInput(useBaselineContextsModel_p);

  }

  /**
   * Objects of this class are used as page keys.
   * @author T0052089
   */
  protected class UseBaselinePageKey {
    /**
     * The starting point context to display in the details page.
     */
    protected final UseBaselineEnvironmentContext _useBaselineContext;

    /**
     * Constructor.
     * @param useBaselineContext_p
     */
    protected UseBaselinePageKey(UseBaselineEnvironmentContext useBaselineContext_p) {
      _useBaselineContext = useBaselineContext_p;
    }
  }

}
