/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.common.job;

import java.util.concurrent.CountDownLatch;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;

/**
 * A dedicated job implementation that waits for the end of the job execution before returning to the caller.<br>
 * Use {@link #execute()} method to achieve this goal (instead of {@link #schedule()} one).
 * @author t0076261
 */
public abstract class AbstractSynchronousJob extends Job {
  /**
   * Blocking queue.
   */
  private CountDownLatch _latch;

  /**
   * Constructor.
   * @param name_p
   */
  public AbstractSynchronousJob(String name_p) {
    super(name_p);
    _latch = new CountDownLatch(1);
  }

  /**
   * Do run the job process.<br>
   * @param monitor_p
   * @return
   */
  protected abstract IStatus doRun(IProgressMonitor monitor_p);

  /**
   * Execute job.<br>
   * Should be used instead of {@link #schedule()} to ensure synchronous execution.
   */
  public void execute() {
    schedule();
    try {
      _latch.await();
    } catch (InterruptedException exception_p) {
      // Silently die.
    }
  }

  /**
   * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  protected IStatus run(IProgressMonitor monitor_p) {
    IStatus result = doRun(monitor_p);
    _latch.countDown();
    return result;
  }
}