/**
 * Copyright (c) THALES, 2013. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environments.logger;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.PropertyConfigurator;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IStatus;

import com.thalesgroup.orchestra.framework.FrameworkActivator;
import com.thalesgroup.orchestra.framework.common.CommonActivator;

/**
 * @author S0035580
 */
public class OrchestraEnvironmentsHubMakeBaselineLogger {
  private static org.apache.log4j.Logger _log = org.apache.log4j.Logger.getLogger(OrchestraEnvironmentsHubMakeBaselineLogger.class);

  public static void initialize(String baselineContentDestination_p) {
    try {
      // set the logname
      StringBuilder logNameBuilder = new StringBuilder();
      if ((null != baselineContentDestination_p) && !baselineContentDestination_p.isEmpty()) {
        logNameBuilder.append(baselineContentDestination_p);
      }
      logNameBuilder.append("\\MakeBaseline_"); //$NON-NLS-1$
      Calendar currentTime = Calendar.getInstance();
      // Define the name of the baseline file. A VALIDER !!!!
      SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd_HH_mm_ss"); //$NON-NLS-1$
      logNameBuilder.append(formatDate.format(currentTime.getTime()));
      System.setProperty("logname", logNameBuilder.toString()); //$NON-NLS-1$
      // Setup logging
      URL confURL = FrameworkActivator.getDefault().getBundle().getEntry("log4j.properties"); //$NON-NLS-1$
      PropertyConfigurator.configure(FileLocator.toFileURL(confURL).getFile());
    } catch (IOException exception_p) {
      StringBuilder loggerMessage = new StringBuilder("OrchestraEnvironmentsHubMakeBaselineLogger.OrchestraEnvironmentsHubMakeBaselineLogger(..) _ "); //$NON-NLS-1$
      CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.ERROR, exception_p);
    }
  }

  public static void log(String pluginId_p, String message_p, Loglevel loglevel_p) {
    log(pluginId_p, message_p, loglevel_p, null);
  }

  public static void log(String pluginId_p, String message_p, Loglevel loglevel_p, Exception e_p) {
    // Format message
    String formattedMsg = "[" + pluginId_p + "] " + message_p; //$NON-NLS-1$ //$NON-NLS-2$
    if (e_p != null) {
      formattedMsg = formattedMsg + ". Exception: " + e_p.toString(); //$NON-NLS-1$
    }

    // log in the baseline directory : MakeBaseline_#Current_TIME#.log
    switch (loglevel_p) {
      case ALL:
        _log.info(formattedMsg);
      break;
      case DEBUG:
        _log.debug(formattedMsg);
      break;
      case ERROR:
        _log.error(formattedMsg);
      break;
      case FATAL:
        _log.fatal(formattedMsg);
      break;
      case INFO:
        _log.info(formattedMsg);
      break;
      case OFF:
        _log.info(formattedMsg);
      break;
      case TRACE:
        _log.trace(formattedMsg);
      break;
      case WARN:
        _log.warn(formattedMsg);
      break;
      default:
        _log.info(formattedMsg);
      break;
    }
  }

  public static enum Loglevel {
    ALL, DEBUG, ERROR, FATAL, INFO, OFF, TRACE, WARN
  }

}
