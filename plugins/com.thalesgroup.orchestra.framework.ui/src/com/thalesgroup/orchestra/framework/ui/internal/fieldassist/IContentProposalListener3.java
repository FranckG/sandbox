package com.thalesgroup.orchestra.framework.ui.internal.fieldassist;

/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

import org.eclipse.jface.fieldassist.IContentProposal;

/**
 * This interface is used to listen to notifications from a {@link ContentProposalAdapter}.
 */
public interface IContentProposalListener3 {
  /**
   * A content proposal has been accepted.
   * @param proposal the accepted content proposal
   */
  public void proposalAccepted(IContentProposal proposal);

  /**
   * A content proposal popup has been opened for content proposal assistance.
   * @param adapter the ContentProposalAdapter which is providing content proposal behavior to a control
   */
  public void proposalPopupOpened(ContentProposalAdapter adapter);

  /**
   * A content proposal popup has been closed.
   * @param adapter the ContentProposalAdapter which is providing content proposal behavior to a control
   */
  public void proposalPopupClosed(ContentProposalAdapter adapter);
}
