/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.root.ui.activator;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * Base class for plug-ins that integrate with the Eclipse platform UI.<br>
 * @author fournier
 */
public class AbstractUIActivator extends AbstractUIPlugin {
  private static final String ICONS_PATH = "icons/"; //$NON-NLS-1$

  /**
   * Get an image descriptor for given key.<br>
   * Images must be located in 'plug-in folder'/icons
   * @param key_p the key must be the file name of the related image.
   * @return an {@link ImageDescriptor} or null if not found
   */
  public ImageDescriptor getImageDescriptor(String key_p) {
    ImageRegistry imageRegistry = getImageRegistry();
    ImageDescriptor imageDescriptor = imageRegistry.getDescriptor(key_p);

    if (null == imageDescriptor) {
      imageDescriptor = createImageDescriptor(key_p);
      imageRegistry.put(key_p, imageDescriptor);
    }
    return imageDescriptor;
  }

  /**
   * Get an image for given key.<br>
   * Images must be located in 'plug-in folder'/icons
   * @param key_p the key must be the file name of the related image.
   * @return an {@link Image} or null if not found
   */
  public Image getImage(String key_p) {
    ImageRegistry imageRegistry = getImageRegistry();

    Image image = imageRegistry.get(key_p);
    if (null == image) {
      // Create an image descriptor for given id.
      ImageDescriptor imageDescriptor = createImageDescriptor(key_p);
      // Store the (id, imageDescriptor) rather than (id,image)
      // because with storing (id,image) the getDescriptor method will return null in later usage
      // this way, everything is correctly initialized.
      imageRegistry.put(key_p, imageDescriptor);

      // Everything is all right at this step, let's get the real image
      image = imageRegistry.get(key_p);
    }
    return image;
  }

  /**
   * Create an image descriptor for given key.<br>
   * Images must be located in 'plug-in folder'/icons
   * @param key_p the key must be the file name of the related image.
   * @return an {@link ImageDescriptor} or null if error occurred
   */
  protected ImageDescriptor createImageDescriptor(String key_p) {
    ImageDescriptor imageDescriptor = AbstractUIPlugin.imageDescriptorFromPlugin(getPluginId(), ICONS_PATH + key_p);
    return imageDescriptor;
  }

  /**
   * Get the plug-in ID according to MANISFEST.MF definition.
   * @return a String containing the plug-in ID.
   */
  public String getPluginId() {
    return getBundle().getSymbolicName();
  }
}