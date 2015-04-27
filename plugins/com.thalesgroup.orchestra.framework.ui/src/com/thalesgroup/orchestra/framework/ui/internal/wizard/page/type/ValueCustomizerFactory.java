/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.wizard.page.type;

import org.eclipse.emf.ecore.EObject;

import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariable;
import com.thalesgroup.orchestra.framework.model.contexts.FileVariable;
import com.thalesgroup.orchestra.framework.model.contexts.FolderVariable;
import com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch;
import com.thalesgroup.orchestra.framework.ui.internal.wizard.page.IValueHandler;

/**
 * Customizers factory.
 * @author s0011584
 */
public class ValueCustomizerFactory {
  /**
   * Creates the proper implementation of {@link DefaultValueCustomizer} per variable type.
   * @param object_p the variable implementation.
   */
  public static DefaultValueCustomizer createVariableValueEditionHandlerFor(Object object_p, final Context editionContext_p, final IValueHandler handler_p) {
    // Precondition.
    if ((null == editionContext_p) || (null == handler_p)) {
      return null;
    }
    if (object_p instanceof EObject) {
      return new ContextsSwitch<DefaultValueCustomizer>() {
        /**
         * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseFileVariable(com.thalesgroup.orchestra.framework.model.contexts.FileVariable)
         */
        @Override
        public DefaultValueCustomizer caseFileVariable(FileVariable fileVariable_p) {
          return new FileVariableValueCustomizer(editionContext_p, handler_p);
        }

        /**
         * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseFolderVariable(com.thalesgroup.orchestra.framework.model.contexts.FolderVariable)
         */
        @Override
        public DefaultValueCustomizer caseFolderVariable(FolderVariable folderVariable_p) {
          return new FolderVariableValueCustomizer(editionContext_p, handler_p);
        }

        /**
         * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseEnvironmentVariable(com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariable)
         */
        @Override
        public DefaultValueCustomizer caseEnvironmentVariable(EnvironmentVariable environmentVariable_p) {
          return new EnvironmentValueCustomizer(editionContext_p, handler_p);
        }

        /**
         * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseAbstractVariable(com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable)
         */
        @Override
        public DefaultValueCustomizer caseAbstractVariable(AbstractVariable variable_p) {
          return new DefaultValueCustomizer(editionContext_p, handler_p);
        }
      }.doSwitch((EObject) object_p);
    }
    return null;
  }
}