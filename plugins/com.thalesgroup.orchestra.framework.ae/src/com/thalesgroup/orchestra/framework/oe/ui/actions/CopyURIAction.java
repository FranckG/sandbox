/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.oe.ui.actions;

import java.util.List;

import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;

import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;
import com.thalesgroup.orchestra.framework.oe.artefacts.internal.Artefact;
import com.thalesgroup.orchestra.framework.oe.artefacts.internal.RootArtefactsBag;
import com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.ArtefactNode;
import com.thalesgroup.orchestra.framework.root.ui.AbstractRunnableWithDisplay;
import com.thalesgroup.orchestra.framework.root.ui.DisplayHelper;

/**
 * Copy selected artefact URI to clipboard
 * @author s0040806
 */
public class CopyURIAction extends ArtefactAction {
  /**
   * Number of selected elements.
   */
  private int _elementsCount;

  /**
   * @param string_p
   */
  public CopyURIAction() {
    super(Messages.CopyURIAction_CopyURILabel);
    setToolTipText(Messages.CopyURIAction_CopyURITooltip);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.ui.actions.ArtefactAction#calculateEnabledState()
   */
  @Override
  protected boolean calculateEnabledState() {
    _elementsCount = 0;
    return applyEnablementConditionToSelection();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.ui.actions.ArtefactAction#isEnablingCondition(com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.ArtefactNode)
   */
  @Override
  protected boolean isEnablingCondition(ArtefactNode artefactNode_p) {
    _elementsCount++;
    // Only applicable to one element.
    if (_elementsCount > 1) {
      return false;
    }
    // Get underlying model element.
    Artefact artefact = artefactNode_p.getValue();
    if (null != artefact) {
      // Precondition.
      if (RootArtefactsBag.getInstance().isOld(artefact)) {
        return false;
      }
      return true;
    }
    return false;
  }

  /**
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    List<ArtefactNode> currentTreeSelection = getCurrentTreeSelection(ArtefactNode.class);
    if (1 == currentTreeSelection.size()) {
      // Retrieve Orchestra URI from selected artefact
      final OrchestraURI uri = currentTreeSelection.get(0).getValue().getUri();

      // Copy URI to clipboard as text
      DisplayHelper.displayRunnable(new AbstractRunnableWithDisplay() {
        public void run() {
          Display display = Display.getCurrent();
          Clipboard clipboard = new Clipboard(display);
          clipboard.setContents(new Object[] { uri.getUri() }, new Transfer[] { TextTransfer.getInstance() });
          clipboard.dispose();
        }
      }, false);

    }
  }

}
