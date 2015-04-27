/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.viewer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Category;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ModelElement;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;

/**
 * @author T0052089
 */
public class ModelElementFilter {
  /**
   * Model element filter current state (can never be <code>null</code>).
   */
  protected FilterState _currentFilterState;
  /**
   * Listeners list.
   */
  protected final List<IModelElementFilterListener> _listeners;

  /**
   * Constructor.
   * @param initialFilterState_p initial filter state, can't be <code>null</code>.
   */
  public ModelElementFilter(FilterState initialFilterState_p) {
    if (null == initialFilterState_p) {
      throw new IllegalArgumentException("Model element filter initial state can't be null."); //$NON-NLS-1$
    }
    _currentFilterState = initialFilterState_p;
    _listeners = new ArrayList<IModelElementFilterListener>();
  }

  /**
   * Add a new listener.
   * @param listener_p
   */
  public void addListener(IModelElementFilterListener listener_p) {
    if (!_listeners.contains(listener_p)) {
      _listeners.add(listener_p);
    }
  }

  /**
   * Return current filter state.
   * @return
   */
  public FilterState getCurrentFilterState() {
    return _currentFilterState;
  }

  /**
   * Should given model element (in given context) be highlighted ?
   * @param modelElement_p
   * @param editionContext_p
   * @return
   */
  public boolean highlight(ModelElement modelElement_p, Context editionContext_p) {
    if (!_currentFilterState.isFiltering()) {
      return false;
    }
    if (modelElement_p instanceof Category) {
      Category category = (Category) modelElement_p;
      // Highlight a category if it directly contains selected variables.
      Collection<AbstractVariable> vars = DataUtil.getVariables(category, editionContext_p);
      for (AbstractVariable abstractVariable : vars) {
        if (selectVariable(abstractVariable, editionContext_p)) {
          return true;
        }
      }
      if (_currentFilterState._selectContributedElements) {
        // Highlight a category if it is a contributed element.
        return editionContext_p.getSuperCategoryCategories().contains(category);
      }
    }
    return false;
  }

  /**
   * Is given element physically contained in a contributed category.
   * @param modelElement_p
   * @param editionContext_p
   * @return
   */
  protected boolean isPhysicallyContainedInAContributedCategory(ModelElement modelElement_p, Context editionContext_p) {
    EObject currentElement = modelElement_p;
    while (!(currentElement instanceof Context)) {
      if (currentElement instanceof Category && editionContext_p.getSuperCategoryCategories().contains(currentElement)) {
        return true;
      }
      currentElement = currentElement.eContainer();
    }
    return false;
  }

  public void removeListener(IModelElementFilterListener listener_p) {
    _listeners.remove(listener_p);
  }

  /**
   * ModelElementFilter main method. Should given model element (in specified context) be displayed ?
   * @param modelElement_p
   * @param editionContext_p
   * @return
   */
  public boolean select(ModelElement modelElement_p, Context editionContext_p) {
    if (!_currentFilterState.isFiltering()) {
      return true;
    }
    if (modelElement_p instanceof Category) {
      return selectCategory((Category) modelElement_p, editionContext_p);
    }
    if (modelElement_p instanceof AbstractVariable) {
      return selectVariable((AbstractVariable) modelElement_p, editionContext_p);
    }
    // By default other elements are selected.
    return true;
  }

  /**
   * According to current filter state, should given category (in given edition context) be selected ?
   * @param variable_p
   * @param editionContext_p
   * @return
   */
  protected boolean selectCategory(Category category_p, Context editionContext_p) {
    // Does given Category contain a selected Variable ?
    Collection<AbstractVariable> vars = DataUtil.getVariables(category_p, editionContext_p);
    for (AbstractVariable abstractVariable : vars) {
      if (selectVariable(abstractVariable, editionContext_p)) {
        return true;
      }
    }
    // Does given Category contain a selected Category ?
    Collection<Category> lst = DataUtil.getCategories(category_p, editionContext_p);
    for (Category category : lst) {
      if (selectCategory(category, editionContext_p)) {
        return true;
      }
    }
    // Is category a child of a contributed category ?
    if (_currentFilterState._selectContributedElements && isPhysicallyContainedInAContributedCategory(category_p, editionContext_p)) {
      return true;
    }
    return false;
  }

  /**
   * According to current filter state, should given variable (in given edition context) be selected ?
   * @param variable_p
   * @param editionContext_p
   * @return
   */
  protected boolean selectVariable(AbstractVariable variable_p, Context editionContext_p) {
    if (_currentFilterState._selectContributedElements) {
      // Is variable_p directly contributed by given edition context.
      if (editionContext_p.getSuperCategoryVariables().contains(variable_p)) {
        return true;
      }
      // Is variable_p contained (recursively) in a category contributed by given edition context.
      if (isPhysicallyContainedInAContributedCategory(variable_p, editionContext_p)) {
        return true;
      }
    }
    // Is variable_p tagged as "mandatory" and empty ?
    if (_currentFilterState._selectEmptyMandatoryVariables && variable_p instanceof Variable && ((Variable) variable_p).isMandatory()
        && !DataUtil.isVariableFilled(variable_p, editionContext_p)) {
      return true;
    }
    if (_currentFilterState._selectOverriddenVariables) {
      // Is variable_p overridden by an overriding variable in given edition context ?
      OverridingVariable overridingVariable = DataUtil.getOverridingVariable(variable_p, editionContext_p);
      // Add it to result.
      if ((null != overridingVariable) && (editionContext_p == ModelUtil.getContext(overridingVariable))) {
        return true;
      }
    }
    return false;
  }

  /**
   * Set current filter state and notify listeners. If given filter state is <code>null</code> or equals to current state, current filter is not changed and
   * listeners are not notified.
   * @param newFilterState_p
   */
  public void setCurrentFilterState(FilterState newFilterState_p) {
    // Given filter state is null or equals to current filter state -> stop here.
    if (null == newFilterState_p || _currentFilterState.equals(newFilterState_p)) {
      return;
    }
    // Set current filter state.
    _currentFilterState = newFilterState_p;
    // Notify listeners (use a copy of the listeners list to avoid ConcurrentModificationException).
    IModelElementFilterListener[] listenersCopy = _listeners.toArray(new IModelElementFilterListener[0]);
    for (IModelElementFilterListener listener : listenersCopy) {
      listener.modelElementFilterChanged(newFilterState_p);
    }
  }

  /**
   * This class holds ModelElementFilter state. FilterState objects are immutable.
   * @author T0052089
   */
  public static class FilterState {
    /**
     * Should contributed elements be selected ?
     */
    public final boolean _selectContributedElements;
    /**
     * Should contributed empty mandatory variables be selected ?
     */
    public final boolean _selectEmptyMandatoryVariables;
    /**
     * Should overridden variables be selected ?
     */
    public final boolean _selectOverriddenVariables;

    /**
     * Constructor.
     */
    public FilterState(boolean selectContributedElements, boolean selectEmptyMandatoryVariables_p, boolean selectOverriddenVariables_p) {
      _selectContributedElements = selectContributedElements;
      _selectEmptyMandatoryVariables = selectEmptyMandatoryVariables_p;
      _selectOverriddenVariables = selectOverriddenVariables_p;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      FilterState other = (FilterState) obj;
      if (_selectContributedElements != other._selectContributedElements)
        return false;
      if (_selectEmptyMandatoryVariables != other._selectEmptyMandatoryVariables)
        return false;
      if (_selectOverriddenVariables != other._selectOverriddenVariables)
        return false;
      return true;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + (_selectContributedElements ? 1231 : 1237);
      result = prime * result + (_selectEmptyMandatoryVariables ? 1231 : 1237);
      result = prime * result + (_selectOverriddenVariables ? 1231 : 1237);
      return result;
    }

    /**
     * @return <code>true</code> if at least on filter criterion is activated, <code>false</code> otherwise.
     */
    public boolean isFiltering() {
      return _selectContributedElements || _selectEmptyMandatoryVariables || _selectOverriddenVariables;
    }
  }
}
