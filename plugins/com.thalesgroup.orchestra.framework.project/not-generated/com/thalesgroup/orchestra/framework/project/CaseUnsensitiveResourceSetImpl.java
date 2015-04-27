/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.project;

import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

/**
 * Extends ResourceSetImpl by adding a CaseUnsensitiveURIHandlerImpl.
 * @author T0052089
 */
public class CaseUnsensitiveResourceSetImpl extends ResourceSetImpl {
  /**
   * @see org.eclipse.emf.ecore.resource.impl.ResourceSetImpl#getURIConverter()
   */
  @Override
  public URIConverter getURIConverter() {
    if (null == uriConverter) {
      // Initialize the URIConverter.
      super.getURIConverter();
      // CaseUnsensitiveURIHandler must be the first element of the list to have a chance to be called.
      uriConverter.getURIHandlers().add(0, new CaseUnsensitiveURIHandlerImpl());
    }
    return uriConverter;
  }
}