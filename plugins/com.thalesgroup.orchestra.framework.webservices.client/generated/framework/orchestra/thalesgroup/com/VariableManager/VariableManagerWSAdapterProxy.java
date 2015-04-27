package framework.orchestra.thalesgroup.com.VariableManager;

public class VariableManagerWSAdapterProxy implements framework.orchestra.thalesgroup.com.VariableManager.VariableManagerWSAdapter {
  private String _endpoint = null;
  private framework.orchestra.thalesgroup.com.VariableManager.VariableManagerWSAdapter variableManagerWSAdapter = null;
  
  public VariableManagerWSAdapterProxy() {
    _initVariableManagerWSAdapterProxy();
  }
  
  public VariableManagerWSAdapterProxy(String endpoint) {
    _endpoint = endpoint;
    _initVariableManagerWSAdapterProxy();
  }
  
  private void _initVariableManagerWSAdapterProxy() {
    try {
      variableManagerWSAdapter = (new framework.orchestra.thalesgroup.com.VariableManager.VariableManagerWSAdapterServiceLocator()).getVariableManager();
      if (variableManagerWSAdapter != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)variableManagerWSAdapter)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)variableManagerWSAdapter)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (variableManagerWSAdapter != null)
      ((javax.xml.rpc.Stub)variableManagerWSAdapter)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public framework.orchestra.thalesgroup.com.VariableManager.VariableManagerWSAdapter getVariableManagerWSAdapter() {
    if (variableManagerWSAdapter == null)
      _initVariableManagerWSAdapterProxy();
    return variableManagerWSAdapter;
  }
  
  public java.lang.String[] getCategories(java.lang.String categoryPath) throws java.rmi.RemoteException{
    if (variableManagerWSAdapter == null)
      _initVariableManagerWSAdapterProxy();
    return variableManagerWSAdapter.getCategories(categoryPath);
  }
  
  public framework.orchestra.thalesgroup.com.VariableManager.VariableWS getVariable(java.lang.String variablePath) throws java.rmi.RemoteException{
    if (variableManagerWSAdapter == null)
      _initVariableManagerWSAdapterProxy();
    return variableManagerWSAdapter.getVariable(variablePath);
  }
  
  public java.lang.String[] getAllContextNames() throws java.rmi.RemoteException{
    if (variableManagerWSAdapter == null)
      _initVariableManagerWSAdapterProxy();
    return variableManagerWSAdapter.getAllContextNames();
  }
  
  public framework.orchestra.thalesgroup.com.VariableManager.VariableWS[] getAllVariables() throws java.rmi.RemoteException{
    if (variableManagerWSAdapter == null)
      _initVariableManagerWSAdapterProxy();
    return variableManagerWSAdapter.getAllVariables();
  }
  
  public framework.orchestra.thalesgroup.com.VariableManager.VariableWS[] getVariables(java.lang.String categoryPath) throws java.rmi.RemoteException{
    if (variableManagerWSAdapter == null)
      _initVariableManagerWSAdapterProxy();
    return variableManagerWSAdapter.getVariables(categoryPath);
  }
  
  public java.lang.String getCurrentContextName() throws java.rmi.RemoteException{
    if (variableManagerWSAdapter == null)
      _initVariableManagerWSAdapterProxy();
    return variableManagerWSAdapter.getCurrentContextName();
  }
  
  public java.lang.String getSubstitutedValue(java.lang.String variablePath) throws java.rmi.RemoteException{
    if (variableManagerWSAdapter == null)
      _initVariableManagerWSAdapterProxy();
    return variableManagerWSAdapter.getSubstitutedValue(variablePath);
  }
  
  public void registerAsVariableConsumer(java.lang.String toolInstanceID) throws java.rmi.RemoteException{
    if (variableManagerWSAdapter == null)
      _initVariableManagerWSAdapterProxy();
    variableManagerWSAdapter.registerAsVariableConsumer(toolInstanceID);
  }
  
  public void unregisterAsVariableConsumer(java.lang.String toolInstanceID) throws java.rmi.RemoteException{
    if (variableManagerWSAdapter == null)
      _initVariableManagerWSAdapterProxy();
    variableManagerWSAdapter.unregisterAsVariableConsumer(toolInstanceID);
  }
  
  public framework.orchestra.thalesgroup.com.VariableManager.VariableWS[] getAllVariablesLocalized(java.lang.String locale) throws java.rmi.RemoteException{
    if (variableManagerWSAdapter == null)
      _initVariableManagerWSAdapterProxy();
    return variableManagerWSAdapter.getAllVariablesLocalized(locale);
  }
  
  public framework.orchestra.thalesgroup.com.VariableManager.VariableWS getVariableLocalized(java.lang.String variablePath, java.lang.String locale) throws java.rmi.RemoteException{
    if (variableManagerWSAdapter == null)
      _initVariableManagerWSAdapterProxy();
    return variableManagerWSAdapter.getVariableLocalized(variablePath, locale);
  }
  
  public framework.orchestra.thalesgroup.com.VariableManager.VariableWS[] getVariablesLocalized(java.lang.String categoryPath, java.lang.String locale) throws java.rmi.RemoteException{
    if (variableManagerWSAdapter == null)
      _initVariableManagerWSAdapterProxy();
    return variableManagerWSAdapter.getVariablesLocalized(categoryPath, locale);
  }
  
  public framework.orchestra.thalesgroup.com.VariableManager.VariableWS getVariableArtefactPath() throws java.rmi.RemoteException{
    if (variableManagerWSAdapter == null)
      _initVariableManagerWSAdapterProxy();
    return variableManagerWSAdapter.getVariableArtefactPath();
  }
  
  public framework.orchestra.thalesgroup.com.VariableManager.VariableWS getVariableConfigurationDirectory() throws java.rmi.RemoteException{
    if (variableManagerWSAdapter == null)
      _initVariableManagerWSAdapterProxy();
    return variableManagerWSAdapter.getVariableConfigurationDirectory();
  }
  
  public framework.orchestra.thalesgroup.com.VariableManager.VariableWS getVariableSharedDirectory() throws java.rmi.RemoteException{
    if (variableManagerWSAdapter == null)
      _initVariableManagerWSAdapterProxy();
    return variableManagerWSAdapter.getVariableSharedDirectory();
  }
  
  public framework.orchestra.thalesgroup.com.VariableManager.VariableWS getVariableTemporaryDirectory() throws java.rmi.RemoteException{
    if (variableManagerWSAdapter == null)
      _initVariableManagerWSAdapterProxy();
    return variableManagerWSAdapter.getVariableTemporaryDirectory();
  }
  
  public java.lang.String getCategorySeparator() throws java.rmi.RemoteException{
    if (variableManagerWSAdapter == null)
      _initVariableManagerWSAdapterProxy();
    return variableManagerWSAdapter.getCategorySeparator();
  }
  
  public framework.orchestra.thalesgroup.com.VariableManager.DeltaState[] configurationDirectoryUpdated(java.lang.String confDirPath, framework.orchestra.thalesgroup.com.VariableManager.ConfDirDelta[] deltas) throws java.rmi.RemoteException{
    if (variableManagerWSAdapter == null)
      _initVariableManagerWSAdapterProxy();
    return variableManagerWSAdapter.configurationDirectoryUpdated(confDirPath, deltas);
  }
  
  
}