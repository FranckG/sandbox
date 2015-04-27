/**
 * Copyright (c) THALES, 2014. All rights reserved.
 */
package com.thalesgroup.orchestra.security;

import java.util.HashMap;

import com.thalesgroup.orchestra.security.credentials.CannotOpenDatabaseException;
import com.thalesgroup.orchestra.security.credentials.Credentials;
import com.thalesgroup.orchestra.security.credentials.CredentialsExistException;
import com.thalesgroup.orchestra.security.credentials.CredentialsNotFoundException;
import com.thalesgroup.orchestra.security.credentials.CredentialsResponse;
import com.thalesgroup.orchestra.security.credentials.ICredentialsResponse;
import com.thalesgroup.orchestra.security.ui.CredentialsDialogBox;
import com.thalesgroup.orchestra.security.ui.CredentialsDialogBoxStatus;

/**
 * This class manages security {@link Credentials} data storage in memory.
 */
public class InMemoryCredentialsManagement {

  // InMemoryCredentialsDatabase singleton instance
  private static HashMap<String, Credentials> _inMemoryCredentialsDatabase;

  /**
   * @return the inMemoryCredentialsDatabase {@link HashMap} singleton
   */
  private static HashMap<String, Credentials> getInMemoryCredentialsDatabase() {
    if (null == _inMemoryCredentialsDatabase)
      _inMemoryCredentialsDatabase = new HashMap<String, Credentials>();
    return _inMemoryCredentialsDatabase;
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
   */
  public static ICredentialsResponse getCredentialsWithDialogBox(final String credentials_id_p, final boolean optionalUIPasswordConfirmation_p,
      final String optionalUIOnTopMessage_p) {
    // preconditions
    if ((null == credentials_id_p) || credentials_id_p.isEmpty())
      return null;

    HashMap<String, Credentials> credentialsDatabase = getInMemoryCredentialsDatabase();

    // credentials data exists
    if (credentialsDatabase.containsKey(credentials_id_p)) {
      Credentials credentials = credentialsDatabase.get(credentials_id_p);
      return new CredentialsResponse(CredentialsDialogBoxStatus.NOT_DISPLAYED, credentials);
    }

    // credentials doesn't exist, display UI to user.
    ICredentialsResponse credentialsResponse = CredentialsDialogBox.show(optionalUIPasswordConfirmation_p, optionalUIOnTopMessage_p);
    if (credentialsResponse.getCredentialsUIStatus() == CredentialsDialogBoxStatus.DISPLAYED_AND_OK)
      credentialsDatabase.put(credentials_id_p, credentialsResponse.getCredentialsData());
    return credentialsResponse;
  }

  /**
   * This method return {@link Credentials} data for a specific {@link String} identifier.
   * @param credentials_id_p : The {@link String} identifier of {@link Credentials} data to get.
   * @return {@link Credentials} object if exists; if not <code>null</code>.
   * @throws CredentialsNotFoundException : This {@link Exception} is raised if {@link Credentials} data to get doesn't exist for the {@link String} identifier
   *           specified.
   */
  public static Credentials getCredentials(final String credentials_id_p) throws CredentialsNotFoundException {
    // preconditions
    if ((null == credentials_id_p) || credentials_id_p.isEmpty())
      return null;

    HashMap<String, Credentials> credentialsDatabase = getInMemoryCredentialsDatabase();
    if (!credentialsDatabase.containsKey(credentials_id_p))
      throw new CredentialsNotFoundException();

    return credentialsDatabase.get(credentials_id_p);
  }

  /**
   * This method delete {@link Credentials} data identified by is {@link String} identifier.
   * @param credentials_id_p : The {@link String} identifier of {@link Credentials} data to delete.
   * @return <code>true</code> if {@link Credentials} data exists and was cleared successfully otherwise <code>false</code>.
   * @throws CredentialsNotFoundException : This {@link Exception} is raised if {@link Credentials} data to delete doesn't exist for the {@link String}
   *           identifier specified.
   */
  public static boolean delCredentials(final String credentials_id_p) throws CredentialsNotFoundException {
    // preconditions
    if ((null == credentials_id_p) || credentials_id_p.isEmpty())
      return false;

    HashMap<String, Credentials> credentialsDatabase = getInMemoryCredentialsDatabase();
    if (!credentialsDatabase.containsKey(credentials_id_p))
      throw new CredentialsNotFoundException();

    credentialsDatabase.remove(credentials_id_p);
    return true;
  }

  /**
   * This method add {@link Credentials} data for a specific {@link String} identifier.
   * @param credentials_id_p : The {@link String} identifier of {@link Credentials} data to add.
   * @param credentials_p : The {@link Credentials} data to add.
   * @return <code>true</code> if {@link Credentials} data was added successfully otherwise <code>false</code>.
   * @throws CredentialsExistException : This {@link Exception} is raised if {@link Credentials} data already exists for the {@link String} identifier
   *           specified.
   */
  public static boolean addCredentials(final String credentials_id_p, final Credentials credentials_p) throws CredentialsExistException {
    if ((null == credentials_id_p) || credentials_id_p.isEmpty() || (null == credentials_p))
      return false;

    HashMap<String, Credentials> credentialsDatabase = getInMemoryCredentialsDatabase();

    if (credentialsDatabase.containsKey(credentials_id_p))
      throw new CredentialsExistException();

    credentialsDatabase.put(credentials_id_p, credentials_p);
    return true;
  }

  /**
   * This method delete ALL {@link Credentials} data of database.
   * @return <code>true</code> if all {@link Credentials} data was deleted successfully otherwise <code>false</code>.
   */
  public static boolean purgeCredentialsDatabase() {
    try {
      getInMemoryCredentialsDatabase().clear();
      return true;
    } catch (Exception exception_p) {
      return false;
    }
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
      status = delCredentials(credentials_id_p);
    } catch (CredentialsNotFoundException exception_p) {
      status = true;
    }
    if (status)
      try {
        status = addCredentials(credentials_id_p, new Credentials(login_p, password_p));
      } catch (CredentialsExistException exception_p) {
        // Should not happen
        status = false;
      }
    return status;
  }
}