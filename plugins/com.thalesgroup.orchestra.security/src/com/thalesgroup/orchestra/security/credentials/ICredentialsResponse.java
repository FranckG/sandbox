/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.security.credentials;

import com.thalesgroup.orchestra.security.ui.CredentialsDialogBoxStatus;

/**
 * Interface who represent the 'getCredentials' API response. <br>
 * Cf. to: <br>
 * -> {@link ICredentialsResponse#getCredentialsUIStatus()} <br>
 * -> {@link ICredentialsResponse#getCredentialsData()} <br>
 */
public interface ICredentialsResponse {

  /**
   * @return the {@link CredentialsDialogBoxStatus}, the status of the credentials user input dialog box.
   */
  public abstract CredentialsDialogBoxStatus getCredentialsUIStatus();

  /**
   * @return the {@link Credentials}, the credential's data.
   */
  public abstract Credentials getCredentialsData();

}