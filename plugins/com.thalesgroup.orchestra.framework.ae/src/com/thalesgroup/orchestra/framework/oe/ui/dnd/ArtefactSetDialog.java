/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.oe.ui.dnd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;
import com.thalesgroup.orchestra.framework.oe.OrchestraExplorerActivator;
import com.thalesgroup.orchestra.framework.oe.artefacts.internal.RootArtefactsBag;
import com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.DragableStringNode;
import com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.StringNode;
import com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.TypeStringNode;
import com.thalesgroup.orchestra.framework.puci.PUCI;
import com.thalesgroup.orchestra.framework.root.ui.AbstractRunnableWithDisplay;
import com.thalesgroup.orchestra.framework.root.ui.DisplayHelper;
import com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizard;
import com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage;

/**
 * @author t0076261
 */
public class ArtefactSetDialog extends WizardDialog {
  /**
   * Constructor.
   * @param parentShell_p
   * @param newWizard_p
   */
  private ArtefactSetDialog(Shell parentShell_p, IWizard newWizard_p) {
    super(parentShell_p, newWizard_p);
  }

  /**
   * Choose artifact set parameters for specified logical folder.
   * @param logicalFolderNode_p
   * @return The artifact set {@link OrchestraURI}, or <code>null</code> if the dialog was cancelled by the user.
   */
  public static OrchestraURI chooseArtefactSetParameters(final DragableStringNode logicalFolderNode_p) {
    // Resulting structure.
    final OrchestraURI[] result = new OrchestraURI[] { null };
    // Display dialog.
    DisplayHelper.displayRunnable(new AbstractRunnableWithDisplay() {
      /**
       * @see java.lang.Runnable#run()
       */
      @SuppressWarnings("synthetic-access")
      public void run() {
        // Create wizard.
        ArtefactSetWizard wizard = new ArtefactSetWizard(logicalFolderNode_p);
        // Choose name dialog.
        ArtefactSetDialog dialog = new ArtefactSetDialog(getDisplay().getActiveShell(), wizard);
        // Open dialog.
        dialog.open();
        // Retain result.
        result[0] = wizard._artefactSetUri;
      }
    }, false);
    return result[0];
  }

  /**
   * Artifact set page.
   * @author t0076261
   */
  public static class ArtefactSetPage extends AbstractFormsWizardPage {
    /**
     * Logical folder path.
     */
    protected String _logicalFolderPath;
    /**
     * Selected root types.
     */
    protected Set<String> _rootTypes;

    /**
     * Constructor.
     */
    public ArtefactSetPage() {
      super("ArtefactSetPage"); //$NON-NLS-1$
    }

    /**
     * @see com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage#doCreateControl(org.eclipse.swt.widgets.Composite,
     *      org.eclipse.ui.forms.widgets.FormToolkit)
     */
    @Override
    protected Composite doCreateControl(Composite parent_p, FormToolkit toolkit_p) {
      // Set parent layout.
      parent_p.setLayout(new GridLayout(1, false));
      // Labels composite.
      Composite labelsComposite = toolkit_p.createComposite(parent_p, SWT.NONE);
      GridData gridData = new GridData();
      gridData.grabExcessHorizontalSpace = true;
      gridData.horizontalAlignment = SWT.FILL;
      labelsComposite.setLayoutData(gridData);
      // Set parent layout.
      TableWrapLayout layout = new TableWrapLayout();
      layout.numColumns = 2;
      layout.makeColumnsEqualWidth = false;
      labelsComposite.setLayout(layout);
      // Display current path.
      Label folderLabel = toolkit_p.createLabel(labelsComposite, Messages.ArtefactSetDialog_Path_Label);
      TableWrapData tableData = new TableWrapData(TableWrapData.LEFT);
      folderLabel.setLayoutData(tableData);
      Text folderText = toolkit_p.createText(labelsComposite, _logicalFolderPath, SWT.READ_ONLY | SWT.BORDER | SWT.WRAP);
      tableData = new TableWrapData();
      tableData.align = SWT.LEFT;
      folderText.setLayoutData(tableData);
      // Create types label.
      Label typesLabel = toolkit_p.createLabel(labelsComposite, Messages.ArtefactSetDialog_Types_Label);
      tableData = new TableWrapData();
      tableData.colspan = 2;
      typesLabel.setLayoutData(tableData);
      // Create viewer.
      Composite viewerComposite = toolkit_p.createComposite(parent_p, SWT.NONE);
      viewerComposite.setLayout(new GridLayout(1, false));
      viewerComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
      // Create viewer.
      ListViewer viewer = new ListViewer(viewerComposite, SWT.READ_ONLY | SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.WRAP);
      // Set layout data.
      viewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
      // Set array content provider.
      viewer.setContentProvider(ArrayContentProvider.getInstance());
      // Initial input.
      List<String> existingRootTypes = new ArrayList<String>(RootArtefactsBag.getInstance().getRootArtefactsTypes());
      Collections.sort(existingRootTypes);
      viewer.setInput(existingRootTypes.toArray());
      // Initial selection.
      if (!_rootTypes.isEmpty()) {
        viewer.setSelection(new StructuredSelection(_rootTypes.toArray()));
      }
      // Finally, add listener.
      viewer.addSelectionChangedListener(new ISelectionChangedListener() {
        /**
         * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
         */
        @SuppressWarnings("unchecked")
        public void selectionChanged(SelectionChangedEvent event_p) {
          IStructuredSelection structuredSelection = (IStructuredSelection) event_p.getSelection();
          _rootTypes.clear();
          _rootTypes.addAll(structuredSelection.toList());
        }
      });
      return parent_p;
    }

    /**
     * @see com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage#getPageImageDescriptor()
     */
    @Override
    protected ImageDescriptor getPageImageDescriptor() {
      return OrchestraExplorerActivator.getDefault().getImageDescriptor("dialogs/artefactset_wiz_ban.png"); //$NON-NLS-1$
    }

    /**
     * @see com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage#getPageTitle()
     */
    @Override
    protected String getPageTitle() {
      return Messages.ArtefactSetDialog_Page_Title;
    }

    /**
     * @see org.eclipse.jface.wizard.WizardPage#getWizard()
     */
    @Override
    public ArtefactSetWizard getWizard() {
      return (ArtefactSetWizard) super.getWizard();
    }

    /**
     * Initialize page attributes.
     */
    protected void initialize() {
      // Selected root types.
      _rootTypes = new LinkedHashSet<String>(0);
      // Computer logical folder path.
      StringNode node = getWizard()._logicalFolderNode;
      IPath path = node.getNodePath(false);
      if ((null == path) || path.isEmpty()) {
        path = new Path("/"); //$NON-NLS-1$
      }
      _logicalFolderPath = path.toString();
      // Retain already selected type (through the Orchestra Explorer structure).
      if (node.getRootNode() instanceof TypeStringNode) {
        _rootTypes.add(((TypeStringNode) node.getRootNode()).getValue());
      }
    }
  }

  /**
   * Artifact set wizard.
   * @author t0076261
   */
  public static class ArtefactSetWizard extends AbstractFormsWizard {
    /**
     * Resulting artifact set URI.
     */
    protected OrchestraURI _artefactSetUri;
    /**
     * Logical folder.
     */
    protected DragableStringNode _logicalFolderNode;
    /**
     * Artifact set page.
     */
    protected ArtefactSetPage _page;

    /**
     * Constructor.
     */
    public ArtefactSetWizard(DragableStringNode logicalFolderNode_p) {
      _logicalFolderNode = logicalFolderNode_p;
      _page = new ArtefactSetPage();
      addPage(_page);
      _page.initialize();
      setWindowTitle(Messages.ArtefactSetDialog_Window_Title);
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#performCancel()
     */
    @Override
    public boolean performCancel() {
      // Make sure resulting structure is nullified.
      _artefactSetUri = null;
      // And free wizard.
      return true;
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     */
    @Override
    public boolean performFinish() {
      // Create resulting structure.
      try {
        _artefactSetUri = PUCI.createArtifactSetUri(_page._logicalFolderPath, new ArrayList<String>(_page._rootTypes));
      } catch (Exception exception_p) {
        return performCancel();
      }
      // And free wizard.
      return true;
    }
  }
}