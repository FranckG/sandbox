package com.thalesgroup.orchestra.eclipse.variablesresolver.contribution;

import java.io.File;
import java.rmi.RemoteException;
import java.text.MessageFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.variableresolvers.PathVariableResolver;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.statushandlers.StatusManager;

import com.thalesgroup.orchestra.framework.model.IVariable;

import framework.orchestra.thalesgroup.com.VariableManagerAPI.VariableManagerAdapter;

import javax.xml.rpc.ServiceException;

public class OdmPathVariableResolver extends PathVariableResolver {
  /**
   * ODM path variables pattern.
   */
  private static final Pattern ODM_PATH_RESOLVER_PATTERN = Pattern.compile("odm-(>(\\w)+){2,}"); //$NON-NLS-1$

  private static final Pattern ODM_PATH_PATTERN = Pattern.compile("\\$\\{\\w+:(\\\\\\w+){2,}\\}"); //$NON-NLS-1$

  /**
   * @see org.eclipse.core.resources.variableresolvers.PathVariableResolver#getValue(java.lang.String, org.eclipse.core.resources.IResource)
   */
  @Override
  public String getValue(String variablePath_p, IResource resource_p) {
    String pluginId = OdmVariableResolverActivator.PLUGIN_ID;
    // Precondition, check pattern.
    if (!ODM_PATH_RESOLVER_PATTERN.matcher(variablePath_p).matches()) {
      return null;
    }
    // Create an ODM variable path from the given user path.
    // odm->Category0>Variable0 becomes \Category0\Variable0 .
    String formattedVariablePath = variablePath_p.substring(4).replace('>', '\\');
    try {
      IVariable variable = VariableManagerAdapter.getInstance().getVariable(formattedVariablePath);
      if (null == variable) {
        // No value in the variable.
        return null;
      }
      List<String> values = variable.getValues();
      if (null == values || values.isEmpty()) {
        // Too many values.
        return null;
      }
      // Get the variable's value (first value if it contains several ones).
      String result = values.get(0);
      // Check the given value is fully resolved.
      Matcher m = ODM_PATH_PATTERN.matcher(result);
      if (m.find()) {
        return null;
      }
      return new File(result).toURI().toASCIIString();
    } catch (RemoteException exception_p) {
      IStatus gettingVariableError =
          new Status(IStatus.ERROR, pluginId, MessageFormat.format(Messages.OdmVariableResolver_Error_CantResolveVariable, variablePath_p));
      StatusManager.getManager().handle(gettingVariableError, StatusManager.BLOCK);
      return null;
    } catch (ServiceException exception_p) {
      IStatus initializationError = new Status(IStatus.ERROR, pluginId, Messages.OdmVariableResolver_Error_Initialization);
      StatusManager.getManager().handle(initializationError, StatusManager.BLOCK);
      return null;
    }
  }
}
