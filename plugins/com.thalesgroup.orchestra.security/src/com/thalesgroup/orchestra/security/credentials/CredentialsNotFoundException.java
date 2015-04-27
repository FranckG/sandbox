/**
 * Copyright (c) THALES, 2014. All rights reserved.
 */
package com.thalesgroup.orchestra.security.credentials;

public class CredentialsNotFoundException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1983490722524808875L;

    public CredentialsNotFoundException() {
        super("Credentials not found");
    }
}
