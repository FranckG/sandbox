/**
 * Copyright (c) THALES, 2014. All rights reserved.
 */
package com.thalesgroup.orchestra.security.credentials;

import org.junit.Test;

public class CredentialsTest {

    @Test
    public void canGetUsernameAndPasswordFromCredentials() {
        final String username = "username";
        final String password = "password";
        Credentials credentials = new Credentials(username, password);

        CredentialsAssert.assertEquals(username, password, credentials);
    }
}
