/**
 * Copyright (c) THALES, 2014. All rights reserved.
 */
package com.thalesgroup.orchestra.security;

import com.thalesgroup.orchestra.security.credentials.CannotOpenDatabaseException;
import com.thalesgroup.orchestra.security.credentials.Credentials;
import com.thalesgroup.orchestra.security.credentials.CredentialsExistException;
import com.thalesgroup.orchestra.security.credentials.CredentialsNotFoundException;
import com.thalesgroup.orchestra.security.credentials.CredentialsResponse;
import com.thalesgroup.orchestra.security.credentials.ICredentialsResponse;
import com.thalesgroup.orchestra.security.credentials.PasswordManager;
import com.thalesgroup.orchestra.security.credentials.SecurePasswordManager;
import com.thalesgroup.orchestra.security.ui.CredentialsDialogBox;
import com.thalesgroup.orchestra.security.ui.CredentialsDialogBoxStatus;

/**
 * This class manages security {@link Credentials} data.
 */
public class CredentialsManagement {

  private static PasswordManager _credentialsManager;

  private static final String DEFAULT_MASPTER_PASSWORD = "masterPassword"; //$NON-NLS-1$

  /**
   * {@link CredentialsManagement} basic constructor.
   * @param databasePath_p : The {@link String} file system path to store {@link Credentials} database file.
   * @param masterPassword_p : The {@link String} master password of the {@link Credentials} database file.
   * @throws CannotOpenDatabaseException : This {@link Exception} is raised if {@link Credentials} database cannot be open. For example if no database file
   *           found in the specified file system path or if bad masterPassword for this database file).
   */
  public CredentialsManagement(final String databasePath_p, final String masterPassword_p) throws CannotOpenDatabaseException {
    _credentialsManager = new SecurePasswordManager(databasePath_p, masterPassword_p);
  }

  /**
   * {@link CredentialsManagement} basic constructor.
   * @param databasePath_p : The {@link String} file system path to store {@link Credentials} database file.
   * @throws CannotOpenDatabaseException : This {@link Exception} is raised if {@link Credentials} database cannot be open. For example if no database file
   *           found in the specified file system path or if bad masterPassword for this database file).
   */
  public CredentialsManagement(final String databasePath_p) throws CannotOpenDatabaseException {
    _credentialsManager = new SecurePasswordManager(databasePath_p, DEFAULT_MASPTER_PASSWORD);
  }

  /**
   * @return the _credentialsManager
   */
  private PasswordManager getCredentialsManager() {
    return _credentialsManager;
  }

  /**
   * This method returns {@link Credentials} data for a specific {@link String} identifier.<br>
   * <br>
   * If {@link Credentials} doesn't exist for the specified {@link String} identifier parameter a dialog box is displayed for allowing user to input
   * login/password informations.<br>
   * If dialog box validation (OK button), credentials data entered by the user are registered in credentials database and returned.
   * @param credentials_id_p : The {@link String} identifier of {@link Credentials} data to retrieved.
   * @param optionalUIPasswordConfirmation_p : {@link Boolean} for asking optional password confirmation field on user input credentials dialog box.
   * @param optionalUIOnTopMessage_p : If not <code>null</code> display optional {@link String} message on top of the user input credentials dialog box. <br>
   *          <ul>
   *          <li>This message can be HTML formatted.
   *          </ul>
   * @return {@link ICredentialsResponse}. <br>
   *         <ul>
   *         <li>If {@link Credentials} do not exists a dialog box is opened and user must input login/password.
   *         <li>If user CANCEL or CLOSE credentials input dialog box, <code>null</code> {@link ICredentialsResponse}.get{@link Credentials} are returned.</br>
   *         </ul>
   * @throws CannotOpenDatabaseException : This {@link Exception} is raised if {@link Credentials} file data access problem occurs.
   */
  public ICredentialsResponse getCredentialsWithDialogBox(final String credentials_id_p, final boolean optionalUIPasswordConfirmation_p,
      final String optionalUIOnTopMessage_p) throws CannotOpenDatabaseException {
    // preconditions
    if ((null == credentials_id_p) || credentials_id_p.isEmpty())
      return null;

    PasswordManager credentialsManager = this.getCredentialsManager();
    try {
      // credentials already exists, return them.
      Credentials credentials = credentialsManager.getCredentials(credentials_id_p);
      return new CredentialsResponse(CredentialsDialogBoxStatus.NOT_DISPLAYED, credentials);
    } catch (CredentialsNotFoundException exception_p) {
      // credentials doesn't exist, display login password dialog box.
      ICredentialsResponse credentialsResponse = CredentialsDialogBox.show(optionalUIPasswordConfirmation_p, optionalUIOnTopMessage_p);
      if (credentialsResponse.getCredentialsUIStatus() == CredentialsDialogBoxStatus.DISPLAYED_AND_OK)
        try {
          credentialsManager.addCredentials(credentials_id_p, credentialsResponse.getCredentialsData());
        } catch (CredentialsExistException exception_p1) {
          // should not be raised.
        }
      return credentialsResponse;
    }
  }

  /**
   * This method delete {@link Credentials} data identified by is {@link String} identifier.
   * @param credentials_id_p : The {@link String} identifier of {@link Credentials} data to delete.
   * @return <code>true</code> if {@link Credentials} data exists and was cleared successfully otherwise <code>false</code>.
   * @throws CredentialsNotFoundException : This {@link Exception} is raised if {@link Credentials} data to delete doesn't exist for the {@link String}
   *           identifier specified.
   * @throws CannotOpenDatabaseException : This {@link Exception} is raised if {@link Credentials} file data access problem occurs.
   */
  public boolean delCredentials(final String credentials_id_p) throws CredentialsNotFoundException, CannotOpenDatabaseException {
    // preconditions
    if ((null == credentials_id_p) || credentials_id_p.isEmpty())
      return false;

    this.getCredentialsManager().deleteCredentials(credentials_id_p);
    return true;
  }

  /**
   * This method return {@link Credentials} data for a specific {@link String} identifier.
   * @param credentials_id_p : The {@link String} identifier of {@link Credentials} data to get.
   * @return {@link Credentials} object if exists; if not <code>null</code>.
   * @throws CredentialsNotFoundException : This {@link Exception} is raised if {@link Credentials} data to get doesn't exist for the {@link String} identifier
   *           specified.
   * @throws CannotOpenDatabaseException : This {@link Exception} is raised if {@link Credentials} file data access problem occurs.
   */
  public Credentials getCredentials(final String credentials_id_p) throws CredentialsNotFoundException, CannotOpenDatabaseException {
    // preconditions
    if ((null == credentials_id_p) || credentials_id_p.isEmpty())
      return null;

    return this.getCredentialsManager().getCredentials(credentials_id_p);
  }

  /**
   * This method add {@link Credentials} data for a specific {@link String} identifier.
   * @param credentials_id_p : The {@link String} identifier of {@link Credentials} data to add.
   * @param credentials_p : The {@link Credentials} data to add.
   * @return <code>true</code> if {@link Credentials} data was added successfully otherwise <code>false</code>.
   * @throws CredentialsExistException : This {@link Exception} is raised if {@link Credentials} data already exists for the {@link String} identifier
   *           specified.
   * @throws CannotOpenDatabaseException : This {@link Exception} is raised if {@link Credentials} file data access problem occurs.
   */
  public boolean addCredentials(final String credentials_id_p, final Credentials credentials_p) throws CredentialsExistException, CannotOpenDatabaseException {
    if ((null == credentials_id_p) || credentials_id_p.isEmpty() || (null == credentials_p))
      return false;

    this.getCredentialsManager().addCredentials(credentials_id_p, credentials_p);
    return true;
  }

  /**
   * This method delete ALL {@link Credentials} data of database.
   * @return <code>true</code> if all {@link Credentials} data was deleted successfully otherwise <code>false</code>.
   * @throws CannotOpenDatabaseException : This {@link Exception} is raised if {@link Credentials} file data access problem occurs.
   */
  public boolean purgeCredentialsDatabase() throws CannotOpenDatabaseException {
    this.getCredentialsManager().purgeDatabase();
    return true;
  }

  /**
   * This method set {@link Credentials} data for a specific {@link String} identifier without popping up a dialog box.
   * @param credentials_id_p The {@link String} identifier of {@link Credentials} data to set.
   * @param login_p Login
   * @param password_p Password
   * @return <code>true</code> if {@link Credentials} data was successfully set, <code>false</code> otherwise.
   * @throws CannotOpenDatabaseException
   */
  public boolean setCredentials(final String credentials_id_p, final String login_p, final String password_p) throws CannotOpenDatabaseException {
    boolean status;
    try {
      // Remove credentials first
      status = this.delCredentials(credentials_id_p);
    } catch (CredentialsNotFoundException exception_p) {
      status = true;
    }
    if (status)
      try {
        status = this.addCredentials(credentials_id_p, new Credentials(login_p, password_p));
      } catch (CredentialsExistException exception_p) {
        // Should not happen
        status = false;
      }
    return status;
  }
}