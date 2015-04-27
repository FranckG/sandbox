/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.connector;

import java.util.Collection;

import org.eclipse.core.runtime.IStatus;

import com.thalesgroup.orchestra.framework.internal.connector.RequestHandler;
import com.thalesgroup.orchestra.framework.models.connector.Connector;

/**
 * Connector registry interface.
 * @author t0076261
 */
public interface IConnectorRegistry {
  /**
   * Test if a declared connector is associated to an association in current context.<br>
   * If so, the connector is considered as configured.<br>
   * If not, the connector is installed but not usable yet : this is a warning.
   * @return
   */
  public IStatus checkConnectorsAssociationAvailability();

  /**
   * Return the {@link IConnectorDescriptor} for specified type.
   * @param type_p
   * @return
   */
  public IConnectorDescriptor getConnectorDescriptor(String type_p);

  /**
   * Get all connector descriptors.
   * @return
   */
  public Collection<IConnectorDescriptor> getDescriptors();

  /**
   * A connector descriptor.
   * @author t0076261
   */
  public interface IConnectorDescriptor {
    /**
     * Execute a specific command, as described by specified context.<br>
     * This is a dedicated path that bypasses the {@link RequestHandler} behavior.<br>
     * This is only intended to be invoked in specific circumstances such as from another connector.<br>
     * In any other case, the usual way of posting a request to the Framework should be used instead.
     * @param context_p A not <code>null</code> {@link CommandContext}.
     * @return A not <code>null</code> {@link CommandStatus} describing the result.
     */
    public CommandStatus executeSpecificCommand(CommandContext context_p);

    /**
     * Get connector model.
     * @return
     */
    public Connector getConnectorModel();

    /**
     * Get icons directory absolute path.
     * @return
     */
    public String getIconsDirectoryPath();

    /**
     * Get connector type.
     * @return
     */
    public String getType();

    /**
     * Is specified command supported by this connector ?
     * @param command_p
     * @return <code>true</code> if so, <code>false</code> if it is either not supported or <code>null</code>.
     */
    public boolean isCommandSupported(String command_p);
  }
}