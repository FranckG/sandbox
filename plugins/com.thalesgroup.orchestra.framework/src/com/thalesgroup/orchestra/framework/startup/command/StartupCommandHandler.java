/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.startup.command;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;

import com.thalesgroup.orchestra.framework.common.CommonActivator;
import com.thalesgroup.orchestra.framework.common.helper.ExtensionPointHelper;
import com.thalesgroup.orchestra.framework.lib.utils.uri.BadOrchestraURIException;
import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;
import com.thalesgroup.orchestra.framework.puci.PUCI;

/**
 * Launch contributed startup commands
 * @author s0040806
 */
public class StartupCommandHandler {

  private static StartupCommandHandler __instance;

  public static StartupCommandHandler getInstance() {
    if (null == __instance) {
      return new StartupCommandHandler();
    }
    return __instance;
  }

  public void launchCommands() {
    new Thread() {
      /**
       * @see java.lang.Thread#run()
       */
      @Override
      public void run() {
        IConfigurationElement[] configurationElements = ExtensionPointHelper.getConfigurationElements("com.thalesgroup.orchestra.framework", "startupCommand");

        // For all startup commands
        for (IConfigurationElement configurationElement : configurationElements) {
          String commandName = configurationElement.getAttribute("name");

          String uri = null;
          List<OrchestraURI> uriList = new ArrayList<OrchestraURI>();
          try {
            // Build the Orchestra URI list
            for (IConfigurationElement child : configurationElement.getChildren()) {
              uri = child.getAttribute("uri");
              uriList.add(new OrchestraURI(uri));
            }

            // Execute specific command
            PUCI.executeSpecificCommand(commandName, uriList);
          } catch (BadOrchestraURIException exception_p) {
            // In case at least one URI is erroneous, do not call the startup command
            CommonActivator.getInstance().logMessage(MessageFormat.format(Messages.StartupCommandHandler_Error_WrongOrchestraURI, uri), IStatus.ERROR,
                exception_p);
          } catch (Exception exception_p) {
            List<String> uriListStr = new ArrayList<String>();
            for (OrchestraURI item : uriList) {
              uriListStr.add(item.toString());
            }
            CommonActivator.getInstance().logMessage(
                MessageFormat.format(Messages.StartupCommandHandler_Error_SpecificCommandFailed, commandName, uriListStr.toString()), IStatus.ERROR,
                exception_p);
          }
        }
      }
    }.start();
  }
}
