package com.thalesgroup.orchestra.framework.variablemanager.server.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.rmi.RemoteException;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.common.helper.FileHelper;
import com.thalesgroup.orchestra.framework.common.helper.ProjectHelper;
import com.thalesgroup.orchestra.framework.model.IRemoteVariableManager;
import com.thalesgroup.orchestra.framework.model.IVariable;
import com.thalesgroup.orchestra.framework.variablemanager.server.model.VariableManager;

public class VariableManagerTest {

  static private VariableManager manager = null;
  static private IRemoteVariableManager remoteManager = null;

  @Test
  public void allContextNames() throws RemoteException {
    final List<String> names = remoteManager.getAllContextNames();
    assertEquals(7, names.size());
    String name = names.get(0);
    assertEquals("junit context", name); //$NON-NLS-1$
    name = names.get(1);
    assertEquals("junit context 2", name); //$NON-NLS-1$
    name = names.get(2);
    assertEquals("junit context 3", name); //$NON-NLS-1$
    name = names.get(3);
    assertEquals("OFVM SUM", name); //$NON-NLS-1$
    name = names.get(4);
    assertEquals("StandardOrchestra", name); //$NON-NLS-1$
    name = names.get(5);
    assertEquals("SUM BPEL", name); //$NON-NLS-1$
    name = names.get(6);
    assertEquals("Default Context", name); //$NON-NLS-1$
  }

  @Test
  public void allVariables() throws RemoteException {
    final List<IVariable> variables = remoteManager.getAllVariables();
    assertTrue(variables.size() >= 4);
    IVariable variable = variables.get(0);
    assertEquals("\\category\\junit variable", variable.getPath()); //$NON-NLS-1$
    assertEquals("junit variable value", variable.getValues().get(0)); //$NON-NLS-1$
    variable = variables.get(1);
    assertEquals("\\category\\junit variable 2", variable.getPath()); //$NON-NLS-1$
    assertEquals("junit variable value", variable.getValues().get(0)); //$NON-NLS-1$
    variable = variables.get(2);
    assertEquals("\\category\\junit variable 3", variable.getPath()); //$NON-NLS-1$
    assertEquals("junit variable value", variable.getValues().get(0)); //$NON-NLS-1$
    variable = variables.get(3);
    assertEquals("\\category\\junit variable 4", variable.getPath()); //$NON-NLS-1$
    assertEquals("toto", variable.getValues().get(0)); //$NON-NLS-1$
  }

  @Test
  public void currentContextName() throws RemoteException {
    final String name = remoteManager.getCurrentContextName();
    assertEquals("junit context", name); //$NON-NLS-1$
  }

  @BeforeClass
  public static void setUp() throws Exception {
    setupBeans();
    unmarshall();
  }

  private static void setupBeans() {
    manager = VariableManager.getInstance();
    remoteManager = manager;
  }

  @Test
  public void substitutedValueWithANormalValue() throws Exception {
    final String variablePathName = ICommonConstants.PATH_SEPARATOR + "category" + ICommonConstants.PATH_SEPARATOR + "junit variable"; //$NON-NLS-1$ //$NON-NLS-2$
    final String value = remoteManager.getSubstitutedValue(variablePathName);
    assertEquals("junit variable value", value); //$NON-NLS-1$
  }

  @Test
  public void substitutedValueWithAValueToSubstituteFromCurrentCategory() throws Exception {
    final String variablePathName = ICommonConstants.PATH_SEPARATOR + "category" + ICommonConstants.PATH_SEPARATOR + "junit variable 3"; //$NON-NLS-1$ //$NON-NLS-2$
    final String value = remoteManager.getSubstitutedValue(variablePathName);
    assertEquals("junit variable value", value); //$NON-NLS-1$
  }

  @Test
  public void substitutedValueWithTheEclipseSubstitutionAndCustomSubstitution() throws Exception {
    final String variablePathName = ICommonConstants.PATH_SEPARATOR + "category" + ICommonConstants.PATH_SEPARATOR + "junit variable 6"; //$NON-NLS-1$ //$NON-NLS-2$
    final String value = remoteManager.getSubstitutedValue(variablePathName);
    assertTrue("C:\\WINDOWSpretotopostC:\\WINDOWS".equalsIgnoreCase(value)); //$NON-NLS-1$
  }

  @Test
  public void getVariableWithAnExistingVariable() throws RemoteException {
    final String variablePathName = ICommonConstants.PATH_SEPARATOR + "category" + ICommonConstants.PATH_SEPARATOR + "junit variable"; //$NON-NLS-1$ //$NON-NLS-2$
    final IVariable variable = remoteManager.getVariable(variablePathName);
    assertNotNull(variable);
    assertEquals(variablePathName, variable.getPath());
    assertEquals("junit variable value", variable.getValues().get(0)); //$NON-NLS-1$
  }

  @Test
  public void getVariableInASubCategory() throws RemoteException {
    final String variablePathName =
        ICommonConstants.PATH_SEPARATOR + "category" + ICommonConstants.PATH_SEPARATOR + "child category" + ICommonConstants.PATH_SEPARATOR + "variable 42"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    final IVariable variable = remoteManager.getVariable(variablePathName);
    assertNotNull(variable);
    assertEquals(variablePathName, variable.getPath());
    assertEquals("42", variable.getValues().get(0)); //$NON-NLS-1$
    assertTrue("Explicit test of toString.", variable.toString().endsWith("[path=\\category\\child category\\variable 42,values=[42],description=<null>]")); //$NON-NLS-1$ //$NON-NLS-2$
  }

  public static void unmarshall() throws Exception {
    assertTrue(ProjectHelper.importExistingProject(FileHelper.getFileFullUrl("com.thalesgroup.orchestra.framework.variablemanager.server.model.test/contexts")).isOK()); //$NON-NLS-1$
    manager.setCurrentContextFromItsName("junit context"); //$NON-NLS-1$
  }

  @Test
  public void categories() throws Exception {
    final List<String> categories = remoteManager.getCategories(String.valueOf(remoteManager.getCategorySeparator()));
    assertEquals(4, categories.size());
  }

  @Test
  public void variableArtefactPathShouldBeDefined() throws Exception {
    final IVariable variableArtefactPath = remoteManager.getVariableArtefactPath();
    assertNotNull(variableArtefactPath);
    assertEquals("\\Orchestra\\ArtefactPath", variableArtefactPath.getPath()); //$NON-NLS-1$
  }

  @Test
  public void variableConfigurationDirectoryShouldBeDefined() throws Exception {
    final IVariable variableConfigurationDirectory = remoteManager.getVariableConfigurationDirectory();
    assertNotNull(variableConfigurationDirectory);
    assertEquals("\\Orchestra\\ConfigurationDirectory", variableConfigurationDirectory.getPath()); //$NON-NLS-1$
  }

  @Test
  public void variableSharedDirectoryShouldBeDefined() throws Exception {
    final IVariable variableSharedDirectory = remoteManager.getVariableSharedDirectory();
    assertNotNull(variableSharedDirectory);
    assertEquals("\\Orchestra\\SharedDirectory", variableSharedDirectory.getPath()); //$NON-NLS-1$
  }

  @Test
  public void variableTemporaryDirectoryShouldBeDefined() throws Exception {
    final IVariable variableTemporaryDirectory = remoteManager.getVariableTemporaryDirectory();
    assertNotNull(variableTemporaryDirectory);
    assertEquals("\\Orchestra\\TemporaryDirectory", variableTemporaryDirectory.getPath()); //$NON-NLS-1$
  }

  @Test
  public void substitutedValueOfANonExistingVariablePath() throws Exception {
    try {
      String substitutedValue = remoteManager.getSubstitutedValue("\\path\\does\\not\\exists"); //$NON-NLS-1$
      assertNull("Should not be possible to substitute a non exisitng variable.", substitutedValue); //$NON-NLS-1$
    } catch (NullPointerException exceptionP) {
      fail("For non-regression purposes, should not throw a NPE."); //$NON-NLS-1$
    }
  }

}
