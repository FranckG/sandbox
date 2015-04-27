/**
 * VariableManagerSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 * @author Customized by s0011584
 */

package framework.orchestra.thalesgroup.com.VariableManager;

import java.rmi.RemoteException;

import org.eclipse.core.runtime.IStatus;

import com.thalesgroup.orchestra.framework.config.ConfDirHelper;
import com.thalesgroup.orchestra.framework.model.IVariable;
import com.thalesgroup.orchestra.framework.transcription.TranscriptionHelper;
import com.thalesgroup.orchestra.framework.variablemanager.server.model.VariableManager;

public class VariableManagerService implements framework.orchestra.thalesgroup.com.VariableManager.VariableManagerWSAdapter {
  /**
   * @see framework.orchestra.thalesgroup.com.VariableManager.VariableManagerWSAdapter#configurationDirectoryUpdated(java.lang.String,
   *      framework.orchestra.thalesgroup.com.VariableManager.ConfDirDelta[])
   */
  public DeltaState[] configurationDirectoryUpdated(String confDirPath_p, ConfDirDelta[] deltas_p) throws RemoteException {
    DeltaState[] result = new DeltaState[deltas_p.length];
    int i = 0;
    for (ConfDirDelta delta : deltas_p) {
      result[i] = new DeltaState();
      result[i].setDelta(delta);
      IStatus status;

      if (delta.getDeltaType() == ConfDirDeltaDeltaType.ADD) {
        status = ConfDirHelper.add(confDirPath_p, delta.getSubPath());
      } else if (delta.getDeltaType() == ConfDirDeltaDeltaType.DEL) {
        status = ConfDirHelper.delete(confDirPath_p, delta.getSubPath());
      } else // (delta.getDeltaType() == ConfDirDeltaDeltaType.MOD)
      {
        status = ConfDirHelper.modify(confDirPath_p, delta.getSubPath());
      }

      result[i].setDescription(status.getMessage());
      result[i].setSuccess(status.getSeverity() == IStatus.OK);

      i++;
    }
    TranscriptionHelper.getInstance().refresh();
    return result;
  }

  public java.lang.String[] getAllContextNames() throws java.rmi.RemoteException {
    return VariableManager.getInstance().getAllContextNames().toArray(new String[] {});
  }

  public framework.orchestra.thalesgroup.com.VariableManager.VariableWS[] getAllVariables() throws java.rmi.RemoteException {
    return VariableSoapHelper.iVariablesToVariableWSs(VariableManager.getInstance().getAllVariables());
  }

  public framework.orchestra.thalesgroup.com.VariableManager.VariableWS[] getAllVariablesLocalized(java.lang.String locale) throws java.rmi.RemoteException {
    return VariableSoapHelper.iVariablesToVariableWSs(VariableManager.getInstance().getAllVariablesLocalized(locale));
  }

  public java.lang.String[] getCategories(java.lang.String categoryPath) throws java.rmi.RemoteException {
    return VariableManager.getInstance().getCategories(categoryPath).toArray(new String[] {});
  }

  public java.lang.String getCategorySeparator() throws java.rmi.RemoteException {
    return VariableManager.getInstance().getCategorySeparator();
  }

  public java.lang.String getCurrentContextName() throws java.rmi.RemoteException {
    return VariableManager.getInstance().getCurrentContextName();
  }

  public java.lang.String getSubstitutedValue(java.lang.String variablePath) throws java.rmi.RemoteException {
    return VariableManager.getInstance().getSubstitutedValue(variablePath);
  }

  public framework.orchestra.thalesgroup.com.VariableManager.VariableWS getVariable(java.lang.String variablePath) throws java.rmi.RemoteException {
    IVariable variable = VariableManager.getInstance().getVariable(variablePath);
    return variable != null ? VariableSoapHelper.iVariableToVariableWS(variable) : null;
  }

  public framework.orchestra.thalesgroup.com.VariableManager.VariableWS getVariableArtefactPath() throws java.rmi.RemoteException {
    return VariableSoapHelper.iVariableToVariableWS(VariableManager.getInstance().getVariableArtefactPath());
  }

  public framework.orchestra.thalesgroup.com.VariableManager.VariableWS getVariableConfigurationDirectory() throws java.rmi.RemoteException {
    return VariableSoapHelper.iVariableToVariableWS(VariableManager.getInstance().getVariableConfigurationDirectory());
  }

  public framework.orchestra.thalesgroup.com.VariableManager.VariableWS getVariableLocalized(java.lang.String variablePath, java.lang.String locale)
      throws java.rmi.RemoteException {
    return VariableSoapHelper.iVariableToVariableWS(VariableManager.getInstance().getVariableLocalized(variablePath, locale));
  }

  public framework.orchestra.thalesgroup.com.VariableManager.VariableWS[] getVariables(java.lang.String categoryPath) throws java.rmi.RemoteException {
    return VariableSoapHelper.iVariablesToVariableWSs(VariableManager.getInstance().getVariables(categoryPath));
  }

  public framework.orchestra.thalesgroup.com.VariableManager.VariableWS getVariableSharedDirectory() throws java.rmi.RemoteException {
    return VariableSoapHelper.iVariableToVariableWS(VariableManager.getInstance().getVariableSharedDirectory());
  }

  public framework.orchestra.thalesgroup.com.VariableManager.VariableWS[] getVariablesLocalized(java.lang.String categoryPath, java.lang.String locale)
      throws java.rmi.RemoteException {
    return VariableSoapHelper.iVariablesToVariableWSs(VariableManager.getInstance().getVariablesLocalized(categoryPath, locale));
  }

  public framework.orchestra.thalesgroup.com.VariableManager.VariableWS getVariableTemporaryDirectory() throws java.rmi.RemoteException {
    return VariableSoapHelper.iVariableToVariableWS(VariableManager.getInstance().getVariableTemporaryDirectory());
  }

  public void registerAsVariableConsumer(java.lang.String toolInstanceID) throws java.rmi.RemoteException {
    VariableManager.getInstance().registerAsVariableConsumer(toolInstanceID);
  }

  public void unregisterAsVariableConsumer(java.lang.String toolInstanceID) throws java.rmi.RemoteException {
    VariableManager.getInstance().unregisterAsVariableConsumer(toolInstanceID);
  }

}
