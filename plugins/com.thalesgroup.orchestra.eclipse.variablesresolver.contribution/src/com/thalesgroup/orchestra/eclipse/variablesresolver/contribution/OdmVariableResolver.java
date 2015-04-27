/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.eclipse.variablesresolver.contribution;

import java.rmi.RemoteException;
import java.text.MessageFormat;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.variables.IDynamicVariable;
import org.eclipse.core.variables.IDynamicVariableResolver;
import org.eclipse.ui.statushandlers.StatusManager;

import com.thalesgroup.orchestra.framework.model.IVariable;

import framework.orchestra.thalesgroup.com.VariableManagerAPI.VariableManagerAdapter;

import javax.xml.rpc.ServiceException;

/**
 * @author T0052089
 */
public class OdmVariableResolver implements IDynamicVariableResolver {

  /**
   * @see org.eclipse.core.variables.IDynamicVariableResolver#resolveValue(org.eclipse.core.variables.IDynamicVariable, java.lang.String)
   */
  @Override
  public String resolveValue(IDynamicVariable variable_p, String argument_p) throws CoreException {
    String pluginId = OdmVariableResolverActivator.PLUGIN_ID;
    try {
      // Try to resolve the variable.
      IVariable variable = VariableManagerAdapter.getInstance().getVariable(argument_p);
      if (null == variable) {
        // Variable not found.
        IStatus variableNotFoundError =
            new Status(IStatus.ERROR, pluginId, MessageFormat.format(Messages.OdmVariableResolver_Error_NotFoundVariable, argument_p));
        throw new CoreException(variableNotFoundError);
      }
      List<String> values = variable.getValues();
      if (null == values || values.isEmpty()) {
        // No value in the variable.
        IStatus noValueError = new Status(IStatus.ERROR, pluginId, MessageFormat.format(Messages.OdmVariableResolver_Error_NoValueInVariable, argument_p));
        throw new CoreException(noValueError);
      }
      if (1 != values.size()) {
        // Too many values.
        IStatus tooValueWarning =
            new Status(IStatus.WARNING, pluginId, MessageFormat.format(Messages.OdmVariableResolver_Warning_MoreThanOneValueInVariable, argument_p));
        StatusManager.getManager().handle(tooValueWarning, StatusManager.LOG);
      }
      // Return the variable's value (first value if it contains several ones).
      return values.get(0);
    } catch (RemoteException exception_p) {
      IStatus gettingVariableError =
          new Status(IStatus.ERROR, pluginId, MessageFormat.format(Messages.OdmVariableResolver_Error_CantResolveVariable, argument_p), exception_p);
      throw new CoreException(gettingVariableError);
    } catch (ServiceException exception_p) {
      IStatus initializationError = new Status(IStatus.ERROR, pluginId, Messages.OdmVariableResolver_Error_Initialization, exception_p);
      throw new CoreException(initializationError);
    }

  }
}
