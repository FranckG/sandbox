/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.environment;

import org.osgi.framework.BundleContext;

import com.thalesgroup.orchestra.framework.environment.filesystem.ITranscriptionProvider;
import com.thalesgroup.orchestra.framework.environment.registry.EnvironmentRegistry;
import com.thalesgroup.orchestra.framework.environment.ui.IVariablesHandler;
import com.thalesgroup.orchestra.framework.root.ui.activator.AbstractUIActivator;

/**
 * @author t0076261
 */
public class EnvironmentActivator extends AbstractUIActivator {
  /**
   * Shared instance.
   */
  private static EnvironmentActivator __instance;
  /**
   * Environment registry unique instance.
   */
  private EnvironmentRegistry _environmentRegistry;
  /**
   * Transcription provider unique instance.
   */
  private ITranscriptionProvider _transcriptionProvider;
  /**
   * Variables handler instances in-use (each thread must have its own copy to avoid interferences between multiple threads).
   */
  private final ThreadLocal<IVariablesHandler> _variablesHandler = new ThreadLocal<IVariablesHandler>();

  /**
   * Get environment registry unique instance.
   * @return
   */
  public EnvironmentRegistry getEnvironmentRegistry() {
    if (null == _environmentRegistry) {
      _environmentRegistry = new EnvironmentRegistry();
    }
    return _environmentRegistry;
  }

  /**
   * Get transcription provider.
   * @return
   */
  public ITranscriptionProvider getTranscriptionProvider() {
    return _transcriptionProvider;
  }

  /**
   * Get variables handler instance in-use for calling thread.
   * @return
   */
  protected IVariablesHandler getVariablesHandler() {
    return _variablesHandler.get();
  }

  /**
   * Set transcription provider.
   * @param transcriptionProvider_p
   */
  public void setTranscriptionProvider(ITranscriptionProvider transcriptionProvider_p) {
    _transcriptionProvider = transcriptionProvider_p;
  }

  /**
   * Set variables handler instance in-use for calling thread.<br>
   * Note that the handler is provided by Framework implementation at runtime.<br>
   * Implementors of environments should not try to set the handler reference for calling thread.
   * @param variablesHandler_p
   */
  public void setVariablesHandler(IVariablesHandler variablesHandler_p) {
    _variablesHandler.set(variablesHandler_p);
  }

  /**
   * Set variables handler instance, if none is already known for this thread.<br>
   * See {@link #setVariablesHandler(IVariablesHandler)} for details.
   * @param variablesHandler_p
   * @return <code>true</code> if specified version was set, <code>false</code> if it was ignored.
   */
  public boolean setVariablesHandlerIfNone(IVariablesHandler variablesHandler_p) {
    if (null == getVariablesHandler()) {
      setVariablesHandler(variablesHandler_p);
      return true;
    }
    return false;
  }

  /**
   * @see org.eclipse.core.runtime.Plugin#start(org.osgi.framework.BundleContext)
   */
  @Override
  public void start(BundleContext context_p) throws Exception {
    super.start(context_p);
    __instance = this;
  }

  /**
   * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
   */
  @Override
  public void stop(BundleContext context_p) throws Exception {
    super.stop(context_p);
    __instance = null;
  }

  /**
   * Get shared instance.
   * @return
   */
  public static EnvironmentActivator getInstance() {
    return __instance;
  }
}