/*******************************************************************************
 * Copyright (c) 2006, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.thalesgroup.orchestra.framework.ui.internal.fieldassist;

import org.eclipse.jface.fieldassist.IContentProposalProvider;
import org.eclipse.jface.fieldassist.IControlContentAdapter;
import org.eclipse.jface.fieldassist.SimpleContentProposalProvider;
import org.eclipse.swt.widgets.Control;

/**
 * AutoCompleteField is a class which attempts to auto-complete a user's keystrokes by activating a popup that filters a list of proposals according to the
 * content typed by the user.
 * <p>
 * <b>Copied</b> from org.eclipse.jface.fieldassist to add {@link #getAdapter()} method only.
 * @see ContentProposalAdapter
 * @see SimpleContentProposalProvider
 * @since 3.3
 */
public class AutoCompleteField {

  private SimpleContentProposalProvider _proposalProvider;
  private ContentProposalAdapter _adapter;

  /**
   * Construct an AutoComplete field on the specified control, whose completions are characterized by the specified array of Strings.
   * @param control_p the control for which autocomplete is desired. May not be <code>null</code>.
   * @param controlContentAdapter_p the <code>IControlContentAdapter</code> used to obtain and update the control's contents. May not be <code>null</code>.
   * @param proposals_p the array of Strings representing valid content proposals for the field.
   */
  public AutoCompleteField(Control control_p, IControlContentAdapter controlContentAdapter_p, String[] proposals_p) {
    _proposalProvider = new SimpleContentProposalProvider(proposals_p);
    _proposalProvider.setFiltering(true);
    _adapter = createContentProposalAdapter(control_p, controlContentAdapter_p, _proposalProvider);
    _adapter.setPropagateKeys(true);
    _adapter.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);
  }

  /**
   * Create content proposal dapter.
   * @param control_p
   * @param controlContentAdapter_p
   * @param proposalProvider_p
   * @return a not <code>null</code> instance.
   */
  protected ContentProposalAdapter createContentProposalAdapter(Control control_p, IControlContentAdapter controlContentAdapter_p,
      IContentProposalProvider proposalProvider_p) {
    return new ContentProposalAdapter(control_p, controlContentAdapter_p, proposalProvider_p, null, null);
  }

  /**
   * Get the the content proposal adapter
   * @return the adapter
   */
  public ContentProposalAdapter getAdapter() {
    return _adapter;
  }

  /**
   * Set the Strings to be used as content proposals.
   * @param _p the array of Strings to be used as proposals.
   */
  public void setProposals(String[] _p) {
    _proposalProvider.setProposals(_p);
  }
}