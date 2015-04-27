/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.activator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.edit.provider.ComposedImage;
import org.eclipse.emf.edit.provider.ComposedImage.Point;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.BundleContext;

import com.thalesgroup.orchestra.framework.common.CommonActivator;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsPackage;
import com.thalesgroup.orchestra.framework.model.validation.EValidatorAdapter;
import com.thalesgroup.orchestra.framework.root.ui.activator.AbstractUIActivator;

/**
 * @author t0076261
 */
public class OrchestraFrameworkUiActivator extends AbstractUIActivator {
  /**
   * Shared instance.
   */
  private static OrchestraFrameworkUiActivator __default;

  /**
   * ComposedImage map.
   */
  private Map<String, ComposedImage> _images;

  /**
   * Constructor.
   */
  public OrchestraFrameworkUiActivator() {
    _images = new HashMap<String, ComposedImage>(0);
  }

  /**
   * Get image for specified key, and/or composition.<br>
   * If no image exists for specified key, a new one is created from specified composition.
   * @param key_p
   * @param images_p
   * @param positions_p
   * @return
   */
  public Image getComposedImage(String key_p, List<Object> images_p, final List<Point> positions_p) {
    ComposedImage result = _images.get(key_p);
    if (null == result) {
      result = new ComposedImage(images_p) {
        @Override
        public List<Point> getDrawPoints(Size size) {
          return positions_p;
        }
      };
      _images.put(key_p, result);
    }
    return ExtendedImageRegistry.getInstance().getImage(result);
  }

  /**
   * @see org.eclipse.core.runtime.Plugin#start(org.osgi.framework.BundleContext)
   */
  @Override
  public void start(BundleContext context_p) throws Exception {
    super.start(context_p);
    __default = this;
    // Register EMF Validation Framework against Contexts Meta-Model.
    try {
      EValidator.Registry.INSTANCE.put(ContextsPackage.eINSTANCE, new EValidatorAdapter());
    } catch (Throwable throwable_p) {
      CommonActivator.getInstance().logMessage("Could not initialize EMF Validation against Contexts M2.", IStatus.ERROR, throwable_p); //$NON-NLS-1$
    }
  }

  /**
   * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
   */
  @Override
  public void stop(BundleContext context_p) throws Exception {
    __default = null;
    super.stop(context_p);
  }

  /**
   * Get shared instance.
   * @return
   */
  public static OrchestraFrameworkUiActivator getDefault() {
    return __default;
  }
}