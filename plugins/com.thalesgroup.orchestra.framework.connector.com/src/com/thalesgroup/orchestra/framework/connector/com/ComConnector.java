/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.connector.com;

import java.text.MessageFormat;

import org.eclipse.core.runtime.IStatus;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Variant;
import com.thalesgroup.orchestra.framework.connector.AbstractConnector;
import com.thalesgroup.orchestra.framework.connector.CommandContext;
import com.thalesgroup.orchestra.framework.connector.CommandStatus;
import com.thalesgroup.orchestra.framework.connector.ConnectorActivator;
import com.thalesgroup.orchestra.framework.connector.IConnectorOptionConstants;
import com.thalesgroup.orchestra.framework.connector.helper.SerializationHelper;

/**
 * COM connector implementation.
 * @author t0076261
 */
public class ComConnector extends AbstractConnector {
  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#create(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  public CommandStatus create(CommandContext context_p) throws Exception {
    return invokeComService("create", context_p); //$NON-NLS-1$
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#documentaryExport(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  public CommandStatus documentaryExport(CommandContext context_p) throws Exception {
    return invokeComService("documentaryExport", context_p); //$NON-NLS-1$
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#executeSpecificCommand(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  @Override
  public CommandStatus executeSpecificCommand(CommandContext context_p) throws Exception {
    return invokeComService("executeSpecificCommand", context_p); //$NON-NLS-1$
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#expand(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  public CommandStatus expand(CommandContext context_p) throws Exception {
    return invokeComService("expand", context_p); //$NON-NLS-1$
  }

  /**
   * Invoke specified COM service for specified context.
   * @param methodName_p The COM service method name.
   * @param context_p
   * @return A not <code>null</code> {@link CommandStatus}.
   * @throws Exception
   */
  protected CommandStatus invokeComService(String methodName_p, CommandContext context_p) throws Exception {
    String progId = (String) getOptions().get(IConnectorOptionConstants.COM_PROG_ID);
    // Fatality !
    if (null == progId) {
      return new CommandStatus(IStatus.ERROR, MessageFormat.format(Messages.ComConnector_ErrorMessage_Configuration_NoProgId, getClass().getName(),
          getOptions().get(IConnectorOptionConstants.CONNECTOR_TYPE)), null, 0);
    }
    SerializationHelper serializationHelper = ConnectorActivator.getInstance().getSerializationHelper();
    String context = serializationHelper.serializeContext(context_p);
    // Fatality !
    if (null == context) {
      return new CommandStatus(IStatus.ERROR, MessageFormat.format(Messages.ComConnector_ErrorMessage_Serialization_Failed, methodName_p, getClass().getName(),
          getOptions().get(IConnectorOptionConstants.CONNECTOR_TYPE)), null, 0);
    }
    // Invoke COM bridge.
    CommandStatus resultingStatus = null;
    try {
      ComThread.InitSTA();
      ActiveXComponent comProg = null;
      // Prod00083444.
      // Make sure there is no concurrent access to the component creation.
      synchronized (this) {
        comProg = new ActiveXComponent(progId);
      }
      Variant result = comProg.invoke(methodName_p, new Variant[] { new Variant(context) });
      resultingStatus = serializationHelper.deserializeStatus(result.getString());
      if (null == resultingStatus) {
        return new CommandStatus(IStatus.ERROR, MessageFormat.format(Messages.ComConnector_ErrorMessage_Deserialization_UnableToDeserialize, methodName_p,
            getClass().getName(), getOptions().get(IConnectorOptionConstants.CONNECTOR_TYPE)), null, 0);
      }
    } finally {
      ComThread.Release();
    }
    return resultingStatus;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#lmExport(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  @Override
  public CommandStatus lmExport(CommandContext context_p) throws Exception {
    return invokeComService("lmExport", context_p); //$NON-NLS-1$
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.IConnector#navigate(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  public CommandStatus navigate(CommandContext context_p) throws Exception {
    return invokeComService("navigate", context_p); //$NON-NLS-1$
  }

  /**
   * @see com.thalesgroup.orchestra.framework.connector.AbstractConnector#search(com.thalesgroup.orchestra.framework.connector.CommandContext)
   */
  @Override
  public CommandStatus search(CommandContext context_p) throws Exception {
    return invokeComService("search", context_p); //$NON-NLS-1$
  }
}