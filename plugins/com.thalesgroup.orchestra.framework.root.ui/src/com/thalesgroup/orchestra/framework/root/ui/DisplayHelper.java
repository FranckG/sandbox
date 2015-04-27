/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.root.ui;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

/**
 * Various methods about displaying dialogs in expected thread, ...
 * @author t0076261
 */
public class DisplayHelper {
  /**
   * Display error dialog.
   * @param title_p
   * @param message_p
   * @param status_p
   */
  public static void displayErrorDialog(final String title_p, final String message_p, final IStatus status_p) {
    displayRunnable(new AbstractRunnableWithDisplay() {
      public void run() {
        Display display = getDisplay();
        if (null != display) {
          ErrorDialog.openError(display.getActiveShell(), title_p, message_p, status_p);
        }
      }
    }, false);
  }

  /**
   * Display specified {@link Runnable}.<br>
   * That is invoke runnable code within the UI thread, against the main display.<br>
   * Most of the time, that means that specified runnable is needing UI resources, or that wouldn't make much sense.<br>
   * Does only work within a workbench instance.<br>
   * The provided runnable display is always not <code>null</code>.
   * @param runnable_p A not <code>null</code> implementation of UI related behavior, that should be processed.
   * @param asynchronousCall_p <code>true</code> to invoke specified {@link Runnable} asynchronously, <code>false</code> to execute it synchronously.
   */
  public static void displayRunnable(AbstractRunnableWithDisplay runnable_p, boolean asynchronousCall_p) {
    // Precondition.
    if (null == runnable_p) {
      return;
    }
    // Get shared instance of the display, within the workbench.
    Display display = null;
    if (PlatformUI.isWorkbenchRunning() && !PlatformUI.getWorkbench().isClosing()) {
      display = PlatformUI.getWorkbench().getDisplay();
    }
    // Try and get current one.
    if (null == display) {
      display = Display.getCurrent();
      // Most probably headless, stop here.
      if (null == display) {
        return;
      }
    }
    // Set display.
    runnable_p.setDisplay(display);
    if (asynchronousCall_p) {
      // Asynchronous call.
      display.asyncExec(runnable_p);
    } else if (null == Display.getCurrent()) {
      // Synchronous code, caller is not in the correct thread.
      display.syncExec(runnable_p);
    } else {
      // Synchronous code, caller is already in the correct thread.
      runnable_p.run();
    }
  }

  /**
   * Is calling thread able to access a display ?
   * @return <code>true</code> if so, <code>false</code> otherwise (most probably headless case).
   */
  public static boolean hasAnAccessibleDisplay() {
    // Get shared instance of the display, within the workbench.
    Display display = null;
    if (PlatformUI.isWorkbenchRunning() && !PlatformUI.getWorkbench().isClosing()) {
      display = PlatformUI.getWorkbench().getDisplay();
    }
    // Try and get current one.
    if (null == display) {
      display = Display.getCurrent();
    }
    // If display is still null, then the application is probably an headless one.
    return (null != display);
  }
}