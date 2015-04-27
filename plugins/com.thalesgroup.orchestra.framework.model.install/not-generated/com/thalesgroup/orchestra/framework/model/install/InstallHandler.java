/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.install;

import com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.ConfigurationFactory;
import com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.DocumentRoot;
import com.thalesgroup.orchestra.doctor.v2.local.localConfiguration.OrchestraDoctorLocalConfigurationType;
import com.thalesgroup.orchestra.framework.common.model.AbstractModelHandler;

/**
 * A handler dedicated to the machine installation model.
 * @author t0076261
 */
public class InstallHandler extends AbstractModelHandler<OrchestraDoctorLocalConfigurationType> {
  /**
   * @see com.thalesgroup.orchestra.framework.common.model.AbstractModelHandler#createRootElement()
   */
  @Override
  protected OrchestraDoctorLocalConfigurationType createRootElement() {
    DocumentRoot root = ConfigurationFactory.eINSTANCE.createDocumentRoot();
    root.setOrchestraDoctorLocalConfiguration(ConfigurationFactory.eINSTANCE.createOrchestraDoctorLocalConfigurationType());
    return root.getOrchestraDoctorLocalConfiguration();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.common.model.AbstractModelHandler#getFileExtension()
   */
  @Override
  public String getFileExtension() {
    return "configuration"; //$NON-NLS-1$
  }

  /**
   * @see com.thalesgroup.orchestra.framework.common.model.AbstractModelHandler#getRootElement(java.lang.Object)
   */
  @Override
  protected OrchestraDoctorLocalConfigurationType getRootElement(Object element_p) {
    if (element_p instanceof DocumentRoot) {
      return ((DocumentRoot) element_p).getOrchestraDoctorLocalConfiguration();
    } else if (element_p instanceof OrchestraDoctorLocalConfigurationType) {
      return (OrchestraDoctorLocalConfigurationType) element_p;
    }
    return null;
  }
}