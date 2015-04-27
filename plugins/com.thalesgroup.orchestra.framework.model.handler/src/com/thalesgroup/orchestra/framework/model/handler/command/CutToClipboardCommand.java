/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.handler.command;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.change.ChangeDescription;

import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;

/**
 * Cut to clipboard command.<br>
 * This command does not harm the model, but {@link PasteFromClipboardCommand} might, as a result.<br>
 * @author t0076261
 */
public class CutToClipboardCommand extends CopyToClipboardCommand {
  /**
   * Internal description.
   */
  protected ChangeDescription _description;

  /**
   * Constructor.
   * @param selectedElements_p
   * @param context_p
   */
  public CutToClipboardCommand(Collection<?> selectedElements_p, Context context_p) {
    super(selectedElements_p, context_p);
    setLabel(Messages.CutToClipboardCommand_Label);
    setDescription(Messages.CutToClipboardCommand_Description);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.handler.command.CopyToClipboardCommand#prepare()
   */
  @Override
  protected boolean prepare() {
    boolean result = super.prepare();
    if (result) {
      for (EObject element : _filteredSelectedModelElements) {
        // One can only cut if element :
        // - is indeed belonging to edition context,
        // - is a modifiable EObject in edition context.
        Context elementContext = ModelUtil.getContext(element);
        result &= (elementContext == _editionContext) && !DataUtil.isUnmodifiable(element, _editionContext);
        // Stop here.
        if (!result) {
          break;
        }
      }
    }
    return result;
  }
}