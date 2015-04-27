package com.thalesgroup.orchestra.security.credentials;

import org.junit.Assert;

public class CredentialsAssert extends Assert {
    public static void assertEquals(String username, String password,
            Credentials credentials) {
        assertEquals(username, credentials.getUsername());
        assertEquals(password, credentials.getPassword());
    }
}