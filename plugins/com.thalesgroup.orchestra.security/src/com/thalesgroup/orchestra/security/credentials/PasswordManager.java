/**
 * Copyright (c) THALES, 2014. All rights reserved.
 */
package com.thalesgroup.orchestra.security.credentials;

public interface PasswordManager {

    Credentials getCredentials(String key) throws CredentialsNotFoundException,
            CannotOpenDatabaseException;

    void addCredentials(String key, Credentials credentials)
            throws CredentialsExistException, CannotOpenDatabaseException;

    void deleteCredentials(String key) throws CredentialsNotFoundException,
            CannotOpenDatabaseException;

    void purgeDatabase() throws CannotOpenDatabaseException;;

}
