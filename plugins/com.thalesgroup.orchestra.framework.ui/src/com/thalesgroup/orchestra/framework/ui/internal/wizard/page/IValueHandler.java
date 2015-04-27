/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.wizard.page;

import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;

/**
 * A single value handler.
 * @author t0076261
 */
public interface IValueHandler {
  /**
   * Edition of current value has been cancelled.
   */
  public void editionCancelled();

  /**
   * Edition of current value is finished (validated).
   */
  public void editionFinished();

  /**
   * Get context which edition takes place for.
   * @return A not <code>null</code> {@link Context}.
   */
  public Context getEditionContext();

  /**
   * Get current variable value.
   * @return
   */
  public String getValue();

  /**
   * Get value index within containing list.
   * @return <code>-1</code> if no value is currently selected, a positive integer otherwise.
   */
  public int getValueIndex();

  /**
   * Get targeted value container.
   * @return A not <code>null</code> variable. Always a leaf type, not an overriding one.
   */
  public AbstractVariable getVariable();

  /**
   * Get handled variable value, if any.
   * @return <code>null</code> if none.
   */
  public VariableValue getVariableValue();

  /**
   * Insert new value at current variable value position.<br>
   * This is not the equivalent of {@link #setNewValue(String)} unless there is no existing value yet.
   * @param newValue_p
   */
  public void insertValue(String newValue_p);

  /**
   * Is specified variable handled by this handler ?
   * @param variable_p
   * @return
   */
  public boolean isHandling(AbstractVariable variable_p);

  /**
   * Set new value for current variable value.
   * @param newValue_p
   */
  public void setNewValue(String newValue_p);
}