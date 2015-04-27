/**
> * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.handler.command;

import com.thalesgroup.orchestra.framework.model.handler.data.ContextsEditingDomain.ClipboardElement;

/**
 * @author T0052089
 */
public interface IClipboardContentChangedListener {
  /**
   * Called after an addition in the clipboard.
   * @param clipboardElement_p
   */
  public void clipboardElementAdded(ClipboardElement clipboardElement_p);

  /**
   * Called after a removal from the clipboard.
   * @param clipboardElement_p
   */
  public void clipboardElementRemoved(ClipboardElement clipboardElement_p);
}
