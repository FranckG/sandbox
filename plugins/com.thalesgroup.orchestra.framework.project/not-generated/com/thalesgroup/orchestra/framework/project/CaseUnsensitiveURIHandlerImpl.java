/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.project;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.PlatformResourceURIHandlerImpl;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.common.helper.FileHelper;

/**
 * This class provides the same methods as its super class but URI case is corrected before each call. <br>
 * This handler is used for context files (*.contexts and description.contextsproject).
 * @author T0052089
 */
public class CaseUnsensitiveURIHandlerImpl extends PlatformResourceURIHandlerImpl {
  /**
   * File extension for .context files (in lower case).
   */
  public static final String LOWER_CASE_FILE_EXTENSION_CONTEXTS = ICommonConstants.FILE_EXTENSION_CONTEXTS.toLowerCase(Locale.ENGLISH);
  /**
   * File name for description.contextproject file (in lower case).
   */
  public static final String LOWER_CASE_DEFAULT_CONTEXTS_PROJECT_DESCRIPTION = ProjectFactory.DEFAULT_CONTEXTS_PROJECT_DESCRIPTION.toLowerCase(Locale.ENGLISH);

  /**
   * @see org.eclipse.emf.ecore.resource.impl.PlatformResourceURIHandlerImpl#canHandle(org.eclipse.emf.common.util.URI)
   */
  @Override
  public boolean canHandle(URI uri_p) {
    // Only .contexts files and description.contextproject files are handled.
    String uriLastSegment = uri_p.lastSegment().toLowerCase(Locale.ENGLISH);
    return super.canHandle(uri_p) && null != uriLastSegment
           && (uriLastSegment.endsWith(LOWER_CASE_FILE_EXTENSION_CONTEXTS) || LOWER_CASE_DEFAULT_CONTEXTS_PROJECT_DESCRIPTION.equals(uriLastSegment));
  }

  /**
   * @see org.eclipse.emf.ecore.resource.impl.PlatformResourceURIHandlerImpl#createOutputStream(org.eclipse.emf.common.util.URI, java.util.Map)
   */
  @Override
  public OutputStream createOutputStream(URI uri_p, Map<?, ?> options_p) throws IOException {
    // Find corresponding file location and correct URI case according to it.
    URI correctedURI = FileHelper.correctURICase(uri_p);
    return super.createOutputStream(correctedURI, options_p);
  }

  /**
   * @see org.eclipse.emf.ecore.resource.impl.PlatformResourceURIHandlerImpl#createInputStream(org.eclipse.emf.common.util.URI, java.util.Map)
   */
  @Override
  public InputStream createInputStream(URI uri_p, Map<?, ?> options_p) throws IOException {
    // Find corresponding file location and correct URI case according to it.
    URI correctedURI = FileHelper.correctURICase(uri_p);
    return super.createInputStream(correctedURI, options_p);
  }

  /**
   * @see org.eclipse.emf.ecore.resource.impl.PlatformResourceURIHandlerImpl#delete(org.eclipse.emf.common.util.URI, java.util.Map)
   */
  @Override
  public void delete(URI uri_p, Map<?, ?> options_p) throws IOException {
    // Find corresponding file location and correct URI case according to it.
    URI correctedURI = FileHelper.correctURICase(uri_p);
    super.delete(correctedURI, options_p);
  }

  /**
   * @see org.eclipse.emf.ecore.resource.impl.PlatformResourceURIHandlerImpl#exists(org.eclipse.emf.common.util.URI, java.util.Map)
   */
  @Override
  public boolean exists(URI uri_p, Map<?, ?> options_p) {
    // Find corresponding file location and correct URI case according to it.
    URI correctedURI = FileHelper.correctURICase(uri_p);
    return super.exists(correctedURI, options_p);
  }

  /**
   * @see org.eclipse.emf.ecore.resource.impl.PlatformResourceURIHandlerImpl#getAttributes(org.eclipse.emf.common.util.URI, java.util.Map)
   */
  @Override
  public Map<String, ?> getAttributes(URI uri_p, Map<?, ?> options_p) {
    // Find corresponding file location and correct URI case according to it.
    URI correctedURI = FileHelper.correctURICase(uri_p);
    return super.getAttributes(correctedURI, options_p);
  }

  /**
   * @see org.eclipse.emf.ecore.resource.impl.PlatformResourceURIHandlerImpl#setAttributes(org.eclipse.emf.common.util.URI, java.util.Map, java.util.Map)
   */
  @Override
  public void setAttributes(URI uri_p, Map<String, ?> attributes_p, Map<?, ?> options_p) throws IOException {
    // Find corresponding file location and correct URI case according to it.
    URI correctedURI = FileHelper.correctURICase(uri_p);
    super.setAttributes(correctedURI, attributes_p, options_p);
  }
}
