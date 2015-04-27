/** 
 * <p>THALES Services - Engineering & Process Management</p>
 * <p>THALES Part Number 16 262 601</p>
 * <p>Copyright (c) 2002-2008 : THALES Services - Engineering & Process Management</p>
 */
package com.thalesgroup.orchestra.framework.oe.ui.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import com.thalesgroup.orchestra.framework.oe.artefacts.IArtefact;
import com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.ArtefactNode;

/**
 * This class is a simple class that extends Action and is used to put in common the methods used by the several Orchestra Explorer actions
 * @author S0018747
 */
public abstract class ArtefactAction extends BaseSelectionListenerAction {
  /**
   * Latest selection on selection provider.
   */
  protected IStructuredSelection _latestSelection;

  /**
   * Subscribers to job events
   */
  protected List<IJobChangeListener> _jobChangeListeners = new ArrayList<IJobChangeListener>();

  /**
   * Constructor.
   * @param string The action name.
   */
  public ArtefactAction(String string) {
    super(string);
  }

  /**
   * Apply enablement condition to all currently selected elements.
   * @return <code>true</code> if enablement condition is met for all elements, <code>false</code> if there is no selection or at least one element is not
   *         fulfilling enablement condition.
   */
  protected boolean applyEnablementConditionToSelection() {
    boolean result = false;
    // Get selected artifact nodes.
    List<ArtefactNode> currentTreeSelection = getCurrentTreeSelection(ArtefactNode.class);
    if ((currentTreeSelection != null) && !currentTreeSelection.isEmpty()) {
      result = true;
      // Cycle through them all.
      for (ArtefactNode artefactNode : currentTreeSelection) {
        result &= isEnablingCondition(artefactNode);
      }
    }
    return result;
  }

  /**
   * Calculate the enabled state of the action. It's called by selection changed listener. <code>true</code> by default.
   */
  protected boolean calculateEnabledState() {
    return true;
  }

  /**
   * Execute specified task asynchronously within current environment.
   * @param taskName_p
   * @param task_p
   */
  protected void executeAsynchronously(String taskName_p, final Runnable task_p) {
    // Precondition.
    if (null == task_p) {
      return;
    }
    Job job = new Job((null == taskName_p) ? getTaskName() : taskName_p) {
      @Override
      protected IStatus run(IProgressMonitor monitor_p) {
        task_p.run();
        return Status.OK_STATUS;
      }
    };
    for (IJobChangeListener listener : _jobChangeListeners) {
      job.addJobChangeListener(listener);
    }
    job.schedule();
  }

  /**
   * @param type_p The type of elements to retain from the current selection.
   * @return
   */
  @SuppressWarnings("unchecked")
  public <T> List<T> getCurrentTreeSelection(Class<T> type_p) {
    List<T> result = new ArrayList<T>(0);
    if ((null != _latestSelection) && !_latestSelection.isEmpty()) {
      for (Object object : _latestSelection.toList()) {
        if (type_p.isInstance(object)) {
          result.add((T) object);
        }
      }
    }
    return result;
  }

  /**
   * Get displayable selected element.<br>
   * Used to fill-in task name when possible.
   * @return
   */
  protected IArtefact getDisplayableSelectedElement() {
    List<ArtefactNode> selectedArtefacts = getCurrentTreeSelection(ArtefactNode.class);
    if (1 == selectedArtefacts.size()) {
      return selectedArtefacts.get(0).getValue();
    }
    return null;
  }

  /**
   * Get asynchronous task name, if any.
   * @return Default implementation returns an empty {@link String} or the name of the unique selected element (if any).
   */
  protected String getTaskName() {
    IArtefact element = getDisplayableSelectedElement();
    if (null != element) {
      return " (" + element.getName() + ")"; //$NON-NLS-1$ //$NON-NLS-2$
    }
    return ""; //$NON-NLS-1$
  }

  /**
   * Is specified selected element meeting the action enablement conditions ?
   * @param artefactNode_p
   * @return <code>true</code> if so, <code>false</code> if the action should not be enabled because of specified element.<br>
   *         Default implementation returns <code>true</code>.
   */
  protected boolean isEnablingCondition(ArtefactNode artefactNode_p) {
    return true;
  }

  /**
   * @see org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org.eclipse.jface.viewers.IStructuredSelection)
   */
  @Override
  protected boolean updateSelection(IStructuredSelection selection_p) {
    _latestSelection = selection_p;
    return calculateEnabledState();
  }

  /**
   * Add subscriber to job events.
   * @param listener_p
   */
  public void addJobListener(IJobChangeListener listener_p) {
    if (!_jobChangeListeners.contains(listener_p)) {
      _jobChangeListeners.add(listener_p);
    }
  }

  /**
   * Remove subscriber from job events
   * @param listener_p
   */
  public void removeJobListener(IJobChangeListener listener_p) {
    _jobChangeListeners.remove(listener_p);
  }
}