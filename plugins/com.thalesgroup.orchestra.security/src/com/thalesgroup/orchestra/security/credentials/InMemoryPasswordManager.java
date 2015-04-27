/**
 * Copyright (c) THALES, 2014. All rights reserved.
 */
package com.thalesgroup.orchestra.security.credentials;

import java.util.HashMap;

/**
 * In memory implementation of PasswordManager. DO NOT USE IN PRODUCTION!
 * 
 */
public class InMemoryPasswordManager implements PasswordManager {

    private HashMap<String, Credentials> db = new HashMap<String, Credentials>();

    public InMemoryPasswordManager(String databasePath, String masterPassword)
            throws CannotOpenDatabaseException {
        if (!"masterPassword".equals(masterPassword)) {
            throw new CannotOpenDatabaseException("Invalid password");
        }
    }

    @Override
    public Credentials getCredentials(String key)
            throws CredentialsNotFoundException {
        if (!db.containsKey(key)) {
            throw new CredentialsNotFoundException();
        }
        return db.get(key);
    }

    @Override
    public void addCredentials(String key, Credentials credentials)
            throws CredentialsExistException {
        if (db.containsKey(key)) {
            throw new CredentialsExistException();
        }
        db.put(key, credentials);
    }

    @Override
    public void deleteCredentials(String key)
            throws CredentialsNotFoundException {
        throw new CredentialsNotFoundException();

    }

    @Override
    public void purgeDatabase() {
        db.clear();
    }
}
