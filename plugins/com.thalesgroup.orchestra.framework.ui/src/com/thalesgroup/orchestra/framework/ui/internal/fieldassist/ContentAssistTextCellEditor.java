/*******************************************************************************
 *  Copyright (c) 2007, 2009 By  La Carotte Et Le Baton.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 * 
 *  Contributors:
 *      La Carotte Et Le Baton - initial API and implementation
 *******************************************************************************/
package com.thalesgroup.orchestra.framework.ui.internal.fieldassist;

import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;

/**
 * @author La Carotte
 */
public class ContentAssistTextCellEditor extends TextCellEditor {
  /**
   * Content assist support.
   */
  private AutoCompleteField _contentAssist;
  private volatile boolean _ignoreEsc;

  /**
   * Constructor
   */
  public ContentAssistTextCellEditor() {
    super();
    _ignoreEsc = false;
  }

  /**
   * Constructor.
   * @param parent_p
   */
  public ContentAssistTextCellEditor(Composite parent_p) {
    super(parent_p);
  }

  /**
   * Constructor
   * @param parent_p
   * @param style_p
   */
  public ContentAssistTextCellEditor(Composite parent_p, int style_p) {
    super(parent_p, style_p);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void fireCancelEditor() {
    if (!_ignoreEsc) {
      super.fireCancelEditor();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void dispose() {
    if (null != _contentAssist) {
      _contentAssist = null;
    }
    super.dispose();
  }

  public void updateProposals(String[] proposals_p) {
    if (null != _contentAssist) {
      _contentAssist.setProposals(proposals_p);
    }
  }

  @SuppressWarnings("synthetic-access")
  public void installContentAssist(String[] proposals_p) {
    if (null == _contentAssist) {
      _contentAssist = new AutoCompleteField(getControl(), new TextContentAdapter(), proposals_p);
      // Install a listener to detect Popup open & close events, according to that events ESC key is ignored or not by the cell editor.
      // Indeed, ESC key closes the content assist popup to ignore the proposals and ESC must not be propagated to the cell editor.
      _contentAssist.getAdapter().addContentProposalListener(new ContentProposalAdapter3() {

        @Override
        public void proposalPopupOpened(ContentProposalAdapter adapter_p) {
          _ignoreEsc = true;
        }

        @Override
        public void proposalPopupClosed(ContentProposalAdapter adapter_p) {
          _ignoreEsc = false;
        }

        @Override
        public void proposalAccepted(IContentProposal proposal_p) {
          // Install a listener to automatically accept a proposal in the cell editor.
          fireApplyEditorValue();
        }
      });
    }
  }
}
