/** 
 * <p>THALES Services - Engineering & Process Management</p>
 * <p>THALES Part Number 16 262 601</p>
 * <p>Copyright (c) 2002-2008 : THALES Services - Engineering & Process Management</p>
 */

package com.thalesgroup.orchestra.framework.oe.ui.views;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

import com.thalesgroup.orchestra.framework.lib.constants.ISegmentConstants;
import com.thalesgroup.orchestra.framework.oe.OrchestraExplorerActivator;
import com.thalesgroup.orchestra.framework.oe.ui.OrchestraExplorerState;
import com.thalesgroup.orchestra.framework.oe.ui.actions.SortAction;
import com.thalesgroup.orchestra.framework.oe.ui.viewer.OrchestraExplorerViewer;

import framework.orchestra.thalesgroup.com.VariableManagerAPI.VariableManagerAdapter;

/**
 * <p>
 * Title : OrchestraExplorerView
 * </p>
 * <p>
 * Description : The class defines the view for the ArtifactExplorer.
 * </p>
 * @author Orchestra Framework Tool Suite developer
 * @version 3.7.0
 */
public class OrchestraExplorerView extends ViewPart {

  // ID
  public static final String ID = "com.thalesgroup.themis.papeetedoc.ui.views.explorer.artifacts.PapeeteArtifactNavigator";//$NON-NLS-1$

  // Gui declaration

  /**
   * Is Orchestra Explorer view disposed ?
   */
  protected volatile boolean _isDisposed;

  protected SortAction _typeSorterAction;

  protected OrchestraExplorerViewer _viewer;

  protected OrchestraExplorerState _orchestraExplorerState;

  /**
   * Constructor.
   */
  public OrchestraExplorerView() {
    // Retain current view.
    OrchestraExplorerActivator.getDefault().setCurrentView(this);
    // Make sure view is not tagged has disposed.
    _isDisposed = false;
    _orchestraExplorerState = new OrchestraExplorerState();
  }

  /**
   * @see IWorkbenchPart#createPartControl(Composite)
   */
  @Override
  public void createPartControl(Composite parent) {
    IActionBars actionBars = getViewSite().getActionBars();

    // Do not update artefacts for Silver segment
    boolean updatArtefacts = true;
    try {
      if (ISegmentConstants.SILVER_SEGMENT.equals(VariableManagerAdapter.getInstance().getSegmentName())) {
        updatArtefacts = false;
      }
    } catch (Exception exception_p) {
    }
    _viewer =
        new OrchestraExplorerViewer(_orchestraExplorerState, actionBars.getMenuManager(), actionBars.getToolBarManager(), null, null, true, updatArtefacts);
    _viewer.createControl(parent);
  }

  /**
   * @see org.eclipse.ui.part.WorkbenchPart#dispose()
   */
  @Override
  public void dispose() {
    // First, switch disposed flag to true.
    _isDisposed = true;
    // Release current view flag.
    OrchestraExplorerActivator.getDefault().setCurrentView(null);
    _viewer.dispose();
    super.dispose();
  }

  /*
   * (non-Javadoc)
   * @see org.eclipse.ui.IViewPart#init(org.eclipse.ui.IViewSite, org.eclipse.ui.IMemento)
   */
  @Override
  public void init(IViewSite site_p, IMemento memento_p) throws PartInitException {
    super.init(site_p, memento_p);
    _orchestraExplorerState.loadMemento(memento_p);
  }

  /**
   * Is Orchestra Explorer view disposed ?
   * @return <code>true</code> if so, <code>false</code> otherwise.
   */
  public boolean isDisposed() {
    return _isDisposed;
  }

  /**
   * @see org.eclipse.ui.part.ViewPart#saveState(org.eclipse.ui.IMemento)
   */
  @Override
  public void saveState(IMemento memento_p) {
    _orchestraExplorerState.saveMemento(memento_p);
  }

  /**
   * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
   */
  @Override
  public void setFocus() {
    _viewer.setFocus();
  }
}