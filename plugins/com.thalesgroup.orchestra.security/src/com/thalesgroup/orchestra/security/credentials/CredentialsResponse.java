/*******************************************************************************
 *  Copyright (c) 2014 Thales Corporate Services S.A.S.
 *******************************************************************************/
package com.thalesgroup.orchestra.security.credentials;

import com.thalesgroup.orchestra.security.ui.CredentialsDialogBoxStatus;

/**
 * The {@link CredentialsResponse} data object who contains {@link CredentialsDialogBoxStatus} and {@link Credentials} data.
 */
public class CredentialsResponse implements ICredentialsResponse {

  private CredentialsDialogBoxStatus _credentialsUIStatus = CredentialsDialogBoxStatus.NOT_DISPLAYED;
  private Credentials _credentialsData;

  /**
   * Constructor.
   * @param credentialsUIStatus_p : The {@link CredentialsDialogBoxStatus} who represent credentials input dialog box status.
   * @param credentialsData_p : The {@link Credentials} data.
   */
  public CredentialsResponse(final CredentialsDialogBoxStatus credentialsUIStatus_p, final Credentials credentialsData_p) {
    this._credentialsUIStatus = credentialsUIStatus_p;
    this._credentialsData = credentialsData_p;
  }

  /**
   * @see com.thalesgroup.orchestra.security.credentials.ICredentialsResponse#getCredentialsUIStatus()
   */
  @Override
  public CredentialsDialogBoxStatus getCredentialsUIStatus() {
    return this._credentialsUIStatus;
  }

  /**
   * @see com.thalesgroup.orchestra.security.credentials.ICredentialsResponse#setCredentialsUIStatus()
   */
  public void setCredentialsUIStatus(final CredentialsDialogBoxStatus credentialsUIStatus_p) {
    this._credentialsUIStatus = credentialsUIStatus_p;
  }

  /**
   * @see com.thalesgroup.orchestra.security.credentials.ICredentialsResponse#getCredentialsData()
   */
  @Override
  public Credentials getCredentialsData() {
    return this._credentialsData;
  }

  /**
   * @see com.thalesgroup.orchestra.security.credentials.ICredentialsResponse#setCredentialsDatas()
   */
  public void setCredentialsData(final Credentials credentialsData_p) {
    this._credentialsData = credentialsData_p;
  }
}