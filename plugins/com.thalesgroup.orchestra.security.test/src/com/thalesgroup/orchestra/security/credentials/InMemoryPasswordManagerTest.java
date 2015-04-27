/**
 * Copyright (c) THALES, 2014. All rights reserved.
 */
package com.thalesgroup.orchestra.security.credentials;

import org.junit.Test;

public class InMemoryPasswordManagerTest {

    @Test
    public void shouldTakeDatabasePathAndMasterPasswordInConstructor()
            throws CannotOpenDatabaseException {
        createPasswordManager();
    }

    private PasswordManager createPasswordManager()
            throws CannotOpenDatabaseException {
        final String databasePath = "databasePath";
        final String masterPassword = "masterPassword";
        return new InMemoryPasswordManager(databasePath, masterPassword);
    }

    @Test(expected = CannotOpenDatabaseException.class)
    public void cannotOpenDatabaseWithWrongPassword() throws Exception {
        new InMemoryPasswordManager("path", null);
    }

    @Test
    public void canAddAngGetCredentials() throws Exception {
        final String key = "myKey";
        final String username = "myUsername";
        final String password = "myPassword";
        PasswordManager passwordManager = createPasswordManager();
        passwordManager
                .addCredentials(key, new Credentials(username, password));
        Credentials credentials = passwordManager.getCredentials(key);

        CredentialsAssert.assertEquals(username, password, credentials);
    }

    @Test(expected = CredentialsNotFoundException.class)
    public void getKeyWithoutCredentialsShouldThrowException() throws Exception {
        PasswordManager passwordManager = createPasswordManager();
        passwordManager.getCredentials("someKey");
    }

    @Test(expected = CredentialsNotFoundException.class)
    public void deleteInexistantKeyShouldThrowException() throws Exception {
        PasswordManager passwordManager = createPasswordManager();
        passwordManager.deleteCredentials("someKey");
    }

    @Test(expected = CredentialsExistException.class)
    public void addExistingKeyShouldThrowException() throws Exception {
        PasswordManager passwordManager = createPasswordManager();
        passwordManager.addCredentials("key", new Credentials("u1", "p1"));
        passwordManager.addCredentials("key", new Credentials("u2", "p2"));
    }

    @Test
    public void getWithSeveralCredentialsShouldReturnCorrectCredentials()
            throws Exception {
        PasswordManager passwordManager = createPasswordManager();
        passwordManager.addCredentials("key1", new Credentials("u1", "p1"));
        passwordManager.addCredentials("key2", new Credentials("u2", "p2"));
        passwordManager.addCredentials("key3", new Credentials("u3", "p3"));

        Credentials credentials = passwordManager.getCredentials("key1");
        CredentialsAssert.assertEquals("u1", "p1", credentials);

        credentials = passwordManager.getCredentials("key2");
        CredentialsAssert.assertEquals("u2", "p2", credentials);

        credentials = passwordManager.getCredentials("key3");
        CredentialsAssert.assertEquals("u3", "p3", credentials);
    }

    @Test
    public void purgeWithNoCredentialsShouldDoNothing() throws Exception {
        PasswordManager passwordManager = createPasswordManager();
        passwordManager.purgeDatabase();
    }

    @Test
    public void purgeWithCredentialsShouldDeletesAllCredentials()
            throws Exception {
        PasswordManager passwordManager = createPasswordManager();
        passwordManager.addCredentials("key1", new Credentials("11", "p11"));
        passwordManager.addCredentials("key2", new Credentials("22", "p22"));
        passwordManager.addCredentials("key3", new Credentials("33", "p33"));

        passwordManager.purgeDatabase();

        passwordManager.addCredentials("key1", new Credentials("u1", "p1"));
        passwordManager.addCredentials("key2", new Credentials("u2", "p2"));
        passwordManager.addCredentials("key3", new Credentials("u3", "p3"));

        Credentials credentials = passwordManager.getCredentials("key1");
        CredentialsAssert.assertEquals("u1", "p1", credentials);

        credentials = passwordManager.getCredentials("key2");
        CredentialsAssert.assertEquals("u2", "p2", credentials);

        credentials = passwordManager.getCredentials("key3");
        CredentialsAssert.assertEquals("u3", "p3", credentials);
    }
}
