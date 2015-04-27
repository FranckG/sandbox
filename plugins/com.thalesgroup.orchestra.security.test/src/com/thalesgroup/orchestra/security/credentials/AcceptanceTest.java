/**
 * Copyright (c) THALES, 2014. All rights reserved.
 */
package com.thalesgroup.orchestra.security.credentials;

import static org.junit.Assert.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.junit.Test;

public class AcceptanceTest {

    @Test
    public void acceptanceTest() throws Exception {
        Path directory = Files.createTempDirectory("libauth-usage");
        directory.toFile().deleteOnExit();
        String path = Paths.get(directory.toString(),
                UUID.randomUUID().toString()).toString();
        SecurePasswordManager passwordManager = new SecurePasswordManager(
                path.toString(), "toto");
        passwordManager.addCredentials("key", new Credentials("username",
                "password"));
        passwordManager = new SecurePasswordManager(path.toString(), "toto");
        passwordManager.addCredentials("key2", new Credentials("u2", "p2"));

        passwordManager = new SecurePasswordManager(path.toString(), "toto");
        Credentials credentials = passwordManager.getCredentials("key");
        CredentialsAssert.assertEquals("username", "password", credentials);

        passwordManager = new SecurePasswordManager(path.toString(), "toto");
        credentials = passwordManager.getCredentials("key2");
        CredentialsAssert.assertEquals("u2", "p2", credentials);

        passwordManager = new SecurePasswordManager(path.toString(), "toto");
        passwordManager.deleteCredentials("key2");

        try {
            passwordManager = new SecurePasswordManager(path.toString(), "toto");
            passwordManager.getCredentials("key2");
            fail("Exception should be raised");
        } catch (CredentialsNotFoundException e) {
        }

        try {
            passwordManager = new SecurePasswordManager(path.toString(), "toto");
            passwordManager.addCredentials("key", new Credentials("username",
                    "password"));
            fail("Exception should be raised");
        } catch (CredentialsExistException e) {
        }

        passwordManager = new SecurePasswordManager(path.toString(), "toto");
        passwordManager.purgeDatabase();
        try {
            passwordManager = new SecurePasswordManager(path.toString(), "toto");
            passwordManager.getCredentials("key");
            fail("Exception should be raised");
        } catch (CredentialsNotFoundException e) {
        }

        try {
            passwordManager = new SecurePasswordManager(path.toString(), "toto");
            passwordManager.purgeDatabase();
            passwordManager.deleteCredentials("key");
            fail("Exception should be raised");
        } catch (CredentialsNotFoundException e) {
        }

        try {
            passwordManager = new SecurePasswordManager(path.toString(), "toti");
            passwordManager.purgeDatabase();
            fail("Exception should be raised");
        } catch (CannotOpenDatabaseException e) {
            System.out.println(e.getMessage());
        }

        try {
            Path readonly = Files.createTempDirectory("readonly");
            File file = readonly.toFile();
            file.setReadOnly();
            file.deleteOnExit();
            String unauthorizedPath = Paths.get(readonly.toString(),
                    UUID.randomUUID().toString()).toString();
            passwordManager = new SecurePasswordManager(unauthorizedPath,
                    "toti");
            passwordManager.purgeDatabase();
            fail("Exception should be raised");
        } catch (CannotOpenDatabaseException e) {
            System.out.println(e.getMessage());
        }
    }
}
