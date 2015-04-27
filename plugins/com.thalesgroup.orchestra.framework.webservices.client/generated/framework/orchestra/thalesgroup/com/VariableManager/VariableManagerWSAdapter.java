/**
 * VariableManagerWSAdapter.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package framework.orchestra.thalesgroup.com.VariableManager;

public interface VariableManagerWSAdapter extends java.rmi.Remote {
    public java.lang.String[] getCategories(java.lang.String categoryPath) throws java.rmi.RemoteException;
    public framework.orchestra.thalesgroup.com.VariableManager.VariableWS getVariable(java.lang.String variablePath) throws java.rmi.RemoteException;
    public java.lang.String[] getAllContextNames() throws java.rmi.RemoteException;
    public framework.orchestra.thalesgroup.com.VariableManager.VariableWS[] getAllVariables() throws java.rmi.RemoteException;
    public framework.orchestra.thalesgroup.com.VariableManager.VariableWS[] getVariables(java.lang.String categoryPath) throws java.rmi.RemoteException;
    public java.lang.String getCurrentContextName() throws java.rmi.RemoteException;
    public java.lang.String getSubstitutedValue(java.lang.String variablePath) throws java.rmi.RemoteException;
    public void registerAsVariableConsumer(java.lang.String toolInstanceID) throws java.rmi.RemoteException;
    public void unregisterAsVariableConsumer(java.lang.String toolInstanceID) throws java.rmi.RemoteException;
    public framework.orchestra.thalesgroup.com.VariableManager.VariableWS[] getAllVariablesLocalized(java.lang.String locale) throws java.rmi.RemoteException;
    public framework.orchestra.thalesgroup.com.VariableManager.VariableWS getVariableLocalized(java.lang.String variablePath, java.lang.String locale) throws java.rmi.RemoteException;
    public framework.orchestra.thalesgroup.com.VariableManager.VariableWS[] getVariablesLocalized(java.lang.String categoryPath, java.lang.String locale) throws java.rmi.RemoteException;
    public framework.orchestra.thalesgroup.com.VariableManager.VariableWS getVariableArtefactPath() throws java.rmi.RemoteException;
    public framework.orchestra.thalesgroup.com.VariableManager.VariableWS getVariableConfigurationDirectory() throws java.rmi.RemoteException;
    public framework.orchestra.thalesgroup.com.VariableManager.VariableWS getVariableSharedDirectory() throws java.rmi.RemoteException;
    public framework.orchestra.thalesgroup.com.VariableManager.VariableWS getVariableTemporaryDirectory() throws java.rmi.RemoteException;
    public java.lang.String getCategorySeparator() throws java.rmi.RemoteException;
    public framework.orchestra.thalesgroup.com.VariableManager.DeltaState[] configurationDirectoryUpdated(java.lang.String confDirPath, framework.orchestra.thalesgroup.com.VariableManager.ConfDirDelta[] deltas) throws java.rmi.RemoteException;
}
