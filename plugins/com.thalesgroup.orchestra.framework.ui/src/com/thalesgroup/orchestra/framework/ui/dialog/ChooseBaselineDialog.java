/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.dialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.fieldassist.IContentProposalProvider;
import org.eclipse.jface.fieldassist.IControlContentAdapter;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.model.baseline.Baseline;
import com.thalesgroup.orchestra.framework.root.ui.AbstractRunnableWithDisplay;
import com.thalesgroup.orchestra.framework.root.ui.DisplayHelper;
import com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizard;
import com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage;
import com.thalesgroup.orchestra.framework.ui.internal.fieldassist.AutoCompleteField;
import com.thalesgroup.orchestra.framework.ui.internal.fieldassist.ComboContentAdapter;
import com.thalesgroup.orchestra.framework.ui.internal.fieldassist.ContentProposalAdapter;

/**
 * Choose Baseline dialog.
 * @author t0076261
 */
public class ChooseBaselineDialog extends WizardDialog {
  /**
   * Constructor.
   * @param parentShell_p
   * @param chooseBaselineWizard_p
   */
  private ChooseBaselineDialog(Shell parentShell_p, ChooseBaselineWizard chooseBaselineWizard_p) {
    super(parentShell_p, chooseBaselineWizard_p);
  }

  /**
   * Choose baseline among specified ones.
   * @param baselineReferences_p
   * @return
   */
  public static BaselineSelection chooseBaseline(final Map<String, Baseline> baselineReferences_p) {
    // Precondition.
    if ((null == baselineReferences_p) || baselineReferences_p.isEmpty()) {
      return null;
    }
    final BaselineSelection[] result = new BaselineSelection[] { null };
    // Display dialog.
    DisplayHelper.displayRunnable(new AbstractRunnableWithDisplay() {
      /**
       * @see java.lang.Runnable#run()
       */
      @SuppressWarnings("synthetic-access")
      public void run() {
        // Create wizard.
        ChooseBaselineWizard wizard = new ChooseBaselineWizard();
        // Retain references.
        wizard._baselineReferences = baselineReferences_p;
        // Choose name dialog.
        ChooseBaselineDialog dialog = new ChooseBaselineDialog(getDisplay().getActiveShell(), wizard);
        // Dialog size.
        dialog.setPageSize(400, 240);
        // Open dialog.
        dialog.open();
        // Retain result.
        result[0] = wizard._selectedBaseline;
      }
    }, false);
    // Return result.
    return result[0];
  }

  /**
   * A baseline selection.
   * @author t0076261
   */
  public static class BaselineSelection {
    public Baseline _selectedBaseline;
    public BaselineSelectionType _selectionType;
  }

  /**
   * Baseline selection available types.
   * @author t0076261
   */
  public static enum BaselineSelectionType {
    REFERENCE, STARTING_POINT;
  }

  /**
   * Make baseline wizard page.
   * @author t0076261
   */
  public static class ChooseBaselinePage extends AbstractFormsWizardPage {
    /**
     * Auto complete field for Combo.
     */
    private AutoCompleteField _autoCompleteField;
    /**
     * Description text.
     */
    protected Text _descriptionText;
    /**
     * Selected baseline name.
     */
    protected String _selectedName;
    /**
     * Selected baseline type.
     */
    protected BaselineSelectionType _selectedType;

    /**
     * Constructor.
     */
    public ChooseBaselinePage() {
      super("ChooseBaselinePage"); //$NON-NLS-1$
    }

    /**
     * @see org.eclipse.jface.dialogs.DialogPage#dispose()
     */
    @Override
    public void dispose() {
      super.dispose();
      // Dispose auto complete field.
      if (null != _autoCompleteField) {
        _autoCompleteField = null;
      }
    }

    /**
     * @see com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage#doCreateControl(org.eclipse.swt.widgets.Composite,
     *      org.eclipse.ui.forms.widgets.FormToolkit)
     */
    @Override
    protected Composite doCreateControl(Composite parent_p, FormToolkit toolkit_p) {
      // Set parent layout.
      parent_p.setLayout(new GridLayout(1, false));
      GridData gridData = null;
      // Baseline selection type group.
      {
        Group baselineSelectionTypeGroup = new Group(parent_p, SWT.NONE);
        baselineSelectionTypeGroup.setText(Messages.ChooseBaselineDialog_GroupText_SelectBaselineUsage);
        gridData = new GridData();
        gridData.grabExcessHorizontalSpace = true;
        gridData.horizontalAlignment = SWT.FILL;
        baselineSelectionTypeGroup.setLayoutData(gridData);
        // Set layout.
        baselineSelectionTypeGroup.setLayout(new GridLayout(1, false));
        // Buttons listener.
        Listener listener = new Listener() {
          /**
           * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
           */
          public void handleEvent(Event event_p) {
            // Precondition.
            if (null == event_p.widget) {
              return;
            }
            // Test data type.
            if (event_p.widget.getData() instanceof BaselineSelectionType) {
              setSelectedType((BaselineSelectionType) event_p.widget.getData());
            }
          }
        };
        // Reference button.
        Button referenceButton = toolkit_p.createButton(baselineSelectionTypeGroup, Messages.ChooseBaselineDialog_Button_BaselineType_Reference, SWT.RADIO);
        referenceButton.setLayoutData(gridData);
        referenceButton.setData(BaselineSelectionType.REFERENCE);
        referenceButton.addListener(SWT.Selection, listener);
        // Starting point button.
        Button startingPointButton =
            toolkit_p.createButton(baselineSelectionTypeGroup, Messages.ChooseBaselineDialog_Button_BaselineType_StartingPoint, SWT.RADIO);
        startingPointButton.setData(BaselineSelectionType.STARTING_POINT);
        startingPointButton.setLayoutData(gridData);
        startingPointButton.addListener(SWT.Selection, listener);
      }
      // Baseline selection group.
      {
        Group baselineSelectionGroup = new Group(parent_p, SWT.NONE);
        baselineSelectionGroup.setText(Messages.ChooseBaselineDialog_GroupText_SelectBaseline);
        gridData = new GridData(GridData.FILL_BOTH);
        baselineSelectionGroup.setLayoutData(gridData);
        // Set layout.
        baselineSelectionGroup.setLayout(new GridLayout(1, false));
        // Combo-box.
        Combo baselineNamesCombo = new Combo(baselineSelectionGroup, SWT.BORDER);
        gridData = new GridData(GridData.FILL_BOTH);
        gridData.grabExcessVerticalSpace = false;
        baselineNamesCombo.setLayoutData(gridData);
        // Add selection listener.
        baselineNamesCombo.addSelectionListener(new SelectionAdapter() {
          /**
           * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
           */
          @Override
          public void widgetSelected(SelectionEvent e_p) {
            setSelectedName(((Combo) e_p.widget).getText());
          }
        });
        // Set items.
        List<String> baselineNames = new ArrayList<String>(getWizard()._baselineReferences.keySet());
        Collections.sort(baselineNames);
        String[] items = baselineNames.toArray(new String[baselineNames.size()]);
        baselineNamesCombo.setItems(items);
        _autoCompleteField = new AutoCompleteField(baselineNamesCombo, new ComboContentAdapter(), items) {
          /**
           * @see com.thalesgroup.orchestra.framework.ui.internal.fieldassist.AutoCompleteField#createContentProposalAdapter(org.eclipse.swt.widgets.Control,
           *      org.eclipse.jface.fieldassist.IControlContentAdapter, org.eclipse.jface.fieldassist.IContentProposalProvider)
           */
          @Override
          protected ContentProposalAdapter createContentProposalAdapter(Control control_p, IControlContentAdapter controlContentAdapter_p,
              IContentProposalProvider proposalProvider_p) {
            return new ContentProposalAdapter(control_p, controlContentAdapter_p, proposalProvider_p, null, null) {
              /**
               * @see com.thalesgroup.orchestra.framework.ui.internal.fieldassist.ContentProposalAdapter#getControl()
               */
              @Override
              public Combo getControl() {
                return (Combo) super.getControl();
              }

              /**
               * @see com.thalesgroup.orchestra.framework.ui.internal.fieldassist.ContentProposalAdapter#openProposalPopup(boolean)
               */
              @Override
              protected void openProposalPopup(boolean autoActivated_p) {
                if (!getControl().getListVisible()) {
                  super.openProposalPopup(autoActivated_p);
                }
              }
            };
          }
        };
        // Description text.
        {
          Group baselineDescriptionGroup = new Group(baselineSelectionGroup, SWT.NONE);
          baselineDescriptionGroup.setText(Messages.ChooseBaselineDialog_GroupText_BaselineDescription);
          gridData = new GridData(GridData.FILL_BOTH);
          baselineDescriptionGroup.setLayoutData(gridData);
          // Set layout.
          baselineDescriptionGroup.setLayout(new GridLayout(1, false));
          // Create text.
          _descriptionText = toolkit_p.createText(baselineDescriptionGroup, ICommonConstants.EMPTY_STRING, SWT.H_SCROLL | SWT.V_SCROLL);
          _descriptionText.setLayoutData(new GridData(GridData.FILL_BOTH));
          _descriptionText.setEditable(false);
        }
      }
      return parent_p;
    }

    /**
     * @see com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage#getPageImageDescriptor()
     */
    @Override
    protected ImageDescriptor getPageImageDescriptor() {
      // TODO Auto-generated method stub
      return null;
    }

    /**
     * @see com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage#getPageTitle()
     */
    @Override
    protected String getPageTitle() {
      return Messages.ChooseBaselineDialog_PageTitle;
    }

    /**
     * @see org.eclipse.jface.wizard.WizardPage#getWizard()
     */
    @Override
    public ChooseBaselineWizard getWizard() {
      return (ChooseBaselineWizard) super.getWizard();
    }

    /**
     * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
     */
    @Override
    public boolean isPageComplete() {
      return (null != _selectedType) && (null != _selectedName) && !_selectedName.trim().isEmpty();
    }

    /**
     * Set selected baseline name.
     * @param text_p
     */
    protected void setSelectedName(String text_p) {
      // Retain selected name.
      _selectedName = text_p;
      // Then access baseline.
      Baseline baseline = getWizard()._baselineReferences.get(text_p);
      if (null != baseline) {
        String description = (null != baseline.getDescription()) ? baseline.getDescription() : ICommonConstants.EMPTY_STRING;
        _descriptionText.setText(description);
      }
      // Set page completion.
      setPageComplete(isPageComplete());
    }

    /**
     * Set selected baseline type.
     * @param type_p
     */
    protected void setSelectedType(BaselineSelectionType type_p) {
      _selectedType = type_p;
      // Set page completion.
      setPageComplete(isPageComplete());
    }
  }

  /**
   * Choose baseline wizard.
   * @author t0076261
   */
  public static class ChooseBaselineWizard extends AbstractFormsWizard {
    /**
     * Baseline references.
     */
    protected Map<String, Baseline> _baselineReferences;
    /**
     * Choose baseline page.
     */
    protected ChooseBaselinePage _chooseBaselinePage;
    /**
     * Selected baseline.
     */
    protected BaselineSelection _selectedBaseline;

    /**
     * Constructor.
     */
    public ChooseBaselineWizard() {
      _chooseBaselinePage = new ChooseBaselinePage();
      addPage(_chooseBaselinePage);
      setWindowTitle(Messages.ChooseBaselineDialog_WizardTitle);
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#performCancel()
     */
    @Override
    public boolean performCancel() {
      // Make sure resulting structure is nullified.
      _selectedBaseline = null;
      // And free wizard.
      return true;
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     */
    @Override
    public boolean performFinish() {
      // Create resulting structure.
      _selectedBaseline = new BaselineSelection();
      _selectedBaseline._selectedBaseline = _baselineReferences.get(_chooseBaselinePage._selectedName);
      // Precondition.
      if (null == _selectedBaseline._selectedBaseline) {
        return false;
      }
      _selectedBaseline._selectionType = _chooseBaselinePage._selectedType;
      // And free wizard.
      return true;
    }
  }
}