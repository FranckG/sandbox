/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.tests;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.AbstractVariable;
import com.thalesgroup.orchestra.framework.model.contexts.Category;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.tests.internal.model.AbstractModelTest;

import junit.framework.Assert;

/**
 * @author t0076261
 */
public class ModelUtilTest extends AbstractModelTest {
  @SuppressWarnings("nls")
  public void testConvertVariableValuesFromRegistryOnes() {
    // Test conversion.
    Assert.assertEquals("ExeName conversion", "test.exe", ModelUtil.convertValue(ModelUtil.INSTALLATION_VAR_EXE_NAME, "test.exe", false));
    Assert.assertEquals("ExeName conversion", "test.exe", ModelUtil.convertValue(ModelUtil.INSTALLATION_VAR_EXE_NAME, ".\\/./test.exe", false));
    Assert.assertEquals("ExeRelativePath conversion", "\\", ModelUtil.convertValue(ModelUtil.INSTALLATION_VAR_EXE_RELATIVE_PATH_NAME, ".", false));
    Assert.assertEquals("ExeRelativePath conversion", "\\Test\\", ModelUtil.convertValue(ModelUtil.INSTALLATION_VAR_EXE_RELATIVE_PATH_NAME, "./Test", false));
    Assert.assertEquals("ExeRelativePath conversion", "\\Test\\", ModelUtil.convertValue(ModelUtil.INSTALLATION_VAR_EXE_RELATIVE_PATH_NAME, "Test\\", false));
    Assert.assertEquals("HomePath conversion", "c:\\Test\\Test", ModelUtil.convertValue(ModelUtil.INSTALLATION_VAR_HOME_PATH, "c:\\Test\\Test//", false));
    Assert.assertEquals("HomePath conversion", "c:/Test\\Test", ModelUtil.convertValue(ModelUtil.INSTALLATION_VAR_HOME_PATH, "c:/Test\\Test//\\", false));
    // Test environment substitution.
    Assert.assertEquals("Environment substitution", "${env_var:SystemRoot}\\system32", ModelUtil.convertValue(ICommonConstants.EMPTY_STRING,
        "%SystemRoot%\\system32", true));
    Assert.assertEquals("Environment substitution", "${env_var:SystemRoot}\\Test\\${env_var:Path}\\test", ModelUtil.convertValue(ICommonConstants.EMPTY_STRING,
        "%SystemRoot%\\Test\\%Path%\\test", true));
    Assert.assertEquals("Environment substitution", "c:\\Test\\${env_var:SystemRoot}\\Test\\${env_var:Path}\\test\\", ModelUtil.convertValue(
        ICommonConstants.EMPTY_STRING, "c:\\Test\\%SystemRoot%\\Test\\%Path%\\test\\", true));
  }

  @SuppressWarnings("nls")
  public void testGetElementPaths() {
    Context thavContext = ModelHandlerActivator.getDefault().getDataHandler().getContext("/THAV/THAV.contexts");
    Category themisCategory = DataUtil.getCategory("\\Themis", thavContext);
    Assert.assertEquals("Category path", "\\Themis", ModelUtil.getElementPath(themisCategory));
    AbstractVariable thavConnector = DataUtil.getVariable("\\Themis\\ThavConnector", thavContext);
    Assert.assertEquals("Variable path", "\\Themis\\ThavConnector", ModelUtil.getElementPath(thavConnector));
    AbstractVariable a380 = DataUtil.getVariable("\\Themis\\A380", thavContext);
    Assert.assertEquals("Variable path", "\\Themis\\A380", ModelUtil.getElementPath(a380));
  }
}