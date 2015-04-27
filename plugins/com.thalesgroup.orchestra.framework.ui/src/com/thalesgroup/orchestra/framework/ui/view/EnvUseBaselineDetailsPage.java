/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.view;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.forms.AbstractFormPart;
import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IFormPart;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.framework.environment.EnvironmentActivator;
import com.thalesgroup.orchestra.framework.environment.IEnvironmentHandler;
import com.thalesgroup.orchestra.framework.environment.IEnvironmentUseBaselineHandler;
import com.thalesgroup.orchestra.framework.environment.UseBaselineEnvironmentContext;
import com.thalesgroup.orchestra.framework.environment.UseBaselineType;
import com.thalesgroup.orchestra.framework.environment.ui.AbstractEnvironmentUseBaselineViewer;
import com.thalesgroup.orchestra.framework.model.IEditionContextProvider;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.root.ui.forms.FormHelper;
import com.thalesgroup.orchestra.framework.ui.dialog.ChooseBaselineDialog.BaselineSelectionType;
import com.thalesgroup.orchestra.framework.ui.internal.viewer.UseBaselineEnvironmentVariablesViewer;
import com.thalesgroup.orchestra.framework.ui.wizard.NewUseBaselineContextWizard.UseBaselineContextsModel;

/**
 * @author T0052089
 */
public class EnvUseBaselineDetailsPage extends AbstractFormPart implements IDetailsPage {
  /**
   * The environment starting point viewer implementation.
   */
  private AbstractEnvironmentUseBaselineViewer _environmentStartingPointViewer;

  /**
   * The environment reference viewer implementation.
   */
  private AbstractEnvironmentUseBaselineViewer _environmentReferenceViewer;

  /**
   * Use baseline context model to edit/modify in this details page.
   */
  protected UseBaselineEnvironmentContext _useBaselineEnvironmentContext;
  /**
   * Use baseline contexts model.
   */
  protected UseBaselineContextsModel _useBaselineContextsModel;
  /**
   * The form toolkit reference.
   */
  private FormToolkit _toolkit;
  /**
   * WizardContainer reference used to update wizard buttons state.
   */
  protected IWizardContainer _wizardContainer;

  protected Composite _parametersComposite;
  protected Button _startingPointRadioButton;
  protected Button _referenceRadioButton;

  /**
   * Baseline type
   */
  protected BaselineSelectionType _baselineSection;

  /**
   * Constructor.
   * @param formToolkit_p
   * @param useBaselineEnvironmentContext_p
   * @param useBaselineContextsModel_p
   * @param wizardContainer_p
   */
  public EnvUseBaselineDetailsPage(FormToolkit formToolkit_p, UseBaselineEnvironmentContext useBaselineEnvironmentContext_p,
      UseBaselineContextsModel useBaselineContextsModel_p, IWizardContainer wizardContainer_p, BaselineSelectionType baselineSelection_p) {
    _toolkit = formToolkit_p;
    _useBaselineEnvironmentContext = useBaselineEnvironmentContext_p;
    _useBaselineContextsModel = useBaselineContextsModel_p;
    _wizardContainer = wizardContainer_p;
    _baselineSection = baselineSelection_p;
  }

  /**
   * @see org.eclipse.ui.forms.IDetailsPage#createContents(org.eclipse.swt.widgets.Composite)
   */
  public void createContents(Composite parent_p) {
    parent_p.setLayout(new FillLayout());
    // Create a section.
    Section section = _toolkit.createSection(parent_p, ExpandableComposite.TITLE_BAR | ExpandableComposite.EXPANDED);
    if (BaselineSelectionType.STARTING_POINT == _baselineSection) {
      section.setText(Messages.EnvUseBaselineDetailsPage_StartingPointDetailsSectionTitle);
    } else {
      section.setText(Messages.EnvUseBaselineDetailsPage_ReferenceDetailsSectionTitle);
    }
    // Create section content Composite.
    SashForm sectionContentComposite = new SashForm(section, SWT.VERTICAL);
    // Background has to be set to avoid an ugly display.
    sectionContentComposite.setBackground(section.getBackground());
    section.setClient(sectionContentComposite);
    //
    // Create impacted environments group.
    //
    Group impactedEnvironmentsGroup = new Group(sectionContentComposite, SWT.NONE);
    impactedEnvironmentsGroup.setLayout(new FillLayout());
    impactedEnvironmentsGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
    impactedEnvironmentsGroup.setText(Messages.EnvUseBaselineDetailsPage_ImpactedEnvironmentGroupTitle);
    TreeViewer impactedEnvironmentsTable = new TreeViewer(impactedEnvironmentsGroup, SWT.NONE);
    impactedEnvironmentsTable.setContentProvider(new UseBaselineEnvironmentVariablesViewer.EnvironmentVariablesContentProvider());
    impactedEnvironmentsTable.setLabelProvider(new UseBaselineEnvironmentVariablesViewer.EnvironmentVariablesLabelProvider(new IEditionContextProvider() {
      /**
       * @see com.thalesgroup.orchestra.framework.model.IEditionContextProvider#getEditionContext()
       */
      public Context getEditionContext() {
        return _useBaselineContextsModel.getContext();
      }
    }));
    impactedEnvironmentsTable.setSorter(new ViewerSorter());
    impactedEnvironmentsTable.setInput(_useBaselineContextsModel.getEnvVariableToSlaveEnvValues(_useBaselineEnvironmentContext));
    impactedEnvironmentsTable.expandAll();

    //
    // Create parameters composite
    //
    _parametersComposite = new Composite(sectionContentComposite, SWT.NONE);
    _parametersComposite.setLayout(new GridLayout(1, false));
    _parametersComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

    Composite referenceComposite;
    if (BaselineSelectionType.STARTING_POINT == _baselineSection) {
      // Create reference radio button
      _referenceRadioButton = FormHelper.createButton(_toolkit, _parametersComposite, "Reference parameters", SWT.RADIO);

      // Create reference parameters group.
      Group referenceParametersGroup = new Group(_parametersComposite, SWT.NONE);
      referenceParametersGroup.setLayout(new GridLayout());
      referenceParametersGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
      referenceComposite = referenceParametersGroup;
    } else {
      referenceComposite = _parametersComposite;
    }

    // Create a scrolled composite.
    ScrolledComposite referenceScrolledComposite = new ScrolledComposite(referenceComposite, SWT.H_SCROLL | SWT.V_SCROLL);
    referenceScrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
    referenceScrolledComposite.setExpandHorizontal(true);
    referenceScrolledComposite.setExpandVertical(true);
    // Create the parameters composite which will be filled by the environment handler.
    final Composite referenceEnvParametersComposite = new Composite(referenceScrolledComposite, SWT.NONE);
    GridLayout referenceGridLayout = new GridLayout(1, false);
    referenceEnvParametersComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
    referenceEnvParametersComposite.setLayout(referenceGridLayout);
    referenceScrolledComposite.setContent(referenceEnvParametersComposite);
    // Find environment handler.
    Couple<IStatus, IEnvironmentHandler> environmentHandler =
        EnvironmentActivator.getInstance().getEnvironmentRegistry().getEnvironmentHandler(_useBaselineEnvironmentContext._environmentId);
    if (environmentHandler.getKey().isOK()) {
      IEnvironmentUseBaselineHandler useBaselineHandler = environmentHandler.getValue().getEnvironmentUseBaselineHandler();
      _environmentReferenceViewer = useBaselineHandler.getReferenceViewer(_useBaselineEnvironmentContext);
      if (null != _environmentReferenceViewer) {
        // Create controls in the parameters composite (if a viewer has been defined).
        _environmentReferenceViewer.createControl(referenceEnvParametersComposite, _toolkit, _wizardContainer);
      }
    }
    // Get the preferred size of the composite, if ScrolledComposite content is smaller than is, srcoll bars will be displayed.
    Point referencePrefSize = referenceScrolledComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT);
    referenceScrolledComposite.setMinSize(referencePrefSize);

    if (BaselineSelectionType.STARTING_POINT == _baselineSection) {
      // Create starting point radio button
      _startingPointRadioButton = FormHelper.createButton(_toolkit, _parametersComposite, "Starting point parameters", SWT.RADIO);

      // Create starting point parameters group.
      Group startingPointParametersGroup = new Group(_parametersComposite, SWT.NONE);
      startingPointParametersGroup.setLayout(new GridLayout());
      startingPointParametersGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
      // startingPointParametersGroup.setText(Messages.EnvStartingPointDetailsPage_StartingPointParametersGroupTitle);
      // Create a scrolled composite.
      ScrolledComposite startingPointScrolledComposite = new ScrolledComposite(startingPointParametersGroup, SWT.H_SCROLL | SWT.V_SCROLL);
      startingPointScrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
      startingPointScrolledComposite.setExpandHorizontal(true);
      startingPointScrolledComposite.setExpandVertical(true);
      // Create the parameters composite which will be filled by the environment handler.
      final Composite startingPointEnvParametersComposite = new Composite(startingPointScrolledComposite, SWT.NONE);
      startingPointScrolledComposite.setContent(startingPointEnvParametersComposite);
      GridLayout startingPointGridLayout = new GridLayout(1, false);
      startingPointEnvParametersComposite.setLayout(startingPointGridLayout);
      if (environmentHandler.getKey().isOK()) {
        IEnvironmentUseBaselineHandler useBaselineHandler = environmentHandler.getValue().getEnvironmentUseBaselineHandler();
        _environmentStartingPointViewer = useBaselineHandler.getStartingPointViewer(_useBaselineEnvironmentContext);
        if (null != _environmentStartingPointViewer) {
          // Create controls in the parameters composite (if a viewer has been defined).
          _environmentStartingPointViewer.createControl(startingPointEnvParametersComposite, _toolkit, _wizardContainer);
        }
      }
      // Get the preferred size of the composite, if ScrolledComposite content is smaller than is, scroll bars will be displayed.
      Point startingPointPrefSize = startingPointScrolledComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT);
      startingPointScrolledComposite.setMinSize(startingPointPrefSize);

      sectionContentComposite.setWeights(new int[] { 1, 4 });

      // Handle radio button selections
      SelectionAdapter adapter = new SelectionAdapter() {
        /**
         * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
         */
        @SuppressWarnings("synthetic-access")
        @Override
        public void widgetSelected(SelectionEvent e_p) {
          Widget widget = e_p.widget;
          if (_startingPointRadioButton == widget) {
            if (_startingPointRadioButton.getSelection()) {
              // Change use baseline type before enabling viewer
              _useBaselineEnvironmentContext._useBaselineType = UseBaselineType.LIVE_ENVIRONMENT;
              if (null != _environmentStartingPointViewer) {
                _environmentStartingPointViewer.setEnabled(true);
              }
            } else {
              if (null != _environmentStartingPointViewer) {
                _environmentStartingPointViewer.setEnabled(false);
              }
            }
          } else if (_referenceRadioButton == widget) {
            if (_referenceRadioButton.getSelection()) {
              // Change use baseline type before enabling viewer
              _useBaselineEnvironmentContext._useBaselineType = UseBaselineType.REFERENCE_ENVIRONMENT;
              if (null != _environmentReferenceViewer) {
                _environmentReferenceViewer.setEnabled(true);
              }
            } else {
              if (null != _environmentReferenceViewer) {
                _environmentReferenceViewer.setEnabled(false);
              }
            }
          }
        }
      };
      _startingPointRadioButton.addSelectionListener(adapter);
      _referenceRadioButton.addSelectionListener(adapter);

      //
      // Initialise selection
      //

      // By default, reference is selected
      if (null == _useBaselineEnvironmentContext._useBaselineType) {
        _useBaselineEnvironmentContext._useBaselineType = UseBaselineType.REFERENCE_ENVIRONMENT;
      }

      if (UseBaselineType.REFERENCE_ENVIRONMENT == _useBaselineEnvironmentContext._useBaselineType) {
        _referenceRadioButton.setSelection(true);
        // reference composite is enabled
        if (null != _environmentReferenceViewer) {
          _environmentReferenceViewer.setEnabled(true);
        }
        // starting point composite is disabled
        if (null != _environmentStartingPointViewer) {
          _environmentStartingPointViewer.setEnabled(false);
        }
      } else {
        _startingPointRadioButton.setSelection(true);
        // reference composite is disabled
        if (null != _environmentReferenceViewer) {
          _environmentReferenceViewer.setEnabled(false);
        }
        // starting point composite is enabled
        if (null != _environmentStartingPointViewer) {
          _environmentStartingPointViewer.setEnabled(true);
        }
      }
    } else {
      sectionContentComposite.setWeights(new int[] { 1, 2 });
    }
  }

  /**
   * @see org.eclipse.ui.forms.AbstractFormPart#dispose()
   */
  @Override
  public void dispose() {
    if (null != _environmentStartingPointViewer) {
      _environmentStartingPointViewer.dispose();
      _environmentStartingPointViewer = null;
    }
    if (null != _environmentReferenceViewer) {
      _environmentReferenceViewer.dispose();
      _environmentReferenceViewer = null;
    }
  }

  /**
   * @see org.eclipse.ui.forms.IPartSelectionListener#selectionChanged(org.eclipse.ui.forms.IFormPart, org.eclipse.jface.viewers.ISelection)
   */
  public void selectionChanged(IFormPart part_p, ISelection selection_p) {
    // Nothing to do.
  }
}
