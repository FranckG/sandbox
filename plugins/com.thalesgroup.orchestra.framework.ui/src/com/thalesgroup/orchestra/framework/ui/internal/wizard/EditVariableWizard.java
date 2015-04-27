/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.wizard;

import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractEditVariablePage;
import com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractReadOnlyEditVariablePage;

/**
 * Edit variable wizard structure.
 * @author t0076261
 */
public class EditVariableWizard extends AbstractVariableWizard {
  /**
   * Edition context.
   */
  protected Context _context;

  /**
   * Constructor.
   * @param variable_p
   * @param editionContext_p
   */
  public EditVariableWizard(Variable variable_p, Context editionContext_p) {
    setObject(variable_p);
    _context = editionContext_p;
    setNeedsProgressMonitor(false);
    setWindowTitle(Messages.EditVariableWizard_Wizard_Title);
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#addPages()
   */
  @Override
  public void addPages() {
    if (!DataUtil.isUnmodifiable(getObject(), _context)) {
      addPage(new AbstractEditVariablePage("EditVariable") { //$NON-NLS-1$
        /**
         * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractEditVariablePage#getVariable()
         */
        @SuppressWarnings("synthetic-access")
        @Override
        protected AbstractVariable getVariable() {
          return getObject();
        }

        /**
         * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractEditVariablePage#getEditionContext()
         */
        @Override
        protected Context getEditionContext() {
          return _context;
        }
      });
    } else {
      // Variable isn't modifiable, use a read-only page to display it.
      addPage(new AbstractReadOnlyEditVariablePage("ReadOnlyEditVariable") { //$NON-NLS-1$
        /**
         * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractEditVariablePage#getVariable()
         */
        @SuppressWarnings("synthetic-access")
        @Override
        protected AbstractVariable getVariable() {
          return getObject();
        }

        /**
         * @see com.thalesgroup.orchestra.framework.ui.internal.wizard.page.AbstractEditVariablePage#getEditionContext()
         */
        @Override
        protected Context getEditionContext() {
          return _context;
        }
      });
    }
  }
}