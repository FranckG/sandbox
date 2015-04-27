/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.common.model;

import java.io.IOException;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.thalesgroup.orchestra.framework.common.CommonActivator;

/**
 * Model handler.<br>
 * Allows for reading/writing of files contents.
 * @author t0076261
 */
public abstract class AbstractModelHandler<M extends EObject> {
  /**
   * Inner models bag.
   */
  private ResourceSet _resourceSet;

  /**
   * Constructor.
   */
  public AbstractModelHandler() {
    _resourceSet = new ResourceSetImpl();
    initialize(_resourceSet);
  }

  /**
   * Clean handler.<br>
   * Warning ! All models currently handled will be unloaded.<br>
   * The handler is not invalidated after this call, but is empty.
   */
  public void clean() {
    for (Resource resource : _resourceSet.getResources()) {
      resource.unload();
    }
    _resourceSet.getResources().clear();
  }

  /**
   * Create model for specified absolute location.<br>
   * Note that the model is an empty new one for this location, and thus is not saved yet.<br>
   * In order to persist it, a call to {@link #saveModel(GEF)} is needed when the model is complete.
   * @param fileModelAbsolutePath_p A not <code>null</code> absolute file path to the future model location.
   * @return <code>null</code> if the model could not be created. A new {@link GEF} root element instance otherwise.
   */
  public M createNewModel(String fileModelAbsolutePath_p) {
    // Precondition.
    if (null == fileModelAbsolutePath_p) {
      return null;
    }
    Resource resource = _resourceSet.createResource(URI.createFileURI(fileModelAbsolutePath_p));
    // Create model of interest.
    M createdModel = createRootElement();
    // Attach tree.
    resource.getContents().add(EcoreUtil.getRootContainer(createdModel));
    return createdModel;
  }

  /**
   * Create new model root element.
   * @return A not <code>null</code> instance of model root.
   */
  protected abstract M createRootElement();

  /**
   * @return The file extension for this type of model.
   */
  public abstract String getFileExtension();

  /**
   * Get root element from specified one.
   * @param element_p
   * @return <code>null</code> if no root element could be found.
   */
  protected abstract M getRootElement(Object element_p);

  /**
   * Initialize handler.
   * @param resourceSet_p
   */
  protected void initialize(ResourceSet resourceSet_p) {
    // Default implementation does nothing.
  }

  /**
   * Load model at specified absolute location.
   * @param fileModelAbsolutePath_p A not <code>null</code> absolute file path to the model location.
   * @return <code>null</code> if the model could not be loaded. The root model element otherwise.
   */
  public M loadModel(String fileModelAbsolutePath_p) {
    if (null == fileModelAbsolutePath_p) {
      return null;
    }
    Resource resource = null;
    try {
      resource = _resourceSet.getResource(URI.createFileURI(fileModelAbsolutePath_p), true);
    } catch (Exception exception_p) {
      return null;
    }
    EList<EObject> contents = resource.getContents();
    if (contents.size() > 0) {
      return getRootElement(contents.get(0));
    }
    return null;
  }

  /**
   * Save specified instance of model to file.<br>
   * Requires that the root element has been created by calling {@link #createNewModel(String)} first.
   * @param rootElement_p A not <code>null</code> root element.
   * @param unload_p Should model be unloaded from memory after save ?
   * @return A not <code>null</code> {@link IStatus} describing the save result (or unload if required and save was successful).
   */
  @SuppressWarnings("nls")
  public IStatus saveModel(M rootElement_p, boolean unload_p) {
    String pluginId = CommonActivator.getInstance().getPluginId();
    // Precondition.
    if (null == rootElement_p) {
      return new Status(IStatus.ERROR, pluginId, "Invalid null model.");
    }
    try {
      rootElement_p.eResource().save(null);
    } catch (IOException exception_p) {
      return new Status(IStatus.ERROR, pluginId, null, exception_p);
    }
    if (unload_p) {
      return unloadModel(rootElement_p);
    }
    return new Status(IStatus.OK, pluginId, "Model successfully written to disk.");
  }

  /**
   * Unload model that contains the specified root element from memory.<br>
   * This is up to the caller to make sure that no reference is still contained by a non-model element.<br>
   * This should be called each time a created or loaded model is no longer needed (for instance after a successful save, or a transformation, ...).
   * @param rootElement_p
   * @return
   */
  @SuppressWarnings("nls")
  public IStatus unloadModel(M rootElement_p) {
    String pluginId = CommonActivator.getInstance().getPluginId();
    // Precondition.
    if (null == rootElement_p) {
      return new Status(IStatus.ERROR, pluginId, "Invalid null model.");
    }
    // Get resource.
    Resource resource = rootElement_p.eResource();
    if (null == resource) {
      return new Status(IStatus.ERROR, pluginId, "Invalid model. Use the handler to create it first.");
    }
    // Unload it.
    resource.unload();
    // Dereference it.
    _resourceSet.getResources().remove(resource);
    return new Status(IStatus.OK, pluginId, "Model successfully unloaded from memory.");
  }
}