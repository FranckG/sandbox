/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.project;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;

import com.thalesgroup.orchestra.framework.contextsproject.ContextsProject;

/**
 * An editing domain specific to the Context Project M².
 * @author t0076261
 */
public class ProjectsEditingDomain extends AdapterFactoryEditingDomain {
  /**
   * Constructor.
   * @param adapterFactory_p
   */
  public ProjectsEditingDomain(AdapterFactory adapterFactory_p) {
    super(adapterFactory_p, new BasicCommandStack(), new CaseUnsensitiveResourceSetImpl());
  }

  /**
   * @see org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain#getResourceSet()
   */
  @Override
  public CaseUnsensitiveResourceSetImpl getResourceSet() {
    return (CaseUnsensitiveResourceSetImpl) super.getResourceSet();
  }

  /**
   * Save specified project resource.
   * @param resource_p
   * @throws IOException
   */
  public void saveResource(Resource resource_p) throws IOException {
    saveResource(resource_p, false);
  }

  /**
   * Save specified project resource.
   * @param resource_p
   * @param <code>true</code> to force save, even if the project resource is not managed by projects editing domain.
   */
  public void saveResource(Resource resource_p, boolean forceSave_p) throws IOException {
    // Preconditions.
    if ((null == resource_p) || !(getResourceSet().getResources().contains(resource_p) || forceSave_p)) {
      return;
    }
    // TODO Guillaume
    // Introduce a customized resource implementation that ensures UTF-8 usage.
    Map<Object, Object> options = new HashMap<Object, Object>();
    options.put(XMLResource.OPTION_ENCODING, "UTF-8"); //$NON-NLS-1$s
    resource_p.save(options);
  }

  /**
   * Unload specified project from editing domain.
   * @param rootContextsProject_p
   */
  public void unloadProject(RootContextsProject rootContextsProject_p) {
    // Precondition.
    if (null == rootContextsProject_p) {
      return;
    }
    // Get model object.
    ContextsProject contextsProject = rootContextsProject_p._contextsProject;
    // Precondition.
    if (null == contextsProject) {
      return;
    }
    // Get model resource.
    Resource resource = contextsProject.eResource();
    if (null != resource) { // Unload it.
      resource.unload();
      getResourceSet().getResources().remove(resource);
    }
  }
}