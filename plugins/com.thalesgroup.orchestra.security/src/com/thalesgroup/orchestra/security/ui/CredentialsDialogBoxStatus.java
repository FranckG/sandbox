/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.security.ui;

/**
 * Status of the credentials user input dialog box. <li>{@link #NOT_DISPLAYED}</li> <li>{@link #DISPLAYED_AND_CLOSE}</li> <li>{@link #DISPLAYED_AND_OK}</li> <li>
 * {@link #DISPLAYED_AND_CANCEL}</li>
 */
public enum CredentialsDialogBoxStatus {

  /**
   * {@link CredentialsDialogBox} was not displayed.
   */
  NOT_DISPLAYED(-1),
  /**
   * {@link CredentialsDialogBox} was displayed but user click on CLOSE windows button.
   */
  DISPLAYED_AND_CLOSE(0),
  /**
   * {@link CredentialsDialogBox} was displayed and user click on OK button.
   */
  DISPLAYED_AND_OK(1),
  /**
   * {@link CredentialsDialogBox} was displayed and user click on CANCEL button.
   */
  DISPLAYED_AND_CANCEL(2);

  private int _value;

  /**
   * @param value_p : The {@link CredentialsDialogBoxStatus} int value.
   */
  private CredentialsDialogBoxStatus(int value_p) {
    this._value = value_p;
  }

  /**
   * @return The {@link CredentialsDialogBoxStatus} value.
   */
  public int getValue() {
    return _value;
  }

}
