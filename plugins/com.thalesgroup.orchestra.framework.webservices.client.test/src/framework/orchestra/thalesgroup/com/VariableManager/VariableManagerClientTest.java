package framework.orchestra.thalesgroup.com.VariableManager;

import java.rmi.RemoteException;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.thalesgroup.orchestra.framework.model.IRemoteVariableManager;
import com.thalesgroup.orchestra.framework.model.IVariable;

import framework.orchestra.thalesgroup.com.VariableManagerAPI.VariableManagerAdapter;

import javax.xml.rpc.ServiceException;

/**
 * See this Test as an example to consume Variable Manager services.
 */
public class VariableManagerClientTest {

  private static IRemoteVariableManager vm;

  @BeforeClass
  public static void resolveManager() throws ServiceException {
    vm = VariableManagerAdapter.getInstance();
  }

  @Test
  public void currentContextNotEmpty() throws RemoteException {
    String currentContext = vm.getCurrentContextName();
    Assert.assertNotNull(currentContext);
    Assert.assertTrue(currentContext.length() > 0);
  }

  @Test
  public void getVariable() throws RemoteException {
    IVariable variable = vm.getVariable("\\Orchestra\\ArtefactPath"); //$NON-NLS-1$
    artefactPathVaraibleVerification(variable);
  }

  private void artefactPathVaraibleVerification(IVariable variable) {
    Assert.assertNotNull(variable);
    Assert.assertEquals("\\Orchestra\\ArtefactPath", variable.getPath()); //$NON-NLS-1$
    Assert.assertEquals("ArtefactPath", variable.getName()); //$NON-NLS-1$
    Assert.assertEquals("C:\\Orchestra_Data\\artifacts\\", variable.getValues().get(0)); //$NON-NLS-1$
  }

  @Test
  public void getVariableWithAValueProvidedByTheAPI() throws RemoteException {
    IVariable variable = vm.getVariableArtefactPath();
    artefactPathVaraibleVerification(variable);
  }

  @Test
  public void getVariables() throws RemoteException {
    List<IVariable> allVariables = vm.getAllVariables();
    Assert.assertNotNull(allVariables);
    IVariable var = null;
    for (IVariable iVariable : allVariables) {
      if ("\\Orchestra\\TemporaryDirectory".equals(iVariable.getPath())) { //$NON-NLS-1$
        var = iVariable;
      }
    }
    Assert.assertNotNull("Temporary directory not found.", var); //$NON-NLS-1$
  }

  @Test
  public void getAllContextNames() throws RemoteException {
    List<String> allContextNames = vm.getAllContextNames();
    Assert.assertNotNull(allContextNames);
    boolean found = false;
    for (String name : allContextNames) {
      if ("Default Context".equals(name)) { //$NON-NLS-1$
        found = true;
      }
    }
    Assert.assertTrue("Default Context not found.", found); //$NON-NLS-1$
  }

  @Test
  public void getCategories() throws RemoteException {
    List<String> categories = vm.getCategories("\\"); //$NON-NLS-1$
    Assert.assertNotNull(categories);
    Assert.assertEquals(2, categories.size());
    Assert.assertEquals("Orchestra", categories.get(0)); //$NON-NLS-1$
    Assert.assertEquals("Orchestra installation", categories.get(1)); //$NON-NLS-1$
  }

  @Test
  public void updateConfDir() throws RemoteException, ServiceException {
    ConfDirDelta delta = new ConfDirDelta("NouveauFichierBidon.txt", ConfDirDeltaDeltaType.ADD); //$NON-NLS-1$
    DeltaState[] states = VariableManagerAdapter.getInstance().notifyConfigurationDirectoryUpdated("D:/Temp/ConfDirTest3", new ConfDirDelta[] { delta }); //$NON-NLS-1$
    Assert.assertTrue(states[0].isSuccess());
  }
}