/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.filesystem.framework.ae.contribution;

import java.io.File;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.thalesgroup.orchestra.framework.ae.creation.ui.environment.AbstractEnvironmentArtefactCreationHandler;
import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizardPage;

/**
 * @author s0040806
 */
public class FileSystemArtefactCreationHandler extends AbstractEnvironmentArtefactCreationHandler {

  public static final String PHYSICAL_PATH_PARAMETER = "physicalPath";

  /**
   * @see com.thalesgroup.orchestra.framework.oe.ui.wizard.creation.environment.IEnvironmentArtefactCreationHandler#createWizardPages(java.util.Map)
   */
  @Override
  public AbstractFormsWizardPage getFirstPage() {
    return new FileSystemArtefactCreationPage(this);
  }

  @Override
  public boolean arePagesComplete() {
    return null != getRootName() && null != getPhysicalPath();
  }

  public String getPhysicalPath() {
    return getParameters().get(PHYSICAL_PATH_PARAMETER);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.ui.wizard.creation.environment.AbstractEnvironmentArtefactCreationHandler#preCreate()
   */
  @Override
  public IStatus preCreate() {
    // Get parent directory of the physical path of the file
    File parent = new File(new File(getPhysicalPath()).getParent());
    if (!parent.exists()) {
      if (!parent.mkdirs()) {
        return new Status(IStatus.ERROR, Activator.getInstance().getPluginId(), Messages.FileSystemArtefactCreationHandler_FailedToCreateDirectories
                                                                                + getPhysicalPath());
      }
    }
    return new Status(IStatus.OK, Activator.getInstance().getPluginId(), ICommonConstants.EMPTY_STRING);
  }
}
