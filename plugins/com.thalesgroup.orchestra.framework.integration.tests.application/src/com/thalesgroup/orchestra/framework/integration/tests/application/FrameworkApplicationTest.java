/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.integration.tests.application;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Platform;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

import com.thalesgroup.orchestra.framework.common.helper.ExtensionPointHelper;
import com.thalesgroup.orchestra.framework.integration.tests.application.handler.IPrePostTestHandler;

/**
 * @author S0024585
 */
public class FrameworkApplicationTest implements IApplication {

  public static final String ORCHESTRA_FRAMEWORK_APPLICATION_ID = "com.thalesgroup.orchestra.framework.application"; //$NON-NLS-1$

  /**
   * Actions defined.
   */
  List<IPrePostTestHandler> _actions = new ArrayList<IPrePostTestHandler>();

  /**
   * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.IApplicationContext)
   */
  @Override
  public Object start(IApplicationContext context_p) throws Exception {

    // Get the pre-post actions extension, if present
    IConfigurationElement[] prePostExtensionElements =
        ExtensionPointHelper.getConfigurationElements(FrameworkApplicationTestActivator.getDefault().getPluginId(), "applicationTestHandler"); //$NON-NLS-1$
    if (prePostExtensionElements.length > 0) {
      for (IConfigurationElement iConfigurationElement : prePostExtensionElements) {
        IPrePostTestHandler instance = (IPrePostTestHandler) ExtensionPointHelper.createInstance(iConfigurationElement, ExtensionPointHelper.ATT_CLASS);
        _actions.add(instance);
        instance.preAction();
      }
    }

    IExtension extension = Platform.getExtensionRegistry().getExtension(Platform.PI_RUNTIME, Platform.PT_APPLICATIONS, ORCHESTRA_FRAMEWORK_APPLICATION_ID);
    // If the extension does not have the correct grammar, return null.
    // Otherwise, return the application object.
    if (null != extension) {
      IConfigurationElement[] elements = extension.getConfigurationElements();
      if (elements.length > 0) {
        IConfigurationElement[] runs = elements[0].getChildren("run"); //$NON-NLS-1$
        if (runs.length > 0) {
          Object runnable = runs[0].createExecutableExtension(ExtensionPointHelper.ATT_CLASS);
          if (runnable instanceof IApplication) {
            IApplication application = (IApplication) runnable;
            application.start(context_p);
          }
        }
      }
    }
    // Execute post handler.
    for (IPrePostTestHandler action : _actions) {
      action.postAction();
    }
    return null;
  }

  /**
   * @see org.eclipse.equinox.app.IApplication#stop()
   */
  @Override
  public void stop() {
    // Nothing to do.
  }

}
