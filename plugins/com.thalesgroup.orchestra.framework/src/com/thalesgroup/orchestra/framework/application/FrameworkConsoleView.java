/**
 * <p>
 * Copyright (c) 2002-2008 : Thales Services - Engineering & Process Management
 * </p>
 * <p>
 * Société : Thales Services - Engineering & Process Management
 * </p>
 * <p>
 * Thales Part Number 16 262 601
 * </p>
 */
package com.thalesgroup.orchestra.framework.application;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.eclipse.ui.internal.console.ConsoleView;

/**
 * <p>
 * Title : PapeeteConsoleView
 * </p>
 * <p>
 * Description : Papeete Console
 * </p>
 * @author Papeete Tool Suite developer
 * @version 3.7.0
 */
public class FrameworkConsoleView extends ConsoleView {
  private static MessageConsole __messageConsole;
  private static Buffer __preBuffer = new Buffer();
  private static Map<LogLevel, MessageConsoleStream> __streams = new HashMap<LogLevel, MessageConsoleStream>(0);
  private static boolean _minimizedInSysTrayOnly = true;
  public static final String _NAME = "Orchestra Framework Console"; //$NON-NLS-1$
  public static final String ID = "PapeeteConsole.view"; //$NON-NLS-1$

  /**
   * This is a callback that will allow us to create the viewer and initialize it.
   */
  @Override
  public void createPartControl(Composite parent) {
    super.createPartControl(parent);
    initConsole(_NAME);
  }

  @SuppressWarnings("boxing")
  private void initConsole(String name) {
    Display display = getDisplay();
    // Precondition.
    if (null == display) {
      return;
    }
    boolean exists = false;
    ConsolePlugin plugin = ConsolePlugin.getDefault();
    IConsoleManager conMan = plugin.getConsoleManager();
    IConsole[] existing = conMan.getConsoles();
    for (int i = 0; i < existing.length && !exists; i++) {
      if (name.equals(existing[i].getName())) {
        __messageConsole = (MessageConsole) existing[i];
        exists = true;
        break;
      }
    }
    // no console found, so create a new one
    if (!exists) {
      __messageConsole = new MessageConsole(name, null);
      conMan.addConsoles(new IConsole[] { __messageConsole });
    }
    __messageConsole.setConsoleWidth(800);
    __messageConsole.activate();
    __messageConsole.setAttribute("editable", true); //$NON-NLS-1$
    MessageConsoleStream okStream = __messageConsole.newMessageStream();
    okStream.setColor(new Color(display, new RGB(0, 0, 0))); // Black
    __streams.put(LogLevel.OK, okStream);
    MessageConsoleStream infoStream = __messageConsole.newMessageStream();
    infoStream.setColor(new Color(display, new RGB(0, 0, 255))); // Blue
    __streams.put(LogLevel.INFO, infoStream);
    MessageConsoleStream warningStream = __messageConsole.newMessageStream();
    warningStream.setColor(new Color(display, new RGB(128, 64, 64))); // Sheiss
    __streams.put(LogLevel.WARNING, warningStream);
    MessageConsoleStream errorStream = __messageConsole.newMessageStream();
    errorStream.setColor(new Color(display, new RGB(255, 0, 0))); // Red
    __streams.put(LogLevel.ERROR, errorStream);
    // Flush buffer.
    for (String message : __preBuffer.bufferedMessages) {
      writeMessageToConsole(message, __preBuffer.logLevels.get(message), true);
    }
    __preBuffer.bufferedMessages.clear();
    __preBuffer.logLevels.clear();
  }

  /**
   * @return
   */
  public static String formatCurrentTime() {
    Date date = new Date();
    SimpleDateFormat dateFormatYMD = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss"); //$NON-NLS-1$
    return dateFormatYMD.format(date);
  }

  /**
   * Get workbench display.
   * @return
   */
  private static Display getDisplay() {
    if (PlatformUI.isWorkbenchRunning()) {
      return PlatformUI.getWorkbench().getDisplay();
    }
    return null;
  }

  public static boolean isMinimizedInSysTrayOnly() {
    return _minimizedInSysTrayOnly;
  }

  public static void minimizedInSysTrayOnly(boolean minimizedInSysTrayOnly) {
    _minimizedInSysTrayOnly = minimizedInSysTrayOnly;
  }

  public static void writeErrorMessageToConsole(final String message) {
    writeMessageToConsole(message, LogLevel.ERROR, false);
  }

  public static void writeInfoMessageToConsole(final String message) {
    writeMessageToConsole(message, LogLevel.INFO, false);
  }

  public static void writeMessageToConsole(String message_p, final LogLevel level_p, boolean sync_p) {
    // Precondition.
    if ((null == message_p) || (null == level_p)) {
      return;
    }
    // No stream available, buffer the message, and wait for console to be initialized.
    final MessageConsoleStream messageConsoleStream = __streams.get(level_p);
    final String message = formatCurrentTime() + " " + message_p; //$NON-NLS-1$
    if (null == messageConsoleStream) {
      __preBuffer.bufferedMessages.add(message_p);
      __preBuffer.logLevels.put(message_p, level_p);
      return;
    }
    // Get display.
    Display display = getDisplay();
    // Precondition.
    if (null == display) {
      return;
    }
    // Create write message runnable.
    Runnable messageRunnable = new Runnable() {
      /**
       * @see java.lang.Runnable#run()
       */
      public void run() {
        messageConsoleStream.println(message);
      }
    };
    // Execute it.
    if (sync_p) {
      display.syncExec(messageRunnable);
    } else {
      display.asyncExec(messageRunnable);
    }
  }

  public static void writeServerMessageToConsole(final String message) {
    writeMessageToConsole(message, LogLevel.OK, false);
  }

  public static void writeWarningMessageToConsole(final String message) {
    writeMessageToConsole(message, LogLevel.WARNING, false);
  }

  /**
   * Pre-Buffer.
   * @author t0076261
   */
  protected static class Buffer {
    /**
     * Messages list.
     */
    protected List<String> bufferedMessages = new ArrayList<String>(0);
    /**
     * Message to log level.
     */
    protected Map<String, LogLevel> logLevels = new HashMap<String, LogLevel>(0);
  }

  public static enum LogLevel {
    ERROR, INFO, OK, WARNING
  }
}