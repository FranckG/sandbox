/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.viewer;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.thalesgroup.orchestra.framework.environment.UseBaselineEnvironmentContext;
import com.thalesgroup.orchestra.framework.model.IEditionContextProvider;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariableValue;
import com.thalesgroup.orchestra.framework.ui.viewer.AbstractLabelProvider;
import com.thalesgroup.orchestra.framework.ui.wizard.NewUseBaselineContextWizard.UseBaselineContextsModel;

/**
 * This viewer displays the environments variables and allows values to be selected or/and checked.<br>
 * Only master environment values are displayed.
 * @author T0052089
 */
public class UseBaselineEnvironmentVariablesViewer {
  /**
   * Use baseline contexts model.
   */
  protected UseBaselineContextsModel _useBaselineContextsModel;

  /**
   * Viewer.
   */
  protected TreeViewer _viewer;

  /**
   * @param selectionChangedListener_p
   */
  public void addSelectionChangedListener(ISelectionChangedListener selectionChangedListener_p) {
    _viewer.addSelectionChangedListener(selectionChangedListener_p);
  }

  /**
   * 
   */
  public Control createViewer(Composite parent_p) {
    _viewer = new TreeViewer(parent_p, SWT.BORDER);
    _viewer.setContentProvider(new EnvironmentVariablesContentProvider());
    _viewer.setLabelProvider(new EnvironmentVariablesLabelProvider(new IEditionContextProvider() {
      /**
       * @see com.thalesgroup.orchestra.framework.model.IEditionContextProvider#getEditionContext()
       */
      public Context getEditionContext() {
        // Precondition.
        if (null == _useBaselineContextsModel) {
          return null;
        }
        return _useBaselineContextsModel.getContext();
      }
    }));
    _viewer.setSorter(new ViewerSorter());
    _viewer.expandAll();
    return _viewer.getControl();
  }

  /**
   * @param selectionChangedListener_p
   */
  public void removeSelectionChangedListener(ISelectionChangedListener selectionChangedListener_p) {
    _viewer.remove(selectionChangedListener_p);
  }

  /**
   * Set input content.
   * @param useBaselineContextsModel_p
   */
  public void setInput(UseBaselineContextsModel useBaselineContextsModel_p) {
    _useBaselineContextsModel = useBaselineContextsModel_p;
    if (null == _useBaselineContextsModel) {
      _viewer.setInput(null);
    } else {
      // Only master env values are displayed.
      _viewer.setInput(useBaselineContextsModel_p.getEnvVariableToMasterEnvValues());
    }
  }

  /**
   * Update internal context state for specified environment variable value.
   * @param environmentVariableValue_p
   */
  protected void updateValueSelectedState(EnvironmentVariableValue environmentVariableValue_p) {
    // Precondition.
    if (null == environmentVariableValue_p) {
      return;
    }
    // Get corresponding context.
    UseBaselineEnvironmentContext useBaselineEnvironmentContext = _useBaselineContextsModel.getEnvValueToUseBaselineContext().get(environmentVariableValue_p);
    // Value is a slave, stop here.
    if (null != useBaselineEnvironmentContext._masterContext) {
      return;
    }
    useBaselineEnvironmentContext._allowUserInteractions = true;
  }

  /**
   * This tree content provider works with an input of the following type:<br>
   * Map<?, List<?>> (a Map of Lists).
   * @author T0052089
   */
  public static class EnvironmentVariablesContentProvider implements ITreeContentProvider {
    /**
     * Source of data.
     */
    protected Map<?, ?> _input;

    /**
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     */
    public void dispose() {
      // Nothing to do.
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
     */
    public Object[] getChildren(Object parentElement_p) {
      // Precondition.
      if (null == _input) {
        return new Object[0];
      }
      Object value = _input.get(parentElement_p);
      if (value instanceof List<?>) {
        return ((List<?>) value).toArray();
      }
      return new Object[0];
    }

    /**
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
     */
    public Object[] getElements(Object inputElement_p) {
      // Precondition.
      if (null == _input) {
        return new Object[0];
      }
      return _input.keySet().toArray();
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
     */
    public Object getParent(Object element_p) {
      // Precondition.
      if (null == _input) {
        return null;
      }
      // Given element is a key of the map -> it's a parent node -> it has no parent.
      if (_input.containsKey(element_p)) {
        return null;
      }
      for (Map.Entry<?, ?> entry : _input.entrySet()) {
        if ((entry.getValue() instanceof List<?>) && ((List<?>) entry.getValue()).contains(element_p)) {
          return entry.getKey();
        }
      }
      return null;
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
     */
    public boolean hasChildren(Object element_p) {
      Object value = _input.get(element_p);
      if (value instanceof List<?>) {
        return ((List<?>) value).size() > 0;
      }
      return false;
    }

    /**
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
     */
    public void inputChanged(Viewer viewer_p, Object oldInput_p, Object newInput_p) {
      if (newInput_p instanceof Map<?, ?>) {
        _input = (Map<?, ?>) newInput_p;
      } else {
        _input = null;
      }
    }
  }

  /**
   * This label provider allows to display model elements.
   * @author T0052089
   */
  public static class EnvironmentVariablesLabelProvider extends AbstractLabelProvider {
    /**
     * A context is needed to generate image and name for elements.
     */
    protected final IEditionContextProvider _contextProvider;

    /**
     * Constructor.
     * @param editionContextProvider_p
     */
    public EnvironmentVariablesLabelProvider(IEditionContextProvider editionContextProvider_p) {
      _contextProvider = editionContextProvider_p;
    }

    /**
     * /!\ This method is used by the ViewerSorter, if it is removed, no sort will be done !
     * @see com.thalesgroup.orchestra.framework.ui.viewer.AbstractLabelProvider#getText(java.lang.Object)
     */
    @Override
    public String getText(Object element_p) {
      // Preconditions.
      Context editionContext = _contextProvider.getEditionContext();
      if (null == editionContext) {
        return null;
      }
      if (!(element_p instanceof EObject)) {
        return null;
      }
      // Generate text.
      return getText((EObject) element_p, editionContext);
    }

    /**
     * @see org.eclipse.jface.viewers.CellLabelProvider#update(org.eclipse.jface.viewers.ViewerCell)
     */
    @Override
    public void update(ViewerCell cell_p) {
      // Preconditions.
      Context editionContext = _contextProvider.getEditionContext();
      if (null == editionContext) {
        return;
      }
      Object element = cell_p.getElement();
      if (!(element instanceof EObject)) {
        return;
      }
      // Update cell content.
      cell_p.setText(getText((EObject) element, editionContext));
      cell_p.setImage(getImage((EObject) element, editionContext));
    }
  }
}
