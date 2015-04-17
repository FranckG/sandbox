/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.doors.framework.environment.ui;

import java.util.ArrayList;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.ui.forms.DetailsPart;
import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IDetailsPageProvider;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.MasterDetailsBlock;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.thalesgroup.orchestra.doors.framework.environment.model.BaselineSetsToComplete;
import com.thalesgroup.orchestra.doors.framework.environment.model.BaselineSetsToComplete.BaselineSet;
import com.thalesgroup.orchestra.doors.framework.environment.ui.DoorsEnvironmentCompleteBaselineSetsWizard.DoorsEnvironmentCompleteBaselineSetsWizardPage;

/**
 * BaselinesSets block, composed of one list of baselinesSets on the master part, and one form to complete on the details part.
 * @author S0035580
 */
public class BaselinesSetsBlock extends MasterDetailsBlock implements IDetailsPageProvider {
  /**
   * List of BaselineSets to complete.
   */
  private BaselineSetsToComplete _baselineSetsToComplete;
  /**
   * BaselineSets List viewer.
   */
  private ListViewer _viewer;
  private DoorsEnvironmentCompleteBaselineSetsWizardPage _wizardPage;
  protected Object newKey;

  /**
   * Constructor
   * @param bstc
   */
  public BaselinesSetsBlock(BaselineSetsToComplete bstc_p, DoorsEnvironmentCompleteBaselineSetsWizardPage wizardpage_p) {
    _baselineSetsToComplete = bstc_p;
    _wizardPage = wizardpage_p;
  }

  /**
   * @see org.eclipse.ui.forms.MasterDetailsBlock#createMasterPart(org.eclipse.ui.forms.IManagedForm, org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected void createMasterPart(final IManagedForm managedForm_p, Composite parent_p) {
    // Get toolkit.
    FormToolkit toolkit = managedForm_p.getToolkit();
    // Create section with title.
    Section section = toolkit.createSection(parent_p, ExpandableComposite.TITLE_BAR);
    section.setText(Messages.BaselinesSetsBlock_BaselinesSetsNames0);
    section.marginWidth = 10;
    section.marginHeight = 5;
    // New composite with list of baselinesSets to complete.
    Composite client = toolkit.createComposite(section, SWT.WRAP);
    GridLayout layout = new GridLayout();

    layout.numColumns = 1;
    layout.marginWidth = 2;
    layout.marginHeight = 2;
    client.setLayout(layout);

    List list = new List(client, SWT.FULL_SELECTION | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
    // Table list = toolkit.createTable(client, SWT.FULL_SELECTION);
    GridData gd = new GridData(GridData.FILL_BOTH);
    gd.heightHint = 20;
    gd.widthHint = 100;
    list.setLayoutData(gd);
    toolkit.paintBordersFor(client);
    gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
    section.setClient(client);

    final SectionPart spart = new SectionPart(section);
    managedForm_p.addPart(spart);

    // Create baselinesets viewer.
    _viewer = new ListViewer(list);

    // Register selection changed listener.
    _viewer.addSelectionChangedListener(new ISelectionChangedListener() {

      public void selectionChanged(SelectionChangedEvent event_p) {
        // Get selection.
        ISelection selection = event_p.getSelection();
        managedForm_p.fireSelectionChanged(spart, selection);
      }
    });
    _viewer.setContentProvider(new MyStructuredContentProvider());
    _viewer.setLabelProvider(new MyLabelProvider());
    _viewer.setInput(_baselineSetsToComplete.getBaselinesPathsNamesToDisplay());
    _viewer.refresh();

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
    return new BaselinesDetailsPage(key_p, _baselineSetsToComplete, _wizardPage);
  }

  /**
   * @see org.eclipse.ui.forms.IDetailsPageProvider#getPageKey(java.lang.Object)
   */
  public Object getPageKey(Object object_p) {
    return object_p;
  }

  /**
   * @see org.eclipse.ui.forms.MasterDetailsBlock#registerPages(org.eclipse.ui.forms.DetailsPart)
   */
  @Override
  protected void registerPages(DetailsPart detailsPart_p) {
    detailsPart_p.setPageLimit(0);
    detailsPart_p.setPageProvider(this);
  }

  static class MyLabelProvider implements ILabelProvider {
    public void addListener(ILabelProviderListener listener) {
      // Nothing to do.
    }

    public void dispose() {
      // Nothing to do.
    }

    public Image getImage(Object element) {
      return null;
    }

    public String getText(Object element_p) {
      BaselineSet currentBaseline = (BaselineSet) element_p;
      return currentBaseline.getName();
    }

    public boolean isLabelProperty(Object element, String property) {
      return false;
    }

    public void removeListener(ILabelProviderListener listener) {
      // Nothing to do.
    }
  }

  static class MyStructuredContentProvider implements IStructuredContentProvider {
    /**
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     */
    public void dispose() {
      // Nothing to do.
    }

    @SuppressWarnings("unchecked")
    public Object[] getElements(Object inputElement) {
      ArrayList<BaselineSet> localInputElement = (ArrayList<BaselineSet>) inputElement;
      return localInputElement.toArray();
    }

    /**
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
     */
    public void inputChanged(Viewer viewer_p, Object oldInput_p, Object newInput_p) {
      // Nothing to do.
    }
  }
}
